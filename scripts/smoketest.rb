#!/usr/bin/env ruby

require "rubygems"
require 'trollop'
require "celerity"
require 'term/ansicolor'

# Main class for Smoketest runner
class Smoke


  def initialize

    @urls = {
      "http://www.eiendomsnett.no/" => [:ok, :no_errors],
      "http://www.ba.no/" => [:ok, :no_errors],
      "http://www.vg.no/foo" => [:ok],
    }

    @opts = Trollop::options do
      opt :format, "Output test results in this format. Default is text, alternative is xml", :type => :string
    end

    @opts[:format] = "text" if !@opts[:format_given]

  end

  def run

    i = 0

    if(@opts[:format] == "xml")
      puts "<testsuites name='smoketests' errors='' failures='' tests='' time=''>"
    end

    run_all_urls @urls

    if(@opts[:format] == "xml")
      puts "</testsuites>"
    end


  end

  def run_all_urls(set)

    @urls.each do |base_url, methods|
      browser = Celerity::Browser.new(:css => true, :refresh_handler => :threaded, :javascript_enabled => false)

      if(@opts[:format] == "text")
        puts base_url
      end

      browser = visit_url browser, base_url

      test = Smoke::Test.new(base_url, browser, @opts[:format])

      statuses = { :OK => 0, :FAIL => 0, :WARNING => 0, :total => 0, :time => Time.now }

      status = []
      message = ""

      methods.each do |method|
        status = test.send(method, method)
        statuses[status[1]] += 1
        message += status[0]
        statuses[:total] += 1
      end

      statuses[:time] = (Time.now - statuses[:time])
      print_results(statuses, message, base_url)

    end
  end


  def print_results(statuses, message, url)
    if(@opts[:format] == "xml")
      puts "  <testsuite name='#{escape(url)}' errors='#{statuses[:WARNING]}' failures='#{statuses[:FAIL]}' tests='#{statuses[:total]}' time='#{statuses[:time]}'>"
      puts message
      puts "  </testsuite>"
    else
      puts message
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

  def escape(url)
    url = url.gsub(/&/, "&amp;")
    url
  end

end



module Smoke::Constants
  NO_ERRORS = :NO_ERRORS;
  OK = :OK
  FAIL = :FAIL
  WARNING = :WARNING
  SITE_STAT = :SITE_STAT
  TNS_STAT = :TNS_STAT
end


# Class for one URL to test. Will handle several kinds of tests
class Smoke::Test
  include Term::ANSIColor
  include Smoke::Constants

  def initialize(url, browser, format)

    @format = format;
    @url = url
    @browser = browser
    @formatter = Smoke::Formatter.new(format)

  end

  def ok(method)
    time = Time.now

    if @browser.page.nil? || @browser.status_code != 200
      out = @formatter.output(@url, OK,
                              " - (method: ok) %15s status was: #{ @browser.page.nil? ? "unknown" : @browser.status_code}\n",
                              FAIL, "", find_time(time))
      return [out, FAIL]
    else
      return [(@formatter.output(@url, OK, " - (method: ok) %20s\n", OK, "", find_time(time))), OK]
    end
  end

  def no_errors(method)
    error_divs = Array.new
    time = Time.now

    if @browser.page.nil? || @browser.status_code != 200
      out = @formatter.output(@url, :NO_ERRORS, " - (method: no_errors) %15s status was: unknown\n",
                              FAIL, "", find_time(time))
      return [out, FAIL]
    else
      @browser.divs.each do |element|
        error_divs << element if element.attribute_value("class") == "errorMsg"
      end

      return process_errors(error_divs, method, time)
    end

  end


  def find_time(time)
    (Time.now - time)
  end


  def process_errors(error_divs, method, time)
    error = error_divs.length > 0;

    if(error)

      error_string = loop_errors(error_divs)

      out = @formatter.output(@url, method, " - (method: no_errors)   %4s (error divs found: #{error_divs.length})\n",
                                :WARNING, error_string, find_time(time))
      return [out, WARNING]
    else
      return [(@formatter.output(@url, method, " - (method: no_errors)   %3s\n", :OK, "", find_time(time))), OK]
    end
  end


  def loop_errors(error_divs)
    output = ""
    error_divs.each do |div|
      output += "     #{div.to_xml}"
      output += "-" * 70
      output += "\n"
    end

    output
  end

end


# format string for terminal/text output
class Smoke::Formatter

  def initialize(format)

   if(format.upcase == "XML")
      @formatter = Smoke::Formatter::XML.new
    else
      @formatter = Smoke::Formatter::Text.new
    end
  end

  def output(url, method, message, status, error=nil, time=1)
    @formatter.output(url, method, message, status, error, time)
  end

end

# format strings for xml output
class Smoke::Formatter::XML
  include Smoke::Constants
  include Term::ANSIColor

  def output(url, method, message, status, error, time)
    out = "    <testcase name='#{method.to_s.downcase}' time='#{time}'>\n"
    if(status ==:FAIL)
      out += "      <failure>#{error}</failure>\n"
    end

    if(status ==:WARNING)
      out += "      <error>#{error}</error>\n"
    end
    out += "    </testcase>\n"
    return out;
  end

end

class Smoke::Formatter::Text
  include Smoke::Constants
  include Term::ANSIColor

  def output(url, method, message, status, error, time)

    if (status == OK)
      return sprintf(message, green(status.to_s))
    elsif(status == FAIL)
      out = sprintf(message, red(status.to_s))

      if(!error.nil?)
        out += error
      end
      return out
    elsif(status == WARNING)
      out = sprintf(message, yellow(status.to_s))
      if(!error.nil?)
        out += error
      end
      return out
    end

  end
end

Smoke.new.run


