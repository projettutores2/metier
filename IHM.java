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
		System.out.println("Algorithme Robot 1 : ");
		for(Ordre ordre : joueur.getRobot(0).getAlgo())
			System.out.println("\t" + (++i) + ordre.toString());
		System.out.println("Algorithme Robot 2 : ");
		for(Ordre ordre : joueur.getRobot(1).getAlgo())
			System.out.println("\t" + (++i) + ordre.toString());
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

	public void afficherStockJoueur(Joueur joueur)
	{
		int i = 0;
		for(Ordre ordre : joueur.getStockOrdres())
			System.out.println("\t" + (++i) + ordre.toString());
	}
}