package com.ss.lms.application;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

import com.ss.lms.constants.Constants;
import com.ss.lms.entity.Author;
import com.ss.lms.entity.Book;
import com.ss.lms.entity.Borrower;
import com.ss.lms.entity.Branch;
import com.ss.lms.entity.Genre;
import com.ss.lms.entity.Loan;
import com.ss.lms.entity.Publisher;
import com.ss.lms.service.AdministratorService;

public class AdministratorMenu extends BaseUserMenu implements Callable<Boolean> {
	
	private AdministratorService service;
	
	AdministratorMenu(Scanner scan, Integer menu) {
		super("Administrator", scan, menu);
		service = new AdministratorService();
	}

	AdministratorMenu(Scanner scan, Integer menu, AdministratorService service) {
		super("Administrator", scan, menu);
		this.service = service;
	}
	
	@Override
	public Boolean call() throws Exception {
		switch (getMenu()) {
			case 1:
				printSubMenu(Constants.MENU_ONE);
				return audrBookAndAuthor();
			case 2:
				printSubMenu(Constants.MENU_TWO);
				return audrGenre();
			case 3:
				printSubMenu(Constants.MENU_THREE);
				return audrPublisher();
			case 4:
				printSubMenu(Constants.MENU_FOUR);
				return audrLibraryBranch();
			case 5:
				printSubMenu(Constants.MENU_FIVE);
				return audrBorrower();
			case 6:
				return overrideDueDate();
			default:
				return true;
		}
	}
	
	
	public Loan getLoanSelection(List<Loan> loanObjects) {
		List<String> loans = loanObjects.stream().map(loan -> loan.toString()).collect(Collectors.toList());;
		int selection = promptOptions(loans);
		if (selection == -1) {
			return new Loan(-1, -1, -1, null, null, null);
		} else if (selection == 0) {
			return new Loan(0, 0, 0, null, null, null);
		}
		return loanObjects.get(selection - 1);
	}

	public Borrower getBorrowerSelection(List<Borrower> borrowerObjects) {
		List<String> borrowers = borrowerObjects.stream().map(borrower -> borrower.toString()).collect(Collectors.toList());;
		int selection = promptOptions(borrowers);
		if (selection == -1) {
			return new Borrower(-1, null, null, null);
		} else if (selection == 0) {
			return new Borrower(0, null, null, null);
		}
		return borrowerObjects.get(selection - 1);
	}

	public Publisher getPublisherSelection(List<Publisher> publisherObjects) {
		List<String> publishers = publisherObjects.stream().map(publisher -> publisher.toString()).collect(Collectors.toList());;
		int selection = promptOptions(publishers);
		if (selection == -1) {
			return new Publisher(-1, null, null, null);
		} else if (selection == 0) {
			return new Publisher(0, null, null, null);
		}
		return publisherObjects.get(selection - 1);
	}
	
	public Genre getGenreSelection(List<Genre> genreObjects) {
		List<String> genres = genreObjects.stream().map(genre -> genre.toString()).collect(Collectors.toList());;
		int selection = promptOptions(genres);
		if (selection == -1) {
			return new Genre(-1, null);
		} else if (selection == 0) {
			return new Genre(0, null);
		}
		return genreObjects.get(selection - 1);
	}

	public Author getAuthorSelection(List<Author> authorObjects) {
		List<String> authors = authorObjects.stream().map(author -> author.toString()).collect(Collectors.toList());;
		int selection = promptOptions(authors);
		if (selection == -1) {
			return new Author(-1, null);
		} else if (selection == 0) {
			return new Author(0, null);
		}
		return authorObjects.get(selection - 1);
	}

	public List<Genre> updateGenres(Book book) {
		List<Genre> updatedGenreList;
		if (book.getGenres() == null) {
			updatedGenreList = new ArrayList<>();
		} else {
			updatedGenreList = book.getGenres();
		}
		
		boolean doneUpdating = false;
		do {
			printSubMenu("How would you like to modify " + Constants.getColor("blue", book.getTitle()) + "'s genres?");
			String[] genreOpts = new String[] {"Add genres", "Remove genres"};
			int genreUpdtType = promptOptions(Arrays.asList(genreOpts));
			if (genreUpdtType == -1 || genreUpdtType == 0) {
				break;
			}
			Genre selectedGenre;
			switch(genreUpdtType) {
				case 1: /* Add genres */
					printSubMenu("Select an genre to add to your book.");
					selectedGenre = getGenreSelection(service.getGenres(null));
					if (selectedGenre.getGenreId() == -1) {
						doneUpdating = true;
						break;
					} else if (selectedGenre.getGenreId() == 0) {
						break;
					}
					updatedGenreList.add(selectedGenre);
					break;
				case 2: /* Remove genres */
					printSubMenu("Select an genre to remove from your book.");
					selectedGenre = getGenreSelection(updatedGenreList);
					if (selectedGenre.getGenreId() == -1) {
						doneUpdating = true;
						break;
					} else if (selectedGenre.getGenreId() == 0) {
						break;
					}
					updatedGenreList.remove(selectedGenre);
					break;
			}
			if (doneUpdating == false) {
				printSubMenu("Continue modifying " + Constants.getColor("blue", book.getTitle()) + "'s genres?");
				int contModifying = promptOptions(Arrays.asList("Yes", "No"));
				if (contModifying != 1) {
					doneUpdating = true;
				}
			}
		} while (doneUpdating == false);
		return updatedGenreList;
	}
	public List<Author> updateAuthors(Book book) {
		List<Author> updatedAuthorList;
		if (book.getAuthors() == null) {
			updatedAuthorList = new ArrayList<>();
		} else {
			updatedAuthorList = book.getAuthors();
		}
		
		boolean doneUpdating = false;
		do {
			printSubMenu("How would you like to modify " + Constants.getColor("blue", book.getTitle()) + "'s authors?");
			String[] authorOpts = new String[] {"Add authors", "Remove authors"};
			int authorUpdtType = promptOptions(Arrays.asList(authorOpts));
			if (authorUpdtType == -1 || authorUpdtType == 0) {
				break;
			}
			Author selectedAuthor;
			switch(authorUpdtType) {
				case 1: /* Add authors */
					printSubMenu("Select an author to add to your book.");
					selectedAuthor = getAuthorSelection(service.getAuthors(null));
					if (selectedAuthor.getAuthorId() == -1) {
						doneUpdating = true;
						break;
					} else if (selectedAuthor.getAuthorId() == 0) {
						break;
					}
					updatedAuthorList.add(selectedAuthor);
					break;
				case 2: /* Remove authors */
					printSubMenu("Select an author to remove from your book.");
					selectedAuthor = getAuthorSelection(updatedAuthorList);
					if (selectedAuthor.getAuthorId() == -1) {
						doneUpdating = true;
						break;
					} else if (selectedAuthor.getAuthorId() == 0) {
						break;
					}
					updatedAuthorList.remove(selectedAuthor);
					break;
			}
			if (doneUpdating == false) {
				printSubMenu("Continue modifying " + Constants.getColor("blue", book.getTitle()) + "'s authors?");
				int contModifying = promptOptions(Arrays.asList("Yes", "No"));
				if (contModifying != 1) {
					doneUpdating = true;
				}
			}
		} while (doneUpdating == false);
		return updatedAuthorList;
	}

	public boolean audrBookAndAuthor() {
		int selection;
		do {
			selection = promptOptions(Arrays.asList(Constants.ADMIN_BOOK_AND_AUTHOR_OPTIONS));
			if (selection == -1) {
				return false;
			} else if (selection == 0) {
				return true;
			}

			switch(selection) {
				case 1: /* ADD */
					printSubMenu("Add a Book and Author");
					// Add by branch || Add generally 
					Integer addByBranch = promptOptions(Arrays.asList(Constants.ADD_OPTIONS));
					if (addByBranch == -1) {
						return false;
					} else if (addByBranch == 0) {
						break;
					}
					// Get new book title
					String newBookTitle = "";
					do {
						System.out.print("Enter new book title: ");
						newBookTitle = scan.nextLine();
						if (newBookTitle.equals("quit")) {
							return false;
						} else if (newBookTitle.length() > 0) {
							break;
						}
						System.out.println(Constants.INCORRECT_INPUT);
					} while (true);

					// Get author selections
					List<Author> authorSelections = updateAuthors(new Book(null, newBookTitle));
					// Get genre selections
					List<Genre> genreSelections = updateGenres(new Book(null, newBookTitle));

					// Get publisher selection
					printSubMenu("Select a Publisher to add to your book.");
					Publisher selectedPublisher = getPublisherSelection(service.getPublishers(null));
					if (selectedPublisher.getPublisherId() == -1) {
						return false;
					} else if (selectedPublisher.getPublisherId() == 0) {
						break;
					}				
					Book newBook = new Book(null, newBookTitle);
					newBook.setAuthors(authorSelections);
					newBook.setGenres(genreSelections);
					newBook.setPublisher(selectedPublisher);
					try {
						if (addByBranch == 1) {
							printSubMenu("Which branch would you like to add copies of your book to?");
							System.out.println(Constants.SELECT_BRANCH);
							Branch branch = getBranchSelection(service.getBranches(null));
							if (branch.getBranchId() == -1) {
								return false;
							} else if (branch.getBranchId() == 0) {
								break;
							}
							System.out.println("How many copies do you want to add to this library branch: ");
							String numCopies = scan.nextLine();
							int numCopiesInt;
							try {
								numCopiesInt = Integer.parseInt(numCopies);
								service.addBook(newBook);
								service.addBookCopies(branch, newBook, numCopiesInt);
							} catch (Exception e) {
								System.out.println("Invalid number of copies. Failed to add copies to this branch.");
							}
						} else {
							service.addBook(newBook);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					break;
					
				case 2: /* UPDATE */
					printSubMenu("Update a Book and Author");
					Book book = getBookSelection(service.getBooks(null));
					if (book.getBookId() == -1) {
						return false;
					} else if (book.getBookId() == 0) {
						break;
					}
					String subMenuMsg = "You have chosen to update Title: " + Constants.getColor("blue", book.getTitle()) + " ID: "
						+ Constants.getColor("blue", book.getBookId().toString()) + "\nEnter" +  Constants.getColor("green", " quit")
						+ " at any prompt to cancel operation.";
					
					printSubMenu(subMenuMsg);
					System.out.println("Enter an updated book title or N/A to continue: ");
					String updatedTitle = scan.nextLine();
					if (updatedTitle.equals("quit")) {
						break;
					} else if (updatedTitle.length() == 0) {
						updatedTitle = book.getTitle();
					}

					List<Author> oldAuthors = book.getAuthors();
					List<Author> updatedAuthors = updateAuthors(book);
					List <Author> authorsToAdd = new ArrayList<>();
					for (Author a : updatedAuthors) {
						if (oldAuthors.contains(a) != true) {
							authorsToAdd.add(a);
						}
					}
					
					List<Genre> oldGenres = book.getGenres();
					List<Genre> updatedGenres = updateGenres(book);
					List <Genre> genresToAdd = new ArrayList<>();
					for (Genre g : updatedGenres) {
						if (oldGenres.contains(g) != true) {
							genresToAdd.add(g);
						}
					}

					book.setTitle(updatedTitle);
					book.setAuthors(authorsToAdd);
					book.setGenres(genresToAdd);

					try {
						service.updateBook(book);
					} catch (Exception e) {/**/}

					break;
				case 3: /* DELETE */
					printSubMenu("Delete a Book and Author");
					Book deleteBook = getBookSelection(service.getBooks(null));
					if (deleteBook.getBookId() == -1) {
						return false;
					} else if (deleteBook.getBookId() == 0) {
						break;
					}
					String deleteMsg = "You have chosen to delete Title: " + Constants.getColor("blue", deleteBook.getTitle()) + " ID: "
						+ Constants.getColor("blue", deleteBook.getBookId().toString()) + "\nEnter" +  Constants.getColor("green", " quit")
						+ " at any prompt to cancel operation.";
					printSubMenu(deleteMsg);

					System.out.println("Are you sure you wish to delete this book?");
					int confirmDeletion = promptOptions(Arrays.asList("Yes", "No"));
					if (confirmDeletion == 1) {
						try {
							service.deleteBook(deleteBook);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					
					break;
				case 4: /* READ */
					printSubMenu("Read Books and Authors");
					// service.readBook();
					break;
				default:
					break;
			}
		} while (true);
	}
	public boolean audrGenre() {
		int selection;
		do {
			selection = promptOptions(Arrays.asList(Constants.GENRE_OPTIONS));
			if (selection == -1) {
				return false;
			} else if (selection == 0) {
				return true;
			}
			Genre genre;
			switch(selection) {
				case 1: /* ADD */
					printSubMenu("Add a Genre");
					String genreName = "";
					do {
						System.out.print("Enter a name for your genre: ");
						genreName = scan.nextLine();
						if (genreName.equals("quit")) {
							return false;
						} else if (genreName.length() > 0) {
							break;
						}
						System.out.println(Constants.INCORRECT_INPUT);
					} while (true);

					genre = new Genre(null, genreName);
					try {
						service.addGenre(genre);
					} catch (Exception e) {
						e.printStackTrace();
					}
					break;

				case 2: /* UPDATE */
					printSubMenu("Update a Genre");
					genre = getGenreSelection(service.getGenres(null));
					if (genre.getGenreId() == -1) {
						return false;
					} else if (genre.getGenreId() == 0) {
						break;
					}
					String subMenuMsg = "You have chosen to update the genre with\nGenre ID: "
						+ genre.getGenreId() + " and Genre Name: " + genre.getGenreName()
						+ ".\nEnter 'quit' at any prompt to cancel operation.";
					printSubMenu(subMenuMsg);

					System.out.print(Constants.NEW_GENRE_NAME);
					String newName = scan.nextLine();
					if (newName.equals("quit")) {
						break;
					} else if (newName.length() == 0 || newName.equals("N/A")) {
						break;
					}

					Genre updatedGenre = new Genre(genre.getGenreId(), newName);
					try {
						service.updateGenre(updatedGenre);
						genre.setGenreName(updatedGenre.getGenreName());
					} catch (Exception e) {
						e.printStackTrace();
					}
					break;

				case 3: /* DELETE */
					printSubMenu("Delete a Genre");
					genre = getGenreSelection(service.getGenres(null));
					if (genre.getGenreId() == -1) {
						return false;
					} else if (genre.getGenreId() == 0) {
						break;
					}
					subMenuMsg = "You have chosen to delete the genre with\nGenre ID: "
						+ genre.getGenreId() + " and Genre Name: " + genre.getGenreName()
						+ ".\nEnter 'quit' at any prompt to cancel operation.";
					printSubMenu(subMenuMsg);

					System.out.println("Are you sure you wish to delete this genre?");
					int confirmDeletion = promptOptions(Arrays.asList("Yes", "No"));
					if (confirmDeletion == 1) {
						try {
							service.deleteGenre(genre);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					break;

				case 4: /* READ */
					printSubMenu("Returning all Genres.");
					List<Genre> genres = service.getGenres(null);
					for (Genre g : genres) {
						System.out.println(g.toString());
					}
					break;
				default:
					break;
			}

		} while (true);
	}
	public boolean audrPublisher() {
		int selection;
		do {
			selection = promptOptions(Arrays.asList(Constants.PUBLISHER_OPTIONS));
			if (selection == -1) {
				return false;
			} else if (selection == 0) {
				return true;
			}
			Publisher publisher;
			switch(selection) {
				case 1: /* ADD */
					printSubMenu("Add a Publisher");

					// Get new publisher name
					String newPublisherName = "";
					do {
						System.out.print("Enter publisher name: ");
						newPublisherName = scan.nextLine();
						if (newPublisherName.equals("quit")) {
							return false;
						} else if (newPublisherName.length() > 0) {
							break;
						}
						System.out.println(Constants.INCORRECT_INPUT);
					} while (true);

					// Get new publisher address
					String newPublisherAddress = "";
					do {
						System.out.print("Enter publisher address: ");
						newPublisherAddress = scan.nextLine();
						if (newPublisherAddress.equals("quit")) {
							return false;
						} else if (newPublisherAddress.length() > 0) {
							break;
						}
						System.out.println(Constants.INCORRECT_INPUT);
					} while (true);
					
					// Get new publisher address
					String newPublisherPhone = "";
					do {
						System.out.print("Enter publisher phone number: ");
						newPublisherPhone = scan.nextLine();
						if (newPublisherPhone.equals("quit")) {
							return false;
						} else if (newPublisherPhone.length() > 0) {
							break;
						}
						System.out.println(Constants.INCORRECT_INPUT);
					} while (true);

					publisher = new Publisher(null, newPublisherName, newPublisherAddress, newPublisherPhone);
					try {
						service.addPublisher(publisher);
					} catch (Exception e) {
						e.printStackTrace();
					}
					break;

				case 2: /* UPDATE */
					printSubMenu("Update a Publisher");
					publisher = getPublisherSelection(service.getPublishers(null));
					if (publisher.getPublisherId() == -1) {
						return false;
					} else if (publisher.getPublisherId() == 0) {
						break;
					}
					String subMenuMsg = "You have chosen to update the publisher with\nPublisher ID: "
						+ publisher.getPublisherId() + " and Publisher Name: " + publisher.getPublisherName()
						+ ".\nEnter 'quit' at any prompt to cancel operation.";
					printSubMenu(subMenuMsg);

					System.out.print(Constants.NEW_PUBLISHER_NAME);
					String newName = scan.nextLine();
					if (newName.equals("quit")) {
						break;
					} else if (newName.length() == 0 || newName.equals("N/A")) {
						newName = publisher.getPublisherName();
					}

					System.out.print(Constants.NEW_PUBLISHER_ADDRESS);
					String newAddress = scan.nextLine();
					if (newAddress.equals("quit")) {
						break;
					} else if (newAddress.length() == 0 || newAddress.equals("N/A")) {
						newAddress = publisher.getPublisherAddress();
					}

					System.out.print(Constants.NEW_PUBLISHER_PHONE);
					String newPhone = scan.nextLine();
					if (newPhone.equals("quit")) {
						break;
					} else if (newPhone.length() == 0 || newPhone.equals("N/A")) {
						newPhone = publisher.getPublisherPhone();
					}

					boolean sameName = newName.equals(publisher.getPublisherName());
					boolean sameAddress = newAddress.equals(publisher.getPublisherAddress());
					boolean samePhone = newPhone.equals(publisher.getPublisherPhone());
					if (sameName && sameAddress && samePhone) {
						break;
					}
						
					Publisher updatedPublisher = new Publisher(publisher.getPublisherId(), newName, newAddress, newPhone);
					try {
						service.updatePublisher(updatedPublisher);
						publisher.setPublisherName(updatedPublisher.getPublisherName());
						publisher.setPublisherAddress(updatedPublisher.getPublisherAddress());
						publisher.setPublisherPhone(updatedPublisher.getPublisherPhone());
					} catch (Exception e) {
						e.printStackTrace();
					}
					break;
				case 3: /* DELETE */
					printSubMenu("Delete a Publisher");
					publisher = getPublisherSelection(service.getPublishers(null));
					if (publisher.getPublisherId() == -1) {
						return false;
					} else if (publisher.getPublisherId() == 0) {
						break;
					}
					subMenuMsg = "You have chosen to delete the publisher with\nPublisher ID: "
						+ publisher.getPublisherId() + " and Publisher Name: " + publisher.getPublisherName()
						+ ".\nEnter 'quit' at any prompt to cancel operation.";
					printSubMenu(subMenuMsg);

					System.out.println("Are you sure you wish to delete this publisher?");
					int confirmDeletion = promptOptions(Arrays.asList("Yes", "No"));
					if (confirmDeletion == 1) {
						try {
							service.deletePublisher(publisher);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					break;

				case 4: /* READ */
					printSubMenu("Returning all Publishers.");
					List<Publisher> publishers = service.getPublishers(null);
					for (Publisher p : publishers) {
						System.out.println(p.toString());
					}
					break;
				default:
					break;
			}
		} while(true);
	}
	public boolean audrLibraryBranch() {
		int selection;
		do {
			selection = promptOptions(Arrays.asList(Constants.ADMIN_BRANCH_OPTIONS));
			if (selection == -1) {
				return false;
			} else if (selection == 0) {
				return true;
			}

			Branch branch;
			switch(selection) {
				case 1: /* ADD */
					printSubMenu("Add a Branch");

					// Get new branch name
					String newBranchName = "";
					do {
						System.out.print("Enter branch name: ");
						newBranchName = scan.nextLine();
						if (newBranchName.equals("quit")) {
							return false;
						} else if (newBranchName.length() > 0) {
							break;
						}
						System.out.println(Constants.INCORRECT_INPUT);
					} while (true);

					// Get new branch address
					String newBranchAddress = "";
					do {
						System.out.print("Enter branch address: ");
						newBranchAddress = scan.nextLine();
						if (newBranchAddress.equals("quit")) {
							return false;
						} else if (newBranchAddress.length() > 0) {
							break;
						}
						System.out.println(Constants.INCORRECT_INPUT);
					} while (true);
					
					branch = new Branch(null, newBranchName, newBranchAddress);
					try {
						service.addBranch(branch);
					} catch (Exception e) {
						e.printStackTrace();
					}
					break;
				
				case 2: /* UPDATE */
					printSubMenu("Update a Branch");
					branch = getBranchSelection(service.getBranches(null));
					if (branch.getBranchId() == -1) {
						return false;
					} else if (branch.getBranchId() == 0) {
						break;
					}
					String subMenuMsg = "You have chosen to update the branch with\nBranch ID: "
						+ branch.getBranchId() + " and Branch Name: " + branch.getBranchName()
						+ ".\nEnter 'quit' at any prompt to cancel operation.";
					printSubMenu(subMenuMsg);

					System.out.print(Constants.NEW_BRANCH_NAME);
					String newName = scan.nextLine();
					if (newName.equals("quit")) {
						break;
					} else if (newName.length() == 0 || newName.equals("N/A")) {
						newName = branch.getBranchName();
					}

					System.out.print(Constants.NEW_BRANCH_ADDRESS);
					String newAddress = scan.nextLine();
					if (newAddress.equals("quit")) {
						break;
					} else if (newAddress.length() == 0 || newAddress.equals("N/A")) {
						newAddress = branch.getBranchAddress();
					}

					boolean sameName = newName.equals(branch.getBranchName());
					boolean sameAddress = newAddress.equals(branch.getBranchAddress());
					if (sameName && sameAddress) {
						break;
					}
						
					Branch updatedBranch = new Branch(branch.getBranchId(), newName, newAddress);
					try {
						service.updateBranch(updatedBranch);
						branch.setBranchName(updatedBranch.getBranchName());
						branch.setBranchAddress(updatedBranch.getBranchAddress());
					} catch (Exception e) {
						e.printStackTrace();
					}
					break;
				case 3: /* DELETE */
					printSubMenu("Delete a Branch");
					branch = getBranchSelection(service.getBranches(null));
					if (branch.getBranchId() == -1) {
						return false;
					} else if (branch.getBranchId() == 0) {
						break;
					}
					subMenuMsg = "You have chosen to delete the branch with\nBranch ID: "
						+ branch.getBranchId() + " and Branch Name: " + branch.getBranchName()
						+ ".\nEnter 'quit' at any prompt to cancel operation.";
					printSubMenu(subMenuMsg);

					System.out.println("Are you sure you wish to delete this branch?");
					int confirmDeletion = promptOptions(Arrays.asList("Yes", "No"));
					if (confirmDeletion == 1) {
						try {
							service.deleteBranch(branch);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					break;
				case 4: /* READ */
					printSubMenu("Returning all Branches.");
					List<Branch> branches = service.getBranches(null);
					for (Branch b : branches) {
						System.out.println(b.toString());
					}
					break;
				default:
					break;
			}
		} while (true);
	}

	public boolean audrBorrower() {
		int selection;
		do {
			selection = promptOptions(Arrays.asList(Constants.AUD_BORROWER_OPTIONS));
			if (selection == -1) {
				return false;
			} else if (selection == 0) {
				return true;
			}

			Borrower borrower;
			switch(selection) {
				case 1: /* ADD */
					printSubMenu("Add a Borrower");

					// Get new borrower name
					String newBorrowerName = "";
					do {
						System.out.print("Enter borrower name: ");
						newBorrowerName = scan.nextLine();
						if (newBorrowerName.equals("quit")) {
							return false;
						} else if (newBorrowerName.length() > 0) {
							break;
						}
						System.out.println(Constants.INCORRECT_INPUT);
					} while (true);

					// Get new borrower address
					String newBorrowerAddress = "";
					do {
						System.out.print("Enter borrower address: ");
						newBorrowerAddress = scan.nextLine();
						if (newBorrowerAddress.equals("quit")) {
							return false;
						} else if (newBorrowerAddress.length() > 0) {
							break;
						}
						System.out.println(Constants.INCORRECT_INPUT);
					} while (true);

					// Get new borrower phone
					String newBorrowerPhone = "";
					do {
						System.out.print("Enter borrower phone: ");
						newBorrowerPhone = scan.nextLine();
						if (newBorrowerPhone.equals("quit")) {
							return false;
						} else if (newBorrowerPhone.length() > 0) {
							break;
						}
						System.out.println(Constants.INCORRECT_INPUT);
					} while (true);
					
					borrower = new Borrower(null, newBorrowerName, newBorrowerAddress, newBorrowerPhone);
					try {
						service.addBorrower(borrower);
					} catch (Exception e) {
						e.printStackTrace();
					}
					break;

				case 2: /* UPDATE */
					printSubMenu("Update a Borrower");
					borrower = getBorrowerSelection(service.getBorrowers(null));
					if (borrower.getCardNo() == -1) {
						return false;
					} else if (borrower.getCardNo() == 0) {
						break;
					}
					String subMenuMsg = "You have chosen to update the borrower with\nCard Number: "
						+ borrower.getCardNo() + " and Name: " + borrower.getName()
						+ ".\nEnter 'quit' at any prompt to cancel operation.";
					printSubMenu(subMenuMsg);

					System.out.print(Constants.NEW_BORROWER_NAME);
					String newName = scan.nextLine();
					if (newName.equals("quit")) {
						break;
					} else if (newName.length() == 0 || newName.equals("N/A")) {
						newName = borrower.getName();
					}

					System.out.print(Constants.NEW_BORROWER_ADDRESS);
					String newAddress = scan.nextLine();
					if (newAddress.equals("quit")) {
						break;
					} else if (newAddress.length() == 0 || newAddress.equals("N/A")) {
						newAddress = borrower.getAddress();
					}

					System.out.print(Constants.NEW_BORROWER_PHONE);
					String newPhone = scan.nextLine();
					if (newPhone.equals("quit")) {
						break;
					} else if (newPhone.length() == 0 || newPhone.equals("N/A")) {
						newPhone = borrower.getPhone();
					}

					boolean sameName = newName.equals(borrower.getName());
					boolean sameAddress = newAddress.equals(borrower.getAddress());
					boolean samePhone = newPhone.equals(borrower.getPhone());
					if (sameName && sameAddress && samePhone) {
						break;
					}
						
					Borrower updatedBorrower = new Borrower(borrower.getCardNo(), newName, newAddress, newPhone);
					try {
						service.updateBorrower(updatedBorrower);
						borrower.setName(updatedBorrower.getName());
						borrower.setAddress(updatedBorrower.getAddress());
					} catch (Exception e) {
						e.printStackTrace();
					}
					break;
				case 3: /* DELETE */
					printSubMenu("Delete a Borrower");
					borrower = getBorrowerSelection(service.getBorrowers(null));
					if (borrower.getCardNo() == -1) {
						return false;
					} else if (borrower.getCardNo() == 0) {
						break;
					}
					subMenuMsg = "You have chosen to delete the borrower with\nCard Number: "
						+ borrower.getCardNo() + " and Name: " + borrower.getName()
						+ ".\nEnter 'quit' at any prompt to cancel operation.";
					printSubMenu(subMenuMsg);

					System.out.println("Are you sure you wish to delete this borrower?");
					int confirmDeletion = promptOptions(Arrays.asList("Yes", "No"));
					if (confirmDeletion == 1) {
						try {
							service.deleteBorrower(borrower);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					break;
				case 4: /* READ */
					printSubMenu("Returning all Borrowers.");
					List<Borrower> borrowers = service.getBorrowers(null);
					for (Borrower b : borrowers) {
						System.out.println(b.toString());
					}
					break;
				default:
					break;
			}
		} while (true);
	}
	public boolean overrideDueDate() {
		printSubMenu("Select a Loan to Update");
		Loan loan = getLoanSelection(service.getLoans(null));
		if (loan.getCardNo() == -1) {
			return false;
		} else if (loan.getCardNo() == 0) {
			return true;
		}

		System.out.println("How long would you like to extend the due date?\nNumber of days: ");
		String userInput = scan.nextLine();
		int numDays;
		try {
			numDays = Integer.parseInt(userInput);
			service.overrideDueDate(loan, numDays);
		} catch (Exception e) {
			System.out.println(Constants.INCORRECT_INPUT);
		}
		return true;
	}

	public void setService(AdministratorService service) {
		this.service = service;
	}
	
	@Override
	public boolean driver() {
		printMenuHeader();

		AdministratorMenu modifyBookAndAuthor = new AdministratorMenu(scan, 1, service);
		AdministratorMenu modifyGenre = new AdministratorMenu(scan, 2, service);
		AdministratorMenu modifyPublisher = new AdministratorMenu(scan, 3, service);
		AdministratorMenu modifyLibraryBranch = new AdministratorMenu(scan, 4, service);
		AdministratorMenu modifyBorrower = new AdministratorMenu(scan, 5, service);
		AdministratorMenu modifyDueDate = new AdministratorMenu(scan, 6, service);
		
		options.put(1, modifyBookAndAuthor);
		options.put(2, modifyGenre);
		options.put(3, modifyPublisher);
		options.put(4, modifyLibraryBranch);
		options.put(5, modifyBorrower);
		options.put(6, modifyDueDate);

		return promptOptions(Constants.MAIN_ADMIN_OPTIONS, options);
	}
}