/**
 * output package name
 */
package com.kingdee.eas.fdc.finance.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.Action;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.BizDataFormat;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.CellTreeNode;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTAction;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTDeleteAction;
import com.kingdee.bos.ctrl.kdf.table.KDTEditHelper;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.BeforeActionEvent;
import com.kingdee.bos.ctrl.kdf.table.event.BeforeActionListener;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.event.NodeClickListener;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.swing.KDSpinner;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.eas.basedata.org.AdminOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.aimcost.client.AimCostClientHelper;
import com.kingdee.eas.fdc.basedata.CostAccountCollection;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCNumberHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCRenderHelper;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.finance.FDCBudgetAcctDataInfo;
import com.kingdee.eas.fdc.finance.FDCBudgetAcctEntryInfo;
import com.kingdee.eas.fdc.finance.FDCBudgetAcctInfo;
import com.kingdee.eas.fdc.finance.FDCBudgetAcctItemInfo;
import com.kingdee.eas.fdc.finance.FDCBudgetAcctItemTypeEnum;
import com.kingdee.eas.fdc.finance.FDCBudgetPeriodInfo;
import com.kingdee.eas.fdc.finance.FDCMonthBudgetAcctFactory;
import com.kingdee.eas.fdc.finance.FDCMonthBudgetAcctInfo;
import com.kingdee.eas.fdc.finance.IFDCBudgetAcct;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * 科目预算维护界面基类
 */
public abstract class FDCBudgetAcctEditUI extends AbstractFDCBudgetAcctEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(FDCBudgetAcctEditUI.class);
    
    //责任部门过滤界面
    private FDCBudgetAcctDeptFilterUI deptFilterUI = null;
    /**
     * output class constructor
     */
    public FDCBudgetAcctEditUI() throws Exception
    {
        super();
    }
    
    public String getMonthCostKey(int month){
    	if(month>12){
    		month=month%12;
    	}
    	return "month_cost"+month;
    }
    
    public String getMonthPayKey(int month){
    	if(month>12){
    		month=month%12;
    	}
    	return "month_pay"+month;
    }
    public boolean isMonthColumn(int colIndex){
    	String key=getDetailTable().getColumnKey(colIndex);
    	if(key!=null&&key.startsWith("month_")){
    		return true;
    	}
    	return false;
    }

    public void actionSave_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSave_actionPerformed(e);
        this.spYear.setEnabled(false);
		this.spMonth.setEnabled(false);
		btnHideBlankRow.doClick();
		setModify(false);
    }

    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSubmit_actionPerformed(e);
        this.spYear.setEnabled(false);
		this.spMonth.setEnabled(false);
		btnHideBlankRow.doClick();
		setModify(false);
    }

    public void actionAddLine_actionPerformed(ActionEvent e) throws Exception
    {
    	if(isCanAddRow()){
    		 if(getDetailTable() != null)
    	        {
    	            addLine(getDetailTable());
//    	            appendFootRow(getDetailTable());
    	        }
    	}else{
    		setMessageText("当前位置不允许新添行");
    		showMessage();
    	}
    }

    public void actionInsertLine_actionPerformed(ActionEvent e) throws Exception
    {
    	if(isCanAddRow()||isUnSettlecContractRow(FDCTableHelper.getSelectedRow(getDetailTable()))){
    		super.actionInsertLine_actionPerformed(e);
    	}else{
    		setMessageText("当前位置不允许插入行");
    		showMessage();
    	}
    }

    public void actionRemoveLine_actionPerformed(ActionEvent e) throws Exception
    {
    	IRow row=FDCTableHelper.getSelectedRow(getDetailTable());
    	if(row == null){
    		MsgBox.showWarning(this, "请选择行!");
    		SysUtil.abort();
    	}
    	if(row.getUserObject() instanceof FDCBudgetAcctEntryInfo &&((FDCBudgetAcctEntryInfo)(row.getUserObject())).getItemType()==FDCBudgetAcctItemTypeEnum.UNSETTLEDCONTRACT){
    		super.actionRemoveLine_actionPerformed(e);
    	}else{
    		setMessageText("当前行不允许删除");
    		showMessage();
    	}
    	setUnionAll();
    }

    public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
    	super.actionAudit_actionPerformed(e);
    	this.prmtAuditor.setValue(SysContext.getSysContext().getCurrentUserInfo().getName());
    }
    
    protected void attachListeners() {
		
	}

	protected void detachListeners() {
		
	}
	
	public void onLoad() throws Exception {
		this.prmtAuditor.setDisplayFormat("$name$");
		SpinnerNumberModel model=new SpinnerNumberModel();
		model.setMinimum(new Integer(1900));
		model.setMaximum(new Integer(3000));
		model.setStepSize(new Integer(1));
		this.spYear.setModel(model);
		model=new SpinnerNumberModel();
		model.setMinimum(new Integer(1));
		model.setMaximum(new Integer(12));
		model.setStepSize(new Integer(1));
		this.spMonth.setModel(model);
		super.onLoad();
		initTable();
		fillTable();
		initCtrlListener();
		
	}
	
	protected KDTextField getNumberCtrl() {
		return txtNumber;
	}
	
	protected IObjectValue createNewData() {
		FDCBudgetAcctInfo info=createNewFDCBudgetAcct();
		if(info!=null){
			info.setCurProject((CurProjectInfo)getUIContext().get("project"));
			info.setVerNumber(1.0f);
		}
		return info;
	}
	
	protected IObjectValue createNewDetailData(KDTable table) {
		FDCBudgetAcctEntryInfo entry=createNewFDCBudgetAcctEntry();
		entry.setItemType(FDCBudgetAcctItemTypeEnum.UNSETTLEDCONTRACT);
		entry.setCreator(SysContext.getSysContext().getCurrentUserInfo());
		entry.setDeptment(SysContext.getSysContext().getCurrentAdminUnit());
		entry.setEmptyRow(true);
		return entry;
	}
	abstract FDCBudgetAcctInfo createNewFDCBudgetAcct();
	abstract FDCBudgetAcctEntryInfo createNewFDCBudgetAcctEntry();
	abstract IFDCBudgetAcct getRemoteInterface()throws BOSException;
	
    public void storeFields()
    {

    }
    protected void beforeStoreFields(ActionEvent e) throws Exception {
//    	super.beforeStoreFields(e);
        getFDCBudgetAcctInfo().setCurProject((CurProjectInfo)txtProject.getUserObject());
        getFDCBudgetAcctInfo().setNumber(txtNumber.getText());
        getFDCBudgetAcctInfo().setVerNumber(Float.parseFloat(txtVerNumber.getText()));
        getFDCBudgetAcctInfo().setFdcPeriod(getPeriod());
        storeRows();
        
        //如果按部门进行了过滤,要将过滤的部门传过去,这样便于保存的时候合并上其它的部门数据
        this.editData.put("deptmentId", getUIContext().get("deptmentId"));
    }
    protected void storeRows(){
    }
    protected FDCBudgetAcctEntryInfo storeRow(IRow row){
    	return null;
    }
    public void loadFields() {
//    	super.loadFields();
    }
    
    protected void initTable(){
    	KDBizPromptBox f7 = new KDBizPromptBox();
    	f7.setQueryInfo("com.kingdee.eas.base.permission.app.F7UserQuery");
    	f7.setEditable(false);
    	f7.setDisplayFormat("$name$");
    	tblMain.getColumn("creator").setEditor(new KDTDefaultCellEditor(f7));
    	BizDataFormat bizDataFormat = new BizDataFormat("$name$");
    	ObjectValueRender render=new ObjectValueRender();
    	render.setFormat(bizDataFormat);
		tblMain.getColumn("creator").setRenderer(render);
    	f7 = new KDBizPromptBox();
    	f7.setQueryInfo("com.kingdee.eas.sem.mp.app.AdminOrgUnitF7");
    	f7.setEditable(false);
    	f7.setCommitFormat("$number$");
    	f7.setEditFormat("$number$");
    	f7.setDisplayFormat("$name$");
    	tblMain.getColumn("deptment").setEditor(new KDTDefaultCellEditor(f7));
    	tblMain.getColumn("acctNumber").setRenderer(FDCRenderHelper.getLongNumberRender());
    	tblMain.getColumn("conName").setEditor(FDCTableHelper.getTxtCellEditor(80,true));
    	tblMain.getColumn("desc").setEditor(FDCTableHelper.getTxtCellEditor(80,false));
    	tblMain.getColumn("splitAmt").setEditor(FDCTableHelper.getCellNumberEditor());
    	tblMain.getColumn("conAmt").setEditor(FDCTableHelper.getCellNumberEditor());
    	FDCTableHelper.setCutCopyPaseMode(tblMain, KDTEditHelper.VALUE);
		((KDTDeleteAction)tblMain.getActionMap().get(KDTAction.DELETE)).setDeleteMode(KDTEditHelper.VALUE);
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
					int number_col_index = tblMain.getColumn("acctName").getColumnIndex();
				tblMain.getViewManager().setFreezeView(-1, number_col_index+1);
			}});
		
		getDetailTable().setAfterAction(new BeforeActionListener() {
			public void beforeAction(BeforeActionEvent e) {

				if (BeforeActionEvent.ACTION_DELETE == e.getType() || BeforeActionEvent.ACTION_PASTE == e.getType()) {
					boolean existMonthCol = false;
					for (int i = 0; i < getDetailTable().getSelectManager().size(); i++) {
						KDTSelectBlock block = getDetailTable().getSelectManager().get(i);
						for (int colIndex = block.getBeginCol(); colIndex <= block.getEndCol(); colIndex++) {
							if (isMonthColumn(colIndex)) {
								existMonthCol = true;
								break;
							}
						}
						if (existMonthCol) {
							break;
						}
					}
					if (existMonthCol) {
						setUnionAll();
					}
				}
				setModify(true);
			}
		});
    }
    
    public void fillTable(){
    	clear();
    	fetchData();
    	this.txtProject.setText(getFDCBudgetAcctInfo().getCurProject()==null?null:this.editData.getCurProject().getName());
    	this.txtProject.setUserObject(getFDCBudgetAcctInfo().getCurProject());
    	//启用编码规则，设置为新增显示时，这里置空了
    	Object o = getUIContext().get("recensionID");
    	if(!getOprtState().equals(STATUS_ADDNEW)&&o!=null){
    		try {
				FDCMonthBudgetAcctInfo tempInfo = (FDCMonthBudgetAcctInfo) FDCMonthBudgetAcctFactory.getRemoteInstance().getValue(o.toString());
//				this.txtNumber.setText(getFDCBudgetAcctInfo().getNumber());
				this.txtNumber.setText(tempInfo.getNumber());
			} catch (EASBizException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (BOSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	
    	//版本号保留一位
    	String verNumberStr = getFDCBudgetAcctInfo().getVerNumber()+"";
    	int index = verNumberStr.indexOf('.');
    	this.txtVerNumber.setText(verNumberStr.substring(0,index+2));
    	this.prmtAuditor.setValue(editData.getAuditor());
    	
//    	this.txtVerNumber.setText(getFDCBudgetAcctInfo().getVerNumber()+"");
    	FDCBudgetPeriodInfo period=getFDCBudgetAcctInfo().getFdcPeriod();
    	if(period!=null){
    		setSpValue(spYear, period.getYear());
    		if (this.spMonth.isVisible()) {
    			setSpValue(spMonth, period.getMonth());
			}
    		if(getFDCBudgetAcctInfo().getId()!=null){
    			this.spYear.setEnabled(false);
    			this.spMonth.setEnabled(false);
    		}
    	}
    	try {
			tblMain.setRefresh(false);
			tblMain.removeRows(false);
			resetTableHead();
			addColumnTree();
			Integer maxLevel=(Integer)getDataMap().get("maxLevel");
			if(maxLevel!=null){
				getDetailTable().getTreeColumn().setDepth(maxLevel.intValue()+2);
			}
			CostAccountCollection costAccounts = getCostAccounts();
			for (Iterator iter = costAccounts.iterator(); iter.hasNext();) {
				CostAccountInfo info = (CostAccountInfo) iter.next();
				IRow row=tblMain.addRow();
				row.getCell("acctNumber").setValue(info.getLongNumber());
				row.getCell("acctName").setValue(info.getName());
				row.setTreeLevel(info.getLevel()-1);
				//明细行上才会有科目实体
				if(info.isIsLeaf()){
					fillEntrys(info);
					row.setUserObject(info);
					fillCostAccountRow(row);
//					row.getStyleAttributes().setBackground(FDCTableHelper.daySubTotalColor);
				}else{
				}
				row.getStyleAttributes().setBackground(new Color(0xF0EDD9));
			}
			setEditable();
			afterFillTable();
		} finally {
			tblMain.setRefresh(true);
			tblMain.repaint();
//			setOprtState(this.oprtState);
		}		
    }
    
    protected void fillCostAccountRow(IRow row) {
    	if(row.getUserObject() instanceof CostAccountInfo){
    		CostAccountInfo info=(CostAccountInfo)row.getUserObject();
    		String key=info.getId().toString();
    		Map map=(Map)getDataMap().get("costMap");
    		if(map!=null&&map.size()>0){
    			FDCBudgetAcctDataInfo dataInfo=(FDCBudgetAcctDataInfo)map.get(key);
    			if(dataInfo==null){
    				return;
    			}
    			row.getCell("aimCost").setValue(dataInfo.getAimCost());
    			row.getCell("dynCost").setValue(dataInfo.getDynCost());
    		}
    	}
	}
    
	protected void afterFillTable() {
		setUnionAll();
    	getDetailTable().getColumn("dynCost").getStyleAttributes().setHided(!isShowDynAimCost());
    	getDetailTable().getColumn("aimCost").getStyleAttributes().setHided(!isShowDynAimCost());
    	Set costColumns=getCostColumns();
    	boolean isShowCost=isShowCost();
    	for(Iterator iter=costColumns.iterator();iter.hasNext();){
    		String key=(String)iter.next();
    		getDetailTable().getColumn(key).getStyleAttributes().setHided(!isShowCost);
    	}
    	
    	actionQuery.setVisible(isHasQueryDeptment());
    	menuView.setVisible(actionQuery.isVisible());
	}
	
	protected void resetTableHead() {
		for(int i=getDetailTable().getColumnCount()-1;i>=0;i--){
			if(isMonthColumn(i)){
				getDetailTable().removeColumn(i);
			}
		}
	}
	
    public void fillEntrys(CostAccountInfo info){
    	String costAcctId = info.getId().toString();
		AbstractObjectCollection entrys=getFDCBudgetAcctEntryCollection(costAcctId+FDCBudgetAcctItemTypeEnum.CONTRACT_VALUE);
		
		if (entrys != null) {
			addSettledContractRow(tblMain.addRow(), info);
			for (Iterator iterator = entrys.iterator(); iterator.hasNext();) {
				FDCBudgetAcctEntryInfo entry = (FDCBudgetAcctEntryInfo) iterator.next();
				entry.setCostAccount(info);
				IRow row = tblMain.addRow();
				row.setUserObject(entry);
				loadRow(row);
			}
		}
		//unsettled contract item
		entrys=getFDCBudgetAcctEntryCollection(costAcctId+FDCBudgetAcctItemTypeEnum.UNSETTLEDCONTRACT_VALUE);
		addUnSettledContractRow(tblMain.addRow(), info);
		if (entrys != null) {
			for (Iterator iterator = entrys.iterator(); iterator.hasNext();) {
				FDCBudgetAcctEntryInfo entry = (FDCBudgetAcctEntryInfo) iterator.next();
				entry.setCostAccount(info);
				IRow row = tblMain.addRow();
				row.setUserObject(entry);
				loadRow(row);
			}
//			//add a blank row
//			IObjectValue detailData = createNewDetailData(getDetailTable());
//			IRow row=getDetailTable().addRow();
//			row.setUserObject(detailData);
//			((FDCBudgetAcctEntryInfo)detailData).setCostAccount(info);
//			loadRow(row);
		}
		
    }
    
	public void loadRow(IRow row){
		FDCBudgetAcctEntryInfo entry=(FDCBudgetAcctEntryInfo)row.getUserObject();
		row.setTreeLevel(entry.getCostAccount().getLevel()+1);
		row.getCell("conNumber").setValue(entry.getNumber());
		row.getCell("conName").setValue(entry.getName());
		if(entry.getContractBill()!=null&&entry.getContractBill().getPartB()!=null){
			row.getCell("partB").setValue(entry.getContractBill().getPartB().getName());
		}
//		row.getCell("conAmt").setValue(entry.getContractBill()==null?null:entry.getContractBill().getAmount());
		row.getCell("conAmt").setValue(entry.getConAmt());
		row.getCell("conLatestAmt").setValue(entry.getConLatestPrice());
		row.getCell("splitAmt").setValue(entry.getSplitAmt());
		
		row.getCell("desc").setValue(entry.getDesc());
		row.getCell("creator").setValue(entry.getCreator());
		row.getCell("deptment").setValue(entry.getDeptment());
		row.getCell("id").setValue(entry.getId());
		row.getCell("id").setUserObject(Boolean.valueOf(entry.isEmptyRow()));
		
		if(entry.getItemType()==FDCBudgetAcctItemTypeEnum.UNSETTLEDCONTRACT){
			row.getCell("partB").setValue(entry.getPartB());
			row.getCell("conName").getStyleAttributes().setLocked(false);
			row.getCell("splitAmt").getStyleAttributes().setLocked(false);
			row.getCell("conAmt").getStyleAttributes().setLocked(false);
			row.getCell("partB").getStyleAttributes().setLocked(false);
		}
		fillItems(row);
    }
	
    public void fillItems(IRow row){
    	FDCBudgetAcctEntryInfo entry=(FDCBudgetAcctEntryInfo)row.getUserObject();
    	AbstractObjectCollection items=(AbstractObjectCollection)entry.get("items");
    	if(items==null||items.size()==0){
    		return;
    	}
    	for(Iterator iterator=items.iterator();iterator.hasNext();){
    		FDCBudgetAcctItemInfo item=(FDCBudgetAcctItemInfo)iterator.next();
    		row.getCell(getMonthCostKey(item.getMonth())).setValue(item.getCost());
    		row.getCell(getMonthPayKey(item.getMonth())).setValue(item.getAmount());
    	}
    }
    
    private CostAccountCollection getCostAccounts(){
    	return (CostAccountCollection)getDataMap().get("costAccounts");
    }
    
    /**
     * key+getItemTye
     * @param key
     * @return
     */
    private AbstractObjectCollection getFDCBudgetAcctEntryCollection(String key){
    	Map map = (Map)getDataMap().get("retBudgetEntrys");
		return (AbstractObjectCollection)map.get(key);
    }
    
    private Map dataMap=null;
    public Map getDataMap(){
    	if(dataMap==null){
    		fetchData();
    		if(dataMap==null){
    			dataMap=new  HashMap();
    		}
    	}
    	return dataMap;
    }
    
    private void clear(){
    	if(dataMap!=null){
    		dataMap.clear();
    	}
    	dataMap=null;
    }
    
    protected void fetchData(){
    	String id = null;
    	if(getUIContext().get(UIContext.ID)!=null){
    		id = getUIContext().get(UIContext.ID).toString();
    	}
    	CurProjectInfo prj=(CurProjectInfo)getUIContext().get("project");
    	FDCBudgetPeriodInfo period=(FDCBudgetPeriodInfo)getUIContext().get("fdcPeriod");
    	if(id==null&&(prj==null||prj.getId()==null||period==null)){
    		return;
    	}
    	Map param=new HashMap();
    	param.put("id", id);
    	if(prj!=null&&prj.getId()!=null){
    		param.put("projectId", prj.getId().toString());
    	}
    	param.put("fdcPeriod", period);
    	param.put("isRecension", getUIContext().get("isRecension"));
    	param.put("deptmentId", getUIContext().get("deptmentId"));
    	param.put("respPersonId", getUIContext().get("respPersonId"));
    	param.put("creatorId", getUIContext().get("creatorId"));
    	param.put("editorId", getUIContext().get("editorId"));
    	try {
			dataMap=getRemoteInterface().fetchData(param);
			FDCBudgetAcctInfo ov=(FDCBudgetAcctInfo)getDataMap().get("editData");
			if(ov!=null&&ov.getId()==null&&!isRecension()){
				ov.setCurProject(prj);
				ov.setFdcPeriod(period);
			}
			if(this.isRecension()){
				ov.setSourceBillId(id);
			}
			setDataObject(ov);
		} catch (EASBizException e) {
			handUIException(e);
		} catch (BOSException e) {
			handUIException(e);
		}
    }
    
    protected KDTable getDetailTable() {
    	return tblMain;
    }
    
    protected void fetchInitData() throws Exception {
    }
    
    protected void fetchInitParam() throws Exception {
    }
    
    private ChangeListener splistener=null;
    
    protected void initCtrlListener(){
    	getDetailTable().addKDTEditListener(new KDTEditAdapter(){
    		public void editStopped(KDTEditEvent e) {
    			tblMain_editStopped(e);
    		}
    	});
    	getDetailTable().addPropertyChangeListener(new PropertyChangeListener(){
    		public void propertyChange(PropertyChangeEvent evt) {
    			tblMain_propertyChange(evt);
    		}
    	});
    	
    	splistener=new ChangeListener(){
			public void stateChanged(ChangeEvent e) {
				date_dateChanged(e);
			}
		};
		spYear.addChangeListener(splistener);
		spMonth.addChangeListener(splistener);
    }
    

	private void date_dateChanged(ChangeEvent e) {
		getUIContext().put("fdcPeriod", getPeriod());
		fillTable();
    	getDetailTable().validate();
	}
	
    protected void tblMain_editStopped(KDTEditEvent e){
    	int index=e.getRowIndex();
    	IRow row=getDetailTable().getRow(index);
    	row.getCell("creator").setValue(SysContext.getSysContext().getCurrentUserInfo());
    	if(row.getUserObject() instanceof FDCBudgetAcctEntryInfo){
    		FDCBudgetAcctEntryInfo entry=(FDCBudgetAcctEntryInfo)row.getUserObject();
    		if(entry.getItemType()==FDCBudgetAcctItemTypeEnum.UNSETTLEDCONTRACT){
    			row.getCell("deptment").setValue(SysContext.getSysContext().getCurrentAdminUnit());
    		}
    	}
    	setEmptRow(e);
    	setUnionAll();
    	setModify(true);
    }
    
    protected void tblMain_propertyChange(PropertyChangeEvent e){
    	
    }
    
    protected void setEmptyRow(IRow row,boolean flag){
    	row.getCell("id").setUserObject(Boolean.valueOf(flag));
    }
    
    protected boolean isEmptyRow(IRow row){
    	if(row.getUserObject() instanceof FDCBudgetAcctEntryInfo){
    		boolean isEmpty=Boolean.TRUE.equals(row.getCell("id").getUserObject());
    		return isEmpty;
    	}
    	return true;
    }
    
    protected boolean isCanAddRow(){
    	IRow row=FDCTableHelper.getSelectedRow(getDetailTable());
    	if(row == null){
    		MsgBox.showWarning(this, "请选择行!");
    		SysUtil.abort();
    	}
    	if(row.getUserObject() instanceof FDCBudgetAcctEntryInfo){
    		FDCBudgetAcctEntryInfo entry=(FDCBudgetAcctEntryInfo)row.getUserObject();
    		if(entry.getItemType()==FDCBudgetAcctItemTypeEnum.UNSETTLEDCONTRACT){
    			return true;
    		}
    	}else if(row.getCell("acctName").getValue()==FDCBudgetAcctItemTypeEnum.UNSETTLEDCONTRACT){
    		return true;
    	}
    	
    	//成本科目下如果没有待签订合同行也可以新增
    	return false;    	
    }
    
    protected void addLine(KDTable table)
    {
        if(table == null)
        {
            return;
        }
        IObjectValue detailData = createNewDetailData(table);
        int index=table.getSelectManager().getActiveRowIndex();
        ((FDCBudgetAcctEntryInfo)detailData).setCostAccount(findCostAccount(index));
        IRow addRow=null;
        if(table.getRowCount()==index+1){
        	//last row
        	addRow = table.addRow();
        }
        for(int i=index+1;i<table.getRowCount();i++){
        	IRow row=table.getRow(i);
        	if(!isSettlecContractRow(row)&&(row.getUserObject()==null|| row.getUserObject() instanceof CostAccountInfo)){
        		addRow=table.addRow(i);
        		break;
        	}
        }
        addRow.setUserObject(detailData);
        loadRow(addRow);
        afterAddLine(table, detailData);
    }
    
    protected void insertLine(KDTable table) {
        if(table == null)
        {
            return;
        }
        IObjectValue detailData = createNewDetailData(table);
        int index=table.getSelectManager().getActiveRowIndex();
        ((FDCBudgetAcctEntryInfo)detailData).setCostAccount(findCostAccount(index));
        if(isUnSettlecContractRow(getDetailTable().getRow(index))){
        	index+=1;
        }
        IRow addRow=table.addRow(index);
        addRow.setUserObject(detailData);
        loadRow(addRow);
        afterInsertLine(table, detailData);
    }
    
    private void addSettledContractRow(IRow row,CostAccountInfo info){
		row.setUserObject("addSettledContractRow");
		row.getCell("acctName").setValue(FDCBudgetAcctItemTypeEnum.CONTRACT);
		row.setTreeLevel(info.getLevel());
		row.getStyleAttributes().setBackground(FDCTableHelper.daySubTotalColor);
    }
    
    private void addUnSettledContractRow(IRow row,CostAccountInfo info){
//    	row.setUserObject(info);
    	row.setUserObject("addUnSettledContractRow");
		row.getCell("acctName").setValue(FDCBudgetAcctItemTypeEnum.UNSETTLEDCONTRACT);
		row.setTreeLevel(info.getLevel());
		row.getStyleAttributes().setBackground(FDCTableHelper.daySubTotalColor);
    }
    
    public CostAccountInfo findCostAccount(int index){
    	for(int i=index;i>=0;i--){
    		IRow row=getDetailTable().getRow(i);
    		if(row.getUserObject() instanceof FDCBudgetAcctEntryInfo){
    			return ((FDCBudgetAcctEntryInfo)row.getUserObject()).getCostAccount();
    		}else if(row.getUserObject() instanceof CostAccountInfo){
    			return (CostAccountInfo)row.getUserObject();
    		}
    	}
    	return null;
    }
    
    protected boolean isUnSettlecContractRow(IRow row){
    	if(row.getCell("acctName").getValue()==FDCBudgetAcctItemTypeEnum.UNSETTLEDCONTRACT){
    		return true;
    	}
    	return false;
    }
    
    protected boolean isSettlecContractRow(IRow row){
    	if(row.getCell("acctName").getValue()==FDCBudgetAcctItemTypeEnum.CONTRACT){
    		return true;
    	}
    	return false;
    }
    
    public void setEditable() {
		KDTable table = getDetailTable();
		if (getOprtState().equals(OprtState.VIEW)||getOprtState().equals(STATUS_FINDVIEW)) {
			table.getStyleAttributes().setLocked(true);
		} else {
			for (int i = 0; i < table.getColumnCount(); i++) {
				IColumn column = table.getColumn(i);
				if (isMonthColumn(i)) {
					column.getStyleAttributes().setLocked(false);
				} else if (column.getKey() != null && column.getKey().equals("desc")) {
					column.getStyleAttributes().setLocked(false);
				} else {
					column.getStyleAttributes().setLocked(true);
				}
			}
			for(int i=0;i<table.getRowCount();i++){
				IRow row=table.getRow(i);
				if(!(row.getUserObject() instanceof FDCBudgetAcctEntryInfo)){
					row.getStyleAttributes().setLocked(true);
				}else{
					FDCBudgetAcctEntryInfo entry=(FDCBudgetAcctEntryInfo)row.getUserObject();
					if(entry.getItemType()==FDCBudgetAcctItemTypeEnum.UNSETTLEDCONTRACT){
						row.getCell("conName").getStyleAttributes().setLocked(false);
						row.getCell("splitAmt").getStyleAttributes().setLocked(false);
						row.getCell("conAmt").getStyleAttributes().setLocked(false);
						row.getCell("partB").getStyleAttributes().setLocked(false);
					}
				}
			}
			IRow row=table.getHeadRow(0);
			row.getStyleAttributes().setLocked(false);
		}
		table.getHeadRow(0).getStyleAttributes().setLocked(true);
	}
    
    protected final FDCBudgetPeriodInfo getPeriod(){
    	return FDCBudgetPeriodInfo.getPeriod(getSpValue(spYear), getSpValue(spMonth), !spMonth.isVisible());
    }
    
    protected int getSpValue(KDSpinner sp){
    	if(sp.getValue()!=null){
    		return ((Integer)sp.getValue()).intValue();
    	}
    	return 0;
    }
    
    protected void setSpValue(KDSpinner sp,int v){
    	//注KDSpinner控件的界面更新通过内部的一个ChangeListener来完成,所有不能直接将所有的listener删除
    	if(splistener!=null){
    		sp.removeChangeListener(splistener);
    	}
    	sp.setValue(new Integer(v));
    	sp.addChangeListener(splistener);
    }
    
    private void addColumnTree() {
		// 设置表格列树
    	KDTable table=getDetailTable();
		IRow headRow = table.getHeadRow(0);
		headRow.getStyleAttributes().setLocked(true);
    	List list=getColumnTreeCols();
    	TreeNodeClickListener listener=new TreeNodeClickListener();
    	for(Iterator iter=list.iterator();iter.hasNext();){
    		String[] cols=(String[])iter.next();
    		CellTreeNode node = new CellTreeNode();
    		node.setTreeLevel(0);
    		node.setHasChildren(true);
    		node.setValue(headRow.getCell(cols[0]).getValue());
    		listener.addNode(node, cols);
    		node.addClickListener(listener);
    		
    		for(int i=0;i<cols.length;i++){
    			headRow.getCell(cols[i]).setValue(node);
    			headRow.getCell(cols[i]).getStyleAttributes().setLocked(false);
    		}
    	}
		headRow.getStyleAttributes().setLocked(true);
	}
    
    private class TreeNodeClickListener implements NodeClickListener{
    	private Map map=new HashMap();
    	public TreeNodeClickListener() {
		}
    	void addNode(CellTreeNode node,String[] cols){
    		map.put(node, cols);
    	}
    	public void doClick(CellTreeNode source, ICell cell, int type) {
			boolean isHide=source.isCollapse();
			String[] cols=(String[])map.get(source);
			if(cols==null){
				return;
			}
			Set set=getCostColumns();
			for(int i=1;i<cols.length;i++){
				getDetailTable().getColumn(cols[i]).getStyleAttributes().setHided(isHide);
				if(!isShowCost()&&set.contains(cols[i])){
					getDetailTable().getColumn(cols[i]).getStyleAttributes().setHided(true);
				}
			}
    	}
    }
    
    /**
     * List里面为表格列Key的字符串数组,字符串的第一个将做为收放的基准
     * @return
     */
    protected List getColumnTreeCols(){
    	KDTable table=getDetailTable();
    	List list=new ArrayList();
    	list.add(new String[]{"conName","conNumber","partB","conAmt","splitAmt","conLatestAmt"});
/*    	List tmpList=new ArrayList();
		int splitAmtIdx=table.getColumnIndex("splitAmt");
		int descIdx=table.getColumnIndex("desc");
		for(int i=splitAmtIdx+1;i<descIdx;i++){
			if(isMonthColumn(i)&&!table.getColumn(i).getStyleAttributes().isHided()){
				tmpList.add(table.getColumnKey(i));
			}
		}
		String [] array=new String[tmpList.size()];
		tmpList.toArray(array);
		list.add(array);*/
    	return list;
    }
    protected void setEmptRow(KDTEditEvent e){
    	
    }
    
    /**
     * 是否修订
     * @return
     */
    protected boolean isRecension(){
    	return getUIContext().get("isRecension")==Boolean.TRUE;
    }
    
	/**
	 * 按责任部门过滤
	 * @return
	 */
    protected boolean isHasQueryDeptment(){
		Boolean obj = (Boolean)getDataMap().get("isHasQueryDeptment");
		return obj==null?false:obj.booleanValue();
	}
	/**
	 * 是否显示目标成本,动态成本列
	 * @param ctx
	 * @return
	 */
	protected boolean isShowDynAimCost(){
		Boolean obj = (Boolean)getDataMap().get("isShowDynAimCost");
		return obj==null?false:obj.booleanValue();
	}
	/**
	 * 是否显示成本列
	 * @param ctx
	 * @return
	 */
	protected boolean isShowCost(){
		Boolean obj = (Boolean)getDataMap().get("isShowCost");
		return obj==null?false:obj.booleanValue();
	}
	/**
	 * 是否显示成本合同列
	 * @param ctx
	 * @return
	 */
	protected boolean isShowTotal(){
		Boolean obj = (Boolean)getDataMap().get("isShowTotal");
		return obj==null?false:obj.booleanValue();
	}
	
	protected void initWorkButton() {
		super.initWorkButton();
		actionShowBlankRow.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_entirely"));
		actionHideBlankRow.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_partshow"));
		actionQuery.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_filter"));
		
		actionAddNew.setEnabled(false);
		actionAddNew.setVisible(false);
		actionAddNew.setEnabled(false);
		actionCopy.setVisible(false);
		actionLast.setEnabled(false);
		actionLast.setVisible(false);
		actionPre.setVisible(false);
		actionPre.setEnabled(false);
		actionNext.setEnabled(false);
		actionNext.setVisible(false);
		actionFirst.setVisible(false);
		actionFirst.setEnabled(false);
		actionTraceDown.setVisible(false);
		actionTraceDown.setEnabled(false);
		actionTraceUp.setVisible(false);
		actionTraceUp.setEnabled(false);
		actionCopyFrom.setVisible(false);
		actionCopyFrom.setEnabled(false);
		this.actionCreateTo.setVisible(false);
		this.actionCreateTo.setEnabled(false);
		this.txtProject.setEditable(false);
		this.txtVerNumber.setEditable(false);
		this.txtNumber.setRequired(true);
		this.spYear.setRequired(true);
		this.spMonth.setRequired(true);
		this.prmtAuditor.setEnabled(false);
		this.dpAuditDate.setEnabled(false);
		this.actionQuery.setEnabled(true);
		
	}
	
	protected void setUnionAll(){
//		if(true)return;
		KDTable table=getDetailTable();
//		if(table.getRowCount()>0){
//			_setUnionSubRow(table.getRow(0),getSumColumns(),getUnSettledSumColumns(),getSettledSumColumns());
//		}
		List sumColumnsList = getSumColumns();
		List unSettledSumColumnsList = getUnSettledSumColumns();
		List settledSumColumnsList = getSettledSumColumns();
		for(int i=0;i<table.getRowCount();i++){
			_setUnionSubRow(table.getRow(i), sumColumnsList, unSettledSumColumnsList, settledSumColumnsList);
		}
	}
	
	protected void afterSetUnionAll(){
    	List columns=new ArrayList();
    	columns.add("splitAmt");
    	boolean isShowCost=isShowCost();
    	if(isShowDynAimCost()){
    		columns.add("aimCost");
    		columns.add("dynCost");
    	}


    	if(isShowCost){
        	Set costColumns=getCostColumns();
    		columns.addAll(costColumns);//进行成本汇总
    	}
    	int start=getStartMonth();
    	int end=getEndMonth();
    	for(int i=start;i<=end;i++){
    		columns.add(getMonthPayKey(i));
    	}
    	if(end-start>3){
    		columns.add("yearPay");
    		columns.add("waitPay");
    		columns.add("lstAllPaid");
    	}else{
    		columns.add("allPay");
    	}
    	if(isShowTotal()){
    		String [] arrays=new String[columns.size()];
    		columns.toArray(arrays);
    		try{
    			AimCostClientHelper.setTotalCostRow(getDetailTable(), arrays);
    		}catch(Exception e){
    			handUIException(e);
    		}
    	}
	}

	/**
	 * 递归汇总
	 * @param row
	 */
	private void _setUnionSubRow(IRow row,List sumCols,List unSettledList,List settledList) {
		KDTable table=getDetailTable();
		Object obj=row.getUserObject();
		int level=row.getTreeLevel();
		if(obj instanceof FDCBudgetAcctEntryInfo){
			//明细行
			return;
		}
		if(isUnSettlecContractRow(row)){
			//待签订合同行
			List subRows=new ArrayList();
			for(int i=row.getRowIndex()+1;i<table.getRowCount();i++){
				IRow tmpRow=table.getRow(i);
				if(tmpRow.getTreeLevel()==level+1){
					subRows.add(tmpRow);
				}
				if(tmpRow.getTreeLevel()<=level){
					break;
				}
			}
			sumRow(row, subRows, unSettledList);
			return;
		}
		if(isSettlecContractRow(row)){
			//已签订合同行或者明细科目行
			List subRows=new ArrayList();
			for(int i=row.getRowIndex()+1;i<table.getRowCount();i++){
				IRow tmpRow=table.getRow(i);
				if(tmpRow.getTreeLevel()==level+1){
					subRows.add(tmpRow);
				}
				if(tmpRow.getTreeLevel()<=level){
					break;
				}
			}
			sumRow(row, subRows, settledList);
			return;
		}
		//明细科目行
		if(row.getUserObject() instanceof CostAccountInfo){
			List subRows=new ArrayList();
			for(int i=row.getRowIndex()+1;i<table.getRowCount();i++){
				IRow tmpRow=table.getRow(i);
				if(tmpRow.getTreeLevel()==level+1){
					_setUnionSubRow(tmpRow,sumCols,unSettledList,settledList);
					subRows.add(tmpRow);
				}
				if(tmpRow.getTreeLevel()<=level){
					break;
				}
			}
			sumRow(row, subRows, settledList);
			return;
		}
		
		//非成本科目行
		List subRows=new ArrayList();
		for(int i=row.getRowIndex()+1;i<table.getRowCount();i++){
			IRow tmpRow=table.getRow(i);
			if(tmpRow.getTreeLevel()==level+1){
				_setUnionSubRow(tmpRow,sumCols,unSettledList,settledList);
				subRows.add(tmpRow);
			}
			if(tmpRow.getTreeLevel()<=level){
				break;
			}
		}
		sumRow(row, subRows, sumCols);
	
	}
	
	/**
	 * 将subRows按cols列汇总到row
	 * @param row
	 * @param subRows
	 * @param cols
	 */
	private void sumRow(IRow row,List subRows,List cols){
		if(cols.size()==0){
			return;
		}
		for(Iterator iter=cols.iterator();iter.hasNext();){
			//先清空再汇总
			row.getCell((String)iter.next()).setValue(null);
		}

		for(Iterator iter=subRows.iterator();iter.hasNext();){
			IRow tmpRow=(IRow)iter.next();
			for(Iterator iter2=cols.iterator();iter2.hasNext();){
				String key=(String)iter2.next();
				row.getCell(key).setValue(FDCNumberHelper.add(row.getCell(key).getValue(), tmpRow.getCell(key).getValue()));
			}
		}
	
	}

	public void actionShowBlankRow_actionPerformed(ActionEvent e) throws Exception {
		handleBlankRow(e);
	}
	
	public void actionHideBlankRow_actionPerformed(ActionEvent e) throws Exception {
		handleBlankRow(e);
	}
	
	private void handleBlankRow(ActionEvent e){
		ItemAction act = getActionFromActionEvent(e);
		boolean isHided=false;
		if(act.equals(this.actionShowBlankRow)){
			isHided=false;
		}else{
			isHided=true;
		}
		for(int i=0;i<getDetailTable().getRowCount();i++){
			IRow row=getDetailTable().getRow(i);
			if(row.getUserObject() instanceof FDCBudgetAcctEntryInfo){
				if(isEmptyRow(row)){
					row.getStyleAttributes().setHided(isHided);
				}
			}
			if(isSettlecContractRow(row)){
				
			}
			if(isUnSettlecContractRow(row)){
				
			}
		}
	}
	
	public void setOprtState(String oprtType) {
		super.setOprtState(oprtType);
		if (STATUS_ADDNEW.equals(this.oprtState)) {
        } else if (STATUS_EDIT.equals(this.oprtState)) {
        	/*if(this.editData!=null&&this.editData.getState()==FDCBillStateEnum.SUBMITTED){
        		actionSave.setEnabled(false);
        		actionSubmit.setEnabled(true);
        	}else{
        		actionSave.setEnabled(true);
        		actionSubmit.setEnabled(true);
        	}
        	setEditable();*/
        } else if (STATUS_VIEW.equals(this.oprtState)) {
        } else if (STATUS_FINDVIEW.equals(this.oprtState)) {
        }
	}
	
	protected void verifyInputForSave() throws Exception {
		verifyNotEmpty();
		verifyContractRows();
		super.verifyInputForSave();
	}
	protected void verifyInputForSubmint() throws Exception {
		verifyNotEmpty();
		verifyContractRows();
		super.verifyInputForSubmint();
	}
	
	/**
	 * 
	 * 描述：校验待签订行合同名称是否为空，同一科目是否存在相同合同名称;<p>
	 * 并且合同行（包括待签定合同）至少要有一列成本或付款
	 * 
	 */
	private void verifyContractRows(){
		
		KDTable table=getDetailTable();
		int colIndex = getDetailTable().getColumn("conName").getColumnIndex();
		
		for(int i=0;i<getDetailTable().getRowCount();i++){
			IRow row = table.getRow(i);
			Object obj=row.getUserObject();
			int level=row.getTreeLevel();
			if(obj instanceof FDCBudgetAcctEntryInfo){
				//明细行
				continue;
			}
			
			if(isUnSettlecContractRow(row)){
				//待签订合同行
				Set set = new HashSet();
				for(int j=row.getRowIndex()+1;j<table.getRowCount();j++){
					IRow tmpRow=table.getRow(j);
					String conName = null;
					if(tmpRow.getTreeLevel()==level+1){
	
						Object objConName = tmpRow.getCell("conName").getValue();
						if(objConName != null && objConName.toString().trim().length()>0){
							conName = objConName.toString().trim();
						}
						
						if(conName == null){
							getDetailTable().getEditManager().editCellAt(j,colIndex);
							MsgBox.showWarning(this,"第 "+(j+1)+" 行待签订合同名称不能为空！");
							SysUtil.abort();
						}
					
						if(conName != null && set.contains(conName)){
							getDetailTable().getEditManager().editCellAt(j,colIndex);
							MsgBox.showWarning(this, "第 "+(j+1)+" 行存在重复的合同名称！");
							SysUtil.abort();
						}else if(conName != null){
							set.add(conName);
						}
					}
					
					if(tmpRow.getTreeLevel()<=level){
						break;
					}
				}
			}
		}
	}
	
	
	/**
	 * 
	 * 描述：校验成本与付款列必须有一行有数据
	 * 
	 * @param tmpRow
	 * @param i
	 * 
	 */
	private void verifyNotEmpty(){
		/*
		 * 必须一个成本,付款,检验第一行是否有数据即可  by sxhong 2008-10-17 13:33:33
		 * 存在多个一级科目，每个一级科目都需校验 by pengwei_hou 2008-12-31
		 */
		if(getDetailTable().getRowCount()<=0){
			return;
		}
		boolean hasInput=false;
		for(int h=0;h<getDetailTable().getRowCount();h++){
			IRow topRow=getDetailTable().getRow(h);
			if(topRow.getTreeLevel()!=0){
				continue;
			}
			for(int i=0;i<getDetailTable().getColumnCount();i++){
				if(isMonthColumn(i)){
					if(FDCHelper.toBigDecimal(topRow.getCell(i).getValue()).signum()>0){
						hasInput=true;
						break;
					}
				}
			}
			
		}
		if(!hasInput){
			FDCMsgBox.showError(this, "数据不完整，请至少录入一栏成本或付款数据");
			SysUtil.abort();
		}

	}
	
	
	protected OrgType getMainBizOrgType() {
		OrgType mainBizOrgType = super.getMainBizOrgType();
//		if(mainBizOrgType==null){
//			mainBizOrgType=OrgType.CostCenter;
//		}
		return mainBizOrgType;
	}
	
	protected FDCBudgetAcctInfo getFDCBudgetAcctInfo(){
		
		return this.editData;
	}
	
	/**
	 * 描述：责任部门过滤界面
	 * @throws Exception 
	 */
    protected void showFDCBudgetAcctDeptFilterUI() throws Exception{
    	if(deptFilterUI == null){
    		deptFilterUI = new FDCBudgetAcctDeptFilterUI();
    	}
    	AdminOrgUnitInfo adminOrgUnitInfo = deptFilterUI.showUI();
    	if(adminOrgUnitInfo != null && adminOrgUnitInfo.getId() != null){
    		getUIContext().put("deptmentId", adminOrgUnitInfo.getId().toString());
    	}else{
    		getUIContext().put("deptmentId", null);
    	}
    	fillTable();
    }
    
    public void actionQuery_actionPerformed(ActionEvent e) throws Exception {
    	showFDCBudgetAcctDeptFilterUI();
    	this.btnShowBlankRow.doClick();
    	super.actionQuery_actionPerformed(e);
    }
    
	/**
	 * 科目汇总列
	 * @return
	 */
	protected List getSumColumns(){
		List list=getSettledSumColumns();
		list.add("aimCost");
		list.add("dynCost");
		return list;
	}
	
	/**
	 * 待签订合同汇总列
	 * @return
	 */
	protected List getUnSettledSumColumns(){
		KDTable table=getDetailTable();
		List list=new ArrayList();
		int splitAmtIdx=table.getColumnIndex("splitAmt");
		int descIdx=table.getColumnIndex("desc");
		for(int i=splitAmtIdx+1;i<descIdx;i++){
			if(isMonthColumn(i)){
				list.add(table.getColumnKey(i));
			}
		}
		list.add("splitAmt");
		return list;
	}
	
	/**
	 * 已签订合同汇总列
	 * @return
	 */
	protected List getSettledSumColumns(){
		KDTable table=getDetailTable();
		List list=new ArrayList();
		int splitAmtIdx=table.getColumnIndex("splitAmt");
		int descIdx=table.getColumnIndex("desc");
		for(int i=splitAmtIdx+1;i<descIdx;i++){
			if(isMonthColumn(i)){
				list.add(table.getColumnKey(i));
			}
		}
		list.add("splitAmt");
		return list;
	}
	
	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {
//		if ((e.getButton() == KDTMouseEvent.BUTTON1) && (e.getClickCount() == 2)
//				&& (e.getType() == KDTStyleConstants.HEAD_ROW)){
//			//不进行表头排序
//			return;
//		}
			
	}
	
	protected void initListener() {
	}
	
	public void onShow() throws Exception {
		super.onShow();
		//控制界面状态
        if (STATUS_ADDNEW.equals(this.oprtState)) {
        } else if (STATUS_EDIT.equals(this.oprtState)) {
        } else if (STATUS_VIEW.equals(this.oprtState)) {
        	actionAddLine.setEnabled(false);
        	actionRemoveLine.setEnabled(false);
        	actionInsertLine.setEnabled(false);
//        	actionAudit.setVisible(true);
//        	actionAudit.setEnabled(true);
//        	actionUnAudit.setVisible(true);
//        	actionUnAudit.setEnabled(true);
        } else if (STATUS_FINDVIEW.equals(this.oprtState)) {
        }
        actionEdit.setVisible(false);
        actionEdit.setEnabled(false);
        actionCreateFrom.setVisible(false);
        actionCreateFrom.setEnabled(false);
        if(this.editData!=null&&this.editData.getState()==FDCBillStateEnum.SUBMITTED){
    		actionSave.setEnabled(false);
    	}
        if(isRecension()){
			this.spYear.setEnabled(false);
			this.spMonth.setEnabled(false);
        }
        menuSubmitOption.setVisible(false);
        if (!STATUS_ADDNEW.equals(getOprtState())) {
        	this.btnHideBlankRow.doClick();
        }
//        tHelper = new TableCoreuiPreferenceHelper(this);
        //暂不提供，以后帮套打
//      actionPrint.setEnabled(true);
//		actionPrint.setVisible(true);
//      actionPrintPreview.setEnabled(true);
//		actionPrintPreview.setVisible(true);
		setModify(false);
	}
	
	public boolean isModify() {
		if (STATUS_VIEW.equals(getOprtState())) {
			return false;
		}
        // 工作流或BOTP查看状态不判断是否修改
        if (STATUS_FINDVIEW.equals(getOprtState()))
        {
            return false;
        }
		return isModify;
	}
	
	private boolean isModify=false;
	
	protected void setModify(boolean isModify){
		this.isModify=isModify;
	}
	
	/**
	 * 成本列,用于对成本列的操作
	 * @return
	 */
	protected Set getCostColumns(){
		Set set=new HashSet();
		int start=getStartMonth();
		int end=getEndMonth();
		for(int i=start;i<=end;i++){
			set.add(getMonthCostKey(i));
		}
		return set;
	}
	
	protected KDTable getTableForCommon() {
		return getDetailTable();
	}
	
	protected abstract int getStartMonth();
	
	protected abstract int getEndMonth();
}
