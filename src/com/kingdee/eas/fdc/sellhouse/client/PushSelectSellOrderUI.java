/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.swing.KDComboBoxMultiColumnItem;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCCommonServerHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.sellhouse.PushManageFactory;
import com.kingdee.eas.fdc.sellhouse.PushManageHisEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PushManageInfo;
import com.kingdee.eas.fdc.sellhouse.PushManageStateEnum;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.StringUtils;

/**
 * output class name
 */
public class PushSelectSellOrderUI extends AbstractPushSelectSellOrderUI {
	private static final Logger logger = CoreUIObject
			.getLogger(PushSelectSellOrderUI.class);

	/**
	 * output class constructor
	 */
	public PushSelectSellOrderUI() throws Exception {
		super();
	}
	PushManageInfo pushInfo =null;
	public void onLoad() throws Exception {
		super.onLoad();
		
		for(int i=1;i<=20;i++){
			if(i==1){
				this.cbSellOrder.addItem(new KDComboBoxMultiColumnItem(new String[] { "首次开盘" }));
			}else{
				this.cbSellOrder.addItem(new KDComboBoxMultiColumnItem(new String[] {"第"+i+"次开盘"}));
			}
		}
		String id=(String) getUIContext().get("ID");
		if(id!=null){
			SelectorItemCollection sel=new SelectorItemCollection();
			sel.add("*");
			sel.add("his.*");
			pushInfo = PushManageFactory.getRemoteInstance().getPushManageInfo(new ObjectUuidPK(id),sel);
			if(pushInfo.getDescription()!=null){
				this.cbSellOrder.setSelectedIndex(Integer.parseInt(pushInfo.getDescription()));
			}
			this.pkOpenDate.setValue(pushInfo.getOpenDate());
		}
	}

	protected IObjectValue createNewData() {
		return null;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return PushManageFactory.getRemoteInstance();
	}

	/**
	 * output actionSubmit_actionPerformed 
	 * 推盘
	 */
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		if (this.cbSellOrder.getSelectedItem()==null) {
			MsgBox.showInfo("盘次不能为空！");
			abort();
		}
		if (this.pkOpenDate.getValue()==null) {
			MsgBox.showInfo("开盘日期不能为空！");
			abort();
		}
		if(pushInfo!=null){
			pushInfo.setSellOrder(this.cbSellOrder.getSelectedItem().toString());
			pushInfo.setOpenDate((Date) this.pkOpenDate.getValue());
			pushInfo.setDescription(String.valueOf(this.cbSellOrder.getSelectedIndex()));
			for(int i=0;i<pushInfo.getHis().size();i++){
				PushManageHisEntryInfo pushHisEntryInfo =pushInfo.getHis().get(i);
				pushHisEntryInfo.setOpenDate((Date) this.pkOpenDate.getValue());
			}
			PushManageFactory.getRemoteInstance().save(pushInfo);
			MsgBox.showInfo(this, "操作成功!");
			this.destroyWindow();
			return;
		}
		int result = MsgBox.showConfirm2New(null, "是否确认推盘！");
		if (result != MsgBox.YES) {
			return;
		}
			
		Set pushRoomIds = (HashSet) getUIContext().get("pushRoomIds");

		if (pushRoomIds == null) {
			return;
		}

		Date pushDate=FDCCommonServerHelper.getServerTimeStamp();
		PushManageInfo pushInfo = new PushManageInfo();
		pushInfo.setSellOrder(this.cbSellOrder.getSelectedItem().toString());
		pushInfo.setPushTotal(pushRoomIds.size());
		pushInfo.setPusher(SysContext.getSysContext().getCurrentUserInfo());
		pushInfo.setPushDate(pushDate);
		pushInfo.setOpenDate((Date) this.pkOpenDate.getValue());
		pushInfo.setDescription(String.valueOf(this.cbSellOrder.getSelectedIndex()));
		for (Iterator itor = pushRoomIds.iterator(); itor.hasNext();) {
			String roomId = (String) itor.next();
			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add("id");
			sic.add("name");
			sic.add("number");
			sic.add("building.id");
			sic.add("building.name");
			sic.add("building.number");
			sic.add("building.sellProject.id");
			sic.add("building.sellProject.name");
			sic.add("building.sellProject.number");
			sic.add("building.sellProject.orgUnit.id");
			sic.add("building.sellProject.orgUnit.name");

			RoomInfo room = RoomFactory.getRemoteInstance().getRoomInfo(
					new ObjectUuidPK(roomId), sic);
			SellProjectInfo sellProject = room.getBuilding().getSellProject();
//				checkSellOrder(sellProject);//判断盘次是否已存在
			
			PushManageHisEntryInfo pushHisEntryInfo = new PushManageHisEntryInfo();
			pushHisEntryInfo.setPushDate(pushDate);
			pushHisEntryInfo.setPusher(SysContext.getSysContext()
					.getCurrentUserInfo());
			pushHisEntryInfo.setRoom(room);
			pushHisEntryInfo.setPushManageState(PushManageStateEnum.PUSH);
			pushHisEntryInfo.setOpenDate((Date) this.pkOpenDate.getValue());
			pushInfo.getHis().add(pushHisEntryInfo);
			pushInfo.setSellProject(sellProject);
			//推盘组织,取当前组织
			//pushInfo.setOrgUnit(room.getBuilding().getSellProject().getOrgUnit());
			pushInfo.setOrgUnit(SysContext.getSysContext().getCurrentSaleUnit().castToFullOrgUnitInfo());
			pushHisEntryInfo.setHead(pushInfo);
		}
		PushManageFactory.getRemoteInstance().save(pushInfo);
		PushManageFactory.getRemoteInstance().executed(pushRoomIds);
		MsgBox.showInfo(this, "推盘成功!");
		this.destroyWindow();
	}
	
	private void checkSellOrder(SellProjectInfo sellProject) throws Exception {
		String sellOrder = this.cbSellOrder.getSelectedItem().toString();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("sellOrder", sellOrder));
		filter.getFilterItems().add(new FilterItemInfo("sellProject.id", sellProject.getId()));
		if (PushManageFactory.getRemoteInstance().exists(filter)) {
			MsgBox.showInfo("推盘盘次已存在,不能重复");
			SysUtil.abort();
		}
	}
	
	
}