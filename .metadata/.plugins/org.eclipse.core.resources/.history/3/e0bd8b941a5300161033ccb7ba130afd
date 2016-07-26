package ufcd;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class AlterarAvaria extends JFrame {

	JPanel contentPane;
	//Painel NovaAvaria
	JPanel altAvaria;
	JLabel lbl;
	JTextField tfAltTipo, tfAltDesc, tfaltPreco, tfAltData, tfAltCliente;
	JTextArea taAltDesc;
	JButton btAltRegisto, btAltCancelar;
	
	/**
	 * Create the frame.
	 */
	public AlterarAvaria() {
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
				
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(300, 260, 1000, 550);
		
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AlterarAvaria frame = new AlterarAvaria();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
}
