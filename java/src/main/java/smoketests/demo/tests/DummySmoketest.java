package smoketests.demo.tests;

import org.springframework.stereotype.Component;

/**
 * User: Knut Haugen <knuthaug@gmail.com>
 * 2012-04-13
 */

@Component
public class DummySmoketest implements Testable {

    private String stacktrace;

    @Override
    public void runTest() {
         //dummy
    }

    @Override
    public String getName() {
        return "Another test";
    }

    @Override
    public String getResult() {
        return FAIL;
    }


}
