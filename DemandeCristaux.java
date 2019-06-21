package TwinTinBots.ihm;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;


public class DemandeCristaux extends JDialog implements ActionListener
{
	private Controleur ctrl;
	private JComboBox<String> listeCoordonnees; //Liste avec les noms des cristaux
	private String[] listeString = new String[]{"Nord-Est","Est","Sud-Est","Sud-Ouest","Ouest","Nord-Ouest"};
	private JButton valider ;

	public DemandeCristaux(Controleur ctrl)
	{
		this.setSize(400,150);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		this.ctrl = ctrl;
		this.listeCoordonnees = new JComboBox<String>();

		this.listeCoordonnees.addItem("");
		for (int i=0; i<listeString.length; i++)
		{
			this.listeCoordonnees.addItem(listeString[i]);
		}

		this.add(this.listeCoordonnees, BorderLayout.CENTER);

		this.valider = new JButton ("Valider");
		this.valider.addActionListener(this);
		Apparence.setStyleBtnRetour(this.valider);
		this.add(this.valider, BorderLayout.SOUTH);

		this.setVisible(true);
	}

	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == this.valider)
		{
			if (this.listeCoordonnees.getSelectedIndex()!=0)
			{
				String envoi = listeString[this.listeCoordonnees.getSelectedIndex()-1];
				this.ctrl.envoiChaine(envoi);
				//System.out.println(envoi);
				this.dispose();
			}
		}
	}
}