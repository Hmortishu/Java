package WicWacWoe_package;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

import javax.swing.*;

public class Client extends JFrame implements ActionListener {
	private static final long serialVersionUID=1l;
	int k = 0;
	Toolkit tlk;
	Dimension dim,pan;
	int jogadas = 0 ;
	String [] nove = {
			"img/1.jpg","img/2.jpg","img/3.jpg","img/4.jpg","img/5.jpg","img/6.jpg","img/7.jpg","img/8.jpg","img/9.jpg"
	};
	String horde ="img/h.jpg";
	String alliance ="img/a.jpg";
	
	JButton [] btGalo = new JButton[9];
	JButton btGuide1, btGuide2;
	JPanel unico, bot;


	public Client()
	{
		super("Jogo");
		tlk = getToolkit();
		dim = tlk.getScreenSize();


		unico = new JPanel(new GridLayout(3,3));
		pan = unico.getSize();
		for(int i = 0 ; i < 9 ; i++)
		{

			btGalo[i] = new JButton();
			ImageIcon img = new ImageIcon(new ImageIcon(nove[i]).getImage().getScaledInstance((dim.width-50)/3, (dim.height-50)/3, Image.SCALE_DEFAULT));
			btGalo[i].setIcon(img);
			btGalo[i].addActionListener(this);
			unico.add(btGalo[i]);

		}

		this.add(unico, BorderLayout.CENTER);
		bot = new JPanel(new FlowLayout());
		btGuide1 = new JButton("Novo Jogo");
		btGuide2 = new JButton("Sair");
		btGuide1.addActionListener(this);
		btGuide2.addActionListener(this);
		bot.add(btGuide1);
		bot.add(btGuide2);
		this.add(bot, BorderLayout.SOUTH); 

		setVisible(true);
		setBounds(5,5,dim.width -5 , dim.height -40);
	}


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Client app = new Client();
		app.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				System.exit(0);
			}
		});
	}


	@Override
	public void actionPerformed(ActionEvent e) 
	{
		ImageIcon imgH = new ImageIcon(new ImageIcon(horde).getImage().getScaledInstance((dim.width-50)/3, (dim.height-50)/3, Image.SCALE_DEFAULT));
		ImageIcon imgA = new ImageIcon(new ImageIcon(alliance).getImage().getScaledInstance((dim.width-50)/3, (dim.height-50)/3, Image.SCALE_DEFAULT));
		// TODO Auto-generated method stub
		
		for(int i = 0 ; i < 9 ; i++)
		{
			if(e.getSource()==btGalo[i])
			{
				System.out.printf("%d", jogadas%2);
				if(jogadas % 2 == 0)
				{
					btGalo[i].removeActionListener(this);
					btGalo[i].setIcon(imgA);
					vitAlli();
					jogadas++;
					
				}
				else
				{
					btGalo[i].removeActionListener(this);
					btGalo[i].setIcon(imgH);
					vitHorde();
					jogadas++;
				}
			}
		}
		
		if(e.getSource() == btGuide1)
		{
			jogadas = 0 ;
			for(int i = 0 ; i < 9 ; i++)
			{
				ImageIcon img = new ImageIcon(new ImageIcon(nove[i]).getImage().getScaledInstance((dim.width-50)/3, (dim.height-50)/3, Image.SCALE_DEFAULT));
				btGalo[i].setIcon(img);
				btGalo[i].addActionListener(this);
			}		
		}
		if(e.getSource() == btGuide2)
		{
			System.exit(0);
		}
	}
	public void vitAlli()
	{
		
	}
	public void vitHorde()
	{
		
	}
}
