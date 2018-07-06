package lexer.messages;

import lexer.handle.FileHandle;

public class ErrorMessage {
	
	private ErrorMessage() {}
	
	
	private static final String RED_COLOR ="\n" + (char)27+ "[31m";
	private static final String ERROR_TYPE = "[Lexical Error]:";
	
	private static final String ERROR_CONFIG = RED_COLOR + ERROR_TYPE;
	
	public static void invalidCaractere(char catactere){
		System.out.println(ERROR_CONFIG + "Caractere invalido: \"" + catactere + "\" na linha " + FileHandle.getCurrentLine() + " e coluna " + FileHandle.getCurrentColumn()+"\n");
	}
	
	public static void incompleteToken(){
		System.out.println(ERROR_CONFIG + "Token incompleto para o caractere ! na linha " + FileHandle.getCurrentLine() + " e coluna " + FileHandle.getCurrentColumn()+"\n");
	}
	
	public static void unclosedString(){
		System.out.println(ERROR_CONFIG + "String deve ser fechada com \" antes do fim de arquivo! linha " + FileHandle.getCurrentLine() + " coluna " + FileHandle.getCurrentColumn()+"\n");
	}
	
	public static void moreThanOneLineString(){
		System.out.println(ERROR_CONFIG + "String com mais de uma linha! linha " + FileHandle.getCurrentLine() + " coluna " + FileHandle.getCurrentColumn()+"\n");
	}
	
	public static void emptyString(){
		System.out.println(ERROR_CONFIG + "String vazia! na linha " + FileHandle.getCurrentLine() + " coluna " + FileHandle.getCurrentColumn()+"\n");
	}

	
	public static void unclosedComment() {
		System.out.println(ERROR_CONFIG + "Comentário começado com  \\\"* não fechado!"+"\n");
	}
}