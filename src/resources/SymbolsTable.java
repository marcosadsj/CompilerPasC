package resources;
import java.util.HashMap;

import lexer.model.Description;
import lexer.model.Token;

public class SymbolsTable {

	private HashMap<Token,Description> symbolsTable;
	
	public SymbolsTable() {
		
		symbolsTable = new HashMap<>();
		
		symbolsTable.put(new Token(Tag.KW_PROGRAM,"program"), new Description());
		symbolsTable.put(new Token(Tag.KW_IF,"if"), new Description());
		symbolsTable.put(new Token(Tag.KW_ELSE,"else"), new Description());
		symbolsTable.put(new Token(Tag.KW_WHILE,"while"), new Description());
		symbolsTable.put(new Token(Tag.KW_WRITE,"write"), new Description());
		symbolsTable.put(new Token(Tag.KW_READ,"read"), new Description());
		symbolsTable.put(new Token(Tag.KW_NUM,"num"), new Description());
		symbolsTable.put(new Token(Tag.KW_CHAR,"char"), new Description());
		symbolsTable.put(new Token(Tag.KW_NOT,"not"), new Description());
		symbolsTable.put(new Token(Tag.KW_OR,"or"), new Description());
		symbolsTable.put(new Token(Tag.KW_AND,"and"), new Description());

	}
	
	public void setSymbol(Token token, Description description) {
		symbolsTable.put(token, description);
	}
	
	public Token getToken(String lexema) {
		for(Token token: symbolsTable.keySet()) {
			if(token.getLexeme().equalsIgnoreCase(lexema)) {
				return token;
			}
		}
		return null;
	}
	
	public Description getDescription(Token token){
		return symbolsTable.get(token);
	}
	
	@Override
	public String toString() {
		
		int i = 0;
		
		String output = "";
		
		for(Token token : symbolsTable.keySet()) {
			output += "position " + i + ": \t" + token.toString() + "\n";
			i++;
		}
		return output;
	}
}