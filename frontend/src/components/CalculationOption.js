import "../styles/CalculationOption.css"

const CalculationOption = ({useBackend, setUseBackend}) => {
    const handleOptionChange = (event) => {
        setUseBackend(event.target.value === "backend");
    };
    
    return (
        <div className="calculation-option-container">
            <h2 className="heading">Choose Calculation Option:</h2>
            <div className="calculation-option-wrapper">
                <label className="calculation-option">
                    <input
                        type="radio"
                        name="calculationOption"
                        value="frontend"
                        id="frontend"
                        className="calculation-input"
                        checked={useBackend === false}
                        onChange={handleOptionChange} 
                    />
                    Calculate in Frontend
                </label>
                <label className="calculation-option">
                    <input
                        type="radio"
                        name="calculationOption"
                        value="backend"
                        id="backend"
                        className="calculation-input"
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