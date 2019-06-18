/** Robot
  * date : 17/08/2019
  * @author : Equipe 14
  * @version 1
  */
package TwinTinBots.metier;

public class Robot extends Pion
{
	private Cristal cristal;
	private Ordre[] algo;
	private Joueur joueur;

	private int direction;

	/*---------------*/
	/* CONSTRUCTEURS */
	/*---------------*/

	public Robot( int x, int y, int z, Joueur joueur, int dir )
	{
		super( x, y, z );

		this.joueur = joueur;
		this.direction = dir;

		this.cristal = null;
		this.algo = new Ordre[3];
	}

	/*---------------*/
	/*    GETTERS    */
	/*---------------*/

	public Ordre[] getAlgo()         { return this.algo;    }
	public Cristal getCristal()      { return this.cristal; }

	public int[] getProchainesCoords()
	{
		int[] coordTemp = this.getCoords();

		switch ( this.direction ) {
			case 0: coordTemp[2]--;coordTemp[0]++; break;
			case 1: coordTemp[0]++;coordTemp[1]--; break;
			case 2: coordTemp[1]--;coordTemp[2]++; break;
			case 3: coordTemp[2]++;coordTemp[0]--; break;
			case 4: coordTemp[0]--;coordTemp[1]++; break;
			case 5: coordTemp[1]++;coordTemp[2]--; break;
		}

		return coordTemp;
	}
	public Ordre   getOrdre( int indice ) 
	{ 
		if(indice < this.algo.length)
			return this.algo[indice]; 
		else return null;
	}



	/*---------------*/
	/*    SETTERS    */
	/*---------------*/

	public void setOrdre( int indice, Ordre ordre ) 
	{
		if(indice < this.algo.length)
			this.algo[indice] = ordre; 
	}



	/*---------------*/
	/*    AUTRES     */
	/*---------------*/

	public void avancer()
	{
		//Déplacement fictif du pion pour vérifier qu'il y a une collision après
		int[] coordTemp = this.getProchainesCoords();

		//Si le pion de la case devant n'est pas null (donc case occupée)
		if ( this.getPionCollision( coordTemp, this.joueur ) != null )
		{
			//Si la collision a lieu avec un Pion autre qu'une base
			if ( !( this.getPionCollision( coordTemp, this.joueur ) instanceof Base ) )
			{
				//On déplace le pion dans le sens de la direction
				switch ( this.direction ) {
					case 0: coordTemp[2]--;coordTemp[0]++; break;
					case 1: coordTemp[0]++;coordTemp[1]--; break;
					case 2: coordTemp[1]--;coordTemp[2]++; break;
					case 3: coordTemp[2]++;coordTemp[0]--; break;
					case 4: coordTemp[0]--;coordTemp[1]++; break;
					case 5: coordTemp[1]++;coordTemp[2]--; break;
				}

				//On vérifie que la place est libre pour le pion à pousser
				//Si oui, le pion à pousser est poussé et on déplace notre
				//robot aux anciennes coordonnées du pion
				if ( this.collision( coordTemp, this.getPionCollision( coordTemp, this.joueur ) ) )
				{
					this.getPionCollision( coordTemp, this.joueur ).setPos( coordTemp[0], 
					                                                        coordTemp[1],
					                                                        coordTemp[2] );

					//Déplacement du robot
					coordTemp = this.getProchainesCoords();
					this.x = coordTemp[0];
					this.y = coordTemp[1];
					this.z = coordTemp[2];
				}
			}
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
		int[] coordTemp = this.getProchainesCoords();

		if ( this.getPionCollision( coordTemp, this.joueur ) instanceof Cristal )
		{
			this.cristal = (Cristal)this.getPionCollision( coordTemp, this.joueur );
			//A tester
			//this.getPionCollision( coordTemp, this.joueur ) = null;

			//Autre moyen
			Pion pTemp = this.getPionCollision( coordTemp, this.joueur );
			for ( Pion p : this.joueur.getListePions() )
			{
				if ( p.equals( pTemp ) )
				{
					this.joueur.getListePions().remove( p );
				}
			}
		}
	}

	public void decharger()
	{
		int[] coordTemp = this.getProchainesCoords();

		//Si on lache le cristal sur une Base
		if ( this.getPionCollision( coordTemp, this.joueur ) instanceof Base )
		{
			((Base)( this.getPionCollision( coordTemp, this.joueur ) )).stocker( this.cristal );
			this.cristal = null;
		}
		else if ( this.getPionCollision( coordTemp, this.joueur ) == null )
		{
			this.joueur.getListePions().add( new Cristal( coordTemp[0],
			                                              coordTemp[1],
			                                              coordTemp[2],
			                                              this.cristal.getValeur() ) );
			this.cristal = null;
		}
	}

	public String toString()
	{
		return "Robot "+"du joueur "+ this.joueur +" - coords " + this.x + ":" + this.y + ":" + this.z;
	}
}