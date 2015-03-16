/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.*;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.common.calc.CalculatorDialog;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basedata.FDCBasedataException;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.basedata.client.FDCBaseDataClientCtrler;
import com.kingdee.eas.fdc.basedata.client.FDCBaseDataClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.invite.supplier.IsGradeEnum;
import com.kingdee.eas.fdc.sellhouse.AgioBillInfo;
import com.kingdee.eas.fdc.sellhouse.AgioCalTypeEnum;
import com.kingdee.eas.fdc.sellhouse.AgioRoomEntryCollection;
import com.kingdee.eas.fdc.sellhouse.AgioRoomEntryFactory;
import com.kingdee.eas.fdc.sellhouse.AgioRoomEntryInfo;
import com.kingdee.eas.fdc.sellhouse.AgioSchemeEntryCollection;
import com.kingdee.eas.fdc.sellhouse.AgioSchemeEntryInfo;
import com.kingdee.eas.fdc.sellhouse.AgioSchemeFactory;
import com.kingdee.eas.fdc.sellhouse.AgioSchemeInfo;
import com.kingdee.eas.fdc.sellhouse.OperateEnum;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.batchHandler.UtilRequest;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class AgioSchemeEditUI extends AbstractAgioSchemeEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AgioSchemeEditUI.class);
    //--------------------分录TABLE列名---------------------------------------
    private static final String AS_AGIO = "agio";
    private static final String AS_AGIOTYPE= "agioType";
    
    private static final String SELLPROJECT=  "sellProject";
   
    /**
     * output class constructor
     */
    public AgioSchemeEditUI() throws Exception
    {
        super();
    }


	protected void attachListeners() {
	}

	protected void detachListeners() {
	}

	protected ICoreBase getBizInterface() throws Exception {
		
		return AgioSchemeFactory.getRemoteInstance();
	}

	protected KDTable getDetailTable() {
		return null;
	}

	protected KDTextField getNumberCtrl() {
		return this.txtNumber;
	}
	protected void fetchInitData() throws Exception {
	}
	
	protected IObjectValue createNewData() {
		AgioSchemeInfo agioSchemeInfo = new AgioSchemeInfo();
		agioSchemeInfo.setValidDate(new Date());
		agioSchemeInfo.setIsEnabled(true);
		agioSchemeInfo.setSellProject((SellProjectInfo)this.getUIContext().get(SELLPROJECT));
		agioSchemeInfo.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
		return agioSchemeInfo;
	}

	protected FDCDataBaseInfo getEditData() {
		return this.editData;
	}

	protected KDBizMultiLangBox getNameCtrl() {
		return null;
	}
	
	protected void btnAddLine_actionPerformed(ActionEvent e) throws Exception {
		IRow row = this.kdtAgioSchemeEntry.addRow();
		row.setUserObject(new AgioSchemeEntryInfo());
		row.getCell("operate").getStyleAttributes().setLocked(true);
	}
	
	protected void btnRemoveLine_actionPerformed(ActionEvent e) throws Exception {
		removeLine(kdtAgioSchemeEntry);
	}
	
	
	/**
	 * 在指定表格中删除当前选中行 增加隔行删除功能 2007-03-12
	 * 
	 * @param table
	 */
	protected void removeLine(KDTable table) {
		if (table == null) {
			return;
		}
		if ((table.getSelectManager().size() == 0))
		{
			MsgBox.showInfo(this, EASResource.getString(FrameWorkClientUtils.strResource + "Msg_NoneEntry"));
			return;
		}
		// [begin]进行删除分录的提示处理。
		if (confirmRemove()) {
			// 获取选择块的总个数
			KDTSelectManager selectManager = table.getSelectManager();
			int size = selectManager.size();
			KDTSelectBlock selectBlock = null;
			// 因为先择块的顺序可能并非是表中行的顺序，所以先要排序使选择块的顺序正好是由小到大
			Set indexSet = new HashSet();

			for (int blockIndex = 0; blockIndex < size; blockIndex++) {
				selectBlock = selectManager.get(blockIndex);
				int top = selectBlock.getBeginRow();
				int bottom = selectBlock.getEndRow();
				if (table.getRow(top) == null) {
					MsgBox.showInfo(this, EASResource
							.getString(FrameWorkClientUtils.strResource
									+ "Msg_NoneEntry"));
					return;
				}
				for (int i = top; i <= bottom; i++) {
					indexSet.add(new Integer(i));
				}
			}
			Integer[] indexArr = new Integer[indexSet.size()];
			Object[] indexObj = indexSet.toArray();
			System.arraycopy(indexObj, 0, indexArr, 0, indexArr.length);
			Arrays.sort(indexArr);
			if (indexArr == null)
				return;
			for (int i = indexArr.length - 1; i >= 0; i--) {
				int rowIndex = Integer.parseInt(String.valueOf(indexArr[i]));
				IObjectValue detailData = (IObjectValue) table.getRow(rowIndex)
						.getUserObject();
				table.removeRow(rowIndex);
			}
			// 如果现在有记录定位到第一行
			if (table.getRow(0) != null)
				table.getSelectManager().select(0, 0);
		}
	}
	protected void verifyInput(ActionEvent e) throws Exception {
		// 编码是否为空
		KDTextField txtNumber = this.txtNumber;
		if (txtNumber.getText() == null || txtNumber.getText().trim().length() < 1) {
			txtNumber.requestFocus(true);
			throw new FDCBasedataException(FDCBasedataException.NUMBER_IS_EMPTY);
		}
		
		KDTextField txtName = this.txtName;
		if (txtNumber.getText() == null || txtNumber.getText().trim().length() < 1) {
			txtNumber.requestFocus(true);
			throw new FDCBasedataException(FDCBasedataException.NUMBER_IS_EMPTY);
		}
		
		KDDatePicker pkDate = this.pkValidDate;
		if(pkDate.getValue()==null){
			pkDate.requestFocus(true);
			FDCMsgBox.showWarning("生效日期不能为空！");
			abort();
		}
		verifyinvalidDate();
		//效验折扣定义分录
		verifyAgioSchemeEntry();
	}
	
	private void verifyinvalidDate() {
		if(this.pkInvalidDate.getValue()!=null&&this.pkValidDate.getValue()!=null){
			if(((Date)this.pkValidDate.getValue()).getTime()>=((Date)this.pkInvalidDate.getValue()).getTime()){
				FDCMsgBox.showWarning("失效日期不能小于等于生效日期！");
				abort();
			}
		}
		
	}

	private void verifyAgioSchemeEntry() {
		KDTable table= this.kdtAgioSchemeEntry;
		if(table.getRowCount()<1){
			FDCMsgBox.showWarning("请增加一条折扣定义！");
			abort();
		}
		ICell kcell = null;
		//效验每行的折扣不能为空
		for(int i = 0 ; i <table.getRowCount() ; i++){
			kcell = table.getRow(i).getCell(AS_AGIO);
			if(kcell.getValue()==null){
				FDCMsgBox.showWarning("第"+(i+1)+"行的折扣名称不能为空！");
				abort();
			}
			kcell = table.getRow(i).getCell(AS_AGIOTYPE);
			if(kcell.getValue()==null){
				FDCMsgBox.showWarning("第"+(i+1)+"行的折扣类型不能为空！");
				abort();
			}
			kcell = table.getRow(i).getCell("operate");
			if(kcell.getValue()==null){
				FDCMsgBox.showWarning("第"+(i+1)+"行的运算不能为空！");
				abort();
			}
		}
		ICell cell = table.getRow(0).getCell(AS_AGIOTYPE);
		AgioCalTypeEnum act = (AgioCalTypeEnum)cell.getValue();
		if(AgioCalTypeEnum.DanJia.equals(act)){
			for(int i = 1 ; i <table.getRowCount() ; i++){
				kcell = table.getRow(i).getCell(AS_AGIOTYPE);
				AgioCalTypeEnum acType = (AgioCalTypeEnum)kcell.getValue();
				if(AgioCalTypeEnum.ZongJia.equals(acType)){
					FDCMsgBox.showWarning("折扣定义中：单价优惠与总价优惠不能同时存在！");
					abort();
				}
			}
		}else if(AgioCalTypeEnum.ZongJia.equals(act)){
			for(int i = 1 ; i <table.getRowCount() ; i++){
				kcell = table.getRow(i).getCell(AS_AGIOTYPE);
				AgioCalTypeEnum acType = (AgioCalTypeEnum)kcell.getValue();
				if(AgioCalTypeEnum.DanJia.equals(acType)){
					FDCMsgBox.showWarning("折扣定义中：总价优惠与单价优惠不能同时存在！");
					abort();
				}
			}
		}
		
		
	}

	private void initKdtAgioSchemeEntry() throws BOSException{
		SellProjectInfo sp = (SellProjectInfo)this.getUIContext().get(SELLPROJECT);
		String queryInfoStr = "com.kingdee.eas.fdc.sellhouse.app.AgioQuery";
		EntityViewInfo viewInfo = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("isEnabled",Boolean.TRUE));
		filter.getFilterItems().add(new FilterItemInfo("cancelDate",new Date(),CompareType.GREATER));
		filter.getFilterItems().add(new FilterItemInfo("cancelDate",null));
		EntityViewInfo entryView = new EntityViewInfo();
		Set idSet = new HashSet();
		SelectorItemCollection sel = new SelectorItemCollection();
		sel.add("head");
		entryView.setSelector(sel);
		AgioRoomEntryCollection agioRoomColl =AgioRoomEntryFactory.getRemoteInstance().getAgioRoomEntryCollection(entryView);
		for(int i = 0; i <agioRoomColl.size();i++){
			AgioRoomEntryInfo agioRoom = agioRoomColl.get(i);
			String id = agioRoom.getHead().getId().toString();
			idSet.add(id);
		}
		if(idSet.size()!=0){
			filter.getFilterItems().add(new FilterItemInfo("id",idSet,CompareType.NOTINCLUDE));
		}
		
		String[] numbers = sp.getLongNumber().split("!");
		String filterStr = " and (";
		for (int i = 0; i < numbers.length; i++) {
			filter.getFilterItems().add(new FilterItemInfo("project.number",numbers[i]));
			if(i>0) filterStr += " or ";
			if(idSet.size()!=0){
				filterStr += "#" + (4+i);
			}else{
				filterStr += "#" + (3+i);
			}
		}
		filterStr += ")";		
		if(idSet.size()!=0){
			filter.setMaskString("#0 and (#1 or #2) and #3" +filterStr);
		}else{
			filter.setMaskString("#0 and (#1 or #2)" +filterStr);
		}
		
		viewInfo.setFilter(filter);
		this.kdtAgioSchemeEntry.checkParsed();
		this.kdtAgioSchemeEntry.getColumn(AS_AGIO).setEditor(CommerceHelper.getKDBizPromptBoxEditor(queryInfoStr, viewInfo));
		this.kdtAgioSchemeEntry.getColumn(AS_AGIO).setRenderer(new ObjectValueRender(){
			public String getText(Object obj) {
				if(obj instanceof AgioBillInfo){
					return ((AgioBillInfo) obj).getName();
				}
				return super.getText(obj);
			}
		});
		this.kdtAgioSchemeEntry.getColumn(AS_AGIOTYPE).setEditor(CommerceHelper.getKDComboBoxEditor(AgioCalTypeEnum.getEnumList()));
		this.kdtAgioSchemeEntry.getColumn(AS_AGIOTYPE).getStyleAttributes().setLocked(true);
		KDComboBox combo = new KDComboBox();
        for(int i = 0; i < OperateEnum.getEnumList().size()-2; i++){
        	combo.addItem(OperateEnum.getEnumList().get(i));
        }
        KDTDefaultCellEditor comboEditor = new KDTDefaultCellEditor(combo);
		this.kdtAgioSchemeEntry.getColumn("operate").setEditor(comboEditor);
	}
	
	public void onLoad() throws Exception {
		initKdtAgioSchemeEntry();
		super.onLoad();
		this.btnCalculator.setIcon(EASResource.getIcon("imgTbtn_counter"));	
		this.txtName.setMaxLength(255);
		this.txtNumber.setMaxLength(80);
		this.txtDescription.setMaxLength(255);
		initCancelBtn();
		SHEManageHelper.handleCodingRule(this.txtNumber, this.oprtState, editData, this.getBizInterface(),null);
	}
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		super.actionSubmit_actionPerformed(e);
		SHEManageHelper.handleCodingRule(this.txtNumber, this.oprtState, editData, this.getBizInterface(),null);
	}
	private void initCancelBtn() {
		if( !OprtState.ADDNEW.equals(getOprtState())){
			AgioSchemeInfo agioScheme = (AgioSchemeInfo)this.editData;
			if(agioScheme!=null&& agioScheme.isIsEnabled()){
				this.btnCancel.setEnabled(true);
				this.btnCancelCancel.setEnabled(false);
			}else{
				this.btnCancel.setEnabled(false);
				this.btnCancelCancel.setEnabled(true);
				if(OprtState.VIEW.equals(getOprtState())){
					this.btnEdit.setEnabled(true);
				}
			}
		}
		if(OprtState.VIEW.equals(getOprtState())){
			this.btnAddLine.setEnabled(false);
			this.btnRemoveLine.setEnabled(false);
		}
	}


	protected void kdtAgioSchemeEntry_editStopped(KDTEditEvent e) throws Exception {
		IRow row = this.kdtAgioSchemeEntry.getRow(e.getRowIndex());
		ICell cell = row.getCell(AS_AGIO);
		AgioBillInfo agio = (AgioBillInfo)cell.getValue();
		AgioSchemeEntryInfo agioEntryInfo = (AgioSchemeEntryInfo)row.getUserObject();
		agioEntryInfo.setAgioBill(agio);
		agioEntryInfo.setAgioType(agio!=null?agio.getCalType():null);
		if(agio!=null&&agio.getCalType()!=null&&(agioEntryInfo.getAgioType().equals(AgioCalTypeEnum.JianDian)||agioEntryInfo.getAgioType().equals(AgioCalTypeEnum.Dazhe))){
			row.getCell("operate").getStyleAttributes().setLocked(false);
			agioEntryInfo.setOperate((OperateEnum) row.getCell("operate").getValue());
		}else{
			row.getCell("operate").getStyleAttributes().setLocked(true);
			row.getCell("operate").setValue(OperateEnum.MULTIPLY);
			agioEntryInfo.setOperate(OperateEnum.MULTIPLY);
		}
		row.getCell(AS_AGIOTYPE).setValue(agio!=null?agio.getCalType():null);
	}
	
	 public SelectorItemCollection getSelectors(){
		 SelectorItemCollection sc = super.getSelectors();
		 sc.add("agioSchemeEntry.agioBill.*");
		 sc.add("agioSchemeEntry.*");
		 sc.add("*");
		 sc.add("CU.*");
		 return sc;
	    }
	 public void loadFields() {
		super.loadFields();
		kdtAgioSchemeEntry.removeRows();
		AgioSchemeEntryCollection  agiocoll = this.editData.getAgioSchemeEntry();
		for(int i = 0 ;i < agiocoll.size();i++){
			AgioSchemeEntryInfo agioEntryInfo = agiocoll.get(i);
			IRow row = kdtAgioSchemeEntry.addRow();
			row.setUserObject(agioEntryInfo);
			row.getCell(AS_AGIO).setValue(agioEntryInfo.getAgioBill());
			row.getCell(AS_AGIOTYPE).setValue(agioEntryInfo.getAgioType());
			row.getCell("operate").setValue(agioEntryInfo.getOperate());
			if(agioEntryInfo.getAgioType()!=null&&(agioEntryInfo.getAgioType().equals(AgioCalTypeEnum.JianDian)||agioEntryInfo.getAgioType().equals(AgioCalTypeEnum.Dazhe))){
				row.getCell("operate").getStyleAttributes().setLocked(false);
			}else{
				row.getCell("operate").getStyleAttributes().setLocked(true);
			}
		}
	}
	 
	 public void storeFields()
	    {
		 AgioSchemeEntryCollection  agiocoll = this.editData.getAgioSchemeEntry();
		 agiocoll.clear();
	      for(int i = 0 ; i < kdtAgioSchemeEntry.getRowCount();i++){
	    	  agiocoll.add((AgioSchemeEntryInfo)kdtAgioSchemeEntry.getRow(i).getUserObject());
	      }
		 super.storeFields();
	    }
	 
	 public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		 	checkModified();
		 	btnCancel.setVisible(false);
			btnCancelCancel.setVisible(false);
			this.getNumberCtrl().requestFocus();
//			checkModified();
			addnew();
			SHEManageHelper.handleCodingRule(this.txtNumber, this.oprtState, editData, this.getBizInterface(),null);
			
	}
	 private void addnew()
	    {
	    	//新增时清理BOTP状态。
	        if(getUIContext().get(BOTPViewS) != null)
	        {
	            getUIContext().remove(BOTPViewS);
	        }
	        if(getUIContext().get(UIContext.INIT_DATAOBJECT) != null)
	        {
	            getUIContext().remove(UIContext.INIT_DATAOBJECT);
	        }
	        setSave(false);
	        setOprtState(STATUS_ADDNEW);
	        setDataObject(createNewData());
	        applyDefaultValue(this.editData);
	        getUILifeCycleHandler().fireOnCreateNewData(this,editData);
	        setDataObject(this.editData);

	        showMessageForStatus();

	        unLockUI();
	        loadFields();
	        this.initOldData(editData);
	        setDefaultFocused();
	        this.appendFootRow(null);
	    }
	 public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		 if (isSystemDefaultData()) {
				MsgBox.showError(EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, "operate_SysData"));
				return;
			}
	    	String selectID = this.editData.getId().toString();
	    	if(outPutWarningSentanceAndVerifyCancelorCancelCancelByID("删除",selectID)){//判断是否启用禁用
	    		return;
	    	}

	         IObjectPK pk = new ObjectUuidPK(this.editData.getId());
	         if (confirmRemove())
	         {
	         	boolean bool=UtilRequest.isPrepare("ActionRemove",this);
	         	if (bool)
	         	{
	         		prepareRomove(null).callHandler();
	         	}
	             removeByPK(pk);
	         }
	}
	 
	 public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		 if (isSystemDefaultData()) {
				MsgBox.showError(EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, "operate_SysData"));
				return;
			}
	    	String selectID = this.editData.getId().toString();
	    	if(outPutWarningSentanceAndVerifyCancelorCancelCancelByID("修改",selectID)){//判断是否启用禁用
	    		return;
	    	}
			this.getNumberCtrl().requestFocus();
			 //在修改前保存状态，最后可恢复。2006-11-16
	        String tempOprState = getOprtState();
	        setSave(false);
	    	//TODO 2005-9-20 张帆 （暂时加入，第二阶段去掉。）
	        // 应EAS管理会计系统部邓玉龙要求 为保持和ListUI的一致性，将该方法注释掉  edit by 林培森 2009.12.24
	        //checkForSType(ACTION_MODIFY);
	    	// 应用网络互斥
	        this.setOprtState(STATUS_EDIT);
	        IObjectValue val = (IObjectValue)getUIContext().get("CURRENT.VO") ;
	        getUIContext().put("CURRENT.VO",null) ;
	        try
	        {
	            setDataObject(val) ;
	        }
	        catch(Exception ex)
	        {
	            setOprtState(tempOprState);
	            getUIContext().put("CURRENT.VO",val) ;
	            throw ex;
	        }

	        unLockUI();

	        showMessageForStatus();

	        //lockUIComponent(false);
	        //getActionManager().enableAllAction();
	        initDataStatus();
	        setDefaultFocused();
	        //initScrollButtons();
	        this.btnAddLine.setEnabled(true);
			this.btnRemoveLine.setEnabled(true);
	}
	 
	 protected void setIsEnable(boolean flag) throws Exception {
			baseDataInfo = getEditData();
//			getCtrler().checkPermission(FDCBaseDataClientCtrler.ACTION_MODIFY);
//			if (!flag && !canCancel)
//				if (isSystemDefaultData()) {
//					MsgBox.showError(EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, "operate_SysData"));
//					return;
//				}
			baseDataInfo.setIsEnabled(flag);
			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add(new SelectorItemInfo("isEnabled"));
			String message = null;
			if (flag) {
				getBizInterface().updatePartial(baseDataInfo, sic);
				message = EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, "Enabled_OK");
			} else {
				getBizInterface().updatePartial(baseDataInfo, sic);
				message = EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, "DisEnabled_OK");
			}
			setMessageText(message);
			showMessage();

			setDataObject(getValue(new ObjectUuidPK(baseDataInfo.getId())));
			loadFields();
			setSave(true);
			setSaved(true);

			this.btnCancelCancel.setVisible(!flag);
			this.btnCancelCancel.setEnabled(!flag);
			this.btnCancel.setVisible(flag);
			this.btnCancel.setEnabled(flag);
			// this.chkIsEnabled.setSelected(flag);

		}
	 protected void btnCalculator_actionPerformed(ActionEvent e) throws Exception {
		 CalculatorDialog calc = new CalculatorDialog(this);
	        calc.showDialog(2, true);
	}
}