import axios from "axios";

const API_BASE_URL = import.meta.env.VITE_API_BASE_URL;
const LOGIN_ENDPOINT = import.meta.env.VITE_AUTH_LOGIN_ENDPOINT;
//const REFRESHTOKEN_ENDPOINT = import.meta.env.VITE_REFRESHTOKEN;

export const loginUser = async (user: any) => {
    try {
        const response = await axios.post(`${API_BASE_URL}${LOGIN_ENDPOINT}`, user, {
            headers: {
                "Content-Type": "application/json"
            },
            withCredentials: true,
        });
        
        return response.data;
    } catch (err: any) {
        if(err.response) {
            alert(err.response.data)
        };
        return;
    }
}

