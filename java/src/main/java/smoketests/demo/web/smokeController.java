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

	@RequestMapping("/smoketests")
	public String runSmoketests(Model uiModel) {
        Map<String, String> results = new HashMap<String, String>();
        
        for(Testable test : allTests ){
            results.put(test.name(), test.runTest());
        }
        uiModel.addAttribute("tests", results);
        return "smoketests";
	}
}
