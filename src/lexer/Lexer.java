package lexer;

import java.io.IOException;
import java.io.RandomAccessFile;

import lexer.handle.FileHandle;
import lexer.model.Token;
import lexer.states.Constants;
import lexer.states.LiteralAndIdentifyers;
import lexer.states.Operators;
import lexer.states.Symbols;
import messages.ErrorMessage;
import resources.Tags;

public class Lexer {

	private StringBuilder lexeme;
	
	private static int state;
	
	private static int unknownChar = 0;
	
	private static char currentChar;
	
	private RandomAccessFile fileAcess;
	
	private Token token;
	
	public Lexer(String path) {
		fileAcess = FileHandle.getInstance(path);
	}
	
	public Token getNextToken() {
		
		lexeme = new StringBuilder();
		state = 0;
		currentChar = '\u0000';

		while(true) {
			
			try {
				FileHandle.setLookAhead(fileAcess.read());
				
				if(FileHandle.getLookAhead() != FileHandle.getEof()) {
					currentChar = (char) FileHandle.getLookAhead();
					unknownChar = 0;			
				}
			}catch(IOException e){
				System.out.println("Erro leitura do arquivo");
				System.exit(3);
			}
						
			if(currentChar == '\t') {
				FileHandle.setIncrementColumn();
				FileHandle.setIncrementColumn();
				FileHandle.setIncrementColumn();
			}else if(currentChar == '\n') {
				FileHandle.setIncrementLine();
				FileHandle.resetColumn();
			}else if(currentChar == '\b'){
				
			}else if(currentChar == ' ') {
				
			}else {
				FileHandle.setIncrementColumn();
				incrementUnknownChar();
			}
						
			token = Operators.analyse(lexeme);
			
			if(token != null) {
				return token;
			}
			
			token = Symbols.analyse(lexeme);
			
			if(token != null) {
				return token;
			}
			
			token = LiteralAndIdentifyers.analyse(lexeme);
			
			if(token != null) {
				return token;
			}
			
			token = Constants.analyse(lexeme);
			
			if(token == null && Lexer.getState() == 0 && unknownChar == 5) {
				ErrorMessage.invalidCaractere(currentChar);
			}
						
			if(FileHandle.getLookAhead() == FileHandle.getEof())
				return new Token(Tags.EOF,"EOF",FileHandle.getCurrentLine(), FileHandle.getCurrentColumn());
		}
	}
	
	public static int getState() {
		return state;
	}
	
	public static void setState(int state) {
		Lexer.state = state;
	}
	
	public static char getCurrentChar() {
		return currentChar;
	}
	
	public static void incrementUnknownChar() {
		unknownChar++;
	}
}