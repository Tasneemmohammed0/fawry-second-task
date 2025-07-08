package com.example.services;

public class MailService {

  public void sendEBook(String isbn, String email) {
    if (isbn == null || isbn.isEmpty() || email == null || email.isEmpty()) {
      throw new IllegalArgumentException(
        "ISBN and email cannot be null or empty "
      );
    }

    System.out.println(
      "Sending eBook with ISBN: " + isbn + " to email: " + email
    );
  }
}
