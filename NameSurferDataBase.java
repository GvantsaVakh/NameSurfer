import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

/*
 * File: NameSurferDataBase.java
 * -----------------------------
 * This class keeps track of the complete database of names.
 * The constructor reads in the database from a file, and
 * the only public method makes it possible to look up a
 * name and get back the corresponding NameSurferEntry.
 * Names are matched independent of case, so that "Eric"
 * and "ERIC" are the same names.
 */

public class NameSurferDataBase implements NameSurferConstants {

	HashMap<String, String> map = new HashMap<String, String>();

	/* Constructor: NameSurferDataBase(filename) */
	/**
	 * Creates a new NameSurferDataBase and initializes it using the data in the
	 * specified file. The constructor throws an error exception if the
	 * requested file does not exist or if an error occurs as the file is being
	 * read.
	 */
	public NameSurferDataBase(String filename) {

		// reads text in the file and adds each line in the HashMap
		// key is the name
		// value is the whole line
		try {
			BufferedReader rd = new BufferedReader(new FileReader(filename));
			String line = rd.readLine();
			while (line != null) {
				map.put(getName(line), line);
				line = rd.readLine();
			}
			rd.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String getName(String line) {
		String name = "";
		// name is the substring before the first space
		for (int i = 0; i < line.length(); i++) {
			if (line.charAt(i) == ' ') {
				name = line.substring(0, i);
				break;
			}
		}
		return name;
	}

	/* Method: findEntry(name) */
	/**
	 * Returns the NameSurferEntry associated with this name, if one exists. If
	 * the name does not appear in the database, this method returns null.
	 */
	public NameSurferEntry findEntry(String name) {
		if (map.keySet().contains(changeName(name))) {
			return new NameSurferEntry(map.get(changeName(name)));
		}
		return null;
	}

	// changes name so that names match independent of case
	private Object changeName(String name) {
		String changedName = "";
		for (int i = 0; i < name.length(); i++) {
			if (i == 0) {
				changedName += Character.toUpperCase(name.charAt(i));
			} else {
				changedName += Character.toLowerCase(name.charAt(i));
			}
		}
		return changedName;
	}
}
