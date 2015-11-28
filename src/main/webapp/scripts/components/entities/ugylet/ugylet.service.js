'use strict';

angular.module('crossCompileApp')
    .factory('Ugylet', function ($resource, DateUtils) {
        return $resource('api/ugylets/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.beerkezesIdeje = DateUtils.convertLocaleDateFromServer(data.beerkezesIdeje);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.beerkezesIdeje = DateUtils.convertLocaleDateToServer(data.beerkezesIdeje);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.beerkezesIdeje = DateUtils.convertLocaleDateToServer(data.beerkezesIdeje);
                    return angular.toJson(data);
                }
            },
            'validator': {
                method: 'GET',
                url: 'api/ugylets/validator',
                isArray: false
            }
        });
    });
