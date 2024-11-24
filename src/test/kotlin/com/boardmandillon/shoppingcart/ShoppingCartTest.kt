package com.boardmandillon.shoppingcart

import com.boardmandillon.shoppingcart.model.Item
import com.boardmandillon.shoppingcart.offers.TwoForOneOffer

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import java.math.BigDecimal

class ShoppingCartTest {

    private lateinit var cart: ShoppingCart

    private val cornflakes = Item("Cornflakes", BigDecimal("3.50"))
    private val bread = Item("Bread", BigDecimal("2.00"))

    @BeforeEach
    fun setUp() {
        cart = ShoppingCart()
    }

    @Test
    fun `add single item to cart`() {
        cart.addItem(cornflakes)
        assertEquals(1, cart.items[cornflakes])
    }

    @Test
    fun `add multiple quantities of the same item to cart`() {
        cart.addItem(cornflakes, 3)
        assertEquals(3, cart.items[cornflakes])
    }

    @Test
    fun `add different items to cart`() {
        cart.addItem(cornflakes, 2)
        cart.addItem(bread, 1)
        assertEquals(2, cart.items[cornflakes])
        assertEquals(1, cart.items[bread])
    }

    @Test
    fun `remove item from cart`() {
        cart.addItem(cornflakes, 3)
        cart.removeItem(cornflakes, 1)
        assertEquals(2, cart.items[cornflakes])
    }

    @Test
    fun `remove item completely when quantity becomes zero`() {
        cart.addItem(cornflakes, 1)
        cart.removeItem(cornflakes, 1)
        assertFalse(cart.items.containsKey(cornflakes))
    }

    @Test
    fun `apply 2-for-1 offer to single item`() {
        cart.addItem(cornflakes, 2)
        cart.applyOffer(TwoForOneOffer("Cornflakes"))

        val total = cart.calculateTotal()
        assertEquals(BigDecimal("3.50"), total, "expecting 1 item free, so total should be 3.50")
    }

    @Test
    fun `apply 2-for-1 offer to multiple items`() {
        cart.addItem(cornflakes, 4)
        cart.applyOffer(TwoForOneOffer("Cornflakes"))

        val total = cart.calculateTotal()
        assertEquals(BigDecimal("7.00"), total, "2 items free, so total should be 7.00")
    }

    @Test
    fun `apply 2-for-1 offer with other items in cart`() {
        cart.addItem(cornflakes, 4)
        cart.addItem(bread, 1)
        cart.applyOffer(TwoForOneOffer("Cornflakes"))

        val total = cart.calculateTotal()
        assertEquals(BigDecimal("9.00"), total, "2 items of cornflakes free + 1 bread")
    }

    @Test
    fun `generate itemized receipt`() {
        cart.addItem(cornflakes, 2)
        cart.addItem(bread, 1)

        val expectedReceipt =
            """
            |RECEIPT:
            |Cornflakes x2 - £7.00
            |Bread x1 - £2.00
            |Subtotal: £9.00
            |Discount: -£0.00
            |Total: £9.00
            """.trimMargin()

        val actualReceipt = cart.generateReceipt()
        assertEquals(expectedReceipt, actualReceipt)
    }
}

