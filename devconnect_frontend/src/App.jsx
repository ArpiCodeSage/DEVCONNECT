import React, { useState } from 'react';
import { BrowserRouter as Router, Routes, Route, useNavigate } from 'react-router-dom';
import Navbar from './components/Navbar';
import RegisterPage from './pages/RegisterPage';
import LoginPage from './pages/LoginPage';
import { ArrowRight, Code2, Search } from 'lucide-react';
import ProfilePage from './pages/ProfilePage';
import EditProfilePage from './pages/EditProfilePage';
import ProjectForm from './components/ProjectForm';
import ProjectFeedPage from './pages/ProjectFeedPage';
import BlogFeedPage from "./pages/BlogFeedPage";
import BlogFormPage from "./pages/BlogFormPage";
import BlogDetailPage from "./pages/BlogDetailPage";
import SearchPage from "./pages/SearchPage";


const Home = () => {

  const navigate = useNavigate();
  const [search, setSearch] = useState("");

  const handleSearch = (e) => {
    e.preventDefault();

    const query = search.trim();

    if (!query) return;

    navigate(`/search?q=${encodeURIComponent(query)}`);
  };


  return (
    <div className="relative min-h-[85vh] w-full flex flex-col items-center justify-center text-center px-4 overflow-hidden">

      {/* Background */}
      <div className="fixed inset-0 pointer-events-none opacity-30 z-0">

        <svg
          className="w-full h-full"
          xmlns="http://www.w3.org/2000/svg"
        >

          <defs>

            <pattern
              id="fullPipeGridMatrix"
              width="80"
              height="80"
              patternUnits="userSpaceOnUse"
            >

              <path
                d="M 0 40 H 80"
                fill="none"
                stroke="#475569"
                strokeWidth="2"
              />

              <path
                d="M 40 0 V 80"
                fill="none"
                stroke="#475569"
                strokeWidth="2"
              />

              <path
                d="M 0 40 H 80"
                fill="none"
                stroke="#06b6d4"
                strokeWidth="2"
                className="pipeline-path"
              />

              <path
                d="M 40 0 V 80"
                fill="none"
                stroke="#6366f1"
                strokeWidth="2"
                className="pipeline-path"
              />

              <circle
                cx="40"
                cy="40"
                r="4.5"
                fill="#6366f1"
                stroke="#0b0f19"
                strokeWidth="1.5"
              />

            </pattern>

          </defs>

          <rect
            width="100%"
            height="100%"
            fill="url(#fullPipeGridMatrix)"
          />

        </svg>

      </div>


      {/* Main Content */}
      <div className="relative z-10 flex flex-col items-center py-8 px-6 max-w-3xl mx-auto my-auto bg-[#0b0f19]/80 backdrop-blur-sm border-y border-slate-800/80">

        {/* Badge */}
        <div className="inline-flex items-center gap-2 px-3 py-1 bg-slate-900 border border-slate-800 text-cyan-400 text-xs font-mono mb-5">

          <Code2
            size={14}
            className="text-indigo-400"
          />

          <span>
            Developer Portfolio Showcase Platform
          </span>

        </div>


        {/* Heading */}
        <h1 className="text-4xl sm:text-5xl font-extrabold text-white leading-tight mb-4 tracking-tight">

          Showcase your projects & <br />

          <span className="bg-gradient-to-r from-indigo-400 to-cyan-400 bg-clip-text text-transparent">
            build your developer portfolio
          </span>

        </h1>


        {/* Description */}
        <p className="text-slate-400 text-sm sm:text-base max-w-md mb-6 leading-relaxed">

          A platform for software engineers to publish portfolio showcases,
          explore tech stacks, and connect with developers worldwide.

        </p>


        {/* Explore Projects Button */}
        <a
          href="/feed"
          className="px-5 py-2.5 bg-indigo-600 hover:bg-indigo-500 text-white font-medium text-sm flex items-center gap-2 transition"
        >

          <span>
            Explore Projects Feed
          </span>

          <ArrowRight size={16} />

        </a>


        {/* SEARCH BAR */}
        <form
          onSubmit={handleSearch}
          className="relative w-full max-w-xl mt-6"
        >

          <Search
            size={19}
            className="absolute left-4 top-1/2 -translate-y-1/2 text-slate-500"
          />

          <input
            type="text"
            value={search}
            onChange={(e) => setSearch(e.target.value)}
            placeholder="Search developers, skills, or projects..."
            className="w-full bg-[#111827] border border-slate-800 rounded-xl pl-11 pr-4 py-3.5 text-white placeholder-slate-500 outline-none focus:border-cyan-500/60 transition"
          />

        </form>

      </div>

    </div>
  );
};


function App() {

  return (

    <Router>

      <div className="min-h-screen bg-[#0b0f19] text-slate-100 font-sans relative">

        <Navbar />

        <main>

          <Routes>

            <Route
              path="/"
              element={<Home />}
            />

            <Route
              path="/feed"
              element={<ProjectFeedPage />}
            />

            <Route
              path="/login"
              element={<LoginPage />}
            />

            <Route
              path="/register"
              element={<RegisterPage />}
            />

            <Route
              path="/profile/edit"
              element={<EditProfilePage />}
            />

            <Route
              path="/profile/:username"
              element={<ProfilePage />}
            />

            <Route
              path="/projects/create"
              element={<ProjectForm />}
            />

            <Route
              path="/blogs"
              element={<BlogFeedPage />}
            />

            <Route
              path="/search"
              element={<SearchPage />}
            />

            <Route
              path="/blogs/create"
              element={<BlogFormPage />}
            />

            <Route
              path="/blogs/:id"
              element={<BlogDetailPage />}
            />

            <Route
              path="/blogs/edit/:id"
              element={<BlogFormPage />}
            />

          </Routes>

        </main>

      </div>

    </Router>

  );
}


export default App;