package com.example.s20141210jinwoojung.capston.service;
//

import com.example.s20141210jinwoojung.capston.model.ImageUrl;
import com.example.s20141210jinwoojung.capston.model.Join;
import com.example.s20141210jinwoojung.capston.model.Login;
import com.example.s20141210jinwoojung.capston.model.Test;
import com.example.s20141210jinwoojung.capston.model.Token;
import com.example.s20141210jinwoojung.capston.model.User;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface GetTokenService {
    @GET("v1/users")
    Call<List<User>> getUser();
    @POST("api/user/login")
    Call<Token> login(@Body Login login);
    //@POST("v1/auth/login")
    @GET("v1/auth/tokenTest")
    Call<Test> getSecret(@Header("Authorization") String authToken);
    @Multipart
    @POST("Api/image")
    Call<ResponseBody> uploadFile(
            @Part("description") RequestBody description,
            @Part MultipartBody.Part file,
            @Header("Authorization") String authToken
    );

    @Multipart
    @POST("Api/image/new")
    Call<ImageUrl> uploadFile2(
            @Part("description") RequestBody description,
            @Part MultipartBody.Part file,
            @Header("Authorization") String authToken
    );



    @Multipart
    @POST("Api/image")
    Call<User> editUser (@Header("Authorization") String authorization, @Part("file\"; filename=\"pp.png\" ") RequestBody file , @Part("FirstName") RequestBody fname);

    @POST("api/user/signUp")
    @Headers({"Content-Type: application/json"})
    Call<Token> join(@Body Join join);
}
