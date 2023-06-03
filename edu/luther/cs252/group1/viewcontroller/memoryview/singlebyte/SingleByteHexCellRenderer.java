package edu.luther.cs252.group1.viewcontroller.memoryview.singlebyte;


import edu.luther.cs252.group1.model.VirtualMachine252;
import edu.luther.cs252.group1.viewcontroller.memoryview.MemoryTableCellRenderer;

import javax.swing.*;
import java.awt.*;

public class SingleByteHexCellRenderer extends MemoryTableCellRenderer  {

    private VirtualMachine252 vm252;
    private SingleByteHexTableModel vm252TableModel;

    //
    // Constructor
    //

    //
    // Public Constructor CustomTableCellRenderer()
    //
    // Purpose:
    //     Cell renderer to have cell text be center aligned
    //
    // Formals:
    //     none
    //
    // Pre-conditions:
    //     none
    //
    // Post-conditions:
    //     none
    //
    // Returns:
    //     Void
    //
    // Worst-case asymptotic runtime:
    //     O(1)
    //
    public SingleByteHexCellRenderer(VirtualMachine252 vm252, SingleByteHexTableModel vm252TableModel) {
        super();
        this.vm252 = vm252;
        this.vm252TableModel = vm252TableModel;
        // Center align
        this.setHorizontalAlignment(0);
        //this.setBackground(Color.LIGHT_GRAY);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component currentCell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        // If current cell being rendered matches program counter location indicate with a yellow background
        try {
            if (((row * vm252TableModel.getColumnCount()) + column) == vm252.getProgramCounter()) {
                currentCell.setForeground(Color.GREEN);
            } else
                currentCell.setForeground(Color.BLACK);

            if (vm252.getBreakpoints()[((row * vm252TableModel.getColumnCount()) + column)])
                if (((row * vm252TableModel.getColumnCount()) + column) == vm252.getProgramCounter())
                    currentCell.setForeground(Color.PINK);
                else
                    currentCell.setForeground(Color.RED);
        } catch (ArrayIndexOutOfBoundsException exception) {
            // Do nothing
        }

        return currentCell;
    }
}
