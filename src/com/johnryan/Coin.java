package com.johnryan;

public class Coin {
    private String name;
    private Integer cost;
    private Integer inventory;

    public Coin(String newName, Integer newCost, Integer newInventory) {
        name = newName;
        cost = newCost;
        inventory = newInventory;
    }

    public Integer getCost() { return cost; }

    public Integer getInventory() { return inventory; }

    public void reduceInventory() { --inventory; }

    public void incrementInventory() { ++inventory; }
}
