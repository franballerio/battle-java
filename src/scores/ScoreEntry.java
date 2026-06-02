package scores;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ScoreEntry {
    private LocalDateTime date;
    private String name;
    private int enemiesDefeated;

    public ScoreEntry(LocalDateTime date, String name, int enemiesDefeated) {
        this.date = date;
        this.name = name;
        this.enemiesDefeated = enemiesDefeated;
    }

    public int getEnemiesDefeated() {
        return enemiesDefeated;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public static ScoreEntry fromFormattedString(String line) {
        try {
            String datePart = line.substring(0, 20).trim();
            String namePart = line.substring(20, 35).trim();
            String scorePart = line.replaceAll(".*?(\\d+) enemies defeated", "$1");

            // Parse format "dd/MM/yyyy at HH'h'mm"
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy 'at' HH'h'mm");
            LocalDateTime date = LocalDateTime.parse(datePart, formatter);

            int score = Integer.parseInt(scorePart);
            return new ScoreEntry(date, namePart, score);

        } catch (Exception e) {
            System.out.println("\u274C Invalid line: " + line);
            return null;
        }
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy 'at' HH'h'mm");
        String formattedDate = date.format(formatter);
        return String.format("%-20s %-15s %d enemies defeated", formattedDate, name, enemiesDefeated);
    }

}
