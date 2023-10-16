package mail_interface;

import Modele.MessageData;

import javax.swing.*;

public class DisplayMail extends JFrame {
    private JPanel mainPanel;
    private JTextArea contentMailArea;
    private JLabel subjectLabel;
    private JLabel fromLabel;
    private JList listAttachments;
    private JTextField txtFrom;
    private JTextField txtSubject;
    private JLabel ContentLabel;
    private JLabel labelAttachments;
    private JLabel labelTitle;

    public DisplayMail(MessageData mail) {
        setContentPane(mainPanel);
        setSize(500,500);

        contentMailArea.setEditable(false);
        contentMailArea.setText(mail.getContenu());
        txtSubject.setEditable(false);
        txtSubject.setText(mail.getSujet());
        txtFrom.setEditable(false);
        txtFrom.setText(mail.getExpediteur());
    }
}
