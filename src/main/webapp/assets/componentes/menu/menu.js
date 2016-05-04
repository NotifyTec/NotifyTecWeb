var module = angular.module("Menu", []);

module.directive("verticalmenu", function(){
    return {
        restrict: "E",
        templateUrl: "assets/componentes/menu/menu.html"
    };
});