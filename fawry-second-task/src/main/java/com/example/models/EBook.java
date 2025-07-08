package com.example.models;

import com.example.services.PaymentService;

public class EBook extends Book {

  private String fileType;

  public EBook(
    String title,
    String author,
    int publishYear,
    String isbn,
    double price,
    String fileType
  ) {
    super(title, author, publishYear, isbn, price);
    this.fileType = fileType;
  }

  public String getFileType() {
    return fileType;
  }

  @Override
  public void processPurchase(
    PaymentService paymentService,
    String email,
    String address
  ) {
    paymentService.getMailService().sendEBook(getIsbn(), email);
  }
}
