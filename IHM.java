package TwinTinBots.ihm;
import TwinTinBots.metier.Joueur;
import TwinTinBots.metier.Ordre;
import iut.algo.*;

public class IHM
{
	private Controleur ctrl ;

	public IHM(Controleur ctrl)
	{
		this.ctrl = ctrl;
	}

	public void afficherAlgo(Joueur joueur)
	{
		int i = 0;
		System.out.println(joueur.getNom());
		System.out.println("Algorithme Robot 1 : ");
		for(Ordre ordre : joueur.getRobot(0).getAlgo())
		{
			System.out.print("\t" + (++i));
			if(ordre!=null)
				System.out.println(" - " + ordre.toString());
			else
				System.out.println();
		}
		System.out.println("Algorithme Robot 2 : ");
		for(Ordre ordre : joueur.getRobot(1).getAlgo())
		{
			System.out.print("\t" + (++i));
			if(ordre!=null)
				System.out.println(" - " + ordre.toString());
			else
				System.out.println();
		}
	}

	public int demandeAction()
	{
		System.out.println("Quelle action faire ? \n"                  + 
						   "1 - Placer un ordre \n"                    +
						   "2 - Permuter deux ordres \n"               +
						   "3 - Retirer un ordre \n"                   +
						   "4 - Retirer tous les ordres d'un robot \n" +
						   "5 - Ne rien faire \n"                        );
										
		return Clavier.lire_int();
	}

	public int choisirSlot()
	{
		System.out.println("Choisir un slot :");
		return Clavier.lire_int()-1;
	}

	public int choisirRobot()
	{
		System.out.println("Choisir un robot");
		return Clavier.lire_int()-1;
	}

	public void afficherStockJoueur(Joueur joueur)
	{
		int i = 0;
		System.out.println(joueur.getNom());
		for(Ordre ordre : joueur.getStockOrdres())
			System.out.println("\t" + (++i) + " - " + ordre.toString());
	}

	public int choisirOrdreJoueur(Joueur joueur)
	{
		System.out.println("Quel ordre ajouter ?");
						
		this.afficherStockJoueur(new Joueur(joueur));
						
		return Clavier.lire_int()-1;
	}

	public void afficherJeu()
	{
		for(String pion : this.ctrl.getListePions())
			System.out.println(pion);
	}
}