package apirequests;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.AbstractHttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import utils.URLManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetFileMetadata extends Request {

    private final HttpEntityEnclosingRequestBase request = new HttpPost(URLManager.URL_FILE_METADATA);
    private final List<String> actions;
    private final String id;

    public GetFileMetadata(List<String> actions, String id) {
        this.actions = actions;
        this.id = id;
    }

    @Override
    public HttpEntityEnclosingRequestBase getRequest() {
        request.setHeader("Content-Type", ContentType.APPLICATION_JSON.getMimeType());
        return request;
    }

    @Override
    public AbstractHttpEntity getRequestBody() {
        Map<String, Object> rqBody = new HashMap<>();
        rqBody.put("actions", actions);
        rqBody.put("file","id:" + id);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonBody = gson.toJson(rqBody);
        return new StringEntity(jsonBody, ContentType.APPLICATION_JSON);
    }

}
