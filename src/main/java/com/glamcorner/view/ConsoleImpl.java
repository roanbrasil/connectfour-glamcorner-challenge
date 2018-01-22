package com.glamcorner.view;

import org.springframework.stereotype.Component;

import java.util.InputMismatchException;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

@Component
public class ConsoleImpl implements Console{

    private Scanner input; // Hold the input stream
    public ConsoleImpl (){
        input = new Scanner(System.in);
    }

    @Override
    public void reportMove (int chosenMove, String name) {
        System.out.println("\n" + name + " drops a Checker in Column " + chosenMove);
    }

    @Override
    public int getIntAnswer (String question) {
        int answer = 0;
        boolean valid = false;

        // Ask for a number
        System.out.print(question + " ");
        while(!valid) {
            try {
                answer = input.nextInt();;
                valid = true; // If got to here we have a valid integer
            }
            catch(InputMismatchException ex) {
                reportToUser("That was not a valid integer");
                valid = false;
                input.nextLine(); // Throw away the rest of the line
                System.out.print(question + " ");
            }
        }
        input.nextLine(); // Throw away the rest of the line

        return answer;
    }

    @Override
    public void reportToUser(String message) {
        // Reports something to the user
        System.out.println(message);
    }

    @Override
    public String getAnswer(String question) {
        System.out.print(question);
        return input.nextLine();
    }
}
