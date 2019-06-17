/** Pion
  * date : 17/08/2019
  * @author : Equipe 14
  * @version 1
  */

public abstract class Pion
{
	//x, y et z sont les coordonnées du pion dans le repère hexagonal
	protected int x;
	protected int y;
	protected int z;

	public Pion( int x, int y, int z )
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public int[] getCoords()
	{
		return new int[]{ this.x, this.y, this.z };
	}

	public boolean collision( int[] setCoord, Pion p )
	{
		return ( setCoord[0] == p.x &&
			     setCoord[1] == p.y &&
			     setCoord[2] == p.z    );
	}

	public Pion getPionCollision( int[] setCoord, Joueur joueur )
	{
		for ( Pion p : joueur.getListePions() )
		{
			if ( collision( setCoord, p ) )
			{
				return p;
			}
		}

		return null;
	}

	public void setPos( int x, int y, int z )
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public String toString()
	{
		String s = this.getClass().getSimpleName();

		s += " - " + this.x + ":" + this.y + ":" + this.z;
	}
}
