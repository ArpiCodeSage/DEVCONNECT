import React from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { GitCommit } from 'lucide-react';

const Navbar = () => {
  const navigate = useNavigate();
  const token = localStorage.getItem('token');
  const username = localStorage.getItem('username');

  const handleLogout = () => {
    localStorage.removeItem('token');
    localStorage.removeItem('username');
    navigate('/login');
  };

  return (
    <header className="border-b border-slate-800/60 bg-[#0b0f19]/90 backdrop-blur-md px-8 py-4 flex justify-between items-center sticky top-0 z-50">
      
      {/* ⚡ Perfectly Styled Pipeline Logo */}
      <Link to="/" className="flex items-center gap-2.5 group">
        <div className="w-9 h-9 rounded-xl bg-slate-900 border border-slate-800 flex items-center justify-center text-cyan-400 group-hover:border-cyan-500/50 transition duration-200">
          <GitCommit size={20} className="text-cyan-400" />
        </div>
        <span className="text-xl font-extrabold tracking-tight text-white">
          Dev<span className="bg-gradient-to-r from-indigo-400 to-cyan-400 bg-clip-text text-transparent">Connect</span>
        </span>
      </Link>

      {/* Nav Links Mapped to Theme */}
      <div className="flex items-center gap-6 text-sm font-medium">
        <Link to="/feed" className="text-slate-300 hover:text-white transition">
          Projects
        </Link>

        {token ? (
          <>
            <Link to={`/profile/${username}`} className="text-slate-300 hover:text-white transition">
              {username || 'Profile'}
            </Link>
            
            <button 
              type="button" 
              onClick={handleLogout} 
              className="px-4 py-1.5 rounded-lg border border-slate-700 hover:bg-slate-800 text-slate-300 transition cursor-pointer"
            >
              Logout
            </button>
          </>
        ) : (
          <>
            <Link to="/login" className="text-slate-300 hover:text-white transition">
              Login
            </Link>

            <Link 
              to="/register" 
              className="px-4 py-1.5 rounded-lg bg-indigo-600 hover:bg-indigo-500 text-white font-medium shadow-md shadow-indigo-600/20 transition"
            >
              Register
            </Link>
          </>
        )}
      </div>
    </header>
  );
};

export default Navbar;