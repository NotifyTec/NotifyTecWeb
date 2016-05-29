var funcionario = angular.module("Funcionario", []);

funcionario.controller("FuncionarioController",
        ["$scope",
            "materialComponents",
            "titleService",
            "fabService",
            "snackbarManagerService",
            "filterManagerService",
            "funcionarioService",
            "paginacao",
            function ($scope,
                    materialComponents,
                    titleService,
                    fabService,
                    snackbarManagerService,
                    filterManagerService,
                    funcionarioService,
                    paginacao) {

                $scope.list = [];
                $scope.error = "";

                $scope.dialogs = {
                    cadastro: {
                        get: function () {
                            return document.querySelector("#cadastro-dialog")
                        },
                        fechar: function () {
                            $scope.dialogs.cadastro.get().close();
                        },
                        salvar: function () {
                            var dados = $("#form-cadastro").getFormData();
                            console.debug(dados);
                        },
                        bloquear: false,
                        btnSalvarTexto: "Salvar",
                        carregando: true,
                        erro: "jsdhsjakdksahdsak"
                    }
                };

                $scope.cadastro = {};

                titleService.set("Funcionário");

                fabService.onClick($scope, function () {
                    snackbarManagerService.show("kljdklasajdklsjadlksj ", 2, null, null);
                    $scope.dialogs.cadastro.get().showModal();
                });

                filterManagerService.onFilter($scope, function (data) {
                    console.error(data);
                });

                var carregar = function (pagina) {
                    paginacao.setLoading(pagina, true);
                    
                    funcionarioService.getList(function (list) { // DONE  
                        console.debug(list);
                        $scope.list = list.result;
                        paginacao.set($scope, list, carregar);
                    }, function (result, messageError) { // ERROR                    
                        snackbarManagerService.show(messageError, 20, null, null);
                    }, function () {// ALWAYS
                        paginacao.setLoading(null, false);
                    }, pagina);
                };


                carregar(1);
                materialComponents.upgradeDom();
            }]);

funcionario.factory("funcionarioService", ["$ajax", function ($ajax) {
        return {
            getList: function (done, error, always, pagina) {
                $ajax.post("Funcionario/getList", {numeroPagina: pagina}, done, error, always);
            }
        };
    }]);