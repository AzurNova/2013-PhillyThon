package atl.phillython;

import java.util.List;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class createMarker {
	public enum MarkerType {
		GENERAL, HISTORICAL, NATURE;
	}

	public static MarkerOptions choose(double latitude, double longitude,
			String name, String snipp) {
		BitmapDescriptor icon = BitmapDescriptorFactory
				.fromResource(R.drawable.historical_marker);
		return new MarkerOptions().position(new LatLng(latitude, longitude))
				.title(name).flat(true).icon(icon).snippet(snipp);
	}

	public static MarkerOptions choose(double latitude, double longitude,
			String name, String snipp, MarkerType type) {
		BitmapDescriptor icon;
		switch (type) {
		case GENERAL:
			// TODO different bitmaps
			icon = BitmapDescriptorFactory
					.fromResource(R.drawable.generic_marker);
			break;
		case HISTORICAL:
			icon = BitmapDescriptorFactory
					.fromResource(R.drawable.historical_marker);
			break;
		case NATURE:
			icon = BitmapDescriptorFactory
					.fromResource(R.drawable.nature_marker);
			break;
		default:
			icon = BitmapDescriptorFactory
					.fromResource(R.drawable.generic_marker);
		}

		return new MarkerOptions().position(new LatLng(latitude, longitude))
				.title(name).flat(true).icon(icon).snippet(snipp);
	}

	public static MarkerType figureOutMarkerType(List<String> tags) {
		if (tags.contains("nature")) {
			return MarkerType.NATURE;
		} else if (tags.contains("historical")) {
			return MarkerType.HISTORICAL;
		} else {
			return MarkerType.GENERAL;
		}
	}
}