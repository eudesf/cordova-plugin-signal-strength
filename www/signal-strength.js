function SignalStrength() {
	
  this.dbm = function(callback) {
    return cordova.exec(callback, function(err) {
      callback(-1);
    }, "SignalStrength", "dbm", []);
  };

  this.snr = function(callback) {
    return cordova.exec(callback, function(err) {
      callback(-1);
    }, "SignalStrength", "snr", []);
  };

  this.cdmaDbm = function(callback) {
    return cordova.exec(callback, function(err) {
      callback(-1);
    }, "SignalStrength", "cdmaDbm", []);
  };

  this.cdmaEcio = function(callback) {
    return cordova.exec(callback, function(err) {
      callback(-1);
    }, "SignalStrength", "cdmaEcio", []);
  };
}

window.SignalStrength = new SignalStrength()
