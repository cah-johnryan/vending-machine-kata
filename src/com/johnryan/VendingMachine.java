package com.johnryan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VendingMachine {

    private final List<String> coinReturn = new ArrayList<String>();
    private final List<String> productReturn = new ArrayList<String>();
    private final CoinMap availableCoins = new CoinHashMap();
    private final ProductMap availableProducts = new ProductHashMap();

    private String displayMessage;
    private Integer currentAmount = 0;

    public VendingMachine() { this(10, 10); }

    public VendingMachine(Integer defaultProductInventoryAmount) { this(defaultProductInventoryAmount, 10); }

    public VendingMachine(Integer defaultProductInventoryAmount, Integer defaultCoinInventoryAmount) {
        availableCoins.put("PENNY", new Coin(1, defaultCoinInventoryAmount));
        availableCoins.put("NICKEL", new Coin(5, defaultCoinInventoryAmount));
        availableCoins.put("DIME", new Coin(10, defaultCoinInventoryAmount));
        availableCoins.put("QUARTER", new Coin(25, defaultCoinInventoryAmount));

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
        if (exactChangeNeeded()) {
            return "EXACT CHANGE ONLY";
        }
        else if (currentAmount == 0) {
            return "INSERT COINS";
        }
        else {
            return getCurrencyFormat(currentAmount);
        }
    }

    private boolean exactChangeNeeded() {
        return availableCoins.get("PENNY").getInventory() == 0 ||
                availableCoins.get("NICKEL").getInventory() == 0 ||
                availableCoins.get("DIME").getInventory() == 0 ||
                availableCoins.get("QUARTER").getInventory() == 0;
    }

    public void insertCoin(String coinName) {
        if (availableCoins.containsKey(coinName)) {
            currentAmount += availableCoins.get(coinName).getCost();
            availableCoins.get(coinName).incrementInventory();
        }
        else {
            coinReturn.add(coinName);
        }
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
            availableCoins.get(coinName).reduceInventory();
            coinReturn.add(coinName);
            currentAmount -= coinAmount;
        }
    }

    private interface CoinMap extends Map<String, Coin> {}
    private class CoinHashMap extends HashMap<String, Coin> implements CoinMap {}
    private interface ProductMap extends Map<String, Product> {}
    private class ProductHashMap extends HashMap<String, Product> implements ProductMap {}
}
