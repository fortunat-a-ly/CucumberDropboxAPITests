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

    private HttpEntityEnclosingRequestBase request = new HttpPost(URLManager.URL_DELETE);
    private List<String> ids;

    public DeleteRequest(List<String> idsToDelete) {
        this.ids = idsToDelete;
    }

    @Override
    public HttpEntityEnclosingRequestBase getRequest() {
        request.setEntity(getRequestBody());
        request.setHeader("Content-Type", ContentType.APPLICATION_JSON.getMimeType());
        return request;
    }

    @Override
    public AbstractHttpEntity getRequestBody() {
        Map<String, Object> rqBody = new HashMap<>();
        rqBody.put("ids", ids);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonBody = gson.toJson(rqBody);
        return new StringEntity(jsonBody, ContentType.APPLICATION_JSON);
    }
}
