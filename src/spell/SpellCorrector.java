package spell;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

public class SpellCorrector implements ISpellCorrector
{
    private Words dictionary = new Words();
    public void useDictionary(String dictionaryFileName) throws IOException
    {
        File input = new File(dictionaryFileName);
        Scanner scan = new Scanner(input);
        while(scan.hasNext())
        {
            String temp = scan.next();
            dictionary.add(temp);
        }
        scan.close();
    }
    public void print()
    {
        System.out.println(dictionary.toString());
    }
    public boolean equal(SpellCorrector RHS)
    {
        return this.dictionary.equals(RHS.dictionary);
    }
    public String suggestSimilarWord(String inputWord)
    {
        String input = inputWord.toLowerCase();
        Set<String> possibleOne = new TreeSet<String>();
        if (dictionary.find(input) != null)     // if the same just return it
        {
            return input;
        }
        else
        {
            int maxFre = 0;
            String similar = "";
            oneDistance(input, possibleOne);    // find all words in one distance away
            for(String item: possibleOne)
            {
                ITrie.INode node = dictionary.find(item);
                if (node != null)   // Tree set is in ascending order
                {
                    if (node.getValue() > maxFre)   // if it has higher frequency in dictionary, use that word
                    {
                        similar = item;
                        maxFre = node.getValue(); // update highest frequency
                    }

                }
            }
            if (similar.equals(""))     // if nothing is found
            {
                maxFre = 0;
                Set<String> possibleTwo = new TreeSet<String>();
                for (String item: possibleOne)      // find second edit distance for each one distance words
                {
                    oneDistance(item, possibleTwo);
                }
                for (String item2 : possibleTwo)    // check all words in 2 edit distance away
                {
                    ITrie.INode node = dictionary.find(item2);
                    if (node != null)
                    {
                        if (node.getValue() > maxFre)
                        {
                            similar = item2;
                            maxFre = node.getValue();
                        }

                    }
                }
            }
            if (similar.equals(""))
            {
                return null;
            }
            return similar;
        }
    }
    public void oneDistance(String input, Set<String> possible)
    {
        // deletion distance
        for (int i = 0; i < input.length(); i++)
        {
            StringBuilder tempS = new StringBuilder(input);
            tempS.deleteCharAt(i);      // use string builder's delete function
            String del = tempS.toString();
            possible.add(del);
        }
        // transposition
        for (int i = 0; i < input.length()-1; i++)
        {
            char[] tempC = input.toCharArray(); // convert string to char array
            char temp = tempC[i];
            tempC[i] = tempC[i+1];
            tempC[i+1] = temp;
            String trans = new String(tempC); // put everything back to string
            possible.add(trans);
        }
        //alteration
        for (int i = 0; i < input.length(); i++)
        {
            for (int j = 'a'; j <= 'z'; j++)
            {
                char[] tempC = input.toCharArray();
                char temp = (char)j;
                tempC[i] = temp;
                String alter = new String(tempC);
                possible.add(alter);
            }
        }
        //insertion
        for (int i = 0; i < input.length() + 1; i++)
        {
            for (int j = 'a'; j <= 'z'; j++)
            {
                StringBuilder tempS = new StringBuilder(input);
                char temp = (char)j;
                tempS.insert(i,temp);       // use string builder's insert function
                String insert = tempS.toString();
                possible.add(insert);
            }
        }
    }

}
