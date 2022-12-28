import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import javax.swing.JFileChooser;

/**
 * This class is a spell checker that takes a file as input and compares all words 
 * to a dictionary on the user's pc and displays to the output stream a list of all 
 * misspelled words with a list of correct suggestions for each one.
 * precondition: the dict must exist on the device and the path should be correct
 * @author 
 *
 */
public class SpellChecker {
	/**
	 * this static variable is used to store the dictionary that is used to 
	 * spell-check the input files.
	 */
	static HashSet<String> dict = new HashSet<>();
	
	
	public static void main (String[] args) {
		File dictFile = new File("D:\\words.txt"); //the path of the dictionary file
		try {
			Scanner filein = new Scanner(dictFile);
			while (filein.hasNext()) {
				String w = filein.next();
				w = w.toLowerCase();
				dict.add(w);
			}
			filein.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		
		File inputFile = getInputFileNameFromUser(); // get the file to be checked from user
		
		
		HashSet<String> misspelledWords = new HashSet<>(); //used to store the found misspelled words.
		try {
			Scanner reader = new Scanner(inputFile);
			reader.useDelimiter("[^a-zA-Z]+");
			while (reader.hasNext()) {
				String w = reader.next();
				w = w.toLowerCase();
				if (!dict.contains(w)) {
					misspelledWords.add(w);
				}
			}
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		System.out.println("Here is a list of wrong words and some suggestions for their corrections:");
		
		for (String word : misspelledWords) {
			TreeSet<String> correct = corrections(word, dict);//a set to store suggested corrections in order
			if (correct.isEmpty()) {
				System.out.println(word + ": " + "(No suggestions)");
			} else {
				System.out.println(word + ": " + correct);
			}
		}
	}
	
	

	/**
	 * Lets the user select an input file using a standard file
	 * selection dialog box.  If the user cancels the dialog
	 * without selecting a file, the return value is null.
	 */
	static File getInputFileNameFromUser() {
		JFileChooser fileDialog = new JFileChooser();
		fileDialog.setDialogTitle("Select File for Input");
		int option = fileDialog.showOpenDialog(null);
		if (option != JFileChooser.APPROVE_OPTION)
			return null;
		else
			return fileDialog.getSelectedFile();
	}

	
	/**
	 * Method takes a misspelled word and a dictionary and tests by changing or inserting letters
	 * to find suggested correct spellings.
	 * @param badWord a misspelled word
	 * @param dictionary a reference to a text file that contains a dictionary to check suggestions
	 * @return a TreeSet of suggested correct spellings of the misspelled word.
	 */
	static TreeSet<String> corrections (String badWord, HashSet<String> dictionary) {
		
		TreeSet<String> corrections = new TreeSet<>(); // the set to be returned
		
		
		for (int i = 0; i < badWord.length(); i++) { // this loop will contain three different tests
			
			//first we test by deleting each letter at a time
			String suggested = badWord.substring(0,i) + badWord.substring(i+1);
			if (dictionary.contains(suggested)) {
				corrections.add(suggested);
			}

			//now we test by changing any letter to any other letter
			for (char ch = 'a'; ch <= 'z'; ch++)  {
				suggested = badWord.substring(0, i) + ch + badWord.substring(i+1);
				if (dictionary.contains(suggested)) {
					corrections.add(suggested);
				}
			}

			//now we test by inserting any letter in any position
			for (char ch = 'a'; ch <= 'z'; ch++)  {
				suggested = badWord.substring(0,i) + ch + badWord.substring(i);
				if (dictionary.contains(suggested)) {
					corrections.add(suggested);
				}
				suggested = badWord + ch; //this is to test by inserting letters at the end 
				if (dictionary.contains(suggested)) {
					corrections.add(suggested);
				}
			}

		} // end of first for loop

		
		for (int i = 0; i < badWord.length()-1; i++) { //This loop will contain 2 tests
			
			//now we test by swapping any neighboring letters
			int j = i + 1;
			String suggested = badWord.substring(0,i) + badWord.charAt(j) + badWord.charAt(i) 
			                                                         + badWord.substring(j+1);
			if (dictionary.contains(suggested)) {
				corrections.add(suggested);
			}

			// now we test by inserting a space at any point in the bad word
			String firstPart = badWord.substring(0,i+1);
			String secondPart = badWord.substring(i+1);
			suggested = firstPart + " " + secondPart;
			if (dictionary.contains(firstPart) & dictionary.contains(secondPart)) {
				corrections.add(suggested);
			}
		}
		return corrections;
	}
}
