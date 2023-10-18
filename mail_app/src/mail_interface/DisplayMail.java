package mail_interface;

import Modele.MessageData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

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

        if(mail.getPiecesJointes() != null) {
            DefaultListModel<String> model = new DefaultListModel<>();
            for(String nomPieceJointe : mail.getPiecesJointes()) {
                model.addElement(nomPieceJointe);
            }

            listAttachments.setModel(model);

            listAttachments.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (e.getClickCount() == 2) {
                        int selectedIndex = listAttachments.getSelectedIndex();
                        if (selectedIndex != -1) {
                            String selectedFileName = (String)listAttachments.getModel().getElementAt(selectedIndex);
                            openFile(selectedFileName);
                        }
                    }
                }
            });
        }
    }

    private void openFile(String fileName) {
        String projectRoot = System.getProperty("user.dir"); // Récupère le répertoire racine du projet
        File file = new File(projectRoot, fileName);

        if (file.exists()) {
            try {
                Desktop.getDesktop().open(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Fichier introuvable : " + fileName);
        }
    }
}
