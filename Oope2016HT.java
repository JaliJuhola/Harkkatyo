
import apulaiset.*;
import java.io.FileNotFoundException;
/**
 * 
 * @author Jali Juhola Juhola.Jali.V@Student.uta.fi
 * Tampereen yliopisto, Informaatiotieteen laitos.
 * Ajoluokka jossa luodaan kayttoliittymaolio ja kutsutaan pelaa metodia.
 */
public class Oope2016HT {


    public static void main(String[] args) throws KarttaaEiLadattu, VirheellisetPeliTiedostot, FileNotFoundException {
        Kayttoliittyma kl = new Kayttoliittyma();
        kl.pelaa();
    }
}
