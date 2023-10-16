package Modele;

import javax.mail.Session;
import java.util.List;

public class MessageData {

    private Session session;
    private String expediteur;
    private String sujet;
    private String contenu;
    private List<String> piecesJointes;

    public void setSession(Session s) {
        this.session = s;
    }

    public void setExpediteur(String exp) {
        this.expediteur = exp;
    }

    public void setSujet(String subject) {
        this.sujet = subject;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public void setPiecesJointes(List<String> piecesJointes) {
        this.piecesJointes = piecesJointes;
    }

    public Session getSession() { return this.session; }
    public String getExpediteur() { return this.expediteur; }
    public String getSujet() { return this.sujet; }
    public String getContenu() { return this.contenu; }
    public List<String> getPiecesJointes() { return this.piecesJointes; }
}
