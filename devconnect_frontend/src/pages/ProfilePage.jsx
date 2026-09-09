// DISPLAY THE PROFILE

import React, { useState, useEffect } from "react";
import { useParams, Link } from "react-router-dom";
import {
  MapPin,
  Mail,
  Globe,
  Pencil,
  Plus,
} from "lucide-react";
import API from "../axios";
import ProjectCard from "../components/ProjectCard";



function ProfilePage() {
  const { username } = useParams();
  const [projects, setProjects] = useState([]);
  const [projectsLoading, setProjectsLoading] = useState(true);
  const [profile, setProfile] = useState(null)
  const [profileLoading, setProfileLoading] = useState(true)//means we're currently waiting for the backend

  useEffect(() => { //run this effect after the component renders, and whenever username changes.
    const fetchProfile = async () => { //async cuz making an HTTP request takes time.
      try {
        const response = await API.get(`/profiles/${username}`);
        setProfile(response.data);
      } catch (error) {
        console.error("Failed to fetch profile:", error);
      } finally {
        setProfileLoading(false);
      }
    };

    if (username) {
      fetchProfile();
    } else {
      setProfileLoading(false);
    }
  }, [username]);//this means that run this effect whenever the username changes

  useEffect(() => {
    const fetchProjects = async () => {
      try {
        const response = await API.get(`/projects/user/${username}`);
        setProjects(response.data);
      } catch (error) {
        console.error("Failed to fetch projects:", error);
      } finally {
        setProjectsLoading(false);
      }
    };

    if (username) {
      fetchProjects();
    } else {
      setProjectsLoading(false);
    }
  }, [username]);

  

  const isOwner = localStorage.getItem("username") === username;//this determines whether you're looking at your own profile : this controls things like edit profile,add project and delete project
  if (profileLoading) { // when the API request is happening, we don't try to access anything
  return (
    <div className="min-h-screen bg-[#0b0f19] flex items-center justify-center">
      <p className="text-slate-500">Loading profile...</p>
    </div>
  );
}

if (!profile) {
  return (
    <div className="min-h-screen bg-[#0b0f19] flex items-center justify-center">
      <p className="text-slate-500">Profile not found.</p>
    </div>
  );
}
const skills = profile.skills
  ? profile.skills.split(",").map((skill) => skill.trim()).filter(Boolean)//the last filter part removes empty values
  : [];
  const handleDelete = async (projectId) => {
    const confirmed = window.confirm( //browser provided popup
      "Are you sure you want to delete this project?"
    );

    if (!confirmed) return;

    try {
      await API.delete(`/projects/${projectId}`);

      setProjects((prev) => //prev = projects at the moment
        prev.filter((project) => project.id !== projectId)
      );
    } catch (error) {
      console.error("Failed to delete project:", error);
      alert("Could not delete project.");
    }
  };

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

                {/* Only owner sees Edit Profile */}
                {isOwner && (
                  <Link
                    to="/profile/edit"
                    className="inline-flex items-center justify-center gap-2 px-4 py-2 bg-indigo-600 hover:bg-indigo-500 text-white text-sm font-medium transition"
                  >
                    <Pencil size={16} />
                    Edit Profile
                  </Link>
                )}

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
                  href={profile.githubUrl}
                  target="_blank"
                  rel="noreferrer"
                  className="text-slate-400 hover:text-white transition"
                >
                  GitHub
                </a>

                <a
                  href={profile.linkedinUrl}
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
                  aria-label="Website"
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

            <div className="flex items-center gap-4">

              <span className="text-sm text-slate-500">
                {projects.length}{" "}
                {projects.length === 1 ? "project" : "projects"}
              </span>

              {/* Only logged-in user sees Add Project */}
              {isOwner && (
                <Link
                  to="/projects/create"
                  className="inline-flex items-center gap-2 px-3 py-2 bg-indigo-600 hover:bg-indigo-500 text-white text-sm font-medium transition"
                >
                  <Plus size={16} />
                  Add Project
                </Link>
              )}

            </div>
          </div>

          {/* Loading */}
          {projectsLoading ? (

            <p className="text-slate-500 text-sm">
              Loading projects...
            </p>

          ) : projects.length === 0 ? (

            /* No Projects */
            <div className="bg-[#111827] border border-slate-800 p-8 text-center">

              <p className="text-slate-400">
                No projects yet.
              </p>

              {isOwner && (
                <Link
                  to="/projects/create"
                  className="inline-flex items-center gap-2 mt-4 px-4 py-2 bg-indigo-600 hover:bg-indigo-500 text-white text-sm transition"
                >
                  <Plus size={16} />
                  Add your first project
                </Link>
              )}

            </div>

          ) : (

            /* Projects Grid */
            <div className="grid grid-cols-1 md:grid-cols-2 gap-5">

              {projects.map((project) => (
                <ProjectCard
                  key={project.id}
                  project={project}
                  isOwner={isOwner}
                  onDelete={handleDelete}
                />
              ))}

            </div>
          )}

        </section>

      </div>
    </div>
  );
}

export default ProfilePage;