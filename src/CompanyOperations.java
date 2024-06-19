import java.util.ArrayList;

public class CompanyOperations extends AbstractClass {
       public static final String FileName = "Companii.ser";
    public static void addCompany(String denumire, String adresa, String telefon) {
        ArrayList<Companie> list = dateFromFileToArrayList(FileName, "Companie");
        if (list == null) {
            list = new ArrayList<>();
        }
        Companie companie = new Companie(denumire, adresa, telefon);
        list.add(companie);
        saveArrayListToFile(FileName, list);
    }


    public static ArrayList<Companie> showCompany() {
        ArrayList<Companie> list = dateFromFileToArrayList(FileName, "Companie");
        return list;
    }

}
