var funcionario = angular.module("Funcionario", []);

funcionario.controller("FuncionarioController",
        ["$scope",
            "materialComponents",
            "titleService",
            "fabService",
            "snackbarManagerService",
            "filterManagerService",
            "funcionarioService",
            function ($scope,
                    materialComponents,
                    titleService,
                    fabService,
                    snackbarManagerService,
                    filterManagerService,
                    funcionarioService) {

                $scope.list = [{nome: "LUCAS", sobrenome: "SOBRENOME", email: "lucas.antevere@gmail.com"}];
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

                funcionarioService.getList(function (list){ // DONE
                    //$scope.list = list;
                }, function(result, messageError){ // ERROR                    
                    snackbarManagerService.show(messageError, 20, null, null);
                }, function(){// ALWAYS
                    
                });

                materialComponents.upgradeDom();
            }]);

funcionario.factory("funcionarioService", ["$ajax", function ($ajax) {
        return {
            getList: function (done, error, always) {
                $ajax.get("Funcionario/GetList", done, error, always);
            }
        };
    }]);