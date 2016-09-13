
/**
 * Esine luokka, joka on KentanObjektit toteuttava luokka.
 * Toteuttaa rajapinnat Paikallinen ja Suunnallinen
 * <p>
 * Harjoitustyö, Olio-ohjelmoinnin perusteet, kevät 2016.
 * <p>
 * @author Jali Juhola (Juhola.Jali.V@student.uta.fi), Informaatiotieteiden
 * yksikkö, Tampereen yliopisto.
 */
public class Esine extends KentanObjektit {

    public final static String ESINEMJ = "E";

    /**
     * Esine luokan konstruktori. Ainut konstruktori Esine luokassa, kaikki
     * parametrit syotetaan KentantanObjketit konstruktoriin.
     * @param sarake sarakekoordinaatti joka maarittelee sijoituksen
     * pelikentalla.
     * @param rivi rivikoordinaatti joka maarittelee sijoituksen pelikentalla.
     * @param energia kohteelle alustusvaiheessa lisattava energia.
     */
    public Esine(int sarake, int rivi, int energia) {
        super(energia, sarake, rivi);

    }

    /**
     * Esine luokan toString metodi. Esin luokan merkkijonoesitys, ylikirjoittaa
     * Object luokan toString metodin.
     *
     * @return Palauttaa esine luokan String muotoisen esitysmuodon kartalla.
     */
    @Override
    public String toString() {
        return ESINEMJ;
    }

    /**
     * Onko samaan ruutuun KentanObjektin siirtaminen mahdollista. Paikallinen
     * luokasta peritty.
     *
     * @return palauttaa true
     */
    @Override
    public boolean sallittu() {
        return true;
    }

    /**
     * Onko olion energia suurempi, pienempi vai yhtäsuuri parametrin olion
     * energian kanssa. Comparable rajapinnan metodi.
     *
     * @param objekti KentanObjekti tyyppinen olio, jonka energiaa verrataan
     * kutsujan energiaan.
     * @return palauttaa 1, jos kutsuvan olion energia on suurempi 0 jos yhtä
     * suuria ja -1 jos parametrin.
     */
    @Override
    public int compareTo(KentanObjektit objekti) {
        if (super.energia() > objekti.energia()) {
            return 1;
        } else if (this.energia() == objekti.energia()) {
            return 0;
        } else {
            return -1;
        }
    }
    /**
     * Esineen merkkijonoesitys tiedostossa, seka tulosteessa.
     * tasaa rivit 0-99 valilla sarakkeen ja rivin osalta energian taas 0-999.
     * @return palauttaa Esineen merkkijonoesityksen.
     */
    @Override
    public String tiedostoon() {
        String palautus = "Esine    |" + this.rivi();
        if (this.rivi() < 10) {
            palautus += "   |" + this.sarake();
        } else if (this.rivi() >= 10) {
            palautus += "  |" + this.sarake();
        }
        if (this.sarake() < 10) {
            palautus += "   |" + this.energia();
        } else if (this.sarake() >= 10) {
            palautus += "  |" + this.energia();
        }
        if (this.energia() < 10) {
            palautus += "   |";
        } else if (this.energia() < 100) {
            palautus += "  |";
        } else if (this.energia() < 1000) {
            palautus += " |";
        } else {
            palautus += "|";
        }
        return palautus;

    }

}
