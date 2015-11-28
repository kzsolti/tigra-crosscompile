'use strict';

angular.module('crossCompileApp')
    .controller('UgyletController', function ($scope, $state, $modal, Ugylet) {
      
        $scope.ugylets = [];
        $scope.loadAll = function() {
            Ugylet.query(function(result) {
               $scope.ugylets = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.ugylet = {
                ugyletszam: null,
                ugyfel: null,
                tajszam: null,
                beerkezesIdeje: null,
                mellekletSzam: null,
                id: null
            };
        };
    });
