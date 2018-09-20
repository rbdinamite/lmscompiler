package tables;

import java.util.ArrayList;

import errors.SemanticError;

public class SymbolsTable {
	//DECLARA LISTA DE SIMBOLOS
	private ArrayList<Symbol> list = new ArrayList<Symbol>();
	//DESLOCAMENTO
	int displacement;
	
	//REALIZA BUSCA DO SIMBOLO PROCURADO
	public int search(String name){
		for(int i = list.size()-1; i>=0;i--){
			if(list.get(i).getName().equals(name))
				return i;
		}		
		return -1; //SE NÃO FOI ENCONTRADO
	}
	
	//INSERE SIMBOLOS NA TABELA DE SIMBOLOS
	public void insert(Symbol s) throws SemanticError{
		int pos = search(s.getName());		
		if(pos == -1 || list.get(pos).getLevel() != s.getLevel()){
			list.add(s);
		}else
			//SE ACHAR O SIMBOLO, ACUSA O ERRO SEMANTICO APONTANDO QUE A VARIAVEL JÁ FOI DECLARADA
			throw new SemanticError(s.getName() + " já foi declarada");
	}
	
	//EXCLUI SIMBOLOS DO NIVEL ANTERIOR
	public void delete(int level){
		for(int i=0; i<list.size();i++)
			if(list.get(i).getLevel() == level)
				list.remove(i);
		
	}

	public ArrayList<Symbol> getList() {
		return list;
	}

	public void setList(ArrayList<Symbol> list) {
		this.list = list;
	}
	
	public String toString(){
		return list.toString();
	}
}
