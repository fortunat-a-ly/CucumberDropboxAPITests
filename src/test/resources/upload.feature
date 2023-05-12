# Created by User at 11/05/2023
Feature: Dropbox Upload
  Create a new file with the contents provided in the request.

  Scenario Outline: Upload a file
    Given I have a file smaller than <size> MB
    When I upload it under the path "<path>"
    Then I should get a file id in the response
    And I should get a file part of the path as the name in the response

    Examples:
      | size | path                        |
      | 150  | /cucumber/text-file6666.txt |
