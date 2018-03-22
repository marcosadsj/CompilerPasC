package lexer;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class FileHelper {
	
	private final int EOF = -1;
	
	private RandomAccessFile fileAcess;
	
	private int currentLine = 1;
	private int currentColumn = 1;
	
	private int lookAhead = 0;
	
	
	public FileHelper(String path) {
		try {
			fileAcess = new RandomAccessFile(path,"r");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.exit(1);
		}catch (Exception e) {
			e.printStackTrace();
			System.exit(2);
		}
	}
	
	public void closeFile() {
		if(fileAcess != null) {
			try {
				fileAcess.close();
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(3);
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(4);
			}
		}
	}
	
	public void fallbackCursor() {
		try {
			if(lookAhead != EOF) {
				fileAcess.seek(fileAcess.getFilePointer() - 1);
				currentColumn--;
			}
		}catch(Exception e) {
			e.printStackTrace();
			System.exit(5);
		}
	}
}
