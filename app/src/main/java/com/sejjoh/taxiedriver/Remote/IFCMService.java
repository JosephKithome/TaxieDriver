package com.sejjoh.taxiedriver.Remote;

import com.sejjoh.taxiedriver.Model.FCMResponse;
import com.sejjoh.taxiedriver.Model.Sender;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface IFCMService {
    @Headers({
            "Content-Type:application/json",
            "Authorization:key<SERVER KEY>"
    })
    @POST("fcm/send")
    Call<FCMResponse>sendMessage (@Body Sender body);
}
