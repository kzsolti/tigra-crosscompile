'use strict';

angular.module('crossCompileApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


