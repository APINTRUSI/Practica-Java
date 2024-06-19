import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class AgentsOperations extends AbstractClass {
    public static final String FileName = "Agenti.ser";
    public static void addAgent(String Nume, String Prenume, String IDNP, String Adresa_domiciliu, String Telefon, Companie companie) {
        ArrayList<Agent> list = dateFromFileToArrayList(FileName, "Agent");
        if (list == null) {
            list = new ArrayList<>();
        }
        Agent agent = new Agent(Nume, Prenume, IDNP, Adresa_domiciliu, Telefon, companie);
        list.add(agent);
        saveArrayListToFile(FileName, list);
    }

    public static ArrayList<Agent> showAgent() {
        ArrayList<Agent> list = dateFromFileToArrayList(FileName, "Agent");
        return list;
    }

    public static ArrayList<Agent> showAgenti() {
        return dateFromFileToArrayList(FileName, "Agent");
    }

    public static String[] getAgentsNames() {
        ArrayList<Agent> agenti = showAgenti();
        if (agenti == null || agenti.isEmpty()) {
            return new String[]{};
        }

        String[] nume = new String[agenti.size()];
        for (int i = 0; i < agenti.size(); i++) {
            nume[i] = String.valueOf(agenti.get(i).getNume());
        }
        return nume;
    }

    public static void addResources(String agentName, double sumaAchitata) {
        ArrayList<Agent> agentsList = dateFromFileToArrayList(FileName, "Agent");
        if (agentsList != null) {
            for (Agent agent : agentsList) {
                if (agent.getNume().equals(agentName)) { // Presupunând că folosim numele agentului pentru identificare
                    // Actualizăm resursele agentului
                    double resurseCurente = agent.getResources();
                    double resurseActualizate = resurseCurente + sumaAchitata;
                    agent.setResources(resurseActualizate);
                    break; // Am găsit agentul, putem ieși din buclă
                }
            }
            // Salvăm lista actualizată în fișier
            saveArrayListToFile(FileName, agentsList);
        }
    }



    public static double getAgentRemunerare(String agentName) {
        double totalRemunerare = 0;
        String filePath = "Registrul_achitarii_cu_agenti.txt";

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" ");
                if (parts.length == 2) {
                    String name = parts[0].trim();
                    double remuneration = Double.parseDouble(parts[1].trim());

                    if (agentName.equals(name)) {
                        totalRemunerare += remuneration;
                    }
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }

        return totalRemunerare;
    }

}