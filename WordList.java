/**
* WordList.java
* Liu, Jeremy
* March 17, 2014
* Create a reference-based linked list by creating nodes that contain strings
* and a reference to the following node.
* This is a sorted linked list that automatically sorts the list
* lexicographically as new nodes are added
*/

import java.util.*;

/**
* WordNode class constructs a node that contains 2 variables:
* A String that identifies the node
* and a pointer to the next node in the list.
*/
class WordNode
{
	public String word; //Each WordNode contains a string for its "name"
	public WordNode next; //Each WordNode references the next node in the sequence
	
	/**
	* Constructs a new WordNode passing just a string argument, and setting
	* the next node to null. Usually used to start off an empty list or the end of the list.
	*/
	public WordNode(String newWord)
	{
		word = newWord;
		next = null; //if the node is the last node in the list, it points to null
	}
	
	/**
	* Constructs a new WordNode by passing 2 arguments:
	* A string containing the name of the node and the pointer to the next node in the list.
	*/
	public WordNode(String newWord, WordNode nextNode)
	{
		word = newWord;
		next = nextNode;
	}
}

/**
* WordList Class contains the methods needed alter the linked list of strings.
* Also contains the main method where the User can input values to the linked list.
*/
public class WordList
{
	private WordNode head = null; //beginning of list
	private int numWords; //number of words in the list
	
	public WordList()
	{
		numWords = 0;
		head = null;
	}
	
	/**
	* Identifies whether or not the list is empty
	*/
	public boolean isEmpty()
	{
		if (numWords == 0) 
		{
			return true;
		}
		else 
		{
			return false;
		}
	}
	
	/**
	* prints out the size of the list by looking at the numWords variable
	*/
	public int size()
	{
		return numWords;
	}
	
	/**
	* takes an integer as a parameter
	* Locates a specified node in the linked list by traversing the list
	* until the index specified was found
	*/
	private WordNode find(int index)
	{
		WordNode curr = head; //set starting position of the search to head
		for (int i = 0; i < index; i++) 
		{
			curr = curr.next;
		}
		return curr;
	}
	
	/**
	* Retrieves the word within a node by using the find method.
	*/
	public String get(int index) 
	{
		WordNode curr = find(index);
		String getWord = curr.word;
		return getWord;
	}
	
	/**
	* the add method identifies four different possibilities to adding new nodes:
	* 1. creating a head if the list is empty
	* 2. Adding a new head to the list
	* 3. Adding a node to the end of the list
	* 4. Adding a node in the middle of the list
	* The add method automatically sorts the new words lexicographically
	* and uses the above options to add the new WordNode.
	*/
	public void add(String word) 
	{
		if (this.isEmpty()) //adds first node if the list is originally empty.
		{
			WordNode newNode = new WordNode(word);
			head = newNode;
		}
		
		/**
		* Adds a new word to the head of the list if it comes earlier in the alphabet
		* than the previous head.
		*/
		else if (word.compareTo(this.get(0)) <= 0)
		{		
			WordNode newNode = new WordNode(word, head);
			head = newNode;
		}
		
		/**
		* Adds a new word to the end of the list (points to null)
		* if all the other words in the list precede the new word in the alphabet.
		*/
		else if (word.compareTo(this.get(numWords - 1)) >= 0)
		{
			WordNode prev = find(numWords - 1);
			WordNode newNode = new WordNode(word);
			prev.next = newNode;
		}
		
		/**
		* Adds a new word to the middle of the list by 
		* finding out which two nodes the new node has to be in between
		* making the new node point to the following node,
		* and making the previous node point to the new node.
		*/
		else
		{
			for (int i = 1; i < numWords; i++) 
			{
				if (word.compareTo(this.get(i)) <= 0)
				{
					WordNode prev = find(i - 1);
					WordNode newNode = new WordNode(word, prev.next);
					prev.next = newNode;
					break; //breaks out of the loop so it doesn't continue adding
				}
			}
		}
		numWords++; //increases the length of the list
	}
	
	/**
	* The remove method deletes a node by making nothing point to the specified node
	* thus marking it for garbage collection.
	*/
	public void remove(String word) 
	{
		/**
		* This section gets executed if the first list in the node should be removed
		*/		
		if (word.equals(this.get(0))) //removes the head node 
		{
			head = head.next; //makes the head the next node after head
			//nothing points to the previous head so it is marked for garbage collection.
			numWords--; //Decreases the length of numWords because a node was deleted.
		}
		else if (word.equals(this.get(numWords - 1)))
		{
			WordNode prev = find(numWords - 2); //Finds the second to last node
			prev.next = null; //makes the second to last node point to null
			//now nothing is pointing to the last node in the list and it is gone
			numWords--;
		}
		else
		{	
			for (int i = 0; i < numWords; i++) 
			{
				if (word.equals(this.get(i))) //matches the word to delete
				{
					WordNode prev = find(i - 1); //finds the node and goes one before it
					WordNode curr = prev.next; //curr is the node we want to delete
					prev.next = curr.next; //makes the previous node point to the node after the current one.
					//now nothing points to the current node, and it is marked as garbage
					numWords--;
					break; //breaks out of the loop so it doesn't continue removing
				}
			}
		}
	}
	
	/**
	* by making the head null, everything in the list becomes unreachable
	* and everything is marked for garbage collection.
	*/	
	public void removeAll()
	{
		head = null; 
		numWords = 0;
	}
	
	/**
	* main block of code that interacts with the user and calls on various methods
	* to insert nodes into the list and delete nodes.
	*/
	public static void main(String[] args)
	{
		Scanner scan = new Scanner(System.in);
		
		WordList wordList = new WordList();
		
		//Command prompt for the user
		System.out.println("\nPlease type your command:" +
			"\n\t>showlist - will display all the items in a list." +
			"\n\t>insert ... - will insert all of the words that you specify into the list." +
			"\n\t>delete ... - will delete all of the nodes that you specify after the command." +
			"\n\t>deleteall - will delete everything from the list." +
			"\n\t>end - exit the program.");
		
		String input = scan.nextLine(); //scans all of the user input
		
		String command = input.toLowerCase(); //converts the input string to all lowercase
		
		while (!command.equals("end")) //Allows user to continue adding commands until "end" is entered
		{
			 if (command.equals("showlist"))
			 {
				if(!wordList.isEmpty())
				{
					for (int i = 0; i < wordList.size(); i++) 
					{
						System.out.print(wordList.get(i) + " ");
						//traverses the list and prints out every node
					}
					System.out.println();
				}
				else
					System.out.println("List is empty!");
			}
			
			if (command.startsWith("insert ")) //space is considered to reduce confusion
			{
				/**
				* Creates a new scanner that scans the substring of everything
				* after the command "insert "
				* uses a delimiter (space) " " or (comma, space) ", "
				* to differentiate between words that are added.
				*/
				Scanner seperator1 = new Scanner(command.substring(7));
				seperator1.useDelimiter( " |, " );//reads until a space
				
				String value;
	
				while (seperator1.hasNext()) 
				{
					value = seperator1.next();
					wordList.add(value);
				}
				System.out.println("added!");
			}
			
			if (command.equals("insert")) //if nothing is entered after the insert this is called
			{
				System.out.println("Please add something to insert!");
			}
			
			if (command.startsWith("delete ")) //again space is considered
			{
				if (wordList.isEmpty()) 
				{
					System.out.println("There is nothing to delete!");
				}
				else 
				{
					/**
					* Creates a new scanner similar to the insert
					*/
					Scanner seperator2 = new Scanner(command.substring(7));
					seperator2.useDelimiter( " |, " );
					boolean isEqual = true;
					String value;
				
					while (seperator2.hasNext()) 
					{
						value = seperator2.next();
						wordList.remove(value);
					}
					System.out.println("Deleted words!");
				}
			}
			
			if (command.equals("delete")) //this is called if user doesn't enter anything to be deleted.
			{
				if (wordList.isEmpty()) 
				{
					System.out.println("There is nothing to delete!");
				}
				else 
				{
					System.out.println("Please write what you want to delete!");
				}
			}
			
			if (command.equals("deleteall")) //deletes everything in the list
			{
				wordList.removeAll();
				System.out.println("List was deleted!");
			}
			
			//Treats every other input as an unsupported command
			if (!command.equals("showlist") && !command.startsWith("insert") && 
					!command.equals("deleteall") && !command.startsWith("delete"))
			{
				System.out.println("This command is not supported.");
			}
			
			input = scan.nextLine(); //repeat the process until end is entered
			command = input.toLowerCase();
		}
	}
}	
	