//Angular code
(function () {
    //Application module

    angular.module('pharmacyApp').controller("SessionController", ['$http', '$scope', '$window', '$cookies', 'accessService', 'userConnected', function ($http, $scope, $window, $cookies, accessService, userConnected) {
        //scope variables
        $scope.user = new User();
        $scope.userAction = 0;
        $scope.userType = 0;
        $scope.idUser = 0;

        this.sessionControl = function () {
            //copy
            $scope.user = angular.copy($scope.user);

            switch ($scope.userAction) {

                case 0: //index.html Management
                    // If the session is open wi will have to go to mainWindow.html
                    // otherwise we will remain in the index.html

                    // Server conenction to verify user's data.
                    var promise = accessService.getData("MainController",
                            true, "POST", {controllerType: 0, action: 10400, JSONData: JSON.stringify($scope.user)});

                    promise.then(function (outputData) {
                        if (outputData[0] === true) {
                            // Login correct, mainWindow is opened.
                            window.open("mainWindow.html", "_self");
                        } else {
                            if (angular.isArray(outputData[1])) {
                                console.log(outputData);
                            }
                        }
                    });
                    break;

                case 1: //mainWindow.html Management
                    // Server conenction to verify user's data.
                    var promise = accessService.getData("MainController",
                            true, "POST", {controllerType: 0, action: 10400, JSONData: JSON.stringify($scope.user)});

                    promise.then(function (outputData) {
                        if (outputData[0] === true) {
                            // Login correct, mainWindow is opened.
                            $scope.sessionOpened = true;
                            $scope.userType = JSON.stringify(outputData[1].userType);
                            $scope.idUser = JSON.stringify(outputData[1].id);
                            //console.log("idUser: "+$scope.idUser);
                        } else {
                            if (angular.isArray(outputData[1])) {
                                console.log(outputData);
                                window.open("index.html", "_self");
                            }
                        }
                    });
                    break;
                default:
                    console.log("user action not correcte: " + $scope.userAction);
                    alert("There has been an error, try later")
                    break;

            }
        }

        this.logOut = function () {
            //Local session destroy
            // Server conenction to verify user's data.
            var promise = accessService.getData("MainController",
                    true, "POST", {controllerType: 0, action: 10500, JSONData: {}});

            promise.then(function (outputData) {
                if (outputData[0] === true) {
                    // Logout correct, mainWindow is opened.
                    $scope.sessionOpened = false;
                    sessionStorage.removeItem("userConnected");
                    window.open("index.html", "_self");

                } else {
                    if (angular.isArray(outputData[1])) {
                        console.log(outputData);
                    } else {
                        alert("There has been an error in the server, try again later.");
                    }
                }
            });
        }
    }]);
})();
