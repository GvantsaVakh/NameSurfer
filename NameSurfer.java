
/*
 * File: NameSurfer.java
 * ---------------------
 * When it is finished, this program will implements the viewer for
 * the baby-name database described in the assignment handout.
 */

import acm.program.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class NameSurfer extends Program implements NameSurferConstants {

	/* Method: init() */
	/**
	 * This method has the responsibility for reading in the data base and
	 * initializing the interactors at the bottom of the window.
	 */
	private JLabel label;
	private JTextField field;
	private JButton graphButton;
	private JButton clearButton;
	private NameSurferGraph graph;
	private NameSurferDataBase base;

	public NameSurfer() {
		// adds graph
		graph = new NameSurferGraph();
		add(graph);
	}

	public void init() {
		
		base = new NameSurferDataBase(NAMES_DATA_FILE);


		label = new JLabel("Name");
		add(label, SOUTH);

		// adds textfield so that the user can search the name
		field = new JTextField(20);
		add(field, SOUTH);

		graphButton = new JButton("graph");
		add(graphButton, SOUTH);

		// clears the graph
		clearButton = new JButton("clear");
		add(clearButton, SOUTH);

		// adds actionlisteners
		field.addActionListener(this);
		graphButton.addActionListener(this);
		clearButton.addActionListener(this);

	}

	/* Method: actionPerformed(e) */
	/**
	 * This class is responsible for detecting when the buttons are clicked, so
	 * you will have to define a method to respond to button actions.
	 */
	public void actionPerformed(ActionEvent e) {
		// this happens when the user presses "enter" or "graph" button
		if (e.getSource() == graphButton || e.getSource() == field) {
			String name = field.getText();
			field.setText("");
			
			// if name is not in the base entry becomes null
			NameSurferEntry entry = base.findEntry(name);
			if (entry != null) {
				graph.addEntry(entry);
			}

		}
		// graph clears when user presses "clear" button
		else if (e.getSource() == clearButton) {
			graph.clear();
		}
		graph.update();
	}
}
