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

import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.codegen.AbstractJavaGenerator;
import org.mybatis.generator.config.JavaModelGeneratorConfiguration;
import org.mybatis.generator.config.PropertyHolder;
import org.mybatis.generator.config.PropertyRegistry;

import static org.mybatis.generator.internal.util.StringUtility.isTrue;

public abstract class AbstractJavaGeneratorPlugin extends AbstractGeneratorPlugin {

    @Override
    public abstract AbstractJavaGenerator getGenerator(IntrospectedTable introspectedTable);

    @Override
    public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(IntrospectedTable introspectedTable) {
        AbstractJavaGenerator generator = getGenerator(introspectedTable);
        if (generator == null) {
            return new ArrayList<>(0);
        }
        initGenerator(generator, introspectedTable);

        List<GeneratedJavaFile> answer = new ArrayList<>();
        List<CompilationUnit> compilationUnits = generator.getCompilationUnits();
        for (CompilationUnit compilationUnit : compilationUnits) {
            GeneratedJavaFile gjf = new GeneratedJavaFile(compilationUnit,
                            getProject(),
                            context.getProperty(PropertyRegistry.CONTEXT_JAVA_FILE_ENCODING),
                            context.getJavaFormatter());
            answer.add(gjf);
        }
        return answer;
    }

    public static String calculateJavaModelPackage(IntrospectedTable introspectedTable) {
        JavaModelGeneratorConfiguration config = introspectedTable.getContext()
                .getJavaModelGeneratorConfiguration();

        return config.getTargetPackage()
                + introspectedTable.getFullyQualifiedTable().getSubPackageForModel(isSubPackagesEnabled(config));
    }

    public static boolean isSubPackagesEnabled(PropertyHolder propertyHolder) {
        return isTrue(propertyHolder.getProperty(PropertyRegistry.ANY_ENABLE_SUB_PACKAGES));
    }

}
