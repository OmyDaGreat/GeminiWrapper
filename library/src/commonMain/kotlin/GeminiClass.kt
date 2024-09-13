import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

expect object Platform {
  val name: String
}

class Gemini(private val apiKey: String, private val modelId: String = "gemini-pro") {
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

  suspend fun generateContent(prompt: String) = generateContent(apiKey, prompt, modelId)

  val platform: String = Platform.name
}
