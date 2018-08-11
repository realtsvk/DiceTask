package com.sample.dice;

import java.util.*;
import java.util.function.IntPredicate;

class Solution {
    public int solution(int[] dices) {
        System.out.println("Dices: " + Arrays.toString(dices));
        System.out.println("Dices count: " + dices.length);

        int minDiceElement = Arrays.stream(dices).min().getAsInt();
        int maxDiceElement = Arrays.stream(dices).max().getAsInt();

        System.out.println("Min dices number: " + minDiceElement);
        System.out.println("Max dices number: " + maxDiceElement);

        HashMap<Integer, Integer> sides = getCountOfSidesInDice(dices);

        System.out.println("Sides: " + sides.toString());

        optimizeSides(sides);

        System.out.println("Optimized sides: " + sides.toString());

        HashMap<Integer, Integer> moves = new HashMap<>();

        for (int n = minDiceElement; n <= maxDiceElement; n++) {
            if (sides.containsKey(n)) {
                moves.put(n, calculateRotationForDefinedSide(n, sides));
            }
        }

        System.out.println("Moves: " + moves.toString());

        int minimumMoves = moves.values().stream().min(Comparator.naturalOrder()).get();

        showSidesNumbersToPerformMinimumMoves(moves, minimumMoves);

        return minimumMoves;
    }

    // Return pipe numbers, rotation to which can be used to rotate with minimum steps
    private static Integer[] getPipeNumbersForMinimumRotation(HashMap<Integer, Integer> moves, int minimumRotationsNumber) {
        return moves.keySet().stream().filter(key -> moves.get(key).equals(minimumRotationsNumber)).distinct().toArray(Integer[]::new);
    }

    // Calculate how many times do we need to turn over each side
    private static int calculateRotationForDefinedSide(int side, HashMap<Integer, Integer> sides) {
        int rotationsCount = 0;
        if (sides.size() > 1) { // for assuming case if all dices shown in the same side
            Set<Integer> keys = sides.keySet();
            for (Integer key : keys) {
                if (key != side) {
                    rotationsCount += getRotationsCount(key, side) * sides.get(key);
                }
            }
        }
        return rotationsCount;
    }

    // Remove from Map keys with absent pipes
    private static void optimizeSides(HashMap<Integer, Integer> sides) {
        HashMap<Integer, Integer> optimizedSides = new HashMap<>();

        sides.keySet().forEach(key -> {
            if (sides.get(key) != 0) {
                optimizedSides.put(key, sides.get(key));
            }
        });

        sides.clear();
        sides.putAll(optimizedSides);
    }

    private static IntPredicate selectSide(Integer side) {
        return p -> Objects.equals(p, side);
    }

    // Calculate how many of each side number we have
    private static HashMap<Integer, Integer> getCountOfSidesInDice(int[] dies) {
        HashMap<Integer, Integer> map = new HashMap<>();

        for (int n = 1; n <= 6; n++) {
            map.put(n, (int) Arrays.stream(dies).filter(selectSide(n)).count());
        }
        return map;
    }

    // Calculate how many times do we need to turn over pipe with one number to pipe with other number
    private static int getRotationsCount(int currentNum, int numToRotate) {
        return currentNum + numToRotate == 7 ? 2 : 1;
    }

    // Initialized array of dices to values which each dice's pipe can consist
    static void initDices(int[] dices) {
        for (int n = 0; n < dices.length; n++) {
            dices[n] = getNumberInDice();
        }
    }

    // Get random number for die side
    private static int getNumberInDice() {
        return getRandomNumberInRange(1, 6);
    }


    // Get random Integer number from defined range
    static int getRandomNumberInRange(int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    // Print pipe numbers, rotation to which can be used to rotate with minimum steps
    public void showSidesNumbersToPerformMinimumMoves(HashMap<Integer, Integer> moves, int minimumRotationsNumber) {
        Integer[] numbers = getPipeNumbersForMinimumRotation(moves, minimumRotationsNumber);
        System.out.println("Minimum moves possible in case if all other sides will be rotated to " + (numbers.length > 1 ? "any from: " + Arrays.toString(numbers) : numbers[0]));

    }
}
