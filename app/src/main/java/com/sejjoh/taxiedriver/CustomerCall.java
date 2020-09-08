package com.sejjoh.taxiedriver;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ValueAnimator;
import android.graphics.Color;
import android.location.Location;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.JointType;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.SquareCap;
import com.sejjoh.taxiedriver.Common.Common;
import com.sejjoh.taxiedriver.Remote.IFCMService;
import com.sejjoh.taxiedriver.Remote.IGoogleAPI;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomerCall extends AppCompatActivity {
    TextView txtTime,txtDistance,txtAddress;
    MediaPlayer mediaPlayer;
    IGoogleAPI iGoogleAPI;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        iGoogleAPI = Common.getGoogleAPI();
        setContentView(R.layout.activity_customer_call);
        txtTime =(TextView)findViewById(R.id.txt_time);
        txtDistance =(TextView)findViewById(R.id.txt_distance);
        txtAddress =(TextView)findViewById(R.id.txt_Address);
        mediaPlayer =MediaPlayer.create(this,R.raw.ringtone);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();

        if (getIntent() != null)
        {
            double lat =getIntent().getDoubleExtra("lat",-1.0);
            double lng = getIntent().getDoubleExtra("lng", -1.0);
            getDirection(lat, lng);

        }

    }

    private void getDirection(double lat,double lng) {
        String requestApi;
        try {
            requestApi ="https://maps.googleapis.com/maps/api/directions/json?"+
                    "mode=driving&"+
                    "transit_routing_preference = less_driving&"+
                    "origin="+ Common.mLastLocation.getLatitude()+ ","+ Common.mLastLocation.getLongitude()+"&"+
                    "destination="+ lat+","+lng + "&"+
                    "key="+getResources().getString(R.string.google_direction_api);

            Log.d("DEVSOFTDEV",requestApi); //print url for debug
            iGoogleAPI.getPath(requestApi)
                    .enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response.body());
                                JSONArray routes =jsonObject.getJSONArray("routes");

                                //after getting routes now get elements of route
                                JSONObject object =routes.getJSONObject(0);

                                //After get first element now we need to get array with name legs
                                JSONArray legs =object.getJSONArray("legs");

                                //And get first element of legs
                                JSONObject legsObject =legs.getJSONObject(0);

                                //now get distance
                                JSONObject distance =legsObject.getJSONObject("distance");
                                txtDistance.setText(distance.getString("text"));

                                //get time
                                JSONObject time = legsObject.getJSONObject("duration");
                                txtTime.setText(time.getString("text"));

                                //get address
                                String address = legsObject.getString("end_address");
                                txtAddress.setText(address);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            Toast.makeText(CustomerCall.this,""+t.getMessage(),Toast.LENGTH_SHORT).show();

                        }
                    });



        }catch (Exception e){
            e.printStackTrace();
        }


    }

    @Override
    protected void onStop() {
        mediaPlayer.release();
        super.onStop();
    }

    @Override
    protected void onPause() {
        mediaPlayer.release();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mediaPlayer.start();
    }
}