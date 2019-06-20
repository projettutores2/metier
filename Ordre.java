package TwinTinBots.metier;
public  class Ordre
{
	protected String txt ;
	protected String imgOrdre;

	public Ordre(String txt, String imgOrdre)
	{
		this.txt=txt;
		this.imgOrdre = imgOrdre;
	}
	public void action(Robot robot){}
	public String getImgOrdre() {return this.imgOrdre; }
	public String toString() {return this.txt ;}
}

class Avancer extends Ordre
{
	int multiple ;

	public Avancer(int multiple)
	{
		super("Avancer"  + (multiple == 1 ? " simple":" double"),
			  "imgOrdre" + (multiple == 1 ? "0":"1") + ".png"  );
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
		super("Rotation" + (sens == 'D' ? " droite":" gauche"),
			  "imgOrdre" + (sens == 'D' ? "3" : "2") + ".png");
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
		super("Charger", "imgOrdre4.png");
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
		super("Decharger", "imgOrdre5.png");
	}
	
	public void action(Robot robot)
	{
		robot.decharger();
	}
}