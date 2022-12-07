package com.otaman.users_list.networking

import io.github.nefilim.kjwt.JWT
import io.github.nefilim.kjwt.sign
import javax.inject.Inject

class JWTManager @Inject constructor() {
    companion object {
        private const val SECRET = "\$SECRET$"
    }

    fun getToken(): String {
        val encodedSecret = java.util.Base64.getEncoder().encodeToString(SECRET.toByteArray())

        val jwt = JWT.hs256 {
            claim("uid", "testuid")
            claim("identity", "testidentity")
        }

        val signedToken = jwt.sign(encodedSecret)

        val token = signedToken.map {
            it.rendered
        }

        return token.fold({"error"}, {it})
    }
}