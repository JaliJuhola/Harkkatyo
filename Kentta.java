
import apulaiset.Automaatti;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import apulaiset.Paikallinen;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

/**
 * Kentta luokka kuvaa kenttaa joka koostuu KentanRakenne tyyppisista olioista.
 * Kentta luokka lukee ja kirjoittaa tekstitiedostoon.
 * <p>
 * Harjoitustyo, Olio-ohjelmoinnin perusteet, kevät 2016.
 * <p>
 * @author Jali Juhola (Juhola.Jali.V@student.uta.fi), Informaatiotieteiden
 * yksikko, Tampereen yliopisto.
 */
public class Kentta {
    /**
     * metodi joka pitaa sisallaan kentan kaksiulotteisessa taulukossa.
     */
    private KentanRakenne[][] kentta;
    /**
     * sisaltaa kaikki kentalla olevat robotit joita siirretaan vuoron lopussa.
     */
    private OmaLista robootit;
    /**
     * monkija tyyppinen olio.
     */
    private Monkija monkija;
    /**
     * int tyyppinen siemenluku jota kaytetaan tiedostoon kirjoittamisessa ja alustuksessa.
     */
    private int siemenLuku;
    /**
     * Kaikki kentalla olevat vapaat esineet jotka eivat ole monkijan varastossa.
     * jos esineet loppuvat peli loppuu pelaajan voittoon.
     */
    private OmaLista vapaatEsineet;
    /**
     * vakio muotoinen merkkijono joka on pelitiedoston osoite.
     */
    public final static String TIEDOSTO = "sokkelo.txt";
    
    /**
     * Lukee kentan tekstitiedostosta keskusmuistiin
     * tama metodi alustaa kaikki oliomuuttujat ja asettaa arvot naihin.
     * @throws VirheellisetPeliTiedostot Jos pelitiedostoissa on ongelmia.
     * @throws FileNotFoundException Jos pelitiedostoa ei loydy.
     */
    public void generoiKentta() throws VirheellisetPeliTiedostot, FileNotFoundException {
        try {
            File f = new File(TIEDOSTO);
            Scanner sc = new Scanner(f);
            vapaatEsineet = new OmaLista();
            ArrayList<String[]> osaLista = new ArrayList<>(); // Tanne luetaan kaikki osat
            while (sc.hasNextLine()) {
                // luetaan rivi kerralaan tekstitiedostoa ja tallennetaan tulokset ArrayListiin
                String rivi = sc.nextLine();
                String[] osat = rivi.split("\\|");
                osaLista.add(osat);
            }
            // alustetetaan listat ja taulukot 
            this.siemenLuku = Integer.parseInt(osaLista.get(0)[0].replaceAll("\\s", ""));
            Automaatti.alusta(this.siemenLuku);
            this.robootit = new OmaLista();
            String[] viimeinenRivi = osaLista.get(osaLista.size() - 1);
            int korkeus = Integer.parseInt(viimeinenRivi[1].replaceAll("\\s", ""));
            int leveys = Integer.parseInt(viimeinenRivi[2].replaceAll("\\s", ""));
            this.kentta = new KentanRakenne[korkeus + 1][leveys + 1];
            
            // kaydaan osalista lapi 
            for (int i = 1; i < osaLista.size(); i++) {
                // Alussa luetaan kaikille olioille yhteiset x ja y koordinaatit
                int yKord = Integer.parseInt(osaLista.get(i)[1].replaceAll("\\s", ""));
                int xKord = Integer.parseInt(osaLista.get(i)[2].replaceAll("\\s", ""));
                
                if (osaLista.get(i)[0].replaceAll("\\s", "").equals("Seina")) {
                    // jos koordinaatti on tyhja siihen lisataan seina muuten heitetaan poikkeus
                    if (kentta[yKord][xKord] == null) {
                        kentta[yKord][xKord] = (new Seina(xKord, yKord));
                    } else {
                        throw new VirheellisetPeliTiedostot("Ruutu: " + xKord + "/" + yKord + "Ei ole tyhja vaan sisaltaa: " + kentta[yKord][xKord].getClass() + "n");
                    }
                    
                } else if (osaLista.get(i)[0].replaceAll("\\s", "").equals("Kaytava")) {
                    if (kentta[yKord][xKord] == null) {
                        // jos koordinaatti on tyhja siihe lisataan kaytava muuten heitetaan poikkeus
                        kentta[yKord][xKord] = (new Kaytava(xKord, yKord));
                    } else {
                        throw new VirheellisetPeliTiedostot("Ruutu: " + xKord + "/" + yKord + "Ei ole tyhja vaan sisaltaa: " + kentta[yKord][xKord].getClass() + "n");
                    }
                } else if (osaLista.get(i)[0].replaceAll("\\s", "").equals("Robotti")) {
                    // luetaan robootille tarvittavat tiedot 
                    char suunta = (osaLista.get(i)[4].replaceAll("\\s", "").charAt(0));
                    int energia = Integer.parseInt(osaLista.get(i)[3].replaceAll("\\s", ""));
                    Robotti rbt = new Robotti(energia, suunta, xKord, yKord);
                    robootit.lisaaLoppuun(rbt);
                    if (kentta[yKord][xKord] == null) { // KentanObjekteja ei voi lisata tyhjaan kenttaan vaan talloin heitetaan poikkeus
                        throw new VirheellisetPeliTiedostot("Ruutu: " + xKord + "/" + yKord + " on tyhja ja yritat lisata siihen Robotin");
                    } else if (kentta[yKord][xKord] instanceof Kaytava) {
                        // robootti voidaan lisata vain kaytavalle
                        ((Kaytava) kentta[yKord][xKord]).lisaaObjekti(rbt);
                    } else {
                        throw new VirheellisetPeliTiedostot("Ruudussa: " + xKord + "/" + yKord + ("Ei ole kaytavaa vaan: ") + kentta[yKord][xKord].getClass());
                    }
                    
                } else if (osaLista.get(i)[0].replaceAll("\\s", "").equals("Monkija")) {
                    // haetaan monkijalle tarpeelliset muuttujat
                    char suunta = (osaLista.get(i)[4].replaceAll("\\s", "").charAt(0));
                    int energia = Integer.parseInt(osaLista.get(i)[3].replaceAll("\\s", ""));
                    this.monkija = (new Monkija(energia, suunta, xKord, yKord));
                    
                    if (kentta[yKord][xKord] == null) { // Tyhjaan ruutuun monkijaa ei voi lisata vaan heitetaan poikkeus
                        throw new VirheellisetPeliTiedostot("Ruutu: " + xKord + "/" + yKord + " on tyhja ja siihen yritetaan lisata monkijaa");
                        
                    } else if (kentta[yKord][xKord] instanceof Kaytava) { // Monkija voidaan lisata vain kaytavalle
                        ((Kaytava) kentta[yKord][xKord]).lisaaObjekti(monkija);
                    } else {
                        throw new VirheellisetPeliTiedostot("Ruudussa: " + xKord + "/" + yKord + ("Ei ole kaytavaa vaan: ") + kentta[yKord][xKord].getClass());
                    }
                    
                } else if (osaLista.get(i)[0].replaceAll("\\s", "").equals("Esine")) {
                    int energia = Integer.parseInt(osaLista.get(i)[3].replaceAll("\\s", ""));
                    if (kentta[yKord][xKord] == null) {
                        throw new VirheellisetPeliTiedostot("Ruutu: " + xKord + "/" + yKord + " on tyhja ja siihen yritetaan lisata esinetta");
                    } else if (kentta[yKord][xKord] instanceof Kaytava) {
                        Kaytava kaytava = ((Kaytava) kentta[yKord][xKord]);
                        OmaLista sisalto = kaytava.getObjektit();
                        boolean lisatty = false;
                        // etsitaan Esineen kanssa samasta ruudusta monkijaa jos sellainen loytyy lisataan Esine suoraan monkijan inventaarioon
                        for (int k = 0; k < sisalto.koko(); k++) {
                            if (sisalto.alkio(k) instanceof Monkija) {
                                lisatty = true;
                                ((Monkija) (sisalto.alkio(k))).lisaaEsine(new Esine(xKord, yKord, energia));
                            }
                        }
                        if (!lisatty) { // jos monkijaa ei loydy lisataan esine kaytavalle
                            Esine e = new Esine(xKord, yKord, energia);
                            vapaatEsineet.lisaa(e);
                            kaytava.getObjektit().lisaa(e);
                        }
                    } else {
                        throw new VirheellisetPeliTiedostot("Ruudussa: " + xKord + "/" + yKord + ("Ei ole kaytavaa vaan: ") + kentta[yKord][xKord].getClass());
                    }
                }

            }
            sc.close(); // suljetaan lukija
        } catch (FileNotFoundException ex) {
            System.out.println("Tiedostoa ei voida avata" + ex.getMessage());
        }

    }
  /**
   * Tulostaa kentan komentoriville.
   * Kayttaa olioiden toString metodeja jotka ovat tietoisia olioiden valisesta hierarkiasta
   */
    public void tulostaKentta() {
        for (int i = 0; i < kentta.length; i++) {
            for (int e = 0; e < kentta[0].length; e++) {
                System.out.print((kentta[i][e]).toString());
            }
            System.out.println("");
        }
    }
    /**
     * Getteri roboottilistalle.
     * @return listan robooteista kentalla jota kaytetaan automaatissa.
     */
    public OmaLista getRobootit() {
        return this.robootit;
    }
     /**
      * getteri pelikentalle joka tulee taulukon muodossa.
      * @return palauttaa pelikentan.
      */
    public KentanRakenne[][] getKentta() {
        return this.kentta;
    }
   /**
    * Paivittaa pelitiedoston keskusmuistin kanssa samaan tilaan.
    * 
    */
    public void paivitaTiedosto() {
        try {
            FileOutputStream tulostusvirta = new FileOutputStream(Kentta.TIEDOSTO);
            PrintWriter writer = new PrintWriter(tulostusvirta, true);
            // avataan tiedosto tulostusvirralla joka listaan kirjoittajaan
            if (this.siemenLuku < 10 && this.siemenLuku > -1) {
                writer.print(this.siemenLuku + "   |" + this.kentta.length);
            } else {
                writer.print(this.siemenLuku + "  |" + this.kentta.length );
            }
            if (this.kentta.length < 10) {
                writer.print("   |" + this.kentta[0].length);
            } else {
                writer.print("  |" + this.kentta[0].length );

            }
            if (kentta[0].length < 10) {
                writer.println("   |");

            } else {
                writer.println("  |");
            }
            // kaydaan lapi kentan jokainen koordinaatti ja kutsutaan KentanRakenteesta tiedostoon metodia
            // tiedostoon metodi tulostaa myos esimerkiksi monkijan inventorion ja kaytavan objektit
            for (int i = 0; i < this.kentta.length; i++) {
                for (int e = 0; e < this.kentta[0].length; e++) {
                    if (kentta[i][e] != null) {
                        String[] rivit = ((KentanRakenne) kentta[i][e]).tiedostoon().split("ww");
                        // ww on rivinvaihtomerkki ohjelmassa
                        for (String s : rivit) {
                            writer.println(s);
                        }
                    }
                }
            }
            // suljetaan tulostusvirta ja kirjoitusolio
            writer.close();
            tulostusvirta.close();
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }
    /**
     * Metodi on vapaiden esineiden getteri.
     * @return vapaatEsineet listan jota kaytetaan pelin lopettamiseen.
     */
    public OmaLista vapaatEsineet() {
        return this.vapaatEsineet;
    }
    /**
     * Monkijan getteri.
     * @return monkija olion.
     */
    public Monkija getMonkija() {
        return this.monkija;
    }

}
