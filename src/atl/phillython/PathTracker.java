package atl.phillython;

import java.util.ArrayList;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

public class PathTracker {
	static ArrayList<LatLng> points = new ArrayList<LatLng>();
	
	public static void fistPoint(LatLng firstPoint, GoogleMap gmap) {
		points.add(firstPoint);
	}
	
	public static void updatePoints(LatLng newPoint, GoogleMap gmap) {
		points.add(newPoint);
		PolylineOptions polylineOptions = new PolylineOptions();
		polylineOptions.addAll(points);
		Polyline route = gmap.addPolyline(polylineOptions);
	}
	
}
