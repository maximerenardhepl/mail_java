
import com.formdev.flatlaf.FlatDarculaLaf;
import controleur.control;
import mail_interface.box;
public class Main {
    public static void main(String[] args) {
        //control.getInstance().trouverMTA();

        FlatDarculaLaf.setup();
        //instancie la fenetre principale appeller box
        box fenetrePrincipale = new box();
        fenetrePrincipale.setVisible(true);
    }
}