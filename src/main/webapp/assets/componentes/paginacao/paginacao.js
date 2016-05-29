var paginacao = angular.module("Paginacao", []);

paginacao.directive("paginacao", function () {
    return {
        restrict: "E",
        templateUrl: "assets/componentes/paginacao/paginacao.html"
    };
});

paginacao.factory("paginacao", ["materialComponents", function (materialComponents) {
        var p = null;
        var callback = null;
        var $scope = null;

        var getRegistrosPorPagina = function () {
            return p.registrosPorPagina;
        };

        var getTotalDeRegistros = function () {
            return p.totalDeRegistros;
        };

        var gerarPaginacao = function () {
            $scope.paginacao = {
                callback: function (pagina) {
                    callback(pagina);
                },
                getRegistros: function () {
                    return p.registros;
                },
                getRegistrosPorPagina: getRegistrosPorPagina,
                getTotalDeRegistros: getTotalDeRegistros,
                html: {
                    previousText: "",
                    previousOnClick: function () {
                        callback(p.pagina - 1);
                    },
                    nextText: "",
                    nextOnClick: function () {
                        callback(p.pagina + 1);
                    },
                    previousDisabled: false,
                    nextDisabled: false
                }
            };

            var total = Math.ceil(getTotalDeRegistros() / getRegistrosPorPagina());
            $scope.paginacao.paginas = [];

            for (var i = 1; i <= total; i++) {
                $scope.paginacao.paginas.push({
                    numero: i,
                    onClick: callback.bind(this, i),
                    carregando: false,
                    selecionado:  i == p.pagina
                });
            }

            if (total > 3) {
                $scope.paginacao.html.previousText = "Anterior";
                $scope.paginacao.html.nextText = "Próximo";

                if (p.pagina == total) {
                    $scope.paginacao.html.nextDisabled = true;
                }

                if (p.pagina == 1) {
                    $scope.paginacao.html.previousDisabled = true;
                }
            }

            return $scope.paginacao.paginas;
        };

        return {
            set: function (scope, paginacao, onPageChange) {
                p = paginacao;
                callback = onPageChange;
                $scope = scope;

                gerarPaginacao();
            },
            setLoading: function(n, loading){                
                if(!$scope || !$scope.paginacao || !$scope.paginacao.paginas)
                    return;
                
                for (var i = 0; i < $scope.paginacao.paginas.length; i++) {
                    if($scope.paginacao.paginas[i].numero == n || !loading){
                       $scope.paginacao.paginas[i].carregando = loading == true;                        
                       materialComponents.upgradeDom();
                    }
                }
            }
        };
    }]);