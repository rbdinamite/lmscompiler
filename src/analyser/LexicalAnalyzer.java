package analyser;


import java.util.LinkedList;

import errors.LexicalError;
import tables.Token;

public class LexicalAnalyzer {

	// VETOR DE LETRAS
	private char[] letters = new char[]{'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'},
	// VETOR DE NUMEROS
	numbers = new char[]{'0','1','2','3','4','5','6','7','8','9'};
	
	// VARIAVEL DE INDICE
	private int index;
	
	// ALGORITMO CARREGADO OU DIGITADO
	private String content;
	
	// PONTEIRO QUE PERCORRE O ALGORITMO
	private char current;
	
	// ESTADO CORRESPONDENTE AO AUTOMATO
	private int state;
	
	// LISTA DE PALAVRAS RESERVADAS
	public static  LinkedList<Token> reservedWords;
	
	// VARIAVEIS PARA CONTROLAR CASOS DE COMENTARIO ABERTO OU LITERAL ABERTO
	private boolean openComment, openLiteral;

	public Token nextToken() throws LexicalError{
		
		// VARIAVEL PRA CONCATENAR OS CARACTERES ANTES DE ACHAR UM TOKEN
		StringBuffer aux = new StringBuffer();
		
		// PERCORRE CADEIA DE CARACTERES DO CAMPO "TEXT" PARA BUSCAR OS TOKENS E REALIZAR A ANALISE LEXICA
		for(int i = index; i< content.length(); i++){
			index = i;
			// BUSCA CARACTER DA CADEIA NA POSICAO I
			current = content.charAt(i);		
			
			// A VERIFICACAO EH FEITA ATRAVES DO AUTOMATO DA LINGUAGEM LMS
			switch(state){
			
				case 0:				
					if(bound(current, letters)){					
						aux.append(current);				
						state = 1;
					}else if(bound(current, numbers)){					
						aux.append(current);
						state = 3;
					}else if(current == '"'){	
						openLiteral = true;
						state =12;
					}else if(current == '+'){
						index = i+1;					
						return  new Token(current+"", 2, "Operacao Adicao");
					}else if(current == '-'){
						index = i+1;
						return new Token('-'+"",3,"Operacao Subtracao");
					}else if(current == '*'){
						index = i+1;
						return new Token(current+"",4, "Operador Multiplicacao");
					}else if(current == '/'){
						index = i+1;
						return new Token(current+"", 5, "Operador Divisao");
					}else if(current == '<'){
						aux.append(current);
						state = 10;
					}else if(current == '>'){
						aux.append(current);
						state = 5;
					}else if(current == '='){
						index = i+1;
						return new Token(current+"", 6, "Igual");
					}else if(current == ':'){
						aux.append(current);
						state = 7;
					}else if(current == '.'){
						index = i+1;
						return new Token(aux.toString(),16, "Ponto");
					}else if(current == '('){
						aux.append(current);
						state = 14;
					}else if(current == ')'){
						index = i+1;
						return new Token(current+"", 18, "Fecha Parenteses");
					}else if (current == ','){
						index = i+1;
						return new Token(current+"",15, "Virgula");
					}else if(current == ';'){
						index = i+1;
						return new Token(current+"",14, "Ponto e Virgula");
					}else if(current == ' ' || current == '\n' || current == '\t'|| current == '\b' || current == '\f'|| current == '\r'){
						index = i+1;
					}else if(current == '$' && i == content.length() -1){
						return null;
					}else {
						// SE ACHAR CARACTERE INVALIDO, CHAMA ERRO LEXICO
						throw new LexicalError("Caractere Invalido",i,1);
					}
	
					break;
					
				// ESTADO QUE VERIFICA SE EH IDENTIFICADOR
				case 1: 
					if(bound(current, letters)|| bound(current, numbers)){
						aux.append(current);				
					}else{
						state = 0;
						index = i;
						Token t =  new Token(aux.toString(),19,"Identificador");
						// PERCORRE LISTA DE PALAVRAS RESERVADAS E COMPARA COM TOKEN ATUAL, SE FOR IGUAL RETORNA A PALAVRA RESERVADA, SENAO RETORNA O IDENTIFICADOR
						for(Token f: reservedWords){						
							if(f.getName().equals(t.getName())){								
								t = f;
								break;
							}
						}					
						return t;
					}
					break;
					
				// ESTADO QUE VERIFICA OS NUMEROS, SE NUMERO FOR MAIOR QUE 32767 RETORNA ERRO LEXICO
				case 3:
					if(bound(current, numbers)){
						aux.append(current);
					}else{
						state = 0;
						index = i;
						int num=0 ;
						try{
							num = Integer.parseInt(aux.toString());
						}catch(NumberFormatException e){
							throw new LexicalError("N�mero fora da escala",i- aux.length(),aux.length());
						}
						if(num>32767||num<-32767 ){
							throw new LexicalError("N�mero fora da escala",i- aux.length(),aux.length());
						}
	
						return new Token(aux.toString(),20, "Inteiro");
					}
					break;
					
				// STATE QUE VERIFICA LITERAIS, SE TAMANHO DO LITERAL FOR MAIOR QUE 255 RETORNA ERRO LEXICO
				case 12:
					if(current != '"'){
						aux.append(current);	
					}else {
						state = 0;
						index = i+1;
						openLiteral = false;
						if(aux.length() > 255) throw new LexicalError("Literal Muito Grande",i- aux.length(),aux.length());
						return new Token(aux.toString(),21,"Literal");
					}
					break;
	
				// ESTADO QUE VERIFICA OS SINAIS COMPOSTOS
				case 10:
					state = 0;
					if(current == '>'){
						aux.append(current);					
						index = i+1;					
						return new Token(aux.toString(),11, "Diferente");
					}else if(current == '='){
						aux.append(current);
						index = i+1;				
						return new Token(aux.toString(),10, "Menor ou Igual");
					}		
					index = i;
					return new Token(aux.toString(),9, "Menor");
	
	
	
				case 5:
					state = 0;
					if(current == '='){
						aux.append(current);
						index = i+1;
						return new Token(aux.toString(),8, "Maior ou Igual");
					}else{
						index = i;
						return new Token(aux.toString(),7, "Maior");}
	
				case 7:
					state = 0;
					if(current == '='){
						aux.append(current);
						index = i+1;
						return new Token(aux.toString(),12, "Atribuicao");
					}else{
						index = i;
						return new Token(aux.toString(),13, "Dois Pontos");}
				
				case 14:
					if(current == '*'){
						openComment = true;
						aux = new StringBuffer();
						state = 15;
					}
					else{
						state = 0;
						index = i;
						return new Token("(",17,"Abre Parenteses");
					}
					break;
	
				case 15:					
					if(current == '*' && content.charAt(i+1) == ')'){
						openComment = false;
						state = 0;
						i+=1;
					}
					break;
			}
		}
		
		// SE COMENTARIO TIVER ABERTO QUANDO TERMINAR VERIFICACAO, RETORNA ERRO LEXICO
		if(openComment)
			throw new LexicalError("Comentario Aberto",true,false,index);
		
		// SE LITERAL TIVER ABERTO QUANDO TERMINAAR A VERIFICACAO, RETORNA ERRO LEXICO
		if(openLiteral)
			throw new LexicalError("Literal Aberto",false,true, index);
		
		return null;
		}


		public LexicalAnalyzer(String content) {
			super();
			
			// CONVERTE CADEIA DE CARACTERES PARA MINUSCULOS
			this.content = content.toLowerCase();
			
			// ZERA INDICE PRA PERCORRER
			index = 0;
			
			// CRIA NOVA LISTA DE TOKENS DE PALAVRAS RESERVADAS
			reservedWords = new LinkedList<Token>();
			
			// ADICIONA TODAS AS PALAVRAS RESERVADAS ATRAVES DO METODO COM SEUS RESPECTIVOS CODIGOS
			addReservedWords();

		}

		// VERIFICA SE CARACTER PERTENCE OU NAO AO VETOR DE LETRAS OU NUMEROS
		public boolean bound(char a,char[] entradas){
			for(char aux:entradas)
				if(a == aux) return true;	
			return false;
		}


		// ADICIONA UMA PALAVRA RESERVADA NA LISTA RELACIONADA A MESMA
		private void addWord(String name, int cod){
			reservedWords.add(new Token(name,cod, "Palavra Reservada"));
		}
		
	
		// ADICIONA TODAS AS PALAVRAS RESERVADAS DA LINGUAGEM LMS
		private void addReservedWords(){
			addWord("program", 22);
			addWord("const", 23);
			addWord("var", 24);
			addWord("procedure", 25);
			addWord("begin", 26);
			addWord("end", 27);
			addWord("integer", 28);
			addWord("of", 29);
			addWord("call", 30);
			addWord("if", 31);
			addWord("then", 32);
			addWord("else", 33);
			addWord("while", 34);
			addWord("do", 35);
			addWord("repeat", 36);
			addWord("until", 37);
			addWord("readln", 38);
			addWord("writeln", 39);
			addWord("or", 40);
			addWord("and", 41);
			addWord("not", 42);
			addWord("for", 43);
			addWord("to", 44);
			addWord("case", 45);

		}

	}
