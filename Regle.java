package TwinTinBots.metier;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileReader;
public class Regle
{
	private final static String[] NOM_ORDRE= {"avancer","avancerDouble","rotationDroite","rotationGauche","charger","decharger"};
	private final static int CRISTAL_VERT = 2;
	private final static int CRISTAL_BLEU = 3;

	public static void initialisation(ArrayList<Joueur> joueurs,int nbJoueur,ArrayList<Pion> pions,Metier metier)
	{
		Regle.scanner(joueurs,nbJoueur,pions,metier);
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

	private static void scanner(ArrayList<Joueur> joueurs ,int nbJoueur,ArrayList<Pion> pions,Metier metier)
	{
		try
	    {
	      Scanner sc = new Scanner (new FileReader ("TwinTinBots/map/map"+nbJoueur+".data"));
	      String[] chaine ;
	      int cpt ;

	      while ( sc.hasNextLine() )
	      {
	        chaine = sc.nextLine().split(":");
	        if(chaine[0].equals("joueur"))
	        {
	        	if(chaine[1].equals("nom") && chaine.length >=9)
	        	{
	        		//on cree un nouveau joueur et on l'ajoute
	        		Joueur tmpJoueur = new Joueur (chaine[2],metier);
	        		if(tmpJoueur!=null) joueurs.add(tmpJoueur);
	        		//on cree c'est 2 robot
	        		Robot tmpRobot1= new Robot(Integer.parseInt(chaine[4]),Integer.parseInt(chaine[5]),Integer.parseInt(chaine[6]),
	        			tmpJoueur,Integer.parseInt(chaine[3]));
	        		Robot tmpRobot2= new Robot(Integer.parseInt(chaine[8]),Integer.parseInt(chaine[9]),Integer.parseInt(chaine[10]),
	        			tmpJoueur,Integer.parseInt(chaine[7]));
	        		//on ajoute les robot au joueur
	        		tmpJoueur.creeRobot(tmpRobot1,tmpRobot2);
	        		//on ajoute les 2 rebot du joueur au pion
	        		pions.add(tmpRobot1);
	        		pions.add(tmpRobot2);
	        	}
	        	if(chaine[1].equals("base") && chaine.length >=4)
	        	{
	        		Base tmpBase = null;
	        		Joueur baseJoueur = metier.getJoueurs(chaine[2]);
	        		if(baseJoueur!= null)tmpBase = new Base(baseJoueur,Integer.parseInt(chaine[3]),Integer.parseInt(chaine[4]),Integer.parseInt(chaine[5]));
	        		if(tmpBase!=null) pions.add(tmpBase);
	        	}
	        }
	    	if(chaine[0].equals("cristal"))
	        {
				if(chaine[1].equals("bleu"))
				{
					cpt =3 ;
					for(int i = 0 ; i<Integer.parseInt(chaine[2]);i++)
					{
						Cristal cristal = new Cristal (Integer.parseInt(chaine[cpt++]),Integer.parseInt(chaine[cpt++]),Integer.parseInt(chaine[cpt++]),CRISTAL_BLEU);
						if(cristal != null) pions.add(cristal);
					}
				}
				if(chaine[1].equals("vert"))
				{
					 cpt =3 ;
					for(int i = 0 ; i<Integer.parseInt(chaine[2]);i++)
					{
						Cristal cristal = new Cristal (Integer.parseInt(chaine[cpt++]),Integer.parseInt(chaine[cpt++]),Integer.parseInt(chaine[cpt++]),CRISTAL_VERT);
						if(cristal != null) pions.add(cristal);
					}
				}
	        }
	      }
	      sc.close();
	    }
	    catch(Exception e) { e.printStackTrace(); }
		}

}