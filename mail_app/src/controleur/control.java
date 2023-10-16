package controleur;

import Modele.MessageData;
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
    private ArrayList<MessageData> listMsg;
    private int nbrMessagesInBox;

    DefaultListModel<String> listModel = new DefaultListModel<>();

    public String GetPassword()
    {
        return password;
    }

    public int getMessageCount() { return this.nbrMessagesInBox; }

    public void setRefView(principale fenetrePrincipale) { this.refMainView = fenetrePrincipale; }

    public principale getRefMainView() { return refMainView; }

    public ArrayList<MessageData> getListMsg() { return listMsg; }

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
            //message.setFrom(new InternetAddress("noa.lallemand.testMail@gmail.com"));
            message.setFrom(new InternetAddress("maximerenardhepl@gmail.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(username));

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

    public synchronized ArrayList<MessageData> receive() {
        // Paramètres IMAP du serveur de messagerie
        String host = "imap.gmail.com";

        String username = "maximerenardhepl@gmail.com";
        String password = "ztnjudjfqvapunwo";

        //String username = "noa.lallemand.testMail@gmail.com";
        //String password = "rvvoapvidjolgcmj";

        Properties props = new Properties();
        props.setProperty("mail.store.protocol", "imaps");
        props.setProperty("mail.imaps.host", host);

        ArrayList<MessageData> listeNouveauxMsg = new ArrayList<>();
        try {
            // Crée une session de messagerie avec authentification
            Session session = Session.getDefaultInstance(props, null);
            Store store = session.getStore("imaps");
            store.connect(host, username, password);

            // Ouvre le dossier de la boîte de réception
            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);

            int totalMessages = inbox.getMessageCount();

            int nbrNouveauxMsg = 0;
            if(nbrMessagesInBox != -1) {
                if(totalMessages > nbrMessagesInBox) { //si il y a un nouveau message dans la boite...
                    nbrNouveauxMsg = totalMessages - nbrMessagesInBox;
                }
            }
            nbrMessagesInBox = totalMessages;

            int start, end;
            if(nbrNouveauxMsg != 0) {
                start = Math.max(1, totalMessages - nbrNouveauxMsg);
                end = totalMessages;

                // Obtient la liste des messages dans la plage spécifiée
                Message[] messages = inbox.getMessages(start, end);

                MessageData nouveauMsg;
                for (Message msg : messages) {

                    String expediteur = msg.getFrom()[0].toString();
                    String sujet = msg.getSubject();

                    nouveauMsg = new MessageData();
                    nouveauMsg.setSession(session);
                    nouveauMsg.setExpediteur(expediteur);
                    nouveauMsg.setSujet(sujet);

                    System.out.println("-------------------------------");
                    System.out.println("Nouveau Message: \n" + "Expéditeur: " + nouveauMsg.getExpediteur() + "Sujet: " + nouveauMsg.getSujet());

                    if(msg.isMimeType("text/plain")) {
                        System.out.println("Ce message est un simple message (text/plain)...");

                        nouveauMsg.setContenu((String) msg.getContent());
                    }
                    else if(msg.isMimeType("multipart/*")) {

                    }

                    listeNouveauxMsg.add(0, nouveauMsg);

                    int tailleListe = listeNouveauxMsg.size();
                    listeNouveauxMsg.remove(tailleListe-1);
                }
            }
            else {
                // Détermine la plage de messages qu'on souhaite récupérer (les 50 derniers)
                start = Math.max(1, totalMessages - 50);
                end = totalMessages;

                // Obtient la liste des messages dans la plage spécifiée
                Message[] messages = inbox.getMessages(start, end);

                MessageData nouveauMsg;
                for (Message msg : messages) {

                    String expediteur = msg.getFrom()[0].toString();
                    String sujet = msg.getSubject();

                    nouveauMsg = new MessageData();
                    nouveauMsg.setSession(session);
                    nouveauMsg.setExpediteur(expediteur);
                    nouveauMsg.setSujet(sujet);

                    System.out.println("-------------------------------");
                    System.out.println("Nouveau Message: \n" + "Expéditeur: " + nouveauMsg.getExpediteur() + "Sujet: " + nouveauMsg.getSujet());

                    if(msg.isMimeType("text/plain")) {
                        System.out.println("Ce message est un simple message (text/plain)...");

                        nouveauMsg.setContenu((String) msg.getContent());
                    }
                    else if(msg.isMimeType("multipart/*")) {

                    }

                    listeNouveauxMsg.add(nouveauMsg);
                }
            }

            // Fermez le dossier de la boîte de réception et la connexion
            inbox.close(false);
            store.close();

            // Mettez à jour la JList avec le modèle de liste
            //list1.setModel(listModel);

        } catch (Exception e) {
            e.printStackTrace();
        }

        listMsg = listeNouveauxMsg;
        return listeNouveauxMsg;
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

    public synchronized void trouverMTA(JList list1) {
        DefaultListModel<String> listModel = new DefaultListModel<>();

        String host = "smtp.gmail.com";
        String username = "maximerenardhepl@gmail.com";
        String password = "ztnjudjfqvapunwo";

        //String username = "noa.lallemand.testMail@gmail.com";
        //String password = "rvvoapvidjolgcmj";

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
