# BAJAJ FIMSERV (QUALIFIER ROUND 1)

A Spring Boot app that:
- Registers a webhook on startup
- Solves a SQL problem (manually)
- Submits the SQL query using a secure token

## 🌐 APIs Used

- `POST /generateWebhook/JAVA` — to get webhook and JWT
- `POST /testWebhook/JAVA` — to submit your final query

## ⚙️ Configuration

Update `src/main/resources/application.yml` with your details:

```yaml
webhook:
  name: John Doe
  regNo: REG12347
  email: john@example.com
  finalQuery: SELECT name FROM students WHERE marks > 75
```
