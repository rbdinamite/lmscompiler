package brain;

/**
 * CLASSE RESPONSAVEL POR MANIPULAR A AREA DE LITERAIS
 * @author TheStaff
 */

public class LiteralArea {
	public String items[]= new String[30];
	public int LIT;
	
	public String toString(){
		String msg="";
		for(String item:items)
			msg+=item+"\n";
		return msg;
	}
}
