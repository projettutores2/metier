package TwinTinBots.ihm;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.Toolkit;

public class Scenario extends JDialog implements ActionListener
{
	private JTextField txtScenario;
	private JPanel panHaut, panBas;
	private JButton bOk, bCancel;
	private Launcher menu;

	public Scenario(Launcher m)
	{
		this.setSize(320,70);
		this.setLocationRelativeTo(m);
		this.menu = m;

		this.panHaut = new JPanel();
		this.panBas  = new JPanel();
		this.bOk=new JButton("Valider");
		this.bOk.addActionListener(this);
		this.bCancel=new JButton("Quitter");
		this.bCancel.addActionListener(this);

		this.txtScenario = new JTextField("");

		this.panHaut.setLayout(new GridLayout(1,2));
		this.panHaut.setBackground(new Color(197, 175, 146));
		this.panBas.setLayout(new GridLayout(1,2));

		JLabel lbl = new JLabel("Scenario ");
		Apparence.setStyleLbl(lbl);
		this.panHaut.add(lbl);
		this.panHaut.add(this.txtScenario);

		this.panBas.add(this.bOk);
		this.panBas.add(this.bCancel);
		Apparence.setStyleBtn(this.bOk);
		Apparence.setStyleBtn(this.bCancel);

		this.setLayout(new GridLayout(2,1));
		this.add(this.panHaut);
		this.add(this.panBas);    
    
	}

	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource()==this.bCancel)
			this.dispose();
		if (e.getSource()==this.bOk)
		{	
			String s = this.txtScenario.getText();
			this.menu.lancerPartie(s);
			this.dispose();
		}
	}
}