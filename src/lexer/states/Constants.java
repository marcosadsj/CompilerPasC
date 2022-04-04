package lexer.states;

import lexer.Lexer;
import lexer.handle.FileHandle;
import lexer.messages.ErrorMessage;
import lexer.model.Token;
import resources.Tag;

public class Constants {

	public static Token analyse(StringBuilder lexeme) {
		switch(Lexer.getState()) {
			
			case 0:
				if(Character.isDigit(Lexer.getCurrentChar())) {
					lexeme.append(Lexer.getCurrentChar());
					Lexer.setState(25);
				}else if(Lexer.getCurrentChar() == '\''){
					lexeme.append(Lexer.getCurrentChar());
					Lexer.setState(26);
				}else {
					Lexer.incrementUnknownChar();
				}
			break;
			
			case 25:
				if(Lexer.getCurrentChar() == '.') {
					lexeme.append(Lexer.getCurrentChar());
					Lexer.setState(27);

				}else if(Character.isDigit(Lexer.getCurrentChar())) {
					lexeme.append(Lexer.getCurrentChar());
				}else{
					Lexer.setState(0);
					FileHandle.fallbackCursor();
					return new Token(Tag.NUM_CONST, lexeme.toString(),
							FileHandle.getCurrentLine(), FileHandle.getCurrentColumn());
				}
			
				break;
			
			case 26:
				if(Character.isLetterOrDigit(Lexer.getCurrentChar())) {
					lexeme.append(Lexer.getCurrentChar());
					Lexer.setState(29);
				}else {
					Lexer.setState(0);
					FileHandle.fallbackCursor();
					ErrorMessage.incompleteToken();
					return null;
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
					return new Token(Tag.NUM_CONST, lexeme.toString(), 
							FileHandle.getCurrentLine(), FileHandle.getCurrentColumn());
				}
			break;
			
			case 29:
				
				if(Lexer.getCurrentChar()== '\'') {
					Lexer.setState(0);
					lexeme.append(Lexer.getCurrentChar());
					return new Token(Tag.CHAR_CONST,lexeme.toString(),
							FileHandle.getCurrentLine(), FileHandle.getCurrentColumn());
					}else {
						Lexer.setState(0);
						FileHandle.fallbackCursor();
						ErrorMessage.incompleteToken();
						return null;
				}
			}
		return null;
	}
}
