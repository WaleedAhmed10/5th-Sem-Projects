package org.example;

public class secondLargestSmallest {
    public static void findSecondLargestSmallest(int[] arr) {
        if (arr == null || arr.length < 2) {
            System.out.println("Array should have at least 2 elements");
            return;
        }

        int firstLargest = Integer.MIN_VALUE;
        int secondLargest = Integer.MIN_VALUE;
        int firstSmallest = Integer.MAX_VALUE;
        int secondSmallest = Integer.MAX_VALUE;

        for (int num : arr) {
            if (num > firstLargest) {
                secondLargest = firstLargest;
                firstLargest = num;
            } else if (num > secondLargest && num != firstLargest) {
                secondLargest = num;
            }

            if (num < firstSmallest) {
                secondSmallest = firstSmallest;
                firstSmallest = num;
            } else if (num < secondSmallest && num != firstSmallest) {
                secondSmallest = num;
            }
        }

        if (secondLargest == Integer.MIN_VALUE) {
            System.out.println("No second largest element exists");
        } else {
            System.out.println("Second Largest: " + secondLargest);
        }

        if (secondSmallest == Integer.MAX_VALUE) {
            System.out.println("No second smallest element exists");
        } else {
            System.out.println("Second Smallest: " + secondSmallest);
        }
    }

    public static void main(String[] args) {
        int[] arr = {12, 69, 1, 7, 84, 10, 35};
        findSecondLargestSmallest(arr);
    }
}