import com.google.gson.Gson;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "SkierServlet", value = "/SkierServlet")
public class SkierServlet extends HttpServlet {
  private static final Gson gson = new Gson();
  private static final String CONTENT_TYPE_JSON = "application/json";
  private static final int STATUS_NOT_FOUND = HttpServletResponse.SC_NOT_FOUND;
  private static final int STATUS_BAD_REQUEST = HttpServletResponse.SC_BAD_REQUEST;
  private static final int STATUS_OK = HttpServletResponse.SC_OK;
  private static final int STATUS_CREATED = HttpServletResponse.SC_CREATED;

  // Servlet methods
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    processGetRequest(request, response);
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    processPostRequest(request, response);
  }

  // Request processing methods
  private void processGetRequest(HttpServletRequest request, HttpServletResponse response)
      throws IOException {
    response.setContentType(CONTENT_TYPE_JSON);
    String urlPath = request.getPathInfo();

    if (isPathInvalid(urlPath)) {
      sendErrorResponse(response, STATUS_NOT_FOUND, "Missing parameters");
      return;
    }

    String[] urlParts = urlPath.split("/");
    if (!isUrlValid(urlParts)) {
      sendErrorResponse(response, STATUS_NOT_FOUND, "Invalid URL");
    } else {
      sendSuccessResponse(response, "It works!", STATUS_OK);
    }
  }

  private void processPostRequest(HttpServletRequest request, HttpServletResponse response)
      throws IOException {
    String[] pathParams = request.getPathInfo().split("/");

    if (!isValidPathParams(pathParams)) {
      sendErrorResponse(response, STATUS_BAD_REQUEST, "Invalid path parameters");
      return;
    }

    try {
      handlePostLogic(pathParams, request, response);
    } catch (NumberFormatException e) {
      sendErrorResponse(response, STATUS_BAD_REQUEST, "Invalid parameter format");
    }
  }

  // Helper methods
  private boolean isPathInvalid(String urlPath) {
    return urlPath == null || urlPath.isEmpty();
  }

  private boolean isValidPathParams(String[] pathParams) {
    return pathParams.length >= 8;
  }

  private void handlePostLogic(String[] pathParams, HttpServletRequest request, HttpServletResponse response)
      throws IOException {
    // Assuming the path structure is correct
    int resortID = parseAndValidateInt(pathParams[1], "resortID", response);
    // Validation for seasonID, dayID, and skierID omitted for brevity

    // Add logic to process the POST request here

    sendSuccessResponse(response, "Lift ride details stored successfully.", STATUS_CREATED);
  }

  private boolean isUrlValid(String[] urlPath) {
    // Implement specific validation logic
    return true; // Simplified for demonstration
  }

  private int parseAndValidateInt(String value, String paramName, HttpServletResponse response) throws IOException {
    try {
      return Integer.parseInt(value.trim());
    } catch (NumberFormatException e) {
      sendErrorResponse(response, STATUS_BAD_REQUEST, paramName + " must be an integer");
      return -1; // Indicator of failure
    }
  }

  private void sendSuccessResponse(HttpServletResponse response, String message, int status) throws IOException {
    response.setStatus(status);
    response.setContentType(CONTENT_TYPE_JSON);
    response.getWriter().print(gson.toJson("{\"message\": \"" + message + "\"}"));
    response.getWriter().flush();
  }

  private void sendErrorResponse(HttpServletResponse response, int statusCode, String message) throws IOException {
    sendSuccessResponse(response, message, statusCode);
  }
}
