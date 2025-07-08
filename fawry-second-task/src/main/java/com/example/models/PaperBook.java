package com.example.models;

import com.example.services.PaymentService;

public class PaperBook extends Book {

  private int stock;

  public PaperBook(
    String title,
    String author,
    String isbn,
    int publishYear,
    int stock,
    double price
  ) {
    super(title, author, publishYear, isbn, price);
    if (stock < 0) {
      throw new IllegalArgumentException("Stock cannot be negative.");
    }
    this.stock = stock;
  }

  public int getStock() {
    return stock;
  }

  public void decreaseStock(int quantity) {
    if (quantity <= 0) {
      throw new IllegalArgumentException("Quantity must be positive.");
    }
    if (quantity > stock) {
      throw new IllegalArgumentException("Not enough stock available.");
    }
    this.stock -= quantity;
  }

  @Override
  public void processPurchase(
    PaymentService paymentService,
    String email,
    String address
  ) {
    paymentService.getShippingService().shipBook(getIsbn(), address);
  }
}
