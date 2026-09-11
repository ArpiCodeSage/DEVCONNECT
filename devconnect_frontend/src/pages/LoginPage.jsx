import React,{useState} from 'react';
import { useNavigate,Link } from 'react-router-dom';
import API from '../axios';
import { LogIn,User,AlertCircle,CheckCircle2,Lock } from 'lucide-react';

const LoginPage=()=>{
    const navigate=useNavigate();
    const [credentials,setCredentials]=useState({
        usernameOrEmail:'',
        password:''
    })
    const [error,setError]=useState('')
    const [loading,setLoading]=useState(false)
    const [success,setSuccess]=useState('')

    const handleChange=(e)=>{
        setCredentials({
            ...credentials,
            [e.target.name]:e.target.value
    })   
    } 

    const handleSubmit=async (e)=>{
        e.preventDefault()
        setError('')
        setSuccess('')
        setLoading(true)
        try{
            const response=await API.post("/auth/login",credentials)
            const {token}=response.data 
            localStorage.setItem('token',token)
            localStorage.setItem('username',credentials.usernameOrEmail)
            setSuccess('Login Successful')
            setTimeout(()=>{
                navigate('/feed')
                window.location.reload()
            },1500)
        }
       catch (error) {
    console.error("Login failed:", error);

    if (error.response?.status === 403) {
        setError("Invalid username or password.");
    } else {
        setError(
            error.response?.data?.message ||
            error.response?.data?.error ||
            "Login failed. Please try again."
        );
    }
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
          <div className="w-12 h-12 rounded-xl bg-cyan-500/10 border border-cyan-500/30 flex items-center justify-center text-cyan-400 mx-auto mb-3">
            <LogIn size={24} />
          </div>
          <h2 className="text-2xl font-bold text-white tracking-tight">Welcome Back</h2>
          <p className="text-slate-400 text-sm mt-1">Sign in to your DevConnect account</p>
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
            <span>{success} Redirecting to feed...</span>
          </div>
        )}
        {/* Form */}
        <form onSubmit={handleSubmit} className="space-y-5">
          
          {/* Username / Email Input */}
          <div>
            <label className="block text-xs font-mono uppercase tracking-wider text-slate-400 mb-1.5">Username or Email</label>
            <div className="relative">
              <User size={18} className="absolute left-3 top-1/2 -translate-y-1/2 text-slate-500" />
              <input
                type="text"
                name="usernameOrEmail"
                required
                value={credentials.usernameOrEmail}
                onChange={handleChange}
                placeholder="ari_dev or ari@devconnect.com"
                className="w-full bg-slate-900 border border-slate-800 rounded-lg pl-10 pr-4 py-2.5 text-sm text-white placeholder-slate-600 focus:outline-none focus:border-cyan-500 transition"
              />
            </div>
          </div>
          {/* Password Input */}
          <div>
            <label className="block text-xs font-mono uppercase tracking-wider text-slate-400 mb-1.5">Password</label>
            <div className="relative">
              <Lock size={18} className="absolute left-3 top-1/2 -translate-y-1/2 text-slate-500" />
              <input
                type="password"
                name="password"
                required
                value={credentials.password}
                onChange={handleChange}
                placeholder="••••••••"
                className="w-full bg-slate-900 border border-slate-800 rounded-lg pl-10 pr-4 py-2.5 text-sm text-white placeholder-slate-600 focus:outline-none focus:border-cyan-500 transition"
              />
            </div>
          </div>
          {/* Submit Button */}
          <button
            type="submit"
            disabled={loading}
            className="w-full py-2.5 rounded-lg bg-cyan-600 hover:bg-cyan-500 text-white font-medium text-sm transition shadow-lg shadow-cyan-600/20 disabled:opacity-50 cursor-pointer"
          >
            {loading ? 'Authenticating...' : 'Sign In'}
          </button>
        </form>
        {/* Footer Link */}
        <div className="text-center mt-6 text-xs text-slate-400">
          Don't have an account?{' '}
          <Link to="/register" className="text-cyan-400 hover:underline font-medium">
            Create account
          </Link>
        </div>
      </div>
    </div>
  );
};

export default LoginPage;