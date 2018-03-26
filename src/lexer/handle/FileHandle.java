package lexer.handle;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class FileHandle {
	
	private final static int EOF = -1;
	
	private static RandomAccessFile fileAcess;
	
	private static int currentLine = 1;
	private static int currentColumn = 1;
	
	private static int lookAhead = 0;
	
	private FileHandle() {}
	
	public static RandomAccessFile getInstance(String path) {
		try {
			fileAcess = new RandomAccessFile(path,"r");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.exit(1);
		}catch (Exception e) {
			e.printStackTrace();
			System.exit(2);
		}
		return fileAcess;
	}
	
	public static void closeFile() {
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
	
	public static void fallbackCursor() {
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

	public static int getEof() {
		return EOF;
	}

	public static int getCurrentLine() {
		return currentLine;
	}

	public static int getCurrentColumn() {
		return currentColumn;
	}

	public static int getLookAhead() {
		return lookAhead;
	}

	public static void setIncrementLine() {
		currentLine++;
	}

	public static void setIncrementColumn() {
		currentColumn++;
	}
	
	public static void resetColumn() {
		currentColumn = 0;
	}

	public static void setLookAhead(int lookAhead) {
		FileHandle.lookAhead = lookAhead;
	}
}