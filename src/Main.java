
import services.GameManager;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        GameManager gm = new GameManager(sc);

        // Welcome the player
        gm.welcomePlayer();

        do {
            gm.startGame();
        } while (gm.askReplay());

    sc.close();
    }
}
