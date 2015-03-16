package com.kingdee.eas.fdc.tenancy.client;

import java.awt.event.ActionEvent;

import com.kingdee.eas.fdc.propertymgmt.RoomChargeCollection;
import com.kingdee.eas.fdc.propertymgmt.client.RoomChargeBatchAddUI;

public class TenRoomChargeBatchAddUI extends RoomChargeBatchAddUI {

	public TenRoomChargeBatchAddUI() throws Exception {
		super();
	}
	
	public void onLoad() throws Exception {
		super.onLoad();
	}
	
	protected void btnSure_actionPerformed(ActionEvent e) throws Exception {
//		super.btnSure_actionPerformed(e);
		
		//TODO
		this.destroyWindow();
	}

	public RoomChargeCollection getRoomCharges() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
