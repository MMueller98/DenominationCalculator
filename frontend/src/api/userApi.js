import axios from "axios";

export const getUserToken = async () => {
    const response = await axios.get("http://localhost:8080/v1/user/token");

    return response.data;
}