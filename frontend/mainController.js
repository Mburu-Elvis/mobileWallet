var app = angular.module("myApp", ['ngRoute']);

app.controller("MainController", function($scope, $location) {
    $scope.isSignIn = function() {
        return $location.path() === '/SignIn';
    };
    $scope.isRegister = function() {
        return $location.path() === "/";
    }
    $scope.hideSidebar = function() {
            return $scope.isSignIn() || $scope.isRegister();
       };
});

app.config(function ($routeProvider) {
    $routeProvider
    .when("/", {
        templateUrl: 'register.html',
        controller: 'RegisterController'
    })
    .when('/SignIn', {
        templateUrl: 'signIn.html',
        controller: 'RegisterController'
    })
    .when("/Account", {
        templateUrl: 'account.html',
        controller: 'AccountController'
    })
    .when("/Transactions", {
        templateUrl: 'transactions.html',
        controller: "TransactionController"
    })
    .when("/Logout", {
        templateUrl: 'register.html',
        controller: 'RegisterController'
    })
    .when("/Dashboard", {
        templateUrl: 'dashboard.html',
        controller: 'DashboardController'
    })
    .otherwise({
        redirect: "/"
    });
})