    package services;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.json.JSONObject;

public class Slack {

    private static final HttpClient client = HttpClient.newHttpClient();
    private static final String URL = "https://hooks.slack.com/services/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";
    private static final String token = "xoxb-2803705969300-2801443846226-vr4R8vfpz0OsigR5bn6fuwUT";
    private static final String tokenBot = "xoxe.xoxp-1-Mi0yLTI4MDM3MDU5NjkzMDAtMjgwMTQzODAxMDI1OC0yODI1NzA2MTY1ODg5LTI4MTAxMzg5OTY3MDktNjU2MDU5MmRhOGEwYmFkMDgxYjU5YWViNjJhOWJmZjk0OGExZTM3YThhYzlkMjZjODBmOThmNDdmYTBlYjlhNg";

    public static void sendMessage(JSONObject content) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder(
                URI.create(URL))
                .header("accept", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(content.toString()))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println(String.format("Status: %s", response.statusCode()));
        System.out.println(String.format("Response: %s", response.body()));
    }
}
