package Rummikub;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;


@RunWith(Cucumber.class)
// plugin = {"pretty", "summary"},
@CucumberOptions(plugin = {"pretty", "summary"}, features = "src/test/java/featureFiles",
        glue = "Rummikub",
        monochrome=true) // disable colors for console
public class RunCucumber {
}