import java.util.list;

public class Joueur
{
	private Metier metier;
	private String nom;
	private ArrayList<Ordre> stockOrdres;
	private Robot[] ensRobots;
	private int nbPoint;
	
	public Joueur(String nom, Metier metier)
	{
		this.nom         = nom;
		this.metier      = metier;
		this.stockOrdres = new ArrayList<Ordre>();
		this.ensRobots   = new Robot[2];
		this.nbPoint     = 0;
	}

	public void creeRobot(Robot robot1, Robot robot2)
	{
		this.ensRobots[0] = robot1;
		this.ensRobots[1] = robot2;
	}
	
	public void setPosRobot(int x, int y, int z, int indRobot)
	{
		this.ensRobots[intRobot].setPos(x,y,z);
	}
	
	public void ajouterOrdre (Ordre newOrdre)
	{
		this.stockOrdres.add(newOrdre);
	}
	
	public ArrayList<Pion> getListePions()
	{
		this.metier.getListePions;
	}
}