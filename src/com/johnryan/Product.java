package com.johnryan;

public class Product {
    private String name;
    private Integer cost;
    private Integer inventory;

    public Product(String newName, Integer newCost, Integer newInventory) {
        name = newName;
        cost = newCost;
        inventory = newInventory;
    }

    public String getName() { return name; }
    public Integer getCost() { return cost; }
    public Integer getInventory() { return inventory; }

    public void setInventory(Integer newInventory) { inventory = newInventory; }

}
