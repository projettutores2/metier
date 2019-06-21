package TwinTinBots.ihm;
import TwinTinBots.metier.Metier;
import TwinTinBots.metier.Joueur;
import TwinTinBots.metier.Pion;
import TwinTinBots.metier.Ordre;
import TwinTinBots.metier.Robot;
import TwinTinBots.metier.ModifAlgo;

import java.util.ArrayList;
public class Controleur
{
	public static boolean DEBUG ;

	private FenPrincipale ihm ;
	private Metier metier;

	public Controleur(String[] tabNoms)
	{
		if(tabNoms.length == 1 ) DEBUG =true;
		this.metier = new Metier(this, tabNoms);
		this.ihm    = new FenPrincipale(this);

		//boucle de jeu
		do
		{
			this.metier.jouer();
		}
		while(!this.metier.getEnd());
	}

	/*public static void main(String[] agrs)
	{
		new Controleur(agrs[0]);
	}*/

	//--------------------------------------------------------------
	//                          LIEN AFFICHAGE
	public void afficherAlgo(Joueur joueur)        { /*this.ihm.afficherAlgo(joueur);        */}
	public void afficherStockJoueur(Joueur joueur) { /*this.ihm.afficherStockJoueur(joueur); */}
	public ModifAlgo  demandeModif()               { return this.ihm.demandeModif();           }
	
	//--------------------------------------------------------------
	//                            LIEN SCANNER
	public int    nombreDeJoueur () { return 2 ;      }
	public String creeJoueur     () { return "test ";}
	public void envoiChaine      (String chaine) { this.metier.setPositionCristaux(chaine);}

	public void afficherJeu() { this.ihm.afficherJeu(); }

	//--------------------------------------------------------------
	//                             GET
	public FenPrincipale getIhm()    { return this.ihm ;  }
	public Metier        getMetier() { return this.metier;}

	public ArrayList<String> getStockJoueur(Joueur joueur)
	{
		ArrayList<String> tmp = new ArrayList<String>();
		for(Ordre ordre : joueur.getStockOrdres())
			tmp.add(ordre.toString());
		return tmp;
	}

	public ArrayList<Pion> getListePions()
	{
		ArrayList<Pion> tmp = new ArrayList<Pion>();
		for(Pion pion : this.metier.getListePions())
			tmp.add(pion);
		return tmp;
	}

	public Robot[] getRobotsCourants()
	{
		return this.metier.getRobotsCourants();
	}
}