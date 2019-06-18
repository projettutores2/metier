package TwinTinBots.metier;
import TwinTinBots.ihm.Controleur;
import java.util.ArrayList;
import iut.algo.*;

public class Metier
{
	private Controleur         ctrl ;
	private boolean            end;
	private ArrayList<Joueur>  joueurs ;
	private ArrayList<Pion>    pions;

	public Metier(Controleur ctrl)
	{
		//init
		this.ctrl=ctrl;
		this.joueurs = new ArrayList<Joueur>();
		this.pions   = new ArrayList<Pion>();

		//a la charge de creer et donner les ordres
		Regle.initialisation(this.joueurs,2,this.pions,this);
	}

	public void jouer()
	{
		int choix;
		Ordre tmp;
		for(Joueur joueur : joueurs)
		{
			//Choix du joueur pour la modification de son algorithme

			this.ctrl.afficherAlgo(new Joueur(joueur));
			
			do
			{
				choix = this.ctrl.demandeAction();
			}
			while(choix<1 || choix>5);
			
			switch (choix)
			{
				case 1:						
					this.placerOrdre(joueur);
					break;
				case 2:
					this.permuterOrdre(joueur);
					break;
				case 3:
					this.retirerOrdre(joueur, -1);
					break;
				case 4:
					this.redemarrerRobot(joueur);
					break;
				default :
					break;
			}

			//Activation des algorithme


			//Vérification de fin de partie
			if(this.end) break;
		}
	}

	public void placerOrdre(Joueur joueur)
	{
		int nouvelOrdre = this.ctrl.choisirOrdreJoueur(new Joueur(joueur));
						
		int   slot = this.ctrl.choisirSlot();
		Ordre tmp  = joueur.getRobot((slot < 3 ? 0 : 1)).getOrdre(slot%3);
		if(tmp!=null)
		{
			joueur.ajouter(tmp);
			tmp = joueur.retirer(nouvelOrdre);
			joueur.getRobot((slot < 3 ? 0 : 1)).setOrdre(slot%3, tmp);
		}
		else
		{
			joueur.getRobot((slot < 3 ? 0 : 1)).setOrdre(slot%3,joueur.retirer(nouvelOrdre));
		}
	}

	public void permuterOrdre(Joueur joueur)
	{
		int slot1, slot2;
		do
		{
			slot1 = this.ctrl.choisirSlot();
			slot2 = this.ctrl.choisirSlot();
		}
		while(slot1<3 != slot2<3);

		Ordre tmp = joueur.getRobot((slot1 < 3 ? 0 : 1)).getOrdre(slot1%3);
		joueur.getRobot((slot1 < 3 ? 0 : 1)).setOrdre(slot1%3, joueur.getRobot((slot2 < 3 ? 0 : 1)).getOrdre(slot2%3));
		joueur.getRobot((slot2 < 3 ? 0 : 1)).setOrdre(slot2%3, tmp);
	}

	public void retirerOrdre(Joueur joueur, int slot)
	{
		if(slot==-1)
		{
			do
			{
				slot = this.ctrl.choisirSlot();
			}
			while(joueur.getRobot((slot < 3 ? 0 : 1)).getOrdre(slot%3)==null);
		}

		joueur.ajouter(joueur.getRobot((slot < 3 ? 0 : 1)).getOrdre(slot%3));
		joueur.getRobot((slot < 3 ? 0 : 1)).setOrdre(slot%3, null);
	}

	public void redemarrerRobot(Joueur joueur)
	{
		int robot;
		do
		{
			robot = this.ctrl.choisirRobot();
		}
		while(robot!=0 && robot!=1);

		for(int i=0 ;  i<joueur.getRobot(robot).getAlgo().length ; i++)
		{
			retirerOrdre(joueur, i+robot*3);
		}
	}


	//--------------------------------------------------------------
	//                             GET
	public Joueur getJoueurs(String nom)
	{
		for(Joueur joueur : this.joueurs)
		{
			if(joueur.getNom().equals(nom))
				return joueur;
		}
		return null ;
	}

	public boolean getEnd() {return this.end ;}
	
	public ArrayList<Pion> getListePions()
	{
		return this.pions;
	}

}
