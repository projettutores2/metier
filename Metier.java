package TwinTinBots.metier;
import TwinTinBots.ihm.Controleur;
import TwinTinBots.ihm.Launcher;
import java.util.ArrayList;
import iut.algo.*;
import java.util.Scanner;
import java.io.FileReader;

public class Metier
{
	private Controleur         ctrl ;
	private boolean            end;
	private ArrayList<Joueur>  joueurs ;
	private ArrayList<Pion>    pions;
	private int                indJoueurActif;
	private int                nbTours;

	public Metier(Controleur ctrl, String[] tabNoms)
	{
		//init
		this.ctrl=ctrl;
		this.joueurs = new ArrayList<Joueur>();
		this.pions   = new ArrayList<Pion>();
		this.nbTours = 0;

		//a la charge de creer et donner les ordres
		Regle.initialisation(this.joueurs,2,this.pions,this);
		for(int i = 0 ; i<tabNoms.length ; i++)
			this.joueurs.get(i).setNom(tabNoms[i]);
	}

	
	public void jouer()
	{
		int choix;
		Ordre tmp;
		boolean erreur;
		nbTours++;
		for(Joueur joueur : this.joueurs)
		{
			this.indJoueurActif = this.joueurs.indexOf(joueur);
			this.ctrl.afficherJeu();
			if(! Controleur.DEBUG)
			{
				//Choix du joueur pour la modification de son algorithme
				this.ctrl.afficherAlgo(new Joueur(joueur));
				do
				{
					do
					{
						choix = this.ctrl.demandeAction();
					}
					while(choix<1 || choix>5);
				
					switch (choix)
					{
						case 1: erreur=this.placerOrdre(joueur); break;
						case 2: erreur=this.permuterOrdre(joueur); break;
						case 3: erreur=this.retirerOrdre(joueur, -1); break;
						case 4: erreur=this.redemarrerRobot(joueur); break;
						default :
							erreur = false;
							break;
					}
				}
				while(erreur);

				//Activation des algorithme
				this.executionAlgo(joueur, 0);
				this.executionAlgo(joueur, 1);		

				//Vérification de fin de partie
				if(this.end) break;
			} else debugActif();
		}
	}

	private void debugActif()
	{
	    try
	    {
		    String scenario = "1";
			Scanner sc = new Scanner (new FileReader ("TwinTinBots/teste/teste"+scenario+".data"));
		    String[] chaine ;
		    String[] algo ;
		    int cpt ;

	    	while ( sc.hasNextLine() )
	    	{
		        chaine = sc.nextLine().split(":");
		        algo = chaine[1].split("|");
		        //nom et robot du joueur
				Joueur joueur =  this.getJoueurs(chaine[0].substring(7));
				for(int i = 0 ; i < algo.length ; i++)
				{
					int indiceRobot = i < 3 ? 0 : 1 ;
					Ordre ordre = null;
					switch(algo[i])
					{
						case "A1" : ordre = new Avancer(1);          break;
						case "A2" : ordre = new Avancer(2);          break;
						case "RD" : ordre = new Rotation('D');       break;
						case "RG" : ordre = new Rotation('G');       break;
						case "CH" : ordre = new Charger();           break;
						case "DE" : ordre = new Decharger();         break;
						case "--" : ordre = null;                    break;
					}
					joueur.getRobot(indiceRobot).setOrdre(i % 3,ordre);
				}
				this.ctrl.afficherJeu();
				this.executionAlgo(joueur, 0);
				this.executionAlgo(joueur, 1);
				this.ctrl.afficherJeu();
	    	}
	      sc.close();
	    }
	   	 catch(Exception e) { e.printStackTrace(); }
	}

	private void executionAlgo(Joueur joueur, int idRobot)
	{
		for(Ordre ordre : joueur.getRobot(idRobot).getAlgo())
				if(ordre!=null)
				{
					try{Thread.sleep(500);}
					catch(Exception e){}
					ordre.action(joueur.getRobot(idRobot));
					//this.ctrl.afficherJeu();
				}
	}

	private boolean placerOrdre(Joueur joueur)
	{
		int nouvelOrdre = this.ctrl.choisirOrdreJoueur(new Joueur(joueur));
						
		int   slot = this.ctrl.choisirSlot();
		if( slot<0 || slot>5 ) return true;
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
		return false;
	}

	private boolean permuterOrdre(Joueur joueur)
	{
		int slot1, slot2;

		slot1 = this.ctrl.choisirSlot();
		slot2 = this.ctrl.choisirSlot();
	
		if(slot1<0 || slot1 >5 ||
		   slot2<0 || slot2 >5 ||
		   (slot1<3 != slot2<3)) return true;

		Ordre tmp = joueur.getRobot((slot1 < 3 ? 0 : 1)).getOrdre(slot1%3);
		joueur.getRobot((slot1 < 3 ? 0 : 1)).setOrdre(slot1%3, joueur.getRobot((slot2 < 3 ? 0 : 1)).getOrdre(slot2%3));
		joueur.getRobot((slot2 < 3 ? 0 : 1)).setOrdre(slot2%3, tmp);
		return false;
	}

	private boolean retirerOrdre(Joueur joueur, int slot)
	{
		if(slot==-1)
		{

			slot = this.ctrl.choisirSlot();
			if (slot < 0 || slot > 5 ) return true;
		}

		joueur.ajouter(joueur.getRobot((slot < 3 ? 0 : 1)).getOrdre(slot%3));
		joueur.getRobot((slot < 3 ? 0 : 1)).setOrdre(slot%3, null);
		return false;
	}

	private boolean redemarrerRobot(Joueur joueur)
	{
		int robot;
			robot = this.ctrl.choisirRobot();
		
		if(robot!=0 && robot!=1) return true;

		for(int i=0 ;  i<joueur.getRobot(robot).getAlgo().length ; i++)
		{
			retirerOrdre(joueur, i+robot*3);
		}
		return false;
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

	public Robot[] getRobotsCourants()
	{
		return this.joueurs.get(this.indJoueurActif).getRobot();
	}

	public int getNbJoueurs()
	{
		return this.joueurs.size();
	}

	public int getNombreTour()
	{
		return this.nbTours;
	}

	public String getJoueurActif()
	{
		return this.joueurs.get(this.indJoueurActif).getNom();
	}

	public int getScoreJoueurActif()
	{
		for(Pion pion : this.pions)
			if(pion instanceof Base)
				if(((Base)pion).getJoueur()==this.joueurs.get(indJoueurActif))
					return ((Base)pion).getScore();
		return -1;
	}

	public boolean getEnd() {return this.end ;}
	
	public ArrayList<Pion> getListePions()
	{
		return this.pions;
	}

}
