package com.perqin.angumi.data.auth

enum class OAuthState {
    NONE,
    INITIATED,
    HANDLING_CALLBACK,
    SUCCESSFUL,
    FAILED,
}
