/**
  * abstrakti luokka olioille jotka tietavat sijantisa ja omaavat energian.
  * sarake_, rivi_ ja energia_ muuttujien tulee olla positiivisia.
  * <p>
  * Harjoitustyo, Olio-ohjelmoinnin perusteet, kevat 2016.
  * <p>
  * @author Jali Juhola (Juhola.Jali.V@Student.uta.fi),
  * Informaatiotieteiden yksikko, Tampereen yliopisto.
  *
  */
public abstract class KentanObjektit implements Comparable<KentanObjektit>, apulaiset.Paikallinen {
   /**
    * Olion energia.
    */
    private int energia_;
    /**
     * Olion rivi-indenksi.
     */
    private int rivi_;
    /** 
     * Olion sarake-indeksi.
     */
    private int sarake_;
     
    @Override
    public abstract int compareTo(KentanObjektit o);
    /**
     * Oliomuuttujan energia_ getteri.
     * @return olion energia.
     */
    public int energia() {
        return this.energia_;
    }
    /**
     * Luokan julkinen konstruktori.
     * @param energia olion energia.
     * @param sarake sarake-indeksi.
     * @param rivi rivi-indeksi.
     */
    public KentanObjektit(int energia, int sarake, int rivi) {
        this.energia_ = energia;
        this.rivi_ = rivi;
        this.sarake_ = sarake;
    }
    /**
     * energia_ oliomuuttujan setteri.
     * asettaa oliomuuttujan parametrin asettamaksi arvoksi.
     * @param energia olion energia.
     * @throws IllegalArgumentException energian tulee olla positiivinen
     */
    public void energia(int energia) {
        if(energia < 0) {
            throw new IllegalArgumentException("Energian tulee olla positiivinen");
        } else {
        this.energia_ = energia;
        }
    }

    /**
     * Paikan rivi-indeksin palauttava metodi.
     *
     * @return paikan rivi-indeksi.
     */
    public int rivi() {
        return this.rivi_;
    }

    /**
     * Paikan rivi-indeksin asettava metodi.
     *
     * @param ind rivi-indeksi.
     * @throws IllegalArgumentException jos indeksi on negatiivinen.
     */
    @Override
    public void rivi(int ind) {
        this.rivi_ = ind;
    }

    /**
     * Paikan sarakeindeksin palauttava metodi.
     *
     * @return paikan sarakeindeksi.
     */
    @Override
    public int sarake() {
        return this.sarake_;
    }

    /**
     * Paikan sarakeindeksin asettava metodi.
     *
     * @param ind sarakeindeksi.
     * @throws IllegalArgumentException jos indeksi on negatiivinen.
     */
    @Override
    public void sarake(int ind) {
        this.sarake_ = ind;
    }

    /**
     * Kertoo onko paikkaan sallittua asettaa sisaltoa (monkija, robotti tai
     * esine). Paikka on kaytettavissa, jos siina on kaytava.
     *
     * @return true, jos paikka on kaytettavissa.
     */
    @Override
    public abstract boolean sallittu();
     /**
     * Abstrakti metodi joka palauttaa luokan merkkijonoesityksen.
     * @return Palauttaa luokan merkkijonoesityksen.
     */
    public abstract String tiedostoon();
    

}
