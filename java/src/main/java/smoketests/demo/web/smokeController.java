package smoketests.demo.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class smokeController {

	@RequestMapping("/smoketests")
	public String runSmoketests(Model uiModel) {
		return "smoketests";
		
	}
}
