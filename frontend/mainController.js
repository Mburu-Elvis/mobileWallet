var app = angular.module("myApp", ['ngRoute']);

app.config(function ($routeProvider) {
    $routeProvider
    .when("/", {
        templateUrl: 'register.html',
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
    .otherwise({
        redirect: "/"
    });
})