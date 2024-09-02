package com.meraki.template.entity

import lombok.Data

@Data
class QuoteItem {
    var description: String? = null
    var quantity: Int? = null
    var unitPrice: Double? = null
    var total: Double? = null
}