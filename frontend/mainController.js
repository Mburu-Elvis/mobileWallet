var app = angular.module("myApp", ['ngRoute']);

app.config(function ($routeProvider) {
    $routeProvider
    .when("/", {
        templateUrl: 'dashboard.html',
        controller: 'DashboardController'
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