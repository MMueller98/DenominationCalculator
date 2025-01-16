import {useEffect, useState} from "react";
import "../styles/App.css";
import useUserToken from "../hooks/useUserToken";
import UserInput from "./UserInput";
import Denomination from "./Denomination";
import {getUserToken} from "../api/userApi";

function App() {
    const [userToken, setUserToken] = useState("")
    const [inputValue, setInputValue] = useState();

    useUserToken(setUserToken);

    return (
        <div className="App">
            <h1>Denomination Calculator</h1>
            <UserInput callback={setInputValue} />
            <Denomination userToken={userToken} userInputValue={inputValue}/>
        </div>
    );
}

export default App;
