
import apulaiset.*;
import java.io.File;
import java.io.FileNotFoundException;
/**
 * Luokka joka toimii kayttoliittymana ja josta kutsutaan KayttoliittymanLogiikka luokan metodeja.
 * <p>
 * Harjoitustyö, Olio-ohjelmoinnin perusteet, kevät 2016.
 * <p>
 * @author Jali Juhola (Juhola.Jali.V@student.uta.fi), Informaatiotieteiden
 * yksikkö, Tampereen yliopisto.
 */

public class Kayttoliittyma {

    /**
     * Lataa komennon merkkijonovakio.
     */
    public final static String LATAA = "lataa";
    /**
     * inventoi komennon merkkijonovakio.
     */
    public final static String INVENTOI = "inventoi";
    /**
     * kartta komennon merkkijonovakio.
     */
    public final static String KARTTA = "kartta";
    /**
     * katso komennon merkkijonovakio.
     */
    public final static String KATSO = "katso";
    /**
     * lopeta komennon merkkijonovakio.
     */
    public final static String LOPETA = "lopeta";
    /**
     * odota komennon merkkijonovakio.
     */
    public final static String ODOTA = "odota";
    /**
     * liiku komennon merkkijonovakio.
     */
    public final static String LIIKU = "liiku";
    /**
     * muunna komennon merkkijonovakio.
     */
    public final static String MUUNNA = "muunna";
    /**
     * virhe komennon merkkijonovakio.
     */
    public final static String VIRHE = "Virhe!";
    /**
     * tallenna komennon merkkijonovakio.
     */
    public final static String TALLENNA = "tallenna";
    /**
     * Vakio joka tulostetaan kuin ohjelma lopetetaan.
     */
    public final static String LOPETETTU = "Ohjelma lopetettu.";
    /**
     * Vakio joka tulostetaan kuin monkija on tuhoutunut
     */
    public final static String HAVISIT = "Havisit!";

    /**
     * Metodi joka kaynnistaa pelin mainloopin. metodi toimii kayttajan
     * syotteella.
     *
     * @throws KarttaaEiLadattu jos karttaa ei ole ladattu
     * @throws VirheellisetPeliTiedostot jos pelitiedostoissa on virhe
     * @throws FileNotFoundException jos tiedostoa ei loydy
     */
    public void pelaa() throws KarttaaEiLadattu, VirheellisetPeliTiedostot, FileNotFoundException {

        System.out.println("***********");
        System.out.println("* SOKKELO *");
        System.out.println("***********");
        boolean mainLoop = true;
        KayttoliittymanLogiikka logiikka = new KayttoliittymanLogiikka();

        while (mainLoop) {
            // Mainloopista poistutaan kuin pelaaminen lopetetaan 
            System.out.println("Kirjoita komento:");
            String komentoRivi = apulaiset.In.readString();
            String[] komennot = komentoRivi.split(" ");
            // luetaan kayttajalta syote, jonka jalkeen switchilla tarkistetaan onko kyseessa joku ennalta
            // maaritellyista syotteista ja toimitaan syotteen mukaan.
            mainLoop = kutsuttuKomento(logiikka, komennot);
         
        }
    }
 /**
  * Toiminta kayttajan syotteen perusteella. 
  *   
  * @param logiikka Logiikka olio joka vastaa toiminnoista.
  * @param komennot Kayttajan syottama String taulukko tyyppinen olio.
  * @return palauttaa true jos peli jatkuu ja false jos peli loppuu.
  * @throws FileNotFoundException jos tiedostoa ei loydy.
  */
    public static boolean kutsuttuKomento(KayttoliittymanLogiikka logiikka, String[] komennot) throws FileNotFoundException {
        String komento = komennot[0];
        switch (komento) {
            case INVENTOI:
                // Inventoi komento tulostaa monkijan inventaarion
                if (komennot.length == 1) {
                    logiikka.inventoi();
                } else {
                    System.out.println(VIRHE);
                }
                break;
                
            case KATSO:
                // Katso komento katsoo parametrissa maaritetyssa ruudussa olevan ruudun tilanteen.
                if (komennot.length == 2) {
                    char suunta = komennot[1].charAt(0);
                    if ((suunta == Suunnallinen.ETELA || suunta == Suunnallinen.ITA || suunta == Suunnallinen.LANSI || suunta == Suunnallinen.POHJOINEN)
                            && komennot[1].trim().length() == 1) {
                        logiikka.katso(suunta);
                    } else {
                        System.out.println(VIRHE);
                    }
                } else {
                    System.out.println(VIRHE);
                }
                break;
                
            case KARTTA:
                // Tulostaa pelikentan
                if (komennot.length == 1) {
                    logiikka.tulostaKentta();
                } else {
                    System.out.println(VIRHE);
                }
                break;
                
            case LOPETA:
                // Sulkee pelin 
                if (komennot.length == 1) {
                    logiikka.tulostaKentta();
                    System.out.println(LOPETETTU);
                    return false;
                } else {
                    System.out.println(VIRHE);
                }
                break;
                
            case ODOTA:
                // Odottaa yhden vuoron ja katsoo mihin robootit liikkuvvat
                if (komennot.length == 1) {
                    logiikka.odota();
                    if (logiikka.loppuukoPeli() == 1) {
                        System.out.println(LOPETETTU);
                        return false;
                    } else if (logiikka.loppuukoPeli() == -1) {
                        System.out.println(HAVISIT);
                        return false;
                    }
                } else {
                    System.out.println(VIRHE);
                }
                break;
                
            case LIIKU:
                // Monkija liikkuu parametrissa maaritettyyn suuntaan
                if (komennot.length == 2) {
                    char suunta = komennot[1].charAt(0);

                    if ((suunta == Suunnallinen.ETELA || suunta == Suunnallinen.ITA || suunta == Suunnallinen.LANSI || suunta == Suunnallinen.POHJOINEN)
                            && komennot[1].trim().length() == 1) {
                       logiikka.liiku(suunta);
                    } else {
                        System.out.println(VIRHE);
                    }
                } else {
                    System.out.println(VIRHE);
                }
                if (logiikka.loppuukoPeli() == 1) {
                    System.out.println(LOPETETTU);
                    return false;
                } else if (logiikka.loppuukoPeli() == -1) {
                    System.out.println(HAVISIT);
                    return false;
                }

                break;
                
            case MUUNNA:
                // Muuntaa parametrissa maariteltyn maaran esineita energiaksi 
                if (komennot.length == 2) {
                    if (komennot[1].trim().length() == 1 && Character.isDigit(komennot[1].charAt(0))) {
                        logiikka.muunna(Integer.parseInt(komennot[1]));
                    } else {
                        System.out.println(VIRHE);
                    }
                } else {
                    System.out.println(VIRHE);
                }
                break;
            case TALLENNA:
                if (komennot.length == 1) {
                    logiikka.tallenna();
                } else {
                    System.out.println(VIRHE);
                }
                break;
            case LATAA:
                if (komennot.length == 1) {
                    logiikka.lataa();
                } else {
                    System.out.println(VIRHE);
                }
                break;
            default:
                System.out.println(VIRHE);
                break;
        }
        return true;
    }
}
