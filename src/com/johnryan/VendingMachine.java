package com.johnryan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VendingMachine {

    private List<String> coinReturn = new ArrayList<String>();
    private List<String> productReturn = new ArrayList<String>();
    private CoinMap acceptableCoins = new CoinHashMap();
    private ProductMap availableProducts = new ProductHashMap();

    private String displayMessage;
    private Integer currentAmount = 0;

    public VendingMachine() { this(10, 10); }

    public VendingMachine(Integer defaultProductInventoryAmount) { this(defaultProductInventoryAmount, 10); }

    public VendingMachine(Integer defaultProductInventoryAmount, Integer defaultCoinInventoryAmount) {
        acceptableCoins.put("PENNY", 1);
        acceptableCoins.put("NICKEL", 5);
        acceptableCoins.put("DIME", 10);
        acceptableCoins.put("QUARTER", 25);

        availableProducts.put("COLA", new Product("COLA", 100, defaultProductInventoryAmount));
        availableProducts.put("CHIPS", new Product("CHIPS", 50, defaultProductInventoryAmount));
        availableProducts.put("CANDY", new Product("CANDY", 65, defaultProductInventoryAmount));
    }

    public List<String> getProductReturn() { return productReturn; }

    public List<String> getCoinReturn() { return coinReturn; }

    public String getDisplay() {
        if(displayMessage != null && !displayMessage.isEmpty()) {
            String tempMessage = displayMessage;
            displayMessage = "";
            return tempMessage;
        }
        return currentAmount == 0 ? "INSERT COINS" : getCurrencyFormat(currentAmount);
    }

    public void insertCoin(String coin) {
        if (acceptableCoins.containsKey(coin))
            currentAmount += acceptableCoins.get(coin);
        else
            coinReturn.add(coin);
    }

    public void selectProduct(String productName) {
        if (!availableProducts.containsKey(productName)) return;
        Product selectedProduct = availableProducts.get(productName);
        if (selectedProduct.getInventory() <= 0) {
            displayMessage = "SOLD OUT";
        } else
        {
            if (currentAmount >= selectedProduct.getCost())
            {
                dispenseProduct(selectedProduct);
            } else {
                displayMessage = "PRICE " + getCurrencyFormat(selectedProduct.getCost());
            }
        }
    }

    private void dispenseProduct(Product selectedProduct) {
        currentAmount -= selectedProduct.getCost();
        productReturn.add(selectedProduct.getName());
        selectedProduct.reduceInventory();
        dispenseRemainingChange();
        displayMessage = "THANK YOU";
        availableProducts.put(selectedProduct.getName(), selectedProduct);
    }

    public void returnCoins() { dispenseRemainingChange(); }

    private String getCurrencyFormat(Integer amount) {
        return String.format("%3.2f", (amount/100.0));
    }

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
