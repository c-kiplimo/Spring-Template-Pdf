package com.meraki.template.entity

import lombok.Data


@Data
data class Customer(
    var companyName: String? = null,
    var contactName: String? = null,
    var address: String? = null,
    var email: String? = null,
    var phone: String? = null
)
