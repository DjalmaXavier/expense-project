import axios from "axios";

const API_BASE_URL = import.meta.env.VITE_API_BASE_URL;
const REGISTER_ENDPOINT = import.meta.env.VITE_AUTH_REGISTER_ENDPOINT;


export const registerUser = async (user: any) => {
    try {
        const response = await axios.post(`${API_BASE_URL}${REGISTER_ENDPOINT}`, user, {
            headers: {
                "Content-Type": "application/json"
            },
        });
        return response.data;
    } catch (err: any) {
        return err.response.data;
    }
}