 'use strict';

angular.module('crossCompileApp')
    .factory('notificationInterceptor', function ($q, AlertService) {
        return {
            response: function(response) {
                var alertKey = response.headers('X-crossCompileApp-alert');
                if (angular.isString(alertKey)) {
                    AlertService.success(alertKey, { param : response.headers('X-crossCompileApp-params')});
                }
                return response;
            }
        };
    });
