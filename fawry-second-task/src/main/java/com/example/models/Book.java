package com.example.models;

import com.example.services.PaymentService;

public abstract class Book {

  private String title;
  private String author;
  private String isbn;
  private double price;
  private Integer publishYear;

  public Book(
    String title,
    String author,
    int publishYear,
    String isbn,
    double price
  ) {
    if (price < 0) {
      throw new IllegalArgumentException("Price cannot be negative.");
    }
    this.title = title;
    this.author = author;
    this.isbn = isbn;
    this.price = price;
    this.publishYear = publishYear;
  }

  // To be implemented by each book type
  public abstract void processPurchase(
    PaymentService paymentService,
    String email,
    String address
  );

  public String getTitle() {
    return title;
  }

  public String getAuthor() {
    return author;
  }

  public String getIsbn() {
    return isbn;
  }

  public double getPrice() {
    return price;
  }

  public Integer getPublishYear() {
    return publishYear;
  }
}
