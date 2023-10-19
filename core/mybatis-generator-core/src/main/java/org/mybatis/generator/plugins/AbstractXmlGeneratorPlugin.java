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

import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.GeneratedXmlFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.codegen.AbstractJavaClientGenerator;
import org.mybatis.generator.codegen.AbstractXmlGenerator;

public abstract class AbstractXmlGeneratorPlugin extends AbstractGeneratorPlugin {

    private ClientCompositePlugin clientPlugin;

    public AbstractXmlGeneratorPlugin(ClientCompositePlugin clientPlugin) {
        super();
        this.clientPlugin = clientPlugin;
    }

    public ClientCompositePlugin getClientPlugin() {
        return clientPlugin;
    }

    public void setClientPlugin(MyBatis3ClientCompositePlugin clientPlugin) {
        this.clientPlugin = clientPlugin;
    }

    public abstract AbstractXmlGenerator getXmlGenerator(IntrospectedTable introspectedTable);

    @Override
    public List<GeneratedXmlFile> contextGenerateAdditionalXmlFiles(IntrospectedTable introspectedTable) {
        AbstractXmlGenerator generator = getGenerator(introspectedTable);
        if (generator == null) {
            return new ArrayList<>(0);
        }
        initGenerator(generator, introspectedTable);

        List<GeneratedXmlFile> answer = new ArrayList<>();
        Document document = generator.getDocument();
        GeneratedXmlFile gxf = new GeneratedXmlFile(document,
                introspectedTable.getMyBatis3XmlMapperFileName(),
                introspectedTable.getMyBatis3XmlMapperPackage(),
                getProject(), true, context.getXmlFormatter());
        if (context.getPlugins().sqlMapGenerated(gxf, introspectedTable)) {
            answer.add(gxf);
        }
        return answer;
    }

    @Override
    public AbstractXmlGenerator getGenerator(
            IntrospectedTable introspectedTable) {
        AbstractXmlGenerator xmlMapperGenerator;
        if (getClientPlugin() != null) {
            AbstractJavaClientGenerator javaClientGenerator = getClientPlugin().getClientGenerator(introspectedTable);
            if (javaClientGenerator == null) {
                if (context.getSqlMapGeneratorConfiguration() != null) {
                    xmlMapperGenerator = getXmlGenerator(introspectedTable);
                } else {
                    xmlMapperGenerator = null;
                }
            } else {
                xmlMapperGenerator = javaClientGenerator.getMatchedXMLGenerator();
            }
        } else {
            xmlMapperGenerator = getXmlGenerator(introspectedTable);
        }
        return xmlMapperGenerator;
    }

}
