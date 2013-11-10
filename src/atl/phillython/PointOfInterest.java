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

	private String name;
	private String description;
	private LatLng position;
	private List<String> tags;

	public PointOfInterest() {
		this("ERR:NAME_MISSING", "ERR:DESCRIPTION_MISSING", new LatLng(0, 0),
				new ArrayList<String>());
	}

	public PointOfInterest(String name, String desc, LatLng pos,
			List<String> tags) {
		super();
		this.setName(name);
		this.setDescription(desc);
		this.setPosition(pos);
		this.setTags(tags);
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
		System.out.println("pulling from data");
		PointOfInterest poi = new PointOfInterest();
		if (!file.hasNext()) {
			if (DEBUG)
				System.out
						.println("POI building: No lines to start building point, returning null.");
			return null;
		}
		try {
			poi.setName(file.nextLine());
			System.out.println("read name");
			poi.setDescription(file.nextLine());
			System.out.println("read description");
			double lat = Double.parseDouble(file.nextLine());
			System.out.println(lat);
			double lng = Double.parseDouble(file.nextLine());
			System.out.println(lng);
			poi.setPosition(new LatLng(lat, lng));
			while (true) {
				String line = file.nextLine();
				if (line.equals(SEPARATOR)) {
					if (DEBUG)
						System.out.println("POI building: Reached separator, breaking from tag sweep.");
					break;
				}
				poi.getTags().add(line);
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
			outfile.println(this.getName());
			outfile.println(this.getDescription());
			outfile.println(this.getPosition().latitude);
			outfile.println(this.getPosition().longitude);
			for(int i = 0; i < this.getTags().size(); i++){
				outfile.println(this.getTags().get(i));
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LatLng getPosition() {
		return position;
	}

	public void setPosition(LatLng position) {
		this.position = position;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}
	
	public String toString() {
		return name;
	}
}
