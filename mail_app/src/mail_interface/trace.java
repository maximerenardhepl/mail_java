package mail_interface;

import controleur.control;

import javax.swing.*;

public class trace extends JFrame{
    private JList listtrace;
    private JPanel panel1;

    public trace(){

        setLocation(250, 150);
        setSize(1000,600);
        setContentPane(panel1);

        control.getInstance().trouverMTA(listtrace);
    }
}
