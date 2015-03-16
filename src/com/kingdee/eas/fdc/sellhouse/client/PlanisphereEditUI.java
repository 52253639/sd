/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JComponent;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.reportone.r1.common.designercore.event.StateChangedListener;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDPromptBox;
import com.kingdee.bos.ctrl.swing.KDSpinner;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.StringUtils;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.sellhouse.BuildingCollection;
import com.kingdee.eas.fdc.sellhouse.BuildingFactory;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.PlanisphereElementEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PlanisphereElementEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PlanisphereElementEnum;
import com.kingdee.eas.fdc.sellhouse.PlanisphereFactory;
import com.kingdee.eas.fdc.sellhouse.PlanisphereInfo;
import com.kingdee.eas.fdc.sellhouse.PlanisphereTypeEnum;
import com.kingdee.eas.fdc.sellhouse.RoomCollection;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectCollection;
import com.kingdee.eas.fdc.sellhouse.SellProjectFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class PlanisphereEditUI extends AbstractPlanisphereEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(PlanisphereEditUI.class);
    
    String allOrgUnitIds = "null";   //从序时簿传过来的组织id串
    String allProjectIds = "null";     //从序时簿传过来的销售项目id串
    private TableDrawManager thisPicTable = new TableDrawManager();
    private Map kdSpiListenersMap = new HashMap();
    
    
    /**
     * output class constructor
     */
    public PlanisphereEditUI() throws Exception
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

	protected ICoreBase getBizInterface() throws Exception {
		return PlanisphereFactory.getRemoteInstance();
	}

	protected KDTable getDetailTable() {
		return this.tblElementEntry;
	}

	protected void attachListeners() {
		this.addDataChangeListener(this.comboPtype);
		this.addDataChangeListener(this.prmtOrgUnit);
		this.addDataChangeListener(this.prmtSellProject);
		this.addDataChangeListener(this.prmtBuilding);
		addKdSpiStateChangeListener(this.kDSpiUnit);
		addKdSpiStateChangeListener(this.kDSpiFloor);
	}

	protected void detachListeners() {
		this.removeDataChangeListener(this.comboPtype);
		this.removeDataChangeListener(this.prmtOrgUnit);
		this.removeDataChangeListener(this.prmtSellProject);
		this.removeDataChangeListener(this.prmtBuilding);
		removeKdSpiStateChangeListener(this.kDSpiUnit);
		removeKdSpiStateChangeListener(this.kDSpiFloor);
	}

		
    private void removeKdSpiStateChangeListener(JComponent com) {
		EventListener[] listeners = null;	
 		if(com instanceof KDSpinner){
			listeners = com.getListeners(ChangeListener.class);	
    		for(int i=0;i<listeners.length;i++){
    			((KDSpinner)com).removeChangeListener((ChangeListener)listeners[i]);
    		}
    	}
		
		if(listeners!=null && listeners.length>0){
			kdSpiListenersMap.put(com,listeners );
		}
    }
	private void addKdSpiStateChangeListener(JComponent com) {
    	EventListener[] listeners = (EventListener[] )kdSpiListenersMap.get(com);    	
    	if(listeners!=null && listeners.length>0){
	    	if(com instanceof KDSpinner){
	    		for(int i=0;i<listeners.length;i++){
	    			((KDSpinner)com).addChangeListener((ChangeListener)listeners[i]);
	    		}
	    	}
    	}
	}    	
	
	
	protected KDTextField getNumberCtrl() {
		return this.txtNumber;
	}

	
	public void onLoad() throws Exception {
		super.onLoad();
		
		SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
		if(!saleOrg.isIsBizUnit()) {
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
			
			setBtnEnabledStatus(false);
		}
		
		
		//设置隐藏菜单和工具按钮
		this.menuBiz.setVisible(false);
		this.menuWorkflow.setVisible(false);
		this.actionSubmit.setVisible(false);
		this.actionSubmitOption.setVisible(false);
		this.btnWorkFlowG.setVisible(false);
		this.btnAudit.setVisible(false);
		this.actionCreateFrom.setVisible(false);
		this.actionCopyFrom.setVisible(false);
		this.actionCopy.setVisible(false);
		
		this.actionAttachment.setVisible(false);		
		this.btnAuditResult.setVisible(false);
		this.btnMultiapprove.setVisible(false);
		this.btnNextPerson.setVisible(false);
		this.actionTraceDown.setVisible(false);
		this.actionTraceUp.setVisible(false);
		this.actionAddLine.setVisible(false);
		this.actionInsertLine.setVisible(false);
		this.actionRemoveLine.setVisible(false);
		
		this.btnLocationSure.setEnabled(false);
		if(this.oprtState.equals(OprtState.VIEW)) {
			this.btnAddElement.setEnabled(false);
			this.btnRemoveElement.setEnabled(false);
			this.btnLocationName.setEnabled(false);
			this.btnLocationOutLine.setEnabled(false);
			this.btnSetPicLayOut.setEnabled(false);
		}else{
			setTblElementColorColumnStyle();
		}
		
		
		//设置组织及销售项目的限制过滤     
		setPrmtOrgUnitViewInfo();
		setPrmtSellProjectViewInfo();
		setPrmtBuildingViewInfo();
		
		reflashPicTable();	
		
		setViewByPtype();
		this.storeFields();
		this.initOldData(this.editData);

	}
	
	/**
	 * 初始化元素分录表，并把给单元格付上颜色
	 */
	private void initialElementEntryTable() {
		this.tblElementEntry.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		this.tblElementEntry.getStyleAttributes().setLocked(true);
		
		this.tblElementEntry.getColumn("outLineBackColor").setEditor(CommerceHelper.getKDComboColorEditor());
		this.tblElementEntry.getColumn("outLineColor").setEditor(CommerceHelper.getKDComboColorEditor());
		this.tblElementEntry.getColumn("nameBackColor").setEditor(CommerceHelper.getKDComboColorEditor());
		this.tblElementEntry.getColumn("nameColor").setEditor(CommerceHelper.getKDComboColorEditor());
		
		//默认背景为白色，字体或轮廓为黑色
		for(int i=0;i<this.tblElementEntry.getRowCount();i++) {
			IRow row = this.tblElementEntry.getRow(i);
			Object userObject = row.getUserObject();
			if(userObject==null) {
				row.getCell("outLineBackColor").getStyleAttributes().setBackground(Color.WHITE);
				row.getCell("outLineColor").getStyleAttributes().setBackground(Color.BLACK);
				row.getCell("nameBackColor").getStyleAttributes().setBackground(Color.WHITE);
				row.getCell("nameColor").getStyleAttributes().setBackground(Color.BLACK);
			}else{
				PlanisphereElementEntryInfo elementInfo = (PlanisphereElementEntryInfo)userObject;
				row.getCell("outLineBackColor").setValue(new Color(elementInfo.getOutLineBackColor()));
				row.getCell("outLineBackColor").getStyleAttributes().setBackground(new Color(elementInfo.getOutLineBackColor()));
				row.getCell("outLineBackColor").getStyleAttributes().setFontColor(new Color(elementInfo.getOutLineBackColor()));
				row.getCell("outLineColor").setValue(new Color(elementInfo.getOutLineColor()));
				row.getCell("outLineColor").getStyleAttributes().setBackground(new Color(elementInfo.getOutLineColor()));				
				row.getCell("outLineColor").getStyleAttributes().setFontColor(new Color(elementInfo.getOutLineColor()));
				row.getCell("nameBackColor").setValue(new Color(elementInfo.getNameBackColor()));
				row.getCell("nameBackColor").getStyleAttributes().setBackground(new Color(elementInfo.getNameBackColor()));
				row.getCell("nameBackColor").getStyleAttributes().setFontColor(new Color(elementInfo.getNameBackColor()));
				row.getCell("nameColor").setValue(new Color(elementInfo.getNameColor()));
				row.getCell("nameColor").getStyleAttributes().setBackground(new Color(elementInfo.getNameColor()));
				row.getCell("nameColor").getStyleAttributes().setFontColor(new Color(elementInfo.getNameColor()));
			}
		}
	}
	
	
	
	//初始化元素表
	private void initialPicTable() {
		this.tblPicView.checkParsed();
		this.tblPicView.getStyleAttributes().setLocked(true);
		Integer rowCount = (Integer)this.txtCellVertiCount.getValue();
		Integer colCount = (Integer)this.txtCellHorizCount.getValue();
		Integer cellHeight = (Integer)this.txtCellHeigth.getValue();
		Integer cellWidth = (Integer)this.txtCellWidth.getValue();

	    thisPicTable.setTable(this.tblPicView);
	    thisPicTable.initTable(rowCount.intValue(),colCount.intValue(),cellHeight.intValue(),cellWidth.intValue(),TableDrawManager.VIEW_MODEL);
	    
	}
	
	
	/**刷新示意图的table显示图*/
	private void reflashPicTable() {
		initialPicTable();  //用初始化表来清除table中的内容
		PlanisphereElementEntryCollection elementColl = new PlanisphereElementEntryCollection();
		for(int i=0;i<this.tblElementEntry.getRowCount();i++) {
			IRow row = this.tblElementEntry.getRow(i);
			PlanisphereElementEntryInfo elementInfo = new PlanisphereElementEntryInfo();
			elementInfo.setType((PlanisphereElementEnum)row.getCell("type").getValue());
			elementInfo.setName((String)row.getCell("name").getValue());
			elementInfo.setOutLineLocationData((byte[])row.getCell("outLineLocationData").getValue());
			elementInfo.setNameLocationData((byte[])row.getCell("nameLocationData").getValue());
			Color outLineBackColor = (Color)row.getCell("outLineBackColor").getValue();
			if(outLineBackColor!=null) 
				elementInfo.setOutLineBackColor(outLineBackColor.getRGB());
			else
				elementInfo.setOutLineBackColor(Color.WHITE.getRGB());
			Color outLineColor = (Color)row.getCell("outLineColor").getValue();
			if(outLineColor!=null)
				elementInfo.setOutLineColor(outLineColor.getRGB());
			else
				elementInfo.setOutLineColor(Color.BLACK.getRGB());
			Color nameBackColor = (Color)row.getCell("nameBackColor").getValue();
			if(nameBackColor!=null)
				elementInfo.setNameBackColor(nameBackColor.getRGB());
			else
				elementInfo.setNameBackColor(Color.WHITE.getRGB());
			Color nameColor = (Color)row.getCell("nameColor").getValue();
			if(nameColor!=null) 
				elementInfo.setNameColor(nameColor.getRGB());
			else
				elementInfo.setNameColor(Color.BLACK.getRGB());
			
			elementColl.add(elementInfo);
		}
		thisPicTable.showElementCollection(elementColl);
		
	}
	
	
	
	private void setPrmtOrgUnitViewInfo() {		
		allOrgUnitIds = (String)this.getUIContext().get("AllOrgUnitIds");
		if(allOrgUnitIds==null) {  //--浏览的时候这些参数未传过来
			PlanisphereListUI listUI = (PlanisphereListUI)this.getUIContext().get("Owner");
			if(listUI!=null) allOrgUnitIds = (String)listUI.getUIContext().get("AllOrgUnitIds");
		}
		if(allOrgUnitIds==null) allOrgUnitIds = "null";
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id",allOrgUnitIds,CompareType.INCLUDE));
		view.setFilter(filter);
		this.prmtOrgUnit.setEntityViewInfo(view);
	}
	
	private void setPrmtSellProjectViewInfo() {
		/* 与组织有关，改为只显示允许看到的销售项目
		allProjectIds= (String)this.getUIContext().get("AllProjectIds");
		if(allProjectIds==null) {  //--浏览的时候这些参数未传过来
			PlanisphereListUI listUI = (PlanisphereListUI)this.getUIContext().get("Owner");
			if(listUI!=null) allProjectIds = (String)listUI.getUIContext().get("AllProjectIds");
		}
		if(allProjectIds!=null) {
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("id",allProjectIds,CompareType.INCLUDE));
			FullOrgUnitInfo orgInfo = (FullOrgUnitInfo)this.prmtOrgUnit.getValue();
			if(orgInfo!=null ) {
				filter.getFilterItems().add(new FilterItemInfo("orgUnit.id",orgInfo.getId().toString()));
			}else{
				filter.getFilterItems().add(new FilterItemInfo("orgUnit.id","null"));
			}
			view.setFilter(filter);
			this.prmtSellProject.setEntityViewInfo(view);
		}
		*/
		
		try{
			this.prmtSellProject.setEntityViewInfo(CommerceHelper.getPermitProjectView());
		}catch(Exception e){
			e.printStackTrace();
			this.abort();
		}



	}
	
	private void setPrmtBuildingViewInfo() {
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		
		SellProjectInfo sellProInfo = (SellProjectInfo)this.prmtSellProject.getValue();
		if(sellProInfo!=null) {
			filter.getFilterItems().add(new FilterItemInfo("sellProject.id",sellProInfo.getId().toString()));
		}else{
			filter.getFilterItems().add(new FilterItemInfo("sellProject.id",null));
		}
		view.setFilter(filter);
		this.prmtBuilding.setEntityViewInfo(view);
	}
		
	
	
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		super.actionEdit_actionPerformed(e);
		
		setViewByPtype();
		setBtnEnabledStatus(true);
		
    	//点编辑时会把附件按钮显示出来的问题
    	this.actionAttachment.setVisible(false);
		
		this.setTblElementColorColumnStyle();
	}
	

	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		super.actionAddNew_actionPerformed(e);
		
		setBtnEnabledStatus(true);
	}	
	
	private void setBtnEnabledStatus(boolean isEnabled) {
		this.btnAddElement.setEnabled(isEnabled);
		this.btnRemoveElement.setEnabled(isEnabled);
		this.btnLocationName.setEnabled(isEnabled);
		this.btnLocationOutLine.setEnabled(isEnabled);
		this.btnSetPicLayOut.setEnabled(isEnabled);
	}
	
	
	
	protected void beforeStoreFields(ActionEvent e) throws Exception {
		super.beforeStoreFields(e);
		
		StringBuffer buff = new StringBuffer();
		
		if(StringUtils.isEmpty(this.txtNumber.getText()))
			buff.append("示意图编码不能空!\n");		
		if(StringUtils.isEmpty(this.txtName.getText()))
			buff.append("示意图名称不能空!\n");
		
		PlanisphereTypeEnum ptype = (PlanisphereTypeEnum)this.comboPtype.getSelectedItem();
		FullOrgUnitInfo orgUnitInfo = (FullOrgUnitInfo)this.prmtOrgUnit.getValue();
		SellProjectInfo sellProInfo = (SellProjectInfo)this.prmtSellProject.getValue();
		BuildingInfo buildInfo = (BuildingInfo)this.prmtBuilding.getValue();
		
		if(ptype==null)
			buff.append("类型不能为空!\n");
		if(orgUnitInfo==null)
			buff.append("组织不能为空!\n");
		if(ptype!=null) {
			if(ptype.equals(PlanisphereTypeEnum.PicBuildDist) || ptype.equals(PlanisphereTypeEnum.PicBuildPlane)) {				
				if(sellProInfo==null)  buff.append("租售不能为空!\n");
				if(ptype.equals(PlanisphereTypeEnum.PicBuildPlane)){					
					if(buildInfo==null)  buff.append("楼栋不能为空!\n");
				}				
			}
		}

		if(this.btnLocationSure.isEnabled()) {
			buff.append("定位确认还未完成!\n");
		}	
		
		if(!buff.toString().equals("")) {
			MsgBox.showWarning(buff.toString());
			this.abort();
		}
		
		//新增、修改时确保平面图的唯一性
			FilterInfo filter = new FilterInfo();
			if(this.oprtState.equals(OprtState.EDIT)) 
				filter.getFilterItems().add(new FilterItemInfo("id",this.editData.getId().toString(),CompareType.NOTEQUALS));
			
			if(ptype.equals(PlanisphereTypeEnum.PicSellProject)){
				filter.getFilterItems().add(new FilterItemInfo("ptype",PlanisphereTypeEnum.PicSellProject.getValue()));
				filter.getFilterItems().add(new FilterItemInfo("orgUnit.id",orgUnitInfo.getId().toString()));
				filter.getFilterItems().add(new FilterItemInfo("sellProject",null,CompareType.EQUALS));	
				if(PlanisphereFactory.getRemoteInstance().exists(filter)){
					MsgBox.showWarning(orgUnitInfo.getName()+"-"+PlanisphereTypeEnum.PicSellProject.getAlias()+",已经存在!\r\n不能同时存在多张!");
					this.abort();
				}
			}else if(ptype.equals(PlanisphereTypeEnum.PicBuildDist)) {
				filter.getFilterItems().add(new FilterItemInfo("ptype",PlanisphereTypeEnum.PicBuildDist.getValue()));
				filter.getFilterItems().add(new FilterItemInfo("orgUnit.id",orgUnitInfo.getId().toString()));
				filter.getFilterItems().add(new FilterItemInfo("sellProject.id",sellProInfo.getId().toString()));
				filter.getFilterItems().add(new FilterItemInfo("building",null,CompareType.EQUALS));		
				if(PlanisphereFactory.getRemoteInstance().exists(filter)){
					MsgBox.showWarning(orgUnitInfo.getName()+"-"+sellProInfo.getName()+"-"+PlanisphereTypeEnum.PicBuildDist.getAlias()+",已经存在!\r\n不能同时存在多张!");
					this.abort();
				}
			}else if(ptype.equals(PlanisphereTypeEnum.PicBuildPlane)){
				boolean isShowUnit = this.chkIsShowUnit.isSelected();		
				Integer unitNum = (Integer)this.kDSpiUnit.getValue();
				Integer floorNum = (Integer)this.kDSpiFloor.getValue();
				filter.getFilterItems().add(new FilterItemInfo("ptype",PlanisphereTypeEnum.PicBuildPlane.getValue()));
				filter.getFilterItems().add(new FilterItemInfo("orgUnit.id",orgUnitInfo.getId().toString()));
				filter.getFilterItems().add(new FilterItemInfo("sellProject.id",sellProInfo.getId().toString()));
				filter.getFilterItems().add(new FilterItemInfo("building.id",buildInfo.getId().toString()));				
				filter.getFilterItems().add(new FilterItemInfo("floor",floorNum));
				filter.getFilterItems().add(new FilterItemInfo("isShowUnit",new Boolean(isShowUnit)));
				if(isShowUnit)	filter.getFilterItems().add(new FilterItemInfo("unit",unitNum));
				if(PlanisphereFactory.getRemoteInstance().exists(filter)){
					String errorStr = orgUnitInfo.getName()+"-"+sellProInfo.getName()+"-"+buildInfo.getName()+"-"+PlanisphereTypeEnum.PicBuildDist.getAlias();
						if(isShowUnit) errorStr += "-"+unitNum+"单元";
						else errorStr += "-不区分单元";
						errorStr += "-"+floorNum+"层,已经存在!\r\n不能同时存在多张!";
					MsgBox.showWarning(errorStr);
					this.abort();
				}
			}

	}

	
	
	
	protected void btnSetPicLayOut_actionPerformed(ActionEvent e) throws Exception {
		super.btnSetPicLayOut_actionPerformed(e);
		
		UIContext uiContext = new UIContext(this);
		uiContext.put("CellHeigth", this.txtCellHeigth.getValue());
		uiContext.put("CellWidth", this.txtCellWidth.getValue());
		uiContext.put("CellHorizCount", this.txtCellHorizCount.getValue());
		uiContext.put("CellVertiCount", this.txtCellVertiCount.getValue());		
		uiContext.put("EditUI",this);
		
		boolean setHasChanged = false;
		try {
			IUIWindow newUIWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(PlanisphereSetPicLayoutUI.class.getName(), uiContext, null, OprtState.EDIT);
			newUIWindow.show();			
			
			Map uiMap = newUIWindow.getUIObject().getUIContext();
			Integer cellHeigth = (Integer)uiMap.get("CellHeigth");
			  if(cellHeigth!=null && !cellHeigth.equals(this.txtCellHeigth.getValue()))  {
				  this.txtCellHeigth.setValue(cellHeigth);
				  setHasChanged = true;
			  }
			  Integer cellWidth = (Integer)uiMap.get("CellWidth");
			  if(cellWidth!=null && !cellWidth.equals(this.txtCellWidth.getValue()))  {
				  this.txtCellWidth.setValue(cellWidth);
				  setHasChanged = true;
			  }
			  Integer cellHorizCount = (Integer)uiMap.get("CellHorizCount");
			  if(cellHorizCount!=null && !cellHorizCount.equals(this.txtCellHorizCount.getValue())) {
				  this.txtCellHorizCount.setValue(cellHorizCount);
				  setHasChanged = true;
			  }
			  Integer cellVertiCount = (Integer)uiMap.get("CellVertiCount");
			  if(cellVertiCount!=null && !cellVertiCount.equals(this.txtCellVertiCount.getValue())) {
				  this.txtCellVertiCount.setValue(cellVertiCount);
				  setHasChanged = true;
			  }
		} catch (UIException ee) {
			ee.printStackTrace();
		}
		
		  
		//如果参数有变化  刷新平面示意图  
		  if(setHasChanged) 
			  reflashPicTable();		
	}
	
	//增加删除只针对'空地'元素
	protected void btnAddElement_actionPerformed(ActionEvent e) throws Exception {
		super.btnAddElement_actionPerformed(e);
	
		IRow row = this.tblElementEntry.addRow();		
		row.getCell("outLineColor").getStyleAttributes().setLocked(true);
		row.getCell("outLineBackColor").getStyleAttributes().setLocked(true);
		row.getCell("nameBackColor").getStyleAttributes().setLocked(true);
		row.getCell("nameColor").getStyleAttributes().setLocked(true);		
		row.getCell("type").setValue(PlanisphereElementEnum.space);		
		row.getCell("name").getStyleAttributes().setLocked(false);
		row.getCell("outLineBackColor").getStyleAttributes().setBackground(Color.WHITE);
		row.getCell("outLineColor").getStyleAttributes().setBackground(Color.BLACK);
		row.getCell("nameBackColor").getStyleAttributes().setBackground(Color.WHITE);
		row.getCell("nameColor").getStyleAttributes().setBackground(Color.BLACK);
			KDTextField textField = new KDTextField();
			ICellEditor textEditor = new KDTDefaultCellEditor(textField);
			row.getCell("name").setEditor(textEditor);
		PlanisphereElementEntryInfo newElem = new PlanisphereElementEntryInfo();
		newElem.setPlanisphere(this.editData);
		newElem.setType(PlanisphereElementEnum.space);
		newElem.setOutLineBackColor(Color.WHITE.getRGB());
		newElem.setOutLineColor(Color.BLACK.getRGB());
		newElem.setNameBackColor(Color.WHITE.getRGB());
		newElem.setNameColor(Color.BLACK.getRGB());		
		row.setUserObject(newElem);
	}

	protected void btnRemoveElement_actionPerformed(ActionEvent e) throws Exception {
		super.btnRemoveElement_actionPerformed(e);
		
		int[] selectRows = KDTableUtil.getSelectedRows(this.tblElementEntry);
		if(selectRows.length==0) {
			MsgBox.showInfo("请先选择要删除的元素!");
			return ;
		}
		
		for(int i=0;i<selectRows.length;i++) {
			IRow row = this.tblElementEntry.getRow(selectRows[i]);
			PlanisphereElementEnum elementType = (PlanisphereElementEnum)row.getCell("type").getValue();
			if(elementType==null || !elementType.equals(PlanisphereElementEnum.space)) {
				MsgBox.showInfo("选择的第"+(selectRows[i]+1)+"行元素不为'空地',不能删除!");
				return ;
			}
		}
		
		if(MsgBox.OK == MsgBox.showConfirm2("确认要删除吗?")) {
			for(int i=0;i<selectRows.length;i++) {
				//删除该空地元素的轮廓和名称
				IRow row = this.tblElementEntry.getRow(selectRows[i]);
				PlanisphereElementEntryInfo elementType = (PlanisphereElementEntryInfo)row.getUserObject();
				thisPicTable.clearElementBorder(elementType);
				thisPicTable.clearElementName(elementType);
				
				this.tblElementEntry.removeRow(selectRows[i]);
			}
		}
		

		
	}	
	
	
	
	/**
	 *房间元素的颜色是后台设置的，不能修改，显示时要根据房间的当前状态变化的
	 */
	private void setTblElementColorColumnStyle(){
		PlanisphereTypeEnum pType = (PlanisphereTypeEnum)this.comboPtype.getSelectedItem();
		if(pType==null)  return;
		if(pType.equals(PlanisphereTypeEnum.PicSellProject) || pType.equals(PlanisphereTypeEnum.PicBuildDist)) {
			this.tblElementEntry.getColumn("outLineColor").getStyleAttributes().setLocked(false);
			this.tblElementEntry.getColumn("outLineBackColor").getStyleAttributes().setLocked(false);
			this.tblElementEntry.getColumn("nameBackColor").getStyleAttributes().setLocked(false);
			this.tblElementEntry.getColumn("nameColor").getStyleAttributes().setLocked(false);		
		}else{
			this.tblElementEntry.getColumn("outLineColor").getStyleAttributes().setLocked(true);
			this.tblElementEntry.getColumn("outLineBackColor").getStyleAttributes().setLocked(true);
			this.tblElementEntry.getColumn("nameBackColor").getStyleAttributes().setLocked(true);
			this.tblElementEntry.getColumn("nameColor").getStyleAttributes().setLocked(true);		
		}
	}
	
	
	
	/**
	 * 刷新元素列表 -- 在元素列表中显示相关的所有元素
	 * 在新增、增加或删除元素；组织、项目、楼栋、单元、楼层变化时要刷新
	 */
	private void reflashTblElementEntry () {
		this.tblElementEntry.removeRows();
		reflashPicTable();

		PlanisphereTypeEnum pType = (PlanisphereTypeEnum)this.comboPtype.getSelectedItem();
		if(pType==null)  return;
		
		FullOrgUnitInfo orgUnitInfo = (FullOrgUnitInfo)this.prmtOrgUnit.getValue();
		if(orgUnitInfo==null) return;		
		
		setTblElementColorColumnStyle();
		
		if(pType.equals(PlanisphereTypeEnum.PicSellProject)) {   //各项目方位图			
				EntityViewInfo view = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();

				try {
					SellProjectCollection sellProColl =  new SellProjectCollection();
					Set proIdSet = CommerceHelper.getPermitProjectIds();
					if(proIdSet.size()>0) {
						filter.getFilterItems().add(new FilterItemInfo("id",proIdSet,CompareType.INCLUDE));
						view.setFilter(filter);
						sellProColl = SellProjectFactory.getRemoteInstance().getSellProjectCollection(view);
					}
					for(int i=0;i<sellProColl.size();i++) {
						SellProjectInfo sellProInfo = sellProColl.get(i);
						IRow row = this.tblElementEntry.addRow();	
						PlanisphereElementEntryInfo elem = new PlanisphereElementEntryInfo();
						elem.setType(PlanisphereElementEnum.sellProject);
						elem.setName(sellProInfo.getName());
						elem.setOutLineBackColor(Color.WHITE.getRGB());
						elem.setOutLineColor(Color.BLACK.getRGB());
						elem.setNameBackColor(Color.WHITE.getRGB());
						elem.setNameColor(Color.BLACK.getRGB());
						elem.setSellProjectEntry(sellProInfo);
						row.setUserObject(elem);
						row.getCell("type").setValue(PlanisphereElementEnum.sellProject);
						row.getCell("name").setValue(sellProInfo.getName());
						row.getCell("outLineBackColor").getStyleAttributes().setBackground(Color.WHITE);
						row.getCell("outLineColor").getStyleAttributes().setBackground(Color.BLACK);
						row.getCell("nameBackColor").getStyleAttributes().setBackground(Color.WHITE);
						row.getCell("nameColor").getStyleAttributes().setBackground(Color.BLACK);
					}
				} catch (BOSException e) {
					this.handleException(e);
					this.abort();					
				}
			return;
		}
		
		SellProjectInfo sellProInfo = (SellProjectInfo)this.prmtSellProject.getValue();
		if(sellProInfo==null) return;
		if(pType.equals(PlanisphereTypeEnum.PicBuildDist)) { //楼栋分布图
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("sellProject.id",sellProInfo.getId().toString()));
			view.setFilter(filter);
			try {
				BuildingCollection buildColl = BuildingFactory.getRemoteInstance().getBuildingCollection(view);
				for(int i=0;i<buildColl.size();i++) {
					BuildingInfo buidInfo = (BuildingInfo)buildColl.get(i);
					IRow row = this.tblElementEntry.addRow();					
					PlanisphereElementEntryInfo elem = new PlanisphereElementEntryInfo();
					elem.setType(PlanisphereElementEnum.building);
					elem.setName(buidInfo.getName());
					elem.setOutLineBackColor(Color.WHITE.getRGB());
					elem.setOutLineColor(Color.BLACK.getRGB());
					elem.setNameBackColor(Color.WHITE.getRGB());
					elem.setNameColor(Color.BLACK.getRGB());
					elem.setBuildingEntry(buidInfo);
					row.setUserObject(elem);
					row.getCell("type").setValue(PlanisphereElementEnum.building);
					row.getCell("name").setValue(buidInfo.getName());		
					row.getCell("outLineBackColor").getStyleAttributes().setBackground(Color.WHITE);
					row.getCell("outLineColor").getStyleAttributes().setBackground(Color.BLACK);
					row.getCell("nameBackColor").getStyleAttributes().setBackground(Color.WHITE);
					row.getCell("nameColor").getStyleAttributes().setBackground(Color.BLACK);
				}
			} catch (BOSException e) {
				this.handleException(e);
				this.abort();	
			} 
			return;
		}
		
		BuildingInfo buidInfo = (BuildingInfo)this.prmtBuilding.getValue();
		if(buidInfo==null) return;
		if(pType.equals(PlanisphereTypeEnum.PicBuildPlane)) {  //楼栋平面图
			Integer unitNum = (Integer)this.kDSpiUnit.getValue();
			Integer floorNum = (Integer)this.kDSpiFloor.getValue();
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("building.id",buidInfo.getId().toString()));
			filter.getFilterItems().add(new FilterItemInfo("unit",unitNum));
			filter.getFilterItems().add(new FilterItemInfo("floor",floorNum));
			view.setFilter(filter);			
			try {
				RoomCollection roomColl = RoomFactory.getRemoteInstance().getRoomCollection(view);
				for(int i=0;i<roomColl.size();i++) {
					RoomInfo roomInfo = (RoomInfo)roomColl.get(i);
					IRow row = this.tblElementEntry.addRow();					
					PlanisphereElementEntryInfo elem = new PlanisphereElementEntryInfo();
					elem.setType(PlanisphereElementEnum.room);
					elem.setName(roomInfo.getName());
					elem.setOutLineBackColor(Color.WHITE.getRGB());
					elem.setOutLineColor(Color.BLACK.getRGB());
					elem.setNameBackColor(Color.WHITE.getRGB());
					elem.setNameColor(Color.BLACK.getRGB());
					elem.setRoomEntry(roomInfo);
					row.setUserObject(elem);
					row.getCell("type").setValue(PlanisphereElementEnum.room);
					row.getCell("name").setValue(roomInfo.getName());
					row.getCell("outLineBackColor").getStyleAttributes().setBackground(Color.WHITE);
					row.getCell("outLineColor").getStyleAttributes().setBackground(Color.BLACK);
					row.getCell("nameBackColor").getStyleAttributes().setBackground(Color.WHITE);
					row.getCell("nameColor").getStyleAttributes().setBackground(Color.BLACK);
				}
			} catch (BOSException e) {
				this.handleException(e);
				this.abort();	
			}
			return;
		}
		
	}
	
	
	protected void tblElementEntry_editStopped(KDTEditEvent e) throws Exception {
		super.tblElementEntry_editStopped(e);
		
		IRow editRow = this.tblElementEntry.getRow(e.getRowIndex());
		if(editRow!=null){
			PlanisphereElementEntryInfo elem = (PlanisphereElementEntryInfo)editRow.getUserObject();
			if(elem!=null)  {
				String name = (String)editRow.getCell("name").getValue();  //名称修改
				if(name!=null) 		elem.setName(name);
				
				Color outLineBkColor = (Color)editRow.getCell("outLineBackColor").getValue();  //轮廓背景色
				if(outLineBkColor!=null)   {
					elem.setOutLineBackColor(outLineBkColor.getRGB());
					editRow.getCell("outLineBackColor").getStyleAttributes().setBackground(outLineBkColor);
					editRow.getCell("outLineBackColor").getStyleAttributes().setFontColor(outLineBkColor);
				}
				Color outLineColor = (Color)editRow.getCell("outLineColor").getValue();  //轮廓颜色	
				if(outLineColor!=null) {
					elem.setOutLineColor(outLineColor.getRGB());
					editRow.getCell("outLineColor").getStyleAttributes().setBackground(outLineColor);
					editRow.getCell("outLineColor").getStyleAttributes().setFontColor(outLineColor);
				}
				Color nameBackColor = (Color)editRow.getCell("nameBackColor").getValue();  //名称背景色	
				if(nameBackColor!=null) {
					elem.setNameBackColor(nameBackColor.getRGB());
					editRow.getCell("nameBackColor").getStyleAttributes().setBackground(nameBackColor);
					editRow.getCell("nameBackColor").getStyleAttributes().setFontColor(nameBackColor);
				}
				Color nameColor = (Color)editRow.getCell("nameColor").getValue();  //名称颜色	
				if(nameColor!=null) {
					elem.setNameColor(nameColor.getRGB());
					editRow.getCell("nameColor").getStyleAttributes().setBackground(nameColor);
					editRow.getCell("nameColor").getStyleAttributes().setFontColor(nameColor);
				}
				thisPicTable.showElement(elem);
			}
		}
	}
	
    protected void tblElementEntry_tableClicked(KDTMouseEvent e) throws Exception {
    	super.tblElementEntry_tableClicked(e);
    	
		if (e.getButton() == 1 && e.getClickCount() == 2) {
			if(e.getType()!=1) return;
		
			PlanisphereElementEntryInfo elementEntryInfo = (PlanisphereElementEntryInfo)this.tblElementEntry.getRow(e.getRowIndex()).getUserObject();
			if(elementEntryInfo==null) return;
			//双击事件触发  热选该元素
			thisPicTable.setSelectedBorder(elementEntryInfo);
		}    	
    }
	
    
    protected void tblDetail_tableSelectChanged(KDTSelectEvent e) throws Exception {
    	super.tblDetail_tableSelectChanged(e);
    	//清除所有的热点
    	this.tblPicView.getSelectManager().removeAll();
    	//复原轮廓定位、名称定位和确认按钮
    	if(!this.getOprtState().equals(OprtState.VIEW)) {
	    	this.btnLocationOutLine.setEnabled(true);
	    	this.btnLocationName.setEnabled(true);
	    	this.btnLocationSure.setEnabled(false);
    	}
    }
	

	
	protected void btnLocationOutLine_actionPerformed(ActionEvent e) throws Exception {
		super.btnLocationOutLine_actionPerformed(e);
		
		
		int[] selectRows = KDTableUtil.getSelectedRows(this.tblElementEntry);
		if(selectRows.length==0) {
			MsgBox.showInfo("请先选择要轮廓定位的元素!");
			return ;
		}
		
		if(!this.btnLocationName.isEnabled()) {
			MsgBox.showInfo("名称定位还未完成!");
			return ;
		}
		
		IRow row = this.tblElementEntry.getRow(selectRows[0]);
		PlanisphereElementEntryInfo elemInfo = (PlanisphereElementEntryInfo)row.getUserObject();
		this.tblPicView.getSelectManager().removeAll();
		thisPicTable.setSelectedBorder(elemInfo);
		
		thisPicTable.setModel(TableDrawManager.EDIT_MODEL);
		
		this.btnLocationOutLine.setEnabled(false);
		this.btnLocationSure.setEnabled(true);			
	}
    
	protected void btnLocationName_actionPerformed(ActionEvent e) throws Exception {
		super.btnLocationName_actionPerformed(e);
		
		int[] selectRows = KDTableUtil.getSelectedRows(this.tblElementEntry);

		if(selectRows.length==0) {
			MsgBox.showInfo("请先选择要轮廓定位的元素!");
			return ;
		}		
		
		IRow row = this.tblElementEntry.getRow(selectRows[0]);
		PlanisphereElementEntryInfo elemInfo = (PlanisphereElementEntryInfo)row.getUserObject();
		
		//名称定为前必须已完成轮廓定位，轮廓数据不为空
		byte[] bytes = elemInfo.getOutLineLocationData();
		if(bytes==null || CommerceHelper.ByteArrayToListObject(bytes).size()==0){
			MsgBox.showInfo("轮廓的具体范围未确定,请先完成轮廓定位");
			return ;
		}
		
		if(selectRows.length==0) {
			MsgBox.showInfo("请先选择要名称定位的元素!");
			return ;
		}
		
		if(!this.btnLocationOutLine.isEnabled()) {
			MsgBox.showInfo("轮廓定位还未完成!");
			return ;
		}

		this.tblPicView.getSelectManager().removeAll();
		thisPicTable.setSelectedName(elemInfo);
		
		thisPicTable.setModel(TableDrawManager.EDIT_MODEL);
		
		this.btnLocationName.setEnabled(false);
		this.btnLocationSure.setEnabled(true);
	}
    
	protected void btnLocationSure_actionPerformed(ActionEvent e) throws Exception {
		super.btnLocationSure_actionPerformed(e);
	
		int[] selectRows = KDTableUtil.getSelectedRows(this.tblElementEntry);
		if(selectRows.length==0) {
			MsgBox.showInfo("请先选择要定位确认的元素!");
			return ;
		}
		
		List selectList = thisPicTable.getSelectPointList();
//		if(selectList.size()==0) {
//			MsgBox.showInfo("选中范围为空，请选中后再确认!");
//			return ;
//		}
		
		
		IRow row = this.tblElementEntry.getRow(selectRows[0]);
		PlanisphereElementEntryInfo elemInfo = (PlanisphereElementEntryInfo)row.getUserObject();		
		thisPicTable.setModel(TableDrawManager.VIEW_MODEL);
		if(!this.btnLocationOutLine.isEnabled()) {  //轮廓定位		
			//该元素所选轮廓和 已存在的元素轮廓是否有重叠的 部分
			List allOutLineList = new ArrayList();
			for(int i=0;i<this.tblElementEntry.getRowCount();i++) {
				IRow indexRow = this.tblElementEntry.getRow(i);
				if(i==selectRows[0]) continue;
				Object cellValue = indexRow.getCell("outLineLocationData").getValue(); 
				if(cellValue!=null && cellValue instanceof byte[]){
					byte[] cellBytes = (byte[])cellValue;
					allOutLineList.addAll(CommerceHelper.ByteArrayToListObject(cellBytes));
				}
			}
			if(thisPicTable.isExistSuperpose(selectList, allOutLineList)) {
				MsgBox.showInfo("所选轮廓有重叠部分，请重新选定!");
				return ;
			}
			
			//清除原有的元素轮廓
			thisPicTable.clearElementBorder(elemInfo);
			//清除原有的元素名称
			thisPicTable.clearElementName(elemInfo);
			row.getCell("nameLocationData").setValue(new byte[0]);
			elemInfo.setNameLocationData(new byte[0]);			
			//获取选种的热点单元格,并绘制轮廓
			List pList = thisPicTable.setBorderOfSelectedCells(new Color(elemInfo.getOutLineBackColor()),new Color(elemInfo.getOutLineColor()));
			byte[] bytes = CommerceHelper.ListObjectToByteArray(pList);
			row.getCell("outLineLocationData").setValue(bytes);
			elemInfo.setOutLineLocationData(bytes);
			
			this.btnLocationSure.setEnabled(false);
			this.btnLocationOutLine.setEnabled(true);
		}else if(!this.btnLocationName.isEnabled()) { //名称定位
			if(!thisPicTable.isNameInOutLineList(thisPicTable.getMaxNameList(selectList),CommerceHelper.ByteArrayToListObject(elemInfo.getOutLineLocationData()))) {
				MsgBox.showInfo("所选名称的最大合并范围不在轮廓范围内，请重新选定!");
				return ;
			}		
			
			//清除原有的元素名称
			thisPicTable.clearElementName(elemInfo);			
			//获取选种的热点单元格，并标上名称
			String elementName = (String)row.getCell("name").getValue();			
			List pList = thisPicTable.setNameOfSelectedCells(elementName,new Color(elemInfo.getNameBackColor()),new Color(elemInfo.getNameColor()));
			byte[] bytes = CommerceHelper.ListObjectToByteArray(pList);
			row.getCell("nameLocationData").setValue(bytes);
			elemInfo.setNameLocationData(bytes);			
		
			this.btnLocationSure.setEnabled(false);
			this.btnLocationName.setEnabled(true);
		}
	}

	
	
	protected void kDSpiUnit_stateChanged(ChangeEvent e) throws Exception {
		super.kDSpiUnit_stateChanged(e);
		this.reflashTblElementEntry();
	}
	
	protected void kDSpiFloor_stateChanged(ChangeEvent e) throws Exception {
		super.kDSpiFloor_stateChanged(e);
		this.reflashTblElementEntry();
	}
	
	
    protected void comboPtype_itemStateChanged(ItemEvent e) throws Exception {
    	super.comboPtype_itemStateChanged(e);
    	
    	//this.prmtOrgUnit.setValue(null);
    	this.prmtSellProject.setValue(null);
    	
    	setViewByPtype();
    	
    	reflashTblElementEntry();
    }
    
    
    private void setViewByPtype(){
    	PlanisphereTypeEnum pType = (PlanisphereTypeEnum)this.comboPtype.getSelectedItem();
    	if(pType==null) return;
    	if(pType.equals(PlanisphereTypeEnum.PicSellProject)) {
    		this.prmtSellProject.setEnabled(false);    		
    		this.prmtBuilding.setEnabled(false);
    		this.chkIsShowUnit.setEnabled(false);    		
    		this.kDSpiUnit.setEnabled(false); 
    		this.kDSpiFloor.setEnabled(false);
    		this.chkIsShowUnit.setSelected(false);
    	}else if(pType.equals(PlanisphereTypeEnum.PicBuildDist)) {
    		this.prmtSellProject.setEnabled(true);
    		this.prmtSellProject.setRequired(true);
     		this.prmtBuilding.setEnabled(false);
    		this.chkIsShowUnit.setEnabled(false);
    		this.kDSpiUnit.setEnabled(false);
    		this.kDSpiFloor.setEnabled(false);
    		this.chkIsShowUnit.setSelected(false);
    	}else if(pType.equals(PlanisphereTypeEnum.PicBuildPlane)) {
    		this.prmtSellProject.setEnabled(true);
    		this.prmtSellProject.setRequired(true);
    		this.prmtBuilding.setEnabled(true);
    		this.prmtBuilding.setRequired(true);
    		this.chkIsShowUnit.setEnabled(true);
    		this.kDSpiUnit.setEnabled(true);
    		this.kDSpiFloor.setEnabled(true);
    	}
    	
//		this.kDSpiUnit.setModel(new SpinnerNumberModel(0, 0, 0, 1));
//		this.kDSpiFloor.setModel(new SpinnerNumberModel(0, 0, 0, 1));
//		this.kDSpiUnit.setValue(new Integer(0));
//		this.kDSpiFloor.setValue(new Integer(0));
    }
	
    
    protected void prmtOrgUnit_dataChanged(DataChangeEvent e) throws Exception {
    	super.prmtOrgUnit_dataChanged(e);
    	
    	this.setPrmtSellProjectViewInfo();        
    	this.prmtSellProject.setValue(null);
    	
    	PlanisphereTypeEnum pType = (PlanisphereTypeEnum)this.comboPtype.getSelectedItem();
    	if(pType!=null && pType.equals(PlanisphereTypeEnum.PicSellProject) && e.getNewValue()!=null) 
    		this.reflashTblElementEntry();
    }
    
    protected void prmtSellProject_dataChanged(DataChangeEvent e) throws Exception {
    	super.prmtSellProject_dataChanged(e);
    	
    	setPrmtBuildingViewInfo();
    	this.prmtBuilding.setValue(null);
    	
    	PlanisphereTypeEnum pType = (PlanisphereTypeEnum)this.comboPtype.getSelectedItem();
    	if(pType!=null && pType.equals(PlanisphereTypeEnum.PicBuildDist) && e.getNewValue()!=null) 
    		this.reflashTblElementEntry();
    }
    
    
    //	根据楼栋设置单元格和楼层的选值范围
    protected void prmtBuilding_dataChanged(DataChangeEvent e) throws Exception {
    	super.prmtBuilding_dataChanged(e);

    	reflashUnitAndFloorByBuilding();
    	
    	BuildingInfo buildInfo = (BuildingInfo)this.prmtBuilding.getValue();;
    	PlanisphereTypeEnum pType = (PlanisphereTypeEnum)this.comboPtype.getSelectedItem();
    	if(pType!=null && pType.equals(PlanisphereTypeEnum.PicBuildPlane) && buildInfo!=null) 
    		this.reflashTblElementEntry();
    }
    
    private void reflashUnitAndFloorByBuilding() {
    	BuildingInfo buildInfo = (BuildingInfo)this.prmtBuilding.getValue();;
    	if(buildInfo!=null) {
    		int unitCount = buildInfo.getUnitCount();
    		int floorCount = buildInfo.getFloorCount();
    		// 新加逻辑  楼层选择 需支持 地下楼层  by jian_wen 2010-4-29
    		FDCSQLBuilder builder=new FDCSQLBuilder();
    		builder.appendSql("select FSubFloorCount from T_SHE_Building where fid=?");
    		builder.addParam(buildInfo.getId().toString());
    		int subFloorCount=1;
    		try {
				IRowSet rs=builder.executeQuery();
				if(rs.next()){
					subFloorCount=rs.getInt("FSubFloorCount");
					subFloorCount=subFloorCount<0 ? subFloorCount : 1;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
    		if(unitCount!=0) {
    			this.kDSpiUnit.setModel(new SpinnerNumberModel(1,1, unitCount,1));
    			this.chkIsShowUnit.setEnabled(true);
    			this.chkIsShowUnit.setSelected(true);
    			this.kDSpiUnit.setEnabled(true);
    			if(this.editData.getUnit()!=0)
    				this.kDSpiUnit.setValue(new Integer(this.editData.getUnit()));
    		}else {
    			this.kDSpiUnit.setModel(new SpinnerNumberModel(0,0, 0,1));
    			this.chkIsShowUnit.setEnabled(false);
    			this.chkIsShowUnit.setSelected(false);
    			this.kDSpiUnit.setEnabled(false);
    		}
    		if(floorCount!=0) {
    			this.kDSpiFloor.setModel(new SpinnerNumberModel(1,subFloorCount, floorCount,1));
    			if(this.editData.getFloor()!=0)
    				this.kDSpiFloor.setValue(new Integer(this.editData.getFloor()));
    		}else
    			this.kDSpiFloor.setModel(new SpinnerNumberModel(0,0, 0,1));
    	}
    }
    
    
    
    
    
    protected void chkIsShowUnit_actionPerformed(ActionEvent e) throws Exception {
    	super.chkIsShowUnit_actionPerformed(e);
    	
    	if(this.chkIsShowUnit.isSelected()) {
    		this.kDSpiUnit.setEnabled(true);
    	}else{
    		this.kDSpiUnit.setEnabled(false);
    	}
    }
    
    protected IObjectValue createNewData() {    

    	PlanisphereInfo planInfo = new PlanisphereInfo();
    	
    	//改变会影响单元和楼层
    	FullOrgUnitInfo orgUnitInfo = (FullOrgUnitInfo)this.getUIContext().get("OrgUnitInfo");
    	SellProjectInfo sellProInfo = (SellProjectInfo)this.getUIContext().get("SellProInfo");
    	BuildingInfo buildInfo = (BuildingInfo)this.getUIContext().get("BuildInfo");
    	Integer unitInfo = (Integer)this.getUIContext().get("UnitInfo");
    	
    	PlanisphereTypeEnum currType = PlanisphereTypeEnum.PicSellProject;

    	planInfo.setIsShowUnit(false);
    	if(orgUnitInfo!=null)   {
    		planInfo.setOrgUnit(orgUnitInfo);
    	}	
    	if(sellProInfo!=null)	{
    		planInfo.setSellProject(sellProInfo);
    		currType = PlanisphereTypeEnum.PicBuildDist;
    	}
    	if(buildInfo!=null)	{  //楼栋平面图
    		planInfo.setBuilding(buildInfo);
    		currType = PlanisphereTypeEnum.PicBuildPlane;
    		if(unitInfo!=null) {
    			this.chkIsShowUnit.setSelected(true);
    			planInfo.setUnit(unitInfo.intValue());
    		}
    	}
    	planInfo.setPtype(currType);
    	
//		this.kDSpiUnit.setModel(new SpinnerNumberModel(0, 0, 0, 1));
//		this.kDSpiFloor.setModel(new SpinnerNumberModel(0, 0, 0, 1));
    	
    	planInfo.setCellHeigth(20);
    	planInfo.setCellWidth(20);
    	planInfo.setCellHorizCount(100);
    	planInfo.setCellVertiCount(100);
    	
    	this.detachListeners();
    	this.comboPtype.setSelectedItem(currType);
    	this.prmtOrgUnit.setValue(orgUnitInfo);
    	this.prmtSellProject.setValue(sellProInfo);
    	this.prmtBuilding.setValue(buildInfo);
    	this.attachListeners();
    	
    	setViewByPtype();
    	
    	return planInfo;
    }
    
    
    
    
    public void loadFields() {
    	this.detachListeners();   	
    	
    	super.loadFields();
    	
    	setViewByPtype();
    	
    	reflashUnitAndFloorByBuilding();
    	
    	this.attachListeners();
    	
    	
    	if(this.oprtState.equals(OprtState.ADDNEW)) {
    		this.reflashTblElementEntry();  
    	}
    		  	
    	
    	initialElementEntryTable();	

    	
    	//点上一个，下一个按钮的时候会把附件和审批按钮显示出来的问题
    	this.actionAttachment.setVisible(false);
    	this.actionAudit.setVisible(false);
    }
	
	
    
    
    
    

	


}