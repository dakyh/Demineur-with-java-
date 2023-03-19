import javax.swing.JOptionPane;

public class Demineur {

    public void start(Demineur minesweeper) {
        Input = new Choix(minesweeper);
        Input.main(Input);
    }
    
    public void proceed(int size) {
        int difficulte = 1;
        Object[] options = {"facile", "moyen", "difficile"};
        difficulte = JOptionPane.showOptionDialog(null,
                "Choisir ton niveau", "Difficulte",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[1]);
        if(difficulte == -1)
            System.exit(0);
        newGame = new game(size, difficulte);
        newGame.main(newGame, size);

    }
    
    public static void main(String[] args) {
        demineur = new Demineur();
        demineur.start(demineur);
    }
    
    private static Demineur demineur;
    private static game newGame;
    private static Choix Input;
}



