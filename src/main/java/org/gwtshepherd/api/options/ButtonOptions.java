package org.gwtshepherd.api.options;

import jsinterop.annotations.JsFunction;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

/**
 * 
 * @author Rhuan Hianc
 */

@JsType(isNative = true, namespace = JsPackage.GLOBAL, name = "Object")
public class ButtonOptions {

    @JsProperty
    public native void setText(String text);

    @JsProperty
    public native void setAction(JsFunction action);

    @JsProperty
    public native void setClasses(String classes);
}