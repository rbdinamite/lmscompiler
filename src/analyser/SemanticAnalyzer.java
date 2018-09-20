package analyser;

import java.util.LinkedList;

import brain.InstructionArea;
import brain.LiteralArea;
import brain.Hipotetica;
import errors.SemanticError;
import tables.Symbol;
import tables.SymbolsTable;
import tables.Token;

public class SemanticAnalyzer {
	SymbolsTable ST;

	String identifierType, identifierName, context;
	public static int currentLevel;
	public static int freePosition;
	public static int numberVariables, numberParameters;
	public static int displacement;
	public static int levelVariable, displacementVariable;
	public static int nivel;
	public static int index = 0;

	LinkedList<Integer> stackIf, stackWhile, stackRepeat, stackDSVT, stackDSVS, stackDSVF, stackProcedures, stackCase,
			stackFor;
	LinkedList<Symbol> stackSymbol;

	public static Hipotetica virtualMachine;
	public static InstructionArea IA;
	public static LiteralArea LA;
	public static int pos = 0;
	public static int count = 5;
	boolean hasParameters;
	Symbol currentConst, currentProcedure;
	public static LinkedList<String[]> instructions;

	@SuppressWarnings("static-access")
	public void execSemanticAction(int code, Token currentToken, Token lastToken) throws SemanticError {

		System.out.println("CODIGO ENVIADO PARA A ACAO SEMANTICA: [" + (code - 77) + "]");
		switch (code - 77) {

		// RECONHECENDO O NOME DO PROGRAMA
		case 100:
			// INSTANCIANDO A MAQUINA VIRTUAL (HIPOTETICA)
			virtualMachine = new Hipotetica();

			// DEFININDO LISTA DE INTRUCOES, MANIPULADA PELA CLASSE DE ANALISE SEMANTICA
			// CODIGO INTERMEDIARIO
			instructions = new LinkedList<String[]>();

			// INICIANDO AREA DE INSTRUCOES E LITERAIS DA MAQUINA HIPOTETICA
			IA = new InstructionArea();
			LA = new LiteralArea();
			Hipotetica.initializeIA(IA);
			Hipotetica.initializeLA(LA);

			// INICIANDO TABELA DE SIMBOLOS
			ST = new SymbolsTable();

			// INICIALIZANDO AS PILHAS
			stackIf = new LinkedList<Integer>();
			stackWhile = new LinkedList<Integer>();
			stackRepeat = new LinkedList<Integer>();
			stackProcedures = new LinkedList<Integer>();
			stackCase = new LinkedList<Integer>();
			stackFor = new LinkedList<Integer>();
			stackDSVT = new LinkedList<Integer>();
			stackDSVS = new LinkedList<Integer>();
			stackDSVF = new LinkedList<Integer>();

			// INICIANDO PILHA DE SIMBOLOS
			stackSymbol = new LinkedList<Symbol>();

			// INICIANDO NIVEL ATUAL
			currentLevel = 0;

			// APONTANDO PARA A PROXIMA POSICAO LIVRE DA TABELA DE SIMBOLOS
			freePosition = 1;

			// DEFININDO O DESCLOCAMENTO COM RELACAO A BASE
			displacement = 3;

			// DEFININDO O NUMERO DE VARIAVEIS DE UM BLOCO
			numberVariables = 0;

			// APONTANDO PARA A PROXIMA INSTRUCAO A SER GERADA
			IA.currentLine = 1;

			// APONTANDO PARA O PRIMEIRO PONTEIRO DA AREA DE LITERAIS
			LA.LIT = 1;
			break;

		// FINAL DE PROGRAMA - INSTRUCAO PARA
		case 101:
			// ADICIONANDO INSTRUCAO NA LISTA DA CLASSE
			instructions.add(new String[] { IA.currentLine + "", "PARA", "-", "-" });

			// ADICIONANDO INSTRUCAO NA LISTA DA MAQUINA VIRTUAL
			virtualMachine.insertIA(IA, 26, 0, 0);
			break;

		// AP�S DECLARA��O DE VARI�VEL - INSTRUCAO AMEN
		case 102:

			// DEFININDO O DESCLOCAMENTO - SEMPRE A PARTIR DE 3 (ESPACOS RESERVADOS DA
			// MAQUINA VITUAL)
			displacement = 3;

			// OPERANDO 2 RECEBE O DESCOLAMENTO + O NUMERO DE VARIAVEIS
			int operating2 = displacement + numberVariables;

			// ADICIONANDO INSTRUCAO NA LISTA DA CLASSE
			instructions.add(new String[] { IA.currentLine + "", "AMEN", "-", "" + (operating2) });

			// ADICIONANDO INSTRUCAO NA LISTA DA MAQUINA VIRTUAL
			virtualMachine.insertIA(IA, 24, 0, operating2);
			break;

		case 104:
			// VERIFICANDO SE O TIPO DO IDENTIFICADOR EH UMA VARIAVEL
			if (identifierType.equals("VAR")) {

				// ADICIONANDO IDENTIFICADOR A TABELA DE SIMBOLOS
				ST.insert(new Symbol(currentToken.getName(), "VAR", currentLevel, displacement, 0));

				// INCREMENTANDO O DESLOCAMENTO E O NUMERO DE VARIAVEIS
				displacement++;
				numberVariables++;
			}
			// VERIFICANDO SE O TIPO DO IDENTIFICADOR EH UM PARAMETRO
			else if (identifierType.equals("PAR")) {

				// ADICIONANDO IDENTIFICADOR A TABELA DE SIMBOLOS
				Symbol identifier = new Symbol(currentToken.getName(), "PAR", currentLevel, 0, 0);
				ST.insert(identifier);

				// ADICIONANDO IDENTIFICADOR A LISTA DE SIMBOLOS
				stackSymbol.push(identifier);

				// INCREMENTANDO O NUMERO DE PARAMETROS
				numberParameters++;
			}
			break;

		// RECONHECIDO NOME DE CONSTANTE EM DECLARACAO
		case 105:
			// ADICIONANDO IDENTIFICADOR A TABELA DE SIMBOLOS
			currentConst = new Symbol(currentToken.getName(), "CONST", currentLevel, 0, 0);
			ST.insert(currentConst);
			break;

		// RECONHECIDO VALOR DE CONSTANTE EM DECLARACAO
		case 106:
			// ADICIONANDO VALOR DA CONSTANTE NA TABELA DE SIMBOLOS
			currentConst.setGeneralA(Integer.parseInt(currentToken.getName()));
			break;

		// ALTERA TIPO DO IDENTIFICADOR PARA "VAR"
		case 107:
			identifierType = "VAR";
			numberVariables = 0;
			break;

		// AP�S NOME DE PROCEDURE, EM DECLARA��O FAZ
		case 108:

			// ADICIONANDO IDENTIFICADOR "PROC" A TABELA DE SIMBOLOS
			// INCREMENTA ONDE INICIA NO C�DIGO GERADO (LINHA NO CODIGO INTERMEDIARIO)
			currentProcedure = new Symbol(currentToken.getName(), "PROC", currentLevel, IA.currentLine + 1, 0);
			ST.insert(currentProcedure);

			// INCREMENTA O NIVEL ATUAL
			currentLevel++;

			// DEFINE O VALOR PADRAO DO DESLOCAMENTO E ZERA O NUMERO DE VARIAVEIS
			displacement = 3;
			numberVariables = 0;

			// DEFINE PROCEDURE SEM PARAMETROS
			hasParameters = false;
			numberParameters = 0;
			break;

		// APOS DECLARACAO DE PROCEDURE
		case 109:
			// VERIFICANDO SE A PROCEDURE TEM PARAMENTROS
			if (hasParameters) {

				// ADICIONANDO O NUMERO DE PARAMETROS DE UMA DETERMINADA PROCEDURE A TABELA DE
				// SIMBOLOS
				currentProcedure.setGeneralB(numberParameters);

				// ADICIONANDO CADA IDENTIFICADOR "PAR" A TABELA DE SIMBOLOS
				for (int i = 0; i < numberParameters; i++) {
					Symbol parameter = stackSymbol.pop();
					parameter.setGeneralA(-(numberParameters - i));
				}
			}

			// ADICIONANDO INSTRUCAO "DSVS" NA LISTA DA CLASSE
			instructions.add(new String[] { IA.currentLine + "", "DSVS", "-", "-" });

			// ADICIONANDO INSTRUCAO NA LISTA DA MAQUINA VIRTUAL
			virtualMachine.insertIA(IA, 19, 0, 0);

			// ARMAZENA O ENDERECO DA INSTRUCAO DE DESVIO E O NUMERO DE PARAMETROS
			stackProcedures.push(IA.currentLine - 1);
			stackProcedures.push(numberParameters);

			// ZERANDO O NUMERO DE PARAMETROS
			numberParameters = 0;

			break;

		// FIM DA PROCEDURE
		case 110:

			// REMOVENDO DA PILHA O NUMERO DE PARAMETROS E O ENDERECO DA INSTRUCAO DE DESVIO
			int parameter = stackProcedures.pop();
			int address = stackProcedures.pop();

			// ADICIONANDO INSTRUCAO "RETU" NA LISTA DA CLASSE
			instructions.add(new String[] { IA.currentLine + "", "RETU", "-", String.valueOf(parameter + 1) });

			// ADICIONANDO INSTRUCAO NA LISTA DA MAQUINA VIRTUAL
			virtualMachine.insertIA(IA, 1, 0, parameter + 1);

			// ATUALIZANDO A INSTRUCAO DE DESVIO NA LISTA DA CLASSE
			String[] deviation = instructions.get(address - 1);
			deviation[3] = String.valueOf(IA.currentLine);
			instructions.set(address - 1, deviation);

			// ATUALIZANDO A INSTRUCAO DE DESVIO NA LISTA DA MAQUINA VIRTUAL
			virtualMachine.updateIA(IA, address, 0, IA.currentLine);

			// REMOVENDO ITEM DA TABELA DE SIMBOLO COM O NIVEL ATUAL
			ST.delete(currentLevel);

			// DECREMENTANDO O NIVEL ATUAL
			currentLevel--;
			break;

		// DEFINE TIPO DO IDENTIFICADOR COMO PARAMETRO E INDICA QUE UM DETERMINADA
		// PROCEDURE TEM PARAMETRO
		case 111:
			identifierType = "PAR";
			hasParameters = true;
			break;

		// SALVA NOME DO IDENTIFICADOR
		case 112:
			identifierName = currentToken.getName();
			break;

		// VERIFICA SE IDENTIFICADOR FOI DECLARADO
		case 114:
			// CONSULTANDO O INDICE DO IDENTIFICADOR
			index = ST.search(currentToken.getName());

			// VERIFICANDO SE O IDENTIFICADOR ESTA NA TABELA DE SIMBOLOS
			if (index != -1) {

				// VERIFICANDO SE O IDENTIFICADOR EH UMA VARIAVEL
				if (!ST.getList().get(index).getCategory().equals("VAR")) {
					throw new SemanticError(identifierName + " nao eh uma variavel");
				} else {
					// SALVANDO O NIVEL DA VARIAVEL E SEU DESLOCAMENTO
					levelVariable = currentLevel - ST.getList().get(index).getLevel();
					displacementVariable = ST.getList().get(index).getGeneralA();
				}

			} else {
				throw new SemanticError("Variavel " + identifierName + " nao foi declarada");
			}
			break;
			
		// APOS EXPRESSAO EM ATRIBUICAO - INSTRUCAO ARMZ
		case 115:
			// ADICIONANDO INSTRUCAO "ARMZ" NA LISTA DA CLASSE
			instructions.add(new String[] { IA.currentLine + "", "ARMZ", levelVariable + "", displacementVariable + "" });
			
			// ADICIONANDO INSTRUCAO "ARMZ" NA LISTA DA MAQUINA VIRTUAL
			virtualMachine.insertIA(IA, 4, levelVariable, displacementVariable);
			break;

		// CHAMADA DE PROCEDURE 
		case 116:
			// CONSULTANDO O INDICE DO NOME DA PROCEDURE 
			index = ST.search(currentToken.getName());
			
			// VERIFICANDO SE O NOME DA PROCEDURE ESTA NA TABELA DE SIMBOLOS
			if (index != -1) {
				
				// VERIFICANDO SE O IDENTIFICADOR EH UMA PROCEDURE
				if (!ST.getList().get(index).getCategory().equals("PROC"))
					throw new SemanticError(currentToken.getName() + " nao eh uma procedure");
			} else {
				throw new SemanticError(currentToken.getName() + " nao foi declarada");
			}
			
			numberParameters = 0;
			break;

		case 117:
			index = ST.search(lastToken.getName());
			
//			for (int i = 0; i <= ST.getList().size() - 1; i++) {
//				System.out.println(ST.getList().get(i));
//				System.out.println(numberParameters);
//			}

			Symbol sim = ST.getList().get(ST.getList().size() - 1);
			if (sim.getGeneralB() != numberParameters)
				throw new SemanticError("numero de parametros invalido");
			// instructions.add(new String[]{IA.currentLine+"","CALL",(currentLevel -
			// sim.getLevel())+ "",sim.getGeneralA()+""});
			// virtualMachine.insertIA(IA, 25,currentLevel - sim.getLevel(),
			// sim.getGeneralA()) ;

			instructions.add(new String[] { IA.currentLine + "", "CALL", (currentLevel - pos) + "", count + "" });
			virtualMachine.insertIA(IA, 25, currentLevel - pos, count);
			count = 3;
			break;

		case 118:
			// numberParameters++;
			break;

		case 120:
			stackIf.push(IA.currentLine);
			instructions.add(new String[] { IA.currentLine + "", "DVSF", "-", "?" });
			virtualMachine.insertIA(IA, 20, -1, -1);

			break;

		case 121:
			int aux = stackIf.pop();
			deviation = instructions.get(aux - 1);
			deviation[3] = IA.currentLine + "";
			instructions.set(aux - 1, deviation);
			virtualMachine.updateIA(IA, aux, 0, IA.currentLine);
			break;

		case 122:
			aux = stackIf.pop();
			deviation = instructions.get(aux - 1);
			deviation[3] = (IA.currentLine + 1) + "";
			instructions.set(aux - 1, deviation);
			virtualMachine.updateIA(IA, aux, 0, IA.currentLine + 1);

			stackIf.push(IA.currentLine);
			instructions.add(new String[] { IA.currentLine + "", "DSVS", "-", "-" });
			virtualMachine.insertIA(IA, 19, 0, 0);

			break;

		case 123:
			stackWhile.push(IA.currentLine);
			break;

		case 124:
			instructions.add(new String[] { IA.currentLine + "", "DVSF", "-", "?" });
			stackWhile.push(IA.currentLine);
			virtualMachine.insertIA(IA, 20, 0, 0);

			break;

		case 125:
			aux = stackWhile.pop();
			virtualMachine.updateIA(IA, aux, 0, IA.currentLine + 1);

			deviation = instructions.get(aux - 1);
			deviation[3] = (IA.currentLine + 1) + "";
			instructions.set(aux - 1, deviation);

			aux = stackWhile.pop();
			instructions.add(new String[] { IA.currentLine + "", "DSVS", "-", "" + (aux) });
			virtualMachine.insertIA(IA, 19, 0, aux);
			break;

		case 126:
			stackRepeat.push(IA.currentLine);
			break;

		case 127:
			aux = stackRepeat.pop();
			instructions.add(new String[] { IA.currentLine + "", "DVSF", "-", aux + "" });
			virtualMachine.insertIA(IA, 20, 0, aux);
			break;

		case 128:
			context = "readln";
			break;

		case 129:
			if (context.equals("readln")) {
				index = ST.search(currentToken.getName());
				if (index != -1) {
					if (ST.getList().get(index).getCategory().equals("VAR")) {
						instructions.add(new String[] { IA.currentLine + "", "LEIT", "-", "-" });
						virtualMachine.insertIA(IA, 21, 0, 0);

						int nivel = ST.getList().get(index).getLevel() - currentLevel;
						int des = ST.getList().get(index).getGeneralA();
						instructions.add(new String[] { IA.currentLine + "", "ARMZ", nivel + "", des + "" });
						virtualMachine.insertIA(IA, 4, nivel, des);

					} else
						throw new SemanticError(currentToken.getName() + " n�o � uma vari�vel");
				}
			} else if (context.equals("EXP")) {
				index = ST.search(currentToken.getName());
				if (index != -1) {
					String cat = ST.getList().get(index).getCategory();
					if (cat.equals("PROC"))
						throw new SemanticError(
								"Erro Sem�ntico: " + currentToken.getName() + " n�o � vari�vel");
					else if (cat.equals("CONST")) {
						int ct = ST.getList().get(index).getGeneralA();
						instructions.add(new String[] { IA.currentLine + "", "CRCT", "-", ct + "" });
						virtualMachine.insertIA(IA, 3, 0, ct);
					} else {
						int difNivel = currentLevel - ST.getList().get(index).getLevel(),
								desl = ST.getList().get(index).getGeneralA();
						instructions.add(new String[] { IA.currentLine + "", "CRVL", difNivel + "", desl + "" });
						virtualMachine.insertIA(IA, 2, difNivel, desl);

					}

				}
			}
			break;

		case 130:
			instructions.add(new String[] { IA.currentLine + "", "IMPL", "0", (LA.LIT) + "" });
			virtualMachine.insertLA(LA, currentToken.getName());
			virtualMachine.insertIA(IA, 23, 0, LA.LIT - 1);
			break;

		case 131:
			instructions.add(new String[] { IA.currentLine + "", "IMPR", "-", "-" });
			virtualMachine.insertIA(IA, 22, 0, 0);
			break;

		case 132:

			break;

		case 133:

			break;

		case 134:

			break;

		case 135:

			break;

		case 136:

			break;

		case 137:
			index = ST.search(currentToken.getName());

			if (index == -1 || !ST.getList().get(index).getCategory().equals("VAR"))
				throw new SemanticError(
						currentToken.getName() + " n�o foi declarada ou n�o � nome de vari�vel");
			levelVariable = ST.getList().get(index).getLevel();
			break;

		case 138:
			displacementVariable = ST.getList().get(index).getGeneralA();
			instructions
					.add(new String[] { IA.currentLine + "", "ARMZ", levelVariable + "", displacementVariable + "" });
			virtualMachine.insertIA(IA, 4, levelVariable, displacementVariable);
			break;

		case 139: //
			stackFor.push(IA.currentLine);
			instructions.add(new String[] { IA.currentLine + "", "COPI", "-", "-" });
			virtualMachine.insertIA(IA, 28, 0, 1);
			nivel = ST.getList().get(index).getLevel() - currentLevel;
			displacementVariable = ST.getList().get(index).getGeneralA() - 1; // coloquei o -1 para o for
			instructions.add(new String[] { IA.currentLine + "", "CRVL", nivel + "", "" + displacementVariable });
			virtualMachine.insertIA(IA, 2, nivel, displacementVariable);
			instructions.add(new String[] { IA.currentLine + "", "CMIA", "-", "-" });
			virtualMachine.insertIA(IA, 18, 0, 0);

			instructions.add(new String[] { IA.currentLine + "", "DVSF", "-", "?" });
			stackFor.push(IA.currentLine);
			virtualMachine.insertIA(IA, 20, 0, 0);
			stackFor.push(index);

			break;

		case 140:
			index = stackFor.pop();
			nivel = ST.getList().get(index).getLevel() - currentLevel;
			displacementVariable = ST.getList().get(index).getGeneralA() - 1; // coloquei o -1 para o for
			instructions.add(new String[] { IA.currentLine + "", "CRVL", nivel + "", "" + displacementVariable });
			virtualMachine.insertIA(IA, 2, nivel, displacementVariable);
			instructions.add(new String[] { IA.currentLine + "", "CRCT", "-", "1" });
			virtualMachine.insertIA(IA, 3, 0, 1);
			instructions.add(new String[] { IA.currentLine + "", "SOMA", "-", "-" });
			virtualMachine.insertIA(IA, 5, 0, 0);
			instructions.add(new String[] { IA.currentLine + "", "ARMZ", nivel + "", displacementVariable + "" });
			virtualMachine.insertIA(IA, 4, nivel, displacementVariable);

			aux = stackFor.pop();
			virtualMachine.updateIA(IA, aux, -1, IA.currentLine + 1);

			deviation = instructions.get(aux - 1);
			deviation[3] = (IA.currentLine + 1) + "";
			instructions.set(aux - 1, deviation);

			aux = stackFor.pop();
			instructions.add(new String[] { IA.currentLine + "", "DSVS", "-", "" + (aux - 1) });
			virtualMachine.insertIA(IA, 19, 0, aux - 1);

			instructions.add(new String[] { IA.currentLine + "", "AMEN", "-", "-1" });
			virtualMachine.insertIA(IA, 24, 0, -1);
			break;

		case 141: // gera CMIG
			instructions.add(new String[] { IA.currentLine + "", "CMIG", "-", "-" });
			virtualMachine.insertIA(IA, 15, 0, 0);
			break;

		case 142: // CMME
			instructions.add(new String[] { IA.currentLine + "", "CMME", "-", "-" });
			virtualMachine.insertIA(IA, 13, 0, 0);
			break;

		case 143: // gera CMMA
			instructions.add(new String[] { IA.currentLine + "", "CMMA", "-", "-" });
			virtualMachine.insertIA(IA, 14, 0, 0);
			break;

		case 144: // gera CMIA
			instructions.add(new String[] { IA.currentLine + "", "CMIA", "-", "-" });
			virtualMachine.insertIA(IA, 18, 0, 0);
			break;

		case 145: // gera CMEI
			instructions.add(new String[] { IA.currentLine + "", "CMEI", "-", "-" });
			virtualMachine.insertIA(IA, 17, 0, 0);
			break;

		case 146: // gera CMDF
			instructions.add(new String[] { IA.currentLine + "", "CMDF", "-", "-" });
			virtualMachine.insertIA(IA, 16, 0, 0);
			break;

		case 148: // gera SOMA
			instructions.add(new String[] { IA.currentLine + "", "SOMA", "-", "-" });
			virtualMachine.insertIA(IA, 5, 0, 0);
			break;
		case 149: // gera SUBT
			instructions.add(new String[] { IA.currentLine + "", "SUBT", "-", "-" });
			virtualMachine.insertIA(IA, 6, 0, 0);
			break;
		case 151: // gera MULT
			instructions.add(new String[] { IA.currentLine + "", "MULT", "-", "-" });
			virtualMachine.insertIA(IA, 7, 0, 0);
			break;
		case 152: // gera DIVD
			instructions.add(new String[] { IA.currentLine + "", "DIVD", "-", "-" });
			virtualMachine.insertIA(IA, 8, 0, 0);

			break;
		case 154: // gera CRCT
			int ct = Integer.parseInt(currentToken.getName());
			instructions.add(new String[] { IA.currentLine + "", "CRCT", "-", ct + "" });
			virtualMachine.insertIA(IA, 3, 0, ct);
			break;
		case 155:
			// gera instru��o NEGA
			instructions.add(new String[] { IA.currentLine + "", "NEGA", "-", "-" });
			virtualMachine.insertIA(IA, 10, 0, 0);
			break;
		case 156:
			context = "EXP";
			break;
		default:
			break;
		}
	}

}
