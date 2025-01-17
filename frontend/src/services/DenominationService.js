import {fetchDenominationFromBackend, fetchPreviousDenomination, fetchPreviousDenominations} from "./DenominationApi";
import { fetchUserToken } from "./UserApi";
import {calculateDenominationResponse} from "./CalculationService";

const calculateDenomination = (userToken, userInputValue, callback, calculateInBackend) => {
    if (calculateInBackend) {
        calculateDenominationInBackend(userToken, userInputValue, callback);
    } else {
        calculateDenominationInFrontend(userToken, userInputValue, callback)
    }
}

const calculateDenominationInBackend = async (userToken, userInputValue, callback) => {
    try {
        const response = await fetchDenominationFromBackend(userToken, userInputValue)
        callback(response)
    } catch (error) {
        console.error(`Error in calculate: ${error.message}`)
    }
}

const calculateDenominationInFrontend = async (userToken, userInputValue, callback) => {
    const previousDenomination = await fetchPreviousDenomination(userToken);
    const denominationResponse = calculateDenominationResponse(userToken, userInputValue, previousDenomination);

    callback(denominationResponse);
}

const getUserToken = async (callback) => {
    let userToken = localStorage.getItem("X-User-Token");

    if (userToken) {
        console.log(`Token found in LocalStorage: ${userToken}`);
    } else {
        userToken = await fetchUserToken();
        console.log(`Token not found in LocalStorage. Fetched Token: ${userToken}`);
        localStorage.setItem("X-User-Token", userToken);
    }

    callback(userToken)
}

const getPreviousDenominations = async (userToken, callback) => {
    try {
        const response = await fetchPreviousDenominations(userToken)
        callback(response)
    } catch (error) {
        console.error(`Error in calculate: ${error.message}`)
    }
}

export {calculateDenomination, getPreviousDenominations, getUserToken}