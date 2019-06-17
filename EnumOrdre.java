public enum EnumOrdre
{
	avancer        ("avancer"        ,2),
	avancerDouble  ("avancerDouble"  ,2),
	rotationDroite ("rotationDroite" ,2),
	rotationGauche ("rotationGauche" ,2),
	charger        ("charger"        ,2),
	decharger      ("decharger"      ,2);

	private String nom;
	private int    nombre;

	private EnumOrdre(String nom,int nombre)
	{
		this.nom=nom;
		this.nombre=nombre;
	}

	public Ordre creeOrdre()
	{
		Ordre tmp = null ;
		switch(this.nom)
		{
			case "avancer"        : tmp = new Avancer(1);
				break;
			case "avancerDouble"  : tmp = new Avancer(2);
				break;
			case "rotationDroite" : tmp = new Rotation('D');
				break;
			case "rotationGauche" : tmp = new Rotation('G');  
				break;
			case "charger"        : tmp = new Charger();
				break;
			case "decharger"      : tmp = new  Decharger();
				break;
		}
		return tmp ;
	}
}