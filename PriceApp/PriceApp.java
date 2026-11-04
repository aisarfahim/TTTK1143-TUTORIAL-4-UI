
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// Interface
interface Price {
    String getPrice(Customer customer);
}

// Product superclass
class Product implements Price {
    private String type;
    private Customer buy;

    public Product(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public String toString() {
        return "The product is " + type + ".";
    }

    public String getPrice(Customer customer) {
        buy = customer;

        if (buy.getSalary() > 4000) {
            return toString() +
                "\nThe customer's name is " +
                buy.getName() + " with salary RM" +
                buy.getSalary() +
                " can buy this product." +
                "\nYour salary is higher than the product's price.";
        } else {
            return toString() +
                "\nThe customer's name is " +
                buy.getName() + " with salary RM" +
                buy.getSalary() +
                " cannot buy this product." +
                "\nThe product price is more than your salary.";
        }
    }
}

// SmartPhone subclass
class SmartPhone extends Product {
    private String nameSP;
    private double priceSP;

    public SmartPhone(String type, String nameSP,
                      double priceSP) {
        super(type);
        this.nameSP = nameSP;
        this.priceSP = priceSP;
    }

    public String toString() {
        return super.toString().replace(
            getType(), nameSP) +
            "\nPrice: RM" + priceSP;
    }
}

// Laptop subclass
class Laptop extends Product {
    private String nameL;
    private double priceL;

    public Laptop(String type, String nameL,
                  double priceL) {
        super(type);
        this.nameL = nameL;
        this.priceL = priceL;
    }

    public String toString() {
        return super.toString().replace(
            getType(), nameL) +
            "\nPrice: RM" + priceL;
    }
}

// Car class
class Car implements Price {
    private String brand;
    private double priceCar;
    private Customer buy;

    public Car(String brand, double priceCar) {
        this.brand = brand;
        this.priceCar = priceCar;
    }

    public String getBrand() {
        return brand;
    }

    public String toString() {
        return "The car brand is " + brand +
            " with price RM" + priceCar + ".";
    }

    public String getPrice(Customer customer) {
        buy = customer;

        if (buy.getSalary() > 8000) {
            return toString() +
                "\nThe customer's name is " +
                buy.getName() + " with salary RM" +
                buy.getSalary() +
                " can buy this car." +
                "\nYour salary is higher than the car's price.";
        } else {
            return toString() +
                "\nThe customer's name is " +
                buy.getName() + " with salary RM" +
                buy.getSalary() +
                " cannot buy this car.";
        }
    }
}

// Customer class
class Customer {
    private String name;
    private double salary;

    public Customer(String name, double salary) {
        this.name = name;
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

    public double getSalary() {
        return salary;
    }
}

// Main GUI
public class PriceApp extends JFrame {

    private JComboBox cmbProduct;
    private JTextField txtName;
    private JTextField txtSalary;
    private JTextArea txtResult;

    public PriceApp() {
        setTitle("Product Affordability System");
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
            "Product Affordability System",
            JLabel.CENTER);

        lblTitle.setFont(
            new Font("Arial", Font.BOLD, 22));

        mainPanel.add(lblTitle,
            BorderLayout.NORTH);

        // Input panel
        JPanel inputPanel = new JPanel(
            new GridLayout(3, 2, 10, 10));

        inputPanel.setBorder(
            BorderFactory.createTitledBorder(
                "Customer Information"));

        cmbProduct = new JComboBox(
            new String[] {
                "SmartPhone",
                "Laptop",
                "Car"
            });

        txtName = new JTextField();
        txtSalary = new JTextField();

        inputPanel.add(
            new JLabel("Product:"));
        inputPanel.add(cmbProduct);

        inputPanel.add(
            new JLabel("Customer Name:"));
        inputPanel.add(txtName);

        inputPanel.add(
            new JLabel("Monthly Salary (RM):"));
        inputPanel.add(txtSalary);

        // Buttons
        JButton btnCheck =
            new JButton("Check Affordability");

        JButton btnClear =
            new JButton("Clear");

        JPanel buttonPanel = new JPanel();

        buttonPanel.add(btnCheck);
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
        txtResult.setLineWrap(true);
        txtResult.setWrapStyleWord(true);

        txtResult.setFont(
            new Font("Monospaced",
                Font.PLAIN, 14));

        JScrollPane scrollPane =
            new JScrollPane(txtResult);

        scrollPane.setBorder(
            BorderFactory.createTitledBorder(
                "Affordability Result"));

        mainPanel.add(scrollPane,
            BorderLayout.SOUTH);

        add(mainPanel);

        // Check button
        btnCheck.addActionListener(
            new ActionListener() {
                public void actionPerformed(
                    ActionEvent e) {
                    checkPrice();
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

    private void checkPrice() {
        String productType =
            cmbProduct.getSelectedItem().toString();

        String customerName =
            txtName.getText().trim();

        String salaryText =
            txtSalary.getText().trim();

        if (customerName.equals("") ||
            salaryText.equals("")) {
            JOptionPane.showMessageDialog(
                this,
                "Please complete all fields.");
            return;
        }

        try {
            double salary =
                Double.parseDouble(salaryText);

            if (salary < 0) {
                JOptionPane.showMessageDialog(
                    this,
                    "Salary cannot be negative.");
                return;
            }

            Customer customer =
                new Customer(customerName, salary);

            Price item;

            if (productType.equals("SmartPhone")) {
                item = new SmartPhone(
                    "SmartPhone",
                    "Iphone 14 ProMax",
                    5500.00);
            }
            else if (productType.equals("Laptop")) {
                item = new Laptop(
                    "Laptop",
                    "Lenovo Yoga",
                    6000.00);
            }
            else {
                item = new Car(
                    "BMW", 200000.00);
            }

            String result =
                item.getPrice(customer);

            txtResult.append(
                result +
                "\n------------------------------\n\n");

        }
        catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(
                this,
                "Please enter a valid salary.");
        }
    }

    private void clearForm() {
        txtName.setText("");
        txtSalary.setText("");
        txtResult.setText("");
        cmbProduct.setSelectedIndex(0);
    }

    public static void main(String[] args) {
        PriceApp app = new PriceApp();
        app.setVisible(true);
    }
}
