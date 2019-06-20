/** Base
  * date : 17/08/2019
  * @author : Equipe 14
  * @version 1
  */
package TwinTinBots.metier;
import java.util.ArrayList;

public class Base extends Pion
{
	private ArrayList<Cristal> stockCristaux;
	private Joueur joueur;

	public Base( Joueur joueur, int x, int y, int z )
	{
		super( x, y, z );

		this.joueur = joueur;
		this.stockCristaux = new ArrayList<Cristal>();
		this.adresseImage = "TwinTinBots/img/"+  "base" + this.joueur.getIdJoueur() + ".png";
	}

	public void stocker( Cristal cristal )
	{
		if(cristal != null)
			this.stockCristaux.add( cristal );
	}

	public int getScore()
	{
		int retour = 0;

		for ( Cristal c : this.stockCristaux )
		{
			retour += c.getValeur();
		}
		return retour;
	}

	public Joueur getJoueur()
	{
		return this.joueur;
	}

	public String toString()
	{
		String s = "";
		s += "Base - coords " + this.x + ":" + this.y + ":" + this.z;
		s += "\n\tScore : " + this.getScore() + "\n\tJoueur : " + this.joueur.getNom() + "\n";
		for(Cristal cristal : this.stockCristaux)
			s+=cristal + "\n";
		return s;
	}
}