package TwinTinBots.metier;
import java.util.ArrayList;

public class Victoire
{
	private Metier          metier;
	//score si depasser victoire auto
	private int             scoreVictoire;
	//gagnant
	private Joueur          joueurGagnant;
	//si egaliter 
	private boolean         egalite ;
	private Joueur          egaliteJ1;
	private Joueur          egaliteJ2;
	//list des bases
	private ArrayList<Base> bases ;

	public Victoire(ArrayList<Pion> pions,int nbJoueur,Metier metier)
	{
		this.metier=metier;
		for(Pion pion : pions)
			if(pion instanceof Base) bases.add((Base)pion);

		switch(nbJoueur)
		{
			case 2 : this.scoreVictoire = 11; break; 
			case 3 : this.scoreVictoire = 10; break; 
			case 4 : this.scoreVictoire =  9; break; 
			case 5 : this.scoreVictoire =  8; break; 
			case 6 : this.scoreVictoire =  7; break; 
		}
	}

	public void estFinie()
	{
		//si un joueur depasse le score de victoire
		for(Base base : this.bases)
		{
			if(base.getScore() >= this.scoreVictoire)
			{
				this.gagner(base.getJoueur());
				return ;
			}
		}

		//on controle si un joueur a un score le plus elever
		int    scoreMax = 0 ;
		int    tmp;
		Joueur gagnant = null ;
		Joueur egalite = null ;
		for(Base base : this.bases)
		{
			tmp=base.getScore()+base.getJoueur().getScoreRobots();
			if(tmp>scoreMax)
			{
				scoreMax = tmp;
				gagnant  = base.getJoueur();
				egalite  = null ;
			}
			else if (tmp == scoreMax)
			{
				egalite = base.getJoueur() ;
			}
		}
		if(egalite == null )
		{
			this.gagner(gagnant);
			return ; 
		}

		// on compare le nombre de cristaux bleu et vert
		int scoreGagnantBleu =0 ;
		int scoreEgaliteBleu =0 ;
		int scoreGagnantVert =0 ;
		int scoreEgaliteVert =0 ;
		for(Base base : this.bases)
		{
			if(base.getJoueur() == gagnant)
			{ 
				scoreGagnantBleu = base.getNbCristal(Regle.CRISTAL_BLEU);
				scoreGagnantVert = base.getNbCristal(Regle.CRISTAL_VERT);
			}
			if(base.getJoueur() == egalite)
			{
				scoreEgaliteBleu = base.getNbCristal(Regle.CRISTAL_BLEU);
				scoreEgaliteVert = base.getNbCristal(Regle.CRISTAL_VERT);
			}
		}

		if(scoreGagnantBleu > scoreEgaliteBleu)           this.gagner(gagnant);
		else if (scoreGagnantBleu < scoreEgaliteBleu)     this.gagner(egalite);
		else
		{
			if(scoreGagnantBleu > scoreEgaliteBleu)       this.gagner(gagnant);
			else if (scoreGagnantBleu < scoreEgaliteBleu) this.gagner(egalite);
			else
			{
				this.egaliteJ1 = gagnant;
				this.egaliteJ2 = egalite;
			}
		}
	}

	private void gagner(Joueur joueur)
	{
		this.joueurGagnant = joueur ;
		this.metier.setEnd(true);
	}
	//---------------------------------------------------------------------------
	//                                      GET
	public Joueur  getJoueurGagnant() { return this.joueurGagnant; }

}