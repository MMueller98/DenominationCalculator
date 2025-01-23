import axios from "axios";

const BASE_PATH = "http://localhost:8080/v1/denomination";

const fetchDenominationFromBackend = async (userToken, userInputValue) => {
    if (!userToken) {
        throw new Error("InvalidInput to calculateDenomination: UserToken is not defined!");
    }
    if (!userInputValue) {
        throw new Error("InvalidInput to calculateDenomination: UserInputValue is null!");
    }

    const request = {
        "value": userInputValue,
        "currency": "EURO"
    };

    const headers = {
        headers: {
            "Content-Type": "application/json",
            "X-User-Token": userToken
        }
    };

    const denominationResponse = await axios.post(`${BASE_PATH}/calculate`, request, headers);

    return denominationResponse.data;
}

const fetchPreviousDenominations = async (userToken) => {
    if (!userToken) {
        throw new Error("InvalidInput to fetchPreviousCalculation: UserToken is not defined!");
    }

    const headers = {
        headers: {
            "Content-Type": "application/json",
            "X-User-Token": userToken
        }
    };

    const lastCalculationResponse = await axios.get(`${BASE_PATH}/last-calculation`, headers);

    return lastCalculationResponse.data;
}

const fetchPreviousDenomination = async (userToken) => {
    const headers = {
        headers: {
            "Content-Type": "application/json",
            "X-User-Token": userToken
        }
    };

    const denomination = await axios.get(`${BASE_PATH}/last-denomination`, headers);
    return denomination?.data?.value && denomination?.data?.currency && denomination?.data?.denominationParts
        ? denomination.data
        : null;
}

const persistDenomination = async (denomination, userToken) => {
    const headers = {
        headers: {
            "Content-Type": "application/json",
            "X-User-Token": userToken
        }
    };

    let creationResponse;
    try {
        await axios.post(`${BASE_PATH}/persist`, denomination, headers);
    } catch (error) {
        console.log("Error while persist denomination: " + error)
    }
    return creationResponse?.data;
}

export {fetchDenominationFromBackend, fetchPreviousDenominations, fetchPreviousDenomination, persistDenomination};