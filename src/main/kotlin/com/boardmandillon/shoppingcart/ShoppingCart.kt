package com.boardmandillon.shoppingcart

import com.boardmandillon.shoppingcart.model.Item
import com.boardmandillon.shoppingcart.offers.Offer
import java.math.BigDecimal
import java.math.RoundingMode

class ShoppingCart {
    val items = mutableMapOf<Item, Int>()
    val offers = mutableListOf<Offer>()

    fun addItem(item: Item, quantity: Int = 1) {
        require(quantity > 0) { "quantity must be positive" }
        items[item] = items.getOrDefault(item, 0) + quantity
    }

    fun removeItem(item: Item, quantity: Int = 1) {
        require(quantity > 0) { "quantity must be positive" }
        items[item]?.let {
            if (it <= quantity) items.remove(item)
            else items[item] = it - quantity
        }
    }

    fun applyOffer(offer: Offer) {
        offers.add(offer)
    }

    fun generateReceipt(): String {
        val itemLines = items.entries.joinToString("\n") { (item, quantity) ->
            "${item.name} x$quantity - £${
                item.price.multiply(BigDecimal(quantity)).setScale(2, RoundingMode.HALF_UP)
            }"
        }
        val discount = offers.fold(BigDecimal.ZERO) { acc, offer -> acc + offer.apply(items) }
        val subtotal = items.entries.sumOf { (item, quantity) -> item.price.multiply(BigDecimal(quantity)) }
        val total = subtotal - discount

        return """
                |RECEIPT:
                |$itemLines
                |Subtotal: £${subtotal.setScale(2, RoundingMode.HALF_UP)}
                |Discount: -£${discount.setScale(2, RoundingMode.HALF_UP)}
                |Total: £${total.setScale(2, RoundingMode.HALF_UP)}
                """.trimMargin()
    }

    fun calculateTotal(): BigDecimal {
        val subtotal = items.entries.sumOf { (item, quantity) -> item.price.multiply(BigDecimal(quantity)) }
        val discount = offers.fold(BigDecimal.ZERO) { acc, offer -> acc + offer.apply(items) }

        return subtotal - discount
    }
}

