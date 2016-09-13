
import apulaiset.Paikallinen;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Robotti luokka, jota kaytetaan kentalla kuvaamassa roboottia Toteuttaa
 * rajapinnat Suunnallinen ja Paikallinen ja perii luokan KentanObjektit
 * <p>
 * Harjoitustyo, Olio-ohjelmoinnin perusteet, kevat 2016..
 * <p>
 * @author Jali Juhola Juhola.Jali.V@Student.uta.fi, Informaatiotieteiden
 * yksikkö, Tampereen yliopisto.
 */
public class Robotti extends KentanObjektit implements apulaiset.Suunnallinen {

    public final static String ROBOTTIMJ = "R";
    /**
     * Robootin suunta, joka edellytetaan rajapinnan Suunnallinen metodien
     * toteuttamiseen.
     */
    private char suunta_;

    /**
     * Robotti luokan konstruktori. Ainut konstruktori Robotti luokassa, kaikki
     * parametrit syotetaan KentantanObjketit konstruktoriin suuntaa
     * lukuunottamatta joka syotetaan oliomuuttujaan.
     *
     * @param sarake sarakekoordinaatti joka maarittelee sijoituksen
     * pelikentalla.
     * @param rivi rivikoordinaatti joka maarittelee sijoituksen pelikentalla.
     * @param energia kohteelle alustusvaiheessa lisattava energia.
     * @param suunta olion suunta kartalla.
     */
    public Robotti(int energia, char suunta, int sarake, int rivi) {
        super(energia, sarake, rivi);
        this.suunta_ = suunta;
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
        if (objekti.energia() > this.energia()) {
            return -1;
        } else if (this.energia() == objekti.energia()) {
            return 0;
        }
        return 1;

    }

    /**
     * Esine luokan toString metodi. Esin luokan merkkijonoesitys, ylikirjoittaa
     * Object luokan toString metodin.
     *
     * @return Palauttaa esine luokan String muotoisen esitysmuodon kartalla.
     */
    @Override
    public String toString() {
        return ROBOTTIMJ;
    }

    /**
     * Suunta olion getteri. Suunnallinen rajapinnasta saatu metodi.
     *
     * @return palauttaa robootin tamanhetkisen suunnan char muuttujana
     */
    @Override
    public char suunta() {
        return this.suunta_;
    }

    /**
     * Suunta olion setteri. Suunnallinen rajapinnasta saatu metodi.
     *
     * @param ilmansuunta char tyyppinen suunta vakio, jotka on maaritelty
     * Suunnallinen rajapinnassa.
     */
    @Override
    public void suunta(char ilmansuunta) throws IllegalArgumentException {
        this.suunta_ = ilmansuunta;
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
     * Robotin merkkijonoesitys tiedostossa, seka tulosteessa.
     * tasaa rivit 0-99 valilla sarakkeen ja rivin osalta robotin energian osalta taas 0-999.
     * @return palauttaa Esineen merkkijonoesityksen.
     */
    @Override
    public String tiedostoon() {
        // lisaa robootin merkkijonoesityksen String muuttujaan ja palauttaa sen.
        String palautus = "Robotti  |" + this.rivi();
        // tasaa rivin merkkijonoesityksen 0-99 valilta.
        if (this.rivi() < 10) {
            palautus += "   |" + this.sarake();
        } else if (this.rivi() >= 10) {
            palautus += "  |" + this.sarake();
        } 
        // tasaa sarakkeen merkkijonoesityksen 0-99 valilta
        if (this.sarake() < 10) {
            palautus += "   |" + this.energia();
        } else if (this.sarake() >= 10) {
            palautus += "  |" + this.energia();
        }
        // tasaa energian merkkijonoesityksen 0-999 valilta
        if (this.energia() < 10) {
            palautus += "   |";
        } else if (this.energia() < 100) {
            palautus += "  |";
        } else if (this.energia() < 1000) {
            palautus += " |";
        } else {
            palautus += "|";
        }
        palautus += this.suunta() + "   |";
        // palauttaa merkkijonon
        return palautus;
    }
    /** 
     * Siirtaa robootin automaatti luokan asettamiin koordinaatteihin.
     * Kay lapi ruudut robootin ymparilta ja etsii robootin aiemman sijainnin ja poistaa sen,
     * seka lisaa robotti muuttujan uuteen kaytava olioon.
     * @param kentta Paikallinen tyyppinen kaksiulotteinen taulu jota muutetaan metodissa.
     * 
     */
    public void siirra(Paikallinen[][] kentta) {
        for (int i = -1; i < 2; i++) { 
            for (int e = -1; e < 2; e++) {
                if (e == 0 || i == 0 && (e != 0 || i != 0)) {
                    Paikallinen objekti = kentta[rivi() + e][sarake() + i]; // lisaa ymparilla olevat objektit yksitellen objekti muuttujaan
                    if (objekti instanceof Kaytava) { // tarkistaa onko olio Kaytava tyyppinen
                        for (int k = 0; k < ((Kaytava) objekti).getObjektit().koko(); k++) {
                            // Kay lapi kaytavalla olevat objektit ja tarkistaa onko objekti itse siella.
                            // jos on poistaa sen
                            if (((Kaytava) objekti).getObjektit().alkio(k).equals(this)) {
                                Robotti r = (Robotti) ((Kaytava) objekti).getObjektit().poista(this);
                                ((Kaytava) kentta[this.rivi()][this.sarake()]).lisaaObjekti(r);

                            }
                        }
                    }
                }
            }
        }
    }

}
