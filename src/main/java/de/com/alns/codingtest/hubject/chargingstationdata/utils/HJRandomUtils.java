package de.com.alns.codingtest.hubject.chargingstationdata.utils;

import java.util.Random;

public class HJRandomUtils {

    public static Random RANDOM = new Random(System.nanoTime());

    public static Double generateRandomDoubleBetweenRange(double pNumMin, double pNumMax) {
        return pNumMin + RANDOM.nextFloat() * (pNumMax - pNumMin);
    }

    public static Integer generateRandomIntBetweenRange(int pNumMin, int pNumMax) {
        return pNumMin + RANDOM.nextInt(pNumMax - pNumMin);
    }

}
