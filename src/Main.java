import javax.swing.*;

public class Main {
    private static JFrame frame;
    private static JPanel allPanels;

    public static void main(String[] args) {

        frame = new JFrame("URL HashTable");
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        allPanels = new JPanel();
        allPanels.add(new GUI());

        frame.getContentPane().add(allPanels);



        frame.pack();
        frame.setVisible(true);



    }

}
