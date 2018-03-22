package resources;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Starter {
	
	public static void main(String args[]) throws IOException{
		
		File file = null;
		BufferedReader bufferedReader = null;
		FileReader fileReader = null;
		
		file = new File("/home/marcosadsj/Eclipse-Workspace/CompilerPasC/src/file");
		
		try {
			fileReader = new FileReader(file);
			bufferedReader = new BufferedReader(fileReader);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	
		
		while(bufferedReader.ready()) {
			try {
				String text = bufferedReader.readLine();
				System.out.println(text);
			} catch (IOException e) {
								e.printStackTrace();
			}
		}
		
	}
}
