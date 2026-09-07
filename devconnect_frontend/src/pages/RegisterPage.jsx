import React,{useState} from 'react';
import { useNavigate,Link } from 'react-router-dom';
import API from '../axios';
import {UserPlus,User,Mail,Lock,AlertCircle,CheckCircle2,Eye,EyeOff} from 'lucide-react';

const RegisterPage=()=>{
    const navigate=useNavigate();
    const [formData,setFormData]=useState({
        username:'',
        email:'',
        password:''
    })
    const [showPassword, setShowPassword] = useState(false);
    const [error,setError]=useState('')
    const [loading,setLoading]=useState(false)
    const [success,setSuccess]=useState('')

    const handleChange=(e)=>{ //input change handler: e is triggered whenever you type a key in any input box
        setFormData({
            ...formData,
            [e.target.name]:e.target.value
        })

    }
    const handleSubmit=async(e)=>{ //form submit handler
      e.preventDefault();//prevents default html behavour of reloading page whenever a form is submitted
      setError('')
      setSuccess('')
      setLoading(true)    
      try{
        const response=await API.post('/auth/register',formData)//sends fomData as JSON body
        setSuccess(response.data || 'user registered successfully!')
        setTimeout(()=>{//so the user can see the loading screen
            navigate('/login')
        },1500)
      }
      catch(err)
      {
        setError(err.response?.data?.message || err.response?.data || 'Registration failed.Please try again')
      }
      finally{
        setLoading(false)
      }
    }
    
  return (
    <div className="min-h-[80vh] flex items-center justify-center px-4 py-12">
      <div className="w-full max-w-md bg-[#0f172a]/90 backdrop-blur-md border border-slate-800 p-8 rounded-2xl shadow-2xl">
        
        {/* Header */}
        <div className="text-center mb-8">
          <div className="w-12 h-12 rounded-xl bg-indigo-600/10 border border-indigo-500/30 flex items-center justify-center text-indigo-400 mx-auto mb-3">
            <UserPlus size={24} />
          </div>
          <h2 className="text-2xl font-bold text-white tracking-tight">Create an Account</h2>
          <p className="text-slate-400 text-sm mt-1">Join the DevConnect developer network</p>
        </div>
        {/* Error Alert */}
        {error && (
          <div className="mb-6 p-3 rounded-lg bg-red-500/10 border border-red-500/30 text-red-400 text-xs font-medium flex items-center gap-2">
            <AlertCircle size={16} />
            <span>{error}</span>
          </div>
        )}
        {/* Success Alert */}
        {success && (
          <div className="mb-6 p-3 rounded-lg bg-emerald-500/10 border border-emerald-500/30 text-emerald-400 text-xs font-medium flex items-center gap-2">
            <CheckCircle2 size={16} />
            <span>{success} Redirecting to login...</span>
          </div>
        )}
        {/* Form */}
        <form onSubmit={handleSubmit} className="space-y-5">
          
          {/* Username Input */}
          <div>
            <label className="block text-xs font-mono uppercase tracking-wider text-slate-400 mb-1.5">Username</label>
            <div className="relative">
              <User size={18} className="absolute left-3 top-1/2 -translate-y-1/2 text-slate-500" />
              <input
                type="text"
                name="username"
                required
                value={formData.username}
                onChange={handleChange}
                placeholder="ari_dev"
                className="w-full bg-slate-900 border border-slate-800 rounded-lg pl-10 pr-4 py-2.5 text-sm text-white placeholder-slate-600 focus:outline-none focus:border-indigo-500 transition"
              />
            </div>
          </div>
          {/* Email Input */}
          <div>
            <label className="block text-xs font-mono uppercase tracking-wider text-slate-400 mb-1.5">Email Address</label>
            <div className="relative">
              <Mail size={18} className="absolute left-3 top-1/2 -translate-y-1/2 text-slate-500" />
              <input
                type="email"
                name="email"
                required
                value={formData.email}
                onChange={handleChange}
                placeholder="ari@devconnect.com"
                className="w-full bg-slate-900 border border-slate-800 rounded-lg pl-10 pr-4 py-2.5 text-sm text-white placeholder-slate-600 focus:outline-none focus:border-indigo-500 transition"
              />
            </div>
          </div>
          {/* Password Input */}
          <div>
            <label className="block text-xs font-mono uppercase tracking-wider text-slate-400 mb-1.5">Password</label>
            <div className="relative">
              <Lock size={18} className="absolute left-3 top-1/2 -translate-y-1/2 text-slate-500" />
              <input
               type={showPassword ? 'text' : 'password'}
                name="password"
                required
                value={formData.password}
                onChange={handleChange}
                placeholder="••••••••"
                className="w-full bg-slate-900 border border-slate-800 rounded-lg pl-10 pr-4 py-2.5 text-sm text-white placeholder-slate-600 focus:outline-none focus:border-indigo-500 transition"
              />
            </div>
          </div>
          {/* Submit Button */}
          <button
            type="submit"
            disabled={loading}
            className="w-full py-2.5 rounded-lg bg-indigo-600 hover:bg-indigo-500 text-white font-medium text-sm transition shadow-lg shadow-indigo-600/20 disabled:opacity-50 cursor-pointer"
          >
            {loading ? 'Registering...' : 'Create Account'}
          </button>
        </form>
        {/* Footer Link */}
        <div className="text-center mt-6 text-xs text-slate-400">
          Already have an account?{' '}
          <Link to="/login" className="text-indigo-400 hover:underline font-medium">
            Sign in
          </Link>
        </div>
      </div>
    </div>
  );
};



export default RegisterPage;
