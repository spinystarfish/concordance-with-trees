import java.io.*;
import java.util.*;
/************************************************
 * @author Kenzie Clarke
 *
 *	Project 4: Concordance Tables with BSTrees 
 *	and AVL trees
 *	Professor: Dr. Michael Scherger
 *	Due date: November 14th, 2017
 *
 *
 *  Project4 class
 *  
 *  This class contains the main method for the 
 *  program execution as well as a few side methods
 *  that perform key operations like printing, 
 *  modifying input, and obtaining the AVL score.
 *	
 ***********************************************/
public class Project4 {
	static String alpha = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	static int numW = 0;
	static double score = 0;

	/*************************************************************
	 *  Main method
	 *  
	 *  This method opens a Scanner , a BS Tree, and an AVL tree.
	 *  Then proceeds to read through the document, removing any
	 *  non-letter input and inserting the word into the BSTree
	 *  and the AVLTree. Afterwards it calls the printer method
	 *  and checks the args[0] for the --dumptable argument. If true
	 *  than calls the dumptable method to print out the concordance
	 *  table. 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String [] args) throws IOException {
		Scanner input = new Scanner(System.in);
		BSTree<String> tree = new BSTree<String>();
		AVLTree avltree = new AVLTree();
		int count = 0;
		while(input.hasNextLine()) {
			StringTokenizer t = new StringTokenizer(input.nextLine());
			count++;
			while(t.hasMoreTokens()) {
				String word = t.nextToken();
				for (int ii = 0; ii < word.length(); ii++) {
					if (!alpha.contains(word.substring(ii, (ii + 1)))) {
						word = marksRemove(word);
						break;
					}
				}
				if (!word.equals("")) {
					tree.Insert(word, count);
					avltree.AlsoInsert(word);
					numW++;
				}

			}
		}
		printer(tree, avltree);
		if(args.length != 0) {
			if(args[0].equals("--dumptable"))
				dumpTable(tree);
		}
		input.close();
	}

	/***************************************************************
	 * Printer method
	 * 
	 * This method prints the Standard Output from this program,
	 * including the Number of words, Number of Unique Words, AVG 
	 * BSTree height, AVG AVLTree height, and the AVL score.
	 * @param tree
	 * @param tree2
	 */
	private static void printer(BSTree<String> tree, AVLTree tree2) {
		System.out.println("NUMBER OF WORDS: " + numW);
		System.out.println("NUMBER OF UNIQUE WORDS: " + tree.numUW);
		tree.traverse(tree.root);
		tree.avgH = tree.avgH/tree.numUW;
		System.out.println("AVERAGE BST HEIGHT: " + tree.avgH);
		tree2.traverse(tree2.root);
		tree2.avgH = tree2.avgH/tree.numUW;
		System.out.println("AVERAGE AVL HEIGHT: " + tree2.avgH);
		traverse(tree, tree2, tree.root);
		score = (score/tree.numUW);
		System.out.println("AVL SCORE: " + score);
	}

	/***************************************************
	 * Dumptable method
	 * 
	 * This method prints out the dumptable header and
	 * then calls the inOrder operation to complete the
	 * concordance table.
	 * @param tree
	 */
	private static void dumpTable(BSTree<String> tree) {
		int llength = tree.llength;
		System.out.print("WORD");
		for(int aa = 0; aa <= (llength - 4); aa++) {
			System.out.print(" ");
		}
		System.out.println("  | FREQUENCY | REFERENCES");
		System.out.print("====");
		for(int aa = 0; aa <= (llength - 4); aa++) {
			System.out.print("=");
		}
		System.out.println("==+===========+===========");
		tree.inOrder();
	}

	/**********************************************
	 * MarksRemove method
	 * 
	 * This method takes the token scanned from the
	 * input file and edits to to remove non-letter
	 * additions. Any puncuation and letters are 
	 * removed from the beginning and the end, while
	 * non-letter in the middle of a word cause a 
	 * loss of it and all pieces after it.
	 * @param word
	 * @return
	 */
	private static String marksRemove(String word) {
		word.trim();
		if(!alpha.contains(word.substring(0, 1))) {
			word = word.substring(1, word.length());
			if(word.equals(""))
				return word;
		}
		if(!alpha.contains(word.substring((word.length() - 1), word.length())))
			word = word.substring(0, (word.length() - 1));
		for(int ii = 0; ii < word.length(); ii++) {
			if(!alpha.contains(word.substring(ii, (ii+1)))) {
				word = word.substring(0, ii);
				break;
			}
		}
		return word;
	}
	
	/*******************************************************
	 * Traverse method
	 * 
	 * This method is used to traverse the BSTree and AVLTree
	 * for the heights of every node. The difference of the 
	 * two respectively is then added to the overall AVL score
	 * total (the score double).
	 * @param tree
	 * @param tree2
	 * @param bst
	 * @return
	 */
	public static BSTree<String>.BSTreeNode traverse(BSTree<String> tree, AVLTree tree2, BSTree<String>.BSTreeNode bst) {
		if(!tree.isempty(bst)) {
			if(bst.getLeft() != null) {
				traverse(tree, tree2, bst.getLeft());
			}
			double avl = tree2.height(tree2.binsearch(tree2.root, bst.getData())); 
			score = score + (tree.height(bst) - avl);
			if(bst.getRight() != null) {
				traverse(tree, tree2, bst.getRight());
			}
		}
		return null;
	}
}