import {useState} from "react";

const UserInput = ({ callback }) => {
    const inputId = "numberInput"
    const [inputValue, setInputValue] = useState(0);

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
        <div>
            <h2>Zahlen-Eingabe</h2>
            <div>
                <input
                    id={inputId}
                    type="number"
                    value={inputValue}
                    onChange={handleInputChange}
                    placeholder="Geben Sie eine Zahl ein"
                    step="0.01" // Erlaubt Eingaben mit bis zu zwei Nachkommastellen
                    min="0" // Stellt sicher, dass nur Werte >= 0 erlaubt sind
                />
                <button onClick={handleSubmit}>
                    Abschicken
                </button>
            </div>
        </div>
    );
}

export default UserInput;