package org.gwtshepherd.api.components.button.helper;

import org.gwtshepherd.api.TourManager;

/**
 * 
 * @author Rhuan Hianc
 */
public class ButtonHelper {

    public ButtonHelper(TourManager tourManagerInstance, String textTooltip) {
        createFloatingButton(tourManagerInstance);
        createTooltip();
        showTooltip(10000,textTooltip); // Exibe o tooltip por 10 segundos
    }

    
     public native static void removeFloatingButton() /*-{
        var button = $doc.getElementById("floating-helper-button");
        if (button) {
            button.remove();
        }
    }-*/;
    
    public native static void removeTooltip() /*-{
        var tooltipContainer = $doc.getElementById("tooltip-helper-container");
        if (tooltipContainer) {
            tooltipContainer.remove();
        }
    }-*/;

    private native void createFloatingButton(TourManager tourManagerInstance) /*-{
        var button = $doc.createElement("div");
        button.id = "floating-helper-button";
        button.className = "floating-helper-button";
        button.innerHTML = "<span>?</span>";
        $doc.body.appendChild(button);
        button.addEventListener("click", function() {
        tourManagerInstance.@TourManager::start()();
    });
    }-*/;

    private native void createTooltip() /*-{
        var tooltip = $doc.createElement("div");
        tooltip.id = "tooltip-helper-container";
        tooltip.className = "tooltip-helper-container";
        $doc.body.appendChild(tooltip);
    }-*/;

    // private native void toggleTooltip() /*-{
    //     var tooltipContainer = $doc.getElementById("tooltip-helper-container");
    //     if (tooltipContainer === null) {
    //         return;
    //     }
    //     if (tooltipContainer.style.display === "none" || tooltipContainer.style.display === "") {
    //         tooltipContainer.style.display = "block";
    //         // Atualize o conteúdo do tooltip conforme necessário
    //         tooltipContainer.innerHTML = "Conteúdo do aviso flutuante (tooltip) aqui";
    //     } else {
    //         tooltipContainer.style.display = "none";
    //     }
    // }-*/;
    private native void showTooltip(int timeout, String textTooltip) /*-{
        var tooltipContainer = $doc.getElementById("tooltip-helper-container");
        if (tooltipContainer === null) {
            return;
        }
    
        tooltipContainer.style.display = "block";
        // Atualize o conteúdo do tooltip conforme necessário
        tooltipContainer.innerHTML = textTooltip;
    
        // Remova o tooltip após o intervalo especificado
        setTimeout(function() {
            tooltipContainer.style.display = "none";
        }, timeout); // Tempo em milissegundos
    }-*/;
    

}
