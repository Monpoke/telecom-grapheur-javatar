package vue;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import controleur.TelecomGrapheurControleur;
import modele.Constantes;
import modele.TelecomGrapheurModele;

public class FenetreContener extends JFrame { // Fenetre dans laquelle est contenu tous le projet

    JTextField text = new JTextField();
    JButton btn = new JButton("Aide");
	TelecomGrapheurModele modele = new TelecomGrapheurModele();
	TelecomGrapheurControleur controleur = new TelecomGrapheurControleur(modele);
	TelecomGrapheurVue graph = new TelecomGrapheurVue(this,modele,controleur);

    public FenetreContener() {
        this.setSize(Constantes.width, Constantes.height);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.initComposent();
        this.setResizable(false);
        this.setVisible(true);
        this.setTitle("JGrapheur - Javatar");
    }

    public void initComposent() {
        Container grandContainer = getContentPane();
        grandContainer.setLayout(new BorderLayout());
        Container petitContainer = new Container();
        petitContainer.setLayout(new BorderLayout());
        text.setPreferredSize(new Dimension(this.getWidth() - 60, 30));
        petitContainer.add("East", text);
        petitContainer.add("West", new JLabel(" f(x) = "));
        grandContainer.add("South", petitContainer);
        btn.setPreferredSize(new Dimension(this.getWidth(), 30));
        grandContainer.add("North",btn);
        grandContainer.add("Center",graph);
    }

    public void addControllerOnJTextField(ActionListener listener) {
        text.addActionListener(listener);
    }
    
    public void addControllerOnBtn(ActionListener listener){
    	btn.addActionListener(listener);
    }

}
