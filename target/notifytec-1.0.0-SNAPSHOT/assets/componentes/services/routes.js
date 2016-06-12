var routes = angular.module("Routes", ["ngRoute"]);

routes.config(function ($routeProvider) {
    $routeProvider
            .when('/funcionario', {
                templateUrl: 'assets/componentes/funcionario/funcionario.html',
                controller: 'FuncionarioController'
            }).when('/curso', {
                templateUrl: 'assets/componentes/curso-periodo/curso-periodo.html',
                controller: 'CursoController'
            }).when('/semestre', {
                templateUrl: 'assets/componentes/semestre/semestre.html',
                controller: 'SemestreController'                
            }).when('/departamento', {
                templateUrl: 'assets/componentes/departamento/departamento.html',
                controller: 'DepartamentoController'                
            }).when('/index', {
        templateUrl: 'assets/componentes/home/home.html',
        controller: 'HomeController'
    }).otherwise({
        redirectTo: '/index'
    });
});