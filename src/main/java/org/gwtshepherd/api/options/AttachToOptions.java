package org.gwtshepherd.api.options;

import com.google.gwt.dom.client.Element;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

/**
 * 
 * @author Rhuan Hianc
 */

@JsType(isNative = true, namespace = JsPackage.GLOBAL, name = "Object")
public class AttachToOptions {

    @JsProperty
    public native void setElement(Element element);

    @JsProperty
    public native void setOn(String on);
}