package com.example.service;

import com.example.model.Choices;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class MathService {

    private static final int RANGE = 20;
    private static final double MIN_GAP = 0.05;

    public static int isPrime(int number) {
        if (number < 2) {
            return 0;
        }
        for (int i = 2; i * i <= number; i++) {
            if (number % i == 0) {
                return 0;
            }
        }
        return 1;
    }

    public static int calculateMod(int a, int b) {
        if (b == 0) {
            throw new IllegalArgumentException("Divisor must not be zero");
        }
        return a % b;
    }

    public static double calculateSin(double degree) {
        return Math.sin(Math.toRadians(degree));
    }

    public static double calculateCos(double degree) {
        return Math.cos(Math.toRadians(degree));
    }

    public static double calculateTan(double degree) {
        double radian = Math.toRadians(degree);
        if (Math.abs(Math.cos(radian)) < 1e-10) {
            return Double.NaN;
        }
        return Math.tan(radian);
    }

    public static double calculateTrig(String fn, double degree) {
        switch (fn) {
            case "sin": return calculateSin(degree);
            case "cos": return calculateCos(degree);
            case "tan": return calculateTan(degree);
            default: throw new IllegalArgumentException("Unknown function: " + fn);
        }
    }

    public static Choices intChoices(int correct) {
        ThreadLocalRandom rnd = ThreadLocalRandom.current();
        Set<Long> wrong = new LinkedHashSet<>();
        while (wrong.size() < 2) {
            long d = (long) correct + rnd.nextInt(-RANGE, RANGE + 1);
            if (d != correct) {
                wrong.add(d);
            }
        }
        List<String> options = new ArrayList<>();
        options.add(String.valueOf(correct));
        for (long d : wrong) {
            options.add(String.valueOf(d));
        }
        return shuffled(options, String.valueOf(correct));
    }

    public static Choices decimalChoices(double correct) {
        ThreadLocalRandom rnd = ThreadLocalRandom.current();
        double right = round2(correct);
        List<Double> wrong = new ArrayList<>();
        while (wrong.size() < 2) {
            double d = round2(right + rnd.nextDouble(-RANGE, RANGE));
            if (Math.abs(d - right) < MIN_GAP) {
                continue;
            }
            boolean clash = false;
            for (double w : wrong) {
                if (Math.abs(w - d) < MIN_GAP) {
                    clash = true;
                }
            }
            if (!clash) {
                wrong.add(d);
            }
        }
        List<String> options = new ArrayList<>();
        options.add(format(right));
        for (double d : wrong) {
            options.add(format(d));
        }
        return shuffled(options, format(right));
    }

    private static Choices shuffled(List<String> options, String correct) {
        Collections.shuffle(options, ThreadLocalRandom.current());
        return new Choices(options.toArray(new String[0]), options.indexOf(correct));
    }

    private static double round2(double value) {
        return Math.round(value * 100) / 100.0;
    }

    private static String format(double value) {
        return String.format(Locale.US, "%.2f", value);
    }
}
