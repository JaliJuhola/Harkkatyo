/**
 * Poikkeus joka heitetaan kuin karttaa ei ole ladattua ja sita yritetaan kasitella.
 * <p>
 * Harjoitustyö, Olio-ohjelmoinnin perusteet, kevät 2016.
 * <p>
 * @author Jali Juhola (Juhola.Jali.V@student.uta.fi), Informaatiotieteiden
 * yksikkö, Tampereen yliopisto.
 */

public class KarttaaEiLadattu extends Exception {
    /**
     * Poikkeuksen julkinen konstruktori.
     * @param msg Viesti joka valitetaan ylaluokalla.
     */
    public KarttaaEiLadattu(String msg) {
        super(msg);
    }
}
