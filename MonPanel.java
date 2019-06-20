package TwinTinBots.ihm;
import javax.swing.JPanel;
import java.awt.*;
import java.lang.Object;
import TwinTinBots.metier.Pion;
import TwinTinBots.metier.Robot;
import javax.swing.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;


public class MonPanel extends JPanel
{
	Controleur ctrl;
	private final int LARGEUR = 1000, HAUTEUR = 722;


	public MonPanel ( Controleur ctrl )
	{
		this.ctrl = ctrl;
	}

	public void maj()
	{
		this.repaint();
	}

	public int getHauteur(){return this.HAUTEUR;}
	public int getLargeur(){return this.LARGEUR;}

	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;

		String sImage;

		// Ajout de l'image du fond
		
		Image img = new ImageIcon(new ImageIcon("TwinTinBots/img/plateau2-4.jpg").getImage().getScaledInstance(LARGEUR, HAUTEUR, Image.SCALE_DEFAULT)).getImage();


		g2.drawImage ( img, 0,0, this );
		//System.out.println("dessin 1");


		ArrayList<Pion> listePion =  ctrl.getListePions();
		//System.out.println(listePion.size());

		/*
		ROBOT     INDICE
		petit       1
		gros        2
		*/

		/*for (Pion p : listePion)
		{
			if (p != null)
			{
				if (p instanceof Robot)
				{		
					System.out.println(p.getAdresseImage());
					Image imgRobot = new ImageIcon(new ImageIcon(p.getAdresseImage()+".png").getImage().getScaledInstance(57, 60, Image.SCALE_DEFAULT)).getImage();
					//Image imgRobot = new ImageIcon(new ImageIcon("TwinTinBots/img/grosRobot"+j+".png").getImage().getScaledInstance(57, 60, Image.SCALE_DEFAULT)).getImage();
					AffineTransform rotation = new AffineTransform();
					rotation.translate(p.getXAxial() + 472, p.getYAxial() + 85);
					//rotation.rotate(p.getDoubleDirection(),(int)(imgRobot.getWidth(null)/2),(int)(imgRobot.getHeight(null)/2));
				
					System.out.println("affichage de cette image");
					System.out.println("-----");
					g2.drawImage (imgRobot, rotation , this);
				}
			}
		}*/
		String s = "Images affichées :\n";
		for (Pion p : listePion)
		{
			if (p != null)
			{				
				ImageIcon imgRobotIcon = new ImageIcon(new ImageIcon(p.getAdresseImage()).getImage().getScaledInstance(57, 60, Image.SCALE_DEFAULT));
				Image imgRobot = imgRobotIcon.getImage();
				AffineTransform rotation = new AffineTransform();
				//System.out.println(""+p.getXAxial() + ": "+p.getYAxial());
				rotation.translate(p.getXAxial(), p.getYAxial());
				//rotation.rotate(p.getDoubleDirection(),(int)(imgRobot.getWidth(null)/2),(int)(imgRobot.getHeight(null)/2));

				s += p.getAdresseImage()+"\n";
				g2.drawImage (imgRobot, rotation , this);				
			}
		}
		//System.out.println(s);

		/*
				** rotate (double theta, double x, double y)
				** theta est l'angle en radians
				** on a donc en possibilité de déplacement :
				** +-      60      |      120     |    180         DEGREE
				** soit 
				** +- 0.33*Math.PI | 0.67*Math.PI | 0.1*Math.PI    RADIAN
				*/
	}
}