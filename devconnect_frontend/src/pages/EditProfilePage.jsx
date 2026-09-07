//CHANGE THE PROFILE

import React, { useState } from "react";
import { useAsyncError, useNavigate } from "react-router-dom";
import API from '../axios';
import {
    User,
    Save,
    AlertCircle,
    CheckCircle2,
    Globe,
    MapPin
} from 'lucide-react';

function EditProfilePage() {

    navigate = useNavigate()

    const [formData, setFormData] = useState({
        username: '',
        email: '',
        headline: '',
        linkedinUrl: '',
        githubUrl: '',
        websiteUrl: '',
        avatarUrl: '',
        bio: '',
        skills: ''

    })

    const [loading, setLoading] = useState(false)
    const [success, setSuccess] = useState('')
    const [error, setError] = useState('')

    const handleChange = (e) => {
        setFormData({
            ...formData,
            [e.target.name]: e.target.value
        })

    }

    const handleSubmit = async (e) => {
        e.preventDefault()
        setLoading(true)
        setSuccess('')
        setError('')


        try {
            const response = await API.put("/profiles/me", formData)
            console.log("Updated profile:", response.data)
            setSuccess(response.data || "Profile updated successfully!")
            setTimeout(() => {
                navigate(`/profile/${response.data.username}`)
            }, 1500)



        }
        catch (err) {
            setError(err.response?.data?.message || err.response?.data || "Failed to update profile")
        }
        finally {
            setLoading(false)
        }

    }



    return (
        <div className="min-h-[80vh] flex items-center justify-center px-4 py-12">

            <div className="w-full max-w-2xl bg-[#0f172a]/90 backdrop-blur-md border border-slate-800 p-8 rounded-2xl shadow-2xl">

                {/* Header */}
                <div className="text-center mb-8">

                    <div className="w-12 h-12 rounded-xl bg-cyan-500/10 border border-cyan-500/30 flex items-center justify-center text-cyan-400 mx-auto mb-3">
                        <User size={24} />
                    </div>

                    <h2 className="text-2xl font-bold text-white tracking-tight">
                        Edit Profile
                    </h2>

                    <p className="text-slate-400 text-sm mt-1">
                        Update your developer profile information
                    </p>

                </div>

                {/* Error Alert */}
                {error && (
                    <div className="mb-6 p-3 rounded-lg bg-red-500/10 border border-red-500/30 text-red-400 text-xs font-medium flex items-center gap-2">
                        <AlertCircle size={16} />
                        <span>{error}</span>
                    </div>
                )}

                {/* Success Alert */}
                {success && (
                    <div className="mb-6 p-3 rounded-lg bg-emerald-500/10 border border-emerald-500/30 text-emerald-400 text-xs font-medium flex items-center gap-2">
                        <CheckCircle2 size={16} />
                        <span>{success}</span>
                    </div>
                )}

                {/* Form */}
                <form onSubmit={handleSubmit} className="space-y-5">

                    {/* Name + Username */}
                    <div className="grid grid-cols-1 sm:grid-cols-2 gap-5">

                        <div>
                            <label className="block text-xs font-mono uppercase tracking-wider text-slate-400 mb-1.5">
                                Name
                            </label>

                            <input
                                type="text"
                                name="name"
                                required
                                value={formData.name}
                                onChange={handleChange}
                                placeholder="Your Name"
                                className="w-full bg-slate-900 border border-slate-800 rounded-lg px-4 py-2.5 text-sm text-white placeholder-slate-600 focus:outline-none focus:border-cyan-500 transition"
                            />
                        </div>

                        <div>
                            <label className="block text-xs font-mono uppercase tracking-wider text-slate-400 mb-1.5">
                                Username
                            </label>

                            <input
                                type="text"
                                name="username"
                                required
                                value={formData.username}
                                onChange={handleChange}
                                placeholder="dev_username"
                                className="w-full bg-slate-900 border border-slate-800 rounded-lg px-4 py-2.5 text-sm text-white placeholder-slate-600 focus:outline-none focus:border-cyan-500 transition"
                            />
                        </div>

                    </div>

                    {/* Role + Location */}
                    <div className="grid grid-cols-1 sm:grid-cols-2 gap-5">

                        <div>
                            <label className="block text-xs font-mono uppercase tracking-wider text-slate-400 mb-1.5">
                                Role
                            </label>

                            <input
                                type="text"
                                name="role"
                                value={formData.role}
                                onChange={handleChange}
                                placeholder="Software Developer"
                                className="w-full bg-slate-900 border border-slate-800 rounded-lg px-4 py-2.5 text-sm text-white placeholder-slate-600 focus:outline-none focus:border-cyan-500 transition"
                            />
                        </div>

                        <div>
                            <label className="block text-xs font-mono uppercase tracking-wider text-slate-400 mb-1.5">
                                Location
                            </label>

                            <div className="relative">
                                <MapPin
                                    size={18}
                                    className="absolute left-3 top-1/2 -translate-y-1/2 text-slate-500"
                                />

                                <input
                                    type="text"
                                    name="location"
                                    value={formData.location}
                                    onChange={handleChange}
                                    placeholder="Your Location"
                                    className="w-full bg-slate-900 border border-slate-800 rounded-lg pl-10 pr-4 py-2.5 text-sm text-white placeholder-slate-600 focus:outline-none focus:border-cyan-500 transition"
                                />
                            </div>
                        </div>

                    </div>

                    {/* Email */}
                    <div>
                        <label className="block text-xs font-mono uppercase tracking-wider text-slate-400 mb-1.5">
                            Email
                        </label>

                        <input
                            type="email"
                            name="email"
                            value={formData.email}
                            onChange={handleChange}
                            placeholder="you@example.com"
                            className="w-full bg-slate-900 border border-slate-800 rounded-lg px-4 py-2.5 text-sm text-white placeholder-slate-600 focus:outline-none focus:border-cyan-500 transition"
                        />
                    </div>

                    {/* Bio */}
                    <div>
                        <label className="block text-xs font-mono uppercase tracking-wider text-slate-400 mb-1.5">
                            Bio
                        </label>

                        <textarea
                            name="bio"
                            rows="4"
                            value={formData.bio}
                            onChange={handleChange}
                            placeholder="Tell people about yourself..."
                            className="w-full bg-slate-900 border border-slate-800 rounded-lg px-4 py-2.5 text-sm text-white placeholder-slate-600 focus:outline-none focus:border-cyan-500 transition resize-none"
                        />
                    </div>

                    {/* Skills */}
                    <div>
                        <label className="block text-xs font-mono uppercase tracking-wider text-slate-400 mb-1.5">
                            Skills
                        </label>

                        <input
                            type="text"
                            name="skills"
                            value={formData.skills}
                            onChange={handleChange}
                            placeholder="Java, Spring Boot, React, PostgreSQL"
                            className="w-full bg-slate-900 border border-slate-800 rounded-lg px-4 py-2.5 text-sm text-white placeholder-slate-600 focus:outline-none focus:border-cyan-500 transition"
                        />

                        <p className="text-xs text-slate-600 mt-1.5">
                            Separate skills with commas
                        </p>
                    </div>

                    {/* GitHub */}
                    <div>
                        <label className="block text-xs font-mono uppercase tracking-wider text-slate-400 mb-1.5">
                            GitHub
                        </label>

                        <div className="relative">
                            <a href={profile.github} target="_blank" rel="noreferrer">
                                GitHub
                            </a>

                            <input
                                type="text"
                                name="github"
                                value={formData.github}
                                onChange={handleChange}
                                placeholder="https://github.com/username"
                                className="w-full bg-slate-900 border border-slate-800 rounded-lg pl-10 pr-4 py-2.5 text-sm text-white placeholder-slate-600 focus:outline-none focus:border-cyan-500 transition"
                            />
                        </div>
                    </div>

                    {/* LinkedIn */}
                    <div>
                        <label className="block text-xs font-mono uppercase tracking-wider text-slate-400 mb-1.5">
                            LinkedIn
                        </label>

                        <div className="relative">

                            <a
                                href={profile.linkedin}
                                target="_blank"
                                rel="noreferrer"
                                className="text-cyan-400 hover:underline"
                            >
                                LinkedIn
                            </a>

                            <input
                                type="text"
                                name="linkedin"
                                value={formData.linkedin}
                                onChange={handleChange}
                                placeholder="https://linkedin.com/in/username"
                                className="w-full bg-slate-900 border border-slate-800 rounded-lg pl-10 pr-4 py-2.5 text-sm text-white placeholder-slate-600 focus:outline-none focus:border-cyan-500 transition"
                            />
                        </div>
                    </div>

                    {/* Portfolio */}
                    <div>
                        <label className="block text-xs font-mono uppercase tracking-wider text-slate-400 mb-1.5">
                            Portfolio Website
                        </label>

                        <div className="relative">
                            <Globe
                                size={18}
                                className="absolute left-3 top-1/2 -translate-y-1/2 text-slate-500"
                            />

                            <input
                                type="text"
                                name="portfolio"
                                value={formData.portfolio}
                                onChange={handleChange}
                                placeholder="https://yourportfolio.com"
                                className="w-full bg-slate-900 border border-slate-800 rounded-lg pl-10 pr-4 py-2.5 text-sm text-white placeholder-slate-600 focus:outline-none focus:border-cyan-500 transition"
                            />
                        </div>
                    </div>

                    {/* Buttons */}
                    <div className="flex gap-3 pt-3">

                        <button
                            type="button"
                            onClick={() => navigate(-1)}
                            className="w-1/2 py-2.5 rounded-lg bg-slate-800 hover:bg-slate-700 text-white font-medium text-sm transition cursor-pointer"
                        >
                            Cancel
                        </button>

                        <button
                            type="submit"
                            disabled={loading}
                            className="w-1/2 py-2.5 rounded-lg bg-cyan-600 hover:bg-cyan-500 text-white font-medium text-sm transition shadow-lg shadow-cyan-600/20 disabled:opacity-50 cursor-pointer flex items-center justify-center gap-2"
                        >
                            <Save size={16} />
                            {loading ? 'Saving...' : 'Save Changes'}
                        </button>

                    </div>

                </form>

            </div>
        </div>
    );
};
export default EditProfilePage;