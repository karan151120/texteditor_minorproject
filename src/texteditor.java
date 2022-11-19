import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class texteditor extends JFrame implements ActionListener{


    JScrollPane scrollPane;
    JTextArea textArea;

    JSpinner spinner;

    JComboBox fontbox;

    JMenuBar menuBar;

    JMenu filemenu;
    JMenuItem save;
    JMenuItem exit;

    JLabel fontsizelabel;

    JFileChooser fileChooser;

    texteditor(){
        this.setSize(500,600);
        this.setTitle("TextEditor-MinorProject");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new FlowLayout());

        //textArea
        textArea = new JTextArea();
        textArea.setSelectedTextColor(Color.blue);
        textArea.setFont(new Font("Arial",Font.PLAIN,18));


        //scrollPane
        scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(450,450));

        //spinner
        spinner = new JSpinner();
        spinner.setSize(50,25);
        spinner.setValue(18);
        fontsizelabel = new JLabel("Font Size:");
        spinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                System.out.println(spinner.getValue());
                textArea.setFont(new Font(textArea.getFont().getFamily(),Font.PLAIN, (int) spinner.getValue()));
            }
            //add font color
            //add autosave
            //add font size(Bold, Italic)

        });

        //fontbox
        String[] fonts = GraphicsEnvironment. getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        fontbox = new JComboBox(fonts); //Check Error fontbox = new JComboBox(fonts);
        fontbox.addActionListener(this);

        //menubar
        menuBar = new JMenuBar();
        filemenu = new JMenu("File");

        save = new JMenuItem("Save");
        save.addActionListener(this);

        exit = new JMenuItem("Exit");
        exit.addActionListener(this);

        //add menuItem
        filemenu.add(save);
        filemenu.add(exit);
        menuBar.add(filemenu);

        //add all elements in JFrame
        this.setJMenuBar(menuBar);
        this.add(fontsizelabel);
        this.add(spinner);
        this.add(fontbox);
        this.add(scrollPane);

        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == fontbox){
            textArea.setFont(new Font((String)fontbox.getSelectedItem(),Font.PLAIN, (int) spinner.getValue()));
        }

        if(e.getSource() == exit){
            System.exit(2);
        }

        if(e.getSource() == save){
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File("."));

            int responce = fileChooser.showSaveDialog(null);

            if(responce == 0){
                //Need to save this file
                File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
                PrintWriter text = null;
                try {
                    text = new PrintWriter(file);
                    text.println(textArea.getText());
                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
                text.close();

            }
        }
    }
}
