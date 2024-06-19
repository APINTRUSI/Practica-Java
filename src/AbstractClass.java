import java.io.*;
import java.util.ArrayList;

public abstract class AbstractClass {

    public static class WRONGIDNP extends Exception {}
    public static class NegativeNumber extends Exception {}
    public static boolean RealIDNP(String x) {
        if (x.length() != 13) return false;
        for (int i = 0; i < x.length(); i++) {
            if (!Character.isDigit(x.charAt(i))) return false;
        }
        return true;
    }

    public static boolean RealNumber(String x) {
        if (x.length() != 9 || !(x.startsWith("06") || x.startsWith("07"))) return false;
        for (int i = 0; i < x.length(); i++) {
            if (!Character.isDigit(x.charAt(i))) return false;
        }
        return true;
    }

    public static <T> void saveArrayListToFile(String fileName, ArrayList<T> list) {
        try (FileOutputStream fos = new FileOutputStream(fileName);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {

            oos.writeObject(list);

        } catch (FileNotFoundException e) {
            System.err.println("\nFile not found: " + fileName);
            throw new RuntimeException(e);
        } catch (IOException e) {
            System.err.println("\nError while writing data: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public static <T> ArrayList<T> dateFromFileToArrayList(String fileName, String type) {
        ArrayList<T> list = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            switch (type.toLowerCase()) {
                case "companie":
                    list = (ArrayList<T>) ois.readObject();
                    break;
                case "agent":
                    list = (ArrayList<T>) ois.readObject();
                    break;
                case "apartament":
                    list = (ArrayList<T>) ois.readObject();
                    break;
                default:
                    throw new IllegalArgumentException("Unknown type: " + type);
            }
        } catch (FileNotFoundException e) {
            System.err.println("\nFile not found: " + fileName);
        } catch (EOFException e) {
            System.out.println("\nEnd of file reached: " + fileName);
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("\nError while reading data: " + e.getMessage());
        }
        return list;
    }

    public static double calculeazaSumaDeAchitat(int numarApartament, String serviciu) {
        double suma = 0;

        try {
            FileInputStream fis = new FileInputStream("Apartamente.ser");
            ObjectInputStream ois = new ObjectInputStream(fis);
            ArrayList<Apartament> apartamente = (ArrayList<Apartament>) ois.readObject();
            ois.close();

            Apartament apartamentCautat = null;
            for (Apartament apartament : apartamente) {
                if (apartament.getNumar() == numarApartament) {
                    apartamentCautat = apartament;
                    break;
                }
            }

            if (apartamentCautat == null) {
                throw new Exception("Apartamentul cu numărul " + numarApartament + " nu există în sistem.");
            }

            // Calculăm suma de achitat în funcție de serviciul ales
            double suprafata = apartamentCautat.getNr_camere();
            double numarPersoane = apartamentCautat.getNr_locatari();

            switch (serviciu) {
                case "Deservire bloc":
                    suma = 15 * suprafata + 10 * numarPersoane;
                    break;
                case "Evacuarea deșeurilor":
                    suma = 8 * suprafata + 15 * numarPersoane;
                    break;
                case "Canalizare":
                    suma = 13 * suprafata + 19 * numarPersoane;
                    break;
                default:
                    throw new Exception("Serviciu necunoscut: " + serviciu);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return suma;
    }


}