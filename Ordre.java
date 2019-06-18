package TwinTinBots.metier;
public  class Ordre
{
	protected String txt ;
	public Ordre(String txt)
	{
		this.txt=txt;
	}
	public void action(Robot robot){}
	public String toString() {return this.txt ;}
}

class Avancer extends Ordre
{
	int multiple ;

	public Avancer(int multiple)
	{
		super("Avancer"+(multiple == 1 ? " simple":" double"));
		this.multiple=multiple;
	}
	
	public void action(Robot robot)
	{
		for(int i = 0 ; i<multiple ; i++)
			robot.avancer();
	}
}

class Rotation extends Ordre
{
	char sens ;
	public Rotation(char sens)
	{
		super("Rotation"+(sens == 'D' ? " droite":" gauche"));
		this.sens=sens;
	}
	
	public void action(Robot robot)
	{
		robot.changerDirection(this.sens);
	}
}

class Charger extends Ordre
{

	public Charger()
	{
		super("Charger");
	}
	
	public void action(Robot robot)
	{
		robot.charger();
	}
}
class Decharger extends Ordre
{
	public Decharger()
	{
		super("Decharger");
	}
	
	public void action(Robot robot)
	{
		robot.decharger();
	}
}