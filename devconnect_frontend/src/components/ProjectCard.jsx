import React from "react";
import { ExternalLink, Globe, Trash2 } from "lucide-react";

function ProjectCard({project,isOwner,onDelete}){
const technologies = project.techStack
  ? project.techStack.split(",").map((tech) => tech.trim())
  : [];



  return (
    <div className="bg-[#111827] border border-slate-800 p-5 hover:border-slate-700 transition">

      {/* Header */}
      <div className="flex justify-between items-start gap-3">

        <h3 className="text-lg font-semibold text-white">
          {project.title}
        </h3>

        <ExternalLink
          size={17}
          className="text-slate-500 shrink-0"
        />

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

    </div>
  );
}
export default ProjectCard;