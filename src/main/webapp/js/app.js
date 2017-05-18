// jQuery code
$(document).ready(function () {

});

// Angular code
(function () {
    var pharmacyApp = angular.module('pharmacyApp', ['ng-currency', 'ui.bootstrap', 'ngCookies', 'angularUtils.directives.dirPagination']);

    pharmacyApp.factory('userConnected', function () {
        // I know this doesn't work, but what will?
        var user = new User();
        return user;
    });


    pharmacyApp.directive('validFile', function () {
        return {
            require: 'ngModel',
            link: function (scope, el, attrs, ctrl) {
                ctrl.$setValidity('validFile', el.val() != '');
                //change event is fired when file is selected
                el.bind('change', function () {
                    ctrl.$setValidity('validFile', el.val() != '');
                    scope.$apply(function () {
                        ctrl.$setViewValue(el.val());
                        ctrl.$render();
                    });
                });
            }
        }
    });

    pharmacyApp.factory('accessService', function ($http, $log, $q) {
        return {
            getData: function (url, async, method, params, data) {
                var deferred = $q.defer();
                $http({
                    url: url,
                    method: method,
                    asyn: async,
                    params: params,
                    data: data
                })
                        .success(function (response, status, headers, config) {
                            deferred.resolve(response);
                        })
                        .error(function (msg, code) {
                            deferred.reject(msg);
                            $log.error(msg, code);
                            alert("There has been an error in the server, try later");
                        });

                return deferred.promise;
            }
        }
    });


    pharmacyApp.directive("purchaseManagement", function () {
        return {
            restrict: 'E',
            templateUrl: "view/templates/purchase-management.html",
            controller: function () {

            },
            controllerAs: 'purchaseManagement'
        };
    });
})();
