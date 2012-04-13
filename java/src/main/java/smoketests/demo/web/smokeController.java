package smoketests.demo.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import smoketests.demo.tests.Testable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
public class smokeController {

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

    private Map<String, String> runAllTests(Model uiModel) {
        Map<String, String> results = new HashMap<String, String>();
        int failureCount = 0;

        for(Testable test : allTests ){
            String status = test.runTest();
            if(status.equals(Testable.FAIL)) {
                failureCount++;
            }
            results.put(test.name(), status);
        }
        uiModel.addAttribute("failureCount", failureCount);
        uiModel.addAttribute("testCount", results.size());
        uiModel.addAttribute("tests", results);
        return results;
    }


}
