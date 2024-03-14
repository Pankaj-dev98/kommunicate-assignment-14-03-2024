#  Chat completion API

## API summary
- This API currently sends API requests to OpenAI's API endpoints through the RapidAPI service.
- This is done using the `java.net.http` package

### Request example.
- A valid POST request to the `/api/v1/complete_api` endpoint responds with an HTTP response containing a Json object with the API response.
![request-example](/src/main/resources/static/request-example.png)

- This request responds with an Http response which is parsed internally using Jackson and handled by SpringBoot's DispatcherServlet.
- A successful response has the following attributes:

![response-example](/src/main/resources/static/response-example.png)

- The API has been end-to-end tested thoroughly and handles null, empty prompts and prompts that exceed the maximum length of 128 using a global exception handler.
![invalid-prompt](/src/main/resources/static/invalid-input.png)

- Note: This public repository does not expose any sensitive API keys for either OpenAI or, RapidAPI.
The program expects an environment variable called `RAPID_API_KEY` available at runtime.