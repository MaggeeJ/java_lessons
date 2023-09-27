package ru.stqa.pft.rest;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import org.testng.SkipException;

import java.io.IOException;

public class TestBase {

    public void skipIfNotFixed(int issueId) throws IOException {
        if (isIssueOpen(issueId)) {
            throw new SkipException("Ignored because of issue " + issueId);
        }
    }

    private boolean isIssueOpen(int issueId) throws IOException {
        String json = getExecutor().execute(Request.Get("http://bugify.stqa.ru/api/issues/" + issueId + ".json?limit=100"))
                .returnContent().asString();
        JsonElement parsed = new JsonParser().parse(json);
        JsonObject parsedObject = parsed.getAsJsonObject();
        String status = parsedObject.get("issues").getAsJsonArray().get(0).getAsJsonObject().get("state_name").getAsString();
        return !status.equals("Closed");
    }

    public Executor getExecutor() {
        return Executor.newInstance().auth("65c38fae4d0f34a3daccb854fc345a2d", "");
    }
}

