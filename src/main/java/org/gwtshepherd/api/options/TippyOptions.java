package org.gwtshepherd.api.options;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

/**
 * 
 * @author Rhuan Hianc
 */

@JsType(isNative = true, namespace = JsPackage.GLOBAL, name = "Object")
public class TippyOptions {

    @JsProperty
    public native void setDuration(int[] duration);

    @JsProperty
    public native void setZIndex(int zIndex);
}