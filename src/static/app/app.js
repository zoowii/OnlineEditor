'use strict';

// Declare app level module which depends on views, and components
angular.module('acg', [
    'ngRoute',
    'acg.index_view'
    //'acg.view2',
    //'acg.version'
]).
    run(['$rootScope', '$http', function ($rootScope, $http) {
        $http.get(contextPath + '/api/current_user').success(function (res) {
            if (res.success) {
                $rootScope.currentUser = res.data;
            } else {
                $rootScope.currentUser = null;
            }
        })
    }]).
    config(['$routeProvider', function ($routeProvider) {
        $routeProvider.otherwise({redirectTo: '/index_view'});
    }]);
