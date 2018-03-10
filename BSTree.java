
/************************************************
 * @author Kenzie Clarke
 *
 *	BSTree Class
 *
 *	This class is designed to store the input of
 *	a concordance table as a binary search tree.
 *
 ***********************************************/
public class BSTree<T> {
	public BSTreeNode root;
	int llength = -1;
	int numUW = 0;
	double avgH = 0;
	
	/*************************************************
	 * BSTreeNode class
	 * 
	 * This class constructs a BSTree unit(node) that 
	 * contains a left, right, and parent pointer with
	 * a DLList of line references, height and frequency
	 * values, as well as the T data variable.
	 */
	public class BSTreeNode{
		private T data;
		private int height;
		private BSTreeNode left;
		private BSTreeNode right;
		private BSTreeNode parent;
		public DLList<Integer> lineRef = new DLList<Integer>();
		private int freq;
		
		public BSTreeNode(T d) {
			data = d;
			left = null;
			right = null;
			parent = null;
			height = 0;
			freq = 1;
		}
		
		/***************************************
		 * BSTreeNode Getters and Setters
		 * 
		 * These do exactly what the title suggests.
		 * Except the frequency value has a 
		 * increment-Frequency method instead of
		 * a set-Frequency method that increases
		 * the frequency value by one.
		 * @return
		 */
		public T getData() {
			return data;
		}
		public void setData(T d) {
			data = d;
		}
		public BSTreeNode getLeft() {
			return left;
		}
		public void setLeft(BSTreeNode l) {
			left = l;
		}
		public BSTreeNode getRight() {
			return right;
		}
		public void setRight(BSTreeNode r) {
			right = r;
		}
		public BSTreeNode getParent() {
			return parent;
		}
		public void setParent(BSTreeNode p) {
			parent = p;
		}
		public int getHeight() {
			return height;
		}
		public void setHeight(int h) {
			height = h;
		}
		public int getFreq() {
			return freq;
		}
		public void increFreq() {
			freq = freq + 1;
		}
	}
	
	public BSTree() {
		root = null;
	}
	public BSTree(T d) {
		root = new BSTreeNode(d);
	}
	
	/************************************
	 * Good 'Ol isEmpty method
	 * 
	 * This public method calls the isempty
	 * method to determine if the tree is
	 * empty
	 * @return
	 */
	public boolean isEmpty() {
		return(root == null);
	}
	
	/*************************************
	 * isempty method
	 * 
	 * This method returns true if the input
	 * node is empty.
	 * @param bst
	 * @return
	 */
	protected boolean isempty(BSTreeNode bst) {
		return(bst == null);
	}
	
	/********************************
	 * inOrder method
	 * 
	 * This wrapper method calls the 
	 * inorder method with the input
	 * of root.
	 */
	public void inOrder() {
		inorder(root);
	}
	/*********************************************
	 * inorder method
	 * 
	 * This method performs an inorder traversal
	 * on the BSTree, while printing the remainder
	 * of the concordance table (word, frequency,
	 * line references, and proper table formatting
	 * @param bst
	 */
	private void inorder(BSTreeNode bst) {
		if(!isempty(bst)) {
			inorder(bst.getLeft());
			//Prints out concordance table row
			System.out.print(bst.getData());
			if(llength < 3) {
				for(int aa = 0; aa <= ((4 - ((String) bst.getData()).length())); aa++) {
					System.out.print(" ");
				}
			}
			else {
				for(int aa = 0; aa <= ((llength - ((String) bst.getData()).length()) + 1); aa++) {
					System.out.print(" ");
				}
			}
			//Prints out the frequency by checking how many digits the number is
			//and adding spaces accordingly to keep the table aligned.
			System.out.print(" |");
			String number = Integer.toString(bst.freq);
			int c = number.length();
			for(int aa = 1; aa <= (10 - c); aa++) {
				System.out.print(" ");
			}
			System.out.print(bst.freq + " | ");
			//Loops to print out the line references, followed by a comma unless the last one
			bst.lineRef.Last();
			for(int bb = 1; bb <= bst.lineRef.size; bb++) {
				System.out.print(bst.lineRef.DataRead());
				bst.lineRef.Previous();
				if(bb != bst.lineRef.Size())
					System.out.print(", ");
			}
			System.out.println("");
			inorder(bst.getRight());
		}
	}

	/*****************************************************
	 * BinSearch method
	 * 
	 * This wrapper method creates a new node and returns
	 * true if the binsearch method found a node with the
	 * parameter as data.
	 * @param data
	 * @return
	 */
	public boolean BinSearch(T data) {
		BSTreeNode val = binsearch(root, data);
		return(val != null);
	}
	/******************************************************
	 * binsearch method
	 * 
	 * This method recursively looks through the tree and
	 * compares the data of the node with it's parameter to
	 * see if the data is already present within the tree.
	 * Return true if so, else returns false.
	 * @param bst
	 * @param data
	 * @return
	 */
	public BSTreeNode binsearch(BSTreeNode bst, T data) {
		if(isempty(bst))
			return(bst);
		int temp = ((String) (data)).compareTo((String)bst.getData());
		if(temp == 0)
			return(bst);
		else if(temp <= 0) // we shall see
			return binsearch(bst.getLeft(), data);
		else
			return binsearch(bst.getRight(), data);
	}

	/*****************************************************
	 * Insert method
	 * 
	 * This wrapper method returns true if the data was 
	 * successfully entered into the true. False otherwise.
	 * @param d
	 * @param line
	 * @return
	 */
	public boolean Insert(T d, Integer line) {
		return (insert(root, d, line));
	}
	/*************************************************************
	 * insert method
	 * 
	 * This method creates a new node. Sets the new node to be
	 * the root if the tree is empty, or finds a leaf node to 
	 * insert it into otherwise - Returns true. Otherwise checks
	 * if the data is a repeat and adds the line to the list of
	 * data's ref numbers - returns true. Otherwise returns false.
	 * @param bst
	 * @param data
	 * @param line
	 * @return
	 */
	private boolean insert(BSTreeNode bst, T data, Integer line) {
		BSTreeNode nn = new BSTreeNode(data);
		BSTreeNode cur = bst;
		BSTreeNode prev = null;
		int temp;
		//if the root is null(iterative), then make nn the root, insert first reference and increment unique words
		if(bst == null) {
			root = nn;
			root.lineRef.InsertFirst(line);
			numUW++;
			return true;
		}
		if((nn != null) && (BinSearch(data) != true)) {
			numUW++;
			//sets the line reference number to the node's DLList
			nn.lineRef.InsertLast(line);
			//saves the length if the word is the longest that has been inserted
			if(llength < (((String) data).length()))
				llength = (((String) data).length());
			//traverses the tree until it finds the right spot to insert the node
			while(!isempty(cur)) {
				prev = cur;
				temp = ((String) data).compareTo((String)cur.getData());
				if(temp <= 0)
					cur = cur.getLeft();
				else
					cur = cur.getRight();
			}
			//adopts itself into the tree by declaring it's parent
			//Then tells the parent whether it is the left child or right
			nn.setParent(prev);
			temp = ((String) data).compareTo((String)prev.getData());
			if(isempty(prev))
				root = nn;
			else if(temp <= 0)
				prev.setLeft(nn);
			else
				prev.setRight(nn);
			return true;
		}
		if(BinSearch(data) == true) {
			binsearch(root, data).increFreq();
			if(IsThere(binsearch(root,data), line) == false) {
				binsearch(root, data).lineRef.InsertAt(line);
				binsearch(root, data).lineRef.Next();
			}
			return true;
		}
		return false;
}

	/*****************************************************
	 * height method
	 * 
	 * traverses the tree to determine the number of nodes
	 * along the longest path from the root node down to
	 * the farthest leaf node
	 * 
	 * @param BTreeNode<String> start
	 * @return int
	 */
	public int height(BSTreeNode start) {
		if (start == null)
			return 0;
		else {
			int lheight = height(start.left);
			int rheight = height(start.right);
	            
			if (lheight > rheight)
				return(lheight+1);
			else
				return(rheight+1); 
		}
	}
	
	/**************************************************
	 * IsThere method
	 * 
	 * Searches the node's line reference DLList for a
	 * line number. If it finds the line number, return
	 * true. Otherwise return false.
	 * @param node
	 * @param line
	 * @return
	 */
	public boolean IsThere(BSTreeNode node, int line) {
		node.lineRef.Last();
		for(int ii=0; ii <= node.lineRef.size; ii++) {
			if(node.lineRef.DataRead() == line)
				return true;
			node.lineRef.Previous();
		}
		return false;
	}
	/***********************************************
	 * traverse method
	 * 
	 * Filters through the BSTree and collects all
	 * the heights of the nodes into a single double
	 * variable. Used for Average BSTree Height.
	 * @param bst
	 * @return
	 */
	public BSTreeNode traverse(BSTreeNode bst) {
		if(!isempty(bst)) {
			if(bst.left != null) {
				traverse(bst.left);
			}
			avgH = avgH + height(bst);
			if(bst.right != null) {
				traverse(bst.right);
			}
		}
		return null;
	}

}