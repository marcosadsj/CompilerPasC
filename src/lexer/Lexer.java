package lexer;

import lexer.model.Token;
import resources.Tags;

public class Lexer {

	private StringBuilder lexema;
	
	private int estado = 0;
	
	private char currentChar = '\u0000';
	
	public Token getNextToken() {
		
		//TODO aqui ficará as condições do switch
		
		return new Token(Tags.ID, "");
	}
}
