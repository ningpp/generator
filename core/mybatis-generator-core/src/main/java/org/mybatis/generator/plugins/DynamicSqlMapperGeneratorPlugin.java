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
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.codegen.AbstractJavaGenerator;
import org.mybatis.generator.runtime.dynamic.sql.DynamicSqlMapperGenerator;

public class DynamicSqlMapperGeneratorPlugin extends AbstractJavaGeneratorPlugin {

    @Override
    public AbstractJavaGenerator getGenerator(IntrospectedTable introspectedTable) {
        return context.getJavaClientGeneratorConfiguration() != null
                ? new DynamicSqlMapperGenerator(getProject()) : null;
    }

    @Override
    public boolean dynamicSqlSupportGenerated(TopLevelClass supportClass, IntrospectedTable introspectedTable) {
        //DynamicSqlMapperGenerator will not gen dynamicSqlSupport
        return false;
    }

}
