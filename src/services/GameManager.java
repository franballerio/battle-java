package services;

import characters.Hero;
import scores.ScoreManager;
import java.util.Scanner;
import exceptions.InvalidChoiceException;
import ui.AsciiArt;

import static characters.Hero.POTION_HP_ADD;

public class GameManager {
    private Hero hero;
    private Scanner scanner;

    private final ScoreManager scoreManager = new ScoreManager();

    public GameManager(Scanner scanner) {
        this.scanner = scanner;
    }

    // Welcome the player
    public void welcomePlayer() {
        System.out.println("==== DUNGEONS & DATA ==== ");
        AsciiArt.displayHeroDragon();

        System.out.print("Enter your character's name: ");
        String name = scanner.nextLine().trim();
        // Loop until the user enters a non-empty name
        while (name.isEmpty()) {
            System.out.println("Name cannot be empty. Please enter it:");
            name = scanner.nextLine().trim();
        }

        createPlayer(name);

        System.out.println("Welcome, " + hero.getName() + " \uD83E\uDDD9\u200D\u2642\uFE0F !");
        System.out.println();

        int choice;
        do {
            displayMenu();
            choice = scanner.nextInt();
            scanner.nextLine(); // Avoid issues with line breaks
            try {
                executeChoice(choice);
            } catch (InvalidChoiceException e) {
                System.out.println("\u274C Error: " + e.getMessage());
            }
        } while (choice != 1);  // Loop runs until the game is started
    }

    // Create the player and make it accessible to other classes
    public void createPlayer(String name) {
        this.hero = new Hero(name);
    }

    public Hero getHero() {
        return this.hero;
    }

    private void displayMenu() {
        System.out.println("What would you like to do?");
        System.out.println("1. \u2694\uFE0F Start the game right away");
        System.out.println("2. \uD83D\uDCD6 Read the rules"); // 📖
        System.out.println("3. \uD83D\uDCDC View scores"); //📜
        System.out.println("Enter the number of your choice: ");
    }

    // Apply the choice
    public void executeChoice(int choice) throws InvalidChoiceException {
        switch (choice) {
            case 1:
                break; // game already started in Main
            case 2:
                displayRules();
                break;
            case 3:
                scoreManager.displayLeaderboard();
                break;
            default:
                throw new InvalidChoiceException("Invalid choice. Enter 1, 2 or 3.");
        }
    }

    public void startGame() {
        CombatManager cm = new CombatManager(scanner);
        int enemiesDefeated = 0;

        while (hero.isAlive()) {
            System.out.println("\nA new battle begins!");

            boolean heroStillAlive = cm.startCombat(hero);

            if (heroStillAlive) {
                enemiesDefeated++;
                System.out.println("\u2705 You survived the battle!");
            } else {
                System.out.println("\u2620\uFE0F The hero died during combat.");
                break;
            }
        }
        System.out.println("\uD83C\uDFC1 Game over. Enemies defeated: " + enemiesDefeated);
        scoreManager.saveScore(hero.getName(), enemiesDefeated);

    }

    private void displayRules() {
        System.out.println("===== GAME RULES =====");
        System.out.println("You play as a hero fighting enemies in turn-based combat.");
        System.out.println("On each combat turn, you can choose between:");
        System.out.println("1. \u2694\uFE0F Attack: The damage dealt to your enemy depends on your attack strength, their defense, and a luck factor!");
        System.out.println("2. \uD83D\uDD25 Use your power to increase your attack strength by 1.5 to 2 times. This costs 10 mana points.");
        System.out.println("3. \uD83E\uDDEA Heal yourself with a potion: this lets you recover " + POTION_HP_ADD + " HP.");
        System.out.println("Your goal is to defeat as many enemies as possible before dying.");
        System.out.println("Good luck!");
        System.out.println("=========================");

        try {
            Thread.sleep(10000); // 10s pause
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    // Ask to replay
    public boolean askReplay() {
        while (true) {
            System.out.println("Do you want to play again? (YES/NO)");
            String response = scanner.nextLine().trim().toUpperCase();

            if (response.equals("YES")) {
                System.out.println("What is your new hero's name?");
                String name = scanner.nextLine();
                hero = new Hero(name);
                return true;
            } else if (response.equals("NO")) {
                System.out.println("See you soon!");
                System.out.println("==== GAME OVER ====");
                return false;
            } else {
                System.out.println("\u274C Invalid response. Please answer YES or NO.");
            }
        }
    }

}
