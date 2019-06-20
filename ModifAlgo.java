package TwinTinBots.metier;

public class ModifAlgo
{
	private Joueur joueur;
	private Robot  robot;
	private int    slot;
	private int    slot2;
	private int    typeModif;
	private int    nouvelOrdre;

	public ModifAlgo(Joueur joueur, Robot robot, int slot, int typeModif)
	{
		this.joueur      = joueur;
		this.robot       = robot;
		this.slot        = slot;
		this.typeModif   = typeModif;
		this.nouvelOrdre = null;
	}

	public void setNewOrdre(int nouvelOrdre)
	{
		this.nouvelOrdre = nouvelOrdre;
	}

	public void setSlot2(int slot2)
	{
		this.slot2 = slot2;
	}

	public int getNewOrdre()
	{
		return this.nouvelOrdre;
	}

	public Joueur getJoueur()
	{
		return this.joueur;
	}

	public Robot getRobot()
	{
		return this.robot;
	}

	public int getSlot()
	{
		return this.slot;
	}

	public int getSlot2()
	{
		return this.slot2;
	}

	public int getType()
	{
		return this.typeModif;
	}
}