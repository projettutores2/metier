package TwinTinBots.ihm;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;

import TwinTinBots.metier.Robot;
import TwinTinBots.metier.Joueur;

public class AffichageAlgo extends JDialog
{
	private Controleur ctrl;
	private ArrayList<Robot> lstRobots;

	private JScrollPane scrollPane;
	private ArrayList<PanAlgo> lstPAlgo;

	private FenPrincipale fenP;

	public AffichageAlgo( FenPrincipale parent, String titre, boolean modal, Controleur ctrl )
	{
		super ( parent, titre, modal );

		this.fenP = parent;
		this.ctrl = ctrl;

		this.lstRobots = new ArrayList<Robot>();
		this.lstPAlgo  = new ArrayList<PanAlgo>();

		//Création d'un panel avec une barre de défilement horizontale
		//La barre sera affichée au besoin
		this.scrollPane = new JScrollPane();
		this.scrollPane.setLayout( new ScrollPaneLayout() );
		this.scrollPane.setVerticalScrollBarPolicy( ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED );

		//Ajout des Robot de tous les joueurs dans la liste
		for ( Joueur joueur : this.ctrl.getMetier().getListJoueur() )
		{
			this.lstRobots.add( joueur.getRobot(0) );
			this.lstRobots.add( joueur.getRobot(1) );
		}

		this.setLayout( new GridLayout( this.lstRobots.size(), 20 ,1 ,20 ) );

		//Création des PanAlgo
		for ( Robot robot : this.lstRobots )
		{
			this.lstPAlgo.add( new PanAlgo( robot.getJoueur().getNom(), robot.getAlgo() ) );
		}

		for ( PanAlgo pAlgo : this.lstPAlgo )
		{
			this.add( pAlgo );
		}

		//this.add( new JLabel("Test") );
		//this.add( this.scrollPane );

		this.setDefaultCloseOperation( this.DISPOSE_ON_CLOSE );
		//La fenêtre sera centrée par rapport à la fenêtre principale
		this.setLocationRelativeTo( null );
		this.setSize(800,600);
		this.setResizable(false);
	}
}