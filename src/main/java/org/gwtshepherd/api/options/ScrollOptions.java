package org.gwtshepherd.api.options;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

/**
 * 
 * @author Rhuan Hianc
 */

@JsType(isNative = true, namespace = JsPackage.GLOBAL, name = "Object")
public class ScrollOptions {

    @JsProperty
    public native void setBehavior(String behavior);

    @JsProperty
    public native void setBlock(String block);
}