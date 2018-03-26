package lexer.states;

import lexer.Lexer;
import lexer.handle.FileHandle;
import lexer.model.Token;
import messages.ErrorMessage;
import resources.Tags;

public class LiteralAndIdentifyers {

	public static Token analyse(StringBuilder lexeme) {
		
		switch(Lexer.getCurrentChar()) {
		
			case 0:
				if(Character.isLetter(Lexer.getCurrentChar())) {
					lexeme.append(Lexer.getCurrentChar());
					Lexer.setState(23);
				}else if(Lexer.getCurrentChar() == '"') {
					lexeme.append(Lexer.getCurrentChar());
					Lexer.setState(33);
				}//TODO PESQUISAR COMO COLOCAR ASPAS SIMPLES DENTRO DE ASPAS SIMPLES
				else if(Lexer.getCurrentChar() == '/') {
					Lexer.setState(16);
				}				
				break;
			
			case 16:
				if(Lexer.getCurrentChar() == '/') {
					Lexer.setState(18);
				}else if(Lexer.getCurrentChar() == '*'){
					Lexer.setState(20);
				}else {
					Lexer.setState(17);
					lexeme.append(Lexer.getCurrentChar());
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
				}
				break;
			
			case 21:
				if(Lexer.getCurrentChar() == '/') {
					Lexer.setState(0);
				}else {
					Lexer.setState(20);
				}
			
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
					Lexer.setState(0);
					return new Token(Tags.LIT, lexeme.toString(), 
							FileHandle.getCurrentLine(), FileHandle.getCurrentColumn());
					
				}else if(FileHandle.getLookAhead() == FileHandle.getEof()) {
					ErrorMessage.unclosedString();
					return null;
				}
				break;
		}
		return null;
	}
}
