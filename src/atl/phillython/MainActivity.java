package atl.phillython;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

public class MainActivity extends FragmentActivity implements
                LocationListener {
        private GoogleMap googleMap;
        private TextView latitudeField;
        private TextView longitudeField;
        private LocationManager locationManager;
        private String provider;
        private PolylineOptions polyOp;
        private Polyline polyline;
        private POI_Collector pointCollector;
        private List<Marker> pointMarkers;
        private Location currentLocation, lastLocation;
        private int num = 1;

        private static final String DATAFILENAME = "locationdb.txt";
        private static final double MAX_DISTANCE = 1000.0;
        private int i = 0;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_main);

                createPersistentNotification();
                
                latitudeField = (TextView) findViewById(R.id.TextView02);
                longitudeField = (TextView) findViewById(R.id.TextView04);
                locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                Criteria criteria = new Criteria();
                provider = locationManager.getBestProvider(criteria, false);
                Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                // PathTracker.firstPoint(new LatLng(location.getLatitude(),
                // location.getLongitude()), googleMap);
				try {
					// Loading map
					initializePoints();
					initializeMap();
					initializeClickListener();
			
				} catch (Exception e) {
					e.printStackTrace();
				}
                
                if (location != null) {
                        System.out.println("Provider " + provider + " has been selected.");
                        onLocationChanged(location);
                } else {
                        latitudeField.setText("Location not available");
                        longitudeField.setText("Location not available");
                }



        }

        /**
         * Loads points up from the data file.
         */
        private void initializePoints() {
                pointCollector = new POI_Collector(getResources().openRawResource(R.raw.locationdb));
                System.out.println(pointCollector.getPoints());
                pointMarkers = new ArrayList<Marker>();
        }

        private void getMarkersFromPoints(Location location) {
                List<PointOfInterest> pois = pointCollector.getNearbyPoints(new LatLng(
                                location.getLatitude(), location.getLongitude()), MAX_DISTANCE);
                System.out.println(pois);
                for (int i = 0; i < pois.size(); i++) {
                        System.out.println(pois.get(i).getPosition().latitude);
                        MarkerOptions marker = createMarker.choose(pois.get(i).getPosition().latitude,
                                        pois.get(i).getPosition().longitude, pois.get(i).getName(),
                                        pois.get(i).getDescription());
                        System.out.println(pois.get(i));
                        
                        Marker a = googleMap.addMarker(marker);
                }
        }

        private void clearMarkers() {
                //for (int i = 0; i < pointMarkers.size(); i++) {
                        //pointMarkers.get(i).remove();
                //}
        }

        /**
         * function to load map. If map is not created it will create it for you
         * */
        private void initializeMap() {
                if (googleMap == null) {
                        googleMap = ((MapFragment) getFragmentManager().findFragmentById(
                                        R.id.map)).getMap();

                        // check if map is created successfully or not
                        if (googleMap == null) {
                                Toast.makeText(getApplicationContext(),
                                                "Sorry! unable to create maps", Toast.LENGTH_SHORT)
                                                .show();
                        }

                        googleMap.setMyLocationEnabled(true);
                        
                        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        getMarkersFromPoints(location);
                        
                        currentLocation = new Location(location);
                        System.out.println("initialization currentLocation" + currentLocation);
                        
		                polyOp = new PolylineOptions()
		                  .add(new LatLng(location.getLatitude(),location.getLongitude()))
		                  .width(5)
		                  .color(Color.BLUE);
                        polyline = googleMap.addPolyline(polyOp);
                 
                }
        }

        /**
         * Location Stuff
         */
        public static LatLng locationToLatLng(Location loc) { 
            if (loc != null) 
                return new LatLng(loc.getLatitude(), loc.getLongitude());
            return null;
        }
        @Override
        public void onLocationChanged(Location location) {
                // TODO Auto-generated method stub
        		System.out.println("location Changed called");
        		System.out.println("Hello");
                System.out.println("lastLocation" + lastLocation);
                System.out.println("currentLocation" + currentLocation);
        		lastLocation = new Location(currentLocation);
        		currentLocation = location;
        		System.out.println("lastLocationafter" + lastLocation);
                System.out.println("currentLocationafter" + currentLocation);
                double lat = (double) (currentLocation.getLatitude());
                double lng = (double) (currentLocation.getLongitude());
                num++;
                latitudeField.setText(String.valueOf(num));
 //               latitudeField.setText(String.valueOf(lat));
                longitudeField.setText(String.valueOf(lng));
                System.out.println("setText fields");
                
                initializePoints();
                List<PointOfInterest> pois = pointCollector.getNearbyPoints(new LatLng(
                                currentLocation.getLatitude(), currentLocation.getLongitude()), 100);
                for (int i = 0; i < pois.size(); i++) {
                        createNotification(pois.get(i).getName(), pois.get(i).getDescription());
                }
                          
                
                LatLng lastLatLng = locationToLatLng(lastLocation);
                LatLng thisLatLng = locationToLatLng(currentLocation);
                System.out.println(lastLatLng.latitude);
                System.out.println(
                thisLatLng.latitude);
                googleMap.addPolyline(new PolylineOptions().add(lastLatLng).add(thisLatLng).width(8).color(Color.BLUE));
                
                Log.i("locationchange", "changed location");
        }

        @Override
        public void onProviderDisabled(String arg0) {
                // TODO Auto-generated method stub
                Toast.makeText(this, "Disabled provider " + provider,
                                Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onProviderEnabled(String arg0) {
                // TODO Auto-generated method stub
                Toast.makeText(this, "Enabled new provider " + provider,
                                Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
                // TODO Auto-generated method stub

        }

        @Override
        protected void onPause() {
                // TODO Auto-generated method stub
                super.onPause();
                //clearMarkers();
                locationManager.removeUpdates(this);
        }

        /**
         * 
         * Long click to make markers - test
         * 
         */
        public class onMapLongClickListener implements OnMapLongClickListener {
                int markers = 0;

                @Override
                public void onMapLongClick(LatLng arg0) {
                        // create marker
                        markers++;
                        MarkerOptions marker = new MarkerOptions().position(arg0).title(
                                        "marker no " + markers);
                        // adding marker
                        googleMap.addMarker(marker);
                }
        }

        private void initializeClickListener() {
                googleMap.setOnMapLongClickListener(new onMapLongClickListener());
        }

        @Override
        protected void onResume() {
                super.onResume();
                initializeMap();
                locationManager.requestLocationUpdates(provider, 400, 1, this);
        }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
                // Inflate the menu; this adds items to the action bar if it is present.
                getMenuInflater().inflate(R.menu.main, menu);
                return true;
        }
        
        //-----------------------------------------------------------------------
        //--------------------------Notifications--------------------------------
        @SuppressLint("NewApi")
        @SuppressWarnings("deprecation")
        public void createPersistentNotification() {
                Intent intent = new Intent(this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                PendingIntent contentIntent = PendingIntent.getActivity(this, 0, intent, 0);
                
                NotificationCompat.Builder notif = new NotificationCompat.Builder(this)
                                        .setContentTitle("EducaTour is Running.")
                                        .setContentText("You will recieve notifications live.")
                                        .setSmallIcon(R.drawable.notification)
                                        .setOngoing(true)
                                        .setContentIntent(contentIntent);
                TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
                stackBuilder.addParentStack(MainActivity.class);
                stackBuilder.addNextIntent(intent);
                
                NotificationManager nM = (NotificationManager) getSystemService(NOTIFICATION_SERVICE); 
                nM.notify(1234, notif.build());
        }
        
        @SuppressWarnings("deprecation")
        @SuppressLint("NewApi")
        public void createNotification(String title, String description) {
                Intent intent = new Intent(this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                PendingIntent contentIntent = PendingIntent.getActivity(this, 0, intent, 0);
                
                Notification notif = new NotificationCompat.Builder(this)
                                        .setContentTitle(title)
                                        .setContentText(description)
                                        .setContentIntent(contentIntent)
                                        .setSmallIcon(R.drawable.loc_notif).build();
                TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
                stackBuilder.addParentStack(MainActivity.class);
                stackBuilder.addNextIntent(intent);
                
                notif.flags |= Notification.FLAG_AUTO_CANCEL;
                NotificationManager nM = (NotificationManager) getSystemService(NOTIFICATION_SERVICE); 
                nM.notify(3221 + i++, notif);
        }
}