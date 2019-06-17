public  class Ordre
{
	public void action(){}
}


class Avancer extends Ordre
{
	int multiple ;

	public Avancer(int multiple)
	{
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
	{}
	
	public void action(Robot robot)
	{
		robot.charger();
	}
}
class Decharger extends Ordre
{
	public Decharger()
	{}
	
	public void action(Robot robot)
	{
		robot.decharger();
	}
}