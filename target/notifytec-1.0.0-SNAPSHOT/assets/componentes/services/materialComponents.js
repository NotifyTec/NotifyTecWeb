var module = angular.module("MaterialComponents", []);

module.factory("materialComponents", ["$timeout", function ($timeout) {
        return {
            upgradeDom: function (element) {
                $timeout(function () {
                    componentHandler.upgradeDom();
                }, 100);
            }
        };
    }]);