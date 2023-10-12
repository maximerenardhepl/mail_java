
import com.formdev.flatlaf.FlatDarculaLaf;
import controleur.control;
import mail_interface.principale;

public class Main {
    public static void main(String[] args) {
        FlatDarculaLaf.setup();
        //instancie la fenetre principale appeller box
        principale fenetrePrincipale = new principale();
        fenetrePrincipale.setVisible(true);
    }
}