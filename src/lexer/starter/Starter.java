package lexer;

import lexer.handle.FileHandle;
import lexer.model.Description;
import lexer.model.Token;
import resources.Tags;

public class Starter {
	
	public static void main(String args[]){
		SymbolsTable symbolsTable = new SymbolsTable();
				
		Lexer lexer = new Lexer("/home/marcosadsj/Github/compilerPasC/src/resources/testFile");
		
		Token token;
		
		do {
			
			token = lexer.getNextToken();
			
			if(token != null) {
                System.out.println("Token: " + token.toString() + "\t Linha: " + FileHandle.getCurrentLine() + "\t Coluna: " + FileHandle.getCurrentColumn());
                
                //TODO verificar se o lexema está na tabela de símbolos
                
                if(symbolsTable.getToken(token.getLexeme())!= null) {
                	symbolsTable.setSymbol(token, new Description());
                }
                
			}
			
		}while(token != null && token.getTag() != Tags.EOF);
		
		FileHandle.closeFile();
		
		//TODO imprimir tabela de símbolos
	}
}