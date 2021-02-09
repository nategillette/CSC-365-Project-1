import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class GUI extends JPanel implements ActionListener {

    private JLabel lblInput;
    private JTextField txtBox;
    private JButton submit;
    private String url;
    public static JFrame frame;
    public static JTextArea textArea;

    public GUI()
    {
        lblInput = new JLabel("Enter a URL: ");
        txtBox = new JTextField();
        txtBox.setPreferredSize(new Dimension(300,20));
        submit = new JButton("Submit");
        submit.addActionListener(this::actionPerformed);
        textArea = new JTextArea();
        textArea.setPreferredSize(new Dimension(500,60));
        textArea.setWrapStyleWord(true);
        textArea.setLineWrap(true);
        textArea.setEditable(false);
        textArea.setBackground(new Color(240, 240, 240));
        textArea.setBorder(BorderFactory.createLoweredBevelBorder());

        add(lblInput);
        add(txtBox);
        add(submit);
        add(textArea);

        this.setBackground(Color.CYAN);
        this.setPreferredSize(new Dimension(500,100));

    }

        public void actionPerformed(ActionEvent ae)
        {
            Document doc;
            try {
                doc = Jsoup.connect(txtBox.getText()).get();
            } catch (Exception EH) {
                JOptionPane.showMessageDialog(frame, "Please enter a valid URL.", "Oops",
                        JOptionPane.WARNING_MESSAGE);
                txtBox.requestFocusInWindow();
                txtBox.selectAll();
                return;
            }
            try {
               url= new FrequencyTables().initialize(doc);
               textArea.setText("The closest match is: " + url);
               submit.setEnabled(false);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

}