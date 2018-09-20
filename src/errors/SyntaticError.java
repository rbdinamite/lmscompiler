package errors;
/**
 * CLASSE RESPONSAVEL POR TRATAR AS EXCESSOES RELACIONADAS AO ANALISADOR SINTATICO
 * @author TheStaff
 *
 */
public class SyntaticError extends Exception {

	private static final long serialVersionUID = 1L;
	String msg;
	
	public SyntaticError(String msg) {
		this.msg = msg;
	}
	
	public String getMessage(){
		return msg;
	}
	
}
