
import apulaiset.*;
import java.io.FileNotFoundException;

/**
 * Kayttoliittyma luokan toiminnallisuudet on toteutettu taalla. pelikentta
 * muuttujaa kasitellaa taalla.
 * <p>
 * Harjoitustyo, Olio-ohjelmoinnin perusteet, kevat 2016.
 * <p>
 * @author Jali Juhola (Juhola.Jali.V@Student.uta.fi), Informaatiotieteiden
 * yksikko, Tampereen yliopisto.
 *
 */

public class KayttoliittymanLogiikka {

    /**
     * Tallettaa Kentta tyyppisen muuttujan joka toimii pelimaailmana.
     */
    private Kentta pelikentta;

    /**
     * Luokan julkinen konstruktori. luodaan uusi Kentta ja viedaan se
     * olio-muuttujaan.
     *
     * @throws FileNotFoundException jos tiedostoa ei loydy.
     */
    public KayttoliittymanLogiikka() throws FileNotFoundException {
        try {
            this.pelikentta = new Kentta();
            pelikentta.generoiKentta();
        } catch (VirheellisetPeliTiedostot vpt) {
            System.err.println(vpt.getMessage());
            System.exit(1);
        }

    }

    /**
     * kutsuu pelikentan tulostaKentta metodia. tulostaa pelikentan
     * komentoriville.
     */
    public void tulostaKentta() {
        pelikentta.tulostaKentta();
    }

    /**
     * Tallentaa pelikentalla tapahtuneet muutokset tiedostoon.
     * kutsuu pelikentan paivitaTiedosto metodia.
     */

    public void tallenna() {
        this.pelikentta.paivitaTiedosto();
    }
    /**
     * Tulostaa monkijan varastossa olevat esineet ja monkijan.
     * Kutsuu monkijan tiedostoon metodia joka kutsuu esineiden tiedostoon metodeja.
     */
    public void inventoi() {
        // ww kuvastaa tassa rivinvaihtoa joten leikataan kohdasta ww jotta saadaan monkija ja esineet eri riveille.
        String[] tuloste = pelikentta.getMonkija().tiedostoon().split("ww");
        for (String s : tuloste) {
            // Yhdella rivilla on yksi KentanObjekti
            System.out.println(s);
        }
    }
    /**
     * Katsoo jonkin monkijan viereisen ruudun tilanteen eli onko siela robootteja tai esineita.
     * @param suunta Paikallinen rajapinnan vakioissa maaritetty suunta.
     */
    public void katso(char suunta) {
        Monkija monkija = this.pelikentta.getMonkija(); 
        // haetaan monkija jotta ei tarvitse kokoajan hakea sita pelikentan kautta.
        switch (suunta) {
            // oikean suunnan loytyessa tulostetaan ruudulla olevan kentanRakenteen tiedot ja jos kyseessa on Kaytava tulostaa 
            // tama myos sen sisalla olevien olioiden merkkijonoesityksen.
            case Suunnallinen.ETELA: {
                String[] tuloste = pelikentta.getKentta()[monkija.rivi() + 1][monkija.sarake()].tiedostoon().split("ww");
                for (String s : tuloste) {
                    System.out.println(s);
                }
                break;
            }
            
            case Suunnallinen.POHJOINEN: {
                String[] tuloste = pelikentta.getKentta()[monkija.rivi() - 1][monkija.sarake()].tiedostoon().split("ww");
                for (String s : tuloste) {
                    System.out.println(s);
                }
                break;
            }
            
            case Suunnallinen.ITA: {
                String[] tuloste = pelikentta.getKentta()[monkija.rivi()][monkija.sarake() + 1].tiedostoon().split("ww");
                for (String s : tuloste) {
                    System.out.println(s);
                }
                break;
            }
            case Suunnallinen.LANSI: {
                String[] tuloste = pelikentta.getKentta()[monkija.rivi()][monkija.sarake() - 1].tiedostoon().split("ww");
                for (String s : tuloste) {
                    System.out.println(s);
                }
                break;
            }
            
            default:
                System.out.println(Kayttoliittyma.VIRHE);
                break;
        }
    }
    /**
     * Siirtaa monkijaa parametrissa maaritettyyn suuntaan.
     * siirtaa myos kentalla olevia robootteja automaatin avulla satunnaisesti.
     * @param suunta suunta johon monkijaa yritetaan siirtaa, syote on Paikallinen rajapinnassa maaritetty vakio.
     * @return true jos siirto onnistui
     */
    public boolean liiku(char suunta) {

        boolean onnistuiko = pelikentta.getMonkija().siirra(suunta, this.pelikentta.getKentta(), this.pelikentta.vapaatEsineet());
        if (!onnistuiko) {
            System.out.println("Kops!");
        }
        boolean loppuuko = Simulaatio.simuloi(this.pelikentta.getRobootit(), this.pelikentta.getMonkija(), this.pelikentta.getKentta());
        if (this.loppuukoPeli() == -1) {
            this.pelikentta.tulostaKentta();
            System.out.println(Kayttoliittyma.HAVISIT);
            System.exit(0);
        } else if (this.loppuukoPeli() == 1) {
            this.pelikentta.tulostaKentta();
            System.out.println(Kayttoliittyma.LOPETETTU);
            System.exit(0);
        }
        Automaatti.paivitaPaikat(this.pelikentta.getRobootit(), this.pelikentta.getKentta());
        for (int i = 0; i < this.pelikentta.getRobootit().koko(); i++) {
            ((Robotti) this.pelikentta.getRobootit().alkio(i)).siirra(this.pelikentta.getKentta());
        }
        loppuuko = Simulaatio.simuloi(this.pelikentta.getRobootit(), this.pelikentta.getMonkija(), this.pelikentta.getKentta());
        if (this.loppuukoPeli() == -1) {
            System.out.println(Kayttoliittyma.HAVISIT);
            System.exit(0);
        }
        this.tulostaKentta();
        return true;
    }
    
   /** 
    * Odottaa vuoron ja antaa roboottien liikkua.
    * Monkija ei liiku, mutta robootit liikkuvat Automaatin avulla satunnaisiin suuntiin.
    */
    public void odota() {
                // paivitetaan kaikkien roboottien paikat
        Automaatti.paivitaPaikat(this.pelikentta.getRobootit(), this.pelikentta.getKentta());
        // Siirretaan kaikki robootit uusiin sijainteihinsa.
        for (int i = 0; i < this.pelikentta.getRobootit().koko(); i++) {
            ((Robotti) this.pelikentta.getRobootit().alkio(i)).siirra(this.pelikentta.getKentta());

        }
        // Tarkistetaan ovatko robootit osuneet monkijan kanssa samaan ruutuun ja tuleeko naiden taistella.
        boolean loppuuko = Simulaatio.simuloi(this.pelikentta.getRobootit(), this.pelikentta.getMonkija(), this.pelikentta.getKentta());
        this.pelikentta.tulostaKentta();

        if (this.loppuukoPeli() == -1) {
            System.out.println(Kayttoliittyma.HAVISIT);
            System.exit(0);
        }

    }
    /**
     * Muuntaa monkijan inventaariossa olevia esineita energiaksi.
     * 
     * @param maara maara joka muunnetaan.
     */
    public void muunna(int maara) {
        boolean muunnettu = pelikentta.getMonkija().kayta(maara);
        if (!muunnettu) {
            System.out.println(Kayttoliittyma.VIRHE);
        }
    }
    /**
     * Lataa kartan tiedostosta ja lopettaa nykyisen pelin ellei sita ole tallennettu.
     * vaatii, etta Kentassa maaritetty tiedosto on luotu projektiin.
     * @throws FileNotFoundException pelitiedostoa ei loydy.
     */
    public void lataa() throws FileNotFoundException {
        try {
            this.pelikentta = new Kentta();
            pelikentta.generoiKentta();
        } catch (VirheellisetPeliTiedostot vpt) {
            System.err.println(vpt.getMessage());
            System.exit(1);
        }
    }
    /**
     * Tarkistaa loppuuko peli.
     * Tarkistaa onko monkija tuhoutunut tai vapaatesineet loppuneet jolloin peli loppuu.
     * @return  palauttaa -1 jos monkija on tuhoutunut, 1 jos kaikki vapaat esineet ovat loppuneet ja 0 jos peli jatkuu.
     */
    public int loppuukoPeli() {
        if (this.pelikentta.getMonkija().energia() < 1) {
            return -1;
        } else if (this.pelikentta.vapaatEsineet().koko() == 0) {
            return 1;
        }
        return 0;
    }

}
