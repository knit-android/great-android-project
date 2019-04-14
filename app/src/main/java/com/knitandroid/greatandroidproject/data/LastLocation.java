package com.knitandroid.greatandroidproject.data;

public class LastLocation {
    private
   float latitude, longitude, accuracy;
    int userId;
    public LastLocation(float lat, float lon, float accur, int userId)
    {
        this.accuracy=accur;
        this.longitude=lon;
        this.userId=userId;
        this.latitude=lat;
    }
    public float getAccuracy(){ return accuracy; }

    public float getLongitude(){ return longitude; }

    public float getLatitude(){ return latitude; }

    public int getUserId(){ return userId; }


}
