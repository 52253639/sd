package com.kingdee.eas.fdc.market.client;

import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;

import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.Scrollable;
import javax.swing.SwingConstants;

public class XPrintPreviewDialog extends JDialog implements ActionListener {
	private JButton nextButton = new JButton("Next");
	private JButton previousButton = new JButton("Previous");
	private JButton closeButton = new JButton("Close");

	private JPanel buttonPanel = new JPanel();
	private List xPageList;

	private PreviewCanvas canvas;
	JScrollPane xScrollPane;

	public XPrintPreviewDialog(Frame parent, String title, boolean modal,
			Printable pt) {
		super(parent, title, modal);
		XDocument doc = (XDocument)pt;
		xPageList = doc.getXPageList();
		canvas = new PreviewCanvas(pt);
		xScrollPane = new JScrollPane(canvas);
		setLayout();
	}

	private void setLayout() {
		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add(xScrollPane, BorderLayout.CENTER);

		nextButton.setMnemonic('N');
		nextButton.addActionListener(this);
		buttonPanel.add(nextButton);
		previousButton.setMnemonic('N');
		previousButton.addActionListener(this);
		buttonPanel.add(previousButton);
		closeButton.setMnemonic('N');
		closeButton.addActionListener(this);
		buttonPanel.add(closeButton);
		this.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		this.setBounds(0, 0, 692, 867);
	}

	public void actionPerformed(ActionEvent evt) {
		Object src = evt.getSource();
		if (src == nextButton)
			nextAction();
		else if (src == previousButton)
			previousAction();
		else if (src == closeButton)
			closeAction();
	}

	private void closeAction() {
		this.setVisible(false);
		this.dispose();
	}

	private void nextAction() {
		canvas.viewPage(1);
	}

	private void previousAction() {
		canvas.viewPage(-1);
	}

	class PreviewCanvas extends JPanel implements Scrollable {
		private int currentPage = 0;
		private Printable preview;

		public PreviewCanvas(Printable pt) {
			preview = pt;
		}

		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D) g;
			//PageFormat pf = PrinterJob.getPrinterJob().defaultPage();
			PageFormat pf = new PageFormat();
			Paper paper = new Paper();
			paper.setSize(680, 800);
			pf.setPaper(paper);
		
//			double xoff;
//			double yoff;
			double scale;
			double px = pf.getWidth();
			double py = pf.getHeight();
			double sx = getWidth() - 1;
			double sy = getHeight() - 1;
			if (px / py < sx / sy) {
				scale = sy / py;
//				xoff = 0.5 * (sx - scale * px);
//				yoff = 0;
			} else {
				scale = sx / px;
//				xoff = 0;
//				yoff = 0.5 * (sy - scale * py);
			}
			
			g2.translate(0, 50 - 685 * currentPage);
			g2.scale((float) scale, (float) scale);

/*			Rectangle2D whitePage = new Rectangle2D.Double(0, 0, px, py);
			g2.setPaint(Color.white);
			g2.fill(whitePage);
			g2.draw(whitePage);*/
			
			
			try {
				preview.print(g2, pf, currentPage);
			} catch (PrinterException pe) {
				g2.draw(new Line2D.Double(0, 0, px, py));
				g2.draw(new Line2D.Double(0, px, 0, py));
			}
		}

		public void viewPage(int pos) {
			int newPage = currentPage + pos;
			if (0 <= newPage && newPage < xPageList.size()) { //
				currentPage = newPage;
				repaint();
			}
		}
		   /**
	     * Returns the preferred size of the viewport for a view component.
	     * This is implemented to do the default behavior of returning
	     * the preferred size of the component.
	     * 
	     * @return the <code>preferredSize</code> of a <code>JViewport</code>
	     * whose view is this <code>Scrollable</code>
	     */
	    public Dimension getPreferredScrollableViewportSize() {
	        return getPreferredSize();
	    }


	    /**
	     * Components that display logical rows or columns should compute
	     * the scroll increment that will completely expose one new row
	     * or column, depending on the value of orientation.  Ideally, 
	     * components should handle a partially exposed row or column by 
	     * returning the distance required to completely expose the item.
	     * <p>
	     * The default implementation of this is to simply return 10% of
	     * the visible area.  Subclasses are likely to be able to provide
	     * a much more reasonable value.
	     * 
	     * @param visibleRect the view area visible within the viewport
	     * @param orientation either <code>SwingConstants.VERTICAL</code> or
	     *   <code>SwingConstants.HORIZONTAL</code>
	     * @param direction less than zero to scroll up/left, greater than
	     *   zero for down/right
	     * @return the "unit" increment for scrolling in the specified direction
	     * @exception IllegalArgumentException for an invalid orientation
	     * @see JScrollBar#setUnitIncrement
	     */
	    public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {
	        switch(orientation) {
	        case SwingConstants.VERTICAL:
	            return visibleRect.height / 10;
	        case SwingConstants.HORIZONTAL:
	            return visibleRect.width / 10;
	        default:
	            throw new IllegalArgumentException("Invalid orientation: " + orientation);
	        }
	    }


	    /**
	     * Components that display logical rows or columns should compute
	     * the scroll increment that will completely expose one block
	     * of rows or columns, depending on the value of orientation. 
	     * <p>
	     * The default implementation of this is to simply return the visible
	     * area.  Subclasses will likely be able to provide a much more 
	     * reasonable value.
	     * 
	     * @param visibleRect the view area visible within the viewport
	     * @param orientation either <code>SwingConstants.VERTICAL</code> or
	     *   <code>SwingConstants.HORIZONTAL</code>
	     * @param direction less than zero to scroll up/left, greater than zero
	     *  for down/right
	     * @return the "block" increment for scrolling in the specified direction
	     * @exception IllegalArgumentException for an invalid orientation
	     * @see JScrollBar#setBlockIncrement
	     */
	    public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) {
	        switch(orientation) {
	        case SwingConstants.VERTICAL:
	            return visibleRect.height;
	        case SwingConstants.HORIZONTAL:
	            return visibleRect.width;
	        default:
	            throw new IllegalArgumentException("Invalid orientation: " + orientation);
	        }
	    }  
	    

	    /**
	     * Returns true if a viewport should always force the width of this 
	     * <code>Scrollable</code> to match the width of the viewport.
	     * For example a normal text view that supported line wrapping
	     * would return true here, since it would be undesirable for
	     * wrapped lines to disappear beyond the right
	     * edge of the viewport.  Note that returning true for a
	     * <code>Scrollable</code> whose ancestor is a <code>JScrollPane</code>
	     * effectively disables horizontal scrolling.
	     * <p>
	     * Scrolling containers, like <code>JViewport</code>,
	     * will use this method each time they are validated.  
	     * 
	     * @return true if a viewport should force the <code>Scrollable</code>s
	     *   width to match its own
	     */
	    public boolean getScrollableTracksViewportWidth() {
		if (getParent() instanceof JViewport) {
		    return (((JViewport)getParent()).getWidth() > getPreferredSize().width);
		}
		return false;
	    }

	    /**
	     * Returns true if a viewport should always force the height of this 
	     * <code>Scrollable</code> to match the height of the viewport.
	     * For example a columnar text view that flowed text in left to
	     * right columns could effectively disable vertical scrolling by 
	     * returning true here.
	     * <p>
	     * Scrolling containers, like <code>JViewport</code>,
	     * will use this method each time they are validated.  
	     * 
	     * @return true if a viewport should force the Scrollables height
	     *   to match its own
	     */
	    public boolean getScrollableTracksViewportHeight() {
		if (getParent() instanceof JViewport) {
		    return (((JViewport)getParent()).getHeight() > getPreferredSize().height);
		}
		return false;
	    }	
	}

}
