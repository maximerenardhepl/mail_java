package mail_interface;

import controleur.control;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class consult extends JFrame{
    public JList list1;
    private JPanel consulpanel;
    private JButton traçageButton;

    //je vais quand j'ouvre la fenetre charger une certaine somme de mail
    public consult()
    {
        setLocation(250, 150);
        setSize(1000,600);
        setContentPane(consulpanel);
        control.getInstance().recieve(list1);

        traçageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                trace t = new trace();
                t.setVisible(true);
            }
        });

    }
}
