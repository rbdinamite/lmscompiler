package brain;

/**
 * CLASSE RESPONSAVEL POR MANIPULAR A AREA DE INSTRUCOES
 * @author TheStaff
 */

public class InstructionArea {
	public Types items[]= new Types[1000];
	public int currentLine;
	
   /**
   * CONSTRUTOR SEM PARAMETROS.
   * TODOS OS ATRIBUTOS SAO INICIALIZADOS COM VALORES PADROES.
   */
	public InstructionArea(){
		for(int i=0; i<1000; i++){
			items[i]=new Types();
		}
	}
	
}
