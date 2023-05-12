package edu.dropboxapi.tests.stepdefs;

import apirequests.GetFileMetadata;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.io.IOException;
import java.util.List;

public class GetFileMetadataStepDefinitions {

    private JsonObject response;
    private String id;
    @Given("I have a file with {string}")
    public void iHaveAFileWith(String id) {
        assert id != null && !id.isBlank();
        this.id = id;
    }

    @When("I send GetFileMetadata request")
    public void iSendGetFileMetadataRequest() throws IOException {
        response = new GetFileMetadata(List.of(), id).send();
    }

    @Then("I should get its name {string} in the response")
    public void iShouldGetItsNameInTheResponse(String fileName) {
        JsonElement name = response.get("name");
        assert name != null && name.getAsString().equals(fileName);
    }

    @And("I should get the same id that I searched for in the response")
    public void iShouldGetTheSameIdThatISearchedForInTheResponse() {
        JsonElement parsedId = response.get("id");
        assert parsedId != null && parsedId.getAsString().substring(3).equals(id);
    }
}
