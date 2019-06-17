/** Base
  * date : 17/08/2019
  * @author : Equipe 14
  * @version 1
  */

import java.util.ArrayList;

public class Base extends Pion
{
	private int xdepot;
	private int ydepot;
	private int zdepot;

	private ArrayList<Cristal> stockCristaux;

	public Base( int x, int y, int z, int xdepot, int ydepot, int zdepot )
	{
		super( x, y, z );

		this.xdepot = xdepot;
		this.ydepot = ydepot;
		this.zdepot = zdepot;

		this.stockCristaux = new ArrayList<Cristal>();
	}

	public boolean robotSurCaseDepot( Robot r )
	{
		int[] coordDepot = new int[]{ this.xdepot, this.ydepot, this.zdepot };

		return collision( coordDepot, r );
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
}