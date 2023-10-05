import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ModernUIApp {
    private JFrame frame;
    private JTextArea leftTextArea;  // Change from JTextField to JTextArea
    private JTextField rightTextField;
    private JButton correctButton;
    private JButton clearButton;
    private JButton translateButton;
    private JComboBox<String> languageComboBox;

    public ModernUIApp() {
        frame = new JFrame("THE LANG CORRECTO");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel textFieldsPanel = new JPanel(new GridLayout(1, 2));
        leftTextArea = new JTextArea();  // Change to JTextArea
        leftTextArea.setLineWrap(true);  // Enable line wrapping
        leftTextArea.setWrapStyleWord(true);  // Wrap at word boundaries
        rightTextField = new JTextField();
        rightTextField.setEditable(false);
        textFieldsPanel.add(new JScrollPane(leftTextArea));  // Wrap in JScrollPane
        textFieldsPanel.add(rightTextField);

        JPanel buttonPanel = new JPanel();
        correctButton = new JButton("Correct");
        clearButton = new JButton("Clear");
        translateButton = new JButton("Translate");
        buttonPanel.add(correctButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(translateButton);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        languageComboBox = new JComboBox<>(new String[]{"Hindi", "English", "French", "German"});
        bottomPanel.add(languageComboBox, BorderLayout.EAST);
        bottomPanel.add(buttonPanel, BorderLayout.CENTER);

        frame.add(textFieldsPanel, BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        correctButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Add functionality for the "Correct" button here
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                leftTextArea.setText("");  // Clear the JTextArea
                rightTextField.setText("");
            }
        });

        translateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Add functionality for the "Translate" button here
            }
        });

        frame.setSize(400, 200);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ModernUIApp();
            }
        });
    }
}
