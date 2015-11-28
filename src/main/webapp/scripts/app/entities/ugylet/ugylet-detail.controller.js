'use strict';

angular.module('crossCompileApp')
    .controller('UgyletDetailController', function ($scope, $rootScope, $stateParams, entity, Ugylet) {
        $scope.ugylet = entity;
        $scope.load = function (id) {
            Ugylet.get({id: id}, function(result) {
                $scope.ugylet = result;
            });
        };
        var unsubscribe = $rootScope.$on('crossCompileApp:ugyletUpdate', function(event, result) {
            $scope.ugylet = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
