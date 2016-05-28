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