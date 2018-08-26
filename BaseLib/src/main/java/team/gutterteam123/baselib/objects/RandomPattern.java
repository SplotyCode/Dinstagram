package team.gutterteam123.baselib.objects;

import java.util.Random;

public class RandomPattern {

    private String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private int groups;
    private int digits;
    private String pin;
    private Random random = new Random();
    private StringBuilder stringBuilder = new StringBuilder();

    public RandomPattern() {
        this.groups = 6;
        this.digits = 4;
        this.pin = generate();
    }

    public RandomPattern groups(int groups) {
        this.groups = groups;
        return this;
    }

    public RandomPattern digits(int digits) {
        this.digits = digits;
        return this;
    }

    public RandomPattern alphabet(String alphabet) {
        this.alphabet = alphabet;
        return this;
    }

    public final String build() {
        this.pin = generate();
        return this.pin;
    }

    private String generate() {
        stringBuilder.delete(0, stringBuilder.length());
        for (int i = 0; i < this.groups * this.digits; i++) {
            stringBuilder.append(alphabet.charAt(random.nextInt(alphabet.length())));
            if ((i + 1) % this.digits == 0 && (i != (this.groups * this.digits) - 1)) {
                stringBuilder.append("-");
            }
        }
        return stringBuilder.toString();
    }
}
