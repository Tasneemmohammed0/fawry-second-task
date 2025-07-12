package com.example;

import java.util.List;

import com.example.models.Book;
import com.example.models.DemoBook;
import com.example.models.EBook;
import com.example.models.PaperBook;
import com.example.services.InventoryService;
import com.example.services.MailService;
import com.example.services.PaymentService;
import com.example.services.ShippingService;

public class Main {

  public static void main(String[] args) {
    System.out.println(
      "Quantum Bookstore: Welcome to the Command-Line Interface!"
    );
    System.out.println(
      "========================================================="
    );

    InventoryService inventoryService = new InventoryService();
    MailService mailService = new MailService();
    ShippingService shippingService = new ShippingService();
    PaymentService paymentService = new PaymentService(
      inventoryService,
      shippingService,
      mailService
    );

    // Add some books to the inventory
    try {
      Book book1 = new PaperBook("Book 1", "Author A", "ISBN1", 2020, 3, 5);
      Book book2 = new PaperBook("Book 2", "Author B", "ISBN2", 1999, 5, 3); // Outdated
      Book book3 = new PaperBook("Book 3", "Author C", "ISBN3", 2023, 0, 7); // This book is out of stock
      Book book4 = new DemoBook("Demo Book", "Author D", 2022, "ISBN4");
      Book book5 = new DemoBook("Demo Book 2", "Author E", 2023, "ISBN5");
      Book book6 = new EBook(
        "E-Book 1",
        "Author F",
        2022,
        "ISBN6",
        10.0,
        "PDF"
      );

      inventoryService.addBook(book1);
      inventoryService.addBook(book2);
      inventoryService.addBook(book3);
      inventoryService.addBook(book4);
      inventoryService.addBook(book5);
      inventoryService.addBook(book6);
      System.out.println("Books added successfully.");
    } catch (Exception e) {
      System.out.println("Error adding books: " + e.getMessage());
    }

    printInventory("Initial Inventory", inventoryService);

    System.out.println("Processing purchases...");
    // Buying paper book
    try {
      System.out.println("Buying PaperBook with ISBN1...");
      Book bookToPurchase = inventoryService.getBookByIsbn("ISBN1");

      paymentService.buyBook(
        bookToPurchase.getIsbn(),
        1,
        "user@gmail.com",
        "Certain Address"
      );
    } catch (Exception e) {
      System.out.println("Error processing purchase: " + e.getMessage());
    }

    // Buying an Ebook
    try {
      System.out.println("Buying EBook with ISBN6...");
      EBook ebookToPurchase = (EBook) inventoryService.getBookByIsbn("ISBN6");

      paymentService.buyBook(
        ebookToPurchase.getIsbn(),
        1,
        "exampla@yahoo.com",
        "Address 2"
      );
    } catch (Exception e) {
      System.out.println("Error processing purchase: " + e.getMessage());
    }

    printInventory("Inventory after purchases", inventoryService);

    // Trying to buy a book that is out of stock
    try {
      System.out.println("Buying PaperBook with ISBN3 (out of stock)...");
      PaperBook outOfStockBook = (PaperBook) inventoryService.getBookByIsbn(
        "ISBN3"
      );

      paymentService.buyBook(
        outOfStockBook.getIsbn(),
        1,
        "exampla2@yahoo.com",
        "Address 3"
      );
    } catch (Exception e) {
      System.out.println(
        "Error processing purchase for out of stock book: " + e.getMessage()
      );
    }

    // Trying to buy a demo book
    try {
      System.out.println(
        "\nAttempting to buy the 'Store Catalog' (Demo Book)..."
      );
      paymentService.buyBook("ISBN4", 1, "customer@example.com", "Address 4");
    } catch (Exception e) {
      System.out.println(
        "Error processing purchase for demo book: " + e.getMessage()
      );
    }

    // Trying to buy a book that doesn't exist
    try {
      System.out.println("\nAttempting to buy a book with a fake ISBN...");
      paymentService.buyBook("FAKE-ISBN", 1, "test@test.com", "Address 6");
    } catch (Exception e) {
      System.out.println(
        "Quantum Bookstore: Purchase failed as expected! Reason: " +
        e.getMessage()
      );
    }

    // Remove outdated books
    System.out.println("\nRemoving outdated books...");
    inventoryService.removeOutDatedBooks();

    printInventory("Inventory after removing outdated books", inventoryService);
  }

  private static void printInventory(
    String title,
    InventoryService inventoryService
  ) {
    System.out.println("\n--- " + title + " ---");
    List<Book> books = inventoryService.getAllBooks();
    if (books.isEmpty()) {
      System.out.println("Inventory is empty.");
      return;
    }

    for (Book book : books) {
      String bookType = "Book";
      String extraInfo = "";
      if (book instanceof PaperBook) {
        bookType = "PaperBook";
        extraInfo = ", Stock: " + ((PaperBook) book).getStock();
      } else if (book instanceof EBook) {
        bookType = "EBook";
        extraInfo = ", FileType: " + ((EBook) book).getFileType();
      } else if (book instanceof DemoBook) {
        bookType = "DemoBook";
        extraInfo = " (Not for sale)";
      }

      System.out.printf(
        "[%s] ISBN: %-16s | Title: %-25s | Author: %-20s | Price: $%-6.2f | Year: %d%s\n",
        bookType,
        book.getIsbn(),
        "'" + book.getTitle() + "'",
        book.getAuthor(),
        book.getPrice(),
        book.getPublishYear(),
        extraInfo
      );
    }
    System.out.println("-------------------------------------");
  }
}
