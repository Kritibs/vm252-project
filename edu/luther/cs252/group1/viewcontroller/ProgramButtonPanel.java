package edu.luther.cs252.group1.viewcontroller;

import edu.luther.cs252.group1.model.VirtualMachine252;
import edu.luther.cs252.group1.model.vm252utilities.VM252Utilities;
import edu.luther.cs252.group1.observation.BasicObserver;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class ProgramButtonPanel extends JPanel implements BasicObserver {


	private final VirtualMachine252 vm252;
    private final boolean[] breakpoints;

    //
    // Constructor
    //
    public ProgramButtonPanel(VirtualMachine252 vm252) {
	    this.vm252=vm252;
        byte[] memory = vm252.getMemory();
	    this.breakpoints=vm252.getBreakpoints();

        //
        // We create a vertical box to contain buttons and have them displayed in blocks
        // (one after another) using the Box class (aligned left)
        //

        Box leftControlBox = Box.createVerticalBox();
        // JLabel for breakpoint textfield
        JLabel breakpoint = new JLabel("Breakpoint Line #: ");
        // buttons and text fields
        JButton breakpointClearButton = new JButton("Clear Breaks");
        JButton nextInstructionButton = new JButton("Execute Next");
        JTextField sourceBreakpointLocation = new JTextField(2);

	//
        // Set clear-breakpoint text colour to red for high-visibility
        // Set new-table button to green for aesthetics
        //

        breakpointClearButton.setForeground(Color.RED);
        breakpointClearButton.setOpaque(true);

        //
        // Set custom button sizes for consistency and better visual appearance
        //

        breakpoint.setPreferredSize(new Dimension(100, 20));
        breakpointClearButton.setPreferredSize(new Dimension(100, 20));
        nextInstructionButton.setPreferredSize(new Dimension(100, 20));

        //
        // Set help tooltips
        //
        breakpoint.setToolTipText("Toggle a breakpoint at selected source-line location");
        breakpointClearButton.setToolTipText("Clear breakpoints in all location");
        nextInstructionButton.setToolTipText("Run Next Program Instruction");
        sourceBreakpointLocation.setToolTipText("Source line number containing code where you'd like a breakpoint to be placed");

        //
        // We create a vertical spacer that separates elements for every 45 pixels
        // We utilise the box class that creates invisible components in spaces
        // with the help of createRigidArea as the Box filler
        //
        
        leftControlBox.add(breakpoint);
        leftControlBox.add(sourceBreakpointLocation);
        leftControlBox.add(Box.createRigidArea(new Dimension(0, 25)));
        leftControlBox.add(breakpointClearButton);
        leftControlBox.add(Box.createRigidArea(new Dimension(0, 25)));
        leftControlBox.add(nextInstructionButton);
        leftControlBox.add(Box.createRigidArea(new Dimension(0, 25)));
        add(leftControlBox);

        // Run the next instruction when the button is pressed
	    
        nextInstructionButton.addActionListener(
                actionEvent -> {
                    if (!vm252.isLastInstructionCausedHalt()) {
                        vm252.runNextInstruction();
                        if (vm252.isPreviousInstructionHitBreakpoint())
                            JOptionPane.showMessageDialog(getRootPane(),"Breakpoint at: " + vm252.getProgramCounter());
                    } else {
                        JOptionPane.showMessageDialog(getRootPane(), "REACHED STOP OPCODE");
                    }
                }
        );
	    
	//read the memory location from breakpoint textfield and then assign true to the breakpoint boolean at that location.

        sourceBreakpointLocation.addActionListener(
                actionEvent -> {
                    // Catch NullPointerException if number isn't a valid source line
                    try {
                        Integer sourceLine = VM252Utilities.memorySourceLineHashMap.get(Integer.valueOf(sourceBreakpointLocation.getText()));
                        breakpoints[sourceLine] = !breakpoints[sourceLine];
                    } catch (NullPointerException ignored) {
                        // Do nothing
                    }

                    vm252.announceChange();
		});
	//
	//clear all the breakpoints by setting all the breakpoints boolean false
	    
        breakpointClearButton.addActionListener(
                actionEvent -> {
                    Arrays.fill(breakpoints, false);
                    vm252.announceChange();
                }
        );
    }

    @Override
    public void update() {
    }
}
