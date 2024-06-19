import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class GUI extends JFrame {
    private JButton addCompanyButton, viewCompaniesButton, addAgentButton, viewAgentsButton, addApartmentButton, viewApartmentsButton,
                    payForApartment, exitButton, showServiceCostsButton, showAgentRemuneraryButton, calculateAssociationMaintenanceButton,
                    viewApartmentPenaltiesButton, viewAssociationPenaltiesButton;

    private JFrame thisFrame;
    public GUI() {
        thisFrame = this;
        setTitle("Meniu Principal");
        setIconImage(new ImageIcon("logo.png").getImage());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(550, 700);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(13, 1, 5, 5));


        addCompanyButton = new JButton("1. Adaugă o companie");
        addCompanyButton.setFocusable(false);
        addCompanyButton.addActionListener(e -> {

            JFrame addFrame = new JFrame("Add Company");
            JTextField nameField = new JTextField(20);
            JTextField addressField = new JTextField(20);
            JTextField phoneField = new JTextField(20);
            JButton submitButton = new JButton("Submit");
            JButton backButton = new JButton("Back");

            addFrame.setSize(300, 200);
            addFrame.setLayout(new GridBagLayout());
            addFrame.setLocationRelativeTo(null);
            addFrame.setAlwaysOnTop(true);

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(5, 5, 5, 5);
            gbc.fill = GridBagConstraints.HORIZONTAL;

            gbc.gridx = 0;
            gbc.gridy = 0;
            addFrame.add(new JLabel("Name:"), gbc);
            gbc.gridx = 1;
            addFrame.add(nameField, gbc);

            gbc.gridx = 0;
            gbc.gridy = 1;
            addFrame.add(new JLabel("Address:"), gbc);
            gbc.gridx = 1;
            addFrame.add(addressField, gbc);

            gbc.gridx = 0;
            gbc.gridy = 2;
            addFrame.add(new JLabel("Phone:"), gbc);
            gbc.gridx = 1;
            addFrame.add(phoneField, gbc);

            gbc.gridx = 1;
            gbc.gridy = 3;
            addFrame.add(submitButton, gbc);

            gbc.gridy = 4;
            addFrame.add(backButton, gbc);



            submitButton.addActionListener(em -> {
                String name = nameField.getText().trim();
                String address = addressField.getText().trim();
                String phone = phoneField.getText().trim();

                try {
                    if (name.isEmpty() || address.isEmpty() || phone.isEmpty()) {
                        throw new Exception("Please fill in all fields.");
                    }

                    if (!AbstractClass.RealNumber(phone)) {
                        throw new AbstractClass.NegativeNumber();
                    }

                    CompanyOperations.addCompany(name, address, phone);

                    JOptionPane.showMessageDialog(addFrame,
                            "Company added:\nName: " + name + "\nAddress: " + address + "\nPhone: " + phone);
                    addFrame.dispose();
                    thisFrame.setVisible(true);
                } catch (AbstractClass.NegativeNumber ex) {
                    JOptionPane.showMessageDialog(addFrame,
                            "Invalid phone number!\nPhone number must be a 9-digit number starting with 06 or 07.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(addFrame,
                            "Error: " + ex.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            });


            backButton.addActionListener(em -> {
                addFrame.dispose();
                thisFrame.setVisible(true);
            });

            thisFrame.setVisible(false);
            addFrame.setVisible(true);
        });

        viewCompaniesButton = new JButton("2. Afișează companiile");
        viewCompaniesButton.setFocusable(false);
        viewCompaniesButton.addActionListener(e -> {
            thisFrame.setVisible(false);
            ArrayList<Companie> companies = CompanyOperations.showCompany();
            JFrame companiesFrame = new JFrame("View Companies");
            companiesFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            companiesFrame.setSize(600, 400);
            companiesFrame.setLocationRelativeTo(null);

            if (companies != null && !companies.isEmpty()) {
                int numRows = companies.size();
                int numCols = 4;

                String[][] data = new String[numRows][numCols];
                for (int i = 0; i < numRows; i++) {
                    Companie company = companies.get(i);
                    data[i][0] = String.valueOf(i + 1);
                    data[i][1] = company.getDenumire();
                    data[i][2] = company.getAdresa();
                    data[i][3] = company.getTelefon_Companie();
                }

                String[] columnNames = {"Număr", "Denumire", "Adresă", "Număr de telefon"};

                DefaultTableModel tableModel = new DefaultTableModel(data, columnNames);
                JTable companyTable = new JTable(tableModel);
                companyTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
                JScrollPane scrollPane = new JScrollPane(companyTable);

                companiesFrame.add(scrollPane, BorderLayout.CENTER);
            } else {
                JLabel noCompaniesLabel = new JLabel("No companies found.");
                noCompaniesLabel.setHorizontalAlignment(SwingConstants.CENTER);
                companiesFrame.add(noCompaniesLabel, BorderLayout.CENTER);
            }

            JButton backButton = new JButton("Back to Main Page");
            backButton.addActionListener(em -> {
                companiesFrame.dispose();
                this.setVisible(true);
            });

            companiesFrame.add(backButton, BorderLayout.SOUTH);

            companiesFrame.setVisible(true);
        });

        addAgentButton = new JButton("3. Adaugă un agent de servicii");
        addAgentButton.setFocusable(false);
        addAgentButton.addActionListener(e -> {
            JFrame addAgentFrame = new JFrame("Add Agent");
            JTextField lastNameField = new JTextField(20);
            JTextField firstNameField = new JTextField(20);
            JTextField idnpField = new JTextField(20);
            JTextField addressField = new JTextField(20);
            JTextField phoneField = new JTextField(20);

            // Dropdown list for selecting company
            ArrayList<Companie> companies = CompanyOperations.showCompany();
            JComboBox<String> companyComboBox = new JComboBox<>();
            for (Companie company : companies) {
                companyComboBox.addItem(company.getDenumire());
            }

            JButton submitButton = new JButton("Submit");
            JButton backButton = new JButton("Back");

            addAgentFrame.setSize(400, 300);
            addAgentFrame.setLayout(new GridBagLayout());
            addAgentFrame.setLocationRelativeTo(null);
            addAgentFrame.setAlwaysOnTop(true);

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(5, 5, 5, 5);
            gbc.fill = GridBagConstraints.HORIZONTAL;

            gbc.gridx = 0;
            gbc.gridy = 0;
            addAgentFrame.add(new JLabel("Last Name:"), gbc);
            gbc.gridx = 1;
            addAgentFrame.add(lastNameField, gbc);

            gbc.gridx = 0;
            gbc.gridy = 1;
            addAgentFrame.add(new JLabel("First Name:"), gbc);
            gbc.gridx = 1;
            addAgentFrame.add(firstNameField, gbc);

            gbc.gridx = 0;
            gbc.gridy = 2;
            addAgentFrame.add(new JLabel("IDNP:"), gbc);
            gbc.gridx = 1;
            addAgentFrame.add(idnpField, gbc);

            gbc.gridx = 0;
            gbc.gridy = 3;
            addAgentFrame.add(new JLabel("Address:"), gbc);
            gbc.gridx = 1;
            addAgentFrame.add(addressField, gbc);

            gbc.gridx = 0;
            gbc.gridy = 4;
            addAgentFrame.add(new JLabel("Phone:"), gbc);
            gbc.gridx = 1;
            addAgentFrame.add(phoneField, gbc);

            gbc.gridx = 0;
            gbc.gridy = 5;
            addAgentFrame.add(new JLabel("Company:"), gbc);
            gbc.gridx = 1;
            addAgentFrame.add(companyComboBox, gbc);

            gbc.gridx = 1;
            gbc.gridy = 6;
            addAgentFrame.add(submitButton, gbc);

            gbc.gridy = 7;
            addAgentFrame.add(backButton, gbc);

            submitButton.addActionListener(em -> {
                String lastName = lastNameField.getText().trim();
                String firstName = firstNameField.getText().trim();
                String idnp = idnpField.getText().trim();
                String address = addressField.getText().trim();
                String phone = phoneField.getText().trim();
                String companyName = (String) companyComboBox.getSelectedItem();

                try {
                    if (lastName.isEmpty() || firstName.isEmpty() || idnp.isEmpty() || address.isEmpty() || phone.isEmpty()) {
                        throw new Exception("Please fill in all fields.");
                    }

                    if (!AbstractClass.RealNumber(phone)) {
                        throw new AbstractClass.NegativeNumber();
                    }

                    if (!AbstractClass.RealIDNP(idnp)) {
                        throw new AbstractClass.WRONGIDNP();
                    }

                    // Find the selected company object
                    Companie selectedCompany = null;
                    for (Companie company : companies) {
                        if (company.getDenumire().equals(companyName)) {
                            selectedCompany = company;
                            break;
                        }
                    }

                    AgentsOperations.addAgent(lastName, firstName, idnp, address, phone, selectedCompany);

                    JOptionPane.showMessageDialog(addAgentFrame,
                            "Agent added:\nLast Name: " + lastName + "\nFirst Name: " + firstName +
                                    "\nIDNP: " + idnp + "\nAddress: " + address + "\nPhone: " + phone +
                                    "\nCompany: " + selectedCompany.getDenumire());
                    addAgentFrame.dispose();
                    thisFrame.setVisible(true);
                } catch (AbstractClass.NegativeNumber ex) {
                    JOptionPane.showMessageDialog(addAgentFrame,
                            "Invalid phone number!\nPhone number must be a 9-digit number starting with 06 or 07.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                } catch (AbstractClass.WRONGIDNP ex) {
                    JOptionPane.showMessageDialog(addAgentFrame,
                            "Invalid IDNP!\nIDNP must be a 13-digit number.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(addAgentFrame,
                            "Error: " + ex.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            });

            backButton.addActionListener(em -> {
                addAgentFrame.dispose();
                thisFrame.setVisible(true);
            });

            thisFrame.setVisible(false);
            addAgentFrame.setVisible(true);
        });


        viewAgentsButton = new JButton("4. Afișează agenții de servicii");
        viewAgentsButton.setFocusable(false);
        viewAgentsButton.addActionListener(e -> {
            thisFrame.setVisible(false);
            ArrayList<Agent> agents = AgentsOperations.showAgent();
            JFrame agentsFrame = new JFrame("View Agents");
            agentsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            agentsFrame.setSize(600, 400);
            agentsFrame.setLocationRelativeTo(null);

            if (agents != null && !agents.isEmpty()) {
                int numRows = agents.size();
                int numCols = 6;

                String[][] data = new String[numRows][numCols];
                for (int i = 0; i < numRows; i++) {
                    Agent agent = agents.get(i);
                    data[i][0] = String.valueOf(i + 1);
                    data[i][1] = agent.getNume();
                    data[i][2] = agent.getPrenume();
                    data[i][3] = agent.getIDNP();
                    data[i][4] = agent.getAdresa_domiciliu();
                    data[i][5] = agent.getTelefon();
                }

                String[] columnNames = {"Număr", "Nume", "Prenume", "IDNP", "Adresă", "Telefon"};

                DefaultTableModel tableModel = new DefaultTableModel(data, columnNames);
                JTable agentTable = new JTable(tableModel);
                agentTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
                JScrollPane scrollPane = new JScrollPane(agentTable);

                agentsFrame.add(scrollPane, BorderLayout.CENTER);
            } else {
                JLabel noAgentsLabel = new JLabel("No agents found.");
                noAgentsLabel.setHorizontalAlignment(SwingConstants.CENTER);
                agentsFrame.add(noAgentsLabel, BorderLayout.CENTER);
            }

            JButton backButton = new JButton("Back to Main Page");
            backButton.setFocusable(false);
            backButton.addActionListener(em -> {
                agentsFrame.dispose();
                thisFrame.setVisible(true);
            });

            agentsFrame.add(backButton, BorderLayout.SOUTH);

            agentsFrame.setVisible(true);
        });

        addApartmentButton = new JButton("5. Adaugă un apartament");
        addApartmentButton.setFocusable(false);
        addApartmentButton.addActionListener(e -> {
            JFrame addApartmentFrame = new JFrame("Add Apartment");
            JTextField numarField = new JTextField(20);
            JTextField proprietarField = new JTextField(20);
            JTextField nrCamereField = new JTextField(20);
            JTextField nrLocatariField = new JTextField(20);

            JButton submitButton = new JButton("Submit");
            JButton backButton = new JButton("Back");

            addApartmentFrame.setSize(400, 300);
            addApartmentFrame.setLayout(new GridBagLayout());
            addApartmentFrame.setLocationRelativeTo(null);
            addApartmentFrame.setAlwaysOnTop(true);

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(5, 5, 5, 5);
            gbc.fill = GridBagConstraints.HORIZONTAL;

            gbc.gridx = 0;
            gbc.gridy = 0;
            addApartmentFrame.add(new JLabel("Număr:"), gbc);
            gbc.gridx = 1;
            addApartmentFrame.add(numarField, gbc);

            gbc.gridx = 0;
            gbc.gridy = 1;
            addApartmentFrame.add(new JLabel("Proprietar:"), gbc);
            gbc.gridx = 1;
            addApartmentFrame.add(proprietarField, gbc);

            gbc.gridx = 0;
            gbc.gridy = 2;
            addApartmentFrame.add(new JLabel("Număr camere:"), gbc);
            gbc.gridx = 1;
            addApartmentFrame.add(nrCamereField, gbc);

            gbc.gridx = 0;
            gbc.gridy = 3;
            addApartmentFrame.add(new JLabel("Număr locatari:"), gbc);
            gbc.gridx = 1;
            addApartmentFrame.add(nrLocatariField, gbc);

            gbc.gridx = 1;
            gbc.gridy = 4;
            addApartmentFrame.add(submitButton, gbc);

            gbc.gridy = 5;
            addApartmentFrame.add(backButton, gbc);

            submitButton.addActionListener(em -> {
                try {
                    int numar = Integer.parseInt(numarField.getText().trim());
                    String proprietar = proprietarField.getText().trim();
                    int nrCamere = Integer.parseInt(nrCamereField.getText().trim());
                    int nrLocatari = Integer.parseInt(nrLocatariField.getText().trim());

                    // Validate apartment number, number of rooms, and number of residents
                    if (numar < 1) {
                        throw new Exception("Numărul apartamentului trebuie să fie mai mare sau egal cu 1.");
                    }
                    if (nrLocatari < 1) {
                        throw new Exception("Numărul de locatari trebuie să fie mai mare sau egal cu 1.");
                    }
                    if (nrCamere < 1) {
                        throw new Exception("Numărul de camere trebuie să fie mai mare sau egal cu 1.");
                    }

                    ApartamentOperations.addApartament(numar, proprietar, nrCamere, nrLocatari);

                    JOptionPane.showMessageDialog(addApartmentFrame,
                            "Apartament adăugat:\nNumăr: " + numar + "\nProprietar: " + proprietar +
                                    "\nNumăr camere: " + nrCamere + "\nNumăr locatari: " + nrLocatari);
                    addApartmentFrame.dispose();
                    thisFrame.setVisible(true);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(addApartmentFrame,
                            "Eroare: Vă rugăm să introduceți numere valide pentru Număr, Număr camere și Număr locatari.",
                            "Eroare", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(addApartmentFrame,
                            "Eroare: " + ex.getMessage(),
                            "Eroare", JOptionPane.ERROR_MESSAGE);
                }
            });

            backButton.addActionListener(em -> {
                addApartmentFrame.dispose();
                thisFrame.setVisible(true);
            });

            thisFrame.setVisible(false);
            addApartmentFrame.setVisible(true);
        });

        viewApartmentsButton = new JButton("6. Afișează lista apartamentelor");
        viewApartmentsButton.setFocusable(false);
        viewApartmentsButton.addActionListener(e -> {
            thisFrame.setVisible(false);
            ArrayList<Apartament> apartments = ApartamentOperations.showApartamente();
            JFrame apartmentsFrame = new JFrame("View Apartments");
            apartmentsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            apartmentsFrame.setSize(600, 400);
            apartmentsFrame.setLocationRelativeTo(null);

            if (apartments != null && !apartments.isEmpty()) {
                int numRows = apartments.size();
                int numCols = 4;

                String[][] data = new String[numRows][numCols];
                for (int i = 0; i < numRows; i++) {
                    Apartament apartment = apartments.get(i);
                    data[i][0] = String.valueOf(apartment.getNumar());
                    data[i][1] = apartment.getNume_proprietar();
                    data[i][2] = String.valueOf(apartment.getNr_camere());
                    data[i][3] = String.valueOf(apartment.getNr_locatari());
                }

                String[] columnNames = {"Număr", "Proprietar", "Număr camere", "Număr locatari"};

                DefaultTableModel tableModel = new DefaultTableModel(data, columnNames);
                JTable apartmentTable = new JTable(tableModel);
                apartmentTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
                JScrollPane scrollPane = new JScrollPane(apartmentTable);

                apartmentsFrame.add(scrollPane, BorderLayout.CENTER);
            } else {
                JLabel noApartmentsLabel = new JLabel("No apartments found.");
                noApartmentsLabel.setHorizontalAlignment(SwingConstants.CENTER);
                apartmentsFrame.add(noApartmentsLabel, BorderLayout.CENTER);
            }

            JButton backButton = new JButton("Back to Main Page");
            backButton.setFocusable(false);
            backButton.addActionListener(em -> {
                apartmentsFrame.dispose();
                thisFrame.setVisible(true);
            });

            apartmentsFrame.add(backButton, BorderLayout.SOUTH);

            apartmentsFrame.setVisible(true);
        });

        showServiceCostsButton = new JButton("7. Afișează costurile serviciilor");
        showServiceCostsButton.setFocusable(false);
        showServiceCostsButton.addActionListener(e -> {
            JFrame serviceCostsFrame = new JFrame("Costurile Serviciilor");
            serviceCostsFrame.setSize(400, 300);
            serviceCostsFrame.setLayout(new GridLayout(4, 1, 10, 10));
            serviceCostsFrame.setLocationRelativeTo(null);
            serviceCostsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            JLabel deservireBlocLabel = new JLabel("Deservire bloc: 15 lei per cameră + 10 lei per persoană");
            JLabel evacuareDeșeuriLabel = new JLabel("Evacuarea deșeurilor: 8 lei per cameră + 15 lei per persoană");
            JLabel canalizareLabel = new JLabel("Canalizare: 13 lei per cameră + 19 lei per persoană");

            serviceCostsFrame.add(deservireBlocLabel);
            serviceCostsFrame.add(evacuareDeșeuriLabel);
            serviceCostsFrame.add(canalizareLabel);

            JButton closeButton = new JButton("Back");
            closeButton.setFocusable(false);
            closeButton.addActionListener(ev -> {
                serviceCostsFrame.dispose();
                this.setVisible(true);
            });

            serviceCostsFrame.add(closeButton);

            this.setVisible(false);
            serviceCostsFrame.setVisible(true);
        });

        payForApartment = new JButton("8. Fă o achitare pentru un apartament");
        payForApartment.setFocusable(false);
        payForApartment.addActionListener(e -> {
            JFrame selectPaymentFrame = new JFrame("Selectează plata pentru apartament");

            String[] apartamentNumbers = ApartamentOperations.getApartamentNumbers();
            JComboBox<String> numarApartamentComboBox = new JComboBox<>(apartamentNumbers);

            String[] numeAgenti = AgentsOperations.getAgentsNames();
            JComboBox<String> numeAgentiComboBox = new JComboBox<>(numeAgenti);

            JComboBox<String> serviciiComboBox = new JComboBox<>(new String[]{"Deservire bloc", "Evacuarea deșeurilor", "Canalizare"});
            JComboBox<String> valutaComboBox = new JComboBox<>(new String[]{"EUR", "MDL"});
            JButton submitButton = new JButton("Submit");
            JButton backButton = new JButton("Back");

            selectPaymentFrame.setSize(400, 250);
            selectPaymentFrame.setLayout(new GridBagLayout());
            selectPaymentFrame.setLocationRelativeTo(null);
            selectPaymentFrame.setAlwaysOnTop(true);

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(5, 5, 5, 5);
            gbc.fill = GridBagConstraints.HORIZONTAL;

            gbc.gridx = 0;
            gbc.gridy = 0;
            selectPaymentFrame.add(new JLabel("Număr apartament:"), gbc);
            gbc.gridx = 1;
            selectPaymentFrame.add(numarApartamentComboBox, gbc);

            gbc.gridx = 0;
            gbc.gridy = 1;
            selectPaymentFrame.add(new JLabel("Agent:"), gbc);
            gbc.gridx = 1;
            selectPaymentFrame.add(numeAgentiComboBox, gbc);

            gbc.gridx = 0;
            gbc.gridy = 2;
            selectPaymentFrame.add(new JLabel("Serviciu:"), gbc);
            gbc.gridx = 1;
            selectPaymentFrame.add(serviciiComboBox, gbc);

            gbc.gridx = 0;
            gbc.gridy = 3;
            selectPaymentFrame.add(new JLabel("Valută:"), gbc);
            gbc.gridx = 1;
            selectPaymentFrame.add(valutaComboBox, gbc);

            gbc.gridx = 1;
            gbc.gridy = 4;
            selectPaymentFrame.add(submitButton, gbc);

            gbc.gridy = 5;
            selectPaymentFrame.add(backButton, gbc);

            submitButton.addActionListener(em -> {
                try {
                    String numarApartamentStr = (String) numarApartamentComboBox.getSelectedItem();
                    if (numarApartamentStr == null || numarApartamentStr.isEmpty()) {
                        JOptionPane.showMessageDialog(selectPaymentFrame,
                                "Eroare: Selectați un număr de apartament.",
                                "Eroare", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    int numarApartament = Integer.parseInt(numarApartamentStr);

                    String agent = (String) numeAgentiComboBox.getSelectedItem();
                    String serviciu = (String) serviciiComboBox.getSelectedItem();
                    String valuta = (String) valutaComboBox.getSelectedItem();

                    // Citim suma curentă din fișierul "SumaAsociație.txt"
                    double sumaDinFisier = 0.0;
                    try (BufferedReader bufferedReader = new BufferedReader(new FileReader("SumaAsociație.txt"))) {
                        String line = bufferedReader.readLine();
                        if (line == null) {
                            sumaDinFisier = 0;
                        } else {
                            sumaDinFisier = Double.parseDouble(line.trim());
                        }
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(selectPaymentFrame,
                                "Eroare la citirea sumei din fișierul SumaAsociație.txt: " + ex.getMessage(),
                                "Eroare", JOptionPane.ERROR_MESSAGE);
                        return;
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(selectPaymentFrame,
                                "Eroare la conversia sumei din fișierul SumaAsociație.txt: " + ex.getMessage(),
                                "Eroare", JOptionPane.ERROR_MESSAGE);
                        return;
                    }


                    // Calculăm suma de achitat în funcție de serviciul ales
                    double sumaDeAchitat = AbstractClass.calculeazaSumaDeAchitat(numarApartament, serviciu);

                    double sumaTotala = sumaDinFisier + sumaDeAchitat;

                    try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("SumaAsociație.txt"))) {
                        bufferedWriter.write(String.valueOf(sumaTotala));
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(selectPaymentFrame,
                                "Eroare la actualizarea sumei în fișierul SumaAsociație.txt: " + ex.getMessage(),
                                "Eroare", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Convertim suma de achitat în MDL dacă este necesar
                    if ("EUR".equals(valuta)) {
                        sumaDeAchitat /= 20; // Convertim EUR în MDL
                    }

                    // Calculăm penalizarea dacă plata se face după ziua 15 a lunii
                    LocalDate today = LocalDate.now();
                    int currentDayOfMonth = today.getDayOfMonth();
                    double penalizare = 0;

                    if (currentDayOfMonth > 15) {
                        long zileIntarziere = ChronoUnit.DAYS.between(LocalDate.of(today.getYear(), today.getMonth(), 15), today);
                        penalizare = Interface.calculeazaPenalizare(sumaDeAchitat, zileIntarziere);
                        sumaDeAchitat += penalizare;

                        // Salvăm penalitatea în fișierul "Penalitati.txt"
                        try (FileWriter writer = new FileWriter("Penalitati.txt", true);
                             BufferedWriter bw = new BufferedWriter(writer);
                             PrintWriter out = new PrintWriter(bw)) {

                            String nrApartamentStr = (String) numarApartamentComboBox.getSelectedItem();
                            int nrApartament = Integer.parseInt(nrApartamentStr);

                            out.println(nrApartament + " " + penalizare);

                        } catch (IOException ex) {
                            JOptionPane.showMessageDialog(selectPaymentFrame,
                                    "Eroare la salvarea penalității în fișierul Penalitati.txt: " + ex.getMessage(),
                                    "Eroare", JOptionPane.ERROR_MESSAGE);
                        }
                    }

                    selectPaymentFrame.dispose();


                    JFrame payFrame = new JFrame("Achiți pentru apartamentul " + numarApartament);
                    JTextField sumaAchitataField = new JTextField(10);
                    JButton achitaButton = new JButton("Achită");

                    payFrame.setSize(400, 250);
                    payFrame.setLayout(new GridBagLayout());
                    payFrame.setLocationRelativeTo(null);
                    payFrame.setAlwaysOnTop(true);

                    GridBagConstraints gbcPay = new GridBagConstraints();
                    gbcPay.insets = new Insets(5, 5, 5, 5);
                    gbcPay.fill = GridBagConstraints.HORIZONTAL;

                    gbcPay.gridx = 0;
                    gbcPay.gridy = 0;

                    // Afișăm mesajul corect în funcție de valuta aleasă
                    if ("EUR".equals(valuta)) {
                        payFrame.add(new JLabel("Trebuie să achitați " + sumaDeAchitat + " MDL"), gbcPay);
                    } else {
                        payFrame.add(new JLabel("Trebuie să achitați " + sumaDeAchitat + " EUR"), gbcPay);
                    }

                    gbcPay.gridx = 0;
                    gbcPay.gridy = 1;
                    payFrame.add(new JLabel("Introduceți suma achitată:"), gbcPay);
                    gbcPay.gridx = 1;
                    payFrame.add(sumaAchitataField, gbcPay);

                    gbcPay.gridy = 2;
                    payFrame.add(achitaButton, gbcPay);

                    achitaButton.addActionListener(ev -> {
                        try {
                            double sumaAchitata = Double.parseDouble(sumaAchitataField.getText().trim());

                            // Convertim suma achitată în MDL dacă este necesar
                            if ("EUR".equals(valuta)) {
                                sumaAchitata *= 20; // Convertim EUR în MDL
                            }

                            // Restul codului existent pentru calcul penalizare, etc.

                            // Salvăm achitarea în fișierul "Registrul_achitarii_cu_agenti.txt"
                            try (FileWriter writer = new FileWriter("Registrul_achitarii_cu_agenti.txt", true);
                                 BufferedWriter bw = new BufferedWriter(writer);
                                 PrintWriter out = new PrintWriter(bw)) {

                                String nrApartamentStr = (String) numarApartamentComboBox.getSelectedItem();
                                int nrApartament = Integer.parseInt(nrApartamentStr);
                                String agentname = (String) numeAgentiComboBox.getSelectedItem(); // get agent's name

                                out.println(agentname + " " + sumaAchitata);

                                JOptionPane.showMessageDialog(payFrame,
                                        "Ați achitat cu succes " + sumaAchitata + (valuta.equals("EUR") ? " EUR" : " MDL") + " pentru apartamentul " + nrApartament);

                                payFrame.dispose();
                                thisFrame.setVisible(true);
                            } catch (IOException ex) {
                                JOptionPane.showMessageDialog(payFrame,
                                        "Eroare la salvarea înregistrării în fișier: " + ex.getMessage(),
                                        "Eroare", JOptionPane.ERROR_MESSAGE);
                            }
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(payFrame,
                                    "Eroare: Vă rugăm să introduceți o sumă validă.",
                                    "Eroare", JOptionPane.ERROR_MESSAGE);
                        }
                    });

                    payFrame.setVisible(true);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(selectPaymentFrame,
                            "Eroare: Vă rugăm să introduceți un număr valid pentru numărul apartamentului.",
                            "Eroare", JOptionPane.ERROR_MESSAGE);
                }
            });

            backButton.addActionListener(em -> {
                selectPaymentFrame.dispose();
                thisFrame.setVisible(true);
            });

            thisFrame.setVisible(false);
            selectPaymentFrame.setVisible(true);
        });

        showAgentRemuneraryButton = new JButton("9. Afișează calculul remunerării agentului");
        showAgentRemuneraryButton.setFocusable(false);
        showAgentRemuneraryButton.addActionListener(e -> {
            JFrame selectAgentFrame = new JFrame("Selectează un agent");

            String[] agentNames = AgentsOperations.getAgentsNames();
            JComboBox<String> agentComboBox = new JComboBox<>(agentNames);

            JButton submitButton = new JButton("Submit");
            JButton backButton = new JButton("Back");

            submitButton.setFocusable(false);
            backButton.setFocusable(false);

            selectAgentFrame.setSize(400, 200);
            selectAgentFrame.setLayout(new GridBagLayout());
            selectAgentFrame.setLocationRelativeTo(null);
            selectAgentFrame.setAlwaysOnTop(true);

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(5, 5, 5, 5);
            gbc.fill = GridBagConstraints.HORIZONTAL;

            gbc.gridx = 0;
            gbc.gridy = 0;
            selectAgentFrame.add(new JLabel("Selectați un agent:"), gbc);
            gbc.gridx = 1;
            selectAgentFrame.add(agentComboBox, gbc);

            gbc.gridx = 1;
            gbc.gridy = 1;
            selectAgentFrame.add(submitButton, gbc);

            gbc.gridy = 2;
            selectAgentFrame.add(backButton, gbc);

            submitButton.addActionListener(ev -> {
                try {
                    String selectedAgent = (String) agentComboBox.getSelectedItem();
                    if (selectedAgent == null || selectedAgent.isEmpty()) {
                        JOptionPane.showMessageDialog(selectAgentFrame,
                                "Eroare: Selectați un agent.",
                                "Eroare", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    double remunerare = AgentsOperations.getAgentRemunerare(selectedAgent);

                    JFrame remunerareFrame = new JFrame("Remunerarea agentului " + selectedAgent);
                    remunerareFrame.setSize(400, 200);
                    remunerareFrame.setLayout(new GridBagLayout());
                    remunerareFrame.setLocationRelativeTo(null);
                    remunerareFrame.setAlwaysOnTop(true);

                    GridBagConstraints gbcRem = new GridBagConstraints();
                    gbcRem.insets = new Insets(5, 5, 5, 5);
                    gbcRem.fill = GridBagConstraints.HORIZONTAL;

                    gbcRem.gridx = 0;
                    gbcRem.gridy = 0;
                    remunerareFrame.add(new JLabel("Agentul " + selectedAgent + " primește o remunerare de " + remunerare + " MDL"), gbcRem);

                    JButton closeRemFrameButton = new JButton("Close");
                    gbcRem.gridy = 1;
                    remunerareFrame.add(closeRemFrameButton, gbcRem);

                    closeRemFrameButton.addActionListener(em -> remunerareFrame.dispose());

                    remunerareFrame.setVisible(true);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(selectAgentFrame,
                            "Eroare: Nu s-a putut obține remunerarea agentului.",
                            "Eroare", JOptionPane.ERROR_MESSAGE);
                }
            });

            backButton.addActionListener(em -> {
                selectAgentFrame.dispose();
                thisFrame.setVisible(true);
            });

            thisFrame.setVisible(false);
            selectAgentFrame.setVisible(true);
        });

        calculateAssociationMaintenanceButton = new JButton("10. Afișează calculul sumelor lunare totale pentru întreținerea întregii asociații");
        calculateAssociationMaintenanceButton.setFocusable(false);
        calculateAssociationMaintenanceButton.addActionListener(e -> {
            try (BufferedReader reader = new BufferedReader(new FileReader("SumaAsociație.txt"));){
                String line = reader.readLine();

                double totalMaintenanceCost = Double.parseDouble(line);

                JFrame maintenanceFrame = new JFrame("Calcul sume lunare pentru întreținere");
                maintenanceFrame.setSize(400, 200);
                maintenanceFrame.setLayout(new GridBagLayout());
                maintenanceFrame.setLocationRelativeTo(null);
                maintenanceFrame.setAlwaysOnTop(true);

                GridBagConstraints gbc = new GridBagConstraints();
                gbc.insets = new Insets(5, 5, 5, 5);
                gbc.fill = GridBagConstraints.HORIZONTAL;

                gbc.gridx = 0;
                gbc.gridy = 0;
                maintenanceFrame.add(new JLabel("Suma totală pentru întreținerea întregii asociații este: " + totalMaintenanceCost + " MDL"), gbc);

                JButton closeButton = new JButton("Close");
                closeButton.setFocusable(false);
                gbc.gridy = 1;
                maintenanceFrame.add(closeButton, gbc);

                closeButton.addActionListener(em -> maintenanceFrame.dispose());

                maintenanceFrame.setVisible(true);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(thisFrame,
                        "Eroare la calculul sumei pentru întreținerea asociației: " + ex.getMessage(),
                        "Eroare", JOptionPane.ERROR_MESSAGE);
            }
        });

        viewApartmentPenaltiesButton = new JButton("11. Afișează sumele lunare a penalizărilor pentru fiecare apartament");
        viewApartmentPenaltiesButton.setFocusable(false);
        viewApartmentPenaltiesButton.addActionListener(e -> {
            JFrame penaltiesFrame = new JFrame("Vizualizare Penalizări Apartament");
            penaltiesFrame.setSize(400, 300);
            penaltiesFrame.setLayout(new GridBagLayout());
            penaltiesFrame.setLocationRelativeTo(null);
            penaltiesFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            JLabel selectApartmentLabel = new JLabel("Selectați numărul apartamentului:");
            String[] apartmentNumbers = ApartamentOperations.getApartamentNumbers(); // Presupunând că există o metodă similară pentru a obține numerele de apartamente
            JComboBox<String> apartmentComboBox = new JComboBox<>(apartmentNumbers);

            JTextArea penaltiesTextArea = new JTextArea(10, 30);
            penaltiesTextArea.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(penaltiesTextArea);

            JButton closeButton = new JButton("Close");
            closeButton.setFocusable(false);

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(5, 5, 5, 5);
            gbc.fill = GridBagConstraints.HORIZONTAL;

            gbc.gridx = 0;
            gbc.gridy = 0;
            penaltiesFrame.add(selectApartmentLabel, gbc);

            gbc.gridx = 1;
            penaltiesFrame.add(apartmentComboBox, gbc);

            gbc.gridx = 0;
            gbc.gridy = 1;
            gbc.gridwidth = 2;
            penaltiesFrame.add(scrollPane, gbc);

            gbc.gridy = 2;
            penaltiesFrame.add(closeButton, gbc);

            closeButton.addActionListener(em -> penaltiesFrame.dispose());

            // Listener pentru ComboBox pentru a afișa penalizările pentru apartamentul selectat
            apartmentComboBox.addActionListener(apartmentEvent -> {
                String selectedApartment = (String) apartmentComboBox.getSelectedItem();
                if (selectedApartment != null && !selectedApartment.isEmpty()) {
                    int apartmentNumber = Integer.parseInt(selectedApartment.trim());
                    try (BufferedReader reader = new BufferedReader(new FileReader("Penalitati.txt"))) {
                        penaltiesTextArea.setText(""); // Curățăm textul din JTextArea înainte de a afișa noi penalizări

                        String line;
                        while ((line = reader.readLine()) != null) {
                            String[] parts = line.split("\\s+");
                            if (parts.length >= 2) {
                                int apartmentNumFromFile = Integer.parseInt(parts[0].trim());
                                if (apartmentNumFromFile == apartmentNumber) {
                                    // Adăugăm doar penalitatea, parts[1], în JTextArea
                                    penaltiesTextArea.append(parts[1] + "\n");
                                }
                            }
                        }
                    } catch (FileNotFoundException ex) {
                        JOptionPane.showMessageDialog(penaltiesFrame,
                                "Fișierul Penalizari.txt nu a fost găsit.",
                                "Eroare", JOptionPane.ERROR_MESSAGE);
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(penaltiesFrame,
                                "Eroare la citirea din fișierul Penalizari.txt: " + ex.getMessage(),
                                "Eroare", JOptionPane.ERROR_MESSAGE);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(penaltiesFrame,
                                "Eroare la conversia numărului de apartament din fișierul Penalizari.txt: " + ex.getMessage(),
                                "Eroare", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });

            penaltiesFrame.setVisible(true);
        });

        viewAssociationPenaltiesButton = new JButton("12. Afișează sumele lunare a penalizărilor pentru asociație");
        viewAssociationPenaltiesButton.setFocusable(false);
        viewAssociationPenaltiesButton.addActionListener(e -> {
            double totalPenalties = 0.0;

            try (BufferedReader reader = new BufferedReader(new FileReader("Penalitati.txt"))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split("\\s+");
                    if (parts.length >= 2) {
                        try {
                            double penaltyAmount = Double.parseDouble(parts[1].trim());
                            totalPenalties += penaltyAmount;
                        } catch (NumberFormatException ex) {
                            // Handle parsing errors for individual penalty amounts
                            JOptionPane.showMessageDialog(null,
                                    "Eroare la conversia sumei din fișierul Penalitati.txt: " + ex.getMessage(),
                                    "Eroare", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            } catch (FileNotFoundException ex) {
                JOptionPane.showMessageDialog(null,
                        "Fișierul Penalitati.txt nu a fost găsit.",
                        "Eroare", JOptionPane.ERROR_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null,
                        "Eroare la citirea din fișierul Penalitati.txt: " + ex.getMessage(),
                        "Eroare", JOptionPane.ERROR_MESSAGE);
            }

            // Show the total penalties after processing
            JOptionPane.showMessageDialog(null,
                    "Suma totală a penalităților pentru asociație este: " + totalPenalties,
                    "Sumă Totală Penalități", JOptionPane.INFORMATION_MESSAGE);

        });



        exitButton = new JButton("Cancel");
        exitButton.setFocusable(false);
        exitButton.addActionListener(e -> System.exit(0));

        add(addCompanyButton);
        add(viewCompaniesButton);
        add(addAgentButton);
        add(viewAgentsButton);
        add(addApartmentButton);
        add(viewApartmentsButton);
        add(showServiceCostsButton);
        add(payForApartment);
        add(showAgentRemuneraryButton);
        add(calculateAssociationMaintenanceButton);
        add(viewApartmentPenaltiesButton);
        add(viewAssociationPenaltiesButton);
        add(exitButton);

        setVisible(true);
    }

    public static void main(String[] args) {
        new GUI();
    }
}