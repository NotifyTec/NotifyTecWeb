var module = angular.module("FilterManager", []);

module.factory("filterManagerService", ["materialComponents", function (materialComponents) {
        var get = function () {
            var $content = $("#filter-content");
            var valores = {};

            $content.find("input").each(function (i, input) {
                var $input = $(input);
                var name = $input.attr("name");
                
                if (!name)
                    console.error("filterManagerService.get() : É preciso colocar um NOME nos inputs do filtro.");

                var valor = $input.val();

                if($input.attr("type") == "checkbox"){
                    valor = $input.prop("checked");
                }

                valores[name] = valor;
            });

            return valores;
        };

        var onFilter = function ($scope, f) {
            if (!$scope) {
                console.error("filterManagerService.onFilter() : Deve ser passado o escopo como parâmetro.");
                return;
            }

            if (!f) {
                console.error("filterManagerService.onFilter() : Deve ser passado uma função como parâmetro.");
                return;
            }
            
            $scope.filter = {
                lock: false
            };

            $scope.filter.onFilterClick = function () {
                f(get());                
                setLockButton($scope, true);
            };
            
            setButtonText($scope, "Filtrar");
            
            materialComponents.upgradeDom();
        };

        var setLockButton = function($scope, lock){
            $scope.filter.lock = lock;
            setButtonText($scope, lock ? "Filtrando..." : "Filtrar");
        };

        var setButtonText = function($scope, text){
            $scope.filter.text = text;
        };

        return {
            onFilter: onFilter,
            get: get
        };
    }]);

module.directive("filter", function () {
    return {
        restrict: "E",
        templateUrl: "assets/componentes/filter/filter.html",
        transclude: true
    };
});