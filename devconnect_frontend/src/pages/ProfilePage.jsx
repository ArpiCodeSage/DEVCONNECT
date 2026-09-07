//DISPLAY THE PROFILE

import React, { useState } from "react";
import { useParams, Link } from "react-router-dom";
import { MapPin, Mail, Globe, Code2, Pencil, ExternalLink } from "lucide-react";

const mockProfile = {
  username: 'mock-user',
  email: 'mock@example.com',
  headline: 'full stack developer',
  linkedinUrl: '#',
  githubUrl: '#',
  websiteUrl: '#',
  avatarUrl: '#',
  bio: 'I build scalable web applications',
  skills: 'java,spring boot'

}
const mockProjects = [
  {
    id: 1,
    title: 'DevConnect',
    description:
      'A developer platform where users can showcase their projects, skills and experience.',
    techStack: 'React, Spring Boot, PostgreSQL',
    githubUrl: '#',
    demoUrl: '#'
  },
  {
    id: 2,
    title: 'AI Code Review Assistant',
    description:
      'An AI-powered tool that analyzes code and provides useful review suggestions.',
    techStack: 'Java, Spring Boot, LLM',
    githubUrl: '#',
    demoUrl: '#'
  },
  {
    id: 3,
    title: 'Movie Search App',
    description:
      'A responsive movie discovery application using an external movie API.',
    techStack: 'React, JavaScript, REST API',
    githubUrl: '#',
    demoUrl: '#'
  }
];

function ProfilePage() {
  const { username } = useParams()
  const profile = {
    ...mockProfile,
    username: username || mockProfile.username
  }
  const skills = profile.skills.split(',').map((skill) => skill.trim())
 return (
  <div className="min-h-screen bg-[#0b0f19] px-4 py-10">
    <div className="max-w-5xl mx-auto">

      {/* Profile Header */}
      <div className="bg-[#111827] border border-slate-800 p-6 sm:p-8">
        <div className="flex flex-col sm:flex-row gap-6 items-start">

          {/* Avatar */}
          <div className="w-24 h-24 rounded-full bg-gradient-to-br from-indigo-500 to-cyan-500 flex items-center justify-center text-3xl font-bold text-white shrink-0">
            {profile.username.charAt(0).toUpperCase()}
          </div>

          <div className="flex-1">

            <div className="flex flex-col sm:flex-row sm:items-center sm:justify-between gap-4">
              <div>
                <h1 className="text-3xl font-bold text-white">
                  @{profile.username}
                </h1>

                <p className="text-cyan-400 mt-1">
                  {profile.headline}
                </p>
              </div>

              <Link
                to="/profile/edit"
                className="inline-flex items-center justify-center gap-2 px-4 py-2 bg-indigo-600 hover:bg-indigo-500 text-white text-sm font-medium transition"
              >
                <Pencil size={16} />
                Edit Profile
              </Link>
            </div>

            <p className="text-slate-400 mt-5 leading-relaxed max-w-2xl">
              {profile.bio}
            </p>

            {/* Contact */}
            <div className="flex flex-wrap gap-4 mt-5 text-sm text-slate-400">
              <div className="flex items-center gap-2">
                <Mail size={15} />
                {profile.email}
              </div>

              <div className="flex items-center gap-2">
                <MapPin size={15} />
                Bangalore, India
              </div>
            </div>

            {/* Social Links */}
            <div className="flex gap-4 mt-5">
              <a
                href={profile.github}
                target="_blank"
                rel="noreferrer"
                className="text-slate-400 hover:text-white transition"
              >
                GitHub
              </a>

              <a
                href={profile.linkedin}
                target="_blank"
                rel="noreferrer"
                className="text-cyan-400 hover:underline"
              >
                LinkedIn
              </a>

              <a
                href={profile.websiteUrl}
                target="_blank"
                rel="noreferrer"
                className="text-slate-400 hover:text-white transition"
              >
                <Globe size={20} />
              </a>
            </div>

          </div>
        </div>
      </div>

      {/* Skills */}
      <section className="mt-8">
        <h2 className="text-xl font-semibold text-white mb-4">
          Skills
        </h2>

        <div className="flex flex-wrap gap-2">
          {skills.map((skill) => (
            <span
              key={skill}
              className="px-3 py-1.5 bg-slate-900 border border-slate-700 text-cyan-400 text-sm"
            >
              {skill}
            </span>
          ))}
        </div>
      </section>

      {/* Projects */}
      <section className="mt-10">
        <div className="flex items-center justify-between mb-4">
          <h2 className="text-xl font-semibold text-white">
            Projects
          </h2>

          <span className="text-sm text-slate-500">
            {mockProjects.length} projects
          </span>
        </div>

        <div className="grid grid-cols-1 md:grid-cols-2 gap-5">
          {mockProjects.map((project) => (
            <div
              key={project.id}
              className="bg-[#111827] border border-slate-800 p-5 hover:border-slate-700 transition"
            >

              <div className="flex justify-between items-start gap-3">
                <h3 className="text-lg font-semibold text-white">
                  {project.title}
                </h3>

                <ExternalLink
                  size={17}
                  className="text-slate-500 shrink-0"
                />
              </div>

              <p className="text-slate-400 text-sm mt-3 leading-relaxed">
                {project.description}
              </p>

              <p className="text-cyan-400 text-xs mt-4">
                {project.techStack}
              </p>

              <div className="flex gap-4 mt-5">

                <a
                  href={project.githubUrl}
                  target="_blank"
                  rel="noreferrer"
                  className="flex items-center gap-2 text-sm text-slate-300 hover:text-white"
                >
                  GitHub
                </a>

                <a
                  href={project.demoUrl}
                  target="_blank"
                  rel="noreferrer"
                  className="flex items-center gap-2 text-sm text-slate-300 hover:text-white"
                >
                  <Globe size={16} />
                  Live Demo
                </a>

              </div>
            </div>
          ))}
        </div>
      </section>

    </div>
  </div>
);
}

export default ProfilePage;
