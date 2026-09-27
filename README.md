# AI Description Microservice

An independent Spring Boot microservice that integrates Google's Gemini API to generate AI-written product descriptions. Built as a companion service to [`ecommerce-backend-api`](https://github.com/akhil900619/ecommerce-backend-api), which calls this service over REST rather than embedding the AI integration directly — a deliberate choice to build and understand a real, if small, microservices architecture: two independently deployable Spring Boot applications communicating over the network, with an explicit contract and explicit failure handling between them.

## Tech Stack

- **Java 17**
- **Spring Boot 4.x**
- **Spring WebFlux** (`WebClient`, used here as a blocking HTTP client to call the Gemini API)
- **Google Gemini API** (`gemini-3.5-flash-lite`)
- **Maven**

## What it does

Exposes a single endpoint, `POST /api/ai/generate-description`, which accepts a product's name, category, and existing details, builds a prompt, sends it to Google's Gemini API, and returns a generated 2–3 sentence product description as JSON.

This service holds no database and no business state of its own — its only job is turning a request into a well-formed prompt, calling an external LLM, and parsing the response back into a clean, minimal JSON shape. `ecommerce-backend-api` calls this endpoint when a product's description is generated, and handles the case where this service is unreachable by returning a `503` rather than saving a product with a missing description.

## Running Locally

### Prerequisites
- Java 17+
- Maven
- A free Gemini API key from [Google AI Studio](https://aistudio.google.com/apikey)

### Setup

1. Clone the repo:
   ```
   git clone https://github.com/akhil900619/ai-service.git
   ```

2. Set the required environment variable:
   ```
   GEMINI_API_KEY=your_gemini_api_key
   ```

3. Run the application:
   ```
   mvn spring-boot:run
   ```

The service runs on `http://localhost:8081` by default, distinct from `ecommerce-backend-api`'s port 8080, since they run as two separate processes.

### Example request

```
POST http://localhost:8081/api/ai/generate-description
Content-Type: application/json

{
    "productName": "Wireless Bluetooth Headphones",
    "category": "Electronics",
    "existingDetails": "Over-ear, noise cancelling, 30-hour battery life"
}
```

```json
{
    "description": "Immerse yourself in pure sound with these wireless over-ear headphones..."
}
```

## Design Notes

- **A defined DTO-based REST contract** (`DescriptionRequest`) between this service and its caller, rather than passing raw JSON maps around — the same discipline used for request/response shaping in `ecommerce-backend-api`.
- **`JsonNode`/`.path(...)` for parsing Gemini's response**, rather than mapping it to a full response class — Gemini's actual response includes several fields (safety ratings, finish reasons, token counts) this service doesn't need; reaching directly for the one value that matters avoids modeling a response shape that isn't used.
- **API key and model name both externalized** via environment variable and `application.properties` — never committed to source control, and the model name is a one-line config change rather than a code change if a newer/different Gemini model is needed later.
- **No database, no persistence** — this service is intentionally stateless and does one job well, which is also what makes it trivially horizontally scalable if it ever needed to be.

## Author

**Akhil Sharma**
[LinkedIn](https://linkedin.com/in/akhilsharma) · akhil900619@gmail.com
