import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileReader;
public class Regle
{
	private final static String[] NOM_ORDRE= {"avancer","avancerDouble","rotationDroite","rotationGauche","charger","decharger"};

	public static void initialisation(ArrayList<Joueur> joueurs,int nbJoueur,ArrayList<Pion> pions,Metier metier)
	{
		EnumOrdre enumTmp = null ;
		Ordre tmp = null;
		for(Joueur joueur : joueurs)
		{
			enumTmp=EnumOrdre.valueOf(Regle.NOM_ORDRE[0]);
			for(int i = 1 ;i <enumTmp.getNombre();i++)
			{
				if(enumTmp != null) tmp = enumTmp.creeOrdre();
				if(tmp     != null) joueur.ajouter(tmp);
				enumTmp = EnumOrdre.valueOf(Regle.NOM_ORDRE[i]);
			}
		}
	}

	private void scanner(ArrayList<Joueur> joueurs ,int nbJoueur,ArrayList<Pion> pions,Metier metier)
	{
		try
	    {
	      Scanner sc = new Scanner (new FileReader ("map"+nbJoueur+".data"));
	      String[] chaine ;

	      while ( sc.hasNextLine() )
	      {
	        chaine = sc.nextLine().split(":");
	        if(chaine[0].equals("joueur"))
	        {
	        	if(chaine[1].equals("nom"))
	        	{
	        		Joueur tmpJoueur = new Joueur (chaine[2],metier);
	        		if(tmpJoueur!=null) joueurs.add(tmp);
	        		Robot tmpRobot1= new Robot(Interger.parseInt(chaine[2]),Interger.parseInt(chaine[2]),Interger.parseInt(chaine[2]));
	        		Robot tmpRobot2= new Robot(Interger.parseInt(chaine[2]),Interger.parseInt(chaine[2]),Interger.parseInt(chaine[2]));
	        		tmpJoueur.creeRobot(tmpRobot1,tmpRobot2);
	        	}
	        	if(chaine[1].equals("base"))
	        	{
	        		Base tmpBase = new Base(Interger.parseInt(chaine[2]),Interger.parseInt(chaine[3]),Interger.parseInt(chaine[4]));
	        		if(tmpBase!=null) pions.add(tmp);
	        	}
	        }
	      }
	      sc.close();
	    }
	    catch(Exception e) { e.printStackTrace(); }
		}
}