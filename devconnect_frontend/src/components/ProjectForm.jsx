import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { ArrowLeft, Plus } from "lucide-react";
import API from "../axios";

function ProjectForm(){
    const navigate=useNavigate()
    const [formData,setFormData]=useState({
        title:'',
        description:'',
        techStack:'',
        githubUrl:'',
        demoUrl:''
    })
    const [error,setError]=useState('')
    const [loading,setLoading]=useState(false)
    
    const handleChange=(e)=>{
        setFormData({
            ...formData,
            [e.target.name]:e.target.value
        })
        

    }

    const handleSubmit=async (e)=>{
        e.preventDefault()
        setError('')
        setLoading(true)
        
        try{
            const response=await API.post("/projects",formData)
            const username=localStorage.getItem("username")
            navigate(`/profile/${username}`)
        }
        catch(err)
        {
            setError(err.response?.data?.message || "Failed to create the project.Please try again")
        }
        finally{
            setLoading(false)
        }

    }


 return (
    <div className="min-h-screen bg-[#0b0f19] px-4 py-10">
      <div className="max-w-3xl mx-auto">

        {/* Back */}
        <button
          onClick={() => navigate(-1)}
          className="flex items-center gap-2 text-slate-400 hover:text-white text-sm mb-6 transition"
        >
          <ArrowLeft size={16} />
          Back
        </button>

        {/* Form Card */}
        <div className="bg-[#111827] border border-slate-800 p-6 sm:p-8">

          <div className="mb-7">
            <h1 className="text-2xl font-bold text-white">
              Add Project
            </h1>

            <p className="text-slate-400 text-sm mt-2">
              Showcase something you've built.
            </p>
          </div>

          {error && (
            <div className="mb-5 px-4 py-3 border border-red-500/30 bg-red-500/10 text-red-400 text-sm">
              {error}
            </div>
          )}

          <form onSubmit={handleSubmit} className="space-y-5">

            {/* Title */}
            <div>
              <label className="block text-sm text-slate-300 mb-2">
                Project Title
              </label>

              <input
                type="text"
                name="title"
                value={formData.title}
                onChange={handleChange}
                placeholder="e.g. DevConnect"
                required
                className="w-full bg-[#0b0f19] border border-slate-700 px-4 py-3 text-sm text-white placeholder-slate-600 outline-none focus:border-indigo-500 transition"
              />
            </div>

            {/* Description */}
            <div>
              <label className="block text-sm text-slate-300 mb-2">
                Description
              </label>

              <textarea
                name="description"
                value={formData.description}
                onChange={handleChange}
                placeholder="Describe what your project does..."
                rows={5}
                required
                className="w-full bg-[#0b0f19] border border-slate-700 px-4 py-3 text-sm text-white placeholder-slate-600 outline-none focus:border-indigo-500 transition resize-none"
              />
            </div>

            {/* Tech Stack */}
            <div>
              <label className="block text-sm text-slate-300 mb-2">
                Tech Stack
              </label>

              <input
                type="text"
                name="techStack"
                value={formData.techStack}
                onChange={handleChange}
                placeholder="React, Spring Boot, PostgreSQL"
                required
                className="w-full bg-[#0b0f19] border border-slate-700 px-4 py-3 text-sm text-white placeholder-slate-600 outline-none focus:border-indigo-500 transition"
              />

              <p className="text-xs text-slate-600 mt-2">
                Separate technologies using commas.
              </p>
            </div>

            {/* GitHub */}
            <div>
              <label className="block text-sm text-slate-300 mb-2">
                GitHub URL
              </label>

              <input
                type="url"
                name="githubUrl"
                value={formData.githubUrl}
                onChange={handleChange}
                placeholder="https://github.com/username/project"
                className="w-full bg-[#0b0f19] border border-slate-700 px-4 py-3 text-sm text-white placeholder-slate-600 outline-none focus:border-indigo-500 transition"
              />
            </div>

            {/* Demo */}
            <div>
              <label className="block text-sm text-slate-300 mb-2">
                Live Demo URL
              </label>

              <input
                type="url"
                name="demoUrl"
                value={formData.demoUrl}
                onChange={handleChange}
                placeholder="https://your-project.com"
                className="w-full bg-[#0b0f19] border border-slate-700 px-4 py-3 text-sm text-white placeholder-slate-600 outline-none focus:border-indigo-500 transition"
              />
            </div>

            {/* Submit */}
            <button
              type="submit"
              disabled={loading}
              className="w-full flex items-center justify-center gap-2 px-5 py-3 bg-indigo-600 hover:bg-indigo-500 disabled:opacity-50 text-white text-sm font-medium transition"
            >
              <Plus size={17} />

              {loading ? "Creating Project..." : "Create Project"}
            </button>

          </form>
        </div>
      </div>
    </div>
  );
}

export default ProjectForm;