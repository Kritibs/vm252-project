package edu.luther.cs252.group1.viewcontroller.events;

import edu.luther.cs252.group1.model.VirtualMachine252;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MiddleClickBreakpointEvent extends MouseAdapter {

    VirtualMachine252 vm252;
    JTable memoryTable;
    JPanel panel;

    //
    // Constructor
    //

    public MiddleClickBreakpointEvent(VirtualMachine252 vm252, JTable memoryTable) {

        this.vm252 = vm252;
        this.memoryTable = memoryTable;

    }


    //
    // Public Instance Method void mouseClicked(MouseEvent event)
    //
    // Purpose:
    //     Create a breakpoint at the location in memory when clicked with the middle mouse button
    //
    // Worst-case asymptotic runtime:
    //     O(1)
    //
    @Override
    public void mouseClicked(MouseEvent event) {
        super.mouseClicked(event);
        if (event.getButton() == MouseEvent.BUTTON3) {
            int row = memoryTable.rowAtPoint(event.getPoint());
            int column = memoryTable.columnAtPoint(event.getPoint());
            boolean[] breakpoints = vm252.getBreakpoints();
            breakpoints[(row * memoryTable.getColumnCount()) + column] = !breakpoints[(row * memoryTable.getColumnCount()) + column];
            vm252.announceChange();
        }
    }

}
