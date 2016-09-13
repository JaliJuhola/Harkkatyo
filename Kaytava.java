
import apulaiset.Paikallinen;

/**
 * Kaytava luokka, jota kaytetaan Kentalla. Perii luokan KentanRakenne, seka
 * toteuttaa rajapinnat Paikallinen ja Suunnallinen.
 * <p>
 * Harjoitustyo, Olio-ohjelmoinnin perusteet, kevat 2016.
 * <p>
 * @author Jali Juhola (Juhola.Jali.V@Student.uta.fi), Informaatiotieteiden
 * yksikkö, Tampereen yliopisto.
 */
public class Kaytava extends KentanRakenne {

    /**
     * OmaLista tyyppinen muuttuja, joka sailoo ruudulla olevat KentanObjekti
     * tyyppiset muuttujat.
     */
    private OmaLista ObjektitRuudussa;

    /**
     * Kaytava luokan konstruktori. Ainut konstruktori Kaytava luokassa, kaikki
     * parametrit syotetaan KentantanRakenne konstruktoriin. Alustaa myos
     * ObjektitRuudussa oliomuuttujan.
     *
     * @param sarake sarakekoordinaatti joka maarittelee sijoituksen
     * pelikentalla.
     * @param rivi rivikoordinaatti joka maarittelee sijoituksen pelikentalla.
     */
    public Kaytava(int sarake, int rivi) {
        super(sarake, rivi);
        ObjektitRuudussa = new OmaLista();
    }

    /**
     * Kaytava luokan toString metodi. Kaytava luokan merkkijonoesitys,
     * ylikirjoittaa object luokan toString metodin.
     *
     * @return Palauttaa Kaytava luokan String muotoisen esitysmuodon kartalla.
     * H huomioiden siina olevat muuttujat
     */
    @Override
    public String toString() {
        // jos ruutu on tyhja palauttaa Kaytavan merkkijonoesityksen
        if (ObjektitRuudussa.koko() == 0) {
            return " ";
        }

        boolean monkija = false;
        boolean robotti = false;
        // Kaydaan ruudussa olevat objektit lapi ja tulostetaan hierarkiassa ylimman merkki.
        for (int i = 0; i < ObjektitRuudussa.koko(); i++) {
            if (ObjektitRuudussa.alkio(i) instanceof Monkija) {
                monkija = true;
            } else if (ObjektitRuudussa.alkio(i) instanceof Robotti) {
                robotti = true;
            }
        }
        if (monkija) {
            return "M"; // Monkijan mjesitys
        }
        if (robotti) {
            return "R"; //Robotin mjesitys
        }
        return Esine.ESINEMJ; // Esineen mjesitys
    }

    /**
     * ObjektitRuudussa getteri.
     *
     * @return Palauttaa listan jossa on olion ruudussa olevat KentanObjektit.
     */
    public OmaLista getObjektit() {
        return this.ObjektitRuudussa;
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
     * Lisaa kaytavalle KentanObjektit tyyppisen olion. Kutsuu OmaLista.lisaa
     * metodia, joka jarjestaa olion listalle automaattisesti energian mukaan.
     *
     * @param obj OmaLista tyyppiseen muuttujaan lisattava muuttuja.
     */
    public void lisaaObjekti(KentanObjektit obj) {
        ObjektitRuudussa.lisaa(obj);
    }

    /**
     * Kaytavan merkkijonoesitys tiedostossa, seka tulosteessa. tasaa rivit 0-99
     * valilla sarakkeen ja rivin osalta energian taas 0-999. Kay
     * ObjektitRuudussa listan lapi ja ja tulostaa siina olevien olioiden
     * merkkijonoesityksen
     *
     * @return palauttaa Kaytavan merkkijonoesityksen.
     */
    @Override
    public String tiedostoon() {
        String palautus = "Kaytava  |" + this.rivi();
        
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
        for (int i = 0; i < ObjektitRuudussa.koko(); i++) {
            palautus += "ww";
            palautus += ((KentanObjektit) ObjektitRuudussa.alkio(i)).tiedostoon();

        }
        return palautus;
    }
}
