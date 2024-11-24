package com.boardmandillon.shoppingcart.offers

import com.boardmandillon.shoppingcart.model.Item
import java.math.BigDecimal

interface Offer {
    fun apply(cartItems: Map<Item, Int>): BigDecimal
}
