package compiler;


import lexer.Lexer;
import lexer.handle.FileHandle;
import parser.Parser;
import resources.SymbolsTable;

public class Starter {
	
	public Starter(String args){
						
		SymbolsTable symbolsTable = new SymbolsTable();

		Lexer lexer = new Lexer(args, symbolsTable);
		
		Parser parser = new Parser(lexer);
		
		parser.prog();
		
		FileHandle.closeFile();
		
		System.out.println("\n-------------------------------\n");
		
		System.out.println("Compilação finalizada!");

		System.out.println("\n-------------------------------\n");
		
		System.out.println("Tabela de Símbolos");
		
		System.out.println(symbolsTable.toString());
		
		System.out.println("********************************");
	}
}