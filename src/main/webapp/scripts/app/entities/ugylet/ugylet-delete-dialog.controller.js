'use strict';

angular.module('crossCompileApp')
	.controller('UgyletDeleteController', function($scope, $modalInstance, entity, Ugylet) {

        $scope.ugylet = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Ugylet.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });