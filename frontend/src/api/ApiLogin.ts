import axios from "axios";

const API_BASE_URL = import.meta.env.VITE_API_BASE_URL;
const LOGIN_ENDPOINT = import.meta.env.VITE_AUTH_LOGIN_ENDPOINT;
const REFRESHTOKEN_ENDPOINT = import.meta.env.VITE_REFRESHTOKEN;
const LOGOUT_ENDPOINT = import.meta.env.VITE_LOGOUT_ENDPOINT;

export const loginUser = async (user: any) => {
    try {
        const response = await axios.post(`${API_BASE_URL}${LOGIN_ENDPOINT}`, user, {
            headers: {
                "Content-Type": "application/json"
            },
            withCredentials: true,
        });
        return response.data
    } catch (err: any) {
        return null;
    }
}

export const logoutUser = async () => {
    try {
       axios.post(`${API_BASE_URL}${LOGOUT_ENDPOINT}`, {}, {
            withCredentials: true,
        });     
    } catch (err) {
        console.log("Error logging out:", err);
    }
}


export const refreshToken = async () => {
    try {
        const response = await axios.post(`${API_BASE_URL}${REFRESHTOKEN_ENDPOINT}`, {}, {
            withCredentials: true,
        });
        return response.data;
    } catch (err) {
        return null;
    }
}
