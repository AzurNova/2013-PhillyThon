package atl.phillython;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

public class POI_Collector {
        private ArrayList<PointOfInterest> points = new ArrayList<PointOfInterest>();
        private Scanner in;

        public POI_Collector(InputStream inputStream) {
                try {
                        in = new Scanner(inputStream);
                        System.out.println("Success scanner");
                } catch (Exception e) {
                        System.out.println("Failed");
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
                        System.out.println("collected pt");
                        points.add(pt);
                }
        }

        public List<PointOfInterest> getNearbyPoints(LatLng mPos, double maxDistance) {
                System.out.println("checking nearby points");
                List<PointOfInterest> nearPoints = new ArrayList<PointOfInterest>();
                for (int i = 0; i < getPoints().size(); i++) {
                        float[] res = new float[1];
                        Location.distanceBetween(mPos.latitude, mPos.longitude,
                                        getPoints().get(i).getPosition().latitude,
                                        getPoints().get(i).getPosition().longitude, res);
                        System.out.println(res[0]);
                        if(res[0] < maxDistance){
                                System.out.println("close point!");
                                nearPoints.add(getPoints().get(i));
                        }
                }
                return nearPoints;
        }

        public List<PointOfInterest> getPoints() {
                return points;
        }

        public void setPoints(ArrayList<PointOfInterest> points) {
                this.points = points;
        }

        public Scanner getIn() {
                return in;
        }

        public void setIn(Scanner in) {
                this.in = in;
        }
        
        public String toString() {
                StringBuilder str = new StringBuilder();
                for (int i = 0; i < this.points.size(); i++) {
                        str.append(this.points.get(i).toString());
                        str.append(" ");
                }
                return str.toString();
        }
}