package Thread;

import Modele.MessageData;
import controleur.control;

import java.awt.*;
import java.util.ArrayList;

public class ThreadReceptionMail extends Thread {
    public ThreadReceptionMail() {}

    @Override
    public void run() {
        System.out.println("Je suis le thread : " + Thread.currentThread().getName() + " (Thread Réception Mail)");

        while(!Thread.currentThread().isInterrupted()) {
            try {
                int currentSize = control.getInstance().getListMsg().size();
                ArrayList<MessageData> listeMsg = control.getInstance().receive();
                int newSize = listeMsg.size();

                if(newSize != currentSize) {
                    EventQueue.invokeLater(new TaskUpdateJListMails());
                }

                Thread.sleep(10000);
            }
            catch(InterruptedException e) {
                System.out.println("(Thread " + Thread.currentThread().getName() + ") Interrupted Exception catchée : " + e.getMessage());
            }
        }
    }
}
