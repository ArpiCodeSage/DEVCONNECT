import React, { useEffect, useState } from "react";
import { useNavigate, useParams, Link } from "react-router-dom";
import {
  ArrowLeft,
  Heart,
  MessageCircle,
  Send,
  Pencil,
  Trash2,
} from "lucide-react";
import DOMPurify from "dompurify";

import API from "../axios";

function BlogDetailPage() {

  const { id } = useParams();
  const navigate = useNavigate();

  const [blog, setBlog] = useState(null);
  const [comments, setComments] = useState([]);

  const [likeCount, setLikeCount] = useState(0);
  const [liked, setLiked] = useState(false);

  const [commentText, setCommentText] = useState("");

  const [loading, setLoading] = useState(true);
  const [commentLoading, setCommentLoading] = useState(false);
  const [likeLoading, setLikeLoading] = useState(false);

  const currentUsername = localStorage.getItem("username");

  // -----------------------------
  // FETCH BLOG
  // -----------------------------

  const fetchBlog = async () => {

    try {

      const response = await API.get(`/blogs/${id}`);

      setBlog(response.data);

    } catch (error) {

      console.error("Failed to fetch blog:", error);

    } finally {

      setLoading(false);

    }
  };


  // -----------------------------
  // FETCH COMMENTS
  // -----------------------------

  const fetchComments = async () => {

    try {

      const response = await API.get(
        `/blogs/${id}/comments`
      );

      setComments(response.data);

    } catch (error) {

      console.error(
        "Failed to fetch comments:",
        error
      );

    }
  };

  const fetchLikeStatus = async () => {
    try {
      const response = await API.get(`/blogs/${id}/like`);

      setLikeCount(response.data.likeCount);
      setLiked(response.data.likedByCurrentUser);

    } catch (error) {
      console.error(
        "Failed to fetch like status:",
        error
      );
    }
  };
  // -----------------------------
  // INITIAL LOAD
  // -----------------------------

  useEffect(() => {

    fetchBlog();
    fetchComments();
    fetchLikeStatus();

  }, [id]);


  // -----------------------------
  // LIKE
  // -----------------------------

  const handleLike = async () => {

    if (!localStorage.getItem("token")) {
      alert("Please log in to like this blog.");
      return;
    }

    if (likeLoading) return;

    try {

      setLikeLoading(true);

      const response = await API.post(
        `/blogs/${id}/like`
      );

      setLikeCount(response.data.likeCount);
      setLiked(response.data.likedByCurrentUser);

    } catch (error) {

      console.error(
        "Failed to like blog:",
        error
      );

    } finally {

      setLikeLoading(false);

    }
  };


  // -----------------------------
  // ADD COMMENT
  // -----------------------------

  const handleAddComment = async (e) => {

    e.preventDefault();

    if (!localStorage.getItem("token")) {
      alert("Please log in to comment.");
      return;
    }

    if (!commentText.trim()) return;

    try {

      setCommentLoading(true);

      const response = await API.post(
        `/blogs/${id}/comments`,
        {
          content: commentText,
        }
      );

      setComments((prev) => [
        response.data,
        ...prev,
      ]);

      setCommentText("");

    } catch (error) {

      console.error(
        "Failed to add comment:",
        error
      );

    } finally {

      setCommentLoading(false);

    }
  };


  // -----------------------------
  // DELETE COMMENT
  // -----------------------------

  const handleDeleteComment = async (commentId) => {

    try {

      await API.delete(
        `/blogs/comments/${commentId}`
      );

      setComments((prev) =>
        prev.filter(
          (comment) => comment.id !== commentId
        )
      );

    } catch (error) {

      console.error(
        "Failed to delete comment:",
        error
      );

    }
  };


  // -----------------------------
  // DELETE BLOG
  // -----------------------------

  const handleDeleteBlog = async () => {

    const confirmed = window.confirm(
      "Are you sure you want to delete this blog?"
    );

    if (!confirmed) return;

    try {

      await API.delete(`/blogs/${id}`);

      navigate("/blogs");

    } catch (error) {

      console.error(
        "Failed to delete blog:",
        error
      );

      alert("Failed to delete blog.");

    }
  };


  // -----------------------------
  // LOADING
  // -----------------------------

  if (loading) {

    return (
      <div className="min-h-screen bg-[#0b0f19] flex items-center justify-center">

        <p className="text-slate-500">
          Loading blog...
        </p>

      </div>
    );

  }


  // -----------------------------
  // BLOG NOT FOUND
  // -----------------------------

  if (!blog) {

    return (
      <div className="min-h-screen bg-[#0b0f19] flex flex-col items-center justify-center">

        <p className="text-slate-400">
          Blog not found.
        </p>

        <Link
          to="/blogs"
          className="mt-4 text-cyan-400 hover:text-cyan-300 text-sm"
        >
          ← Back to blogs
        </Link>

      </div>
    );

  }


  const isOwner =
    currentUsername === blog.username;


  return (
    <div className="min-h-screen bg-[#0b0f19] px-4 py-10">

      <div className="max-w-4xl mx-auto">

        {/* Back */}
        <Link
          to="/blogs"
          className="inline-flex items-center gap-2 text-sm text-slate-400 hover:text-white transition mb-6"
        >
          <ArrowLeft size={16} />
          Back to blogs
        </Link>


        {/* Blog */}
        <article className="bg-[#111827] border border-slate-800 p-6 sm:p-8">

          {/* Header */}
          <div className="flex flex-col sm:flex-row sm:items-start justify-between gap-4">

            <div>

              <h1 className="text-3xl font-bold text-white">
                {blog.title}
              </h1>

              <div className="flex items-center gap-3 mt-3">

                <Link
                  to={`/profile/${blog.username}`}
                  className="text-sm text-cyan-400 hover:text-cyan-300 transition"
                >
                  @{blog.username}
                </Link>

                <span className="text-slate-700">
                  •
                </span>

                <span className="text-sm text-slate-500">
                  {blog.createdAt
                    ? new Date(
                      blog.createdAt
                    ).toLocaleDateString()
                    : ""}
                </span>

              </div>

            </div>


            {/* Owner controls */}
            {isOwner && (
              <div className="flex items-center gap-3">

                <Link
                  to={`/blogs/edit/${blog.id}`}
                  className="flex items-center gap-2 text-sm text-slate-400 hover:text-cyan-400 transition"
                >
                  <Pencil size={16} />
                  Edit
                </Link>

                <button
                  onClick={handleDeleteBlog}
                  className="flex items-center gap-2 text-sm text-slate-400 hover:text-red-400 transition"
                >
                  <Trash2 size={16} />
                  Delete
                </button>

              </div>
            )}

          </div>


          {/* Content */}
          <div className="mt-8">

            <div
              className="blog-content text-slate-300 leading-8"
              dangerouslySetInnerHTML={{
                __html: DOMPurify.sanitize(blog.content)
              }}
            />

          </div>


          {/* Like */}
          <div className="flex items-center gap-6 mt-8 pt-5 border-t border-slate-800">

            <button
              onClick={handleLike}
              disabled={likeLoading}
              className={`flex items-center gap-2 text-sm transition ${liked
                  ? "text-red-400"
                  : "text-slate-400 hover:text-red-400"
                }`}
            >

              <Heart
                size={18}
                fill={
                  liked
                    ? "currentColor"
                    : "none"
                }
              />

              {likeCount}

            </button>


            <div className="flex items-center gap-2 text-sm text-slate-500">

              <MessageCircle size={18} />

              {comments.length}

            </div>

          </div>

        </article>


        {/* Comments */}
        <div className="mt-6 bg-[#111827] border border-slate-800 p-6">

          <h2 className="text-lg font-semibold text-white">
            Comments
          </h2>


          {/* Comment form */}
          {localStorage.getItem("token") && (
            <form
              onSubmit={handleAddComment}
              className="flex gap-2 mt-5"
            >

              <input
                type="text"
                value={commentText}
                onChange={(e) =>
                  setCommentText(e.target.value)
                }
                placeholder="Write a comment..."
                className="flex-1 bg-slate-900 border border-slate-700 px-4 py-2.5 text-sm text-white placeholder-slate-600 outline-none focus:border-cyan-500"
              />

              <button
                type="submit"
                disabled={
                  commentLoading ||
                  !commentText.trim()
                }
                className="px-4 bg-indigo-600 hover:bg-indigo-500 disabled:opacity-50 text-white transition"
              >
                <Send size={17} />
              </button>

            </form>
          )}


          {/* Comments list */}
          <div className="mt-6 space-y-4">

            {comments.length === 0 ? (

              <p className="text-sm text-slate-500">
                No comments yet. Be the first to comment.
              </p>

            ) : (

              comments.map((comment) => (

                <div
                  key={comment.id}
                  className="bg-slate-900 border border-slate-800 p-4"
                >

                  <div className="flex justify-between items-start gap-4">

                    <div>

                      <Link
                        to={`/profile/${comment.username}`}
                        className="text-sm text-cyan-400 hover:text-cyan-300"
                      >
                        @{comment.username}
                      </Link>

                      <p className="text-sm text-slate-300 mt-2">
                        {comment.content}
                      </p>

                    </div>


                    {currentUsername ===
                      comment.username && (
                        <button
                          onClick={() =>
                            handleDeleteComment(
                              comment.id
                            )
                          }
                          className="text-xs text-slate-600 hover:text-red-400 transition"
                        >
                          Delete
                        </button>
                      )}

                  </div>

                </div>

              ))

            )}

          </div>

        </div>

      </div>

    </div>
  );
}

export default BlogDetailPage;