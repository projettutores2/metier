import java.util.ArrayList;
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
		String algorithme = "";
		for(Joueur joueur : joueurs)
		{
			if(!this.end)
			{
				System.out.println("Algorithme Robot 1 : ");
				for(Ordre ordre : joueur.getRobot(0).getOrdre())
					algorithme += "\n\t"  + ordre.toString();
				System.out.println(algorithme + "\nAlgorithme Robot 2 : ");
				algorithme = "";
				for(Ordre ordre : joueur.getRobot(1).getOrdre())
					algorithme += "\n\t"  + ordre.toString();
				
				
			}
		}
	}

	//--------------------------------------------------------------
	//                             GET
	public boolean getEnd() {return this.end ;}
	
	public ArrayList<Pion> getListePion()
	{
		return this.pions;
	}

}