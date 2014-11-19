package simpleNLP;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Writer {

	private String path;
	private String name;
	private BufferedWriter writer;
	
	public Writer(String path,String name) {
		// TODO Auto-generated constructor stub
		try {
			// create a temporary file
			File logFile = new File(path + "\\" + name + ".txt");

			// This will output the full path where the file will be written
			// to...
			System.out.println(logFile.getCanonicalPath());
//
//			writer = new BufferedWriter(new FileWriter(logFile,true));
			writer = new BufferedWriter(new OutputStreamWriter( new FileOutputStream(logFile), "UTF-8"));
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	public void WriteLine(String str){
		try {
			writer.write(str);
			writer.newLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("can't write");
		}
	}
	
	public void CloseWriter(){
		if(writer!=null){
			try {
				// Close the writer regardless of what happens...
				writer.close();
			} catch (Exception e) {
			}
		}
	}
}
