<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html ng-app="notifytec-app">
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <!--Material Design Lite-->
        <link href="assets/css/material.min.css" rel="stylesheet" type="text/css"/>
        <script src="assets/js/material.min.js" type="text/javascript"></script>
        <link href="assets/css/material-icon.css" rel="stylesheet" type="text/css"/>

        <!--AngularJS-->
        <script src="assets/js/jquery-2.1.4.min.js" type="text/javascript"></script>
        <script src="assets/js/jquery-functions.js" type="text/javascript"></script>
        <script src="assets/js/angular.min.js" type="text/javascript"></script>
        <script src="assets/js/angular-route.js" type="text/javascript"></script>
        <script src="assets/js/ngStorage.js" type="text/javascript"></script>

        <!--SERVICES-->
        <script src="assets/componentes/services/app.js" type="text/javascript"></script>        
        <script src="assets/componentes/services/routes.js" type="text/javascript"></script>
        <script src="assets/componentes/services/materialComponents.js" type="text/javascript"></script>
        <script src="assets/componentes/services/titleManager.js" type="text/javascript"></script>
        <script src="assets/componentes/services/authorization.js" type="text/javascript"></script>
        <script src="assets/componentes/fab/fab.js" type="text/javascript"></script>
        <script src="assets/componentes/snackbar/snackbarManager.js" type="text/javascript"></script>
        <script src="assets/componentes/filter/filter.js" type="text/javascript"></script>
        <script src="assets/componentes/menu/menu.js" type="text/javascript"></script>
        <script src="assets/componentes/services/ajax.js" type="text/javascript"></script>
        <script src="assets/componentes/paginacao/paginacao.js" type="text/javascript"></script>
        <script src="assets/componentes/ui-utils-0.2.3/ui-utils.min.js" type="text/javascript"></script>
        <script src="assets/componentes/ui-utils-0.2.3/ui-utils-ieshiv.min.js" type="text/javascript"></script>
        
        <!--CONTROLLERS-->
        <script src="assets/componentes/home/home.js" type="text/javascript"></script>
        <script src="assets/componentes/funcionario/funcionario.js" type="text/javascript"></script>
        <script src="assets/componentes/curso-periodo/curso-periodo.js" type="text/javascript"></script>
        <script src="assets/componentes/semestre/semestre.js" type="text/javascript"></script>
        <script src="assets/componentes/departamento/departamento.js" type="text/javascript"></script>
        <!--CSS-->
        <link href="assets/componentes/home/home.css" rel="stylesheet" type="text/css"/>
        <title>

        </title>
    </head>
    <body>
        
        
        <div class="mdl-layout mdl-js-layout mdl-layout--fixed-drawer
             mdl-layout--fixed-header">
            <header class="mdl-layout__header">
                <div class="mdl-layout__header-row">
                    <div class="mdl-layout-spacer">
                        <div id="title"></div>
                    </div>                    
                </div>
            </header>
            <div class="mdl-layout__drawer">
                <span class="mdl-layout-title">NotifyTec</span>
                
                <verticalmenu></verticalmenu>
                
            </div>
            <main class="mdl-layout__content">
                <div class="page-content">
                    <ng-view></ng-view>
                </div>                
            </main>
        </div>
    </body>
</html>