package vue;


import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import modele.Constantes;

public class FenetreContener extends JFrame{ // Fenetre dans laquelle est contenu tous le projet

	JTextField text;
	
	public FenetreContener() {
		this.setSize(Constantes.width,Constantes.height);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.initComposent();
		this.setResizable(false);
		this.setVisible(true);
	}
	
	public void initComposent(){
		Container grandContainer = getContentPane();
		grandContainer.setLayout(new BorderLayout());
		Container petitContainer = new Container();
		petitContainer.setLayout(new BorderLayout());
		text = new JTextField();
		text.setPreferredSize(new Dimension(this.getWidth()-60, 30));
		petitContainer.add("East", text);
		petitContainer.add("West", new JLabel(" f(x) = "));
		grandContainer.add("South",petitContainer);
	}
	
	public void addControllerOnJTextField(ActionListener listener){
		text.addActionListener(listener);
	}
		
}