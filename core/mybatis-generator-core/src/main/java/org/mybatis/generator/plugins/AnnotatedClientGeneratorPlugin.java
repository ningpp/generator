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

import org.mybatis.generator.api.FullyQualifiedTable;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.codegen.AbstractJavaClientGenerator;
import org.mybatis.generator.codegen.mybatis3.javamapper.AnnotatedClientGenerator;
import org.mybatis.generator.config.TableConfiguration;

import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;

public class AnnotatedClientGeneratorPlugin extends AbstractJavaClientGeneratorPlugin {

    public static final String TYPE_ANNOTATEDMAPPER = "ANNOTATEDMAPPER";

    @Override
    public void initialized(IntrospectedTable introspectedTable) {
        introspectedTable.setMyBatis3SqlProviderType(
                calculateSqlProviderType(introspectedTable));
    }

    @Override
    public AbstractJavaClientGenerator getClientGenerator(
            IntrospectedTable introspectedTable) {
        if (TYPE_ANNOTATEDMAPPER.equalsIgnoreCase(getType())) {
            return new AnnotatedClientGenerator(getProject());
        }
        return null;
    }

    public static String calculateSqlProviderType(IntrospectedTable introspectedTable) {
        StringBuilder sb = new StringBuilder();
        sb.append(DynamicSqlSupportClassGeneratorPlugin
                .calculateJavaClientInterfacePackage(introspectedTable));
        sb.append('.');
        TableConfiguration tableConfiguration = introspectedTable.getTableConfiguration();
        FullyQualifiedTable fullyQualifiedTable = introspectedTable.getFullyQualifiedTable();
        if (stringHasValue(tableConfiguration.getSqlProviderName())) {
            sb.append(tableConfiguration.getSqlProviderName());
        } else {
            if (stringHasValue(fullyQualifiedTable.getDomainObjectSubPackage())) {
                sb.append(fullyQualifiedTable.getDomainObjectSubPackage());
                sb.append('.');
            }
            sb.append(fullyQualifiedTable.getDomainObjectName());
            sb.append("SqlProvider"); //$NON-NLS-1$
        }
        return sb.toString();
    }

}
