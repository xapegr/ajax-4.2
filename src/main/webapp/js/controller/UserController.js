//Angular code
(function () {
    //Application module

    angular.module('pharmacyApp').controller("UserController", ['$http', '$scope', '$window', '$cookies', 'accessService', 'userConnected', function ($http, $scope, $window, $cookies, accessService, userConnected) {
            //scope variables
            $scope.user = new User();

            $scope.userOption = 0;
            $scope.nickValid = true;
            $scope.passwordValid = true;
            $scope.userType = 0;

            $scope.formats = ['dd-MMMM-yyyy', 'yyyy/MM/dd', 'dd.MM.yyyy', 'shortDate'];
            $scope.format = $scope.formats[0];
            $scope.dateOptions = {
                dateDisabled: "",
                formatYear: 'yyyy',
                maxDate: new Date(),
                minDate: "",
                startingDay: 1
            };

            $scope.birthDate = {
                opened: false
            };

            $scope.openBirthDate = function () {
                $scope.birthDate.opened = true;
            };

            this.connection = function ()
            {
                //copy
                $scope.user = angular.copy($scope.user);

                //Server conenction to verify user's data
                var promise = accessService.getData("MainController",
                        true, "POST", {controllerType: 0, action: 10000, JSONData: JSON.stringify($scope.user)});

                promise.then(function (outputData) {
                    if (outputData[0] === true) {
                        //User correct, mainWindow is opened.
                        sessionStorage.userConnected = JSON.stringify(outputData[1][0]);
                        window.open("mainWindow.html", "_self");
                    } else {
                        if (angular.isArray(outputData[1])) {
                            console.log(outputData);
                        } else {
                            alert("There has been an error in the server, try later");
                        }
                    }
                });
            }

            this.checkNick = function ()
            {

            }

            this.userManagement = function () {
                switch ($scope.userOption)
                {
                    //User entry: index.html
                    case 1:
                        var imageFile = $("#imageUser")[0].files[0];

                        var imagesArrayToSend = new FormData();
                        imagesArrayToSend.append('images[]', imageFile);
                        //imagesArrayToSend['images[]']

                        $http({
                            method: 'POST',
                            url: 'MainController?controllerType=1&action=10000&userNick=' + $scope.user.getNick(),
                            headers: {'Content-Type': undefined},
                            data: imagesArrayToSend,
                            transformRequest: function (data, headersGetterFunction) {
                                return data;
                            }
                        }).success(function (outPutData) {
                            if (outPutData[0] === true) {
                                //File uploaded
                                $scope.user.setImage(outPutData[1][0]);
                                $scope.user.setActive(1);


                                $scope.user = angular.copy($scope.user);



                            } else
                            {

                                if (angular.isArray(outPutData[1]))
                                {
                                    showErrors(outPutData[1]);
                                } else {
                                    alert("There has been an error in the server, try later");
                                }
                            }
                        });
                        break;
                    case 2:

                        break;
                    default:
                        alert("There has been an error, try later")
                        console.log("user action not correcte: " + $scope.userOption);
                        break;
                }
            }

            $scope.setFile = function (element) {
                $scope.currentFile = element.files[0];
                var reader = new FileReader();

                reader.onload = function (event) {
                    $scope.userImage = event.target.result
                    $scope.$apply();
                }

                // when the file is read it triggers the onload event above.
                reader.readAsDataURL(element.files[0]);
            }
        }]);

    angular.module('pharmacyApp').directive("userDataManagement", function () {
        return {
            restrict: 'E',
            templateUrl: "view/templates/user-data-management.html",
            controller: function () {

            },
            controllerAs: 'userDataManagement'
        };
    });

    angular.module('pharmacyApp').directive('file', function () {
        return {
            scope: {
                file: '='
            },
            link: function (scope, el, attrs) {
                el.bind('change', function (event) {
                    var files = event.target.files;
                    var file = files[0];
                    scope.file = file ? file.name : undefined;
                    scope.$apply();
                });
            }
        };
    });

})();
