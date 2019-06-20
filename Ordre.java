package TwinTinBots.metier;
import javax.swing.*;

public  class Ordre
{
	protected String txt ;
	protected JLabel image;

	public Ordre(String txt, String adresseImg)
	{
		this.txt = txt;
		if(!adresseImg.equals(""))
			this.image = new JLabel(new ImageIcon(adresseImg));
	}

	public Ordre(String txt)
	{
		this(txt, "");
	}

	public void action(Robot robot){}
	public String toString() { return this.txt ;}
	public JLabel getImg()   { return this.image; }
}

class Avancer extends Ordre
{
	int multiple ;

	public Avancer(int multiple, String adresseImg)
	{
		super("Avancer"+(multiple == 1 ? " simple":" double"), adresseImg);
		this.multiple = multiple;
	}

	public Avancer(int multiple)
	{
		this(multiple, "");
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

	public Rotation(char sens, String adresseImg)
	{
		super("Rotation"+(sens == 'D' ? " droite":" gauche"), adresseImg);
		this.sens = sens;
	}
	public Rotation(char sens)
	{
		this(sens, "");
	}
	
	public void action(Robot robot)
	{
		robot.changerDirection(this.sens);
	}
}

class Charger extends Ordre
{
	public Charger(String adresseImg)
	{
		super("Charger", adresseImg);
	}

	public Charger()
	{
		this("");
	}
	
	public void action(Robot robot)
	{
		robot.charger();
	}
}
class Decharger extends Ordre
{
	public Decharger(String adresseImg)
	{
		super("Decharger", adresseImg);
	}

	public Decharger()
	{
		this("");
	}
	
	public void action(Robot robot)
	{
		robot.decharger();
	}
}