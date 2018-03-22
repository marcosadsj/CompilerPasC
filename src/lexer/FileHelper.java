package lexer;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class FileHelper {
	
	private RandomAccessFile acessFile;
	
	public FileHelper(String path) {
		try {
			acessFile = new RandomAccessFile(path,"r");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.exit(1);
		}catch (Exception e) {
			e.printStackTrace();
			System.exit(2);
		}
	}
	
	public void closeFile() {
		if(acessFile != null) {
			try {
				acessFile.close();
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(3);
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(4);
			}
		}
	}
}
