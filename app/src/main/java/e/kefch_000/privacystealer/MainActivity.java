package e.kefch_000.privacystealer;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends Activity
        implements OnClickListener {

    private static final String TAG = "Debug";
    private LocationManager locationManager = null;
    private LocationListener locationListener = null;
    private Button btnGetLocation = null;
    private EditText editLocation = null;
    private ProgressBar pb = null;
    private Boolean flag = false;
    private Button awesomeButton;

    String[] oldlocationsarr;
    String oldlocations = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        awesomeButton = new Button(this);

        awesomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                awesomeButtonClicked();
            }
        });



    // Button button = (Button) findViewById(R.id.btnGetInfo);
    //   ButterKnife.bind(this);
  /*      button.setOnClickListener( new View.OnClickListener()

        {
            public void onClick (View v){
                    oldlocationsarr = DataSaver.getFavoriteList(MainActivity.this);
                Log.v("TAG", "Sent request to get data");
                    try {
                        StringBuilder result = new StringBuilder();
                        for (int i = 0; i < oldlocationsarr.length; i++) {
                            result.append(oldlocationsarr[i]);
                            result.append(", ");

                        }
                        Log.v("TAG", "built a string");
                        oldlocations = result.toString();
                        editLocation.setText(oldlocations);
                        Log.v("TAG", "set text");
                    } catch (Exception e) {
                    }

            }
        });*/




        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        setRequestedOrientation(ActivityInfo
                .SCREEN_ORIENTATION_PORTRAIT);
//could use butterknife, chose ths way because im an idiot
        pb = findViewById(R.id.progressBar1);
        pb.setVisibility(View.INVISIBLE);
        editLocation = findViewById(R.id.editTextLocation);

            editLocation.setText(oldlocations);

        btnGetLocation = findViewById(R.id.btnLocation);
        btnGetLocation.setOnClickListener(this);

        locationManager = (LocationManager)
                getSystemService(Context.LOCATION_SERVICE);

    }
    private void awesomeButtonClicked() {
        oldlocationsarr = DataSaver.getFavoriteList(MainActivity.this);
        Log.v("TAG", "Sent request to get data");
        try {
            StringBuilder result = new StringBuilder();
            for (int i = 0; i < oldlocationsarr.length; i++) {
                result.append(oldlocationsarr[i]);
                result.append(", ");

            }
            Log.v("TAG", "built a string");
            oldlocations = result.toString();
            editLocation.setText(oldlocations);
            Log.v("TAG", "set text");
        } catch (Exception e) {
        }

    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onClick(View v) {
        startService(new Intent(this, LocationService.class));
        flag = displayGpsStatus();
        if (flag) {

            Log.v(TAG, "onClick");

            editLocation.setText(getString(R.string.moveyourdevice_txt));

            pb.setVisibility(View.VISIBLE);
            locationListener = new MyLocationListener();

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
                return;
            }
            locationManager.requestLocationUpdates(LocationManager
                    .GPS_PROVIDER, 5000, 5, locationListener);
        } else {
            alertbox("Gps Status", "Your GPS is: OFF");
        }

    }

    private Boolean displayGpsStatus() {
        boolean gpsStatus = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        return gpsStatus;
    }
    protected void alertbox(String title, String mymessage) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Turn your location on")
                .setCancelable(false)
                .setTitle("Location Status")
                .setPositiveButton("Gps On",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent myIntent = new Intent(
                                        Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivity(myIntent);
                                dialog.cancel();
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }
    private class MyLocationListener implements LocationListener {
        @Override
        public void onLocationChanged(Location loc) {

            editLocation.setText("");
            pb.setVisibility(View.INVISIBLE);
            Toast.makeText(getBaseContext(), "Location changed : Lat: " +
                            loc.getLatitude() + " Lng: " + loc.getLongitude(),
                    Toast.LENGTH_SHORT).show();
            String longitude = "Longitude: " + loc.getLongitude();
            Log.v(TAG, longitude);
            String latitude = "Latitude: " + loc.getLatitude();
            Log.v(TAG, latitude);
            String cityName = null;
            Geocoder gcd = new Geocoder(getBaseContext(),
                    Locale.getDefault());
            List<Address> addresses;
            try {
                addresses = gcd.getFromLocation(loc.getLatitude(), loc
                        .getLongitude(), 1);
                if (addresses.size() > 0)
                    System.out.println(addresses.get(0).getLocality());
                cityName = addresses.get(0).getLocality();
                String lat = Double.toString(loc.getLatitude());
                String longi = Double.toString(loc.getLongitude());
                DataSaver.addFavoriteItem(MainActivity.this,lat);
                Log.v("TAG","Sent request to add item");
                DataSaver.addFavoriteItem(MainActivity.this, longi);
            } catch (IOException e) {
                e.printStackTrace();
            }

            String s = longitude + "\n" + latitude +
                    "\n\nMy Currrent City is: " + cityName;
            editLocation.setText(s);
        }

        @Override
        public void onProviderDisabled(String provider) {
            alertbox("Gps Status", "Your GPS is: OFF");
        }

        @Override
        public void onProviderEnabled(String provider) {
        }

        @Override
        public void onStatusChanged(String provider,
                                    int status, Bundle extras) {
        }

    }

 }
