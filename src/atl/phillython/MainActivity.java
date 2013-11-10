package atl.phillython;

<<<<<<< HEAD
<<<<<<< HEAD
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
=======
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

>>>>>>> PointsTest
=======
>>>>>>> parent of 944eab5... points test working
import android.content.Context;
import android.content.Intent;
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

<<<<<<< HEAD
<<<<<<< HEAD
=======
>>>>>>> parent of 944eab5... points test working
public class MainActivity extends FragmentActivity implements LocationListener{
    private GoogleMap googleMap;
    private TextView latitudeField;
    private TextView longitudeField;
    private LocationManager locationManager;
    private String provider;
    private PolylineOptions rectOptions;
    private Polyline polyline;
    
<<<<<<< HEAD
=======
    
>>>>>>> parent of 944eab5... points test working
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
<<<<<<< HEAD
        //notification
        createPersistentNotification();
        
=======
>>>>>>> parent of 944eab5... points test working
        latitudeField = (TextView)findViewById(R.id.TextView02);
        longitudeField = (TextView)findViewById(R.id.TextView04);
        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, false);
        Location location = locationManager.getLastKnownLocation(provider);
        
 //       PathTracker.firstPoint(new LatLng(location.getLatitude(), location.getLongitude()), googleMap);
        
        if (location != null) {
        	System.out.println("Provider " + provider + " has been selected.");
        	onLocationChanged(location);
        } else {
        	latitudeField.setText("Location not available");
        	longitudeField.setText("Location not available");
        }
        
        try {
            // Loading map
            initilizeMap();
            initializeClickListener();
        } catch (Exception e) {
            e.printStackTrace();
        }
 
    }
 
    /**
     * function to load map. If map is not created it will create it for you
     * */
    private void initilizeMap() {
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
            
            // latitude and longitude
            double latitude = 39.920955;
            double longitude = -75.183258;
            String name = "Hi there";
            String snippet = "Very descriptive, this is";
            // create marker
            MarkerOptions marker = new createMarker().choose(latitude, longitude, name, snippet);
             
            // adding marker
            Marker test = googleMap.addMarker(marker);
<<<<<<< HEAD
            
            //notif.createPersistentNotification();
=======
>>>>>>> parent of 944eab5... points test working
        }
    }
    
    /**
     * Location Stuff
     */
<<<<<<< HEAD
=======
public class MainActivity extends FragmentActivity implements
		LocationListener {
	private GoogleMap googleMap;
	private TextView latitudeField;
	private TextView longitudeField;
	private LocationManager locationManager;
	private String provider;
	private PolylineOptions rectOptions;
	private Polyline polyline;
	private POI_Collector pointCollector;
	private List<Marker> pointMarkers;

	private static final String DATAFILENAME = "locationdb.txt";
	private static final double MAX_DISTANCE = 1000.0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		
		
		latitudeField = (TextView) findViewById(R.id.TextView02);
		longitudeField = (TextView) findViewById(R.id.TextView04);
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		Criteria criteria = new Criteria();
		provider = locationManager.getBestProvider(criteria, false);
		Location location = locationManager.getLastKnownLocation(provider);

		// PathTracker.firstPoint(new LatLng(location.getLatitude(),
		// location.getLongitude()), googleMap);

		if (location != null) {
			System.out.println("Provider " + provider + " has been selected.");
			onLocationChanged(location);
		} else {
			latitudeField.setText("Location not available");
			longitudeField.setText("Location not available");
		}

		try {
			// Loading map
			initializePoints();
			initializeMap();
			initializeClickListener();

		} catch (Exception e) {
			e.printStackTrace();
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
			
			getMarkersFromPoints(locationManager.getLastKnownLocation(provider));
		
		}
	}

	/**
	 * Location Stuff
	 */
>>>>>>> PointsTest
=======
>>>>>>> parent of 944eab5... points test working
	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		double lat = (double) (location.getLatitude());
		double lng = (double) (location.getLongitude());		
		latitudeField.setText(String.valueOf(lat));
		longitudeField.setText(String.valueOf(lng));
		
<<<<<<< HEAD
<<<<<<< HEAD
		createNotification("You Moved!", "This is a test btw");
=======
>>>>>>> parent of 944eab5... points test working
//		if (polyline != null) {
//			polyline.remove();
//		}
//		rectOptions.add(new LatLng(lat, lng));
//		rectOptions.color(Color.BLUE);
//		polyline = googleMap.addPolyline(rectOptions);
<<<<<<< HEAD
		
		
=======
		//clearMarkers();
		//sgetMarkersFromPoints(location);
>>>>>>> PointsTest
=======
>>>>>>> parent of 944eab5... points test working
		
		
		
//        PathTracker.updatePoints(new LatLng(lat,lng), googleMap);
//	    Location myLocation = googleMap.getMyLocation();
//	    if(myLocation == null) {
//	    	Toast.makeText(getApplicationContext(), "My location not available", Toast.LENGTH_LONG).show();
//	    } else {
//			PolylineOptions polylineOptions = new PolylineOptions();
//			polylineOptions.add(new LatLng(myLocation.getLatitude(), myLocation.getLongitude()));
//			googleMap.addPolyline(polylineOptions);
//		}
		Log.i("locationchange","changed location");
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
		locationManager.removeUpdates(this);
	}
 
    /**
     * 
     * Long click to make markers - test
     *
     */
    public class onMapLongClickListener implements OnMapLongClickListener{
		int markers = 0;
		@Override
		public void onMapLongClick(LatLng arg0) {
			 // create marker
			markers ++;
            MarkerOptions marker = new MarkerOptions().position(arg0).title("marker no " + markers);
            // adding marker
            googleMap.addMarker(marker);
		}
    }
    
    private void initializeClickListener(){
    	googleMap.setOnMapLongClickListener(new onMapLongClickListener());
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        initilizeMap();
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
		
		NotificationCompat.Builder notif = new NotificationCompat.Builder(this)
					.setContentTitle(title)
					.setContentText(description)
					.setSmallIcon(R.drawable.loc_notif);
		TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
		stackBuilder.addParentStack(MainActivity.class);
		stackBuilder.addNextIntent(intent);
		
		NotificationManager nM = (NotificationManager) getSystemService(NOTIFICATION_SERVICE); 
		nM.notify(1233, notif.build());
	}
}