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
            <h1>Hello World!</h1>
            <br/>
            <h1>Token:</h1>
            <p>{userToken}</p>
            <br/>
            <UserInput callback={setInputValue} />
            <br/>
            <p>User Input: {inputValue}</p>
            <br/>
            <Denomination userToken={userToken} userInputValue={inputValue}/>
        </div>
    );
}

export default App;
