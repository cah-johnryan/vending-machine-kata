package com.johnryan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VendingMachine {

    private List<String> coinReturn = new ArrayList<String>();
    private List<String> productReturn = new ArrayList<String>();
    private CoinMap coins = new CoinHashMap();
    private ProductMap products = new ProductHashMap();

    private String displayMessage;
    private Integer currentAmount = 0;


    public VendingMachine() {
        this(10);
    }

    public VendingMachine(Integer defaultInventoryAmount) {
        coins.put("PENNY", 1);
        coins.put("NICKEL", 5);
        coins.put("DIME", 10);
        coins.put("QUARTER", 25);

        products.put("COLA", new Product("COLA", 100, defaultInventoryAmount));
        products.put("CHIPS", new Product("CHIPS", 50, defaultInventoryAmount));
        products.put("CANDY", new Product("CANDY", 65, defaultInventoryAmount));
    }

    public List<String> getProductReturn() { return productReturn; }

    public List<String> getCoinReturn() { return coinReturn; }

    public String getDisplay() {
        if(displayMessage != null && !displayMessage.isEmpty()) {
            String tempMessage = displayMessage;
            displayMessage = "";
            return tempMessage;
        }
        return currentAmount == 0 ? "INSERT COINS" : String.format("%3.2f", currentAmount/100.0);
    }

    public void insertCoin(String coin) {
        if (coins.containsKey(coin))
            currentAmount += coins.get(coin);
        else
            coinReturn.add(coin);
    }

    public void selectProduct(String productName) {
        if (!products.containsKey(productName)) return;
        Product selectedProduct = products.get(productName);
        if (selectedProduct.getInventory() <= 0) {
            displayMessage = "SOLD OUT";
        } else
        {
            if (currentAmount >= selectedProduct.getCost())
            {
                currentAmount -= selectedProduct.getCost();
                productReturn.add(productName);
                dispenseRemainingChange();
                displayMessage = "THANK YOU";
            } else {
                displayMessage = "PRICE " + String.format("%3.2f", selectedProduct.getCost()/100.0);
            }
        }
    }

    public void returnCoins() { dispenseRemainingChange(); }

    private void dispenseRemainingChange() {
        if (currentAmount >= 25) dispenseCoinsForRemainingAmount("QUARTER", 25);
        if (currentAmount >= 10) dispenseCoinsForRemainingAmount("DIME",10);
        if (currentAmount >= 5) dispenseCoinsForRemainingAmount("NICKEL",5);
        if (currentAmount >= 0) dispenseCoinsForRemainingAmount("PENNY",1);
    }

    private void dispenseCoinsForRemainingAmount(String coinName,Integer coinAmount) {
        for (int i = 0; i < (currentAmount/coinAmount); i++) {
            coinReturn.add(coinName);
            currentAmount -= coinAmount;
        }
    }

    private interface CoinMap extends Map<String, Integer> {}
    private class CoinHashMap extends HashMap<String, Integer> implements CoinMap {}
    private interface ProductMap extends Map<String, Product> {}
    private class ProductHashMap extends HashMap<String, Product> implements ProductMap {}
}