
import apulaiset.Paikallinen;
import apulaiset.Suunnallinen;
   /**
     * Simulaatio luokka, jossa simuloidaan taistelut
     * 
     * <p>
     * Harjoitustyo, Olio-ohjelmoinnin perusteet, kevat 2016..
     * <p>
     * @author Jali Juhola Juhola.Jali.V@Student.uta.fi,
     * Informaatiotieteiden yksikkö, Tampereen yliopisto.
     */
public class Simulaatio {
    /**
     * 
     * @param robootit Omalista jolla on kentalla olevat robootit
     * @param monkija Monkija olio
     * @param kentta Kentta olio
     * @return palauttaa true jos monkija selviytyi simulaatiosta ja false jos monkijan energia laskee alle yhden
     */
    public static boolean simuloi(OmaLista robootit, Monkija monkija, Paikallinen[][] kentta) {
        for (int i = 0; i < robootit.koko(); i++) {
            KentanObjektit ko = (KentanObjektit) robootit.alkio(i);
            if (ko instanceof Robotti) {
                if (((Robotti) ko).rivi() == monkija.rivi() && ko.sarake() == monkija.sarake()) {
                    if (monkija.energia() >= ko.energia()) {
                        monkija.energia(monkija.energia() - ko.energia());
                        ((Kaytava) kentta[ko.rivi()][ko.sarake()]).getObjektit().poista(ko);
                        robootit.poista(ko);
                        System.out.println("Voitto!");
                    } else {
                        System.out.println("Tappio!");
                        monkija.energia(0);
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
