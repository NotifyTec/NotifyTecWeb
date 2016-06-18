var semestre = angular.module("Semestre", []);

semestre.controller("SemestreController",
        ["$scope",
            "$filter",
            "materialComponents",
            "titleService",
            "fabService",
            "snackbarManagerService",
            "filterManagerService",
            "semestreService",
            "paginacao",
            function ($scope,
                    $filter,
                    materialComponents,
                    titleService,
                    fabService,
                    snackbarManagerService,
                    filterManagerService,
                    semestreService,
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
                            semestreService.salvar(function (list) { // DONE  
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
                            $("[name='form']").find(".mdl-textfield").addClass("is-dirty").removeClass("is-invalid");
                            $scope.edicao = item;
                            var dtInicio = item.inicio;
                            var dtFim = item.fim;
                            
                            //tirar depois
                            console.debug("Item ID" + item.id);
                            console.debug(dtInicio);
                            console.debug(dtFim);
                            //
                            
                            $scope.edicao.id = item.id;
                            $scope.edicao.inicio = $filter('date')(dtInicio, 'dd-MM-yyyy');
                            $scope.edicao.fim = $filter('date')(dtFim, 'dd-MM-yyyy');
                            
                            //tirar depois
                            console.debug($scope.edicao.id);
                            console.debug($scope.edicao.inicio);
                            console.debug($scope.edicao.fim);
                            //
                            $scope.dialogs.editar.get().showModal();
                            carregar(1);
                        },
                        salvar: function () {
                            $scope.dialogs.editar.carregando= true ;
                            var dados = $scope.edicao;
                            if(dados.ativo == "on")
                                dados.ativo = true;
                            semestreService.editar(function (list) { // DONE  
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
                
                titleService.set("Semestre");

                fabService.onClick($scope, function () {
                    //snackbarManagerService.show("kljdklasajdklsjadlksj ", 2, null, null);
                    $scope.dialogs.cadastro.get().showModal();
                    carregar(1);
                });

                filterManagerService.onFilter($scope, function (data) {
                    console.error(data);
                });

                var carregar = function (pagina) {
                    paginacao.setLoading(pagina, true);
                    
                    semestreService.getList(function (list) { // DONE  
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

semestre.factory("semestreService", ["$ajax", function ($ajax) {
        return {
            getList: function (done, error, always, pagina) {
                $ajax.post("Semestre/getList", {numeroPagina: pagina}, done, error, always);
            },
            salvar: function(done, error, always, dados){
                $ajax.post("Semestre/add", {inicio: dados.inicio, fim: dados.fim}, done,error,always);
            },
            editar: function(done, error, always, dados){
                $ajax.post("Semestre/edit", {id: dados.id , inicio: dados.inicio, fim: dados.fim}, done,error,always);
            }
        };
    }]);