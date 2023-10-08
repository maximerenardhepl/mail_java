package mail_interface;

import controleur.control;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class piece_jointe extends JFrame{
    private JTextField path;
    private JPanel fenetre;
    private JButton validerButton;

    public piece_jointe(){
        setLocation(250, 150);
        setSize(320,200);
        setContentPane(fenetre);

        validerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                control.getInstance().piece_path = path.getText();
            }
        });
    }
}
