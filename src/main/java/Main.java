import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;


public class Main {
    static AtomicInteger word3Chars = new AtomicInteger(0);
    static AtomicInteger word4Chars = new AtomicInteger(0);
    static AtomicInteger word5Chars = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }

        List<Thread> threads = new ArrayList<>();

        threads.add(new Thread(() -> {
            polindromCounter(texts);
        }));

        threads.add(new Thread(() -> {
            sameCharsCounter(texts);
        }));

        threads.add(new Thread(() -> {
            abcCounter(texts);
        }));

        for (Thread thread : threads) {
            thread.start();
        }

        for (Thread thread : threads) {
            thread.join();
        }


        System.out.println("Красивых слов с длиной 3:" + word3Chars.get());
        System.out.println("Красивых слов с длиной 4:" + word4Chars.get());
        System.out.println("Красивых слов с длиной 5:" + word5Chars.get());
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    public static void polindromCounter(String[] nicknames) {
        for (String nickname : nicknames) {
            if (nickname.contentEquals(new StringBuilder(nickname).reverse())) {
                switch (nickname.length()) {
                    case (3) -> word3Chars.getAndIncrement();
                    case (4) -> word4Chars.getAndIncrement();
                    case (5) -> word5Chars.getAndIncrement();
                }
            }
        }
    }

    public static void sameCharsCounter(String[] nicknames) {
        for (String nickname : nicknames) {
            if (nickname.chars().allMatch(c -> nickname.charAt(0) == c)) {
                switch (nickname.length()) {
                    case (3) -> word3Chars.getAndIncrement();
                    case (4) -> word4Chars.getAndIncrement();
                    case (5) -> word5Chars.getAndIncrement();
                }
            }
        }
    }

    public static void abcCounter(String[] nicknames) {
        for (String nickname : nicknames) {
            for (int i = 0; i < nickname.length(); i++) {
                if (i + 1 == nickname.length()) {
                    switch (nickname.length()) {
                        case (3) -> word3Chars.getAndIncrement();
                        case (4) -> word4Chars.getAndIncrement();
                        case (5) -> word5Chars.getAndIncrement();
                    }
                    break;
                }
                if (nickname.chars().allMatch(c -> nickname.charAt(0) == c) || nickname.charAt(i) > nickname.charAt(i + 1))
                    break;
            }
        }
    }
}
