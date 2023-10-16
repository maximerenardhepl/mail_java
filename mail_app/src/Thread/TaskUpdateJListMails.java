package Thread;

import controleur.control;
import mail_interface.principale;

import javax.mail.Message;
import java.util.ArrayList;

public class TaskUpdateJListMails implements Runnable {
    public TaskUpdateJListMails() {}

    @Override
    public void run() {
        System.out.println("Objet RecoitMails : Début maj JList mails en cours...");
        principale fenPrincipale = control.getInstance().getRefMainView();
        ArrayList<Message> listMsg = control.getInstance().getListMsg();
        fenPrincipale.actualiseListeMails(listMsg);
        System.out.println("Fin de réception des mails...");
    }
}
