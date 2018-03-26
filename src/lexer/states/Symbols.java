package lexer.states;

import lexer.Lexer;
import lexer.handle.FileHandle;
import lexer.model.Token;
import resources.Tags;

public class Symbols {
	
	public static Token analyse(StringBuilder lexeme) {
		
		switch(Lexer.getState()) {
			
			case 0:
				if(Lexer.getCurrentChar() == '{') {
					lexeme.append(Lexer.getCurrentChar());
					return new Token(Tags.SMB_OBC, lexeme.toString(),
							FileHandle.getCurrentLine(), FileHandle.getCurrentColumn());	
				}else if(Lexer.getCurrentChar() == '}') {
					lexeme.append(Lexer.getCurrentChar());
					return new Token(Tags.SMB_CBC, lexeme.toString(),
							FileHandle.getCurrentLine(), FileHandle.getCurrentColumn());
				}else if(Lexer.getCurrentChar() == ')') {
					lexeme.append(Lexer.getCurrentChar());
					return new Token(Tags.SMB_CPA, lexeme.toString(),
							FileHandle.getCurrentLine(), FileHandle.getCurrentColumn());
				}else if(Lexer.getCurrentChar() == '(') {
					lexeme.append(Lexer.getCurrentChar());
					return new Token(Tags.SMB_OPA, lexeme.toString(),
							FileHandle.getCurrentLine(), FileHandle.getCurrentColumn());
				}else if(Lexer.getCurrentChar() == ',') {
					lexeme.append(Lexer.getCurrentChar());
					return new Token(Tags.SMB_COM, lexeme.toString(),
							FileHandle.getCurrentLine(), FileHandle.getCurrentColumn());
				}else if(Lexer.getCurrentChar() == ';') {
					lexeme.append(Lexer.getCurrentChar());
					return new Token(Tags.SMB_SEM, lexeme.toString(),
							FileHandle.getCurrentLine(), FileHandle.getCurrentColumn());
				}
		}
		return null;
	}

}
