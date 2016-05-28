var app = angular.module("Ajax", []);

app.factory("$ajax", ["$http", function ($http) {
        var getMessage = function (messages) {
            var msg = "";
            if (!messages) {
                return msg;
            }


            $(messages).each(function (i, m) {
                msg += m;

                if (msg[msg.length - 1] == ".") {
                    msg += " ";
                } else {
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
                    response.data.messages = ["Não foi possível contator o servidor para realizar a ação. Verifique sua conexão com a internet e tente novamente."];
                    
                    error(response.data.result, response.data.messages, getMessage(response.data.messages));
                    if (always)
                        always(response.data.result, response.data.messages, getMessage(response.data.messages));
                });
            },
            get: function (url, done, error, always) {
                $http.get(
                        url
                        ).then(function (response) {
                    if (response.data.success) {
                        done(response.data.result, response.data.messages, getMessage(response.data.messages));
                    } else {
                        error(response.data.result, response.data.messages, getMessage(response.data.messages));
                    }

                    if (always)
                        always(response.data.result, response.data.messages, getMessage(response.data.messages));
                }, function (response) {
                    var message = "Não foi possível contator o servidor para realizar a ação. Verifique sua conexão com a internet e tente novamente.";
                    
                    error(null, message, message);
                    if (always)
                        always(response.data.result, response.data.messages, getMessage(response.data.messages));
                });
            }
        };
    }]);