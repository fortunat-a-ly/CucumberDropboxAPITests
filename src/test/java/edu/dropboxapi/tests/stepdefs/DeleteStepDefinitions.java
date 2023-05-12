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
    public void iHaveAFileWith(String id) throws IOException {
        assert id != null && !id.isBlank();
        JsonObject rs = new GetFileMetadata(List.of(), id).send();
        assert rs.get("error") == null;
        JsonElement responseId = rs.get("id");
        assert responseId != null && responseId.getAsString().equals("id:" + id);
        this.id = id;
    }

    @And("I have another file under wrong id {string} that I want to delete")
    public void iHaveAnotherFileUnderWrongIdThatIWantToDelete(String wrongId) throws IOException {
        assert id != null;
        JsonObject rs = new GetFileMetadata(List.of(), wrongId).send();
        JsonElement error = rs.get("error");
        assert  error != null
                && ("not_found".equals(error.getAsJsonObject().get(".tag").getAsString())
                    || "access_error".equals(error.getAsJsonObject().get(".tag").getAsString()));
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
