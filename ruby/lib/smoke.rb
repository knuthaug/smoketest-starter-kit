
class Smoke
  attr_accessor :description, :status, :message

  def initialize(descr)
    self.description = descr
  end

  def run
    yield
  end

  def self.OK
    "OK"
  end

  def self.FAIL
    "FAIL"
  end

end
