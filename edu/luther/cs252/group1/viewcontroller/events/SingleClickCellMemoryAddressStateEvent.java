package edu.luther.cs252.group1.viewcontroller.events;

import edu.luther.cs252.group1.model.VirtualMachine252;
import edu.luther.cs252.group1.viewcontroller.ProgramInfoPanel;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SingleClickCellMemoryAddressStateEvent extends MouseAdapter {

    VirtualMachine252 vm252;
    JTable memoryTable;
    ProgramInfoPanel panel;

    //
    // Constructor
    //

    public SingleClickCellMemoryAddressStateEvent(VirtualMachine252 vm252, JTable memoryTable, ProgramInfoPanel panel) {

        this.vm252 = vm252;
        this.memoryTable = memoryTable;
        this.panel = panel;

    }


    //
    // Public Instance Method void mouseClicked(MouseEvent event)
    //
    // Purpose:
    //     Change the info panel memory address to the clicked cell
    //
    // Worst-case asymptotic runtime:
    //     O(1)
    //
    @Override
    public void mousePressed(MouseEvent event) {
        super.mouseClicked(event);
        if (event.getButton() == MouseEvent.BUTTON1) {
            int row = memoryTable.rowAtPoint(event.getPoint());
            int column = memoryTable.columnAtPoint(event.getPoint());
            panel.getSelectedCellMemmoryAddressLabel().setText(String.valueOf((row * memoryTable.getColumnCount()) + column));
        }
    }

}
