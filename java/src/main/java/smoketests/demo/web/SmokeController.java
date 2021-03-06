package smoketests.demo.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import smoketests.demo.tests.Testable;

import java.util.List;


@Controller
public class SmokeController {

    @Autowired
    List<Testable> allTests;

	@RequestMapping("/smoketests.html")
	public String runSmoketests(Model uiModel) {
        runAllTests(uiModel);
        return "smoketests";
	}

    @RequestMapping("/smoketests.xml")
    public String runSmoketestsAsXML(Model uiModel) {
        runAllTests(uiModel);
        return "smoketests.xml";
    }

    private void runAllTests(Model uiModel) {
        int failureCount = 0;

        for(Testable test : allTests ){
            test.runTest();

            if(Testable.FAIL.equals(test.getResult())) {
                failureCount++;
            }

        }
        uiModel.addAttribute("failureCount", failureCount);
        uiModel.addAttribute("testCount", allTests.size());
        uiModel.addAttribute("tests", allTests);

    }


}
