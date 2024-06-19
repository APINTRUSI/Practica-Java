import java.io.Serializable;


public class Companie implements Serializable{
    private String denumire;
    private String adresa;
    private String telefon;

    //constructors
    Companie(){}
    Companie(String denumire, String adresa, String telefon){
        this.denumire = denumire;
        this.adresa = adresa;
        this.telefon = telefon;
    }


    //setters
    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }
    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }
    public void setTelefon_Companie(String telefon) {
        this.telefon = telefon;
    }


    //getters
    public String getDenumire() {
        return denumire;
    }
    public String getAdresa() {
        return adresa;
    }
    public String getTelefon_Companie() {
        return telefon;
    }


    @Override
    public String toString() {
        return "\nCompanie: " + denumire + "\nAdresa: " + adresa + "\nTelefon: " + telefon;
    }
}