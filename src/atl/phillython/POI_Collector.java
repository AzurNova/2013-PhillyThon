package atl.phillython;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

public class POI_Collector {
	private List<PointOfInterest> points;
	private Scanner in;

	public POI_Collector(String filename) {
		File f;
		try {
			f = new File(filename);
			setIn(new Scanner(f));
		} catch (Exception e) {
			e.printStackTrace();
		}

		collect();
	}

	public void collect() {
		while (true) {
			PointOfInterest pt = PointOfInterest.pullFromData(getIn());
			if (pt == null) {
				break;
			}
			getPoints().add(pt);
		}
	}

	public List<PointOfInterest> getNearbyPoints(LatLng mPos, double maxDistance) {
		List<PointOfInterest> nearPoints = new ArrayList<PointOfInterest>();
		for (int i = 0; i < getPoints().size(); i++) {
			float[] res = new float[1];
			Location.distanceBetween(mPos.latitude, mPos.longitude,
					getPoints().get(i).getPosition().latitude,
					getPoints().get(i).getPosition().longitude, res);
			if(res[0] < maxDistance){
				nearPoints.add(getPoints().get(i));
			}
		}
		return nearPoints;
	}

	public List<PointOfInterest> getPoints() {
		return points;
	}

	public void setPoints(List<PointOfInterest> points) {
		this.points = points;
	}

	public Scanner getIn() {
		return in;
	}

	public void setIn(Scanner in) {
		this.in = in;
	}

}
