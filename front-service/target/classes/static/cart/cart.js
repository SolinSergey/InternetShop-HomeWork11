angular.module('market').controller('cartController', function ($scope, $http, $localStorage) {
    $scope.loadCart = function () {
        $http.get('http://localhost:7777/cart/api/v1/cart/' + $localStorage.marchMarketGuestCartId)
            .then(function (response) {
                $scope.cart = response.data;
                console.info($scope.cart);
            });
    };

    $scope.createOrder = function () {
        $http.post('http://localhost:7777/core/api/v1/orders/create',$scope.deliveryadress)
            .then(function (response) {
                $scope.loadCart();
            });
    }

    $scope.addProductAmount=function (id){
        $http.get('http://localhost:7777/cart/api/v1/cart/'+ $localStorage.marchMarketGuestCartId+'/add/'+id)
            .then(function (response){
                $scope.loadCart();
            });
    };

    $scope.subProductAmount=function (id){
        $http.get('http://localhost:7777/cart/api/v1/cart/'+ $localStorage.marchMarketGuestCartId+'/sub/'+id)
            .then(function (response){
                $scope.loadCart();
            });
    };

    $scope.removeItem=function (id){
        $http.delete('http://localhost:7777/cart/api/v1/cart/'+ $localStorage.marchMarketGuestCartId+'/remove/'+id)
            .then(function (response){
                $scope.loadCart();
            });

    };

    $scope.guestCreateOrder = function () {
        alert('Для оформления заказа необходимо войти в учетную запись');
    }

    $scope.loadCart();
});