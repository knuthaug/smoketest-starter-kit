package smoketests.demo.tests;

import org.springframework.stereotype.Component;

/**
 * User: Knut Haugen <knuthaug@gmail.com>
 * 2012-04-25
 */
@Component
public class Smoketest2 implements Testable {

    @Override
    public void runTest() {
        //dummy
    }

    @Override
    public String getName() {
        return "This test does very important work!";
    }

    @Override
    public String getResult() {
        return OK;
    }
}

