/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//Angular code
(function () {
    //Application module

    angular.module('pharmacyApp').controller("ProductController", ['$http', '$scope', '$window', '$cookies', 'accessService', 'userConnected', function ($http, $scope, $window, $cookies, accessService, userConnected) {
        $scope.productsArray = new Array();

        this.loadProducts = function () {

            //Server conenction to verify user's data
            var promise = accessService.getData("MainController",
                    true, "POST", {controllerType: 1, action: 10000, JSONData: {products: ''} });

            promise.then(function (outputData) {
                if (outputData[0] === true) {
                    for (var i=0; i<outputData[1].length; i++) {
                        var productObj = new Product();
                        productObj.construct(outputData[1][i].id, outputData[1][i].name, outputData[1][i].price);
                        $scope.productsArray.push(productObj);
                    }
                    $scope.selectedProduct = $scope.productsArray[0];

                } else {
                    if (angular.isArray(outputData[1])) {
                        console.log(outputData);
                    } else {
                        alert("There has been an error in the server, try later");
                    }
                }
            });

        }
        /**
         * This method adds a product to DB and arrayProducts if the product is added
         * @returns {undefined}
         */
        this.addProduct = function (){

            console.log($scope.product);
            $scope.product.id=0;
            $scope.product = angular.copy($scope.product);

            var promise = accessService.getData("MainController",
                    true, "POST", {controllerType: 1, action: 10100, JSONData: JSON.stringify($scope.product)});


            promise.then(function (outputData) {
                console.log("Devuelve outputData");
                if (outputData[0] === true) {

                        var productObj = new Product();
                        productObj.construct(outputData[1][i].id, outputData[1][i].name, outputData[1][i].price);
                        console.log(productObj);
                        /*$scope.productsArray.push(productObj);
                    $scope.selectedProduct = $scope.productsArray[0];*/

                } else {
                    if (angular.isArray(outputData[1])) {
                        console.log(outputData);
                    } else {
                        alert("There has been an error in the server, try later");
                    }
                }
            });
        }
    }]);

})();
