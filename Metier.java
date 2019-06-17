import java.util.ArrayList;
public class Metier
{
	private Controleur         ctrl ;
	private boolean            end;
	private ArrayList<Joueur>  joueurs ;

	public Metier(Controleur ctrl)
	{
		//init
		this.ctrl=ctrl;
		this.joueurs = new ArrayList<Joueur>();

		//demande et cree les joueurs
		int nbJoueur=this.ctrl.nombreDeJoueur();
		for (int i = 0; i < nbJoueur; i++) 
		{
			this.joueurs.add(new Joueur(ctrl.creeJoueur()));
		}
		//a la charge de cree et donner les ordres
		Regle.initialisation(joueurs);
	}

	public void jouer()
	{

	}

	//--------------------------------------------------------------
	//                             GET
	public boolean getEnd() {return this.end ;}

}