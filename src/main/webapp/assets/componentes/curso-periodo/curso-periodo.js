var curso = angular.module("Curso", []);

curso.controller("CursoController",
        ["$scope",
            "materialComponents",
            "titleService",
            "fabService",
            "snackbarManagerService",
            "filterManagerService",
            "cursoService",
            "paginacao",
            function ($scope,
                    materialComponents,
                    titleService,
                    fabService,
                    snackbarManagerService,
                    filterManagerService,
                    cursoService,
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
                            if(dados.ativo == "on")
                                dados.ativo = true;
                            cursoService.salvar(function (list) { // DONE  
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
                            if(dados.ativo == "on")
                                dados.ativo = true;
                            cursoService.editar(function (list) { // DONE  
                                console.debug(list);   
                                carregar(1);
                                $scope.dialogs.editar.get().close();
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
                        btnSalvarTexto: "Editar",
                        carregando: false
                    }
                };

                $scope.cadastro = {};
                $scope.edicao = {};
                
                titleService.set("Curso");

                fabService.onClick($scope, function () {
                    //snackbarManagerService.show("kljdklasajdklsjadlksj ", 2, null, null);
                    $scope.dialogs.cadastro.get().showModal();
                });

                filterManagerService.onFilter($scope, function (data) {
                    console.error(data);
                });

                var carregar = function (pagina) {
                    paginacao.setLoading(pagina, true);
                    
                    cursoService.getList(function (list) { // DONE  
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

curso.factory("cursoService", ["$ajax", function ($ajax) {
        return {
            getList: function (done, error, always, pagina) {
                $ajax.post("Curso/getList", {numeroPagina: pagina}, done, error, always);
            },
            salvar: function(done, error, always, dados){
                $ajax.post("Curso/add", {nome: dados.nome, apelido: dados.apelido, ativo: dados.ativo, periodo: dados.periodo}, done,error,always);
            },
            editar: function(done, error, always, dados){
                $ajax.post("Curso/edit", {id: dados.id , nome: dados.nome, apelido: dados.apelido, ativo: dados.ativo, periodo: dados.periodo}, done,error,always);
            }
        };
    }]);