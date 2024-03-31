// Function sum, which takes an optional parameter n of type number
function sum(n?: number) {
    // Initialize total with the provided number n, or 0 if not provided
    let total = n || 0;

    // Define a nested function addNext, which takes an optional parameter num of type number
    function addNext(num?: number) {
        // If num is not provided, return the current total sum
        if (num === undefined) {
            return total;
        }
        // If num is provided, add it to the total sum
        total += num;
        // Return the addNext function itself to allow chaining
        return addNext;
    }

    // Return the addNext function to allow initial chaining
    return addNext;
}

// Example usage:
const result = sum(1)(34)(5)(108)();
console.log(result); // Output: 148
