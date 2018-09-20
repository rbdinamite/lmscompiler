package errors;
/**
 * CLASSE RESPONSAVEL POR TRATAR AS EXCESSOES RELACIONADAS AO ANALISADOR LEXICO 
 * @author TheStaff
 *
 */
public class LexicalError extends Exception {
	
	private static final long serialVersionUID = 1L;
	String msg;
	int index, lenght;
	boolean comment, literal;

	public LexicalError(String msg, int index, int lenght) {
		super();
		this.msg = msg;
		this.index = index;
		this.lenght = lenght;
	}

	public LexicalError(String msg, boolean comment, boolean literal, int index) {
		super();
		this.msg = msg;
		this.comment = comment;
		this.literal = literal;
		this.index = index;
	}
	
	public boolean isComment() {
		return comment;
	}

	public boolean isLiteral() {
		return literal;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getLenght() {
		return lenght;
	}

	public void setLenght(int lenght) {
		this.lenght = lenght;
	}

	public String getMessage(){
		return msg;
	}
}
