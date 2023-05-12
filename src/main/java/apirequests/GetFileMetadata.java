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
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public GetFileMetadata(List<String> actions, String id) {
        request.setHeader("Content-Type", ContentType.APPLICATION_JSON.getMimeType());
        Map<String, Object> rqBody = Map.of(
                "actions", actions,
                "file","id:" + id
        );
        request.setEntity(new StringEntity(gson.toJson(rqBody), ContentType.APPLICATION_JSON));
    }

    @Override
    public HttpEntityEnclosingRequestBase getRequest() {
        return request;
    }

}
