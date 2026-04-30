package com.example.ktalogapp.data.remote.interceptors

import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody

class MockInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val uri = chain.request().url.toUri().toString()
        
        // Simulamos un delay de red para testear el Loading state
        Thread.sleep(800) 

        val jsonResponse = when {
            uri.endsWith("/products") -> """
                [
                    {"id": "1", "name": "Jarilla en Hebras", "description": "Ideal para reumatismo y limpieza energética.", "price": 1200.0, "image_url": "https://picsum.photos/200", "hasStock": true, "category": "Hierbas"},
                    {"id": "2", "name": "Aceite de Caléndula", "description": "Regenerador celular para pieles sensibles.", "price": 2500.0, "image_url": "https://picsum.photos/201", "hasStock": true, "category": "Cosmética"},
                    {"id": "3", "name": "Tintura de Cardo Mariano", "description": "Protector hepático natural.", "price": 1800.0, "image_url": "https://picsum.photos/202", "hasStock": false, "category": "Tinturas"}
                ]
            """.trimIndent()
            else -> ""
        }

        return Response.Builder()
            .code(200)
            .message("OK")
            .request(chain.request())
            .protocol(Protocol.HTTP_1_1)
            .body(ResponseBody.create("application/json".toMediaTypeOrNull(), jsonResponse))
            .addHeader("content-type", "application/json")
            .build()
    }
}