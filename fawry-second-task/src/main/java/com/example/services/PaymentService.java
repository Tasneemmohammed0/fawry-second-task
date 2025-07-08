package com.example.services;

import com.example.models.Book;
import com.example.models.PaperBook;

public class PaymentService {

  ShippingService shippingService;
  MailService mailService;
  InventoryService inventoryService;

  public PaymentService(
    InventoryService inventoryService,
    ShippingService shippingService,
    MailService mailService
  ) {
    this.inventoryService = inventoryService;
    this.shippingService = shippingService;
    this.mailService = mailService;
  }

  public double buyBook(
    String isbn,
    Integer quantity,
    String email,
    String address
  ) {
    if (quantity <= 0) {
      throw new IllegalArgumentException("Quantity must be greater than zero.");
    }

    // Find the book in inventory
    Book book = inventoryService.getBookByIsbn(isbn);

    if (book == null) {
      throw new IllegalArgumentException(
        "Book with ISBN " + isbn + " not found."
      );
    }

    // Handle stock fot Paper Books
    if (book instanceof PaperBook paperBook) {
      if (paperBook.getStock() < quantity) {
        throw new IllegalArgumentException("Not enough stock available.");
      }
      paperBook.decreaseStock(quantity);
    }

    for (int i = 0; i < quantity; i++) {
      book.processPurchase(this, email, address);
    }

    // Calculate paid amount
    double totalAmount = book.getPrice() * quantity;
    System.out.println(
      "Total amount paid for " +
      quantity +
      "x " +
      book.getTitle() +
      ": $" +
      totalAmount
    );

    return totalAmount;
  }

  public ShippingService getShippingService() {
    return shippingService;
  }

  public MailService getMailService() {
    return mailService;
  }
}
