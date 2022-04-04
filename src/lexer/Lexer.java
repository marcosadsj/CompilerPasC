package lexer;

import java.io.IOException;
import java.io.RandomAccessFile;

import lexer.handle.FileHandle;
import lexer.messages.ErrorMessage;
import lexer.model.Description;
import lexer.model.Token;
import lexer.states.Constants;
import lexer.states.LiteralAndIdentifyers;
import lexer.states.Operators;
import lexer.states.Symbols;
import resources.SymbolsTable;
import resources.Tag;

public class Lexer {

	private StringBuilder lexeme;
	
	private static int state;
	
	private static int unknownChar = 0;
	
	private static char currentChar;
	
	private RandomAccessFile fileAcess;
	
	SymbolsTable symbolsTable;
	
	private Token token;
	
	public Lexer(String path, SymbolsTable symbolsTable) {
		fileAcess = FileHandle.getInstance(path);
		this.symbolsTable = symbolsTable;
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
				}else
				{
					currentChar = '\u0000';
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
			}else if(currentChar == ' ') {
				FileHandle.setIncrementColumn();

			}else {
				FileHandle.setIncrementColumn();
				incrementUnknownChar();
			}
						
			token = Operators.analyse(lexeme);
			
			if(token != null) {
				//System.out.println("Token: " + token.toString() + "\t Linha: " + FileHandle.getCurrentLine() + "\t Coluna: " + FileHandle.getCurrentColumn());
                
                if(symbolsTable.getToken(token.getLexeme()) == null) {
                	symbolsTable.setSymbol(token, new Description());
    				return token;
                }else {
                	return symbolsTable.getToken(token.getLexeme());
                }
			}
			
			token = Symbols.analyse(lexeme);
			
			if(token != null) {
				//System.out.println("Token: " + token.toString() + "\t Linha: " + FileHandle.getCurrentLine() + "\t Coluna: " + FileHandle.getCurrentColumn());
                
                if(symbolsTable.getToken(token.getLexeme()) == null) {
                	symbolsTable.setSymbol(token, new Description());
    				return token;
                }else {
                	return symbolsTable.getToken(token.getLexeme());
                }
			}
			
			token = LiteralAndIdentifyers.analyse(lexeme);
			
			if(token != null) {
				//System.out.println("Token: " + token.toString() + "\t Linha: " + FileHandle.getCurrentLine() + "\t Coluna: " + FileHandle.getCurrentColumn());
                
                if(symbolsTable.getToken(token.getLexeme()) == null) {
                	symbolsTable.setSymbol(token, new Description());
    				return token;
                }else {
                	return symbolsTable.getToken(token.getLexeme());
                }
			}
			
			token = Constants.analyse(lexeme);
			
			if(token != null) {
				//System.out.println("Token: " + token.toString() + "\t Linha: " + FileHandle.getCurrentLine() + "\t Coluna: " + FileHandle.getCurrentColumn());
                
                if(symbolsTable.getToken(token.getLexeme()) == null) {
                	symbolsTable.setSymbol(token, new Description());
    				return token;
                }else {
                	return symbolsTable.getToken(token.getLexeme());
                }
			}
						
			if(token == null && Lexer.getState() == 0 && unknownChar == 5) {
				ErrorMessage.invalidCaractere(currentChar);
			}
			
			if(FileHandle.getLookAhead() == FileHandle.getEof())
				return new Token(Tag.EOF,"EOF",FileHandle.getCurrentLine(), FileHandle.getCurrentColumn());
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