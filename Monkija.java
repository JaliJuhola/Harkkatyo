
import apulaiset.Suunnallinen;
import apulaiset.Paikallinen;

/**
 * Monkija luokka, joka kuvastaa Kentalla olevaa monkijaa. Perii KentanObjektit
 * luokan ja toteuttaa luokan Suunnallinen.
 * <p>
 * Harjoitustyo, Olio-ohjelmoinnin perusteet, kevat 2016.
 * <p>
 * @author Jali Juhola (Juhola.Jali.V@student.uta.fi), Informaatiotieteiden
 * yksikko, Tampereen yliopisto.
 */
public class Monkija extends KentanObjektit implements Suunnallinen {

    public final static String MONKIJAMJ = "M";
    /**
     * Monkijan varastossa olevat esineet omaLista tyyppisessa muuttujassa.
     */
    private OmaLista varasto; // samassa kaytavapaikassa olevat objektit
    /**
     * Suuntaa kuvaava muuttuja joka jonkin Suunnallinen luokassa maaritellyista
     * vakioista.
     */
    private char suunta_;

    /**
     * Monkija luokan ainut konstruktori. Konstruktorissa syotetaan kaikki
     * parametrit suuntaa lukuunottamatta KentanObjektit konstruktoriin.
     *
     * @param energia Kuvaa monkijan energiaa.
     * @param suunta Kuvaa monkijan suuntaa Suunnallinen luokassa maarityjen
     * vakioiden avulla.
     * @param sarake Kuvaa monkijan saraketta Kentalla.
     * @param rivi Kuvaa monkijan rivia Kentalla.
     */
    public Monkija(int energia, char suunta, int sarake, int rivi) {
        super(energia, sarake, rivi);
        this.suunta_ = suunta;
        this.varasto = new OmaLista();
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
        } else if (objekti.energia() < this.energia()) {
            return 1;
        }
        return 0;
    }

    /**
     * Object luokasta ylikirjoitettu metodi, joka ilmoittaa luokan
     * merkkijonoesityksen.
     *
     * @return Palauttaa monkijaluokan merkkijonoesityksen kartalla.
     */
    @Override
    public String toString() {
        return MONKIJAMJ;
    }

    /**
     * Lisaa esineen monkijan varastoon. Kayttaa OmaLista luokan lisaa metodia,
     * joka lisaa objekteja nousevaan jarjestykseen.
     *
     * @param e Lisattava Esine tyyppinen olio.
     */
    public void lisaaEsine(Esine e) {
        varasto.lisaa(e);
    }

    /**
     * Suunta oliomuuttujan getteri.
     *
     * @return palauttaa oliomuuttujan suunta arvon.
     */
    @Override
    public char suunta() {
        return this.suunta_;
    }

    /**
     * Suunta oliomuuttujan setteri. asettaa parametrin oliomuuttujaan
     *
     * @param ilmansuunta kuvastaa Monkijan ilmansuuntaa sallitut syottet ovat
     * Suunnallinen rajapinnan vakiot.
     * @throws IllegalArgumentException jos parametri ei ole sallittu.
     */
    @Override
    public void suunta(char ilmansuunta) throws IllegalArgumentException {
        if (ilmansuunta == Suunnallinen.ETELA || ilmansuunta == Suunnallinen.ITA
                || ilmansuunta == Suunnallinen.LANSI || ilmansuunta == Suunnallinen.POHJOINEN) {
            this.suunta_ = ilmansuunta;
        } else {
            throw new IllegalArgumentException("Syottamasi ilmansuunta on virheellinen. ( " + ilmansuunta + ")");
        }
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
     * Kaytavan merkkijonoesitys tiedostossa , seka tulosteessa. tasaa rivit
     * 0-99 valilla sarakkeen ja rivin osalta energian taas 0-999. Kay varaston
     * lapi ja ja tulostaa siina olevien olioiden merkkijonoesityksen
     *
     * @return palauttaa monkijan merkkijonoesityksen.
     *
     */
    @Override
    public String tiedostoon() {
        String palautus = "Monkija  |" + this.rivi();
        // tasaa rivin merkkijonoesityksen 0-99 valilla.
        if (this.rivi() < 10) {
            palautus += "   |" + this.sarake();
        } else if (this.rivi() >= 10) {
            palautus += "  |" + this.sarake();
        }
        // tasaa sarakkeen merkkijonoesityksen 0-99 valilla.
        if (this.sarake() < 10) {
            palautus += "   |" + this.energia();
        } else if (this.sarake() >= 10) {
            palautus += "  |" + this.energia();
        }
        // tasaa energian merkkijonoesityksen valilla 0-999.
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
        // kay lapi monkijan inventaarion 

        for (int i = 0; i < this.varasto.koko(); i++) {
            palautus += "ww" + ((Esine) this.varasto.alkio(i)).tiedostoon();
        }
        return palautus; // palauttaa merkkijonon
    }

    /**
     * Kayttaa parametrissa maaritetyn maaran esineita. Kaytetyiden esineiden
     * energia lisataan monkijan energiaan.
     *
     * @param n kaytettavien esineiden maara
     * @return palautttaa true jos parametri oli sallitulla valilla ja
     * muuntaminen onnistui.
     */
    public boolean kayta(int n) {
        if (n > varasto.koko()) {
            return false;
        }
        for (int i = 0; i < n; i++) {
            Esine e = ((Esine) this.varasto.alkio(0));
            this.energia(this.energia() + e.energia());
            this.varasto.poista(e);

        }
        return true;
    }

    /**
     * Siirtaa monkijan parametrissa maaritettyyn suuntaan.
     *
     * @param suunta Suunta johon monkijaa siirretaan.
     * @param kentta Kentta jota muokataan.
     * @param vapaatEsineet naita poistetaan jos monkija keraa esineita.
     * @return true jos siirto onnistui.
     */
    public boolean siirra(char suunta, KentanRakenne[][] kentta, OmaLista vapaatEsineet) {
        this.suunta_ = suunta;
        Monkija mkj = this;
        switch (suunta) {
            // muuttaa monkijan koordinaatteja sen mukaan mihin suuntaan monkijaa ollaan liikuttamassa ja poistaa monkijan vanhasta ruudusta.
            case Suunnallinen.POHJOINEN:
                if (kentta[super.rivi() - 1][super.sarake()].sallittu()) {
                    mkj = (Monkija) ((Kaytava) kentta[super.rivi()][super.sarake()]).getObjektit().poista(this);
                    super.rivi(rivi() - 1);
                    break;
                } else {
                    return false;
                }
            case Suunnallinen.ETELA:
                if (kentta[super.rivi() + 1][super.sarake()].sallittu()) {
                    mkj = (Monkija) ((Kaytava) kentta[super.rivi()][super.sarake()]).getObjektit().poista(this);
                    super.rivi(rivi() + 1);
                    break;
                } else {
                    return false;
                }
            case Suunnallinen.ITA:
                if (kentta[super.rivi()][super.sarake() + 1].sallittu()) {
                    mkj = (Monkija) ((Kaytava) kentta[super.rivi()][super.sarake()]).getObjektit().poista(this);
                    super.sarake(sarake() + 1);
                    break;
                } else {
                    return false;
                }
            case Suunnallinen.LANSI:
                if (kentta[super.rivi()][super.sarake() - 1].sallittu()) {
                    mkj = (Monkija) ((Kaytava) kentta[super.rivi()][super.sarake()]).getObjektit().poista(this);
                    super.sarake(sarake() - 1);
                    break;
                } else {
                    return false;
                }

        }
        // lisaa monkijan uuteen ruuttuun ja kutsuu metodia joka keraa esineet ja vaihtaa varaston esineiden koordinaatit oikeisiin.
        ((Kaytava) kentta[this.rivi()][this.sarake()]).lisaaObjekti(mkj);
        this.siirraEsineet(kentta, vapaatEsineet);
        return true;

    }

    /**
     * Vaihtaa varastossa olevien esineiden koordinaatteja ja keraa ruudulla
     * olevat esineet.
     *
     * @param kentta Kentta taulukko jolta poistetaan keratyt esineet.
     * @param vapaatEsineet OmaLista jolta poistetaan keratyt esineet, koska
     * nama eivat ole enaan vapaita.
     */
    public void siirraEsineet(KentanRakenne[][] kentta, OmaLista vapaatEsineet) {
        int t = 0;
        int listanKoko = ((Kaytava) (kentta[this.rivi()][this.sarake()])).getObjektit().koko(); //pienenee kuin esineita poistetaan joten asetetaan tassa.
        for (int i = 0; i < listanKoko; i++) {

            // kaydaan lapi kaikki KentanObjektit kaytavalla johon monkija on juuri siirtynyt.
            if (((Kaytava) kentta[this.rivi()][this.sarake()]).getObjektit().alkio(t) instanceof Esine) {

                // poistaa kaikki vapaat esineet monkijan taman hetkisesta ruudusta
                Esine e = (Esine) ((Kaytava) kentta[this.rivi()][this.sarake()]).getObjektit().alkio(t);
                Esine p = (Esine) ((Kaytava) kentta[this.rivi()][this.sarake()]).getObjektit().poista(e);
                Esine s = (Esine) vapaatEsineet.poista(p);
                this.varasto.lisaa(s);
            } else {
                t++;
            }
        }

        for (int i = 0; i < this.varasto.koko(); i++) {
            // vaihdetaan monkijan varastossa olevien esineiden koordinaatit vastamaan monkijan koordinaatteja.
            Esine e = (Esine) varasto.alkio(i);
            e.rivi(super.rivi());
            e.sarake(super.sarake());
        }
    }
}
