/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.event.KDTActiveCellEvent;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.swing.KDCheckBox;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCSQLFacadeFactory;
import com.kingdee.eas.fdc.sellhouse.InvestorHouseFactory;
import com.kingdee.eas.fdc.sellhouse.InvestorHouseInfo;
import com.kingdee.eas.fdc.sellhouse.InvestorHouseLinkmanEntrysCollection;
import com.kingdee.eas.fdc.sellhouse.InvestorHouseLinkmanEntrysInfo;
import com.kingdee.eas.fdc.sellhouse.InvestorHouseTrackRecordCollection;
import com.kingdee.eas.fdc.sellhouse.InvestorHouseTrackRecordInfo;
import com.kingdee.eas.fdc.sellhouse.LinkmanEntryInfo;
import com.kingdee.eas.fdc.sellhouse.LinkmanTypeEnum;
import com.kingdee.eas.fdc.sellhouse.OrderSourceClassEnum;
import com.kingdee.eas.fdc.sellhouse.OwnerShipNewEnum;
import com.kingdee.eas.fdc.sellhouse.OwnerShipStateEnum;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SexEnum;
import com.kingdee.eas.fdc.sellhouse.TrackRecordInfo;
import com.kingdee.eas.fdc.tenancy.client.TenancyClientHelper;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.bos.metadata.query.util.CompareType;

/**
 * output class name
 */
public class InvestorHouseEditUI extends AbstractInvestorHouseEditUI
{
	private static final Logger logger = CoreUIObject.getLogger(InvestorHouseEditUI.class);
	
	UserInfo userInfo = SysContext.getSysContext().getCurrentUserInfo();
	public InvestorHouseEditUI() throws Exception
	{
		super();
	}
	
	public void loadFields() {
		super.loadFields();
		InvestorHouseInfo info = (InvestorHouseInfo)this.editData;
		this.pkCreateTime.setValue(info.getCreateTime());
		this.tblLinkPerson.removeRows();
		InvestorHouseLinkmanEntrysCollection entryColl = info.getEntrys();
		for(int i=0;i<entryColl.size();i++)
		{
			InvestorHouseLinkmanEntrysInfo entryInfo = entryColl.get(i);
			this.tblLinkPerson.checkParsed();
			addRowLinkmanEntrys(entryInfo);
		}
		this.tblTrackRecord.removeRows();
		InvestorHouseTrackRecordCollection trackRecordColl = info.getTrackRecords();
		for(int i=0;i<trackRecordColl.size();i++)
		{
			InvestorHouseTrackRecordInfo trackRecordInfo = trackRecordColl.get(i);
			this.tblTrackRecord.checkParsed();
			addRowTrackRecord(trackRecordInfo);
		}
		
	}
	
	
	private void addRowTrackRecord(InvestorHouseTrackRecordInfo trackRecordInfo)
	{
		IRow row = this.tblTrackRecord.addRow();
		row.setUserObject(trackRecordInfo);
		row.getCell("number").setValue(trackRecordInfo.getNumber());
		row.getCell("trackDate").setValue(trackRecordInfo.getTrackDate());
		row.getCell("trackPerson").setValue(trackRecordInfo.getTrackPerson().getName());
		row.getCell("trackPerson").setUserObject(trackRecordInfo.getTrackPerson());
		row.getCell("createTime").setValue(trackRecordInfo.getCreateTime());
		row.getCell("description").setValue(trackRecordInfo.getDescription());
	}
	
	private void addRowLinkmanEntrys(InvestorHouseLinkmanEntrysInfo entryInfo)
	{
		IRow row = this.tblLinkPerson.addRow();
		row.setUserObject(entryInfo);
		//row.setUserObject(entryInfo);
		row.getCell("isOwner").setValue(new Boolean(entryInfo.isIsOwner()));
		//设置这个是为了单选框选择事件判断
		if(STATUS_VIEW.equals(this.getOprtState()))
		{
			row.getCell("isOwner").getStyleAttributes().setLocked(true);
		}else
		{
			row.getCell("isOwner").getStyleAttributes().setLocked(false);
		}		
		row.getCell("linkmanType").setValue(entryInfo.getLinkmanType());
		row.getCell("name").setValue(entryInfo.getName());
		row.getCell("sex").setValue(entryInfo.getSex());
		row.getCell("phone").setValue(entryInfo.getPhone());
		row.getCell("paperName").setValue(entryInfo.getPaperName());
		row.getCell("paperNumber").setValue(entryInfo.getPaperNumber());
	}
	
	public void storeFields()
	{
		InvestorHouseInfo info = (InvestorHouseInfo)this.editData;
		info.getEntrys().clear();
		for(int i=0;i<tblLinkPerson.getRowCount();i++)
		{
			IRow row = this.tblLinkPerson.getRow(i);
			InvestorHouseLinkmanEntrysInfo entrysInfo = (InvestorHouseLinkmanEntrysInfo)row.getUserObject();
			if(entrysInfo==null){
				entrysInfo = new InvestorHouseLinkmanEntrysInfo();
			}			
			entrysInfo.setIsOwner(((Boolean)row.getCell("isOwner").getValue()).booleanValue());
			entrysInfo.setLinkmanType((LinkmanTypeEnum)row.getCell("linkmanType").getValue());
			entrysInfo.setName((String)row.getCell("name").getValue());
			entrysInfo.setSex((SexEnum)row.getCell("sex").getValue());
			entrysInfo.setPhone((String)row.getCell("phone").getValue());
			entrysInfo.setPaperName((String)row.getCell("paperName").getValue());
			entrysInfo.setPaperNumber((String)row.getCell("paperNumber").getValue());
			info.getEntrys().add(entrysInfo);
		}
		info.getTrackRecords().clear();
		for(int i=0;i<tblTrackRecord.getRowCount();i++)
		{
			IRow row = this.tblTrackRecord.getRow(i);
			InvestorHouseTrackRecordInfo trackRecordInfo = (InvestorHouseTrackRecordInfo)row.getUserObject();
			trackRecordInfo.setNumber((String)row.getCell("number").getValue());
			trackRecordInfo.setTrackDate((Date)row.getCell("trackDate").getValue());
			trackRecordInfo.setTrackPerson((UserInfo)row.getCell("trackPerson").getUserObject());
//			Date create = (Date)row.getCell("createTime").getValue();
//			trackRecordInfo.setCreateTime(new Timestamp(create.getTime()));
			trackRecordInfo.setCreateTime((Timestamp)row.getCell("createTime").getValue());
			trackRecordInfo.setDescription((String)row.getCell("description").getValue());
			info.getTrackRecords().add(trackRecordInfo);
		}
		super.storeFields();
	}
	
	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("*");
		sels.add("creator.*");
		sels.add("habitationArea.*");
		sels.add("salesman.*");
		sels.add("buildingProperty.*");
		sels.add("booker.*");
		sels.add("informationSource.*");
		sels.add("entrys.*");
		sels.add("trackRecords.*");
		sels.add("trackRecords.trackPerson.*");
		return sels;
	}
	
	public void onLoad() throws Exception {
		super.onLoad();
		init();
		initTblLinkPerson();
		if(STATUS_VIEW.equals(this.getOprtState()))
		{
			this.btnAddLine.setEnabled(false);
			this.btnDelLine.setEnabled(false);
		}else if(STATUS_EDIT.equals(this.getOprtState()))
		{
			this.btnAddLine.setEnabled(true);
			this.btnDelLine.setEnabled(true);
			if(this.tblTrackRecord.getRowCount()>0)
			{
				for(int i=0;i<tblTrackRecord.getRowCount();i++)
				{
					IRow row = tblTrackRecord.getRow(i);
					//row.getCell("trackDate").getStyleAttributes().setLocked(true);
					row.getCell("trackPerson").getStyleAttributes().setLocked(true);
					row.getCell("createTime").getStyleAttributes().setLocked(true);
				}
			}
		}
		this.storeFields();
		initOldData(this.editData);
	}
	
	private void init()
	{
		this.btnPrint.setVisible(false);
		this.btnPrintPreview.setVisible(false);
		this.actionCancel.setVisible(false);
		this.actionRemove.setVisible(false);
		this.btnCancel.setVisible(false);
		this.btnCancelCancel.setVisible(false);
		this.actionSave.setVisible(false);
		this.btnCopy.setVisible(false);
		this.txtName.setRequired(true);
		this.txtNumber.setRequired(true);
		this.prmtCreator.setEnabled(false);
		this.pkCreateTime.setEnabled(false);
		UserInfo user = (UserInfo)this.getUIContext().get("user");
		if(user==null)
		{
			this.prmtSalesman.setValue(null);
		}else
		{
			this.prmtSalesman.setValue(user);
		}
		
		//控制字符录入的长度，默认为80.modified by wenyaowei 2009-06-08
		this.txtNumber.setMaxLength(80);
		this.txtName.setMaxLength(80);
		this.txtOrderLocation.setMaxLength(80);
		this.txtOrderLocation.setMaxLength(80);
		this.txtAssignBuilding.setMaxLength(80);
		this.txtAssignRoom.setMaxLength(80);
		this.txtAreaDescription.setMaxLength(80);
		this.txtPriceDescription.setMaxLength(80);
		this.txtChargeComplexion.setMaxLength(80);
		this.txtWuyeActuality.setMaxLength(80);
		this.txtDescription.setMaxLength(80);
	    this.setF7UserViewAsOnload();
	}
	
	
	/**
	 * 设置营销顾问和登记人的认证管理员过滤
	 * 
	 * @author yaowei_wen
	 * @throws BOSException 
	 * @throws EASBizException 
	 */
	private void setF7UserViewAsOnload()
	{	
		//营销顾问和登记人根据左边营销单元树过滤  bt339998 by lijun
		EntityViewInfo view = null;;
		try {
			view = CommerceHelper.getPermitSalemanView(null);
		} catch (Exception e) {
			logger.error(e.getMessage());
			this.handleException(e);
		}
//		FilterInfo filter = new FilterInfo();
//		view.setFilter(filter);	
//		filter.getFilterItems().add(new FilterItemInfo("type", "60",CompareType.NOTEQUALS));
		this.prmtSalesman.setEntityViewInfo(view);
		this.prmtBooker.setEntityViewInfo(view);
	}
	
	
	private void initTblLinkPerson()
	{
		this.tblLinkPerson.checkParsed();
		this.tblLinkPerson
		.setActiveCellStatus(KDTStyleConstants.ACTIVE_CELL_EDIT);
		
		KDCheckBox chkBox = new KDCheckBox();
		ICellEditor checkBox = new KDTDefaultCellEditor(chkBox);
		this.tblLinkPerson.getColumn("isOwner").setEditor(checkBox);
		
//		设置联系人类型为枚举类型
		KDComboBox linkmanTypeComboBox =  new KDComboBox();
		List list2 = LinkmanTypeEnum.getEnumList();
		for(int i=0;i<list2.size();i++)
		{
			linkmanTypeComboBox.addItem(list2.get(i));
		}
		KDTDefaultCellEditor linkmanTypeComboBoxEditer = new KDTDefaultCellEditor(linkmanTypeComboBox);
		this.tblLinkPerson.getColumn("linkmanType").setEditor(linkmanTypeComboBoxEditer);
		
		KDTextField textField = new KDTextField();
		textField.setMaxLength(80);
		ICellEditor txtEditor = new KDTDefaultCellEditor(textField);
		this.tblLinkPerson.getColumn("name").setEditor(txtEditor);
		
//		设置性别为枚举类型
		KDComboBox sexComboBox =  new KDComboBox();
		List list = SexEnum.getEnumList();
		for(int i=0;i<list.size();i++)
		{
			sexComboBox.addItem(list.get(i));
		}
		KDTDefaultCellEditor sexComboBoxEditer = new KDTDefaultCellEditor(sexComboBox);
		this.tblLinkPerson.getColumn("sex").setEditor(sexComboBoxEditer);
		
		KDTextField phoneField = new KDTextField();
		phoneField.setMaxLength(80);
		ICellEditor phoneEditor = new KDTDefaultCellEditor(phoneField);
		this.tblLinkPerson.getColumn("phone").setEditor(phoneEditor);
		
		KDTextField paperName = new KDTextField();
		paperName.setMaxLength(80);
		ICellEditor paperNameEditor = new KDTDefaultCellEditor(paperName);
		this.tblLinkPerson.getColumn("paperName").setEditor(paperNameEditor);
		
		KDTextField paperNumber = new KDTextField();
		paperNumber.setMaxLength(80);
		ICellEditor paperNumberEditor = new KDTDefaultCellEditor(paperNumber);
		this.tblLinkPerson.getColumn("paperNumber").setEditor(paperNumberEditor);
	}
	
	
	
	protected IObjectValue createNewData() {
		InvestorHouseInfo info = new InvestorHouseInfo();
		info.setOrderSourceClass(OrderSourceClassEnum.Sell);
		info.setOwnerShipState(OwnerShipStateEnum.ReplaceAssignGlebe);
		info.setOwnerShipNew(OwnerShipNewEnum.OwnerHold);
		info.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
		info.setCreator(SysContext.getSysContext().getCurrentUserInfo());
		try {
			info.setBizDate(FDCSQLFacadeFactory.getRemoteInstance()
					.getServerTime());
			info.setCreateTime(FDCSQLFacadeFactory.getRemoteInstance()
					.getServerTime());
		} catch (BOSException e) {
			logger.error(e.getMessage());
			this.handleException(e);
		}
		info.setCreator(userInfo);
		this.prmtCreator.setEnabled(false);
		this.pkCreateTime.setEnabled(false);
		this.btnAddLine.setEnabled(true);
		this.btnDelLine.setEnabled(true);
		return info;
	}
	
	public void actionAddLine_actionPerformed(ActionEvent e) throws Exception {
		if(this.kDTabbedPane1.getSelectedIndex() == 0){
			InvestorHouseLinkmanEntrysInfo entry = new InvestorHouseLinkmanEntrysInfo();
			entry.setIsOwner(false);
			entry.setLinkmanType(LinkmanTypeEnum.Individual);
//			entry.setSex(SexEnum.Mankind);
			this.addLinkPersonRow(entry);
		} 
		else if(this.kDTabbedPane1.getSelectedIndex() == 1){
			InvestorHouseTrackRecordInfo info = new InvestorHouseTrackRecordInfo();
			info.setCreateTime(FDCSQLFacadeFactory.getRemoteInstance()
					.getServerTime());
			info.setTrackDate(FDCSQLFacadeFactory.getRemoteInstance()
					.getServerTime());
			info.setTrackPerson(SysContext.getSysContext().getCurrentUserInfo());
			this.addTrackRecordRow(info);
		}
		super.actionAddLine_actionPerformed(e);
	}
	
	private void addLinkPersonRow(InvestorHouseLinkmanEntrysInfo entry)
	{
		IRow row = tblLinkPerson.addRow();
		//row.setUserObject(entry);
		if(entry!=null)
		{
			row.getCell("isOwner").setValue(new Boolean(entry.isIsOwner()));
			row.getCell("linkmanType").setValue(entry.getLinkmanType());
			//row.getCell("sex").setValue(entry.getSex());
		}
	}
	
	private void addTrackRecordRow(InvestorHouseTrackRecordInfo info)
	{
		IRow row = tblTrackRecord.addRow();
		row.setUserObject(info);
		if(info!=null)
		{
			row.getCell("number").setValue(info.getNumber());
			row.getCell("trackDate").setValue(info.getTrackDate());
			//row.getCell("trackDate").getStyleAttributes().setLocked(true);
			row.getCell("trackPerson").setValue(info.getTrackPerson().getName());
			row.getCell("trackPerson").setUserObject(info.getTrackPerson());
			row.getCell("trackPerson").getStyleAttributes().setLocked(true);
			row.getCell("createTime").setValue(info.getCreateTime());
			row.getCell("createTime").getStyleAttributes().setLocked(true);
			row.getCell("description").setValue(info.getDescription());
		}
	}
	
	public void actionDelLine_actionPerformed(ActionEvent e) throws Exception {
		super.actionDelLine_actionPerformed(e);
		if(this.kDTabbedPane1.getSelectedIndex() == 0){
			int activeRowIndex = this.tblLinkPerson.getSelectManager()
			.getActiveRowIndex();
			if (activeRowIndex == -1) {
				activeRowIndex = this.tblLinkPerson.getRowCount();
			}
			this.tblLinkPerson.removeRow(activeRowIndex);
		}else if(this.kDTabbedPane1.getSelectedIndex() == 1){
			int activeRowIndex = this.tblTrackRecord.getSelectManager()
			.getActiveRowIndex();
			if (activeRowIndex == -1) {
				activeRowIndex = this.tblTrackRecord.getRowCount();
			}
			this.tblTrackRecord.removeRow(activeRowIndex);
		}
		
	}
	
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		super.actionEdit_actionPerformed(e);
		this.prmtCreator.setEnabled(false);
		this.pkCreateTime.setEnabled(false);
		this.btnAddLine.setEnabled(true);
		this.btnDelLine.setEnabled(true);
		if(this.tblLinkPerson.getRowCount()>0)
		{
			for(int i=0;i<tblLinkPerson.getRowCount();i++)
			{
				IRow row = tblLinkPerson.getRow(i);
				row.getCell("isOwner").getStyleAttributes().setLocked(false);
			}
		}
		if(this.tblTrackRecord.getRowCount()>0)
		{
			for(int i=0;i<tblTrackRecord.getRowCount();i++)
			{
				IRow row = tblTrackRecord.getRow(i);
				//row.getCell("trackDate").getStyleAttributes().setLocked(true);
				row.getCell("trackPerson").getStyleAttributes().setLocked(true);
				row.getCell("createTime").getStyleAttributes().setLocked(true);
			}
		}	
	}
	protected void tblLinkPerson_activeCellChanged(KDTActiveCellEvent e) throws Exception {
		super.tblLinkPerson_activeCellChanged(e);
		TenancyClientHelper.tableName_activeCellChanged(e,this.tblLinkPerson);
		int rowCount = e.getRowIndex();
		int cloumnCount = e.getColumnIndex();
		if(rowCount == -1 || cloumnCount ==-1)
		{
			return;
		}
		Object object = tblLinkPerson.getRow(rowCount).getCell("linkmanType").getValue();
		if(object instanceof LinkmanTypeEnum)
		{
			if(object.equals(LinkmanTypeEnum.Enterprise))
			{
				tblLinkPerson.getRow(rowCount).getCell("sex").setValue(null);
				tblLinkPerson.getRow(rowCount).getCell("sex").getStyleAttributes().setLocked(true);
				//tblLinkPerson.getRow(rowCount).getCell("sex").getStyleAttributes().setBackground(Color.WHITE);
			}else
			{
				tblLinkPerson.getRow(rowCount).getCell("sex").getStyleAttributes().setLocked(false);
				//tblLinkPerson.getRow(rowCount).getCell("sex").getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);			
			}
		}
	}
	
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		super.actionSubmit_actionPerformed(e);
		if(this.tblTrackRecord.getRowCount()>0)
		{
			for(int i=0;i<tblTrackRecord.getRowCount();i++)
			{
				IRow row = tblTrackRecord.getRow(i);
				//row.getCell("trackDate").getStyleAttributes().setLocked(true);
				row.getCell("trackPerson").getStyleAttributes().setLocked(true);
				row.getCell("createTime").getStyleAttributes().setLocked(true);
			}
		}
	}
	protected ICoreBase getBizInterface() throws Exception {
		return InvestorHouseFactory.getRemoteInstance();
	}
	
	protected void verifyInput(ActionEvent e) throws Exception {
		super.verifyInput(e);
		if(this.tblTrackRecord.getRowCount()>0)
		{
			for(int i=0;i<this.tblTrackRecord.getRowCount();i++)
			{
				IRow row = tblTrackRecord.getRow(i);
				if((String)row.getCell("number").getValue()!=null && ((String)row.getCell("number").getValue()).length()>80)
				{
					MsgBox.showInfo("跟进编码长度不要超过80个字符!");
					this.abort();
				}if((String)row.getCell("description").getValue()!=null && ((String)row.getCell("description").getValue()).length()>255)
				{
					MsgBox.showInfo("跟进说明长度不要超过255!");
					this.abort();
				}
			}
		}
		
	}
	
}