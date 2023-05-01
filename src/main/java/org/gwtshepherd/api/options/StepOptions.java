package org.gwtshepherd.api.options;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

/**
 * 
 * @author Rhuan Hianc
 */

@JsType(isNative = true, namespace = JsPackage.GLOBAL, name = "Object")
public class StepOptions {

    @JsProperty
    public native void setId(String id);

    @JsProperty
    public native void setAttachTo(Object attachTo);

    @JsProperty
    public native void setTitle(String title);

    @JsProperty
    public native void setText(String text);

    @JsProperty
    public native void setButtons(Object[] buttons);

    @JsProperty
    public native void setClasses(String classes);

    @JsProperty
    public native void setCancelIcon(CancelIconOptions cancelIcon);
    
    @JsProperty
    public native void setScrollTo(ScrollOptions scrollTo);

    @JsProperty
    public native void setTippyOptions(TippyOptions tippyOptions);
}
