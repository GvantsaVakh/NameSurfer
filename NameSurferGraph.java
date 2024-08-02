
/*
 * File: NameSurferGraph.java
 * ---------------------------
 * This class represents the canvas on which the graph of
 * names is drawn. This class is responsible for updating
 * (redrawing) the graphs whenever the list of entries changes or the window is resized.
 */

import acm.graphics.*;
import java.awt.event.*;
import java.util.*;
import java.awt.*;

public class NameSurferGraph extends GCanvas implements NameSurferConstants, ComponentListener {
	private double intervalY;
	private double intervalX;
	private double lastY;
	private double newY;
	private Color color;
	private ArrayList<NameSurferEntry> entrysList = new ArrayList<NameSurferEntry>();

	/**
	 * Creates a new NameSurferGraph object that displays the data.
	 */
	public NameSurferGraph() {
		addComponentListener(this);
		// You fill in the rest //
	}

	/**
	 * Clears the list of name surfer entries stored inside this class.
	 */
	public void clear() {
		entrysList.clear();

	}

	/* Method: addEntry(entry) */
	/**
	 * Adds a new NameSurferEntry to the list of entries on the display. Note
	 * that this method does not actually draw the graph, but simply stores the
	 * entry; the graph is drawn by calling update.
	 */
	public void addEntry(NameSurferEntry entry) {
		for(NameSurferEntry x : entrysList){
			if(x.getName().equals(entry.getName())) return;
		}
		entrysList.add(entry);
	}

	/**
	 * Updates the display image by deleting all the graphical objects from the
	 * canvas and then reassembling the display according to the list of
	 * entries. Your application must call update after calling either clear or
	 * addEntry; update is also called whenever the size of the canvas changes.
	 */
	public void update() {
		// divides height into intervals
		intervalY = (double) (getHeight() - 2 * GRAPH_MARGIN_SIZE) / MAX_RANK;

		// divides width into intervals
		intervalX = (double) getWidth() / NDECADES;
		removeAll();
		addBackground();

		// adds every entry in the entryslist to the canvas
		for (int n = 0; n < entrysList.size(); n++) {
			NameSurferEntry entry = entrysList.get(n);

			// changes color for different entries
			setColor(n);

			// gives value to lastY and adds the first label
			setLastY(entry);
			for (int i = 1; i < NDECADES; i++) {
				if (entry.getRank(i) == 0) {
					newY = getHeight() - GRAPH_MARGIN_SIZE;
					addLabels(entry.getName() + "*", i * intervalX, newY);
				} else {
					newY = GRAPH_MARGIN_SIZE + entry.getRank(i) * intervalY;
					addLabels(entry.getName() + " " + entry.getRank(i), i * intervalX, newY);
				}
				addLine((i - 1) * intervalX, lastY, i * intervalX, newY, color);

				// lastY is changed so that new line starts from this coordinant
				lastY = newY;
			}
		}
	}

	// gives lastY the value according to the first rank of the entry
	private void setLastY(NameSurferEntry entry) {
		if (entry.getRank(0) == 0) {
			lastY = getHeight() - GRAPH_MARGIN_SIZE;
			addLabels(entry.getName() + "*", 0, lastY);
		} else {
			lastY = GRAPH_MARGIN_SIZE + entry.getRank(0) * intervalY;
			addLabels(entry.getName() + " " + entry.getRank(0), 0, lastY);
		}
	}

	// chooses color for graph lines
	private void setColor(int n) {
		if (n % 4 == 0) {
			color = Color.RED;
		} else if (n % 4 == 1) {
			color = Color.BLUE;
		} else if (n % 4 == 2) {
			color = Color.GREEN;
		} else if (n % 4 == 3) {
			color = Color.ORANGE;
		}
	}

	// adds background
	private void addBackground() {
		for (int i = 0; i < NDECADES; i++) {
			addLine(i * intervalX, 0, i * intervalX, getHeight(), Color.BLACK);
			addYearsLabels("" + (START_DECADE + i * 10), i * intervalX, getHeight() - GRAPH_MARGIN_SIZE);
		}
		addLine(0, GRAPH_MARGIN_SIZE, getWidth(), GRAPH_MARGIN_SIZE, Color.BLACK);
		addLine(0, getHeight() - GRAPH_MARGIN_SIZE, getWidth(), getHeight() - GRAPH_MARGIN_SIZE, Color.BLACK);
	}

	// adds line in the given coordinants and gives is specific color
	private void addLine(double x1, double y1, double x2, double y2, Color color) {
		GLine line = new GLine(x1, y1, x2, y2);
		line.setColor(color);
		add(line);
	}

	// adds label
	private void addLabels(String str, double x, double y) {
		GLabel label = new GLabel(str);
		label.setColor(color);
		add(label, x + 4, y - 4);
	}

	// adds labels that show decades
	private void addYearsLabels(String str, double x, double y) {
		GLabel label = new GLabel(str);
		add(label, x + 4, y + label.getAscent() + 2);
	}

	/* Implementation of the ComponentListener interface */
	public void componentHidden(ComponentEvent e) {
	}

	public void componentMoved(ComponentEvent e) {
	}

	public void componentResized(ComponentEvent e) {
		update();
	}

	public void componentShown(ComponentEvent e) {
	}
}
