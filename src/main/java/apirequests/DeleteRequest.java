package apirequests;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.AbstractHttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import utils.URLManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DeleteRequest extends Request {

    private final HttpEntityEnclosingRequestBase request = new HttpPost(URLManager.URL_DELETE);

    public DeleteRequest(List<String> idsToDelete) {
        request.setHeader("Content-Type", ContentType.APPLICATION_JSON.getMimeType());
        Map<String, Object> rqBody = new HashMap<>();
        rqBody.put("ids", idsToDelete);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonBody = gson.toJson(rqBody);
        request.setEntity(new StringEntity(jsonBody, ContentType.APPLICATION_JSON));
    }

    @Override
    public HttpEntityEnclosingRequestBase getRequest() {
        return request;
    }

}
