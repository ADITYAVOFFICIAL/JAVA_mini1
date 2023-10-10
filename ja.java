import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;

public class ModernUIApp {
    private JFrame frame;
    private JTextArea leftTextArea;
    private JTextArea rightTextArea;
    private JButton correctButton;
    private JButton clearButton;
    private JButton translateButton;
    private JComboBox<String> languageComboBox;

    private static final String PYTHON_SERVER_HOST = "127.0.0.1";
    private static final int PYTHON_SERVER_PORT = 12345;

    public ModernUIApp() {
        frame = new JFrame("THE LANG CORRECTO");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel textFieldsPanel = new JPanel(new GridLayout(1, 2));
        leftTextArea = new JTextArea();
        leftTextArea.setLineWrap(true);
        leftTextArea.setWrapStyleWord(true);
        rightTextArea = new JTextArea();
        rightTextArea.setEditable(false);
        textFieldsPanel.add(new JScrollPane(leftTextArea));
        textFieldsPanel.add(new JScrollPane(rightTextArea));

        JPanel buttonPanel = new JPanel();
        correctButton = new JButton("Correct");
        clearButton = new JButton("Clear");
        translateButton = new JButton("Translate");
        buttonPanel.add(correctButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(translateButton);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        languageComboBox = new JComboBox<>(new String[]{"Hindi", "English", "French", "German", "Korean", "Japanese"});
        bottomPanel.add(languageComboBox, BorderLayout.EAST);
        bottomPanel.add(buttonPanel, BorderLayout.CENTER);

        frame.add(textFieldsPanel, BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        correctButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() {
                try {
                    Socket pythonSocket = new Socket(PYTHON_SERVER_HOST, PYTHON_SERVER_PORT);
                    String language = languageComboBox.getSelectedItem().toString();
                    String textToCorrect = leftTextArea.getText();
                    String message = "Correct#" + language + "#" + textToCorrect;

                    OutputStream os = pythonSocket.getOutputStream();
                    PrintWriter writer = new PrintWriter(os, true);

                    // Send the message to the server
                    writer.println(message);

                    InputStream is = pythonSocket.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                    String correctedText = reader.readLine();

                    rightTextArea.setText(correctedText);
                    // System.out.println("Grammar corrected.");

                    // Close the socket when done
                    pythonSocket.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                return null;
            }
        };
        worker.execute();
    }
});

        translateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
                    @Override
                    protected Void doInBackground() {
                        try {
                            Socket pythonSocket = new Socket(PYTHON_SERVER_HOST, PYTHON_SERVER_PORT);
                            String textToTranslate = leftTextArea.getText();
                            String targetLanguage = languageComboBox.getSelectedItem().toString();
                            String message = "Translate#" + targetLanguage + "#" + textToTranslate;

                            OutputStream os = pythonSocket.getOutputStream();
                            PrintWriter writer = new PrintWriter(os, true);
                            writer.println(message);

                            InputStream is = pythonSocket.getInputStream();
                            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                            String translatedText = reader.readLine();

                            rightTextArea.setText(translatedText);
                            // System.out.println("Text translated.");

                            pythonSocket.close();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                        return null;
                    }
                };
                worker.execute();
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                leftTextArea.setText("");
                rightTextArea.setText("");
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
