import {useState} from "react";
import "../styles/UserInput.css"
import { calculateDenomination } from "../services/CalculationService";

const UserInput = ({ userToken, useBackend, callback }) => {
    const inputId = "numberInput"
    const [inputValue, setInputValue] = useState(null);

    const handleInputChange = (event) => {
        setInputValue(event.target.value)
    }

    const handleSubmit = () => {
        const inputElement = document.getElementById(inputId);
        if (inputElement.checkValidity()) {
            calculateDenomination(userToken, inputValue, callback, useBackend)
        } else {
            inputElement.reportValidity();
        }

        console.log(`Check validity: ${inputElement.checkValidity()}`)
        console.log("Submit")
        console.log(`Number: ${inputValue}`)
    };

    return (
        <div className="input-container">
            <div className="input-wrapper">
                <input
                    id={inputId}
                    className="number-input"
                    type="number"
                    onChange={handleInputChange}
                    placeholder="Enter an amount of money"
                    step="0.01"
                    min="0"
                />
                <button className="submit-button" onClick={handleSubmit}>Submit</button>
            </div>
        </div>
    );
}

export default UserInput;