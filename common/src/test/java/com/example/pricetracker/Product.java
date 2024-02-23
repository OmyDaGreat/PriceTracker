package com.example.pricetracker;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import java.io.IOException;

public class Product {

    private String name;
    private double price;

    private String url;

    private String img;

    private String domain;

    private final String amazonPriceHolder = ".priceToPay.reinventPricePriceToPayMargin.aok-align-center.a-price";
    private final String amazonPriceElement = "span:nth-of-type(2)";

    private final String amazonNameHolder = ".product-title-word-break.a-size-large";
    private final String ebayPriceHolder = ".x-price-primary";
    private final String ebayPriceElement = ".ux-textspans";

    private final String ebayNameHolder = ".x-item-title__mainTitle";
    private final String ebayNameElement = ".ux-textspans--BOLD.ux-textspans";



    public Product(String url) {
        this.url = url;
        scrapeProduct(url);
    }

    public String toString() {
        return "========================\nProduct URL: " + url + "\nProduct image: " + img + "\nProduct's Marketplace: " + domain + "\nProduct Name: " + name + "\nPrice: " + price;
    }
    public double getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public String getUrl(){
        return url;
    }

    public String getImg(){
        return img;
    }
    public void scrapeProduct(String url) {

        String productURL = url;

        String subPriceHolder = "";
        String subPriceElement = "";
        String subNameHolder = "";
        String subNameElement = "";



        boolean isAmazon = false;
        boolean isEbay = false;

        if (this.getProductWebsite(url).equals("www.amazon.com")){
            isAmazon = true;
            subPriceHolder = amazonPriceHolder;
            subPriceElement = amazonPriceElement;
            subNameHolder = amazonNameHolder;
        } else if (this.getProductWebsite(url).equals("www.ebay.com")){
            isEbay = true;
            subPriceHolder = ebayPriceHolder;
            subPriceElement = ebayPriceElement;
            subNameHolder = ebayNameHolder;
            subNameElement = ebayNameElement;
        }

        try {
            Document document = Jsoup.connect(productURL).get();

            Elements priceHolder = document.select(subPriceHolder);
            String priceElement = priceHolder.select(subPriceElement).text();

            Elements nameHolder = document.select(subNameHolder);






            String nameElement = "";

            if (isAmazon){
                nameElement =   String.valueOf(nameHolder);

                Element imageHolder = document.getElementById("imgTagWrapperId");
                Elements imageTag = imageHolder.getElementsByTag("img");
                img = imageTag.attr("src");


                this.setAmazonElements(priceElement,nameElement);
            } else if (isEbay){
                nameElement = nameHolder.select(subNameElement).text();

                Elements imageHolder = document.select(".image.active.image-treatment.ux-image-carousel-item");
                Elements imageTag = imageHolder.first().getElementsByTag("img");

                img = imageTag.attr("src");

                this.setEbayElements(priceElement,nameElement);
            }



        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getProductWebsite(String url){
        String domain = "";
        int slashCount = 0;
        for (int i = 0; i < url.length();i++){
            if (url.substring(i,i+1).equals("/")){
                if (slashCount == 2){
                    break;
                }
                slashCount++;
                domain+= url.substring(i,i+1);
                continue;
            }

            if (slashCount == 2){
                domain += url.substring(i,i+1);
            }




        }


        switch(domain) {
            case "//www.amazon.com":
                this.domain = "www.amazon.com";
                return "www.amazon.com";
            case "//www.ebay.com":
                this.domain = "www.ebay.com";
                return "www.ebay.com";
        }
        return null;
    }

    public void setAmazonElements(String priceElement, String nameElement){
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
    }

    public void setEbayElements(String priceElement, String nameElement){
        String finalPrice = "";
        boolean isPrice = false;
        for (int i = 0; i < priceElement.length();i++){

            if (priceElement.substring(i,i+1).equals("$")){
                isPrice = true;
                continue;
            }
            if (isPrice){
                finalPrice+=priceElement.substring(i,i+1);
            }
        }


        name = nameElement;
        price = Double.parseDouble(finalPrice);
    }
}