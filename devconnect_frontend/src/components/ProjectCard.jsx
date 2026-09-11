import React, { useEffect, useState } from "react";
import {
  ExternalLink,
  Globe,
  Trash2,
  Heart,
  MessageCircle,
  Send,
} from "lucide-react";

import API from "../axios";

function ProjectCard({ project, isOwner, onDelete }) {

  const technologies = project.techStack
    ? project.techStack.split(",").map((tech) => tech.trim())
    : [];

  const [likeCount, setLikeCount] = useState(0);
  const [liked, setLiked] = useState(false);

  const [comments, setComments] = useState([]);
  const [commentText, setCommentText] = useState("");
  const [commentsOpen, setCommentsOpen] = useState(false);
  const [commentCount, setCommentCount] = useState(0);
  const [commentLoading, setCommentLoading] = useState(false);
  const [likeLoading, setLikeLoading] = useState(false);

  // -----------------------------
  // LIKE PROJECT
  // -----------------------------

  const handleLike = async () => {

    if (likeLoading) return;

    try {
      setLikeLoading(true);

      const response = await API.post(
        `/projects/${project.id}/like`
      );

      setLikeCount(response.data.likeCount);
      setLiked(response.data.likedByCurrentUser);

    } catch (error) {
      console.error("Failed to like project:", error);
    } finally {
      setLikeLoading(false);
    }
  };

  // -----------------------------
  // GET COMMENTS
  // -----------------------------

  const fetchComments = async () => {
    try {
      const response = await API.get(`/projects/${project.id}/comments`);

      setComments(response.data);
      setCommentCount(response.data.length);
    } catch (err) {
      console.error("Failed to fetch comments:", err);
    }
  };
  useEffect(() => {
    const loadLikeStatus = async () => {
      try {
        const response = await API.get(`/projects/${project.id}/like`);

        setLikeCount(response.data.likeCount);
        setLiked(response.data.likedByCurrentUser);
      } catch (err) {
        console.error("Failed to load like status:", err);
      }
    };

    const loadComments = async () => {
      try {
        const response = await API.get(`/projects/${project.id}/comments`);

        setComments(response.data);
        setCommentCount(response.data.length);
      } catch (err) {
        console.error("Failed to load comments:", err);
      }
    };

    loadLikeStatus();
    loadComments();
  }, [project.id]);

  // -----------------------------
  // OPEN COMMENTS
  // -----------------------------

  const handleToggleComments = async () => {

    if (!commentsOpen) {
      await fetchComments();
    }

    setCommentsOpen((prev) => !prev);
  };

  // -----------------------------
  // ADD COMMENT
  // -----------------------------

  const handleAddComment = async (e) => {

    e.preventDefault();

    if (!commentText.trim()) return;

    try {

      setCommentLoading(true);

      const response = await API.post(
        `/projects/${project.id}/comments`,
        {
          content: commentText,
        }
      );
      setComments((prev) => [
        response.data,
        ...prev,
      ]);

      setCommentCount((prev) => prev + 1);

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
        `/projects/comments/${commentId}`
      );

      setComments((prev) =>
        prev.filter(
          (comment) => comment.id !== commentId
        )
      );

      setCommentCount((prev) => prev - 1);

    } catch (error) {

      console.error(
        "Failed to delete comment:",
        error
      );

    }
  };
  return (
    <div className="bg-[#111827] border border-slate-800 p-5 hover:border-slate-700 transition">

      {/* Header */}
      <div className="flex justify-between items-start gap-3">

        <h3 className="text-lg font-semibold text-white">
          {project.title}
        </h3>

        {project.demoUrl && (
          <a
            href={project.demoUrl}
            target="_blank"
            rel="noopener noreferrer"
            className="text-slate-500 hover:text-cyan-400 transition shrink-0"
            title="Open Live Demo"
          >
            <ExternalLink size={17} />
          </a>
        )}

      </div>

      {/* Description */}
      <p className="text-slate-400 text-sm mt-3 leading-relaxed">
        {project.description}
      </p>

      {/* Tech Stack */}
      <div className="flex flex-wrap gap-2 mt-4">

        {technologies.map((tech) => (
          <span
            key={tech}
            className="px-2.5 py-1 bg-slate-900 border border-slate-700 text-cyan-400 text-xs"
          >
            {tech}
          </span>
        ))}

      </div>

      {/* Links */}
      <div className="flex items-center gap-5 mt-5">

        {project.githubUrl && (
          <a
            href={project.githubUrl}
            target="_blank"
            rel="noreferrer"
            className="text-sm text-slate-300 hover:text-white transition"
          >
            GitHub
          </a>
        )}

        {project.demoUrl && (
          <a
            href={project.demoUrl}
            target="_blank"
            rel="noreferrer"
            className="flex items-center gap-2 text-sm text-slate-300 hover:text-white transition"
          >
            <Globe size={16} />
            Live Demo
          </a>
        )}

        {/* Delete only for owner */}
        {isOwner && (
          <button
            onClick={() => onDelete(project.id)}
            className="ml-auto text-slate-500 hover:text-red-400 transition"
            title="Delete project"
          >
            <Trash2 size={16} />
          </button>
        )}

      </div>

      {/* Like + Comment buttons */}
      <div className="flex items-center gap-6 mt-5 pt-4 border-t border-slate-800">

        <button
          onClick={handleLike}
          disabled={likeLoading}
          className={`flex items-center gap-2 text-sm transition ${liked
            ? "text-red-400"
            : "text-slate-400 hover:text-red-400"
            }`}
        >
          <Heart
            size={17}
            fill={liked ? "currentColor" : "none"}
          />

          {likeCount}
        </button>

        <button
          onClick={handleToggleComments}
          className="flex items-center gap-2 text-sm text-slate-400 hover:text-cyan-400 transition"
        >
          <MessageCircle size={17} />

          {commentCount}        </button>

      </div>

      {/* Comments */}
      {commentsOpen && (
        <div className="mt-4">

          {/* Add comment */}
          <form
            onSubmit={handleAddComment}
            className="flex gap-2"
          >

            <input
              type="text"
              value={commentText}
              onChange={(e) =>
                setCommentText(e.target.value)
              }
              placeholder="Write a comment..."
              className="flex-1 bg-slate-900 border border-slate-700 px-3 py-2 text-sm text-white placeholder-slate-500 outline-none focus:border-cyan-500"
            />

            <button
              type="submit"
              disabled={
                commentLoading ||
                !commentText.trim()
              }
              className="px-3 py-2 bg-indigo-600 hover:bg-indigo-500 disabled:opacity-50 text-white transition"
            >
              <Send size={16} />
            </button>

          </form>

          {/* Comments list */}
          <div className="mt-4 space-y-3">

            {comments.length === 0 ? (

              <p className="text-sm text-slate-500">
                No comments yet.
              </p>

            ) : (

              comments.map((comment) => (

                <div
                  key={comment.id}
                  className="bg-slate-900 border border-slate-800 p-3"
                >

                  <div className="flex justify-between items-start gap-3">

                    <div>

                      <p className="text-sm text-cyan-400">
                        @{comment.username}
                      </p>

                      <p className="text-sm text-slate-300 mt-1">
                        {comment.content}
                      </p>

                    </div>

                    {localStorage.getItem("username") ===
                      comment.username && (
                        <button
                          onClick={() =>
                            handleDeleteComment(comment.id)
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
      )}

    </div>
  );
}

export default ProjectCard;