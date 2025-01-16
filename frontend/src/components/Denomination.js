import {useEffect, useRef, useState} from "react";
import {calculateDenomination, fetchPreviousCalculation} from "../api/denominationApi";
import DenominationResultTable from "./DenominationResultTable";

const Denomination = ({ userToken, userInputValue }) => {
    const [denominationResult, setDenominationResult] = useState({});
    const isInitialRenderingFetch = useRef(true);
    const isInitialRenderingCalculate = useRef(true);

    useEffect(() => {
        if (isInitialRenderingFetch.current) {
            console.log("Initial Rendering Token");
        } else {
            console.log(`Get previous results for token ${userToken}`);
            fetchPreviousResults()
        }

        isInitialRenderingFetch.current = false
    }, [userToken]);

    useEffect(() => {
        if (isInitialRenderingCalculate.current) {
            console.log("Initial Rendering Calculate");
        } else {
            console.log(`calculate: Token ${userToken} and value ${userInputValue}`);
            calculate()
        }

        isInitialRenderingCalculate.current = false
    }, [userInputValue]);

    async function calculate() {
        try {
            const response = await calculateDenomination(userToken, userInputValue)
            console.log(response)
            setDenominationResult(response)
        } catch (error) {
            console.error(`Error in calculate: ${error.message}`)
        }
    }

    async function fetchPreviousResults() {
        try {
            const response = await fetchPreviousCalculation(userToken)
            console.log(response)
            setDenominationResult(response)
        } catch (error) {
            console.error(`Error in calculate: ${error.message}`)
        }
    }

    return (
        <DenominationResultTable denominationResponse={denominationResult}/>
    );
}

export default Denomination;