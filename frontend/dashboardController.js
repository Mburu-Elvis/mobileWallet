app.controller("DashboardController", ["$scope", "$http", "$route", function($scope, $http, $route) {
    $scope.sendForm = {};
    
    
    $scope.sendMoney = function() {
        $http({
            method: 'POST',
            headers: {
                'ngrok-skip-browser-warning': 'hello',
                'Content-Type': 'application/json'
            },
            data: $scope.sendForm,
            url: 'https://stallion-holy-informally.ngrok-free.app/api/v1/employees',
        }).then(function(response) {
            // Success callback
            console.log('Accounts:', response);
            $scope.refreshAccounts();
	    $scope.sendForm = {};
        }, function(error) {
            // Error callback
            console.error('Error fetching accounts:', error);
            $scope.refreshAccounts();
	    $scope.sendForm = {};
        });
    };
}]);