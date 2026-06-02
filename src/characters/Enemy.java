package characters;

public class Enemy extends Character {
    public Enemy(String name, int hp, int attack, int defense) {
        super(name, hp, attack, defense);
    }

    @Override
    public String toString() {
        return this.getName() + " | " + "\u2764\uFE0F " + hp + ", \uD83D\uDDE1\uFE0F " + attack + ", \uD83D\uDEE1\uFE0F " + defense + "\n";
    }
}
