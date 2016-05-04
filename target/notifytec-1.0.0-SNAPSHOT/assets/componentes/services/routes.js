var routes = angular.module("Routes", ["ngRoute"]);

routes.config(function ($routeProvider) {
    $routeProvider
            .when('/funcionario', {
                templateUrl: 'assets/componentes/funcionario/funcionario.html',
                controller: 'FuncionarioController'
            }).when('/index', {
        templateUrl: 'assets/componentes/home/home.html',
        controller: 'HomeController'
    }).otherwise({
        redirectTo: '/index'
    });
});