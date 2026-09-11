import React, {
  useEffect,
  useRef,
  useState
} from "react";

import {
  useNavigate,
  useParams,
  Link
} from "react-router-dom";

import {
  ArrowLeft,
  Save,
  Send
} from "lucide-react";

import ReactQuill from "react-quill-new";
import "react-quill-new/dist/quill.snow.css";

import API from "../axios";


function BlogFormPage() {

  const { id } = useParams();
  const navigate = useNavigate();

  const isEditing = Boolean(id);

  const quillRef = useRef(null);

  const [formData, setFormData] = useState({
    title: "",
    content: "",
  });

  const [loading, setLoading] = useState(false);
  const [fetching, setFetching] = useState(isEditing);
  const [uploadingImage, setUploadingImage] = useState(false);


  // -----------------------------
  // FETCH BLOG FOR EDITING
  // -----------------------------

  useEffect(() => {

    if (!isEditing) {
      return;
    }

    const fetchBlog = async () => {

      try {

        const response = await API.get(`/blogs/${id}`);

        const blog = response.data;

        const username = localStorage.getItem("username");

        if (blog.username !== username) {

          alert(
            "You are not allowed to edit this blog."
          );

          navigate(`/blogs/${id}`);

          return;
        }

        setFormData({
          title: blog.title || "",
          content: blog.content || "",
        });

      } catch (error) {

        console.error(
          "Failed to fetch blog:",
          error
        );

        alert("Failed to load blog.");

        navigate("/blogs");

      } finally {

        setFetching(false);

      }

    };

    fetchBlog();

  }, [id, isEditing, navigate]);


  // -----------------------------
  // TITLE
  // -----------------------------

  const handleTitleChange = (e) => {

    setFormData((prev) => ({
      ...prev,
      title: e.target.value,
    }));

  };


  // -----------------------------
  // CONTENT
  // -----------------------------

  const handleContentChange = (value) => {

    setFormData((prev) => ({
      ...prev,
      content: value,
    }));

  };


  // -----------------------------
  // IMAGE UPLOAD
  // -----------------------------

  const handleImageUpload = () => {

    const input = document.createElement("input");

    input.setAttribute(
      "type",
      "file"
    );

    input.setAttribute(
      "accept",
      "image/*"
    );

    input.click();


    input.onchange = async () => {

      const file = input.files[0];

      if (!file) {
        return;
      }

      try {

        setUploadingImage(true);

        const imageData = new FormData();

        imageData.append(
          "image",
          file
        );


        // Upload image to backend

        const response = await API.post(
          "/uploads/image",
          imageData
        );


        // Create full production image URL

        let imageUrl;

        if (response.data.startsWith("http")) {

          imageUrl = response.data;

        } else {

          imageUrl =
            `https://devconnect-backend-m0un.onrender.com${response.data}`;

        }


        // Insert image into Quill editor

        const editor =
          quillRef.current.getEditor();

        const range =
          editor.getSelection(true);

        editor.insertEmbed(
          range.index,
          "image",
          imageUrl
        );

        editor.setSelection(
          range.index + 1
        );


      } catch (error) {

        console.error(
          "Failed to upload image:",
          error
        );

        alert(
          "Failed to upload image."
        );

      } finally {

        setUploadingImage(false);

      }

    };

  };


  // -----------------------------
  // QUILL TOOLBAR
  // -----------------------------

  const modules = {

    toolbar: {

      container: [

        [
          {
            header: [
              1,
              2,
              3,
              false
            ]
          }
        ],

        [
          "bold",
          "italic",
          "underline",
          "strike"
        ],

        [
          {
            list: "ordered"
          },
          {
            list: "bullet"
          }
        ],

        [
          "blockquote",
          "code-block"
        ],

        [
          "link",
          "image"
        ],

        [
          "clean"
        ]

      ],

      handlers: {
        image: handleImageUpload,
      }

    }

  };


  // -----------------------------
  // SUBMIT
  // -----------------------------

  const handleSubmit = async (e) => {

    e.preventDefault();

    if (!formData.title.trim()) {

      alert(
        "Please enter a title."
      );

      return;
    }


    const plainText =
      formData.content
        .replace(/<[^>]*>/g, "")
        .replace(/&nbsp;/g, " ")
        .trim();


    if (
      !plainText &&
      !formData.content.includes("<img")
    ) {

      alert(
        "Please write some content."
      );

      return;
    }


    try {

      setLoading(true);

      let response;


      if (isEditing) {

        response = await API.put(
          `/blogs/${id}`,
          formData
        );

      } else {

        response = await API.post(
          "/blogs",
          formData
        );

      }


      navigate(
        `/blogs/${response.data.id}`
      );


    } catch (error) {

      console.error(
        "Failed to save blog:",
        error
      );

      alert(
        error.response?.data?.message ||
        "Failed to save blog."
      );

    } finally {

      setLoading(false);

    }

  };


  // -----------------------------
  // LOADING
  // -----------------------------

  if (fetching) {

    return (

      <div
        className="
          min-h-screen
          bg-[#0b0f19]
          flex
          items-center
          justify-center
        "
      >

        <p className="text-slate-500">
          Loading blog...
        </p>

      </div>

    );

  }


  return (

    <div
      className="
        min-h-screen
        bg-[#0b0f19]
        px-4
        py-10
      "
    >

      <div
        className="
          max-w-4xl
          mx-auto
        "
      >


        {/* BACK */}

        <Link
          to="/blogs"
          className="
            inline-flex
            items-center
            gap-2
            text-sm
            text-slate-400
            hover:text-white
            transition
            mb-6
          "
        >

          <ArrowLeft size={16} />

          Back to blogs

        </Link>


        {/* HEADER */}

        <div className="mb-6">

          <h1
            className="
              text-2xl
              font-bold
              text-white
            "
          >

            {isEditing
              ? "Edit Blog"
              : "Write a Blog"}

          </h1>


          <p
            className="
              text-slate-500
              text-sm
              mt-2
            "
          >

            {isEditing
              ? "Update your blog post."
              : "Share your knowledge with the developer community."}

          </p>

        </div>


        {/* FORM */}

        <form
          onSubmit={handleSubmit}
          className="
            bg-[#111827]
            border
            border-slate-800
            p-6
          "
        >


          {/* TITLE */}

          <div>

            <label
              className="
                block
                text-sm
                text-slate-300
                mb-2
              "
            >

              Title

            </label>


            <input
              type="text"
              value={formData.title}
              onChange={handleTitleChange}
              placeholder="Enter your blog title..."
              className="
                w-full
                bg-slate-900
                border
                border-slate-700
                px-4
                py-3
                text-white
                placeholder-slate-600
                outline-none
                focus:border-cyan-500
                transition
              "
            />

          </div>


          {/* CONTENT */}

          <div className="mt-6">

            <div
              className="
                flex
                items-center
                justify-between
                mb-2
              "
            >

              <label
                className="
                  block
                  text-sm
                  text-slate-300
                "
              >

                Content

              </label>


              {uploadingImage && (

                <span
                  className="
                    text-xs
                    text-cyan-400
                  "
                >

                  Uploading image...

                </span>

              )}

            </div>


            <ReactQuill
              ref={quillRef}
              theme="snow"
              value={formData.content}
              onChange={handleContentChange}
              modules={modules}
              placeholder="Write your blog here..."
            />

          </div>


          {/* BUTTONS */}

          <div
            className="
              flex
              items-center
              justify-end
              gap-3
              mt-20
            "
          >

            <Link
              to="/blogs"
              className="
                px-4
                py-2
                text-sm
                text-slate-400
                hover:text-white
                transition
              "
            >

              Cancel

            </Link>


            <button
              type="submit"
              disabled={
                loading ||
                uploadingImage
              }
              className="
                flex
                items-center
                gap-2
                px-5
                py-2
                bg-indigo-600
                hover:bg-indigo-500
                disabled:opacity-50
                text-white
                text-sm
                transition
              "
            >

              {isEditing ? (
                <Save size={16} />
              ) : (
                <Send size={16} />
              )}


              {loading
                ? "Saving..."
                : isEditing
                  ? "Save Changes"
                  : "Publish Blog"}

            </button>

          </div>

        </form>

      </div>

    </div>

  );

}

export default BlogFormPage;