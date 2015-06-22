package com.johnryan;

public class Coin {
    private final Integer cost;
    private Integer inventory;

    public Coin(Integer newCost, Integer newInventory) {
        cost = newCost;
        inventory = newInventory;
    }

    public Integer getCost() { return cost; }

    public Integer getInventory() { return inventory; }

    public void reduceInventory() { --inventory; }

    public void incrementInventory() { ++inventory; }
}
