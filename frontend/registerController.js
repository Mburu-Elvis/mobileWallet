app.controller("RegisterController", ["$scope", "$http", "$location", function($scope, $http, $location) {

    $scope.registerForm = {};

    $scope.register = function() {

        console.log($scope.sendForm)
        $http({
            method: 'POST',
            headers: {
                'ngrok-skip-browser-warning': 'hello',
                'Content-Type': 'application/json'
            },
            data: $scope.registerForm,
            url: 'https://stallion-holy-informally.ngrok-free.app/api/v1/register',
        }).then(function(response) {
            // Success callback
            console.log('register:', response);
	        $scope.registerForm = {};
	        $location.path('/Dashboard');
        }, function(error) {
            // Error callback
            console.error('Error fetching accounts:', error);
	    $scope.registerForm = {};
        });
    };

     $scope.signIn = function() {

            console.log($scope.sendForm)
            $http({
                method: 'POST',
                headers: {
                    'ngrok-skip-browser-warning': 'hello',
                    'Content-Type': 'application/json'
                },
                data: $scope.registerForm,
                url: 'https://stallion-holy-informally.ngrok-free.app/api/v1/signin',
            }).then(function(response) {
                // Success callback
                console.log('register:', response);
    	        $scope.registerForm = {};
    	        $location.path('/Dashboard');
            }, function(error) {
                // Error callback
                console.error('Error fetching accounts:', error);
    	        $scope.registerForm = {};
            });
        };
}]);