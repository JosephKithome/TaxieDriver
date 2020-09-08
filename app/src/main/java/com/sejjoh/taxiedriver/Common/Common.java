package com.sejjoh.taxiedriver.Common;

import android.location.Location;

import com.sejjoh.taxiedriver.Remote.FCMClient;
import com.sejjoh.taxiedriver.Remote.IFCMService;
import com.sejjoh.taxiedriver.Remote.IGoogleAPI;
import com.sejjoh.taxiedriver.Remote.RetrofitClient;
public class Common {

//    public  static  String currentToken ="";
public  static  final  String driver_tbl ="Drivers";
    public  static  final  String user_driver_tbl ="DriversInformation";
    public  static  final  String user_rider_tbl ="RidersInformation";
    public  static  final  String pickup_request_tbl ="PickupRequest";
    public  static  final  String tokens_tbl ="Tokens";

    public  static     Location mLastLocation = null;



    public  static  final String baseURL ="https://maps.googleapis.com";
    public  static  final String fcmURL ="https://fcm.googleapis.com";
    public  static IGoogleAPI getGoogleAPI()
    {
        return RetrofitClient.getClient(baseURL).create(IGoogleAPI.class);
    }
    public  static IFCMService getIFCMService()
    {
        return FCMClient.getClient(fcmURL).create(IFCMService.class);
    }
}

