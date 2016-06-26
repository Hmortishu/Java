

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

public class Ciisco extends JFrame implements ActionListener, ItemListener {
	private static final long serialVersionUID = 1L;
	//Global variables;
	
	//contar tempo
	double segundos = 0;
	//panel da calculadora
	JPanel calc;
	//tabela
	DefaultTableModel tableModel;
	JTable table;
	JTextArea ta;
	
	JTextField tfTotal, tfAddress, tfMascara, tfHostName,
				tfConsole, tfAdmin, tfTelnet;
	JTextField tfRouterHostname, tfRouterTelnet, tfRouterAdmin, tfRouterConsole;
	JTextField tfVlanAdmin, tfAdministrator, tfMascaraAdmin;
	
	JTextField [][] tfRouterIpAddress = new JTextField[2][4];
	JComboBox [][] comboBoxRouter = new JComboBox [2][4];
	JCheckBox [] cbRouterInterface = new JCheckBox[4];
	ButtonGroup [] grupo1 = new ButtonGroup[4];
	JRadioButton [][] rbRouterInterface = new JRadioButton [4][2];
	
	//para router -> interfaces wan
	JCheckBox [] cbRouterWanActivo = new JCheckBox[2];
	JComboBox [][] comboBoxRouterWanTipo = new JComboBox[3][2];
	JTextField [] tfRouterWanIpAddress = new JTextField[2];
	JRadioButton [][] rbRouterWanClockRate = new JRadioButton[2][2];
	ButtonGroup [] grupo2 = new ButtonGroup [2];
	
	//Para router -> subnetting
	JCheckBox [] cbRouterSubnetting = new JCheckBox [5];
	JTextField [] tfRouterSubnetting = new JTextField[5];
	JComboBox [] comboBoxRouterSubnetting = new JComboBox[5];
	
	JCheckBox [] cbInterface = new JCheckBox[7];
	JComboBox [][] ComboBoxList = new JComboBox[2][7];
	ButtonGroup [] group = new ButtonGroup[7];
	JRadioButton [][] radioButton = new JRadioButton[7][3];
	
	//butoes para calculadora
	JButton [] bt = new JButton[3];
	String [] btName = {"Calcular", "Apagar", "Sair"};
	//headers da tabela
	String [] tableHeaders = {
			"Grupo", "Vlan", "Hosts", "Dim", 
			"Rede", "Inicio", "Gateway", 
			"BroadCast", "Cidr", "Mascara", "Obs"
	};
	String [] comboBoxStrType = {"int F0/0", "int F0/1", "int F0/2", "int F0/3", "int F0/4"};
	String [] comboBoxStrVlan = {"Vlan 1", "Vlan 10", "Vlan 20", "Vlan 30", "Vlan 40", "Vlan 50"};
	String [] SerialPortsStr = {"int Se0/0", "int Se0/1", "int Se1/0", "int Se1/1", "int Se2/0", "int Se2/1"};
	String [] radioButtonStr = {"Access", "Trunk", "NA"};
	String [] protocolosStr = {"RIP", "RIPv2", "OSPF", "EIGRP"};
	String [] MascarasStr = {"255.255.255.0", "255.255.255.128", "255.255.255.192", "255.255.255.224", 
							"255.255.255.240", "255.255.255.248", "255.255.255.252", "255.255.255.254"};
	String [] yesOrNo = {"Sim", "Não"};
	
	//outputs
	String switchCodeHeader = "";
	String routerCodeHeader = "/****************************************************/"
			+ "\n/********************Router************************/"
			+ "\n/****************************************************/\n";
	String routerCodeConf = "";
	String switchCodeConf = "";
	String routerCode = "";
	String switchCode = "";
	
	//Verificar se alguma configuração mudou
	Boolean switchChange = false;
	Boolean routerChange = false;
	Boolean routerShowCode = false;
	Boolean switchShowCode = false;
	Boolean waitBruh = false;
	Boolean showHeader = false;
	//tamanho do Object
	int objSize = 0;
	
	//Vector <Object>Data = new Vector<Object>();
	
	/*
	 * THREADS 
	 */
	public class CountsTime extends Thread 
	{
		public void run()
		{
			while(true)
			{
				try {
					Thread.sleep(1000);
				}
				catch(Exception e) {}
				if(waitBruh)
				{
					try
					{
						Thread.sleep(7000);
						waitBruh = false;
					}
					catch(Exception e) {}
				}
				//ta.setText(Integer.toString(segundos));
				segundos++;
				//atualizar todos os campos
				if(segundos == 1)
				{
					routerCodeConf = "";
					switchCodeConf = "";
					routerCodeHeader = "";
					switchCodeHeader = "";
					switchToCode();
					routerToCode();
					ta.setText("");
					ta.append(switchCodeHeader);
					ta.append(switchCodeConf);
					ta.append(switchCode);
					ta.append(routerCodeHeader);
					ta.append(routerCodeConf);
					ta.append(routerCode);
					
					segundos = 0;
				}
				else
					continue;
			}
		}
	}
	
	
	/*
	 * metodo para formatação de configuração de switch
	 */
	public void switchToCode()
	{
		//Colocar
		
		//verificar se os coises estão acionados
		switchCode = "";
		//verificar se existe algo nas textBoxes. Se existir, então deixar a variavel showHeader
		//em true, para caso não haver interfaces selecionadas, ele mostrar à mesma toda a informação
		// formatada
		if(!tfHostName.getText().isEmpty())
			showHeader = true;
		else if(!tfConsole.getText().isEmpty())
			showHeader = true;
		else if(!tfTelnet.getText().isEmpty())
			showHeader = true;
		else if(!tfAdmin.getText().isEmpty())
			showHeader = true;
		
		for(int i = 0; i < 7; i++)
		{
			if(cbInterface[i].isSelected())
			{
				showHeader = false;
				switchCodeConf = "en\nconf t\n\n";
				switchCodeHeader = "/****************************************************/"
						+ "\n/********************Switch*************************/"
						+ "\n/****************************************************/\n";
					
				String tmp = ComboBoxList[0][i].getSelectedItem().toString();
				switchCode += tmp + "\n";
				String aux = ComboBoxList[1][i].getSelectedItem().toString();
				switchCode += aux + "\n";
				switchCode += "exit\n\n";
				
				for(int j = 0; j < 3; j++)
				{
					if(radioButton[i][j].isSelected())
					{
						if(j == 0)
						{
							aux = "sw mode access";
							switchCode += tmp + "\n";
							switchCode += aux + "\n";
							switchCode += "sw access " + ComboBoxList[1][i].getSelectedItem().toString() + "\n\n";
						}	
						else if(j == 1)
						{
							aux = "sw mode trunk";
							switchCode += tmp + "\n";
							switchCode += aux + "\n\n";
						}	
						else
						{
							aux ="";
							continue;
						}
					}
				}
				if(i == 6)
					switchCode += "do wr";
			}		
		}
		//se nenhum interface estiver selecionado
		if(showHeader)
		{
			showHeader = false;
			routerCodeConf = "en\nconf t\n\n";
			switchCodeConf = "en\nconf t\n\n";
			switchCodeHeader = "/****************************************************/"
					+ "\n/********************Switch*************************/"
					+ "\n/****************************************************/\n";
			//passwords e admins
			if(!tfHostName.getText().isEmpty())
				switchCode += "hostname " + tfHostName.getText() + "\n\n";
			if(!tfConsole.getText().isEmpty())
				{
					switchCode += "line con 0\n";
					switchCode += "password " + tfConsole.getText() + "\n";
					switchCode += "login\n";
					switchCode += "exit\n\n";
				}
			//esperar alguns segundos antes de atualizar
			if(KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner() == ta)
				waitBruh = true;
				
				
			if(!tfTelnet.getText().isEmpty())
			{
				switchCode += "line vty 0 4\n";
				switchCode += "password " + tfTelnet.getText() + "\n";
				switchCode += "login\n";
				switchCode += "exit\n\n";
			}
			if(!tfAdmin.getText().isEmpty())
				switchCode += "ena secret " + tfAdmin.getText() + "\n";
		}
		//fazer exatamente a mesma coisa
		else
		{
			//passwords e admins
			if(!tfHostName.getText().isEmpty())
				switchCode += "hostname " + tfHostName.getText() + "\n\n";
			if(!tfConsole.getText().isEmpty())
				{
					switchCode += "line con 0\n";
					switchCode += "password " + tfConsole.getText() + "\n";
					switchCode += "login\n";
					switchCode += "exit\n\n";
				}
			//esperar alguns segundos antes de atualizar
			if(KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner() == ta)
				waitBruh = true;
				
				
			if(!tfTelnet.getText().isEmpty())
			{
				switchCode += "line vty 0 4\n";
				switchCode += "password " + tfTelnet.getText() + "\n";
				switchCode += "login\n";
				switchCode += "exit\n\n";
			}
		}	
	}
	
	/*
	 * Transform router conf to code
	 */
	public void routerToCode ()
	{	
		//fazer um loop sobre a area de subnetting para ver se houve alterações
		// se por ventura houver alterações, então apresentamos as devidas alterações
		routerCode = "";
		showHeader = false;
		if(!tfRouterHostname.getText().isEmpty())
			showHeader = true;
		else if(!tfRouterConsole.getText().isEmpty())
			showHeader = true;
		else if(!tfRouterAdmin.getText().isEmpty())
			showHeader = true;
		else if(!tfRouterTelnet.getText().isEmpty())
			showHeader = true;
		for(int i = 0; i < 5; i++)
		{
			if(cbRouterSubnetting[i].isSelected())
			{
				routerCodeHeader = "/****************************************************/"
								+ "\n/********************Router************************/"
								+ "\n/****************************************************/\n";
				routerCodeConf = "en\nconf t\n\n";
				
				showHeader = false;
				routerCode += "int fa0/4." + (i + 1) + "0\n";
				routerCode += "encapsulation dot1q " + (i + 1) + "0\n";
				routerCode += "ip add " + tfRouterSubnetting[i].getText() + " " + comboBoxRouterSubnetting[i].getSelectedItem().toString() + "\n";
			}
		}
		if(showHeader)
		{
			showHeader = false;
			routerCodeHeader = "/****************************************************/"
					+ "\n/********************Router************************/"
					+ "\n/****************************************************/\n";
			routerCodeConf = "en\nconf t\n\n";
			//passwords e admins
			if(!tfRouterHostname.getText().isEmpty())
				routerCode += "hostname " + tfRouterHostname.getText() + "\n\n";
			if(!tfRouterConsole.getText().isEmpty())
			{
				routerCode += "line con 0\n";
				routerCode += "password " + tfRouterConsole.getText() + "\n";
				routerCode += "login\n";
				routerCode += "exit\n\n";
			}
			//esperar alguns segundos antes de atualizar
			if(KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner() == ta)
				waitBruh = true;
				
				
			if(!tfRouterTelnet.getText().isEmpty())
			{
				routerCode += "line vty 0 4\n";
				routerCode += "password " + tfRouterTelnet.getText() + "\n";
				routerCode += "login\n";
				routerCode += "exit\n\n";
			}
			if(!tfRouterAdmin.getText().isEmpty())
				routerCode += "ena secret " + tfRouterAdmin.getText() + "\n";
			
		}
		//fazer exatamente a mesma coisa, mas sem header
		else
		{
			if(!tfRouterHostname.getText().isEmpty())
				routerCode += "hostname " + tfRouterHostname.getText() + "\n\n";
			if(!tfRouterConsole.getText().isEmpty())
			{
				routerCode += "line con 0\n";
				routerCode += "password " + tfRouterConsole.getText() + "\n";
				routerCode += "login\n";
				routerCode += "exit\n\n";
			}
			//esperar alguns segundos antes de atualizar
			if(KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner() == ta)
				waitBruh = true;
				
				
			if(!tfRouterTelnet.getText().isEmpty())
			{
				routerCode += "line vty 0 4\n";
				routerCode += "password " + tfRouterTelnet.getText() + "\n";
				routerCode += "login\n";
				routerCode += "exit\n\n";
			}
			if(!tfRouterAdmin.getText().isEmpty())
				routerCode += "ena secret " + tfRouterAdmin.getText() + "\n";
		}
		for(int i = 0; i < 2; i++)
		{
			if(showHeader)
			{
				routerCodeHeader = "/****************************************************/"
						+ "\n/********************Router************************/"
						+ "\n/****************************************************/\n";
				routerCodeConf = "en\nconf t\n\n";
			}
			if(cbRouterWanActivo[i].isSelected())
			{
				routerCode += "\n";
				routerCode += comboBoxRouterWanTipo[0][i].getSelectedItem().toString() + "\n";
				routerCode += "ip add " + tfRouterWanIpAddress[i].getText() + "\n";
				routerCode += "exit\n\n";
					
				//se for protocolo RIP
				if(comboBoxRouterWanTipo[2][i].getSelectedItem().toString().equals("RIP"))
				{
					routerCode += "router rip\n";
					routerCode += "no auto-summary\n";
					routerCode += "passive interface default\n";
					routerCode += "no passive interface " + comboBoxRouterWanTipo[0][i].getSelectedItem().toString() + "\n";
					routerCode += "address " + tfRouterWanIpAddress[i].getText() + "\n";
					routerCode += "exit\n\n";
				}
				//se for protocolo RIP2
				else if(comboBoxRouterWanTipo[2][i].getSelectedItem().toString().equals("RIPv2"))
				{
					routerCode += "router rip\n";
					routerCode += "version 2\n";
					routerCode += "no auto-summary\n";
					routerCode += "passive interface default\n";
					routerCode += "no passive interface " + comboBoxRouterWanTipo[0][i].getSelectedItem().toString() + "\n";
					routerCode += "address " + tfRouterWanIpAddress[i].getText() + "\n";
					routerCode += "exit\n\n";
				}
			}
		}
	}
	
	public Ciisco () 
	{
		//title
		super("Cisco 4 you!");
		
		//depois tenho que adicionar os menus!
		
		JTabbedPane pane = new JTabbedPane();
		
		/*
		 * area da calculadora
		 */
		calc = new JPanel();
		pane.addTab("Calculadora", calc);
		JLabel lbTotal, lbAddress, lbMascara;
		//adicionar membros à zona norte do panel
		lbTotal = new JLabel("Total");
		calc.add(lbTotal, BorderLayout.NORTH);
		tfTotal = new JTextField(5);
		calc.add(tfTotal, BorderLayout.NORTH);
		
		lbAddress = new JLabel("Address");
		calc.add(lbAddress, BorderLayout.NORTH);
		tfAddress = new JTextField(15);
		calc.add(tfAddress, BorderLayout.NORTH);
		
		lbMascara = new JLabel("Máscara");
		calc.add(lbMascara, BorderLayout.NORTH);
		tfMascara = new JTextField(15);
		calc.add(tfMascara, BorderLayout.NORTH);
		
		//tabela(panel centro)
		tableModel = new DefaultTableModel(tableHeaders, 0);
		table = new JTable(tableModel);
		table.setPreferredScrollableViewportSize(new Dimension(1080,450));
		//table.setFillsViewportHeight(true);
		JScrollPane scroll = new JScrollPane(table);
		
		//adicionar tabbedpanes à frame
		 calc.add(scroll, BorderLayout.CENTER);
		 
		 //zona sul da calculadora
		 for(int i = 0; i < 3; i++)
		 {
			 bt[i] = new JButton(btName[i]);
			 bt[i].addActionListener(this);
			 calc.add(bt[i], BorderLayout.SOUTH);
		 }
		//fim da area da calculadora	 
		 
		 /*
		  * area do switch 
		  */
		JPanel switchArea = new JPanel();
		switchArea.setLayout(null);
		 
		pane.addTab("Switch", switchArea);
		
		JLabel lbHostName = new JLabel("Hostname");
		lbHostName.setBounds(20,20,lbHostName.getPreferredSize().width, lbHostName.getPreferredSize().height);
		lbHostName.setForeground(Color.blue);
		switchArea.add(lbHostName);
		
		tfHostName = new JTextField(15);
		tfHostName.setBounds(85,20,tfHostName.getPreferredSize().width, tfHostName.getPreferredSize().height);
		switchArea.add(tfHostName);
		
		//labels
		JLabel lbPasswordTitle = new JLabel("Password");
		lbPasswordTitle.setBounds(20,90,lbPasswordTitle.getPreferredSize().width, lbPasswordTitle.getPreferredSize().height);
		lbPasswordTitle.setForeground(Color.red);
		switchArea.add(lbPasswordTitle);
		
		JLabel lbConsole = new JLabel("Console");
		lbConsole.setBounds(25,120,lbConsole.getPreferredSize().width, lbConsole.getPreferredSize().height);
		lbConsole.setForeground(Color.blue);
		switchArea.add(lbConsole);
		
		JLabel lbAdmin = new JLabel("Admin");
		lbAdmin.setBounds(25, 150, lbAdmin.getPreferredSize().width, lbAdmin.getPreferredSize().height);
		lbAdmin.setForeground(Color.blue);
		switchArea.add(lbAdmin);
		
		JLabel lbTelnet = new JLabel("Telnet");
		lbTelnet.setBounds(25, 180, lbTelnet.getPreferredSize().width, lbTelnet.getPreferredSize().height);
		lbTelnet.setForeground(Color.blue);
		switchArea.add(lbTelnet);
		
		//textfields for password area
		tfConsole = new JTextField(15);
		tfConsole.setBounds(85, 120, tfConsole.getPreferredSize().width, tfConsole.getPreferredSize().height);
		switchArea.add(tfConsole);
		
		tfAdmin = new JTextField(15);
		tfAdmin.setBounds(85, 150, tfAdmin.getPreferredSize().width, tfAdmin.getPreferredSize().height);
		switchArea.add(tfAdmin);
		
		tfTelnet = new JTextField(15);
		tfTelnet.setBounds(85, 180, tfTelnet.getPreferredSize().width, tfTelnet.getPreferredSize().height);
		switchArea.add(tfTelnet);
		
		//administração Label
		JLabel lbAdministrator = new JLabel("Administrador");
		lbAdministrator.setBounds(600,90, lbAdministrator.getPreferredSize().width, lbAdministrator.getPreferredSize().height);
		lbAdministrator.setForeground(Color.red);
		switchArea.add(lbAdministrator);
		
		JLabel lbVlanAdmin = new JLabel("vlan1");
		lbVlanAdmin.setBounds(400, 120, lbVlanAdmin.getPreferredSize().width, lbVlanAdmin.getPreferredSize().height);
		lbVlanAdmin.setForeground(Color.blue);
		switchArea.add(lbVlanAdmin);
		
		JLabel lbMascaraAdmin = new JLabel("Msk");
		lbMascaraAdmin.setBounds(630, 120, lbMascaraAdmin.getPreferredSize().width, lbMascaraAdmin.getPreferredSize().height);
		lbMascaraAdmin.setForeground(Color.blue);
		switchArea.add(lbMascaraAdmin);
		
		//administração textfields
		tfVlanAdmin = new JTextField(15);
		tfVlanAdmin.setBounds(440, 120, tfVlanAdmin.getPreferredSize().width, tfVlanAdmin.getPreferredSize().height);
		switchArea.add(tfVlanAdmin);
		
		tfMascaraAdmin = new JTextField(15);
		tfMascaraAdmin.setBounds(670, 120, tfMascaraAdmin.getPreferredSize().width, tfMascaraAdmin.getPreferredSize().height);
		switchArea.add(tfMascaraAdmin);
		 
		//interfaces VLAN
		JLabel lbLanInterfaces = new JLabel("Interfaces LAN");
		lbLanInterfaces.setBounds(20, 210, lbLanInterfaces.getPreferredSize().width, lbLanInterfaces.getPreferredSize().height);
		lbLanInterfaces.setForeground(Color.red);
		switchArea.add(lbLanInterfaces);
		
		JLabel lbActiveInterface = new JLabel("Ativo");
		lbActiveInterface.setBounds(25, 230, lbActiveInterface.getPreferredSize().width, lbActiveInterface.getPreferredSize().height);
		lbActiveInterface.setForeground(Color.blue);
		switchArea.add(lbActiveInterface);
		
		JLabel lbInterfaceType = new JLabel("Tipo");
		lbInterfaceType.setBounds(120, 230, lbActiveInterface.getPreferredSize().width, lbActiveInterface.getPreferredSize().height);
		lbInterfaceType.setForeground(Color.blue);
		switchArea.add(lbInterfaceType);
		
		JLabel lbInterfaceVlan = new JLabel("Vlan");
		lbInterfaceVlan.setBounds(220, 230, lbInterfaceVlan.getPreferredSize().width, lbInterfaceVlan.getPreferredSize().height);
		lbInterfaceVlan.setForeground(Color.blue);
		switchArea.add(lbInterfaceVlan);
		
		JLabel lbInterfaceMode = new JLabel("Modo");
		lbInterfaceMode.setBounds(470, 230, lbInterfaceMode.getPreferredSize().width, lbInterfaceMode.getPreferredSize().height);
		lbInterfaceMode.setForeground(Color.blue);
		switchArea.add(lbInterfaceMode);
		
		int cbPositionX = 0, 
			comboBoxTypePositionX = 0,
			comboBoxVlanPositionX = 0,
			cbPositionY = 0,
			comboBoxTypePositionY = 0,
			comboBoxVlanPositionY = 0,
			radioButtonPositionX = 0,
			radioButtonPositionY = 0;
		//preparar a lista por linhas com um ciclo for
		for(int i = 0; i < 7; i++)
		{
			//preparações
			if(i == 0)
			{
				cbPositionX = 25;
				cbPositionY = 250;
				comboBoxTypePositionX = 120;
				comboBoxTypePositionY = 250;
				comboBoxVlanPositionX = 220;
				comboBoxVlanPositionY = 250;
				radioButtonPositionX = 400;
				radioButtonPositionY = 250;
			}


			//checkboxes
			cbInterface[i] = new JCheckBox("Interface " + (i + 1), false);
			cbInterface[i].setBounds(cbPositionX, cbPositionY, cbInterface[i].getPreferredSize().width, cbInterface[i].getPreferredSize().height);
			cbInterface[i].addItemListener(this);
			switchArea.add(cbInterface[i]);
			
			//combobox "Tipo"
			ComboBoxList[0][i] = new JComboBox<String>(comboBoxStrType);
			ComboBoxList[0][i].setBounds(comboBoxTypePositionX, comboBoxTypePositionY, ComboBoxList[0][i].getPreferredSize().width + 20, ComboBoxList[0][i].getPreferredSize().height);
			ComboBoxList[0][i].setEnabled(false);
			ComboBoxList[0][i].addActionListener(this);
			switchArea.add(ComboBoxList[0][i]);
			
			//combobox vlan
			ComboBoxList[1][i] = new JComboBox<String>(comboBoxStrVlan);
			ComboBoxList[1][i].setBounds(comboBoxVlanPositionX, comboBoxVlanPositionY, ComboBoxList[1][i].getPreferredSize().width + 20, ComboBoxList[1][i].getPreferredSize().height);
			ComboBoxList[1][i].setEnabled(false);
			ComboBoxList[1][i].addActionListener(this);
			switchArea.add(ComboBoxList[1][i]);
			
			group[i] = new ButtonGroup();
			//JRadioButton
			for(int j = 0; j < 3; j++)
			{
				if(j == 2)
					radioButton[i][j] = new JRadioButton(radioButtonStr[j], true);
				else
					radioButton[i][j] = new JRadioButton(radioButtonStr[j], false);
				radioButton[i][j].setBounds(radioButtonPositionX, radioButtonPositionY, radioButton[i][j].getPreferredSize().width, radioButton[i][j].getPreferredSize().height);
				radioButton[i][j].setEnabled(false);
				radioButton[i][j].addItemListener(this);
				switchArea.add(radioButton[i][j]);
				radioButtonPositionX += 70;
				
				group[i].add(radioButton[i][j]);
			}
				//atualizar posições
				cbPositionY += 40;
				comboBoxTypePositionY += 40;
				comboBoxVlanPositionY += 40;
				radioButtonPositionY += 40;
				radioButtonPositionX = 400;
		}
		
		/*
		 * Router pane
		 */
		JPanel router = new JPanel();
		router.setLayout(null);
		pane.add("Router", router);
		
		JLabel lbRouterHostName = new JLabel("Hostname");
		lbRouterHostName.setBounds(20,20, lbRouterHostName.getPreferredSize().width, lbRouterHostName.getPreferredSize().height);
		lbRouterHostName.setForeground(Color.blue);
		router.add(lbRouterHostName);
		
		tfRouterHostname = new JTextField(15);
		tfRouterHostname.setBounds(85, 20, tfRouterHostname.getPreferredSize().width, tfRouterHostname.getPreferredSize().height);
		router.add(tfRouterHostname);
		
		//password campus
		JLabel lbRouterPasswordTitle = new JLabel("Password");
		lbRouterPasswordTitle.setFont(lbRouterPasswordTitle.getFont().deriveFont(18f));
		lbRouterPasswordTitle.setBounds(20, 50, lbRouterPasswordTitle.getPreferredSize().width, lbRouterPasswordTitle.getPreferredSize().height);
		lbRouterPasswordTitle.setForeground(Color.red);
		router.add(lbRouterPasswordTitle);
		
		JLabel lbRouterConsole = new JLabel("Console");
		lbRouterConsole.setBounds(25,80, lbRouterConsole.getPreferredSize().width, lbRouterConsole.getPreferredSize().height);
		lbRouterConsole.setForeground(Color.blue);
		router.add(lbRouterConsole);
		
		tfRouterConsole = new JTextField(15);
		tfRouterConsole.setBounds(85, 80, tfRouterConsole.getPreferredSize().width, tfRouterConsole.getPreferredSize().height);
		router.add(tfRouterConsole);
		
		JLabel lbRouterAdmin = new JLabel("Admin");
		lbRouterAdmin.setBounds(25, 110, lbRouterAdmin.getPreferredSize().width, lbRouterAdmin.getPreferredSize().height);
		lbRouterAdmin.setForeground(Color.blue);
		router.add(lbRouterAdmin);
		
		tfRouterAdmin = new JTextField(15);
		tfRouterAdmin.setBounds(85, 110, tfRouterAdmin.getPreferredSize().width, tfRouterAdmin.getPreferredSize().height);
		router.add(tfRouterAdmin);
		
		JLabel lbRouterTelnet = new JLabel("Telnet");
		lbRouterTelnet.setBounds(25, 140, lbRouterTelnet.getPreferredSize().width, lbRouterTelnet.getPreferredSize().height);
		lbRouterTelnet.setForeground(Color.blue);
		router.add(lbRouterTelnet);
		
		tfRouterTelnet = new JTextField(15);
		tfRouterTelnet.setBounds(85, 140, tfRouterTelnet.getPreferredSize().width, tfRouterTelnet.getPreferredSize().height);
		router.add(tfRouterTelnet);
		
		//interfaces vlan title
		JLabel lbRouterInterfacesVlan = new JLabel("Interfaces Lan");
		lbRouterInterfacesVlan.setFont(lbRouterInterfacesVlan.getFont().deriveFont(18f));
		lbRouterInterfacesVlan.setBounds(20, 170, lbRouterInterfacesVlan.getPreferredSize().width, lbRouterInterfacesVlan.getPreferredSize().height);
		lbRouterInterfacesVlan.setForeground(Color.red);
		router.add(lbRouterInterfacesVlan);
		
		JLabel lbRouterInterfaceActivo = new JLabel("Activo");
		lbRouterInterfaceActivo.setBounds(35, 200, lbRouterInterfaceActivo.getPreferredSize().width, lbRouterInterfaceActivo.getPreferredSize().height);
		lbRouterInterfaceActivo.setForeground(Color.blue);
		router.add(lbRouterInterfaceActivo);
		
		JLabel lbRouterInterfaceTipo = new JLabel("Tipo");
		lbRouterInterfaceTipo.setBounds(95, 200, lbRouterInterfaceTipo.getPreferredSize().width, lbRouterInterfaceTipo.getPreferredSize().height);
		lbRouterInterfaceTipo.setForeground(Color.blue);
		router.add(lbRouterInterfaceTipo);
		
		JLabel lbRouterInterfaceIPAddress = new JLabel("IP Address");
		lbRouterInterfaceIPAddress.setBounds(200, 200, lbRouterInterfaceIPAddress.getPreferredSize().width, lbRouterInterfaceIPAddress.getPreferredSize().height);
		lbRouterInterfaceIPAddress.setForeground(Color.blue);
		router.add(lbRouterInterfaceIPAddress);
		
		JLabel lbRouterInterfaceMascara= new JLabel("Mascara");
		lbRouterInterfaceMascara.setBounds(380, 200, lbRouterInterfaceMascara.getPreferredSize().width, lbRouterInterfaceMascara.getPreferredSize().height);
		lbRouterInterfaceMascara.setForeground(Color.blue);
		router.add(lbRouterInterfaceMascara);
		
		JLabel lbRouterInterfaceSubnet= new JLabel("SubNetting");
		lbRouterInterfaceSubnet.setBounds(535, 200, lbRouterInterfaceSubnet.getPreferredSize().width, lbRouterInterfaceSubnet.getPreferredSize().height);
		lbRouterInterfaceSubnet.setForeground(Color.blue);
		router.add(lbRouterInterfaceSubnet);
		
		JLabel lbRouterInterfaceRede= new JLabel("SubNetting");
		lbRouterInterfaceRede.setBounds(700, 200, lbRouterInterfaceRede.getPreferredSize().width, lbRouterInterfaceRede.getPreferredSize().height);
		lbRouterInterfaceRede.setForeground(Color.blue);
		router.add(lbRouterInterfaceRede);
		
		//posição de cada elemento em Y
		int RouterInterfaceElementPositionY = 0;
		for(int i = 0; i < 4; i++)
		{
			if(i == 0)
				RouterInterfaceElementPositionY = 230;
			
			cbRouterInterface[i] = new JCheckBox("LAN1", false);
			cbRouterInterface[i].setBounds(25, RouterInterfaceElementPositionY, cbRouterInterface[i].getPreferredSize().width, cbRouterInterface[i].getPreferredSize().height);
			cbRouterInterface[i].addItemListener(this);
			router.add(cbRouterInterface[i]);
		
			//Tipo combobox & textfield
			comboBoxRouter[0][i] = new JComboBox<String>(comboBoxStrType);
			comboBoxRouter[0][i].setBounds(95, RouterInterfaceElementPositionY, comboBoxRouter[0][i].getPreferredSize().width + 20, comboBoxRouter[0][i].getPreferredSize().height);
			comboBoxRouter[0][i].setEnabled(false);
			router.add(comboBoxRouter[0][i]);
			
			tfRouterIpAddress[0][i] = new JTextField(15);
			tfRouterIpAddress[0][i].setBounds(200, RouterInterfaceElementPositionY, tfRouterIpAddress[0][i].getPreferredSize().width, tfRouterIpAddress[0][i].getPreferredSize().height + 6);
			tfRouterIpAddress[0][i].setBorder(new LineBorder(Color.gray, 1));
			tfRouterIpAddress[0][i].setEnabled(false);
			router.add(tfRouterIpAddress[0][i]);
				
			comboBoxRouter[1][i] = new JComboBox<String>(MascarasStr);
			comboBoxRouter[1][i].setBounds(380, RouterInterfaceElementPositionY, comboBoxRouter[0][i].getPreferredSize().width + 50, comboBoxRouter[0][i].getPreferredSize().height);
			comboBoxRouter[1][i].setEnabled(false);
			router.add(comboBoxRouter[1][i]);
			
			grupo1[i] = new ButtonGroup();
			int posx = 520;
			for(int j = 0; j < 2; j++)
			{
				if(j == 1)
					rbRouterInterface[i][j] = new JRadioButton(yesOrNo[j], true);
				else
					rbRouterInterface[i][j] = new JRadioButton(yesOrNo[j], false);
				rbRouterInterface[i][j].setBounds(posx, RouterInterfaceElementPositionY, rbRouterInterface[i][j].getPreferredSize().width, rbRouterInterface[i][j].getPreferredSize().height);
				rbRouterInterface[i][j].setEnabled(false);
				grupo1[i].add(rbRouterInterface[i][j]);
				router.add(rbRouterInterface[i][j]);
				
				posx +=50;
			}
			
			tfRouterIpAddress[1][i] = new JTextField(15);
			tfRouterIpAddress[1][i].setBounds(650, RouterInterfaceElementPositionY, tfRouterIpAddress[1][i].getPreferredSize().width, tfRouterIpAddress[1][i].getPreferredSize().height + 6);
			tfRouterIpAddress[1][i].setEnabled(false);
			router.add(tfRouterIpAddress[1][i]);

			RouterInterfaceElementPositionY += 25;
		}
		
		JLabel lbRouterInterfaceVlanTitle = new JLabel("Interfaces Wan");
		lbRouterInterfaceVlanTitle.setBounds(20, 340, lbRouterInterfaceVlanTitle.getPreferredSize().width + 50, lbRouterInterfaceVlanTitle.getPreferredSize().height);
		lbRouterInterfaceVlanTitle.setFont(lbRouterInterfaceVlanTitle.getFont().deriveFont(18f));
		lbRouterInterfaceVlanTitle.setForeground(Color.red);
		router.add(lbRouterInterfaceVlanTitle);
		
		RouterInterfaceElementPositionY = 380;
		
		for(int i = 0; i < 2; i++)
		{
			int posx = 520;
			cbRouterWanActivo[i] = new JCheckBox("WIC" + (i + 1), false);
			cbRouterWanActivo[i].setBounds(25, RouterInterfaceElementPositionY, cbRouterWanActivo[i].getPreferredSize().width, cbRouterWanActivo[i].getPreferredSize().height);
			cbRouterWanActivo[i].addItemListener(this);
			router.add(cbRouterWanActivo[i]);
			
			comboBoxRouterWanTipo[0][i] = new JComboBox<String>(SerialPortsStr);
			comboBoxRouterWanTipo[0][i].setBounds(85, RouterInterfaceElementPositionY, comboBoxRouterWanTipo[0][i].getPreferredSize().width + 20, comboBoxRouterWanTipo[0][i].getPreferredSize().height);
			comboBoxRouterWanTipo[0][i].setEnabled(false);
			router.add(comboBoxRouterWanTipo[0][i]);
			
			tfRouterWanIpAddress[i] = new JTextField(15);
			tfRouterWanIpAddress[i].setBounds(200, RouterInterfaceElementPositionY, tfRouterWanIpAddress[i].getPreferredSize().width, tfRouterWanIpAddress[i].getPreferredSize().height + 6);
			tfRouterWanIpAddress[i].setBorder(new LineBorder(Color.gray, 1));
			tfRouterWanIpAddress[i].setEnabled(false);
			router.add(tfRouterWanIpAddress[i]);
			
			comboBoxRouterWanTipo[1][i] = new JComboBox<String>(MascarasStr);
			comboBoxRouterWanTipo[1][i].setBounds(380, RouterInterfaceElementPositionY, comboBoxRouterWanTipo[1][i].getPreferredSize().width , comboBoxRouterWanTipo[1][i].getPreferredSize().height);
			comboBoxRouterWanTipo[1][i].setEnabled(false);
			router.add(comboBoxRouterWanTipo[1][i]);
			
			grupo2[i] = new ButtonGroup();
			for(int j = 0; j < 2; j++)
			{
				if(j == 1)
					rbRouterWanClockRate[i][j] = new JRadioButton(yesOrNo[j], true);
				else
					rbRouterWanClockRate[i][j] = new JRadioButton(yesOrNo[j], false);
				rbRouterWanClockRate[i][j].setBounds(posx, RouterInterfaceElementPositionY, rbRouterWanClockRate[i][j].getPreferredSize().width, rbRouterWanClockRate[i][j].getPreferredSize().height);
				rbRouterWanClockRate[i][j].setEnabled(false);
				router.add(rbRouterWanClockRate[i][j]);
				grupo2[i].add(rbRouterWanClockRate[i][j]);
				posx += 50;
			}
			
			comboBoxRouterWanTipo[2][i] = new JComboBox<String>(protocolosStr);
			comboBoxRouterWanTipo[2][i].setBounds(630, RouterInterfaceElementPositionY, comboBoxRouterWanTipo[2][i].getPreferredSize().width + 10, comboBoxRouterWanTipo[2][i].getPreferredSize().height);
			comboBoxRouterWanTipo[2][i].setEnabled(false);
			router.add(comboBoxRouterWanTipo[2][i]);
			
			RouterInterfaceElementPositionY += 25;
		}
		//subnetting
		JLabel lbRouterSubnettingTitle = new JLabel("SubNetting");
		lbRouterSubnettingTitle.setBounds(570, 05, lbRouterSubnettingTitle.getPreferredSize().width + 50, lbRouterSubnettingTitle.getPreferredSize().height + 10);
		lbRouterSubnettingTitle.setFont(lbRouterSubnettingTitle.getFont().deriveFont(18f));
		lbRouterSubnettingTitle.setForeground(Color.red);
		router.add(lbRouterSubnettingTitle);
		
		//y position for the subnetting loop
		int posy =30;
		for(int i = 0; i < 5; i++)
		{
			cbRouterSubnetting[i] = new JCheckBox("Vlan " + (i + 1) + "0", false);
			cbRouterSubnetting[i].setBounds(450, posy, cbRouterSubnetting[i].getPreferredSize().width, cbRouterSubnetting[i].getPreferredSize().height);
			cbRouterSubnetting[i].addItemListener(this);
			router.add(cbRouterSubnetting[i]);
			
			tfRouterSubnetting[i] = new JTextField(15);
			tfRouterSubnetting[i].setBounds(535, posy, tfRouterSubnetting[i].getPreferredSize().width, tfRouterSubnetting[i].getPreferredSize().height + 6);
			tfRouterSubnetting[i].setBorder(new LineBorder(Color.gray, 1));
			tfRouterSubnetting[i].setEnabled(false);
			router.add(tfRouterSubnetting[i]);
			
			comboBoxRouterSubnetting[i] = new JComboBox<String>(MascarasStr);
			comboBoxRouterSubnetting[i].setBounds(730, posy, comboBoxRouterSubnetting[i].getPreferredSize().width, comboBoxRouterSubnetting[i].getPreferredSize().height);
			comboBoxRouterSubnetting[i].setEnabled(false);
			router.add(comboBoxRouterSubnetting[i]);
			posy += 25;
		}
		
		JPanel commandArea = new JPanel(new BorderLayout(1,1));
		
		//adicionar tabbedpanes à frame
		ta = new JTextArea();
		JScrollPane Scroll = new JScrollPane(ta);
		commandArea.add(Scroll);
		pane.add("Programação", commandArea);
			
		this.add(pane);
		//frame
		this.setBounds(10,10,1090,600);
		this.setVisible(true);
		this.setResizable(false);
		//para terminar o processo quando o programa acaba
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//começar a contar os segundos
		new CountsTime().start();
	}
	
	public void actionPerformed(ActionEvent e)
	{	
		//botao de calcular
		if(e.getSource() == bt[0])
		{
			//apagar todas as rows da tabela
			for(int i = 0; i < tableModel.getRowCount(); i++)
				tableModel.removeRow(i);
			//fazer as contas
			Object [][] data2 = calculadora();
			//preencher a tabela
			for(int i = 0; i < data2.length ; i++)
				tableModel.addRow(data2[i]);
			table.repaint();
			
		}
		//botao apagar
		else if(e.getSource() == bt[1])
		{
			for(int i = 0; i < tableModel.getRowCount(); i++)
				tableModel.removeRow(i);
			table.repaint();
		}
		//botao sair
		else if(e.getSource() == bt[2])
			System.exit(0);
	}
	
	//faz os calculos para a tabela
	public Object [][] calculadora()
	{	
		//ir buscar valores
		int total = Integer.parseInt(tfTotal.getText()); 
		String address = tfAddress.getText();
		String mascara = tfMascara.getText();
		
		//declarar objeto
		Object [][]Data = new Object[total][11];
		//separar águas
		String [] mascaraAt = mascara.split("\\.");
		String [] addressAt = address.split("\\.");
		
		//contadores
		int cidr = 0;
		int rede = Integer.parseInt(addressAt[3]);
		int inicio = rede + 1;
		int dim = 0;
		
		switch(mascaraAt[3])
		{
			case "0": 	cidr = 24; dim = 225; break;
			case "128": cidr = 25; dim = 128; break;
			case "192": cidr = 26; dim = 64;  break;
			case "224": cidr = 27; dim = 32;  break;
			case "240": cidr = 28; dim = 16;  break;
			case "248": cidr = 29; dim = 8;   break;
			case "252": cidr = 30; dim = 4;   break;
			case "254": cidr = 31; dim = 2;   break;	//não faz sentido estar aqui
			default: 	cidr = 0;  dim = 0;   break; 
		}
		
		//carregar dados
		for(int i = 0; i < total; i++)
		{
			//numero da rede
			Data[i][0] = Integer.toString(i);
			//numero da vlan
			Data[i][1] = Integer.toString((i + 1) * 10);
			//fazer hosts
			
			//dimensão
			Data[i][3] = Integer.toString(dim);
			//rede
			Data[i][4] = Integer.toString(rede);
			//inicio
			Data[i][5] = Integer.toString(inicio);
			
			inicio += dim; 
			rede += dim;
			
			//Gateway
			Data[i][6] = Integer.toString(rede - 2);
			//broadcast
			Data[i][7] = Integer.toString(rede - 1);
			//cidr
			Data[i][8] = "/" + Integer.toString(cidr);
			//mascara
			Data[i][9] = mascara;
			
			
			if(inicio >= 255 || (rede + dim) > 255)
				break;
		}
		return Data;
	}
	
	public void itemStateChanged (ItemEvent e)
	{
		int testSwitchShow = 0;
		int testRouterShow = 0;
		//to activate or not
		for(int i = 0; i < 4; i++)
		{
			if(!cbRouterInterface[i].isSelected())
			{
				tfRouterIpAddress[0][i].setEnabled(false);
				comboBoxRouter[0][i].setEnabled(false);
				tfRouterIpAddress[1][i].setEnabled(false);
				comboBoxRouter[1][i].setEnabled(false);
				//rbRouterInterface[0][i].setEnabled(false);
				for(int j = 0; j < 2; j++)
						rbRouterInterface[i][j].setEnabled(false);
				ta.setText("");
				routerChange = true;
			}
			else
			{
				tfRouterIpAddress[0][i].setEnabled(true);
				comboBoxRouter[0][i].setEnabled(true);
				tfRouterIpAddress[1][i].setEnabled(true);
				comboBoxRouter[1][i].setEnabled(true);
				//rbRouterInterface[0][i].setEnabled(true);
				for(int j = 0; j < 2; j++)
					rbRouterInterface[i][j].setEnabled(true);
				ta.setText("");
				routerChange = true;
				testRouterShow++;
			}
		}
		
		for(int i = 0; i < 2; i++)
		{
			if(cbRouterWanActivo[i].isSelected())
			{
				comboBoxRouterWanTipo[0][i].setEnabled(true);
				tfRouterWanIpAddress[i].setEnabled(true);
				comboBoxRouterWanTipo[1][i].setEnabled(true);
				comboBoxRouterWanTipo[2][i].setEnabled(true);
				for(int j = 0; j < 2; j++)
					rbRouterWanClockRate[i][j].setEnabled(true);
				ta.setText("");
				routerChange = true;
				testRouterShow++;
			}
			else
			{
				comboBoxRouterWanTipo[0][i].setEnabled(false);
				tfRouterWanIpAddress[i].setEnabled(false);
				comboBoxRouterWanTipo[1][i].setEnabled(false);
				comboBoxRouterWanTipo[2][i].setEnabled(false);
				for(int j = 0; j < 2; j++)
					rbRouterWanClockRate[i][j].setEnabled(false);
				ta.setText("");
				routerChange = true;
			}
		}
		for(int i = 0; i < 5; i++)
		{
			if(cbRouterSubnetting[i].isSelected())
			{
				tfRouterSubnetting[i].setEnabled(true);
				comboBoxRouterSubnetting[i].setEnabled(true);
				ta.setText("");
				routerChange = true;
				testRouterShow++;
			}
			else
			{
				tfRouterSubnetting[i].setEnabled(false);
				comboBoxRouterSubnetting[i].setEnabled(false);
				ta.setText("");
				routerChange = true;
			}
		}
		for(int i = 0; i < 7; i++)
		{
			
			if(cbInterface[i].isSelected())
			{
				ComboBoxList[0][i].setEnabled(true);
				ComboBoxList[1][i].setEnabled(true);
				for(int j = 0; j < 3; j++)
					radioButton[i][j].setEnabled(true);
				ta.setText("");
				switchChange = true;
				testSwitchShow++;
			}
			else
			{
				ComboBoxList[0][i].setEnabled(false);
				ComboBoxList[1][i].setEnabled(false);
				for(int j = 0; j < 3; j++)
					radioButton[i][j].setEnabled(false);
				ta.setText("");
				switchChange = true;
			}
		}
		//to show or not to show, there lies the question
		if(testSwitchShow != 0)
			switchShowCode = true;
		else
			switchShowCode = false;
		if(testRouterShow != 0)
			routerShowCode = true;
		else
			routerShowCode = false;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Ciisco app = new Ciisco();
	}
}