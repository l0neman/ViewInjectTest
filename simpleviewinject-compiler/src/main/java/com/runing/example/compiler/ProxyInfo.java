package com.runing.example.compiler;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeVariableName;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.Parameterizable;
import javax.lang.model.element.TypeElement;

/**
 * Created by runing on 2016/6/18.
 * <p>
 * This file is part of ViewInjectTest.
 * ViewInjectTest is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * ViewInjectTest is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with ViewInjectTest.  If not, see <http://www.gnu.org/licenses/>.
 * 处理代理
 */
final class ProxyInfo {
    private static final String PROXY = "Proxy";
    /**
     * 包名
     */
    private String packageName;
    /**
     * 目标类名
     */
    private String targetClassName;
    /**
     * 代理类名
     */
    private String proxyClassName;
    /**
     * id到View信息映射
     */
    private Map<Integer, ViewInfo> idViewMap = new LinkedHashMap<>();

    ProxyInfo(String packageName, String targetClassName) {
        this.packageName = packageName;
        this.targetClassName = targetClassName;
        /* TargetClassName$$Proxy */
        this.proxyClassName = targetClassName + "$$" + PROXY;
    }

    void putViewInfo(int id, ViewInfo viewInfo) {
        idViewMap.put(id, viewInfo);
    }

    /**
     * 获取目标类名
     */
    private String getTargetClassName() {
        return targetClassName.replace("$", ".");
    }

    /**
     * 生成Java代码
     */
    void generateJavaCode(ProcessingEnvironment processingEnv) throws IOException {
        final ClassName FINDER_STRATEGY =
                ClassName.get("com.runing.example.simpleviewinject_api", "FindStrategy");
        final ClassName ABSTRACT_INJECTOR =
                ClassName.get("com.runing.example.simpleviewinject_api", "AbstractInjector");
        final TypeName T = TypeVariableName.get("T");

        /*生成方法*/
        MethodSpec.Builder builder = MethodSpec.methodBuilder("inject")
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .returns(void.class)
                .addAnnotation(Override.class)
                .addParameter(FINDER_STRATEGY, "finder", Modifier.FINAL)
                .addParameter(T, "target", Modifier.FINAL)
                .addParameter(TypeName.OBJECT, "source");

        for (Map.Entry<Integer, ViewInfo> viewInfoEntry : idViewMap.entrySet()) {
            ViewInfo info = viewInfoEntry.getValue();
            builder.addStatement("target.$L = finder.findViewById(source, $L)",
                    info.getName(), String.valueOf(info.getId()));
        }
        MethodSpec inject = builder.build();

        /*生成类*/
        String className = proxyClassName;
        TypeSpec proxyClass = TypeSpec.classBuilder(className)
                .addTypeVariable(TypeVariableName.get("T extends " + getTargetClassName()))
                .addSuperinterface(ParameterizedTypeName.get(ABSTRACT_INJECTOR, T))
                .addMethod(inject)
                .build();
        JavaFile javaFile = JavaFile.builder(packageName, proxyClass).build();
        javaFile.writeTo(processingEnv.getFiler());
    }
}
