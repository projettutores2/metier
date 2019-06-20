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
	private Joueur  joueur;
	private String  type;

	private int direction;

	/*---------------*/
	/* CONSTRUCTEURS */
	/*---------------*/

	public Robot( int x, int y, int z, Joueur joueur, int dir, String type )
	{
		super( x, y, z );

		this.joueur = joueur;
		this.direction = dir;

		this.cristal = null;
		this.algo = new Ordre[3];

		this.adresseImage = "./TwinTinBots/img/"+ type + "Robot" + this.joueur.getIdJoueur() + ".png";
	}

	/*---------------*/
	/*    GETTERS    */
	/*---------------*/

	public Ordre[] getAlgo()         { return this.algo;    }
	public Cristal getCristal()      { return this.cristal; }

	public int[] getProchainesCoords(int[] coordTemp)
	{
		/* Le tableau coordTemp regroupe les trois coordonnées d'un robot
		 * Les indices correspondent à :
		 *    =>   0 : x
		 *    =>   1 : y
		 *    =>   2 : z
		 */
		if(coordTemp==null)
			coordTemp = this.getCoords();

		switch ( this.direction )
		{
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

	public Joueur getJoueur()
	{
		return new Joueur(this.joueur);
	}

	public double getDoubleDirection() { return super.getDoubleDirection()*this.direction; }

	/*---------------*/
	/*    SETTERS    */
	/*---------------*/

	public void setOrdre( int indice, Ordre ordre ) 
	{
		if(indice < this.algo.length)
			this.algo[indice] = ordre; 
	}

	public void setCristal( Cristal cristal ) { this.cristal = cristal; }



	/*---------------*/
	/*    AUTRES     */
	/*---------------*/

	public void avancer()
	{
		int tailleMap = ( ( this.joueur.getNbJoueurs() > 4 ) ? 5:4 );

		//Déplacement fictif du pion pour vérifier qu'il y a une collision après
		int[] coordTemp = this.getProchainesCoords(null);

		Pion pionTmp = this.getPionCollision( coordTemp, this.joueur );
		//Si le pion de la case devant n'est pas null (donc case occupée)
		if ( pionTmp != null )
		{
			//Si la collision a lieu avec un Pion autre qu'une base
			if ( !( pionTmp instanceof Base ) )
			{
				//On déplace le pion dans le sens de la direction

				
				coordTemp=this.getProchainesCoords(coordTemp);

				//On vérifie que la place est libre pour le pion à pousser
				//Si oui, le pion à pousser est poussé et on déplace notre
				//robot aux anciennes coordonnées du pion
				Pion pionTmp2 = this.getPionCollision( coordTemp, this.joueur );
				if ( pionTmp2 == null )
				{
					if ( !horsDeLaMap( tailleMap, coordTemp ) )
					{
						int[] coordTemp2 = this.getProchainesCoords(null);

						pionTmp.setPos( coordTemp[0], coordTemp[1], coordTemp[2] );
					}

					//Déplacement du robot
					coordTemp = this.getProchainesCoords(null);
					if ( !horsDeLaMap( tailleMap, coordTemp ) && !this.collision( coordTemp, pionTmp ) )
					{
						this.x = coordTemp[0];
						this.y = coordTemp[1];
						this.z = coordTemp[2];

						this.transformerCoordonnees();
					}
				}
			}
		}
		else
		{
			if ( !horsDeLaMap( tailleMap, coordTemp ) )
			{
				this.x = coordTemp[0];
				this.y = coordTemp[1];
				this.z = coordTemp[2];
				this.transformerCoordonnees();
			}
		}
	}

	public boolean horsDeLaMap( int tailleMap, int[] coord )
	{
		return ( coord[0] > tailleMap || coord[0] < -1 * tailleMap ||
			     coord[1] > tailleMap || coord[1] < -1 * tailleMap ||
			     coord[2] > tailleMap || coord[2] < -1 * tailleMap   );
	}

	public void changerDirection( char sens )
	{
		switch ( sens )
		{
			case 'D':this.direction++; break;
			case 'G':this.direction+=5; break;
		}

		this.direction %= 6;
	}

	public void charger()
	{
		if(this.cristal!=null)return;

		int[] coordTemp = this.getProchainesCoords(null);
		Pion pionTmp = this.getPionCollision( coordTemp, this.joueur );
		//Si on est en face d'un cristal et qu'on essaie de le ramasser
		if ( pionTmp instanceof Cristal)
		{
			this.cristal = (Cristal)pionTmp;

			if(this.cristal != null)
				this.joueur.getListePions().remove( this.cristal);

		}//Sinon, si on est en face d'un robot, on lui vole son cristal
		else if ( pionTmp instanceof Robot)
		{
			this.cristal = ((Robot)pionTmp).getCristal();

			((Robot)pionTmp).setCristal( null );
		}
	}

	public void decharger()
	{
		if(this.cristal==null) return;

		int tailleMap = ( ( this.joueur.getNbJoueurs() > 4 ) ? 5:4 );

		int[] coordTemp = this.getProchainesCoords(null);

		Pion pionTmp = this.getPionCollision( coordTemp, this.joueur );

		if ( !horsDeLaMap( tailleMap, coordTemp ))
		{
			//Si on lache le cristal sur une Base
			if ( pionTmp instanceof Base )
			{
				((Base)pionTmp).stocker( this.cristal );
				this.cristal = null;
			}
			else if (pionTmp instanceof Robot && ((Robot)pionTmp).cristal == null)
			{
				((Robot)pionTmp).cristal = this.cristal;
				this.cristal = null;
			}
			else if (pionTmp==null)
			{
				this.joueur.getListePions().add( new Cristal( coordTemp[0],
				                                              coordTemp[1],
				                                              coordTemp[2],
				                                              this.cristal.getValeur() ) );
				this.cristal = null;
			}
		}
	}

	public boolean equals(Robot robot)
	{
		return this.x == robot.x && this.y == robot.y && this.z == robot.z;
	}

	public String toString()
	{
		String s = "Robot du joueur "+ this.joueur.getNom() +" - coords ";
		s += String.format("%2d", this.x) + ":";
		s += String.format("%2d", this.y) + ":";
		s += String.format("%2d", this.z);
		s += String.format("%2d", this.direction);
		s += String.format("%6s", this.cristal!=null);

		return s;
	}
}
