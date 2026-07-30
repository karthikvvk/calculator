import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Calculator extends JFrame implements ActionListener {

    private JTextField display;
    private String currentInput = "";
    private double firstNumber = 0;
    private String operator = "";
    private boolean newInput = true;

    // Button labels
    private static final String[] BUTTONS = {
        "AC", "+/-", "%", "/",
        "7",  "8",  "9", "*",
        "4",  "5",  "6", "-",
        "1",  "2",  "3", "+",
        "0",  ".",  "="
    };

    public Calculator() {
        setTitle("Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout(5, 5));
        getContentPane().setBackground(new Color(30, 30, 30));

        // --- Display ---
        display = new JTextField("0");
        display.setEditable(false);
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setFont(new Font("SansSerif", Font.PLAIN, 38));
        display.setBackground(new Color(30, 30, 30));
        display.setForeground(Color.WHITE);
        display.setBorder(BorderFactory.createEmptyBorder(20, 15, 10, 15));
        add(display, BorderLayout.NORTH);

        // --- Button Panel ---
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(new Color(30, 30, 30));
        panel.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.weightx = 1;
        gbc.weighty = 1;

        // Row 0: AC, +/-, %, /
        addButton(panel, gbc, "AC",  0, 0, 1, new Color(165, 165, 165), Color.BLACK);
        addButton(panel, gbc, "+/-", 1, 0, 1, new Color(165, 165, 165), Color.BLACK);
        addButton(panel, gbc, "%",   2, 0, 1, new Color(165, 165, 165), Color.BLACK);
        addButton(panel, gbc, "/",   3, 0, 1, new Color(255, 159, 10),  Color.WHITE);

        // Row 1: 7, 8, 9, *
        addButton(panel, gbc, "7", 0, 1, 1, new Color(58, 58, 58), Color.WHITE);
        addButton(panel, gbc, "8", 1, 1, 1, new Color(58, 58, 58), Color.WHITE);
        addButton(panel, gbc, "9", 2, 1, 1, new Color(58, 58, 58), Color.WHITE);
        addButton(panel, gbc, "*", 3, 1, 1, new Color(255, 159, 10), Color.WHITE);

        // Row 2: 4, 5, 6, -
        addButton(panel, gbc, "4", 0, 2, 1, new Color(58, 58, 58), Color.WHITE);
        addButton(panel, gbc, "5", 1, 2, 1, new Color(58, 58, 58), Color.WHITE);
        addButton(panel, gbc, "6", 2, 2, 1, new Color(58, 58, 58), Color.WHITE);
        addButton(panel, gbc, "-", 3, 2, 1, new Color(255, 159, 10), Color.WHITE);

        // Row 3: 1, 2, 3, +
        addButton(panel, gbc, "1", 0, 3, 1, new Color(58, 58, 58), Color.WHITE);
        addButton(panel, gbc, "2", 1, 3, 1, new Color(58, 58, 58), Color.WHITE);
        addButton(panel, gbc, "3", 2, 3, 1, new Color(58, 58, 58), Color.WHITE);
        addButton(panel, gbc, "+", 3, 3, 1, new Color(255, 159, 10), Color.WHITE);

        // Row 4: 0 (wide), ., =
        addButton(panel, gbc, "0", 0, 4, 2, new Color(58, 58, 58), Color.WHITE);
        addButton(panel, gbc, ".", 2, 4, 1, new Color(58, 58, 58), Color.WHITE);
        addButton(panel, gbc, "=", 3, 4, 1, new Color(255, 159, 10), Color.WHITE);

        add(panel, BorderLayout.CENTER);

        pack();
        setMinimumSize(new Dimension(300, 420));
        setLocationRelativeTo(null);
    }

    private void addButton(JPanel panel, GridBagConstraints gbc,
                           String label, int col, int row, int colSpan,
                           Color bg, Color fg) {
        gbc.gridx = col;
        gbc.gridy = row;
        gbc.gridwidth = colSpan;

        JButton btn = new JButton(label);
        btn.setFont(new Font("SansSerif", Font.PLAIN, 20));
        btn.setBackground(bg);
        btn.setForeground(fg);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setOpaque(true);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(60, 60));

        // Hover effect
        btn.addMouseListener(new MouseAdapter() {
            Color original = bg;
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(bg.brighter());
            }
            public void mouseExited(MouseEvent e) {
                btn.setBackground(original);
            }
        });

        btn.addActionListener(this);
        panel.add(btn, gbc);

        // Reset colspan
        gbc.gridwidth = 1;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();

        switch (cmd) {
            case "AC":
                currentInput = "";
                firstNumber = 0;
                operator = "";
                newInput = true;
                display.setText("0");
                break;

            case "+/-":
                if (!display.getText().equals("0")) {
                    if (display.getText().startsWith("-")) {
                        display.setText(display.getText().substring(1));
                    } else {
                        display.setText("-" + display.getText());
                    }
                    currentInput = display.getText();
                }
                break;

            case "%":
                try {
                    double val = Double.parseDouble(display.getText()) / 100;
                    display.setText(formatResult(val));
                    currentInput = display.getText();
                } catch (NumberFormatException ex) { /* ignore */ }
                break;

            case "+": case "-": case "*": case "/":
                try {
                    firstNumber = Double.parseDouble(display.getText());
                } catch (NumberFormatException ex) { break; }
                operator = cmd;
                newInput = true;
                break;

            case "=":
                if (!operator.isEmpty()) {
                    try {
                        double secondNumber = Double.parseDouble(display.getText());
                        double result = compute(firstNumber, operator, secondNumber);
                        display.setText(formatResult(result));
                        currentInput = display.getText();
                        operator = "";
                        newInput = true;
                    } catch (NumberFormatException | ArithmeticException ex) {
                        display.setText("Error");
                    }
                }
                break;

            case ".":
                if (newInput) {
                    display.setText("0.");
                    newInput = false;
                } else if (!display.getText().contains(".")) {
                    display.setText(display.getText() + ".");
                }
                currentInput = display.getText();
                break;

            default:
                // Digit pressed
                if (newInput) {
                    display.setText(cmd);
                    newInput = false;
                } else {
                    String current = display.getText();
                    display.setText(current.equals("0") ? cmd : current + cmd);
                }
                currentInput = display.getText();
        }
    }

    private double compute(double a, String op, double b) {
        switch (op) {
            case "+": return a + b;
            case "-": return a - b;
            case "*": return a * b;
            case "/":
                if (b == 0) throw new ArithmeticException("Division by zero");
                return a / b;
            default: return b;
        }
    }

    private String formatResult(double value) {
        if (Double.isNaN(value) || Double.isInfinite(value)) return "Error";
        if (value == Math.floor(value) && Math.abs(value) < 1e15) {
            return String.valueOf((long) value);
        }
        return String.valueOf(value);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            } catch (Exception ignored) {}
            new Calculator().setVisible(true);
        });
    }
}
