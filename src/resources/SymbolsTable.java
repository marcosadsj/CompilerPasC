package resources;
import java.util.HashMap;

import lexer.model.Description;
import lexer.model.Token;

public class SymbolsTable {

	private HashMap<Token,Description> symbolsTable;
	
	public SymbolsTable() {
		
		symbolsTable = new HashMap<>();
		
		for(String keyWord : KeyWords.getAllKeyWords()) {
			symbolsTable.put(new Token(Tags.KW,keyWord), new Description());
		}
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