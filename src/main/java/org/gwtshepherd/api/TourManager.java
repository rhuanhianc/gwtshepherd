package org.gwtshepherd.api;

import org.gwtshepherd.api.options.CancelIconOptions;
import org.gwtshepherd.api.options.StepOptions;
import org.gwtshepherd.api.options.TourOptions;

/**
 * Wrapper parcial para a biblioteca Shepherd em GWT.
 * 
 * @author Rhuan Hianc
 */
public class TourManager {
    private ShepherdTour tour;

    /**
     * Cria um objeto tour com opções padrão.
     *
     * @return O objeto JavaScript do tour.
     */
    public TourManager() {
        TourOptions tourOptions = new TourOptions();
        StepOptions defaultStepOptions = new StepOptions();
        CancelIconOptions cancelIconOptions = new CancelIconOptions();
        cancelIconOptions.setEnabled(true);
       defaultStepOptions.setCancelIcon(cancelIconOptions);
       // defaultStepOptions.setScrollTo(new ScrollOptions("smooth", "center"));
        defaultStepOptions.setClasses("shepherd-theme-arrows");
      //  defaultStepOptions.setTippyOptions(new TippyOptions(300, 200, 99999));

        tourOptions.setDefaultStepOptions(defaultStepOptions);
        tourOptions.setUseModalOverlay(true);

        tour = new ShepherdTour(tourOptions);
    }

    public void start() {
        tour.start();
    }

    /**
     * Adiciona um passo ao tour.
     *
     * @param tid       O identificador do elemento alvo.
     * @param title     O título do passo.
     * @param text      O texto do passo.
     * @param position  A posição do passo em relação ao elemento alvo.
     */
    public void addStep(String tid, String title, String text, String position) {
        StepOptions stepOptions = new StepOptions();
        stepOptions.setId(tid);
        stepOptions.setAttachTo(buildAttachTo(tid, position));
        stepOptions.setTitle(title);
        stepOptions.setText(text);
        stepOptions.setButtons(buildButtons(tour));

        tour.addStep(stepOptions);
    }

    private native Object buildAttachTo(String tid, String position) /*-{
        return {
            element: $doc.querySelector('[tid="' + tid + '"]'),
            on: position || 'auto'
        };
    }-*/;

    private native Object[] buildButtons(ShepherdTour shepherdTour) /*-{
        var tour = shepherdTour;
        return [
            {
                text: 'Voltar',
                action: function() {
                    tour.back();
                },
                classes: 'shepherd-button-secondary'
            },
            {
                text: 'Próximo',
                action: function() {
                    tour.next();
                },
                classes: 'shepherd-button-primary'
            }
        ];
    }-*/;

    /**
     * Adiciona um passo com campo de entrada ao tour.
     *
     * @param tid    O identificador do elemento alvo.
     * @param title  O título do passo.
     * @param text   O texto do passo.
     */
    public void addInputStep(String tid, String title, String text) {
        StepOptions stepOptions = new StepOptions();
        stepOptions.setId(tid);
        stepOptions.setAttachTo(buildAttachTo(tid, "auto"));
        stepOptions.setTitle(title);
        stepOptions.setText(text);
        stepOptions.setButtons(buildInputButtons(tid, tour));
    
        tour.addStep(stepOptions);
    }
    
    private native Object[] buildInputButtons(String tid,ShepherdTour shepherdTour) /*-{
        var tour = shepherdTour;
        return [
            {
                text: 'Voltar',
                action: function () {
                    tour.back();
                },
                classes: 'shepherd-button-secondary'
            },
            {
                text: 'Próximo',
                action: function () {
                    var inputElement = $doc.querySelector('[tid="' + tid + '"] input');
                    console.log(inputElement);
                    tour.next();
                },
                classes: 'shepherd-button-primary'
            }
        ];
    }-*/;

}
