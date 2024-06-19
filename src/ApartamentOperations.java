import java.util.ArrayList;

public class ApartamentOperations extends AbstractClass {
    public static final String FileName = "Apartamente.ser";

    public static void addApartament(int numar, String nume_proprietar, int nr_camere, int nr_locatari) {
        ArrayList<Apartament> list = dateFromFileToArrayList(FileName, "Apartament");
        if (list == null) {
            list = new ArrayList<>();
        }
        Apartament apartament = new Apartament(numar, nume_proprietar, nr_camere, nr_locatari);
        list.add(apartament);
        saveArrayListToFile(FileName, list);
    }

    public static ArrayList<Apartament> showApartamente() {
        return dateFromFileToArrayList(FileName, "Apartament");
    }

    public static String[] getApartamentNumbers() {
        ArrayList<Apartament> apartamente = showApartamente();
        if (apartamente == null || apartamente.isEmpty()) {
            return new String[]{};
        }

        String[] numbers = new String[apartamente.size()];
        for (int i = 0; i < apartamente.size(); i++) {
            numbers[i] = String.valueOf(apartamente.get(i).getNumar());
        }
        return numbers;
    }


}
