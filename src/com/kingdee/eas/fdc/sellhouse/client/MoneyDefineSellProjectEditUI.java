///**
// * output package name
// */
//package com.kingdee.eas.fdc.sellhouse.client;
//
//import java.awt.event.ActionEvent;
//import java.sql.Timestamp;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Comparator;
//import java.util.EventListener;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//import java.util.regex.Pattern;
//
//import javax.swing.SwingConstants;
//
//import org.apache.log4j.Logger;
//
//import com.kingdee.bos.ctrl.extendcontrols.KDCommonPromptDialog;
//import com.kingdee.bos.ctrl.kdf.table.IRow;
//import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
//import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
//import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
//import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
//import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
//import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
//import com.kingdee.bos.dao.IObjectValue;
//import com.kingdee.bos.metadata.IMetaDataLoader;
//import com.kingdee.bos.metadata.MetaDataLoaderFactory;
//import com.kingdee.bos.metadata.MetaDataPK;
//import com.kingdee.bos.metadata.entity.EntityViewInfo;
//import com.kingdee.bos.metadata.entity.FilterInfo;
//import com.kingdee.bos.metadata.entity.FilterItemInfo;
//import com.kingdee.bos.metadata.entity.SelectorItemCollection;
//import com.kingdee.bos.metadata.query.util.CompareType;
//import com.kingdee.bos.ui.face.CoreUIObject;
//import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
//import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
//import com.kingdee.eas.common.client.OprtState;
//import com.kingdee.eas.common.client.SysContext;
//import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
//import com.kingdee.eas.fdc.basedata.client.FDCColorConstants;
//import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
//import com.kingdee.eas.fdc.propertymgmt.AdvanceReceiveFactory;
//import com.kingdee.eas.fdc.propertymgmt.AdvanceReceiveMatchFactory;
//import com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo;
//import com.kingdee.eas.fdc.sellhouse.MoneyDefineReverseEntryCollection;
//import com.kingdee.eas.fdc.sellhouse.MoneyDefineReverseEntryInfo;
//import com.kingdee.eas.fdc.sellhouse.MoneyDefineSellProjectFactory;
//import com.kingdee.eas.fdc.sellhouse.MoneyDefineSellProjectInfo;
//import com.kingdee.eas.fdc.sellhouse.MoneyTypeEnum;
//import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
//import com.kingdee.eas.framework.ICoreBase;
//import com.kingdee.eas.util.SysUtil;
//import com.kingdee.eas.util.client.MsgBox;
//
///**
// * output class name
// */
//public class MoneyDefineSellProjectEditUI extends AbstractMoneyDefineSellProjectEditUI {
//
//	private static final Logger logger = CoreUIObject.getLogger(MoneyDefineSellProjectEditUI.class);
//	SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
//	private Set idSet = new HashSet();
//	private boolean flag= false;
//	public boolean checkBeforeWindowClosing() {		
////		return super.checkBeforeWindowClosing();
//		return true;
//	}
//
//	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
//		this.txtRemark.setEnabled(true);
//		this.btnAdd.setEnabled(true);
//		this.btnDel.setEnabled(true);
//		this.btnUp.setEnabled(true);
//		this.btnDown.setEnabled(true);
//		super.actionEdit_actionPerformed(e);
////		this.prmtMoneyDefine.setRequired(false);
//		if(flag){
//			this.prmtMoneyDefine.setEnabled(false);
//		}
//		else {
//			this.prmtMoneyDefine.setRequired(true);
//			this.prmtMoneyDefine.setEnabled(true);
//		}
//	}
//	
//	
//	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception
//	{
//		this.txtRemark.setEnabled(true);
//		this.btnAdd.setEnabled(true);
//		this.btnDel.setEnabled(true);
//		this.btnUp.setEnabled(true);
//		this.btnDown.setEnabled(true);
//		super.actionAddNew_actionPerformed(e);
//		this.prmtMoneyDefine.setRequired(true);
//		this.prmtMoneyDefine.setEnabled(true);
//	}
//	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
//		if(editData.getId()!=null){  
//			FilterInfo info1  = new FilterInfo();
//			FilterInfo info2  = new FilterInfo();
//			info1.getFilterItems().add(new FilterItemInfo("moneyDefineSellProject.id",editData.getId().toString()));
//			info2.getFilterItems().add(new FilterItemInfo("offsetrule.id",editData.getId().toString()));
//			if (AdvanceReceiveMatchFactory.getRemoteInstance().exists(info1) || AdvanceReceiveFactory.getRemoteInstance().exists(info2)){
//				FDCMsgBox.showWarning("该冲抵规则被引用，不能删除！");
//				SysUtil.abort();
//			}
//		}
//		super.actionRemove_actionPerformed(e);
//		prmtMoneyDefine.setEnabled(true);
//		prmtMoneyDefine.setRequired(true);
//	}
//	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
//		if (this.prmtMoneyDefine.getValue() == null || this.prmtMoneyDefine.getValue() == "") {
//			FDCMsgBox.showInfo("预收款名称不能为空！");
//			SysUtil.abort();
//		}
//		this.txtRemark.getTabSize();
//		if (this.txtRemark.getText().length()>255){
//			FDCMsgBox.showInfo("描述字符数超过最大限制255！");
//			SysUtil.abort();
//		}
//		if (this.kdtReverseEntry.getRowCount() < 1) {
//			MsgBox.showInfo("冲抵收费项目最少要有一条！");
//			SysUtil.abort();
//		}
//		//写入冲抵顺序
//		int rowNum = this.kdtReverseEntry.getRowCount();
//		IRow row = null;
//		for (int i=0;i<rowNum;i++){
//			this.kdtReverseEntry.getCell(i, "orderNumber").setValue(new Integer(i+1));			
//		}
//		super.actionSubmit_actionPerformed(e);
//	}
//	
//	
////	private void verifyOrderNumber() {
////		int rowNum = this.kdtReverseEntry.getRowCount();
////		IRow row = null;
////		List list = new ArrayList();
////		int i=0;
////		for(;i<rowNum;i++){
////			row = this.kdtReverseEntry.getRow(i);
////			Object obj = row.getCell("orderNumber").getValue();
////			if(obj!=null){
////				String str = String.valueOf(obj);
////				if(!isNumeric(str)){
////					MsgBox.showInfo("第"+(i+1)+"行的冲抵顺序不为数字！");
////					SysUtil.abort();
////				}
////			}else{
////				MsgBox.showInfo("第"+(i+1)+"行的冲抵顺序不可以为空！");
////				SysUtil.abort();
////			}
////
////			String num = String.valueOf(obj);
////			if(num.length()>1){
////
////				Integer intN = new Integer(num);
////				num = String.valueOf(intN);
////			}
////			list.add(num);
////		}
////		if(i>=rowNum){
////			int len = list.size();
////			String tempStr = "";
////			String innerStr = "";
////			for(int j = 0; j <len;j++ ){
////				tempStr = (String) list.get(j);
////				for(int k=j+1;k<len;k++){
////					innerStr = (String) list.get(k);
////					if(tempStr.equals(innerStr)){
////						MsgBox.showInfo("第"+(j+1)+"行的冲抵顺序有重复！");
////						SysUtil.abort();
////					}
////				}
////			}
////		}
////		
////	}
//	public static boolean isNumeric(String str) {
//		Pattern pattern = Pattern.compile("[0-9]*");
//		return pattern.matcher(str).matches();
//	}
//
//	protected void prmtMoneyDefine_dataChanged(DataChangeEvent e) throws Exception {
//		if (this.prmtMoneyDefine.getValue()==null) {
//			this.kdtReverseEntry.removeRows();
//		}
//	}
//
//	public SelectorItemCollection getSelectors() {
//		SelectorItemCollection sel = super.getSelectors();
//		sel.add("*");
//		sel.add("head.*");
//		sel.add("reverseEntry.*");
//		sel.add("reverseEntry.moneyDefine.*");
//		return sel;
//	}
//
//	public void loadFields() {
//		EventListener[] listeners = this.prmtSellProject.getListeners(DataChangeListener.class);
//		for (int i = 0; i < listeners.length; i++) {
//			this.prmtSellProject.removeDataChangeListener((DataChangeListener) listeners[i]);
//		}
//		EventListener[] periodListeners = this.prmtMoneyDefine.getListeners(DataChangeListener.class);
//		for (int i = 0; i < periodListeners.length; i++) {
//			this.prmtMoneyDefine.removeDataChangeListener((DataChangeListener) periodListeners[i]);
//		}
//		
//		super.loadFields();
////		prmtMoneyDefine.setEnabled(false);		
//		this.kdtReverseEntry.removeRows();
//		this.kdtReverseEntry.checkParsed();
//		MoneyDefineSellProjectInfo info = this.editData;
//		List listRow = new ArrayList();
//		
//		//判断规则是否被引用
//        try {
//			if(editData.getId()!=null){  
//				FilterInfo info1  = new FilterInfo();
//				FilterInfo info2  = new FilterInfo();
//				info1.getFilterItems().add(new FilterItemInfo("moneyDefineSellProject.id",editData.getId().toString()));
//				info2.getFilterItems().add(new FilterItemInfo("offsetrule.id",editData.getId().toString()));
//				if (AdvanceReceiveMatchFactory.getRemoteInstance().exists(info1) || AdvanceReceiveFactory.getRemoteInstance().exists(info2)){				
//					flag=true;
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		
//		MoneyDefineReverseEntryCollection col = info.getReverseEntry();
//		int j = 0;
//		for (int i = 0; i < col.size(); i++) {
//			MoneyDefineReverseEntryInfo entryInfo = col.get(i);
//			if (entryInfo != null) {
//				listRow .add(entryInfo);
//				
////				MoneyDefineInfo money = entryInfo.getMoneyDefine();
////				if (money != null) {
////					j++;
////					IRow row = this.kdtReverseEntry.addRow();
////					row.getCell("seq").setValue("" + j);
////					row.getCell("moneyDefine").setValue(money.getName());
////					row.getCell("orderNumber").setValue(entryInfo.get("orderNumber"));
////					row.setUserObject(entryInfo);
////					row.getCell("orderNumber").getStyleAttributes().setBackground(FDCColorConstants.requiredColor);
////				}
//			}
//		}
//		
//			
//	    //排序 按照orderNumber
//		sort(listRow);
//		
//		
//		//排序后 显示
//		if(listRow!=null){
//			for(int i=0;i<listRow.size();i++){
//				MoneyDefineReverseEntryInfo entryInfo = (MoneyDefineReverseEntryInfo)listRow.get(i);
//				MoneyDefineInfo money = entryInfo.getMoneyDefine();
//				if (money != null) {
//					j++;
//					IRow row = this.kdtReverseEntry.addRow();
//					row.getCell("id").setValue(money.getId().toString());
//					row.getCell("seq").getStyleAttributes().setLocked(true);					
//					row.getCell("seq").setValue("" + j);
//					row.getCell("moneyDefine").getStyleAttributes().setLocked(true);
//					row.getCell("moneyDefine").setValue(money.getName());
//					row.getCell("orderNumber").setValue(entryInfo.get("orderNumber"));
//					row.setUserObject(entryInfo);
//					row.getCell("orderNumber").getStyleAttributes().setBackground(FDCColorConstants.requiredColor);
//					if((this.oprtState.equals(OprtState.EDIT) || this.oprtState.equals(OprtState.VIEW)) && flag){	
//						idSet.add(row.getCell("id").getValue().toString());
//					}	
//				}
//			}
//		}
//		
//		
//		for (int i = 0; i < listeners.length; i++) {
//			this.prmtSellProject.addDataChangeListener((DataChangeListener) listeners[i]);
//		}
//
//		for (int i = 0; i < periodListeners.length; i++) {
//			this.prmtMoneyDefine.addDataChangeListener((DataChangeListener) periodListeners[i]);
//		}
//		if(this.prmtMoneyDefine.getValue()!=null){
//			this.prmtMoneyDefine.setRequired(false);
//			this.prmtMoneyDefine.setEnabled(false);
//		}
//	}
//	
//	
////排序
//	private List sort(List list){
//		Collections.sort(list, new Comparator(){
//			public int compare(Object arg0, Object arg1) {
//				MoneyDefineReverseEntryInfo obj0 = (MoneyDefineReverseEntryInfo)arg0;
//				MoneyDefineReverseEntryInfo obj1 = (MoneyDefineReverseEntryInfo)arg1;
//				Comparable tmp0 = (Comparable) obj0.get("orderNumber");
//				Comparable tmp1 = (Comparable) obj1.get("orderNumber");
//				if(tmp0 == null  ||  tmp1 == null){
//					return 0;
//				}
//				if((tmp0.toString().trim().equals(null)) && (tmp1.toString().trim().equals(null))){
//					Integer integer0= new Integer(tmp0.toString());
//					Integer integer1= new Integer((String)tmp1);
//					return (integer0.intValue()>integer1.intValue())?1:-1;
//				}else{
//					return tmp0.compareTo(tmp1);
//				}
//				
//			}
//		});
//		return list;
//	}
//	
//	
//	
//
//	public void onLoad() throws Exception {
//		prmtMoneyDefineSetView();
//		this.setUITitle("预收冲抵规则编辑");
//		this.actionSave.setVisible(false);
//		this.actionCopy.setVisible(false);
//		this.actionPrint.setVisible(false);
//		this.actionPrintPreview.setVisible(false);
//		this.actionCancel.setVisible(false);
//		this.actionCancelCancel.setVisible(false);
//		this.actionFirst.setVisible(false);
//		this.actionPre.setVisible(false);
//		this.actionNext.setVisible(false);
//		this.actionLast.setVisible(false);
//		this.prmtMoneyDefine.setEnabled(false);
//		this.txtRemark.setEnabled(false);
////		this.prmtMoneyDefine.setRequired(true);
//		this.kdtReverseEntry.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
//		//限制备注长度为255
//        this.txtRemark.setMaxLength(255);          
//        super.onLoad();	      
//		//限制长度为80
////		KDTextField ca = new KDTextField();
////        ca.setHorizontalAlignment(JTextField.LEFT);
////        ca.setVisible(true);
////        ca.setEnabled(true);
////        ca.setMaxLength(80);
//		KDFormattedTextField  formattedTextField = new KDFormattedTextField();
//		formattedTextField.setVisible(true);
//		formattedTextField.setEditable(true);
//		formattedTextField.setHorizontalAlignment(SwingConstants.RIGHT);
//		formattedTextField.setDataType(KDFormattedTextField.INTEGER);
//		KDTDefaultCellEditor IntergeCellEditor = new KDTDefaultCellEditor(formattedTextField);
//		if(OprtState.EDIT.equals(this.oprtState)){
//			this.prmtMoneyDefine.setEnabled(false);
//			this.txtRemark.setEnabled(true);
//			if (this.getUIContext().get("orgStructure") != null){
//				btnAddNew.setEnabled(false);
//			}
//		}
//		
//        //如果是启用状态 则不可以编辑
//        if(this.editData.isIsEnabled()){
//        	this.actionEdit.setEnabled(false);
//        }
////        //查看界面下，  非销售组织是不可以新增或删除
////        if(OprtState.VIEW.equals(this.getOprtState()) && !saleOrg.isIsBizUnit()){
////        	this.actionAddNew.setEnabled(false);
////        	this.actionRemove.setEnabled(false);
////        }
//        if(!saleOrg.isIsBizUnit()){
//			this.btnAddNew.setEnabled(false);
//			this.btnEdit.setEnabled(false);
//			this.btnSubmit.setEnabled(false);
//			this.btnRemove.setEnabled(false);
//			this.menuItemAddNew.setEnabled(false);
//			this.menuItemEdit.setEnabled(false);
//			this.menuItemSubmit.setEnabled(false);
//			this.menuItemRemove.setEnabled(false);
//		}
//        if(OprtState.ADDNEW.equals(oprtState)){
//        	this.prmtMoneyDefine.setRequired(true);
//        	this.prmtMoneyDefine.setEnabled(true);
//        	this.txtRemark.setEnabled(true);
//        }
//        
//      boolean isEnabled =  this.editData.isIsEnabled();
//      boolean isAddNew = OprtState.ADDNEW.equals(oprtState);
//      this.actionRemove.setEnabled(!isAddNew && !isEnabled);
//    //查看状态
//		if(OprtState.VIEW.equals(oprtState)){
////			this.prmtMoneyDefine.setRequired(false);
////			this.prmtMoneyDefine.setEnabled(false);
//			if (!saleOrg.isIsBizUnit()){
//				this.actionRemove.setEnabled(false);
//			}
//			if (this.getUIContext().get("orgStructure") != null){
//				btnAddNew.setEnabled(false);
//			}
//			btnAdd.setEnabled(false);
//			btnDel.setEnabled(false);
//			btnUp.setEnabled(false);
//			btnDown.setEnabled(false);
//		}
//		//如果规则没有被引用款项类型也可修改
//     if(OprtState.EDIT.equals(oprtState) && !flag){
//    	 this.prmtMoneyDefine.setRequired(true);
//    	 this.prmtMoneyDefine.setEnabled(true);
//    	 this.txtRemark.setEnabled(true);
//     }
//	}
//
//	private void prmtMoneyDefineSetView() {
//		CompanyOrgUnitInfo currFiOrg = SysContext.getSysContext().getCurrentFIUnit();
//		EntityViewInfo view = new EntityViewInfo();
//		FilterInfo filter = new FilterInfo();		
//    	if(currFiOrg != null){
//    		filter.getFilterItems().add(new FilterItemInfo("orgUnit.id", currFiOrg.getId(), CompareType.EQUALS));
//    	}else {
//    		String longNumber = SysContext.getSysContext().getCurrentOrgUnit().getLongNumber();
//    		filter.getFilterItems().add(new FilterItemInfo("orgUnit.longNumber", longNumber+"%", CompareType.LIKE));
//    	}
//		filter.getFilterItems().add(new FilterItemInfo("moneyType", MoneyTypeEnum.PREMONEY_VALUE));
//		filter.getFilterItems().add(new FilterItemInfo("sysType", MoneySysTypeEnum.MANAGESYS_VALUE));
//		filter.getFilterItems().add(new FilterItemInfo("isGroup", Boolean.TRUE));
//		view.setFilter(filter);
//		this.prmtMoneyDefine.setEntityViewInfo(view);
//	}
//
//
//	/**
//	 * output class constructor
//	 */
//	public MoneyDefineSellProjectEditUI() throws Exception {
//		super();
//	}
//
//	/**
//	 * output storeFields method
//	 */
//	public void storeFields() {
//		super.storeFields();
//		MoneyDefineSellProjectInfo info = this.editData;
//		
//		MoneyDefineInfo moneyDefineInfo = (MoneyDefineInfo) this.prmtMoneyDefine.getValue();
//		
//		info.setSellproject((SellProjectInfo) this.prmtSellProject.getValue());
//		info.setMoneyDefine(moneyDefineInfo);
//		info.setRemark(this.txtRemark.getText());
//		info.setLastUpdateUser(SysContext.getSysContext().getCurrentUserInfo());
//		info.setLastUpdateTime(new Timestamp(System.currentTimeMillis()));
//		
//		String name = moneyDefineInfo.getName()+"_冲抵规则";
//		info.setName(name);
//		MoneyDefineReverseEntryCollection col = new MoneyDefineReverseEntryCollection();
//		for (int i = 0; i < this.kdtReverseEntry.getRowCount(); i++) {
//			IRow row = this.kdtReverseEntry.getRow(i);
//			MoneyDefineReverseEntryInfo entryInfo = (MoneyDefineReverseEntryInfo) row.getUserObject();
//			entryInfo.setOrderNumber(row.getCell("orderNumber").getValue()==null?"":row.getCell("orderNumber").getValue().toString());
//			if (entryInfo != null) {
//				col.add(entryInfo);
//			}
//		}
//		info.getReverseEntry().clear();
//		info.getReverseEntry().addCollection(col);
//
//	}
//
//	/**
//	 * output btnAdd_actionPerformed method
//	 */
//	protected void btnAdd_actionPerformed(java.awt.event.ActionEvent e) throws Exception {
//		this.kdtReverseEntry.setEditable(false);
//		CompanyOrgUnitInfo currFiOrg = SysContext.getSysContext().getCurrentFIUnit();
//		HashSet idSet = new HashSet();
//		for (int i = 0; i < this.kdtReverseEntry.getRowCount(); i++) {
//			IRow row = this.kdtReverseEntry.getRow(i);
//			MoneyDefineReverseEntryInfo entryInfo = (MoneyDefineReverseEntryInfo) row.getUserObject();
//			if (entryInfo != null) {
//				MoneyDefineInfo money = entryInfo.getMoneyDefine();
//				if(money!=null){
//					idSet.add(money.getId().toString());
//				}
//			}
//		}
//		KDCommonPromptDialog dlg = new KDCommonPromptDialog();
//		IMetaDataLoader loader = MetaDataLoaderFactory.getRemoteMetaDataLoader();
//		dlg.setQueryInfo(loader.getQuery(new MetaDataPK("com.kingdee.eas.fdc.sellhouse.app.MoneyDefineQuery")));
//		EntityViewInfo view = new EntityViewInfo();
//		FilterInfo filter = new FilterInfo();
//		if(currFiOrg != null){
//    		filter.getFilterItems().add(new FilterItemInfo("orgUnit.id", currFiOrg.getId(), CompareType.EQUALS));
//    	}else {
//    		String longNumber = SysContext.getSysContext().getCurrentOrgUnit().getLongNumber();
//    		filter.getFilterItems().add(new FilterItemInfo("orgUnit.longNumber", longNumber+"%", CompareType.LIKE));
//    	}
//		filter.getFilterItems().add(new FilterItemInfo("moneyType", MoneyTypeEnum.PREMONEY_VALUE, CompareType.NOTEQUALS));
//		filter.getFilterItems().add(new FilterItemInfo("sysType", MoneySysTypeEnum.MANAGESYS_VALUE));
//		filter.getFilterItems().add(new FilterItemInfo("isGroup", Boolean.TRUE));
//		if(idSet.size()>0){
//			filter.getFilterItems().add(new FilterItemInfo("id", idSet,CompareType.NOTINCLUDE));			
//		}
//		view.setFilter(filter);		
//		dlg.setEntityViewInfo(view);
//		dlg.setEnabledMultiSelection(true);
//		dlg.show();
//		Object[] object = (Object[]) dlg.getData();
//		
////		KDFormattedTextField amountField = new KDFormattedTextField(KDFormattedTextField.BIGDECIMAL_TYPE);
////       amountField.setHorizontalAlignment(KDFormattedTextField.RIGHT);
////       amountField.setSupportedEmpty(true);
////       amountField.setPrecision(2);
////	    ICellEditor appReceiveAmountFieldEditor = new KDTDefaultCellEditor(amountField);
////	    this.kdtReverseEntry.getColumn("appReceiveAmount").setEditor(appReceiveAmountFieldEditor);
//		
//		int rowId = this.kdtReverseEntry.getRowCount();
//		if (object != null && object.length > 0) {
//			for (int j = 0; j < object.length; j++) {
//				MoneyDefineInfo money = (MoneyDefineInfo) object[j];
//				if (money != null) {
//					rowId++;
//					MoneyDefineReverseEntryInfo entryInfo = new MoneyDefineReverseEntryInfo();
//					entryInfo.setMoneyDefine(money);
//					IRow row = this.kdtReverseEntry.addRow();
//					row.getCell("id").setValue(money.getId().toString());
//					row.getCell("seq").setValue("" + rowId);
//					row.getCell("moneyDefine").setValue(money.getName());
//					row.getCell("orderNumber").getStyleAttributes().setBackground(FDCColorConstants.requiredColor);
//					row.setUserObject(entryInfo);
//				}
//			}
//		}
//
//	}
//
//	/**
//	 * output btnDel_actionPerformed method
//	 */
//	protected void btnDel_actionPerformed(java.awt.event.ActionEvent e) throws Exception {
//		
//		int[] selectRows = KDTableUtil.getSelectedRows(this.kdtReverseEntry);	
//		for(int i=0;i<selectRows.length;i++){
//			if (selectRows[i]!= -1) {
//				IRow row = kdtReverseEntry.getRow(selectRows[i]);
//				if( row != null){
//					String id = row.getCell("id").getValue().toString();
//					if(flag && idSet.contains(id)){
//						FDCMsgBox.showInfo("第"+(selectRows[i]+1)+"行款项被引用不能删除");
//						SysUtil.abort();
//					}
//				}
//			}
//		}
//		for(int i=0;i<selectRows.length;i++){
//			if (selectRows[i]!= -1) {
//				this.kdtReverseEntry.removeRow(selectRows[i]-i);
//			}
//		}
////		int activeRowIndex = this.kdtReverseEntry.getSelectManager().getActiveRowIndex();
////		IRow row = kdtReverseEntry.getRow(activeRowIndex);
////		if( row != null){
////			String id = row.getCell("id").getValue().toString();
////			if(flag && idSet.contains(id)){
////				FDCMsgBox.showInfo("该款项被引用不能删除");
////				SysUtil.abort();
////			}
////		}
////		if (activeRowIndex == -1) {
////			activeRowIndex = this.kdtReverseEntry.getRowCount();
////		}
////		this.kdtReverseEntry.removeRow(activeRowIndex);
//	}
//
//	protected IObjectValue createNewData() {
//
//		SellProjectInfo sellProject = (SellProjectInfo) this.getUIContext().get("sellProject");
//		MoneyDefineSellProjectInfo info = new MoneyDefineSellProjectInfo();
//		if (sellProject != null) {
//			info.setSellproject(sellProject);
//		}
//		info.setCreator(SysContext.getSysContext().getCurrentUserInfo());
//		info.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
//		info.setCreateTime(new Timestamp(System.currentTimeMillis()));
//		info.setIsEnabled(true);
//		return info;
//	}
//
//	protected ICoreBase getBizInterface() throws Exception {
//		return MoneyDefineSellProjectFactory.getRemoteInstance();
//	}
////上移冲抵收费项目 hz
//	protected void btnDown_actionPerformed(ActionEvent e) throws Exception {
//		IRow selectRow = KDTableUtil.getSelectedRow(this.kdtReverseEntry);
//		if (selectRow==null){
//			return;
//		}
//		if (selectRow.getRowIndex() == (this.kdtReverseEntry.getRowCount()-1)){
//			FDCMsgBox.showInfo("已经是最后一条信息了！");
//		}
//		this.kdtReverseEntry.moveRow(selectRow.getRowIndex(), (selectRow.getRowIndex()+1));
//		KDTableUtil.setSelectedRow(this.kdtReverseEntry, (selectRow.getRowIndex()+1));
//	}
//	//下移冲抵收费项目 hz
//	protected void btnUp_actionPerformed(ActionEvent e) throws Exception {
//		IRow selectRow = KDTableUtil.getSelectedRow(this.kdtReverseEntry);
//		if (selectRow==null) {
//			return;
//		}
//		if (selectRow.getRowIndex()==0){
//			MsgBox.showInfo("已经是第一条信息了！");
//			return;
//		}
//		this.kdtReverseEntry.moveRow(selectRow.getRowIndex(), (selectRow.getRowIndex()-1));
//		KDTableUtil.setSelectedRow(this.kdtReverseEntry, (selectRow.getRowIndex()-1));
//	}
//
//}