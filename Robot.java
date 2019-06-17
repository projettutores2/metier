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

	public Ordre[] getAlgo() { return this.algo; }
	public Cristal getCristal() { return this.cristal; }

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

	public boolean avancer()
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
					this.getPionCollision( coordTemp, this.joueur ).setX( coordTemp[0] );
					this.getPionCollision( coordTemp, this.joueur ).setY( coordTemp[1] );
					this.getPionCollision( coordTemp, this.joueur ).setZ( coordTemp[2] );

					//Déplacement du robot
					coordTemp = this.getProchainesCoords();
					this.setX( coordTemp[0] );
					this.setY( coordTemp[1] );
					this.setZ( coordTemp[2] );
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

		if ( Pion.getPionCollision( coordTemp, this.joueur ) instanceof Cristal )
		{

		}
	}

	public void decharger()
	{

	}
}
