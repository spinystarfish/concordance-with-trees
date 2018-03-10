/**********************************************
 * AVLTree class
 * 
 * This class extends the BSTree class, with 
 * added functionality like hasLeft, hasRight,
 * and rotation methods. Contains a unique 
 * insert method.
 * @author kenzieclarke
 *
 */
public class AVLTree extends BSTree<String> {
	
	/***************************************
	 * getHeight method
	 * 
	 * Returns -1 if the node doesn't exist.
	 * Otherwise returns the stored height 
	 * data field of the node.
	 * @param node
	 * @return
	 */
	public int getHeight(BSTreeNode node) {
		if(node == null) {
			return -1;
		}
		else
			return(node.getHeight());
	}
	/***************************************
	 * hasLeft and hasRight methods
	 * 
	 * return true if the node's Left/Right
	 * pointer is not null. False Otherwise.
	 * @param node
	 * @return
	 */
	public boolean hasLeft(BSTreeNode node) {
		return(node.getLeft() != null);
	}
	public boolean hasRight(BSTreeNode node) {
		return(node.getRight() != null);
	}
	
	/****************************************
	 * AlsoInsert
	 * 
	 * This wrapper method sets the root to 
	 * be the new tree after a node has be 
	 * inserted.
	 * @param d
	 */
	public void AlsoInsert(String d) {
			root = alsoinsert(root, d);
	}
	/*************************************************************
	 * alsoinsert method
	 * 
	 * This method recursively inserts a node into the AVLTree,
	 * checking where to insert (left or right) and also checks if
	 * the tree is balanced according to the AVLTree property. If
	 * not, it calls the appropriate method to rotate the tree.
	 * Returns the parameter node that contains the balanced tree
	 * with the data field inserted as a node.
	 * @param avl
	 * @param data
	 * @return
	 */
	public BSTreeNode alsoinsert(BSTreeNode avl, String data) {
		if (avl == null) {
			avl = new BSTreeNode(data);
			
		}
		else {
			int temp = (data.compareTo((String)avl.getData()));
			if (temp < 0) {
				avl.setLeft(alsoinsert(avl.getLeft(), data));
				if((getHeight(avl.getLeft()) - getHeight(avl.getRight())) == 2) {
					int ltemp = (data.compareTo((String)(avl.getLeft().getData())));
					if (ltemp < 0)
						avl = rwlc(avl);
					else
						avl = dwlc(avl);
				}
			}
			else if (temp > 0) {
				avl.setRight(alsoinsert(avl.getRight(), data));
				 if ((getHeight(avl.getRight()) - getHeight(avl.getLeft())) == 2) {
					int rtemp = (data.compareTo((String)(avl.getRight().getData())));
					if (rtemp > 0)
						avl = rwrc(avl);
					else
						avl = dwrc(avl);
				}
			}
			else
				;
		}
		avl.setHeight(Math.max(getHeight(avl.getLeft()), getHeight(avl.getRight())) + 1);
		return avl;
	}
	/***************************************************************
	 * rwrc method
	 * 
	 * The Rotation with Right Child method takes a tree that is 
	 * heavier on the right subtree and rotates to the left to 
	 * balance it.
	 * @param k1
	 * @return
	 */
	public BSTreeNode rwrc(BSTreeNode k1) {
		BSTreeNode k2 = k1.getRight();
		k1.setRight(k2.getLeft());
		k2.setLeft(k1);
		k1.setHeight(Math.max(getHeight(k1.getLeft()), getHeight(k1.getRight())) + 1);
		k2.setHeight(Math.max(getHeight(k2.getRight()), getHeight(k1)) + 1);

		return k2;
	}
	/***************************************************************
	 * rwlc method
	 * 
	 * The Rotation with Left Child method takes a tree that is 
	 * heavier on the left subtree and rotates to the right to 
	 * balance it.
	 * @param k1
	 * @return
	 */
	public BSTreeNode rwlc(BSTreeNode k2) {
		BSTreeNode k1 = k2.getLeft(); 
		k2.setLeft(k1.getRight());
		k1.setRight(k2);
		k2.setHeight(Math.max(getHeight(k2.getLeft()), getHeight(k2.getRight())) + 1);
		k1.setHeight(Math.max(getHeight(k1.getLeft()), getHeight(k2)) + 1);

		return k1;
	}
	/***************************************************************
	 * dwrc method
	 * 
	 * The Double Rotation with Right Child method calls a rwlc
	 * rotation on the right subtree of the node, then calls a rwrc
	 * rotation on the parameter node.
	 * @param k1
	 * @return
	 */
	public BSTreeNode dwrc(BSTreeNode k1) {
		k1.setRight(rwlc(k1.getRight()));
		return(rwrc(k1));
	}
	/***************************************************************
	 * dwlc method
	 * 
	 * The Double Rotation with Left Child method calls a rwrc
	 * rotation on the left subtree of the node, then calls a rwlc
	 * rotation on the parameter node.
	 * @param k1
	 * @return
	 */
	public BSTreeNode dwlc(BSTreeNode k3) {
		k3.setLeft(rwrc(k3.getLeft()));
		return(rwlc(k3));
	}

}
