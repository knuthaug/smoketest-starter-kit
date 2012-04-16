Java smoketest starter kit
##################################################

Meta:
 This is a demo java project doing internal smoketests in a java web app. 
 It is implemented using an interface and implementing classes, and spring 
 for wiring up the tests in a controller. The controller does very simple 
 "content negotiation" for pedagogical reasons.

Files:
 The most interesting files are the controller (src/main/java/smoketests/demo/web/SmokeController.java)
 where the test instances are injected and run.

 Other than that the tests themselves and the interface the extends is in the smoketests.demo.tests
 package.

Plumbing:
 There are to sets of templates for producing html and xml output. To simply work around the 
 extension model of apache tiles, there are two sets of parent tiles too, namely default.jspx, 
 used for the html smoketest, wrapping the output in the normal site look&feel, and blank.jspx
 which is used for smoketests.xml.jspx and creates a simple xml document.
