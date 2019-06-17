/** Robot
  * date : 17/08/2019
  * @author : Equipe 14
  * @version 1
  */

public class Robot extends Pion
{
	private Cristal cristal;
	private Ordre[] algo;
	private Joueur joueur;

	private int direction;

	public Robot( int x, int y, int z, Joueur j, int dir )
	{
		super( x, y, z );

		this.joueur = j;
		this.direction = dir;

		this.algo = new Ordre[3];
	}

	public Robot( Joueur j )
	{
		this( 0, 0, 0, j, 0 );
	}

	public void avancer()
	{
		//Déplacement du pion pour vérifier qu'il y a une collision après
		switch ( this.direction ) {
			case 0: this.z--;this.x++; break;
			case 1: this.x++;this.y--; break;
			case 2: this.y--;this.z++; break;
			case 3: this.z++;this.x--; break;
			case 4: this.x--;this.y--; break;
			case 5: this.y++;this.z--; break;
		}
	}


	public void changerDirection( char sens )
	{
		switch ( sens )
		{
			case 'D':this.direction++; break;
			case 'G':this.direction--; break;
		}

		this.direction %= 6;
	}

	public void charger()
	{
		
	}

	public void decharger( Cristal c )
	{

	}
}
