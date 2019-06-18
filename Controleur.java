package TwinTinBots.ihm;
import TwinTinBots.metier.Metier;
import TwinTinBots.metier.Joueur;
import TwinTinBots.metier.Pion;
import java.util.ArrayList;
public class Controleur
{
	private IHM ihm ;
	private Metier metier;

	public Controleur()
	{
		this.ihm    = new IHM(this) ;
		this.metier = new Metier(this);
		//boucle de jeu
		do
		{
			this.metier.jouer();
		}
		while(!this.metier.getEnd());
	}

	public static void main(String[] agrs)
	{
		new Controleur();
	}

	//--------------------------------------------------------------
	//                          LIEN AFFICHAGE
	public void afficherAlgo(Joueur joueur)        { this.ihm.afficherAlgo(joueur);              }
	public void afficherStockJoueur(Joueur joueur) { this.ihm.afficherStockJoueur(joueur);       }
	public int  demandeAction()                    { return this.ihm.demandeAction();            }
	public int  choisirSlot()                      { return this.ihm.choisirSlot();              }
	public int  choisirRobot()                     { return this.ihm.choisirRobot();             }
	public int  choisirOrdreJoueur(Joueur joueur)  { return this.ihm.choisirOrdreJoueur(joueur); }
	
	//--------------------------------------------------------------
	//                            LIEN SCANNER
	public int    nombreDeJoueur () { return 2 ;      }
	public String creeJoueur     () { return "teste ";}

	public void afficherJeu() { this.ihm.afficherJeu(); }

	//--------------------------------------------------------------
	//                             GET
	public IHM    getIhm()    { return this.ihm ;  }
	public Metier getMetier() { return this.metier;}
	public ArrayList<String> getListePions()
	{
		ArrayList<String> tmp = new ArrayList<String>();
		for(Pion pion : this.metier.getListePions())
			tmp.add(pion.toString());
		return tmp;

	}
}