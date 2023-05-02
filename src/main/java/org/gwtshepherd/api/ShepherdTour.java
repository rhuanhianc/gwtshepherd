package org.gwtshepherd.api;
import org.gwtshepherd.api.options.StepOptions;
import org.gwtshepherd.api.options.TourOptions;

import jsinterop.annotations.JsConstructor;
import jsinterop.annotations.JsMethod;
import jsinterop.annotations.JsType;

/**
 * 
 * @author Rhuan Hianc
 */
@JsType(isNative = true, namespace = "Shepherd", name = "Tour")
public class ShepherdTour {

    @JsConstructor
    public ShepherdTour(TourOptions options) {
    }

    @JsMethod
    public native void start();

    @JsMethod
    public native void addStep(StepOptions step);

    @JsMethod
    public native void next();

    @JsMethod
    public native void back();

}
