require 'test_helper'

class SmoketestControllerTest < ActionController::TestCase
  test "should get index" do
    get :index
    assert_response :success
  end

end
