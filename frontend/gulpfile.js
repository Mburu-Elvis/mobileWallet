const gulp = require('gulp');
const concat = require('gulp-concat');
const uglify = require('gulp-uglify');
const cleanCSS = require('gulp-clean-css');
const rename = require('gulp-rename');
const htmlmin = require('gulp-htmlmin');

// Task to minify HTML
function minifyHtml() {
    return gulp.src(['index.html', 'register.html', 'signIn.html', 'transactions.html', 'account.html', 'dashboard.html'])
        .pipe(htmlmin({ collapseWhitespace: true }))
        .pipe(gulp.dest('dist'));
}

// Task to compile and minify SASS
function compileSass() {
    return gulp.src(['styles.scss', 'dashboard.scss', 'signin.scss']) // Adjust file extensions if necessary
        .pipe(sass().on('error', sass.logError))
        .pipe(cleanCSS())
        .pipe(rename({ suffix: '.min' }))
        .pipe(gulp.dest('dist/styles'));
}

// Task to concatenate and minify JS
function minifyScripts() {
    return gulp.src(['mainController.js', 'accountController.js', 'dashboardController.js', 'registerController.js', 'transactionsController.js', 'userService.js'])
        .pipe(concat('app.js'))
        .pipe(uglify())
        .pipe(rename({ suffix: '.min' }))
        .pipe(gulp.dest('dist/scripts'));
}

// Define the build task
exports.build = gulp.series(minifyHtml, minifyScripts);

// Default task
exports.default = exports.build;