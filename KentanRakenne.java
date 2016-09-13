/**
  * abstrakti luokka paikkansa tunteville olioille jotka kuvastavat kentan ruutuja.
  * sarake_ ja rivi_ muuttujien tulee olla positiivisia.
  * <p>
  * Harjoitustyo, Olio-ohjelmoinnin perusteet, kevat 2016.
  * <p>
  * @author Jali Juhola (Juhola.Jali.V@Student.uta.fi),
  * Informaatiotieteiden yksikko, Tampereen yliopisto.
  *
  */
public abstract class KentanRakenne implements apulaiset.Paikallinen {
    /**
     * Kuvastaa saraketta kentalla. 
     * muuttujan tulee olla positiivinen.
     */
    private int sarake_;
    /**
     * Kuvastaa rivia kentalla.
     * muuttujan tulee olla positiivinen.
     */
    private int rivi_;
     /**
      * Kentanrakenne luokan julkinen rakentaja.
      * @param sarake sarake-indeksi.
      * @param rivi rivi-indeksi.
      */
    public KentanRakenne(int sarake, int rivi) {

        sarake_ = sarake;
        rivi_ = rivi;
    }
    
   /** Paikan sarakeindeksin palauttava metodi.
     *
     * @return paikan sarakeindeksi.
     */
    @Override
    public int rivi() {
        return rivi_;
    }

    /**
     * Paikan rivi-indeksin asettava metodi.
     *
     * @param ind rivi-indeksi.
     * @throws IllegalArgumentException jos indeksi on negatiivinen.
     */
    public void rivi(int ind) {
        if(ind >= 0) {
        this.rivi_ = ind;
        } else {
           throw new IllegalArgumentException("Indeksi ei voi olla negatiivinen ( " + ind + ")");
        }
    }

    /**
     * Paikan sarakeindeksin palauttava metodi.
     *
     * @return paikan sarakeindeksi.
     */
    public int sarake() {
       
        return this.sarake_;
    }

    /**
     * Paikan sarakeindeksin asettava metodi.
     *
     * @param ind sarakeindeksi.
     * @throws IllegalArgumentException jos indeksi on negatiivinen.
     */
    public void sarake(int ind) {
        if (ind >= 0) {
            this.sarake_ = ind;
        } else {
            throw new IllegalArgumentException("Indeksi ei voi olla negatiivinen ( " + ind + ")");
        }
    }

   /** Kertoo onko paikkaan sallittua asettaa sisaltaa (monkija, robotti tai esine).
     * Paikka on k�ytettavissa, jos siina on kaytava.
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
