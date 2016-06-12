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
                            $scope.dialogs.cadastro.carregando= true ;
                            var dados = $("#form-cadastro").getFormData();
                            departamentoService.salvar(function (list) { // DONE  
                                console.debug(list);   
                                carregar(1);
                                $scope.dialogs.cadastro.get().close();
                                //TODO> Carregar novo dado
                                
                            }, function (result, messageError) { // ERROR                    
                                snackbarManagerService.show(messageError, 20, null, null);
                            }, function () {// ALWAYS
                                $scope.dialogs.cadastro.carregando= false ;
                                paginacao.setLoading(null, false);
                            }, dados);

                            console.debug(dados);
                        },
                        bloquear: false,
                        btnSalvarTexto: "Salvar",
                        carregando: false
                    },
                    editar:{
                        get: function () {
                            return document.querySelector("#editar-dialog")
                        },
                        fechar: function () {
                            $scope.dialogs.editar.get().close();
                        },
                        openModal: function(item){
                            $("[name='form-editar']").find(".mdl-textfield").addClass("is-dirty").removeClass("is-invalid");
                            $scope.edicao = item;
                            $scope.dialogs.editar.get().showModal();
                        },
                        salvar: function () {
                            $scope.dialogs.editar.carregando= true ;
                            var dados = $scope.edicao;
                            departamentoService.editar(function (list) { // DONE  
                                console.debug(list);   
                                carregar(1);
                                $scope.dialogs.editar.get().close();
                                
                            }, function (result, messageError) { // ERROR                    
                                snackbarManagerService.show(messageError, 20, null, null);
                            }, function () {// ALWAYS
                                $scope.dialogs.cadastro.carregando= false ;
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
                    }, function (result, messageError) { // ERROR                    
                        snackbarManagerService.show(messageError, 20, null, null);
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
            salvar: function(done, error, always, dados){
                $ajax.post("Departamento/add", {nome: dados.nome}, done,error,always);
            },
            editar: function(done, error, always, dados){
                $ajax.post("Departamento/edit", {id: dados.id , nome: dados.nome}, done,error,always);
            }
        };
    }]);