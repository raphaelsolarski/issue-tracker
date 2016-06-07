define('routes', ['./app'], function (app) {
    'use strict';
    return angular.module('app')
        .config(['$routeProvider', function ($routeProvider) {
            $routeProvider
                .when('/welcome', {
                    templateUrl: 'app/welcome/welcome.html'
                })
                .when('/dashboard', {
                    templateUrl: 'app/dashboard/dashboard.html',
                    controller: 'DashboardController'
                })
                .otherwise({redirectTo: '/dashboard'});
        }]);
});
