package com.kingdee.eas.fdc.market.client;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.util.Map;

import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JViewport;
import javax.swing.Scrollable;
import javax.swing.SwingConstants;

import com.kingdee.eas.fdc.market.DocumentInfo;
import com.kingdee.eas.fdc.market.DocumentItemInfo;
import com.kingdee.eas.fdc.market.DocumentOptionCollection;
import com.kingdee.eas.fdc.market.DocumentOptionHorizonLayoutEnum;
import com.kingdee.eas.fdc.market.DocumentOptionInfo;

public class XResult extends JPanel implements Scrollable {
	public static Font fontSubject = new Font("Dialog",Font.PLAIN,14);
	public static Font fontItem = new Font("Dialog",Font.PLAIN,12);
	public static Font fontOption = new Font("Dialog",Font.PLAIN,10);
	public static int lineMaxWidth = 300;
	public static int labelMaxWidth = 150;
	public static int lineHeight = 8;
	
	/**
	 * 有效问卷份数
	 */
	private int effectualDoc;
	
	private DocumentInfo doc;
	
	private Map optionId2Content;
	
	public void setEffectualDoc(int effectualDoc) {
		this.effectualDoc = effectualDoc;
	}

	public void setDoc(DocumentInfo doc) {
		this.doc = doc;
	}

	public void setOptionId2Content(Map optionId2Content) {
		this.optionId2Content = optionId2Content;
	}
	
	public void calculateOption(DocumentItemInfo itemInfo,Map optionId2Content){
		DocumentOptionCollection optionCollection = itemInfo.getOptions();
		int totalCount = 0;
		for(int i=0 ; i<optionCollection.size() ; i++){
			DocumentOptionInfo option = optionCollection.get(i);
			Integer count = (Integer)optionId2Content.get(option.getId().toString());
			XJLabel[] xLabels = XCellCommonCreater.createPureLabel(fontOption, option.getTopic(), labelMaxWidth, true, DocumentOptionHorizonLayoutEnum.HORIZONTAL_ALIGN_CENTER_VALUE);
			if(count != null){
				totalCount += count.intValue();
			}
		}
	}
	

	//以下实现接口 Scrollable
	/**
	 * Returns the preferred size of the viewport for a view component. This is
	 * implemented to do the default behavior of returning the preferred size of
	 * the component.
	 * 
	 * @return the <code>preferredSize</code> of a <code>JViewport</code> whose
	 *         view is this <code>Scrollable</code>
	 */
	public Dimension getPreferredScrollableViewportSize() {
		return getPreferredSize();
	}

	/**
	 * Components that display logical rows or columns should compute the scroll
	 * increment that will completely expose one new row or column, depending on
	 * the value of orientation. Ideally, components should handle a partially
	 * exposed row or column by returning the distance required to completely
	 * expose the item.
	 * <p>
	 * The default implementation of this is to simply return 10% of the visible
	 * area. Subclasses are likely to be able to provide a much more reasonable
	 * value.
	 * 
	 * @param visibleRect
	 *            the view area visible within the viewport
	 * @param orientation
	 *            either <code>SwingConstants.VERTICAL</code> or
	 *            <code>SwingConstants.HORIZONTAL</code>
	 * @param direction
	 *            less than zero to scroll up/left, greater than zero for
	 *            down/right
	 * @return the "unit" increment for scrolling in the specified direction
	 * @exception IllegalArgumentException
	 *                for an invalid orientation
	 * @see JScrollBar#setUnitIncrement
	 */
	public int getScrollableUnitIncrement(Rectangle visibleRect,
			int orientation, int direction) {
		switch (orientation) {
		case SwingConstants.VERTICAL:
			return visibleRect.height / 10;
		case SwingConstants.HORIZONTAL:
			return visibleRect.width / 10;
		default:
			throw new IllegalArgumentException("Invalid orientation: "
					+ orientation);
		}
	}

	/**
	 * Components that display logical rows or columns should compute the scroll
	 * increment that will completely expose one block of rows or columns,
	 * depending on the value of orientation.
	 * <p>
	 * The default implementation of this is to simply return the visible area.
	 * Subclasses will likely be able to provide a much more reasonable value.
	 * 
	 * @param visibleRect
	 *            the view area visible within the viewport
	 * @param orientation
	 *            either <code>SwingConstants.VERTICAL</code> or
	 *            <code>SwingConstants.HORIZONTAL</code>
	 * @param direction
	 *            less than zero to scroll up/left, greater than zero for
	 *            down/right
	 * @return the "block" increment for scrolling in the specified direction
	 * @exception IllegalArgumentException
	 *                for an invalid orientation
	 * @see JScrollBar#setBlockIncrement
	 */
	public int getScrollableBlockIncrement(Rectangle visibleRect,
			int orientation, int direction) {
		switch (orientation) {
		case SwingConstants.VERTICAL:
			return visibleRect.height;
		case SwingConstants.HORIZONTAL:
			return visibleRect.width;
		default:
			throw new IllegalArgumentException("Invalid orientation: "
					+ orientation);
		}
	}

	/**
	 * Returns true if a viewport should always force the width of this
	 * <code>Scrollable</code> to match the width of the viewport. For example a
	 * normal text view that supported line wrapping would return true here,
	 * since it would be undesirable for wrapped lines to disappear beyond the
	 * right edge of the viewport. Note that returning true for a
	 * <code>Scrollable</code> whose ancestor is a <code>JScrollPane</code>
	 * effectively disables horizontal scrolling.
	 * <p>
	 * Scrolling containers, like <code>JViewport</code>, will use this method
	 * each time they are validated.
	 * 
	 * @return true if a viewport should force the <code>Scrollable</code>s
	 *         width to match its own
	 */
	public boolean getScrollableTracksViewportWidth() {
		if (getParent() instanceof JViewport) {
			return (((JViewport) getParent()).getWidth() > getPreferredSize().width);
		}
		return false;
	}

	/**
	 * Returns true if a viewport should always force the height of this
	 * <code>Scrollable</code> to match the height of the viewport. For example
	 * a columnar text view that flowed text in left to right columns could
	 * effectively disable vertical scrolling by returning true here.
	 * <p>
	 * Scrolling containers, like <code>JViewport</code>, will use this method
	 * each time they are validated.
	 * 
	 * @return true if a viewport should force the Scrollables height to match
	 *         its own
	 */
	public boolean getScrollableTracksViewportHeight() {
		if (getParent() instanceof JViewport) {
			return (((JViewport) getParent()).getHeight() > getPreferredSize().height);
		}
		return false;
	}
}
