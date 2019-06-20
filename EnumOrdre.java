/** EnumOrdre
  * date : 17/08/2019
  * @author : Equipe 14
  * @version 1
  */

package TwinTinBots.metier;
public enum EnumOrdre
{
	avancer        ("avancer"        ,2 , "./TwinTinBots/img/imgOrdre0.png"),
	avancerDouble  ("avancerDouble"  ,1 , "./TwinTinBots/img/imgOrdre1.png"),
	rotationDroite ("rotationDroite" ,3 , "./TwinTinBots/img/imgOrdre3.png"),
	rotationGauche ("rotationGauche" ,3 , "./TwinTinBots/img/imgOrdre2.png"),
	charger        ("charger"        ,2 , "./TwinTinBots/img/imgOrdre4.png"),
	decharger      ("decharger"      ,2 , "./TwinTinBots/img/imgOrdre5.png");

	private String nom;
	private int    nombre;
	private String adresseImg;

	private EnumOrdre(String nom,int nombre, String adresseImg)
	{
		this.nom        = nom;
		this.nombre     = nombre;
		this.adresseImg = adresseImg;
	}

	public Ordre creeOrdre()
	{
		Ordre tmp = null ;
		switch(this.nom)
		{
			case "avancer"        : tmp = new Avancer(1, this.adresseImg);
				break;
			case "avancerDouble"  : tmp = new Avancer(2, this.adresseImg);
				break;
			case "rotationDroite" : tmp = new Rotation('D', this.adresseImg);
				break;
			case "rotationGauche" : tmp = new Rotation('G', this.adresseImg);
				break;
			case "charger"        : tmp = new Charger(this.adresseImg);
				break;
			case "decharger"      : tmp = new  Decharger(this.adresseImg);
				break;
		}
		return tmp ;
	}

	public int getNombre() { return this.nombre;}

}