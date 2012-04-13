package smoketests.demo.tests;

import org.springframework.stereotype.Component;

/**
 * User: Knut Haugen <knuthaug@gmail.com>
 * 2012-04-13
 */

@Component
public class DbSmoketest implements Testable {

    @Override
    public String runTest() {
        //actually run a light query agains the database
        return OK;
    }

    @Override
    public String name() {
        return "Test database connection";
    }
}
