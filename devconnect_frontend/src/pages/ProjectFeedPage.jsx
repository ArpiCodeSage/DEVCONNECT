import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { ArrowLeft, FolderGit2 } from "lucide-react";
import API from "../axios";
import ProjectCard from "../components/ProjectCard";

function ProjectFeedPage() {

    const [projects, setProjects] = useState([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {

        const fetchProjects = async () => {

            try {

                const response = await API.get("/projects");

                setProjects(response.data);

            } catch (error) {

                console.error("Failed to fetch projects:", error);

            } finally {

                setLoading(false);

            }
        };

        fetchProjects();

    }, []);

    const handleDeleteProject = async (projectId) => {

        const confirmed = window.confirm(
            "Are you sure you want to delete this project?"
        );

        if (!confirmed) return;

        try {

            await API.delete(`/projects/${projectId}`);

            setProjects((prev) =>
                prev.filter((project) => project.id !== projectId)
            );

        } catch (error) {

            console.error("Failed to delete project:", error);

            alert("Could not delete project.");
        }
    };

    return (
        <div className="min-h-screen bg-[#0b0f19] px-4 py-10">

            <div className="max-w-6xl mx-auto">

                {/* Header */}
                <div className="flex items-center justify-between mb-8">

                    <div>

                        <h1 className="text-3xl font-bold text-white">
                            Explore Projects
                        </h1>

                        <p className="text-slate-400 mt-2">
                            Discover projects built by developers in the DevConnect community.
                        </p>

                    </div>

                    <Link
                        to="/"
                        className="hidden sm:inline-flex items-center gap-2 text-sm text-slate-400 hover:text-white transition"
                    >
                        <ArrowLeft size={16} />
                        Back
                    </Link>

                </div>

                {/* Loading */}
                {loading ? (

                    <div className="flex items-center justify-center py-20">

                        <p className="text-slate-500">
                            Loading projects...
                        </p>

                    </div>

                ) : projects.length === 0 ? (

                    /* Empty state */
                    <div className="bg-[#111827] border border-slate-800 p-10 text-center">

                        <FolderGit2
                            size={40}
                            className="mx-auto text-slate-600 mb-4"
                        />

                        <h2 className="text-lg font-semibold text-white">
                            No projects yet
                        </h2>

                        <p className="text-slate-400 text-sm mt-2">
                            Be the first developer to share a project.
                        </p>

                        <Link
                            to="/projects/create"
                            className="inline-flex items-center mt-5 px-4 py-2 bg-indigo-600 hover:bg-indigo-500 text-white text-sm font-medium transition"
                        >
                            Create Project
                        </Link>

                    </div>

                ) : (

                    /* Projects */
                    <div className="grid grid-cols-1 md:grid-cols-2 gap-5">

                        {projects.map((project) => (

                            <div key={project.id}>

                                {/* Developer name */}
                                <div className="mb-2">

                                    <Link
                                        to={`/profile/${project.username}`}
                                        className="text-sm text-cyan-400 hover:underline"
                                    >
                                        @{project.username}
                                    </Link>

                                </div>

                                <ProjectCard
                                    project={project}
                                    isOwner={
                                        localStorage.getItem("username") ===
                                        project.username
                                    }
                                    onDelete={handleDeleteProject}
                                />

                            </div>

                        ))}

                    </div>

                )}

            </div>

        </div>
    );
}

export default ProjectFeedPage;