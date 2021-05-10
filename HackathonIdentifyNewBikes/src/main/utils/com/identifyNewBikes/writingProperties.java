package com.identifyNewBikes;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Properties;

public class writingProperties {

	// Method writing the properties file used to store the output
	public static void writingfile(List<String> models, String key) {

		Properties prop1 = new Properties();
		OutputStream writeFile = null;

		try {
			writeFile = new FileOutputStream("src\\test\\resources\\popularCars.properties");

			for (int i = 0; i < models.size(); i++) {
				int j = i + 1;
				String keyString = Integer.toString(j);
				prop1.setProperty(keyString, (String) models.get(i));
			}
			prop1.store(writeFile, null);

		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		} finally {
			if (writeFile != null) {
				try {
					writeFile.close();
				} catch (IOException e) {

					e.printStackTrace();
				}
			}
		}
	}

}
