package lexer.states;

import lexer.Lexer;
import lexer.handle.FileHandle;
import lexer.model.Token;
import messages.ErrorMessage;
import resources.Tags;

public class Constants {

	public static Token analyse(StringBuilder lexeme) {
		switch(Lexer.getState()) {
			
			case 0:
				if(Character.isDigit(Lexer.getCurrentChar())) {
					lexeme.append(Lexer.getCurrentChar());
					Lexer.setState(25);
				}
				//TODO PESQUISAR COMO COLOCAR ASPAS SIMPLES DENTRO DE ASPAS SIMPLES
			break;
			
			case 25:
				if(Lexer.getCurrentChar() == '.') {
					lexeme.append(Lexer.getCurrentChar());
					Lexer.setState(27);
				}else{
					Lexer.setState(0);
					FileHandle.fallbackCursor();
					return new Token(Tags.NUM_CONST, lexeme.toString(),
							FileHandle.getCurrentLine(), FileHandle.getCurrentColumn());
				}
			
			break;
			
			case 27:
				if(Character.isDigit(Lexer.getCurrentChar())) {
					lexeme.append(Lexer.getCurrentChar());
					Lexer.setState(28);
				}else  {
					ErrorMessage.incompleteToken();
					return null;
				}
			break;
				
			case 28:
				if(Character.isDigit(Lexer.getCurrentChar())) {
					lexeme.append(Lexer.getCurrentChar());
				}else{
					Lexer.setState(0);
					FileHandle.fallbackCursor();
					return new Token(Tags.NUM_CONST, lexeme.toString(), 
							FileHandle.getCurrentLine(), FileHandle.getCurrentColumn());
				}
			break;
		}
		return null;
	}
}
