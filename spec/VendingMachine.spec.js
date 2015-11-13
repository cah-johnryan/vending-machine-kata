describe("Vending Machine", function() {

  var subject;

  beforeEach(function() {
    subject = vendingMachine.create();
  });

  when("When there are no coins inserted", function() {

    it("displays INSERT COIN", function() {
      expect(subject.display()).toEqual("INSERT COIN");
    });
  });

});
