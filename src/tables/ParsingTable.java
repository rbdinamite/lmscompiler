package tables;
/**
 * CLASSE RESPONSAVEL POR GERENCIAR A TABELA DE PARSING 
 * @author TheStaff
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;


public class ParsingTable {

	private static LinkedList<Rule> table = new LinkedList<Rule>();

	//REGRAS INICIALIZADAS ATRAVES DA TABLE PARSING GERADA DA GRAMATICA
	public static void initialize(){

		table.add(new Rule (  1, 46, 22, new ArrayList<Integer>(Arrays.asList(  22,  19, 177,  14,  47,  16, 178))));      		//<PROGRAMA> ::= "PROGRAM" IDENT ";" <BLOCO> "." ; 
		table.add( new Rule(  2, 47,  23, new ArrayList<Integer>(Arrays.asList(50,  52, 179,  55,  57) ) ) );	 	 		//<BLOCO> ::= <DCLCONST> <DCLVAR> <DCLPROC> <CORPO> ; 
		table.add( new Rule(  2, 47,  24, new ArrayList<Integer>(Arrays.asList(50,  52, 179,  55,  57) ) ) );	 	 		//<BLOCO> ::= <DCLCONST> <DCLVAR> <DCLPROC> <CORPO> ; 
		table.add( new Rule(  2, 47,  25, new ArrayList<Integer>(Arrays.asList(50,  52, 179,  55,  57) ) ) );	 	 		//<BLOCO> ::= <DCLCONST> <DCLVAR> <DCLPROC> <CORPO> ; 
		table.add( new Rule(  2, 47,  26, new ArrayList<Integer>(Arrays.asList(50,  52, 179,  55,  57) ) ) );	 	 		//<BLOCO> ::= <DCLCONST> <DCLVAR> <DCLPROC> <CORPO> ; 
		table.add( new Rule(  3, 48,  19, new ArrayList<Integer>(Arrays.asList(19, 181,  49) ) ));						//<LID> ::= IDENT <REPIDENT> ; 
		table.add( new Rule(  4, 49,  13, null ) );																//<REPIDENT> ::= ю ;
		table.add( new Rule(  5, 49,  15, new ArrayList<Integer>(Arrays.asList(15,  19, 181,  49) ) ) );					//<REPIDENT> ::= "," IDENT <REPIDENT> ; 
		table.add( new Rule(  6, 50,  23, new ArrayList<Integer>(Arrays.asList(23,  19, 182,   6,  20, 183,  14,  51) ) ));		//<DCLCONST> ::= "CONST" IDENT "=" INTEIRO ";" <LDCONST> ; 
		table.add( new Rule(  7, 51,  24, null ) ) ;																//<LDCONST> ::= ю ;
		table.add( new Rule(  7, 51,  25, null ) ) ;																//<LDCONST> ::= ю ;
		table.add( new Rule(  7, 51,  26, null ) ) ;																//<LDCONST> ::= ю ;
		table.add( new Rule(  8, 51, 19, new ArrayList<Integer>(Arrays.asList( 19, 182,   6,  20, 183,  14,  51) ) ) );		//<LDCONST> ::= IDENT "=" INTEIRO ";" <LDCONST> ; 
		table.add( new Rule (  9, 50, 24, null));																	//<DCLCONST> ::= ю ; 
		table.add( new Rule (  9, 50, 25, null));																	//<DCLCONST> ::= ю ; 
		table.add( new Rule (  9, 50, 26, null));																	//<DCLCONST> ::= ю ; 
		table.add( new Rule (  10, 52, 24, new ArrayList<Integer>(Arrays.asList( 24, 184,  48,  13,  54,  14,  53))));		//<DCLVAR> ::= "VAR" <LID> ":" <TIPO> ";" <LDVAR> ; 
		table.add( new Rule( 11, 53, 25, null ) );																//<LDVAR> ::= ю ; 
		table.add( new Rule( 11, 53, 26, null ) );																//<LDVAR> ::= ю ; 
		table.add(new Rule ( 12, 53,19, new ArrayList<Integer>(Arrays.asList( 48,  13,  54,  14,  53))));					//<LDVAR> ::= <LID> ":" <TIPO> ";" <LDVAR> ; 
		table.add(new Rule ( 13, 52,25,null));																		//<DCLVAR> ::= ю ; 
		table.add(new Rule ( 13, 52,26,null));																		//<DCLVAR> ::= ю ; 
		table.add(new Rule ( 14, 54, 28, new ArrayList<Integer>(Arrays.asList( 28))));								//<TIPO> ::= "INTEGER" ; 
		table.add(new Rule ( 15, 55, 25,new ArrayList<Integer>(Arrays.asList( 25,  19, 185,  56,  14, 186,  47,  14, 187,  55))));	//<DCLPROC> ::= "PROCEDURE" IDENT <DEFPAR> ";" <BLOCO> ";" <DCLPROC> ; 
		table.add(new Rule ( 16, 55, 26,null));																		//<DCLPROC> ::= ю ; 
		table.add( new Rule( 17, 56, 14, null ) );																//<DEFPAR> ::= ю ;
		table.add( new Rule( 18, 56, 17, new ArrayList<Integer>(Arrays.asList(  17, 188,  48,  13,  28,  18))));		//<DEFPAR> ::= "(" <LID> ":" "INTEGER" ")" ; 
		table.add( new Rule( 19, 57, 26, new ArrayList<Integer>(Arrays.asList(  26,  59,  58,  27))));				//<CORPO> ::= "BEGIN" <COMANDO> <REPCOMANDO> "END" ; 
		table.add( new Rule( 20, 58, 27, null ) );																//<REPCOMANDO> ::= ю 
		table.add( new Rule( 21, 58, 14, new ArrayList<Integer>(Arrays.asList(14,  59,  58) ) ) );				//<REPCOMANDO> ::= ";" <COMANDO> <REPCOMANDO> ; 
		table.add( new Rule( 22, 59,  19, new ArrayList<Integer>(Arrays.asList(19, 191,  12,  67, 192) ) ) );		//<COMANDO> ::= IDENT ":=" <EXPRESSAO> ; 
		table.add( new Rule( 23, 59,  26, new ArrayList<Integer>(Arrays.asList(57) ) ) );							//<COMANDO> ::= <CORPO> ; 
		table.add( new Rule( 24, 59,  14, null ) );																//<COMANDO> ::= ю ; 
		table.add( new Rule( 24, 59,  27, null ) );																//<COMANDO> ::= ю ; 
		table.add( new Rule( 24, 59,  33, null ) );																//<COMANDO> ::= ю ; 
		table.add( new Rule( 24, 59,  37, null ) );																//<COMANDO> ::= ю ; 
		table.add( new Rule( 25, 59,  30, new ArrayList<Integer>(Arrays.asList( 30,  19, 193,  60, 194) ) ) );		//<COMANDO> ::= "CALL" IDENT <PARAMETROS> ; 
		table.add( new Rule( 26, 60,  14, null ) );																//<PARAMETROS> ::= ю ; 
		table.add( new Rule( 26, 60,  27, null ) );																//<PARAMETROS> ::= ю ; 
		table.add( new Rule( 26, 60,  33, null ) );																//<PARAMETROS> ::= ю ; 
		table.add( new Rule( 26, 60,  37, null ) );																//<PARAMETROS> ::= ю ; 
		table.add( new Rule( 27, 60,  17, new ArrayList<Integer>(Arrays.asList(17,  67, 195,  61,  18) ) ) );		//<PARAMETROS> ::= "(" <EXPRESSAO> <REPPAR> ")" ; 
		table.add( new Rule( 28, 61, 18, null ) );																//<REPPAR> ::= ю ; 
		table.add( new Rule( 29, 61,  15, new ArrayList<Integer>(Arrays.asList( 15,  67, 195,  61) ) ) );			//<REPPAR> ::= "," <EXPRESSAO> <REPPAR> ; 
		table.add( new Rule( 30, 59,  31, new ArrayList<Integer>(Arrays.asList( 31,  67, 197,  32,  59,  62, 198) ) ) );		//<COMANDO> ::= "IF" <EXPRESSAO> "THEN" <COMANDO> <ELSEPARTE> ; 
		table.add( new Rule( 31, 62,  14, null ) );																//<ELSEPARTE> ::= ю ; 
		table.add( new Rule( 31, 62,  27, null ) );																//<ELSEPARTE> ::= ю ; 
		table.add( new Rule( 31, 62,  37, null ) );																//<ELSEPARTE> ::= ю ; 
		table.add( new Rule( 32, 62,  33, new ArrayList<Integer>(Arrays.asList(199,  33,  59) ) ) );				//<ELSEPARTE> ::= "ELSE" <COMANDO> ; 
		table.add( new Rule( 33, 59,  34, new ArrayList<Integer>(Arrays.asList( 34, 200,  67, 201,  35,  59, 202) ) ) ); 	//<COMANDO> ::= "WHILE" <EXPRESSAO> "DO" <COMANDO> ; 
		table.add( new Rule( 34, 59,  36, new ArrayList<Integer>(Arrays.asList( 36, 203,  59,  37,  67, 204) ) ) );		//<COMANDO> ::= "REPEAT" <COMANDO> "UNTIL" <EXPRESSAO> 
		table.add( new Rule( 35, 59, 38, new ArrayList<Integer>(Arrays.asList( 38, 205,  17,  63,  64,  18) ) ) );			//<COMANDO> ::= "READLN" "(" <VARIAVEL> <REPVARIAVEL> ")" ;
		table.add( new Rule( 36, 63, 19, new ArrayList<Integer>(Arrays.asList(19, 206) ) ) );						//<VARIAVEL> ::= IDENT ; 
		table.add( new Rule( 37, 64,  18, null ) );																//<REPVARIAVEL>::= ю ; 
		table.add( new Rule( 38, 64,  15, new ArrayList<Integer>(Arrays.asList(15,  63,  64) ) ) );				//<REPVARIAVEL>::= "," <VARIAVEL> <REPVARIAVEL> ; 
		table.add( new Rule( 39, 59, 39, new ArrayList<Integer>(Arrays.asList(39,  17,  65,  66,  18) ) ) );		//<COMANDO> ::= "WRITELN" "(" <ITEMSAIDA> <REPITEM> ")" ; 
		table.add( new Rule( 40, 65, 21, new ArrayList<Integer>(Arrays.asList(21, 207) ) ) );						//<ITEMSAIDA> ::= LITERAL ; 
		table.add( new Rule( 41, 65, 2, new ArrayList<Integer>(Arrays.asList(67, 208) ) ) );						//<ITEMSAIDA> ::= <EXPRESSAO> ; 
		table.add( new Rule( 41, 65, 3, new ArrayList<Integer>(Arrays.asList(67, 208) ) ) );						//<ITEMSAIDA> ::= <EXPRESSAO> ; 
		table.add( new Rule( 41, 65, 17, new ArrayList<Integer>(Arrays.asList(67, 208) ) ) );						//<ITEMSAIDA> ::= <EXPRESSAO> ; 
		table.add( new Rule( 41, 65, 19, new ArrayList<Integer>(Arrays.asList(67, 208) ) ) );						//<ITEMSAIDA> ::= <EXPRESSAO> ; 
		table.add( new Rule( 41, 65, 20, new ArrayList<Integer>(Arrays.asList(67, 208) ) ) );						//<ITEMSAIDA> ::= <EXPRESSAO> ; 
		table.add( new Rule( 41, 65, 42, new ArrayList<Integer>(Arrays.asList(67, 208) ) ) );						//<ITEMSAIDA> ::= <EXPRESSAO> ; 
		table.add( new Rule( 42, 66, 18, null ) );																//<REPITEM> ::= ю ; 
		table.add( new Rule( 43, 66, 15, new ArrayList<Integer>(Arrays.asList(15,  65,  66) ) ) );				//<REPITEM> ::= "," <ITEMSAIDA> <REPITEM> ; 
		table.add( new Rule( 44, 59, 45, new ArrayList<Integer>(Arrays.asList(45, 209,  67,  29,  74,  27, 210) ) ) );	//<COMANDO> ::= "CASE" <EXPRESSAO> "OF" <CONDCASE> "END" ; 
		table.add( new Rule( 45, 74, 20, new ArrayList<Integer>(Arrays.asList(20,  76,  13, 211,  59, 212,  75) ) ) );		//<CONDCASE> ::= INTEIRO <RPINTEIRO> ":" <COMANDO> <CONTCASE> ; 
		table.add( new Rule( 46, 76,  15, new ArrayList<Integer>(Arrays.asList(15, 213,  20,  76) ) ) );			//<RPINTEIRO> ::= "," INTEIRO <RPINTEIRO> ; 
		table.add( new Rule( 47, 76,  13, null ) );																//<RPINTEIRO> ::= ю ; 							
		table.add( new Rule( 48, 75,  27, null ) );																//<CONTCASE> ::= ю ; 
		table.add( new Rule( 49, 75,  14, new ArrayList<Integer>(Arrays.asList(14,  74) ) ) );					//<CONTCASE> ::= ";" <CONDCASE> ; 
		table.add( new Rule( 50, 59,  43, new ArrayList<Integer>(Arrays.asList(43,  19, 214,  12,  67, 215,  44,  67, 216,  35,  59, 217) ) ) );	//<COMANDO> ::= "FOR" IDENT ":=" <EXPRESSAO> "TO" <EXPRESSAO> "DO" <COMANDO> ; 
		table.add( new Rule( 51, 67, 2, new ArrayList<Integer>(Arrays.asList(69,  68) ) ) );						//<EXPRESSAO> ::= <EXPSIMP> <REPEXPSIMP> ; 
		table.add( new Rule( 51, 67, 3, new ArrayList<Integer>(Arrays.asList(69,  68) ) ) );						//<EXPRESSAO> ::= <EXPSIMP> <REPEXPSIMP> ; 
		table.add( new Rule( 51, 67, 17, new ArrayList<Integer>(Arrays.asList(69,  68) ) ) );						//<EXPRESSAO> ::= <EXPSIMP> <REPEXPSIMP> ; 
		table.add( new Rule( 51, 67, 19, new ArrayList<Integer>(Arrays.asList(69,  68) ) ) );						//<EXPRESSAO> ::= <EXPSIMP> <REPEXPSIMP> ; 
		table.add( new Rule( 51, 67, 20, new ArrayList<Integer>(Arrays.asList(69,  68) ) ) );						//<EXPRESSAO> ::= <EXPSIMP> <REPEXPSIMP> ; 
		table.add( new Rule( 51, 67, 42, new ArrayList<Integer>(Arrays.asList(69,  68) ) ) );						//<EXPRESSAO> ::= <EXPSIMP> <REPEXPSIMP> ; 
		table.add( new Rule( 52, 68,  14, null ) );																//<REPEXPSIMP> ::= ю ; 
		table.add( new Rule( 52, 68,  15, null ) );																//<REPEXPSIMP> ::= ю ; 
		table.add( new Rule( 52, 68,  18, null ) );																//<REPEXPSIMP> ::= ю ; 
		table.add( new Rule( 52, 68,  27, null ) );																//<REPEXPSIMP> ::= ю ; 
		table.add( new Rule( 52, 68,  29, null ) );																//<REPEXPSIMP> ::= ю ; 
		table.add( new Rule( 52, 68,  32, null ) );																//<REPEXPSIMP> ::= ю ; 
		table.add( new Rule( 52, 68,  33, null ) );																//<REPEXPSIMP> ::= ю ; 
		table.add( new Rule( 52, 68,  35, null ) );																//<REPEXPSIMP> ::= ю ; 
		table.add( new Rule( 52, 68,  37, null ) );																//<REPEXPSIMP> ::= ю ; 
		table.add( new Rule( 52, 68,  44, null ) );																//<REPEXPSIMP> ::= ю ; 
		table.add( new Rule( 53, 68,  6, new ArrayList<Integer>(Arrays.asList(6,  69, 218) ) ) );				//<REPEXPSIMP> ::= "=" <EXPSIMP> ;
		table.add( new Rule( 54, 68,  9, new ArrayList<Integer>(Arrays.asList(9,  69, 219) ) ) );				//<REPEXPSIMP> ::= "<" <EXPSIMP> ; 
		table.add( new Rule( 55, 68,  7, new ArrayList<Integer>(Arrays.asList(7,  69, 220) ) ) );				//<REPEXPSIMP> ::= ">" <EXPSIMP> ;
		table.add( new Rule( 56, 68, 8, new ArrayList<Integer>(Arrays.asList(8,  69, 221) ) ) );				//<REPEXPSIMP> ::= ">=" <EXPSIMP> ; 
		table.add( new Rule( 57, 68, 10, new ArrayList<Integer>(Arrays.asList(10,  69, 222) ) ) );				//<REPEXPSIMP> ::= "<=" <EXPSIMP> 
		table.add( new Rule( 58, 68, 11, new ArrayList<Integer>(Arrays.asList(11,  69, 223) ) ) );				//<REPEXPSIMP> ::= "<>" <EXPSIMP> ; 
		table.add( new Rule( 59, 69, 2, new ArrayList<Integer>(Arrays.asList(2,  71,  70) ) ) );				//<EXPSIMP> ::= "+" <TERMO> <REPEXP> ;
		table.add( new Rule( 60, 69, 3, new ArrayList<Integer>(Arrays.asList(3,  71, 224,  70) ) ) );			//<EXPSIMP> ::= "-" <TERMO> <REPEXP> ;
		table.add( new Rule( 61, 69,  17, new ArrayList<Integer>(Arrays.asList(71,  70) ) ) );						//<EXPSIMP> ::= <TERMO> <REPEXP> ; 
		table.add( new Rule( 61, 69,  19, new ArrayList<Integer>(Arrays.asList(71,  70) ) ) );						//<EXPSIMP> ::= <TERMO> <REPEXP> ; 
		table.add( new Rule( 61, 69,  20, new ArrayList<Integer>(Arrays.asList(71,  70) ) ) );						//<EXPSIMP> ::= <TERMO> <REPEXP> ; 
		table.add( new Rule( 61, 69,  42, new ArrayList<Integer>(Arrays.asList(71,  70) ) ) );						//<EXPSIMP> ::= <TERMO> <REPEXP> ; 
		table.add( new Rule( 62, 70,  2, new ArrayList<Integer>(Arrays.asList(2,  71, 225,  70) ) ) );			//<REPEXP> ::= "+" <TERMO> <REPEXP> ; 
		table.add( new Rule( 63, 70,  3, new ArrayList<Integer>(Arrays.asList(3,  71, 226,  70) ) ) );			//<REPEXP> ::= "-" <TERMO> <REPEXP> ; 
		table.add( new Rule( 64, 70, 40, new ArrayList<Integer>(Arrays.asList(40,  71, 227,  70) ) ) );			//<REPEXP> ::= "OR" <TERMO> <REPEXP> 
		table.add( new Rule( 65, 70, 6, null ) );																//<REPEXP> ::= ю ; 
		table.add( new Rule( 65, 70, 7, null ) );																//<REPEXP> ::= ю ; 
		table.add( new Rule( 65, 70, 8, null ) );																//<REPEXP> ::= ю ; 
		table.add( new Rule( 65, 70, 9, null ) );																//<REPEXP> ::= ю ; 
		table.add( new Rule( 65, 70, 10, null ) );																//<REPEXP> ::= ю ; 
		table.add( new Rule( 65, 70, 11, null ) );																//<REPEXP> ::= ю ; 
		table.add( new Rule( 65, 70, 14, null ) );																//<REPEXP> ::= ю ; 
		table.add( new Rule( 65, 70, 15, null ) );																//<REPEXP> ::= ю ; 
		table.add( new Rule( 65, 70, 18, null ) );																//<REPEXP> ::= ю ; 
		table.add( new Rule( 65, 70, 27, null ) );																//<REPEXP> ::= ю ; 
		table.add( new Rule( 65, 70, 29, null ) );																//<REPEXP> ::= ю ; 
		table.add( new Rule( 65, 70, 32, null ) );																//<REPEXP> ::= ю ; 
		table.add( new Rule( 65, 70, 33, null ) );																//<REPEXP> ::= ю ; 
		table.add( new Rule( 65, 70, 35, null ) );																//<REPEXP> ::= ю ; 
		table.add( new Rule( 65, 70, 37, null ) );																//<REPEXP> ::= ю ; 
		table.add( new Rule( 65, 70, 44, null ) );																//<REPEXP> ::= ю ; 
		table.add( new Rule( 66, 71, 17, new ArrayList<Integer>(Arrays.asList(73,  72) ) ) );						//<TERMO> ::= <FATOR> <REPTERMO> 
		table.add( new Rule( 66, 71, 19, new ArrayList<Integer>(Arrays.asList(73,  72) ) ) );						//<TERMO> ::= <FATOR> <REPTERMO> 
		table.add( new Rule( 66, 71, 20, new ArrayList<Integer>(Arrays.asList(73,  72) ) ) );						//<TERMO> ::= <FATOR> <REPTERMO> 
		table.add( new Rule( 66, 71, 42, new ArrayList<Integer>(Arrays.asList(73,  72) ) ) );						//<TERMO> ::= <FATOR> <REPTERMO> 
		table.add( new Rule( 67, 72, 2, null ) );																//<REPTERMO> ::= ю ; 
		table.add( new Rule( 67, 72, 3, null ) );																//<REPTERMO> ::= ю ; 
		table.add( new Rule( 67, 72, 6, null ) );																//<REPTERMO> ::= ю ; 
		table.add( new Rule( 67, 72, 7, null ) );																//<REPTERMO> ::= ю ; 
		table.add( new Rule( 67, 72, 8, null ) );																//<REPTERMO> ::= ю ; 
		table.add( new Rule( 67, 72, 9, null ) );																//<REPTERMO> ::= ю ; 
		table.add( new Rule( 67, 72, 10, null ) );																//<REPTERMO> ::= ю ; 
		table.add( new Rule( 67, 72, 11, null ) );																//<REPTERMO> ::= ю ; 
		table.add( new Rule( 67, 72, 14, null ) );																//<REPTERMO> ::= ю ; 
		table.add( new Rule( 67, 72, 15, null ) );																//<REPTERMO> ::= ю ; 
		table.add( new Rule( 67, 72, 18, null ) );																//<REPTERMO> ::= ю ; 
		table.add( new Rule( 67, 72, 27, null ) );																//<REPTERMO> ::= ю ; 
		table.add( new Rule( 67, 72, 29, null ) );																//<REPTERMO> ::= ю ; 
		table.add( new Rule( 67, 72, 32, null ) );																//<REPTERMO> ::= ю ; 
		table.add( new Rule( 67, 72, 33, null ) );																//<REPTERMO> ::= ю ; 
		table.add( new Rule( 67, 72, 35, null ) );																//<REPTERMO> ::= ю ; 
		table.add( new Rule( 67, 72, 37, null ) );																//<REPTERMO> ::= ю ; 
		table.add( new Rule( 67, 72, 40, null ) );																//<REPTERMO> ::= ю ; 
		table.add( new Rule( 67, 72, 44, null ) );																//<REPTERMO> ::= ю ; 
		table.add( new Rule( 68, 72, 4, new ArrayList<Integer>(Arrays.asList(4,  73, 228,  72) ) ) );			//<REPTERMO> ::= "*" <FATOR> <REPTERMO> ; 
		table.add( new Rule( 69, 72,  5, new ArrayList<Integer>(Arrays.asList(5,  73, 229,  72) ) ) );			//<REPTERMO> ::= "/" <FATOR> <REPTERMO> ; 
		table.add( new Rule( 70, 72,  41, new ArrayList<Integer>(Arrays.asList(41,  73, 230,  72) ) ) );			//<REPTERMO> ::= "AND" <FATOR> <REPTERMO> ;
		table.add( new Rule( 71, 73,  20, new ArrayList<Integer>(Arrays.asList(20, 231) ) ) );					//<FATOR> ::= INTEIRO ; 
		table.add( new Rule( 72, 73,  17, new ArrayList<Integer>(Arrays.asList(17,  67,  18) ) ) );				//<FATOR> ::= "(" <EXPRESSAO> ")" ; 
		table.add( new Rule( 73, 73,  42, new ArrayList<Integer>(Arrays.asList(42,  73, 232) ) ) );				//<FATOR> ::= "NOT" <FATOR> ; 
		table.add( new Rule( 74, 73,  19, new ArrayList<Integer>(Arrays.asList(233,  63) ) ) );					//<FATOR> ::= <VARIAVEL> ;


	}

	//PERCORRE TABELA DE REGRAS, SE  CODIGO NAO TERMINAL FOR IGUAL AO CODIGO TERMINAL,
	//E A REGRA NAO TENHA DERIVAЧOES VAZIAS, RETORNA A REGRA, SENAO RETORNA NULL
	public static Rule findRule(int NonTerminalCode, int terminalCode){
		for (Rule r:table){
			if(r.getNonTerminalCode()== NonTerminalCode && r.getTerminalCode() == terminalCode && r.getDerivations() != null)
				return r;			
		}
		return null;
	}

	//PERCORRE A TABELA DE REGRAS, SE O CODIGO NAO TERMINAL FOR IGUAL AO CODIGO TERMINAL,
	//E A REGRA NAO TENHA DERIVAЧеES RETORNA TRUE
	public static boolean checkIfAcceptEmpty(int NonTerminalCode, int terminalCode){
		for(Rule r:table){
			if(r.getNonTerminalCode()== NonTerminalCode && r.getTerminalCode() == terminalCode && r.getDerivations()==null){
				return true;
			}
		}
		return false;
	}



}
