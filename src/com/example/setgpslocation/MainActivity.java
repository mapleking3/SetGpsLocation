package com.example.setgpslocation;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Timer;
import java.util.TimerTask;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.location.ILocationManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends Activity implements OnClickListener {
    
    private Timer timer;
    
    private TimerTask task;
    
    private ILocationManager mService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);
        
        init();
        
        mService = ILocationManager.Stub.asInterface(ServiceManager.getService(Context.LOCATION_SERVICE));
        
        findViewById(R.id.startBtn).setOnClickListener(this);
        findViewById(R.id.stopBtn).setOnClickListener(this);
    }
    
    private void init() {
        final LocationManager locationManager = (LocationManager)this
                .getSystemService(Context.LOCATION_SERVICE);
        
        
        final Location location = new Location(LocationManager.GPS_PROVIDER);
        
        location.setAccuracy(3.0f);
        location.setAltitude(38.92341241234);
        location.setBearing(0);
        location.setLatitude(31.7692030183);
        location.setLongitude(117.1874422489);
        location.setSpeed(1);
        location.setTime(System.currentTimeMillis());
        

        
//        locationManager.
            
        timer = new Timer();
        task = new TimerTask() {
            
            @SuppressLint("NewApi")
            @Override
            public void run() {
//                try {
//                    location.setTime(System.currentTimeMillis());
//        
//                    location.setElapsedRealtimeNanos(SystemClock.elapsedRealtimeNanos());
//                    
//                    Class clazz = LocationManager.class;
//                    try {
//                        Field field = clazz.getDeclaredField("mService");
//                        Method method = field.getClass().getMethod("reportLocation", Location.class, Boolean.class);
//                        method.invoke(field.get(locationManager), location, false);
//                    } catch (NoSuchFieldException e1) {
//                        // TODO Auto-generated catch block
//                        e1.printStackTrace();
//                    } catch (NoSuchMethodException e) {
//                        // TODO Auto-generated catch block
//                        e.printStackTrace();
//                    } catch (IllegalAccessException e) {
//                        // TODO Auto-generated catch block
//                        e.printStackTrace();
//                    } catch (IllegalArgumentException e) {
//                        // TODO Auto-generated catch block
//                        e.printStackTrace();
//                    } catch (InvocationTargetException e) {
//                        // TODO Auto-generated catch block
//                        e.printStackTrace();
//                    }
////                    location.makeComplete();
//                    Log.e("RETTON", "location:" + location);
////                    mService.reportLocation(location, false);
//                } catch (RemoteException e) {
//                    e.printStackTrace();
//                }
                location.setTime(System.currentTimeMillis());
                
                location.setElapsedRealtimeNanos(SystemClock.elapsedRealtimeNanos());
                
                Class clazz = LocationManager.class;
                try {
                    Field field = clazz.getDeclaredField("mService");
                    Method method = field.getClass().getMethod("reportLocation", Location.class, Boolean.class);
                    method.invoke(field.get(locationManager), location, false);
                } catch (NoSuchFieldException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                } catch (NoSuchMethodException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IllegalArgumentException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
//                location.makeComplete();
                Log.e("RETTON", "location:" + location);
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
