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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UploadRequest extends Request implements AutoCloseable {

    private static final List<UploadRequest> requests = new ArrayList<>();
    private final HttpEntityEnclosingRequestBase request = new HttpPost(URLManager.URL_UPLOAD);
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final Map<String, Object> dropboxApiArgs = new HashMap<>();

    private UploadRequest(String path, byte[] binaryData) {
        request.setHeader("Content-Type", ContentType.APPLICATION_OCTET_STREAM.getMimeType());
        dropboxApiArgs.put("autorename", false);
        dropboxApiArgs.put("mode", "add");
        dropboxApiArgs.put("mute", false);
        dropboxApiArgs.put("strict_conflict", false);
        changeVolatileContent(path, binaryData);
    }

    @Override
    public HttpEntityEnclosingRequestBase getRequest() {
        return request;
    }

    public static synchronized Request getRequest(String path, byte[] binaryData) {
        UploadRequest rq;
        if (requests.size() > 0) {
            rq = requests.get(requests.size() - 1);
            rq.changeVolatileContent(path, binaryData);
        } else {
            rq = new UploadRequest(path, binaryData);
            requests.add(rq);
        }
        return rq;
    }

    public synchronized void returnRequest() {
        requests.add(this);
    }

    private void changeVolatileContent(String path, byte[] binaryData) {
        dropboxApiArgs.put("path", path);
        request.setHeader("Dropbox-API-Arg", gson.toJson(dropboxApiArgs));
        request.setEntity(new ByteArrayEntity(binaryData));
    }

    @Override
    public void close() throws Exception {
        returnRequest();
    }
}
