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

import java.util.Arrays;
import java.util.List;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.kotlin.KotlinFile;
import org.mybatis.generator.codegen.AbstractKotlinGenerator;
import org.mybatis.generator.runtime.kotlin.KotlinDynamicSqlSupportClassGenerator;

public class KotlinDynamicSqlSupportClassGeneratorPlugin
        extends AbstracKotlinGeneratorPlugin {

    @Override
    public void initialized(IntrospectedTable introspectedTable) {
        introspectedTable.setMyBatisDynamicSqlSupportType(
                DynamicSqlSupportClassGeneratorPlugin.calculateDynamicSqlSupportType(introspectedTable));
    }

    @Override
    public AbstractKotlinGenerator getGenerator(
            IntrospectedTable introspectedTable) {
        if (context.getJavaClientGeneratorConfiguration() != null
                && introspectedTable.getRules().generateJavaClient()) {
            return new AbstractKotlinGenerator(null) {

                @Override
                public List<KotlinFile> getKotlinFiles() {
                    return Arrays.asList(new KotlinDynamicSqlSupportClassGenerator(
                            context, introspectedTable, warnings).getKotlinFile());
                }

            };
        }
        return null;
    }

}
