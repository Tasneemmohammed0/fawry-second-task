package com.example.models;

import com.example.services.PaymentService;

public class DemoBook extends Book {

  public DemoBook(String title, String author, int publishYear, String isbn) {
    super(title, author, publishYear, isbn, 0.0);
  }

  @Override
  public void processPurchase(
    PaymentService paymentService,
    String email,
    String address
  ) {
    throw new UnsupportedOperationException("DemoBook cannot be purchased.");
  }
}
