'use strict';

angular.module('crossCompileApp').controller('UgyletDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Ugylet',
        function($scope, $stateParams, $modalInstance, entity, Ugylet) {

        Ugylet.validator(function (result) {
            $scope.validatorScript = result.data;
            eval('$scope.validator = ' + result.data);
        });

        $scope.ugylet = entity;
        $scope.load = function(id) {
            Ugylet.get({id : id}, function(result) {
                $scope.ugylet = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('crossCompileApp:ugyletUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            var messages = $scope.validator($scope.ugylet);
            alert(messages);
            if ($scope.ugylet.id != null) {
                Ugylet.update($scope.ugylet, onSaveSuccess, onSaveError);
            } else {
                Ugylet.save($scope.ugylet, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
