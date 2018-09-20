package tables;

/**
 * CLASSE RESPONSÁVEL POR REPRESENTAR UMA DETERMINADA REGRA DO SISTEMA
 * @author TheStaff
 */

import java.util.ArrayList;

public class Rule {
	
	private int number,terminalCode, NonTerminalCode;
	private ArrayList<Integer> derivations;
	
	
	public Rule(int number, int NonTerminalCode, int terminalCode, ArrayList<Integer> derivations) {
		super();
		this.number = number;
		this.NonTerminalCode = NonTerminalCode;
		this.terminalCode =terminalCode;
		this.derivations = derivations;
	}

	public String toString(){
		return "Regra: " + number + "\nCódigo Não-Terminal: " +NonTerminalCode+ "\nCódigo Terminal: "+terminalCode+"\nDerivações: "+ derivations;
	}

	public int getNumber() {
		return number;
	}


	public void setNumber(int number) {
		this.number = number;
	}


	public int getTerminalCode() {
		return terminalCode;
	}


	public void setTerminalCode(int terminalCode) {
		this.terminalCode =terminalCode;
	}


	public int getNonTerminalCode() {
		return NonTerminalCode;
	}


	public void setNonTerminalCode(int NonTerminalCode) {
		this.NonTerminalCode = NonTerminalCode;
	}


	public ArrayList<Integer> getDerivations() {
		return derivations;
	}


	public void setDerivations(ArrayList<Integer> derivations) {
		this.derivations = derivations;
	}
	
	
	
    
}
