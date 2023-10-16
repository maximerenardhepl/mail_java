package Thread;

import controleur.control;

import java.awt.*;

public class ThreadReceptionMail extends Thread {
    public ThreadReceptionMail() {}

    @Override
    public void run() {
        System.out.println("Je suis le thread : " + Thread.currentThread().getName() + " (Thread Réception Mail)");

        while(!Thread.currentThread().isInterrupted()) {
            try {
                Thread.sleep(10000);
                control.getInstance().receive();
                EventQueue.invokeLater(new TaskUpdateJListMails());
            }
            catch(InterruptedException e) {
                System.out.println("(Thread " + Thread.currentThread().getName() + ") Interrupted Exception catchée : " + e.getMessage());
            }
        }
    }
}
