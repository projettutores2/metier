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

	protected double xAxial;
	protected double yAxial;

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

	public double getXAxial() { return this.xAxial; }
	public double getYAxial() { return this.yAxial; }

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

	public double getDoubleDirection() { return Math.PI/3; }

	public void transformerCoordonnees()
	{
		//this.xAxial = ((this.x-this.y)*Math.sin(Math.PI*60/180))*(42.5)+472;
		this.xAxial = ((this.x-this.y)*38+472);
		//this.xAxial = ((this.x-this.y)*Math.sin(Math.PI*60/180)/(42.5) + 366);
		System.out.println(this.x-this.y);
		this.yAxial = ((this.z * 42.1 * 3 + 675)/2);
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
