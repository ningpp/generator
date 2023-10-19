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

import org.mybatis.generator.api.GeneratedKotlinFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.kotlin.KotlinFile;
import org.mybatis.generator.codegen.AbstractKotlinGenerator;
import org.mybatis.generator.config.PropertyRegistry;

public abstract class AbstracKotlinGeneratorPlugin extends AbstractGeneratorPlugin {

    @Override
    public abstract AbstractKotlinGenerator getGenerator(IntrospectedTable introspectedTable);

    @Override
    public List<GeneratedKotlinFile> contextGenerateAdditionalKotlinFiles(IntrospectedTable introspectedTable) {
        AbstractKotlinGenerator generator = getGenerator(introspectedTable);
        if (generator == null) {
            return new ArrayList<>(0);
        }
        initGenerator(generator, introspectedTable);

        List<GeneratedKotlinFile> answer = new ArrayList<>();
        List<KotlinFile> kotlinFiles = generator.getKotlinFiles();
        for (KotlinFile kotlinFile : kotlinFiles) {
            GeneratedKotlinFile gjf = new GeneratedKotlinFile(kotlinFile,
                            getProject(),
                            context.getProperty(PropertyRegistry.CONTEXT_KOTLIN_FILE_ENCODING),
                            context.getKotlinFormatter());
            answer.add(gjf);
        }
        return answer;
    }

}
