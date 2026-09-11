import React, { useEffect, useState } from "react";
import {
    Heart,
    MessageCircle,
    ArrowRight,
    Send,
    Trash2
} from "lucide-react";
import { Link } from "react-router-dom";
import API from "../axios";
import DOMPurify from "dompurify";

function BlogCard({ blog, isOwner, onDelete }) {

    const [likeCount, setLikeCount] = useState(0);
    const [liked, setLiked] = useState(false);

    const [comments, setComments] = useState([]);
    const [commentCount, setCommentCount] = useState(0);

    const [commentsOpen, setCommentsOpen] = useState(false);
    const [commentText, setCommentText] = useState("");

    const [likeLoading, setLikeLoading] = useState(false);
    const [commentLoading, setCommentLoading] = useState(false);

    const currentUsername = localStorage.getItem("username");

    useEffect(() => {

        const loadBlogData = async () => {

            try {

                // Load like status
                const likeResponse = await API.get(
                    `/blogs/${blog.id}/like`
                );

                setLikeCount(likeResponse.data.likeCount);

                setLiked(
                    likeResponse.data.likedByCurrentUser
                );

                // Load comments
                const commentResponse = await API.get(
                    `/blogs/${blog.id}/comments`
                );

                setComments(commentResponse.data);

                setCommentCount(
                    commentResponse.data.length
                );

            } catch (error) {

                console.error(
                    "Failed to load blog data:",
                    error
                );

            }

        };

        loadBlogData();

    }, [blog.id]);

    const handleLike = async () => {

        if (likeLoading) return;

        try {

            setLikeLoading(true);

            const response = await API.post(
                `/blogs/${blog.id}/like`
            );

            setLikeCount(response.data.likeCount);
            setLiked(response.data.likedByCurrentUser);

        } catch (err) {

            console.error(
                "Failed to like blog:",
                err
            );

        } finally {

            setLikeLoading(false);

        }
    };

    const toggleComments = () => {
        setCommentsOpen(prev => !prev);
    };

    const handleAddComment = async () => {

        if (!commentText.trim() || commentLoading) return;

        try {

            setCommentLoading(true);

            const response = await API.post(
                `/blogs/${blog.id}/comments`,
                {
                    content: commentText
                }
            );

            setComments(prev => [
                response.data,
                ...prev
            ]);

            setCommentCount(prev => prev + 1);

            setCommentText("");

            setCommentsOpen(true);

        } catch (err) {

            console.error(
                "Failed to add comment:",
                err
            );

        } finally {

            setCommentLoading(false);

        }
    };

    const handleDeleteComment = async (commentId) => {

        try {

            await API.delete(
                `/blogs/comments/${commentId}`
            );

            setComments(prev =>
                prev.filter(
                    comment => comment.id !== commentId
                )
            );

            setCommentCount(prev => prev - 1);

        } catch (err) {

            console.error(
                "Failed to delete comment:",
                err
            );

        }
    };

    const plainTextContent = DOMPurify
        .sanitize(blog.content || "", {
            ALLOWED_TAGS: []
        })
        .replace(/\s+/g, " ")
        .trim();

    return (
        <div className="bg-[#111827] border border-slate-800 p-5 hover:border-slate-700 transition">

            {/* Header */}
            <div className="flex items-center justify-between mb-3">

                <Link
                    to={`/profile/${blog.username}`}
                    className="text-sm text-cyan-400 hover:text-cyan-300 transition"
                >
                    @{blog.username}
                </Link>

                <span className="text-xs text-slate-500">
                    {blog.createdAt
                        ? new Date(blog.createdAt).toLocaleDateString()
                        : ""}
                </span>

            </div>

            {/* Blog Image */}
            {blog.imageUrl && (
                <img
                    src={blog.imageUrl}
                    alt={blog.title}
                    className="w-full max-h-80 object-cover mb-5"
                />
            )}

            {/* Title */}
            <h2 className="text-xl font-semibold text-white">
                {blog.title}
            </h2>

            {/* Preview */}
            <p className="text-slate-400 text-sm mt-3 leading-relaxed">
                {plainTextContent.length > 180
                    ? plainTextContent.substring(0, 180) + "..."
                    : plainTextContent}
            </p>

            {/* Actions */}
            <div className="flex items-center justify-between mt-5 pt-4 border-t border-slate-800">

                <div className="flex items-center gap-5">

                    {/* LIKE */}
                    <button
                        onClick={handleLike}
                        disabled={likeLoading}
                        className={`flex items-center gap-2 text-sm transition ${
                            liked
                                ? "text-red-400"
                                : "text-slate-500 hover:text-red-400"
                        }`}
                    >

                        <Heart
                            size={18}
                            fill={liked ? "currentColor" : "none"}
                        />

                        <span>{likeCount}</span>

                    </button>

                    {/* COMMENTS */}
                    <button
                        onClick={toggleComments}
                        className="flex items-center gap-2 text-sm text-slate-500 hover:text-cyan-400 transition"
                    >

                        <MessageCircle size={18} />

                        <span>{commentCount}</span>

                    </button>

                </div>

                {/* READ + DELETE */}
                <div className="flex items-center gap-4">

                    <Link
                        to={`/blogs/${blog.id}`}
                        className="flex items-center gap-2 text-sm text-cyan-400 hover:text-cyan-300 transition"
                    >
                        Read
                        <ArrowRight size={16} />
                    </Link>

                    {isOwner && (
                        <button
                            onClick={() => onDelete(blog.id)}
                            className="text-slate-500 hover:text-red-400 transition"
                            title="Delete blog"
                        >
                            <Trash2 size={16} />
                        </button>
                    )}

                </div>

            </div>

            {/* Comments */}
            {commentsOpen && (

                <div className="mt-5 pt-4 border-t border-slate-800">

                    {/* Add comment */}
                    <div className="flex gap-2">

                        <input
                            type="text"
                            value={commentText}
                            onChange={(e) =>
                                setCommentText(e.target.value)
                            }
                            onKeyDown={(e) => {
                                if (e.key === "Enter") {
                                    handleAddComment();
                                }
                            }}
                            placeholder="Write a comment..."
                            className="flex-1 bg-[#0b0f19] border border-slate-700 px-3 py-2 text-sm text-white outline-none focus:border-cyan-500"
                        />

                        <button
                            onClick={handleAddComment}
                            disabled={commentLoading}
                            className="px-3 text-cyan-400 hover:text-cyan-300"
                        >
                            <Send size={18} />
                        </button>

                    </div>

                    {/* Comment list */}
                    <div className="mt-4 space-y-3">

                        {comments.length === 0 ? (

                            <p className="text-sm text-slate-500">
                                No comments yet.
                            </p>

                        ) : (

                            comments.map(comment => (

                                <div
                                    key={comment.id}
                                    className="bg-[#0b0f19] border border-slate-800 p-3"
                                >

                                    <div className="flex items-center justify-between">

                                        <span className="text-sm text-cyan-400">
                                            @{comment.username}
                                        </span>

                                        {comment.username === currentUsername && (

                                            <button
                                                onClick={() =>
                                                    handleDeleteComment(
                                                        comment.id
                                                    )
                                                }
                                                className="text-slate-600 hover:text-red-400 transition"
                                            >
                                                <Trash2 size={15} />
                                            </button>

                                        )}

                                    </div>

                                    <p className="text-sm text-slate-300 mt-2">
                                        {comment.content}
                                    </p>

                                </div>

                            ))

                        )}

                    </div>

                </div>

            )}

        </div>
    );
}

export default BlogCard;