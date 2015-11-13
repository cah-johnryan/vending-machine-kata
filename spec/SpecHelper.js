var callbackFn = function(a, b, c) {
  return function(callback) {
    callback(a, b, c);
  };
};

String.prototype.endsWith = function(suffix) {
  return this.indexOf(suffix, this.length - suffix.length) !== -1;
};

var sugarFn = function(term) {
  return function(desc, fn) {
    return describe(term + ' ' + desc, fn);
  };
};

var when = sugarFn('when');
var and = sugarFn('and');
