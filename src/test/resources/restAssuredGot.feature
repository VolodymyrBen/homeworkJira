Feature:  RestAssured GET method

  Scenario: get all ids and house on GOT
    When user sends a get request on APi to url
    Then the user get all houses and ids and store them in a map