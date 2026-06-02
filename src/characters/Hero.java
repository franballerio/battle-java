package characters;

import exceptions.InsufficientManaException;
import exceptions.UnavailablePotionException;
import interfaces.SpecialPower;

import java.util.Objects;
import java.util.Random;

public class Hero extends Character implements SpecialPower {
    private static final int MAX_HP = 70;
    public static final int POTION_HP_ADD = 20;

    private int mana;
    private int numPotions;

    //TODO
    // Add progression (XP / leveling) so the hero becomes stronger over fights


    public Hero(String name) {
        super(name, 50, 12, 4);
        this.mana = 30;
        this.numPotions = 2;
    }

    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public int getNumPotions() {
        return numPotions;
    }

    public void setNumPotions(int numPotions) {
        this.numPotions = numPotions;
    }

    //METHODS
    // Use Power: deals more damage than a normal attack but costs "mana"
    public void usePower(Character target) throws InsufficientManaException {
        int manaCost = 10;

        if (this.mana < manaCost){
            throw new InsufficientManaException("You don't have enough mana \uD83D\uDD25 to use your power!");
        }
        this.mana -= manaCost;

        // Apply a random factor between 1.5 and 2 on attack to add unpredictability
        Random r = new Random();
        double min = 1.5;
        double max = 2;
        double random = min + (max - min) * r.nextDouble();

        double damageDouble = (this.attack * random) - target.defense;
        int damage = (int) Math.round(damageDouble);

        if (damage < 0) damage = 0;
        System.out.println(this.name + " uses their power \uD83D\uDD25 and deals " + damage + " damage to " + target.name + ".");
        System.out.println("\uD83D\uDD25 Mana remaining: " + this.mana);
        target.takeDamage(damage);
    }

    // Use a potion: to restore hit points
    public void usePotion() throws UnavailablePotionException {
        if (numPotions == 0){
            throw new UnavailablePotionException("No potion available!");
        }
        int hpBefore = this.hp;
        this.hp += POTION_HP_ADD;
        if (this.hp > this.MAX_HP){
            this.hp = this.MAX_HP;
            System.out.println("You used a potion \uD83E\uDDEA and restored all your HP.");
        }
        int hpGained = this.hp - hpBefore;

        System.out.println("You used a potion \uD83E\uDDEA and recovered " + hpGained + " HP. You now have " + this.hp + " HP.");
        numPotions--;
        System.out.println("Potions remaining: " + numPotions);

    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Hero hero = (Hero) o;
        return mana == hero.mana && numPotions == hero.numPotions;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), mana, numPotions);
    }

    @Override
    public String toString() {
        return name + " | " + "\u2764\uFE0F " + hp + ", \uD83D\uDDE1\uFE0F " + attack + ", \uD83D\uDEE1\uFE0F " + defense + " |  \uD83D\uDD25 " + mana + ", \uD83E\uDDEA " + numPotions;
    }
}
