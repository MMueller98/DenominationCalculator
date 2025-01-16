import "../styles/DenominationResultTable.css"

const DenominationResultTable = ({ denominationResult }) => {
    const inputValue = denominationResult?.amount;
    const denomination = denominationResult?.denomination;
    const difference = denominationResult?.difference;

    return (
        <>
            {denomination?.length > 0 ? (
                <>
                <h2>Denomination of {inputValue}</h2>
                <div className="table-container">

                    {/* Denomination Table */}
                    <table className="styled-table">
                        <thead>
                        <tr>
                            <th>CashType</th>
                            <th>Amount</th>
                        </tr>
                        </thead>
                        <tbody>
                        {
                            denomination.map((entry, index) => (
                                <tr key={index}>
                                    <td>{entry.cashType}</td>
                                    <td>{entry.amount}</td>
                                </tr>
                            ))
                        }
                        </tbody>
                    </table>

                    {/* Difference Table */}
                    {difference?.length > 0 ? (
                        <table className="styled-table">
                            <thead>
                            <tr>
                                <th>CashType</th>
                                <th>Difference</th>
                            </tr>
                            </thead>
                            <tbody>
                            {
                                denomination.map((entry, index) => (
                                    <tr key={index}>
                                        <td>{entry.cashType}</td>
                                        <td>{entry.amount}</td>
                                    </tr>
                                ))
                            }
                            </tbody>
                        </table>
                    ) : null}
                </div>
                </>
            ) : null}
        </>
    );
}

export default DenominationResultTable;
