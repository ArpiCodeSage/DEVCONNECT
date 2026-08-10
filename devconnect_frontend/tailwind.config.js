/** @type {import('tailwindcss').Config} */
export default {
  content: [
    "./index.html",
    "./src/**/*.{js,ts,jsx,tsx}",
  ],
  theme: {
    extend: {
      colors: {
        // Our custom palette:
        pinkAccent: "#ec4899",     // Soft Rose Pink
        pinkHover: "#db2777",
        greenAccent: "#10b981",    // Fresh Sage/Emerald Green
        greenHover: "#059669",
        bgCream: "#fdfbf7",        // Warm Cream Background
        cardBg: "#ffffff",         // Clean White Card
        borderCustom: "#f3e8ff",
      }
    },
  },
  plugins: [],
}