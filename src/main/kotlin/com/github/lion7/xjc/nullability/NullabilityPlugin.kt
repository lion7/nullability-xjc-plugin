package com.github.lion7.xjc.nullability

import com.sun.tools.xjc.Options
import com.sun.tools.xjc.Plugin
import com.sun.tools.xjc.outline.FieldOutline
import com.sun.tools.xjc.outline.Outline
import org.glassfish.jaxb.core.v2.model.core.AttributePropertyInfo
import org.glassfish.jaxb.core.v2.model.core.ElementPropertyInfo
import org.xml.sax.ErrorHandler

class NullabilityPlugin : Plugin() {
    override fun getOptionName(): String = "Xnullability"
    override fun getUsage(): String = "-${optionName}: Nullability plugin"
    override fun run(outline: Outline, opt: Options, errorHandler: ErrorHandler): Boolean {
        outline.classes
            .flatMap { it.declaredFields.asList() }
            .forEach {
                val propertyInfo = it.propertyInfo
                val nonNull = propertyInfo.isCollection ||
                        (propertyInfo is ElementPropertyInfo<*, *> && propertyInfo.isRequired) ||
                        (propertyInfo is AttributePropertyInfo<*, *> && propertyInfo.isRequired)
                val annotation = if (nonNull) org.jetbrains.annotations.NotNull::class.java else org.jetbrains.annotations.Nullable::class.java
                it.getter().annotate(annotation)
            }
        return true
    }

    private fun FieldOutline.getter() =
        parent().implClass.getMethod("get" + propertyInfo.getName(true), emptyArray())
}
