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
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.codegen.AbstractJavaClientGenerator;
import org.mybatis.generator.internal.ObjectFactory;
import org.mybatis.generator.internal.util.StringUtility;

public class CustomJavaClientGeneratorPlugin
        extends AbstractJavaClientGeneratorPlugin {

    public static final Set<String> PREDEFINED_TYPES = Collections.unmodifiableSet(new HashSet<>(Arrays.asList(
            JavaMapperGeneratorPlugin.TYPE_MAPPER,
            JavaMapperGeneratorPlugin.TYPE_XMLMAPPER,
            MixedClientGeneratorPlugin.TYPE_MIXEDMAPPER,
            AnnotatedClientGeneratorPlugin.TYPE_ANNOTATEDMAPPER)));

    @Override
    public AbstractJavaClientGenerator getClientGenerator(
            IntrospectedTable introspectedTable) {
        String type = null;
        if (context.getJavaClientGeneratorConfiguration() != null) {
            type = context.getJavaClientGeneratorConfiguration()
                    .getConfigurationType();
        } else {
            //get type from plugin properties config
            type = properties.getProperty("type");
        }
        if (StringUtility.stringHasValue(type)
                && !PREDEFINED_TYPES.contains(type)) {
            return (AbstractJavaClientGenerator) ObjectFactory
                    .createInternalObject(type);
        }
        return null;
    }

}
