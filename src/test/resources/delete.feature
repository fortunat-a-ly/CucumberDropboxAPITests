# Created by User at 12/05/2023
Feature: Delete a file
  Delete a batch of closed file requests.

  Scenario Outline: Delete an existing file and non-existing file
    Given I have a file with "<id>" that I want to delete
    And I have another file under wrong id "<wrong_id>" that I want to delete
    When I delete them
    Then I should get the error "<error>" in the response

    Examples:
      | id                     | wrong_id | error     |
      | XecZVagxrBsAAAAAAAAACA | aaaaaaaa | not_found |