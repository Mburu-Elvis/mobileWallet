app.controller("DashboardController", ["$scope", "$http", "$route", "UserService", "$location", function($scope, $http, $route, UserService, $location) {
    $scope.sendForm = {};
    $scope.url = 'https://mobilewallet.onrender.com/api/v1';
//    if (UserService.isAuthenticated()) {
//        $scope.user = UserService.getUser();
//        return $location.path("/Dashboard");
//        // Now you can use $scope.user to make API calls or display user data
//    } else {
//        console.log("redirecting");
//        return $location.path("/SignIn");
//    }
    $scope.sendMoney = function() {

        $scope.sendForm.from = $scope.user.phoneNumber;
        console.log($scope.sendForm)
        $http({
            method: 'POST',
            headers: {
                'ngrok-skip-browser-warning': 'hello',
                'Content-Type': 'application/json'
            },
            data: $scope.sendForm,
            url: $scope.url+'/payments/transfer',
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

    $scope.test = function() {
        console.log("TEST 1");
    }

    $scope.depositMoney = function() {

        $scope.sendForm.from = $scope.user.phoneNumber;
        $scope.sendForm.to = $scope.user.phoneNumber;
        console.log("CLICKED");
        $http({
            method: 'POST',
            headers: {
                'ngrok-skip-browser-warning': 'hello',
                'Content-Type': 'application/json'
            },
            data: $scope.sendForm,
            url: $scope.url + '/payments/deposit',
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

            $scope.sendForm.from = $scope.user.phoneNumber;
            $scope.sendForm.to = $scope.user.phoneNumber;
            console.log($scope.sendForm)
            $http({
                method: 'POST',
                headers: {
                    'ngrok-skip-browser-warning': 'hello',
                    'Content-Type': 'application/json'
                },
                data: $scope.sendForm,
                url: $scope.url + '/payments/withdraw',
            }).then(function(response) {
                // Success callback
                console.log('Accounts:', response);
            $scope.sendForm = {};
            }, function(error) {
                // Error callback
                console.error('Error fetching accounts:', error);
            $scope.sendForm = {};
            });
        };

        $scope.getBalance = function() {
            $http({
                method: 'GET',
                headers: {
                    'ngrok-skip-browser-warning': 'hello',
                    'Content-Type': 'application/json'
                },
                url: $scope.url + '/accounts/balance/' + $scope.user.phoneNumber,
            }).then(function(response) {
                // Success callback
                console.log('Accounts:', response);
                $scope.user.balance = response.data.balance;
                console.log($scope.user.balance);
            }, function(error) {
                // Error callback
                console.error('Error fetching accounts:', error);
            });
        };
       $scope.getBalance();
}]);