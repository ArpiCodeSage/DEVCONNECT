// In React, whenever you make an HTTP request to Spring Boot (e.g., fetching a profile or adding a project), you shouldn't have to manually write Authorization: Bearer <token> in every single component!

// We create an Axios instance with an Interceptor. The interceptor automatically reads your saved JWT token from localStorage and attaches Authorization: Bearer <token> to EVERY outgoing API request!

import axios from 'axios';//Imports the axios library, which is the industry-standard JavaScript tool used to make HTTP requests (GET, POST, PUT, DELETE) from React to Spring Boot.
const API=axios.create({
    baseURL:'https://devconnect-backend-m0un.onrender.com/api'//default base URL for all backend requests
   
})
API.interceptors.request.use((config)=>{//An interceptor is like a middleman checkpoint. It intercepts EVERY SINGLE OUTGOING HTTP REQUEST right before it leaves React to head over the network to Spring Boot.
    const token=localStorage.getItem('token');//Reads the saved JWT token string out of the browser's localStorage.
    if(token){
        config.headers.Authorization=`Bearer ${token}`;
 // If a token exists in localStorage (meaning the user is logged in), the interceptor automatically attaches the header: Authorization: Bearer <token> onto the request configuration (config)
    }
    return config;
},
(error)=>{
    return Promise.reject(error);// If a client-side request error occurs, rejects the promise cleanly.
});
export default API;//Exports the pre-configured API instance so any React component or page can import it (import API from '../api/axios') and make authenticated API calls effortlessly! 


//PROMISE:

// Imagine you order food at a fast-food counter:

// You pay for your food. The cashier hands you a Buzzer / Token (A Promise).
// While your food is cooking in the kitchen, JavaScript doesn't freeze or block the whole website. It continues running other code.
// When the kitchen finishes cooking your food (Spring Boot returns HTTP response):
// Resolved (Success): Your buzzer vibrates ➔ You get your data (.then()).
// Rejected (Error): The kitchen ran out of ingredients ➔ Error pops up (.catch()).
// A Promise is just JavaScript's way of handling asynchronous actions (network requests that take time to complete) without freezing the user's browser screen!


//An Axios interceptor is a special function that lets you catch and change HTTP requests or responses before they reach your app or leave your device.