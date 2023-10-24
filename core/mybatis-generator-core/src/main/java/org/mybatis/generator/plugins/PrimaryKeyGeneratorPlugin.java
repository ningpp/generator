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
import org.mybatis.generator.codegen.mybatis3.model.PrimaryKeyGenerator;

public class PrimaryKeyGeneratorPlugin extends AbstractJavaGeneratorPlugin {

    @Override
    public void initialized(IntrospectedTable introspectedTable) {
        introspectedTable.setPrimaryKeyType(calculatePrimaryKeyType(introspectedTable));
    }

    public static String calculatePrimaryKeyType(IntrospectedTable introspectedTable) {
        StringBuilder sb = new StringBuilder();
        sb.append(calculateJavaModelPackage(introspectedTable));
        sb.append('.');
        sb.append(introspectedTable.getFullyQualifiedTable().getDomainObjectName());
        sb.append("Key"); //$NON-NLS-1$
        return sb.toString();
    }

    @Override
    public AbstractJavaGenerator getGenerator(IntrospectedTable introspectedTable) {
        if (introspectedTable.getRules().generatePrimaryKeyClass()) {
            return new PrimaryKeyGenerator(getProject());
        }
        return null;
    }

}
