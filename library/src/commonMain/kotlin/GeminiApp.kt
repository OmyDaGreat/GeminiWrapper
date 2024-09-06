import io.ktor.client.*
import io.ktor.client.call.body
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json

expect object Platform {
  val name: String
}

class GeminiApi(private val apiKey: String, private val modelId: String = "gemini-pro") {

  private val baseUrl = "https://generativelanguage.googleapis.com/v1beta/models"

  @OptIn(ExperimentalSerializationApi::class)
  private val client =
    HttpClient(CIO) {
      install(ContentNegotiation) {
        json(
          Json {
            isLenient = true
            ignoreUnknownKeys = true
            explicitNulls = false
          }
        )
      }
    }

  suspend fun generateContent(prompt: String): GenerateContentResponse {
    val part = Part(text = prompt)
    val contents = Content(listOf(part))
    val request = GenerateContentRequest(contents)

    return client
      .post("$baseUrl/$modelId:generateContent") {
        contentType(ContentType.Application.Json)
        url { parameters.append("key", apiKey) }
        setBody(request)
      }
      .body<GenerateContentResponse>()
  }
}
