/*
 *==================================================
 * CS141
 * LAB 5: MadLibs
 * authors: Jack Pate, Aaron Tripp, Daniel Grijalva
 *==================================================
 * Description: This program allows a user to play a
 * game of short stories.
 *==================================================
 */
import java.io.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.FileNotFoundException;
import java.util.Scanner;
public class MadLibs {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner console = new Scanner(System.in);
        introduction();
        boolean playMadLibs = true;
        //Loop for playing the run method until player quits.
        while(playMadLibs){
            playMadLibs = run(console);
        }
    }
    //Provides the introduction and starts the game. 
    public static void introduction() {
        System.out.println("Welcome to the game of Mad Libs.");
        System.out.println("I will ask you to provide various words");
        System.out.println("and phrases to fill in a story.");
        System.out.println("The results will be written to an output file.");
    }
    //Method asks the player what they would like to do and will take players response.
    public static boolean run(Scanner console) throws FileNotFoundException {
        boolean playMadLibs = true;
        System.out.println("(C)reate mad-lib, (V)iew mad-lib, (Q)uit?");
        String answer = console.next();
        String answerLC = answer.toLowerCase();
        if(answerLC.charAt(0) == ('c')) {
            create(console);
        } else if (answerLC.charAt(0) == ('v')) {
            view(console);
        } else if (answerLC.charAt(0) == ('q')) {
            return false;
        } else {
            System.out.println("Invalid answer. Please try again.");
        }
        return true;
    }
    //Method creates a new mad-lib.
    public static void create(Scanner console)throws FileNotFoundException{
        System.out.println("Input file name: ");
        String nameOfFileInput = console.next();
        File file1 = new File(nameOfFileInput);
        while(!file1.exists()){
            System.out.println("File not found. Try again: ");
                    nameOfFileInput = console.next();
            file1 = new File(nameOfFileInput);
        }
        System.out.println("Output file name: ");
        String nameOfFileOutput = console.next();
        File o = new File(nameOfFileOutput);
        PrintStream Output = new PrintStream(o);
        Scanner input = new Scanner(file1);
        //Replaces the characters.
        String outline = "";
        while(input.hasNextLine()){
            //Ignore outline
            outline = "";
            String text = input.nextLine();
            Scanner scanLine = new Scanner(text);
            while (scanLine.hasNext()) {
                String textToken = scanLine.next();
                if(textToken.startsWith("<") && textToken.endsWith(">")){
                    char a = textToken.charAt(1);
                    String article = articleChecker(a);
                    textToken = textToken.replace('<', ' ');
                    textToken = textToken.replace('>', ' ');
                    textToken = textToken.replace('-', ' ');
                    if(textToken.endsWith(".")){
                        break;
                    }
                    System.out.println("Please type" + article + textToken);
                    String in = console.next();
                    textToken = in;
                }
                else {
                    //Ignore outline and leftover code
                    outline +=(textToken + (" "));
                }
                if(textToken.endsWith("\n") || textToken.endsWith(".") ||
                        textToken.endsWith("!") || textToken.endsWith("?"))
                    Output.println(textToken);
                else
                    //Ignore outline and leftover code
                    Output.print(textToken + " ");
            }
        }
        System.out.println("Your mad-lib has been created!");
    }
    //Method will let player view previous mad-lib and ask for the filename.
    public static void view(Scanner console)throws FileNotFoundException {
        System.out.println("Input File Name: ");
        String nameOfFileViewing = console.next();
        File o = new File(nameOfFileViewing);
        while(!o.exists()) {
            System.out.println("Invalid Output. Please Try Again.");
            nameOfFileViewing = console.next();
            o = new File(nameOfFileViewing);
        }
        Scanner oInput = new Scanner(o);
        while(oInput.hasNextLine()){
            String text = oInput.nextLine();
            System.out.println(text + " ");
        }
        System.out.println();
    }
    //Method will check whether to type an "a" or "an" before the word by checking if it starts with a vowel
    public static String articleChecker(char check){
        char checkLC = Character.toLowerCase(check);
        String a = "";
        if (checkLC == 'a' || checkLC == 'e' || checkLC == 'i' || checkLC == 'o' ||
                checkLC == 'u'){
            a = " an";
        } else {
            a = " a";
        }
        return a;
    }
}