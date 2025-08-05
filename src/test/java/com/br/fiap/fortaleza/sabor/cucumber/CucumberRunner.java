package com.br.fiap.fortaleza.sabor.cucumber;

import org.junit.platform.suite.api.*;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ConfigurationParameter(key = "cucumber.glue", value = "com.br.fiap.fortaleza.sabor.cucumber")
@ConfigurationParameter(key = "cucumber.plugin", value = 
    "pretty," +
    "html:target/cucumber-html-reports," +
    "json:target/cucumber-json-reports/cucumber.json," +
    "junit:target/cucumber-xml-reports/cucumber.xml")
@ConfigurationParameter(key = "cucumber.features", value = "src/test/resources/features")
@ConfigurationParameter(key = "cucumber.filter.tags", value = "not @ignore")
public class CucumberRunner {
}
