import { useState, useEffect } from "react";
import { getUserToken } from "../services/CalculationService";

export const useUserToken = () => {
    const [userToken, setUserToken] = useState(null);

    useEffect(() => {
        if (!userToken) {
            getUserToken(setUserToken);
        }
    }, [userToken]);

    return userToken;
}