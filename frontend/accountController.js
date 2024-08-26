app.controller("AccountController", ["$scope", "$http", "UserService", function($scope, $http, UserService) {
    let user = UserService.getUser();
    $scope.updateForm = {};


    $scope.updateProfile = function() {
        $scope.updateForm.name = user.name;
        $scope.updateForm.email = user.email;
        $scope.updateForm.number = user.phoneNumber;
    }
}]);