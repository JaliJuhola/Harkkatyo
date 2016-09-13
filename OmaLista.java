
/**
 * LinkitettyLista luokasta peritty listaluokka.
 * Tarkoitettu Comparablen toteuttaneiden olioiden kasittelyyn
 * <p>
 * Harjoitustyo, Olio-ohjelmoinnin perusteet, kevat 2016.
 * <p>
 * @author Jali Juhola (Juhola.Jali.V@student.uta.fi), Informaatiotieteiden
 * yksikko, Tampereen yliopisto.
 */
public class OmaLista extends fi.uta.csjola.oope.lista.LinkitettyLista {

    /**
     * Metodi joka asettaa compareTo metodin perusteella olion listalle
     *
     * @param alkio lisattava object tyyppinen olio, jonka tulisi toteuttaa
     * Comparable rajapinta.
     *
     */
    public void lisaa(Object alkio) {
        // Tarkistetaan etta voiko alkiota vertailla eli onko se comparable
        if (alkio instanceof Comparable) {
 
            if (super.koko == 0) {
                // Jos listan koko on 0 lisataan alkio suoraan listan alkuun
                super.lisaaAlkuun(alkio);

            } else {
                for (int i = 0; i < super.koko(); i++) {
                    // Kaydaan lista lapi
                    if (i == this.koko() - 1) {
                        // Jos i saavuttaa listan koon alkio on pakko vain lisata listaan viimesesta arvosta riippuen joko viimeiseksi tai toisiksi viimeiseksi
                        
                        if ((((Comparable) alkio).compareTo((Comparable) alkio(i)) == 1 || ((Comparable) alkio).compareTo((Comparable) alkio(i)) == 0)) {
                            super.lisaaLoppuun(alkio);
                            i = super.koko();
                            break;
                        } else {
                            super.lisaa(i, alkio);
                            i = super.koko();
                            break;
                        }
                    } else if (((Comparable) alkio).compareTo((Comparable) alkio(i)) == -1 || ((Comparable) alkio).compareTo((Comparable) alkio(i)) == 0) {
                        // muiden kuin viimeisen alkion kohdalla testataan etta onko seuraava alkio suurempi kuin lisattava alkio
                        // jos on loysimme valin koska aiempi alkio on jo tarkistettu ja se on pienempi kuin lisattava objekti
                        super.lisaa(i, alkio);
                        i = super.koko();
                        break;
                    }
                }
            }

        }
    }

    /**
     * Poisto metodi joka poistaa parametrina olevan objektin.
     *
     * @param o objekti joka listalta halutaan poistaa.
     * @return Palauttaa poistetun objektin
     */
    public Object poista(Object o) {
        for (int i = 0; i < super.koko(); i++) {
            // kaydaan koko lista lapi etsien jos parametrina oleva arvo loytyy listasta
            if (o.equals(alkio(i))) {
                // arvo on loytynyt arvo poistetaan
                return super.poista(i);
            }
        }
        return null;
    }

}
