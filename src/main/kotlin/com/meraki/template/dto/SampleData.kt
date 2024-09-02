package com.meraki.template.dto

import com.meraki.template.entity.Customer
import com.meraki.template.entity.QuoteItem

class SampleData {
    fun createSampleCustomer(): Customer {
        return Customer().apply {
            companyName = "Tech Innovations Inc."
            contactName = "John Doe"
            address = "123 Innovation Drive, Suite 456"
            email = "john.doe@techinnovations.com"
            phone = "+1-800-123-4567"
        }
    }

    fun createSampleQuoteItems(): List<QuoteItem> {
        return listOf(
            QuoteItem().apply {
                description = "High-Performance Laptop"
                quantity = 2
                unitPrice = 1200.00
                total = (quantity ?: 0) * (unitPrice ?: 0.0)
            },
            QuoteItem().apply {
                description = "Wireless Mouse"
                quantity = 5
                unitPrice = 25.00
                total = (quantity ?: 0) * (unitPrice ?: 0.0)
            },
            QuoteItem().apply {
                description = "External SSD"
                quantity = 1
                unitPrice = 150.00
                total = (quantity ?: 0) * (unitPrice ?: 0.0)
            }
        )
    }
}
