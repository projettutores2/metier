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

		//demande et cree les joueurs
		int nbJoueur=this.ctrl.nombreDeJoueur();
		for (int i = 0; i < nbJoueur; i++) 
		{
			this.joueurs.add(new Joueur(this));
		}
		//a la charge de creer et donner les ordres
		Regle.initialisation(joueurs);
	}

	public void jouer()
	{
		/*String algorithme = "";
		for(Joueur joueur : joueurs)
		{
			if(!this.end)
			{
				int i = 0;
				System.out.println("Algorithme Robot 1 : ");
				for(Ordre ordre : joueur.getRobot(0).getAlgo())
					System.out.println("\t" + (++i) + ordre.toString());
				System.out.println("Algorithme Robot 2 : ");
				for(Ordre ordre : joueur.getRobot(1).getAlgo())
					System.out.println("\t" + (++i) + ordre.toString());
				System.out.println("Quelle action faire ? \n"                     + 
									"1 - Placer un ordre \n"                     +
									"2 - Permuter deux ordres \n"                 +
									"3 - Retirer un ordre \n"                    +
									"4 - Retirer tous les ordres d'un robot \n" +
									"5 - Ne rien faire \n"                        );
									
				int choix = Clavier.lire_int();
				
				if(choix<1 || choix>5) choix=5;
				
				switch (choix)
				{
					case 1:
						System.out.println("Quel ordre ajouter ?");
						for(Ordre ordre : joueur.getStockOrdres())
							System.out.println("\t" + (++i) + ordre.toString());
						int nouvelOrdre = Clavier.lire_int();
						System.out.println("Dans quel slot voulez vous l'ajouter ?");
						int slot    = Clavier.lire_int();
						joueur.getRobot((slot < 3 ? 0 : 1)).getOrdre(slot%3);
						if(
						break;
				}
			}
		}
		*/
	}

	//--------------------------------------------------------------
	//                             GET
	public boolean getEnd() {return this.end ;}
	
	public ArrayList<Pion> getListePion()
	{
		return this.pions;
	}

}