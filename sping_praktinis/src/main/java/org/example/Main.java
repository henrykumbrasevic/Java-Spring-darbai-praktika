package org.example;

import java.util.Scanner;

public class Main {

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);


    while (true) {
      System.out.println("Yes or no?");
      String input = scanner.nextLine();
      if (input.equalsIgnoreCase("no")) {
        break;
      }
    }
  }
}