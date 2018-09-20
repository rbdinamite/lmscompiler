package view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;

import analyser.SemanticAnalyzer;
import analyser.LexicalAnalyzer;
import analyser.SyntaticAnalyzer;
import brain.Hipotetica;
import errors.LexicalError;
import errors.SemanticError;
import errors.SyntaticError;
import tables.ParsingTable;
import tables.Token;


@SuppressWarnings("serial")
public class Main extends JFrame implements KeyListener{
	public static JTextArea txt;
	private JScrollPane scroll, paneTable;
	private JFileChooser fileChooser;
	private JPanel panel, panelLateral, panelMessage;
	private JTable table;
	private DefaultTableModel modelToken, modelCode;
	private JTextArea txtMessage;
	private JScrollPane js ;
	private LinkedList<Token> listTokens;

	public Main(){
		initialize();
	}		
	
	public static void main(String[] args) {
		new Main();
	}

	// INICIANDO A INTERFACE
	private void initialize(){	
		ParsingTable.initialize();
		txt = new JTextArea();	
		scroll = new JScrollPane(txt);		
		setSize(940, 700);
		setTitle("Compilador");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);	
		txt.addKeyListener(this);
		panel = new JPanel(new BorderLayout());

		// DEFININDO OS MENUS E A TELA INICIAL
		JMenuBar menuBar = new JMenuBar();
		this.setJMenuBar(menuBar);
		JMenu menuFile = new JMenu("Arquivo");
		menuBar.add(menuFile);
		JMenuItem menuOpen = new JMenuItem("Abrir");
		menuOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {			
					//CRIA UM NOVO FILECHOOSER
					fileChooser = new JFileChooser();
					
					//ABRE A DIALOG PARA ESCOLHER O ARQUIVO
					int i = fileChooser.showOpenDialog(null);
					if(i != JFileChooser.APPROVE_OPTION)
						return;		
					File file = fileChooser.getSelectedFile();
					if (!file.exists()) {
						return;
					}
					
					//CARREGA O ARQUIVO ABERTO
					BufferedReader br = new BufferedReader(new FileReader(file));
					StringBuffer bufSaida = new StringBuffer();

					String linha;
					//ADICIONA LINHA A LINHA O STRINGBUFFER DE SAIDA
					while( (linha = br.readLine()) != null ){
						//ADICIONA O TEXTO DA LINHA E QUEBRA A LINHA
						bufSaida.append(linha + "\n");
					}
					//FECHA O ARQUIVO ABERTO
					br.close();
					
					//CARREGA O TEXTO DO ARQUIVO NO EDITOR
					txt.setText(bufSaida.toString());
					//LIMPANDO A TABELA
					while(table.getRowCount() > 0){
						modelToken.removeRow(0);
					}
					txtMessage.setText("");
				} catch (IOException e1) {	
					System.out.println("Ocorreu um erro ao abrir o arquivo. Erro: [" + e1.getMessage() + "]");
				} catch (NullPointerException e2){
					System.out.println("Ocorreu um erro ao abrir o arquivo. Erro: [" + e2.getMessage() + "]");
				}
			}
		});
		menuFile.add(menuOpen);
		JSeparator separator = new JSeparator();
		menuFile.add(separator);
		
		JMenuItem menuExit = new JMenuItem("Sair");
		menuExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		menuFile.add(menuExit);
		
		JMenu menuCompiler = new JMenu("Compilador");
		menuBar.add(menuCompiler);
		JMenuItem menuLexical = new JMenuItem("Lexico");
		menuLexical.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//CHAMANDO A FUNCAO PARA REALIZAR A ANALISE LEXICA
				lexicaLAnalisys();			}
		});
		menuCompiler.add(menuLexical);
		
		JMenuItem menuSyntatic = new JMenuItem("Sintatico");
		menuSyntatic.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//CHAMA A FUNCAO PARA REALIZAR A ANALISE SINTATICA
				syntaticAnalisys(false);
			}
		});
		menuCompiler.add(menuSyntatic);
		
		JMenuItem menuSemantic= new JMenuItem("Semantico");
		menuSemantic.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//CHAMA A FUNCAO PARA REALIZAR A ANALISE SEMANTICA
				syntaticAnalisys(true);
			}
		});
		menuCompiler.add(menuSemantic);

		JMenuItem menuExecute = new JMenuItem("Executar");
		menuExecute.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				// CHAMANDO METODO PARA ANALISE SINTATICA
				syntaticAnalisys(true);
				Hipotetica.Interpreta(SemanticAnalyzer.IA, SemanticAnalyzer.LA);
				
			}
		});
		
		JSeparator separator01 = new JSeparator();
		menuCompiler.add(separator01);
		menuCompiler.add(menuExecute);

		panelMessage = new JPanel(new BorderLayout());
		panelMessage.setBorder(BorderFactory.createTitledBorder("Mensagens"));
		txtMessage = new JTextArea(5,30);
		txtMessage.setEditable(false);
		js = new JScrollPane(txtMessage);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		panelLateral = new JPanel(new BorderLayout());
		
		//TABELA DE TOKENS
		modelToken = new DefaultTableModel(new String[][]{},new String[]{"Codigo","Token","Descricao"});
		
		//TABELA DE CODIGO GERADO PELA MAQUINA HIPOTETICA
		modelCode = new DefaultTableModel(new String[][]{},new String[]{"Codigo","Instrucao","Operando 1","Operando 2"});
		table = new JTable(modelToken);
		table.setEnabled(false);

		paneTable = new JScrollPane(table);

		panelLateral.add(scroll, BorderLayout.CENTER);
		panelLateral.add(paneTable,BorderLayout.EAST);
		panel.add(panelLateral, BorderLayout.CENTER);
		panelMessage.add(js,BorderLayout.CENTER);
		panel.add(panelMessage, BorderLayout.SOUTH);

		setContentPane(panel);
		setVisible(true);		
	}

	public JTextArea gettxt() {
		return txt;
	}
	public void settxt(JTextArea txt) {
		Main.txt = txt;
	}

	@SuppressWarnings("static-access")
	public void keyPressed(KeyEvent event) {		
		if(event.isControlDown()){			
		 if(event.getKeyCode() == event.VK_L)
			 lexicaLAnalisys();
		 else if(event.getKeyCode() == event.VK_S)
			 syntaticAnalisys(false);

		}
	}
	
	private boolean lexicaLAnalisys(){
		String content = txt.getText()+"\n"; 
		
		//DECLARA LISTA DE TOKENS
		listTokens = new LinkedList<Token>();		
		LexicalAnalyzer analyzer = new LexicalAnalyzer(content);
		
		//DEFINE O MODELO DA TABELA PARA TOKENS
		table.setModel(modelToken);
		
		//LIMPA TABELA DE TOKENS
		while(table.getRowCount() > 0)
			modelToken.removeRow(0);
		
		//INSTANCIANDO A CLASSE TOKEN
		Token token;
	
		try {
			while((token = analyzer.nextToken()) != null){
				listTokens.add(token);
				modelToken.addRow(token.toArray());
			}
			txtMessage.setText("Analise lexica concluida com sucesso");
			return true;
		
		} catch (LexicalError e) {
			if(e.isComment()){
				int res = txt.getText().lastIndexOf("(*");
				txt.select(res, res + e.getIndex());
			}else if(e.isLiteral()){
				int res = txt.getText().lastIndexOf('"');
					txt.select(res, res + e.getIndex());
			}
			else{
				txt.select(e.getIndex(), e.getIndex() + e.getLenght());
			}
			txtMessage.setText(e.getMessage());
			
			return false;
		}
	}
	
	private void syntaticAnalisys(boolean value){
		txtMessage.append("CHEGUEI NESSA PORRA 1!");

		// 1 - VERIFICANDO SE ANALISE LEXICA FOI EFETUADA COM SUCESSO
		if(lexicaLAnalisys()){
			
			//INSTANCIANDO A CLASSE PARA ANALISE SINTATICA
			SyntaticAnalyzer syntatic = new SyntaticAnalyzer();
			
			try {				
				if (syntatic.syntaticAnalisys(listTokens, value)) {
					txtMessage.append("\nAnalise Sintatica concluida com sucesso");
					
					if(value){
						txtMessage.append("\nAnalise Semantica concluida com sucesso");
						
						//DEFINE O MODELO DA TABELA PARA CODIGO INTERMEDIARIO
						table.setModel(modelCode);
						
						//LIMPA TABELA
						while(table.getRowCount() > 0){
							modelCode.removeRow(0);					
						}
						
						//PERCORRE AS INSTRUCOES GERADAS NA ANALISE SEMANTICA E ADICIONA NA TABELA
						for(String[] instruction:SemanticAnalyzer.instructions){
							modelCode.addRow(instruction);
						}
					}
				}
			} catch (SyntaticError e) {
				txtMessage.setText(e.getMessage());
			} catch (SemanticError e){
				txtMessage.setText(e.getMessage());
		}}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub	
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
