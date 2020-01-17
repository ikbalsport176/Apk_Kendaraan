package com.juara.apk_kendaraan.service;

/**
 * Created by user on 1/10/2018.
 */

import com.juara.apk_kendaraan.addKendaraan.AddModel;
import com.juara.apk_kendaraan.merkkendaraan.MerkModel;
import com.juara.apk_kendaraan.modelKendaraan.KendaraanModel;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by anupamchugh on 09/01/17.
 */

public interface APIInterfacesRest {
    //get merk
    @GET("merk_kendaraan/all")
    Call<MerkModel> getmerk();

    //get kendaraan
    @GET("kendaraan/all")
    Call<KendaraanModel> getmobil();


    //addKendaraan
    @Multipart
    @POST("kendaraan/add")
    Call<AddModel> addkendaraan(

            @Part("merek_kendaraan") RequestBody spn_merk,
            @Part("cc") RequestBody txt_cc,
            @Part("tahun_kendaraan") RequestBody thn_pembuatan,
            @Part MultipartBody.Part img_btn1

            //@Part("foto_kendaraan") RequestBody txt_Directby,

            //button
            //@Part MultipartBody.Part btn_camera
    );




 /*   @GET("weather")
    Call<WeatherModel> getWeather(@Query("q") String q, @Query("appid") String appid);
*//*
 @FormUrlEncoded
 @POST("geolocation/add")
 Call<PostResult> postDataGeo(@Field("latitude") String lat, @Field("longitude") String lon, @Field("datetime") String datetime, @Field("photo") String photo);


    @Multipart
    @POST("geolocation/add")
    Call<PostResult> postDataGeoWithPhoto(

            @Part("latitude") RequestBody latitude,
            @Part("longitude") RequestBody lon,
            @Part("datetime") RequestBody datetime,
            @Part MultipartBody.Part img1

    );
*//*
    @GET("geolocation/all")
    Call<GeolocationModel> getGeolocation();

    @GET("weather")
    Call<WeatherModel> getWeatherBasedLocation(@Query("lat") Double lat, @Query("lon") Double lon, @Query("appid") String appid);
/*
    @GET("forecast")
    Call<ForcastWeatherModel> getForecastBasedLocation(@Query("lat") Double lat, @Query("lon") Double lon, @Query("appid") String appid);
*/

/*
    @GET("users")
    Call<com.juaracoding.absensi.model.reqres.User> getUserReqres(@Query("page") String page);

    @GET("posts")
    Call<List<Post>> getPost();

    @GET("comments")
    Call<List<Comment>> getComment(@Query("postId") String postId);*/



   /* @FormUrlEncoded
    @POST("api/user/login")
    Call<Authentication> getAuthentication(@Field("username") String username, @Field("password") String password);

    @GET("api/user_mobile/all")
    Call<Authentication> getUser(@Query("username_k") String user);

   @GET("api/komplain/ticket")
   Call<KomplainModel> getTicket(@Query("username") String username);

    @FormUrlEncoded
    @POST("api/komplain/add")
    Call<UpdateKomplain> addKomplain(@Field("username_komplain") String username, @Field("kode_edc") String kode_edc, @Field("masalah") String masalah, @Field("notes_komplain") String notes);

    @GET("api/edc_problem/all")
    Call<MasalahEdcModel> getEDCProblem();
*//*
    @GET("api/dataorder/all")
    Call<ModelOrder> getOrder(@Query("username") String user);



/*
            @Part MultipartBody.Part img1,
           @Part MultipartBody.Part img2,
           @Part MultipartBody.Part img3,
 *//*

   @Multipart
   @POST("api/dataorder/update")
   Call<Komplain> updateData(

           @Part("pod_date") RequestBody podate,
           @Part("status") RequestBody status,
           @Part("lat") RequestBody lat,
           @Part("lon") RequestBody lon,
           @Part("poddate") RequestBody poddate,
           @Part("recievedate") RequestBody recievedate,
           @Part("id") RequestBody id,
           @Part MultipartBody.Part img1


   );


   @Multipart
   @POST("api/komplain/update")
   Call<UpdateKomplain> sendImage(
           @Part("id") RequestBody id,
           @Part("username_penanganan") RequestBody username_komplain,
           @Part("hasil_penanganan") RequestBody masalah,
           @Part("tanggal_penanganan") RequestBody kode_edc,
           @Part("notes_penanganan") RequestBody notes_komplain,
           @Part("status") RequestBody status,
           @Part MultipartBody.Part img1


   );*/

}
