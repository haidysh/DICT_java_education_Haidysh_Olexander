package hangman;
import java.util.*;

public class Hangman {
    private static final Scanner scan = new Scanner(System.in);
    public static void main(String[] args) {
        boolean isExit = false;
        while (!isExit) {
            System.out.print("Type \"play\" to play the game, \"exit\" to quit: ");
            switch (scan.next().trim().toLowerCase()) {
                case "play" -> {
                    Game game = new Game(8);
                    game.gameProcess();
                }
                case "exit" -> isExit = true;
            }
        }
    }
}

class Game {
    private static final Scanner scan = new Scanner(System.in);
    private static final List<String> words = Arrays.asList("python", "java", "javascript", "kotlin");
    private final TargetWord targetWord;
    private int lives;
    private int lettersToGuess;

    public Game(int lives) {
        Random rand = new Random();
        String word = words.get(rand.nextInt(words.size()));

        this.lives = lives;
        this.targetWord = new TargetWord(word);
        this.lettersToGuess = word.length();
    }

    public void gameProcess() {
        while (true) {
            System.out.println('\n' + targetWord.getFinishWord());

            System.out.print("Input a letter: ");
            String input = scan.next();

            if (!input.matches(".")) {
                System.out.println("You should input a single letter");
                continue;
            }
            if (!input.matches("[a-z]")) {
                System.out.println("Please enter a lowercase English letter");
                continue;
            }

            Pair<TargetWord.CheckResult, Integer> result = targetWord.checkLetter(input.charAt(0));
            switchResult(result);

            if (lettersToGuess == 0) {
                System.out.println("You guessed the word " + targetWord.getWord() + "!\nYou survived!");
                return;
            }
            if (lives == 0) {
                System.out.println("You lost!\n");
                return;
            }
        }
    }

    private void switchResult(Pair<TargetWord.CheckResult, Integer> result) {
        switch (result.getFirst()) {
            case already_user -> System.out.println("You've already guessed this letter");
            case fail -> {
                System.out.println("That letter doesn't appear in the word");
                lives--;
            }
            case success -> lettersToGuess -= result.getSecond();
        }
    }
}

class TargetWord {
    private final List<Character> alreadyUsed = new ArrayList<>();
    private final String word;
    private String finishWord = "";

    private static List<Integer> getAllOccurrences(Character ch, String word) {
        ArrayList<Integer> indexes = new ArrayList<>();
        for (int i = 0; i < word.length(); i++) {
            if (ch.equals(word.charAt(i))) {
                indexes.add(i);
            }
        }

        return indexes;
    }

    public TargetWord(String word) {
        this.word = word;
        while (finishWord.length() < word.length()) {
            finishWord += "-";
        }
    }

    public String getFinishWord() {
        return finishWord;
    }

    public String getWord() {
        return word;
    }

    public Pair<CheckResult, Integer> checkLetter(Character character) {
        if (alreadyUsed.contains(character)) {
            return new Pair<>(CheckResult.already_user, 0);
        }

        alreadyUsed.add(character);

        List<Integer> indexes = getAllOccurrences(character, word);
        if (indexes.isEmpty()) {
            return new Pair<>(CheckResult.fail, 0);
        }

        rewriteFinishLetters(indexes, character);
        return new Pair<>(CheckResult.success, indexes.size());
    }

    private void rewriteFinishLetters(List<Integer> indexes, Character letter) {
        indexes.forEach(el -> finishWord = finishWord.substring(0, el) + letter + finishWord.substring(el + 1));
    }

    enum CheckResult {
        already_user,
        success,
        fail
    }
}

record Pair<First, Second>(First first, Second second) {
    public First getFirst() {
        return first;
    }
    public Second getSecond() {
        return second;
    }
}