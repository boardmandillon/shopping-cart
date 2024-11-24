**MONETARY PRECISION:**
Prices use BigDecimal with two decimal places, rounded half-up for accurate financial calculations.

**INDEPENDENT OFFERS:**
Each Offer applies its discount separately without stacking or dependencies on other offers.

**UNIQUE ITEM IDENTIFICATION:**
Items are identified by their name, which allows TwoForOneOffer to locate applicable items by name.

**POSITIVE QUANTITIES ONLY:**
Adding or removing items requires positive integers to maintain cart consistency.

**RECEIPT FORMAT:**
The receipt is text-based, showing item quantities, subtotal, discount, and total, and can be customised if needed.

**COMPREHENSIVE TESTING:**
Unit tests cover typical shopping scenarios and edge cases to ensure consistent behaviour.

**EASY OFFER EXPANSION:**
New discounts can be added by implementing additional Offer classes, supporting future flexibility.
