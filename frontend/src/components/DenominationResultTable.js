import React from "react"
import "../styles/DenominationResultTable.css"

const DenominationResultTable = React.memo(({denominationResponse}) => {
    const denomination = denominationResponse?.denomination;
    const denominationParts = denomination?.denominationParts;
    const inputValue = denomination?.value;

    const previousDenomination = denominationResponse?.previousDenomination;
    const previousInputValue = previousDenomination?.value;
    const difference = denominationResponse?.difference;

    return (
        <>
            {denomination ? (
                <>
                <div className="table-container">
                    <div>
                        <h2>Denomination of {inputValue}</h2>
                        <table className="styled-table">
                            <thead>
                            <tr>
                                <th>CashType</th>
                                <th>Amount</th>
                            </tr>
                            </thead>
                            <tbody>
                                {
                                    denominationParts
                                        .filter(entry => entry.amount !== 0) // Filtert Einträge mit amount = 0 heraus
                                        .map((entry, index) => (
                                            <tr key={index}>
                                                <td>{entry.cashType}</td>
                                                <td>{entry.amount}</td>
                                            </tr>
                                        ))
                                }
                            </tbody>
                        </table>
                    </div>

                {difference?.length ? (
                    <div>
                        <h2>Difference to {previousInputValue}</h2>
                            <table className="styled-table">
                                <thead>
                                <tr>
                                    <th>CashType</th>
                                    <th>Difference</th>
                                </tr>
                                </thead>
                                <tbody>
                                {
                                    difference
                                        .map((entry, index) => (
                                            <tr key={index}>
                                                <td>{entry.cashType}</td>
                                                <td>{entry.amount}</td>
                                            </tr>
                                        ))
                                }
                                </tbody>
                            </table>
                    </div>) : null}
                </div>
                </>
            ) : null}
        </>
    );
})

export default DenominationResultTable;
