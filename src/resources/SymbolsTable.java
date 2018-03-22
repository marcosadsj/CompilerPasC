package resources;
import java.util.HashMap;

import lexer.Description;
import lexer.Token;

public class SymbolsTable {

	private HashMap<Token,Description> symbolsTable;
	
	public SymbolsTable() {
		
		symbolsTable = new HashMap<>();
		
		symbolsTable.put(new Token(Tags.KW,"program"), new Description());
		symbolsTable.put(new Token(Tags.KW,"if"), new Description());
		symbolsTable.put(new Token(Tags.KW,"else"), new Description());
		symbolsTable.put(new Token(Tags.KW,"while"), new Description());
		symbolsTable.put(new Token(Tags.KW,"write"), new Description());
		symbolsTable.put(new Token(Tags.KW,"read"), new Description());
		symbolsTable.put(new Token(Tags.KW,"num"), new Description());
		symbolsTable.put(new Token(Tags.KW,"char"), new Description());
		symbolsTable.put(new Token(Tags.KW,"not"), new Description());
		symbolsTable.put(new Token(Tags.KW,"or"), new Description());
		symbolsTable.put(new Token(Tags.KW,"and"), new Description());

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
		return (Description) symbolsTable.get(token);
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