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

import java.util.List;

import org.mybatis.generator.api.CompositePlugin;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.codegen.AbstractJavaClientGenerator;

public class ClientCompositePlugin extends CompositePlugin {

    protected final List<AbstractJavaClientGeneratorPlugin> generatorPlugins;

    public ClientCompositePlugin(List<AbstractJavaClientGeneratorPlugin> generatorPlugins) {
        for (AbstractJavaClientGeneratorPlugin plugin : generatorPlugins) {
            super.addPlugin(plugin);
        }
        this.generatorPlugins = generatorPlugins;
    }

    public AbstractJavaClientGenerator getClientGenerator(
            IntrospectedTable introspectedTable) {
        AbstractJavaClientGenerator generator = null;
        for (AbstractJavaClientGeneratorPlugin plugin : generatorPlugins) {
            generator = plugin.getGenerator(introspectedTable);
            if (generator != null) {
                break;
            }
        }
        return generator;
    }

    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }

}
