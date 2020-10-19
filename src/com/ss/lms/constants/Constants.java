package com.ss.lms.constants;

class Color {
	public static final String HEADER = "\033[95m";
	public static final String OKBLUE = "\033[94m";
	public static final String OKGREEN = "\033[92m";
	public static final String WARNING = "\033[93m";
	public static final String FAIL = "\033[91m";
	public static final String ENDC = "\033[0m";
	public static final String BOLD = "\033[1m";
	public static final String UNDERLINE = "\033[4m";
}

public final class Constants {
	
	private Constants() {
	}
	
	public static String getColor(String color, String text) {
		switch (color) {
		case "blue":
			return Color.OKBLUE + text + Color.ENDC;
		case "green":
			return Color.OKGREEN + text + Color.ENDC;
		case "red":
			return Color.FAIL + text + Color.ENDC;
		case "header":
			return Color.HEADER + text + Color.ENDC;
		default:
			return text;
		}
	}

	public static final String INCORRECT_INPUT = getColor("red",
			"\nInvalid selection. Try again or type 'quit' if you wish to quit.\n");
	public static final String QUIT_MESSAGE = getColor("green", "\nGood Bye.\n");
	public static final String PREVIOUS_MENU = getColor("blue", "Quit to previous menu");
	public static final String MAKE_SELECTION = "Please make a selection: ";
	public static final String LINE_BREAK = "---------------------------------------";
	public static final String MENU_LINE_BREAK = "=======================================";
	public static final String MENU = getColor("header", "Menu");
	public static final String NO_DATA = getColor("red", "No data available.");
	public static final String RETURNING = getColor("green", "\nReturning to previous menu.\n");

	// CRUD
	public static final String SUCCESSFUL_UPDATE = getColor("green", "\nSuccessfully updated.\n");
	public static final String FAILED_UPDATE = getColor("green", "\nFailed to update.\n");
	public static final String SUCCESSFUL_DELETE = getColor("green", "\nSuccessfully deleted.\n");
	public static final String FAILED_DELETE = getColor("green", "\nFailed to delete.\n");
	
	// driver
	public static final String WELCOME_MESSAGE = getColor("header",
			"Welcome to the GCIT Library Management\nSystem. Which category of user are you?");
	public static final String USER_TYPES = "1) " + Color.OKBLUE + "Librarian\n" + Color.ENDC + "2) " + Color.OKBLUE
			+ "Administrator\n" + Color.ENDC + "3) " + Color.OKBLUE + "Borrower" + Color.ENDC;

	// librarian
	public static final String ENTER_BRANCH = getColor("blue", "Enter Branch you manage");
	public static final String[] LIB1_OPTIONS = new String[] {ENTER_BRANCH};
	public static final String UPDATE_LIBRARY = getColor("blue", "Update the details of the Library");
	public static final String ADD_COPIES = getColor("blue", "Add Copies of a Book to the Branch");
	public static final String[] LIB3_OPTIONS = new String[] {UPDATE_LIBRARY, ADD_COPIES};
	public static final String SELECT_BRANCH = "Select a Branch\n" + LINE_BREAK;
	public static final String PICK_BOOK = "\nPick the Book you want to add copies of";
	public static final String EXISTING_NUM_COPIES = "\nExisting number of copies: ";
	public static final String ENTER_NEW_NUM_COPIES = "Enter new number of copies: ";
	public static final String NEW_BRANCH_NAME = "Please enter new branch name or enter N/A for no change: ";
	public static final String NEW_BRANCH_ADDRESS = "Please enter new branch address or enter N/A for no change: ";
	
	// borrower
	public static final String CARD_NUMBER_PROMPT = getColor("blue", "Enter your card number: ");
	public static final String TRY_AGAIN = getColor("red", "\nPlease try a different number.\n");
	// BORR1
	public static final String CHECKOUT = getColor("blue", "Check out a book");
	public static final String RETURN = getColor("blue", "Return a book");
	public static final String[] MAIN_BORROWER_OPTIONS = new String[] {CHECKOUT, RETURN};
	public static final String CHECKOUT_BRANCH = getColor("blue",
			"\nPick the branch you want to check out from: ");
	public static final String CHECKOUT_BOOK = getColor("blue", "Pick the book you want to check out:");
	public static final String RETURN_BRANCH = getColor("blue",
			"\nPick the branch you want to return a book to: ");
	public static final String RETURN_BOOK = getColor("blue", "Pick the book you want to return: ");
	public static final String ALREADY_BORROWED = getColor("red", "You've already borrowed that book!\n");
	public static final String NO_COPIES = getColor("red", "Sorry, we don't currently have copies of that book.\n");


	// administrator
	//// menus
	public static final String MENU_ONE = "Book and Author Menu";
	public static final String MENU_TWO = "Genre Menu";
	public static final String MENU_THREE = "Publisher Menu";
	public static final String MENU_FOUR = "Library Branch Menu";
	public static final String MENU_FIVE = "Borrower Menu";
	public static final String MENU_SIX = "XXXX";
	//// driver function
	public static final String AUD_BOOK_AND_AUTHOR = getColor("blue", "Add/Update/Delete/Read Book and Author");
	public static final String AUD_GENRES = getColor("blue", "Add/Update/Delete/Read Genre");
	public static final String AUD_PUBLISHERS = getColor("blue", "Add/Update/Delete/Read Publishers");
	public static final String AUD_LIBRARY_BRANCHES = getColor("blue", "Add/Update/Delete/Read Library Branches");
	public static final String AUD_BORROWERS = getColor("blue", "Add/Update/Delete/Read Borrowers");
	public static final String OVERRIDE_DUEDATE = getColor("blue", "Override Due Date for a Book Loan");
	public static final String[] MAIN_ADMIN_OPTIONS = new String[] {AUD_BOOK_AND_AUTHOR, AUD_GENRES, AUD_PUBLISHERS, AUD_LIBRARY_BRANCHES, AUD_BORROWERS, OVERRIDE_DUEDATE};
	//// aud book and author
	public static final String ADD_BOOK_AND_AUTHOR = getColor("blue", "Add Book and Author");
	public static final String UPDATE_BOOK_AND_AUTHOR = getColor("blue", "Update Book and Author");
	public static final String DELETE_BOOK_AND_AUTHOR = getColor("blue", "Delete Book and Author");
	public static final String READ_BOOK_AND_AUTHOR = getColor("blue", "Read Books and Authors");
	public static final String[] ADMIN_BOOK_AND_AUTHOR_OPTIONS = new String[] {ADD_BOOK_AND_AUTHOR,UPDATE_BOOK_AND_AUTHOR,DELETE_BOOK_AND_AUTHOR, READ_BOOK_AND_AUTHOR};
	public static final String ADD_MORE_AUTHORS = getColor("header", "Add another author?\n")+"1) "+getColor("blue","Yes\n")+"2) "+getColor("blue","No");
	public static final String ADD_BY_BRANCH = getColor("blue", "Add by branch");
	public static final String ADD_WOUT_BRANCH = getColor("blue", "Add without specifying a branch");
	public static final String[] ADD_OPTIONS = new String[] { ADD_BY_BRANCH, ADD_WOUT_BRANCH};

	//// aud genre
	public static final String ADD_GENRE = getColor("blue", "Add a genre");
	public static final String UPDATE_GENRE = getColor("blue", "Update a genre");
	public static final String DELETE_GENRE = getColor("blue", "Delete a genre");
	public static final String READ_GENRES = getColor("blue", "Read genres");
	public static final String[] GENRE_OPTIONS = new String[] {ADD_GENRE, UPDATE_GENRE, DELETE_GENRE, READ_GENRES};
	public static final String NEW_GENRE_NAME = "Please enter new genre name or enter N/A for no change: ";
	
	//// aud branches
	public static final String NOT_APPLICABLE = "N/A";
	public static final String ADD_BRANCH = getColor("blue", "Add a branch");
	public static final String UPDATE_BRANCH = getColor("blue", "Update a branch");
	public static final String DELETE_FROM_BRANCH = getColor("blue", "Delete a branch");
	public static final String READ_BRANCHES = getColor("blue", "Read branches");
	public static final String[] ADMIN_BRANCH_OPTIONS = new String[] {ADD_BRANCH,UPDATE_BRANCH,DELETE_FROM_BRANCH, READ_BRANCHES};

	// aud publishers
	public static final String ADD_PUBLISHER = getColor("blue", "Add a publisher");
	public static final String UPDATE_PUBLISHER = getColor("blue", "Update a publisher");
	public static final String DELETE_PUBLISHER = getColor("blue", "Delete a publisher");
	public static final String READ_PUBLISHERS = getColor("blue", "Read publishers");
	public static final String[] PUBLISHER_OPTIONS = new String[] {ADD_PUBLISHER, UPDATE_PUBLISHER, DELETE_PUBLISHER, READ_PUBLISHERS};
	public static final String NEW_PUBLISHER_NAME = "Please enter new publisher name or enter N/A for no change: ";
	public static final String NEW_PUBLISHER_ADDRESS = "Please enter new address name or enter N/A for no change: ";
	public static final String NEW_PUBLISHER_PHONE = "Please enter new publisher phone or enter N/A for no change: ";

  // aud borrowers
	public static final String ADD_BORROWER = getColor("blue", "Add a Borrower");
	public static final String UPDATE_BORROWER = getColor("blue", "Update a Borrower");
	public static final String DELETE_BORROWER = getColor("blue", "Delete a Borrower");
	public static final String READ_BORROWERS = getColor("blue", "Read a Borrowers");
	public static final String[] AUD_BORROWER_OPTIONS = new String[] {ADD_BORROWER, UPDATE_BORROWER, DELETE_BORROWER, READ_BORROWERS};
	
	public static final String NEW_BORROWER_NAME = "Please enter new borrower name or enter N/A for no change: ";
	public static final String NEW_BORROWER_ADDRESS = "Please enter new address or enter N/A for no change: ";
	public static final String NEW_BORROWER_PHONE = "Please enter new borrower phone or enter N/A for no change: ";

}
