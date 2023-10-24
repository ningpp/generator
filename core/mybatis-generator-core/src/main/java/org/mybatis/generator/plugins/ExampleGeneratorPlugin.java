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

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.codegen.AbstractJavaGenerator;
import org.mybatis.generator.codegen.mybatis3.model.ExampleGenerator;
import org.mybatis.generator.config.JavaModelGeneratorConfiguration;
import org.mybatis.generator.config.PropertyRegistry;

import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;

public class ExampleGeneratorPlugin extends AbstractJavaGeneratorPlugin {

    @Override
    public void initialized(IntrospectedTable introspectedTable) {
        introspectedTable.setExampleType(calculateExampleType(introspectedTable));
    }

    @Override
    public AbstractJavaGenerator getGenerator(IntrospectedTable introspectedTable) {
        if (introspectedTable.getRules().generateExampleClass()) {
            return new ExampleGenerator(getProject());
        }
        return null;
    }

    public static String calculateExampleType(IntrospectedTable introspectedTable) {
        String exampleTargetPackage = calculateJavaModelExamplePackage(introspectedTable);
        StringBuilder sb = new StringBuilder();
        sb.append(exampleTargetPackage);
        sb.append('.');
        sb.append(introspectedTable.getFullyQualifiedTable().getDomainObjectName());
        sb.append("Example"); //$NON-NLS-1$
        return sb.toString();
    }

    /**
     * If property exampleTargetPackage specified for example use the specified value, else
     * use default value (targetPackage).
     *
     * @return the calculated package
     */
    public static String calculateJavaModelExamplePackage(IntrospectedTable introspectedTable) {
        JavaModelGeneratorConfiguration config = introspectedTable.getContext().getJavaModelGeneratorConfiguration();
        String exampleTargetPackage = config.getProperty(PropertyRegistry.MODEL_GENERATOR_EXAMPLE_PACKAGE);
        if (!stringHasValue(exampleTargetPackage)) {
            return calculateJavaModelPackage(introspectedTable);
        }

        return exampleTargetPackage
                + introspectedTable.getFullyQualifiedTable().getSubPackageForModel(isSubPackagesEnabled(config));
    }

}
