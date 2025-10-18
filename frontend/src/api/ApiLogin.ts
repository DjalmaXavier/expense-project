import axios from "axios";

const API_BASE_URL = import.meta.env.VITE_API_BASE_URL;
const LOGIN_ENDPOINT = import.meta.env.VITE_AUTH_LOGIN_ENDPOINT;
const REFRESHTOKEN_ENDPOINT = import.meta.env.VITE_REFRESHTOKEN;

export const loginUser = async (user: any) => {
    try {
        const response = await axios.post(`${API_BASE_URL}${LOGIN_ENDPOINT}`, user, {
            headers: {
                "Content-Type": "application/json"
            },
        });
        
        return response.data;
    } catch (err: any) {
        if(err.response) {
            alert(err.response.data)
        };
        return;
    }
}

export const refreshToken = async (token: string, sub: string) => {
    try {
        const response = await axios.post(`${API_BASE_URL}${REFRESHTOKEN_ENDPOINT}`, id , {
            headers: {
                "Content-Type": "application/json",
                "Authorization": "Bearer " + token
            },
        });
        
        console.log("Retorno do back: " + response )
        const newToken = response.data?.token;
        console.log("teste feito = ", newToken);
        return newToken
    } catch (error) {
        console.error("Falha ao renovar token:", error);
        // Remove o token inválido se a renovação falhar
        localStorage.removeItem("token");
        return null;
    }
}