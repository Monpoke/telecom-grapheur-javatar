package modele;

import java.awt.Dimension;

public class Constantes {

	static Dimension dimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
	
	public static int width = (int) dimension.getWidth();
	public static int height = (int) dimension.getHeight()-35;
	
}
