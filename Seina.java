
/**
 * Seina luokka, joka on KentanRuudut periva luokka.
 * Toteuttaa rajapinnan paikallinen.
 * <p>
 * Harjoitustyo, Olio-ohjelmoinnin perusteet, kevät 2016.
 * <p>
 * @author Jali Juhola (Juhola.Jali.V@student.uta.fi), Informaatiotieteiden
 * yksikko, Tampereen yliopisto.
 */
public class Seina extends KentanRakenne {

    public final static String SEINAMJ = ".";

    /**
     * Seina luokan konstruktori. Ainut konstruktori Seina luokassa, kaikki
     * parametrit syotetaan KentantanRuudut konstruktoriin.
     *
     * @param sarake sarakekoordinaatti joka maarittelee sijoituksen
     * pelikentalla.
     * @param rivi rivikoordinaatti joka maarittelee sijoituksen pelikentalla.
     */
    public Seina(int sarake, int rivi) {
        super(sarake, rivi);
    }

    /**
     * Seina luokan toString metodi. Seina luokan merkkijonoesitys,
     * ylikirjoittaa object luokan toString metodin.
     *
     * @return Palauttaa Seina luokan String muotoisen esitysmuodon kartalla.
     */
    @Override
    public String toString() {
        return SEINAMJ;
    }

    /**
     * Onko samaan ruutuun KentanObjektin siirtaminen mahdollista. Paikallinen
     * luokasta peritty.
     *
     * @return palauttaa false.
     */
    @Override
    public boolean sallittu() {
        return false;
    }

    /**
     * Seinan merkkijonoesitys tiedostossa, seka tulosteessa. tasaa rivit 0-99
     * valilla sarakkeen ja rivin osalta energian taas 0-999.
     *
     * @return palauttaa seinan merkkijonoesityksen.
     */
    @Override
    public String tiedostoon() {
        String palautus = "Seina    |" + this.rivi();
        if (this.rivi() < 10) {
            palautus += "   |" + this.sarake();
        } else if (this.rivi() >= 10) {
            palautus += "  |" + this.sarake();
        }
        if (this.sarake() < 10) {
            palautus += "   |";
        } else if (this.sarake() >= 10) {
            palautus += "  |";
        }
        return palautus;

    }
}
