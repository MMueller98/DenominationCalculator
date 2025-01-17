import axios from "axios";

export const fetchUserToken = async () => {
    console.log(`userApi.getUserToken(): fetch user token`)
    const response = await axios.get("http://localhost:8080/v1/user/token");

    return response.data;
}