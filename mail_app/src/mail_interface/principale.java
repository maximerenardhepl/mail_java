package mail_interface;

import com.formdev.flatlaf.FlatDarculaLaf;
import controleur.control;

import javax.mail.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Properties;

public class principale extends JFrame{
    private JPanel principale;
    private JList list1;
    private JButton suivreLogButton;
    private JButton nouveauMailButton;

    public JList getJList() { return list1; }

    public principale(){
        FlatDarculaLaf.setup();
        setLocation(250, 150);

        setSize(900,600);
        setContentPane(principale);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ArrayList<Message> listMsg = control.getInstance().receive();
        actualiseListeMails(listMsg);

        nouveauMailButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nouveau_mail fen = new nouveau_mail();
                fen.setVisible(true);
            }
        });
    }

    public void actualiseListeMails(ArrayList<Message> listMsg) {
        DefaultListModel<String> model = new DefaultListModel<>();

        try {
            for(int i = (listMsg.size()-1), n = 1; i >= 0; i--, n++) {
                Message msg = listMsg.get(i);
                String sender = msg.getFrom()[0].toString();
                String subject = msg.getSubject();

                model.addElement("N: " + n + "  De : " + sender + " - Sujet : " + subject);
                model.addElement("=============================================================================================================");
            }

            list1.setModel(model);
        }
        catch(MessagingException e) {
            e.printStackTrace();
        }
    }
}
