package com.johnryan;

import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class VendingMachineTest {

    private VendingMachine subject;

    @Before
    public void setUp() throws Exception {
        subject = new VendingMachine();
    }

    @Test
    public void willDisplayInsertCoinsWhenNoCoinsAreInserted() {
        assertThat(subject.getDisplay(), equalTo("INSERT COINS"));
    }

    @Test
    public void willDisplay5CentsWhenNickelIsInserted() {
        subject.insertCoin("NICKEL");
        assertThat(subject.getDisplay(), equalTo("0.05"));
    }

    @Test
    public void willDisplay10CentsWhenDimeIsInserted() {
        subject.insertCoin("DIME");
        assertThat(subject.getDisplay(), equalTo("0.10"));
    }

    @Test
    public void willDisplay25CentsWhenQuarterIsInserted() {
        subject.insertCoin("QUARTER");
        assertThat(subject.getDisplay(), equalTo("0.25"));
    }

    @Test
    public void doesNotUpdateDisplayWhenInvalidCoinIsInserted() {
        subject.insertCoin("WOODEN NICKEL");
        assertThat(subject.getDisplay(), equalTo("INSERT COINS"));
    }

    @Test
    public void willDisplayPreviousCoinValueAfterInvalidCoinInserted() {
        subject.insertCoin("QUARTER");
        subject.insertCoin("WOODEN NICKEL");
        assertThat(subject.getDisplay(), equalTo("0.25"));
    }

    @Test
    public void itAddsCoinsTogether() {
        subject.insertCoin("QUARTER");
        subject.insertCoin("NICKEL");
        assertThat(subject.getDisplay(), equalTo("0.30"));
    }

    @Test
    public void coinReturnIsEmpty() {
        assertThat(subject.getCoinReturn(), empty());
    }

    @Test
    public void rejectedCoinsArePlacedInTheCoinReturn() {
        subject.insertCoin("WOODEN NICKEL");
        subject.insertCoin("DOLLAR COIN");
        assertThat(subject.getCoinReturn(), contains("WOODEN NICKEL", "DOLLAR COIN"));
    }

    @Test
    public void iCanBuyACokeWithOneDollar() {
        subject.insertCoin("QUARTER");
        subject.insertCoin("QUARTER");
        subject.insertCoin("QUARTER");
        subject.insertCoin("QUARTER");
        subject.selectProduct("COLA");
        assertThat(subject.getProductReturn(), contains("COLA"));
    }

    @Test
    public void iCanBuyChipsWithFiftyCents() {
        subject.insertCoin("QUARTER");
        subject.insertCoin("QUARTER");
        subject.selectProduct("CHIPS");
        assertThat(subject.getProductReturn(), contains("CHIPS"));
    }


    @Test
    public void iCanBuyCandyWithSixtyFiveCents() {
        subject.insertCoin("QUARTER");
        subject.insertCoin("QUARTER");
        subject.insertCoin("DIME");
        subject.insertCoin("NICKEL");
        subject.selectProduct("CANDY");
        assertThat(subject.getProductReturn(), contains("CANDY"));
    }

    @Test
    public void theMachineDisplaysThankYouAfterDispensingAProduct() {
        subject.insertCoin("QUARTER");
        subject.insertCoin("QUARTER");
        subject.selectProduct("CHIPS");
        assertThat(subject.getDisplay(), equalTo("THANK YOU"));
    }

    @Test
    public void theMachineDisplaysInsertCoinsAfterSayingThankYou() {
        subject.insertCoin("QUARTER");
        subject.insertCoin("QUARTER");
        subject.insertCoin("QUARTER");
        subject.selectProduct("CHIPS");
        subject.getDisplay();
        assertThat(subject.getDisplay(), equalTo("INSERT COINS"));
    }

    @Test
    public void displaysProductPriceIfNotEnoughMoneyInserted() {
        subject.insertCoin("QUARTER");
        subject.selectProduct("CHIPS");
        assertThat(subject.getDisplay(), equalTo("PRICE 0.50"));
    }

    @Test
    public void displaysInsertCoinsAfterProductPriceDisplay() {
        subject.selectProduct("CHIPS");
        subject.getDisplay();
        assertThat(subject.getDisplay(), equalTo("INSERT COINS"));
    }

    @Test
    public void displaysInsertedAmountAfterProductPriceDisplay() {
        subject.insertCoin("QUARTER");
        subject.selectProduct("CHIPS");
        subject.getDisplay();
        assertThat(subject.getDisplay(), equalTo("0.25"));
    }

    @Test
    public void returnsNickelAsChange() {
        subject.insertCoin("QUARTER");
        subject.insertCoin("QUARTER");
        subject.insertCoin("DIME");
        subject.insertCoin("DIME");
        subject.selectProduct("CANDY");
        assertThat(subject.getCoinReturn(), contains("NICKEL"));
    }

    @Test
    public void returnsDimeAsChange() {
        subject.insertCoin("QUARTER");
        subject.insertCoin("QUARTER");
        subject.insertCoin("QUARTER");
        subject.selectProduct("CANDY");
        assertThat(subject.getCoinReturn(), contains("DIME"));
    }

    @Test
    public void returnsQuarterAsChange() {
        subject.insertCoin("QUARTER");
        subject.insertCoin("QUARTER");
        subject.insertCoin("QUARTER");
        subject.selectProduct("CHIPS");
        assertThat(subject.getCoinReturn(), contains("QUARTER"));
    }

    @Test
    public void coinsReturnedWhenReturnCoinsSelected() {
        subject.insertCoin("QUARTER");
        subject.insertCoin("DIME");
        subject.returnCoins();
        assertThat(subject.getCoinReturn(), contains("QUARTER","DIME"));
        assertThat(subject.getDisplay(), equalTo("INSERT COINS"));
    }

    @Test
    public void productSoldOut() {
        subject = new VendingMachine(0);
        subject.selectProduct("CHIPS");
        assertThat(subject.getDisplay(), equalTo("SOLD OUT"));
        assertThat(subject.getDisplay(), equalTo("INSERT COINS"));
    }

}