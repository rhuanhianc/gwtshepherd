package org.gwtshepherd.api;

import java.util.ArrayList;
import java.util.List;

import org.gwtshepherd.api.components.button.helper.ButtonHelper;
import org.gwtshepherd.api.options.CancelIconOptions;
import org.gwtshepherd.api.options.ScrollOptions;
import org.gwtshepherd.api.options.StepOptions;
import org.gwtshepherd.api.options.TippyOptions;
import org.gwtshepherd.api.options.TourOptions;

import com.google.gwt.core.client.JsArrayString;

/**
 * Wrapper parcial para a biblioteca Shepherd em GWT.
 * 
 * @author Rhuan Hianc
 */
public class TourManager {
    private ShepherdTour tour;
    private ButtonHelper floatingButtonHelper;
    private List<String> stepTitles = new ArrayList<>();

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

        ScrollOptions scrollOptions = new ScrollOptions();
        scrollOptions.setBehavior("smooth");
        scrollOptions.setBlock("center");

        TippyOptions tippyOptions = new TippyOptions();
        tippyOptions.setDuration(new int[] { 300, 200 });
        tippyOptions.setZIndex(99999);

        defaultStepOptions.setCancelIcon(cancelIconOptions);
        defaultStepOptions.setScrollTo(scrollOptions);
        defaultStepOptions.setClasses("shepherd-theme-arrows");
        defaultStepOptions.setTippyOptions(tippyOptions);

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
     * @param tid      O identificador do elemento alvo.
     * @param title    O título do passo.
     * @param text     O texto do passo.
     * @param position A posição do passo em relação ao elemento alvo.(auto, top,
     *                 bottom, left, right) quando null ou vazio, assume auto.
     */
    public void addStep(String tid, String title, String text, String position) {
        StepOptions stepOptions = new StepOptions();
        stepOptions.setId(tid);
        stepOptions.setAttachTo(buildAttachTo(tid, position));
        stepOptions.setTitle(title);
        stepOptions.setText(text);
        stepOptions.setButtons(buildButtons(tour));

        tour.addStep(stepOptions);
        stepTitles.add(title);
    }

    private String createStepSelectionHtml() {
        StringBuilder stepOptionsHtml = new StringBuilder();
        stepOptionsHtml.append("<div class='step-selection-modal' style='position: fixed; top: 0; left: 0; width: 100%; height: 100%; background-color: rgba(0, 0, 0, 0.8); z-index: 99999;'>");
        stepOptionsHtml.append("<div style='position: absolute; top: 50%; left: 50%; transform: translate(-50%, -50%); background-color: white; padding: 20px; border-radius: 5px; box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);'>");
    
        for (int i = 0; i < stepTitles.size(); i++) {
            stepOptionsHtml.append("<div class='step-option' data-step-index='").append(i + 1).append("' style='cursor: pointer; padding: 10px; border-bottom: 1px solid #eee;'>").append(stepTitles.get(i)).append("</div>");
        }
    
        stepOptionsHtml.append("</div></div>");
    
        return stepOptionsHtml.toString();
    }


    /**
     * Adiciona o passo inicial ao tour.
     * Esse passo contem a opção de iniciar o tour, e o dropdown com os passos.
     *
     * @param tid   O identificador do elemento alvo.
     * @param title O título do passo.
     * @param text  O texto do passo.
     * @param position A posição do passo em relação ao elemento alvo.(auto, top, bottom, left, right) quando null ou vazio, assume auto.
     */
    public void addCustomStartStep(String tid, String title, String text, String position) {
        StepOptions stepOptions = new StepOptions();
        stepOptions.setId(tid);
        stepOptions.setAttachTo(buildAttachTo(tid, position));
        stepOptions.setTitle(title);
        stepOptions.setText(text);
        stepOptions.setButtons(buildCustomStartButtons(tour));

        tour.addStep(stepOptions);
    }

    public static JsArrayString convertListToJsArrayString(List<String> list) {
        JsArrayString jsArray = (JsArrayString) JsArrayString.createArray();
        for (String s : list) {
            jsArray.push(s);
        }
        return jsArray;
    }
    private native int getSelectedStepIndex() /*-{
        var selectElement = $doc.getElementById("tourStepsSelect");
        if (selectElement) {
            return parseInt(selectElement.value);
        }
        return -1;
    }-*/;

    private native void toggleStepSelectionModal() /*-{
        var selectStepModal = $doc.getElementById('step-selection-modal');
        if (selectStepModal.style.display === 'none') {
            selectStepModal.style.display = 'block';
        } else {
            selectStepModal.style.display = 'none';
        }
    }-*/;
    
    

    private native Object[] buildCustomStartButtons(ShepherdTour shepherdTour) /*-{
        var tour = shepherdTour;
        var tourManager = this;
        var stepsTitles = this.@org.gwtshepherd.api.TourManager::stepTitles;
        return [
            {
                text: 'Fazer o tour completo',
                action: function() {
                    tour.next();
                },
                classes: 'shepherd-button-primary'
            },
            {
                text: 'Selecionar etapa',
                action: function() {
                    var selectStepModal = $doc.getElementById('step-selection-modal');
                    if (!selectStepModal) {
                        selectStepModal = $doc.createElement('div');
                        selectStepModal.id = 'step-selection-modal';
                        selectStepModal.innerHTML = tourManager.@org.gwtshepherd.api.TourManager::createStepSelectionHtml()();
                        selectStepModal.style.position = 'fixed';
                        selectStepModal.style.left = '0';
                        selectStepModal.style.top = '0';
                        selectStepModal.style.width = '100%';
                        selectStepModal.style.height = '100%';
                        selectStepModal.style.backgroundColor = 'rgba(0, 0, 0, 0.5)';
                        selectStepModal.style.zIndex = '10000';
                        selectStepModal.style.overflow = 'auto';
                        selectStepModal.style.padding = '100px';
                        selectStepModal.onclick = function(event) {
                            if (event.target && event.target.matches('.step-option')) {
                                var stepIndex = parseInt(event.target.getAttribute('data-step-index'), 10);
                                tour.show(stepIndex);
                                selectStepModal.style.display = 'none';
                            } else if (event.target.id === 'step-selection-modal') {
                                selectStepModal.style.display = 'none';
                            }
                        };
                        $doc.body.appendChild(selectStepModal);
                    }
                    selectStepModal.style.display = 'block';
                },
                classes: 'shepherd-button-secondary'
            }
        ];
    }-*/;
    
    
    
    
    

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
     * @param tid   O identificador do elemento alvo.
     * @param title O título do passo.
     * @param text  O texto do passo.
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

    /**
     * Limpa o tour.
     */
    public void clearTour() {
        ButtonHelper.removeFloatingButton();
        ButtonHelper.removeTooltip();
        stepTitles.clear();
    }

    /**
     * Cria um botão flutuante de ajuda.
     * 
     * @param textTooltip O texto do tooltip.(Texto que vai aparecer quando o botão for criado)
     */
    public void createFloatingButtonHelp(String textTooltip) {
        floatingButtonHelper = new ButtonHelper(this, textTooltip);
    }

    private native Object[] buildInputButtons(String tid, ShepherdTour shepherdTour) /*-{
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
