/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.*;
import org.apache.log4j.Logger;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.fdc.sellhouse.RoomEyeshotFactory;
import com.kingdee.eas.fdc.sellhouse.RoomEyeshotInfo;
import com.kingdee.eas.framework.*;

/**
 * output class name
 */
public class RoomEyeshotEditUI extends AbstractRoomEyeshotEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(RoomEyeshotEditUI.class);
    
    /**
     * output class constructor
     */
    public RoomEyeshotEditUI() throws Exception
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

	protected IObjectValue createNewData() {
		RoomEyeshotInfo value=new RoomEyeshotInfo();
		return value;
	}

	protected ICoreBase getBizInterface() throws Exception {
		// TODO Auto-generated method stub
		return RoomEyeshotFactory.getRemoteInstance();
	}



}