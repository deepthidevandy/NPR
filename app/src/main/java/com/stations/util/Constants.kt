package com.stations.util

import com.stations.data.ResponseAccessToken

object Constants {

    const val INFO_PATH = "chain/get_info"

    const val EACH_BLOCK_PATH = "chain/get_block"


    const val USER_AUTH = "v2/token"
    const val CHANNELS = "/v3/stations"

    const val AUTHORIZATION_API_BASE_URL = "authorization.api.npr.org"
    const val AUTHORIZE_REQUEST = "v2/authorize"
    const val PARAM_CLIENT_ID = "client_id"
    const val PARAM_REDIRECT_URI = "redirect_uri"
    const val PARAM_RESPONSE_TYPE = "response_type"
    const val PARAM_SCOPE = "scope"
    const val PARAM_STATE = "state"
    const val RESPONSE_TYPE_CODE = "code"
    const val REDIRECT_URI = "https://www.deepthi.com/"
    const val SCOPE_IDENTITY_WRITE = "identity.write"
    const val SCOPE_IDENTITY_READ_ONLY = "identity.readonly"
    const val SCOPE_LISTENING_READ_ONLY = "listening.readonly"
    const val SCOPE_LISTENING_WRITE = "listening.write"
    const val SCOPE_LOCALACTIVATION = "localactivation"
    const val authorization_code = "authorization_code"
    
    var responseAccessToken: ResponseAccessToken? = null

}