import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.util.Random;
import java.awt.event.*;
import javax.imageio.ImageIO;



public class game extends JFrame {
    
    public game(int size, int difficulte) {
        nbDeMines = size*(1 + difficulte/2);
        this.setSize(size*MAGIC_SIZE, size*MAGIC_SIZE + 50);
        this.setTitle("Demineur by KHADY AND MATAR");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void setMines(int size) {
        Random rand = new Random();
        
        zonemine = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                zonemine[i][j] = 0;
            }
        }

        int count = 0;
        int xPoint;
        int yPoint;
        while(count<nbDeMines) {
            xPoint = rand.nextInt(size);
            yPoint = rand.nextInt(size);
            if (zonemine[xPoint][yPoint]!=-1) {
                zonemine[xPoint][yPoint]=-1;  // -1 representes bombe
                count++;
            }
        }
        
        for (int i = 0; i < size; i++) {
        for (int j = 0; j < size; j++) {
            if (zonemine[i][j]==-1) {
                    for (int k = -1; k <= 1 ; k++) {
                    for (int l = -1; l <= 1; l++) {

                        try {
                            if (zonemine[i+k][j+l]!=-1) {
                                zonemine[i+k][j+l] += 1;
                            }
                        }
                        catch (Exception e) {

                        }
                    }
                    }
            }
        }
        }
    }

    public void main(game frame, int size) {


        Interface intergraph = new Interface(frame);
        MyMouseListener myMouseListener = new MyMouseListener(frame);
        JPanel mainPanel = new JPanel();

        panel1 = new JPanel();
        panel2 = new JPanel();

        this.caserevele = 0;

        revele = new boolean[size][size];
        marque = new boolean[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                revele[i][j] = false;
                marque[i][j] = false;
            }
        }

        // Images
        try {
            smiley = ImageIO.read(getClass().getResource("images/Smiley.png"));
            newSmiley = smiley.getScaledInstance(MAGIC_SIZE, MAGIC_SIZE, Image.SCALE_SMOOTH);

            dead = ImageIO.read(getClass().getResource("images/dead.png"));
            newDead = dead.getScaledInstance(MAGIC_SIZE, MAGIC_SIZE, Image.SCALE_SMOOTH);

            flag = ImageIO.read(getClass().getResource("images/flag.png"));
            newFlag = flag.getScaledInstance(MAGIC_SIZE, MAGIC_SIZE, Image.SCALE_SMOOTH);

            mine = ImageIO.read(getClass().getResource("images/mine.png"));
            newMine = mine.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        }
        catch (Exception e){
        }

        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        BoxLayout g1 = new BoxLayout(panel1, BoxLayout.X_AXIS);
        panel1.setLayout(g1);

        JLabel jLabel1 = new JLabel(" Flags = ");
        jLabel1.setAlignmentX(Component.LEFT_ALIGNMENT);
        jLabel1.setHorizontalAlignment(JLabel.LEFT);
        flagsLabel = new JLabel(""+this.nbDeMines);

        smiling = true;
        smileButton = new JButton(new ImageIcon(newSmiley));
        smileButton.setPreferredSize(new Dimension(MAGIC_SIZE, MAGIC_SIZE));
        smileButton.setMaximumSize(new Dimension(MAGIC_SIZE, MAGIC_SIZE));
        smileButton.setBorderPainted(true);
        smileButton.setName("smileButton");
        smileButton.addActionListener(intergraph);

        JLabel jLabel2 = new JLabel(" Time :");
        timeLabel = new JLabel("0");
        timeLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
        timeLabel.setHorizontalAlignment(JLabel.RIGHT);

        panel1.add(jLabel1);
        panel1.add(flagsLabel);
        panel1.add(Box.createRigidArea(new Dimension((size-1)*15 - 80,50)));
        panel1.add(smileButton, BorderLayout.PAGE_START);
        panel1.add(Box.createRigidArea(new Dimension((size-1)*15 - 85,50)));
        panel1.add(jLabel2);
        panel1.add(timeLabel);
        
        GridLayout g2 = new GridLayout(size, size);
        panel2.setLayout(g2);

        buttons = new JButton[size][size];

        for (int i=0; i<size; i++) {
            for (int j=0; j<size ; j++ ) {
                buttons[i][j] = new JButton();
                buttons[i][j].setPreferredSize(new Dimension(12, 12));
                buttons[i][j].setBorder(new LineBorder(Color.BLACK));
                buttons[i][j].setBorderPainted(true);
                buttons[i][j].setName(i + " " + j);
                buttons[i][j].addActionListener(intergraph);
                buttons[i][j].addMouseListener(myMouseListener);

                // Ajouter une transition au survol de la souris
                buttons[i][j].setContentAreaFilled(false);
                buttons[i][j].setFocusPainted(false);
                buttons[i][j].setOpaque(false);
                int finalI = i;
                int finalJ = j;
                buttons[i][j].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseEntered(MouseEvent e) {
                        buttons[finalI][finalJ].setBackground(Color.LIGHT_GRAY);
                    }
                    @Override
                    public void mouseExited(MouseEvent e) {
                        buttons[finalI][finalJ].setBackground(UIManager.getColor("Button.background"));
                    }
                });


                panel2.add(buttons[i][j]);
            }
        }



        mainPanel.add(panel1);
        mainPanel.add(panel2);
        frame.setContentPane(mainPanel);
        this.setVisible(true);

        setMines(size);

        // Le timer
        timeThread timer = new timeThread(this);
        timer.start();

    }

    public void timer() {
        String[] time = this.timeLabel.getText().split(" ");
        int time0 = Integer.parseInt(time[0]);
        ++time0;
        this.timeLabel.setText(Integer.toString(time0) + " s");
    }


    public void changeSmile() {
        if (smiling) {
            smiling=false;
            smileButton.setIcon(new ImageIcon(newDead));
        } else {
            smiling=true;
            smileButton.setIcon(new ImageIcon(newSmiley));
        }
    }

    //click droit
    public void cliqueDroit(int x, int y) {
        if(!revele[x][y]) {
            if (marque[x][y]) {
                buttons[x][y].setIcon(null);
                marque[x][y] = false;
                int old = Integer.parseInt(this.flagsLabel.getText());
                ++old;
                this.flagsLabel.setText(""+old);
            }
            else {
                if (Integer.parseInt(this.flagsLabel.getText())>0) {
                    buttons[x][y].setIcon(new ImageIcon(newFlag));
                    marque[x][y] = true;
                    int old = Integer.parseInt(this.flagsLabel.getText());
                    --old;
                    this.flagsLabel.setText(""+old);
                }
            }
        }
    }

    private boolean gameWon() {

        return (this.caserevele) ==
                        (Math.pow(this.zonemine.length, 2) - this.nbDeMines);
    }

    //Lors d'un click
    public void clique(int x, int y) {
        if(!revele[x][y] && !marque[x][y]) {
            revele[x][y] = true;

            switch (zonemine[x][y]) {
                case -1:
                    try {
                        buttons[x][y].setIcon(new ImageIcon(newMine));
                    } catch (Exception e1) {
                    }
                    buttons[x][y].setBackground(Color.RED);
                    try {
                        smileButton.setIcon(new ImageIcon(newDead));
                    } catch (Exception e2) {
                    }

                    if(JOptionPane.showConfirmDialog(null,"Game over ! voulez vous rejouer", "Demineur", JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
                        this.setVisible(false);        
                        Demineur demineur = new Demineur();
                                demineur.start(demineur);
                            }
                    else
                    System.exit(0);

                    break;

                case 0:
                    buttons[x][y].setBackground(Color.lightGray);
                    ++this.caserevele;

                    if (gameWon()) {
                        if(JOptionPane.showConfirmDialog(null,"Vous avez gagné ! voulez vous rejouer", "Demineur", JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
                            Demineur demineur = new Demineur();
                            demineur.start(demineur);
                        }
                else
                System.exit(0);
                    } // condition de victoire

                    // Autres
                    for (int i = -1; i <= 1; i++) {
                    for (int j = -1; j <= 1; j++) {
                        try {
                            clique(x + i, y + j);
                        }
                        catch (Exception e3) {

                        }
                    }
                    }

                    break;

                default:
                    buttons[x][y].setText(Integer.toString(zonemine[x][y]));
                    buttons[x][y].setBackground(Color.LIGHT_GRAY);
                    ++this.caserevele;
                    if (gameWon()) {
                        if(JOptionPane.showConfirmDialog(null,"Victoire ! voulez vous rejouer", "Demineur", JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
                            this.setVisible(false);        
                            Demineur demineur = new Demineur();
                                    demineur.start(demineur);
                                }
                        else
                        System.exit(0);
    
                    }

                    break;
            }
        }
        
    }

    private JButton[][] buttons;  // Les boutons de la grille
    private JPanel panel1;  // Top panel containing labels and a smile button
    private JPanel panel2;  // Panneau supérieur contenant des étiquettes et un bouton sourire
    private JLabel flagsLabel;  // Le nombre de drapeau (flag) que l'on peut utiliser
    private JButton smileButton;  // bouton smile
    private JLabel timeLabel;  // Label pour le timer
    private int nbDeMines = 0;  // Le nombre de mines sur le terrain
    private int[][] zonemine;  // Tableau 2D contenant des informations pour chaque bloc
    private boolean[][] revele;  // Si le bouton a été cliqué
    private int caserevele;  // Combien ?
    private boolean[][] marque;  // marqué ou pas ?

    
    private Image smiley;
    private Image newSmiley;
    private Image flag;
    private Image newFlag;
    private Image mine;
    private Image newMine;
    private Image dead;
    private Image newDead;
    
    private boolean smiling;  // oui ou non

    public static final int MAGIC_SIZE = 30;

}

class Interface implements ActionListener {
    game parent;
    
    Interface(game parent) {
        this.parent = parent;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object eventSource = e.getSource();
        JButton clickedButton = (JButton) eventSource;
        String name = clickedButton.getName();
        if (name.equals("smileButton")) {
            parent.changeSmile();
        }
        else {
            String[] xy = clickedButton.getName().split(" ", 2);
            int x = Integer.parseInt(xy[0]);
            int y = Integer.parseInt(xy[1]);
            parent.clique(x, y);

        }
    }
}

class MyMouseListener implements MouseListener {
    game parent;

    MyMouseListener(game parent) {
        this.parent = parent;
    }

    public void mouseExited(MouseEvent arg0){
    }
    public void mouseEntered(MouseEvent arg0){
    }
    public void mousePressed(MouseEvent arg0){
    }
    public void mouseClicked(MouseEvent arg0){
    }

    @Override
    public void mouseReleased(MouseEvent arg0) {
        if(SwingUtilities.isRightMouseButton(arg0)){
            Object eventSource = arg0.getSource();
            JButton clickedButton = (JButton) eventSource;
            String[] xy = clickedButton.getName().split(" ", 2);
            int x = Integer.parseInt(xy[0]);
            int y = Integer.parseInt(xy[1]);
            parent.cliqueDroit(x, y);
        }
    }
}

class timeThread implements Runnable {
    private Thread t;
    private game newGame;

    timeThread(game newGame) {
        this.newGame = newGame;
    }

    public void run() {
        while(true) {
            try {
                Thread.sleep(1000);
                newGame.timer();
            }
            catch (InterruptedException e) {
                System.exit(0);
            }
        }
    }

    public void start() {
        if (t==null) {
            t = new Thread(this);
            t.start();
        }
    }
}