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
		this.victoire = new Victoire(pions,joueurs.size(),this);
	}

	
	public void jouer()
	{
		ModifAlgo modifAlgo;
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
						modifAlgo = this.getActualModif(this.ctrl.demandeModif());
					}
					while(modifAlgo.getType()<1 || modifAlgo.getType()>5);
				
					switch (modifAlgo.getType())
					{
						case 1: erreur=this.placerOrdre(modifAlgo); break;
						case 2: erreur=this.permuterOrdre(modifAlgo); break;
						case 3: erreur=this.retirerOrdre(modifAlgo, -1); break;
						case 4: erreur=this.redemarrerRobot(modifAlgo); break;
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
				this.victoire.estFinie();
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

	private boolean placerOrdre(ModifAlgo modifAlgo)
	{
		int nouvelOrdre = modifAlgo.getNewOrdre();
		Joueur joueur = modifAlgo.getJoueur();
		Robot robot = modifAlgo.getRobot();
		int   slot = modifAlgo.getSlot();
		if( slot<0 || slot>5 ) return true;
		Ordre tmp  = robot.getOrdre(slot);
		if(tmp!=null)
		{
			joueur.ajouter(tmp);
			tmp = joueur.retirer(nouvelOrdre);
			robot.setOrdre(slot, tmp);
		}
		else
		{
			robot.setOrdre(slot,joueur.retirer(nouvelOrdre));
		}
		return false;
	}

	private boolean permuterOrdre(ModifAlgo modifAlgo)
	{
		int slot1, slot2;

		slot1 = modifAlgo.getSlot();
		slot2 = modifAlgo.getSlot2();
	
		if(slot1<0 || slot1 >5 ||
		   slot2<0 || slot2 >5 ||
		   (slot1<3 != slot2<3)) return true;

		Joueur joueur = modifAlgo.getJoueur();
		Robot  robot  = modifAlgo.getRobot();

		Ordre tmp = robot.getOrdre(slot1);
		robot.setOrdre(slot1, robot.getOrdre(slot2));
		robot.setOrdre(slot2, tmp);
		return false;
	}

	private boolean retirerOrdre(ModifAlgo modifAlgo, int slot)
	{
		if(slot==-1)
		{
			slot = modifAlgo.getSlot();
			if (slot < 0 || slot > 5 ) return true;
		}

		modifAlgo.getJoueur().ajouter(modifAlgo.getRobot().getOrdre(slot));
		modifAlgo.getRobot().setOrdre(slot, null);
		return false;
	}

	private boolean redemarrerRobot(ModifAlgo modifAlgo)
	{
		for(int i=0 ;  i<modifAlgo.getRobot().getAlgo().length ; i++)
		{
			retirerOrdre(modifAlgo, i);
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

	public ModifAlgo getActualModif(ModifAlgo modifAlgo)
	{
		Joueur joueur;
		Robot  robot;
		for(Joueur actJoueur : this.joueurs)
		{
			if(modifAlgo.getJoueur().equals(actJoueur))
			{
				joueur = actJoueur;
				joueur.getRobot(0).equals(modifAlgo.getRobot()) ? robot = joueur.getRobot(0) : robot = joueur.getRobot(1);
			}
		}
		actModifAlgo = new ModifAlgo(joueur, robot, modifAlgo.slot(), modifAlgo.typeModif());
		if()
	}
}
