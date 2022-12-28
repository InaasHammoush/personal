/**
 *The program generates and outputs one random sentence every three seconds
 *until it is halted (for example, by typing Control-C in the terminal
 *window where it is running).
 *The sentences are generated using lists of random words provided in the 
 *class, depending on a randomly generated number.
 */
public class MyRandomSentences {
	// the following arrays hold the different words used to
	// generate the sentences, categorized according to the
	// sentence structure and syntax.
	static final String[] conjunctions = {"and", "or", "but", "because"};

	static final String[] properNouns = {"Fred", "Jane", "Raymond Redington",
			"Richard Nixon", "Miss America", "Harry Potter", "Frodo Bagins"};

	static final String[] commonNouns = {"man", "woman", "fish", "elephant",
			"unicorn", "dragon", "flower", "bed"};

	static final String[] determiners = {"a", "the", "every", "some"};

	static final String[] adjectives = {"big", "tiny", "pretty", "bald", 
			"colorful", "sad", "smart"};

	static final String[] intransitiveVerbs = {"runs", "jumps", "talks", "sleeps",
			"goes", "eats"};

	static final String[] transitiveVerbs = {"loves", "hates", "sees", "knows",
			"looks for", "finds"};


	public static void main (String[] args) {
		while (true) {
			sentence(); // generate a random sentence
			System.out.println(".\n\n");
			try {
				Thread.sleep(5000); 
			}
			catch (InterruptedException e) {
			}
		}
	}

/**
 * This method generates and outputs a random sentence with a 40%
 * chance of recursively having a randomly generated sentence as
 * an extra part making it longer and longer.
 */
	static void sentence() {
		simpleSentence();
		// There is a 40% chance the if statement will execute
		if (Math.random() > 0.6) {
			System.out.print(randomItem(conjunctions) + " ");
			sentence();
		}
	}

	/**
	 * This method generates a random sentence by calling other methods
	 * that generate different parts of the sentence structure and syntax
	 */
	static void simpleSentence() {
		nounPhrase();
		verbPhrase();
	}

	/**
	 * This method randomly generates the noun part of the sentence depending
	 * on a randomly generated number.
	 */
	static void nounPhrase() {
		double i = Math.random();

		// There is a 50% chance for each possibility to be executed
		if (i < 0.5) {
			System.out.print(randomItem(properNouns) + " ");	
		}
		else {
			
			System.out.print(randomItem(determiners) + " ");

			do { 
				System.out.print(randomItem(adjectives) + " ");
			} while (Math.random() > 0.9); // There is only a 10% chance that
										// this loop will continue running
										// and generating new adjectives

			System.out.print(randomItem(commonNouns) + " ");
			
			if (Math.random() > 0.8) { //20% chance this extra part will be generated
				System.out.print("who ");
				verbPhrase();
			}
		}

	}

	/**
	 * This method randomly generates the verb part of the sentence depending
	 * on a randomly generated number.
	 */
	static void verbPhrase() {
		int i = (int)(4*Math.random());

		// There are equal chances for each possibility to be executed
		switch (i) {
		case 0:
			System.out.print(randomItem(intransitiveVerbs) + " ");
			break;
		case 1:
			System.out.print(randomItem(transitiveVerbs) + " ");
			nounPhrase();
			break;
		case 2:
			System.out.print("is ");
			System.out.print(randomItem(adjectives) + " ");
			break;
		default:
			System.out.print("believes that ");
			simpleSentence();
		}
	}

	/**
	 * This method takes an array as a parameter and returns a random
	 * element of this array.
	 * @param list: an array of strings
	 * @return a random string from the array given as a parameter
	 */
	static String randomItem(String[] list) {
		int i = (int)(list.length*Math.random());
		return list[i];
	}
}


