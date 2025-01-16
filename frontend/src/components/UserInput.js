import {useState} from "react";
import "../styles/UserInput.css"

const UserInput = ({ callback }) => {
    const inputId = "numberInput"
    const [inputValue, setInputValue] = useState();

    const handleInputChange = (event) => {
        setInputValue(event.target.value)
    }

    const handleSubmit = () => {
        const inputElement = document.getElementById(inputId);
        if (inputElement.checkValidity()) {
            callback(inputValue)
        } else {
            inputElement.reportValidity();
        }

        console.log(`Check validity: ${inputElement.checkValidity()}`)
        console.log("Submit")
        console.log(`Number: ${inputValue}`)
    };

    return (
        <div class="input-container">
            <div class="input-wrapper">
                <input
                    id={inputId}
                    class="number-input"
                    type="number"
                    value={inputValue}
                    onChange={handleInputChange}
                    placeholder="Enter an amount of money"
                    step="0.01"
                    min="0"
                />
                <button class="submit-button" onClick={handleSubmit}>Submit</button>
            </div>
        </div>
    );
}

export default UserInput;