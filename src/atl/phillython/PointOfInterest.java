package atl.phillython;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.google.android.gms.maps.model.LatLng;

public class PointOfInterest {

	private static boolean DEBUG = true;

	public static String SEPARATOR = "###";

	public String name;
	public String description;
	public LatLng position;
	public List<String> tags;

	public PointOfInterest() {
		this("ERR:NAME_MISSING", "ERR:DESCRIPTION_MISSING", new LatLng(0, 0),
				new ArrayList<String>());
	}

	public PointOfInterest(String name, String desc, LatLng pos,
			List<String> tags) {
		super();
		this.name = name;
		this.description = desc;
		this.position = pos;
		this.tags = tags;
	}
	
	public static void writePointToData(PointOfInterest poi, String filename){
		FileWriter wr;
		PrintWriter pwr;
		boolean append = true;
		try{
			wr = new FileWriter(filename, append);
			pwr = new PrintWriter(wr);
			poi.writeToData(pwr);
		}catch(Exception e){
			if(DEBUG) System.out.println("POI writing: exception in creating file writer");
			e.printStackTrace();
		}
	}
	
	public static PointOfInterest pullFromData(Scanner file) {
		PointOfInterest poi = new PointOfInterest();
		if (!file.hasNext()) {
			if (DEBUG)
				System.out
						.println("POI building: No lines to start building point, returning null.");
			return null;
		}
		try {
			poi.name = file.nextLine();
			poi.description = file.nextLine();
			double lat = Double.parseDouble(file.nextLine());
			double lng = Double.parseDouble(file.nextLine());
			poi.position = new LatLng(lat, lng);
			while (true) {
				String line = file.nextLine();
				if (line == SEPARATOR) {
					if (DEBUG)
						System.out
								.println("POI building: Reached separator, breaking from tag sweep.");
					break;
				}
				poi.tags.add(line);
			}
		} catch (Exception e) {
			if (DEBUG) {
				System.out
						.println("POI building: Ran into issue building from file. Probably missing lines or separator.");
				e.printStackTrace();
			}
		}
		return poi;
	}

	/**
	 * assumes outfile is set up
	 * 
	 * @param outfile
	 */
	public void writeToData(PrintWriter outfile) {

		try {
			outfile.println(this.name);
			outfile.println(this.description);
			outfile.println(this.position.latitude);
			outfile.println(this.position.longitude);
			for(int i = 0; i < this.tags.size(); i++){
				outfile.println(this.tags.get(i));
			}
			outfile.println(SEPARATOR);
			
		} catch (Exception e) {
			if (DEBUG) {
				System.out
						.println("POI writing: Some issue occurred in writing to file.");
				e.printStackTrace();
			}
		}
	}
}
