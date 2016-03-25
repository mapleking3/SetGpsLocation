package com.example.setgpslocation;

import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.model.LatLng;

public class MainActivity extends Activity implements OnClickListener {
    
    private Timer timer;
    
    private TimerTask task;

    private EditText latEt;

    private EditText lngEt;

    private MapView mMapView;

    private BaiduMap mBaiduMap;

    private LatLng lastLatLng = new LatLng(31.8365450000, 117.1513650000);

    private static final LatLng GEO_START = new LatLng(31.84541,117.196711);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);

        init();

        mMapView = (MapView) findViewById(R.id.mapview);

        mBaiduMap = mMapView.getMap();

        MapStatusUpdate u1 = MapStatusUpdateFactory.newLatLng(GEO_START);

        mBaiduMap.setMapStatus(u1);

        mBaiduMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                latEt.setText(String.format(Locale.CHINA, "%f", latLng.latitude));
                lngEt.setText(String.format(Locale.CHINA, "%f", latLng.longitude));
            }

            @Override
            public boolean onMapPoiClick(MapPoi mapPoi) {
                return false;
            }
        });
        
        findViewById(R.id.startBtn).setOnClickListener(this);
        findViewById(R.id.stopBtn).setOnClickListener(this);

        latEt = (EditText) findViewById(R.id.latEt);
        lngEt = (EditText) findViewById(R.id.lngEt);
    }
    
    private void init() {
        final LocationManager locationManager = (LocationManager)this
                .getSystemService(Context.LOCATION_SERVICE);
        
        locationManager.addTestProvider(LocationManager.GPS_PROVIDER, false,
                false, false, false, true, true, true, 0, /* magic */5);
        locationManager.setTestProviderEnabled(LocationManager.GPS_PROVIDER,
                true);
        
        final Location location = new Location(LocationManager.GPS_PROVIDER);
        
        location.setAccuracy(3.0f);
        location.setAltitude(38.92341241234);
        location.setBearing(0);
        location.setLatitude(31.7692030183);
        location.setLongitude(117.1874422489);
        location.setSpeed(1);
        location.setTime(System.currentTimeMillis());

        timer = new Timer();
        task = new TimerTask() {
            
            @SuppressLint("NewApi")
            @Override
            public void run() {

                try {
                    location.setLatitude(Double.parseDouble(latEt.getText().toString()));
                    location.setLongitude(Double.parseDouble(lngEt.getText().toString()));
                    location.setTime(System.currentTimeMillis());

                    if (android.os.Build.VERSION.SDK_INT >= 17) {
                        location.setElapsedRealtimeNanos(SystemClock.elapsedRealtimeNanos());
                    }

                    locationManager.setTestProviderLocation(LocationManager.GPS_PROVIDER, location);
                    Log.e("RETTON", "location:" + location);
                } catch (Exception e) {
                    Log.e("RETTON", "run: ", e);
                }
            }
        };
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.startBtn:
            timer.schedule(task, 1000, 500);
            break;
        case R.id.stopBtn:
            timer.cancel();
            break;
        }
    }
}
