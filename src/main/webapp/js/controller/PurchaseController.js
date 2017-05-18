/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//Angular code
(function () {
    //Application module

    angular.module('pharmacyApp').controller("PurchaseController", ['$http', '$scope', '$window', '$cookies', 'accessService', 'userConnected', function ($http, $scope, $window, $cookies, accessService, userConnected) {
        $scope.purchase = new Purchase();
        $scope.purchasesArray = new Array();
        
        //Scope variables
        $scope.showForm = 0;
        $scope.specialRequests = ["Dlivery at the main hospital", "Fragil material, must be sended in a special vehicle", "Product easily contamined, special protection nedded"];
        
        //Date pickers scope variables and functions
        $scope.minDeliveryDate = new Date((new Date()).setDate((new Date()).getDate() + 1));
        $scope.formats = ['dd-MMMM-yyyy', 'yyyy/MM/dd', 'dd.MM.yyyy', 'shortDate'];
        $scope.format = $scope.formats[0];
        $scope.dateOptions = {
            dateDisabled: "",
            formatYear: 'yyyy',
            maxDate: "",
            minDate: $scope.minDeliveryDate,
            startingDay: 1
        };

        $scope.deliveryDate = {
            opened: false
        };

        $scope.openDeliveryDate = function () {
            $scope.deliveryDate.opened = true;
        };
        
        $scope.specialReqMng = function (indexChecked) {
            if($("#specialReq"+indexChecked).is(":checked")) {
                $scope.purchase.addSpecialRequests($scope.specialRequests[indexChecked]);
            } else {
                $scope.purchase.removeSpecialRequests($scope.specialRequests[indexChecked]);
            }
        }
        
        this.addPurchase = function () {
            $scope.purchase.setIdUser($scope.$parent.idUser); //Get user id from session controller
            $scope.purchase.setIdProduct($scope.selectedProduct.id);
            $scope.purchase = angular.copy($scope.purchase);

            // Server conenction to verify user's data.
            var promise = accessService.getData("MainController",
                    true, "POST", {controllerType: 2, action: 10000, JSONData: JSON.stringify($scope.purchase)});

            promise.then(function (outputData) {
                
                if (outputData[0] === true) {
                    var purchaseObj = new Purchase();
                    purchaseObj.construct(outputData[1].id, $scope.$parent.idUser, $scope.selectedProduct.id, $scope.purchase.deliveryDate, $scope.purchase.specialRequests, $scope.purchase.specialInstructions);
                } else {
                    if (angular.isArray(outputData[1])) {
                        console.log(outputData);
                    }
                }
            });
        }
        
    }]);

})();

