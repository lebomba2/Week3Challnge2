package us.mattgreen;

import java.util.Scanner;

public class Main {

    //Global variables
    //these 3 are for file input
    private final static FileInput cardAccts = new FileInput("movie_cards.csv");
    private final static FileInput cardPurchases = new FileInput("movie_purchases.csv");
    private final static FileInput cardRatings = new FileInput("movie_rating.csv");

    //these are for the methods findPurchases and findScores
    private static String line = "";
    private static String lineScore = "";

    public static void main(String[] args) {
        String line;
        //used for output
        String[] fields;
        //output
        int[] nums = new int[3];

        //format header
        System.out.format("%8s  %-18s %6s %6s %5s\n", "Account", "Name", "Movies", "Points", "Score");
        boolean first = true;

        // Read in data from movie_cards.csv
        while ((line = cardAccts.fileReadLine()) != null) {
            // Splits the strings into a String[] called fields
            fields = line.split(",");

            //use methods below
            findPurchases(first, fields[0], nums);
            //Placed custom method here
            findRating(first, fields[0], nums);

            first = false;

            //output format for file data, aligns with the output header.
            System.out.format("00%6s  %-18s  %2d   %4d    %2d \n", fields[0], fields[1], nums[0], nums[1], nums[2]);
        }
    }

    public static void findPurchases(boolean first, String acct, int[] nums) {
        //nums[0] is the "Movies" data
        nums[0] = 0;
        //nums[1] is the "Points" data
        nums[1] = 0;
        //each field of each line in the .csv file, separated by commas
        String[] fields;
        boolean done = false;
        // if you are on the first of the file
        // then read it in
        if (first) {
            line = cardPurchases.fileReadLine();
        }
        while ((line != null) && !(done)) {
            //if you have lines split with commas
            fields = line.split(",");

            //if you there are no more account numbers to look at, be done
            if (fields[0].compareTo(acct) > 0) {
                done = true;

                //if the account field is still the same account number as last time, increment and accumulate!
            } else if (fields[0].equals(acct)) {
                nums[0]++;
                // fields 2 movie points is currently a string
                // parse it to an integer and saves it to numb 1
                nums[1] += Integer.parseInt(fields[2]);

                //next line
                line = cardPurchases.fileReadLine();
            }
        }
    }

    public static void findRating(boolean first, String acct, int[] nums) {
        //nums[2] is the "Scores" data
        nums[2] = 0;
        String[] fields;
        boolean done = false;
        int counter = 0;
        // if you are on the first of the file
        // then read it in
        if (first) {
            lineScore = cardRatings.fileReadLine();
        }
        while ((lineScore != null) && !(done)) {
            //if you have lines split with commas
            fields = lineScore.split(",");
            if (fields[0].compareTo(acct) > 0) {
                // exit out of loop
                done = true;

                //if you are still in the same account, accumulate and increment!
            } else if (fields[0].equals(acct)) {
                counter++;
                // fields 2 movie points is currently a string
                // parse it to an integer and saves it to numb 1
                nums[2] += Integer.parseInt(fields[1]);

                //read next line
                lineScore = cardRatings.fileReadLine();
            }


        }
        //get average for accumulations larger than 0
        if (nums[2] > 0) {

            nums[2] /= counter;
        }
    }
}