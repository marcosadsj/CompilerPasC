package resources;
import java.util.HashMap;

import lexer.Description;
import lexer.Token;

public class SymbolsTable {

	private HashMap<Token,Description> sysmbolsTable;
	
	public SymbolsTable() {
		
		sysmbolsTable = new HashMap<>();
		
		sysmbolsTable.put(new Token(Tags.KW,"program"), new Description());
		sysmbolsTable.put(new Token(Tags.KW,"if"), new Description());
		sysmbolsTable.put(new Token(Tags.KW,"else"), new Description());
		sysmbolsTable.put(new Token(Tags.KW,"while"), new Description());
		sysmbolsTable.put(new Token(Tags.KW,"write"), new Description());
		sysmbolsTable.put(new Token(Tags.KW,"read"), new Description());
		sysmbolsTable.put(new Token(Tags.KW,"num"), new Description());
		sysmbolsTable.put(new Token(Tags.KW,"char"), new Description());
		sysmbolsTable.put(new Token(Tags.KW,"not"), new Description());
		sysmbolsTable.put(new Token(Tags.KW,"or"), new Description());
		sysmbolsTable.put(new Token(Tags.KW,"and"), new Description());


	}
}