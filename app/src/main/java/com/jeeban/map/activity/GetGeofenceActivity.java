package com.jeeban.map.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.jeeban.map.R;
import com.jeeban.map.databinding.ActivityGetGeofenceBinding;
import com.jeeban.map.model.BaseResponse;
import com.jeeban.map.model.Geofence;
import com.jeeban.map.network.ApiClient;
import com.jeeban.map.util.AppConstant;
import com.jeeban.map.util.Utils;

import org.json.JSONObject;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetGeofenceActivity extends AppCompatActivity {

    private ActivityGetGeofenceBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_get_geofence);
        setUI();
    }

    private void setUI() {
        binding.btnGetGeofences.setOnClickListener((v) -> {
            getGeofences();
        });
    }

    private void getGeofences(){
        ProgressDialog progressDialog = new ProgressDialog(this);
        Utils.showProgressDialog(progressDialog,getString(R.string.loading_geofences));
        ApiClient.getClient().getGeofences().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Utils.dismissProgressDialog(progressDialog);
                try {
                    if (null!=response.body()){
                        String strResponse = response.body().string();
                        JSONObject jsonObject = new JSONObject(strResponse);
                        if (jsonObject.getInt(AppConstant.STATUS)==AppConstant._200){
                            BaseResponse<List<Geofence>> baseResponse =  new Gson().fromJson(new JsonParser().parse(strResponse),new TypeToken<BaseResponse<List<Geofence>>>(){}.getType());
                            startActivity(new Intent(GetGeofenceActivity.this,MainActivity.class).putExtra(AppConstant.GEOFENCE_LIST,baseResponse));
                        }else {
                            Toast.makeText(GetGeofenceActivity.this, jsonObject.getString(AppConstant.MESSAGE), Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(GetGeofenceActivity.this, R.string.something_wrong, Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Utils.dismissProgressDialog(progressDialog);
                Toast.makeText(GetGeofenceActivity.this, R.string.try_again, Toast.LENGTH_SHORT).show();
            }
        });


//        ApiClient.getClient().getMultipleLocations().enqueue(new Callback<BaseResponse<List<Location>>>() {
//            @Override
//            public void onResponse(Call<BaseResponse<List<Location>>> call, Response<BaseResponse<List<Location>>> response) {
//                System.out.println("response code=="+response.code());
//                System.out.println("response body=="+response.body());
//            }
//
//            @Override
//            public void onFailure(Call<BaseResponse<List<Location>>> call, Throwable t) {
//                System.out.println(" onFailure=="+t.getMessage());
//            }
//        });
    }
}
