package TwinTinBots.ihm;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;

import TwinTinBots.metier.Ordre;
import TwinTinBots.metier.Robot;
import TwinTinBots.metier.Joueur;

public class PanAlgo extends JPanel
{
	public PanAlgo( String nomJoueur, Ordre[] algoRobot )
	{
		this.setLayout( new BorderLayout() );
		//this.setLayout( new GridLayout( 2, 1 ) );
		this.add( new PanInfoJoueur( nomJoueur ), BorderLayout.NORTH );
		this.add( new PanTabAlgo   ( algoRobot ), BorderLayout.SOUTH );
	}
}

class PanInfoJoueur extends JPanel
{
	private static int numRobot = 0;

	public PanInfoJoueur( String nomJoueur )
	{
		this.add( new JLabel( "Joueur " + (nomJoueur) + ", Robot " + ( PanInfoJoueur.numRobot + 1 ), SwingConstants.CENTER ) );
		this.setSize( 800, 50 );

		PanInfoJoueur.numRobot++;
		PanInfoJoueur.numRobot %= 2;
	}
}

class PanTabAlgo extends JPanel
{
	private final int NOMBRE_ORDRE_ROBOT = 3;
	private JLabel[] tabLabelOrdresRobot;

	public PanTabAlgo( Ordre[] algoRobot )
	{
		String path = "./TwinTinBots/img/";
		this.tabLabelOrdresRobot = new JLabel[NOMBRE_ORDRE_ROBOT];
		this.setLayout( new GridLayout( 1, NOMBRE_ORDRE_ROBOT ) );

		//Si l'ordre est null on met "vide.png", sinon on met l'image correspondant à l'ordre
		for (int i = 0; i<tabLabelOrdresRobot.length; i++)
		{
			if ( algoRobot[i] != null )
			{
				this.tabLabelOrdresRobot[i] = new JLabel( new ImageIcon( path + "" + algoRobot[i].getImgOrdre() ) );
			}
			else  this.tabLabelOrdresRobot[i]= new JLabel(new ImageIcon( path + "vide.png"));

			this.tabLabelOrdresRobot[i].setTransferHandler(new TransferHandler("icon"));
			this.tabLabelOrdresRobot[i].setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.GRAY));
			this.add( this.tabLabelOrdresRobot[i] );
		}
		this.setSize(800, 250);
	}
}
