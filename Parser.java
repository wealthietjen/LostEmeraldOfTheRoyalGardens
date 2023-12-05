// Wealthie Tjendera K22046226

/**
 * This class is part of the "Lost Emerald of the Royal Gardens" application. 
 * "Lost Emerald of the Royal Gardens" is a very simple, text based adventure game.  
 * 
 * This parser reads user input and tries to interpret it as an "Adventure"
 * command. Every time it is called it reads a line from the terminal and
 * tries to interpret the line as a two word command. It returns the command
 * as an object of class Command.
 *
 * The parser has a set of known command words. It checks user input against
 * the known commands, and if the input is not one of the known commands, it
 * returns a command object that is marked as an unknown command.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */

import java.util.Scanner;

public class Parser 
{
    private CommandWords commands;  // holds all valid command words
    private Scanner reader;         // source of command input

    /**
     * Create a parser to read from the terminal window.
     */
    public Parser() 
    {
        commands = new CommandWords();
        reader = new Scanner(System.in);
    }

    /**
     * @return The next command from the user.
     */
    public Command getCommand() 
    {
        String inputLine;   // will hold the full input line
        String word1 = null;
        String word2 = null;
        String word3 = null;

        System.out.print("> ");     // print prompt

        inputLine = reader.nextLine();

        // Find up to three words on the line.
        Scanner tokenizer = new Scanner(inputLine);
        if(tokenizer.hasNext()) {
            word1 = tokenizer.next();      // get first word
            if(tokenizer.hasNext()) {
                word2 = tokenizer.next();      // get second word
                if(tokenizer.hasNext()) {
                    word3 = tokenizer.next();      // get third word
                    // note: we just ignore the rest of the input line.
                }
            }
        }

        if (word1 != null) 
        {
            word1 = word1.trim().toLowerCase();    
        }
        
        if (word2 != null) 
        {
            word2 = word2.trim().toLowerCase();
        }
        
        if (word3 != null) 
        {
            word3 = word3.trim().toLowerCase();    
        }
        
        // Now check whether this word is known. If so, create a command
        // with it. If not, create a "null" command (for unknown command).
        if (word3 != null)
        {
            word2 += " ";
            word2 += word3;
        }
        if(commands.isCommand(word1)) {
            return new Command(word1, word2);
        }
        else {
            return new Command(null, word2); 
        }
    }

    /**
     * Print out a list of valid command words.
     */
    public void showCommands()
    {
        commands.showAll();
    }
}
