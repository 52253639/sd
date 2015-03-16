/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.*;

import javax.swing.plaf.basic.BasicBorders;

import org.apache.log4j.Logger;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.framework.*;

/**
 * output class name
 */
public class TestDraggerUI extends AbstractTestDraggerUI
{
    private static final Logger logger = CoreUIObject.getLogger(TestDraggerUI.class);
    
    /**
     * output class constructor
     */
    public TestDraggerUI() throws Exception
    {
        super();
    }

    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }


    int x = 0;
    int y = 0;
    public void actionPopupPaste_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPopupPaste_actionPerformed(e);
    }
    public void onLoad() throws Exception {
    	super.onLoad();
    	this.labTest.setBorder(BasicBorders.getButtonBorder());
    	this.labTest.addMouseListener(new MouseListener(){
			public void mouseClicked(MouseEvent e) {
			}
			public void mouseEntered(MouseEvent e) {
			}
			public void mouseExited(MouseEvent e) {
			}
			//Ãªµã
			public void mousePressed(MouseEvent e) {
				x = e.getX();
				y = e.getY();
			}
			public void mouseReleased(MouseEvent e) {
			}
    	});
    	
    	this.labTest.addMouseMotionListener(new MouseMotionListener(){
			public void mouseDragged(MouseEvent e) {
				int diffX = e.getX() -x;
				int diffY = e.getY() -y;
				labTest.setBounds(labTest.getX()+diffX, labTest.getY()+diffY,labTest.getWidth(),labTest.getHeight());
			}
			public void mouseMoved(MouseEvent e) {
			}
    	});
    }
}