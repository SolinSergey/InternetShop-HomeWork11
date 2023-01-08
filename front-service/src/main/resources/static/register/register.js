angular.module('market').controller('registerController', function ($scope, $http,$location) {
    $scope.register = function () {
        $http.post('http://localhost:7777/auth/register', $scope.userdata)
            .then(function successCallback(response) {

                $location.path('/');

            }, function errorCallback(response) {
                console.log(response);
                alert(response.data.message);
            });
    };
});