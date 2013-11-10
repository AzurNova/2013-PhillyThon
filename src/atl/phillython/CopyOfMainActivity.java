package atl.phillython;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
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

public class CopyOfMainActivity extends FragmentActivity implements
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
			initilizeMap();
			initializePoints();
			initializeClickListener();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Loads points up from the data file.
	 */
	private void initializePoints() {
		pointCollector = new POI_Collector(DATAFILENAME);
		pointMarkers = new ArrayList<Marker>();
	}

	private void getMarkersFromPoints(Location location) {
		List<PointOfInterest> pois = pointCollector.getNearbyPoints(new LatLng(
				location.getLatitude(), location.getLongitude()), MAX_DISTANCE);
		for (int i = 0; i < pois.size(); i++) {
			MarkerOptions marker = new createMarker().choose(pois.get(i)
					.getPosition().latitude,
					pois.get(i).getPosition().longitude, pois.get(i).getName(),
					pois.get(i).getDescription());
			pointMarkers.add(googleMap.addMarker(marker));
		}

	}

	private void clearMarkers() {
		for (int i = 0; i < pointMarkers.size(); i++) {
			pointMarkers.get(i).remove();
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
			MarkerOptions marker = new createMarker().choose(latitude,
					longitude, name, snippet);

			// adding marker
			Marker test = googleMap.addMarker(marker);
		}
	}

	/**
	 * Location Stuff
	 */
	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		double lat = (double) (location.getLatitude());
		double lng = (double) (location.getLongitude());
		latitudeField.setText(String.valueOf(lat));
		longitudeField.setText(String.valueOf(lng));
		
		clearMarkers();
		getMarkersFromPoints(location);
		
		// if (polyline != null) {
		// polyline.remove();
		// }
		// rectOptions.add(new LatLng(lat, lng));
		// rectOptions.color(Color.BLUE);
		// polyline = googleMap.addPolyline(rectOptions);

		// PathTracker.updatePoints(new LatLng(lat,lng), googleMap);
		// Location myLocation = googleMap.getMyLocation();
		// if(myLocation == null) {
		// Toast.makeText(getApplicationContext(), "My location not available",
		// Toast.LENGTH_LONG).show();
		// } else {
		// PolylineOptions polylineOptions = new PolylineOptions();
		// polylineOptions.add(new LatLng(myLocation.getLatitude(),
		// myLocation.getLongitude()));
		// googleMap.addPolyline(polylineOptions);
		// }
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
		clearMarkers();
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
		initilizeMap();
		locationManager.requestLocationUpdates(provider, 400, 1, this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}