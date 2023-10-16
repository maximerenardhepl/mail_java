package controleur;

import mail_interface.principale;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import javax.swing.*;
import java.util.*;
import javax.swing.JFileChooser;

public class control {

    private static control instance = new control();
    //le constructeur ne seras instancier que une seul fois comme c'est un sigleton
    private control() {
        listMsg = new ArrayList<>();
        nbrMessagesInBox = -1;
    }

    // Méthode statique pour récupérer l'instance unique
    public static control getInstance() {
        return instance;
    }

    private principale refMainView;
    public String password;
    public String piece_path="";
    private ArrayList<Message> listMsg;
    private int nbrMessagesInBox;

    DefaultListModel<String> listModel = new DefaultListModel<>();

    public String GetPassword()
    {
        return piece_path;
    }

    public int getMessageCount() { return this.nbrMessagesInBox; }

    public void setRefView(principale fenetrePrincipale) { this.refMainView = fenetrePrincipale; }

    public principale getRefMainView() { return refMainView; }

    public ArrayList<Message> getListMsg() { return listMsg; }

    public void send(String username, String subject, String text) {
        // Paramètres SMTP du serveur de messagerie
        String host = "smtp.gmail.com"; // Adresse du serveur SMTP

        // Propriétés SMTP
        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        // Créez une session de messagerie avec authentification
        Session session = javax.mail.Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, GetPassword());
            }
        });

        try {
            // Créez un objet Message
            Message message = new MimeMessage(session);

            // Définissez l'expéditeur, le destinataire, le sujet et le corps du message
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("maximerenardhepl@gmail.com"));

            // Définissez le sujet et le contenu du message
            message.setSubject(subject);
            message.setText(text);

            // Si une pièce jointe est spécifiée
            if (piece_path != null && !piece_path.isEmpty()) {
                // Créez la pièce jointe
                MimeBodyPart pieceJointe = new MimeBodyPart();
                FileDataSource source = new FileDataSource(piece_path);
                pieceJointe.setDataHandler(new DataHandler(source));
                pieceJointe.setFileName("nom_de_la_piece_jointe.pdf"); // Nom de la pièce jointe dans l'e-mail

                // Créez un conteneur multipart pour le message
                Multipart multipart = new MimeMultipart();
                multipart.addBodyPart(pieceJointe);

                // Ajoutez le contenu au message
                message.setContent(multipart);
            }


            // Envoyez le message en utilisant la méthode Transport.send
            Transport.send(message);

            System.out.println("E-mail envoyé avec succès !");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public synchronized ArrayList<Message> receive() {
        // Paramètres IMAP du serveur de messagerie
        String host = "smtp.gmail.com";
        String username = "maximerenardhepl@gmail.com";
        String password = "ztnjudjfqvapunwo";

        Properties props = new Properties();
        props.setProperty("mail.store.protocol", "imaps");
        props.setProperty("mail.imaps.host", host);

        try {
            // Créez une session de messagerie avec authentification
            Session session = Session.getDefaultInstance(props, null);
            Store store = session.getStore("imaps");
            store.connect(host, username, password);

            // Ouvrez le dossier de la boîte de réception
            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);

            // Obtenez le nombre total de messages
            int totalMessages = inbox.getMessageCount();

            /*int nbrNouveauxMsg = 0;
            if(nbrMessagesInBox != -1) {
                if(totalMessages > nbrMessagesInBox) { //si il y a un nouveau message dans la boite...
                    nbrNouveauxMsg = totalMessages - nbrMessagesInBox;
                }
            }
            nbrMessagesInBox = totalMessages;*/

            // Déterminez la plage de messages que vous souhaitez récupérer (les 50 derniers)
            int start = Math.max(1, totalMessages - 50);
            int end = totalMessages;

            // Obtenez la liste des messages dans la plage spécifiée
            Message[] messages = inbox.getMessages(start, end);

            int n=1;
            // Parcourez les messages et affichez-les
            listMsg = new ArrayList<>();
            for (Message msg : messages) {
                listMsg.add(msg);

                //String subject = msg.getSubject();
                //String sender = msg.getFrom()[0].toString();

                // Ajoutez le sujet et l'expéditeur au modèle de liste
                //listModel.addElement("N: " + n + "  De : " + sender + " - Sujet : " + subject);
                //listModel.addElement("=============================================================================================================");
                n++;
            }

            // Fermez le dossier de la boîte de réception et la connexion
            inbox.close(false);
            store.close();

            // Mettez à jour la JList avec le modèle de liste
            //list1.setModel(listModel);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listMsg;
    }

    public void PieceJointe()
    {
        JFileChooser fileChooser = new JFileChooser();

        // Affiche la boîte de dialogue de sélection de fichier
        int returnValue = fileChooser.showOpenDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            // Récupère le chemin d'accès au fichier sélectionné
            piece_path = fileChooser.getSelectedFile().getAbsolutePath();
            System.out.println("Chemin d'accès au fichier sélectionné : " + piece_path);
        } else {
            System.out.println("Aucun fichier sélectionné.");
        }
    }

    public void trouverMTA(JList list1) {
        DefaultListModel<String> listModel = new DefaultListModel<>();

        String host = "smtp.gmail.com";
        String username = "maximerenardhepl@gmail.com";
        String password = "ztnjudjfqvapunwo";

        Properties props = new Properties();
        props.put("mail.store.protocol", "imaps");
        props.put("mail.imaps.host", host);
        props.put("mail.imaps.port", "993");

        try {
            Session session = Session.getInstance(props, null);
            Store store = session.getStore();
            store.connect(host, username, password);

            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);

            Message[] messages = inbox.getMessages();
            int n=1;
            for (Message message : messages) {
                String sujet = "N: " + n + "  Sujet : " + message.getSubject();
                listModel. addElement(sujet);

                Enumeration<Header> headers = message.getAllHeaders();
                while (headers.hasMoreElements()) {
                    Header header = headers.nextElement();
                    if (header.getName().equalsIgnoreCase("Received")) {
                        String mta = "MTA : " + header.getValue();
                        listModel.addElement(mta);
                    }
                }
                listModel.addElement("==================================================================================");
                n++;
            }

            inbox.close(false);
            store.close();

            // Mettez à jour la JList avec le modèle de liste
            list1.setModel(listModel);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
