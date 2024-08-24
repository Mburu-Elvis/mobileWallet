app.factory('UserService', function() {
    var user = JSON.parse(localStorage.getItem('user')) || {};

    return {
        setUser: function(userData) {
            user = userData;
            localStorage.setItem('user', JSON.stringify(user));
        },
        getUser: function() {
            return user;
        },
        clearUser: function() {
            user = null;
            localStorage.removeItem('user');
        },
        isAuthenticated: function() {
            return !!user.username;
        }
    };
});