Feature: Event offer
  Validation of events for offer feature

  @event @offer
  Scenario: events trigger for offer failure
    Given I am on ott website
    And  I enter invalid credentials
    When I capture network traffic from browermobproxy
    Then I should see the event triggered with "Subscription - Attempt - Failure"
