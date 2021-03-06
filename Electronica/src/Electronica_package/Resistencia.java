package Electronica_package;

import java.awt.*;

public class Resistencia {
//Atributos
	int posX, posY; //coordenadas
	int valor;
	boolean montagem;//true = horizontal - false = vertical;
	String referencia;
	int terminal = 50;
	int corpoComp, corpoAlt;
	int comp = 100, alt = 30;
	Color bd1,bd2,bd3,bd4;//cores da resistencia
	/*
	 * bd1 - algorismo significativo 	1
	 * bd2 - algorismo significativo	0
	 * bd3 - factor de multiplicašao	+100
	 * bd4 - tolerancia do componente	~ 5%
	 * 
	 */
	
	
	public Resistencia() {
		super();
	}
		
	public Resistencia(int posX, int posY, int valor, boolean montagem, String referencia) {
		super();
		this.posX = posX;
		this.posY = posY;
		this.valor = valor;
		this.montagem = montagem;
		this.referencia = referencia;
	}
	
	



	//METODOS SET
	//METODOS SET
	//METODOS SET
	
	//@param posX the posX to set
	public void setPosX(int posX) {
		this.posX = posX;
	}
	
	

	//@param posY the posY to set
			public void setPosY(int posY) {
		this.posY = posY;
	}
			
	// @param valor the valor to set
	public void setValor(int valor) {
		this.valor = valor;
	}
	
	//@param montagem the montagem to set
	public void setMontagem(boolean montagem) {
		this.montagem = montagem;
	}
	
	// @param referencia the referencia to set
	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}
	
	//@param terminal the terminal to set 
	public void setTerminal(int terminal) {
		this.terminal = terminal;
	}
	
	//@param corpoComp the corpoComp to set 
	public void setCorpoComp(int corpoComp) {
		this.corpoComp = corpoComp;
	}
	
	// @param corpoAlt the corpoAlt to set 
	public void setCorpoAlt(int corpoAlt) {
		this.corpoAlt = corpoAlt;
	}
	
	//@param bd1 the bd1 to set 
	public void setBd1(Color bd1) {
		this.bd1 = bd1;
	}
	
	//@param bd2 the bd2 to set
	public void setBd2(Color bd2) {
		this.bd2 = bd2;
	}
	
	// @param bd3 the bd3 to set
	public void setBd3(Color bd3) {
		this.bd3 = bd3;
	}
	
	// @param bd4 the bd4 to set
	public void setBd4(Color bd4) {
		this.bd4 = bd4;
	}
	
		
	//METODOS GET
	//METODOS GET
	//METODOS GET
	
	//@return the posX
	public int getPosX() {
		return posX;
	}
	
	//@return the posY 
	public int getPosY() {
		return posY;
	}
	
	//@return the valor
	public int getValor() {
		return valor;
	}
	
	//@return the montagem
	public boolean isMontagem() {
		return montagem;
	}
	
	//@return the referencia
	public String getReferencia() {
		return referencia;
	}
	
	//@return the terminal
	public int getTerminal() {
		return terminal;
	}
	
	//@return the corpoComp
	public int getCorpoComp() {
		return corpoComp;
	}
	
	// @return the corpoAlt

	public int getCorpoAlt() {
		return corpoAlt;
	}
	
	//@return the bd1
	public Color getBd1() {
		return bd1;
	}


	// @return the bd2
	public Color getBd2() {
		return bd2;
	}
	
	//@return the bd3

	public Color getBd3() {
		return bd3;
	}
	
	//@return the bd4

	public Color getBd4() {
		return bd4;
	}
	
	//OUTROS METODOS
	//OUTROS METODOS
	//OUTROS METODOS
	
	public void paint(Graphics g)
	{
		Graphics2D g2D = (Graphics2D) g;
		g2D.setStroke(new BasicStroke(1.8f));
		
		//Montagem a true -  componente horizontal. false - vetical
		if(this.montagem)
		{
			g2D.setColor(Color.black);
			//primeira linha
			g2D.drawLine(this.posX, this.posY, this.posX+this.terminal, this.posY);
			//retangulo
			g2D.setColor(new Color(255,228,196));
			g2D.fillRoundRect(this.posX+this.terminal, this.posY-this.alt/2 , this.comp, this.alt,5,5);
			
			//segunda linha
			g2D.setColor(Color.black);
			g2D.drawLine(this.posX+this.terminal+this.comp, this.posY, this.posX+this.terminal+this.comp+this.terminal, this.posY);
			//referencia
			g2D.drawString(this.referencia, this.posX+this.terminal+this.comp/2 , this.posY-this.alt);
			//valor
			g2D.drawString(String.valueOf(this.valor), this.posX+this.terminal+this.comp/3 , this.posY+this.alt);
		
			g2D.setColor(this.bd1);
			g2D.fillRect(this.posX+this.terminal+this.comp/2-3, this.posY-this.alt/2, 10, this.alt);
			
			g2D.setColor(this.bd2);
			g2D.fillRect(this.posX+this.terminal+this.comp/2+10, this.posY-this.alt/2, 5, this.alt);
			
			g2D.setColor(this.bd3);
			g2D.fillRect(this.posX+this.terminal+this.comp/2+17, this.posY-this.alt/2, 5, this.alt);
			
			g2D.setColor(this.bd4);
			g2D.fillRect(this.posX+this.terminal+this.comp/2+30, this.posY-this.alt/2, 10, this.alt);
		}
		else
		{
			g2D.setColor(Color.black);
			//primeira linha
			g2D.drawLine(this.posX, this.posY, this.posX, this.posY+this.terminal);
			//retangulo
			g2D.setColor(new Color(255,228,196));
			g2D.fillRoundRect(this.posX-this.alt/2, this.posY+this.terminal , this.alt, this.comp, 5,5);
			//segunda linha
			g2D.setColor(Color.black);
			g2D.drawLine(this.posX, this.posY+this.terminal+this.comp,this.posX , this.posY+this.terminal+this.comp+this.terminal);
			/*referencia
			g2D.drawString(this.referencia, this.posX+this.terminal+this.comp/2 , this.posY-this.alt);
			//valor
			g2D.drawString(String.valueOf(this.valor), this.posX+this.terminal+this.comp/3 , this.posY+this.alt);
		
			g2D.setColor(this.bd1);
			g2D.fillRect(this.posX+this.terminal+this.comp/2-3, this.posY-this.alt/2, 10, this.alt);
			
			g2D.setColor(this.bd2);
			g2D.fillRect(this.posX+this.terminal+this.comp/2+10, this.posY-this.alt/2, 5, this.alt);
			
			g2D.setColor(this.bd3);
			g2D.fillRect(this.posX+this.terminal+this.comp/2+17, this.posY-this.alt/2, 5, this.alt);
			
			g2D.setColor(this.bd4);
			g2D.fillRect(this.posX+this.terminal+this.comp/2+30, this.posY-this.alt/2, 10, this.alt);*/
		}
	}
	
}
