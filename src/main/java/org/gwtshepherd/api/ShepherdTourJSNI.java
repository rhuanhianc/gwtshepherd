package org.gwtshepherd.api;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.ScriptInjector;
import com.google.gwt.dom.client.StyleInjector;

/**
 * Wrapper parcial para a biblioteca Shepherd em GWT.
 * 
 * @author Rhuan Hianc
 */
public class ShepherdTourJSNI {
    private static JavaScriptObject tour = createTour();

    /**
     * Cria um objeto tour com opções padrão.
     *
     * @return O objeto JavaScript do tour.
     */
    private static native JavaScriptObject createTour() /*-{
        return new $wnd.Shepherd.Tour({
            defaultStepOptions: {
                cancelIcon: {
                    enabled: true
                },
                scrollTo: { behavior: 'smooth', block: 'center' },
                classes: 'shepherd-theme-arrows',
                tippyOptions: { duration: [300, 200], zIndex: 99999 }
            },
            useModalOverlay: true
        });
    }-*/;

    /**
     * Inicia o tour.
     */
    public static native void start() /*-{
        @obras.client.util.ShepherdTour::tour.start();
    }-*/;

    /**
     * Adiciona um passo ao tour.
     *
     * @param tid       O identificador do elemento alvo.
     * @param title     O título do passo.
     * @param text      O texto do passo.
     * @param position  A posição do passo em relação ao elemento alvo.
     */
    public static native void addStep(String tid, String title, String text, String position) /*-{
        if (position === null || position === undefined || position === '') {
            position = 'auto';
        }
        var step = {
            id: tid,
            attachTo: { element: $doc.querySelector('[tid="' + tid + '"]'), on: position },
            title: title,
            text: text,
            buttons: [
                {
                    text: 'Voltar',
                    action: function () {
                        @obras.client.util.ShepherdTour::tour.back();
                    },
                    classes: 'shepherd-button-secondary'
                },
                {
                    text: 'Próximo',
                    action: function () {
                        @obras.client.util.ShepherdTour::tour.next();
                    },
                    classes: 'shepherd-button-primary'
                }
            ]
        };

        @obras.client.util.ShepherdTour::tour.addStep(step);
    }-*/;

    /**
     * Adiciona um passo com campo de entrada ao tour.
     *
     * @param tid    O identificador do elemento alvo.
     * @param title  O título do passo.
     * @param text   O texto do passo.
     */
    public static native void addInputStep(String tid, String title, String text) /*-{
        var step = {
            id: tid,
            attachTo: { element: $doc.querySelector('[tid="' + tid + '"]'), on: 'auto' },
            title: title,
            text: text,
            buttons: [
                {
                    text: 'Voltar',
                    action: function () {
                        @obras.client.util.ShepherdTour::tour.back();
                    },
                    classes: 'shepherd-button-secondary'
                },
                {
                    text: 'Próximo',
                    action: function () {
                        var inputElement = $doc.querySelector('[tid="' + tid + '"] input');
                        if (inputElement && inputElement.value !== '') {
                            @obras.client.util.ShepherdTour::tour.next();
                        }
                    },
                    classes: 'shepherd-button-primary'
                }
            ]
        };

        @obras.client.util.ShepherdTour::tour.addStep(step);

        var inputElement = $doc.querySelector('[tid="' + tid + '"] input');
        if (inputElement) {
            inputElement.addEventListener('change', function () {
                if (inputElement.value === expectedInput) {
                    @obras.client.util.ShepherdTour::tour.next();
                }
            });
        }
    }-*/;


    public static void clearSteps() {
        tour = createTour();
    }
    static {
        ScriptInjector.fromUrl(GWT.getModuleBaseForStaticFiles() + "public/assets/js/shepherd.min.js")
            .setWindow(ScriptInjector.TOP_WINDOW)
            .inject();
    
        StyleInjector.injectAtEnd("@url('public/assets/css/shepherd.css');");
    }
}
