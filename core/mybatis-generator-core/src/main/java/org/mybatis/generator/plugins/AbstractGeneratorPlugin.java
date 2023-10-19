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
package org.mybatis.generator.plugins;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.ProgressCallback;
import org.mybatis.generator.codegen.AbstractGenerator;

public abstract class AbstractGeneratorPlugin extends PluginAdapter {

    public static final String TARGET_PROJECT_PROPERTY = "targetProject";

    public static final ProgressCallback NULL_PROGRESS_CALLBACK = new ProgressCallback() {};

    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }

    public abstract AbstractGenerator getGenerator(IntrospectedTable introspectedTable);

    protected String getProject() {
        return properties.getProperty(TARGET_PROJECT_PROPERTY);
    }

    protected void initGenerator(AbstractGenerator generator,
            IntrospectedTable introspectedTable) {
        generator.setContext(context);
        generator.setIntrospectedTable(introspectedTable);
        generator.setProgressCallback(NULL_PROGRESS_CALLBACK);
        generator.setWarnings(new ArrayList<>(0));
    }



}
