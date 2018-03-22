package lexer;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class FileHelper {
	
	private int fallbackPosition;
	private int currentPosition;
	
	private BufferedReader bufferedReader;
	
	private static StringBuilder stringBuilder;
	
	public FileHelper(String path) {
		
		stringBuilder = new StringBuilder();
		
		try {
			bufferedReader = new BufferedReader(new FileReader(path));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		try {
			while(bufferedReader.ready()) {
				stringBuilder.append(bufferedReader.readLine());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
		
	public static StringBuilder getContent() {
		return stringBuilder;
	}
}
