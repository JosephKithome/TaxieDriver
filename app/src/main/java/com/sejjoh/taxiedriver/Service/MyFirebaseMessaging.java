package com.sejjoh.taxiedriver.Service;

import android.content.Intent;

import androidx.annotation.NonNull;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;
import com.sejjoh.taxiedriver.CustomerCall;

public class MyFirebaseMessaging extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        LatLng customer_location = new Gson().fromJson(remoteMessage.getNotification().getBody(),LatLng.class);
        Intent customerCall = new Intent(getBaseContext(), CustomerCall.class);
        customerCall.putExtra("lat",customer_location.latitude);
        customerCall.putExtra("lng",customer_location.longitude);
        startActivity(customerCall);
    }
}
