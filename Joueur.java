/** Joueur
  * date : 17/08/2019
  * @author : Equipe 14
  * @version 1
  */

package TwinTinBots.metier;
import java.util.ArrayList;

public class Joueur
{
	private String           nom;
	private Metier           metier;
	private ArrayList<Ordre> stockOrdres;
	private Robot[]          ensRobots;
	private int              nbPoint;
	
	public Joueur(String nom,Metier metier)
	{
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
		this.stockOrdres = joueur.getStockOrdres();
		this.ensRobots   = joueur.ensRobots;
		this.nbPoint     = joueur.nbPoint;
	}

	public void ajouter(Ordre newOrdre)
	{
		if(newOrdre != null ) this.stockOrdres.add(newOrdre);
	}

	public Ordre retirer(int indice)
	{
		if(indice < this.stockOrdres.size())
			 return this.stockOrdres.remove(indice);
		else return null ;
	}
	
	public void creeRobot(Robot robot1, Robot robot2)
	{
		this.ensRobots[0] = robot1;
		this.ensRobots[1] = robot2;
	}
	
	public void setPosRobot(int x, int y, int z, int indRobot)
	{
		if(indRobot < this.ensRobots.length)
			this.ensRobots[indRobot].setPos(x,y,z);
	}
	
	//Set
	public void setNom(String nom)
	{
		this.nom = nom;
	}
	
	//Get
	public Robot getRobot(int indRobot)
	{
		if(indRobot < this.ensRobots.length)
		     return this.ensRobots[indRobot];
		else return null ;
	}
	
	public ArrayList<Ordre> getStockOrdres()
	{
		return this.stockOrdres;
	}

	public ArrayList<Pion> getListePions()
	{
		return this.metier.getListePions();
	}

	public int getNbJoueurs() { return this.metier.getNbJoueurs(); }

	public String getNom(){return this.nom;}
}