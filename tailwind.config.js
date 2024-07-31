/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    './src/main/resources/templates/footer/*.html',
    './src/main/resources/templates/header/*.html',
    './src/main/resources/templates/main/*.html',
    './src/main/resources/templates/forms/*.html',
    './src/main/resources/static/js/**/*.js',
  ],
  theme: {
    extend: {},
  },
  plugins: [],
};
