package tables;
/**
 * CLASSE RESPONSÁVEL POR REPRESENTAR OS SIMBOLOS ENCONTRADOS
 * @author TheStaff
 */

public class Symbol {
	private String name,category;
	private int level,generalA, generalB;
	
	public Symbol(String name, String category, int level, int generalA,
			int generalB) {
		super();
		this.name = name;
		this.category = category;
		this.level = level;
		this.generalA = generalA;
		this.generalB = generalB;
	}
	
	public String toString(){
		return name + " | " +category +" | "+ level + " | " + generalA+ " | " + generalB;
	}

	public String getName() {
		return name;
	}

	public String getCategory() {
		return category;
	}

	public int getLevel() {
		return level;
	}

	public int getGeneralA() {
		return generalA;
	}

	public int getGeneralB() {
		return generalB;
	}

	public void setGeneralA(int generalA) {
		this.generalA = generalA;
	}

	public void setGeneralB(int generalB) {
		this.generalB = generalB;
	}
}
