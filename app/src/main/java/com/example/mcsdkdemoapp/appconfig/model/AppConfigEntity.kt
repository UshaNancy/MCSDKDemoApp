package com.kobil.mcwmpgettingstarted.appconfig.model


import com.google.gson.annotations.SerializedName
import com.kobil.mcwmpgettingstarted.extensions.isNotNullOrEmpty
import com.kobil.mcwmpgettingstarted.extensions.orElse

data class AppConfigEntity(
    @SerializedName("anonymousActivationChat")
    val anonymousActivationChat: AnonymousActivationChat,
    @SerializedName("anonymousActivationWebviewUrl")
    val anonymousActivationWebviewUrl: String, // https://www.kobil.com/contact-us/
    @SerializedName("contactUrl")
    val contactUrl: String, // https://www.kobil.com/contact-us/
    @SerializedName("defaultAnonymousWebview")
    private val defaultAnonymousWebview: DefaultWebviewProperties,
    @SerializedName("defaultLocalization")
    val defaultLocalization: String, // en-US
    @SerializedName("defaultPersonalizedWebview")
    private val defaultPersonalizedWebview: DefaultWebviewProperties,
    @SerializedName("helpUrl")
    val helpUrl: String, // https://www.kobil.com/
    @SerializedName("legalUrl")
    val legalUrl: String, // https://www.kobil.com/imprint/
    @SerializedName("licencesUrl")
    val licencesUrl: String, // https://www.kobil.com/terms-of-use/
    @SerializedName("logFileReceiverMailAddress")
    val logFileReceiverMailAddress: LogFileReceiverMailAddress,
    @SerializedName("preloginInformation")
    val preloginInformation: PreloginInformation? = null,
    @SerializedName("userActivationLoginType")
    var userActivationLoginType: String, // pinBased | tokenBased
    @SerializedName("identityAccessManagementInformation")
    val identityAccessManagementInformation: IdentityAccessManagementInformation? = null,
    @SerializedName("tenants")
    var tenants: List<Tenant> = mutableListOf(),
    @SerializedName("userCredentialsPath")
    val userCredentialsPath: UserCredentialsPath,
    @SerializedName("enableMultiTenants")
    var isMultipleTenantsEnabled: Boolean,
    @SerializedName("defaultEcoId")
    val defaultEcoId: String,
    @SerializedName("openCensus")
    val openCensus: OpenCensus,
    @SerializedName("identityProvider")
    val identityProvider: List<IdentityProvider> = mutableListOf(),
    @SerializedName("webPortalUrl")
    val webPortalUrl: String
) {
    val defaultWebViewProperties: DefaultWebviewProperties
        get() {
            return defaultAnonymousWebview
        }

    val isTokenBasedActivation: Boolean
        get() {

            return userActivationLoginType.isNotNullOrEmpty() && userActivationLoginType == "tokenBased"
        }

    fun firstTenant(): Tenant? = tenants.firstOrNull()

    fun getDefaultTenant(): Tenant? {
        if (tenants.isNotEmpty()) {
            return tenants.singleOrNull { it.tenantId == "default" }
        }
        return null
    }

    fun isAnonymousActivationEnabled(): Boolean {
        @Suppress("SENSELESS_COMPARISON", "UselessCallOnNotNull") // It should be null
        return anonymousActivationChat != null || !anonymousActivationWebviewUrl.isNullOrEmpty()
    }

    fun isPasswordRecoveryEnabled(): Boolean {
        return getDefaultTenant()?.let {
            @Suppress("UselessCallOnNotNull")  // It should be null
            return@let (it.passwordRecoveryWebviewUrl.isNullOrEmpty() || it.passwordRecoveryChat != null)
        }.orElse {
            return@orElse false
        }
    }

    data class UserCredentialsPath(
        @SerializedName("macOS")
        val macOS: String, // %HOME%/Library/Application Support/KOBIL/%ECO_ID%/%APP_NAME%
        @SerializedName("windows")
        val windows: String // %LOCALAPPDATA%/KOBIL/%ECO_ID%/%APP_NAME%
    )

    data class DefaultWebviewProperties(
        @SerializedName("clientAuthentication")
        val clientAuthentication: Any?, // null
        @SerializedName("enableHistoryBack")
        val enableHistoryBack: Boolean, // true
        @SerializedName("externalUrls")
        var externalUrls: List<String> = mutableListOf(),
        @SerializedName("internalUrls")
        var internalUrls: List<String> = mutableListOf(),
        @SerializedName("serverCertificateTrustStore")
        var serverCertificateTrustStore: List<String> = mutableListOf()// null
    )


    data class Tenant(
        @SerializedName("passwordRecoveryChat")
        val passwordRecoveryChat: PasswordRecoveryChat? = null,
        @SerializedName("passwordRecoveryWebviewUrl")
        val passwordRecoveryWebviewUrl: String, // https://www.kobil.com/contact-us/
        @SerializedName("pinRules")
        var pinRules: List<PinRule> = mutableListOf(),
        @SerializedName("startScreenAfterLogin")
        val startScreenAfterLogin: String, // IntelligentStartScreen
        @SerializedName("tenantId")
        val tenantId: String, // default
        @SerializedName("webStartScreenSettings")
        val webStartScreenSettings: WebStartScreenSettings // webStartScreenSettings
    ) {

        data class WebStartScreenSettings(
            @SerializedName("type")
            val type: String, // online
            @SerializedName("url")
            val url: String // https://client.becomes.co/kobil/sdk/native-demo/
        )

        data class PasswordRecoveryChat(
            @SerializedName("serviceName")
            val serviceName: String, // passwordRecovery
            @SerializedName("serviceProvider")
            val serviceProvider: ServiceProvider
        ) {
            data class ServiceProvider(
                @SerializedName("ecoId")
                val ecoId: String, // kobil
                @SerializedName("userId")
                val userId: String // anonymousAccountServices
            )
        }


        data class PinRule(
            @SerializedName("errorMessageKey")
            val errorMessageKey: String, // ks_ast_001_setpin_pinnotdigitonly
            @SerializedName("negateRule")
            val negateRule: Boolean, // false
            @SerializedName("pinRuleRegularExpression")
            val pinRuleRegularExpression: String // ^[0-9]*$
        )
    }

    data class AnonymousActivationChat(
        @SerializedName("serviceName")
        val serviceName: String, // activation
        @SerializedName("serviceProvider")
        val serviceProvider: ServiceProvider
    ) {
        data class ServiceProvider(
            @SerializedName("ecoId")
            val ecoId: String, // kobil
            @SerializedName("userId")
            val userId: String // anonymousAccountServices
        )
    }

    data class PreloginInformation(
        @SerializedName("definitionUrl")
        val definitionUrl: String?, // https://www.cm3p.de/files/login-screen.json
        @SerializedName("serverCertificateTrustStore")
        var serverCertificateTrustStore: List<String> = mutableListOf(),
        @SerializedName("clientAuthentication")
        var clientAuthentication: ClientAuthentication? = null
    )

    data class IdentityAccessManagementInformation(
        @SerializedName("loginActivationWebpageUrl")
        var loginActivationWebpageUrl: String?, // this will be the login/activation page for weblogin sdk to show from webview
        @SerializedName("clientId")
        val clientId: String?, // placeholderClientId
        @SerializedName("scope")
        val scope: String?, // openId
        @SerializedName("getAuthorizationCodeUrl")
        val getAuthorizationCodeUrl: String?, //this is for app2app use case to get the authorizationcode
        @SerializedName("getTokenUrl")
        val getTokenUrl: String?, // this is for fetching the different token access and refresh token for weblogin
        @SerializedName("serverCertificateTrustStore")
        var serverCertificateTrustStore: List<String> = mutableListOf(),
        @SerializedName("clientAuthentication")
        var clientAuthentication: ClientAuthentication? = null
    )

    data class ClientAuthentication(
        @SerializedName("mode")
        val mode: String?,
        @SerializedName("username")
        val username: String?,
        @SerializedName("password")
        val password: String?
    )

    data class LogFileReceiverMailAddress(
        @SerializedName("android")
        var android: List<String> = mutableListOf(),
        @SerializedName("iOS")
        var iOS: List<String> = mutableListOf(),
        @SerializedName("macOS")
        var macOS: List<String> = mutableListOf(),
        @SerializedName("windows")
        var windows: List<String> = mutableListOf()
    )

    data class OpenCensus(
        @SerializedName("exporterUrl")
        var exporterUrl: String?,
        @SerializedName("serverCertificateTrustStore")
        var serverCertificateTrustStore: List<String> = mutableListOf(),
        @SerializedName("clientAuthentication")
        var clientAuthentication: ClientAuthentication? = null
    )

    data class IdentityProvider(
        @SerializedName("path")
        var path: String?,
        @SerializedName("cookieDomain")
        var cookieDomain: String?,
        @SerializedName("audience")
        val audience: String?
    )
}