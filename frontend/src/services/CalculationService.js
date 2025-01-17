import {CashTypes} from "../util/CashTypes";
import {persistDenomination} from "./DenominationApi";

export const calculateDenominationResponse = (userToken, userInputValue, previousDenomination) => {
    const denominationParts = calculateMinimumDenominationParts(userInputValue);
    const denomination = createDenomination(userInputValue, "EURO", denominationParts);
    const persistResponse = persistDenomination(denomination, userToken)

    const difference = calculateDifference(denomination, previousDenomination);

    const response = {
        "denomination": denomination,
        "previousDenomination": previousDenomination,
        "difference": difference
    }

    console.log("Response ready")
    console.log(response)

    return response;
}

const calculateMinimumDenominationParts = (userInputValue) => {
    const denominationParts = [];
    let euroCentValue = Math.round(userInputValue * 100);
    console.log(`calculateMinimumDenominationParts: ${userInputValue} -> ${euroCentValue}`)

    Object.values(CashTypes).forEach((cashType) => {
        const cashTypeValue = cashType.value;
        let count = 0;

        while (euroCentValue >= cashTypeValue) {
            count += 1;
            euroCentValue -= cashTypeValue;
        }

        denominationParts.push(createDenominationParts(count, cashType.displayName))
    });

    console.log(`Finished`)
    console.log(denominationParts)

    return denominationParts;
}

const calculateDifference = (denomination, previousDenomination) => {
    if (!denomination) {
        throw new Error("...")
    }
    if (!previousDenomination) {
        return null;
    }

    const difference = [];

    Object.values(CashTypes).forEach((cashType) => {
        const currentDenominationPart = extraDenominationPartByCashType(denomination, cashType);
        const currentAmount = parseInt(currentDenominationPart?.amount);
        const previousDenominationPart = extraDenominationPartByCashType(previousDenomination, cashType);
        const previousAmount = parseInt(previousDenominationPart?.amount);

        console.log("hilfe")
        console.log(extraDenominationPartByCashType(denomination, cashType)?.amount)
        console.log(isNaN(extraDenominationPartByCashType(denomination, cashType)?.amount))
        //console.log(currentDenominationPart)

        if (isNaN(currentAmount) || isNaN(previousAmount)) {
            console.log("NAN")
            return;
        }
        if (currentAmount === 0 && previousAmount === 0) {
            console.log("Not 0")
            return;
        }


        console.log(`Difference: ${currentAmount} - ${previousAmount}`);
        const amount = currentAmount - previousAmount;
        difference.push(createDenominationParts(amount, cashType.displayName))
    });


    return difference;
}

const createDenomination = (value, currency, denominationParts) => {
    return {
        "value": value,
        "currency": currency,
        "denominationParts": denominationParts
    }
}

const createDenominationParts = (amount, cashType) => {
    return {
        "amount": amount,
        "cashType": cashType
    }
}

const extraDenominationPartByCashType = (denomination, cashType) => {
    return denomination?.denominationParts?.find((part) => part.cashType === cashType?.displayName);
}