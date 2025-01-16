import {useEffect, useState} from "react";
import {getUserToken} from "../api/userApi";

const useUserToken = (tokenCallback) => {
    useEffect(() => {
        const getToken = async () => {
            let userToken = localStorage.getItem("X-User-Token");

            if (userToken) {
                console.log(`Token found in LocalStorage: ${userToken}`);
            } else {
                userToken = await getUserToken();
                console.log(`Token not found in LocalStorage. Fetched Token: ${userToken}`);
                localStorage.setItem("X-User-Token", userToken);
            }

            tokenCallback(userToken);
        }

        getToken();
    }, [])
}

export default useUserToken