import React from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { Coffee, User, LogOut, Compass, LogIn, UserPlus } from 'lucide-react';

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
    <nav className="m-4 px-6 py-3.5 bg-[#241e1b]/90 backdrop-blur-md border border-[#3b322c] rounded-2xl flex justify-between items-center shadow-xl">
      {/* Brand Logo */}
      <Link to="/" className="flex items-center gap-2.5 text-lg font-bold text-[#f3eae1] hover:opacity-90">
        <div className="w-8 h-8 rounded-xl bg-[#3b322c] border border-[#52463e] flex items-center justify-center text-[#d97706]">
          <Coffee size={18} />
        </div>
        <span className="font-serif">dev<span className="text-[#d97706]">.cabin</span>🌲</span>
      </Link>

      {/* Nav Links */}
      <div className="flex items-center gap-5 text-sm font-sans font-medium">
        <Link to="/feed" className="flex items-center gap-1.5 text-[#c4b5fd] hover:text-[#d97706] transition">
          <Compass size={16} />
          <span>explore</span>
        </Link>

        {token ? (
          <>
            <Link to={`/profile/${username}`} className="flex items-center gap-1.5 text-[#e5d5c5] hover:text-[#d97706] transition">
              <User size={16} />
              <span>{username || 'profile'}</span>
            </Link>
            
            <button 
              type="button" 
              onClick={handleLogout} 
              className="flex items-center gap-1.5 px-3.5 py-1.5 rounded-xl border border-[#4a3f37] hover:bg-[#322a25] text-[#e5d5c5] transition cursor-pointer"
            >
              <LogOut size={15} />
              <span>logout</span>
            </button>
          </>
        ) : (
          <>
            <Link 
              to="/login" 
              className="flex items-center gap-1.5 px-4 py-1.5 rounded-xl border border-[#4a3f37] hover:bg-[#322a25] text-[#e5d5c5] transition"
            >
              <LogIn size={15} />
              <span>login</span>
            </Link>

            <Link 
              to="/register" 
              className="flex items-center gap-1.5 px-4 py-1.5 rounded-xl bg-[#d97706] hover:bg-[#b45309] text-white font-semibold shadow-md shadow-[#d97706]/20 transition"
            >
              <UserPlus size={15} />
              <span>join cabin</span>
            </Link>
          </>
        )}
      </div>
    </nav>
  );
};

export default Navbar;