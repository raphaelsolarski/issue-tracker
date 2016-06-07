define([
    'angular',
    'angularRoute',
    'dashboard/index'
], function (angular) {
    'use strict';

    var app = angular.module('app', [
            'ngRoute',
            'app.dashboard'])
        .run(run);

    return app;
});
