/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.*;
import org.apache.log4j.Logger;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.fdc.sellhouse.RoomEyeshotFactory;
import com.kingdee.eas.framework.*;

/**
 * output class name
 */
public class RoomEyeshotListUI extends AbstractRoomEyeshotListUI
{
    private static final Logger logger = CoreUIObject.getLogger(RoomEyeshotListUI.class);
    
    /**
     * output class constructor
     */
    public RoomEyeshotListUI() throws Exception
    {
        super();
    }

	protected ICoreBase getBizInterface() throws Exception {
		// TODO Auto-generated method stub
		return  RoomEyeshotFactory.getRemoteInstance();
	}

	protected String getEditUIName() {
		// TODO Auto-generated method stub
		return RoomEyeshotEditUI.class.getName();
	}
	

   

}