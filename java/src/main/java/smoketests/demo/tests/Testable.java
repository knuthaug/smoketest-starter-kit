package smoketests.demo.tests;

/**
 * User: Knut Haugen <knuthaug@gmail.com>
 * 2012-04-13
 */
public interface Testable {

    String OK = "OK";
    String FAIL = "FAILED";

    void runTest();
    String getName();
    String getResult();
}
