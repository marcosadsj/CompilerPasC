package lexer.states;

import lexer.Lexer;
import lexer.handle.FileHandle;
import lexer.model.Token;
import messages.ErrorMessage;
import resources.Tags;

public class Operators {

	public static Token analyse(StringBuilder lexeme) {
		// TODO Auto-generated method stub
		
		switch(Lexer.getState()) {
		
			case 0:
				if(Lexer.getCurrentChar() == '*') {
					lexeme.append(Lexer.getCurrentChar());
					Lexer.setState(0);
					return new Token(Tags.OP_MUL, lexeme.toString(),
							FileHandle.getCurrentLine(), FileHandle.getCurrentColumn());
				}else if(Lexer.getCurrentChar() == '<') {
					lexeme.append(Lexer.getCurrentChar());
					Lexer.setState(4);
				}else if(Lexer.getCurrentChar() == '>') {
					lexeme.append(Lexer.getCurrentChar());
					Lexer.setState(3);
				}else if(Lexer.getCurrentChar() == '!') {
					lexeme.append(Lexer.getCurrentChar());
					Lexer.setState(222); // TODO Atualizar valor com estado do autômato modificado
				}else if(Lexer.getCurrentChar() == '=') {
					lexeme.append(Lexer.getCurrentChar());
					Lexer.setState(111);//TODO Atualizar valor com estado do autômato modificado
				}else if(Lexer.getCurrentChar() == '+') {
					lexeme.append(Lexer.getCurrentChar());
					Lexer.setState(0);
					return new Token(Tags.OP_AD, lexeme.toString(),
							FileHandle.getCurrentLine(), FileHandle.getCurrentColumn());
				}else if(Lexer.getCurrentChar() == '-') {
					lexeme.append(Lexer.getCurrentChar());
					Lexer.setState(0);
					return new Token(Tags.OP_MIN, lexeme.toString(),
							FileHandle.getCurrentLine(), FileHandle.getCurrentColumn());
				}else {
					Lexer.incrementKnewnChar();
				}
				
			break;
			
			case 3:
				if(Lexer.getCurrentChar() == '=') {
					lexeme.append(Lexer.getCurrentChar());
					Lexer.setState(0);
					return new Token(Tags.OP_GE, lexeme.toString(),
							FileHandle.getCurrentLine(), FileHandle.getCurrentColumn());
				}else {
					Lexer.setState(0);
					FileHandle.fallbackCursor();
					return new Token(Tags.OP_GT, lexeme.toString(),
							FileHandle.getCurrentLine(), FileHandle.getCurrentColumn());
				}
			
			case 4:
				if(Lexer.getCurrentChar() == '=') {
					lexeme.append(Lexer.getCurrentChar());
					Lexer.setState(0);
					return new Token(Tags.OP_LE, lexeme.toString(),
							FileHandle.getCurrentLine(), FileHandle.getCurrentColumn());
					
				}else {
					Lexer.setState(0);
					FileHandle.fallbackCursor();
					return new Token(Tags.OP_LT, lexeme.toString(),
							FileHandle.getCurrentLine(), FileHandle.getCurrentColumn());
				}			
			case 17:
				Lexer.setState(0);
				lexeme.append('/');
				return new Token(Tags.OP_DIV, lexeme.toString(),
						FileHandle.getCurrentLine(), FileHandle.getCurrentColumn());	
		
			case 111:
				if(Lexer.getCurrentChar() == '=') {
					lexeme.append(Lexer.getCurrentChar());
					Lexer.setState(0);
					return new Token(Tags.OP_EQ, lexeme.toString(),
							FileHandle.getCurrentLine(), FileHandle.getCurrentColumn());	
					
				}else {
					FileHandle.fallbackCursor();
					Lexer.setState(0);
					return new Token(Tags.OP_ASS, lexeme.toString(),
							FileHandle.getCurrentLine(), FileHandle.getCurrentColumn());	
				}
				
			case 222:
				if(Lexer.getCurrentChar() == '=') {
					lexeme.append(Lexer.getCurrentChar());
					Lexer.setState(0);
					return new Token(Tags.OP_GE, lexeme.toString(),
							FileHandle.getCurrentLine(), FileHandle.getCurrentColumn());
				}else {
					Lexer.setState(0);
					ErrorMessage.incompleteToken();
					lexeme.deleteCharAt(0);
					return null;
				}
		}
		return null;
	}
}