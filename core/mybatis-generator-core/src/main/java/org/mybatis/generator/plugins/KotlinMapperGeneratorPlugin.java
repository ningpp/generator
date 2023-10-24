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
import org.mybatis.generator.api.dom.kotlin.KotlinFile;
import org.mybatis.generator.api.dom.kotlin.KotlinType;
import org.mybatis.generator.codegen.AbstractKotlinGenerator;
import org.mybatis.generator.runtime.kotlin.KotlinMapperAndExtensionsGenerator;

public class KotlinMapperGeneratorPlugin
        extends AbstracKotlinGeneratorPlugin {

    @Override
    public void initialized(IntrospectedTable introspectedTable) {
        if (context.getJavaClientGeneratorConfiguration() == null) {
            return;
        }
        introspectedTable.setMyBatisDynamicSqlSupportType(
                DynamicSqlSupportClassGeneratorPlugin.calculateDynamicSqlSupportType(introspectedTable));
        introspectedTable.setMyBatisDynamicSQLTableObjectName(
                DynamicSqlSupportClassGeneratorPlugin.calculateDynamicSQLTableObjectName(introspectedTable));

        introspectedTable.setMyBatis3JavaMapperType(AbstractJavaClientGeneratorPlugin.calculateJavaMapperType(introspectedTable));
    }

    @Override
    public AbstractKotlinGenerator getGenerator(
            IntrospectedTable introspectedTable) {
        if (context.getJavaClientGeneratorConfiguration() != null
                && introspectedTable.getRules().generateJavaClient()) {
            return new KotlinMapperAndExtensionsGenerator(getProject());
        }
        return null;
    }

    @Override
    public boolean dynamicSqlSupportGenerated(KotlinFile kotlinFile,
            KotlinType outerSupportObject, KotlinType innerSupportClass,
            IntrospectedTable introspectedTable) {
        return false;
    }

}
