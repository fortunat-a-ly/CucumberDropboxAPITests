# Created by User at 12/05/2023
Feature: Get File Metadata
  The feature returns shared file metadata.

  Scenario Outline: Get name of the available file by its id
    Given I have a file with "<id>"
    When I send GetFileMetadata request
    Then I should get its name "<name>" in the response
    And I should get the same id that I searched for in the response

    Examples:
      | id                     | name          |
      | XecZVagxrBsAAAAAAAAACg | text-file.txt |