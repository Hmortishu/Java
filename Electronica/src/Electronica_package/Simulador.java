package Electronica_package;

import java.awt.*;
import java.awt.event.*;

public class Simulador extends Frame {
	Resistencia r1, r2, r3, r4;
	Shunt sh1, sh2, sh3;
	Color TabColor [] = {
			Color.black, 					//preto
			new Color(128,64,64),			//castanho
			Color.orange,					//Laranja
			Color.yellow, 					//amarelo
			new	Color(0 ,115, 0),			//verde
			new Color(243, 80, 248),		//Violeta
			Color.gray,						//cizento
			Color.red						//vermelho
											
	};
	public Simulador(){
		// TODO Auto-generated constructor stub
		super("Simulador");
		
		//criar uma nova resistencia
		
		r1= new Resistencia(100, 100, 1000, true, "R1");
		r1.setBd1(TabColor[1]);
		r1.setBd2(TabColor[0]);
		r1.setBd3(TabColor[7]);
		r1.setBd4(TabColor[2]);
		
		r2= new Resistencia(300, 300, 1000, true, "R2");
		r2.setBd1(TabColor[1]);
		r2.setBd2(TabColor[0]);
		r2.setBd3(TabColor[7]);
		r2.setBd4(TabColor[2]);
		
		r3= new Resistencia(300, 100, 1000, true, "R3");
		r3.setBd1(TabColor[1]);
		r3.setBd2(TabColor[0]);
		r3.setBd3(TabColor[7]);
		r3.setBd4(TabColor[2]);
		
		r4= new Resistencia(100, 100, 1000, false, "R3");
		r4.setBd1(TabColor[1]);
		r4.setBd2(TabColor[0]);
		r4.setBd3(TabColor[7]);
		r4.setBd4(TabColor[2]);
		
		sh1 = new Shunt(300,100,"sh1", false);
		sh2 = new Shunt(500,100,"sh2", false);
		sh3 = new Shunt(500,100,"sh3", true);
		
		//frame
		setSize(1200, 800);
		setResizable(false);
		setVisible(true);
	}
/*
	public Simulador(GraphicsConfiguration arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public Simulador(String arg0) throws HeadlessException {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public Simulador(String arg0, GraphicsConfiguration arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}
*/
	public void paint(Graphics g)
	{
		g.setColor(Color.blue);
		for(int i = 0 ; i < 20000; i+=100)
		{
			for(int j = 0 ; j < 10000; j+=100 )
			{
				g.fillOval(i-5, j-5, 10, 10);
			}
		}
		r1.paint(g);
		r2.paint(g);
		r3.paint(g);
		r4.paint(g);
		sh1.paint(g);
		sh2.paint(g);
		sh3.paint(g);
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Simulador app = new Simulador();
		app.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				System.exit(0);
			}
		});
	}

}
