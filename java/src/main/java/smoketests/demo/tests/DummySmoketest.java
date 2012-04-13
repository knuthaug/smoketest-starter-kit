package smoketests.demo.tests;

import org.springframework.stereotype.Component;

/**
 * User: Knut Haugen <knuthaug@gmail.com>
 * 2012-04-13
 */

@Component
public class DummySmoketest implements Testable {

    @Override
    public String runTest() {
        return FAIL;
    }

    @Override
    public String name() {
        return "Another test";
    }
}
