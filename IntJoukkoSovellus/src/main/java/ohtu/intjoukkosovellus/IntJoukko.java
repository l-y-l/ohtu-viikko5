
package ohtu.intjoukkosovellus;

import java.util.Arrays;

public class IntJoukko {

    public final static int KAPASITEETTI = 5, // aloitustalukon koko
                            OLETUSKASVATUS = 5;  // luotava uusi taulukko on 
    // näin paljon isompi kuin vanha
    private int kasvatusKoko;     // Uusi taulukko on tämän verran vanhaa suurempi.
    private int[] taulukko;      // Joukon luvut säilytetään taulukon alkupäässä. 
    private int alkioidenLkm;    // Tyhjässä joukossa alkioiden_määrä on nolla. 

    public IntJoukko() {
        this(KAPASITEETTI, OLETUSKASVATUS);
    }

    public IntJoukko(int kapasiteetti) {
        this(kapasiteetti, OLETUSKASVATUS);
    }

    public IntJoukko(int kapasiteetti, int kasvatusKoko) {
        tarkistaKonstruktorinParametrit(kapasiteetti, kasvatusKoko);

        taulukko = new int[kapasiteetti];
        alkioidenLkm = 0;
        this.kasvatusKoko = kasvatusKoko;
    }

    private void tarkistaKonstruktorinParametrit(int kapasiteetti, int kasvatusKoko) {
        if (kapasiteetti < 0) {
            throw new IndexOutOfBoundsException("Kapasitteetti ei saa olla negatiivinen");
        }
        if (kasvatusKoko < 0) {
            throw new IndexOutOfBoundsException("Kasvatuskoko ei saa olla negatiivinen");
        }
    }

    public boolean lisaa(int luku) {
        if (kuuluu(luku)) {
            return false;
        }

        lisaaLukuPeraan(luku);

        return true;
    }

    private void lisaaLukuPeraan(int luku) {
        if (alkioidenLkm == taulukko.length) {
            taulukko = suurennaTaulukkoa(taulukko);
        }

        taulukko[alkioidenLkm] = luku;
        alkioidenLkm++;
    }

    private int[] suurennaTaulukkoa(int[] taulukko) {
        int[] uusiTaulukko = new int[taulukko.length + kasvatusKoko];

        for (int i = 0; i < taulukko.length; i++) {
            uusiTaulukko[i] = taulukko[i];
        }

        return uusiTaulukko;
    }

    public boolean kuuluu(int luku) {
        for (int i = 0; i < alkioidenLkm; i++) {
            if (luku == taulukko[i]) {
                return true;
            }
        }

        return false;
    }

    public boolean poista(int luku) {
        if (!kuuluu(luku)) {
            return false;
        }

        poistaAlkioKeskelta(etsiLuvunIndeksi(luku));

        return true;
    }

    private int etsiLuvunIndeksi(int luku) {
        for (int i = 0; i < alkioidenLkm; i++) {
            if (luku == taulukko[i]) {
                return i;
            }
        }

        return -1;
    }

    private void poistaAlkioKeskelta(int indeksi) {
        for (int i = indeksi; i < alkioidenLkm - 1; i++) {
            taulukko[i] = taulukko[i+1];
        }

        alkioidenLkm--;
    }

    public int mahtavuus() {
        return alkioidenLkm;
    }

    @Override
    public String toString() {
        String tuloste = Arrays.toString(toIntArray())
            .replaceAll("[\\[\\]]", "");

        return "{" + tuloste + "}";
    }

    public int[] toIntArray() {
        return Arrays.copyOfRange(taulukko, 0, alkioidenLkm);
    }

    public static IntJoukko yhdiste(IntJoukko syoteA, IntJoukko syoteB) {
        IntJoukko tuloste = new IntJoukko();

        tuloste.lisaaToisenJoukonAlkiot(syoteA);
        tuloste.lisaaToisenJoukonAlkiot(syoteB);

        return tuloste;
    }

    private void lisaaToisenJoukonAlkiot(IntJoukko lisattavaJoukko) {
        int[] lisattavanTaulukko = lisattavaJoukko.toIntArray();

        for (int i = 0; i < lisattavanTaulukko.length; i++) {
            lisaa(lisattavanTaulukko[i]);
        }
    }

    public static IntJoukko leikkaus(IntJoukko syoteA, IntJoukko syoteB) {
        IntJoukko tuloste = new IntJoukko();

        tuloste.lisaaKahdenJoukonLeikkaus(syoteA, syoteB);

        return tuloste;
    }

    private void lisaaKahdenJoukonLeikkaus(IntJoukko syoteA, IntJoukko syoteB) {
        int[] aTaulu = syoteA.toIntArray();

        for (int i = 0; i < aTaulu.length; i++) {
            if (syoteB.kuuluu(aTaulu[i])) {
                lisaa(aTaulu[i]);
            }
        }
    }

    public static IntJoukko erotus(IntJoukko syoteA, IntJoukko syoteB) {
        IntJoukko tuloste = new IntJoukko();

        tuloste.lisaaToisenJoukonAlkiot(syoteA);
        tuloste.poistaToisenJoukonAlkiot(syoteB);

        return tuloste;
    }

    private void poistaToisenJoukonAlkiot(IntJoukko poistettavaJoukko) {
        int[] lisattavanTaulukko = poistettavaJoukko.toIntArray();

        for (int i = 0; i < lisattavanTaulukko.length; i++) {
            poista(lisattavanTaulukko[i]);
        }
    }
}
