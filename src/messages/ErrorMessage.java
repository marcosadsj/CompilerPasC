package messages;

import lexer.handle.FileHandle;

public class ErrorMessage {
	
	private ErrorMessage() {}
	
	
	private static final String RED_COLOR ="\n" + (char)27+ "[31m";
	private static final String ERROR_TYPE = "[Lexical Error]:";
	
	private static final String ERROR_CONFIG = RED_COLOR + ERROR_TYPE;
	
	public static void invalidCaractere(char catactere){
		System.out.println(ERROR_CONFIG + "Caractere invalido " + catactere + " na linha " + FileHandle.getCurrentLine() + " e coluna " + FileHandle.getCurrentColumn());
	}
	
	public static void incompleteToken(){
		System.out.println(ERROR_CONFIG + "Token incompleto para o caractere ! na linha " + FileHandle.getCurrentLine() + " e coluna " + FileHandle.getCurrentColumn());
	}
	
	public static void unclosedString(){
		System.out.println(ERROR_CONFIG + "String deve ser fechada com \" antes do fim de arquivo");
	}
	
	public static void invalidDoublePattern(){
		System.out.println(ERROR_CONFIG + "Padrão para double inválido na linha " + FileHandle.getCurrentLine() + " coluna " + FileHandle.getCurrentColumn());
	}
}