/*
 * date : 17/08/2019
 * @author : Equipe 14
 */
package TwinTinBots.metier;
import javax.swing.*;

public  class Ordre
{
	protected String txt ;
	protected JLabel image;

 	//--------------------------------------------------------------------------------
 	//                                     CONSTRUCTEUR
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
 	//--------------------------------------------------------------------------------
 	//                                     PUBLIC

	public void action(Robot robot){}
	public String toString() { return this.txt ;}

	//--------------------------------------------------------------------------------
 	//                                     GET
	public JLabel getImg()   { return new JLabel(this.image.getIcon()); }
}
//_______________________________________________________________________________________________
//_______________________________________________________________________________________________
class Avancer extends Ordre
{
	int multiple ;
 	//--------------------------------------------------------------------------------
 	//                                     CONSTRUCTEUR
	public Avancer(int multiple, String adresseImg)
	{
		super("Avancer"+(multiple == 1 ? " simple":" double"), adresseImg);
		this.multiple = multiple;
	}

	public Avancer(int multiple)
	{
		this(multiple, "./TwinTinBots/img/imgOrdre" + multiple + ".png");
	}
	
	//--------------------------------------------------------------------------------
 	//                                     PUBLIC
	public void action(Robot robot)
	{
		for(int i = 0 ; i<multiple ; i++)
			robot.avancer();
	}
}
//_______________________________________________________________________________________________
//_______________________________________________________________________________________________
class Rotation extends Ordre
{
	private char sens ;
	//--------------------------------------------------------------------------------
 	//                                     CONSTRUCTEUR
	public Rotation(char sens, String adresseImg)
	{
		super("Rotation"+(sens == 'D' ? " droite":" gauche"), adresseImg);
		this.sens = sens;
	}
	public Rotation(char sens)
	{
		this(sens, "./TwinTinBots/img/imgOrdre" + (sens=='G'?2:3) + ".png");
	}
	//--------------------------------------------------------------------------------
 	//                                     PUBLIC
	public void action(Robot robot)
	{
		robot.changerDirection(this.sens);
	}
}
//_______________________________________________________________________________________________
//_______________________________________________________________________________________________
class Charger extends Ordre
{
	 //--------------------------------------------------------------------------------
 	//                                     CONSTRUCTEUR
	public Charger(String adresseImg)
	{
		super("Charger", adresseImg);
	}

	public Charger()
	{
		this("./TwinTinBots/img/imgOrdre4.png");
	}
	//--------------------------------------------------------------------------------
 	//                                     PUBLIC
	public void action(Robot robot)
	{
		robot.charger();
	}
}
//_______________________________________________________________________________________________
//_______________________________________________________________________________________________
class Decharger extends Ordre
{
	//--------------------------------------------------------------------------------
 	//                                     CONSTRUCTEUR
	public Decharger(String adresseImg)
	{
		super("Decharger", adresseImg);
	}

	public Decharger()
	{
		this("./TwinTinBots/img/imgOrdre5.png");
	}
	//--------------------------------------------------------------------------------
 	//                                     PUBLIC
	public void action(Robot robot)
	{
		robot.decharger();
	}
}