package com.boardmandillon.shoppingcart.offers

import com.boardmandillon.shoppingcart.model.Item
import java.math.BigDecimal
import java.math.RoundingMode

class TwoForOneOffer(private val applicableItemName: String) : Offer {

    override fun apply(cartItems: Map<Item, Int>): BigDecimal {
        val cartItem = cartItems.entries.find { it.key.name == applicableItemName }
        return if (cartItem != null) {
            val freeItems = cartItem.value / 2
            val discount = cartItem.key.price.multiply(BigDecimal(freeItems))
            discount.setScale(2, RoundingMode.HALF_UP)
        } else {
            BigDecimal.ZERO
        }
    }
}
