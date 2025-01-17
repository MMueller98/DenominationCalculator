import {useState} from "react";
import "../styles/UserInput.css"
import { calculateDenomination } from "../services/DenominationService";

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
    };

    return (
        <div className="input-container">
            <div className="input-wrapper">
                <input
                    id={inputId}
                    className="number-input"
                    type="number"
                    onChange={handleInputChange}
                    placeholder="Enter an amount of money in €"
                    step="0.01"
                    min="0.01"
                />
                <button className="submit-button" onClick={handleSubmit}>Submit</button>
            </div>
        </div>
    );
}

export default UserInput;