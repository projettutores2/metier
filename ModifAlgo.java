/*
 * date : 17/08/2019
 * @author : Equipe 14
 */
package TwinTinBots.metier;

public class ModifAlgo
{
	private Joueur  joueur;
	private Robot   robot;
	private int     slot;
	private int     slot2;
	private int     typeModif;
	private int     nouvelOrdre;
	private boolean ready;

 	//--------------------------------------------------------------------------------
 	//                                     CONSTRUCTEUR
	public ModifAlgo(Joueur joueur, Robot robot, int slot, int typeModif)
	{
		this.joueur      = joueur;
		this.robot       = robot;
		this.slot        = slot;
		this.slot2       = -1;
		this.typeModif   = typeModif;
		this.nouvelOrdre = -1;
		this.ready       = false;
	}

	public ModifAlgo()
	{
		this(null, null, -1, 5);
	}

	public ModifAlgo(int typeModif)
	{
		this(new Joueur("bob", null), null, -1, typeModif);
	}

	//--------------------------------------------------------------------------------
    //                                     GET
 
	public int     getNewOrdre () { return this.nouvelOrdre; }
	public Joueur  getJoueur   () { return this.joueur;      }
	public Robot   getRobot    () { return this.robot;       }
	public int     getSlot     () { return this.slot;        }
	public int     getSlot2    () { return this.slot2;       }
	public int     getType     () { return this.typeModif;   }
	public boolean getReady    () { return this.ready;       }

    //--------------------------------------------------------------------------------
    //                                     SET

	public void setJoueur    (Joueur joueur)   {this.joueur = joueur;           }
	public void setRobot     (Robot robot)     {this.robot = robot;             }
	public void setSlot      (int slot)        {this.slot = slot;               }
	public void setTypeModif (int typeModif)   {this.typeModif = typeModif;     }
	public void setNewOrdre  (int nouvelOrdre) {this.nouvelOrdre = nouvelOrdre; }
	public void setSlot2     (int slot2)       {this.slot2 = slot2;             }
	public void setReady     (boolean ready)   {this.ready = ready;             }
}