/*
 * date : 17/08/2019
 * @author : Equipe 14
 */
package TwinTinBots.metier;

public abstract class Pion
{
	//x, y et z sont les coordonnées du pion dans le repère hexagonal
	protected int x;
	protected int y;
	protected int z;
	//coordonner en 2d pour l'affichage
	protected double xAxial;
	protected double yAxial;
	protected String adresseImage;

	//--------------------------------------------------------------------------------
 	//                                     CONSTRUCTEUR
	public Pion( int x, int y, int z )
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.transformerCoordonnees();

	}
	//--------------------------------------------------------------------------------
 	//                                     PUBLIC

	public boolean collision( int[] setCoord, Pion p )
	{
		return ( setCoord[0] == p.x &&
			     setCoord[1] == p.y &&
			     setCoord[2] == p.z    );
	}
	public boolean collision(Pion b )
	{
		return ( this.x == b.x &&
			     this.y == b.y &&
			     this.z == b.z    );
	}

	public void transformerCoordonnees()
	{
		this.xAxial = ((this.x-this.y)*38+472);
		this.yAxial = ((this.z * 42.1 * 3 + 675)/2);
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
	//--------------------------------------------------------------------------------
 	//                                     GET

	public int[]  getCoords          () { return new int[]{ this.x, this.y, this.z };}
	public double getXAxial          () { return this.xAxial;                        }
	public double getYAxial          () { return this.yAxial;                        }
	public double getDoubleDirection () { return Math.PI/3;                          }
	public String getAdresseImage    () { return this.adresseImage;                  }

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
	//--------------------------------------------------------------------------------
 	//                                     SET

	public void setPos( int x, int y, int z )
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.transformerCoordonnees();
	}
}