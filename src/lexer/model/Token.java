package lexer.model;
import resources.Tag;

public class Token {
	private final int line;
	private final int column;
	private final String lexeme;
	private final Tag tag;
	
	public Token(Tag tag, String lexeme, int line, int column) {
		this.line = line;
		this.column = column;
		this.lexeme = lexeme;
		this.tag = tag;
	}
	
	public Token(Tag tag, String lexeme) {
		this.line = 0;
		this.column = 0;
		this.lexeme = lexeme;
		this.tag = tag;
	}

	public int getLine() {
		return line;
	}

	public int getColumn() {
		return column;
	}

	public String getLexeme() {
		return lexeme;
	}

	public Tag getTag() {
		return tag;
	}
	
	@Override
	public String toString() {
		return "<" + getTag() + ", \"" + getLexeme() + "\">";
	}
}
