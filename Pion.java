/** Pion
  * date : 17/08/2019
  * @author : Equipe 14
  * @version 1
  */
package TwinTinBots.metier;

public abstract class Pion
{
	//x, y et z sont les coordonnées du pion dans le repère hexagonal
	protected int x;
	protected int y;
	protected int z;

	protected int xAxial;
	protected int yAxial;

	protected String adresseImage;

	public Pion( int x, int y, int z )
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.transformerCoordonnees();

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

	public int getXAxial() { return this.xAxial; }
	public int getYAxial() { return this.yAxial; }

	public String getAdresseImage() { return this.adresseImage; }

	public Pion getPionCollision( int[] setCoord, Joueur joueur )
	{
		for ( Pion pion : joueur.getListePions() )
		{
			if ( collision( setCoord, pion ) )
			{
				return pion;
			}
		}
		return null;
	}

	public void transformerCoordonnees()
	{
		this.xAxial = ((int)((this.x-this.y)*Math.sin(Math.PI*60/180)/(38 + 366)));
		this.yAxial = ((this.z * 38 * 3 + 500)/2);
	}

	public void setPos( int x, int y, int z )
	{
		this.x = x;
		this.y = y;
		this.z = z;

		this.transformerCoordonnees();
	}

	public String toString()
	{
		String s = this.getClass().getSimpleName();

		s += " - "; 
		s += String.format("%-2d", this.x) + ":";
		s += String.format("%-2d", this.y) + ":";
		s += String.format("%-2d", this.z);

		return s ;
	}
}
