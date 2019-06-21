/*
 * date : 17/08/2019
 * @author : Equipe 14
 */
package TwinTinBots.metier;

public class Cristal extends Pion
{
	private int valeur;

	//--------------------------------------------------------------------------------
 	//                                     CONSTRUCTEUR
	public Cristal( int x, int y, int z, int valeur )
	{
		super( x, y, z );
		this.valeur = valeur;
		this.adresseImage = "TwinTinBots/img/"+ "cristal" + valeur + "Plateau.png";
	}

	//--------------------------------------------------------------------------------
 	//                                     PUBLIC

	public String toString()
	{
		String s = "Cristal - coords ";
		s += String.format("%2d", this.x) + ":";
		s += String.format("%2d", this.y) + ":";
		s += String.format("%2d", this.z);
		s += "\n\tValeur : " + this.valeur;
		return s;
	}

	//--------------------------------------------------------------------------------
	//                                     GET
	public int getValeur() { return this.valeur; }
}
