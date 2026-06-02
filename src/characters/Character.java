package characters;

import java.util.Objects;
import java.util.Random;

public abstract class Character {
    protected String name;
    protected int hp;
    protected int attack;
    protected int defense;

    protected Character(String name, int hp, int attack, int defense) {
        this.name = name;
        this.hp = hp;
        this.attack = attack;
        this.defense = defense;
    }

    // GETTERS & SETTERS
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }


    //  METHODS
    // Attacking = dealing damage to the target
    public void attack(Character target) {
        // Apply a random factor between 0.8 and 1.2 on attack to limit predictable calculations
        Random r = new Random();
        double min = 0.8;
        double max = 1.2;
        double random = min + (max - min) * r.nextDouble();

        double damageDouble = (this.attack * random) - target.defense;
        int damage = (int) Math.round(damageDouble);

        if (damage < 0) damage = 0;
        System.out.println("> " + this.name + " attacks " + target.name + "! Deals " + damage + " damage!");
        target.takeDamage(damage);
    }

    // Take damage
    public void takeDamage(int damage) {
        this.hp -= damage;
        if (this.hp < 0) this.hp = 0;
    }

    // Check if the character is alive based on hp
    public boolean isAlive() {
        return hp > 0;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Character that = (Character) o;
        return hp == that.hp && attack == that.attack && defense == that.defense && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, hp, attack, defense);
    }

    @Override
    public String toString() {
        return "Character{" +
                "name='" + name + '\'' +
                ", hp=" + hp +
                ", attack=" + attack +
                ", defense=" + defense +
                '}';
    }
}
