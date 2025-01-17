import "../styles/CalculationOption.css"

const CalculationOption = ({useBackend, setUseBackend}) => {
    const handleOptionChange = (event) => {
        setUseBackend(event.target.value === "backend");
    };
    return (
        <div class="calculation-option-container">
            <h2 class="heading">Choose Calculation Option:</h2>
            <div class="calculation-option-wrapper">
                <label class="calculation-option">
                    <input
                        type="radio"
                        name="calculationOption"
                        value="frontend"
                        id="frontend"
                        class="calculation-input"
                        checked={useBackend === false}
                        onChange={handleOptionChange} 
                    />
                    Calculate in Frontend
                </label>
                <label class="calculation-option">
                    <input
                        type="radio"
                        name="calculationOption"
                        value="backend"
                        id="backend"
                        class="calculation-input"
                        checked={useBackend === true}
                        onChange={handleOptionChange} 
                    />
                    Calculate in Backend
                </label>
            </div>
        </div>
    );
}

export default CalculationOption;