import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Choix extends JFrame {

    public Choix(Demineur minesweeper) {
        this.iMinesweeper = minesweeper;
        this.setSize(400, 130);
        this.setTitle("Remplir l'espace vide par quelques chose");
        setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Set the color of the text to green
        this.setForeground(Color.GREEN);
    }

    // Setter and Getter
    public void set(int n) {
        size = n;
        iMinesweeper.proceed(size);
    }

    public int get() {
        return size;
    }
    
    public void main(Choix Input) {
        inputEngine = new ChoixInter(Input);

        size=0;

        panel = new JPanel();
        
        label = new JLabel("Entrer la taille de votre grille");
        panel.add(label);
        
        text = new JTextField(30);
        text.addActionListener(inputEngine);
        panel.add(text);
        
        Input.setContentPane(panel);
        this.setVisible(true);
    }
    
    final private Demineur iMinesweeper;  // A reference to the original game
    private ChoixInter inputEngine;  // The ActionListener

    private int size;  // size given
    private JPanel panel;
    private JLabel label;
    private JTextField text;
}

class ChoixInter implements ActionListener {
    Choix parent;
    
    ChoixInter(Choix parent) {
        this.parent = parent;
    }
    
    @Override
    public void actionPerformed(ActionEvent evt) {
        Object eventSource = evt.getSource();
        JTextField text = (JTextField) eventSource;
        String input =  "0";
        int size = 0;
        
        while(true) {
            try {
                input = text.getText();
                size = Integer.parseInt(input);
                if (size<=6) {
                    JOptionPane.showMessageDialog(parent,
                            "donnez un entier superieur a 6", "Invalide",
                            JOptionPane.ERROR_MESSAGE);
                    text.setText("");
                    break;
                } else {
                    parent.setVisible(false);
                    parent.set(size);
                }
                break;
            }
            catch (NumberFormatException | HeadlessException e) {
                JOptionPane.showMessageDialog(parent,
                        "entrer un entier valide", "Invalide",
                        JOptionPane.ERROR_MESSAGE);
                text.setText("");

                // Set the color of the text to red
                text.setForeground(Color.RED);
                break;
            }

        }
        }
    }


