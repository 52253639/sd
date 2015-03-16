package com.kingdee.eas.fdc.sellhouse.client;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.sellhouse.RoomCollection;
import com.kingdee.eas.fdc.sellhouse.RoomDisplaySetting;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.tenancy.TenancyDisPlaySetting;

public class SimulateRoomSelectUI extends RoomSelectUI {

	public SimulateRoomSelectUI() throws Exception {
		super();
	}

	protected void fillData() throws BOSException {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		if (node == null) {
			return;
		}
		
		SellProjectInfo sellProject = (SellProjectInfo)this.getUIContext().get("sellProject");
		RoomCollection roomColl = (RoomCollection)this.getUIContext().get("roomColl");
		if(moneySysTypeEnum==null)
			moneySysTypeEnum = (MoneySysTypeEnum) this.getUIContext().get("moneySysTypeEnum");
		if(moneySysTypeEnum!=null) {
			if(moneySysTypeEnum.equals(MoneySysTypeEnum.SalehouseSys))	{
				setting = new RoomDisplaySetting();
			}else if(moneySysTypeEnum.equals(MoneySysTypeEnum.TenancySys))		{
				setting = new TenancyDisPlaySetting();
			}else{
				setting = new RoomDisplaySetting();
			}
		}else{
			setting = new RoomDisplaySetting();
		}
		
		
		SHEHelper.fillVirtualRoomTableByNode(this.tblMain,node, moneySysTypeEnum, roomColl,setting,true);

	}
}
