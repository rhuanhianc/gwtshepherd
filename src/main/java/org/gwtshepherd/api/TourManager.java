package org.gwtshepherd.api;

import java.util.ArrayList;
import java.util.List;

import org.gwtshepherd.api.components.button.helper.ButtonHelper;
import org.gwtshepherd.api.options.CancelIconOptions;
import org.gwtshepherd.api.options.ScrollOptions;
import org.gwtshepherd.api.options.StepOptions;
import org.gwtshepherd.api.options.TippyOptions;
import org.gwtshepherd.api.options.TourOptions;

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
     * @param position A posição do passo em relação ao elemento alvo.(auto, top, bottom, left, right) quando null ou vazio, assume auto.
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

    /**
     * Adiciona o passo inicial ao tour.
     * Esse passo contem a opção de iniciar o tour, e o dropdown com os passos.
     *
     * @param tid   O identificador do elemento alvo.
     * @param title O título do passo.
     * @param text  O texto do passo.
     */

    public void addCustomStartStep(String tid, String title, String text) {
        StepOptions stepOptions = new StepOptions();
        stepOptions.setId(tid);
        stepOptions.setAttachTo(buildAttachTo(tid, "auto"));
        stepOptions.setTitle(title);
        stepOptions.setText(text);
        stepOptions.setButtons(buildCustomStartButtons(tid, tour, stepTitles));
    
        tour.addStep(stepOptions);
    }
    private native Object[] buildCustomStartButtons(String tid, ShepherdTour shepherdTour, List<String> stepTitles) /*-{
        var tour = shepherdTour;
        var stepTitlesArray = @org.gwtshepherd.api.TourManager::convertListToJsArrayString(*)(stepTitles);
        
        var stepsDropdown = '<select id="tourStepsSelect">';
        for (var i = 0; i < stepTitlesArray.length; i++) {
            stepsDropdown += '<option value="' + i + '">' + stepTitlesArray[i] + '</option>';
        }
        stepsDropdown += '</select>';
        
        return [
            {
                text: 'Fazer o tour completo',
                action: function() {
                    tour.next();
                },
                classes: 'shepherd-button-primary'
            },
            {
                text: stepsDropdown,
                action: function() {
                    var selectedIndex = $doc.getElementById("tourStepsSelect").value;
                    tour.show(selectedIndex);
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
     */
    public void createFloatingButtonHelp() {
        floatingButtonHelper = new ButtonHelper(this);
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
