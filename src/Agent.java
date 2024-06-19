import java.io.Serializable;

public class Agent implements Serializable {
    private String Nume;
    private String Prenume;
    private String IDNP;
    private String Adresa_domiciliu;
    private String Telefon;
    private Companie companie;
    private static double remunerae;

    //constructors
    Agent(){}
    Agent(String Nume, String Prenume, String IDNP, String Adresa_domiciliu, String Telefon, Companie companie){
        this.Nume = Nume;
        this.Prenume = Prenume;
        this.IDNP = IDNP;
        this.Adresa_domiciliu = Adresa_domiciliu;
        this.Telefon = Telefon;
        this.companie = companie;
    }

    //setters
    public void setNume(String Nume) {
        this.Nume = Nume;
    }
    public void setPrenume(String Prenume) {
        this.Prenume = Prenume;
    }
    public void setIDNP(String IDNP) {
        this.IDNP = IDNP;
    }
    public void setAdresa_domiciliu(String Adresa_domiciliu) {
        this.Adresa_domiciliu = Adresa_domiciliu;
    }
    public void setTelefon_Agent(String Telefon) {
        this.Telefon = Telefon;
    }
    public void setCompanie(Companie companie) {
        this.companie = companie;
    }
    public void setResources(double resources ) {
        this.remunerae += resources;
    }


    //getters
    public String getNume() {
        return Nume;
    }
    public String getPrenume() {
        return Prenume;
    }
    public String getIDNP() {
        return IDNP;
    }
    public String getAdresa_domiciliu() {
        return Adresa_domiciliu;
    }
    public String getTelefon() {
        return Telefon;
    }
    public Companie getCompanie() {
        return companie;
    }
    static public double getResources() {
        return remunerae;
    }


    class Serviciu{
        private String denumire_serviciu;
        private double preț_serviciu;
        //constuctors
        Serviciu(){}
        Serviciu(String denumire_serviciu, double preț_serviciu){
            this.denumire_serviciu = denumire_serviciu;
            this.preț_serviciu = preț_serviciu;
        }

        //setter
        public void setDenumire_serviciu(String denumire_serviciu) {
            this.denumire_serviciu = denumire_serviciu;
        }
        public void setPreț_serviciu(double preț_serviciu) {
            this.preț_serviciu = preț_serviciu;
        }

        //getter
        public String getDenumire_serviciu() {
            return denumire_serviciu;
        }
        public double getPreț_serviciu() {
            return preț_serviciu;
        }

        @Override
        public String toString() {
            return "\nServiciu: " + denumire_serviciu + "\nPreț serviciu: " + preț_serviciu;
        }
    }
    @Override
    public String toString() {
        return "\nAgent: " + Nume + " " + Prenume + "\nCod Personal: " + IDNP + "\nAdresa: " + Adresa_domiciliu + "\nTelefon: " + Telefon + "\nCompanie: " + companie;
    }

}
