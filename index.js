try {
    require("source-map-support").install();
} catch(err) {
}

require("./lambda/goog/bootstrap/nodejs.js");

// It's not clear why this is necessary
goog.global.CLOSURE_UNCOMPILED_DEFINES = {"cljs.core._STAR_target_STAR_":"nodejs"};

require("./lambda/main.js");

goog.require("lambda.core");

exports.core = lambda.core.main;
