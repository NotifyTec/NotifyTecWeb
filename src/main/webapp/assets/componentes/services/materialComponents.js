var module = angular.module("MaterialComponents", []);

module.factory("materialComponents", ["$timeout", "$interval", function ($timeout, $interval) {
        var isRunning = false;
        
        return {
            upgradeDom: function (element) {
                componentHandler.upgradeDom();
                $timeout(function () {
                    componentHandler.upgradeDom();
                }, 100);
            }
        };
    }]);