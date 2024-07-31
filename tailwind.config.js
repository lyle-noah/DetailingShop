/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    './src/main/resources/templates//*.html', // Tailwind가 유틸리티 클래스를 찾을 파일 경로
    './src/main/resources/static/js//*.js', // JavaScript 파일 경로를 추가 (필요한 경우)
  ],
  theme: {
    extend: {},
  },
  plugins: [],
};