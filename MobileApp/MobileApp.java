
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// Interface
interface RadioTuner {
    void setStation(String station);
    void setFrequency(double frequency);
}

// MobileDevice superclass
class MobileDevice implements RadioTuner {
    private String manufacturer;
    private String model;
    private String station;
    private double frequency;

    public MobileDevice(String manufacturer,
                        String model) {
        this.manufacturer = manufacturer;
        this.model = model;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public void setFrequency(double frequency) {
        this.frequency = frequency;
    }

    public String getStation() {
        return station;
    }

    public double getFrequency() {
        return frequency;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public String getModel() {
        return model;
    }

    public String getDetails() {
        return "Manufacturer: " + manufacturer +
               "\nModel: " + model;
    }

    public void printDetails() {
        System.out.println(getDetails());
    }
}

// SmartPhone subclass
class SmartPhone extends MobileDevice {
    private String type;

    public SmartPhone(String manufacturer,
                      String model, String type) {
        super(manufacturer, model);
        this.type = type;
    }

    public String getDetails() {
        return "SmartPhone Detail:\n" +
               super.getDetails() +
               "\nType: " + type;
    }
}

// BasicPhone subclass
class BasicPhone extends MobileDevice {

    public BasicPhone(String manufacturer,
                      String model) {
        super(manufacturer, model);
    }

    public String getDetails() {
        return "Basic Phone Detail:\n" +
               super.getDetails();
    }
}

// Main GUI
public class MobileApp extends JFrame {

    private JComboBox cmbType;
    private JTextField txtManufacturer;
    private JTextField txtModel;
    private JTextField txtStation;
    private JTextField txtFrequency;
    private JTextArea txtResult;

    public MobileApp() {
        setTitle("Mobile Radio Tuner System");
        setSize(600, 500);
        setDefaultCloseOperation(
            JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(
            new BorderLayout(10, 10));

        mainPanel.setBorder(
            BorderFactory.createEmptyBorder(
                15, 15, 15, 15));

        // Title
        JLabel lblTitle = new JLabel(
            "Mobile Radio Tuner",
            JLabel.CENTER);

        lblTitle.setFont(
            new Font("Arial", Font.BOLD, 22));

        mainPanel.add(lblTitle,
            BorderLayout.NORTH);

        // Input panel
        JPanel inputPanel = new JPanel(
            new GridLayout(5, 2, 10, 10));

        inputPanel.setBorder(
            BorderFactory.createTitledBorder(
                "Mobile Device Information"));

        cmbType = new JComboBox(
            new String[] {
                "SmartPhone",
                "BasicPhone"
            });

        txtManufacturer = new JTextField();
        txtModel = new JTextField();
        txtStation = new JTextField();
        txtFrequency = new JTextField();

        inputPanel.add(new JLabel("Phone Type:"));
        inputPanel.add(cmbType);

        inputPanel.add(new JLabel("Manufacturer:"));
        inputPanel.add(txtManufacturer);

        inputPanel.add(new JLabel("Model:"));
        inputPanel.add(txtModel);

        inputPanel.add(new JLabel("Radio Station:"));
        inputPanel.add(txtStation);

        inputPanel.add(new JLabel("Frequency (MHz):"));
        inputPanel.add(txtFrequency);

        // Buttons
        JButton btnTune = new JButton("Tune Radio");
        JButton btnClear = new JButton("Clear");

        JPanel buttonPanel = new JPanel();

        buttonPanel.add(btnTune);
        buttonPanel.add(btnClear);

        JPanel topPanel = new JPanel(
            new BorderLayout(10, 10));

        topPanel.add(inputPanel,
            BorderLayout.CENTER);

        topPanel.add(buttonPanel,
            BorderLayout.SOUTH);

        mainPanel.add(topPanel,
            BorderLayout.CENTER);

        // Result area
        txtResult = new JTextArea();
        txtResult.setEditable(false);
        txtResult.setFont(
            new Font("Monospaced",
                Font.PLAIN, 14));

        JScrollPane scrollPane =
            new JScrollPane(txtResult);

        scrollPane.setBorder(
            BorderFactory.createTitledBorder(
                "Mobile Details"));

        mainPanel.add(scrollPane,
            BorderLayout.SOUTH);

        add(mainPanel);

        // Tune Radio button
        btnTune.addActionListener(
            new ActionListener() {
                public void actionPerformed(
                    ActionEvent e) {
                    tuneRadio();
                }
            });

        // Clear button
        btnClear.addActionListener(
            new ActionListener() {
                public void actionPerformed(
                    ActionEvent e) {
                    clearForm();
                }
            });
    }

    private void tuneRadio() {
        String type =
            cmbType.getSelectedItem().toString();

        String manufacturer =
            txtManufacturer.getText().trim();

        String model =
            txtModel.getText().trim();

        String station =
            txtStation.getText().trim();

        String frequencyText =
            txtFrequency.getText().trim();

        if (manufacturer.equals("") ||
            model.equals("") ||
            station.equals("") ||
            frequencyText.equals("")) {

            JOptionPane.showMessageDialog(
                this,
                "Please complete all fields.");
            return;
        }

        try {
            double frequency =
                Double.parseDouble(frequencyText);

            if (frequency <= 0) {
                JOptionPane.showMessageDialog(
                    this,
                    "Frequency must be positive.");
                return;
            }

            MobileDevice mobile;

            if (type.equals("SmartPhone")) {
                mobile = new SmartPhone(
                    manufacturer, model, "Smartphone");
            }
            else {
                mobile = new BasicPhone(
                    manufacturer, model);
            }

            mobile.setStation(station);
            mobile.setFrequency(frequency);

            String result =
                mobile.getDetails() +
                "\nStation: " + mobile.getStation() +
                "\nFrequency: " +
                mobile.getFrequency() + " MHz" +
                "\n\nRadio tuned successfully!" +
                "\n------------------------------\n";

            txtResult.append(result);

        }
        catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(
                this,
                "Please enter a valid frequency.");
        }
    }

    private void clearForm() {
        txtManufacturer.setText("");
        txtModel.setText("");
        txtStation.setText("");
        txtFrequency.setText("");
        txtResult.setText("");
        cmbType.setSelectedIndex(0);
    }

    public static void main(String[] args) {
        MobileApp app = new MobileApp();
        app.setVisible(true);
    }
}
