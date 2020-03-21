package com.jeeban.map.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.jeeban.map.R;
import com.jeeban.map.databinding.ActivityMainBinding;
import com.jeeban.map.model.BaseResponse;
import com.jeeban.map.model.DistanceMatrix;
import com.jeeban.map.model.Element;
import com.jeeban.map.model.Geofence;
import com.jeeban.map.model.Row;
import com.jeeban.map.model.UserDistance;
import com.jeeban.map.network.ApiClient;
import com.jeeban.map.util.AppConstant;
import com.jeeban.map.util.Utils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.jeeban.map.util.AppConstant.API_KEY;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private ActivityMainBinding binding;
    private List<Geofence> geofences;
    private GoogleMap googleMap;
    private List<Double> distances = new ArrayList<>();
    private List<Observable<DistanceMatrix>> observableList = new ArrayList<>();
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);

        initUI();

        binding.btnSortestDistance.setOnClickListener(v -> calculateDistance());

    }

    private void initUI() {
        progressDialog = new ProgressDialog(this);
        geofences = ((BaseResponse<List<Geofence>>)getIntent().getSerializableExtra(AppConstant.GEOFENCE_LIST)).getData();
        initilizeMap();
    }

    private void initilizeMap() {
        ((MapFragment) getFragmentManager().findFragmentById(R.id.mapFragment)).getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        for (Geofence geofence : geofences){
            createMarker(Double.parseDouble(geofence.getLatitude()),Double.parseDouble(geofence.getLongitude()));
        }
    }

    public void createMarker(double latitude, double longitude){
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(latitude, longitude))
                .anchor(0.5f, 0.5f)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude,longitude),12));
    }

    private void calculateDistance(){
        for (int i=0 ; i<geofences.size() ; i++){
            for (int j=i+1 ; j<geofences.size() ;j++){
                addAllCombinationsInObservables(new LatLng(Double.parseDouble(geofences.get(i).getLatitude()),Double.parseDouble(geofences.get(i).getLongitude())),new LatLng(Double.parseDouble(geofences.get(j).getLatitude()),Double.parseDouble(geofences.get(j).getLongitude())));
            }
        }
        getSortestDistance();
    }

    private void addAllCombinationsInObservables(LatLng origin, LatLng dest){
        HashMap<String,String> map = new HashMap<>();
        map.put("origins",origin.latitude + "," + origin.longitude);
        map.put("destinations",dest.latitude + "," + dest.longitude);
        map.put("key",API_KEY);
        observableList.add(ApiClient.getClient().getObservableDistance(map).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()));
    }

    private void getSortestDistance(){
        Observable<List<DistanceMatrix>> d = Observable.zip(observableList, objects -> { return new ArrayList(Arrays.asList(objects)); });
        d.subscribe(new Observer<List<DistanceMatrix>>() {
            @Override
            public void onSubscribe(Disposable d) {
                Utils.showProgressDialog(progressDialog,getString(R.string.progress_dialg_msg));
            }

            @Override
            public void onNext(List<DistanceMatrix> distanceRespons) {
                for (DistanceMatrix d : distanceRespons) {
                    for (Row r : d.getRows()) {
                        for (Element e : r.getElements()) {
                            distances.add(Double.parseDouble(Utils.removeChar(e.getDistance().getText())));
                        }
                    }
                }
            }

            @Override
            public void onError(Throwable e) {
                Utils.dismissProgressDialog(progressDialog);
                Toast.makeText(MainActivity.this, R.string.failed_distance_calculate, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onComplete() {
                Utils.dismissProgressDialog(progressDialog);
                Collections.sort(distances);
                Utils.showAlertDialog(MainActivity.this,getString(R.string.alert_dialog_title),getString(R.string.Sortest_distance_between)+" "+geofences.size()+" "+getString(R.string.points_is)+" "+distances.get(0)+" "+getString(R.string.km) ,((dialog, status) -> {
                    if (status){
                        sendSortestDistance(String.valueOf(distances.get(0)));
                    }else {
                        dialog.dismiss();
                    }
                }));
            }
        });
    }

    private void sendSortestDistance(String sortestDistance){
        Utils.showProgressDialog(progressDialog,getString(R.string.please_wait));

        HashMap<String,String> map = new HashMap<>();
        map.put("distance",sortestDistance);
        map.put("userId",String.valueOf(new Random().nextInt(90)+10));
        ApiClient.getClient().sendSortestDistance(map).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Utils.dismissProgressDialog(progressDialog);
                try {
                    if (null!=response.body()){
                        String strResponse = response.body().string();
                        JSONObject jsonObject = new JSONObject(strResponse);
                        if (jsonObject.getInt(AppConstant.STATUS)==AppConstant._200){
                            BaseResponse<UserDistance> baseResponse =  new Gson().fromJson(new JsonParser().parse(strResponse),new TypeToken<BaseResponse<UserDistance>>(){}.getType());
                            Toast.makeText(MainActivity.this, baseResponse.getMsg(), Toast.LENGTH_LONG).show();
                        }else {
                            Toast.makeText(MainActivity.this, jsonObject.getString(AppConstant.MESSAGE), Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(MainActivity.this, R.string.something_wrong, Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Utils.dismissProgressDialog(progressDialog);
                Toast.makeText(MainActivity.this, R.string.try_again, Toast.LENGTH_SHORT).show();
            }
        });
    }

}
