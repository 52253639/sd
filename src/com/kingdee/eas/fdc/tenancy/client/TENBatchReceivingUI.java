/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JButton;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.BizDataFormat;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.kdf.util.render.CellTextRender;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.basedata.assistant.CurrencyFactory;
import com.kingdee.eas.basedata.assistant.CurrencyInfo;
import com.kingdee.eas.basedata.assistant.SettlementTypeInfo;
import com.kingdee.eas.basedata.master.account.AccountViewInfo;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillCollection;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillEntryInfo;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillFactory;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillInfo;
import com.kingdee.eas.fdc.basecrm.RevBillStatusEnum;
import com.kingdee.eas.fdc.basecrm.RevBillTypeEnum;
import com.kingdee.eas.fdc.basecrm.RevBizTypeEnum;
import com.kingdee.eas.fdc.basecrm.RevFDCCustomerEntryInfo;
import com.kingdee.eas.fdc.basecrm.RevListTypeEnum;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCCommonServerHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.invite.ListingItemInfo;
import com.kingdee.eas.fdc.sellhouse.FDCReceiveBillFactory;
import com.kingdee.eas.fdc.sellhouse.FDCReceiveBillInfo;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo;
import com.kingdee.eas.fdc.sellhouse.MoneySubjectContrastCollection;
import com.kingdee.eas.fdc.sellhouse.MoneySubjectContrastFactory;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SettlementgGatheringCollection;
import com.kingdee.eas.fdc.sellhouse.SettlementgGatheringFactory;
import com.kingdee.eas.fdc.sellhouse.SettlementgGatheringInfo;
import com.kingdee.eas.fdc.tenancy.RentFreeEntryCollection;
import com.kingdee.eas.fdc.tenancy.RentFreeEntryFactory;
import com.kingdee.eas.fdc.tenancy.RentFreeEntryInfo;
import com.kingdee.eas.fdc.tenancy.TenBillOtherPayCollection;
import com.kingdee.eas.fdc.tenancy.TenBillOtherPayFactory;
import com.kingdee.eas.fdc.tenancy.TenBillOtherPayInfo;
import com.kingdee.eas.fdc.tenancy.TenancyBillInfo;
import com.kingdee.eas.fdc.tenancy.TenancyBillStateEnum;
import com.kingdee.eas.fdc.tenancy.TenancyCustomerEntryCollection;
import com.kingdee.eas.fdc.tenancy.TenancyCustomerEntryFactory;
import com.kingdee.eas.fdc.tenancy.TenancyCustomerEntryInfo;
import com.kingdee.eas.fdc.tenancy.TenancyRoomPayListEntryCollection;
import com.kingdee.eas.fdc.tenancy.TenancyRoomPayListEntryFactory;
import com.kingdee.eas.fdc.tenancy.TenancyRoomPayListEntryInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.rptclient.newrpt.util.MsgBox;

/**
 * output class name
 */
public class TENBatchReceivingUI extends AbstractTENBatchReceivingUI
{
    private static final Logger logger = CoreUIObject.getLogger(TENBatchReceivingUI.class);
    
    private JButton btnRoomAddLine;                //房间 新增行
    private JButton btnRoomDeleteLine;	            //房间 删除行
    private JButton btnSelectFundConfirm;          //选择款项  确认
    private JButton btnSelectedFundDeleteLine;     //已选定款项  删除行
    private JButton btnReceiveNoteAddLine;         //付款记录 新增行
    private JButton btnReceiveNoteDeleteLine;      //付款记录 删除行
    KDBizPromptBox tenancybill;
    Set receivingBillID=new HashSet();
   
    public TENBatchReceivingUI() throws Exception
    {
        super();
    }

    
    public void storeFields()
    {
        super.storeFields();
    }
    
    public void onLoad() throws Exception {
    	tblRoom.checkParsed();
    	tblSelectFund.checkParsed();
    	tblSelectedFund.checkParsed();
    	tblReceiveNote.checkParsed();
    	super.onLoad();
    	setCellIsEdit();
    	setButtonStatus();
    	//设置房间F7
    	KDBizPromptBox prptRoom=new KDBizPromptBox();
    	prptRoom.setDisplayFormat("$name$");
    	prptRoom.setEditFormat("$number$");
    	prptRoom.setCommitFormat("$name$");
    	prptRoom.setQueryInfo("com.kingdee.eas.fdc.tenancy.app.F7RoomQuery");
    	EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
//		filter.getFilterItems().add(new FilterItemInfo("lastTenancy.tenancyState",TenancyBillStateEnum.EXECUTING_VALUE));
//		filter.getFilterItems().add(new FilterItemInfo("lastTenancy.id",null,CompareType.NOTEQUALS));
		SellProjectInfo sellProject = (SellProjectInfo) this.getUIContext().get("sellProject");
		StringBuffer sql=new StringBuffer(" select FRoomID from T_TEN_TenancyRoomEntry where FTenancyID in( ");
		sql.append(" select fid from T_TEN_TenancyBill where FSellProjectID='"+sellProject.getId().toString()+"");
		sql.append("' and ftenancystate not in('Saved','Submitted','BlankOut','Auditing'))");
		filter.getFilterItems().add(new FilterItemInfo("id",sql.toString(),CompareType.INNER));
		view.setFilter(filter);
    	prptRoom.setEntityViewInfo(view);
		KDTDefaultCellEditor roomEditor=new KDTDefaultCellEditor(prptRoom);
    	tblRoom.getColumn("room").setEditor(roomEditor);
    	prptRoom.setEditable(true);
    	tenancybill=new KDBizPromptBox();
    	tenancybill.setDisplayFormat("$name$");
    	tenancybill.setEditFormat("$name$");
    	tenancybill.setCommitFormat("$name$");
    	tenancybill.setQueryInfo("com.kingdee.eas.fdc.tenancy.app.TenancyBillQuery");
    	KDTDefaultCellEditor tenancybillEditor=new KDTDefaultCellEditor(tenancybill);
    	tenancybill.setEditable(false);
    	tblRoom.getColumn("tenancyBill").setEditor(tenancybillEditor);
    	class CellTextRenderImpl extends CellTextRender {
    		public String getText(Object obj) {
    			if(obj == null){
    				return null;
    			}
    			if(obj instanceof TenancyBillInfo)
    			{
    				return ((TenancyBillInfo)obj).getName();
    			}
    			return null;
    		}
    	}
    	CellTextRenderImpl avr = new CellTextRenderImpl();
    	avr.getText(new BizDataFormat("$name$"));
    	tblRoom.getColumn("tenancyBill").setRenderer(avr);
    	tblSelectedFund.getColumn("tenancyBill").setRenderer(avr);
    	//设置结算方式F7
    	KDBizPromptBox settlementType=new KDBizPromptBox();
    	settlementType.setDisplayFormat("$name$");
    	settlementType.setEditFormat("$name$");
    	settlementType.setCommitFormat("$name$");
    	settlementType.setQueryInfo("com.kingdee.eas.basedata.assistant.app.SettlementTypeQuery");
    	KDTDefaultCellEditor payTypeEditor=new KDTDefaultCellEditor(settlementType);
    	settlementType.setEditable(false);
    	tblReceiveNote.getColumn("settlementType").setEditor(payTypeEditor);
    	
    	//设置收款金额只能录入数值
    	KDFormattedTextField receiveAmount=new KDFormattedTextField();
    	receiveAmount.setHorizontalAlignment(FDCClientHelper.NUMBERTEXTFIELD_ALIGNMENT);
    	receiveAmount.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
    	receiveAmount.setMinimumValue(FDCHelper.ZERO);
    	receiveAmount.setSupportedEmpty(true);
    	receiveAmount.setPrecision(2);
		KDTDefaultCellEditor valueTextEditor = new KDTDefaultCellEditor(receiveAmount);
		tblReceiveNote.getColumn("receiveAmount").setEditor(valueTextEditor);
    	
    	//设置选择模式为行选择模式   屏蔽掉delete键
    	tblRoom.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
    	tblSelectFund.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
    	tblSelectedFund.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
    	tblReceiveNote.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
    	FDCTableHelper.disableDelete(tblRoom);
    	FDCTableHelper.disableDelete(tblSelectFund);
    	FDCTableHelper.disableDelete(tblSelectedFund);
    	FDCTableHelper.disableDelete(tblReceiveNote);
    	
    	//设置必录项
    	tblReceiveNote.getColumn("settlementType").setRequired(true);
    	tblReceiveNote.getColumn("receiveAmount").setRequired(true);
    	
    }
    /**
     * 设置单元格是否可编辑
     */
    private void setCellIsEdit(){
    	tblRoom.getColumn("roomState").getStyleAttributes().setLocked(true);
    	tblRoom.getColumn("buildingFloor").getStyleAttributes().setLocked(true);
    	tblRoom.getColumn("tenancyBill").getStyleAttributes().setLocked(true);
    	tblRoom.getColumn("customer").getStyleAttributes().setLocked(true);
    	tblRoom.getColumn("tel").getStyleAttributes().setLocked(true);
    	tblSelectFund.getColumn("room").getStyleAttributes().setLocked(true);
    	tblSelectFund.getColumn("moneyType").getStyleAttributes().setLocked(true);
    	tblSelectFund.getColumn("deratePeriod").getStyleAttributes().setLocked(true);
    	tblSelectFund.getColumn("startDate").getStyleAttributes().setLocked(true);
    	tblSelectFund.getColumn("endDate").getStyleAttributes().setLocked(true);
    	tblSelectFund.getColumn("appDate").getStyleAttributes().setLocked(true);
    	tblSelectFund.getColumn("appAmount").getStyleAttributes().setLocked(true);
    	tblSelectFund.getColumn("actAmount").getStyleAttributes().setLocked(true);
    	tblSelectFund.getColumn("derateAmount").getStyleAttributes().setLocked(true);
    	tblSelectedFund.getColumn("room").getStyleAttributes().setLocked(true);
    	tblSelectedFund.getColumn("tenancyBill").getStyleAttributes().setLocked(true);
    	tblSelectedFund.getColumn("customer").getStyleAttributes().setLocked(true);
    	tblSelectedFund.getColumn("moneyType").getStyleAttributes().setLocked(true);
    	tblSelectedFund.getColumn("deratePeriod").getStyleAttributes().setLocked(true);
    	tblSelectedFund.getColumn("startDate").getStyleAttributes().setLocked(true);
    	tblSelectedFund.getColumn("endDate").getStyleAttributes().setLocked(true);
    	tblSelectedFund.getColumn("appDate").getStyleAttributes().setLocked(true);
    	tblSelectedFund.getColumn("appAmount").getStyleAttributes().setLocked(true);
    	tblSelectedFund.getColumn("actAmount").getStyleAttributes().setLocked(true);
    	tblSelectedFund.getColumn("derateAmount").getStyleAttributes().setLocked(true);
    	tblSelectedFund.getColumn("settlementType").getStyleAttributes().setHided(true);
    	tblSelectedFund.getColumn("settlementNumber").getStyleAttributes().setHided(true);
    	tblSelectedFund.getColumn("revListId").getStyleAttributes().setHided(true);
    	tblSelectedFund.getColumn("amount").getStyleAttributes().setHided(true);
    	tblReceiveNote.getColumn("seq").getStyleAttributes().setLocked(true);
    	tblReceiveNote.getColumn("receiver").getStyleAttributes().setHided(true);
    }
    
    /**
     * 设置按钮状态
     */
    private void setButtonStatus(){
    	this.menuBar.setVisible(false);
    	this.menuBar.setEnabled(false);
    	this.toolBar.setVisible(false);
    	this.toolBar.setEnabled(false);
    	this.statusBar.setVisible(false);
    	this.statusBar.setEnabled(false);
    	this.confirmBtn.setEnabled(true);
    	this.cancelBtn.setEnabled(true);
    	btnRoomAddLine=contRoom.add(actionRoomAddLine);
    	btnRoomAddLine.setText("添加行");
    	btnRoomAddLine.setToolTipText("添加行");
    	btnRoomDeleteLine=contRoom.add(actionRoomDeleteLine);
    	btnRoomDeleteLine.setText("删除行");
    	btnRoomDeleteLine.setToolTipText("删除行");
    	btnSelectFundConfirm=contSelectFund.add(actionSelectFundConfirm);
    	btnSelectFundConfirm.setText("确认");
    	btnSelectFundConfirm.setToolTipText("确认");
    	actionSelectFundConfirm.setEnabled(true);
    	btnSelectedFundDeleteLine=contSelectedFund.add(actionSelectedFundDeleteLine);
    	btnSelectedFundDeleteLine.setText("删除行");
    	btnSelectedFundDeleteLine.setToolTipText("删除行");
    	actionSelectedFundDeleteLine.setEnabled(true);
    	btnReceiveNoteAddLine=contReceiveNote.add(actionReceiveAddLine);
    	btnReceiveNoteAddLine.setText("添加行");
    	btnReceiveNoteAddLine.setToolTipText("添加行");
    	btnReceiveNoteDeleteLine=contReceiveNote.add(actionReceiveDeleteLine);
    	btnReceiveNoteDeleteLine.setText("删除行");
    	btnReceiveNoteDeleteLine.setToolTipText("删除行");
    }
    
    private RoomInfo getRoomInfo(String id) throws Exception{
    	RoomInfo room=null;
    	EntityViewInfo view = new EntityViewInfo();
    	SelectorItemCollection sic=new SelectorItemCollection();
    	sic.add("tenancyState");//房间状态
    	sic.add("name");//房间名称
    	sic.add("number");
    	sic.add("buildingFloor.floorAlias");//楼层名称
    	sic.add("lastTenancy.number");
    	sic.add("lastTenancy.name");
    	sic.add("lastTenancy.tenancyName");//租赁合同
    	sic.add("id");
    	sic.add("buildingFloor.id");
    	sic.add("lastTenancy.id");
    	FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id",id));
		view.setSelector(sic);
		view.setFilter(filter);
		room=RoomFactory.getRemoteInstance().getRoomCollection(view).get(0);
    	return room;
    }
    
    private TenancyCustomerEntryCollection getCustomerCollection(String tenancyBillID) throws Exception{
    	TenancyCustomerEntryCollection tcec=null;
    	EntityViewInfo view = new EntityViewInfo();
    	SelectorItemCollection sic=new SelectorItemCollection();
    	sic.add("fdcCustomer.id");
    	sic.add("fdcCustomer.sysCustomer.id"); //系统客户
    	sic.add("fdcCustomer.name");//客户名称
    	sic.add("fdcCustomer.certificateNumber"); //客户身份证号码
    	sic.add("fdcCustomer.phone");//客户联系方式
    	FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("tenancyBill.id",tenancyBillID));
		view.setSelector(sic);
		view.setFilter(filter);
		tcec=TenancyCustomerEntryFactory.getRemoteInstance().getTenancyCustomerEntryCollection(view);
    	return tcec;
    }
    
    private TenancyRoomPayListEntryCollection getRoomPayListEntry(String id,String tenID) throws Exception{
    	TenancyRoomPayListEntryCollection trpc=null;
    	EntityViewInfo view = new EntityViewInfo();
    	SelectorItemCollection sic=new SelectorItemCollection();
    	sic.add("id");
    	sic.add("leaseSeq");  //租期序号
    	sic.add("startDate"); //起始日期
    	sic.add("endDate"); //结束日期
    	sic.add("appDate"); //应收日期
    	sic.add("appAmount");//应收金额
    	sic.add("actRevAmount");//已收金额
    	sic.add("remissionAmount");//减免金额
    	sic.add("moneyDefine.id");
    	sic.add("moneyDefine.moneyType");//款项类型
    	sic.add("moneyDefine.name");//款项名称
    	FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("tenRoom.room.id",id));
		filter.getFilterItems().add(new FilterItemInfo("tenRoom.tenancy.id",tenID));
		SorterItemCollection sc=new SorterItemCollection();
		SorterItemInfo sort=new SorterItemInfo("startDate");
		sort.setSortType(SortType.ASCEND);
		sc.add(sort);
		view.setSelector(sic);
		view.setFilter(filter);
		view.setSorter(sc);
		trpc=TenancyRoomPayListEntryFactory.getRemoteInstance().getTenancyRoomPayListEntryCollection(view);
    	return trpc;
    }
    
    /**
     * 获取出传入KDTable当前选中行
     * @param table
     * @return
     */
    private IRow getSelectRow(KDTable table){
    	IRow row=null;
    	if(table.getRowCount()>0){
    		if(table.getSelectManager().getActiveRowIndex()>=0){
    			row=table.getRow(table.getSelectManager().getActiveRowIndex());
    		}
    	}
    	return row;
    }

    /**
     * 房间编辑结束后触发
     */
    protected void tblRoom_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
        if(e.getColIndex()==tblRoom.getColumnIndex("room")){
        	if(e.getOldValue()!= null && e.getValue() != null){
        		RoomInfo room1=(RoomInfo)e.getOldValue();
        		RoomInfo room2=(RoomInfo)e.getValue();
        		if(room1.getId().toString().equals(room2.getId().toString())){//选择没有改变
        			return;
        		}
        	}
        	IRow irow=tblRoom.getRow(e.getRowIndex());
        	irow.getCell("roomState").setValue(null);
        	irow.getCell("buildingFloor").setValue(null);
        	irow.getCell("tenancyBill").setValue(null);
        	irow.getCell("tenancyBill").setUserObject(null);
        	irow.getCell("customer").setValue(null);
        	irow.getCell("tel").setValue(null);
        	if(e.getValue()==null){// 选择被清空时
        		if(tblSelectFund.getRowCount()>0){
        			tblSelectFund.removeRows();
        		}
        		tblRoom.getColumn("tenancyBill").getStyleAttributes().setLocked(true);
        	}
        	if(e.getValue()!=null){// 新选择得房间不为空则带出 以下内容
        		for(int i=0;i<tblRoom.getRowCount();i++){
            		if(i==e.getRowIndex()){
            			continue;
            		}
            		RoomInfo room=(RoomInfo)e.getValue();
            		if(tblRoom.getCell(i, "room").getValue()!=null){
            			RoomInfo info=(RoomInfo)tblRoom.getCell(i, "room").getValue();
            			if(room.getId().toString().equals(info.getId().toString())){
            				MsgBox.showError("房间不能重复！");
            				tblRoom.getCell(e.getRowIndex(), "room").setValue(null);
            				abort();
            			}
            		}
            	}
        		RoomInfo room=(RoomInfo)e.getValue();
        		room=getRoomInfo(room.getId().toString());
        		tblRoom.getColumn("tenancyBill").getStyleAttributes().setLocked(false);
        		EntityViewInfo view = new EntityViewInfo();
        		FilterInfo filter = new FilterInfo();
        		String sql="select FTenancyID from T_TEN_TenancyRoomEntry where FRoomID='"+room.getId().toString()+"'";
        		filter.getFilterItems().add(new FilterItemInfo("id",sql,CompareType.INNER));
        		filter.getFilterItems().add(new FilterItemInfo("tenancyState",TenancyBillStateEnum.SAVED_VALUE,CompareType.NOTEQUALS));
        		filter.getFilterItems().add(new FilterItemInfo("tenancyState",TenancyBillStateEnum.SUBMITTED_VALUE,CompareType.NOTEQUALS));
        		filter.getFilterItems().add(new FilterItemInfo("tenancyState",TenancyBillStateEnum.AUDITING_VALUE,CompareType.NOTEQUALS));
//        		filter.getFilterItems().add(new FilterItemInfo("tenancyState",TenancyBillStateEnum.TENANCYCHANGING_VALUE,CompareType.NOTEQUALS));
//        		filter.getFilterItems().add(new FilterItemInfo("tenancyState",TenancyBillStateEnum.BLANKOUT_VALUE,CompareType.NOTEQUALS));
        		view.setFilter(filter);
        		tenancybill.setEntityViewInfo(view);
        		
        		IRow row=tblRoom.getRow(e.getRowIndex());
        		if(room.getTenancyState()!=null){
        			row.getCell("roomState").setValue(room.getTenancyState());
        		}
        		if(room.getBuildingFloor()!=null && room.getBuildingFloor().getFloorAlias()!=null){
        			row.getCell("buildingFloor").setValue(room.getBuildingFloor().getFloorAlias());
        		}
        		if(room.getLastTenancy()!=null && room.getLastTenancy().getId()!=null){
        			row.getCell("tenancyBill").setValue(room.getLastTenancy());
        			row.getCell("tenancyBill").setUserObject(room.getLastTenancy());
        			TenancyCustomerEntryCollection tcec=getCustomerCollection(room.getLastTenancy().getId().toString());
        			StringBuffer names=new StringBuffer("");
        			StringBuffer phones=new StringBuffer("");
        			if(tcec!=null && tcec.size()>0){
        				for(int i=0;i<tcec.size();i++){
        					TenancyCustomerEntryInfo info=tcec.get(i);
        					if(i==tcec.size()-1){
        						names.append(info.getFdcCustomer().getName());
            					phones.append(info.getFdcCustomer().getPhone());
        					}else{
	        					names.append(info.getFdcCustomer().getName()+";");
	        					phones.append(info.getFdcCustomer().getPhone()+";");
        					}
        				}
        				row.getCell("customer").setValue(names);
        				row.getCell("tel").setValue(phones);
        			}
        		}
        		refreshTblSelectFund(room);
        		if(e.getOldValue()!=null){ //房间选择变化后 应该删除掉  前房间所选定的款项
        			RoomInfo roomInfo=(RoomInfo)e.getOldValue();
        			roomInfo=getRoomInfo(roomInfo.getId().toString());
        			removeSelelct(roomInfo);
        		}
        	}
        }
        
        if(e.getColIndex()==tblRoom.getColumnIndex("tenancyBill")){
        	if(e.getValue()==null){
        		return ;
        	}
        	if(e.getOldValue()!=null && e.getValue()!=null){
        		TenancyBillInfo info1=(TenancyBillInfo)e.getValue();
        		TenancyBillInfo info2=(TenancyBillInfo)e.getOldValue();
        		if(info1.getId().toString().equals(info2.getId().toString())){
        			return;
        		}
        	}
        	TenancyBillInfo info=(TenancyBillInfo)e.getValue();
			tblRoom.getCell(tblRoom.getSelectManager().getActiveRowIndex(), "tenancyBill").setValue(info);
			tblRoom.getCell(tblRoom.getSelectManager().getActiveRowIndex(), "tenancyBill").setUserObject(info);
			TenancyCustomerEntryCollection tcec=getCustomerCollection(info.getId().toString());
			StringBuffer names=new StringBuffer("");
			StringBuffer phones=new StringBuffer("");
			if(tcec!=null && tcec.size()>0){
				for(int i=0;i<tcec.size();i++){
					TenancyCustomerEntryInfo einfo=tcec.get(i);
					if(i==tcec.size()-1){
						names.append(einfo.getFdcCustomer().getName());
    					phones.append(einfo.getFdcCustomer().getPhone());
					}else{
    					names.append(einfo.getFdcCustomer().getName()+";");
    					phones.append(einfo.getFdcCustomer().getPhone()+";");
					}
				}
				tblRoom.getCell(tblRoom.getSelectManager().getActiveRowIndex(), "customer").setValue(names);
				tblRoom.getCell(tblRoom.getSelectManager().getActiveRowIndex(), "tel").setValue(phones);
			}
			
			RoomInfo room=(RoomInfo)tblRoom.getCell(tblRoom.getSelectManager().getActiveRowIndex(), "room").getValue();
			
			refreshTblSelectFund(room);
			removeSelelct(room);
        }
    }
    
    private void removeSelelct(RoomInfo roomInfo){
    	if(tblSelectedFund.getRowCount()>0){
			for(int i=0;i<tblSelectedFund.getRowCount()-1;i++){
				if(tblSelectedFund.getCell(i, "room").getValue()!=null){
					RoomInfo r=(RoomInfo)tblSelectedFund.getCell(i, "room").getValue();
					if(roomInfo.getId().toString().equals(r.getId().toString())){
						tblSelectedFund.removeRow(i);
						i--;
					}
				}
				else{
					TenancyBillInfo tbi=(TenancyBillInfo)tblSelectedFund.getCell(i, "tenancyBill").getUserObject();
					TenancyBillInfo tenInfo=(TenancyBillInfo)tblRoom.getCell(i, "tenancyBill").getValue();
					if(tenInfo.getId().toString().equals(tbi.getId().toString())){
						tblSelectedFund.removeRow(i);
						i--;
					}
				}
			}
			if(tblSelectedFund.getRowCount()>0){// 这里是暂时删除合计行
				tblSelectedFund.removeRow(tblSelectedFund.getRowCount()-1);
			}
			addAggregate(tblSelectedFund,new String[]{"appAmount","actAmount","derateAmount"});
			if(tblSelectedFund.getRowCount()==1){
				tblSelectedFund.removeRows();
			}
		}
    }
    
    private void refreshTblSelectFund(RoomInfo room) throws Exception{
    	if(tblSelectFund.getRowCount()>0){
			tblSelectFund.removeRows();
		}
    	//这里加载房间付款计划
    	if(tblRoom.getCell(tblRoom.getSelectManager().getActiveRowIndex(), "tenancyBill").getValue()==null){
    		return;
    	}
    	TenancyBillInfo tenInfo=(TenancyBillInfo)tblRoom.getCell(tblRoom.getSelectManager().getActiveRowIndex(), "tenancyBill").getUserObject();
    	TenancyRoomPayListEntryCollection trpc=getRoomPayListEntry(room.getId().toString(),tenInfo.getId().toString());
		if(trpc!=null && trpc.size()>0){
			for(int i=0;i<trpc.size();i++){
				IRow r=tblSelectFund.addRow();
				r.getCell("select").setValue(Boolean.FALSE);
				TenancyRoomPayListEntryInfo info=trpc.get(i);
				r.getCell("room").setValue(room);
				r.getCell("room").setUserObject(room);
				r.getCell("tenancyBill").setValue(tenInfo.getTenancyName());
				r.getCell("tenancyBill").setUserObject(tenInfo);
				if(info.getMoneyDefine()!=null && info.getMoneyDefine().getMoneyType()!=null){
					r.getCell("moneyType").setValue(info.getMoneyDefine().getName());
					r.getCell("moneyType").setUserObject(info.getMoneyDefine());
				}
				if(info.getId()!=null){
					r.getCell("revListId").setValue(info.getId());
				}
				if(info.getStartDate()!=null){
					r.getCell("startDate").setValue(info.getStartDate());
				}
				if(info.getEndDate()!=null){
					r.getCell("endDate").setValue(info.getEndDate());
				}
				if(info.getStartDate()!=null && info.getEndDate()!=null){//款项有起始日期和结束日期 才会有 免租期间
					RentFreeEntryCollection rfec=getRentFreeEntryCollection(tenInfo.getId().toString());
					if(rfec!=null && rfec.size()>0){
						String deratePeriod=null;
						for(int j=0;j<rfec.size();j++){
							RentFreeEntryInfo rf=rfec.get(j);
							if((info.getStartDate().before(rf.getFreeStartDate())||info.getStartDate().equals(rf.getFreeStartDate()))
									&& (info.getEndDate().after(rf.getFreeStartDate())||info.getEndDate().equals(rf.getFreeStartDate()))
									&& (info.getEndDate().before(rf.getFreeEndDate()))||info.getEndDate().equals(rf.getFreeEndDate())){
								//当款项起始日期早于或等于减免日期的起始日期 
								//并且 款项结束日期晚于或等于减免日期的起始日期 
								//并且 款项结束日期早于或等于减免日期的结束日期   这时取并集结果是 减免日期的起始日期 到  款项结束日期
								deratePeriod=rf.getFreeStartDate()+"—"+info.getEndDate()+";";
							}
							else if((info.getStartDate().before(rf.getFreeStartDate())||info.getStartDate().equals(rf.getFreeStartDate())) 
									&& (info.getEndDate().after(rf.getFreeEndDate())||info.getEndDate().equals(rf.getFreeEndDate()))){
								//当款项起始日期早于或等于减免日期的起始日期 并且 款项结束日期晚于或等于减免日期的结束日期
								//这时取并集结果是减免日期的起始日期到减免日期的结束日期
								deratePeriod=rf.getFreeStartDate()+"—"+rf.getFreeEndDate()+";";
							}
							else if((info.getStartDate().after(rf.getFreeStartDate())||info.getStartDate().equals(rf.getFreeStartDate())) 
									&& (info.getEndDate().before(rf.getFreeEndDate())||info.getEndDate().equals(rf.getFreeEndDate()))){
								//当款项起始日期晚于或等于减免日期的起始日期 并且 款项结束日期早于或等于减免日期的结束日期
								//这时取并集结果是款项的起始日期到款项的结束日期
								deratePeriod=info.getStartDate()+"—"+info.getEndDate()+";";
							}
							else if((info.getStartDate().after(rf.getFreeStartDate())||info.getStartDate().equals(rf.getFreeStartDate())) 
									&& (info.getStartDate().before(rf.getFreeEndDate())||info.getStartDate().equals(rf.getFreeEndDate())
									&& (info.getEndDate().after(rf.getFreeEndDate())||info.getEndDate().equals(rf.getFreeEndDate())))){
								//当款项起始日期晚于或等于减免日期的起始日期  
								//并且款项起始日期早于或等于减免日期的结束日期
								//并且款项的结束日期晚于或等于减免日期的结束日期  这时取并集为 款项起始日期到减免日期的结束日期
								deratePeriod=info.getStartDate()+"—"+rf.getFreeEndDate()+";";
							}
							else{//只有当以上四种情况时会有并集  其他情况下无并集
								deratePeriod="";
							}
							if(r.getCell("deratePeriod").getValue()!=null){
								deratePeriod=r.getCell("deratePeriod").getValue().toString()+deratePeriod;
							}
							r.getCell("deratePeriod").setValue(deratePeriod);
						}
//						if(r.getCell("deratePeriod").getValue()!=null){
//							deratePeriod=r.getCell("deratePeriod").getValue().toString()+deratePeriod;
//						}
//						r.getCell("deratePeriod").setValue(deratePeriod);
					}
				}
				if(info.getAppDate()!=null){
					r.getCell("appDate").setValue(info.getAppDate());
				}
				if(info.getAppAmount()!=null){
					r.getCell("appAmount").setValue(info.getAppAmount());
				}
				if(info.getActRevAmount()!=null){
					r.getCell("actAmount").setValue(info.getActRevAmount());
				}
				if(info.getRemissionAmount()!=null){
					r.getCell("derateAmount").setValue(info.getRemissionAmount());
				}
			}
		}
		//这里加载租赁合同其他应付款项明细
		TenBillOtherPayCollection topc=getTenBillOtherPayCollection(tenInfo.getId().toString());
		if(topc!=null && topc.size()>0){
			for(int i=0;i<topc.size();i++){
				IRow r=tblSelectFund.addRow();
				r.getCell("select").setValue(Boolean.FALSE);
				r.getCell("tenancyBill").setValue(tenInfo.getName());
				r.getCell("tenancyBill").setUserObject(tenInfo);
				TenBillOtherPayInfo info=topc.get(i);
				if(info.getMoneyDefine()!=null && info.getMoneyDefine().getMoneyType()!=null){
					r.getCell("moneyType").setValue(info.getMoneyDefine().getName());
					r.getCell("moneyType").setUserObject(info.getMoneyDefine());
				}
				if(info.getId()!=null){
					r.getCell("revListId").setValue(info.getId());
				}
				if(info.getAppDate()!=null){
					r.getCell("appDate").setValue(info.getAppDate());
				}
				if(info.getAppAmount()!=null){
					r.getCell("appAmount").setValue(info.getAppAmount());
				}
				if(info.getActRevAmount()!=null){
					r.getCell("actAmount").setValue(info.getActRevAmount());
				}
			}
		}
		
		//添加了逻辑 批量收款时款项选择中不显示已收清的款项
		for(int i=0;i<tblSelectFund.getRowCount();i++){
			IRow row=tblSelectFund.getRow(i);
			if(row.getCell("appAmount").getValue()!=null && row.getCell("actAmount").getValue()!=null){
				BigDecimal appAmount=new BigDecimal(row.getCell("appAmount").getValue().toString());
				BigDecimal actAmount=new BigDecimal(row.getCell("actAmount").getValue().toString());
				if(appAmount.compareTo(actAmount)<=0){ //应收金额 小于等 实收金额
					tblSelectFund.removeRow(i);
					i--;
				}
			}
		}
    }
    
    private TenBillOtherPayCollection getTenBillOtherPayCollection(String tenancyBillID) throws Exception{ 
    	TenBillOtherPayCollection topc=null;
    	EntityViewInfo view = new EntityViewInfo();
    	SelectorItemCollection sic=new SelectorItemCollection();
    	sic.add("id");
    	sic.add("leaseSeq");  //租期序号
    	sic.add("appDate"); //应收日期
    	sic.add("appAmount");//应收金额
    	sic.add("actRevAmount");//已收金额
    	sic.add("moneyDefine.id");
    	sic.add("moneyDefine.moneyType");//款项类型
    	sic.add("moneyDefine.name");//款项名称
    	FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("head.id",tenancyBillID));
		view.setSelector(sic);
		view.setFilter(filter);  
		topc=TenBillOtherPayFactory.getRemoteInstance().getTenBillOtherPayCollection(view);
		return topc;
    }
    
    private RentFreeEntryCollection getRentFreeEntryCollection(String tenancyBillID) throws Exception{
    	RentFreeEntryCollection rfec=null;
    	EntityViewInfo view = new EntityViewInfo();
    	SelectorItemCollection sic=new SelectorItemCollection();
    	sic.add("id");
    	sic.add("freeStartDate");
    	sic.add("freeEndDate");
    	FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("tenancy.id",tenancyBillID));
		view.setSelector(sic);
		view.setFilter(filter);
		rfec=RentFreeEntryFactory.getRemoteInstance().getRentFreeEntryCollection(view);
    	return rfec;
    }
    
    /**
     *  房间列表选择变化时
     */
    protected void tblRoom_tableSelectChanged(KDTSelectEvent e)
    		throws Exception {
    	tblSelectFund.removeRows();
    	IRow row=getSelectRow(tblRoom);
    	if(row.getCell("room").getValue()!=null){
    		row.getCell("tenancyBill").getStyleAttributes().setLocked(false);
    		RoomInfo room=(RoomInfo)row.getCell("room").getValue();
    		room=getRoomInfo(room.getId().toString());
    		refreshTblSelectFund(room);
    		EntityViewInfo view = new EntityViewInfo();
    		FilterInfo filter = new FilterInfo();
    		String sql="select FTenancyID from T_TEN_TenancyRoomEntry where FRoomID='"+room.getId().toString()+"'";
    		filter.getFilterItems().add(new FilterItemInfo("id",sql,CompareType.INNER));
    		filter.getFilterItems().add(new FilterItemInfo("tenancyState",TenancyBillStateEnum.SAVED_VALUE,CompareType.NOTEQUALS));
    		filter.getFilterItems().add(new FilterItemInfo("tenancyState",TenancyBillStateEnum.SUBMITTED_VALUE,CompareType.NOTEQUALS));
    		filter.getFilterItems().add(new FilterItemInfo("tenancyState",TenancyBillStateEnum.AUDITING_VALUE,CompareType.NOTEQUALS));
    		filter.getFilterItems().add(new FilterItemInfo("tenancyState",TenancyBillStateEnum.TENANCYCHANGING_VALUE,CompareType.NOTEQUALS));
    		filter.getFilterItems().add(new FilterItemInfo("tenancyState",TenancyBillStateEnum.BLANKOUT_VALUE,CompareType.NOTEQUALS));
    		view.setFilter(filter);
    		tenancybill.setEntityViewInfo(view);
    	}
    	else{
    		row.getCell("tenancyBill").getStyleAttributes().setLocked(true);
    	}
    }

    /**
     * 收款记录编辑结束后触发
     */
    protected void tblReceiveNote_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    	//编辑收款金额结束后 应该删除合计行然后重新添加   实际就是刷新合计了。。
    	if(e.getColIndex()==tblReceiveNote.getColumnIndex("receiveAmount")){
	    	if(tblReceiveNote.getRowCount()>0){
	    		tblReceiveNote.removeRow(tblReceiveNote.getRowCount()-1);
	    	}
	    	addAggregate(tblReceiveNote,new String[]{"receiveAmount"});
    	}
//    	setSettlementType();
    }

    /**
     * 取消
     */
    public void actionCancelBtn_actionPerformed(ActionEvent e) throws Exception
    {
    	this.disposeUIWindow();
    }

    /**
     *  确定
     */
    public void actionConfirmBtn_actionPerformed(ActionEvent e) throws Exception
    {
    	checkAmount();
    	setSettlementType();
    	FDCReceivingBillCollection coll=createReceives();
    	if(coll!=null && coll.size()>0){
    		for(int i=0;i<coll.size();i++){
    			String id=FDCReceivingBillFactory.getRemoteInstance().submitRev(coll.get(i),"com.kingdee.eas.fdc.tenancy.app.TenRevHandle");    			
    			receivingBillID.add(id);
    		}
    	}
    	this.disposeUIWindow();
    	IUIFactory uiFactory = null;
    	UIContext uiContext = new UIContext(this);
    	uiContext.put("keyID", receivingBillID); 
    	uiFactory = UIFactory.createUIFactory(UIFactoryName.NEWWIN);
		IUIWindow curDialog = uiFactory.create(TENReceivingBillListPartUI.class
				.getName(),uiContext, null, OprtState.VIEW);
		curDialog.show();
    }
    /**
     *  分配结算方式到 已选定款项
     */
    private void setSettlementType(){
    	for(int i=0;i<tblReceiveNote.getRowCount()-1;i++){
    		if(tblReceiveNote.getCell(i,"settlementType").getValue()==null){
    			continue;
    		}
    		BigDecimal temp=new BigDecimal("0.00");
    		SettlementTypeInfo info=(SettlementTypeInfo)tblReceiveNote.getCell(i,"settlementType").getValue();
    		String settlementNumber=null;
    		if(tblReceiveNote.getCell(i,"settlementNumber").getValue()!=null){
    			settlementNumber=tblReceiveNote.getCell(i,"settlementNumber").getValue().toString();
    		}
    		BigDecimal receiveAmount=new BigDecimal(tblReceiveNote.getCell(i,"receiveAmount").getValue().toString());
    		for(int j=0;j<tblSelectedFund.getRowCount()-1;j++){
    			if(tblSelectedFund.getCell(j, "settlementType").getValue()!=null){
    				continue;
    			}
    			BigDecimal appAmount=new BigDecimal("0.00");
    			BigDecimal actAmount=new BigDecimal("0.00");
//    			BigDecimal derateAmount=new BigDecimal("0.00");
    			if(tblSelectedFund.getCell(j, "appAmount").getValue()!=null){
    				appAmount=new BigDecimal(tblSelectedFund.getCell(j, "appAmount").getValue().toString());
    			}
    			if(tblSelectedFund.getCell(j, "actAmount").getValue()!=null){
    				actAmount=new BigDecimal(tblSelectedFund.getCell(j, "actAmount").getValue().toString());
    			}
//    			if(tblSelectedFund.getCell(j, "derateAmount").getValue()!=null){
//    				derateAmount=new BigDecimal(tblSelectedFund.getCell(j, "derateAmount").getValue().toString());
//    			}
    			BigDecimal settlementAmount=appAmount.subtract(actAmount);
    			temp=receiveAmount.subtract(settlementAmount);
    			if(temp.compareTo(new BigDecimal("0.00"))>0){ 
    				receiveAmount=temp;
    				tblSelectedFund.getCell(j, "settlementType").setValue(info);
    				tblSelectedFund.getCell(j, "amount").setValue(settlementAmount);
    				tblSelectedFund.getCell(j,"settlementNumber").setValue(settlementNumber);
    			}
    			else if(temp.compareTo(new BigDecimal("0.00"))==0){ 
    				tblSelectedFund.getCell(j, "settlementType").setValue(info);
    				tblSelectedFund.getCell(j, "amount").setValue(settlementAmount);
    				tblSelectedFund.getCell(j,"settlementNumber").setValue(settlementNumber);
    				break;
    			}
    			else{
    				//这里匹配不完全  所以要分割下 弄成俩条数据   
    				IRow row=tblSelectedFund.removeRow(j);
    				row.getCell("settlementType").setValue(info);
    				row.getCell("amount").setValue(receiveAmount);
    				row.getCell("settlementNumber").setValue(settlementNumber);
    				tblSelectedFund.addRow(j,row);
    				IRow newRow=tblSelectedFund.addRow(j+1);
    				if(row.getCell("room").getValue()!=null){
    					newRow.getCell("room").setValue(row.getCell("room").getValue());
    				}
    				if(row.getCell("tenancyBill").getValue()!=null){
    					newRow.getCell("tenancyBill").setValue(row.getCell("tenancyBill").getValue());
    					newRow.getCell("tenancyBill").setUserObject(row.getCell("tenancyBill").getUserObject());
    				}
    				if(row.getCell("customer").getValue()!=null){
    					newRow.getCell("customer").setValue(row.getCell("customer").getValue());
    				}
    				if(row.getCell("moneyType").getValue()!=null){
    					newRow.getCell("moneyType").setValue(row.getCell("moneyType").getValue());
    					newRow.getCell("moneyType").setUserObject(row.getCell("moneyType").getUserObject());
    				}
    				if(row.getCell("startDate").getValue()!=null){
    					newRow.getCell("startDate").setValue(row.getCell("startDate").getValue());
    				}
    				if(row.getCell("endDate").getValue()!=null){
    					newRow.getCell("endDate").setValue(row.getCell("endDate").getValue());
    				}
    				if(row.getCell("deratePeriod").getValue()!=null){
    					newRow.getCell("deratePeriod").setValue(row.getCell("deratePeriod").getValue());
    				}
    				if(row.getCell("appDate").getValue()!=null){
    					newRow.getCell("appDate").setValue(row.getCell("appDate").getValue());
    				}
    				if(row.getCell("revListId").getValue()!=null){
    					newRow.getCell("revListId").setValue(row.getCell("revListId").getValue());
    				}
    				newRow.getCell("appAmount").setValue(settlementAmount.subtract(receiveAmount));
    				newRow.getCell("actAmount").setValue(null);//.setValue(new BigDecimal("0.00"));
    				newRow.getCell("derateAmount").setValue(null);//.setValue(new BigDecimal("0.00"));
    				newRow.getCell("settlementType").setValue(null);
    				newRow.getCell("amount").setValue(null);
    				newRow.getCell("settlementNumber").setValue(null);
    				break;
    			}
    		}
    	}
    }
    
    /**
     *  批量生成收款单
     */
    private FDCReceivingBillCollection createReceives() throws Exception{
    	FDCReceivingBillCollection coll=new FDCReceivingBillCollection();
    	boolean isNullAccount = false;
    	for(int i=0;i<tblRoom.getRowCount();i++){
    		if(tblRoom.getCell(i, "room").getValue()==null){
    			continue;
    		}
    		FDCReceivingBillInfo fdcRev = new FDCReceivingBillInfo();
//    		BOSUuid id=BOSUuid.create("F12182FE");
//    		fdcRev.setId(id);
//    		receivingBillID.add(id.toString());
//        	info.setId(BOSUuid.create("9412AC80"));
    		OrgUnitInfo crrOu = SysContext.getSysContext().getCurrentSaleUnit();   //当前销售组织;
    		if(crrOu==null){
    			crrOu = SysContext.getSysContext().getCurrentOrgUnit();            //当前组织
    		}
    		ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getRemoteInstance();
        	if(iCodingRuleManager.isExist(fdcRev,crrOu.getId().toString())){//是否启用了编码规则
            	if(iCodingRuleManager.isAddView(fdcRev,crrOu.getId().toString())){//是否启用了新增显示
            		fdcRev.setNumber(iCodingRuleManager.getNumber(fdcRev, crrOu.getId().toString()));
            	}
        	}
    		Timestamp curTime = null;
    		try {
    			curTime = FDCCommonServerHelper.getServerTimeStamp();
    		} catch (BOSException e1) {
    			e1.printStackTrace();
    			curTime = new Timestamp(System.currentTimeMillis());
    		}
        	fdcRev.setCreateTime(curTime);
        	fdcRev.setCreator(SysContext.getSysContext().getCurrentUserInfo());
        	CompanyOrgUnitInfo company = SysContext.getSysContext().getCurrentFIUnit();
        	fdcRev.setCompany(company);
        	FullOrgUnitInfo org = SysContext.getSysContext().getCurrentOrgUnit().castToFullOrgUnitInfo();
        	fdcRev.setOrgUnit(org);
        	SellProjectInfo sellProject = (SellProjectInfo)this.getUIContext().get("sellProject");
        	fdcRev.setSellProject(sellProject);
        	fdcRev.setRevBillType(RevBillTypeEnum.gathering);
        	fdcRev.setRevBizType(RevBizTypeEnum.tenancy);
        	//设置币别
    		CurrencyInfo baseCurrency = CurrencyFactory.getRemoteInstance().getCurrencyInfo(new ObjectUuidPK(BOSUuid.read(company.getBaseCurrency().getId().toString())));
    		fdcRev.setCurrency(baseCurrency);
    		//目前暂不支持外币,全部取出的全部为人民币,汇率设置为1
    		fdcRev.setExchangeRate(FDCHelper.ONE);
    		//默认收款日期为当前服务器日期
    		fdcRev.setBizDate(curTime);
    		fdcRev.setBillStatus(RevBillStatusEnum.SUBMIT);//单据状态设置为 保存
        	fdcRev.setFiVouchered(false);
        	fdcRev.setState(FDCBillStateEnum.SUBMITTED);
    		fdcRev.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
    		IRow row=tblRoom.getRow(i);
    		RoomInfo room=(RoomInfo)row.getCell("room").getValue();
    		TenancyBillInfo tenBill=(TenancyBillInfo)row.getCell("tenancyBill").getValue();
//    		room=getRoomInfo(room.getId().toString());
    		fdcRev.setTenancyObj(tenBill);
    		TenancyCustomerEntryCollection tcec=getCustomerCollection(tenBill.getId().toString()); 
    		//收款用户  需求确认直接取第一个用户
    		fdcRev.setCustomer(tcec.get(0).getFdcCustomer().getSysCustomer());
    		//交费 房地产用户分录
    		fdcRev.getFdcCustomers().clear();
    		for(int j=0;j<tcec.size();j++){
    			RevFDCCustomerEntryInfo revFdcCusEntry = new RevFDCCustomerEntryInfo();
				revFdcCusEntry.setFdcCustomer(tcec.get(j).getFdcCustomer());
    			fdcRev.getFdcCustomers().add(revFdcCusEntry);
    		}
    		BigDecimal sumAmount=new BigDecimal("0.00");
    		//收款单分录
    		fdcRev.getEntries().clear();   		 
    		for(int j=0;j<tblSelectedFund.getRowCount()-1;j++){
    			FDCReceivingBillEntryInfo entryInfo=new FDCReceivingBillEntryInfo();
    			if(tblSelectedFund.getCell(j, "room").getValue()!=null){
    				RoomInfo roomInfo=(RoomInfo)tblSelectedFund.getCell(j, "room").getValue();
    				if(room.getId().toString().equals(roomInfo.getId().toString())){
    					//这里是付款明细
//    					entryInfo.setHead(fdcRev);
    					entryInfo.setRevAmount(new BigDecimal(tblSelectedFund.getCell(j, "amount").getValue().toString()));
    					sumAmount=sumAmount.add(entryInfo.getRevAmount());
    					entryInfo.setRevLocAmount(new BigDecimal(tblSelectedFund.getCell(j, "amount").getValue().toString()));
    					SettlementTypeInfo type=(SettlementTypeInfo)tblSelectedFund.getCell(j,"settlementType").getValue();
    					entryInfo.setSettlementType(type);
    					if(tblSelectedFund.getCell(j,"settlementNumber").getValue()!=null){
    						entryInfo.setSettlementNumber(tblSelectedFund.getCell(j,"settlementNumber").getValue().toString());
    					}
    					//结算方式对应收款科目
    					AccountViewInfo accountView = getAccountAndGathing(type);
    					MoneyDefineInfo  mdi=(MoneyDefineInfo)tblSelectedFund.getCell(j, "moneyType").getUserObject();
    					entryInfo.setMoneyDefine(mdi);
    					//款项类型对应对方科目
    					AccountViewInfo account = getAccountView(mdi.getId().toString());
    					if(account==null || accountView==null)
    					{
    						isNullAccount = true;
    					}
    					entryInfo.setRoom(roomInfo);
    					entryInfo.setOppAccount(account);
    					entryInfo.setRevAccount(accountView);
    					entryInfo.setRevListType(RevListTypeEnum.tenRoomRev);
    					entryInfo.setRevListId(tblSelectedFund.getCell(j, "revListId").getValue().toString());
    					fdcRev.getEntries().add(entryInfo);
    				}
    			}
    			else{
    				if(tblSelectedFund.getCell(j, "tenancyBill").getValue()!=null){
    					TenancyBillInfo tenancyBill=(TenancyBillInfo)tblSelectedFund.getCell(j, "tenancyBill").getUserObject();
    					if(tenBill.getId().toString().equals(tenancyBill.getId().toString())){
    						//加这个判断是为了 同一个合同的 其它款项明细 只在第一个房间生成收款单时被记录
    						if(tblSelectedFund.getRow(j).getUserObject()==null ){
//	    						entryInfo.setHead(fdcRev);
	        					entryInfo.setRevAmount(new BigDecimal(tblSelectedFund.getCell(j, "amount").getValue().toString()));
	        					sumAmount=sumAmount.add(entryInfo.getRevAmount());
	        					entryInfo.setRevLocAmount(new BigDecimal(tblSelectedFund.getCell(j, "amount").getValue().toString()));
	        					SettlementTypeInfo type=(SettlementTypeInfo)tblSelectedFund.getCell(j,"settlementType").getValue();
	        					entryInfo.setSettlementType(type);
	        					if(tblSelectedFund.getCell(j,"settlementNumber").getValue()!=null){
	        						entryInfo.setSettlementNumber(tblSelectedFund.getCell(j,"settlementNumber").getValue().toString());
	        					}
	        					//结算方式对应收款科目
	        					AccountViewInfo accountView = getAccountAndGathing(type);
	        					MoneyDefineInfo  mdi=(MoneyDefineInfo)tblSelectedFund.getCell(j, "moneyType").getUserObject();
	        					entryInfo.setMoneyDefine(mdi);
	        					AccountViewInfo account=getAccountView(mdi.getId().toString());
	        					if(account==null || accountView==null)
	        					{
	        						isNullAccount = true;
	        					}
	        					entryInfo.setOppAccount(account);
	        					entryInfo.setRevListType(RevListTypeEnum.tenOtherRev);
	        					entryInfo.setRevListId(tblSelectedFund.getCell(j, "revListId").getValue().toString());
	        					tblSelectedFund.getRow(j).setUserObject("true");
	        					fdcRev.getEntries().add(entryInfo);
    						}
    					}
    				}
    			}
    		}
    		if(isNullAccount)
    		{
    			MsgBox.showInfo("收款单收款科目或者对方科目为空，请到收款单序时薄上去进行科目维护");
    		}
    		fdcRev.setAmount(sumAmount);
    		fdcRev.setOriginalAmount(sumAmount);
    		coll.add(fdcRev);
    	}
    	return coll;
    }
    
    /*
	 * 根据结算方式和收款科目对照表找出收款科目和账户
	 */
	private AccountViewInfo getAccountAndGathing(SettlementTypeInfo settType) throws BOSException
	{
		CompanyOrgUnitInfo company = SysContext.getSysContext().getCurrentFIUnit();
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		if(company != null){
			filter.getFilterItems().add(new FilterItemInfo("orgUnit.id", company.getId().toString()));
		}else{
			filter.getFilterItems().add(new FilterItemInfo("orgUnit.id", null));
		}
		filter.getFilterItems().add(new FilterItemInfo("balance.id",settType.getId().toString()));
		view.getSelector().add("balance.*");
		view.getSelector().add("bankAccount.*");
		view.getSelector().add("Gathering.*");
		
		SettlementgGatheringCollection settGatColl = SettlementgGatheringFactory.getRemoteInstance().getSettlementgGatheringCollection(view);
		if(settGatColl.size()>0)
		{
			SettlementgGatheringInfo settGatInfo = settGatColl.get(0);
			return settGatInfo.getGathering();
		}
		return null;
	}
    
    private AccountViewInfo getAccountView(String id) throws Exception{
    	EntityViewInfo view = new EntityViewInfo();
    	SelectorItemCollection sic=new SelectorItemCollection();
    	sic.add("id");
    	sic.add("accountView.id");
    	sic.add("accountView.name");
    	sic.add("accountView.number");
    	FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("moneyDefine.id",id));
		view.setSelector(sic);
		view.setFilter(filter); 
		MoneySubjectContrastCollection coll=MoneySubjectContrastFactory.getRemoteInstance().getMoneySubjectContrastCollection(view);
		if(coll!=null && coll.size()>0){
			return coll.get(0).getAccountView();
		}
    	return null;
    }
    
    /**
     * 验证输入的 收款金额合计 是否等于 已选择款项中的应收金额减去已收金额和减免金额的合计
     */
    private void checkAmount(){
    	if(tblRoom.getRowCount()>0){
    		if(tblSelectedFund.getRowCount()>0){
        		for(int i=0;i<tblRoom.getRowCount();i++){
        			if(tblRoom.getCell(i, "room").getValue()==null){
            			continue;
            		}
        			IRow row=tblRoom.getRow(i);
        			RoomInfo room=(RoomInfo)row.getCell("room").getValue();
        			TenancyBillInfo tenInfo=(TenancyBillInfo)row.getCell("tenancyBill").getValue();
        			boolean b=true; //房间是否没有选定款项 默认为true
        			for(int j=0;j<tblSelectedFund.getRowCount();j++){
        				IRow r=tblSelectedFund.getRow(j);
        				if(r.getCell("room").getValue() instanceof RoomInfo)
        				if(r.getCell("room").getValue()!=null){
        					RoomInfo info=(RoomInfo)r.getCell("room").getValue();
        					if(room.getId().toString().equals(info.getId().toString())){
        						b=false;
        						break;
        					}
        				}
        				else{
	        				if(r.getCell("tenancyBill").getUserObject()!=null){//如果房间对应合同 选定了其他款项 
	        					TenancyBillInfo info=(TenancyBillInfo)r.getCell("tenancyBill").getUserObject();
	        					if(tenInfo.getId().toString().equals(info.getId().toString())){
	        						b=false;
	        						break;
	        					}
	        				}
        				}
        			}
        			if(b){ //如果 比较完毕 没有任何匹配 则提示如下
        				MsgBox.showError("房间"+room.getName()+"没有选定任何款项！");
        	    		abort();
        			}
        			b=true;
        		}
			}
			else{
	    		MsgBox.showError("已选定款项为空！");
	    		abort();
	    	}
    	}
    	else{
    		MsgBox.showError("没有选择任何房间!");
    		abort();
    	}
    	if(tblSelectedFund.getRowCount()>0){
    		BigDecimal appAmount=new BigDecimal("0.00");
			BigDecimal actAmount=new BigDecimal("0.00");
//			BigDecimal derateAmount=new BigDecimal("0.00");
			if(tblSelectedFund.getCell(tblSelectedFund.getRowCount()-1, "appAmount").getValue()!=null){
	    		appAmount=new BigDecimal(tblSelectedFund
	    				.getCell(tblSelectedFund.getRowCount()-1, "appAmount").getValue().toString());
			}
			if(tblSelectedFund.getCell(tblSelectedFund.getRowCount()-1, "actAmount").getValue()!=null){
	    		actAmount=new BigDecimal(tblSelectedFund
	    				.getCell(tblSelectedFund.getRowCount()-1, "actAmount").getValue().toString());
	    		if(actAmount.compareTo(FDCHelper.ZERO)<0){
	    			MsgBox.showError("已收金额为负数，数据错误！");
					abort();
	    		}
    		}
//			if(tblSelectedFund.getCell(tblSelectedFund.getRowCount()-1, "derateAmount").getValue()!=null){
//	    		derateAmount=new BigDecimal(tblSelectedFund
//	    				.getCell(tblSelectedFund.getRowCount()-1, "derateAmount").getValue().toString());
//	    		if(derateAmount.compareTo(FDCHelper.ZERO)<0){
//	    			MsgBox.showError("减免金额为负数，数据错误！");
//					abort();
//	    		}
//			}
    		if(tblReceiveNote.getRowCount()>0){
    			for(int i=0;i<tblReceiveNote.getRowCount()-1;i++){
    				if(tblReceiveNote.getCell(i,"settlementType").getValue()==null){
    					MsgBox.showError("结算方式为空！");
    	        		abort();
    				}
    				if(tblReceiveNote.getCell(i,"receiveAmount").getValue()==null){
    					MsgBox.showError("收款金额为空！");
    					abort();
    				}
    				else{
    					if(new BigDecimal(tblReceiveNote.getCell(i,"receiveAmount").getValue().toString()).compareTo(FDCHelper.ZERO)<=0){
    						MsgBox.showError("收款金额必须大于0！");
        					abort();
    					}
    				}
    			}
    			BigDecimal receiveAmount=new BigDecimal(tblReceiveNote
        				.getCell(tblReceiveNote.getRowCount()-1, "receiveAmount").getValue().toString());
    			if(receiveAmount.compareTo(appAmount.subtract(actAmount))!=0){
    				MsgBox.showError("收款金额录入错误，收款金额录入值应等于已选择款项中的应收金额减去已收金额！");
            		abort();
    			}
    		}
    		else{
    			MsgBox.showError("收款记录为空！");
        		abort();
    		}
    	}
    	else{
    		MsgBox.showError("已选定款项为空！");
    		abort();
    	}
    }

    /**
     * 房间添加行
     */
    public void actionRoomAddLine_actionPerformed(ActionEvent e) throws Exception
    {
        tblRoom.addRow();
    }

    /**
     * 房间删除行
     */
    public void actionRoomDeleteLine_actionPerformed(ActionEvent e) throws Exception
    {
        if(tblRoom.getRowCount()>0){
        	int index=tblRoom.getSelectManager().getActiveRowIndex();
        	if(index>=0){
        		if(tblSelectedFund.getRowCount()>0){
        			for(int i=0;i<tblSelectedFund.getRowCount();i++){
        				if(tblSelectedFund.getCell(i,"revListId").getValue()!=null){
        					for(int j=0;j<tblSelectFund.getRowCount();j++){
        						if(tblSelectFund.getCell(j,"revListId").getValue()!=null){
        							if(tblSelectedFund.getCell(i,"revListId").getValue().toString().equals(tblSelectFund.getCell(j,"revListId").getValue().toString())){
        								tblSelectedFund.removeRow(i);
        								i--;
        								break;
        							}
        						}
        					}
        				}
        			}
        			if(tblSelectedFund.getRowCount()>0){// 这里是暂时删除合计行
        				tblSelectedFund.removeRow(tblSelectedFund.getRowCount()-1);
        			}
        			addAggregate(tblSelectedFund,new String[]{"appAmount","actAmount","derateAmount"});
        			if(tblSelectedFund.getRowCount()==1){
        				tblSelectedFund.removeRows();
        			}
        		}
        		tblRoom.removeRow(index);
        		if(tblSelectFund.getRowCount()>0){
        			tblSelectFund.removeRows();
        		}
        		if(tblRoom.getRowCount()>0){
        			tblRoom.getSelectManager().select(0, 0);
        			IRow row=getSelectRow(tblRoom);
        	    	if(row.getCell("room").getValue()!=null){
        	    		RoomInfo room=(RoomInfo)row.getCell("room").getValue();
        	    		room=getRoomInfo(room.getId().toString());
        	    		refreshTblSelectFund(room);
        	    	}
        		}
        	}
        }
    }

    /**
     * 选择款项确定
     */
    public void actionSelectFundConfirm_actionPerformed(ActionEvent e) throws Exception
    {
    	IRow roomRow=getSelectRow(tblRoom);
        if(tblSelectFund.getRowCount()>0){
        	if(tblSelectedFund.getRowCount()>0){//每次点击确定时 应该清除掉该房间已经选定的款项
        		for(int i=0;i<tblSelectedFund.getRowCount();i++){
        			if(tblSelectedFund.getCell(i,"revListId").getValue()!=null){
        				for(int j=0;j<tblSelectFund.getRowCount();j++){
        					if(tblSelectFund.getCell(j,"revListId").getValue()!=null){
        						if(tblSelectedFund.getCell(i,"revListId").getValue().toString().equals(tblSelectFund.getCell(j,"revListId").getValue().toString())){
        							tblSelectedFund.removeRow(i);
        							i--;
        							break;
        						}
        					}
        				}
        			}
        		}
        		if(tblSelectedFund.getRowCount()>0){// 这里是暂时删除合计行
    				tblSelectedFund.removeRow(tblSelectedFund.getRowCount()-1);
    			}
    			addAggregate(tblSelectedFund,new String[]{"appAmount","actAmount","derateAmount"});
    			if(tblSelectedFund.getRowCount()==1){
    				tblSelectedFund.removeRows();
    			}
        	}
        	if(tblSelectedFund.getRowCount()>0){// 这里是暂时删除合计行
				tblSelectedFund.removeRow(tblSelectedFund.getRowCount()-1);
			}
        	for(int i=0;i<tblSelectFund.getRowCount();i++){
        		IRow row = tblSelectFund.getRow(i);
        		boolean isSelect=Boolean.valueOf(row.getCell("select").getValue().toString()).booleanValue();
        		if(isSelect){//这里把 款项选择表中 选择行 加载到 已选择款项中
        			IRow selectFundRow=tblSelectedFund.addRow();
        			//这里避免再次RPC 直接从 房间表里取数
        			if(roomRow.getCell("tenancyBill").getValue()!=null){
        				selectFundRow.getCell("tenancyBill").setValue(roomRow.getCell("tenancyBill").getValue());
        				selectFundRow.getCell("tenancyBill").setUserObject(roomRow.getCell("tenancyBill").getUserObject());
        			}
        			if(roomRow.getCell("customer").getValue()!=null){
        				selectFundRow.getCell("customer").setValue(roomRow.getCell("customer").getValue());
        			}
        			
        			if(row.getCell("room").getValue()!=null){
        				selectFundRow.getCell("room").setValue(row.getCell("room").getValue());
        			}
        			if(row.getCell("revListId").getValue()!=null){
        				selectFundRow.getCell("revListId").setValue(row.getCell("revListId").getValue());
        			}
        			if(row.getCell("moneyType").getValue()!=null){
        				selectFundRow.getCell("moneyType").setValue(row.getCell("moneyType").getValue());
        				selectFundRow.getCell("moneyType").setUserObject(row.getCell("moneyType").getUserObject());
        			}
        			if(row.getCell("startDate").getValue()!=null){
        				selectFundRow.getCell("startDate").setValue(row.getCell("startDate").getValue());
        			}
        			if(row.getCell("endDate").getValue()!=null){
        				selectFundRow.getCell("endDate").setValue(row.getCell("endDate").getValue());
        			}
        			if(row.getCell("deratePeriod").getValue()!=null){
        				selectFundRow.getCell("deratePeriod").setValue(row.getCell("deratePeriod").getValue());
        			}
        			if(row.getCell("appDate").getValue()!=null){
        				selectFundRow.getCell("appDate").setValue(row.getCell("appDate").getValue());
        			}
        			if(row.getCell("appAmount").getValue()!=null){
        				selectFundRow.getCell("appAmount").setValue(row.getCell("appAmount").getValue());
        			}
        			if(row.getCell("actAmount").getValue()!=null){
        				selectFundRow.getCell("actAmount").setValue(row.getCell("actAmount").getValue());
        			}
        			if(row.getCell("derateAmount").getValue()!=null){
        				selectFundRow.getCell("derateAmount").setValue(row.getCell("derateAmount").getValue());
        			}
        		}
        	}
        	//这里把合计行又添加上
        	addAggregate(tblSelectedFund,new String[]{"appAmount","actAmount","derateAmount"});
        }
    }
    /**
     * 给指定table的指定列集合 添加统计总合的行 列里所放的值必须为数值类型而且不能包含第一列
     * @param table
     * @param keys
     */
    public void addAggregate(KDTable table,String[] keys){
    	IRow row=table.addRow();
    	row.getCell(0).setValue("合计:");
    	for(int i=0;i<keys.length;i++){
    		BigDecimal amount=new BigDecimal(0);
	    	for(int j=0;j<table.getRowCount()-1;j++){//这里因为添加了合计行 所以要减掉1
	    		IRow r=table.getRow(j);
	    		if(r.getCell(keys[i]).getValue()!=null){
	    			amount=amount.add(new BigDecimal(r.getCell(keys[i]).getValue().toString()));
	    		}
	    	}
//	    	if(amount.compareTo(new BigDecimal(0))>0){
	    		row.getCell(keys[i]).setValue(amount);
//	    	}
	    	row.getStyleAttributes().setBackground(Color.YELLOW);
	    	row.getStyleAttributes().setLocked(true);
    	}
    }

    /**
     * 收款记录添加行
     */
    public void actionReceiveAddLine_actionPerformed(ActionEvent e) throws Exception
    {
    	if(tblReceiveNote.getRowCount()>0){//如果新增行时 已经有记录 就先移除合计行
    		tblReceiveNote.removeRow(tblReceiveNote.getRowCount()-1);
    	}
    	tblReceiveNote.addRow();
    	for(int i=0;i<tblReceiveNote.getRowCount();i++){//添加默认序号 和收款人
    		IRow row=tblReceiveNote.getRow(i);
    		row.getCell("seq").setValue(new Integer(i+1));
//    		row.getCell("receiver").setValue(SysContext.getSysContext().getCurrentUserInfo().getName());
    	}
    	addAggregate(tblReceiveNote,new String[]{"receiveAmount"});//新增行后添加合计行
    }

    /**
     * 收款记录删除行
     */
    public void actionReceiveDeleteLine_actionPerformed(ActionEvent e) throws Exception
    {
    	if(tblReceiveNote.getRowCount()>0){
    		int index=tblReceiveNote.getSelectManager().getActiveRowIndex();
        	if(index==tblReceiveNote.getRowCount()-1){
        		MsgBox.showError("不能删除合计行！");
        		abort();
        	}
        	if(index>=0){
        		tblReceiveNote.removeRow(index);
        		tblReceiveNote.removeRow(tblReceiveNote.getRowCount()-1);//这里去掉合计行 然后重新再添加 
        		if(tblReceiveNote.getRowCount()>0){
        			addAggregate(tblReceiveNote,new String[]{"receiveAmount"});
        		}
        	}
    	}
    }

    /**
     * 选定款项删除行
     */
    public void actionSelectedFundDeleteLine_actionPerformed(ActionEvent e) throws Exception
    {
        if(tblSelectedFund.getRowCount()>0){
        	int index=tblSelectedFund.getSelectManager().getActiveRowIndex();
        	if(index==tblSelectedFund.getRowCount()-1){
        		MsgBox.showError("不能删除合计行！");
        		abort();
        	}
        	if(index>=0){
        		tblSelectedFund.removeRow(index);
        		tblSelectedFund.removeRow(tblSelectedFund.getRowCount()-1);//这里去掉合计行 然后重新再添加 
        		if(tblSelectedFund.getRowCount()>0){
        			addAggregate(tblSelectedFund,new String[]{"appAmount","actAmount","derateAmount"});
        		}
        	}
        }
    }

	protected IObjectValue createNewData() {
		FDCReceiveBillInfo info=new FDCReceiveBillInfo();
		return info;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return FDCReceiveBillFactory.getRemoteInstance();
	}

}