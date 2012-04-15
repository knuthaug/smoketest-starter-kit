package smoketests.demo.tests;

/**
 * User: Knut Haugen <knuthaug@gmail.com>
 * 2012-04-13
 */
public interface Testable {

    String OK = "OK";
    String FAIL = "FAILED";

    public void runTest();
    public String getName();
    public String getResult();
    public String getStacktrace();
    void stacktrace(String s);
}
