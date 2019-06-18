/** Cristal
  * date : 17/08/2019
  * @author : Equipe 14
  * @version 1
  */
package TwinTinBots.metier;
public class Cristal extends Pion
{
	private int valeur;

	public Cristal( int x, int y, int z, int val )
	{
		super( x, y, z );

		this.valeur = val;
	}

	public int getValeur() { return this.valeur; }

	public String toString()
	{
		return "Cristal - coords " + this.x + ":" + this.y + ":" + this.z + "\n\tValeur : " + this.valeur;
	}
}