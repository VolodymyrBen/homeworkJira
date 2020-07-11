Feature: Create five users story and five bugs

  Scenario Outline: Create five US and verify
    When create user story via API with  "<summary>", "<description>", "<issueType>"
    Then verify user stories in UI "<summary>", "<description>", "<issueType>"
    Examples:
      | summary      | description | issueType |
      | summary_1    | good1       | Story     |
      | summary_2    | good2       | Story     |
      | summary_3    | good3       | Story     |
      | summary_4    | good4       | Story     |
      | summary_5    | good5       | Story     |
      | summaryBag_1 | very_bad1   | Bug       |
      | summaryBag_2 | very_bad2   | Bug       |
      | summaryBag_3 | very_bad3   | Bug       |
      | summaryBag_4 | very_bad4   | Bug       |
      | summaryBag_5 | very_bad5   | Bug       |

