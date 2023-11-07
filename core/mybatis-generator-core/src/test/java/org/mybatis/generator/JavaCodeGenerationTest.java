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

import com.github.javaparser.ParseProblemException;
import com.github.javaparser.StaticJavaParser;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.api.dom.DefaultJavaFormatter;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

class JavaCodeGenerationTest {

    @ParameterizedTest
    @MethodSource("javaFileGenerator")
    void testJavaParse(GeneratedJavaFile generatedJavaFile) {
        DefaultJavaFormatter formatter = new DefaultJavaFormatter();

        ByteArrayInputStream is = new ByteArrayInputStream(
                formatter.getFormattedContent(generatedJavaFile.getCompilationUnit()).getBytes());
        try {
            StaticJavaParser.parse(is);
        } catch (ParseProblemException e) {
            fail("Generated Java File " + generatedJavaFile.getFileName() + " will not compile");
        }
    }

    static List<GeneratedJavaFile> javaFileGenerator() throws Exception {
        List<GeneratedJavaFile> generatedFiles = new ArrayList<>();
        List<GeneratedJavaFile> mybatisFiles = generateJavaFilesMybatis();
        generatedFiles.addAll(mybatisFiles);
        List<GeneratedJavaFile> mybatisFiles2 = generateJavaFilesMybatis2();
        generatedFiles.addAll(mybatisFiles2);

        assertEquals(mybatisFiles.size(), mybatisFiles2.size());
        int size = mybatisFiles.size();
        for (int i = 0; i < size; i++) {
            assertEquals(mybatisFiles.get(i).getFormattedContent(),
                    mybatisFiles2.get(i).getFormattedContent());
        }


        List<GeneratedJavaFile> dsqlFiles = generateJavaFilesMybatisDsql();
        generatedFiles.addAll(dsqlFiles);
        List<GeneratedJavaFile> dsql2Files = generateJavaFilesMybatisDsql2();
        generatedFiles.addAll(dsql2Files);

        assertEquals(dsqlFiles.size(), dsql2Files.size());
        int dsize = dsqlFiles.size();
        for (int i = 0; i < dsize; i++) {
            assertEquals(dsqlFiles.get(i).getFormattedContent(),
                         dsql2Files.get(i).getFormattedContent());
        }

        return generatedFiles;
    }

    static List<GeneratedJavaFile> generateJavaFilesMybatis() throws Exception {
        createDatabase();
        return generateJavaFiles("/scripts/generatorConfig.xml");
    }

    static List<GeneratedJavaFile> generateJavaFilesMybatis2() throws Exception {
        createDatabase();
        return generateJavaFiles("/scripts/generatorConfig2.xml");
    }

    static List<GeneratedJavaFile> generateJavaFilesMybatisDsql() throws Exception {
        createDatabase();
        return generateJavaFiles("/scripts/generatorConfig_Dsql.xml");
    }

    static List<GeneratedJavaFile> generateJavaFilesMybatisDsql2() throws Exception {
        createDatabase();
        return generateJavaFiles("/scripts/generatorConfig_Dsql2.xml");
    }

    static List<GeneratedJavaFile> generateJavaFiles(String configFile) throws Exception {
        List<String> warnings = new ArrayList<>();
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(JavaCodeGenerationTest.class.getResourceAsStream(configFile));

        DefaultShellCallback shellCallback = new DefaultShellCallback(true);

        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, shellCallback, warnings);
        myBatisGenerator.generate(null, null, null, false);
        return myBatisGenerator.getGeneratedJavaFiles();
    }

    static void createDatabase() throws Exception {
        SqlScriptRunner scriptRunner = new SqlScriptRunner(JavaCodeGenerationTest.class.getResourceAsStream("/scripts/CreateDB.sql"), "org.hsqldb.jdbcDriver", "jdbc:hsqldb:mem:aname", "sa", "");
        scriptRunner.executeScript();
    }
}
