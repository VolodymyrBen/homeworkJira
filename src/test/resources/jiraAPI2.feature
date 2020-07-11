Feature: Total number of issues per Assignee

  Scenario: get number of issues for Roman
    When the user sends get request to get total number of issues assigned to assignee
    Then the user validates total number of stories for same assignee on IU
