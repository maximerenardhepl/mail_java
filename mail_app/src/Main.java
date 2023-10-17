import Thread.ThreadReceptionMail;
import com.formdev.flatlaf.FlatDarculaLaf;
import controleur.control;
import mail_interface.principale;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        FlatDarculaLaf.setup();
        //instancie la fenetre principale appeller box
        principale fenetrePrincipale = new principale();

        control.getInstance().setRefView(fenetrePrincipale);

        ThreadReceptionMail threadReceptMails = new ThreadReceptionMail();
        threadReceptMails.setDaemon(true);
        threadReceptMails.start();

        fenetrePrincipale.setVisible(true);
    }
}