package modele;

import java.awt.Dimension;

public class Constantes {

	static Dimension dimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
	
	public static final int width = (int) dimension.getWidth();
	public static final int height = (int) dimension.getHeight()-35;
	
}
