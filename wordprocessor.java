import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

public class WordProcessor extends JFrame {
    private JTextArea textArea;

    public WordProcessor() {
        // Basic frame setup
        setTitle("My Word Processor");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Create a text area inside a scroll pane
        textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);
        add(scrollPane, BorderLayout.CENTER);
        
        // Build the menu bar
        JMenuBar menuBar = new JMenuBar();
        
        // File Menu
        JMenu fileMenu = new JMenu("File");
        JMenuItem openItem = new JMenuItem("Open Word Document");
        openItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openWordDocument();
            }
        });
        fileMenu.add(openItem);
        menuBar.add(fileMenu);
        
        // Edit Menu with simple copy and paste functionalities
        JMenu editMenu = new JMenu("Edit");
        JMenuItem copyItem = new JMenuItem("Copy");
        copyItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textArea.copy();
            }
        });
        editMenu.add(copyItem);
        JMenuItem pasteItem = new JMenuItem("Paste");
        pasteItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textArea.paste();
            }
        });
        editMenu.add(pasteItem);
        menuBar.add(editMenu);
        
        // Help Menu with an About dialog
        JMenu helpMenu = new JMenu("Help");
        JMenuItem aboutItem = new JMenuItem("About");
        aboutItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Word Processor v1.0\nCreated by Your Name");
            }
        });
        helpMenu.add(aboutItem);
        menuBar.add(helpMenu);
        
        setJMenuBar(menuBar);
    }
    
    /**
     * Opens a DOCX file using a file chooser and displays its content in the text area.
     */
    private void openWordDocument() {
        JFileChooser fileChooser = new JFileChooser();
        int option = fileChooser.showOpenDialog(this);
        if(option == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {
                // Check file extension for DOCX format
                if(file.getName().toLowerCase().endsWith(".docx")) {
                    FileInputStream fis = new FileInputStream(file);
                    XWPFDocument document = new XWPFDocument(fis);
                    StringBuilder content = new StringBuilder();
                    document.getParagraphs().forEach(paragraph -> {
                        content.append(paragraph.getText()).append("\n");
                    });
                    textArea.setText(content.toString());
                    document.close();
                    fis.close();
                } else {
                    JOptionPane.showMessageDialog(this, "Unsupported file type. Please select a .docx file.");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error opening file: " + ex.getMessage());
            }
        }
    }
    
    public static void main(String[] args) {
        // Run the GUI in the Event Dispatch Thread for thread safety.
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new WordProcessor().setVisible(true);
            }
        });
    }
}
