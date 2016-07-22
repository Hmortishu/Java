/*
 * Cria��o da base de dados
 * create table if not exists Cliente
  				(id_cliente smallint not null auto_increment,
				nomeCliente varchar(255) not null,
				moradaCliente varchar(255) not null,
				cidadeCliente varchar(255) not null,
				teleCliente varchar(9) not null,
				emailCliente varchar(255) not null,
				nifCliente varchar(255) not null,
				estadoCliente ENUM('Activo' , 'Inactivo'),
				primary key(id_cliente))
				engine = innodb;
	create table if not exists Avaria
				(id_avaria smallint not null auto_increment,
				id_cliente smallint not null,
				tipoAvaria varchar(255) not null,
				descricaoAvaria varchar(255) not null,
				precoAvaria varchar(255) not null,
				estadoAvaria ENUM('Por resolver' , 'Resolvido'),
				primary key(id_avaria))
				engine = innodb;
	create table if not exists Equipamentos
				(id_equipamentos smallint not null auto_increment,
				id_colaboradores smallint not null,
				tipoEquipamento varchar(255) not null,
				localEquipamento varchar(255) not null,
				primary key(id_equipamentos))
				engine = innodb;
	create table if not exists Utilizadores
				(id_utilizador smallint not null auto_increment,
				loginUtilizador varchar(25) not null,
				passUtilizador varchar(25) not null,
				nomeUtilizador varchar(255) not null,
				moradaUtilizador varchar(255) not null,
				cidadeUtilizador varchar(255) not null,
				ndUtilizador varchar(255) not null,
				tipoUtilizador ENUM('IT','Colaboradores'),
				primary key(id_utilizador))
				engine = innodb;
 * 
 * 
 * 
*/
package ufcd;

import java.awt.EventQueue;
import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.sql.*;
import javax.swing.text.Document;

public class Client extends JFrame implements ActionListener {

	 /**
	 * variaveis que importao ai proprio programa
	 */
	private static final long serialVersionUID = 1L;
	String tipo, it = "IT";
	/*
	 * Variaveis para o MenuBar
	 */
	JMenuBar menuBar;
	JMenu mnOpcoes;
	JMenuItem mniNUtilizador, mniNAvaria;
	
	/*
	 * Variaveis para o painel Login
	 */
	JPanel login;
	
	JLabel lblNome, lblPass;
	JTextField tfNome, tfPass;
	JButton btLogin;
	
	
	/*
	 * Variaveis para o conteudo tabbed
	 */
	JTabbedPane conteudo;
	//Painel Cliente
	JPanel cliente;
	
	//Painel Avarias
	JPanel avarias, selAvarias;
	JLabel lbl;
	JScrollPane panAvaria;
	JTable tabelaAvaria;
	JComboBox<Object> cbProavarias;
	JTextField tfSel;
	JButton btSel;
	String [] titulo = {"Nr de Cliente","Nome","Nif","Codigo Postal","Email","Morada"};
	String [][] Data = new String[150][6];
	
	//Painel NovoUtilizador
	JPanel novoUtilizador;
	JLabel lblNULogin, lblNUPass, lblNUTipo, lblNUNome, lblNUNident, lblNUMorada, lblNUCidade;
	JTextField tfNULogin, tfNUPass, tfNUNome, tfNUNident, tfNUMorada, tfNUCidade; 
	JButton btNURegisto, btNUCancelar;
	JComboBox<Object> cBNUTipo;
	
	//Painel NovaAvaria
	JPanel novaAvaria;
	JLabel lbNATipo, lblNADesc, lblNAPreco, lblNAData, lblNACliente;
	JTextField tfNATipo, tfNADesc, tfNAPreco, tfNAData, tfNACliente;
	JTextArea taNADesc;
	JButton btNARegisto, btNACancelar;
	
	
	/*
	 * Variaveis para a conec��o
	 */
	static Connection con = null;	 
	 
	
	
	public Client() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		
		menuBar = new JMenuBar();
		
		mnOpcoes = new JMenu("Op��es");
		menuBar.add(mnOpcoes);
		
		mniNUtilizador = new JMenuItem("Novo Utilizador");
		mniNUtilizador.addActionListener(this);
		mnOpcoes.add(mniNUtilizador);
		
		mniNAvaria = new JMenuItem("Nova Avaria");
		mniNAvaria.addActionListener(this);
		mnOpcoes.add(mniNAvaria);
		
		//Painel LOGIN
		login = new JPanel();
		login.setLayout(null);
		
		lblNome = new JLabel("Nome de Utilizador");
		lblNome.setHorizontalAlignment(SwingConstants.CENTER);
		lblNome.setFont(new Font("Arial", Font.PLAIN, 17));
		lblNome.setBounds(525,150, 150, 20);
		lblNome.setLabelFor(tfNome);
		login.add(lblNome);
		
		tfNome = new JTextField();
		tfNome.setHorizontalAlignment(SwingConstants.CENTER);
		tfNome.setBounds(535, 180, 130, 20);
		tfNome.setColumns(10);
		login.add(tfNome);
		
		lblPass = new JLabel("Palavra-Passe");
		lblPass.setHorizontalAlignment(SwingConstants.CENTER);
		lblPass.setFont(new Font("Arial", Font.PLAIN, 17));
		lblPass.setBounds(535, 250, 130, 20);
		lblPass.setLabelFor(tfPass);
		login.add(lblPass);
		
		tfPass = new JTextField();
		tfPass.setHorizontalAlignment(SwingConstants.CENTER);
		tfPass.setBounds(535, 280, 130, 20);
		tfPass.setColumns(10);
		login.add(tfPass);
		
		btLogin = new JButton("Entrar!");
		btLogin.setBounds(535, 337, 130, 35);
		btLogin.addActionListener(this);
		login.add(btLogin);
		
		getContentPane().add(login);
		///Fim do Login
		
		//Inicio do tabed conteudo.
		conteudo = new JTabbedPane();
		
		/*
		 *Avarias
		*/
		avarias = new JPanel();
		avarias.setLayout(new BorderLayout());
		conteudo.addTab("Avarias", avarias);
	
		selAvarias = new JPanel();
		lbl = new JLabel("Prourar por");
		selAvarias.add(lbl);
		cbProavarias = new JComboBox<Object>();
		cbProavarias.setModel(new DefaultComboBoxModel<Object>(new String[] {"Cliente", "Colaborador", "Tipo de Equipamento", "Mes"}));
		selAvarias.add(cbProavarias);
		tfSel = new JTextField();
		tfSel.setColumns(10);
		selAvarias.add(tfSel);
		btSel = new JButton("Procurar");
		selAvarias.add(btSel);
		avarias.add(selAvarias, BorderLayout.NORTH);
		
		tabelaAvaria = new JTable(Data, titulo);
		panAvaria = new JScrollPane(tabelaAvaria);
		
		avarias.add(panAvaria, BorderLayout.CENTER);
		
		 //CLientes 
		 
		cliente = new JPanel();
		conteudo.addTab("Clientes",cliente);
		
		//o painel de registo Novo Utilizador
		novoUtilizador = new JPanel();
		novoUtilizador.setLayout(null);
		
		lblNULogin = new JLabel("Login de Utilizador");
		lblNULogin.setBounds(171, 104, 150, 20);
		lblNULogin.setHorizontalAlignment(SwingConstants.CENTER);
		lblNULogin.setFont(new Font("Arial", Font.PLAIN, 17));
		novoUtilizador.add(lblNULogin);
		
		tfNULogin = new JTextField();
		tfNULogin.setBounds(181, 136, 130, 20);
		tfNULogin.setHorizontalAlignment(SwingConstants.CENTER);
		tfNULogin.setColumns(10);
		novoUtilizador.add(tfNULogin);
		
		
		lblNUPass = new JLabel("Palavra-Passe");
		lblNUPass.setBounds(181, 191, 130, 20);
		lblNUPass.setHorizontalAlignment(SwingConstants.CENTER);
		lblNUPass.setFont(new Font("Arial", Font.PLAIN, 17));
		novoUtilizador.add(lblNUPass);
		
		tfNUPass = new JTextField();
		tfNUPass.setBounds(181, 222, 130, 20);
		tfNUPass.setHorizontalAlignment(SwingConstants.CENTER);
		tfNUPass.setColumns(10);
		novoUtilizador.add(tfNUPass);
		
		lblNUTipo = new JLabel("Tipo de Utilizador");
		lblNUTipo.setHorizontalAlignment(SwingConstants.CENTER);
		lblNUTipo.setFont(new Font("Arial", Font.PLAIN, 17));
		lblNUTipo.setBounds(171, 283, 150, 20);
		novoUtilizador.add(lblNUTipo);
		
		cBNUTipo = new JComboBox<Object>();
		cBNUTipo.setModel(new DefaultComboBoxModel<Object>(new String[] {"Colaboradores", "IT"}));
		cBNUTipo.setMaximumRowCount(2);
		cBNUTipo.setBounds(181, 314, 130, 20);
		novoUtilizador.add(cBNUTipo);
		
		lblNUNome = new JLabel("Nome do Utilizador");
		lblNUNome.setHorizontalAlignment(SwingConstants.CENTER);
		lblNUNome.setFont(new Font("Arial", Font.PLAIN, 17));
		lblNUNome.setBounds(858, 109, 150, 14);
		novoUtilizador.add(lblNUNome);
		
		tfNUNome = new JTextField();
		tfNUNome.setBounds(883, 136, 112, 20);
		tfNUNome.setColumns(10);
		novoUtilizador.add(tfNUNome);
		
		lblNUNident = new JLabel("Nr de Identifica\u00E7\u00E3o");
		lblNUNident.setHorizontalAlignment(SwingConstants.CENTER);
		lblNUNident.setFont(new Font("Arial", Font.PLAIN, 17));
		lblNUNident.setBounds(858, 196, 150, 14);
		novoUtilizador.add(lblNUNident);
		
		tfNUNident = new JTextField();
		tfNUNident.setBounds(883, 222, 112, 20);
		tfNUNident.setColumns(10);
		novoUtilizador.add(tfNUNident);
		
		lblNUMorada = new JLabel("Morada utilizador");
		lblNUMorada.setHorizontalAlignment(SwingConstants.CENTER);
		lblNUMorada.setFont(new Font("Arial", Font.PLAIN, 17));
		lblNUMorada.setBounds(858, 284, 144, 19);
		novoUtilizador.add(lblNUMorada);
		
		tfNUMorada = new JTextField();
		tfNUMorada.setBounds(852, 314, 182, 20);
		tfNUMorada.setColumns(10);
		novoUtilizador.add(tfNUMorada);

		lblNUCidade = new JLabel("Cidade utilizador");
		lblNUCidade.setFont(new Font("Arial", Font.PLAIN, 17));
		lblNUCidade.setBounds(870, 373, 125, 14);
		novoUtilizador.add(lblNUCidade);
		
		tfNUCidade = new JTextField();
		tfNUCidade.setBounds(858, 398, 150, 20);
		tfNUCidade.setColumns(10);
		novoUtilizador.add(tfNUCidade);
		
		btNURegisto = new JButton("Registar Utilizador");
		btNURegisto.setBounds(525, 586, 150, 30);
		btNURegisto.addActionListener(this);
		novoUtilizador.add(btNURegisto);	
		
		btNUCancelar = new JButton("Cancelar");
		btNUCancelar.setBounds(550, 620 ,100, 30);
		btNUCancelar.addActionListener(this);
		novoUtilizador.add(btNUCancelar);	
		/*
		 * Novas avarias
		 */
		novaAvaria = new JPanel();	
		novaAvaria.setLayout(null);
		
		lbNATipo = new JLabel("Tipo de equipamento");
		lbNATipo.setHorizontalAlignment(SwingConstants.CENTER);
		lbNATipo.setFont(new Font("Arial", Font.PLAIN, 17));
		lbNATipo.setBounds(510, 50, 180, 20);
		novaAvaria.add(lbNATipo);
		
		tfNATipo = new JTextField();
		tfNATipo.setHorizontalAlignment(SwingConstants.CENTER);
		tfNATipo.setBounds(510, 75, 180, 20);
		tfNATipo.setColumns(10);
		novaAvaria.add(tfNATipo);
		
		lblNADesc = new JLabel("Descricao da Avaria");
		lblNADesc.setHorizontalAlignment(SwingConstants.CENTER);
		lblNADesc.setFont(new Font("Arial", Font.PLAIN, 17));
		lblNADesc.setBounds(510, 120, 180, 20);
		novaAvaria.add(lblNADesc);
		
		taNADesc = new JTextArea();
		taNADesc.setLineWrap(true);
		taNADesc.setColumns(150);
		taNADesc.setBounds(490, 145, 240, 90);
		novaAvaria.add(taNADesc);
		
		lblNAPreco = new JLabel("Preco");
		lblNAPreco.setHorizontalAlignment(SwingConstants.CENTER);
		lblNAPreco.setFont(new Font("Arial", Font.PLAIN, 17));
		lblNAPreco.setBounds(565, 275, 70, 20);
		novaAvaria.add(lblNAPreco);
		
		tfNAPreco = new JTextField();
		tfNAPreco.setHorizontalAlignment(SwingConstants.CENTER);
		tfNAPreco.setBounds(560, 300, 85, 20);
		tfNAPreco.setColumns(10);
		novaAvaria.add(tfNAPreco);
				
		lblNACliente = new JLabel("Cliente");
		lblNACliente.setBounds(570,340,60,20);
		lblNACliente.setFont(new Font("Arial", Font.PLAIN, 17));
		novaAvaria.add(lblNACliente);
		
		tfNACliente = new JTextField();
		tfNACliente.setPlaceh
		tfNACliente.setHorizontalAlignment(SwingConstants.CENTER);
		tfNACliente.setBounds(560, 380, 80, 20);
		novaAvaria.add(tfNACliente);
		
		lblNAData = new JLabel("Data");
		lblNAData.setHorizontalAlignment(SwingConstants.CENTER);
		lblNAData.setFont(new Font("Arial", Font.PLAIN, 17));
		lblNAData.setBounds(565, 450, 70, 20);
		novaAvaria.add(lblNAData);
		
		tfNAData = new JTextField();
		tfNAData.setHorizontalAlignment(SwingConstants.CENTER);
		tfNAData.setBounds(560, 475, 85, 20);
		tfNAData.setColumns(10);
		novaAvaria.add(tfNAData);
		
		btNARegisto = new JButton("Registar Avaria");
		btNARegisto.setFont(new Font("Arial", Font.PLAIN, 17));
		btNARegisto.addActionListener(this);
		btNARegisto.setBounds(515, 555, 170, 45);
		novaAvaria.add(btNARegisto);
		
		btNACancelar = new JButton("Cancelar");
		btNACancelar.setBounds(550, 620 ,100, 30);
		btNACancelar.addActionListener(this);
		novaAvaria.add(btNACancelar);	
		
		
		setResizable(false);
		setBounds(100, 100, 1200, 750);
	}


	public static void main(String[] args) throws SQLException 
	{
		//Criar();
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() 
			{
				try {
					Client frame = new Client();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == btLogin)
		{
			try {
				Login(tfNome.getText(), tfPass.getText());
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		if(e.getSource() == mniNUtilizador)
		{
			if(tipo.equals("IT"))
			{
				this.setJMenuBar(null);
				this.remove(conteudo);
				getContentPane().add(novoUtilizador);
				this.doLayout();
				this.validate();
				//repaint();
				//novoUtilizador.validate();
			}
			else
			{
				JOptionPane.showMessageDialog(this,
				    "Esta op��o apenas se encontra disponivel para o IT",
				    "Erro de Acesso",
				    JOptionPane.ERROR_MESSAGE);
			}
		}
		if(e.getSource() == btNURegisto)
		{
			try {
				NovoUtilizador(tfNULogin.getText(), tfNUPass.getText());
				this.remove(novoUtilizador);
				this.setJMenuBar(menuBar);
				getContentPane().add(conteudo);
				conteudo.addTab("Avarias", avarias);
				conteudo.addTab("Clientes",cliente);
				conteudo.doLayout();
				conteudo.validate();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		if(e.getSource() == btNUCancelar)
		{
			this.remove(novoUtilizador);
			this.setJMenuBar(menuBar);
			this.add(conteudo);
			conteudo.addTab("Avarias", avarias);
			conteudo.addTab("Clientes",cliente);
			conteudo.doLayout();
			conteudo.validate();
		}
		
		if(e.getSource() == mniNAvaria)
		{
			this.setJMenuBar(null);
			this.remove(menuBar);
			this.remove(conteudo);
			getContentPane().add(novaAvaria);
			this.doLayout();
			this.validate();
		}
		
		if(e.getSource() == btNACancelar)
		{
			this.remove(novaAvaria);
			this.setJMenuBar(menuBar);
			this.add(conteudo);
			conteudo.addTab("Avarias", avarias);
			conteudo.addTab("Clientes",cliente);
			conteudo.doLayout();
			conteudo.validate();
		}
	}
	
	/*
	 * Codigo para a cria��o de um novo utilizador
	 */
	public void NovoUtilizador(String N, String P) throws SQLException
	{
		if ( tfNULogin.getText().trim().length() == 0 || tfNUPass.getText().trim().length() == 0 || tfNUNome.getText().trim().length() == 0 || tfNUCidade.getText().trim().length() == 0 || tfNUNident.getText().trim().length() == 0 || tfNUMorada.getText().trim().length() == 0)
		{
			JOptionPane.showMessageDialog(this,
			    "Todos os campos devem estar preenchidos",
			    "Erro no Registo",
			    JOptionPane.ERROR_MESSAGE);
			return;
		}
		getContentPane().add(novoUtilizador);
		Conect();//Abir a conec�ao
		Statement st = null;
		ResultSet rs = null;
		st = con.createStatement();
		String str_QUERY = "SELECT id_utilizador FROM Utilizadores where loginUtilizador like '"+ N +"';" ;
		rs = st.executeQuery(str_QUERY);
		if(rs.next())
		{
			//custom title, warning icon
			JOptionPane.showMessageDialog(this,
			    "Esse utilizador ja se encontra registado",
			    "Erro no Registo",
			    JOptionPane.WARNING_MESSAGE);
		}
		else
		{
			String login = "";
			String pass = "";
			String nome = "";
			String morada = "";
			String cidade = "";
			String nrIdent = "";
			String tipo = "";
			login = tfNULogin.getText();
			pass = tfNUPass.getText();
			nome = tfNUNome.getText();
			morada = tfNUMorada.getText();
			cidade = tfNUCidade.getText();
			nrIdent = tfNUNident.getText();
			tipo = cBNUTipo.getSelectedItem().toString();
			String Query = "INSERT INTO utilizadores(loginUtilizador, passUtilizador, nomeUtilizador, moradaUtilizador, cidadeUtilizador, ndUtilizador, tipoUtilizador) VALUES (?,?,?,?,?,?,?);";
			PreparedStatement prep;
			
			prep = con.prepareStatement(Query);
			prep.setString(1, login);
			prep.setString(2, pass);
			prep.setString(3, nome);
			prep.setString(4, morada);
			prep.setString(5, cidade);
			prep.setString(6, nrIdent);
			prep.setString(7, tipo);
			prep.executeUpdate();
			}
		con.close();
	}
	
	/*
	 * Codigo para verificar o login.
	 */
	public void Login(String N, String P) throws SQLException
	{
		Conect();//Abir a conec�ao
		Statement st = null;
		ResultSet rs = null;
		st = con.createStatement();
	    String str_QUERY = "SELECT id_utilizador, tipoUtilizador FROM Utilizadores where loginUtilizador like '"+ N +"' AND passUtilizador like'"+ P +"';";
	    rs = st.executeQuery(str_QUERY);//executa a query
	    //se houver resultados apaga o painel login, fecha a conec�ao.
		if(rs.next())
		{
			tipo = rs.getString(2);
			System.out.println(tipo);
			System.out.println(it);
			login.setVisible(false);
			con.close();
			con = null;
			getContentPane().add(conteudo);
			this.remove(login);
			this.setJMenuBar(menuBar);
		}
		else
		{
			JOptionPane.showMessageDialog(this,
				    "O Login de utilizador ou a Palavra-passe estao errados, ou nao estao registados",
				    "Erro no Login",
				    JOptionPane.WARNING_MESSAGE);
			con.close();
			con = null;
		} 
	}
	
	
		
	public void Conect() throws SQLException
	{
		String drive = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://127.0.0.1:3306/Ticket";
		try {
		     Class.forName(drive);
		     con = DriverManager.getConnection(url, "root","");
		     if (con != null)
		       System.out.println("\n Sucesso na liga��o a BD Dados \n");
		 }catch( ClassNotFoundException e) {e.printStackTrace();}
	}
}