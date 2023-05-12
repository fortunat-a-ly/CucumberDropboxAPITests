package apirequests;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.AbstractHttpEntity;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import utils.URLManager;

import java.util.HashMap;
import java.util.Map;

public class UploadRequest extends Request {

    private final HttpEntityEnclosingRequestBase request = new HttpPost(URLManager.URL_UPLOAD);
    private final String path;
    private final byte[] binaryData;

    public UploadRequest(String path, byte[] binaryData) {
        this.path = path;
        this.binaryData = binaryData;
    }

    public AbstractHttpEntity getRequestBody() {
        return new ByteArrayEntity(binaryData);
    }

    public HttpEntityEnclosingRequestBase getRequest() {
        request.setHeader("Content-Type", ContentType.APPLICATION_OCTET_STREAM.getMimeType());
        request.setHeader("Dropbox-API-Arg", getDropboxApiArg());
        return request;
    }

    private String getDropboxApiArg() {
        Map<String, Object> body = new HashMap<>();
        body.put("autorename", false);
        body.put("mode", "add");
        body.put("mute", false);
        body.put("path", path);
        body.put("strict_conflict", false);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(body);
    }
}
