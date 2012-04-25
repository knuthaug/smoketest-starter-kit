package smoketests.demo.tests;

import org.springframework.stereotype.Component;

/**
 * User: Knut Haugen <knuthaug@gmail.com>
 * 2012-04-25
 */

@Component
public class Smoketest3 implements Testable {
    @Override
    public void runTest() {
        //dummy
    }

    @Override
    public String getName() {
        return "This one too!";
    }

    @Override
    public String getResult() {
        return OK;
    }
}
