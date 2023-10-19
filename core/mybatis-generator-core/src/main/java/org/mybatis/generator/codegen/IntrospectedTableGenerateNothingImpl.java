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
package org.mybatis.generator.codegen;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.GeneratedKotlinFile;
import org.mybatis.generator.api.GeneratedXmlFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.ProgressCallback;

/**
 * Introspected table implementation for generating nothing.
 */
public class IntrospectedTableGenerateNothingImpl extends IntrospectedTable {

    public IntrospectedTableGenerateNothingImpl() {
        this(TargetRuntime.MYBATIS3_DSQL);
    }

    public IntrospectedTableGenerateNothingImpl(TargetRuntime targetRuntime) {
        super(targetRuntime);
    }

    @Override
    public void calculateGenerators(List<String> warnings, ProgressCallback progressCallback) {
        //no generator
    }

    @Override
    public List<GeneratedJavaFile> getGeneratedJavaFiles() {
        return new ArrayList<>(0);
    }

    @Override
    public List<GeneratedXmlFile> getGeneratedXmlFiles() {
        return new ArrayList<>(0);
    }

    @Override
    public List<GeneratedKotlinFile> getGeneratedKotlinFiles() {
        return new ArrayList<>(0);
    }

    @Override
    public int getGenerationSteps() {
        return 0;
    }

    @Override
    public boolean requiresXMLGenerator() {
        return false;
    }

}
