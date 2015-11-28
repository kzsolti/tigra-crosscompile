'use strict';

angular.module('crossCompileApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('ugylet', {
                parent: 'entity',
                url: '/ugylets',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Ugylets'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/ugylet/ugylets.html',
                        controller: 'UgyletController'
                    }
                },
                resolve: {
                }
            })
            .state('ugylet.detail', {
                parent: 'entity',
                url: '/ugylet/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Ugylet'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/ugylet/ugylet-detail.html',
                        controller: 'UgyletDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Ugylet', function($stateParams, Ugylet) {
                        return Ugylet.get({id : $stateParams.id});
                    }]
                }
            })
            .state('ugylet.new', {
                parent: 'ugylet',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/ugylet/ugylet-dialog.html',
                        controller: 'UgyletDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    ugyletszam: null,
                                    ugyfel: null,
                                    tajszam: null,
                                    beerkezesIdeje: null,
                                    mellekletSzam: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('ugylet', null, { reload: true });
                    }, function() {
                        $state.go('ugylet');
                    })
                }]
            })
            .state('ugylet.edit', {
                parent: 'ugylet',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/ugylet/ugylet-dialog.html',
                        controller: 'UgyletDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Ugylet', function(Ugylet) {
                                return Ugylet.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('ugylet', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('ugylet.delete', {
                parent: 'ugylet',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/ugylet/ugylet-delete-dialog.html',
                        controller: 'UgyletDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Ugylet', function(Ugylet) {
                                return Ugylet.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('ugylet', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
