package org.springframework.samples.petclinic.ui.util;

import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.formlayout.FormLayout.ResponsiveStep;
import com.vaadin.flow.component.formlayout.FormLayout.ResponsiveStep.LabelsPosition;

public class FormUtil {

    public static void singleColumn(FormLayout form) {
        form.setResponsiveSteps(new ResponsiveStep("0", 1, LabelsPosition.TOP),
                new ResponsiveStep("600px", 1, LabelsPosition.ASIDE));
    }

}
