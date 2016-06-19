var departamento = angular.module("Departamento", []);

departamento.controller("DepartamentoController",
        ["$scope",
            "materialComponents",
            "titleService",
            "fabService",
            "snackbarManagerService",
            "filterManagerService",
            "departamentoService",
            "paginacao",
            function ($scope,
                    materialComponents,
                    titleService,
                    fabService,
                    snackbarManagerService,
                    filterManagerService,
                    departamentoService,
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
                            $scope.dialogs.cadastro.carregando = true;
                            var dados = $("#form-cadastro").getFormData();
                            departamentoService.salvar(function (list) { // DONE  
                                console.debug(list);
                                carregar(1);
                                snackbarManagerService.showSalvo();
                                $scope.dialogs.cadastro.get().close();
                                //TODO> Carregar novo dado

                            }, function (result, messages, messageError) { // ERROR                    
                                snackbarManagerService.showErro(messageError);
                            }, function () {// ALWAYS
                                $scope.dialogs.cadastro.carregando = false;
                                paginacao.setLoading(null, false);
                            }, dados);

                            console.debug(dados);
                        },
                        bloquear: false,
                        btnSalvarTexto: "Salvar",
                        carregando: false
                    },
                    editar: {
                        get: function () {
                            return document.querySelector("#editar-dialog")
                        },
                        fechar: function () {
                            $scope.dialogs.editar.get().close();
                        },
                        openModal: function (item) {
                            $("[name='form-editar']").find(".mdl-textfield").addClass("is-dirty").removeClass("is-invalid");
                            $scope.edicao = item;
                            $scope.dialogs.editar.get().showModal();
                        },
                        remove: function (item) {
                            departamentoService.remover(function (list) { // DONE                                  
                                carregar(1);
                                snackbarManagerService.showExcluido();
                            }, function (result, messages, messageError) { // ERROR                    
                                snackbarManagerService.showErro(messageError);
                            }, function () {// ALWAYS                               
                                paginacao.setLoading(null, false);
                            }, item);
                        },
                        salvar: function () {
                            $scope.dialogs.editar.carregando = true;
                            var dados = $scope.edicao;
                            departamentoService.editar(function (list) { // DONE  
                                console.debug(list);
                                carregar(1);
                                $scope.dialogs.editar.get().close();
                                snackbarManagerService.showEditado();

                            }, function (result, messages, messageError) { // ERROR                    
                                snackbarManagerService.showErro(messageError);
                            }, function () {// ALWAYS
                                $scope.dialogs.editar.carregando = false;
                                paginacao.setLoading(null, false);
                            }, dados);

                            console.debug(dados);
                        },
                        bloquear: false,
                        btnSalvarTexto: "Editar",
                        carregando: false
                    }
                };

                $scope.cadastro = {};
                $scope.edicao = {};
                $scope.formularioValido = function () {
                    var inputs = $("[name='form']").find("input");
                    var result = $.grep(inputs, function (i) {
                        if (!$(i).hasClass("naoobrigatorio"))
                            return $(i).val() == "";
                    }).length != 0;
                    return result;
                };
                $scope.formularioValidoEdicao = function () {
                    var inputs = $("[name='form-editar']").find("input");
                    var result = $.grep(inputs, function (i) {
                        if (!$(i).hasClass("naoobrigatorio"))
                            return $(i).val() == "";
                    }).length != 0;
                    return result;
                };
                titleService.set("Departamento");

                fabService.onClick($scope, function () {
                    //snackbarManagerService.show("kljdklasajdklsjadlksj ", 2, null, null);
                    $scope.dialogs.cadastro.get().showModal();
                });

                filterManagerService.onFilter($scope, function (data) {
                    console.error(data);
                });

                var carregar = function (pagina) {
                    paginacao.setLoading(pagina, true);

                    departamentoService.getList(function (list) { // DONE  
                        console.debug(list);
                        $scope.list = list.result;
                        paginacao.set($scope, list, carregar);
                    }, function (result, messages, messageError) { // ERROR                    
                        snackbarManagerService.showErro(messageError);
                    }, function () {// ALWAYS
                        paginacao.setLoading(null, false);
                    }, pagina);
                };


                carregar(1);
                materialComponents.upgradeDom();
            }]);

departamento.factory("departamentoService", ["$ajax", function ($ajax) {
        return {
            getList: function (done, error, always, pagina) {
                $ajax.post("Departamento/getList", {numeroPagina: pagina}, done, error, always);
            },
            salvar: function (done, error, always, dados) {
                $ajax.post("Departamento/add", {nome: dados.nome}, done, error, always);
            },
            editar: function (done, error, always, dados) {
                $ajax.post("Departamento/edit", {id: dados.id, nome: dados.nome}, done, error, always);
            },
            remover: function (done, error, always, dados) {
                $ajax.post("Departamento/remove", {id: dados.id}, done, error, always);
            }
        };
    }]);