import {use, useEffect, useRef, useState} from "react";
import "../styles/App.css";

import { getPreviousDenominations, getUserToken } from "../services/CalculationService";
import DenominationResultTable from "./DenominationResultTable";
import UserInput from "./UserInput";
import CalculationOption from "./CalculationOption";

function App() {
    const isInitialRendering = useRef(true);
    const [userToken, setUserToken] = useState(null)
    const [useBackend, setUseBackend] = useState(true);
    const [denominationResponse, setDenominationResponse] = useState(null)

    useEffect(() => {
        if (!userToken) {
            getUserToken(setUserToken);
        }
    }, [])
    
    useEffect(() => {
        if (!isInitialRendering.current && userToken) {
            console.log(`User Token changed to ${userToken}`)
            getPreviousDenominations(userToken, setDenominationResponse)
        }

        isInitialRendering.current = false;
    }, [userToken])

    return (
        <div className="App">
            <h1>Denomination Calculator</h1>
            <p>User Token: {userToken}</p>
            <p>Use Backend: {useBackend ? "true" : "false"}</p>
            <CalculationOption useBackend={useBackend} setUseBackend={setUseBackend}/>
            <UserInput userToken={userToken} useBackend={useBackend} callback={setDenominationResponse}/>
            <DenominationResultTable denominationResponse={denominationResponse} />
        </div>
    );
}

export default App;
