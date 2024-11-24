package com.boardmandillon.shoppingcart.offers

import com.boardmandillon.shoppingcart.model.Item

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

import java.math.BigDecimal

class TwoForOneOfferTest {

    private val cornflakes = Item("Cornflakes", BigDecimal("3.50"))
    private val offer = TwoForOneOffer("Cornflakes")

    @Test
    fun `apply 2-for-1 offer with even quantity`() {
        val cartItems = mapOf(cornflakes to 4)
        val discount = offer.apply(cartItems)
        assertEquals(BigDecimal("7.00"), discount, "expected discount for 2-for-1 on 4 items")
    }

    @Test
    fun `apply 2-for-1 offer with odd quantity`() {
        val cartItems = mapOf(cornflakes to 3)
        val discount = offer.apply(cartItems)
        assertEquals(BigDecimal("3.50"), discount, "expected discount for 2-for-1 on 3 items")
    }

    @Test
    fun `apply 2-for-1 offer with item not in cart`() {
        val cartItems = emptyMap<Item, Int>()
        val discount = offer.apply(cartItems)
        assertEquals(BigDecimal.ZERO, discount, "expected no discount for item not in cart")
    }
}
