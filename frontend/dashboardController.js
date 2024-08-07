app.controller("DashboardController", ["$scope", "$http", "$route", function($scope, $http, $route) {
    $scope.sendForm = {};
    
    
    $scope.sendMoney = function() {

        $scope.sendForm.from = "0701888380";
        console.log($scope.sendForm)
        $http({
            method: 'POST',
            headers: {
                'ngrok-skip-browser-warning': 'hello',
                'Content-Type': 'application/json'
            },
            data: $scope.sendForm,
            url: 'https://stallion-holy-informally.ngrok-free.app/api/v1/payments/transfer',
        }).then(function(response) {
            // Success callback
            console.log('Accounts:', response);
            $scope.refreshAccounts();
	    $scope.sendForm = {};
        }, function(error) {
            // Error callback
            console.error('Error fetching accounts:', error);
	    $scope.sendForm = {};
        });
    };

    $scope.depositMoney = function() {

            $scope.sendForm.from = "0701888380";
            $scope.sendForm.to = "0701888380";
            console.log($scope.sendForm)
            $http({
                method: 'POST',
                headers: {
                    'ngrok-skip-browser-warning': 'hello',
                    'Content-Type': 'application/json'
                },
                data: $scope.sendForm,
                url: 'https://stallion-holy-informally.ngrok-free.app/api/v1/payments/deposit',
            }).then(function(response) {
                // Success callback
                console.log('Accounts:', response);
                $scope.refreshAccounts();
    	    $scope.sendForm = {};
            }, function(error) {
                // Error callback
                console.error('Error fetching accounts:', error);
    	    $scope.sendForm = {};
            });
        };

        $scope.withdrawMoney = function() {

                    $scope.sendForm.from = "0701888380";
                    $scope.sendForm.to = "0701888380";
                    console.log($scope.sendForm)
                    $http({
                        method: 'POST',
                        headers: {
                            'ngrok-skip-browser-warning': 'hello',
                            'Content-Type': 'application/json'
                        },
                        data: $scope.sendForm,
                        url: 'https://stallion-holy-informally.ngrok-free.app/api/v1/payments/withdraw',
                    }).then(function(response) {
                        // Success callback
                        console.log('Accounts:', response);
                        $scope.refreshAccounts();
            	    $scope.sendForm = {};
                    }, function(error) {
                        // Error callback
                        console.error('Error fetching accounts:', error);
            	    $scope.sendForm = {};
                    });
                };
}]);