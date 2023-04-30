package org.gwtshepherd.api.options;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

/**
 * 
 * @author Rhuan Hianc
 */

@JsType(isNative = true, namespace = JsPackage.GLOBAL, name = "Object")
public class TourOptions {

    @JsProperty
    public native void setDefaultStepOptions(StepOptions options);

    @JsProperty
    public native void setUseModalOverlay(boolean useModalOverlay);
    
}
