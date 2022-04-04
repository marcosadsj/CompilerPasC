package parser;

import java.util.ArrayList;

import lexer.Lexer;
import lexer.model.Token;
import resources.Tag;

public class Parser {

	private final Lexer lexer;
	private Token token;
	private ArrayList<Tag>synchronousTags;
	
	public Parser(Lexer lexer) {
		this.lexer = lexer;
		token = lexer.getNextToken();
		
		synchronousTags = new ArrayList<Tag>();
	}
	
	public void syntaxErro(String menssage) {
		 System.out.print("[Erro Sintatico] na linha " + token.getLine() + " e coluna " + token.getColumn() + ": ");
	     System.out.println(menssage + "\n");
	}
	
	public void advance() {
		token = lexer.getNextToken();
    	System.out.println("[DEBUG]" + token.toString());
	}
	
	public void skip(String menssage) {
		syntaxErro(menssage);
		advance();
	}
	
	public boolean eat(Tag tag) {

		if(token.getTag() == tag) {

			advance();
			
			return true;
		}else {

			return false;
		}
	}
	
	public void synchronizeToken(String message) {
		boolean matchToken = false;
		
		while(!matchToken && token.getTag()!=Tag.EOF) {
			if(synchronousTags.contains(token.getTag())) {
				matchToken = true;
				
			}else {
				skip(message);
			}
		}
		
		synchronousTags.clear();
	}
	
	public void prog() {

		if(token.getTag() == Tag.KW_PROGRAM) {
			
			eat(Tag.KW_PROGRAM);
			
			if(!eat(Tag.ID)) {
	      		syntaxErro("Esperado \"id\", encontrado " + token.getLexeme());
			}
			
			body();
			
		}else {
  		
			syntaxErro("Esperado \"program\", encontrado " + token.getLexeme());
  		
			synchronousTags.add(Tag.EOF);
		
			synchronizeToken("[Modo Panico] Esperado \"EOF\", encontrado " + token.getLexeme());
		}
	}
	
	public void body() {
		
		decl_list();
		
		if(!eat(Tag.SMB_OBC)) 
			syntaxErro("Esperado \"{\", encontrado " + token.getLexeme());
		
		stmt_list();
		
		if(!eat(Tag.SMB_CBC)) 
			syntaxErro("Esperado \"}\", encontrado " + token.getLexeme());
	}

	private void stmt_list() {
		
		if(token.getTag() == Tag.ID
				|| token.getTag() == Tag.KW_IF
				|| token.getTag() == Tag.KW_WHILE
				|| token.getTag() == Tag.KW_READ
				|| token.getTag() == Tag.KW_WRITE) {
				
			stmt();
			
			if(!eat(Tag.SMB_SEM))
				syntaxErro("Esperado \";\", encontrado " 
			+ token.getLexeme());
			
			if(token.getTag() == Tag.ID
					|| token.getTag() == Tag.KW_IF
					|| token.getTag() == Tag.KW_WHILE
					|| token.getTag() == Tag.KW_READ
					|| token.getTag() == Tag.KW_WRITE) {
				stmt_list();
			}
		}
	}

	private void stmt() {
		if(token.getTag() == Tag.ID) {
			assign_stmt();
		}else if(token.getTag() == Tag.KW_IF) {
			if_stmt();
		}else if(token.getTag() == Tag.KW_WHILE) {
			while_stmt();
		}else if(token.getTag() == Tag.KW_READ) {
			read_stmt();
		}else if(token.getTag() == Tag.KW_WRITE) {
			write_stmt();
		}else {
			syntaxErro("Esperado \"=,if,while,read,write\", encontrado " + token.getLexeme());
	  		
			synchronousTags.add(Tag.SMB_SEM);
		
			synchronizeToken("[Modo Panico] Esperado \";\", encontrado " + token.getLexeme());
		}
	}

	private void write_stmt() {
		if(!eat(Tag.KW_WRITE))
			syntaxErro("Esperado \"write\", encontrado " + token.getLexeme());
		writable();
	}

	private void writable() {
		if(token.getTag() == Tag.LIT) {
			eat(Tag.LIT);
		}else if(token.getTag() == Tag.ID ||
				token.getTag() == Tag.KW_IF
				|| token.getTag() == Tag.KW_WHILE
				|| token.getTag() == Tag.KW_READ
				|| token.getTag() == Tag.KW_WRITE) {
			simple_expr();
		}
	}

	private void simple_expr() {
		
		term();
		
		simple_exprL();
	}

	private void simple_exprL() {

		if(token.getTag() == Tag.OP_AD 
				||token.getTag() == Tag.OP_MIN
				|| token.getTag() == Tag.KW_OR) {
			addop();
			
			term();
			
			simple_exprL();
		}
	}

	private void addop() {
		
		if(token.getTag() == Tag.OP_AD) {
			eat(Tag.OP_AD);			

		}else if(token.getTag() == Tag.OP_MIN) {
			eat(Tag.OP_MIN);
		}else if(token.getTag() == Tag.KW_OR) {
				eat(Tag.KW_OR);
		}else {
			syntaxErro("Esperado \"+,-,or\", encontrado " + token.getLexeme());
	  		
			synchronousTags.add(Tag.ID);
			synchronousTags.add(Tag.NUM_CONST);
			synchronousTags.add(Tag.CHAR_CONST);
			synchronousTags.add(Tag.SMB_OPA);
			synchronousTags.add(Tag.KW_NOT);
			
			synchronizeToken("[Modo Panico] Esperado \"id, num_const, char_const,"
					+ " (,not\", encontrado " + token.getLexeme());
		}
	}

	private void term() {
		factor_a();
		
		termL();
	}

	private void factor_a() {
		
		if(token.getTag() == Tag.ID
				|| token.getTag() == Tag.NUM_CONST
				|| token.getTag() == Tag.CHAR_CONST
				|| token.getTag() == Tag.SMB_OPA) {
				
		factor();
		
		}else if(token.getTag() == Tag.KW_NOT) {
			eat(Tag.KW_NOT);
			
			factor();
		}else {
			
			syntaxErro("Esperado \"id,num_const,char_const,(\", encontrado " + token.getLexeme());
	  		
			synchronousTags.add(Tag.OP_MUL);
			synchronousTags.add(Tag.OP_DIV);
			synchronousTags.add(Tag.KW_AND);
			synchronousTags.add(Tag.OP_AD);
			synchronousTags.add(Tag.OP_MIN);
			synchronousTags.add(Tag.KW_OR);
			synchronousTags.add(Tag.SMB_SEM);
			synchronousTags.add(Tag.OP_EQ);
			synchronousTags.add(Tag.OP_GT);
			synchronousTags.add(Tag.OP_GE);
			synchronousTags.add(Tag.OP_LT);
			synchronousTags.add(Tag.OP_LE);
			synchronousTags.add(Tag.OP_NE);
			synchronousTags.add(Tag.SMB_CPA);

			synchronizeToken("[Modo Panico] Esperado \"*,"
					+ "/,and,+,-,or,;,==,>,>=,<,<=,!=,)\", encontrado " + token.getLexeme());
		}
	}

	private void factor() {
		if(token.getTag() == Tag.ID) {
			eat(Tag.ID);
		}else if(token.getTag() == Tag.CHAR_CONST
			|| token.getTag() == Tag.NUM_CONST){
			constant();
		}else if(token.getTag() == Tag.SMB_OPA) {
			eat(Tag.SMB_OPA);
			
			expression();
			
			if(!eat(Tag.SMB_CPA))
				syntaxErro("Esperado \")\", encontrado " + token.getLexeme());
		}else {

			syntaxErro("Esperado \"id,num,char,(\", encontrado " + token.getLexeme());
	  		
			synchronousTags.add(Tag.OP_MUL);
			synchronousTags.add(Tag.OP_DIV);
			synchronousTags.add(Tag.KW_AND);
			synchronousTags.add(Tag.OP_AD);
			synchronousTags.add(Tag.OP_MIN);
			synchronousTags.add(Tag.KW_OR);
			synchronousTags.add(Tag.SMB_SEM);
			synchronousTags.add(Tag.OP_EQ);
			synchronousTags.add(Tag.OP_GT);
			synchronousTags.add(Tag.OP_GE);
			synchronousTags.add(Tag.OP_LT);
			synchronousTags.add(Tag.OP_LE);
			synchronousTags.add(Tag.OP_NE);
			synchronousTags.add(Tag.SMB_CPA);

			synchronizeToken("[Modo Panico] Esperado \"*,"
					+ "/,and,+,-,or,;,==,>,>=,<,<=,!=,)\", encontrado " + token.getLexeme());
		
		}
	}

	private void constant() {

		if(token.getTag() == Tag.NUM_CONST) {
			eat(Tag.NUM_CONST);
		}else if(token.getTag() == Tag.CHAR_CONST) {
			eat(Tag.CHAR_CONST);
		}else {
	syntaxErro("Esperado \"id,num,char,(\", encontrado " + token.getLexeme());
	  		
			synchronousTags.add(Tag.OP_MUL);
			synchronousTags.add(Tag.OP_DIV);
			synchronousTags.add(Tag.KW_AND);
			synchronousTags.add(Tag.OP_AD);
			synchronousTags.add(Tag.OP_MIN);
			synchronousTags.add(Tag.KW_OR);
			synchronousTags.add(Tag.SMB_SEM);
			synchronousTags.add(Tag.OP_EQ);
			synchronousTags.add(Tag.OP_GT);
			synchronousTags.add(Tag.OP_GE);
			synchronousTags.add(Tag.OP_LT);
			synchronousTags.add(Tag.OP_LE);
			synchronousTags.add(Tag.OP_NE);
			synchronousTags.add(Tag.SMB_CPA);

			synchronizeToken("[Modo Panico] Esperado \"*,"
					+ "/,and,+,-,or,;,==,>,>=,<,<=,!=,)\", encontrado " + token.getLexeme());
		}
	}

	private void termL() {
		if(token.getTag() == Tag.OP_MUL
			|| token.getTag() == Tag.OP_DIV
			|| token.getTag() == Tag.KW_AND) {
			
			mulop();
			
			factor_a();
			
			termL();
		}
	}

	private void mulop() {
		if(token.getTag() == Tag.OP_MUL) {
			eat(Tag.OP_MUL);
		}else if(token.getTag() == Tag.OP_DIV) {
			eat(Tag.OP_DIV);
		}else if(token.getTag() == Tag.KW_AND){
			eat(Tag.KW_AND);
		}else{
			
			syntaxErro("Esperado \"id,num,char,(\", encontrado " + token.getLexeme());
	  		
			synchronousTags.add(Tag.ID);
			synchronousTags.add(Tag.NUM_CONST);
			synchronousTags.add(Tag.CHAR_CONST);
			synchronousTags.add(Tag.SMB_OPA);
			synchronousTags.add(Tag.KW_NOT);
			
			synchronizeToken("[Modo Panico] Esperado \"id, num_const, char_const,"
					+ " (,not\", encontrado " + token.getLexeme());
		}		
	}

	private void read_stmt() {
		if(!eat(Tag.KW_READ))
			syntaxErro("Esperado \"read\", encontrado " + token.getLexeme());

		
		if(!eat(Tag.ID)) 
			syntaxErro("Esperado \"id\", encontrado " + token.getLexeme());
	}

	private void while_stmt() {
		stmt_prefix();
		
		if(!eat(Tag.SMB_OBC))
			syntaxErro("Esperado \"{\", encontrado " + token.getLexeme());
	
		stmt_list();
		
		if(!eat(Tag.SMB_CBC))
			syntaxErro("Esperado \"}\", encontrado " + token.getLexeme());
	}

	private void stmt_prefix() {
		if(!eat(Tag.KW_WHILE))
			syntaxErro("Esperado \"while\", encontrado " + token.getLexeme());
		
		if(!eat(Tag.SMB_OPA))
			syntaxErro("Esperado \"(\", encontrado " + token.getLexeme());
	
		condition();
		
		if(!eat(Tag.SMB_CPA))
			syntaxErro("Esperado \")\", encontrado " + token.getLexeme());
	
	}

	private void condition() {
		expression();	
	}

	private void expression() {
		simple_expr();
		
		expressionL();
	}

	private void expressionL() {
		if(token.getTag() == Tag.OP_EQ
				|| token.getTag() == Tag.OP_LT
				|| token.getTag() == Tag.OP_GT
				|| token.getTag() == Tag.OP_LE
				|| token.getTag() == Tag.OP_GE
				|| token.getTag() == Tag.OP_NE){
			
			relop();
			
			simple_expr();
		}
	}

	private void relop() {
		if(token.getTag() == Tag.OP_EQ) {
			eat(Tag.OP_EQ);
		}else if(token.getTag() == Tag.OP_GT) {
			eat(Tag.OP_GT);
		}else if(token.getTag() == Tag.OP_GE){
			eat(Tag.OP_GE);
		}else if(token.getTag() == Tag.OP_LT){
			eat(Tag.OP_LT);
		}else if(token.getTag() == Tag.OP_LE){
			eat(Tag.OP_LE);
		}else if(token.getTag() == Tag.OP_NE) {
			eat(Tag.OP_NE);
		}else{
			
			syntaxErro("Esperado \"id,num,char,(\", encontrado " + token.getLexeme());
	  		
			synchronousTags.add(Tag.ID);
			synchronousTags.add(Tag.NUM_CONST);
			synchronousTags.add(Tag.CHAR_CONST);
			synchronousTags.add(Tag.SMB_OPA);
			synchronousTags.add(Tag.KW_NOT);
			

			synchronizeToken("[Modo Panico] Esperado \"id, num_const, char_const,"
					+ " (,not\", encontrado " + token.getLexeme());
		}	
	}

	private void if_stmt() {
		
		if(eat(Tag.KW_IF)) {
	
			if(!eat(Tag.SMB_OPA))
				syntaxErro("Esperado \"(\", encontrado " + token.getLexeme());
		
			condition();
			
			if(!eat(Tag.SMB_CPA))
				syntaxErro("Esperado \")\", encontrado " + token.getLexeme());
		
			if(!eat(Tag.SMB_OBC)) 
				syntaxErro("Esperado \"{\", encontrado " + token.getLexeme());
			
			stmt_list();
			
			if(!eat(Tag.SMB_CBC)) 
				syntaxErro("Esperado \"}\", encontrado " + token.getLexeme());
			
			if_stmtL();
		}else {
				  		
			synchronousTags.add(Tag.SMB_SEM);

			synchronizeToken("[Modo Panico] Esperado \";\" encontrado " + token.getLexeme());
		}
	}

	private void if_stmtL() {
			
		if(eat(Tag.KW_ELSE)) {
			
			if(!eat(Tag.SMB_OBC)) 
				syntaxErro("Esperado \"{\", encontrado " + token.getLexeme());
			
			stmt_list();
			
			if(!eat(Tag.SMB_CBC)) 
				syntaxErro("Esperado \"}\", encontrado " + token.getLexeme());
		}
	}

	private void assign_stmt() {

		if(!eat(Tag.ID))
			syntaxErro("Esperado \"id\", encontrado " + token.getLexeme());
		
		if(!eat(Tag.OP_ASS))
			syntaxErro("Esperado \"=\", encontrado " + token.getLexeme());
		
		simple_expr();
	}

	private void decl_list() {

		decl();
		
		if(!eat(Tag.SMB_SEM))
			syntaxErro("Esperado \";\", encontrado " + token.getLexeme());
		
		if(token.getTag() == Tag.KW_NUM ||token.getTag() == Tag.KW_CHAR)
			decl_list();
		
	}

	private void decl() {

		type();
		
		id_list();
	}

	private void id_list() {
		if(!eat(Tag.ID))
			syntaxErro("Esperado \"id\", encontrado " + token.getLexeme());
		
		id_listL();
	}

	private void id_listL() {
		if(eat(Tag.SMB_COM)) {
			id_list();
		}
	}

	private void type() {
		if(token.getTag() == Tag.KW_NUM || token.getTag() == Tag.KW_CHAR)
		{
			if(eat(Tag.KW_NUM)) {
				return;
			}
			
			if(eat(Tag.KW_CHAR)) {
				return;
			}
		}else {
			
			syntaxErro("Esperado \"num, char\", encontrado " + token.getLexeme());
		}
	}
}
