import React, { useEffect, useState } from "react";
import { useSearchParams, useNavigate } from "react-router-dom";
import { Search, User, Code2 } from "lucide-react";
import API from "../axios";

function SearchPage() {
    const [searchParams, setSearchParams] = useSearchParams();
    const navigate = useNavigate();

    const initialQuery = searchParams.get("q") || "";
    const [query, setQuery] = useState(initialQuery);

    const [results, setResults] = useState({
        developers: [],
        projects: []
    });

    const [loading, setLoading] = useState(false);

    useEffect(() => {
        const timer = setTimeout(async () => {
            const value = query.trim();

            if (!value) {
                setResults({
                    developers: [],
                    projects: []
                });
                setLoading(false);
                return;
            }

            setSearchParams({ q: value });

            try {
                setLoading(true);

                const response = await API.get(
                    `/search?q=${encodeURIComponent(value)}`
                );

                console.log("SEARCH RESPONSE:", response.data);

                setResults({
                    developers: response.data?.developers || [],
                    projects: response.data?.projects || []
                });

            } catch (error) {
                console.error("Search error:", error);

                setResults({
                    developers: [],
                    projects: []
                });

            } finally {
                setLoading(false);
            }
        }, 300);

        return () => clearTimeout(timer);
    }, [query, setSearchParams]);


    return (
        <div className="min-h-screen bg-[#0b0f19] px-4 py-10">

            <div className="max-w-6xl mx-auto">

                {/* SEARCH */}
                <div className="relative mb-10">

                    <Search
                        size={20}
                        className="absolute left-4 top-1/2 -translate-y-1/2 text-slate-500"
                    />

                    <input
                        value={query}
                        onChange={(e) => setQuery(e.target.value)}
                        placeholder="Search skills or tech stack..."
                        className="w-full bg-[#111827] border border-slate-800 rounded-xl pl-12 pr-4 py-4 text-white outline-none focus:border-cyan-500"
                    />

                </div>


                {/* LOADING */}
                {loading && (
                    <p className="text-slate-500 mb-6">
                        Searching...
                    </p>
                )}


                {/* DEVELOPERS */}
                {results.developers.length > 0 && (

                    <section className="mb-12">

                        <div className="flex items-center gap-2 mb-5">

                            <User
                                size={20}
                                className="text-cyan-400"
                            />

                            <h2 className="text-xl font-semibold text-white">
                                Developers
                            </h2>

                        </div>


                        <div className="grid md:grid-cols-2 lg:grid-cols-3 gap-5">

                            {results.developers.map((developer) => (

                                <button
                                    key={developer.username}
                                    onClick={() =>
                                        navigate(
                                            `/profile/${developer.username}`
                                        )
                                    }
                                    className="text-left bg-[#111827] border border-slate-800 rounded-xl p-5 hover:border-cyan-500/50 transition"
                                >

                                    <div className="flex items-center gap-3">

                                        {developer.avatarUrl ? (
                                            <img
                                                src={developer.avatarUrl}
                                                alt={developer.username}
                                                className="w-10 h-10 rounded-full object-cover"
                                            />
                                        ) : (
                                            <div className="w-10 h-10 rounded-full bg-slate-800 flex items-center justify-center">
                                                <User
                                                    size={20}
                                                    className="text-slate-500"
                                                />
                                            </div>
                                        )}

                                        <div>
                                            <h3 className="text-white font-semibold">
                                                @{developer.username}
                                            </h3>

                                            <p className="text-slate-500 text-sm">
                                                {developer.headline || "Developer"}
                                            </p>
                                        </div>

                                    </div>


                                    {developer.skills && (

                                        <div className="flex flex-wrap gap-2 mt-4">

                                            {developer.skills
                                                .split(",")
                                                .map((skill, index) => (

                                                    <span
                                                        key={index}
                                                        className="text-xs px-2.5 py-1 rounded-md bg-cyan-500/10 text-cyan-400"
                                                    >
                                                        {skill.trim()}
                                                    </span>

                                                ))}

                                        </div>

                                    )}

                                </button>

                            ))}

                        </div>

                    </section>

                )}


                {/* PROJECTS */}
                {results.projects.length > 0 && (

                    <section>

                        <div className="flex items-center gap-2 mb-5">

                            <Code2
                                size={20}
                                className="text-indigo-400"
                            />

                            <h2 className="text-xl font-semibold text-white">
                                Projects
                            </h2>

                        </div>


                        <div className="grid md:grid-cols-2 gap-5">

                            {results.projects.map((project) => (

                                <div
                                    key={project.id}
                                    className="bg-[#111827] border border-slate-800 rounded-xl p-5 hover:border-indigo-500/50"
                                >

                                    <h3 className="text-white text-lg font-semibold">
                                        {project.title}
                                    </h3>

                                    <p className="text-slate-400 text-sm mt-2">
                                        {project.description}
                                    </p>


                                    {project.techStack && (

                                        <div className="flex flex-wrap gap-2 mt-4">

                                            {project.techStack
                                                .split(",")
                                                .map((tech, index) => (

                                                    <span
                                                        key={index}
                                                        className="text-xs px-2.5 py-1 rounded-md bg-indigo-500/10 text-indigo-400"
                                                    >
                                                        {tech.trim()}
                                                    </span>

                                                ))}

                                        </div>

                                    )}


                                    {project.username && (

                                        <button
                                            onClick={() =>
                                                navigate(
                                                    `/profile/${project.username}`
                                                )
                                            }
                                            className="text-sm text-slate-500 hover:text-cyan-400 mt-4"
                                        >
                                            View developer
                                        </button>

                                    )}

                                </div>

                            ))}

                        </div>

                    </section>

                )}


                {/* NO RESULTS */}
                {!loading &&
                    query.trim() &&
                    results.developers.length === 0 &&
                    results.projects.length === 0 && (

                        <div className="text-center py-20">

                            <Search
                                size={40}
                                className="mx-auto text-slate-700 mb-4"
                            />

                            <p className="text-slate-400">
                                No developers or projects found.
                            </p>

                        </div>

                    )}

            </div>

        </div>
    );
}

export default SearchPage;