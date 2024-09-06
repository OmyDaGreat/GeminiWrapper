import kotlinx.serialization.Serializable

@Serializable data class Part(val text: String)

@Serializable data class Content(val parts: List<Part>)

@Serializable data class Candidate(val content: Content)

@Serializable data class Error(val message: String)

@Serializable data class GenerateContentResponse(val error: Error? = null, val candidates: List<Candidate>? = null)

@Serializable data class GenerateContentRequest(val contents: Content)
