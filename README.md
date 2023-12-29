# Saucedemo Automation Tests

## Overview
This project includes automated test scripts for the Saucedemo application, with a primary focus on validating the shopping cart functionality, checkout process, and order completion.

## Setup
1. Clone the repository
2. Navigate to the project directory
3. Install dependencies
4. Execute Tests

## Test Cases
### 1. Addition of Product to Shopping Cart
- **Method**: `productAddedToShoppingCart_when_addToCart()`
- **Objective**: This test ensures that products are accurately added to the shopping cart.
- **Procedure**:
    1. Login
    2. Add 2 products to the shopping cart
    3. Navigate to the shopping cart
    4. Assert that the correct items have been added

### 2. Addition of User Details
- **Method**: `userDetailsAdded_when_checkoutWithValidInformation()`
- **Objective**: This test verifies that user details are correctly added during the checkout process.
- **Procedure**:
    1. Login
    2. Add 2 products to the shopping cart
    3. Navigate to the shopping cart
    4. Proceed to checkout
    5. Enter user information
    6. Navigate to the summary page
    7. Verify the details on the summary page

### 3. Order Completion
- **Method**: `orderCompleted_when_addProduct_and_checkout_withConfirm()`
- **Objective**: This test confirms that an order is successfully completed and items are removed from the shopping cart post-order.
- **Procedure**:
    1. Login
    2. Add 2 products to the shopping cart
    3. Navigate to the shopping cart
    4. Proceed to checkout
    5. Enter user information
    6. Navigate to the summary page
    7. Complete the order
    8. Verify that items have been removed from the shopping cart

## Technology Stack
- Selenium
