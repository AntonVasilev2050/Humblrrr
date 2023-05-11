package com.avv2050soft.humblrrr.data.auth

import android.content.Intent
import android.util.Log
import androidx.core.net.toUri
import com.avv2050soft.humblrrr.data.auth.models.TokensModel
import net.openid.appauth.AuthorizationRequest
import net.openid.appauth.AuthorizationService
import net.openid.appauth.AuthorizationServiceConfiguration
import net.openid.appauth.ClientAuthentication
import net.openid.appauth.ClientSecretBasic
import net.openid.appauth.EndSessionRequest
import net.openid.appauth.GrantTypeValues
import net.openid.appauth.ResponseTypeValues
import net.openid.appauth.TokenRequest
import kotlin.coroutines.suspendCoroutine

object AppAuth {

    private val serviceConfiguration = AuthorizationServiceConfiguration(
        AuthConfig.AUTH_URI.toUri(),
        AuthConfig.TOKEN_URI.toUri(),
        null, // registration endpoint
        AuthConfig.LOGOUT_URL.toUri()
    )

    fun getAuthRequest(): AuthorizationRequest {

        return AuthorizationRequest.Builder(
            serviceConfiguration,
            AuthConfig.CLIENT_ID,
            AuthConfig.RESPONSE_TYPE,
            AuthConfig.REDIRECT_URL.toUri()
        )
            .setScope(AuthConfig.SCOPE)
            .setAdditionalParameters(mapOf("duration" to "permanent"))
            .setState(AuthConfig.STATE)
            .build()
    }

    fun prepareTokenRequest(intent: Intent): TokenRequest {
        val code = intent.dataString?.substringAfter("code=")?.substringBefore('#')
        Log.d("AAA code", code.toString())
        return TokenRequest.Builder(serviceConfiguration, AuthConfig.CLIENT_ID)
            .setGrantType(AuthConfig.GRANT_TYPE_CODE)
            .setAuthorizationCode(code)
            .setRedirectUri(AuthConfig.REDIRECT_URL.toUri())
            .build()
    }

    fun getEndSessionRequest(): EndSessionRequest {
        return EndSessionRequest.Builder(serviceConfiguration)
            .setPostLogoutRedirectUri(AuthConfig.LOGOUT_URL.toUri())
            .build()
    }

    fun getRefreshTokenRequest(refreshToken: String): TokenRequest {
        return TokenRequest.Builder(
            serviceConfiguration,
            AuthConfig.CLIENT_ID
        )
            .setGrantType(GrantTypeValues.REFRESH_TOKEN)
            .setScopes(AuthConfig.SCOPE)
            .setRefreshToken(refreshToken)
            .build()
    }

    suspend fun performTokenRequestSuspend(
        authService: AuthorizationService,
        tokenRequest: TokenRequest,
    ): TokensModel {
        return suspendCoroutine { continuation ->
            authService.performTokenRequest(
                tokenRequest,
                getClientAuthentication()
            ) { response, ex ->
                when {
                    response != null -> {
                        //получение токена произошло успешно
                        val tokens = TokensModel(
                            accessToken = response.accessToken.orEmpty(),
                            refreshToken = response.refreshToken.orEmpty(),
                            idToken = response.idToken.orEmpty()
                        )
                        continuation.resumeWith(Result.success(tokens))
                    }
                    //получение токенов произошло неуспешно, показываем ошибку
                    ex != null -> {
                        continuation.resumeWith(Result.failure(ex))
                    }

                    else -> error("unreachable")
                }
            }
        }
    }

    private fun getClientAuthentication(): ClientAuthentication {
        return ClientSecretBasic(AuthConfig.CLIENT_SECRET)
    }

    private object AuthConfig {
        const val AUTH_URI = "https://www.reddit.com/api/v1/authorize.compact"
        const val TOKEN_URI = "https://www.reddit.com/api/v1/access_token"
        const val RESPONSE_TYPE = ResponseTypeValues.CODE
        const val SCOPE =
            "identity edit flair history modconfig modflair modlog modposts modwiki mysubreddits" +
                    " privatemessages read report save submit subscribe vote wikiedit wikiread"

        const val CLIENT_ID = "7nZvEKPtied50seYDxO5QA"
        const val CLIENT_SECRET = ""
        const val REDIRECT_URL = "com.avv2050soft.humblrrr://reddit"
        const val LOGOUT_URL = "https://www.reddit.com/api/v1/revoke_token"
        const val STATE = "com.avv2050soft.humblrrr:state"
        const val GRANT_TYPE_CODE = "authorization_code"
    }
}