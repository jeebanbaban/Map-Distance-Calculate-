package com.jeeban.map.network;

import com.jeeban.map.model.DistanceMatrix;
import com.jeeban.map.util.AllUrls;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

public interface ApiInterface {

    @GET(AllUrls.GET_LOCATIONS_OR_POST_DIST_URL)
    Call<ResponseBody> getGeofences();

    @GET(AllUrls.GET_DISTANCE_URL)
    Observable<DistanceMatrix> getObservableDistance(@QueryMap Map<String,String> map);

    @POST(AllUrls.GET_LOCATIONS_OR_POST_DIST_URL)
    @FormUrlEncoded
    Call<ResponseBody> sendSortestDistance(@FieldMap HashMap<String,String> map);

}
