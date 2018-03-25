package e.kefch_000.privacystealer;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startService(new Intent(this, MyLocationService.class));

    }

 /*   Location gpslocation = null;
    private static final int GPS_TIME_INTERVAL = 100;
    private static final int GPS_DISTANCE = 1;

    private void obtainLocation() {
        if (locMan == null) {
            locMan = (LocationManager) getSystemService(LOCATION_SERVICE);
        }
        if (locMan.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            gpslocation = locMan.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (isLocationListener) {
                locMan.requestLocationUpdates(LocationManager.GPS_PROVIDER, GPS_TIME_INTERVAL, GPS_DISTANCE, GPSListener);
            }
        }
    }

    private static final int HANDLER_DELAY = 1 * 60 * 5;
    Handler handler = new Handler();
    handler.postDelayed(new Runnable() {
        public void run() {
            myLocation = obtainLocation();
            handler.postDelayed(this, HANDLER_DELAY);
        }
    }, START_HA)

 public class LocationService extends Service
 {
     public static final String BROADCAST_ACTION = "Hello World";
     private static final int TWO_MINUTES = 1000 * 60 * 2;
     public LocationManager locationManager;
     public MyLocationListener listener;
     public Location previousBestLocation = null;

     Intent intent;
     int counter = 0;

     @Override
     public void onCreate()
     {
         super.onCreate();
         intent = new Intent(BROADCAST_ACTION);
         locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
         listener = new MyLocationListener();
         if(ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION)== PackageManager.PERMISSION_GRANTED) {
             //        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},1);
             //        Location locationObj= locationManager.getLastKnownLocation(provider);
             //         lastLocation=locationObj.toString();
             //        Toast.makeText(this, lastLocation, Toast.LENGTH_LONG).show();
             //        return lastLocation;}
             locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 4000, 0, listener);
             locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 4000, 0, listener);
         }
         else{
             ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},1);
             locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 4000, 0, listener);
             locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 4000, 0, listener);
         }
     }

     @Nullable
     @Override
     public IBinder onBind(Intent intent) {
         return null;
     }

 }
String lastLocation;
     public void onStart(Intent intent, int startId)
     {

     }

     @Override
     public IBinder onBind(Intent intent)
     {
         return null;
     }

     protected boolean isBetterLocation(Location location, Location currentBestLocation) {
         if (currentBestLocation == null) {
             // A new location is always better than no location
             return true;
         }

         // Check whether the new location fix is newer or older
         long timeDelta = location.getTime() - currentBestLocation.getTime();
         boolean isSignificantlyNewer = timeDelta > TWO_MINUTES;
         boolean isSignificantlyOlder = timeDelta < -TWO_MINUTES;
         boolean isNewer = timeDelta > 0;

         // If it's been more than two minutes since the current location, use the new location
         // because the user has likely moved
         if (isSignificantlyNewer) {
             return true;
             // If the new location is more than two minutes older, it must be worse
         } else if (isSignificantlyOlder) {
             return false;
         }

         // Check whether the new location fix is more or less accurate
         int accuracyDelta = (int) (location.getAccuracy() - currentBestLocation.getAccuracy());
         boolean isLessAccurate = accuracyDelta > 0;
         boolean isMoreAccurate = accuracyDelta < 0;
         boolean isSignificantlyLessAccurate = accuracyDelta > 200;

         // Check if the old and new location are from the same provider
         boolean isFromSameProvider = isSameProvider(location.getProvider(),
                 currentBestLocation.getProvider());

         // Determine location quality using a combination of timeliness and accuracy
         if (isMoreAccurate) {
             return true;
         } else if (isNewer && !isLessAccurate) {
             return true;
         } else if (isNewer && !isSignificantlyLessAccurate && isFromSameProvider) {
             return true;
         }
         return false;
     }



     /** Checks whether two providers are the same
     private boolean isSameProvider(String provider1, String provider2) {
         if (provider1 == null) {
             return provider2 == null;
         }
         return provider1.equals(provider2);
     }



     @Override
     public void onDestroy() {
         // handler.removeCallbacks(sendUpdatesToUI);
         super.onDestroy();
         Log.v("STOP_SERVICE", "DONE");
         locationManager.removeUpdates(listener);
     }

     public Thread performOnBackgroundThread(final Runnable runnable) {
         final Thread t = new Thread() {
             @Override
             public void run() {
                 try {
                     runnable.run();
                 } finally {

                 }
             }
         };
         t.start();
         return t;
     }




     public class MyLocationListener implements LocationListener
     {

         public void onLocationChanged(final Location loc)
         {
             Log.i("*****************", "Location changed");
             if(isBetterLocation(loc, previousBestLocation)) {
                 loc.getLatitude();
                 loc.getLongitude();
                 intent.putExtra("Latitude", loc.getLatitude());
                 intent.putExtra("Longitude", loc.getLongitude());
                 intent.putExtra("Provider", loc.getProvider());
                 sendBroadcast(intent);

             }
         }

         public void onProviderDisabled(String provider)
         {
             Toast.makeText( getApplicationContext(), "Gps Disabled", Toast.LENGTH_SHORT ).show();
         }


         public void onProviderEnabled(String provider)
         {
             Toast.makeText( getApplicationContext(), "Gps Enabled", Toast.LENGTH_SHORT).show();
         }


         public void onStatusChanged(String provider, int status, Bundle extras)
         {

         }

     }*//*
 public class MyLocationService extends Service {
     private static final String TAG = "MyLocationService";
     private LocationManager mLocationManager = null;
     private static final int LOCATION_INTERVAL = 1000;
     private static final float LOCATION_DISTANCE = 10f;

     private class LocationListener implements android.location.LocationListener {
         Location mLastLocation;

         public LocationListener(String provider) {
             Log.e(TAG, "LocationListener " + provider);
             mLastLocation = new Location(provider);
         }

         @Override
         public void onLocationChanged(Location location) {
             Log.e(TAG, "onLocationChanged: " + location);
             mLastLocation.set(location);
         }

         @Override
         public void onProviderDisabled(String provider) {
             Log.e(TAG, "onProviderDisabled: " + provider);
         }

         @Override
         public void onProviderEnabled(String provider) {
             Log.e(TAG, "onProviderEnabled: " + provider);
         }

         @Override
         public void onStatusChanged(String provider, int status, Bundle extras) {
             Log.e(TAG, "onStatusChanged: " + provider);
         }
     }

    /*
    LocationListener[] mLocationListeners = new LocationListener[]{
            new LocationListener(LocationManager.GPS_PROVIDER),
            new LocationListener(LocationManager.NETWORK_PROVIDER)
    };


     LocationListener[] mLocationListeners = new LocationListener[]{
             new LocationListener(LocationManager.PASSIVE_PROVIDER)
     };

     @Override
     public IBinder onBind(Intent arg0) {
         return null;
     }

     @Override
     public int onStartCommand(Intent intent, int flags, int startId) {
         Log.e(TAG, "onStartCommand");
         super.onStartCommand(intent, flags, startId);
         return START_STICKY;
     }

     @Override
     public void onCreate() {

         Log.e(TAG, "onCreate");

         initializeLocationManager();

         try {
             mLocationManager.requestLocationUpdates(
                     LocationManager.PASSIVE_PROVIDER,
                     LOCATION_INTERVAL,
                     LOCATION_DISTANCE,
                     mLocationListeners[0]
             );
         } catch (java.lang.SecurityException ex) {
             Log.i(TAG, "fail to request location update, ignore", ex);
         } catch (IllegalArgumentException ex) {
             Log.d(TAG, "network provider does not exist, " + ex.getMessage());
         }

        /*try {
            mLocationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    LOCATION_INTERVAL,
                    LOCATION_DISTANCE,
                    mLocationListeners[1]
            );
        } catch (java.lang.SecurityException ex) {
            Log.i(TAG, "fail to request location update, ignore", ex);
        } catch (IllegalArgumentException ex) {
            Log.d(TAG, "gps provider does not exist " + ex.getMessage());
        }
     }

     @Override
     public void onDestroy() {
         Log.e(TAG, "onDestroy");
         super.onDestroy();
         if (mLocationManager != null) {
             for (int i = 0; i < mLocationListeners.length; i++) {
                 try {
                     if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                         return;
                     }
                     mLocationManager.removeUpdates(mLocationListeners[i]);
                 } catch (Exception ex) {
                     Log.i(TAG, "fail to remove location listener, ignore", ex);
                 }
             }
         }
     }

     private void initializeLocationManager() {
         Log.e(TAG, "initializeLocationManager - LOCATION_INTERVAL: "+ LOCATION_INTERVAL + " LOCATION_DISTANCE: " + LOCATION_DISTANCE);
         if (mLocationManager == null) {
             mLocationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
         }
     }*/

 }
