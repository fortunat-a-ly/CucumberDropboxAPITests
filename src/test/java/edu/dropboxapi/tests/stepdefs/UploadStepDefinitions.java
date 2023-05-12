package edu.dropboxapi.tests.stepdefs;

import apirequests.Request;
import apirequests.UploadRequest;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.io.IOException;

public class UploadStepDefinitions {
    private JsonObject response;
    private String filePath;
    private final byte[] binaryFile = "Hello, Dropbox".getBytes();

    @Given("I have a file smaller than {int} MB")
    public void i_have_a_file_smaller_than_mb(int size) {
        assert binaryFile != null && binaryFile.length / (1024.0 * 1024.0) < size;
    }
    @When("I upload it under the path {string}")
    public void i_upload_it_under_the_path(String path) throws IOException {
        response = new UploadRequest(path, binaryFile).send();
        filePath = path;
    }
    @Then("I should get a file id in the response")
    public void i_should_get_a_file_id_in_the_response() {
        JsonElement id = response.get("id");
        assert id != null && !id.getAsString().isBlank();
    }
    @Then("I should get a file part of the path as the name in the response")
    public void i_should_get_a_file_part_of_the_path_as_the_name_in_the_response() {
        String[] pathParts = filePath.split("/");
        JsonElement name = response.get("name");
        assert name != null && pathParts[pathParts.length - 1].equals(name.getAsString());
    }

}
