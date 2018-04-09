package lexer.starter;

import lexer.Lexer;
import lexer.handle.FileHandle;
import lexer.model.Description;
import lexer.model.Token;
import resources.SymbolsTable;
import resources.Tags;

public class Starter {
	
	public Starter(String args){
				
		SymbolsTable symbolsTable = new SymbolsTable();
		
		Lexer lexer = new Lexer(args);
		
		Token token;
		        
		do {
			
			token = lexer.getNextToken();
			
			if(token != null) {
                System.out.println("Token: " + token.toString() + "\t Linha: " + FileHandle.getCurrentLine() + "\t Coluna: " + FileHandle.getCurrentColumn());
                                
                if(symbolsTable.getToken(token.getLexeme())== null) {
                	symbolsTable.setSymbol(token, new Description());
                }
			}
			
		}while(token != null && token.getTag() != Tags.EOF);
		
		FileHandle.closeFile();
		
		System.out.println("\n********************************\n");
		
		System.out.println("Tabela de Símbolos");
		
		System.out.println(symbolsTable.toString());
		
		System.out.println("********************************");
	}
}