package edu.luther.cs252.group1.viewcontroller;

import edu.luther.cs252.group1.model.VirtualMachine252;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.util.concurrent.atomic.AtomicBoolean;

public class ProgramMenuBar extends JMenuBar {
	private final AtomicBoolean paused;
	private final JTextField fileNameField;
	private JTextField runDelayField;
	private Thread threadObject;

	//
	// Constructor
	//
	public ProgramMenuBar(VirtualMachine252 vm252) {

		//
		// Create menu items
		//

		paused = new AtomicBoolean(false);
		JMenu helpMenu = new JMenu("Help");
		JMenu loadMenu = new JMenu("File");
		JMenu runMenu = new JMenu("Run");
		JButton reinitializeButton = new JButton("Z");
		JMenu fileNameLabel = new JMenu("File Name:");

		//
		// TextField, fileNameField's, width in columns is 100.
		//
		fileNameField = new JTextField(100);
		runDelayField = new JTextField("0", 10);

		//
		// Set the text field as read-only
		//

		fileNameField.setEditable(false);


		//
		// Set help tooltips
		//

		helpMenu.setToolTipText("Spawn a help dialog explaining the application");
		loadMenu.setToolTipText("Load a vm252 object file");
		runMenu.setToolTipText("Run program until breakpoint reached");
		reinitializeButton.setToolTipText("Reinitialize the program");
		fileNameLabel.setToolTipText("The name of the loaded vm252 object file");
		runDelayField.setToolTipText("Delay executing each instruction of the program");


		//
		// Add components to the menu bar
		//

		//
		// Adding the Help menu item separated into quick tips and a full command list
		// that appear on button click
		//

		add(helpMenu);
		JMenuItem quick_tips = new JMenuItem("Quick Tip");
		JMenuItem command_list = new JMenuItem("Command List");
		helpMenu.add(quick_tips);
		helpMenu.add(command_list);
		quick_tips.addActionListener(actionEvent -> JOptionPane.showMessageDialog(getRootPane(),
				"To receive fast help for the program you can hover over a component and read the tooltip."
						+ "Otherwise you can view the full command list for all program controls and inputs"));
		command_list.addActionListener(actionEvent -> JOptionPane.showMessageDialog(getRootPane(),
				"""
						Execute Next: Run Next Program Instruction\s

						File/Open: Load a vm252 object file\s

						Run: Run program until breakpoint reached\s

						Run/Pause: Pause the execution of the program\s

						File Name: The name of the loaded vm252 object file\s

						Delay: Delay executing each instruction of the program\s

						PC: View or edit the program counter\s

						ACC: View or edit the accumulator\s

						Next Instruction: View the next instruction that will be ran\s
											    
						Clear Breaks: Clears all breakpoints in the program\s
											    
						Breakpoint Line#: Toggle a breakpoint at selected source line location\s
											    
						Z button: Re-initialize the program\s
											    
						Selected Memory Address: User cursor selected location in memory"""));


		//
		// Adding the load menu item with subsidiary open button to open files on a
		// user's machine using JFileChooser
		//

		add(loadMenu);
		JMenuItem fileMenuItem = new JMenuItem("Open");
		loadMenu.add(fileMenuItem);
		JFileChooser vm252FileChooser = new JFileChooser();

		//
		// Filter the search to look for ".vm252obj" files
		//

		FileNameExtensionFilter vm252ExtensionFilter = new FileNameExtensionFilter(
				"VM252 Object File", "vm252obj"
		);
		vm252FileChooser.setFileFilter(vm252ExtensionFilter);


		// Component for taking a file as input from the user using a dialog box
		fileMenuItem.addActionListener(
				actionEvent -> {
					int returnVal = vm252FileChooser.showOpenDialog(fileMenuItem);
					if (returnVal == JFileChooser.APPROVE_OPTION) {
						String file_name = vm252FileChooser.getSelectedFile().toString();

						//
						// Retrieve the filename of the vm252obj file selected by the user and display it in our
						//      read-only field, fileNameField
						//

						fileNameField.setText(file_name);

						//
						// Load the vm252obj file
						//

						vm252.loadFile(file_name);
					}
				}
		);


		//
		// Add components to the menu bar
		//
		add(fileNameLabel);
		add(fileNameField);
		add(runMenu);
		add(new JLabel("Delay:"));
		add(runDelayField);
		add(reinitializeButton);

		//menu item to run the program, when pressed makes a new thread and runs the program till the lastInstruction is found
		JMenuItem runItem = new JMenuItem("Run");
		JMenuItem pauseItem = new JMenuItem("Pause");
		runItem.addActionListener(
				actionEvent -> {
					Runnable runnable = new Runnable() {
						@Override
						public void run() {
							while (!vm252.isLastInstructionCausedHalt()) {

								//if paused menu item is pressed, the thread waits until an exception occurs
								if (paused.get()) {
									synchronized (threadObject) {
										// Pause
										try {
											threadObject.wait();
										} catch (InterruptedException ignored) {
										}
									}
								}
								//delays the program by reading the input in  **milliseconds** in delay text field, 								//delays the program for as long as the user wants until an exception occurs
								try {
									long delayValue = (Long.parseLong(runDelayField.getText()));
									try {
										// Only delay if delay is above 0
										if (delayValue > 0) {
											System.out.println("sleeping");
											Thread.sleep(delayValue);
											System.out.println("slept for " + delayValue);
										}
									} catch (InterruptedException ex) {
										System.out.println("no delay inserted");
									}
								} catch (NumberFormatException ignored) {
								}

								//runs the next instruction in the object file
								vm252.runNextInstruction();
								if (vm252.isPreviousInstructionHitBreakpoint()) {
									JOptionPane.showMessageDialog(getRootPane(), "Breakpoint at: " + vm252.getProgramCounter());
									if (!paused.get()) {
										pauseItem.setText("Resume");
										paused.set(true);
									} else {
										pauseItem.setText("Pause");
										paused.set(false);

										//resumes the code once start is pressed and the thread is notified.
										synchronized (threadObject) {
											threadObject.notify();
										}
									}
								}
							}

						}
					};
					//initialize a thread and run it
					threadObject = new Thread(runnable);
					threadObject.start();
				}
		);
		runMenu.add(runItem);
		//when pause button is pressed, it changes the name to start and sets itself true
		pauseItem.addActionListener(
				actionEvent -> {
					if (!paused.get()) {
						pauseItem.setText("Resume");
						paused.set(true);
					} else {
						pauseItem.setText("Pause");
						paused.set(false);

						//resumes the code once start is pressed and the thread is notified.
						synchronized (threadObject) {
							threadObject.notify();
						}
					}
				}
		);
		runMenu.add(pauseItem);
		reinitializeButton.addActionListener(
				actionEvent -> {
					vm252.reinitialize();
				}
		);
	}


}
