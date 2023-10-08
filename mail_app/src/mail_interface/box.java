package mail_interface;

import controleur.control;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.formdev.flatlaf.FlatDarculaLaf;

public class box extends JFrame{
    private JTextField destT;
    private JTextField textT;
    private JLabel text;
    private JLabel destination;
    private JButton envoyer;
    private JTextField objT;
    private JLabel object;
    private JPanel mainpanel;
    private JButton mesMailsButton;
    private JButton piece_jointe;

    //consrtrcuteur de box
    public box()
    {
        FlatDarculaLaf.setup();
        setLocation(250, 150);

        setSize(400,400);
        setContentPane(mainpanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        envoyer.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String dest = destT.getText();
                String objet = objT.getText();
                String texte = textT.getText();

                System.out.println("mail : " + dest);
                System.out.println("subject : " + objet);
                System.out.println("text : " + texte);

                //envoi de la requete une fois quon appuis sur envoyer
                control.getInstance().send(dest, objet,texte);
            }
        });

        mesMailsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                consult fen = new consult();
                fen.setVisible(true);
            }
        });

        piece_jointe.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                piece_jointe fen = new piece_jointe();
                fen.setVisible(true);

            }
        });
    }
}
