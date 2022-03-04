package com.example.mcsdkdemoapp

import com.google.gson.annotations.SerializedName

data class ScpConfigEntity(
    @SerializedName("ecoId")
    val ecoId: String,
    @SerializedName("ecoTrustedSSLServerCerts")
    val ecoTrustedSSLServerCerts: String,
    @SerializedName("ecoTokenIssuerServiceUrl")
    val ecoTokenIssuerServiceUrl: String,
    @SerializedName("ecoMessageRouterUrl")
    val ecoMessageRouterUrl: String,
    @SerializedName("ecoAddressBookServiceUrl")
    val ecoAddressBookServiceUrl: String,
    @SerializedName("ecoPresenceServiceUrl")
    val ecoPresenceServiceUrl: String
)