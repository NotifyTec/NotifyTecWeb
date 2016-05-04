var module = angular.module("TitleManager", []);

module.factory("titleService", function () {
    var t = "";

    function get() {
        return t;
    }

    function set(title) {
        t = title;
        $("#title").text(t);
    }

    return {
        get: get,
        set: set
    };
});