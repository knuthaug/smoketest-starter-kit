require 'smoke'

class Smoketests

  def initialize
  end

  def run_all
    @tests = Array.new

    google_api_test
    redis_test
    mongodb_smoke_test
    @tests
  end

  def redis_test
    test = Smoke.new("Redis")
    test.run {
      test.status = Smoke.OK
    }

    @tests << test
  end


  def google_api_test
    test = Smoke.new("Google API")
    test.run {
      test.status = Smoke.OK
    }

    @tests << test
  end


  def mongodb_smoke_test
    test = Smoke.new("MongoDB test connection")

    test.run {
      begin
        Person.foo
        test.status = Smoke.OK
      rescue Exception => e
        test.status = Smoke.FAIL
        test.message = e.message
      end
    }

    @tests << test
  end

end
