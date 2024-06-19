import java.io.Serializable;

public class Apartament implements Serializable {
    private int numar;
    private String Nume_proprietar;
    private int nr_camere;
    private int nr_locatari;

    //constructors
    Apartament(){}
    Apartament(int numar, String Nume_proprietar, int nr_camere, int nr_locatari){
        this.numar = numar;
        this.Nume_proprietar = Nume_proprietar;
        this.nr_camere = nr_camere;
        this.nr_locatari = nr_locatari;
    }

    //setters
    public void setNumar(int numar) {
        this.numar = numar;
    }
    public void setNume_proprietar(String nume_proprietar) {
        Nume_proprietar = nume_proprietar;
    }
    public void setNr_camere(int nr_camere) {
        this.nr_camere = nr_camere;
    }
    public void setNr_locatari(int nr_locatari) {
        this.nr_locatari = nr_locatari;
    }

    //getters
    public int getNumar() {
        return numar;
    }
    public String getNume_proprietar() {
        return Nume_proprietar;
    }
    public int getNr_camere() {
        return nr_camere;
    }
    public int getNr_locatari() {
        return nr_locatari;
    }

    @Override
    public String toString() {
        return "Apartament: " + numar + ", Proprietar: " + Nume_proprietar + ", Camere: " + nr_camere + ", Locatari: " + nr_locatari;
    }
}