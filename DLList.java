/******************************************************
 * @author Kenzie Clarke
 */
//****************************************************************
//
// DLList Class
//
//****************************************************************
class DLList<T>{


	//****************************************************************
	// DLList private data members
	//****************************************************************
	private DLListNode<T> front;
	private DLListNode<T> rear;
	private DLListNode<T> current;
	int size;
	int index;


	//****************************************************************
	//
	// DLListNode (inner) class
	//
	//****************************************************************
	private static class DLListNode<T>{
		//****************************************************************
		// constructor
		//****************************************************************
		DLListNode(T value){
			data = value;
			previous = null;
			next = null;
		}

		//****************************************************************
		// DLList node private data members
		//****************************************************************
		public T data;
		public DLListNode<T> previous;
		public DLListNode<T> next;


	}


	//****************************************************************
	//
	// constructor
	//
	// this is the base/default constructor
	//
	//****************************************************************
	// constructors
	public DLList(){
		front = null;
		rear = null;
		current= null;
		size = 0;
		index = -1;
	}


	//****************************************************************
	//
	// copy constructor
	//
	// this is the copy constructor
	//
	//****************************************************************
	public DLList(DLList<T> other){
		DLListNode<T>cur;
		cur = other.front;
		front = null;
		rear = null;
		current = null;
		size = 0;
		index= -1;
		while(cur!=null) {
			InsertLast(cur.data);
			cur = cur.next;
		}
		Seek(0);
	
	}


	//****************************************************************
	//
	// clear list method
	//
	// this method "clears" the list...deletes all nodes
	//****************************************************************
	public void Clear(){
		front = null;
		rear = null;
		current = null;
		size = 0;
		index = -1;
	}


	//****************************************************************
	//
	// list size method
	//
	// this method returns the size of the list
	//
	//****************************************************************
	public int Size(){
		return(size);
	}
	
	
	//****************************************************************
	//
	// list is empty method
	//
	// this method returns true/false if the list is empty
	// 
	//
	//****************************************************************
	public boolean IsEmpty(){
		return(size == 0);
	}

	
	//****************************************************************
	//
	// list current index method
	//
	// this method returns the current list index
	// initially set to -1
	//
	//****************************************************************
	public int CurrentIndex(){
		return(index);
	}


	//****************************************************************
	//
	// list at first method
	//
	// this list method returns true/false if the current index is at
	// the first/front node in the list
	// initially set to false
	//
	//****************************************************************
	public boolean AtFirst(){
		return(index == 0);
	}


	//****************************************************************
	//
	// list at last method
	//
	// this list method returns true/false if the current index is at
	// the first/front node in the list
	// initially set to false
	//
	//****************************************************************
	public boolean AtLast(){
		if(size > 0)
			return(current == rear);
		return false;
	}


	//****************************************************************
	//
	// list data read method
	//
	// this method returns a reference to the data field at the node 
	// referenced by "current"
	// this method returns null if the list is empty
	//
	//****************************************************************
	public T DataRead(){
		return(current.data);
	}


	//****************************************************************
	//
	// list data write method
	//
	// this method writes "x" into the data field at the node referenced
	// by "current"
	// this method returns the reference "x" if successful and
	// null otherwise
	//
	//****************************************************************
	public T DataWrite(T x) {
		return(current.data = x);
	}



	//****************************************************************
	//
	// list seek first node method
	//
	// this method moves the current reference to the first node
	// returns a boolean true/false if successful
	//
	//****************************************************************
	public boolean First(){
		return(Seek(0));
	}


	//****************************************************************
	//
	// list seek next node method
	//
	// this method moves the current reference to the next node and returns
	// true if successful
	// otherwise it returns false
	//
	//****************************************************************
	public boolean Next(){
		return(Seek((CurrentIndex() + 1)));
	}


	//****************************************************************
	//
	// list seek last node method
	//
	// this method moves the current pointer to the last node in the
	// list if successful
	// otherwise it returns false
	//
	//****************************************************************
	public boolean Last(){
		return(Seek(size - 1));
	}


	//****************************************************************
	//
	// list seek previous node method
	//
	// this method moves the current pointer to the previous node in the
	// list if successful
	// otherwise it returns false
	//
	//****************************************************************
	public boolean Previous(){
		return(Seek(CurrentIndex() - 1));
	}


	//****************************************************************
	//
	// list seek to node at location method
	//
	// this method "seeks" to the node in the list whose index is "loc"
	// example: if loc = 5, then the current reference will goto the 
	// 6th node in the list (indices start at 0)
	// returns true if successful and false otherwise
	//
	//****************************************************************
	public boolean Seek(int loc){
		if(IsEmpty())
			return false;
		else if((loc < 0) || (loc >= size))
			return false;
		else if(loc == 0) {
			current = front;
			index = 0;
			return true;
		}
		else if(loc == size - 1) {
			current = rear;
			index = size - 1;
			return true;
		}
		else if(loc < CurrentIndex()) {
			while(CurrentIndex() != loc) {
				current = current.previous;
				index--;
			}
			return true;
		}
		else if(loc > CurrentIndex()){
			while(CurrentIndex() != loc) {
			current = current.next;
			index++;
			}
			return true;
		}
		return(true);
	}

	//****************************************************************
	//
	// insert first method
	//
	// this method inserts "item" into a new "first" node in the list
	// current is pointing at the newly inserted first node
	// returns true if successful and false otherwise
	//
	//****************************************************************
	public boolean InsertFirst(T item){
		First();
		DLListNode<T> nn;
		nn = new DLListNode<T>(item);
		if(IsEmpty()) {
			front = nn;
			current = front;
			rear = nn;
			index = 0;
			size++;
			return true;
		}
		else {
			current = front;
			current.previous = nn;
			nn.next = current;
			front = nn;
			index = 0;
			size++;
			current = nn;
			return true;
		}
	}


	//****************************************************************
	//
	// insert at current location method
	//
	// this method inserts "item" a the "current" location in the list
	// example: InsertAt(50)
	// before:  0 1 2 3 4 5 6 7 8 9
	//          current --^
	// after:   0 1 2 3 4 50 5 6 7 8 9
	//          current --^
	// returns true if successful and false otherwise
	//
	//****************************************************************
	@SuppressWarnings("unused")
	public boolean InsertAt(T item){
		DLListNode<T> nn;
		nn = new DLListNode<T>(item);
		if(IsEmpty() || index == 0)
			return(InsertFirst(item));
		else if(index == size-1) {
			return(InsertLast(item));
		}
		else if(nn != null) {
			current.previous.next = nn;
			nn.previous = current.previous;
			nn.next = current;
			current.previous = nn;
			current = nn;
			size++;
			return true;
		}
		else
			return false;
	}


	//****************************************************************
	//
	// insert last method
	//
	// this method inserts "item" into a new "last" node in the list
	// current is pointing at the newly inserted last node
	// returns true if successful and false otherwise
	//
	//****************************************************************
	@SuppressWarnings("unused")
	public boolean InsertLast(T item){
		DLListNode<T> nn;
		if(IsEmpty())
			return(InsertFirst(item));
		else {
			Last();
			nn = new DLListNode<T>(item);
			if(nn != null) {
				nn.previous = rear;
				rear.next = nn;
				size++;
				index++;
				rear = nn;
				current = rear;
				return true;
			}
			return false;
		}
	}


	//****************************************************************
	//
	// delete first method
	//
	// this method deletes the first node in a list
	// current points to the following node or null if empty
	// returns true if delete was successful and false otherwise
	//
	//****************************************************************
	public boolean DeleteFirst(){
		First();
		if(current.next == null) {
			Clear();
			return true;
		}
		else {
			front = front.next;
			front.previous = null;
			current = front;
			index = 0;
			size--;
			return true;
		}
	}


	//****************************************************************
	//
	// delete at current position method
	//
	//****************************************************************
	public boolean DeleteAt(){
		if(index == size-1) {
			return(DeleteLast());
		}
		else if(index == 0)
			return(DeleteFirst());
		else {
			current.previous.next = current.next;
			current = current.next;
			current.previous = current.previous.previous;
			size--;
			return true;
		}
	}


	//****************************************************************
	//
	// delete last method
	//
	//****************************************************************
	public boolean DeleteLast(){
		if(rear.previous != null) {
			Last();
			rear = rear.previous;
			rear.next = null;
			current = rear;
			size--;
			index = size-1;
			return true;
		}
		else {
			Clear();
			return true;
		}
	}


	//****************************************************************
	//****************************************************************
}

