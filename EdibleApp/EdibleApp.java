
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// Interface
interface Edible {
    String howToEat();
}

// Animal class
class Animal implements Edible {
    private String name;

    public Animal(String name) {
        this.name = name;
    }

    public String howToEat() {
        return name +
            " needs to be cooked first before eating.";
    }

    public String toString() {
        return name;
    }
}

// Fruit class
class Fruit implements Edible {
    protected String name;

    public Fruit(String name) {
        this.name = name;
    }

    public String howToEat() {
        return name;
    }

    public String toString() {
        return name;
    }
}

// Orange class
class Orange extends Fruit {
    public Orange(String name) {
        super(name);
    }

    public String howToEat() {
        return name +
            " can make orange butter cake.";
    }
}

// Apple class
class Apple extends Fruit {
    public Apple(String name) {
        super(name);
    }

    public String howToEat() {
        return name +
            " can make apple pie.";
    }
}

// Person class
class Person {
    private String name;
    private Edible eat;

    public Person(String name) {
        this.name = name;
    }

    public String eating(Edible eat) {
        this.eat = eat;

        return name + " is eating " +
            eat.toString() + ". " +
            eat.howToEat();
    }
}

// Main GUI
public class EdibleApp extends JFrame {

    private JComboBox cmbPerson;
    private JComboBox cmbFood;
    private JTextField txtName;
    private JTextArea txtResult;

    public EdibleApp() {
        setTitle("Edible Food System");
        setSize(550, 450);
        setDefaultCloseOperation(
            JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main panel
        JPanel mainPanel = new JPanel(
            new BorderLayout(10, 10));

        mainPanel.setBorder(
            BorderFactory.createEmptyBorder(
                15, 15, 15, 15));

        // Title
        JLabel lblTitle = new JLabel(
            "Edible Food System",
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
                "Food Information"));

        JLabel lblPerson =
            new JLabel("Person:");

        cmbPerson = new JComboBox(
            new String[] {"Ali", "Sarah"});

        JLabel lblFood =
            new JLabel("Food Type:");

        cmbFood = new JComboBox(
            new String[] {
                "Animal",
                "Apple",
                "Orange"
            });

        JLabel lblName =
            new JLabel("Food Name:");

        txtName = new JTextField();

        inputPanel.add(lblPerson);
        inputPanel.add(cmbPerson);

        inputPanel.add(lblFood);
        inputPanel.add(cmbFood);

        inputPanel.add(lblName);
        inputPanel.add(txtName);

        // Buttons
        JButton btnEat = new JButton("Eat");
        JButton btnClear = new JButton("Clear");

        JPanel buttonPanel = new JPanel();

        buttonPanel.add(btnEat);
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
            new Font("Arial", Font.PLAIN, 14));

        JScrollPane scrollPane =
            new JScrollPane(txtResult);

        scrollPane.setBorder(
            BorderFactory.createTitledBorder(
                "Eating Result"));

        mainPanel.add(scrollPane,
            BorderLayout.SOUTH);

        add(mainPanel);

        // Eat button
        btnEat.addActionListener(
            new ActionListener() {
                public void actionPerformed(
                    ActionEvent e) {
                    eatFood();
                }
            });

        // Clear button
        btnClear.addActionListener(
            new ActionListener() {
                public void actionPerformed(
                    ActionEvent e) {
                    txtName.setText("");
                    txtResult.setText("");
                }
            });
    }

    private void eatFood() {
        String personName =
            cmbPerson.getSelectedItem().toString();

        String foodType =
            cmbFood.getSelectedItem().toString();

        String foodName =
            txtName.getText().trim();

        if (foodName.equals("")) {
            JOptionPane.showMessageDialog(
                this,
                "Please enter food name.");
            return;
        }

        Edible food;

        if (foodType.equals("Animal")) {
            food = new Animal(foodName);
        }
        else if (foodType.equals("Apple")) {
            food = new Apple(foodName);
        }
        else {
            food = new Orange(foodName);
        }

        Person person =
            new Person(personName);

        String result =
            person.eating(food);

        txtResult.append(result + "\n\n");
    }

    public static void main(String[] args) {
        EdibleApp app = new EdibleApp();
        app.setVisible(true);
    }
}
