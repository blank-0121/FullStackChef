package data_access;

import entity.CommonRecipe;
import entity.Recipe;
import entity.User;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import use_case.display_history.DisplayHistoryDataAccessInterface;
import use_case.history.HistoryDataAccessInterface;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HistoryDataAccessObject implements HistoryDataAccessInterface, DisplayHistoryDataAccessInterface {
    private static final int SUCCESS_CODE = 200;
    private static final int CREDENTIAL_ERROR = 401;
    private static final String CONTENT_TYPE_LABEL = "Content-Type";
    private static final String CONTENT_TYPE_JSON = "application/json";
    private static final String STATUS_CODE_LABEL = "status_code";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String MESSAGE = "message";

    private static final String API_KEY = Constants.API_KEY;
    private static final String BASE_SEARCH_URL = "https://api.spoonacular.com/recipes/informationBulk?";

    private static final String RESULTS = "results";
    private static final String ID = "id";
    private static final String TITLE = "title";
    private static final String IMAGE = "image";
    private static final String IMAGE_TYPE = "imageType";
    private static final List<Recipe> RECIPES = new ArrayList<>();

    private final OkHttpClient client;

    /**
     * Constructs a new HistoryDataAccessObject with a default OkHttpClient.
     */
    public HistoryDataAccessObject() {
        this.client = new OkHttpClient();
    }

    @Override
    public List<Integer> getHistory(User user) throws HistoryException {
        final String username = user.getName() + "---history";

        final Request request = new Request.Builder()
                .url(String.format("http://vm003.teach.cs.toronto.edu:20112/user?username=%s", username))
                .addHeader(CONTENT_TYPE_LABEL, CONTENT_TYPE_JSON)
                .build();
        try {
            final Response response = client.newCall(request).execute();
            final JSONObject responseBody = new JSONObject(response.body().string());

            if (responseBody.getInt(STATUS_CODE_LABEL) == SUCCESS_CODE) {
                final JSONObject userJSONObject = responseBody.getJSONObject("user");
                final JSONObject info = userJSONObject.getJSONObject("info");

                final List<Integer> history = new ArrayList<>();

                // Check if "history" field exists
                if (info.has("history")) {
                    JSONArray historyArray = info.getJSONArray("history");
                    for (int i = 0; i < historyArray.length(); i++) {
                        history.add(historyArray.getInt(i));
                    }
                }
                return history;
            } else if (responseBody.getInt(STATUS_CODE_LABEL) == CREDENTIAL_ERROR) {
                throw new HistoryException("Invalid credentials provided.");
            } else {
                throw new HistoryException("Error retrieving favorites: " + responseBody.getString(MESSAGE));
            }
        } catch (IOException | JSONException ex) {
            throw new HistoryException("Error during API call: " + ex.getMessage());
        }
    }

    @Override
    public void saveHistory(User user, List<Integer> history) throws HistoryException {
        final OkHttpClient client = new OkHttpClient().newBuilder().build();

        final MediaType mediaType = MediaType.parse(CONTENT_TYPE_JSON);
        final JSONObject requestBody = new JSONObject();
        requestBody.put(USERNAME, user.getName()+"---history");
        requestBody.put(PASSWORD, user.getPassword());

        // Convert history list to JSON array
        final JSONObject extra = new JSONObject();
        extra.put("history", history);
        requestBody.put("info", extra);

        final RequestBody body = RequestBody.create(requestBody.toString(), mediaType);
        final Request request = new Request.Builder()
                .url("http://vm003.teach.cs.toronto.edu:20112/modifyUserInfo")
                .method("PUT", body)
                .addHeader(CONTENT_TYPE_LABEL, CONTENT_TYPE_JSON)
                .build();
        try {
            final Response response = client.newCall(request).execute();
            final JSONObject responseBody = new JSONObject(response.body().string());

            if (responseBody.getInt(STATUS_CODE_LABEL) == SUCCESS_CODE) {
                getHistory(user);
            } else if (responseBody.getInt(STATUS_CODE_LABEL) == CREDENTIAL_ERROR) {
                throw new HistoryException("Invalid credentials provided.");
            } else {
                throw new HistoryException("Error saving favorites: " + responseBody.getString(MESSAGE));
            }
        } catch (IOException | JSONException ex) {
            throw new HistoryException("Error during API call: " + ex.getMessage());
        }
    }

    @Override
    public List<Recipe> getRecipes(String recipeId) throws IOException, JSONException {

        final String url = String.format("%sids=%s&apiKey=%s",BASE_SEARCH_URL, recipeId, API_KEY);

        System.out.println("URL: " + url);

        final Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                String responseString = response.body().string();
                final JSONArray results = new JSONArray(responseString);

                System.out.println(results.length());

                // Clear previous search results
                RECIPES.clear();

                // Loop through each recipe and add it to the list
                for (int i = 0; i < results.length(); i++) {
                    final JSONObject recipeJson = results.getJSONObject(i);
                    final int recipeID = recipeJson.getInt(ID);
                    System.out.println(recipeID);
                    final String title = recipeJson.getString(TITLE);
                    final String image = recipeJson.getString(IMAGE);
                    final String imageType = recipeJson.optString(IMAGE_TYPE, "jpg");

                    // Fetch detailed information for each recipe
                    final Recipe recipe = new CommonRecipe(recipeID, title, image, imageType, null, null, null, false);
                    // Add to the list
                    System.out.println(recipe);
                    RECIPES.add(recipe);
                }
                return RECIPES;
            } else if (response.body() != null) {
                final JSONObject errorResponse = new JSONObject(response.body().string());
                final String errorMessage = errorResponse.optString("message", "Unknown error occurred.");
                throw new IOException("API request failed: " + errorMessage);
            } else {
                throw new IOException("API request failed with status code: " + response.code());
            }
        } catch (IOException | JSONException ex) {
            throw new IOException("Error during API request: " + ex.getMessage(), ex);
        }
    }
}