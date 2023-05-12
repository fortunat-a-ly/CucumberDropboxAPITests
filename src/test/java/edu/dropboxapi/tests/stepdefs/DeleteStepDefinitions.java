package edu.dropboxapi.tests.stepdefs;

import apirequests.DeleteRequest;
import apirequests.GetFileMetadata;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.io.IOException;
import java.util.List;

public class DeleteStepDefinitions {

    private JsonObject response;
    private String id;
    private String wrongId;
    @Given("I have a file with {string} that I want to delete")
    public void iHaveAFileWith(String id) {
        assert id != null && !id.isBlank();
        this.id = id;
    }

    @And("I have another file under wrong id {string} that I want to delete")
    public void iHaveAnotherFileUnderWrongIdThatIWantToDelete(String wrongId) {
        assert wrongId != null && !wrongId.isBlank();
        this.wrongId = wrongId;
    }

    @When("I delete them")
    public void iSendDeleteRequest() throws IOException {
        response = new DeleteRequest(List.of(wrongId, id)).send();
    }

    @Then("I should get the error {string} in the response")
    public void iShouldGetItsErrorInTheResponse(String errorMessage) {
        JsonElement error = response.get("error");
        assert error != null;
        assert errorMessage.equals(error.getAsJsonObject().get(".tag").getAsString());
    }
}
