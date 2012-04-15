package smoketests.demo.tests;

import org.springframework.stereotype.Component;

/**
 * User: Knut Haugen <knuthaug@gmail.com>
 * 2012-04-13
 */

@Component
public class DbSmoketest implements Testable {

    private String stacktrace;

    @Override
    public void runTest() {
        //actually run a light query agains the database
    }

    @Override
    public String getName() {
        return "Test database connection";
    }

    @Override
    public String getResult() {
        return OK;
    }

    @Override
    public String getStacktrace() {
        return stacktrace;
    }

    @Override
    public void stacktrace(String s) {
        stacktrace = s;
    }
}
