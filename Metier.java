package TwinTinBots.metier;
import TwinTinBots.ihm.Controleur;
import TwinTinBots.ihm.Launcher;
import TwinTinBots.ihm.DemandeCristaux;
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
	private ArrayList<Cristal> cristals;
	private int                indJoueurActif;
	private int                nbTours;
	private int                tourFin;
	private ModifAlgo          modifAlgo, modifAlgo2;
	private String             scenario;
	private String             positionCristaux;
	private Victoire           victoire;

	public Metier(Controleur ctrl, String[] tabNoms)
	{
		//init
		this.ctrl=ctrl;
		this.positionCristaux="";
		this.joueurs = new ArrayList<Joueur>();
		this.pions   = new ArrayList<Pion>();
		this.cristals = new ArrayList<Cristal>();
		this.nbTours = 0;
		this.tourFin = 0;
		this.end = false;

		//a la charge de creer et donner les ordres
		if(! Controleur.DEBUG)
		{
			Regle.initialisation(this.joueurs,tabNoms.length,this.pions,this.cristals,this,"");
			for(int i = 0 ; i<tabNoms.length ; i++)
				this.joueurs.get(i).setNom(tabNoms[i]);
		}
		else
		{
			this.scenario = tabNoms[0];
			Regle.initialisation(this.joueurs,6,this.pions,this.cristals,this,scenario);
		}
		this.victoire = new Victoire(this.pions, this.joueurs.size(), this);
	}

	
	public void jouer()
	{
		Ordre tmp;
		boolean erreur = false;
		this.nbTours++;
		if(Controleur.DEBUG)
		{
			this.debugActif(scenario);
		}
		for(Joueur joueur : this.joueurs)
		{
			this.modifAlgo = new ModifAlgo();
			this.modifAlgo2 = new ModifAlgo();
			this.indJoueurActif = this.joueurs.indexOf(joueur);
			this.ctrl.afficherJeu();
			this.typeAction(this.modifAlgo);
			if(this.nbTours==1)
				this.typeAction(this.modifAlgo2);

			//Activation des algorithme

			
			this.executionAlgo(joueur, 0);
			this.executionAlgo(joueur, 1);
			for(Pion pion : pions)
				System.out.println(pion);

			//Vérification de fin de partie
			if(this.cristals.isEmpty() && this.tourFin==0)
				this.tourFin = this.nbTours + 3;

			this.victoire.setEnd(this.nbTours==this.tourFin);
			this.victoire.estFinie();
			if(this.end) break;
		}
	}

	private void typeAction(ModifAlgo modifAlgo)
	{
		do { System.out.print(""); } while(!modifAlgo.getReady());

		switch (modifAlgo.getType())
		{
			case 1: this.placerOrdre(modifAlgo)     ; break;
			case 2: this.permuterOrdre(modifAlgo)   ; break;
			case 3: this.retirerOrdre(modifAlgo, -1); break;
			case 4: this.redemarrerRobot(modifAlgo) ; break;
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
				this.nbTours++;
				chaine = sc.nextLine().split(":");
				algo = chaine[1].split("|");
				//nom et robot du joueur
				Joueur joueur =  this.getJoueurs(chaine[0].substring(7));
				this.ordreDebug(joueur,chaine[1].split("\\|"),0);
				this.ordreDebug(joueur,chaine[2].split("\\|"),1);
				this.ctrl.afficherJeu();

				//Vérification de fin de partie
				//if(this.cristals.isEmpty() && this.tourFin==0)
				//	this.tourFin = this.nbTours + 3;

				//this.victoire.setEnd(this.nbTours==this.tourFin);
				this.victoire.estFinie();
				if(this.end) break;
			}
		  sc.close();
		}
		 catch(Exception e) { e.printStackTrace(); }
		 this.victoire.setEnd(true);
		this.victoire.estFinie();
		 this.end=true;
	}

	private void executionAlgo(Joueur joueur, int idRobot)
	{
		this.ctrl.afficherJeu();
		for(Ordre ordre : joueur.getRobot(idRobot).getAlgo())
			if(ordre!=null)
			{
				try{Thread.sleep(500);}
				catch(Exception e){}
				ordre.action(joueur.getRobot(idRobot));
				this.ctrl.afficherJeu();
			}
		this.ctrl.afficherJeu();
	}

	private void placerOrdre(ModifAlgo modifAlgo)
	{
		int nouvelOrdre = modifAlgo.getNewOrdre();
		Joueur joueur = modifAlgo.getJoueur();
		Robot robot = modifAlgo.getRobot();
		int   slot = modifAlgo.getSlot();
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
	}

	private void permuterOrdre(ModifAlgo modifAlgo)
	{
		int slot1, slot2;

		slot1 = modifAlgo.getSlot();
		slot2 = modifAlgo.getSlot2();

		Joueur joueur = modifAlgo.getJoueur();
		Robot  robot  = modifAlgo.getRobot();

		Ordre tmp = robot.getOrdre(slot1);
		robot.setOrdre(slot1, robot.getOrdre(slot2));
		robot.setOrdre(slot2, tmp);
	}

	private void retirerOrdre(ModifAlgo modifAlgo, int slot)
	{
		if(slot==-1)
		{
			slot = modifAlgo.getSlot();
		}

		modifAlgo.getJoueur().ajouter(modifAlgo.getRobot().getOrdre(slot));
		modifAlgo.getRobot().setOrdre(slot, null);
	}

	private void redemarrerRobot(ModifAlgo modifAlgo)
	{
		for(int i=0 ;  i<modifAlgo.getRobot().getAlgo().length ; i++)
		{
			retirerOrdre(modifAlgo, i);
		}
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
		return /*new Joueur(*/this.joueurs.get(this.indJoueurActif);//);
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

	public String getGagnant()
	{
		String gagnant = "";
		if(this.victoire.getEgalite())
		{
			gagnant += "Egalité entre : ";
			for(Joueur joueur : this.victoire.getGagnants())
				gagnant+= joueur.getNom() + " ";
		}
		else
			gagnant+=this.victoire.getJoueurGagnant().getNom();
		return gagnant;
	}
	
	public ArrayList<Pion> getListePions()
	{
		return this.pions;
	}

	public ModifAlgo getModifAlgo()
	{
		return this.modifAlgo;
	}

	public ModifAlgo getModifAlgo2()
	{
		return this.modifAlgo2;
	}

	public boolean getStockVide() { return this.cristals.isEmpty(); }

	public void spawnCristal()
	{
		boolean actif = false;
		if(!this.cristals.isEmpty())
		{
			for(Pion pion : this.pions)
			{
				if(pion.collision(this.cristals.get(0)))
				{
					actif=true;
					break;
				}
			}
			if(actif)
				this.popCristal();
			else
				this.pions.add(this.cristals.remove(0));
		}

	}
	public void setPositionCristaux(String position)
	{
		this.positionCristaux=position ;
	}

	public void setEnd(boolean b)
	{
		this.end = b;
	}

	private void popCristal()
	{
		Cristal cristal = this.cristals.remove(0);
		boolean bonPlacement ;
		do
		{
			bonPlacement = true ;

			new DemandeCristaux(this.ctrl);
			while(this.positionCristaux.equals(""))
			{
				System.out.print("");
			}

			int x,y,z ;
			x=y=z=0;
			switch(this.positionCristaux)
			{
				case"Nord-Est"   : x=1  ; y=0  ; z=-1 ; break ;
				case"Est"        : x=1  ; y=-1 ; z=0  ; break ;
				case"Sud-Est"    : x=0  ; y=-1 ; z=1  ; break ;
				case"Sud-Ouest"  : x=-1 ; y=0  ; z=+1 ; break ;
				case"Ouest"      : x=-1 ; y=+1 ; z=0  ; break ;
				case"Nord-Ouest" : x=0  ; y=+1 ; z=-1 ; break ;
			}
			cristal.setPos(x,y,z);
			for(Pion pion : this.pions)
			{
				if(pion.collision(cristal)) bonPlacement =false;
			}
			this.positionCristaux= "";
		}
		while(!bonPlacement);

		this.pions.add(cristal);
	}
}
