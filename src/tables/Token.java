package tables;
/**
 * CLASSE RESPONSÁVEL POR REPRESENTAR UM TOKEN DENTRO DAS AMPLAS ANÁLISES REALIZADAS
 * @author TheStaff
 *
 */
public class Token {
	String description, name;
	int code;
	
	boolean reservedWord;
	
	public Token(String name, int code, String description) {
		super();
		this.name = name;
		this.code = code;
		this.description = description;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	
	public void setReserverWord(boolean reservedWord) {
		this.reservedWord = reservedWord;
	}
	public String toString(){
		return name + " | " + code + " | " + description;
	}
	
	public String[] toArray(){
		return new String[]{code+"", name, description};
	}
	
	public Token clone(){
		return new Token(name,code,description);
	}
	
}
