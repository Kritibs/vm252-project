package edu.luther.cs252.group1.viewcontroller;

import edu.luther.cs252.group1.model.VirtualMachine252;
import edu.luther.cs252.group1.observation.BasicObserver;

import javax.swing.*;

public class ProgramInfoPanel extends JPanel implements BasicObserver {

    private final VirtualMachine252 vm252;
    private final JLabel nextInstructionLabel;
    private final JLabel selectedCellMemmoryAddressLabel;

    //
    // Constructor
    //
    public ProgramInfoPanel(VirtualMachine252 vm252) {

        this.vm252 = vm252;

        //
        // Create Input/Output Menu and Text Field
        //
        JLabel nextLabel = new JLabel("Next Instruction:️");
        nextInstructionLabel = new JLabel(vm252.getNextInstruction());

        JLabel selectCellLabel = new JLabel("Selected Memory Address:");
        selectedCellMemmoryAddressLabel = new JLabel("0");

        //
        // Set help tooltips
        //
        nextInstructionLabel.setToolTipText("View the next instruction that will be ran");


        add(selectCellLabel);
        add(selectedCellMemmoryAddressLabel);

        add(nextLabel);
        add(nextInstructionLabel);


    }

    @Override
    public void update() {
        nextInstructionLabel.setText(vm252.getNextInstruction());
    }

    public JLabel getSelectedCellMemmoryAddressLabel() {
        return selectedCellMemmoryAddressLabel;
    }
}
