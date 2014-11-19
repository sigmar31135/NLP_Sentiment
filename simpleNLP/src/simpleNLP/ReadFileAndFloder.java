package simpleNLP;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public abstract class ReadFileAndFloder{
	
	String path;

	public ReadFileAndFloder(String path) {
		// TODO Auto-generated constructor stub
		this.path = path;
	}
	
	public void StartRead(){
		File folder = new File(path);
		ReadFloder(folder);
	}

	private void ReadFloder(File folder) {
		File[] listOfFiles = folder.listFiles();
		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()
					&& listOfFiles[i].getName().endsWith(".txt")) {
				ReadFile(listOfFiles[i]);

			} else if (listOfFiles[i].isDirectory()) {
				ReadFloder(listOfFiles[i]);
			}
		}
	}

	private void ReadFile(File inputFile) {

		StringBuilder contents = new StringBuilder();
		BufferedReader reader = null;
		
		String filename = inputFile.getName();
		ReadFile(filename);
		
		
		try {
			FileInputStream fis = new FileInputStream(inputFile);
			reader = new BufferedReader(new InputStreamReader(fis, "UTF-8"));
			String hohi = null;

			// repeat until all lines is read
			while ((hohi = reader.readLine()) != null) {
				// GetNLP(hohi);
				contents.append(hohi);
				ReadLine(hohi);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (reader != null) {
					reader.close();
					GetString(contents.toString());
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
	
	public abstract void GetString(String input);
	public abstract void ReadLine(String input);
	public abstract void ReadFile(String input);
}
