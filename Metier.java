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
	private ModifAlgo          modifAlgo;
	private String             scenario;

	public Metier(Controleur ctrl, String[] tabNoms)
	{
		//init
		this.ctrl=ctrl;
		this.joueurs = new ArrayList<Joueur>();
		this.pions   = new ArrayList<Pion>();
		this.nbTours = 0;
		this.end = false;

		//a la charge de creer et donner les ordres
		if(! Controleur.DEBUG)
		{
			Regle.initialisation(this.joueurs,tabNoms.length,this.pions,this,"");
			System.out.println(tabNoms.length);
			for(int i = 0 ; i<tabNoms.length ; i++)
				this.joueurs.get(i).setNom(tabNoms[i]);
			//teste
			for(Joueur joueur : this.joueurs)
			{
				joueur.getRobot(0).setOrdre(0,new Avancer(1,"./TwinTinBots/img/imgOrdre0.png"));
				joueur.getRobot(1).setOrdre(0,new Avancer(1,"./TwinTinBots/img/imgOrdre0.png"));
			}
		}
		else
		{
			this.scenario = tabNoms[0];
			Regle.initialisation(this.joueurs,6,this.pions,this,scenario);
		}
	}

	
	public void jouer()
	{
		Ordre tmp;
		boolean erreur;
		nbTours++;
		if(Controleur.DEBUG)
		{
			this.debugActif(scenario);
		}
		for(Joueur joueur : this.joueurs)
		{
			System.out.println(joueur.getNom());
			this.modifAlgo = new ModifAlgo();
			this.indJoueurActif = this.joueurs.indexOf(joueur);
			this.ctrl.afficherJeu();
			do
			{
				do
				{
					System.out.print("");
				}
				while(!this.modifAlgo.getReady());

				System.out.println(this.modifAlgo.getJoueur());
				System.out.println(this.modifAlgo.getRobot());
				System.out.println(this.modifAlgo.getType());
				switch (this.modifAlgo.getType())
				{
					case 1: erreur=this.placerOrdre(this.modifAlgo); break;
					case 2: erreur=this.permuterOrdre(this.modifAlgo); break;
					case 3: erreur=this.retirerOrdre(this.modifAlgo, -1); break;
					case 4: erreur=this.redemarrerRobot(this.modifAlgo); break;
					default :
						erreur = false;
						break;
				}
			}
			while(erreur);

			//Activation des algorithme

			this.ctrl.afficherJeu();
			this.executionAlgo(joueur, 0);
			this.ctrl.afficherJeu();
			this.executionAlgo(joueur, 1);
			this.ctrl.afficherJeu();
			for(Pion pion : pions)
				System.out.println(pion);

			//Vérification de fin de partie
			if(this.end) break;
		}
	}

	private void debugActif(String scenario)
	{
		try
		{
			Scanner sc = new Scanner (new FileReader ("TwinTinBots/test/test_"+scenario+".data"));
			String[] chaine ;
			String[] algo ;
			int cpt ;

			while ( sc.hasNextLine() )
			{
				chaine = sc.nextLine().split(":");
				algo = chaine[1].split("|");
				//nom et robot du joueur
				Joueur joueur =  this.getJoueurs(chaine[0].substring(7));
				System.out.println(joueur);
				this.ordreDebug(joueur,chaine[1].split("\\|"),0);
				this.ordreDebug(joueur,chaine[2].split("\\|"),1);
				this.ctrl.afficherJeu();
			}
		  sc.close();
		}
		 catch(Exception e) { e.printStackTrace(); }
		 this.end=true;
	}

	private void executionAlgo(Joueur joueur, int idRobot)
	{
		for(Ordre ordre : joueur.getRobot(idRobot).getAlgo())
				if(ordre!=null)
				{
					try{Thread.sleep(500);}
					catch(Exception e){}
					ordre.action(joueur.getRobot(idRobot));
					this.ctrl.afficherJeu();
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
		System.out.println(slot1 + " " + slot2);
	
		if(slot1<0 || slot1 >5 ||
		   slot2<0 || slot2 >5 ||
		   (slot1<3 != slot2<3)) return true;

		Joueur joueur = modifAlgo.getJoueur();
		Robot  robot  = modifAlgo.getRobot();

		Ordre tmp = robot.getOrdre(slot1);
		System.out.println(tmp);
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

	private void ordreDebug(Joueur joueur,String[] algo,int indiceRobot)
	{
		for(int i = 0 ; i < algo.length ; i++)
		{
			Ordre ordre = null;
			this.indJoueurActif = this.joueurs.indexOf(joueur);
			this.ctrl.afficherJeu();
			switch(algo[i])
			{
				case "A1" : ordre = new Avancer(1);    break ;
				case "A2" : ordre = new Avancer(2);    break ;
				case "RD" : ordre = new Rotation('D'); break ;
				case "RG" : ordre = new Rotation('G'); break ;
				case "CH" : ordre = new Charger();     break ;
				case "DE" : ordre = new Decharger();   break ;
				case "--" : ordre = null;              break ;
			}
			joueur.getRobot(indiceRobot).setOrdre(i % 3,ordre);
			this.ctrl.afficherJeu();
			System.out.println(joueur.getRobot(indiceRobot));
		}
		this.ctrl.afficherJeu();
		this.executionAlgo(joueur, indiceRobot);
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

	public ArrayList<Joueur> getListJoueur()
	{
		ArrayList<Joueur> tmp = new ArrayList<Joueur>();
		for(Joueur joueur : this.joueurs)
			tmp.add(new Joueur(joueur));
		return tmp;
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

	public Joueur getJoueurActif()
	{
		return new Joueur(this.joueurs.get(this.indJoueurActif));
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

	public ModifAlgo getModifAlgo()
	{
		return this.modifAlgo;
	}

	public ModifAlgo getActualModif(ModifAlgo modifAlgo)
	{
		Joueur joueur = null;
		Robot  robot  = null;
		for(Joueur actJoueur : this.joueurs)
		{
			if(modifAlgo.getJoueur().equals(actJoueur))
			{
				joueur = actJoueur;
				if (joueur.getRobot(0).equals(modifAlgo.getRobot()))
					robot = joueur.getRobot(0);
				else
					robot = joueur.getRobot(1);
			}
		}
		ModifAlgo actModifAlgo = new ModifAlgo(joueur, robot, modifAlgo.getSlot(), modifAlgo.getType());

		actModifAlgo.setNewOrdre(modifAlgo.getNewOrdre());
		actModifAlgo.setSlot2(modifAlgo.getSlot2());

		return actModifAlgo;
	}
}
