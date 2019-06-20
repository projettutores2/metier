package TwinTinBots.ihm;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class PanDroit extends JPanel implements ActionListener
{
	private Controleur ctrl;
	private JLabel     labTour, labJoueur;
	private String[]   tabNomBoutons;
	private JButton[]  tabJButt;
	private FenPrincipale fenP;
	
	public PanDroit(Controleur ctrl, FenPrincipale f)
	{
		this.ctrl = ctrl;
		this.fenP = f;
		this.initComponents();
	}
	
	private void initComponents()
	{
		int tour = this.ctrl.getMetier().getNombreTour();//%(this.ctrl.getMetier().getNombreJoueurs()+1);
		this.labTour   = new JLabel("Tour   en cours : "+tour);
		Apparence.setStyleLbl(this.labTour);
		this.labJoueur = new JLabel("Joueur en cours : "+this.ctrl.getMetier().getJoueurActif());
		Apparence.setStyleLbl(this.labJoueur);
		this.tabNomBoutons = new String[]{"Modifier Robot", "Consulter Algorithmes","Ex\u00e9cuter Programme"};
		this.tabJButt      = new JButton[this.tabNomBoutons.length];
		for (int i=0; i<this.tabNomBoutons.length; i++)
		{
			this.tabJButt[i] = new JButton(this.tabNomBoutons[i]);
			this.tabJButt[i].addActionListener(this);
			Apparence.setStyleBtn(this.tabJButt[i]);
		}
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		//ajout des labels
		this.add(this.labTour);
		this.add(Box.createVerticalStrut(20));
		this.add(this.labJoueur);
		this.add(Box.createVerticalStrut(20));
		
		//ajout des boutons
		for (int j=0; j<this.tabJButt.length; j++)
		{
			this.add(Box.createVerticalStrut(20));
			this.add(this.tabJButt[j]);			
		}
		Apparence.setFondColore(this);
	}
	
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == this.tabJButt[0])
		{
			DemandeRobots modifRob = new DemandeRobots(this.fenP, "Modifier Robot", true, this.ctrl);
			modifRob.setVisible(true);
		}
		else if (e.getSource() == this.tabJButt[1])
		{
			AffichageAlgo affAlgo = new AffichageAlgo( this.fenP, "Consulter Algorithmes", true, this.ctrl );
			affAlgo.setVisible( true );
		}
		else if (e.getSource() == this.tabJButt[2])
		{
			
		}

	}

	public void majPanel()
	{
		int tour = this.ctrl.getMetier().getNombreTour();
		this.labTour.setText("Tour en cours   : "+tour);
		this.labJoueur.setText("Joueur en cours : "+this.ctrl.getMetier().getJoueurActif());
		//this.fenP.majScore();
		this.revalidate();
		this.repaint();
	}
}
