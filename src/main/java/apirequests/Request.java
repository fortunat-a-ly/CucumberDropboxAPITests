package apirequests;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.entity.AbstractHttpEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import utils.URLManager;

import java.io.IOException;

public abstract class Request {

    public JsonObject send() throws IOException, IllegalStateException {
        try(CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpEntityEnclosingRequestBase request = getRequest();
            request.setHeader("Authorization", "Bearer " + URLManager.TOKEN);
            System.out.println(request);
            HttpResponse response = httpClient.execute(request);
            System.out.println(response);
            HttpEntity responseEntity = response.getEntity();
            String responseBody = EntityUtils.toString(responseEntity);
            System.out.println(responseBody);
            JsonElement jsonElement = JsonParser.parseString(responseBody);
            if (jsonElement.isJsonObject())
                return jsonElement.getAsJsonObject();
            throw new IllegalStateException("No Json Object found in the response");
        }
    }
    public abstract HttpEntityEnclosingRequestBase getRequest();
}
