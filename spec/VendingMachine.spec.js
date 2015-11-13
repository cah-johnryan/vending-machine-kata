describe("Vending Machine", function() {
  var subject;

  beforeEach(function() {
    subject = johnryanKata.vendingMachine.create();
  });

  it("displays INSERT COIN", function() {
    expect(subject.display()).toEqual("INSERT COIN");
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
  });
});
