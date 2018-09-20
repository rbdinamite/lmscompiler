package errors;
/**
 * CLASSE RESPONSAVEL POR TRATAR AS EXCESSOES RELACIONADAS AO ANALISADOR SEMANTICO 
 * @author TheStaff
 *
 */
public class SemanticError extends Exception {
	
	private static final long serialVersionUID = 1L;
	String msg;
	
	public SemanticError(String msg) {
		this.msg = msg;
	}
	
	public String getMessage(){
		return msg;
	}
}
