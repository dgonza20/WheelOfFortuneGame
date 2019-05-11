/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//David Gonzales

package woffortune;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Random;
import java.util.Set;

/**
 * WofFortuneGame class
 * Contains all logistics to run the game
 * @author clatulip
 */
public class WofFortuneGame {

    private boolean puzzleSolved = false;

    private Wheel wheel;
    private Player player1;
    private String phrase = "Once upon a time";
   
    
    // 1A   Array list to hold the users phrase
    private ArrayList<Letter> userPhrase = new ArrayList<Letter>();     
   
    // 2A   Arraylist of string objects for phrases
    private ArrayList<String> ListPhrases = new ArrayList<String>();    
    
    // 3A   Arraylist of player objects for players playing
    private ArrayList<Player> players = new ArrayList<Player>();
    
    // 5  Add Player Prize Array List
     private ArrayList<String> playerPrize = new ArrayList<String>(); 
    
    Scanner answer = new Scanner(System.in);        // Will hold user answer for if he wants to type his own phrse
    Scanner phr = new Scanner(System.in);       // Will hold user input phrase
    Scanner names = new Scanner(System.in);     // input for player names
    
    /**
     * Constructor
     * @param wheel Wheel 
     * @throws InterruptedException 
     */
    public WofFortuneGame(Wheel wheel) throws InterruptedException {
        // get the wheel
        this.wheel = wheel;
        
        // do all the initialization for the game
        setUpGame();
        

    }
    
    /**
     * Plays the game
     * @throws InterruptedException 
     */
    public void playGame() throws InterruptedException {
        // while the puzzle isn't solved, keep going
        while (!puzzleSolved){
            // let the current player play
            
            // 3D Rotate throught each player playing and let them have a turn
            for(int i=0; i< players.size(); i++){
               playTurn(players.get(i)); 
            }
            
        }
    }
    
    /**
     * Sets up all necessary information to run the game
     */
    private void setUpGame() {
        // 2C   Call for the list of phrases 
        ListofPhrases();
        
        // Number of people currently playing when game starts
        int peoplePlaying = 0;
       
        
        
        // Loop to ensure that a there is a certain number of people playing
        // This loop will contiune untill peoplePlaying becomes greater than 0
        while(peoplePlaying < 1){
            
        // 4B Catch InputMismatchException for number of players
        try{
            Scanner numPeople = new Scanner(System.in);     // input for number of players
            
            // 3B Ask for how many players are playing and ask for names
            System.out.println("How many people are going to be playing? ");
        
            peoplePlaying = numPeople.nextInt();
             
            }
        catch (InputMismatchException ime){
            System.out.println("You did not enter a number. Please enter a number greater than zero.");
            }
        
        
        }
        
        
        // 3C Ask for the names of the players to be entered and add them to the
        // arraylist of players
        for(int i=0, j=1; i < peoplePlaying; i++, j++){
            System.out.println("Player " + j + " enter your name: ");
            String playerNames = names.next();
            Player player = new Player(playerNames);
            players.add(player);
                }
        
        
        // print out the rules
        System.out.println("RULES!");
        System.out.println("Each player gets to spin the wheel, to get a number value");
        System.out.println("Each player then gets to guess a letter. If that letter is in the phrase, ");
        System.out.println(" the player will get the amount from the wheel for each occurence of the letter");
        System.out.println("If you have found a letter, you will also get a chance to guess at the phrase");
        System.out.println("Each player only has three guesses, once you have used up your three guesses, ");
        System.out.println("you can still guess letters, but no longer solve the puzzle.");
        System.out.println();
        
        // Loop so that the player can decide if he wants to enter his own phrase or not
        // This loop will contine untill they answer 'y' or 'n'
        boolean done = false;
        
        do{
            try{
            // 1B Ask the user if they want to enter thier own phrase
            System.out.println("Would you like to enter your own phrase? (Y/N)");
            
            char ans = answer.next().charAt(0);
        
            if((ans == 'y') || (ans =='Y')){
                System.out.println("Enter your phrase: ");
                phrase = phr.nextLine();
                done = true;
            }
            else if((ans == 'n') || (ans == 'N')){
                Random randGen = new Random();
                int num = randGen.nextInt(ListPhrases.size());
                phrase =ListPhrases.get(num);
                done = true; 
            }
            }
            catch(Exception e){
                System.out.print("You did not say (Y or N)");
               // System.out.println(e);
            }
        
        }while(!done);
        
        
        
        // 1A
        // This will set the users phrase into the array list
        for(int i=0; i < phrase.length(); i++){
            userPhrase.add(i, new Letter(phrase.charAt(i))); 
        }
        
        
        
    }
    
    /**
     * One player's turn in the game
     * Spin wheel, pick a letter, choose to solve puzzle if letter found
     * @param player
     * @throws InterruptedException 
     */
    private void playTurn(Player player) throws InterruptedException {
        int money = 0;
        Scanner sc = new Scanner(System.in);
        int m = 0;
        
        
        
        System.out.println(player.getName() + ", you have $" + player.getWinnings());
        System.out.println("Spin the wheel! <press enter>");
        sc.nextLine();
        
        
        // Bonus Work: 
        // Loop to allow the same player to continue guessing if they guess 
        // a correct letter
        boolean correctGuess = true;
        do{
        
        System.out.println("<SPINNING>");
        Thread.sleep(200);
        Wheel.WedgeType type = wheel.spin();
        System.out.print("The wheel landed on: ");
        switch (type) {
            case MONEY:
                money = wheel.getAmount();
                System.out.println("$" + money);
                m = 1;
                break;
                
            case LOSE_TURN:
                System.out.println("LOSE A TURN");
                System.out.println("So sorry, you lose a turn.");
                return; // doesn't get to guess letter
                
                
            case BANKRUPT:
                System.out.println("BANKRUPT");
                player.bankrupt();
                return; // doesn't get to guess letter
                
            case PRIZE:
                System.out.println("Prize");
                m = 2;
                break;
                
            default:
                
        }
        
        // This part of the code will only run if the wheel landed on 
        // MONEY or PRIZE
            
        System.out.println("");
        System.out.println("Here is the puzzle:");
        showPuzzle();
        System.out.println();
        System.out.println(player.getName() + ", please guess a letter.");
        
        
        
            
        char letter = sc.next().charAt(0);
        if (!Character.isAlphabetic(letter)) {
            System.out.println("Sorry, but only alphabetic characters are allowed. You lose your turn.");
        }
        else {
            // search for letter to see if it is in
            int numFound = 0;
            for (Letter l : userPhrase) {
                if ((l.getLetter() == letter) || (l.getLetter() == Character.toUpperCase(letter)) ||
                        (l.getLetter() == Character.toLowerCase(letter))) {
                    l.setFound();
                    numFound += 1;
                }
            }
            if (numFound == 0) {
                System.out.println("Sorry, but there are no " + letter + "'s.");
                correctGuess = false;
            } else {
                if (numFound == 1) {
                    System.out.println("Congrats! There is 1 letter " + letter + ":");
                } else {
                    System.out.println("Congrats! There are " + numFound + " letter " + letter + "'s:");
                }
                System.out.println();
                showPuzzle();
                System.out.println();
                
                     


                // This if else statement will add the prize or money for the  
                // player depending on which wedge the wheel landed 
                // m = 1: MONEY
                if(m==1){
                    player.incrementScore(numFound*money);
                    System.out.println("You earned $" + (numFound*money) + ", and you now have: $" + player.getWinnings());   
                }
                // m = 2: PRIZE 
                else if(m==2){
                    PlayerPrizes();
                    Random randGen2 = new Random();
                    int num2 = randGen2.nextInt(playerPrize.size());
                    String prize = playerPrize.get(num2);
                    player.setPrizes(prize);
                    System.out.println("You won a: " + prize);
                }
                
                
                System.out.println("Would you like to try to solve the puzzle? (Y/N)");
                letter = sc.next().charAt(0);
                System.out.println();
                if ((letter == 'Y') || (letter == 'y')) {
                    solvePuzzleAttempt(player);
                    correctGuess = false;
                }
                
            }
            
            
        }
        
                
        }while(correctGuess);
        
    }
    
    /**
     * Logic for when user tries to solve the puzzle
     * @param player 
     */
    private void solvePuzzleAttempt(Player player) {
        
        
        if (player.getNumGuesses() >= 3) {
            System.out.println("Sorry, but you have used up all your guesses.");
            return;
        }
        
        player.incrementNumGuesses();
        System.out.println("What is your solution?");
        Scanner sc = new Scanner(System.in);
        sc.useDelimiter("\n");
        String guess = sc.next();
        if (guess.compareToIgnoreCase(phrase) == 0) {
            System.out.println("Congratulations! You guessed it!");
            puzzleSolved = true;
            // Round is over. Write message with final stats
            // TODO
            
            // 3E This will display the winner
            System.out.println(player.getName() + " is the Winner!");
            System.out.println();
            
            // This will display how much each player has earned/won
            for(int i=0; i< players.size(); i++){
                Player currentPlayer = players.get(i);
                
                // Display money won
                System.out.println(currentPlayer.getName() + " has earned $" + currentPlayer.getWinnings());
                
                // Display Prizes won
                if(currentPlayer.getPrizes().size() > 0){
                System.out.print(currentPlayer.getName() + " has also won a: ");
                System.out.println(currentPlayer.getPrizes());
                }
                puzzleSolved = true;
            }
            
        }
        else {
            System.out.println("Sorry, but that is not correct.");
            }
     
        
        
    }
    
    /**
     * Display the puzzle on the console
     */
    private void showPuzzle() {
        System.out.print("\t\t");
        for (Letter l : userPhrase) {
            if (l.isSpace()) {
                System.out.print("   ");
            } else {
                if (l.isFound()) {
                    System.out.print(Character.toUpperCase(l.getLetter()) + " ");
                } else {
                    System.out.print(" _ ");
                }
            }
        }
        System.out.println();
        
    }
    
    /**
     * For a new game reset player's number of guesses to 0
     */
    public void reset() {
        for(int i=0; i< players.size(); i++){
            players.get(i).reset();
        }
    }
    
    /**
     * 2B
     * This method has 10 different phrases that could come into play if the 
     * user does not want to enter their own phrase.
     */
    public void ListofPhrases(){
        ListPhrases.add("I hope to pass this class");    
        ListPhrases.add("Charlotte is in North Carolina");
        ListPhrases.add("Halfway through the semester");
        ListPhrases.add("UNCC is a good school");
        ListPhrases.add("I like hamburgers");
        ListPhrases.add("This is actully a pretty cool assignment");
        ListPhrases.add("Thinking of phrases on the spot is challenging");
        ListPhrases.add("Can I go on a vaction soon");
        ListPhrases.add("What research have you done in Machine Learning");
        ListPhrases.add("Is it summer yet");
    }
    
    /**
     * 5
     * This method holds the player prizes that could be won.
     */
    public void PlayerPrizes(){
        playerPrize.add("TV");
        playerPrize.add("Car");
        playerPrize.add("Computer");
        playerPrize.add("Cruise");
        playerPrize.add("Vacation");
        playerPrize.add("Pair of headphones");
    }
    
    
    /**
     * This method  was suppose to restart the game setting if the player decided 
     * to play another round. Couldn't get it to work
     */
    public void resetGame(){
        puzzleSolved = false;
        setUpGame();
        
    }
    
    
   

}
