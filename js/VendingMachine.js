var johnryanKata = johnryanKata || {};
johnryanKata.vendingMachine = {
  create: function() {
    var self = {};
    var currentAmount = 0;
    var temporaryDisplayText = "";
    var dispenserContents = "";
    var coinReturnContents = "";

    var allowedProducts = {
      "COLA": 100,
      "CHIPS": 50,
      "CANDY": 65
    };

    self.display = function() {
      if (temporaryDisplayText !== "") {
        var temp = temporaryDisplayText;
        temporaryDisplayText = "";
        return temp;
      }
      return currentAmount === 0 ? "INSERT COIN" : amountFormatter(
        currentAmount);
    };

    function amountFormatter(amount) {
      return "$" + (amount / 100).toFixed(2);
    }

    self.insert = function(coin) {
      switch (coin) {
        case "NICKEL":
          currentAmount += 5;
          break;
        case "DIME":
          currentAmount += 10;
          break;
        case "QUARTER":
          currentAmount += 25;
          break;
        default:
          coinReturnContents = coin;
      }
    };

    self.selectProduct = function(productName) {
      if (allowedProducts[productName] > 0) {
        if ((currentAmount >= allowedProducts[productName])) {
          currentAmount -= allowedProducts[productName];
          dispenserContents = productName;
          temporaryDisplayText = "THANK YOU";
        } else {
          temporaryDisplayText = "PRICE " + amountFormatter(
            allowedProducts[productName]);
        }
      } else {
        temporaryDisplayText = "INVALID PRODUCT SELECTED";
      }
    };

    self.dispenser = function() {
      return dispenserContents;
    }

    self.coinReturn = function() {
      return coinReturnContents;
    }

    return self;
  }

};
