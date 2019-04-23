package spell;


import java.io.IOException;

/**
 * A simple main class for running the spelling corrector. This class is not
 * used by the passoff program.
 */
public class Main {
	
	/**
	 * Give the dictionary file name as the first argument and the word to correct
	 * as the second argument.
	 */
	public static void main(String[] args) throws IOException {
		
		String dictionaryFileName = args[0];
		String inputWord = args[1];
		
		/**
		 * Create an instance of your corrector here
		 */
		SpellCorrector machine = new SpellCorrector();

		machine.useDictionary(dictionaryFileName);
		//machine.print();

//		SpellCorrector machine2 = new SpellCorrector();
//		machine2.useDictionary(dictionaryFileName);
//		machine2.useDictionary(dictionaryFileName);
//		System.out.println(machine.equal(machine2));

		String suggestion = machine.suggestSimilarWord(inputWord);
		if (suggestion == null) {
		    suggestion = "No similar word found";
		}
		
		System.out.println("Suggestion is: " + suggestion);
	}

}
