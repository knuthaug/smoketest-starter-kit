
class SmoketestController < ApplicationController
  def index

    @tests = Smoketests.new.run_all

    respond_to do |format|
      format.html { render :handlers => [:haml] }

      format.xml { render :handlers => [:haml],
                          :formats => [:xml],
                          :layout => false }
    end
  end
end
