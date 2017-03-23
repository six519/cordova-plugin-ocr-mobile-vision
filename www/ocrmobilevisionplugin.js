"use strict";

var exec = require("cordova/exec");

var oCRMobileVisionPlugin = {
	recognize: function(sc, ec) {
		exec(sc, ec, "OCRMobileVisionPlugin", "recognize", []);
	}
};

module.exports = oCRMobileVisionPlugin;