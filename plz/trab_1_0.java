/***/

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;


import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.LineBorder;
import javax.swing.JComboBox;

public class trab_1_0 extends JFrame implements ActionListener
{

	String [] stSNM = 
		{
			"255.255.255.252",
			"255.255.255.248",
			"255.255.255.240",
			"255.255.255.224",
			"255.255.255.192",
			"255.255.255.128",
			"225.225.225.0"
		};
	int [] intSNM =
		{
				4,
				8,
				16,
				32,
				64,
				128,
				256
		};
	JMenuBar mnb;
	JMenu mn1,mn2;
	JMenuItem [] mni = new JMenuItem[3];
	JTable tabela;
	JScrollPane scrollcalc;
	JTabbedPane tabsdir;
	JPanel calc, northcalc, southcalc, switchp, northswitch, router, prog;
	JLabel lbnetworks, lbaddress, lbsubnetmask, lbswitch;
	JTextField tfnetworks, tfaddress, tfsubnetmask, tfhostnswitch, tfpassconsw, tfpassadminsw, tfpasstelsw, tfvlanadminsw, tfmaskadminsw;
	JButton btcalc, btapagar;
	Font Arial = new Font("Arial", Font.BOLD, 16), tituloA = new Font("Arial", Font.BOLD, 30), table = new Font("Arial", Font.PLAIN, 15);
	Toolkit tlk;
	Dimension dim;
	JCheckBox [] cbSwitch = new JCheckBox[8];
	JComboBox [] ComboSwitch = new JComboBox[8];
	JComboBox [] ComboSwitchvlan = new JComboBox[8];
	String [] titulo = {"Rede","Vlan","Nr de Hosts","IP de Rede","Prineiro Host","Gateway de Rede","IP de Broadcast","CIDR", "Subnetmask","Observa��es"};
	String [][] Data = new String[25][10];
	String [] stSwitch = {"Activo","Porta","Vlan"};
	ArrayList<String> CBswitch = new ArrayList<String>();
	ArrayList<String> CBrouter = new ArrayList<String>();
	ArrayList<String> CBvlanswitch = new ArrayList<String>();
	ButtonGroup [] gbtS = new ButtonGroup[8];
	JRadioButton [] rbacc = new JRadioButton[8];
	JRadioButton [] rbtru = new JRadioButton[8];
	JRadioButton [] rbna = new JRadioButton[8];
	JRadioButton [][] rba = new JRadioButton[3][7];
	
	
	//router
	JLabel lbrouter;
	String [] stRouter = {"Activo","Porta", "IP Address", "Subnetmask", "Clockrate", "Protocolo"};
	JComboBox [] cbvlanrouter =  new JComboBox[8];
	ArrayList<String> CBvlanrouter = new ArrayList<String>();
	JComboBox [] cbvlanintrouter =  new JComboBox[8];
	ArrayList<String> CBvlanintrouter = new ArrayList<String>();
	JComboBox [] cbvlanmskrouter =  new JComboBox[8];
	ArrayList<String> CBvlanmskrouter = new ArrayList<String>();
	JTextField [] ipfieldroutervlan = new JTextField[8];
	JCheckBox [] cbIntlanrout = new JCheckBox[8];
	JComboBox [] cbIntlanport = new JComboBox[8];
	ArrayList<String> CBIntlanport = new ArrayList<String>();
	JTextField [] tfipaddlanRouter = new JTextField[8];
	JComboBox [] cdSNMlanrouter = new JComboBox[8];
	ArrayList<String> CBsnmlanport = new ArrayList<String>();
	JRadioButton[] rbsubsimlanrouter = new JRadioButton[8];
	JRadioButton[] rbsubnaolanrouter = new JRadioButton[8];
	ButtonGroup [] bgsublanrouter = new ButtonGroup[8];
	JTextField [] tfRedelanrouter = new JTextField[8];
	//wan
	JCheckBox [] cbIntwanrout = new JCheckBox[2];
	JComboBox [] cbIntwanport = new JComboBox[2];
	ArrayList<String> CBIntwanport = new ArrayList<String>();
	JTextField [] tfipaddwanRouter = new JTextField[2];
	JComboBox [] cdSNMwanrouter = new JComboBox[2];
	JRadioButton[] rbsubsimwanrouter = new JRadioButton[2];
	JRadioButton[] rbsubnaowanrouter = new JRadioButton[2];
	ButtonGroup [] bgsubwanrouter = new ButtonGroup[2];
	ArrayList<String> CBwanprot = new ArrayList<String>();
	JComboBox [] cbProwanrouter = new JComboBox[2];
	
	//tab programa�ao
	JTextArea taprog = new JTextArea();
	
	public trab_1_0()
	{
		super("help");

		tlk = getToolkit();
		dim = tlk.getScreenSize();

		mnb = new JMenuBar();
		mn1 = new JMenu("Op��es");
		mnb.add(mn1);
		mni[0] = new JMenuItem("Apagar");
		mni[0].addActionListener(this);
		mni[1] = new JMenuItem("Sair");
		mni[1].addActionListener(this);
		mn1.add(mni[0]);
		mn1.add(mni[1]);
		mnb.add(mn1);
		setJMenuBar(mnb);


		tabsdir = new JTabbedPane();
		tabsdir.setTabPlacement(2);
		//separador calcular
		calc = new JPanel(new BorderLayout());
		northcalc = new JPanel();
		lbnetworks = new JLabel("Nr de redes");
		northcalc.add(lbnetworks);
		tfnetworks = new JTextField(12);
		northcalc.add(tfnetworks);
		
		lbaddress = new JLabel("Ip base");
		northcalc.add(lbaddress);
		tfaddress = new JTextField(12);
		northcalc.add(tfaddress);
		
		lbsubnetmask = new JLabel("Subnetmask");
		northcalc.add(lbsubnetmask);
		tfsubnetmask = new JTextField(12);
		northcalc.add(tfsubnetmask);
		
		calc.add(northcalc, BorderLayout.NORTH);
		
		tabela = new JTable(Data,titulo);
		scrollcalc = new JScrollPane(tabela);
		calc.add(scrollcalc, BorderLayout.CENTER);
		southcalc = new JPanel();
		btcalc = new JButton("Calcular");
		btcalc.addActionListener(this);
		southcalc.add(btcalc);
		calc.add(southcalc, BorderLayout.SOUTH);
		tabsdir.add("Calculadora",calc);

		//separador switch

		switchp = new JPanel(null);

		lbswitch = new JLabel("Hostmane");
		lbswitch.setForeground(Color.BLUE);
		lbswitch.setFont(new Font("Arial", Font.BOLD, 19));
		lbswitch.setBounds((dim.width/11)-20,85,100,35);
		switchp.add(lbswitch);

		tfhostnswitch = new JTextField(30);
		tfhostnswitch.setBounds((dim.width/11)+80, 85, 160, 35);
		switchp.add(tfhostnswitch);

		lbswitch = new JLabel("Palavas-Passe");
		lbswitch.setFont(tituloA);
		lbswitch.setForeground(Color.RED);
		lbswitch.setBounds((dim.width/11)-25,160,220,35);
		switchp.add(lbswitch);

		lbswitch = new JLabel("Consola");
		lbswitch.setFont(Arial);
		lbswitch.setForeground(Color.BLUE);
		lbswitch.setBounds((dim.width/11)-20,195,70,35);
		switchp.add(lbswitch);

		tfpassconsw = new JTextField(30);
		tfpassconsw.setBounds((dim.width/11)+50, 200, 160, 25);
		switchp.add(tfpassconsw);

		lbswitch = new JLabel("Admin");
		lbswitch.setFont(Arial);
		lbswitch.setForeground(Color.BLUE);
		lbswitch.setBounds((dim.width/11)-20,230,50,35);
		switchp.add(lbswitch);

		tfpassadminsw = new JTextField(30);
		tfpassadminsw.setBounds((dim.width/11)+40, 235, 160, 25);
		switchp.add(tfpassadminsw);

		lbswitch = new JLabel("Telent");
		lbswitch.setFont(Arial);
		lbswitch.setForeground(Color.BLUE);
		lbswitch.setBounds((dim.width/11)-20,265,70,35);
		switchp.add(lbswitch);

		tfpasstelsw = new JTextField(30);
		tfpasstelsw.setBounds((dim.width/11)+40, 270, 160, 25);
		switchp.add(tfpasstelsw);

		lbswitch = new JLabel("Rede De Administra��o");
		lbswitch.setFont(tituloA);
		lbswitch.setForeground(Color.RED);
		lbswitch.setBounds((dim.width/3)+80,160,348,35);
		switchp.add(lbswitch);

		lbswitch = new JLabel("Vlan");
		lbswitch.setFont(Arial);
		lbswitch.setForeground(Color.BLUE);
		lbswitch.setBounds((dim.width/3)+80,200,70,30);
		switchp.add(lbswitch);

		tfvlanadminsw = new JTextField(30);
		tfvlanadminsw.setBounds((dim.width/3)+130, 200, 160, 25);
		switchp.add(tfvlanadminsw);

		lbswitch = new JLabel("Subnetmask");
		lbswitch.setFont(Arial);
		lbswitch.setForeground(Color.BLUE);
		lbswitch.setBounds((dim.width/3)+80,245,100,30);
		switchp.add(lbswitch);

		tfmaskadminsw = new JTextField(30);
		tfmaskadminsw.setBounds((dim.width/3)+190, 250, 160, 25);
		switchp.add(tfmaskadminsw);


		//interfaces
		int j = 0;
		for(int i = 0; i <3; i++){

			lbswitch = new JLabel(stSwitch[i]);
			lbswitch.setFont(table);
			lbswitch.setForeground(Color.BLUE);
			lbswitch.setBounds(((dim.width/11) + j),(dim.height/2),120,30);
			switchp.add(lbswitch);
			j = j+120;
		}
		
		j = 0;	
		for(int i = 0; i < 8 ; i++)
		{
			cbSwitch[i] = new JCheckBox("Interface "+i) ;
			cbSwitch[i].setBounds(((dim.width/11))-20,(dim.height/2)+40 + j,100,20);
			switchp.add(cbSwitch[i]);
			j = j + 30 ;
		}

		
		for(j = 1; j < 25 ; j++)
		{
			CBswitch.add("int fa0/"+j);
		}
		String[] array = new String[CBswitch.size()];
		for(int i = 0; i < array.length; i++) {
			array[i] = CBswitch.get(i);
		}
		j = 0 ;
		for(int i = 0; i < 8; i++)
		{
			ComboSwitch[i] = new JComboBox(array);
			ComboSwitch[i].setBounds(((dim.width/11))+100,(dim.height/2)+40 + j,100,20);
			switchp.add(ComboSwitch[i]);
			j = j + 30;
		}

		
		for(j = 1; j < 100 ; j++)
		{
			CBvlanswitch.add("vlan"+j);
		}
		String[] arrayvlan = new String[CBvlanswitch.size()];
		for(int i = 0; i < arrayvlan.length; i++) {
			arrayvlan[i] = CBvlanswitch.get(i);
		}
		j = 0 ;
		for(int i = 0; i < 8; i++)
		{
			ComboSwitch[i] = new JComboBox(arrayvlan);
			ComboSwitch[i].setBounds(((dim.width/11))+220,(dim.height/2)+40 + j,100,20);
			switchp.add(ComboSwitch[i]);
			j = j + 30;
		}
		lbswitch = new JLabel("Modo");
		lbswitch.setFont(table);
		lbswitch.setForeground(Color.BLUE);
		lbswitch.setBounds((dim.width/3) + 100,(dim.height/2),100,30);
		switchp.add(lbswitch);
		
		j = 0;
		for( int i = 0 ; i < 8 ; i++)
		{
			gbtS[i] = new ButtonGroup();
			rbacc[i] = new JRadioButton("Acess");
			rbacc[i].setBounds((dim.width/3) ,(dim.height/2)+40 + j,70,20);
			switchp.add(rbacc[i]);
			rbtru[i] = new JRadioButton("Trunk");
			rbtru[i].setBounds((dim.width/3) + 90 ,(dim.height/2)+40 + j,70,20);
			switchp.add(rbtru[i]);
			rbna[i] = new JRadioButton("NA");
			rbna[i].setSelected(true);
			rbna[i].setBounds((dim.width/3) + 180 ,(dim.height/2)+40 + j,70,20);
			switchp.add(rbna[i]);
			gbtS[i].add(rbacc[i]);
			gbtS[i].add(rbtru[i]);
			gbtS[i].add(rbna[i]);
			j = j + 30;
		}
		
		
		
		
		tabsdir.add("Switch", switchp);
		
		
		router = new JPanel(null);

		lbrouter = new JLabel("Hostmane");
		lbrouter.setForeground(Color.BLUE);
		lbrouter.setFont(new Font("Arial", Font.BOLD, 19));
		lbrouter.setBounds((dim.width/11)-20,85,100,35);
		router.add(lbrouter);

		tfhostnswitch = new JTextField(30);
		tfhostnswitch.setBounds((dim.width/11)+80, 85, 160, 35);
		router.add(tfhostnswitch);

		lbrouter = new JLabel("Palavas-Passe");
		lbrouter.setFont(tituloA);
		lbrouter.setForeground(Color.RED);
		lbrouter.setBounds((dim.width/11)-25,160,220,35);
		router.add(lbrouter);

		lbrouter = new JLabel("Consola");
		lbrouter.setFont(Arial);
		lbrouter.setForeground(Color.BLUE);
		lbrouter.setBounds((dim.width/11)-20,195,70,35);
		router.add(lbrouter);

		tfpassconsw = new JTextField(30);
		tfpassconsw.setBounds((dim.width/11)+50, 200, 160, 25);
		router.add(tfpassconsw);

		lbrouter = new JLabel("Admin");
		lbrouter.setFont(Arial);
		lbrouter.setForeground(Color.BLUE);
		lbrouter.setBounds((dim.width/11)-20,230,50,35);
		router.add(lbrouter);

		tfpassadminsw = new JTextField(30);
		tfpassadminsw.setBounds((dim.width/11)+40, 235, 160, 25);
		router.add(tfpassadminsw);

		lbrouter = new JLabel("Telent");
		lbrouter.setFont(Arial);
		lbrouter.setForeground(Color.BLUE);
		lbrouter.setBounds((dim.width/11)-20,265,70,35);
		router.add(lbrouter);

		tfpasstelsw = new JTextField(30);
		tfpasstelsw.setBounds((dim.width/11)+40, 270, 160, 25);
		router.add(tfpasstelsw);
		
		//interfaces
		tabsdir.add("Router", router);
		getContentPane().add(tabsdir, BorderLayout.CENTER);

		lbrouter = new JLabel("Subnetting");
		lbrouter.setBounds((dim.width/2) - 50,(dim.height/20),160,35);
		lbrouter.setFont(tituloA);
		lbrouter.setForeground(Color.RED);
		router.add(lbrouter);
		
		//Vlans e etc
		for(j = 1; j < 100 ; j++)
		{
			CBvlanrouter.add("vlan"+j);
		}
		String[] arrayvlanrouter = new String[CBvlanrouter.size()];
		for(int i = 0; i < arrayvlanrouter.length; i++) {
			arrayvlanrouter[i] = CBvlanrouter.get(i);
		}
		j = 0 ;
		for(int i = 0 ; i < 8 ; i++ )
		{
			cbvlanrouter[i] = new JComboBox(arrayvlanrouter);
			cbvlanrouter[i].setBounds((dim.width/2) - 80,(dim.height/20) + 50 + j,100,20);
			router.add(cbvlanrouter[i]);
			j = j + 20;
		}

		//interfaces para as vlans
		for(j = 0; j < 5 ; j++)
		{
			for(int i = 0 ; i < 5 ; i++ )
			{
				CBvlanintrouter.add("int fa"+j+"/"+i);
			}
		}
		String[] arrayvlanintrouter = new String[CBvlanintrouter.size()];
		for(int i = 0; i < arrayvlanintrouter.length; i++) {
			arrayvlanintrouter[i] = CBvlanintrouter.get(i);
		}
		j = 0 ;
		for(int i = 0 ; i < 8 ; i++ )
		{
			cbvlanintrouter[i] = new JComboBox(arrayvlanintrouter);
			cbvlanintrouter[i].setBounds((dim.width/2) + 50,(dim.height/20) + 50 + j,100,20);
			router.add(cbvlanintrouter[i]);
			j = j + 20;
		}
		j= 0 ;
		for(int i = 0 ; i < 8 ; i++)
		{
			ipfieldroutervlan[i] = new JTextField();
			ipfieldroutervlan[i].setBounds((dim.width/2) + 180,(dim.height/20) + 50 + j,120,20);;
			router.add(ipfieldroutervlan[i]);
			j = j + 20;
		}
		
		CBvlanmskrouter.add("255.255.255.252");
		CBvlanmskrouter.add("255.255.255.248");
		CBvlanmskrouter.add("255.255.255.224");
		CBvlanmskrouter.add("255.255.255.192");
		CBvlanmskrouter.add("255.255.255.128");
		
		String[] arrayvlanmskrouter = new String[CBvlanmskrouter.size()];
		for(int i = 0; i < arrayvlanmskrouter.length; i++) {
			arrayvlanmskrouter[i] = CBvlanmskrouter.get(i);
		}
		j = 0 ;
		for(int i = 0 ; i < 8 ; i++ )
		{
			cbvlanmskrouter[i] = new JComboBox(arrayvlanmskrouter);
			cbvlanmskrouter[i].setBounds((dim.width/2) + 330,(dim.height/20) + 50 + j,120,20);
			router.add(cbvlanmskrouter[i]);
			j = j + 20;
		}
		
		lbrouter = new JLabel("Interfaces Lan");
		lbrouter.setFont(tituloA);
		lbrouter.setForeground(Color.RED);
		lbrouter.setBounds((dim.width/11)-25,(dim.height/2) - 120,220,35);
		router.add(lbrouter);
		
		j = 0 ;
		for(int i = 0 ; i < 3 ; i++)
		{
			lbrouter = new JLabel(stRouter[i]);
			lbrouter.setFont(table);
			lbrouter.setForeground(Color.BLUE);
			lbrouter.setBounds((dim.width/11)-35 + j, (dim.height/2) - 90, 220 , 35);
			router.add(lbrouter);			
			j = j + 100;
		}
		
		j = 0 ;
		for(int i = 0 ; i < 8 ; i++ )
		{
			cbIntlanrout[i] = new JCheckBox("Interface " + i);
			cbIntlanrout[i].setBounds((dim.width/11) - 60,(dim.height/2) - 60 + j, 90, 17);
			router.add(cbIntlanrout[i]);
			j = j + 17;
		}
		
		for(j = 0; j < 5 ; j++)
		{
			for(int i = 0 ; i < 5 ; i++ )
			{
				CBIntlanport.add("int fa"+j+"/"+i);
			}
		}
		
		String[] arrayintlanport = new String[CBIntlanport.size()];
		for(int i = 0; i < arrayintlanport.length; i++) {
			arrayintlanport[i] = CBIntlanport.get(i);
		}
		
		j = 0 ;
		for(int i = 0 ; i < 8 ; i++ )
		{
			cbIntlanport[i] = new JComboBox(arrayintlanport);
			cbIntlanport[i].setBounds((dim.width/11) +40,(dim.height/2) - 60 + j,90,17);
			router.add(cbIntlanport[i]);
			j = j + 17;
		}
		
		j = 0 ;
		for(int i = 0 ; i < 8 ; i++)
		{
			tfipaddlanRouter[i] = new JTextField("");
			tfipaddlanRouter[i].setBounds((dim.width/11) +150,(dim.height/2) - 60 + j, 120 , 17 );
			router.add(tfipaddlanRouter[i]);
			j = j + 17;
		}
		
		lbrouter = new JLabel("SubnetMask");
		lbrouter.setFont(table);
		lbrouter.setForeground(Color.BLUE);
		lbrouter.setBounds((dim.width/11) + 300 , (dim.height/2) - 90, 150 , 35);
		router.add(lbrouter);
		
		for(int i = 0 ; i < 6 ; i++)
		{
			CBsnmlanport.add(stSNM[i]);
		}
		
		
		String[] arraysnmlanport = new String[CBsnmlanport.size()];
		for(int i = 0; i < arraysnmlanport.length; i++) {
			arraysnmlanport[i] = CBsnmlanport.get(i);
		}
		
		j = 0 ; 
		for(int i = 0 ; i < 8 ; i++ )
		{
			cbvlanmskrouter[i] = new JComboBox(arraysnmlanport);
			cbvlanmskrouter[i].setBounds((dim.width/11) + 280 , (dim.height/2) - 60 + j, 125 , 17);
			router.add(cbvlanmskrouter[i]);
			j = j + 17;
		}
		
		lbrouter = new JLabel("Subneting");
		lbrouter.setFont(table);
		lbrouter.setForeground(Color.BLUE);
		lbrouter.setBounds((dim.width/3) + 50 , (dim.height/2) - 90, 150 , 35);
		router.add(lbrouter);
		
		j = 0 ;
		for(int i = 0 ; i < 8 ; i++)
		{
			bgsublanrouter[i] = new ButtonGroup();
			rbsubsimlanrouter[i] = new JRadioButton("Sim");
			rbsubsimlanrouter[i].setBounds((dim.width/3) + 30 , (dim.height/2) - 60 + j, 50 , 17);
			bgsublanrouter[i].add(rbsubsimlanrouter[i]);
			router.add(rbsubsimlanrouter[i]);
			rbsubnaolanrouter[i] = new JRadioButton("N�o");
			rbsubnaolanrouter[i].setBounds((dim.width/3) + 100 , (dim.height/2) - 60 + j, 50 , 17);
			rbsubnaolanrouter[i].setSelected(true);
			bgsublanrouter[i].add(rbsubnaolanrouter[i]);
			router.add(rbsubnaolanrouter[i]);	
			j = j + 17;
		}
		
		lbrouter = new JLabel("Rede");
		lbrouter.setFont(table);
		lbrouter.setForeground(Color.BLUE);
		lbrouter.setBounds((dim.width/3) + 210 , (dim.height/2) - 90, 150 , 35);
		router.add(lbrouter);
		
		j = 0 ;
		for(int i = 0 ; i < 8 ; i++ )
		{
		
			tfRedelanrouter[i] = new JTextField();
			tfRedelanrouter[i].setBounds((dim.width/3) + 175 , (dim.height/2) - 60 + j, 150 , 17);
			tfRedelanrouter[i].setEditable(false);
			router.add(tfRedelanrouter[i]);
			j = j + 17;
		}
		
		
		
		lbrouter = new JLabel("Interfaces Wan");
		lbrouter.setFont(tituloA);
		lbrouter.setForeground(Color.RED);
		lbrouter.setBounds((dim.width/11)-25,(dim.height/2) + 120,220,35);
		router.add(lbrouter);
		
		j = 0 ;
		for(int i =  0 ; i < 6 ; i++)
		{
			lbrouter = new JLabel(stRouter[i]);
			lbrouter.setFont(table);
			lbrouter.setForeground(Color.BLUE);
			lbrouter.setBounds((dim.width/11)-35 + j,(dim.height/2) + 155,220,35);
			router.add(lbrouter);
			if(i<3)
			j = j + 110 ;
			else
			j = j + 150 ;
		}
		
		j = 0 ;
		for(int i = 0; i < 2 ; i++)
		{
			cbIntwanrout[i] = new JCheckBox("Interface " + i);
			cbIntwanrout[i].setBounds((dim.width/11) - 60 ,(dim.height/2) + 180 + j,90,20);
			router.add(cbIntwanrout[i]);
			j = j + 20;
		}
		
		
		for(j = 0; j < 7 ; j++)
		{
			for(int i = 0 ; i < 3 ; i++ )
			{
				CBIntwanport.add("int gi"+j+"/"+i);
			}
		}
		
		String[] arrayintwanport = new String[CBIntwanport.size()];
		for(int i = 0; i < arrayintwanport.length; i++) {
			arrayintwanport[i] = CBIntwanport.get(i);
		}
		
		j = 0 ;
		for(int i = 0 ; i < 2 ; i++ )
		{
			cbIntwanport[i] = new JComboBox(arrayintwanport);
			cbIntwanport[i].setBounds((dim.width/11) + 40 ,(dim.height/2) + 180 + j,90,20);
			router.add(cbIntwanport[i]);
			j = j + 20;
		}
		
		j = 0 ; 
		for(int i = 0 ; i < 2 ; i++)
		{
			tfipaddwanRouter[i] = new JTextField();
			tfipaddwanRouter[i].setBounds((dim.width/11) + 150 ,(dim.height/2) + 180 + j,130,20);
			router.add(tfipaddwanRouter[i]);
			j = j + 20;
		}
		
		j = 0 ; 
		for(int i = 0 ; i < 2 ; i++ )
		{
			cbIntwanport[i] = new JComboBox(arraysnmlanport);
			cbIntwanport[i].setBounds((dim.width/11) + 280 , (dim.height/2) + 180 + j, 125 , 20);
			router.add(cbIntwanport[i]);
			j = j + 20;
		}	
		
		
		
		j = 0 ;
		for(int i = 0 ; i < 2 ; i++)
		{
			bgsubwanrouter[i] = new ButtonGroup();
			rbsubsimwanrouter[i] = new JRadioButton("Sim");
			rbsubsimwanrouter[i].setBounds((dim.width/11) + 440 , (dim.height/2) + 180 + j, 50 , 20);
			bgsubwanrouter[i].add(rbsubsimwanrouter[i]);
			router.add(rbsubsimwanrouter[i]);
			rbsubnaowanrouter[i] = new JRadioButton("N�o");
			rbsubnaowanrouter[i].setBounds((dim.width/11) + 510, (dim.height/2) + 180 + j, 50 , 20);
			rbsubnaowanrouter[i].setSelected(true);
			bgsubwanrouter[i].add(rbsubnaowanrouter[i]);
			router.add(rbsubnaowanrouter[i]);	
			j = j + 20;
		}
		
		
		CBwanprot.add("Rip");
		CBwanprot.add("Rip2");
		CBwanprot.add("Eigrp");
		
		String[] arrayintwanprot = new String[CBwanprot.size()];
		for(int i = 0; i < arrayintwanprot.length; i++) {
			arrayintwanprot[i] = CBwanprot.get(i);
		}
		
		j = 0 ;
		for(int i = 0 ; i < 2; i++)
		{
			cbProwanrouter[i] = new JComboBox(arrayintwanprot);
			cbProwanrouter[i].setBounds((dim.width/11) + 600, (dim.height/2) + 180 + j, 50 , 20);
			router.add(cbProwanrouter[i]);
			j = j + 20;
		}
		
		
		prog = new JPanel(new BorderLayout());
		taprog = new JTextArea("");
		prog.add(taprog, BorderLayout.CENTER);
		tabsdir.add("Programa�ao",prog);
		
		setBounds(5,5,dim.width - 10, dim.height - 50);
		setVisible(true);
		setResizable(true);
	}

	public static void main(String [] args)
	{
		trab_1_0 app = new trab_1_0();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == mni[0])
		{
			
		}
		if(e.getSource() == mni[1])
		{
			System.exit(0);
		}
		if(e.getSource() == btcalc)
		{
			Calc();
			//Data[0][0] = "ola";
			//repaint();
		}
		
	}
	public void Calc()
	{
		int nrredes = Integer.parseInt(tfnetworks.getText());
		String ip = tfaddress.getText();
		String snm = tfsubnetmask.getText();
		String[] base = ip.split("\\.");
		int nrhosts = 0 ;
		int ipbase = 0;
		
		if(nrredes == 0 )
		{
			JOptionPane.showMessageDialog(null, "O numero de redes nao pode ser 0" , "Caixa de informa�ao", JOptionPane.ERROR_MESSAGE);
		}
		if(nrredes > 25)
		{
			JOptionPane.showMessageDialog(null, "Nr de Redes excede 25" , "Caixa de informa�ao", JOptionPane.ERROR_MESSAGE);
		}
		
		if(base.length != 4)
		{
			JOptionPane.showMessageDialog(null, "O Ip base introduzido esta errado" , "Caixa de informa�ao", JOptionPane.ERROR_MESSAGE);
			ipbase = Integer.parseInt(base[3]);
		}
		
			for(int i = 0 ; i < 7 ; i++)
			{
				if(snm == stSNM[i])
				{
					nrhosts = 255;
				}
			}
			for(int i = 0 ; i < nrredes ; i++)
			{
				String out = base[0]+"."+base[1]+"."+base[2]+"."+Integer.toString(ipbase);
				Data[i][0] = out;
				ipbase = ipbase + nrhosts;
				repaint();
			}
		
		repaint();
	}
	
	
	
	
	
}