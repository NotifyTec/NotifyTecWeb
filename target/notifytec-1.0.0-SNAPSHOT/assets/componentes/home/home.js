var module = angular.module("Home", []);

module.controller("HomeController",
        ["$scope",
            "materialComponents",
            "titleService",
            "snackbarManagerService",
            "LoginService",
            "$location",
            function ($scope,
                    materialComponents,
                    titleService,
                    snackbarManagerService,
                    loginService,
                    $location) {
                $scope.credentials = {
                    user: "",
                    password: ""
                };

                $scope.actions = {
                    btnLoginClick: function () {
                        $scope.actions.lockInputs = true;
                        $scope.actions.btnLoginLock = true;
                        $scope.actions.btnLoginText = "Entrando...";

                        loginService.login(function (data, messages, message) {
                            snackbarManagerService.show("Sucesso no login" + data.token, 5, null, null);
                            localStorage["token"] = data.token;
                            $location.path("/funcionario");
                        }, function (data, messages, message) {                            
                            $scope.actions.error = message;
                        }, function (data, messages, message) {
                            $scope.actions.lockInputs = false;
                            $scope.actions.btnLoginLock = false;
                            $scope.actions.btnLoginText = "Entrar";
                        }, $scope.credentials.user, $scope.credentials.password);
                    },
                    btnLoginText: "Entrar",
                    btnLoginLock: false,
                    lockInputs: false,
                    btnForgotPasswordText: "Esqueceu a senha? Acesse pelo aplicativo para recuperá-la.",
                    error: ""
                };
                
                
                titleService.set("Realize a autenticação para acessar o sistema");
                materialComponents.upgradeDom();
            }]);

module.factory("LoginService", ["$ajax", function ($ajax) {
        return {
            login: function (done, error, always, userName, password) {
                $ajax.post(
                        "Login/Login",
                        {
                            userName: userName,
                            password: password
                        }, done, error, always);
            }
        };
    }]);

module.factory("$ajax", ["$http", function ($http) {
        var getMessage = function(messages){
            var msg = "";
            if(!messages){
                return msg;
            }
            
            
            $(messages).each(function(i, m){
                msg += m;
                
                if(msg[msg.length - 1] == "."){
                    msg += " ";
                }else{
                    msg += ". ";
                }
            });
            
            return msg;
        };
        
        return {
            post: function (url, data, done, error, always) {
                $http.post(
                        url,
                        data
                        ).then(function (response) {
                    if (response.data.success) {
                        done(response.data.result, response.data.messages, getMessage(response.data.messages));
                    } else {
                        error(response.data.result, response.data.messages, getMessage(response.data.messages));
                    }

                    if (always)
                        always(response.data.result, response.data.messages, getMessage(response.data.messages));
                }, function (response) {
                    error(response.data.result, response.data.messages, getMessage(response.data.messages));
                    if (always)
                        always(response.data.result, response.data.messages, getMessage(response.data.messages));
                });
            }
        };
    }]);

