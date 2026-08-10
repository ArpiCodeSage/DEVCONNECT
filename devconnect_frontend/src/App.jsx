import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Navbar from './components/Navbar';
import { TreePine, ArrowRight, Sparkles, Coffee } from 'lucide-react';

const Home = () => (
  <div className="relative min-h-[85vh] flex flex-col items-center justify-center text-center px-4 overflow-hidden rounded-3xl m-4 border border-[#3b322c]">
    <div 
      className="absolute inset-0 bg-cover bg-center bg-no-repeat scale-105 transition-transform duration-1000"
      style={{ 
        backgroundImage: `url('https://images.unsplash.com/photo-1542273917363-3b1817f69a2d?q=80&w=2074&auto=format&fit=crop')` 
      }}
    />

    <div className="absolute inset-0 bg-gradient-to-t from-[#161210] via-[#161210]/85 to-[#161210]/60 backdrop-blur-[2px]" />

    <div className="relative z-10 animate-fade-up flex flex-col items-center max-w-2xl mx-auto py-12 px-6 rounded-3xl bg-[#241e1b]/80 border border-[#443831] shadow-2xl backdrop-blur-md">
      
      <div className="inline-flex items-center gap-2 px-4 py-1.5 rounded-full bg-[#322a25] border border-[#52463e] text-[#d97706] text-xs font-sans font-semibold mb-6 shadow-md">
        <Coffee size={14} className="text-[#d97706]" />
        <span>cozy developer retreat</span>
        <Sparkles size={13} className="text-[#d97706]" />
      </div>

      <h1 className="text-4xl sm:text-5xl font-serif font-normal text-[#f3eae1] leading-tight mb-5 tracking-tight">
        Craft your portfolio in a <br />
        <span className="italic text-transparent bg-clip-text bg-gradient-to-r from-[#d97706] via-[#f59e0b] to-[#10b981]">
          warm lo-fi atmosphere ☕
        </span>
      </h1>

      <p className="text-[#c4b5fd]/90 font-sans text-base sm:text-lg max-w-md mb-8 leading-relaxed font-medium">
        A quiet cabin space to showcase developer projects, write dev notes, and connect over coffee.
      </p>

      <div className="flex items-center gap-4 font-sans">
        <a 
          href="/feed" 
          className="px-6 py-3 rounded-xl bg-[#d97706] hover:bg-[#b45309] text-white font-bold text-sm flex items-center gap-2 shadow-lg shadow-[#d97706]/20 hover:scale-105 transition-all"
        >
          <span>explore projects</span>
          <ArrowRight size={16} />
        </a>
        <a 
          href="/register" 
          className="px-6 py-3 rounded-xl bg-[#161210]/80 hover:bg-[#322a25] border border-[#443831] text-[#e5d5c5] font-medium text-sm hover:border-[#d97706]/50 transition-all"
        >
          join cabin 🌲
        </a>
      </div>
    </div>
  </div>
);

function App() {
  return (
    <Router>
      <div className="min-h-screen bg-[#161210] text-[#f3eae1] relative">
        <Navbar />
        <main>
          <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/feed" element={<Home />} />
            <Route path="/login" element={<div className="text-center mt-20 text-[#9e8c7c]"><h2>Login Page (Day 8)</h2></div>} />
            <Route path="/register" element={<div className="text-center mt-20 text-[#9e8c7c]"><h2>Register Page (Day 8)</h2></div>} />
            <Route path="/profile/:username" element={<div className="text-center mt-20 text-[#9e8c7c]"><h2>Profile Page (Day 9)</h2></div>} />
          </Routes>
        </main>
      </div>
    </Router>
  );
}

export default App;