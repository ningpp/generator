/*
 *    Copyright 2006-2023 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       https://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.mybatis.generator;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mybatis.generator.api.GeneratedXmlFile;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

class XmlCodeGenerationTest {

    @ParameterizedTest
    @MethodSource("xmlFileGenerator")
    void testXmlParse(GeneratedXmlFile generatedXmlFile) {
        ByteArrayInputStream is = new ByteArrayInputStream(
                generatedXmlFile.getFormattedContent().getBytes());
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setValidating(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            builder.setEntityResolver(new TestEntityResolver());
            builder.setErrorHandler(new TestErrorHandler());
            builder.parse(is);
        } catch (Exception e) {
            fail("Generated XML File " + generatedXmlFile.getFileName() + " will not parse");
        }
    }

    static List<GeneratedXmlFile> xmlFileGenerator() throws Exception {
        return new ArrayList<>(generateXmlFilesMybatis());
    }

    static List<GeneratedXmlFile> generateXmlFilesMybatis() throws Exception {
        List<GeneratedXmlFile> allFiles = new ArrayList<>();
        JavaCodeGenerationTest.createDatabase();
        List<GeneratedXmlFile> xmlFiles = generateXmlFiles("/scripts/generatorConfig.xml");
        allFiles.addAll(xmlFiles);
        List<GeneratedXmlFile> xml2Files = generateXmlFiles("/scripts/generatorConfig2.xml");
        allFiles.addAll(xml2Files);

        assertEquals(xmlFiles.size(), xml2Files.size());
        int size = xmlFiles.size();
        for (int i = 0; i < size; i++) {
            assertEquals(xmlFiles.get(i).getFormattedContent(),
                    xml2Files.get(i).getFormattedContent());
        }
        return allFiles;
    }

    static List<GeneratedXmlFile> generateXmlFiles(String configFile) throws Exception {
        List<String> warnings = new ArrayList<>();
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(JavaCodeGenerationTest.class.getResourceAsStream(configFile));

        DefaultShellCallback shellCallback = new DefaultShellCallback(true);

        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, shellCallback, warnings);
        myBatisGenerator.generate(null, Set.of("miscellaneousTests_NoClient"), null, false);
        return myBatisGenerator.getGeneratedXmlFiles();
    }

    static class TestEntityResolver implements EntityResolver {

        @Override
        public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
            // just return an empty string. this should stop the parser from trying to access the network
            return new InputSource(new ByteArrayInputStream("".getBytes()));
        }
    }

    static class TestErrorHandler implements ErrorHandler {

        private final List<String> errors = new ArrayList<>();
        private final List<String> warnings = new ArrayList<>();

        @Override
        public void warning(SAXParseException exception) throws SAXException {
            warnings.add(exception.getMessage());
        }

        @Override
        public void error(SAXParseException exception) throws SAXException {
            errors.add(exception.getMessage());
        }

        @Override
        public void fatalError(SAXParseException exception) throws SAXException {
            errors.add(exception.getMessage());
        }

        public List<String> getErrors() {
            return errors;
        }

        public List<String> getWarnings() {
            return warnings;
        }
    }
}
