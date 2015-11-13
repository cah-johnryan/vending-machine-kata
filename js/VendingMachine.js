var johnryanKata = johnryanKata || {};
johnryanKata.vendingMachine = {
  create: function() {
    var self = {};
    var currentAmount = 0;

    self.display = function() {
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
      }
    };

    return self;
  }

};
