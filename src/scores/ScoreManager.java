package scores;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class ScoreManager {
    private List<ScoreEntry> scores = new ArrayList<>();
    private static final String SCORES_FILE = "scores.txt";

    // Save score to a text file
    public void saveScore(String name, int enemiesDefeated) {
        ScoreEntry entry = new ScoreEntry(LocalDateTime.now(), name, enemiesDefeated);
        scores.add(entry);

        try (FileWriter writer = new FileWriter(SCORES_FILE, true)) {
            writer.write(entry.toString() + "\n");
            System.out.println("\u2705 Score saved.");
        } catch (IOException e) {
            System.out.println("\u274C Error while saving the score.");
        }
    }

    public void displayLeaderboard() {
        System.out.println("===== LEADERBOARD =====");
        try {
            List<String> lines = Files.readAllLines(Paths.get(SCORES_FILE));
            List<ScoreEntry> scores = lines.stream()
                    .map(ScoreEntry::fromFormattedString)
                    .filter(Objects::nonNull)
                    .sorted(Comparator.comparingInt(ScoreEntry::getEnemiesDefeated).reversed())
                    .limit(5)
                    .toList();

            for (ScoreEntry entry : scores) {
                System.out.println(entry);
            }

        } catch (IOException e) {
            System.out.println("\u274C Unable to read scores.");
        }
        System.out.println("==================");
    }

}
