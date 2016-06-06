define([
    'angular',
    'angularRoute'
], function (angular) {
    'use strict';

    var app = angular.module('app', [
            'ngRoute'])
        .run(run);

    return app;
});
