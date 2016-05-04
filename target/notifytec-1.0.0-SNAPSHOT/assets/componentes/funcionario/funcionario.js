var funcionario = angular.module("Funcionario", []);

funcionario.controller("FuncionarioController",
        ["$scope",
            "materialComponents",
            "titleService",
            "fabService",
            "snackbarManagerService",
            "filterManagerService",
            function ($scope,
                    materialComponents,
                    titleService,
                    fabService,
                    snackbarManagerService,
                    filterManagerService) {

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

                materialComponents.upgradeDom();
            }]);