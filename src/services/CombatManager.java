package services;

import exceptions.InsufficientManaException;
import exceptions.UnavailablePotionException;
import characters.*;
import java.util.Random;
import java.util.Scanner;
import exceptions.InvalidChoiceException;


public class CombatManager {
    private final Scanner scanner;

    public CombatManager(Scanner scanner) {
        this.scanner = scanner;
    }

    // Start combat (For each turn)
    public boolean startCombat(Hero hero) {
        Enemy enemy = generateRandomEnemy();

        String emoji = "";
        switch (enemy.getName()) {
            case "Troll":
                emoji = "\uD83E\uDDCC"; // 🧌
                break;
            case "Goblin":
                emoji = "\uD83D\uDD77\uFE0F"; // 🕷️
                break;
            case "Dragon":
                emoji = "\uD83D\uDC09"; // 🐉
                break;
            default:
                emoji = "\u2753"; // ❓ unknown
        }

        System.out.println("\n " + emoji + " A " + enemy.getName() + " appears!");

        // Add a delay
        try {
            Thread.sleep(3000); // 3s pause
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        while (hero.isAlive() && enemy.isAlive()) {
            // 1 turn
            displayState(hero, enemy);
            // Add a delay
            try {
                Thread.sleep(1000); // 1s pause
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            displayChoices();
            int choice = scanner.nextInt();
            scanner.nextLine();
            try {
                executeChoice(choice, hero, enemy);
            } catch (InvalidChoiceException e) {
                System.out.println("\u274C Error: " + e.getMessage());
            }

            // Add a delay
            try {
                Thread.sleep(500); // 0.5s pause
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        return hero.isAlive();
    }


    // Generate a random enemy
    private Enemy generateRandomEnemy() {
        Random rand = new Random();
        int pick = rand.nextInt(3) + 1;
        switch (pick) {
            case 1: return new Goblin();
            case 2: return new Troll();
            case 3: return new Dragon();
            default: return new Goblin();
        }
    }

    // Display hero and enemy state
    private void displayState(Hero hero, Enemy enemy) {
        System.out.println("\n--- Current State ---");
        System.out.println(hero);
        System.out.println(enemy);
    }

    // Display choice menu
    public void displayChoices() {
        System.out.println("What would you like to do?");
        System.out.println("1. \u2694\uFE0F Attack");
        System.out.println("2. \uD83D\uDD25 Use mana");
        System.out.println("3. \uD83E\uDDEA Use a healing potion");
        System.out.println("4. Display your stats");
        System.out.println("Enter the number of your choice: ");
    }

        // Apply the choice
    public void executeChoice(int choice, Hero hero, Enemy enemy) throws InvalidChoiceException {
        try {
            switch (choice) {
                case 1:
                    hero.attack(enemy);
                    if (enemy.isAlive()) {
                        enemy.attack(hero);
                    }
                    break;
                case 2:
                    hero.usePower(enemy);
                    if (enemy.isAlive()) {
                        enemy.attack(hero);
                    }
                    break;
                case 3:
                    hero.usePotion();
                    break;
                case 4:
                    System.out.println(hero);
                    break;
                default:
                    throw new InvalidChoiceException("Invalid choice during combat. Enter one of the given numbers.");
            }
        } catch (InsufficientManaException | UnavailablePotionException e) {
            System.out.println("\u274C " + e.getMessage());
        }
    }

}
