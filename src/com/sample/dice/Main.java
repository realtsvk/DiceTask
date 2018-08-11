package com.sample.dice;

public class Main {
    public static void main(String[] args) {
        final int MAX_DICES_COUNT = 100;
        int diceNumber = Solution.getRandomNumberInRange(1, MAX_DICES_COUNT);
        int[] dices = new int[diceNumber + 1];


        // Solution with random values
        Solution.initDices(dices);
        printSolution(dices);

        // Solution for condition "A = [1, 2, 3], the function should return 2"
        dices = new int[]{1, 2, 3};
        printSolution(dices);

        // Solution for condition "A = [1, 1, 6], the function should also return 2"
        dices = new int[]{1, 1, 6};
        printSolution(dices);

        // Solution for condition "A = [1, 6, 2, 3], the function should return 3"
        dices = new int[]{1, 6, 2, 3};
        printSolution(dices);
    }

    private static void printSolution(int[] a) {
        Solution solution = new Solution();
        System.out.println("Minimum moves: " + solution.solution(a));
        System.out.println("---------------------------------------------------------------------------------");
    }
}
