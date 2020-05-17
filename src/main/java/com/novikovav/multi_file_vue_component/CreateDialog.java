package com.novikovav.multi_file_vue_component;

import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.ValidationInfo;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;

public class CreateDialog extends DialogWrapper {
    private JTextField textField;

    public CreateDialog() {
        super(true);
        init();
        setTitle("Create Multi File Vue Component");
    }

    @Override
    protected @Nullable JComponent createCenterPanel() {
        JPanel dialogPanel = new JPanel(new BorderLayout());

        JLabel label = new JLabel("Create a new multi file Vue component");
        dialogPanel.add(label, BorderLayout.PAGE_START);
        this.textField = new JTextField();
        dialogPanel.add(textField, BorderLayout.PAGE_END);

        return dialogPanel;
    }

    @Override
    public @Nullable JComponent getPreferredFocusedComponent() {
        return this.textField;
    }

    @Override
    protected @Nullable ValidationInfo doValidate() {
        if (this.textField.getText().isEmpty()) {
            return new ValidationInfo("File name should not be empty", this.textField);
        }
        return null;
    }

    public String getFileName() {
        return textField.getText();
    }
}
