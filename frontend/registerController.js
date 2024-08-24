app.controller("RegisterController", ["$scope", "$http", "$location", "UserService", function($scope, $http, $location, UserService) {

    $scope.registerForm = {};
    $scope.url = 'https://mobilewallet.onrender.com/api/v1';

    $scope.register = function() {

        console.log($scope.sendForm)
        $http({
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            data: $scope.registerForm,
            url: $scope.url + '/register',
        }).then(function(response) {
            // Success callback
	        $scope.registerForm = {};
	        $location.path('/Dashboard');
        }, function(error) {
            // Error callback
            console.error('Error fetching accounts:', error);
	    $scope.registerForm = {};
        });
    };

     $scope.signIn = function() {

            console.log($scope.registerForm)
            $http({
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                data: $scope.registerForm,
                url: $scope.url + '/signin',
            }).then(function(response) {
                // Success callback
                console.log('SignIn:', response);
    	        $scope.registerForm = {};
//    	        UserService.clearUser();
    	        UserService.setUser(response.data);
    	        $location.path('/Dashboard');
            }, function(error) {
                // Error callback
                console.error('Error fetching accounts:', error);
    	        $scope.registerForm = {};
            });
        };
}]);