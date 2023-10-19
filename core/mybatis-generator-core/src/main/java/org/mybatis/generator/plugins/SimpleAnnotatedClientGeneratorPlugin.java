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
import org.mybatis.generator.codegen.AbstractJavaClientGenerator;
import org.mybatis.generator.codegen.mybatis3.javamapper.SimpleAnnotatedClientGenerator;

public class SimpleAnnotatedClientGeneratorPlugin extends AbstractJavaClientGeneratorPlugin {

    @Override
    public AbstractJavaClientGenerator getClientGenerator(
            IntrospectedTable introspectedTable) {
        if (AnnotatedClientGeneratorPlugin.TYPE_ANNOTATEDMAPPER.equalsIgnoreCase(getType())) {
            return new SimpleAnnotatedClientGenerator(getProject());
        }
        return null;
    }

}
