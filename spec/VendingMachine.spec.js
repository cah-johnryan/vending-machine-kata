describe("Vending Machine", function() {
  var subject;

  beforeEach(function() {
    subject = johnryanKata.vendingMachine.create();
  });

  it("displays INSERT COIN", function() {
    expect(subject.display()).toEqual("INSERT COIN");
  });

  it("has an empty dispenser", function() {
    expect(subject.dispenser()).toBe("");
  });

  it("has an empty coin return", function() {
    expect(subject.coinReturn()).toBe("");
  });

  when("A nickel is inserted", function() {
    beforeEach(function() {
      subject.insert("NICKEL");
    });

    it("will display '$0.05'", function() {
      expect(subject.display()).toEqual("$0.05");
    });
  });

  when("A dime is inserted", function() {
    beforeEach(function() {
      subject.insert("DIME");
    });

    it("will display '$0.10'", function() {
      expect(subject.display()).toEqual("$0.10");
    });

    when("A quarter is inserted", function() {
      beforeEach(function() {
        subject.insert("QUARTER");
      });

      it("will display '$0.35'", function() {
        expect(subject.display()).toEqual("$0.35");
      });
    });
  });

  when("A quarter is inserted", function() {
    beforeEach(function() {
      subject.insert("QUARTER");
    });

    it("will display '$0.25'", function() {
      expect(subject.display()).toEqual("$0.25");
    });

    when("An invalid coin is inserted", function() {
      beforeEach(function() {
        subject.insert("WOODEN NICKEL");
      });

      it("will display '$0.25'", function() {
        expect(subject.display()).toEqual("$0.25");
      });
    });
  });

  when("An invalid coin is inserted", function() {
    beforeEach(function() {
      subject.insert("WOODEN NICKEL");
    });

    it("displays INSERT COIN", function() {
      expect(subject.display()).toEqual("INSERT COIN");
    });

    it("the coin return contains the invalid coin", function() {
      expect(subject.coinReturn()).toBe("WOODEN NICKEL");
    });

  });

  when("Cola is selected with no money", function() {

    beforeEach(function() {
      subject.selectProduct("COLA");
    });

    it("displays 'PRICE $1.00'", function() {
      expect(subject.display()).toEqual("PRICE $1.00");
    });

    it("displays INSERT COIN when checking the display again", function() {
      subject.display();
      expect(subject.display()).toEqual("INSERT COIN");
    });
  });

  when("Cola is selected without enough money", function() {

    beforeEach(function() {
      subject.insert("QUARTER");
      subject.selectProduct("COLA");
    });

    it("displays 'PRICE $1.00'", function() {
      expect(subject.display()).toEqual("PRICE $1.00");
    });

    it("will display '$0.25' when checking the display again", function() {
      subject.display();
      expect(subject.display()).toEqual("$0.25");
    });
  });

  when("Cola is selected with enough money inserted", function() {

    beforeEach(function() {
      subject.insert("QUARTER");
      subject.insert("QUARTER");
      subject.insert("QUARTER");
      subject.insert("QUARTER");
      subject.selectProduct("COLA");
    });

    it("dispenses a Cola", function() {
      expect(subject.dispenser()).toEqual("COLA");
    });

    it("displays 'THANK YOU'", function() {
      expect(subject.display()).toEqual("THANK YOU");
    });
  });

  when("Chips is selected with enough money inserted", function() {

    beforeEach(function() {
      subject.insert("QUARTER");
      subject.insert("QUARTER");
      subject.selectProduct("CHIPS");
    });

    it("dispenses Chips", function() {
      expect(subject.dispenser()).toEqual("CHIPS");
    });

    it("displays 'THANK YOU'", function() {
      expect(subject.display()).toEqual("THANK YOU");
    });
  });

  when("Candy is selected with enough money inserted", function() {

    beforeEach(function() {
      subject.insert("QUARTER");
      subject.insert("QUARTER");
      subject.insert("DIME");
      subject.insert("NICKEL");
      subject.selectProduct("CANDY");
    });

    it("dispenses Candy", function() {
      expect(subject.dispenser()).toEqual("CANDY");
    });

    it("displays 'THANK YOU'", function() {
      expect(subject.display()).toEqual("THANK YOU");
    });
  });
});
