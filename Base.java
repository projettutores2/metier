/** Base
  * date : 17/08/2019
  * @author : Equipe 14
  * @version 1
  */

import java.util.ArrayList;

public class Base extends Pion
{
	private ArrayList<Cristal> stockCristaux;

	public Base( int x, int y, int z )
	{
		super( x, y, z );

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
}