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
		int choix, nouvelOrdre, slot1, slot2;
		Ordre tmp;
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
						nouvelOrdre = this.ctrl.choisirOrdreJoueur(new Joueur(joueur));
						
						slot1 = this.ctrl.choisirSlot();
						tmp = joueur.getRobot((slot1 < 3 ? 0 : 1)).getOrdre(slot1%3);
						if(tmp!=null)
						{
							joueur.ajouter(tmp);
							tmp = joueur.retirer(nouvelOrdre);
							joueur.getRobot((slot1 < 3 ? 0 : 1)).setOrdre(slot1%3, tmp);
						}
						else
						{
							joueur.getRobot((slot1 < 3 ? 0 : 1)).setOrdre(slot1%3,joueur.retirer(nouvelOrdre));
						}
						break;
					case 2:
						do
						{
							slot1 = this.ctrl.choisirSlot();
							slot2 = this.ctrl.choisirSlot();
						}
						while(slot1<3 != slot2<3);
						tmp = joueur.getRobot((slot1 < 3 ? 0 : 1)).getOrdre(slot1%3);
						joueur.getRobot((slot1 < 3 ? 0 : 1)).setOrdre(slot1%3, joueur.getRobot((slot2 < 3 ? 0 : 1)).getOrdre(slot2%3));
						joueur.getRobot((slot2 < 3 ? 0 : 1)).setOrdre(slot2%3, tmp);
						break;
					case 3:
						do
						{
							slot1 = this.ctrl.choisirSlot();
							tmp   = joueur.getRobot((slot1 < 3 ? 0 : 1)).getOrdre(slot1%3);
						}
						while(tmp==null);

						joueur.ajouter(tmp);
						joueur.getRobot((slot1 < 3 ? 0 : 1)).setOrdre(slot1%3, null);
						break;
					default :
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