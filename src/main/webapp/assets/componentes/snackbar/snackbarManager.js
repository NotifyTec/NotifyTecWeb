var module = angular.module("SnackbarManager", []);

module.factory("snackbarManagerService", ["materialComponents", function (materialComponents) {
        var show = function (message, timeout, actionHandler, actionText) {
            var data = {
                message: message,
                timeout: timeout ? timeout * 1000 : 2000,
                actionHandler: actionHandler,
                actionText: actionText
            };

            materialComponents.upgradeDom();

            var $snack = document.querySelector("#snackbar");
            if (!$snack)
            {
                console.error("snackbarManagerService.show() : Você precisa inserir a tag '<snackbar></snackbar>' no HTML do módulo que o fará uso. De nada! ;)");
                return;
            }

            $snack.MaterialSnackbar.showSnackbar(data);
        };

        var showSalvo = function () {
            show("Registro salvo com sucesso", 3, null, null);
        };

        var showExcluido = function () {
            show("Registro excluido com sucesso", 3, null, null);
        };

        var showEditado = function () {
            show("Registro editado com sucesso", 3, null, null);
        };

        var showErro = function (mensagem) {
            show(mensagem, 10, null, null);
        };

        return {
            show: show,
            showSalvo: showSalvo,
            showExcluido: showExcluido,
            showEditado: showEditado,
            showErro: showErro
        };
    }]);

module.directive("snackbar", function () {
    return {
        restrict: "E",
        templateUrl: "assets/componentes/snackbar/snackbar.html"
    };
});