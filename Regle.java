import java.util.ArrayList;
public class Regle
{
	private final static int NB_ROBOT = 2 ;

	public static void initialisation(ArrayList<Joueur> joueurs)
	{
		for(Joueur joueur : joueurs)
		{
			joueur.creeRobot(NB_ROBOT);
		}
	}
}