define('routes', ['./app'], function (app) {
    'use strict';
    return angular.module('app')
        .config(['$routeProvider', function ($routeProvider) {
            $routeProvider
                .when('/welcome', {
                    templateUrl: 'app/welcome/welcome.html'
                })
                .otherwise({redirectTo: '/welcome'});
        }]);
});
