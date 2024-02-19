package com.example.pricetracker;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class Product {

    private String name;
    private double price;

    public Product(String url) {
        scrapeProduct(url);
    }

    public String toString() {
        return "========================\nProduct Name: " + name + "\nPrice: " + price;
    }
    public double getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }
    public void scrapeProduct(String url) {

        String productURL = url;

        try {
            Document document = Jsoup.connect(productURL).get();

            Elements priceHolder = document.select(".priceToPay.reinventPricePriceToPayMargin.aok-align-center.a-price");
            String priceElement = priceHolder.select("span:nth-of-type(2)").text();

            Elements nameHolder = document.select(".product-title-word-break.a-size-large");

            String nameElement = String.valueOf(nameHolder);

            String finalPrice = "";
            for (int i = 0; i < priceElement.length(); i++) {
                if (priceElement.substring(i, i + 1).equals("$")) {
                    continue;
                }
                if (priceElement.substring(i, i + 1).equals(" ")) {
                    break;
                }
                finalPrice += priceElement.substring(i, i + 1);
            }

            String finalName = "";
            boolean isName = false;
            boolean firstSpace = false;

            for (int x = 0; x < nameElement.length(); x++) {
                if (nameElement.substring(x, x + 1).equals(">")) {
                    isName = true;
                    continue;
                }

                if (nameElement.substring(x, x + 1).equals("<") && x != 0) {
                    break;
                }

                if (nameElement.substring(x, x + 1).equals(" ") && isName) {
                    if (firstSpace == false) {
                        firstSpace = true;
                        continue;
                    }

                }

                if (isName) {
                    if (firstSpace) {
                        finalName += nameElement.substring(x, x + 1);
                    }
                }

            }

            name = finalName;
            price = Double.parseDouble(finalPrice);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}