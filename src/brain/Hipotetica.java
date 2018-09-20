package brain;

import javax.swing.JOptionPane;

/**
 * CLASSE RESPONSAVEL POR MANIPULAR A CLASSE HIPOTETICA
 * 
 * @author TheStaff
 */
public class Hipotetica {
	public static int MaxInst = 1000;
	public static int MaxList = 30;
	public static int b; // base do segmento
	public static int topo; // topo da pilha da base de dados
	public static int p; // apontador de instru��es
	public static int l; // primeiro operando
	public static int a; // segundo operando
	public static int numberVariables; // n�mero de vari�veis;
	public static int numberParameters; // n�mero de par�metros;
	public static int operator; // codigo da instru��o
	public static int k; // segundo operando
	public static int i;
	public static int num_impr;
	public static int[] S = new int[1000];

	/**
	 * CONSTRUTOR DA CLASSE
	 */
	public Hipotetica() {
		numberVariables = numberParameters = num_impr = 0;
	}

	/**
	 * INICIALIZA A AREA DE INSTRUCOES.
	 */
	public static void initializeIA(InstructionArea IA) {
		for (int i = 0; i < MaxInst; i++) {
			IA.items[i].code = -1;
			IA.items[i].option1 = -1;
			IA.items[i].option2 = -1;
		}
		IA.currentLine = 0;
	}

	/**
	 * INICIALIZA A AREA DE LITERAIS
	 */
	public static void initializeLA(LiteralArea LA) {

		for (int i = 0; i < MaxList; i++) {
			LA.items[i] = "";
			LA.LIT = 0;
		}
	}

	/**
	 * INCLUI UMA INSTRUCAO NA AREA DE INSTRUCOES UTILIZADA PELA MAQUINA HIPOTETICA.
	 */
	public boolean insertIA(InstructionArea IA, int c, int o1, int o2) {
		boolean aux;
		if (IA.currentLine >= MaxInst) {
			aux = false;
		} else {
			aux = true;
			IA.items[IA.currentLine].code = c;

			if (o1 != -1) {
				IA.items[IA.currentLine].option1 = o1;
			}

			if (c == 24) {
				IA.items[IA.currentLine].option2 = o2;
			}

			if (o2 != -1) {
				IA.items[IA.currentLine].option2 = o2;
			}

			IA.currentLine = IA.currentLine + 1;
		}
		return aux;
	}

	/**
	 * Altera uma instru��o da �rea de instru��es utilizada pela m�quina hipot�tica.
	 */
	public static void updateIA(InstructionArea IA, int s, int o1, int o2) {

		if (o1 != -1) {
			IA.items[s].option1 = o1;
		}

		if (o2 != -1) {
			IA.items[s].option2 = o2;
		}
	}

	/**
	 * Inclui um literal na �rea de literais utilizada pela m�quina hipot�tica.
	 */
	public static boolean insertLA(LiteralArea LA, String literal) {
		boolean aux;
		if (LA.LIT >= MaxList) {
			aux = false;
		} else {
			aux = true;
			LA.items[LA.LIT] = literal;
			LA.LIT = LA.LIT + 1;
		}
		return aux;
	}

	/**
	 * Utilizada para determinar a base.
	 */
	public static int Base() {// determina base
		int b1;
		b1 = b;
		while (l > 0) {
			b1 = S[b1];
			l = l - 1;
		}
		return b1;
	}

	/**
	 * Respons�vel por interpretar as instru��es.
	 */
	public static void Interpreta(InstructionArea IA, LiteralArea LA) {

		topo = 0;
		b = 0; // registrador base
		p = 0; // aponta proxima instru��o
		S[1] = 0; // SL
		S[2] = 0; // DL
		S[3] = 0; // RA
		operator = 0;

		String leitura;

		while (operator != 26) {// Enquanto instru��o diferente de Pare

			operator = IA.items[p].code;

			l = IA.items[p].option1;
			a = IA.items[p].option2;
			p = p + 1;

			switch (operator) {
			case 1:// RETU
				p = S[b + 2];
				topo = b - a;
				b = S[b + 1];
				break;

			case 2:// CRVL
				topo = topo + 1;
				S[topo] = S[Base() + a];
				break;

			case 3: // CRCT
				topo = topo + 1;
				S[topo] = a;
				break;

			case 4:// ARMZ
				S[Base() + a] = S[topo];
				topo = topo - 1;
				break;

			case 5:// SOMA
				S[topo - 1] = S[topo - 1] + S[topo];
				topo = topo - 1;
				break;

			case 6:// SUBT
				S[topo - 1] = S[topo - 1] - S[topo];
				topo = topo - 1;
				break;

			case 7:// MULT
				S[topo - 1] = S[topo - 1] * S[topo];
				topo = topo - 1;
				break;

			case 8: // DIVI
				if (S[topo] == 0) {
					JOptionPane.showMessageDialog(null, "Divis�o por zero.", "Erro durante a execu��o",
							JOptionPane.ERROR_MESSAGE);
					S[topo - 1] = S[topo - 1] / S[topo];
					topo = topo - 1;
				}
				break;

			case 9:// INVR
				S[topo] = -S[topo];
				break;

			case 10: // NEGA
				S[topo] = 1 - S[topo];
				break;

			case 11:// CONJ
				if ((S[topo - 1] == 1) && (S[topo] == 1)) {
					S[topo - 1] = 1; // A no material impresso est� como "1" e aqui estava como "-1"
				} else {
					S[topo - 1] = 0;
				}
				topo = topo - 1;
				break;

			case 12:// DISJ
				if ((S[topo - 1] == 1 || S[topo] == 1)) {
					S[topo - 1] = 1;
				} else {
					S[topo - 1] = 0;
				}
				topo = topo - 1;
				break;

			case 13:// CMME
				if (S[topo - 1] < S[topo]) {
					S[topo - 1] = 1;
				} else {
					S[topo - 1] = 0;
				}
				topo = topo - 1;
				break;

			case 14:// CMMA
				if (S[topo - 1] > S[topo]) {
					S[topo - 1] = 1;
				} else {
					S[topo - 1] = 0;
				}
				topo = topo - 1;
				break;

			case 15:// CMIG
				if (S[topo - 1] == S[topo]) {
					S[topo - 1] = 1;
				} else {
					S[topo - 1] = 0;
				}
				topo = topo - 1;
				break;

			case 16:// CMDF
				if (S[topo - 1] != S[topo]) {
					S[topo - 1] = 1;
				} else {
					S[topo - 1] = 0;
				}
				topo = topo - 1;
				break;

			case 17:// CMEI
				if (S[topo - 1] <= S[topo]) {
					S[topo - 1] = 1;
				} else {
					S[topo - 1] = 0;
				}
				topo = topo - 1;
				break;

			case 18:// CMAI
				if (S[topo - 1] >= S[topo]) {
					S[topo - 1] = 1;
				} else {
					S[topo - 1] = 0;
				}
				topo = topo - 1;
				break;

			case 19:// DSVS
				p = a;
				break;

			case 20:// DSVF
				if (S[topo] == 0) {
					p = a;
					// topo=topo-1; //A no material impresso esta linha est� fora do "if"!
				}
				topo = topo - 1;

				break;

			case 21:// LEIT
				topo = topo + 1;
				leitura = JOptionPane.showInputDialog(null, "Informe o valor:", "Leitura",
						JOptionPane.QUESTION_MESSAGE);
				// System.out.print("Leia: "); A
				(S[topo]) = Integer.parseInt(leitura); // problema aqui A
				break;

			case 22:// IMPR
				JOptionPane.showMessageDialog(null, "" + S[topo], "Informa��o", JOptionPane.INFORMATION_MESSAGE);
				// System.out.println(S[topo]); A
				topo = topo - 1;
				break;

			case 23:// IMPRLIT
				if (a >= LA.LIT) {
					JOptionPane.showMessageDialog(null, "Literal n�o encontrado na �rea dos literais.",
							"Erro durante a execu��o", JOptionPane.ERROR_MESSAGE);
					// System.out.println("ERRO >> Literal nao encontrada na area"); A
				} else {
					JOptionPane.showMessageDialog(null, "" + LA.items[a], "Informa��o",
							JOptionPane.INFORMATION_MESSAGE);
					// System.out.println(LA.items[a]); A
					// LA.LIT++;
				}
				break;

			case 24:// AMEM
				topo = topo + a;
				break;

			case 25:// CALL
				S[topo + 1] = Base();
				S[topo + 2] = b;
				S[topo + 3] = p;
				b = topo + 1;
				p = a;
				break;

			case 26:
				// System.exit(0);
				// PARA
				break;

			case 27:
				// NADA
				break;

			case 28:// COPI
				topo = topo + 1;
				S[topo] = S[topo - 1];
				break;

			case 29:// DSVT
				if (S[topo] == 1) {
					p = a;
					// topo=topo-1; //A no material impresso esta linha est� fora do "if"!
				}
				topo = topo - 1;
			}// fim do case
		} // fim do while
	}// fim do procedimento interpreta

	public static void print() {
		System.out.println(
				"#################################################################################################################################");
		System.out.println(" ___________________________________________");
		System.out.println("|_____|_____________________________________|"); // 5 - 37 espacos respectivamente
		System.out.println("|  " + i + "  |                  " + topo + "                  |");
		System.out.println("|  " + i + "  |                  " + operator + "                  |");
		System.out.println(
				"#################################################################################################################################");

		System.out.println("Base = " + b);
		System.out.println("Topo = " + topo);
		System.out.println("Apontador = " + p);
		System.out.println("1 operando = " + l);
		System.out.println("2 operando = " + a);
		System.out.println("Num variaveis = " + numberVariables);
		System.out.println("Num parametros = " + numberParameters);
		System.out.println("Cod. Instrucao = " + operator);
		System.out.println("2 operando (k) = " + k);
		System.out.println("num_impr = " + num_impr);

	}
}
