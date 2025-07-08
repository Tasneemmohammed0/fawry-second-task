package com.example.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.example.models.Book;

public class InventoryService {

  private Map<String, Book> inventory = new ConcurrentHashMap<>(); // isbn -> book
  private static final int MAXIMUM_BOOK_AGE_YEARS = 10;

  public void addBook(Book book) {
    if (book == null || book.getIsbn() == null || book.getIsbn().isEmpty()) {
      throw new IllegalArgumentException("Book or ISBN cannot be null/empty.");
    }

    if (inventory.containsKey(book.getIsbn())) {
      throw new IllegalStateException(
        "Book with ISBN " + book.getIsbn() + " already exists."
      );
    }

    inventory.put(book.getIsbn(), book);
    System.out.println(
      "Book with ISBN " + book.getIsbn() + " added to inventory."
    );
  }

  public List<Book> removeOutDatedBooks() {
    int currentYear = LocalDate.now().getYear();

    for (Map.Entry<String, Book> entry : inventory.entrySet()) {
      Book book = entry.getValue();
      if (
        book.getPublishYear() != null &&
        (currentYear - book.getPublishYear()) > MAXIMUM_BOOK_AGE_YEARS
      ) {
        inventory.remove(entry.getKey());
      }
    }

    return List.copyOf(inventory.values());
  }

  public Book getBookByIsbn(String isbn) {
    if (isbn == null || isbn.isEmpty()) {
      throw new IllegalArgumentException("ISBN cannot be null or empty.");
    }

    Book book = inventory.get(isbn);
    if (book == null) {
      throw new IllegalStateException("Book with ISBN " + isbn + " not found.");
    }

    return book;
  }

  public List<Book> getAllBooks() {
    return List.copyOf(inventory.values());
  }
}
