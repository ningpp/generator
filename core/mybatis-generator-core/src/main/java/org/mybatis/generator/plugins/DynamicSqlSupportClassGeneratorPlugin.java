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
import org.mybatis.generator.codegen.AbstractJavaGenerator;
import org.mybatis.generator.config.JavaClientGeneratorConfiguration;
import org.mybatis.generator.config.PropertyRegistry;
import org.mybatis.generator.config.TableConfiguration;
import org.mybatis.generator.runtime.dynamic.sql.DynamicSqlSupportClassGenerator;

import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;

public class DynamicSqlSupportClassGeneratorPlugin extends AbstractJavaGeneratorPlugin {

    @Override
    public void initialized(IntrospectedTable introspectedTable) {
        if (context.getJavaClientGeneratorConfiguration() == null) {
            return;
        }
        introspectedTable.setMyBatisDynamicSqlSupportType(
                calculateDynamicSqlSupportType(introspectedTable));
        introspectedTable.setMyBatisDynamicSQLTableObjectName(
                calculateDynamicSQLTableObjectName(introspectedTable));

        introspectedTable.setMyBatis3JavaMapperType(AbstractJavaClientGeneratorPlugin.calculateJavaMapperType(introspectedTable));
    }

    @Override
    public AbstractJavaGenerator getGenerator(IntrospectedTable introspectedTable) {
        return context.getJavaClientGeneratorConfiguration() != null
                ? new DynamicSqlSupportClassGenerator(getProject()) : null;
    }

    public static String calculateDynamicSqlSupportType(IntrospectedTable introspectedTable) {
        StringBuilder sb = new StringBuilder();
        sb.append(calculateDynamicSqlSupportPackage(introspectedTable));
        sb.append('.');
        TableConfiguration tableConfiguration = introspectedTable.getTableConfiguration();
        FullyQualifiedTable fullyQualifiedTable = introspectedTable.getFullyQualifiedTable();
        if (stringHasValue(tableConfiguration.getDynamicSqlSupportClassName())) {
            sb.append(tableConfiguration.getDynamicSqlSupportClassName());
        } else {
            if (stringHasValue(fullyQualifiedTable.getDomainObjectSubPackage())) {
                sb.append(fullyQualifiedTable.getDomainObjectSubPackage());
                sb.append('.');
            }
            sb.append(fullyQualifiedTable.getDomainObjectName());
            sb.append("DynamicSqlSupport"); //$NON-NLS-1$
        }
        return sb.toString();
    }

    private static String calculateDynamicSqlSupportPackage(IntrospectedTable introspectedTable) {
        JavaClientGeneratorConfiguration config = introspectedTable.getContext()
                .getJavaClientGeneratorConfiguration();
        if (config == null) {
            return null;
        }

        String packkage = config.getProperty(PropertyRegistry.CLIENT_DYNAMIC_SQL_SUPPORT_PACKAGE);
        if (stringHasValue(packkage)) {
            return packkage + introspectedTable.getFullyQualifiedTable()
                    .getSubPackageForClientOrSqlMap(isSubPackagesEnabled(config));
        } else {
            return calculateJavaClientInterfacePackage(introspectedTable);
        }
    }

    public static String calculateJavaClientInterfacePackage(IntrospectedTable introspectedTable) {
        JavaClientGeneratorConfiguration config = introspectedTable.getContext()
                .getJavaClientGeneratorConfiguration();
        if (config == null) {
            return null;
        }

        return config.getTargetPackage()
                + introspectedTable.getFullyQualifiedTable()
                .getSubPackageForClientOrSqlMap(isSubPackagesEnabled(config));
    }

    public static String calculateDynamicSQLTableObjectName(IntrospectedTable introspectedTable) {
        TableConfiguration tableConfiguration = introspectedTable.getTableConfiguration();
        FullyQualifiedTable fullyQualifiedTable = introspectedTable.getFullyQualifiedTable();
        if (stringHasValue(tableConfiguration.getDynamicSqlTableObjectName())) {
            return tableConfiguration.getDynamicSqlTableObjectName();
        } else {
            return fullyQualifiedTable.getDomainObjectName();
        }
    }

}
