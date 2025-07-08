package com.example.services;

public class ShippingService {

  public void shipBook(String isbn, String address) {
    if (
      isbn == null || isbn.isEmpty() || address == null || address.isEmpty()
    ) {
      throw new IllegalArgumentException(
        "ISBN and address cannot be null or empty"
      );
    }

    System.out.println(
      "Shipping book with ISBN: " + isbn + " to address: " + address
    );
  }
}
