package analyser;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.NoSuchElementException;

import errors.SemanticError;
import errors.SyntaticError;
import tables.Rule;
import tables.ParsingTable;
import tables.Token;


public class SyntaticAnalyzer {
	// LISTA DE DE stack DO TIPO INTEIRO
	LinkedList<Integer> stack;
	
	// DECLARACAO DO OBJETO SEMANTICO
	SemanticAnalyzer semantic;

	
	public boolean syntaticAnalisys(LinkedList<Token> tokensList, boolean isSemantic) throws SyntaticError,SemanticError{
		// INCIALIZA AS DUAS VARIAVIES
		semantic = new SemanticAnalyzer();
		stack = new LinkedList<Integer>();
		
		// TRAZ "46" PARA O COMECO DA LISTA DA PILHA. 46 EH O CODIGO DO PRIMEIRO NAO-TERMINAL DE ACORDO COM A TABELA DE ANALISE
		stack.push(46);
		
		// INICIALIZA OS TOKENS DE CONTROLE PARA O PROCESSO
		Token current = null, previous = null, previous2 = null;
		
		int x;
		try{
			// LOOP ENQUANTO A LISTA NA PILHA FOR VAZIA OU LISTA DE TOKENS FOR VAZIA
			do{
				// ARMAZENA CODIGO DO PRIMEIRO VALOR DA PILHA
				x = stack.getFirst();
				
				try{
					// ARMAZENA PRIMEIRO TOKEN NA LISTA DE TOKENS GERADA NA ANALISE LEXICA PARA A VARIAVEL CURRENT
					current = tokensList.getFirst();	
				}catch (NoSuchElementException  e) {
					x = stack.getFirst();
				}
				// SE FOR TERMINAL OU $ (46 PRIMEIRO CODIGO NAO TERMINAL)
				if(x <46){
					// SE PRIMEIRO CODIGO DA STACK FOR IGUAL AO CODIGO DO CURRENT TOKEN DA LISTA DE TOKENS
					if(x == current.getCode()){
						// APAGA PRIMEIRO CODIGO DA PILHA
						stack.pop();
						
						// SE EXISTIR UM ANTERIOR, ARMAZENA O MESMO PARA CONTROLAR SEU ANTERIOR
						if(previous != null){
							previous2 = previous.clone();
						}
						
						// ARMAZENA O PRIMEIRO TOKEN REMOVIDO DA LISTA PARA CONTROLAR O ANTERIOR DELE
						previous = tokensList.removeFirst();
						
					}else throw new SyntaticError("Nao esperava '"+ current.getName() + "' depois de '" + previous.getName()+"'");
					
				// SE X FOR NAO TERMINAL (77 PRIMEIRA ACAO SEMANTICA)
				}else if(x<77){ 
					
					// PROCURA REGRA NA TABELA PARSE DO NAO TERMINAL X, E TERMINAL DO CODIGO DO TOKEN CURRENT E ARMAZENA NA VARIAVEL R
					Rule r = ParsingTable.findRule(x, current.getCode());
					
					// SE ACHOU REGRA, APAGA PRIMEIRO CODIGO DA PILHA E EXPANDE DERIVACOES DA REGRA NA PILHA
					if( r  != null ){
						stack.pop();
						expandsRule(r);				
					}else{
						// SE REGRA ACHADA NAO TEM DERIVACOES, APAGA PRIMEIRO CODIGO DA PILHA, SENAO RETORNA ERRO SINTATICO
						if(ParsingTable.checkIfAcceptEmpty(x, current.getCode())){
							stack.pop();
						}else throw new SyntaticError("N�o esperava '"+current.getName()+ "' depois de '" + previous.getName()+"'");
					}
					
					// SE X FOR CODIGO DE UMA REGRA SEMANTICA, EXECUTA ACAO SEMANTICA
				}else {
					
					// SE BOTAO PRESSIONADO FOR PARA REALIZAR ANALISE SEMANTICA, EXECUTA ACAO SEMANTICA
					if(isSemantic){
						// PASSA COMO PARAMETRO O PRIMEIRO CODIGO DA PILHA JUNTO COM SEUS ANTERIORES CONTROLADOS
						semantic.execSemanticAction(x,previous,previous2);
					}
					// APAGA PRIMEIRO CODIGO DA PILHA
					stack.pop();
				}
				
			}while(!stack.isEmpty() || !tokensList.isEmpty()); 
			
			return true;
			
		} catch (NoSuchElementException e) {
			// SE � PORQUE O ANTERIOR N�O EXISTE, ACUSA ERRO SINTATICO QUE DEVE COME�AR COM PROGRAM
			if(previous == null) {
				throw new SyntaticError("O Programa deve comecar com o token 'program'");
				
			// SE FOR PORQUE O ANTERIOR EH DIFERENTE DO ATUAL
			} else if (previous != current) {
				throw new SyntaticError("Nao esperava '"+current.getName()+ "' depois de '" + previous.getName()+"'");
				
			// SENAO FOR ESSAS CONDICOES E ACABAR DE LER CADEIA COM TOKEN PREVIOUS INVALIDO
			} else {
				throw new SyntaticError("Nao Esperava fim de arquivo depois de '"+ previous.getName()+"'" );
			}
		}
	}
	
	// PEGA ARRAY DE DERIVA��ES DA REGRA RETONADA E PERCORRE DE FORMA DECRESCENTE ARMAZENANDO NA PILHA
	private void expandsRule(Rule r){
		ArrayList<Integer> aux = r.getDerivations();
		for(int i = aux.size() -1; i>=0; i--){
			stack.push(aux.get(i));
		}
	}
}
