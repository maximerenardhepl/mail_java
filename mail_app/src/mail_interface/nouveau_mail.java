package mail_interface;

import controleur.control;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.formdev.flatlaf.FlatDarculaLaf;

public class nouveau_mail extends JFrame{
    private JTextField destT;
    private JTextArea textT;
    private JLabel text;
    private JLabel destination;
    private JButton envoyer;
    private JTextField objT;
    private JLabel object;
    private JPanel mainpanel;
    private JButton pieceJointeButton;
    private JRadioButton maxRadioButton;
    private JRadioButton noaRadioButton;
    private JButton piece_jointe;

    //consrtrcuteur de box
    public nouveau_mail()
    {
        FlatDarculaLaf.setup();
        setLocation(250, 150);

        setSize(400,400);
        setContentPane(mainpanel);

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

        pieceJointeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                control.getInstance().PieceJointe();
            }
        });

        maxRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                control.getInstance().password = "ztnjudjfqvapunwo";
            }
        });
        noaRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                control.getInstance().password = "ztnjudjfqvapunwo";
            }
        });
    }
}
