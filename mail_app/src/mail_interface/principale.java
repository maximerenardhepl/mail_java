package mail_interface;

import com.formdev.flatlaf.FlatDarculaLaf;
import controleur.control;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class principale extends JFrame{
    private JPanel principale;
    private JList list1;
    private JButton suivreLogButton;
    private JButton nouveauMailButton;

    public principale(){
        FlatDarculaLaf.setup();
        setLocation(250, 150);

        setSize(900,600);
        setContentPane(principale);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        control.getInstance().recieve(list1);

        nouveauMailButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nouveau_mail fen = new nouveau_mail();
                fen.setVisible(true);
            }
        });
    }
}
