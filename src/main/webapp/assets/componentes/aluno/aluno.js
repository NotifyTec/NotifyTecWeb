var aluno = angular.module("Aluno", []);

aluno.controller("AlunoController",
        ["$scope",
            "materialComponents",
            "titleService",
            "fabService",
            "snackbarManagerService",
            "filterManagerService",
            "alunoService",
            "paginacao",
            function ($scope,
                    materialComponents,
                    titleService,
                    fabService,
                    snackbarManagerService,
                    filterManagerService,
                    alunoService,
                    paginacao) {

                $scope.list = [];
                $scope.error = "";
                $scope.periodo = null;
                $scope.periodos = null;
                $scope.semestre = null;
                $scope.semestres = null;
                $scope.depts = null;
                $scope.alunoPeriodoAdd = null;
                $scope.listAlunoPeriodo = [];

                $scope.dialogs = {
                    cadastro: {
                        get: function () {
                            return document.querySelector("#cadastro-dialog");
                        },
                        fechar: function () {
                            $scope.dialogs.cadastro.get().close();
                        },
                        salvar: function () {
                            var dados = $("#form-cadastro").getFormData();
                            dados.ativo = $("#switch-2").prop("checked");
                            $scope.dialogs.cadastro.carregando= true ;
                            if($scope.validarModal(dados.cpf) == false) {
                                snackbarManagerService.show("CPF Inválido!", 2, null, null);
                                return;
                            }
                            dados.departamento = $scope.dept;  
                            alunoService.salvar(function (list) { // DONE  
                                if(!list.success){
                                    var message = "";
                                    var listMessage = list.messages;
                                    $(listMessage).each(function(i, item){
                                        message += " " + item;
                                    });
                                    snackbarManagerService.show(message, 20, null, null);
                                }
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
                            //CHAMAR ADD
                         
                            console.debug(dados);
                        },
                        bloquear: false,
                        btnSalvarTexto: "Salvar",
                        carregando: false,
                        erro: ""
                    },
                    editar: {
                        get: function () {
                            return document.querySelector("#editar-dialog")
                        },
                        fechar: function () {
                            $scope.dialogs.editar.get().close();
                        },
                        openModal: function(item){                            
                            $("[name='form-edit']").find(".mdl-textfield").addClass("is-dirty").removeClass("is-invalid");
                            $("#departamento-edit").val(item.departamentoId);
                            $scope.edicao = item;
                            $scope.dialogs.editar.get().showModal();
                        },
                        salvar: function () {
                            $scope.dialogs.editar.carregando= true ;
                            var dados = $scope.edicao;
                            dados.departamento = $("#departamento-edit").val();
                            dados.ativo = $("#switch-2-edit").prop("checked");                             
                            if($scope.validarModal(dados.cpf) == false) {
                                snackbarManagerService.show("CPF Inválido!", 2, null, null);
                                return;
                            }                            
                             alunoService.editar(function (list) { // DONE  
                                if(!list.success){
                                    var message = "";
                                    var listMessage = list.messages;
                                    $(listMessage).each(function(i, item){
                                        message += " " + item;
                                    });
                                    snackbarManagerService.show(message, 20, null, null);
                                }
                                console.debug(list);                                 
                                $scope.dialogs.editar.get().close();
                                carregar(1);
                            }, function (result, messageError) { // ERROR                    
                                snackbarManagerService.show(messageError, 20, null, null);
                            }, function () {// ALWAYS
                                $scope.dialogs.editar.carregando= false ;
                                paginacao.setLoading(null, false);
                            }, dados);
                          
                           
                            carregar(1);
                            console.debug(dados);
                        },
                        bloquear: false,
                        btnSalvarTexto: "Salvar",
                        carregando: false,
                        erro: ""
                    }
                };
                $scope.addAlunoPeriodo=function (){
                    var periodoID = $("#periodo-select-add").val();
                    var periodoNome=$("#periodo-select-add").find("option[value='"+$("#periodo-select-add").val()+"']").text(); 
                    var semestreID = $("#semestre-select-add").val();
                    var semestreNome = $("#semestre-select-add").find("option[value='"+$("#semestre-select-add").val()+"']").text();
                    
                    var objeto = {idperiodo: periodoID, nomeperiodo:periodoNome, idsemestre: semestreID, nomesemestre: semestreNome};
                    $scope.listAlunoPeriodo.push(objeto);
                };                
                
                $scope.removeperiodoaluno = function(item){
                    $scope.listAlunoPeriodo = jQuery.grep($scope.listAlunoPeriodo, function(value) {
                        return value != item;
                      });
                };
                
                $scope.cadastro = {};
                $scope.edicao = {};
                //*---------------------------------------------------------------------*//
                
                $scope.formularioValido = function () {
                   var inputs = $("[name='form']").find("input,select");
                   return $.grep(inputs, function (i) {
                       return $(i).val() == "" || $(i).val() == "? object:null ?";
                   }).length != 0;
               };
                $scope.formularioValidoEdicao = function () {
                   var inputs = $("[name='form']").find("input,select");
                   return $.grep(inputs, function (i) {
                       return $(i).val() == "" || $(i).val() == "? object:null ?";
                   }).length != 0;
               };
               


                $scope.loadPeriodos = function() {
                      alunoService.getPeriodos(function (list) { // DONE  
                                console.debug(list); 
                                var lista = [];
                                $(list).each(function(i,item){
                                    $(item.listPeriodo).each(function(o,object){
                                        lista.push(object);
                                    });
                                });
                                
                                
                                
                                $scope.periodos = lista;
                            }, function (result, messageError) { // ERROR                    
                                snackbarManagerService.show(messageError, 20, null, null);
                            }, function () {// ALWAYS
                               
                            });
                      return $scope.depts;
                };
               
                $scope.loadSemestres = function() {
                      alunoService.getSemestre(function (list) { // DONE  
                                console.debug(list); 
                                $scope.semestres = list.result;
                            }, function (result, messageError) { // ERROR                    
                                snackbarManagerService.show(messageError, 20, null, null);
                            }, function () {// ALWAYS
                               
                            });
                      return $scope.depts;
                };
                $scope.validarModal = function(cpf){
                    return $scope.validaCPF(cpf) == true;
                }
                
                $scope.validaCPF = function (cpf) {
                    if (cpf == '' || cpf == null)
                        return false;

                    cpf = cpf.replace(/[^\d]+/g, '');

                    if (cpf.length != 11)
                        return false;

                    // Elimina CPFs invalidos conhecidos    
                    if (cpf == "00000000000" ||
                            cpf == "11111111111" ||
                            cpf == "22222222222" ||
                            cpf == "33333333333" ||
                            cpf == "44444444444" ||
                            cpf == "55555555555" ||
                            cpf == "66666666666" ||
                            cpf == "77777777777" ||
                            cpf == "88888888888" ||
                            cpf == "99999999999")
                        return false;
                    // Valida 1o digito 
                    add = 0;
                    for (i = 0; i < 9; i ++)
                        add += parseInt(cpf.charAt(i)) * (10 - i);
                    rev = 11 - (add % 11);
                    if (rev == 10 || rev == 11)
                        rev = 0;
                    if (rev != parseInt(cpf.charAt(9)))
                        return false;
                    // Valida 2o digito 
                    add = 0;
                    for (i = 0; i < 10; i ++)
                        add += parseInt(cpf.charAt(i)) * (11 - i);
                    rev = 11 - (add % 11);
                    if (rev == 10 || rev == 11)
                        rev = 0;
                    if (rev != parseInt(cpf.charAt(10)))
                        return false;
                    return true;
                }
                
               //*--------------------------------------------------------------------------*//
                titleService.set("Aluno");

                fabService.onClick($scope, function () {
                    //snackbarManagerService.show("kljdklasajdklsjadlksj ", 2, null, null);
                    $scope.dialogs.cadastro.get().showModal();
                });

                filterManagerService.onFilter($scope, function (data) {
                    /*if(data.nome == "" || data.nome == null){
                        filterManagerService.setLockButton($scope,false);
                        carregar(1);
                        return;
                    } */
                    
                   alunoService.getByFilter(function (list) { // DONE  
                        //console.debug(list);
                        $scope.list = list.result;
                        filterManagerService.setLockButton($scope,false);
                        
                    }, function (result, messageError) { // ERROR                    
                        snackbarManagerService.show(messageError, 20, null, null);
                    }, function () {// ALWAYS
                        paginacao.setLoading(null, false);
                    }, data);
                });

                var carregar = function (pagina) {
                    paginacao.setLoading(pagina, true);
                    
                    alunoService.getList(function (list) { // DONE  
                        console.debug(list);
                        $scope.list = list.result;
                        paginacao.set($scope, list, carregar);
                        filterManagerService.setLockButton($scope,false);
                    }, function (result, messageError) { // ERROR                    
                        snackbarManagerService.show(messageError, 20, null, null);
                    }, function () {// ALWAYS
                        paginacao.setLoading(null, false);
                    }, pagina);
                };
                carregar(1);
                materialComponents.upgradeDom();
            }]);

aluno.factory("alunoService", ["$ajax", function ($ajax) {
        return {
            getList: function (done, error, always, pagina) {
                $ajax.post("Aluno/getList", {numeroPagina: pagina}, done, error, always);
            },
            salvar: function(done, error, always, dados){
                $ajax.post("Aluno/add", {nome: dados.nome, sobrenome: dados.sobrenome, cpf: dados.cpf, apelido: dados.apelido, departamentoId: dados.departamento, ativo: dados.ativo, email: dados.email}, done,error,always);
            },
            editar: function(done, error, always, dados){
                $ajax.post("Aluno/edit", {id: dados.id ,nome: dados.nome, sobrenome: dados.sobrenome, cpf: dados.cpf, apelido: dados.apelido, departamentoId: dados.departamento, ativo: dados.ativo, email: dados.email,usuarioID: dados.usuarioId}, done,error,always);
            },
            getByFilter: function(done,error,always,dados){
                $ajax.post("Aluno/getByFilter",{nome:dados.nome, ra:dados.ra, cpf:dados.cpf, email:dados.email,ativo: dados.ativo},done,error,always)
            },
            getPeriodos: function(done,error,always,dados){
                $ajax.post("Aluno/getPeriodos",{ativo:true},done,error,always)
            },
            getSemestre: function(done,error,always,dados){
                $ajax.post("Aluno/getSemestre",{ativo:true},done,error,always)
            },
            
        };
    }]);