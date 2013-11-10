package atl.phillython;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

public class Infoview extends Activity {
	private GoogleMap googleMap;
	private double lat;
	private double lng;
	private String name, desc;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_info);
		Intent i = getIntent();

		TextView locName = (TextView) findViewById(R.id.locName);
		TextView locDescription = (TextView) findViewById(R.id.locDescription);
		
		lat = i.getDoubleExtra("lat", 0);
		lng = i.getDoubleExtra("lng", 0);
		
		name = i.getStringExtra("name");
		desc = i.getStringExtra("desc");
		
		locName.setText(name);
		locDescription.setText(desc);

		try {
			// Loading map
			initializeMap();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

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
		}
		Marker a = googleMap.addMarker(createMarker.choose(lat, lng, name, desc));
		googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(lat, lng)));
	}

}
