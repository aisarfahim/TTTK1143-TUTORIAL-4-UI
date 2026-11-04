
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;

// Interface
interface Measurable {
    double getMeasure();
    String getName();
}

// Bank Account class
class BankAccount implements Measurable {
    private double balance;

    public BankAccount(double balance) {
        this.balance = balance;
    }

    public double getMeasure() {
        return balance;
    }

    public String getName() {
        return "Bank Account";
    }
}

// Coin class
class Coin implements Measurable {
    private double value;
    private String name;

    public Coin(double value, String name) {
        this.value = value;
        this.name = name;
    }

    public double getMeasure() {
        return value;
    }

    public String getName() {
        return name;
    }
}

// Student class
class Student implements Measurable {
    private String name;
    private double height;

    public Student(String name, double height) {
        this.name = name;
        this.height = height;
    }

    public double getMeasure() {
        return height;
    }

    public String getName() {
        return name;
    }
}

// DataSet class
class DataSet implements Measurable {
    private double sum;
    private int count;
    private Measurable maximum;

    public DataSet() {
        sum = 0;
        count = 0;
        maximum = null;
    }

    public void add(Measurable x) {
        sum = sum + x.getMeasure();
        count++;

        if (maximum == null ||
            x.getMeasure() > maximum.getMeasure()) {
            maximum = x;
        }
    }

    public double getAverage() {
        if (count == 0) {
            return 0;
        }
        return sum / count;
    }

    public Measurable getMax() {
        return maximum;
    }

    public double getMeasure() {
        return getAverage();
    }

    public String getName() {
        return "DataSet";
    }

    public int getCount() {
        return count;
    }
}

// Main GUI
public class DataSetApp extends JFrame {

    public DataSetApp() {
        setTitle("Measurable DataSet System");
        setSize(650, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabs = new JTabbedPane();

        tabs.addTab("Bank Account",
            new DataPanel("BANK"));

        tabs.addTab("Coin",
            new DataPanel("COIN"));

        tabs.addTab("Student",
            new DataPanel("STUDENT"));

        add(tabs);
    }

    public static void main(String[] args) {
        DataSetApp app = new DataSetApp();
        app.setVisible(true);
    }
}

// Reusable panel for all categories
class DataPanel extends JPanel {

    private DataSet data;
    private String type;

    private JTextField txtName;
    private JTextField txtMeasure;
    private JTextArea txtResult;

    private JLabel lblName;
    private JLabel lblMeasure;

    private DecimalFormat df =
        new DecimalFormat("0.00");

    public DataPanel(String type) {
        this.type = type;
        data = new DataSet();

        setLayout(new BorderLayout(10, 10));

        // Title
        JLabel title = new JLabel(
            "Measurable DataSet - " + type,
            JLabel.CENTER);

        title.setFont(new Font("Arial",
            Font.BOLD, 20));

        add(title, BorderLayout.NORTH);

        // Input panel
        JPanel inputPanel = new JPanel(
            new GridLayout(3, 2, 10, 10));

        inputPanel.setBorder(
            BorderFactory.createTitledBorder(
                "Enter Information"));

        lblName = new JLabel("Name:");
        txtName = new JTextField();

        lblMeasure = new JLabel("Value:");
        txtMeasure = new JTextField();

        if (type.equals("BANK")) {
            lblName.setText("Account Type:");
            txtName.setText("Bank Account");
            txtName.setEditable(false);

            lblMeasure.setText("Balance:");
        }
        else if (type.equals("COIN")) {
            lblName.setText("Coin Name:");
            lblMeasure.setText("Coin Value:");
        }
        else {
            lblName.setText("Student Name:");
            lblMeasure.setText("Height (cm):");
        }

        inputPanel.add(lblName);
        inputPanel.add(txtName);

        inputPanel.add(lblMeasure);
        inputPanel.add(txtMeasure);

        // Buttons
        JButton btnAdd = new JButton("Add Data");
        JButton btnCalculate =
            new JButton("Calculate");

        JButton btnClear = new JButton("Clear");

        inputPanel.add(btnAdd);
        inputPanel.add(btnCalculate);

        JPanel topPanel = new JPanel(
            new BorderLayout());

        topPanel.add(inputPanel,
            BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();

        buttonPanel.add(btnClear);

        topPanel.add(buttonPanel,
            BorderLayout.SOUTH);

        add(topPanel, BorderLayout.CENTER);

        // Output area
        txtResult = new JTextArea();
        txtResult.setEditable(false);
        txtResult.setFont(
            new Font("Monospaced",
                Font.PLAIN, 14));

        JScrollPane scroll = new JScrollPane(
            txtResult);

        scroll.setBorder(
            BorderFactory.createTitledBorder(
                "Results"));

        add(scroll, BorderLayout.SOUTH);

        // Add Data button
        btnAdd.addActionListener(
            new ActionListener() {
                public void actionPerformed(
                    ActionEvent e) {
                    addData();
                }
            });

        // Calculate button
        btnCalculate.addActionListener(
            new ActionListener() {
                public void actionPerformed(
                    ActionEvent e) {
                    calculate();
                }
            });

        // Clear button
        btnClear.addActionListener(
            new ActionListener() {
                public void actionPerformed(
                    ActionEvent e) {
                    clearData();
                }
            });
    }

    private void addData() {
        try {
            double value = Double.parseDouble(
                txtMeasure.getText());

            Measurable item;

            if (type.equals("BANK")) {
                if (value < 0) {
                    JOptionPane.showMessageDialog(
                        this,
                        "Balance cannot be negative.");
                    return;
                }

                item = new BankAccount(value);
            }
            else if (type.equals("COIN")) {
                String name = txtName.getText();

                if (name.trim().equals("")) {
                    JOptionPane.showMessageDialog(
                        this,
                        "Please enter coin name.");
                    return;
                }

                if (value < 0) {
                    JOptionPane.showMessageDialog(
                        this,
                        "Coin value cannot be negative.");
                    return;
                }

                item = new Coin(value, name);
            }
            else {
                String name = txtName.getText();

                if (name.trim().equals("")) {
                    JOptionPane.showMessageDialog(
                        this,
                        "Please enter student name.");
                    return;
                }

                if (value <= 0) {
                    JOptionPane.showMessageDialog(
                        this,
                        "Height must be greater than 0.");
                    return;
                }

                item = new Student(name, value);
            }

            data.add(item);

            txtResult.append(
                "Added: " + item.getName()
                + " - " + df.format(
                    item.getMeasure()) + "\n");

            txtMeasure.setText("");

            if (!type.equals("BANK")) {
                txtName.setText("");
            }

            JOptionPane.showMessageDialog(
                this,
                "Data added successfully!");

        }
        catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(
                this,
                "Please enter a valid number.");
        }
    }

    private void calculate() {
        if (data.getCount() == 0) {
            JOptionPane.showMessageDialog(
                this,
                "Please add data first.");
            return;
        }

        Measurable max = data.getMax();

        txtResult.append(
            "\n===== SUMMARY =====\n");

        txtResult.append(
            "Total Records: "
            + data.getCount() + "\n");

        txtResult.append(
            "Average: "
            + df.format(data.getAverage())
            + "\n");

        txtResult.append(
            "Highest Value: "
            + df.format(max.getMeasure())
            + "\n");

        txtResult.append(
            "Highest Name: "
            + max.getName() + "\n");

        txtResult.append(
            "===================\n\n");
    }

    private void clearData() {
        data = new DataSet();

        txtName.setText("");
        txtMeasure.setText("");
        txtResult.setText("");

        if (type.equals("BANK")) {
            txtName.setText("Bank Account");
        }
    }
}
