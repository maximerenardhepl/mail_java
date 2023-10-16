package mail_interface;

import Modele.MessageData;
import com.formdev.flatlaf.FlatDarculaLaf;
import controleur.control;

import javax.mail.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Properties;

public class principale extends JFrame{
    private JPanel principale;
    private JList list1;
    private JButton suivreLogButton;
    private JButton nouveauMailButton;

    public JList getJList() { return list1; }

    public principale() {
        FlatDarculaLaf.setup();
        setLocation(250, 150);

        setSize(900,600);
        setContentPane(principale);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //ArrayList<MessageData> listMsg = control.getInstance().receive();
        //actualiseListeMails(listMsg);

        nouveauMailButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nouveau_mail fen = new nouveau_mail();
                fen.setVisible(true);
            }
        });

        list1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) { // Réagit au double clic
                    int selectedIndex = list1.getSelectedIndex();
                    if (selectedIndex != -1) {
                        //String selectedItem = (String)(list1.getModel().getElementAt(selectedIndex));
                        ArrayList<MessageData> listMsg = control.getInstance().getListMsg();
                        int tailleListe = listMsg.size();
                        MessageData mail = listMsg.get((tailleListe-1)-selectedIndex);

                        DisplayMail fenDisplayMail = new DisplayMail(mail);
                        fenDisplayMail.setVisible(true);
                    }
                }
            }
        });
    }

    public void actualiseListeMails(ArrayList<MessageData> listMsg) {
        DefaultListModel<String> model = new DefaultListModel<>();

        for(int i = (listMsg.size()-1), n = 1; i >= 0; i--, n++) {
            MessageData msg = listMsg.get(i);
            String sender = msg.getExpediteur();
            String subject = msg.getSujet();

            model.addElement("N: " + n + "  De : " + sender + " - Sujet : " + subject);
            //model.addElement("=============================================================================================================");
        }

        list1.setModel(model);
    }
}
