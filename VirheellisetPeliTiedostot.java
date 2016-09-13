/**
 * Poikkeus joka heitetaan kuin pelitiedostoja yritetaan kasitella, mutta ne ovat virheelliset.
 * <p>
 * Harjoitustyö, Olio-ohjelmoinnin perusteet, kevät 2016.
 * <p>
 * @author Jali Juhola (Juhola.Jali.V@student.uta.fi), Informaatiotieteiden
 * yksikkö, Tampereen yliopisto.
 */

public class VirheellisetPeliTiedostot extends Exception {
    /**
     * Luokan julkinen konstruktori.
     * @param msg Viesti joka syotetaan ylaluokan konstruktoriin.
     */
   public VirheellisetPeliTiedostot(String msg) {
       super(msg);
   }
}
