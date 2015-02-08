'use strict';

angular.module('acg.index_view', ['ngRoute'])

    .config(['$routeProvider', function ($routeProvider) {
        $routeProvider.when('/', {
            templateUrl: contextPath + '/partials/index_view',
            controller: 'IndexViewCtrl'
        });
    }])

    .controller('IndexViewCtrl', ['$scope', '$rootScope', '$http', function ($scope, $rootScope, $http) {
        $scope.loginForm = {username: '', password: ''};
        $scope.loginFormError = null;
        $scope.login = _.throttle(function () {
            $http.post(contextPath + '/api/login', $scope.loginForm).success(function (data) {
                console.log(data);
                if (data.success) {
                    $scope.loginFormError = null;
                    $rootScope.currentUser = data.data;
                } else {
                    $scope.loginFormError = data.errorMessage;
                }
            });
        }, 3000);
        $scope.logout = function () {
            $http.get(contextPath + '/api/logout').success(function (data) {
                $rootScope.currentUser = null;
            });
        }
    }]);