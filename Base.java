/** Base
  * date : 17/08/2019
  * @author : Equipe 14
  * @version 1
  */

import java.util.ArrayList;

public class Base extends Pion
{
	private ArrayList<Cristal> stockCristaux;
	private Joueur joueur;

	public Base( Joueur j, int x, int y, int z )
	{
		super( x, y, z );

		this.joueur = j;
		this.stockCristaux = new ArrayList<Cristal>();
	}

	public void stocker( Cristal c )
	{
		this.stockCristaux.add( c );
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

	public String toString()
	{
		return "Base - coords " + this.x + ":" + this.y + ":" + this.z + "\n\tScore : " + this.getScore() + "\n\tJoueur : " + this.joueur.getNom();
	}
}