import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { Plus, BookOpen } from "lucide-react";

import API from "../axios";
import BlogCard from "../components/BlogCard";

function BlogFeedPage() {

    const [blogs, setBlogs] = useState([]);
    const [loading, setLoading] = useState(true);

    const fetchBlogs = async () => {

        try {

            const response = await API.get("/blogs");

            setBlogs(response.data);

        } catch (error) {

            console.error(
                "Failed to fetch blogs:",
                error
            );

        } finally {

            setLoading(false);

        }
    };

    useEffect(() => {
        fetchBlogs();
    }, []);

    const handleDeleteBlog = async (blogId) => {

        const confirmed = window.confirm(
            "Are you sure you want to delete this blog?"
        );

        if (!confirmed) return;

        try {

            await API.delete(`/blogs/${blogId}`);

            setBlogs((prev) =>
                prev.filter(
                    (blog) => blog.id !== blogId
                )
            );

        } catch (error) {

            console.error(
                "Failed to delete blog:",
                error
            );

            alert("Could not delete blog.");
        }
    };

    return (
        <div className="min-h-screen bg-[#0b0f19] px-4 py-10">

            <div className="max-w-5xl mx-auto">

                {/* Header */}
                <div className="flex flex-col sm:flex-row sm:items-center justify-between gap-4 mb-8">

                    <div>

                        <div className="flex items-center gap-3">

                            <BookOpen
                                size={24}
                                className="text-cyan-400"
                            />

                            <h1 className="text-2xl font-bold text-white">
                                Developer Blogs
                            </h1>

                        </div>

                        <p className="text-slate-500 text-sm mt-2">
                            Share knowledge, ideas and experiences with the developer community.
                        </p>

                    </div>

                    {/* Create Blog */}
                    {localStorage.getItem("token") && (

                        <Link
                            to="/blogs/create"
                            className="flex items-center justify-center gap-2 px-4 py-2 bg-indigo-600 hover:bg-indigo-500 text-white text-sm transition"
                        >
                            <Plus size={17} />
                            Write Blog
                        </Link>

                    )}

                </div>

                {/* Loading */}
                {loading && (

                    <div className="text-center py-16 text-slate-500">
                        Loading blogs...
                    </div>

                )}

                {/* Empty */}
                {!loading && blogs.length === 0 && (

                    <div className="bg-[#111827] border border-slate-800 p-10 text-center">

                        <BookOpen
                            size={32}
                            className="mx-auto text-slate-600"
                        />

                        <p className="text-slate-400 mt-4">
                            No blogs have been published yet.
                        </p>

                        {localStorage.getItem("token") && (

                            <Link
                                to="/blogs/create"
                                className="inline-block mt-4 text-cyan-400 hover:text-cyan-300 text-sm"
                            >
                                Write the first blog →
                            </Link>

                        )}

                    </div>

                )}

                {/* Blog list */}
                {!loading && blogs.length > 0 && (

                    <div className="grid grid-cols-1 md:grid-cols-2 gap-5">

                        {blogs.map((blog) => (

                            <BlogCard
                                key={blog.id}
                                blog={blog}
                                isOwner={
                                    localStorage.getItem("username") ===
                                    blog.username
                                }
                                onDelete={handleDeleteBlog}
                            />

                        ))}

                    </div>

                )}

            </div>

        </div>
    );
}

export default BlogFeedPage;