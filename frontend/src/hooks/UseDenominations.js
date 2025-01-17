import { useState, useEffect } from "react";
import { getPreviousDenominations } from "../services/CalculationService";

export const useDenominations = (userToken) => {
    const [denominationResponse, setDenominationResponse] = useState(null);

    useEffect(() => {
        if (userToken) {
            getPreviousDenominations(userToken, setDenominationResponse);
        }
    }, [userToken]);

    return denominationResponse;
}
