require.config({

    paths: {
        'angular': '../node_modules/angular/angular',
        'angularRoute': '../node_modules/angular-route/angular-route',
        'domReady': '../node_modules/requirejs-domready/domReady'
    },

    shim: {
        'angular': {
            exports: 'angular'
        },
        'angularRoute': {
            deps: ['angular']
        }
    },

    deps: [
        './bootstrap'
    ]
});