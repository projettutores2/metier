/** Robot
  * date : 17/08/2019
  * @author : Equipe 14
  * @version 1
  */

public class Robot extends Pion
{
	private Cristal cristal;
	private Ordres[] algo;
	private Joueur joueur;

	private int direction;

	public Robot( int x, int y, int z, Joueur j, int dir )
	{
		super( x, y, z );

		this.joueur = j;
		this.direction = dir;

		this.algo = new Ordres[3];
	}

	public boolean deposer( Cristal c )
	{
		Base b = this.joueur.getBase();

		switch ( this.direction )
		{
			case 0: this.z--; break;
			case 1: this.x++; break;
			case 2: this.y--; break;
			case 3: this.z++; break;
			case 4: this.x--; break;
			case 5: this.y++; break;
		}

		for ( Pion p : this.joueur.getListePions() )
		{

		}
	}
}