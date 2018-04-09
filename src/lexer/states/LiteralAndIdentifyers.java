package lexer.states;

import lexer.Lexer;
import lexer.handle.FileHandle;
import lexer.model.Token;
import messages.ErrorMessage;
import resources.Tags;

public class LiteralAndIdentifyers {

	public static Token analyse(StringBuilder lexeme) {
		
		switch(Lexer.getState()) {
		
			case 0:
				if(Character.isLetter(Lexer.getCurrentChar())) {
					lexeme.append(Lexer.getCurrentChar());
					Lexer.setState(23);
				}else if(Lexer.getCurrentChar() == '"') {
					lexeme.append(Lexer.getCurrentChar());
					Lexer.setState(33);
				}else if(Lexer.getCurrentChar() == '\'') {
					
				}
				else if(Lexer.getCurrentChar() == '/') {
					Lexer.setState(16);
				}
				else {
					Lexer.incrementUnknownChar();
				}
				break;
			
			case 16:
				if(Lexer.getCurrentChar() == '/') {
					Lexer.setState(18);
				}else if(Lexer.getCurrentChar() == '*'){
					Lexer.setState(20);
				}else {
					Lexer.setState(17);
					return Operators.analyse(lexeme);
				}
				break;
			
			case 18:
				if(Lexer.getCurrentChar() == '\n') {
					Lexer.setState(0);
				}
				break;
			
			case 20:
				if(Lexer.getCurrentChar() == '*') {
					Lexer.setState(21);
				}else if(FileHandle.getLookAhead()== FileHandle.getEof()) {
					Lexer.setState(0);
					ErrorMessage.unclosedComment();
					return null;
				}
				break;
			
			case 21:
				if(Lexer.getCurrentChar() == '/') {
					Lexer.setState(0);
				}else {
					Lexer.setState(20);
				}
				break;
			case 23:
				if(Character.isLetterOrDigit(Lexer.getCurrentChar())) {
					lexeme.append(Lexer.getCurrentChar());
				}else{
					Lexer.setState(0);
					FileHandle.fallbackCursor();
					
					return new Token(Tags.ID, lexeme.toString(),
							FileHandle.getCurrentLine(), FileHandle.getCurrentColumn());
				}
				break;
			
			case 33:
				if(Lexer.getCurrentChar() == '"') {
					
					if(lexeme.length() == 1) {
						Lexer.setState(0);
						ErrorMessage.emptyString();
						lexeme.deleteCharAt(0);
						return null;
					}
					
					lexeme.append(Lexer.getCurrentChar());
					Lexer.setState(0);
					return new Token(Tags.LIT, lexeme.toString(), 
							FileHandle.getCurrentLine(), FileHandle.getCurrentColumn());
				}else if(FileHandle.getLookAhead() == FileHandle.getEof()) {
					Lexer.setState(0);
					ErrorMessage.unclosedString();
					return null;
				}else if(Lexer.getCurrentChar() == '\n'){
					Lexer.setState(0);
					ErrorMessage.moreThanOneLineString();
					return null;
				}else {
					lexeme.append(Lexer.getCurrentChar());
				}
				break;
		}
		return null;
	}
}
