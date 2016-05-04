var module = angular.module("Fab", []);

module.directive("fab", function () {
    return {
        restrict: "E",
        templateUrl: "assets/componentes/fab/fab.html"
    };
});

module.factory("fabService", function () {
    var onClick = function (scope, f) {
        scope.onFabClick = f;
    };

    return {
        onClick: onClick
    };
});