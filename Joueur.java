/*
 * date : 17/08/2019
 * @author : Equipe 14
 */
package TwinTinBots.metier;
import java.util.ArrayList;

public class Joueur
{
	private static int       nbJoueurs;
	private String           nom;
	private Metier           metier;
	private ArrayList<Ordre> stockOrdres;
	private Robot[]          ensRobots;
	private int              nbPoint;
	private int              idJoueur;

	//--------------------------------------------------------------------------------
 	//                                     CONSTRUCTEUR
	public Joueur(String nom,Metier metier)
	{
		this.idJoueur    = nbJoueurs++;
		this.nom         = nom;
		this.metier      = metier;
		this.stockOrdres = new ArrayList<Ordre>();
		this.ensRobots   = new Robot[2];
		this.nbPoint     = 0;
	}

	public Joueur(Joueur joueur)
	{
		this.nom         = joueur.nom;
		this.metier      = joueur.metier;
		this.stockOrdres = this.copyStock(joueur.getStockOrdres());
		this.ensRobots   = joueur.ensRobots;
		this.nbPoint     = joueur.nbPoint;
	}
	//--------------------------------------------------------------------------------
 	//                                     PUBLIC
 	//ajoute au jouer un ordre
	public void ajouter(Ordre newOrdre)
	{
		if(newOrdre != null ) this.stockOrdres.add(newOrdre);
	}
	//retire au joueur un ordre
	public Ordre retirer(int indice)
	{
		if(indice < this.stockOrdres.size())
			 return this.stockOrdres.remove(indice);
		else 
			return null ;
	}
	//ajoute les robot au joueur
	public void creeRobot(Robot robot1, Robot robot2)
	{
		this.ensRobots[0] = robot1;
		this.ensRobots[1] = robot2;
	}
	//verifie l'egalité entre deux joueur
	public boolean equals(Joueur joueur)
	{
		return this.nom == joueur.nom;
	}

	//--------------------------------------------------------------------------------
 	//                                     PRIVATE
	//cree une copi profonde
	private ArrayList<Ordre> copyStock(ArrayList<Ordre> stock)
	{
		ArrayList<Ordre> tmp = new ArrayList<Ordre>();
		for(Ordre ordre : stock)
			tmp.add(ordre);
		return tmp;
	}
	//--------------------------------------------------------------------------------
    //                                     GET

    public int              getIdJoueur    () { return this.idJoueur;               }
    public Metier           getMetier      () { return this.metier;                 }
    public Robot[]          getRobot       () { return this.ensRobots;              }
	public ArrayList<Ordre> getStockOrdres () { return this.stockOrdres;            }
	public ArrayList<Pion>  getListePions  () { return this.metier.getListePions(); }
	public int              getNbJoueurs   () { return this.metier.getNbJoueurs();  }
	public String           getNom         () { return this.nom;                    }

	//revoins le robot de l'indice entrer
	public Robot getRobot(int indRobot)
	{
		if(indRobot < this.ensRobots.length)
		     return this.ensRobots[indRobot];
		else return null ;
	}
	//renvois le score des cristaux que porte les robot 
	public int getScoreRobots()
	{
		int score =0;
		for(int i = 0 ; i < 2 ; i++)
		{
			Cristal cristal = this.getRobot(i).getCristal();
			if(cristal != null )score += cristal.getValeur()-1;
		}
		return score ;
	}

    //--------------------------------------------------------------------------------
    //                                     SET
    public void setNom(String nom) { this.nom = nom; }
	public void setPosRobot(int x, int y, int z, int indRobot)
	{
		if(indRobot < this.ensRobots.length)
			this.ensRobots[indRobot].setPos(x,y,z);
	}
	
}