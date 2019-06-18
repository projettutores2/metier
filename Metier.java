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

		for(Joueur joueur : joueurs)
		{
			this.ctrl.afficherStockJoueur(joueur);
		}
	}

	public void jouer()
	{
		int choix;
		for(Joueur joueur : joueurs)
		{
			if(!this.end)
			{
				this.ctrl.afficherAlgo(new Joueur(joueur));
				
				do
				{
					choix = this.ctrl.demandeAction();
				}
				while(choix<1 || choix>5);
				
				switch (choix)
				{
					case 1:
						System.out.println("Quel ordre ajouter ?");
						
						this.ctrl.afficherStockJoueur(new Joueur(joueur));
						
						int nouvelOrdre = Clavier.lire_int();
						
						System.out.println("Dans quel slot voulez vous l'ajouter ?");
						int slot    = Clavier.lire_int();
						Ordre tmp = joueur.getRobot((slot < 3 ? 0 : 1)).getOrdre(slot%3);
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
						break;
					case 2:
						
						break;
				}
			}
		}
	}

	public Joueur getJoueurs(String nom) { for(Joueur joueur : this.joueurs) { if(joueur.getNom().equals(nom)) return joueur; } return null ; }

	//--------------------------------------------------------------
	//                             GET
	public boolean getEnd() {return this.end ;}
	
	public ArrayList<Pion> getListePions()
	{
		return this.pions;
	}

}