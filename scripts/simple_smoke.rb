#!/usr/bin/env ruby

require "rubygems"
require "celerity"

# Main class for Smoketest runner
class Smoke

  def initialize
    @urls = {
      "http://www.eiendomsnett.no/" => [:ok],
      "http://www.ba.no/" => [:ok],
    }
  end

  def run
    run_all_urls @urls
  end

  def run_all_urls(set)

    @urls.each do |base_url, methods|
      browser = Celerity::Browser.new(:css => true,
                                      :refresh_handler => :threaded,
                                      :javascript_enabled => false)

      browser = visit_url browser, base_url

      if(browser.status_code == 200)
        puts "#{base_url} OK"
      else
        puts "#{base_ur} FAIL"
      end

    end
  end

  def visit_url(browser, url)
    begin
      browser.goto url
    rescue Exception => e
      printf "Exception: #{e.backtrace}"
      puts "=" * 60
    end
    browser
  end

end

Smoke.new.run


