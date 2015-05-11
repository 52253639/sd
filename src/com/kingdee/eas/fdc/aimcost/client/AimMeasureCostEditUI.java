/**
 * output package name
 */
package com.kingdee.eas.fdc.aimcost.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.EventListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.common.variant.Variant;
import com.kingdee.bos.ctrl.excel.io.kds.KDSBookToBook;
import com.kingdee.bos.ctrl.excel.model.struct.Sheet;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.extendcontrols.KDCommonPromptDialog;
import com.kingdee.bos.ctrl.kdf.export.ExportManager;
import com.kingdee.bos.ctrl.kdf.export.KDTables2KDSBook;
import com.kingdee.bos.ctrl.kdf.export.KDTables2KDSBookVO;
import com.kingdee.bos.ctrl.kdf.kds.KDSBook;
import com.kingdee.bos.ctrl.kdf.read.POIXlsReader;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTMenuManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDContainer;
import com.kingdee.bos.ctrl.swing.KDFileChooser;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDPanel;
import com.kingdee.bos.ctrl.swing.KDPromptBox;
import com.kingdee.bos.ctrl.swing.KDScrollPane;
import com.kingdee.bos.ctrl.swing.KDSplitPane;
import com.kingdee.bos.ctrl.swing.KDTabbedPane;
import com.kingdee.bos.ctrl.swing.KDTextArea;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.ctrl.swing.event.SelectorEvent;
import com.kingdee.bos.ctrl.swing.event.SelectorListener;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.ormapping.impl.ObjectReader;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.function.FunctionObjectInfo;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.ui.face.WinStyle;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.workflow.ProcessInstInfo;
import com.kingdee.bos.workflow.service.ormrpc.EnactmentServiceFactory;
import com.kingdee.bos.workflow.service.ormrpc.IEnactmentService;
import com.kingdee.eas.base.attachment.AttachmentFactory;
import com.kingdee.eas.base.attachment.AttachmentFtpFacadeFactory;
import com.kingdee.eas.base.attachment.AttachmentInfo;
import com.kingdee.eas.base.attachment.BoAttchAssoCollection;
import com.kingdee.eas.base.attachment.BoAttchAssoFactory;
import com.kingdee.eas.base.attachment.BoAttchAssoInfo;
import com.kingdee.eas.base.attachment.IAttachment;
import com.kingdee.eas.base.attachment.util.FileGetter;
import com.kingdee.eas.base.param.ParamControlFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.base.permission.client.longtime.ILongTimeTask;
import com.kingdee.eas.base.permission.client.util.PermissionHelper;
import com.kingdee.eas.base.uiframe.client.UIFactoryHelper;
import com.kingdee.eas.basedata.assistant.MeasureUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.aimcost.ConstructPlanIndexEntryInfo;
import com.kingdee.eas.fdc.aimcost.CostIndexConfigInfo;
import com.kingdee.eas.fdc.aimcost.CostIndexEntryInfo;
import com.kingdee.eas.fdc.aimcost.CustomPlanIndexEntryCollection;
import com.kingdee.eas.fdc.aimcost.CustomPlanIndexEntryInfo;
import com.kingdee.eas.fdc.aimcost.MeasureCostCompareInfo;
import com.kingdee.eas.fdc.aimcost.MeasureCostFactory;
import com.kingdee.eas.fdc.aimcost.MeasureCostInfo;
import com.kingdee.eas.fdc.aimcost.MeasureEntryCollection;
import com.kingdee.eas.fdc.aimcost.MeasureEntryFactory;
import com.kingdee.eas.fdc.aimcost.MeasureEntryInfo;
import com.kingdee.eas.fdc.aimcost.MeasureIncomeEntryCollection;
import com.kingdee.eas.fdc.aimcost.MeasureIncomeEntryInfo;
import com.kingdee.eas.fdc.aimcost.MeasureIncomeFactory;
import com.kingdee.eas.fdc.aimcost.MeasureIncomeInfo;
import com.kingdee.eas.fdc.aimcost.PlanIndexCollection;
import com.kingdee.eas.fdc.aimcost.PlanIndexEntryCollection;
import com.kingdee.eas.fdc.aimcost.PlanIndexEntryFactory;
import com.kingdee.eas.fdc.aimcost.PlanIndexEntryInfo;
import com.kingdee.eas.fdc.aimcost.PlanIndexFactory;
import com.kingdee.eas.fdc.aimcost.PlanIndexInfo;
import com.kingdee.eas.fdc.aimcost.TemplateMeasureCostCollection;
import com.kingdee.eas.fdc.aimcost.VersionTypeEnum;
import com.kingdee.eas.fdc.basecrm.CRMHelper;
import com.kingdee.eas.fdc.basedata.AcctAccreditHelper;
import com.kingdee.eas.fdc.basedata.ApportionTypeEnum;
import com.kingdee.eas.fdc.basedata.ApportionTypeInfo;
import com.kingdee.eas.fdc.basedata.CostAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CostAccountTypeEnum;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCCommonServerHelper;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCNumberHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.IProjectIndexData;
import com.kingdee.eas.fdc.basedata.IncomeAccountFactory;
import com.kingdee.eas.fdc.basedata.IncomeAccountInfo;
import com.kingdee.eas.fdc.basedata.MeasureIndexCollection;
import com.kingdee.eas.fdc.basedata.MeasureIndexFactory;
import com.kingdee.eas.fdc.basedata.MeasureStageInfo;
import com.kingdee.eas.fdc.basedata.ProductTypeCollection;
import com.kingdee.eas.fdc.basedata.ProductTypeFactory;
import com.kingdee.eas.fdc.basedata.ProductTypeInfo;
import com.kingdee.eas.fdc.basedata.ProjectIndexDataCollection;
import com.kingdee.eas.fdc.basedata.ProjectIndexDataEntryCollection;
import com.kingdee.eas.fdc.basedata.ProjectIndexDataEntryInfo;
import com.kingdee.eas.fdc.basedata.ProjectIndexDataFactory;
import com.kingdee.eas.fdc.basedata.ProjectIndexDataInfo;
import com.kingdee.eas.fdc.basedata.ProjectStageEnum;
import com.kingdee.eas.fdc.basedata.ProjectTypeInfo;
import com.kingdee.eas.fdc.basedata.TimeTools;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.basedata.client.FDCUIWeightWorker;
import com.kingdee.eas.fdc.basedata.client.IFDCWork;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContracCostInfo;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo;
import com.kingdee.eas.fdc.invite.InviteTypeInfo;
import com.kingdee.eas.fdc.migrate.BOSUuidHelper;
import com.kingdee.eas.fdc.sellhouse.client.SHEHelper;
import com.kingdee.eas.fi.gl.GlUtils;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.client.CoreUI;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.ma.budget.client.LongTimeDialog;
import com.kingdee.eas.tools.datatask.DatataskMode;
import com.kingdee.eas.tools.datatask.DatataskParameter;
import com.kingdee.eas.tools.datatask.client.DatataskCaller;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.ExceptionHandler;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * 注:用T_AIM_Measureentry上的simpleName存储指标,number存储汇总表的六类公摊分摊方式
 * output class name
 */
public class AimMeasureCostEditUI extends AbstractAimMeasureCostEditUI {
	
	/**
	 * 系数名称
	 * 没有办法只能用静态来传值
	 */
	public static String indexName = null;

	private List tables = null;

	private CurProjectInfo project = null;

	private Map measureCostMap = new HashMap();

	private TreeModel costAcctTree = null;

	public Map apportionMap = new HashMap();
	
	/** 规划指标表 */
	private PlanIndexTable planIndexTable = null;

	/** 测算汇总表 */
	private MeasureCollectTable measureCollectTable = null;
	
	private boolean isFirstLoad=true;
	
	CoreUI MeasureIncomeEditUI=null;
	List lockIds = new ArrayList();
	List lockId2s = new ArrayList();
	boolean hasMutex = true;
	
	private MeasureIncomeInfo miInfo=null;
	/**
	 * output class constructor
	 */
	/**
	 * 产品类型
	 */
	private Map productTypeMap = new HashMap(); 
	public AimMeasureCostEditUI() throws Exception {
		super();
	}

	public boolean destroyWindow() {
		boolean destroyWindow = super.destroyWindow();
		if(destroyWindow){
			//释放
			if ("RELEASEALL".equals(getOprtState())&&hasMutex) {
				try {
					FDCClientUtils.releaseDataObjectLock(this, lockIds);
					if(lockId2s.size()>0)
					FDCClientUtils.releaseDataObjectLock(MeasureIncomeEditUI, lockId2s);
					
				} catch (Throwable e1) {
					this.handUIException(e1);
				}
			}
		}
//			if(this.editData.getId()!=null && isEdit){//给成本关键指标做快照
//				ProjectIndexDataCollection coll = this.aimIdexUI.getProjectIndexDataCollection();
//				if(coll!=null){
//					coll = doSpotCutData(coll,this.editData.getId());
//				}
//			}
			//释放资源
			if(true)return true;
			this.removeAll();
			costAcctTree=null;
			tables=null;
			project=null;
			measureCollectTable.clear();
			measureCollectTable=null;
			planIndexTable.clear();
			planIndexTable=null;
			apportionMap=null;
			measureCostMap=null;
			tHelper=null;
			this.plTables=null;
			FDCClientHelper.clearMenuKeyboardHelper();
		
		return destroyWindow;
	}
	
	/**
	 * 为项目指标做快照
	 * @param coll
	 * @param uuid
	 * @return
	 */
	private ProjectIndexDataCollection doSpotCutData(ProjectIndexDataCollection coll, BOSUuid uuid) {
		ProjectIndexDataCollection collection = coll;
		IProjectIndexData  projectIndexFacad = null;
		try {
			projectIndexFacad = ProjectIndexDataFactory.getRemoteInstance();
			FilterInfo filter = new FilterInfo();
			filter.appendFilterItem("aimMeasureID",this.getEditData().getId().toString());
			projectIndexFacad.delete(filter);
			for(int i = 0 ; i < coll.size();i++){
				ProjectIndexDataInfo info = collection.get(i);
				info.setId(BOSUuid.create(info.getBOSType()));
				info.setAimMeasureID(uuid);
				ProjectIndexDataEntryCollection projectColl = info.getEntries();
				for(int j = 0 ; j < projectColl.size() ; j ++){
					ProjectIndexDataEntryInfo entryInfo =  projectColl.get(j);
					entryInfo.setId(BOSUuid.create(entryInfo.getBOSType()));
					entryInfo.setParent(info);
				}
				projectIndexFacad.addnew(info);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return collection;
	}

	public void storeFields() {
		super.storeFields();
		MeasureCostInfo cost = (MeasureCostInfo) this.editData;
		cost.setMeasureDescription(measureDes!=null?measureDes.getText():null);
		Object objStage = comboMeasureStage.getSelectedItem();
		if(objStage instanceof MeasureStageInfo){
			cost.setMeasureStage((MeasureStageInfo)objStage);
		}else{
			cost.setMeasureStage(null);
		}
		cost.setVersionType((VersionTypeEnum)this.comboVersionType.getSelectedItem());
		cost.setVersionName(this.txtVersionName.getText());
		cost.setVersionNumber(this.txtVersionNumber.getText());
		Object objPrj = prmtProject.getValue();
		if(cost.getProject()==null&& objPrj instanceof CurProjectInfo){
			cost.setProject((CurProjectInfo)objPrj);
		}
		
		Object objPrjType = prmtProjectType.getValue();
		if(objPrjType instanceof ProjectTypeInfo){
			cost.setProjectType((ProjectTypeInfo)objPrjType);
		}else{
			cost.setProjectType(null);
		}
//		cost.getCostEntry().clear();
		try {
			handleAimCostAccredit(cost);
		} catch (BOSException e) {
			handUIException(e);
		}
		Map splitTypeMap=measureCollectTable.getSplitTypes();
		for (int i = 3; i < tables.size(); i++) {
			KDTable table = (KDTable) tables.get(i);
			ProductTypeInfo product = (ProductTypeInfo) table.getHeadRow(0)
					.getUserObject();
			for (int j = 0; j < table.getRowCount(); j++) {
				IRow row = table.getRow(j);
				if (row.getUserObject() instanceof MeasureEntryInfo) {
//					if(isDetailAcctRow(row)&&!isDetailAcctHasInput(row)){
						boolean isEmpty=true;
						for(int k=3;k<table.getColumnCount();k++){
							if(!FDCHelper.isEmpty(row.getCell(k).getValue())){
								isEmpty=false;
								break;
							}
						}
						if(isEmpty){
							continue;
						}
//					}
					MeasureEntryInfo entry = (MeasureEntryInfo) row
							.getUserObject();
					entry.setEntryName((String) row.getCell("acctName")
							.getValue());
					entry.setProduct(product);
					/*
					 * entry.setApportionType((ApportionTypeInfo) row.getCell(
					 * "indexName").getValue());
					 */
					Object obj = row.getCell("indexName").getValue();
					if (obj instanceof Item) {
						Item item = (Item) obj;
						entry.setSimpleName(item.key);
						entry.setIndexName(item.toString());
						entry.setIndexValue(FDCHelper.toBigDecimal(row.getCell("index").getValue()));
						// KDComboBox
						// box=(KDComboBox)row.getCell("indexName").getEditor().getComponent();
						// box.getSelectedIndex();
					}
					entry.setCoefficientName((String) row.getCell(
							"coefficientName").getValue());
					entry.setCoefficient((BigDecimal) row
							.getCell("coefficient").getValue());
					final Object value = row.getCell("unit").getValue();
					if(value instanceof IObjectValue){
						entry.setUnit((MeasureUnitInfo)value);
					}else if(value!=null){
						entry.setName(value.toString());
					}
					entry.setWorkload((BigDecimal) row.getCell("workload")
							.getValue());
					entry
							.setPrice((BigDecimal) row.getCell("price")
									.getValue());
					entry.setCostAmount((BigDecimal) row.getCell("sumPrice")
							.getValue());
					entry.setProgram((String)row.getCell("program").getValue());
					entry.setDesc((String)row.getCell("desc").getValue());
					entry.setChangeReason((String)row.getCell("changeReason").getValue());
					entry.setDescription((String) row.getCell("description").getValue());
					if(entry.getCostAccount().getType()==CostAccountTypeEnum.SIX){
						Object splitType=splitTypeMap.get(entry.getCostAccount().getId().toString());
						if(splitType!=null){
							entry.setNumber(splitType.toString());
						}
					}
					entry.setAdjustCoefficient((BigDecimal)row.getCell("adjustCoefficient").getValue());
					entry.setAdjustAmt((BigDecimal)row.getCell("adjustAmt").getValue());
					entry.setAmount((BigDecimal)row.getCell("amount").getValue());
					cost.getCostEntry().add(entry);
				}
			}
		}
		//当本版本是最终版本时1.反审批修改,界面保持打开2.将另一版本审批为最终版本,3,再保存1.的数据后存在两个最终版本
		if(cost!=null&&cost.getId()!=null){
			cost.setIsLastVersion(isLastVersion(cost.getId().toString()));
		}
		
		if(isHasCompareEntry){
			Object[] key = compareTables.keySet().toArray(); 
			for (int k = 0; k < key.length; k++) {
				String productKey=key[k].toString();
				KDTable table=(KDTable) compareTables.get(productKey);
				for(int i=0;i<table.getRowCount();i++){
					MeasureCostCompareInfo entry=(MeasureCostCompareInfo) table.getRow(i).getUserObject();
					entry.setReason((String)table.getRow(i).getCell("reason").getValue());
				}
			}
		}
    		
	}

	protected void initWorkButton() {
		super.initWorkButton();
		
		this.actionFirst.setVisible(false);
		this.actionPre.setVisible(false);
		this.actionNext.setVisible(false);
		this.actionLast.setVisible(false);
		this.actionCancel.setVisible(false);
		this.actionCancelCancel.setVisible(false);
		this.actionRemove.setVisible(false);
		actionAddRow.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_addline"));
		actionDeleteRow.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_deleteline"));
		this.btnImportApportion.setIcon(EASResource.getIcon("imgTbtn_input"));
		actionImportTemplate.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_input"));
		menuEdit.setEnabled(true);
		menuEdit.setVisible(true);
		actionExportAllToExcel.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_output"));
		actionExportAllToExcel.setVisible(true);
		actionExportAllToExcel.setEnabled(true);

		menuItemExportAll.setMnemonic('E');
		menuItemExportAll.setText(menuItemExportAll.getText()+"(E)");
		menuItemExportAll.setAccelerator(KeyStroke.getKeyStroke("ctrl shift E"));
		
		menuItemImportTemplate.setMnemonic('T');
		menuItemImportTemplate.setText(menuItemImportTemplate.getText()+"(T)");
		menuItemImportTemplate.setAccelerator(KeyStroke.getKeyStroke("ctrl shift T"));
		
		menuItemAddRow.setMnemonic('A');
		menuItemAddRow.setText(menuItemAddRow.getText()+"(A)");
		menuItemAddRow.setAccelerator(KeyStroke.getKeyStroke("alt A"));
		
		menuItemDeleteRow.setMnemonic('D');
		menuItemDeleteRow.setText(menuItemDeleteRow.getText()+"(D)");
		menuItemDeleteRow.setAccelerator(KeyStroke.getKeyStroke("alt D"));
		chkMenuItemSubmitAndAddNew.setEnabled(false);
		chkMenuItemSubmitAndAddNew.setVisible(false);
		//by Cassiel_peng 2009-8-19
		this.btnSave.setIcon(EASResource.getIcon("imgTbtn_save"));
        this.btnSubmit.setIcon(EASResource.getIcon("imgTbtn_submit"));
        this.menuItemSave.setIcon(EASResource.getIcon("imgTbtn_save"));
        this.menuItemSubmit.setIcon(EASResource.getIcon("imgTbtn_submit"));
        this.menuItemSave.setVisible(true);
        //工程项目不让选择，必须新增的时候带过来，否者请重新生成
        this.prmtProject.setEnabled(false);
	}

	protected IObjectValue createNewData() {
		MeasureCostInfo cost = new MeasureCostInfo();
		String orgId=(String)getUIContext().get("orgId");
		String prjId=(String)getUIContext().get("projectId");
		Boolean isAimMeasure=(Boolean)getUIContext().get("isAimMeasure");
		Boolean isEditVersion=(Boolean)getUIContext().get("isEditVersion");
		cost.setIsAimMeasure(isAimMeasure.booleanValue());
		if(getUIContext().get("MeasureEditData") instanceof MeasureCostInfo){
			MeasureCostInfo editData1=(MeasureCostInfo)getUIContext().get("MeasureEditData");
			cost.putAll(editData1);
			getUIContext().remove("MeasureEditData");
			return cost;
		}
		
		MeasureStageInfo lastStageInfo = null;
		try {
				SelectorItemCollection selector=new SelectorItemCollection();
				selector.add("name");
				selector.add("number");
				selector.add("longNumber");
				selector.add("projectType.*");
				selector.add("curProjProductEntries.isAccObj");
				if(prjId!=null){
					project = CurProjectFactory.getRemoteInstance().getCurProjectInfo(
							new ObjectUuidPK(BOSUuid.read(prjId)),selector);
					cost.setProject(this.project);
					cost.setProjectType(project.getProjectType());
					
					lastStageInfo = AimCostClientHelper.getLastMeasureStageForXuHui(project,isAimMeasure.booleanValue());
				}
				
				/*MeasureCostVersionHandler version = new MeasureCostVersionHandler(
						this.project.getId().toString(), true);*/
	//			MeasureCostVersionHandler version = new MeasureCostVersionHandler(
	//					orgId,prjId, isAimMeasure.booleanValue());
				MeasureCostVersionHandler version = new MeasureCostVersionHandler(
						orgId,prjId, isAimMeasure.booleanValue(),lastStageInfo);
				
				cost.setVersionNumber(MeasureCostVersionHandler
						.getNextVersion(version.getLastVersion()));
		} catch (BOSException e) {
			handUIException(e);
		} catch (SQLException e) {
			handUIException(e);
		} catch (EASBizException e) {
			handUIException(e);
		}
		
		FullOrgUnitInfo org = new FullOrgUnitInfo();
		org.setId(BOSUuid.read(orgId));
		cost.setOrgUnit(org);
		if(lastStageInfo==null){
			//系统默认三个阶段，默认最后一阶段 by hpw 2010-9-1
			lastStageInfo=(MeasureStageInfo)comboMeasureStage.getItemAt(0);
		} else{
			//remove这个阶段之前的阶段
			removeitemListener(comboMeasureStage);
			AimCostClientHelper.removeBeforeStage(comboMeasureStage,lastStageInfo);
			addItemlistener(comboMeasureStage);
			cost.setMeasureStage(lastStageInfo);
		}
		//版本类型
		cost.setVersionType(VersionTypeEnum.CheckVersion);
		return cost;
	}
	private void removeitemListener(KDComboBox comboMeasureStage) {
		itemListeners = comboMeasureStage.getItemListeners();
		for(int i = 0 ; i < itemListeners.length ; i++){
			comboMeasureStage.removeItemListener(itemListeners[i]);
		}
	}
	private void addItemlistener(KDComboBox comboMeasureStage){
		for(int i = 0 ; i < itemListeners.length ; i++){
			comboMeasureStage.addItemListener(itemListeners[i]);
		}
	}
	ItemListener[]  itemListeners= null;
	protected ICoreBase getBizInterface() throws Exception {
		return MeasureCostFactory.getRemoteInstance();
	}
	KDTextArea measureDes = null;
	KDTable tblAttachement= null;
	boolean isHasCompareEntry=false;
	Map compareTables = null;
	Map totalColor = null;
	KDTabbedPane tabCompareEntry=null;
//	KDPanel indexPanel = null;
//	AimIndexUI aimIdexUI  = null;
	private void addPanel() throws Exception {
		if(isFirstLoad) {
			isFirstLoad=false;
		}else{
			return;
		}
		ChangeListener[] changeListeners = plTables.getChangeListeners();
		for(int i=0;i<changeListeners.length;i++){
			plTables.removeChangeListener(changeListeners[i]);
		}
		this.plTables.removeAll();
		Object obj=getUIContext().get(UIContext.ID);
		if(editData.getId()!=null){
			obj=editData.getId();
		}
		
		tables = new ArrayList();
		//添加测算说明
		KDSplitPane splitPane=new KDSplitPane();
		KDScrollPane  panel = new KDScrollPane();
		measureDes = new KDTextArea();
		measureDes.setMaxLength(50000);
		panel.setViewportView(measureDes);
		measureDes.setText(((MeasureCostInfo)this.editData).getMeasureDescription());
		
		tblAttachement=new KDTable();
		tblAttachement.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
	            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
	                try {
	                    tblAttachement_tableClicked(e);
	                } catch (Exception exc) {
	                    handUIException(exc);
	                } finally {
	                }
	            }
	        });
		tblAttachement.checkParsed();
		tblAttachement.addHeadRow();
		tblAttachement.setEnabled(false);
		IColumn name=tblAttachement.addColumn();
		name.setKey("name");
		tblAttachement.getHeadRow(0).getCell("name").setValue("名称");
		name.setWidth(300);
		
		IColumn id=tblAttachement.addColumn();
		id.setKey("id");
		tblAttachement.getHeadRow(0).getCell("id").setValue("id");
		id.getStyleAttributes().setHided(true);
		
		this.fillAttachmnetTable();
		
		KDContainer contDes=new KDContainer();
		contDes.setLayout(new BorderLayout(0, 0)); 
		contDes.add(panel,BorderLayout.CENTER);
		
		KDContainer contAttach=new KDContainer();
		contAttach.getContentPane().setLayout(new BorderLayout(0, 0));        
		contAttach.getContentPane().add(tblAttachement, BorderLayout.CENTER);
		
		splitPane.add(contDes, "left");
		splitPane.add(contAttach,"right");
		this.plTables.add(splitPane, "测算说明&附件管理");
		splitPane.setDividerLocation(1000);
		
		KDWorkButton btnAttachment = new KDWorkButton();
		this.actionAttachment.putValue("SmallIcon", EASResource.getIcon("imgTbtn_affixmanage"));
		this.actionAttachment.setVisible(true);
		btnAttachment = (KDWorkButton) contAttach.add(this.actionAttachment);
		btnAttachment.setText("附件管理");
		btnAttachment.setSize(new Dimension(140, 19));
		
		tabCompareEntry=new KDTabbedPane();
		KDContainer contCompareEntry=new KDContainer();
		contCompareEntry.getContentPane().setLayout(new BorderLayout(0, 0)); 
		contCompareEntry.getContentPane().add(tabCompareEntry,BorderLayout.CENTER);
		
		Boolean isEditVersion=(Boolean)getUIContext().get("isEditVersion");
    	if(isEditVersion!=null){
    		if(isEditVersion.booleanValue()){
    			((MeasureCostInfo)this.editData).getCompareEntry().clear();
    			isHasCompareEntry=true;
    		}
		}else{
			if(((MeasureCostInfo)this.editData).getVersionType().equals(VersionTypeEnum.AdjustVersion)){
				isHasCompareEntry=true;
			}
		}
    	if(isHasCompareEntry){
    		compareTables=new HashMap();
    		loadCompareEntry((MeasureCostInfo)this.editData);
			this.plTables.add(contCompareEntry, "调整原因");
		}
    	up.setOpaque(true);
		up.setBackground(new Color(248,171,166));
		down.setOpaque(true);
		down.setBackground(new Color(163,207,98));
		
		this.contUp.setVisible(isHasCompareEntry);
		this.contDown.setVisible(isHasCompareEntry);
		this.up.setVisible(isHasCompareEntry);
		this.down.setVisible(isHasCompareEntry);
		
    	
		KDWorkButton btnCompare = new KDWorkButton();
		this.actionCompare.putValue("SmallIcon", EASResource.getIcon("imgTbtn_input"));
		btnCompare = (KDWorkButton) contCompareEntry.add(this.actionCompare);
		btnCompare.setText("提取");
		btnCompare.setSize(new Dimension(140, 19));
		
		//添加汇总表及规划指标表
		KDTable table =null;// new KDTable();
		measureCollectTable=new MeasureCollectTable(this);
		table=measureCollectTable.getTable();
		this.tables.add(table);
		FDCTableHelper.setColumnMoveable(table, true);
		FDCTableHelper.addTableMenu(table);
		this.plTables.add(table, "测算汇总表");
		planIndexTable=new PlanIndexTable(getInitPlanIndexInfo(),this);
		table=planIndexTable.getTable();
		this.tables.add(table);
		FDCTableHelper.setColumnMoveable(table, true);
		FDCTableHelper.addTableMenu(table);
		this.plTables.add(planIndexTable.getContentPanel(), "规划指标表");
		
		table = planIndexTable.getConstructTable();
		this.tables.add(table);
		
		KDContainer constructTable=new KDContainer();
		constructTable.getContentPane().setLayout(new BorderLayout(0, 0));        
		constructTable.getContentPane().add(table, BorderLayout.CENTER);
		
		KDWorkButton btnImportExcel=new KDWorkButton();
		this.actionImportConstructTable.putValue("SmallIcon", EASResource.getIcon("imgTbtn_input"));
		btnImportExcel = (KDWorkButton)constructTable.add(this.actionImportConstructTable);
		btnImportExcel.setText("导入");
		btnImportExcel.setSize(new Dimension(140, 19));
		
		KDWorkButton btnExportExcel=new KDWorkButton();
		this.actionExportConstructTable.putValue("SmallIcon", EASResource.getIcon("imgTbtn_output"));
		btnExportExcel = (KDWorkButton)constructTable.add(this.actionExportConstructTable);
		btnExportExcel.setText("导出");
		btnExportExcel.setSize(new Dimension(140, 19));
		
		this.plTables.add(constructTable,"建造标准");
		
		//成本关键指标表
//		indexPanel = new KDPanel();
//		indexPanel.setLayout(new BorderLayout());
//		UIContext uicontext = new UIContext();
//		uicontext.put("projectId",this.getUIContext().get("projectId"));
//		uicontext.put("stage", ProjectStageEnum.AIMCOST);
//		uicontext.put("isEdit",Boolean.valueOf(isEdit));
//		uicontext.put("parentID",(this.editData !=null&&this.editData.getId()!=null) ?this.editData.getId().toString():null);
//		aimIdexUI  = (AimIndexUI)UIFactoryHelper.initUIObject(AimIndexUI.class.getName(), uicontext,null, OprtState.VIEW);
//		indexPanel.add(aimIdexUI,BorderLayout.CENTER);
//		this.plTables.add(indexPanel,"成本关键指标表",4);
		PlanIndexInfo info = planIndexTable.getPlanIndexInfo();
		
		//公摊及产品
		table = new KDTable();
		table.setName("六类公摊及期间费");
		this.tables.add(table);
		FDCTableHelper.addTableMenu(table);
		FDCTableHelper.setColumnMoveable(table, true);
		this.initTable(table, CostAccountTypeEnum.SIX,null);
		

		BigDecimal amount=planIndexTable.getAllSellArea(info);
		info.put("allSellArea", amount);
		amount=planIndexTable.getAllBuildArea(info);
		info.put("allBuildArea", amount);
		
		table.getHeadRow(0).getCell(0).setUserObject(info);
		this.fillTable(table);
		setUnionData(table);
		this.plTables.add(table, "六类公摊及期间费");
		
		
		for(int i=0;i<info.getEntrys().size();i++){
			PlanIndexEntryInfo entry=info.getEntrys().get(i);
			if(entry.getProduct()!=null){
				table=addProductTypeTable(entry.getProduct());
				
//				if(table!=null) table.setUserObject(entry);
				
				
			}
		}
		//从界面取数
		measureCollectTable.refresh();
/*		AimProductTypeGetter getter = new AimProductTypeGetter(project.getId()
				.toString());
		Map prodcutMap = getter.getSortedProductMap();
		Set set = prodcutMap.keySet();
		for (Iterator pIter = set.iterator(); pIter.hasNext();) {
			ProductTypeInfo product = (ProductTypeInfo) prodcutMap.get(pIter
					.next());
			table = new KDTable();
			this.tables.add(table);
			this.initTable(table, CostAccountTypeEnum.MAIN,product.getId().toString());
			table.getHeadRow(0).setUserObject(product);
			this.fillTable(table);
			this.plTables.add(table, product.getName());
		}
		for (int i = 0; i < tables.size(); i++) {
			KDTable aTable = (KDTable) tables.get(i);
			this.setUnionData(aTable);
		}*/
		for(int i=0;i<changeListeners.length;i++){
			plTables.addChangeListener(changeListeners[i]);
		}
	}

	public KDTable addProductTypeTable(ProductTypeInfo product){
		boolean isadd=true; 
		for(int i=3;i<tables.size();i++){
			KDTable table=(KDTable) tables.get(i);
			if(table.getHeadRow(0).getUserObject() instanceof ProductTypeInfo){
				if(((ProductTypeInfo)table.getHeadRow(0).getUserObject()).getId().equals(product.getId())){
					isadd=false;
					break;
				}
			}
		}
		if(!isadd) return null;
		
		KDTable table = new KDTable();
		this.tables.add(table);
		FDCTableHelper.setColumnMoveable(table, true);
		this.initTable(table, CostAccountTypeEnum.MAIN,product.getId().toString());
		table.getHeadRow(0).getCell(0).setUserObject(planIndexTable.getPlanIndexEntryInfo(product.getId().toString()));
		table.getHeadRow(0).setUserObject(product);
		try {
			this.fillTable(table);
		} catch (Exception e) {
			handUIException(e);
		}
		table.setName(product.getName());
		this.plTables.add(table, product.getName());
		
		KDTable constrTable = planIndexTable.getConstructTable();
		IRow headRow = constrTable.getHeadRow(0);
		boolean isHasAdd = true;
//		if(constrTable.getColumnCount()>4){
			for(int i=4;i<constrTable.getColumnCount();i++){
				ProductTypeInfo type = (ProductTypeInfo)headRow.getCell(i).getUserObject();
				if(type.getId().toString().equals(product.getId().toString())){
					isHasAdd=false;
					break;
				}
//				IColumn column = constrTable.addColumn();
//				column.setWidth(120);
//				column.setKey(product.getId().toString());
//				headRow.getCell(product.getId().toString()).setUserObject(product);
//				headRow.getCell(product.getId().toString()).setValue(product.getName());
			}
//		}
		if(isHasAdd){
			IColumn column = constrTable.addColumn();
			column.setWidth(150);
			column.setKey(product.getId().toString());
			headRow.getCell(product.getId().toString()).setUserObject(product);
			headRow.getCell(product.getId().toString()).setValue(product.getName());
		}
		
		this.setUnionData(table);
		FDCTableHelper.addTableMenu(table);
		return table;
		
	}
	public void addConstructIndexTable(){
		KDTable table = planIndexTable.getConstructTable();
		this.tables.add(table);
	}
	
	public void deleteProductTypeTable(ProductTypeInfo product){
		for(int i=3;i<tables.size();i++){
			KDTable table=(KDTable) tables.get(i);
			if(table.getHeadRow(0).getUserObject() instanceof ProductTypeInfo){
				if(((ProductTypeInfo)table.getHeadRow(0).getUserObject()).getId().equals(product.getId())){
					tables.remove(i);
					//以指标必须保证顺序,现在顺序变化了 by hpw
//					plTables.remove(i);
					plTables.remove(table);
					
					disableTableMenus(table);
					break;
				}
			}
		}
		
		//add by david_yang R110411-539 2011.04.19
		for(int j=4;j<planIndexTable.getConstructTable().getColumnCount();j++){
			IRow headRow = planIndexTable.getConstructTable().getHeadRow(0);
			if(headRow.getCell(j).getUserObject() instanceof ProductTypeInfo){
				if(((ProductTypeInfo)headRow.getCell(j).getUserObject()).getId().equals(product.getId())){
					planIndexTable.getConstructTable().removeColumn(j);
					break;
				}
			}
		}
	}
	public void addTableChangeEnvent(final KDTable table) {
		table
				.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
					public void editStopped(
							com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
						try {
							table_editStopped(table, e);
						} catch (Exception exc) {
							handUIException(exc);
						}
					}
				});
	}

	public void initTable(KDTable table, CostAccountTypeEnum type,String productId) {
		table.getViewManager().setFreezeView(-1, 3);
		table.getSelectManager().setSelectMode(KDTSelectManager.CELL_SELECT);
		table.setActiveCellStatus(KDTStyleConstants.ACTIVE_CELL_EDIT);
		IColumn column = table.addColumn();
		column.setKey("id");
		column.getStyleAttributes().setHided(true);
		column = table.addColumn();
		column.setKey("acctNumber");
		column = table.addColumn();
		column.setKey("acctName");
		column = table.addColumn();
		column.setKey("indexName");
		column = table.addColumn();
		column.setKey("index");
		column = table.addColumn();
		column.setKey("coefficientName");
		column = table.addColumn();
		column.setKey("coefficient");
		column = table.addColumn();
		column.setKey("workload");
		column = table.addColumn();
		column.setKey("unit");
		column = table.addColumn();
		column.setKey("price");
		column = table.addColumn();
		column.setKey("sumPrice");
		column.getStyleAttributes().setHided(true);
		//start调整系数三列
		column = table.addColumn();
		column.setKey("adjustCoefficient");
		column.getStyleAttributes().setHided(true);
		column = table.addColumn();
		column.setKey("adjustAmt");
		column.getStyleAttributes().setHided(true);
		column = table.addColumn();
		column.setKey("amount");
		
		//end
		column = table.addColumn();
		column.setKey("buildPart");
		column = table.addColumn();
		column.setKey("sellPart");
		column = table.addColumn();
		column.setKey("program");
		column = table.addColumn();
		column.setKey("desc");
		column = table.addColumn();
		column.setKey("changeReason");
		column = table.addColumn();
		column.setKey("description");
		IRow row = table.addHeadRow();
		row.getCell("acctNumber").setValue("科目编码");
		row.getCell("acctName").setValue("科目名称");
		row.getCell("indexName").setValue("原始指标名称");
		row.getCell("index").setValue("原始指标值");
		row.getCell("coefficientName").setValue("系数名称");
		row.getCell("coefficient").setValue("系数值");
		row.getCell("workload").setValue("工作量");
		row.getCell("unit").setValue("单位");
		row.getCell("price").setValue("单价");
		if(isUseAdjustCoefficient()){
			row.getCell("sumPrice").setValue("调整前合价");
		}else{
			row.getCell("sumPrice").setValue("合价");
		}
		row.getCell("adjustCoefficient").setValue("调整系数");
		row.getCell("adjustAmt").setValue("调整金额");
		row.getCell("amount").setValue("合价");
		row.getCell("buildPart").setValue("建筑单方");
		row.getCell("sellPart").setValue("可售单方");
		row.getCell("program").setValue("合约规划");
		row.getCell("desc").setValue("备注");
		row.getCell("changeReason").setValue("变化原因");
//		row.getCell("description").setValue("描述");
		
		if(isUseQuality()){
			row.getCell("description").setValue("品质特征");
		}else{
			table.getColumn("description").getStyleAttributes().setHided(true);
		}
		
		if(!isUseAdjustCoefficient()){
			table.getColumn("adjustCoefficient").getStyleAttributes().setHided(true);
			table.getColumn("adjustAmt").getStyleAttributes().setHided(true);
			table.getColumn("amount").getStyleAttributes().setHided(true);
		}
//		if(((MeasureCostInfo)editData).getSourceBillId()==null){
//			table.getColumn("changeReason").getStyleAttributes().setHided(true);
//		}
/*		table.getColumn("index").getStyleAttributes().setHorizontalAlign(
				HorizontalAlignment.RIGHT);
		table.getColumn("coefficient").getStyleAttributes().setHorizontalAlign(
				HorizontalAlignment.RIGHT);
		table.getColumn("workload").getStyleAttributes().setHorizontalAlign(
				HorizontalAlignment.RIGHT);
		table.getColumn("price").getStyleAttributes().setHorizontalAlign(
				HorizontalAlignment.RIGHT);
		table.getColumn("sumPrice").getStyleAttributes().setHorizontalAlign(
				HorizontalAlignment.RIGHT);
		table.getColumn("sellPart").getStyleAttributes().setHorizontalAlign(
				HorizontalAlignment.RIGHT);

		table.getColumn("index").getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(2));
		table.getColumn("workload").getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(2));
		table.getColumn("coefficient").getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(2));
		table.getColumn("price").getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(2));
		table.getColumn("sumPrice").getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(2));
		table.getColumn("amount").getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(2));
		table.getColumn("adjustAmt").getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(2));
		table.getColumn("adjustCoefficient").getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(2));
		table.getColumn("sellPart").getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(2));*/
		FDCHelper.formatTableNumber(table, new String[]{
				"index","workload","sumPrice","amount","adjustAmt","buildPart","sellPart"});
		FDCHelper.formatTableNumber(table, "adjustCoefficient", "#,##0.000000;-#,##0.000000");
		FDCHelper.formatTableNumber(table, "coefficient", "#,##0.0000;-#,##0.0000");
		FDCHelper.formatTableNumber(table, "price", "#,##0.0000;-#,##0.0000");
		KDTextField textField = new KDTextField();
		textField.setMaxLength(80);
		ICellEditor txtEditor = new KDTDefaultCellEditor(textField);
		table.getColumn("acctName").setEditor(txtEditor);

		KDFormattedTextField formattedTextField = new KDFormattedTextField(
				KDFormattedTextField.DECIMAL);
		formattedTextField.setPrecision(2);
		formattedTextField.setSupportedEmpty(true);
		formattedTextField.setNegatived(false);
		ICellEditor numberEditor = new KDTDefaultCellEditor(formattedTextField);
		table.getColumn("index").setEditor(numberEditor);

		textField = new KDTextField();
		textField.setMaxLength(80);
		txtEditor = new KDTDefaultCellEditor(textField);
		table.getColumn("coefficientName").setEditor(txtEditor);

		/*** 项目公司在于进行目标成本测算的时候需要将单价和系数值精确到小数点后四位数，目前只能精确到小数点后两位数
		 * R090514-137
		 * ——by neo****/
		formattedTextField = new KDFormattedTextField(
				KDFormattedTextField.DECIMAL);
		formattedTextField.setPrecision(4);
		formattedTextField.setMaximumValue(FDCNumberHelper.TEN_THOUSAND);
		formattedTextField.setMinimumValue(FDCNumberHelper._TEN_THOUSAND);
		formattedTextField.setSupportedEmpty(true);
		formattedTextField.setNegatived(true);
		numberEditor = new KDTDefaultCellEditor(formattedTextField);
//		table.getColumn("coefficient").setEditor(numberEditor);
//		table.getColumn("price").setEditor(numberEditor);
		
		/*******/
		formattedTextField = new KDFormattedTextField(
				KDFormattedTextField.DECIMAL);
		formattedTextField.setPrecision(2);
		formattedTextField.setSupportedEmpty(true);
		formattedTextField.setNegatived(false);
		numberEditor = new KDTDefaultCellEditor(formattedTextField);
		table.getColumn("workload").setEditor(numberEditor);

		textField = new KDTextField();
		textField.setMaxLength(80);
		//单位只能选择,不能手工录入
		KDBizPromptBox f7Unit = new KDBizPromptBox(){
/*			protected String valueToString(Object o) {
				if(o instanceof IObjectValue){
					return (String)((IObjectValue)o).get("name");
				}
				return super.valueToString(o);
			}
			protected Object stringToValue(String t) {
				 Object obj= super.stringToValue(t);
				 if(obj instanceof  IObjectValue){
					 return obj;
				 }else{
					 return t;
				 }
			}*/
		};
		f7Unit.setQueryInfo("com.kingdee.eas.basedata.assistant.app.F7MeasureUnitQuery");
		f7Unit.setEditable(true);
		f7Unit.setDisplayFormat("$name$");
		f7Unit.setEditFormat("$number$");
		f7Unit.setCommitFormat("$number$");
		ICellEditor f7Editor = new KDTDefaultCellEditor(f7Unit);
		table.getColumn("unit").setEditor(f7Editor);
/*
		formattedTextField = new KDFormattedTextField(
				KDFormattedTextField.DECIMAL);
		formattedTextField.setPrecision(2);
		formattedTextField.setSupportedEmpty(true);
		formattedTextField.setNegatived(false);
		numberEditor = new KDTDefaultCellEditor(formattedTextField);
		table.getColumn("coefficient").setEditor(numberEditor);

		formattedTextField = new KDFormattedTextField(
				KDFormattedTextField.DECIMAL);
		formattedTextField.setPrecision(2);
		formattedTextField.setSupportedEmpty(true);
		formattedTextField.setNegatived(false);
		numberEditor = new KDTDefaultCellEditor(formattedTextField);
		table.getColumn("price").setEditor(numberEditor);
*/
		formattedTextField = new KDFormattedTextField(
				KDFormattedTextField.DECIMAL);
		formattedTextField.setPrecision(2);
		formattedTextField.setSupportedEmpty(true);
		formattedTextField.setNegatived(false);
//		formattedTextField.setMaximumValue(FDCHelper.MAX_TOTAL_VALUE.multiply(FDCHelper.MAX_TOTAL_VALUE));
		numberEditor = new KDTDefaultCellEditor(formattedTextField);
		table.getColumn("sumPrice").setEditor(numberEditor);
		table.getColumn("amount").setEditor(numberEditor);
		table.getColumn("adjustAmt").setEditor(numberEditor);
		formattedTextField = new KDFormattedTextField(
				KDFormattedTextField.DECIMAL);
		formattedTextField.setPrecision(6);
		formattedTextField.setMaximumValue(FDCNumberHelper.TEN_THOUSAND);
		formattedTextField.setMinimumValue(FDCNumberHelper._TEN_THOUSAND);
		formattedTextField.setSupportedEmpty(true);
		formattedTextField.setNegatived(true);
		numberEditor = new KDTDefaultCellEditor(formattedTextField);
		table.getColumn("adjustCoefficient").setEditor(numberEditor);

		textField = new KDTextField();
		textField.setMaxLength(80);
		txtEditor = new KDTDefaultCellEditor(textField);
		table.getColumn("description").setEditor(txtEditor);

		table.getColumn("acctNumber").getStyleAttributes().setLocked(true);
		Color lockColor =FDCTableHelper.cantEditColor;// new Color(0xF0AAD9);
		table.getColumn("acctNumber").getStyleAttributes().setBackground(
				lockColor);
		table.getColumn("index").getStyleAttributes().setLocked(true);
		table.getColumn("index").getStyleAttributes().setBackground(lockColor);
		// table.getColumn("workload").getStyleAttributes().setLocked(true);
		// table.getColumn("workload").getStyleAttributes().setBackground(
		// lockColor);
		// table.getColumn("sumPrice").getStyleAttributes().setLocked(true);
		// table.getColumn("sumPrice").getStyleAttributes().setBackground(
		// lockColor);
		table.getColumn("buildPart").getStyleAttributes().setLocked(true);
		table.getColumn("buildPart").getStyleAttributes().setBackground(
				lockColor);
		table.getColumn("sellPart").getStyleAttributes().setLocked(true);
		table.getColumn("sellPart").getStyleAttributes().setBackground(
				lockColor);
		table.getColumn("amount").getStyleAttributes().setLocked(true);
		table.getColumn("amount").getStyleAttributes().setBackground(lockColor);
/*		
		KDBizPromptBox f7ApportionType = new KDBizPromptBox();
		f7ApportionType
				.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7ApportionTypeQuery");
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("isEnabled", new Integer(1)));
		view.setFilter(filter);
		f7ApportionType.setEntityViewInfo(view);
		f7ApportionType.setEditable(true);
		f7ApportionType.setDisplayFormat("$name$");
		f7ApportionType.setEditFormat("$number$");
		f7ApportionType.setCommitFormat("$number$");
		ICellEditor f7Editor = new KDTDefaultCellEditor(f7ApportionType);
		
*/		
		ICellEditor editor=getIndexEditor(type, productId);
		table.getColumn("indexName").setEditor(editor);
		setTemplateMeasureCostF7Editor(table);
		this.addTableChangeEnvent(table);
	}

	public void fillTable(KDTable table) throws Exception {

		table.removeRows();
		table.setUserObject(null);
		DefaultKingdeeTreeNode root = (DefaultKingdeeTreeNode) costAcctTree
				.getRoot();
		Enumeration childrens = root.depthFirstEnumeration();
		int maxLevel = 0;
		while (childrens.hasMoreElements()) {
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) childrens
					.nextElement();
			if (node.getUserObject() != null && node.getLevel() > maxLevel) {
				maxLevel = node.getLevel();
			}
		}
		table.getTreeColumn().setDepth(maxLevel + 1);
		for (int i = 0; i < root.getChildCount(); i++) {
			DefaultMutableTreeNode child = (DefaultMutableTreeNode) root
					.getChildAt(i);
			fillNode(table, child);
		}
	}

	private void fillNode(KDTable table, DefaultMutableTreeNode node)
			throws BOSException, SQLException, EASBizException {
		CostAccountInfo costAcct = (CostAccountInfo) node.getUserObject();
		
		//应该先判断是否为空
		if (costAcct == null) {
			MsgBox.showError("成本科目的级别太多!");
			return;
		}
		
		if (costAcct.getType() != null) {
			if (table.getHeadRow(0).getUserObject() != null) {
				if (costAcct.getType().equals(CostAccountTypeEnum.SIX)) {
					return;
				}
			} else {
				if (costAcct.getType().equals(CostAccountTypeEnum.MAIN)) {
					return;
				}
			}
		}

		ProductTypeInfo product = (ProductTypeInfo) table.getHeadRow(0)
				.getUserObject();
		IRow row = table.addRow();
		row.setTreeLevel(node.getLevel() - 1);
		String longNumber = costAcct.getLongNumber();
		longNumber = longNumber.replace('!', '.');
		row.getCell("acctNumber").setValue(longNumber);
		row.getCell("acctName").setValue(costAcct.getName());
		row.setUserObject(costAcct);
		if (node.isLeaf()&&node.getLevel()>1) {
			String key = costAcct.getId().toString();
			if (product != null) {
				key += product.getId().toString();
			}
			MeasureEntryCollection coll = (MeasureEntryCollection) measureCostMap
					.get(key);
			if (coll != null && coll.size() > 0) {
				if(coll.size()==1){
					MeasureEntryInfo info = coll.get(0);
					IRow entryRow = row;
					entryRow.setUserObject(info);
					loadRow(table,entryRow, product);
					setDetailAcctRow(entryRow);
					row.getCell("acctName").setValue(costAcct.getName());
				}else{
					row.getStyleAttributes().setLocked(true);
					row.getStyleAttributes().setBackground(new Color(0xF0EDD9));
					row.setUserObject(costAcct);
					for (int i = 0; i < coll.size(); i++) {
						MeasureEntryInfo info = coll.get(i);
						IRow entryRow = table.addRow();
						entryRow.setTreeLevel(node.getLevel());
						entryRow.setUserObject(info);
						loadRow(table,entryRow, product);
					}
				}
			}else{
				//空科目的情况
				MeasureEntryInfo info = new MeasureEntryInfo();
				info.setCostAccount(costAcct);
				row.setUserObject(info);
				setTemplateMeasureCostF7Editor(table,  row);
				setDetailAcctRow(row);
			}
		} else {
			for (int i = 0; i < node.getChildCount(); i++) {
				this.fillNode(table, (DefaultMutableTreeNode) node
						.getChildAt(i));
			}
			row.getStyleAttributes().setLocked(true);
			row.getStyleAttributes().setBackground(new Color(0xF0EDD9));
		}
	}

	protected IObjectValue getValue(IObjectPK pk) throws Exception {
		return super.getValue(pk);
	}

	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sels = super.getSelectors();
		sels.add("*");
		sels.add("project.name");
		sels.add("project.longNumber");
		sels.add("project.number");
		sels.add("project.curProjProductEntries.isAccObj");
		sels.add("project.curProjProductEntries.*");
		sels.add("projectType.*");
		sels.add("orgUnit.id");
		sels.add("costEntry.*");
		sels.add("costEntry.costAccount.*");
		sels.add("costEntry.apportionType.*");
		sels.add("costEntry.unit.id");
		sels.add("costEntry.unit.name");
		sels.add("costEntry.unit.number");
		sels.add("measureStage.id");
		sels.add("measureStage.name");
		sels.add("measureStage.number");
		sels.add("constrEntrys.*");
		sels.add("constrEntrys.productType.*");
		
		sels.add("creator.*");
		sels.add("compareEntry.*");
		return sels;
	}

	public void loadRow(KDTable table,IRow row, ProductTypeInfo product) {
		MeasureEntryInfo info = (MeasureEntryInfo) row.getUserObject();
		row.getCell("acctName").setValue(info.getEntryName());
//		row.getCell("indexName").setValue(info.getApportionType());
/*		if (info.getApportionType() != null) {
			String key = project.getId().toString() + " ";
			if (product != null) {
				key += product.getId().toString() + " ";
			}
			key += info.getApportionType().getId().toString();
			BigDecimal value = (BigDecimal) this.apportionMap.get(key);
			row.getCell("index").setValue(value);
		}*/
		row.getCell("index").setValue(info.getIndexValue());
		if(info.getSimpleName()!=null){
//			row.getCell("indexName").setValue(info.getSimpleName());
			if(table.getColumn("indexName").getEditor()!=null){
				KDComboBox box=(KDComboBox)table.getColumn("indexName").getEditor().getComponent();
				if(box!=null){
					for(int i=0;i<box.getItemCount();i++){
						if(((Item)box.getItemAt(i)).key.equals(info.getSimpleName())){
							row.getCell("indexName").setValue(box.getItemAt(i));
							break;
						}
					}
				}
			}
		}
		row.getCell("coefficientName").setValue(info.getCoefficientName());
		BigDecimal coefficient = info.getCoefficient();
		if (coefficient != null && coefficient.compareTo(FDCHelper.ZERO) == 0) {
			coefficient = null;
		}
		//显示四位
		row.getCell("coefficient").setValue(FDCHelper.toBigDecimal(coefficient,4));
		BigDecimal workload = info.getWorkload();
		if (workload != null && workload.compareTo(FDCHelper.ZERO) == 0) {
			workload = null;
		}
		/*if (info.getApportionType() != null && coefficient != null) {
			row.getCell("workload").getStyleAttributes().setLocked(true);
		}*/
		
		if (info.getSimpleName() != null && coefficient != null) {
			row.getCell("workload").getStyleAttributes().setLocked(true);
		}
		row.getCell("workload").setValue(workload);
		//老数据升级　unit保存在name里面
		if(info.getUnit()==null){
			row.getCell("unit").setValue(info.getName());
		}else{
			row.getCell("unit").setValue(info.getUnit());
		}
		BigDecimal price = info.getPrice();
		if (price != null && price.compareTo(FDCHelper.ZERO) == 0) {
			price = null;
		}
		row.getCell("price").setValue(FDCHelper.toBigDecimal(price,4));
		row.getCell("sumPrice").setValue(info.getCostAmount());
		if (workload != null && price != null) {
			row.getCell("sumPrice").getStyleAttributes().setLocked(true);
		}
		// if (buildArea != null && buildArea.compareTo(FDCHelper.ZERO) != 0) {
		// BigDecimal buildPart = info.getCostAmount().divide(this.buildArea,
		// 2, BigDecimal.ROUND_HALF_UP).setScale(2,
		// BigDecimal.ROUND_HALF_UP);
		// row.getCell("buildPart").setValue(buildPart);
		// }
/*		String key = project.getId().toString() + " ";
		if (product != null) {
			key += product.getId().toString() + " ";
		}*/
//		key += ApportionTypeInfo.sellAreaType;
//		BigDecimal sellArea = (BigDecimal) this.apportionMap.get(key);
		Object obj=table.getHeadRow(0).getCell(0).getUserObject();
		if (obj instanceof PlanIndexEntryInfo) {
			BigDecimal sellArea = ((PlanIndexEntryInfo) obj).getSellArea();
			if (sellArea != null && sellArea.compareTo(FDCHelper.ZERO) != 0) {
				BigDecimal sellPart = FDCNumberHelper.divide(info.getAmount(),
						sellArea);
				row.getCell("sellPart").setValue(sellPart);
			} else {
				row.getCell("sellPart").setValue(null);
			}
			BigDecimal buildArea = ((PlanIndexEntryInfo) obj).getBuildArea();
			if (buildArea != null && buildArea.compareTo(FDCHelper.ZERO) != 0) {
				BigDecimal buildPart = FDCNumberHelper.divide(info.getAmount(),
						buildArea);
				row.getCell("buildPart").setValue(buildPart);
			} else {
				row.getCell("buildPart").setValue(null);
			}
		}else if(obj instanceof PlanIndexInfo){
			//六类公摊的单方
			BigDecimal sellArea = ((PlanIndexInfo)obj).getBigDecimal("allSellArea");
			if (sellArea != null && sellArea.compareTo(FDCHelper.ZERO) != 0) {
				BigDecimal sellPart = FDCNumberHelper.divide(info.getAmount(),sellArea);
				row.getCell("sellPart").setValue(sellPart);
			}else{
				row.getCell("sellPart").setValue(null);
			}
			BigDecimal buildArea = ((PlanIndexInfo)obj).getBigDecimal("allBuildArea");
			if (buildArea != null && buildArea.compareTo(FDCHelper.ZERO) != 0) {
				BigDecimal buildPart = FDCNumberHelper.divide(info.getAmount(),buildArea);
				row.getCell("buildPart").setValue(buildPart);
			}else{
				row.getCell("buildPart").setValue(null);
			}
		}
		row.getCell("amount").setValue(info.getAmount());
		row.getCell("adjustCoefficient").setValue(info.getAdjustCoefficient());
		row.getCell("adjustAmt").setValue(info.getAdjustAmt());
		row.getCell("program").setValue(info.getProgram());
		row.getCell("desc").setValue(info.getDesc());
		row.getCell("changeReason").setValue(info.getChangeReason());
		Boolean isEditVersion=(Boolean)getUIContext().get("isEditVersion");
    	if(isEditVersion!=null){
    		if(isEditVersion.booleanValue()){
    			info.setDescription(null);
    		}
    	}
		row.getCell("description").setValue(info.getDescription());
		if(isHasCompareEntry){
			if("upred".equals(info.getDescription())){
				table.getRow(row.getRowIndex()-1).getStyleAttributes().setBackground(up.getBackground());
			}else if("red".equals(info.getDescription())){
				row.getStyleAttributes().setBackground(up.getBackground());
			}else if("upgreen".equals(info.getDescription())){
				table.getRow(row.getRowIndex()-1).getStyleAttributes().setBackground(down.getBackground());
			}else if("green".equals(info.getDescription())){
				row.getStyleAttributes().setBackground(down.getBackground());
			}
		}
		setTemplateMeasureCostF7Editor(table, row);
	}
	
	
	boolean isEdit=false;
	public void onLoad() throws Exception {
		/**
		 * 由于框架问题，使得addnew操作受限，通过addnew1伪装后，创建了窗口后
		 * 然后再修改回来
		 */
		if(this.getOprtState().equals("ADDNEW1")){
			this.setOprtState("ADDNEW");
		}
		this.getMenuManager(null);
//		String projectId = (String) this.getUIContext().get("projectId");
/*		if(projectId!=null){
			project = CurProjectFactory.getRemoteInstance().getCurProjectInfo(
					new ObjectUuidPK(BOSUuid.read(projectId)));
			acctFilter.getFilterItems().add(
					new FilterItemInfo("curProject.id", projectId));
		}else{
			String orgId = (String) this.getUIContext().get("orgId");
			acctFilter.getFilterItems().add(new FilterItemInfo("fullOrgUnit.id",orgId));
		}*/

/*		this.apportionMap = ProjectHelper.getIndexValueByProjProd(null,
				projectId, ProjectStageEnum.AIMCOST);
*/
		this.txtVersionNumber.setEnabled(false);
		this.actionImportApportion.setVisible(false);
		this.menuSubmitOption.setVisible(false);
		this.actionPrint.setEnabled(true);
		this.actionPrintPreview.setEnabled(true);
		if(STATUS_ADDNEW.equals(getOprtState()) || STATUS_EDIT.equals(getOprtState())){
			isEdit=true;
		}else{
			isEdit=false;
		}
		FDCClientHelper.initComboMeasureStage(comboMeasureStage,isEdit);
		initMeasureIndex();
		super.onLoad();

		txtVersionName.setMaxLength(80);
		initCtrlListener();

		Boolean isEditVersion=(Boolean)getUIContext().get("isEditVersion");
		if(isEditVersion!=null&&isEditVersion.booleanValue()){//是否修订版本
			comboMeasureStage.setEnabled(false);
		}
		Boolean isAimMeasure=(Boolean)getUIContext().get("isAimMeasure");
		if(isAimMeasure==null){
			isAimMeasure=Boolean.TRUE;
			getUIContext().put("isAimMeasure",Boolean.TRUE);
		}
		if(!isAimMeasure.booleanValue()){
			setUITitle("可研成本测算");
		}else{
			setUITitle("目标成本测算");
		}
		((MeasureCostInfo)editData).setIsAimMeasure(isAimMeasure.booleanValue());
		if(getOprtState().equals(OprtState.ADDNEW)){
			actionImportTemplate.setEnabled(true);
		}
		actionAddNew.setEnabled(false);
		actionAddNew.setVisible(false);
		
		registerMeasureDefaultSplitTypeSetKey();
        
    	actionEdit.setVisible(false);
    	actionEdit.setEnabled(false);
    	
    	comboVersionType.setEnabled(false);
    	editVersion();
    	
    	setShowMessagePolicy(SHOW_MESSAGE_BOX_FIRST);
    	actionCopy.setVisible(false);
    	if(editData!=null&&((MeasureCostInfo)editData).getMeasureStage()!=null){
    		comboMeasureStage.setSelectedItem(((MeasureCostInfo)editData).getMeasureStage());
    	}
    	storeFields();//汇总表增加afterSetUnionData退出提示
    	
    	actionImportData.setVisible(false);
//    	actionExportAllToExcel.setVisible(false);
    	
    	if(this.editData.getId()!=null&&!(getUIContext().get("Owner") instanceof AimMeasureCostListUI)){
    		ProcessInstInfo instInfo = null;
    		IEnactmentService svc = EnactmentServiceFactory.createRemoteEnactService();
    		ProcessInstInfo[] procInsts = svc.getProcessInstanceByHoldedObjectId(this.editData.getId().toString());
    		for (int i = 0, n = procInsts.length; i < n; i++) {
    			if ("open.running".equals(procInsts[i].getState())
    					|| "open.not_running.suspended".equals(procInsts[i].getState())) {
    				instInfo = procInsts[i];
    			}
    		}
    		if(instInfo!=null){
    			Map  processVarMap = svc.getProcessContext(instInfo.getProcInstId());
    			if(processVarMap.get("isHideTable")!=null&&Boolean.valueOf(processVarMap.get("isHideTable").toString())){
    				for(int i=0;i<tables.size();i++){
    					KDTable table=(KDTable) tables.get(i);
    					if(table.getHeadRow(0).getUserObject() instanceof ProductTypeInfo||
    							table.getName().equals("六类公摊及期间费")){
    						this.plTables.remove(table);
    					}
    				}
    			}
    		}
    	}
    	this.btnAttachment.setVisible(false);
	}

	private void editVersion() throws BOSException, SQLException {
		Boolean isEditVersion=(Boolean)getUIContext().get("isEditVersion");
    	if(isEditVersion!=null&&isEditVersion.booleanValue()){
    		//修改版本号
    		this.comboVersionType.setSelectedItem(VersionTypeEnum.AdjustVersion);
    		String sourceId=((MeasureCostInfo)editData).getSourceBillId();
    		if(sourceId==null){
    			sourceId=editData.getId().toString();
    		}
    		FDCSQLBuilder builder=new FDCSQLBuilder();
    		builder.appendSql("select FVersionNumber from T_AIM_MeasureCost where fsourceBillId=?");
    		builder.addParam(sourceId);
    		IRowSet rowSet=builder.executeQuery();
    		int lastSubVersion=1;
    		String verNo=((MeasureCostInfo)editData).getVersionNumber();
    		verNo=verNo.replaceAll("\\.", "!");
    		while(rowSet.next()){
    			//找出最大版本
    			verNo=rowSet.getString("FVersionNumber");
    			verNo=verNo.replaceAll("\\.", "!");
    			if(verNo!=null&&verNo.indexOf('!')>0){
    				int temp=Integer.parseInt(verNo.substring(verNo.indexOf('!')+1));
    				if(temp>=lastSubVersion){
    					lastSubVersion=temp+1;
    				}
    			}
    			
    		}
    		if(!FDCHelper.isEmpty(verNo)){
    			if(verNo.indexOf('!')>0){
    				int index=verNo.lastIndexOf("!");
    				verNo=verNo.substring(0, index+1)+lastSubVersion;
    			}
    		}
    		txtVersionNumber.setText(verNo.replaceAll("!", "\\."));
    	}
	}

	private void registerMeasureDefaultSplitTypeSetKey() {
		String actionName="MeasureDefaultSplitTypeSetUI";
		final UIContext uiContext = new UIContext(this);
		this.getActionMap().put(actionName, new javax.swing.AbstractAction(){
			public void actionPerformed(ActionEvent e) {
                try {
                	setCursorOfWair();
					IUIFactory fy = UIFactory.createUIFactory(UIFactoryName.MODEL);
					IUIWindow wnd = fy.create(MeasureDefaultSplitTypeSetUI.class.getName(),
							uiContext,null,"EDIT",WinStyle.SHOW_KINGDEELOGO);
					wnd.show();
                } catch (Exception e1) {
                    handUIException(e1);
                }finally{
                	setCursorOfDefault();
                }
            }
		});
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ctrl shift alt F12"), actionName);
		
	}
	
	private void initCtrlListener(){
		prmtProjectType.addDataChangeListener(new DataChangeListener(){
			public void dataChanged(DataChangeEvent e) {
				if(e.getNewValue()==null){
					prmtProject.setData(null);
				}
				if(e.getOldValue()==null?e.getNewValue()!=null:!e.getOldValue().equals(e.getNewValue())){
					prmtProject.setData(null);
				}
				
			}
		});
		SelectorItemCollection selector=new SelectorItemCollection();
		selector.add("name");
		selector.add("number");
		selector.add("longNumber");
		selector.add("projectType.*");
		prmtProject.setSelectorCollection(selector);
		prmtProject.addDataChangeListener(new DataChangeListener(){
			public void dataChanged(DataChangeEvent e) {
				if(prmtProjectType.getData()==null&&e.getNewValue()!=null){
					EventListener[] listeners = prmtProjectType.getListeners(DataChangeListener.class);
					for(int i=0;i<listeners.length;i++){
						prmtProjectType.removeDataChangeListener((DataChangeListener)listeners[i]);
					}
					prmtProjectType.setData(((CurProjectInfo)e.getNewValue()).getProjectType());
					for(int i=0;i<listeners.length;i++){
						prmtProjectType.addDataChangeListener((DataChangeListener)listeners[i]);
					}
				}
			}
		});
		prmtProject.addSelectorListener(new SelectorListener(){
			public void willShow(SelectorEvent e) {
				prmtProject.getQueryAgent().resetRuntimeEntityView();
				String projectTypeid = FDCHelper.getF7Id(prmtProjectType);
				EntityViewInfo view = prmtProject.getEntityViewInfo();
				if(view==null){
					view=new EntityViewInfo();
				}
				view.setFilter(new FilterInfo());
				if(projectTypeid!=null){
					view.getFilter().appendFilterItem("projectType.id", projectTypeid);
				}
				view.getFilter().appendFilterItem("CU.id", SysContext.getSysContext().getCurrentCtrlUnit().getId().toString());
//				prmtProject
				prmtProject.setEntityViewInfo(view);
			}
		});
		plTables.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent e) {
				Object obj=plTables.getClientProperty("oldIndex");
				int i=2;
				if(isHasCompareEntry){
					i=3;
				}
				if(obj instanceof Integer){
					if(((Integer)obj).intValue() ==i){
						refreshAllMeasureTable();
					}
				}
				plTables.putClientProperty("oldIndex", new  Integer(plTables.getSelectedIndex()));
				if(plTables.getSelectedIndex()==i-1){
					measureCollectTable.refresh();
					setTotalColor();
				}
			}
			
		});
	}
	public void loadFields() {
		/*TimeTools.getInstance().msValuePrintln("start loadFields");
		if(getUIContext().get("MeasureEditData") instanceof MeasureCostInfo){
			MeasureCostInfo editData1=(MeasureCostInfo)getUIContext().get("MeasureEditData");
			this.editData.putAll(editData1);
		}else{
		}*/
//		System.out.println("sdjfk");
		super.loadFields();
		//只取公司的成本科目
		String orgId = (String) this.getUIContext().get("orgId");
		/*如果是在工作流中直接打开一个AimMeasureCostEditUI而不是从序时簿上双击打开的话字段isAimMeasure极有可能为空
		这样的话经过onLoad方法中的
		if(isAimMeasure==null){
			isAimMeasure=Boolean.TRUE;
			getUIContext().put("isAimMeasure",Boolean.TRUE);
		}
		处理便会使得isAimMeasure字段的值变为true.但是在配置工作流中这个字段是用来区分是可研测算工作流或是目标成本测算工作流
		的依据。如此一来，就区分不了了。故现在处理一下，避免流程的混淆   by Cassiel 2009-10-30
		*/
		MeasureCostInfo cost = (MeasureCostInfo) this.editData;
		if(getUIContext().get("isAimMeasure")==null){
			getUIContext().put("isAimMeasure", Boolean.valueOf(cost.isIsAimMeasure()));
		}
		if(orgId==null){
			orgId=((MeasureCostInfo)this.editData).getOrgUnit().getId().toString();
			this.getUIContext().put("orgId", orgId);
		}
		if(costAcctTree==null){
			try{
				FilterInfo acctFilter = new FilterInfo();
				acctFilter.getFilterItems().add(
						new FilterItemInfo("isEnabled", Boolean.TRUE));
			/*
			 * 为支持测算的时候可以进行更明细科目的测算，科目必须用工程项目的  by sxhong 2009-08-25 15:11:21
			*/	
			if(cost.getProject()==null //选组织新增的
				//老数据，老数据以前是用实体财务组织的成本科目来测算的 by sxhong
			   ||(cost.getCostEntry().size()>0&&cost.getCostEntry().get(0).getCostAccount().getCurProject()==null)
			){
				acctFilter.getFilterItems().add(new FilterItemInfo("fullOrgUnit.id",orgId));
				accreditSet=AcctAccreditHelper.handAcctAccreditFilter(null, orgId, acctFilter);
			}else{
				String prjId = cost.getProject().getId().toString();
				acctFilter.getFilterItems().add(new FilterItemInfo("curProject.id",prjId));
				accreditSet=AcctAccreditHelper.handAcctAccreditFilter(null, prjId, acctFilter);
			}
			//在使用了MergeFilter之后再给FilterInfo添加FilterItemInfo不再起作用，必须在MergeFilter之前 .by sxhong、Cassiel 
//			acctFilter.getFilterItems().add(
//					new FilterItemInfo("isEnabled", new Integer(1)));
			costAcctTree = FDCClientHelper.createDataTree(CostAccountFactory
					.getRemoteInstance(), acctFilter);
			}catch(Exception e){
				this.handUIException(e);
			}
		}
		GlUtils.setSelectedItem(comboMeasureStage, cost.getMeasureStage());
		this.prmtProject.setValue(cost.getProject());
		this.prmtProjectType.setValue(cost.getProjectType());
		
		//判断版本号是否为空，如果为空，错误信息提示
		if(!(cost.getVersionNumber()== null ))
		{
			this.txtVersionNumber.setText(cost.getVersionNumber().replaceAll("!", "\\."));
		}
		else
		{
			MsgBox.showWarning(this, "版本号不能为空");
			SysUtil.abort();
		}
		this.txtVersionName.setText(cost.getVersionName());
		this.comboVersionType.setSelectedItem(cost.getVersionType());
		measureCostMap.clear();
		MeasureEntryCollection costEntrys = cost.getCostEntry();
/*		if(editData!=null&&editData.getId()!=null&&costEntrys.size()==0){
			EntityViewInfo view=new EntityViewInfo();
			view.getSelector().add("*");
			view.getSelector().add("costAccount.*");
			view.getSelector().add("product.*");
			view.setFilter(new FilterInfo());
			view.getFilter().appendFilterItem("head.id", editData.getId().toString());
			try {
				costEntrys=MeasureEntryFactory.getRemoteInstance().getMeasureEntryCollection(view);
			} catch (BOSException e) {
				handUIException(e);
			}
		}*/
		for (int i = 0; i < costEntrys.size(); i++) {
			MeasureEntryInfo info = costEntrys.get(i);
			CostAccountInfo costAccount = info.getCostAccount();
			String key = costAccount.getId().toString();
			if (info.getProduct() != null) {
				key += info.getProduct().getId().toString();
			}
			if (measureCostMap.containsKey(key)) {
				MeasureEntryCollection coll = (MeasureEntryCollection) measureCostMap
						.get(key);
				coll.add(info);
			} else {
				MeasureEntryCollection newColl = new MeasureEntryCollection();
				newColl.add(info);
				measureCostMap.put(key, newColl);
			}
		}
		try {
			addPanel();
		} catch (Exception e) {
			e.printStackTrace();
		}
		TimeTools.getInstance().msValuePrintln("end loadFields");
		
		//互斥
		if(STATUS_EDIT.equals(getOprtState())&&cost!=null&&cost.getId()!=null){
			String billId =cost.getId().toString();
			lockIds.add(billId);
			String measureIncomeId = getMeasureIncomeId(billId);
			if(measureIncomeId!=null){//未启用收入测算子联用参数或可研时
				lockId2s.add(measureIncomeId);
				try{
					FDCClientUtils.requestDataObjectLock(this, lockIds, "edit");
					Map uiContext = new HashMap();
					uiContext.put(UIContext.ID, measureIncomeId);
					IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.NEWWIN);
					IUIWindow window = uiFactory.create(MeasureIncomeEditUI.class.getName(), uiContext, null, OprtState.VIEW);
					MeasureIncomeEditUI = (CoreUI)window.getUIObject();
					if(lockId2s.size()>0)
					FDCClientUtils.requestDataObjectLock(MeasureIncomeEditUI, lockId2s, "edit");
				}
				catch (Throwable e1) {
					this.handUIException(e1);
					hasMutex = FDCClientUtils.hasMutexed(e1);
				}
			}
		}
		
	}
	private String getMeasureIncomeId(String measureCostId) {
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql("select fid from t_aim_measureincome where fsrcmeasurecostid=? ");
		builder.addParam(measureCostId);
		try {
			IRowSet rs = builder.executeQuery();
			if(rs!=null&&rs.next()){
				return rs.getString("fid");
			}
		} catch (SQLException e) {
			handUIException(e);
		} catch (BOSException e1) {
			handUIException(e1);
		}
		return null;
	}
	private boolean isLastVersion(String measureCostId){
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql("select FIsLastVersion from t_aim_measurecost where fid=? ");
		builder.addParam(measureCostId);
		try {
			IRowSet rs = builder.executeQuery();
			if(rs!=null&&rs.next()){
				return rs.getBoolean("FIsLastVersion");
			}
		} catch (SQLException e) {
			handUIException(e);
		} catch (BOSException e1) {
			handUIException(e1);
		}
		return false;
	}
	protected void table_editStopped(KDTable table, KDTEditEvent e)
			throws Exception {
		Object oldValue = e.getOldValue();
		Object newValue=e.getValue();
		if(oldValue==null&&newValue==null){
			return;
		}
		//第一次加载数据时oldValue,newValue都为空,增加上面一句，返回
		if(oldValue!=null&&newValue!=null&&oldValue.equals(newValue)){
			return;
		}
		IObjectValue info=null;
		Object objTmp=table.getHeadRow(0).getCell(0).getUserObject();
		
		planIndexTable.getPlanIndexInfo();
		
		if(table.getHeadRow(0).getUserObject()!=null){
			objTmp=planIndexTable.getPlanIndexEntryInfo(((ProductTypeInfo)table.getHeadRow(0).getUserObject()).getId().toString());
			table.getHeadRow(0).getCell(0).setUserObject(objTmp);
		}
		
		if(objTmp instanceof IObjectValue){
			info=(IObjectValue)objTmp;
		}
		int rowIndex = e.getRowIndex();
		int columnIndex = e.getColIndex();
		if(oldValue==null&&newValue==null){
			if(!table.getColumnKey(columnIndex).equals("indexName")){
				return;
			}
		}
		
		IRow row = table.getRow(rowIndex);
		if (table.getColumnKey(columnIndex).equals("index")
				|| table.getColumnKey(columnIndex).equals("coefficient")) {
			refreshWorkload(table,row);
		} else if (table.getColumnKey(columnIndex).equals("price")
				|| table.getColumnKey(columnIndex).equals("workload")) {
			refreshSumPrice(table,row);
		} else if (table.getColumnKey(columnIndex).equals("sumPrice")) {
			row.getCell("adjustCoefficient").setUserObject("adjust");
			refreshAdjustAmount(table,row);
		} else if (table.getColumnKey(columnIndex).equals("indexName")) {
			Object obj=row.getCell("indexName").getValue();
			if(obj instanceof Item){
				Item item=(Item)obj;
//				row.getCell("index").setValue(item.getValue());
				if(info!=null){
					BigDecimal amount=FDCHelper.ZERO;
					//处理景观面积及户数
					if(info instanceof PlanIndexInfo){
						PlanIndexInfo planIndexInfo=(PlanIndexInfo)info;
//						if(item.key.equals("viewArea")){
							//道路用地合计+绿化用地合计
//							amount=FDCHelper.toBigDecimal(planIndexInfo.getTotalRoadArea()).add(FDCHelper.toBigDecimal(planIndexInfo.getTotalGreenArea())); 
//						}
						if(item.key.equals("doors")){
							//户数之和
							for(int i=0;i<planIndexInfo.getEntrys().size();i++){
								PlanIndexEntryInfo entry = planIndexInfo.getEntrys().get(i);
								amount=amount.add(FDCHelper.toBigDecimal(entry.getDoors()));
							}
						}
					}
					if(amount.compareTo(FDCHelper.ZERO)==0){
						if(!"entityIndex".equals(item.key)){
							row.getCell("index").getStyleAttributes().setLocked(true);
							row.getCell("index").getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
							row.getCell("index").setValue(getCustomIndexValue(info, item));
						}else {
							if(isEdit){
								row.getCell("index").getStyleAttributes().setLocked(false);
								row.getCell("index").getStyleAttributes().setBackground(Color.WHITE);
							}
						}
						
					}else{
						row.getCell("index").setValue(amount);
					}
					
					if(item.key.equals("empty")){
						row.getCell("coefficient").setValue(null);
						row.getCell("coefficientName").setValue(null);
					}
				}
			}
			refreshWorkload(table,row);
		}
		
		//调整系数
		if (table.getColumnKey(columnIndex).equals("adjustCoefficient")) {
			row.getCell(columnIndex).setUserObject("adjust");
			refreshAdjustAmount(table,row);
		} 
		if (table.getColumnKey(columnIndex).equals("adjustAmt")) {
			refreshAdjustAmount(table,row);
		} 
		if(isDetailAcctRow(row)){
			if(newValue!=null){
				setDetailAcctHashInput(row);
			}else{
				boolean isEmpty=true;
				for(int i=3;i<table.getColumnCount();i++){
					if(!FDCHelper.isEmpty(row.getCell(i).getValue())){
						isEmpty=false;
						break;
					}
				}
				if(isEmpty){
					setDetailAcctHasNotInput(row);
				}
			}
		}
		setUnionData(table);
		
		setDataChange(true);
	}

	private void refreshSumPrice(KDTable table,IRow row) {
		BigDecimal price = (BigDecimal) row.getCell("price").getValue();
		BigDecimal workload = (BigDecimal) row.getCell("workload").getValue();
		if (price == null) {
			price = FDCHelper.ZERO;
		}
		if (workload == null) {
			workload = FDCHelper.ZERO;
		}

		if (price.compareTo(FDCHelper.ZERO) == 0
				&& workload.compareTo(FDCHelper.ZERO) == 0) {
			row.getCell("price").setValue(null);
			row.getCell("workload").setValue(null);
			row.getCell("sumPrice").getStyleAttributes().setLocked(false);
		} else {

			BigDecimal sumPrice = price.multiply(workload).setScale(2,
					BigDecimal.ROUND_HALF_UP);
			;
			row.getCell("sumPrice").setValue(sumPrice);
			row.getCell("sumPrice").getStyleAttributes().setLocked(true);
/*			放到refreshAdjustAmount(row);内计算
			if (sumPrice != null) {
				KDTable table = (KDTable) this.tables.get(this.plTables
						.getSelectedIndex());
				ProductTypeInfo product = (ProductTypeInfo) table.getHeadRow(0)
						.getUserObject();
				String key = project.getId().toString() + " ";
				if (product != null) {
					key += product.getId().toString() + " ";
				}
				key += ApportionTypeInfo.sellAreaType;
				Object obj=table.getHeadRow(0).getCell(0).getUserObject();
				if(obj instanceof PlanIndexEntryInfo){
					BigDecimal sellArea = ((PlanIndexEntryInfo)obj).getSellArea();
					if (sellArea != null && sellArea.compareTo(FDCHelper.ZERO) != 0) {
						BigDecimal sellPart = sumPrice.divide(sellArea,
								BigDecimal.ROUND_HALF_UP);
						row.getCell("sellPart").setValue(sellPart);
					}else{
						row.getCell("sellPart").setValue(null);
					}
				}else if(obj instanceof PlanIndexInfo){
					BigDecimal sellArea = ((PlanIndexInfo)obj).getBigDecimal("allSellArea");
					if (sellArea != null && sellArea.compareTo(FDCHelper.ZERO) != 0) {
						BigDecimal sellPart = sumPrice.divide(sellArea,
								BigDecimal.ROUND_HALF_UP);
						row.getCell("sellPart").setValue(sellPart);
					}else{
						row.getCell("sellPart").setValue(null);
					}
				}else{
					row.getCell("sellPart").setValue(null);
				}

			} else {
				row.getCell("sellPart").setValue(null);
			}*/
		}
		row.getCell("adjustCoefficient").setUserObject("adjust");
		refreshAdjustAmount(table,row);
	}

	private void refreshWorkload(KDTable table,IRow row) {
		if(AimMeasureCostEditUI.indexName != null){
			row.getCell("coefficientName").setValue(AimMeasureCostEditUI.indexName);
			AimMeasureCostEditUI.indexName = null;
		}
		
		BigDecimal index = FDCHelper.toBigDecimal(row.getCell("index").getValue());
		BigDecimal coefficient = FDCHelper.toBigDecimal(row.getCell("coefficient").getValue());
		if (index == null) {
			index = FDCHelper.ZERO;
		}
		if (coefficient == null) {
			coefficient = FDCHelper.ZERO;
		} 

		if (index.compareTo(FDCHelper.ZERO) == 0
				&& coefficient.compareTo(FDCHelper.ZERO) == 0) {
			row.getCell("index").setValue(null);
			row.getCell("coefficient").setValue(null);
			row.getCell("workload").getStyleAttributes().setLocked(false);
		} else {
			BigDecimal workload = index.multiply(coefficient).setScale(2,
					BigDecimal.ROUND_HALF_UP);
			row.getCell("workload").setValue(workload);
			row.getCell("workload").getStyleAttributes().setLocked(true);

		}
		refreshSumPrice(table,row);
	}
	
	/**
	 * 调整系数与合价的计算
	 * 合价=调整前合价+调整额。其中调整额=原有合价*调整系数,其中调整额可以手工在录入，也可以通过计算得到
	 *  by sxhong 2008-05-29 17:14:12
	 * @param row
	 */
	private void refreshAdjustAmount(KDTable table,IRow row){
		BigDecimal sumPrice = (BigDecimal)row.getCell("sumPrice").getValue();
		if(row.getCell("adjustCoefficient").getUserObject()!=null){
			BigDecimal adjustAmt=FDCNumberHelper.multiply(sumPrice, row.getCell("adjustCoefficient").getValue());
			row.getCell("adjustAmt").setValue(adjustAmt);
			row.getCell("amount").setValue(FDCNumberHelper.add(sumPrice, adjustAmt));
		}else{
			BigDecimal adjustAmt=(BigDecimal)row.getCell("adjustAmt").getValue();
			row.getCell("adjustCoefficient").setValue(FDCNumberHelper.divide(adjustAmt,sumPrice,6,BigDecimal.ROUND_HALF_UP));
			row.getCell("amount").setValue(FDCNumberHelper.add(sumPrice, adjustAmt));
		}
		row.getCell("adjustCoefficient").setUserObject(null);
		
		//算单方
		BigDecimal amount = (BigDecimal)row.getCell("amount").getValue();
		if (amount != null) {
			Object obj=table.getHeadRow(0).getCell(0).getUserObject();
			if(obj instanceof PlanIndexEntryInfo){
				BigDecimal sellArea = ((PlanIndexEntryInfo)obj).getSellArea();
				if (sellArea != null && sellArea.compareTo(FDCHelper.ZERO) != 0) {
					BigDecimal sellPart = amount.divide(sellArea,
							BigDecimal.ROUND_HALF_UP);
					row.getCell("sellPart").setValue(sellPart);
				}else{
					row.getCell("sellPart").setValue(null);
				}
				BigDecimal buildArea = ((PlanIndexEntryInfo)obj).getBuildArea();
				if (buildArea != null && buildArea.compareTo(FDCHelper.ZERO) != 0) {
					BigDecimal buildPart = amount.divide(buildArea,
							BigDecimal.ROUND_HALF_UP);
					row.getCell("buildPart").setValue(buildPart);
				}else{
					row.getCell("buildPart").setValue(null);
				}
			}else if(obj instanceof PlanIndexInfo){
				BigDecimal sellArea = ((PlanIndexInfo)obj).getBigDecimal("allSellArea");
				if (sellArea != null && sellArea.compareTo(FDCHelper.ZERO) != 0) {
					BigDecimal sellPart = amount.divide(sellArea,
							BigDecimal.ROUND_HALF_UP);
					row.getCell("sellPart").setValue(sellPart);
				}else{
					row.getCell("sellPart").setValue(null);
				}
				BigDecimal buildArea = ((PlanIndexInfo)obj).getBigDecimal("allBuildArea");
				if (buildArea != null && buildArea.compareTo(FDCHelper.ZERO) != 0) {
					BigDecimal buildPart = amount.divide(buildArea,
							BigDecimal.ROUND_HALF_UP);
					row.getCell("buildPart").setValue(buildPart);
				}else{
					row.getCell("buildPart").setValue(null);
				}
			}else{
				row.getCell("buildPart").setValue(null);
				row.getCell("sellPart").setValue(null);
			}

		} else {
			row.getCell("buildPart").setValue(null);
			row.getCell("sellPart").setValue(null);
		}
	}

	public void actionAddRow_actionPerformed(ActionEvent arg0) throws Exception {
		int ii=1;
		if(this.isHasCompareEntry){
			ii=2;
		}
		if(this.plTables.getSelectedIndex()==0){
			return;
		}
		if(this.plTables.getSelectedIndex()==1){
			return;
		}
		if(this.plTables.getSelectedIndex()==ii){
			return;
		}
		if(this.plTables.getSelectedIndex()==ii+1){
			planIndexTable.addRow(arg0);
			return;
		}
		
		if(this.plTables.getSelectedIndex()==ii+2){
			planIndexTable.addConstrIndexRow(arg0);
			return;
		}
//		if(this.plTables.getSelectedIndex()==4){
//			return;
//		}
		
		if(this.plTables.getSelectedIndex()==ii+3){
			Object v = prmtProjectType.getValue();
			if(v==null){
				MsgBox.showWarning(this, "六类公摊测算必须先设置项目系列");
				return;
			}
		}
		
		int selectIndex = this.plTables.getSelectedIndex();
		KDTable table = (KDTable) this.tables.get(selectIndex-2);
		if (table.getRowCount() == 0) {
			return;
		}
		int index = table.getSelectManager().getActiveRowIndex();
		if (index == -1) {
			table.getSelectManager().set(table.getRowCount() - 1, 0);
			index = table.getRowCount() - 1;
		}
		IRow selectRow = table.getRow(index);
		if (selectRow.getUserObject() instanceof CostAccountInfo) {
			CostAccountInfo acct = (CostAccountInfo) selectRow.getUserObject();
			if (acct.isIsLeaf()&&acct.getLevel()>1) {
				IRow row = table.addRow(index + 1);
				row.setTreeLevel(selectRow.getTreeLevel() + 1);
				MeasureEntryInfo info = new MeasureEntryInfo();
				info.setCostAccount(acct);
				row.setUserObject(info);
				table.setUserObject("addRow");
				setTemplateMeasureCostF7Editor(table,  row);
				
				
			} else {
				this.setMessageText("非明细行或一级明细行不能添加子行!");
				this.showMessage();
			}
		} else {
			MeasureEntryInfo infoUp = (MeasureEntryInfo) selectRow
					.getUserObject();
			if(isDetailAcctRow(selectRow)){
				{
					Map splitTypeMap=measureCollectTable.getSplitTypes();
					MeasureEntryInfo entry = (MeasureEntryInfo) selectRow.getUserObject();
					ProductTypeInfo product = (ProductTypeInfo) table.getHeadRow(0)
					.getUserObject();
					entry.setEntryName((String) selectRow.getCell("acctName").getValue());
					entry.setProduct(product);
					/*
					 * entry.setApportionType((ApportionTypeInfo)
					 * selectRow.getCell( "indexName").getValue());
					 */
					Object obj = selectRow.getCell("indexName").getValue();
					if (obj instanceof Item) {
						Item item = (Item) obj;
						entry.setSimpleName(item.key);
						entry.setIndexName(item.toString());
						entry.setIndexValue(FDCHelper.toBigDecimal(selectRow.getCell("index").getValue()));
						// KDComboBox
						// box=(KDComboBox)selectRow.getCell("indexName").getEditor().getComponent();
						// box.getSelectedIndex();
					}
					entry.setCoefficientName((String) selectRow.getCell("coefficientName").getValue());
					entry.setCoefficient((BigDecimal) selectRow.getCell("coefficient").getValue());
					final Object value = selectRow.getCell("unit").getValue();
					entry.setUnit((MeasureUnitInfo)value);
					// entry.setUnit(value);
					entry.setWorkload((BigDecimal) selectRow.getCell("workload").getValue());
					entry.setPrice((BigDecimal) selectRow.getCell("price").getValue());
					entry.setCostAmount((BigDecimal) selectRow.getCell("sumPrice").getValue());
					entry.setProgram(((String)selectRow.getCell("program").getValue()));
					entry.setDesc((String)selectRow.getCell("desc").getValue());
					entry.setChangeReason((String)selectRow.getCell("changeReason").getValue());
					entry.setDescription((String) selectRow.getCell("description").getValue());
					if (entry.getCostAccount().getType() == CostAccountTypeEnum.SIX) {
						Object splitType = splitTypeMap.get(entry.getCostAccount().getId().toString());
						if (splitType != null) {
							entry.setNumber(splitType.toString());
						}
					}
					entry.setAdjustCoefficient((BigDecimal) selectRow.getCell("adjustCoefficient").getValue());
					entry.setAdjustAmt((BigDecimal) selectRow.getCell("adjustAmt").getValue());
					entry.setAmount((BigDecimal) selectRow.getCell("amount").getValue());
				}
				//明细行录入
				IRow tempRow=table.addRow(index+1);
				tempRow.setUserObject(infoUp);
				tempRow.setTreeLevel(selectRow.getTreeLevel()+1);
				loadRow(table, tempRow, infoUp.getProduct());
				selectRow.setUserObject(infoUp.getCostAccount());
				selectRow.getCell(0).setUserObject(null);
				clearDetailAcctRow(table, selectRow);
				setDetailAcctRowNull(selectRow);
				selectRow.getStyleAttributes().setLocked(true);
				selectRow.getStyleAttributes().setBackground(new Color(0xF0EDD9));
				index++;
				IRow row = table.addRow(index + 1);
				row.setTreeLevel(selectRow.getTreeLevel()+1);
				MeasureEntryInfo info = new MeasureEntryInfo();
				info.setCostAccount(infoUp.getCostAccount());
				row.setUserObject(info);
				table.setUserObject("addRow");
				setTemplateMeasureCostF7Editor(table,  row);
				this.setUnionData(table);
			}else{
				IRow row = table.addRow(index + 1);
				row.setTreeLevel(selectRow.getTreeLevel());
				MeasureEntryInfo info = new MeasureEntryInfo();
				info.setCostAccount(infoUp.getCostAccount());
				row.setUserObject(info);
				table.setUserObject("addRow");
				setTemplateMeasureCostF7Editor(table,  row);
			}
		}
	}

	/**
	 * @deprecated
	 * @param table
	 * @param row
	 */
	private void setTemplateMeasureCostF7Editor(KDTable table, final IRow row) {
		if(true){
			return;
		}
		Boolean objBoolean=(Boolean)getUIContext().get("isAimMeasure");
		boolean isAimMeasure=true;
		if(objBoolean!=null){
			isAimMeasure=objBoolean.booleanValue();
		}
		Object obj = row.getUserObject();
		final CostAccountInfo acct;
		String indexType=null;
		if(obj instanceof MeasureEntryInfo){
			acct=((MeasureEntryInfo)obj).getCostAccount();
			indexType=((MeasureEntryInfo)obj).getSimpleName();
		}else{
			return;
		}
		String orgId = (String) this.getUIContext().get("orgId");
		String acctLongNumber=acct.getLongNumber();
		String projectTypeId=null;
		String productId=null;
		Object product = table.getHeadRow(0).getUserObject();
		if(product instanceof ProductTypeInfo){
			productId=((ProductTypeInfo)product).getId().toString();
		}
		if(acct.getType()==CostAccountTypeEnum.SIX){
			Object v = prmtProjectType.getValue();
			if(v instanceof ProjectTypeInfo){
				projectTypeId=((ProjectTypeInfo)v).getId().toString();
				
			}
		}
		
		//set cell editor
		TemplateMeasureCostPromptBox selector=new TemplateMeasureCostPromptBox(this,isAimMeasure,orgId,productId,acctLongNumber,null,projectTypeId,true);
		KDBizPromptBox myPrmtBox=new  KDBizPromptBox(){
			protected Object stringToValue(String t) {
				Object obj = super.stringToValue(t);
				if(obj!=null){
					return FDCHelper.toBigDecimal(t,2);
				}
				return obj;
			}
		};
		myPrmtBox.setSelector(selector);
		ICellEditor f7Editor = new KDTDefaultCellEditor(myPrmtBox);
		row.getCell("price").setEditor(f7Editor);
		myPrmtBox.addSelectorListener(new SelectorListener(){
			public void willShow(SelectorEvent e) {
				if(acct.getType()==CostAccountTypeEnum.SIX){
					Object v = prmtProjectType.getValue();
					if(v instanceof ProjectTypeInfo){
						KDBizPromptBox my=(KDBizPromptBox) e.getSource();
						((TemplateMeasureCostPromptBox)my.getSelector()).setProjectTypeID(((ProjectTypeInfo)v).getId().toString());
					}else{
						MsgBox.showWarning(getplTables(), "六类公摊必须先设置项目系列");
						e.setCanceled(true);
					}
				}
				
			}
		});
		//coefficient
		selector=new TemplateMeasureCostPromptBox(this,isAimMeasure,orgId,productId,acctLongNumber,indexType,projectTypeId,false);
		myPrmtBox=new  KDBizPromptBox(){
			protected Object stringToValue(String t) {
				Object obj = super.stringToValue(t);
				if(obj!=null){
					return FDCHelper.toBigDecimal(t,2);
				}
				return obj;
			}
		};
		myPrmtBox.setSelector(selector);
		f7Editor = new KDTDefaultCellEditor(myPrmtBox);
		row.getCell("coefficient").setEditor(f7Editor);
		myPrmtBox.addSelectorListener(new SelectorListener(){
			public void willShow(SelectorEvent e) {
				KDBizPromptBox my=(KDBizPromptBox) e.getSource();
				if(acct.getType()==CostAccountTypeEnum.SIX){
					Object v = prmtProjectType.getValue();
					if(v instanceof ProjectTypeInfo){
						((TemplateMeasureCostPromptBox)my.getSelector()).setProjectTypeID(((ProjectTypeInfo)v).getId().toString());
					}else{
						MsgBox.showWarning(getplTables(), "六类公摊必须先设置项目系列");
						e.setCanceled(true);
					}
				}
				if(row.getCell("indexName").getValue() instanceof Item){
					((TemplateMeasureCostPromptBox)my.getSelector()).setIndex(((Item)row.getCell("indexName").getValue()).key);
				}else{
					MsgBox.showWarning(getplTables(), "请先选择指标");
					e.setCanceled(true);
				}
			}
		});
		
	}


	public void actionDeleteRow_actionPerformed(ActionEvent arg0)
			throws Exception {
		int ii=1;
		if(this.isHasCompareEntry){
			ii=2;
		}
		if(this.plTables.getSelectedIndex()==0){
			return;
		}
		if(this.plTables.getSelectedIndex()==1){
			return;
		}
		if(this.plTables.getSelectedIndex()==ii){
			return;
		}
		if(this.plTables.getSelectedIndex()==ii+1){
			planIndexTable.deleteRow(arg0);
			return;
		}
		if(this.plTables.getSelectedIndex()==ii+2){
			planIndexTable.deleteConstrIndexRow(arg0);
			return;
		}
		
//		if(this.plTables.getSelectedIndex()==4){
//			return;
//		}
		int selectIndex = this.plTables.getSelectedIndex();
		KDTable table = (KDTable) this.tables.get(selectIndex - 2);
		KDTSelectManager selectManager = table.getSelectManager();
		if (selectManager == null || selectManager.size() == 0) {
			return;
		}
		for (int i = 0; i < selectManager.size(); i++) {
			KDTSelectBlock selectBlock = selectManager.get(i);
			for (int j = selectBlock.getTop(); j <= selectBlock.getBottom(); j++) {
				IRow selectRow = table.getRow(j);
				if (selectRow == null) {
					continue;
				}
				if (selectRow.getUserObject() instanceof MeasureEntryInfo) {
					selectRow.getCell("price").setUserObject("delete");
				}
			}
		}
		for (int i = 0; i < table.getRowCount(); i++) {
			IRow row = (IRow) table.getRow(i);
			if (row.getCell("price").getUserObject() != null) {
				if(isDetailAcctRow(row)){
					clearDetailAcctRow(table, row);
					row.getCell("price").setUserObject(null);
				}else{
					//判断是不是最后一行
					int j=row.getRowIndex()-1;
					int k=row.getRowIndex()+1;
					if(j>0){
						IRow parentRow = table.getRow(j);
						if(parentRow.getUserObject() instanceof CostAccountInfo){
							if(k==table.getRowCount()||isDetailAcctRow(table.getRow(k))
									//非明细行
									||table.getRow(k).getUserObject() instanceof CostAccountInfo){
								clearDetailAcctRow(table, parentRow);
								parentRow.getStyleAttributes().setBackground(Color.WHITE);
								parentRow.getStyleAttributes().setLocked(false);
								MeasureEntryInfo info = new MeasureEntryInfo();
								info.setCostAccount((CostAccountInfo)parentRow.getUserObject());
								parentRow.setUserObject(info);
								setTemplateMeasureCostF7Editor(table,  parentRow);
								setDetailAcctRow(parentRow);
								parentRow.getCell("acctNumber").getStyleAttributes().setLocked(true);
								parentRow.getCell("acctNumber").getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
								parentRow.getCell("acctName").getStyleAttributes().setLocked(true);
								parentRow.getCell("acctName").getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
								parentRow.getCell("amount").getStyleAttributes().setLocked(true);
								parentRow.getCell("amount").getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
								parentRow.getCell("buildPart").getStyleAttributes().setLocked(true);
								parentRow.getCell("buildPart").getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
								parentRow.getCell("sellPart").getStyleAttributes().setLocked(true);
								parentRow.getCell("sellPart").getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
								parentRow.getCell("index").getStyleAttributes().setLocked(true);
								parentRow.getCell("index").getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
							}
						}
					}
					table.removeRow(row.getRowIndex());
					i--;
				}
				table.setUserObject("delteRow");
				setUnionData(table);
			}
		}

	}

	/**
	 * 设置父科目汇总数
	 * 
	 * @param table
	 * @param amountColumns
	 */
	public void setUnionData(KDTable table) {
		String[] cols = new String[] { 
				//"price", 
				"sumPrice","amount","buildPart","sellPart","adjustAmt"
				};
		for (int i = 0; i < table.getRowCount(); i++) {
			IRow row = table.getRow(i);
			if (row.getUserObject() instanceof CostAccountInfo) {
				// 设置汇总行
				int level = row.getTreeLevel();
				List aimRowList = new ArrayList();
				for (int j = i + 1; j < table.getRowCount(); j++) {
					IRow rowAfter = table.getRow(j);
					if (rowAfter.getTreeLevel() <= level) {
						break;
					}
					if (rowAfter.getUserObject() instanceof MeasureEntryInfo) {
						aimRowList.add(rowAfter);
					}

				}
				
				for (int j = 0; j < cols.length; j++) {
					BigDecimal sum = FDCHelper.ZERO;
					/**
					 * 所有报表的所有单方不存在上下级汇总关系，而应该是各个级次的都等于各自的对应成本除以对应的面积，而非下级的单方汇总
					 * @author pengwei_hou Date: 2009-01-19 14:23:23
					 */
					if(cols[j].equals("sellPart")){
						BigDecimal amount = (BigDecimal)row.getCell("amount").getValue();
						if (amount != null) {
							Object obj=table.getHeadRow(0).getCell(0).getUserObject();
							if(obj instanceof PlanIndexEntryInfo){
								BigDecimal sellArea = ((PlanIndexEntryInfo)obj).getSellArea();
								if (sellArea != null && sellArea.compareTo(FDCHelper.ZERO) != 0) {
									BigDecimal sellPart = FDCNumberHelper.divide(amount, sellArea, 2, BigDecimal.ROUND_HALF_UP);
									sum = sellPart;
								}
							}else if(obj instanceof PlanIndexInfo){
								BigDecimal sellArea = ((PlanIndexInfo)obj).getBigDecimal("allSellArea");
								if (sellArea != null && sellArea.compareTo(FDCHelper.ZERO) != 0) {
									BigDecimal sellPart = FDCNumberHelper.divide(amount, sellArea, 2,BigDecimal.ROUND_HALF_UP);
									sum = sellPart;
								}
							}
						} 
						
					}else if(cols[j].equals("buildPart")) {
						BigDecimal amount = (BigDecimal)row.getCell("amount").getValue();
						if (amount != null) {
							Object obj=table.getHeadRow(0).getCell(0).getUserObject();
							if(obj instanceof PlanIndexEntryInfo){
								BigDecimal buildArea = ((PlanIndexEntryInfo)obj).getBuildArea();
								if (buildArea != null && buildArea.compareTo(FDCHelper.ZERO) != 0) {
									BigDecimal buildPart = FDCNumberHelper.divide(amount, buildArea, 2, BigDecimal.ROUND_HALF_UP);
									sum = buildPart;
								}
							}else if(obj instanceof PlanIndexInfo){
								BigDecimal buildArea = ((PlanIndexInfo)obj).getBigDecimal("allBuildArea");
								if (buildArea != null && buildArea.compareTo(FDCHelper.ZERO) != 0) {
									BigDecimal buildPart = FDCNumberHelper.divide(amount, buildArea, 2,BigDecimal.ROUND_HALF_UP);
									sum = buildPart;
								}
							}
						} 
					}
					else {
						
						for (int rowIndex = 0; rowIndex < aimRowList.size(); rowIndex++) {
							IRow rowAdd = (IRow) aimRowList.get(rowIndex);
							Object value = rowAdd.getCell(cols[j]).getValue();
							if (value != null) {
	//							if (sum == null) {
	//								sum = FMConstants.ZERO;
	//							}
								if (value instanceof BigDecimal) {
									sum = sum.add((BigDecimal) value);
								} else if (value instanceof Integer) {
									sum = sum.add(new BigDecimal(((Integer) value)
											.toString()));
								}
							}
						}
						
					}
					if(sum != null && sum.compareTo(FDCHelper.ZERO)==0){
						sum = null;
					}
					row.getCell(cols[j]).setValue(sum);
				}
			}
		}
		addTotalRow(table);
	}

	public void actionImportApportion_actionPerformed(ActionEvent arg0)
			throws Exception {
		super.actionImportApportion_actionPerformed(arg0);
	}
	
	//处理不完整记录 by hpw 2010.09.27
	private boolean dealWithEmptyRow(){
		String titleStr = "存在分录数据填写不完整的记录，导致调整前合价为零或者为空。 \n \n是否系统自动删除对应记录，然后保存？\n若选择是，则系统自动将信息不完整的纪录行删除，然后保存。\n选择否，则需要返回将对应记录填写完整后才能进行保存！";
		Map detailMap = new HashMap();
		
		for (int i = 0; i < tables.size(); i++) {
			KDTable table = (KDTable) tables.get(i);
			for (int j = 0; j < table.getRowCount(); j++) {
				IRow row = table.getRow(j);
				if (row.getUserObject() instanceof MeasureEntryInfo) {
					if(isDetailAcctRow(row)&&!isDetailAcctHasInput(row)){
						//未录入的不做检查判断
						continue;
					}
					BigDecimal sumPrice = FDCHelper.toBigDecimal(row.getCell("sumPrice").getValue());
					String key = table.getName();
					if(sumPrice.compareTo(FDCHelper.ZERO)==0){
						if(detailMap.containsKey(key)){
							StringBuffer detail = (StringBuffer)detailMap.get(key);
							detail.append(row.getRowIndex()+1+"、");
						}else{
							StringBuffer detail = new StringBuffer();
							detail.append(row.getRowIndex()+1+"、");
							detailMap.put(key, detail);
						}
					}
					
				}
			}
		}
		if(detailMap.size()>0){
			StringBuffer msg = new StringBuffer();
			
			for(Iterator iter=detailMap.keySet().iterator();iter.hasNext();){
				String key = (String)iter.next();
				String val =((StringBuffer)detailMap.get(key)).toString();
				
				msg.append("页签：");
				msg.append(key);
				msg.append("，");
				msg.append("分录行第 ");
				msg.append(val.substring(0, val.length()-1));
				msg.append(" 行");
				msg.append("\n");
			}
			int v = MsgBox.showConfirm3(this, titleStr, msg.toString());
			if(v==MsgBox.YES){
				for (int i = 0; i < tables.size(); i++) {
					KDTable table = (KDTable) tables.get(i);
					for (int j = 0; j < table.getRowCount(); j++) {
						IRow row = table.getRow(j);
						if (row.getUserObject() instanceof MeasureEntryInfo) {
							if(isDetailAcctRow(row)&&!isDetailAcctHasInput(row)){
								//未录入的不做检查判断
								continue;
							}
							BigDecimal sumPrice = FDCHelper.toBigDecimal(row.getCell("sumPrice").getValue());
							if(sumPrice.compareTo(FDCHelper.ZERO)==0){
								setDetailAcctHasNotInput(row);//置为无数据状态
								for(int k=3;k<table.getColumnCount()-1;k++){
									row.getCell(k).setValue(null);//清空数据
								}
							}
						}
					}
				}
			}
		}
		
		return false;
	}
	
	protected void beforeStoreFields(ActionEvent e) throws Exception {
		dealWithEmptyRow();
		if (this.txtVersionName.getText() == null
				|| this.txtVersionName.getText().length() == 0) {
			MsgBox.showInfo("版本名称不能为空!");
			this.abort();
		}
		for (int i = 0; i < tables.size(); i++) {
			KDTable table = (KDTable) tables.get(i);
			for (int j = 0; j < table.getRowCount(); j++) {
				IRow row = table.getRow(j);
				if (row.getUserObject() instanceof MeasureEntryInfo) {
					if(isDetailAcctRow(row)&&!isDetailAcctHasInput(row)){
						//未录入的不做检查判断
						continue;
					}
					int k = table.getColumnIndex("workload");
					if (row.getCell("sumPrice").getValue() != null) {
						BigDecimal value = (BigDecimal) row.getCell("workload")
								.getValue();
						if (value != null
								&& value.compareTo(FDCHelper.MAX_VALUE) > 0) {
							this.setMessageText("工作量超出最大值!");
							this.showMessage();
							this.plTables.setSelectedIndex(i+1);
							table.getSelectManager().select(0, 0);
							table.getSelectManager().select(row.getRowIndex(),
									k);
							this.abort();
						}
					}

//					k = table.getColumnIndex("sumPrice");
//					String msg="合价";
//					if(isUseAdjustCoefficient()){
////						k=table.getColumnIndex("amount");
//						msg="调整前合价";
//					}
//					//
//					if (row.getCell("sumPrice").getValue() == null) {
//						this.setMessageText(msg+"不能为空!");
//						this.showMessage();
//						this.plTables.setSelectedIndex(i+1);
//						table.getSelectManager().select(0, 0);
//						table.getSelectManager().select(row.getRowIndex(), k);
//						this.abort();
//					} else {
//						BigDecimal value = (BigDecimal) row.getCell("sumPrice")
//								.getValue();
//						if (value.compareTo(FDCHelper.ZERO) == 0) {
//							this.setMessageText(msg+"不能为0!");
//							this.showMessage();
//							this.plTables.setSelectedIndex(i+1);
//							table.getSelectManager().select(0, 0);
//							table.getSelectManager().select(row.getRowIndex(),
//									k);
//							this.abort();
//						}
//						if (value.compareTo(FDCHelper.MAX_VALUE) > 0) {
//							this.setMessageText(msg+"超出最大值!");
//							this.showMessage();
//							this.plTables.setSelectedIndex(i+1);
//							table.getSelectManager().select(0, 0);
//							table.getSelectManager().select(row.getRowIndex(),
//									k);
//							this.abort();
//						}
//					}
				}
			}
		}
	}
	//add by david_yang R110411-507 2011.04.18
	public void importTemplateMeasureIncome() throws EASBizException, BOSException{
		if(miInfo!=null){
    		String measureIncomeId = getMeasureIncomeId(this.editData.getId().toString());
    		if(measureIncomeId!=null){
    			MeasureIncomeInfo measureIncomeinfo=MeasureIncomeFactory.getRemoteInstance().getMeasureIncomeInfo(new ObjectUuidPK(measureIncomeId));
    			measureIncomeinfo.getIncomeEntry().clear();
    			for(int i=0;i<miInfo.getIncomeEntry().size();i++){
    				MeasureIncomeEntryInfo entry=(MeasureIncomeEntryInfo)miInfo.getIncomeEntry().get(i).clone();
    				entry.setHead(measureIncomeinfo);
    				entry.setId(null);
    				if(!measureIncomeinfo.getProject().equals(miInfo.getProject())){
    					String codingnumber=IncomeAccountFactory.getRemoteInstance().getIncomeAccountInfo(new ObjectUuidPK(miInfo.getIncomeEntry().get(i).getIncomeAccount().getId())).getCodingNumber();
    					IncomeAccountInfo account=IncomeAccountFactory.getRemoteInstance().getIncomeAccountInfo("select * from where codingnumber='"+codingnumber+"' and curProject='"+measureIncomeinfo.getProject().getId()+"'");
    					entry.setIncomeAccount(account);
    				}
    				measureIncomeinfo.getIncomeEntry().add(entry);
    			}
    			MeasureIncomeFactory.getRemoteInstance().save(measureIncomeinfo);
    		}
		}
	}
	/**
	 * 系统原来的设计是将btnSave看做"暂存", btnSubmit看做"保存"，并不存在"提交"。现客户要求能够在工作流中审批，故必须提供"提交"功能。
	 * 故将actionSubmit_actionPerformed()方法中的代码迁移到actionSave_actionPerformed()中.actionSubmit_actionPerformed()中则为
	 * 提交时的逻辑  by Cassiel_peng   2009-08-18
	 */
	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		this.setOprtState("EDIT");
		/*		if(editData.getId()==null){
					editData.setId(BOSUuid.create(editData.getBOSType()));
				}*/
				editData.put("PlanIndex",planIndexTable.getPlanIndexInfo());
				editData.put("constrEntrys",planIndexTable.getConstrEntrys());
		    	Boolean isEditVersion=(Boolean)getUIContext().get("isEditVersion");
		    	if(isEditVersion!=null&&isEditVersion.booleanValue()){
		    		handleVersion((MeasureCostInfo)editData);
		    	}
				//检查是否可以保存
				if(editData.getId()!=null){
					FilterInfo filter=new FilterInfo();
					filter.appendFilterItem("id",editData.getId().toString());
					filter.appendFilterItem("state", FDCBillStateEnum.AUDITTED_VALUE);
					if(MeasureCostFactory.getRemoteInstance().exists(filter)){
						FDCMsgBox.showWarning(this, "测算已审批不能进行此操作");
						SysUtil.abort();
					}
				}
				confirmVersionOnly();
				
//				super.actionSubmit_actionPerformed(e);
		    	super.actionSave_actionPerformed(e);
//				planIndexTable.save(editData.getId().toString());	    	
		    	
		    	//add by david_yang R110411-507 2011.04.18
		    	importTemplateMeasureIncome();
		    	
		    	
				this.storeFields();
				this.initOldData(this.editData);
				actionImportTemplate.setEnabled(false);
				
				getUIContext().put("isEditVersion",null);
				
				setDataChange(false);
			}
	
	/**
	 * 在保存和提交的时候需要再次确认版本的唯一性  by Cassiel_peng 2009-8-19
	 * 版本号校验，排除当前数据，增加阶段,项目 by hpw
	 * @throws BOSException 
	 */
	public void confirmVersionOnly() throws BOSException{
		
		Boolean isAimMeasure = (Boolean)getUIContext().get("isAimMeasure");
		String  versionNum = this.txtVersionNumber.getText();
		MeasureCostInfo info=(MeasureCostInfo)this.editData;
		CurProjectInfo prj = (CurProjectInfo)this.prmtProject.getValue();
		//显示.数据库中!
		if(versionNum.indexOf('!')==-1){
			versionNum = versionNum.replace('.', '!');
		}
		MeasureStageInfo stage =(MeasureStageInfo)comboMeasureStage.getSelectedItem();
		if(isAimMeasure.booleanValue())
		{
			String selectOrgId = getUIContext().get("orgId").toString();
			FDCSQLBuilder builder=new FDCSQLBuilder();
			builder.appendSql("select REPLACE(FVersionNumber, '!', '.') as FVersionNumber from T_AIM_MeasureCost where FOrgUnitID = ? ");
			builder.addParam(selectOrgId);
			if(prj!=null&&prj.getId()!=null){
				builder.appendSql(" and FProjectID = ? ");
				builder.addParam(prj.getId().toString()) ;
			}
			builder.appendSql(" and FIsAimMeasure = ? ");
			builder.addParam(isAimMeasure) ;
			builder.appendSql(" and FVersionNumber = ? ");
			builder.addParam(versionNum) ;
			//排除当前
			if(info!=null&&info.getId()!=null){
				builder.appendSql(" and FID <> ? ");
				builder.addParam(info.getId().toString());
			}
			//阶段
			if(stage!=null&&stage.getId()!=null){
				builder.appendSql(" and FMeasureStageID =? ");
				builder.addParam(stage.getId().toString());
			}
			IRowSet row = builder.executeQuery();
			if(row.size() != 0)
			{
				FDCMsgBox.showWarning(this, "该版本号已经存在");
				SysUtil.abort();
			}
		}
		else
		{
			String selectOrgId = getUIContext().get("orgId").toString();
			
			FDCSQLBuilder builder=new FDCSQLBuilder();
			builder.appendSql("select REPLACE(FVersionNumber, '!', '.') as FVersionNumber from T_AIM_MeasureCost where FOrgUnitID = ? ");
			builder.addParam(selectOrgId);
			if(prj!=null&&prj.getId()!=null){
				builder.appendSql(" and FProjectID = ? ");
				builder.addParam(prj.getId().toString()) ;
			}
			builder.appendSql(" and FIsAimMeasure = ? ");
			builder.addParam(isAimMeasure) ;
			builder.appendSql(" and FVersionNumber = ? ");
			builder.addParam(versionNum) ;
			if(info!=null&&info.getId()!=null){
				builder.appendSql(" and FID <> ? ");
				builder.addParam(info.getId().toString());
			}
			if(stage!=null&&stage.getId()!=null){
				builder.appendSql(" and FMeasureStageID =? ");
				builder.addParam(stage.getId().toString());
			}
			IRowSet row = builder.executeQuery() ;
			if(row.size() !=0)
			{
				FDCMsgBox.showWarning(this, "该版本号已经存在");
				SysUtil.abort();
			}
		}

	}
	
	protected boolean isModifySave() {
		return isModify();
	}
	
	
	/**
	 * 原来系统中这个方法中处理的是"保存"单据逻辑，现需要提供"提交"功能故已将该方法原有逻辑迁移至actionSave_actionPerformed()方法中
	 * by Cassiel_peng   2009-08-18
	 */
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		this.setOprtState("EDIT");
/*		if(editData.getId()==null){
			editData.setId(BOSUuid.create(editData.getBOSType()));
		}*/
		editData.put("PlanIndex",planIndexTable.getPlanIndexInfo());
		editData.put("constrEntrys",planIndexTable.getConstrEntrys());
    	Boolean isEditVersion=(Boolean)getUIContext().get("isEditVersion");
    	if(isEditVersion!=null&&isEditVersion.booleanValue()){
    		handleVersion((MeasureCostInfo)editData);
    	}
		//检查是否可以保存
		if(editData.getId()!=null){
			FilterInfo filter=new FilterInfo();
			filter.appendFilterItem("id",editData.getId().toString());
			filter.appendFilterItem("state", FDCBillStateEnum.AUDITTED_VALUE);
			if(MeasureCostFactory.getRemoteInstance().exists(filter)){
				FDCMsgBox.showWarning(this, "测算已审批不能进行此操作");
				SysUtil.abort();
			}
		}
		confirmVersionOnly();
		
		if(isHasCompareEntry){
			Map verAllCompare=new HashMap();
			compare(new MeasureCostInfo(),verAllCompare);
			
			Object[] key = compareTables.keySet().toArray(); 
			for (int k = 0; k < key.length; k++) {
				String productKey=key[k].toString();
				if("汇总".equals(productKey))continue;
				KDTable table=(KDTable) compareTables.get(productKey);
				for(int i=0;i<table.getRowCount();i++){
					String costAccount=(String)table.getRow(i).getCell("costAccount").getValue();
					String content=(String)table.getRow(i).getCell("content").getValue();
					String reson=(String)table.getRow(i).getCell("reason").getValue();
					if(reson == null||"".equals(reson.trim())){
						FDCMsgBox.showWarning(this,productKey+" 第"+(i+1)+"行调整原因不能为空！");
						SysUtil.abort();
					}
					if(verAllCompare.containsKey(productKey+costAccount)){
						if(!content.equals(verAllCompare.get(productKey+costAccount).toString())){
							FDCMsgBox.showWarning(this,"请先进行调整原因提取操作！");
							SysUtil.abort();
						}else{
							verAllCompare.remove(productKey+costAccount);
						}
					}
				}
			}
			if(verAllCompare.size()>0){
				FDCMsgBox.showWarning(this,"请先进行调整原因提取操作！");
				SysUtil.abort();
			}
		}
	
		
		super.actionSubmit_actionPerformed(e);
		//提交了之后要将"导入模板"灰掉 ，只有在新增单据的时候允许使用"导入模板"   by Cassiel_peng   2009-8-19
		
		//add by david_yang R110411-507 2011.04.18
    	importTemplateMeasureIncome();
    	
		actionImportTemplate.setEnabled(false);
		this.storeFields();
		this.initOldData(this.editData);
		actionImportTemplate.setEnabled(false);
		
		getUIContext().put("isEditVersion",null);
		
		setDataChange(false);
	}

	/**
	 * @see com.kingdee.eas.framework.client.EditUI
	 * @see com.kingdee.eas.framework.client.CoreBillEditUI
	 * 由于该UI直接继承的框架的EditUI,没有继承CoreBillEditUI或者是FDCBillEditUI(这两个类里有重写以下两个方法)
	 * 框架中的保存和提交成功的时候提示信息不准确,故自己再重写    by Cassiel_peng  2009-8-19
	 */
	protected void showSaveSuccess() {
		setMessageText(getClassAlise()
				+ " "
				+ EASResource.getString(FrameWorkClientUtils.strResource
						+ "Msg_Save_OK"));
		setNextMessageText(getClassAlise()
				+ " "
				+ EASResource.getString(FrameWorkClientUtils.strResource
						+ "Msg_Edit"));
		setShowMessagePolicy(SHOW_MESSAGE_DEFAULT);
		setIsShowTextOnly(false);
		showMessage();
	}
	protected void showSubmitSuccess() {
		setMessageText(getClassAlise()
				+ " "
				+ EASResource.getString(FrameWorkClientUtils.strResource
						+ "Msg_Submit_OK"));
		if (this.chkMenuItemSubmitAndAddNew.isSelected()) {
			setNextMessageText(getClassAlise()
					+ " "
					+ EASResource.getString(FrameWorkClientUtils.strResource
							+ "Msg_AddNew"));
		} else if (!this.chkMenuItemSubmitAndPrint.isSelected()
				&& this.chkMenuItemSubmitAndAddNew.isSelected()) {
			setNextMessageText(getClassAlise()
					+ " "
					+ EASResource.getString(FrameWorkClientUtils.strResource
							+ "Msg_Edit"));
		}
		setIsShowTextOnly(false);
		setShowMessagePolicy(SHOW_MESSAGE_DEFAULT);
		showMessage();
	}

	public void actionPrint_actionPerformed(ActionEvent e) throws Exception {
		int i=1;
		if(this.isHasCompareEntry){
			i=2;
		}
		if(this.plTables.getSelectedIndex()-i>=0){
			KDTable table = (KDTable) this.tables.get(this.plTables.getSelectedIndex()-i);
			table.getPrintManager().print();
		}
	}

	/**
	 * refresh measure table 2-X
	 */
	private void refreshAllMeasureTable(){
		for(int i=3;i<tables.size();i++){
			KDTable table=(KDTable)tables.get(i);
			refreshMeasureTable(table);
		}
	}
	
	private void refreshMeasureTable(KDTable table){
		if(table.getHeadRow(0).getUserObject() instanceof ProductTypeInfo){
			ProductTypeInfo product=(ProductTypeInfo)table.getHeadRow(0).getUserObject();
			table.getHeadRow(0).getCell(0).setUserObject(
					planIndexTable.getPlanIndexEntryInfo(product.getId().toString()));
			ICellEditor editor=getIndexEditor(CostAccountTypeEnum.MAIN,product.getId().toString());
			table.getColumn("indexName").setEditor(editor);
		}else{
			PlanIndexInfo planIndexInfo = planIndexTable.getPlanIndexInfo();
			ICellEditor editor=getIndexEditor(CostAccountTypeEnum.SIX,null);
			table.getColumn("indexName").setEditor(editor);
			BigDecimal amount=planIndexTable.getAllSellArea(planIndexInfo);
			planIndexInfo.put("allSellArea", amount);
			amount=planIndexTable.getAllBuildArea(planIndexInfo);
			planIndexInfo.put("allBuildArea", amount);
			table.getHeadRow(0).getCell(0).setUserObject(planIndexInfo);
		}
		
		for (int j = 0; j < table.getRowCount(); j++) {
			IRow row = table.getRow(j);
			if (row.getUserObject() instanceof MeasureEntryInfo) {
				try {
					table_editStopped(table, new KDTEditEvent(table,null,null,j,table.getColumnIndex("indexName"),false,1));
//					table_editStopped(table, new KDTEditEvent(table,null,null,j,table.getColumnIndex("sumPrice"),false,1));
				} catch (Exception e) {
					handUIException(e);
				}
			}
		}
		
	}
	public void actionPrintPreview_actionPerformed(ActionEvent e)throws Exception {
		int i=1;
		if(this.isHasCompareEntry){
			i=2;
		}
		if(this.plTables.getSelectedIndex()-i>=0){
			KDTable table = (KDTable) this.tables.get(this.plTables.getSelectedIndex()-i);
			table.getPrintManager().printPreview();
		}
	}
	
	/**
	 * get new data from six or main table to measureCostMap
	 */
	void refreshMeasureCostMap(){
		storeFields();
		MeasureCostInfo cost = (MeasureCostInfo) this.editData;
//		this.txtProject.setText(cost.getProject().getName());
//		this.txtVersionNumber.setText(cost.getVersionNumber());
//		this.txtVersionName.setText(cost.getVersionName());
		measureCostMap.clear();
		MeasureEntryCollection costEntrys = cost.getCostEntry();
		for (int i = 0; i < costEntrys.size(); i++) {
			MeasureEntryInfo info = costEntrys.get(i);
			CostAccountInfo costAccount = info.getCostAccount();
			String key = costAccount.getId().toString();
			if (info.getProduct() != null) {
				key += info.getProduct().getId().toString();
			}
			if (measureCostMap.containsKey(key)) {
				MeasureEntryCollection coll = (MeasureEntryCollection) measureCostMap
						.get(key);
				coll.add(info);
			} else {
				MeasureEntryCollection newColl = new MeasureEntryCollection();
				newColl.add(info);
				measureCostMap.put(key, newColl);
			}
		}
	}
	TreeModel getCostAcctTree(){
		return costAcctTree;
	}
	Map getMeasureCostMap() {
		return measureCostMap;
	}
	PlanIndexTable getPlanIndexTable() {
		return planIndexTable;
	}
	
	KDTabbedPane getplTables(){
		return plTables;
	}
	
	List getTables(){
		return tables;
	}
	
	/**
	 * 汇总表产品分摊使用指标+自定义指标
	 * @return
	 */
	KDTDefaultCellEditor getCollectIndexEditor(){
		if(isMeasureIndex()){
			return getCollectMeasureIndexEditor();
		}
		Object []items=getCollectItems();
		KDComboBox box=new KDComboBox(items);
		return 	new KDTDefaultCellEditor(box);
	}
	/**
	 * 汇总表产品分摊使用指标+自定义指标
	 * @return
	 */
	KDTDefaultCellEditor getCollectMeasureIndexEditor(){
		Object []items=null;
		if(nameSet!=null){
			Object []nameItems=new Object[nameSet.size()];
			int h =0;
			for(int i=0;i<getCollectItems().length;i++){
				String name = (String)getCollectItems()[i].toString();
				if(nameSet.contains(name)){
					nameItems[h]=getCollectItems()[i];
					h++;
				}
			}
			items = nameItems;
			
		}
		KDComboBox box=new KDComboBox(items);
		return 	new KDTDefaultCellEditor(box);
	}
	
	KDTDefaultCellEditor getIndexEditor(CostAccountTypeEnum type,String productId){
		Object []items=null;
		if(type==CostAccountTypeEnum.SIX){
			items=getSixItems();
		}else if(type==CostAccountTypeEnum.MAIN){
			items=getMainItems();
		}else{
			return null;
		}
		KDComboBox box=new KDComboBox(items);
		return 	new KDTDefaultCellEditor(box);
	}
	private Set nameSet = null;
	private void initMeasureIndex() throws BOSException{
		nameSet = new HashSet();
		if(isMeasureIndex()){
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			view.setFilter(filter);
			filter.getFilterItems().add(new FilterItemInfo("type",ApportionTypeEnum.STANDARD_VALUE));
			filter.getFilterItems().add(new FilterItemInfo("isEnabled",Boolean.TRUE));
			view.getSelector().add("name");
			MeasureIndexCollection indexNames = MeasureIndexFactory.getRemoteInstance().getMeasureIndexCollection(view);
			if(indexNames!=null){
				for(int i=0;i<indexNames.size();i++){
					nameSet.add(indexNames.get(i).getName());
				}
			}
		}
	}
	private Object[] COLLECTITEMS=null;
	private Object[] getCollectItems(){
		
		COLLECTITEMS=null;
		if(COLLECTITEMS==null){
			CustomPlanIndexEntryCollection customPlanIndexs = planIndexTable.getCustomPlanIndexs("productId");
			if(customPlanIndexs.size()==0){
				COLLECTITEMS=Item.SPLITITEMS;
			}else{
				List list=new ArrayList();
				Set appSet=new HashSet();
				for(int i=0;i<customPlanIndexs.size();i++){
					String appId = customPlanIndexs.get(i).getApportType().getId().toString();
					if(!appSet.contains(appId)){
						appSet.add(appId);
						list.add(Item.getCustomItem(customPlanIndexs.get(i).getApportType()));
					}
				}
				COLLECTITEMS=new Item[list.size()+Item.SPLITITEMS.length];
				System.arraycopy(Item.SPLITITEMS, 0, COLLECTITEMS, 0, Item.SPLITITEMS.length);
				int i=Item.SPLITITEMS.length;
				for(Iterator iter=list.iterator();iter.hasNext();){
					COLLECTITEMS[i++]=iter.next();
				}
			}
		}
		return COLLECTITEMS;
	}
	
	private Object[] MAINITEMS=null;
	private Object[] getMainItems(){
		MAINITEMS=null;
		if(MAINITEMS==null){
			CustomPlanIndexEntryCollection customPlanIndexs = planIndexTable.getCustomPlanIndexs("productId");
			if(customPlanIndexs.size()==0){
				MAINITEMS=Item.PRODUCTITEMS;
			}else{
				List list=new ArrayList();
				Set appSet=new HashSet();
				for(int i=0;i<customPlanIndexs.size();i++){
					String appId = customPlanIndexs.get(i).getApportType().getId().toString();
					if(!appSet.contains(appId)){
						appSet.add(appId);
						list.add(Item.getCustomItem(customPlanIndexs.get(i).getApportType()));
					}
				}
				MAINITEMS=new Item[list.size()+Item.PRODUCTITEMS.length];
				System.arraycopy(Item.PRODUCTITEMS, 0, MAINITEMS, 0, Item.PRODUCTITEMS.length);
				int i=Item.PRODUCTITEMS.length;
				for(Iterator iter=list.iterator();iter.hasNext();){
					MAINITEMS[i++]=iter.next();
				}
			}
		}
		return MAINITEMS;
	}
	
	public static class Item{
		String key=null;
		String name=null;
		String productId=null;
		BigDecimal sellArea=null;
		boolean isCustom=false;
		Item(String key,String name) {
			this.key=key;
			this.name=name;
		}
		private Item(ApportionTypeInfo info) {
			this.key=info.getId().toString();
			this.name=info.getName();
			isCustom=true;
		}
		private static Map hashMap=null;
		public static Item getCustomItem(ApportionTypeInfo info){
			if(hashMap==null){
				hashMap=new HashMap();
			}
			Item item=(Item)hashMap.get(info.getId());
			if(item==null){
				item=new Item(info);
			}
			hashMap.put(info.getId(), item);
			return item;
			
		}
		public boolean isCustomIndex(){
			return isCustom;
		}
		public String toString() {
			return name;
		}
		
		public int hashCode() {
			return super.hashCode();
		}
		
		/**
		 * 六类公摊使用指标
		 * TODO 后续需要优化
		 */
		public static Item [] SIXITEMS=new Item[]{
			new Item("empty",	""),	
			new Item("totalContainArea",	"总占地面积"),	
//			new Item("buildArea",	"建筑用地面积"),	
			new Item("totalBuildArea",	"总建筑面积"),
			new Item("totalSellArea",	"总可售面积"),	
			new Item("groundBuildArea",	"总地上建筑面积"),	
			new Item("underGroundBuildArea","总地下建筑面积"),	
			new Item("decorationArea","精装修面积"),	
			new Item("lengthOfWall","小区围墙长度"),	
			new Item("roadArea","小区道路面积"),	
//			new Item("buildContainArea",	"建筑物占地面积"),
//			new Item("buildDensity",	"建筑密度	"),
//			new Item("greenAreaRate",	"绿地率"),	
//			new Item("cubageRateArea",	"计容积率面积"),	
//			new Item("cubageRate",	"容积率"),	
//			new Item("totalRoadArea",	"道路用地合计"),	
//			new Item("totalGreenArea",	"绿化用地合计	"),
//			new Item("pitchRoad",	"沥青路面车行道"),
//			new Item("concreteRoad",	"砼路面车行道（停车场）"),	
//			new Item("hardRoad",	"硬质铺装车行道"),
//			new Item("hardSquare",	"硬质铺装广场	"),
//			new Item("hardManRoad",	"硬质铺装人行道"),
//			new Item("importPubGreenArea",	"重要公共绿地	"),
//			new Item("houseGreenArea",	"组团宅间绿化	"),
//			new Item("privateGarden",	"底层私家花园	"),
//			new Item("warterViewArea",	"水景面积"),
			new Item("sightSpotArea",	"景观面积"),
			new Item("basementArea",	"地下室面积"),
			new Item("entityIndex",	"实体指标")
//			new Item("doors",	"户数")
			
		};
		
		/**
		 * 产品使用指标
		 */
		public static Item [] PRODUCTITEMS=new Item[]{
				new Item("empty",	""),
				new Item("containArea",	"产品总占地面积"),	
				new Item("buildArea",	"产品总建筑面积"),	
				new Item("sellArea",	"产品总可售面积"),	
				new Item("groundArea",	"产品总地上建筑面积"),	
				new Item("underGroundArea",	"产品总地下建筑面积"),
				new Item("entityIndex",	"实体指标")
//				new Item("cubageRate",	"容积率"),	
//				new Item("productRate",	"产品比例"),	
//				new Item("unitArea",	"平均每户面积"),	
//				new Item("units",	"单元数"),	
//				new Item("doors",	"户数"),
		};
		
		/**
		 * 汇总表产品分摊使用指标
		 */
		public static Item [] SPLITITEMS = new Item[] { 
				new Item("man", "指定分摊"), 
				new Item("buildArea", "建筑面积"), 
				new Item("sellArea", "可售面积"),
				new Item("containArea", "占地面积"),
				new Item("cubageRate", "容积率"), 
				new Item("productRate", "产品比例"), 
				new Item("unitArea", "平均每户面积"), 
				new Item("units", "单元数"),
				new Item("doors", "户数	"),

		};

	}
	
	protected void verifyInput(ActionEvent e) throws Exception {
		FDCClientVerifyHelper.verifyRequire(this);
		if(comboMeasureStage.getSelectedItem()==null){
			FDCMsgBox.showWarning(this,"测算阶段不能为空");
			this.abort();
		}
		super.verifyInput(e);
		
	}
	
	public void actionImportTemplate_actionPerformed(ActionEvent e)
			throws Exception {
		TimeTools.getInstance().setDebug(true);
		TimeTools.getInstance().reset();
		TimeTools.getInstance().msValuePrintln("start");
		Map context = new HashMap();
		String orgId=(String)getUIContext().get("orgId");
		context.put("orgUnit.id", orgId);
		context.put(UIContext.OWNER, this);
		context.put("isAimMeasure", getUIContext().get("isAimMeasure"));
		IUIFactory uiFactory = null;
		try {
			uiFactory = UIFactory.createUIFactory("com.kingdee.eas.base.uiframe.client.UIModelDialogFactory");
			IUIWindow  classDlg = uiFactory.create("com.kingdee.eas.fdc.aimcost.client.TemplateMeasureCostListUI", context,null,null,WinStyle.SHOW_KINGDEELOGO);
			classDlg.show();
			TemplateMeasureCostListUI ui=(TemplateMeasureCostListUI)classDlg.getUIObject();
			if(!ui.isCancel()){
				TemplateMeasureCostCollection data = ui.getData();
//				String id=(this.editData!=null&&this.editData.getId()!=null)?this.editData.getId().toString():null;
				if(data!=null&&data.size()>0){
					String versionName=this.txtVersionName.getText();
					String versionNumber=this.txtVersionNumber.getText();
					Object versionType = this.comboVersionType.getSelectedItem();
					Object projectType=prmtProjectType.getData();
					Object project=prmtProject.getData();
					TimeTools.getInstance().msValuePrintln("start storeFromTemplate");
					//如果选择的是工程项目则使用工程项目
					String objectId=orgId;
					if(project!=null){
						objectId=((CurProjectInfo)project).getId().toString();
					}
				 
					MeasureCostInfo editData2=MeasureCostFactory.getRemoteInstance().getMeasureFromTemplate(data.get(0).getId().toString(),objectId);
					editData2.setIsAimMeasure(isAimMeasure());
					editData2.setCreateTime(null);
					editData2.setCreator(null);
					//组织
					FullOrgUnitInfo org = new FullOrgUnitInfo();
					org.setId(BOSUuid.read(orgId));
					editData2.setOrgUnit(org);
					//跨项目
					editData2.setProject((CurProjectInfo)project);
					
					MeasureCostVersionHandler version = new MeasureCostVersionHandler(
							orgId,objectId, editData2.isIsAimMeasure(),editData2.getMeasureStage());
					versionNumber =MeasureCostVersionHandler
					.getNextVersion(version.getLastVersion());
					
					editData2.setVersionNumber(versionNumber);
					
					editData.putAll(editData2);
/*					if(id!=null){
						this.editData.setId(BOSUuid.read(id));
					}*/
//					setDataObject(editData);
					TimeTools.getInstance().msValuePrintln("end storeFromTemplate");
					isFirstLoad=true;
					loadFields();
					txtVersionName.setText(versionName);
					//版本类型
					comboVersionType.setSelectedItem(versionType);
					if(!(editData2.getVersionNumber()== null ))
					{
						this.txtVersionNumber.setText(editData2.getVersionNumber().replaceAll("!", "\\."));
					}
					if(projectType!=null){
						prmtProjectType.setData(projectType);
					}
					
					if(project!=null){
						prmtProject.setData(project);
					}
					
					//add by david_yang R110411-507 2011.04.18
					MeasureCostInfo mcinfo=(MeasureCostInfo) MeasureCostFactory.getRemoteInstance().getCollection("select * from where versionNumber='"+data.get(0).getVersionNumber()+"' and versionName='"+data.get(0).getVersionName()+"' and project='"+data.get(0).getProject().getId()+"'").get(0);
					String measureIncomeId = getMeasureIncomeId(mcinfo.getId().toString());
					if(measureIncomeId!=null){
						miInfo =MeasureIncomeFactory.getRemoteInstance().getMeasureIncomeInfo(new ObjectUuidPK(measureIncomeId));
					}
				}
				
				setDataChange(true);
				
			
				
			}
			TimeTools.getInstance().msValuePrintln("end");
			
		} catch (BOSException ex) {
			ExceptionHandler.handle(this, ex);
			return;
		}
	}
	
	public void actionExitCurrent_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionExitCurrent_actionPerformed(e);
	}
	
	protected void comboMeasureStage_itemStateChanged(ItemEvent e) throws Exception {
		super.comboMeasureStage_itemStateChanged(e);
		MeasureStageInfo stageInfo = (MeasureStageInfo)comboMeasureStage.getSelectedItem();
		if(stageInfo==null || project==null){
			return;
		}
		if(STATUS_ADDNEW.equals(getOprtState()) || STATUS_EDIT.equals(getOprtState())){
			String msg=AimCostClientHelper.getRes("addNew");
			Boolean isAimMeasure=(Boolean)getUIContext().get("isAimMeasure");
			MeasureStageInfo lastStageInfo = AimCostClientHelper.getLastMeasureStage(project,isAimMeasure.booleanValue());
			if (lastStageInfo != null && FDCHelper.subtract(lastStageInfo.getNumber(), stageInfo.getNumber()).compareTo(FDCHelper.ZERO) == 1) {
				comboMeasureStage.setSelectedItem(null);
				StringBuffer sb = new StringBuffer();
				sb.append("已存在 [ ").append(lastStageInfo.getNumber()).append(lastStageInfo.getName()).append(" ] 最终版本的目标成本测算,不能").append(msg).append(" [ ").append(stageInfo.getNumber()).append(
						stageInfo.getName()).append(" ] 目标成本测算。");
				FDCMsgBox.showWarning(sb.toString());
				SysUtil.abort();
			}
			/**
			 * 目标成本测算新增时，根据目标成本最终版本对应的测算阶段及版本号，生成同一测算阶段的新的版本的目标成本测算，并且允许修改测算阶段为后一阶段，同时变更相应的版本。
			 */
			MeasureCostInfo info = ((MeasureCostInfo)editData);
			MeasureCostVersionHandler version = new MeasureCostVersionHandler(
					info.getOrgUnit().getId().toString(),info.getProject().getId().toString(), isAimMeasure.booleanValue(),stageInfo);
			
			String versionNumber = version.getLastVersion();
			if(versionNumber.indexOf('!')==-1){
				versionNumber = versionNumber.replace('.', '!');
			}
			txtVersionNumber.setText(MeasureCostVersionHandler.getNextVersion(versionNumber).replaceAll("!", "\\."));
		}
		
	}
	
    public void actionExportAllToExcel_actionPerformed(ActionEvent e) throws Exception
    {
    	
        ExportManager exportM = new ExportManager();
        String path = null;
        File tempFile = File.createTempFile("eastemp",".xls");
        path = tempFile.getCanonicalPath();



        KDTables2KDSBookVO[] tablesVO = new KDTables2KDSBookVO[tables.size()];
        for(int i =0;i<tables.size();i++)
        {
            tablesVO[i] = new KDTables2KDSBookVO((KDTable)tables.get(i));
            String title = plTables.getTitleAt(i+1);
            title=title.replaceAll("[{\\\\}{\\*}{\\?}{\\[}{\\]}{\\/}]", "|");
			tablesVO[i].setTableName(title);
        }
        KDSBook book = null;
        book = KDTables2KDSBook.getInstance().exportKDTablesToKDSBook(tablesVO,true,true);

        exportM.exportToExcel(book, path);
        
		// 尝试用excel打开
		try
		{
			KDTMenuManager.openFileInExcel(path);
			tempFile.deleteOnExit();
		}
		catch (IOException e2)
		{
			// excel打开失败，保存到指定位置
			KDFileChooser fileChooser = new KDFileChooser();
			int result = fileChooser.showSaveDialog(this);
			if (result == KDFileChooser.APPROVE_OPTION)
			{
				// File dest = this.getFileChooser().getSelectedFile();
				File dest = fileChooser.getSelectedFile();
				try
				{
					// 重命名临时文件到指定目标
					File src = new File(path);
					if (dest.exists())
						dest.delete();
					src.renameTo(dest);
				}
				catch (Exception e3)
				{
					handUIException(e3);
				}
			}
		}

    }
    
    public void onShow() throws Exception {
        
    	this.setQueryPreference(false);
    	super.onShow();
		FDCUIWeightWorker.getInstance().addWork(new IFDCWork(){
			public void run(){
				refreshAllMeasureTable();
				setDataChange(false);
			}
		});
        if(!getOprtState().equals(OprtState.ADDNEW) && !getOprtState().equals(OprtState.EDIT)){
        	for(int i=0;i<tables.size();i++){
//        		if(i==2) continue;
        		((KDTable)tables.get(i)).getStyleAttributes().setLocked(true);
        		if(i>2){
        			ICellEditor editor =((KDTable)tables.get(i)).getColumn("workload").getEditor();
        			if(editor!=null&&editor.getComponent()!=null){
        				editor.getComponent().setEnabled(false);
        			}
        			editor =((KDTable)tables.get(i)).getColumn("sumPrice").getEditor();
        			if(editor!=null&&editor.getComponent()!=null){
        				editor.getComponent().setEnabled(false);
        			}
        		}
        	}
//        	actionAddRow.setVisible(false);
        	actionAddRow.setEnabled(false);
//        	actionDeleteRow.setVisible(false);
        	actionDeleteRow.setEnabled(false);
        	
        	measureDes.setEnabled(false);
        	this.actionImportConstructTable.setEnabled(false);
        	this.actionCompare.setEnabled(false);
        }
        //工程项目不让选择，必须新增的时候带过来，否者请重新生成
        this.prmtProject.setEnabled(false);
        /**
         * by Cassiel_peng 2009-8-18
         */
        /*this.btnSave.setIcon(EASResource.getIcon("imgTbtn_save"));
        this.btnSubmit.setIcon(EASResource.getIcon("imgTbtn_submit"));*/
//        this.btnSave.setVisible(true);
//        this.btnSave.setEnabled(true);
//        this.menuItemSave.setVisible(true);
//        this.menuItemSave.setEnabled(true);
//        this.menuItemSubmit.setVisible(true);
//        this.menuItemSubmit.setEnabled(true);
    }
    
    /**
     * 修订处理
     */
    private void handleVersion(MeasureCostInfo info){
    	if(info.getId()==null){//第二次进来info为修订版本，返回
    		return;
    	}
    	//设置源版本，如果对修订的版本进行修订则源版本与之同，否则为源版本
    	if(info.getSourceBillId()==null){
    		info.setSourceBillId(info.getId().toString());
    	}
    	info.setSrcMeasureCostId(info.getId().toString());
    	info.setId(null);
    	info.setIsLastVersion(false);
    	info.setState(FDCBillStateEnum.SAVED);
    	UserInfo user = SysContext.getSysContext().getCurrentUserInfo();
    	Timestamp timeStamp = null;
		try {
			timeStamp = FDCCommonServerHelper.getServerTimeStamp();
		} catch (BOSException e) {
			handUIException(e);
		}
		info.setCreateTime(timeStamp);
		info.setLastUpdateTime(timeStamp);
    	info.setCreator(user);
    	info.setLastUpdateUser(user);
    	for(Iterator iter=info.getCostEntry().iterator();iter.hasNext();){
    		((MeasureEntryInfo)iter.next()).setId(null);
    	}
    	for(Iterator iter=info.getConstrEntrys().iterator();iter.hasNext();){
    		((ConstructPlanIndexEntryInfo)iter.next()).setId(null);
    	}
    	PlanIndexInfo planIndex=(PlanIndexInfo)editData.get("PlanIndex");
    	if(planIndex==null) return;
    	planIndex.setId(null);
    	for(Iterator iter=planIndex.getEntrys().iterator();iter.hasNext();){
    		((PlanIndexEntryInfo)iter.next()).setId(null);
    	}
    	for(Iterator iter=planIndex.getCustomEntrys().iterator();iter.hasNext();){
    		((CustomPlanIndexEntryInfo)iter.next()).setId(null);
    	}
    }
    
	private boolean hasChanged=false;
    void setDataChange(boolean hasChange){
    	hasChanged=hasChange;
    }
    
    public boolean isModify() {
        //查看状态不判断是否修改
        if (OprtState.VIEW.equals(getOprtState())) {
            return false;
        }
    	return hasChanged;
    }
    
    private FDCBillInfo getFDCBillInfo() {
		return (FDCBillInfo) editData;
	}
    

    /**
     * 得到初始的规划指标表
     * @throws BOSException 
     */
    private PlanIndexInfo getInitPlanIndexInfo() throws BOSException{
    	if(editData==null){
    		return null;
    	}
    	if(editData.get("PlanIndex")==null&&editData.getId()!=null){
    		EntityViewInfo view=new EntityViewInfo();
    		FilterInfo filter=new FilterInfo();
    		view.setFilter(filter);
    		filter.appendFilterItem("headID", editData.getId().toString());
    		
    		SelectorItemCollection selector=view.getSelector();
    		selector.add("*");
    		selector.add("entrys.*");
    		selector.add("entrys.product.*");
    		selector.add("customEntrys.*");
    		selector.add("customEntrys.productType.id");
    		selector.add("customEntrys.productType.number");
    		selector.add("customEntrys.productType.name");
    		selector.add("customEntrys.apportType.id");
    		selector.add("customEntrys.apportType.number");
    		selector.add("customEntrys.apportType.name");
    		selector.add("customEntrys.value");
    		view.getSorter().add(new SorterItemInfo("entrys.type"));
    		view.getSorter().add(new SorterItemInfo("entrys.index"));
    		PlanIndexCollection planIndexCollection = PlanIndexFactory.getRemoteInstance().getPlanIndexCollection(view);
    		if(planIndexCollection.size()==1){
    			editData.put("PlanIndex",planIndexCollection.get(0));
    		}
    		
    	}
		
    	return (PlanIndexInfo)editData.get("PlanIndex");
    }
    
    public boolean isAimMeasure(){
		Boolean isAimMeasure=(Boolean)getUIContext().get("isAimMeasure");
		if(isAimMeasure!=null){
			return isAimMeasure.booleanValue();
		}else{
			return false;
		}
    }
    
    private void addTotalRow(KDTable table){
		try {
			AimCostClientHelper.setTotalCostRow(table, new String[]{"sumPrice","buildPart","sellPart","adjustAmt","amount"});
		} catch (Exception e) {
			handUIException(e);
		}
    }
    private boolean isDetailAcctRow(IRow row){
    	if(row!=null&&row.getCell(0).getUserObject()!=null&&row.getCell(0).getUserObject().equals("DetailInput")){
    		return true;
    	}
    	return false;
    }
    public void setDetailAcctRowNull(IRow row){
    	if(row!=null&&row.getCell(0).getUserObject()!=null){
    		row.getCell(0).setUserObject(null);
    	}
    }
    private void setDetailAcctRow(IRow row){
    	if(row!=null&&row.getCell(0)!=null){
    		row.getCell(0).setUserObject("DetailInput");
    	}
    	if(row != null && row.getCell("acctName") != null)
    	{
    		row.getCell("acctName").getStyleAttributes().setLocked(true);
    		row.getCell("acctName").getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	}
    }
    private void setDetailAcctHashInput(IRow row){
    	if(row!=null&&row.getCell(1)!=null){
    		row.getCell(1).setUserObject("HasInput");
    	}
    }
    private boolean isDetailAcctHasInput(IRow row){
    	if(row!=null&&row.getCell(1).getUserObject()!=null&&row.getCell(1).getUserObject().equals("HasInput")){
    		return true;
    	}
    	return false;
    }
    
    public void setDetailAcctHasNotInput(IRow row){
    	if(row!=null&&row.getCell(1)!=null){
    		row.getCell(1).setUserObject(null);
    	}
    }
    public void clearDetailAcctRow(KDTable table,IRow row){
		for(int i=3;i<table.getColumnCount();i++){
			row.getCell(i).setValue(null);
		}
    }
    
	private void setTemplateMeasureCostF7Editor(final KDTable table) {
		KDBizPromptBox myPrmtBox=new  KDBizPromptBox()
		{
			protected Object stringToValue(String t) {
				Object obj = super.stringToValue(t);
				if(obj!=null){
					return FDCHelper.toBigDecimal(t,4);			//R090514-137（奥园）
				}
				return obj;
			}
		};
		
		myPrmtBox.addSelectorListener(new SelectorListener(){
			public void willShow(SelectorEvent e) {
				IRow row=KDTableUtil.getSelectedRow(table);
				int colIdx=table.getSelectManager().getActiveColumnIndex();
				boolean isPrice=true;
				if(table.getColumnIndex("price")==colIdx){
					isPrice=true;
				}else{
					isPrice=false;
				}
				Object obj = row.getUserObject();
				final CostAccountInfo acct;
				if(obj instanceof MeasureEntryInfo){
					acct=((MeasureEntryInfo)obj).getCostAccount();
				}else{
					return;
				}

				
				String acctLongNumber=acct.getLongNumber();
				String projectTypeId=null;
				String productId=null;
				Object product = table.getHeadRow(0).getUserObject();
				if(product instanceof ProductTypeInfo){
					productId=((ProductTypeInfo)product).getId().toString();
				}
				if(acct.getType()==CostAccountTypeEnum.SIX){
					Object v = prmtProjectType.getValue();
					if(v instanceof ProjectTypeInfo){
						projectTypeId=((ProjectTypeInfo)v).getId().toString();
						
					}else{
						MsgBox.showWarning(getplTables(), "六类公摊必须先设置项目系列");
						e.setCanceled(true);
						return;
					}
				}
				TemplateMeasureCostPromptBox selector=getTemplateMeasureCostPromptBox();
				selector.setAcctLongNumber(acctLongNumber);
				selector.setProjectTypeID(projectTypeId);
				selector.setProductId(productId);
				selector.setIsPrice(isPrice);
				if(!isPrice){
					//系数要先设置指标
					if(row.getCell("indexName").getValue() instanceof Item){
						String key = ((Item)row.getCell("indexName").getValue()).key;
						if(key==null||key.equals("empty")){
							MsgBox.showWarning(getplTables(), "请先选择指标");
							e.setCanceled(true);
							return;
						}
						selector.setIndex(key);
						
					}else{
						MsgBox.showWarning(getplTables(), "请先选择指标");
						e.setCanceled(true);
						return;
					}
				}else{
					selector.setIndex(null);
				}
				((KDBizPromptBox) e.getSource()).setSelector(selector);
			}
		});
		ICellEditor f7Editor = new KDTDefaultCellEditor(myPrmtBox);
		table.getColumn("price").setEditor(f7Editor);
		table.getColumn("coefficient").setEditor(f7Editor);
	}
	
	private TemplateMeasureCostPromptBox selector=null;
	private TemplateMeasureCostPromptBox getTemplateMeasureCostPromptBox(){
//		if(selector==null){
			String orgId = (String) getUIContext().get("orgId");
			Boolean objBoolean=(Boolean)getUIContext().get("isAimMeasure");
			String projectId = (String)this.getUIContext().get("projectId");
			boolean isAimMeasure=true;
			if(objBoolean!=null){
				isAimMeasure=objBoolean.booleanValue();
			}
//			selector=new TemplateMeasureCostPromptBox(this,isAimMeasure,orgId);
			selector=new TemplateMeasureCostPromptBox(this,isAimMeasure,orgId,projectId,productTypeInfo);
//		}
		return selector;
	}
	
	protected MeasureCostInfo getEditData() {
		return (MeasureCostInfo)editData;
	}
	/**
	 * 设私有属性params(HashMap),可采取以下方式取值
	 * <pre>if (params==null) {
			try {
				params = FDCUtils.getDefaultFDCParam(null, null);
			} catch (Exception e) {
				handUIException(e);
			}
		}
        Object theValue = params.get(FDCConstants.FDC_PARAM_MEASUREQUALITY);
        if(theValue != null){
        	return Boolean.valueOf(theValue.toString()).booleanValue();
		}else{
			return false;
		}
	 */
	private HashMap params = null;
	private HashMap getParams() {
		if(params==null){
	        HashMap hmParamIn = new HashMap();
	        hmParamIn.put(FDCConstants.FDC_PARAM_MEASUREADJUST, null);
	        hmParamIn.put(FDCConstants.FDC_PARAM_MEASUREQUALITY, null);
	        hmParamIn.put(FDCConstants.FDC_PARAM_USECOSTOMINDEX, null);
	        hmParamIn.put(FDCConstants.FDC_PARAM_PLANINDEXLOGIC, null);
	        hmParamIn.put(FDCConstants.FDC_PARAM_BUILDPARTLOGIC, null);
	        //成本测算是否同步收入测算
	        hmParamIn.put(FDCConstants.FDC_PARAM_ISINCOMEJOINCOST, null);
	        hmParamIn.put(FDCConstants.FDC_PARAM_MEASUREINDEX, null);
	        try{
	        	HashMap hmAllParam = ParamControlFactory.getRemoteInstance().getParamHashMap(hmParamIn);
	        	params=hmAllParam;
	        }catch(Exception e){
	        	handUIException(e);
	        }
		}
		if(params==null){
			params=new HashMap();
		}
        return params;
	}
	
	private boolean isMeasureIndex(){
		Object theValue = getParams().get(FDCConstants.FDC_PARAM_MEASUREINDEX);
        if(theValue != null){
        	return Boolean.valueOf(theValue.toString()).booleanValue();
		}else{
			return false;
		}
	}
	
	/**
	 * 系统参数，是否启用调整系数
	 * @return
	 * @throws BOSException 
	 * @throws EASBizException 
	 */
	private boolean isUseAdjustCoefficient() {
		Object theValue = getParams().get(FDCConstants.FDC_PARAM_MEASUREADJUST);
        if(theValue != null){
        	return Boolean.valueOf(theValue.toString()).booleanValue();
		}else{
			return false;
		}
	}
	
	/**
	 * 启用品质特征列
	 * @return
	 */
	private boolean isUseQuality() {
		Object theValue = getParams().get(FDCConstants.FDC_PARAM_MEASUREQUALITY);
        if(theValue != null){
        	return Boolean.valueOf(theValue.toString()).booleanValue();
		}else{
			return false;
		}
	}
	/**
	 * 启用自定义指标
	 * @return
	 */
	protected boolean isUseCustomIndex() {
		Object theValue = getParams().get(FDCConstants.FDC_PARAM_USECOSTOMINDEX);
        if(theValue != null){
        	return Boolean.valueOf(theValue.toString()).booleanValue();
		}else{
			return false;
		}
	}
	/**
	 * 可研成本测算及目标成本测算时，规划指标表上：人行道及广场计算在“绿化用地合计”内
	 * @return
	 */
	protected boolean isPlanIndexLogic() {
		Object theValue = getParams().get(FDCConstants.FDC_PARAM_PLANINDEXLOGIC);
        if(theValue != null){
        	return Boolean.valueOf(theValue.toString()).booleanValue();
		}else{
			return false;
		}
	}
	/**
	 * 可研成本测算、目标成本测算上项目建筑单方计算，目标成本测算导出项目建筑面积指标都使用参与分摊的产品建筑面积之和，而不是总建筑面积
	 * @return
	 */
	protected boolean isBuildPartLogic() {
		Object theValue = getParams().get(FDCConstants.FDC_PARAM_BUILDPARTLOGIC);
        if(theValue != null){
        	return Boolean.valueOf(theValue.toString()).booleanValue();
		}else{
			return false;
		}
	}
	
	/**
	 * 成本测算与收入测算是否联用
	 * @return
	 */
	protected boolean isIncomeJoinCost() {
		Object theValue = getParams().get(FDCConstants.FDC_PARAM_ISINCOMEJOINCOST);
        if(theValue != null){
        	return Boolean.valueOf(theValue.toString()).booleanValue();
		}else{
			return false;
		}
	}
	
	public Object[] SIXITEMS=null;
	private Object[] getSixItems(){
		SIXITEMS=null;
		if(SIXITEMS==null){
			CustomPlanIndexEntryCollection customPlanIndexs = planIndexTable.getCustomPlanIndexs(null);
			if(customPlanIndexs.size()==0){
				SIXITEMS=Item.SIXITEMS;
			}else{
				List list=new ArrayList();
				Set appSet=new HashSet();
				for(int i=0;i<customPlanIndexs.size();i++){
					String appId = customPlanIndexs.get(i).getApportType().getId().toString();
					if(!appSet.contains(appId)){
						appSet.add(appId);
						list.add(Item.getCustomItem(customPlanIndexs.get(i).getApportType()));
					}
				}
				SIXITEMS=new Item[list.size()+Item.SIXITEMS.length];
				System.arraycopy(Item.SIXITEMS, 0, SIXITEMS, 0, Item.SIXITEMS.length);
				int i=Item.SIXITEMS.length;
				for(Iterator iter=list.iterator();iter.hasNext();){
					SIXITEMS[i++]=iter.next();
				}
			}
			
		}
		return SIXITEMS;
	}
	
	public BigDecimal getCustomIndexValue(IObjectValue info,Item item){
		if(item.isCustomIndex()){
			CustomPlanIndexEntryInfo customEntry=null;
			if(info instanceof PlanIndexInfo){
				customEntry = planIndexTable.getCustomPlanIndexEntryInfo(item.key, null);
			}
			if(info instanceof PlanIndexEntryInfo){
				PlanIndexEntryInfo entry=(PlanIndexEntryInfo)info;
				if(entry.getProduct()==null){
					return FDCHelper.ZERO;
				}
				String productId=entry.getProduct().getId().toString();
				customEntry = planIndexTable.getCustomPlanIndexEntryInfo(item.key, productId);
			}
			
			if(customEntry!=null){
				return customEntry.getValue();
			}
		}else {
			return info.getBigDecimal(item.key);
		}
		return null;
	}
	

	/**
	 * 授权科目，用于保存时做判断
	 */
	private Set accreditSet=null;
	
    /**
     * 将原来清空所有分录数据的逻辑改成清空授权科目，这样未被授权没有显示的科目数据就不会在保存后被清除掉
     *  by sxhong 2009-01-05 14:04:41
     * @param aimCost
     * @throws BOSException
     */
    private void handleAimCostAccredit(MeasureCostInfo measureCost) throws BOSException{
    	if(!AcctAccreditHelper.hasUsed(null)||accreditSet==null||accreditSet.size()==0){
    		//不使用科目授权的情况下按照原来的方式进行数据清除
    		measureCost.getCostEntry().clear();
    		return;
    	}
    	for(int i=measureCost.getCostEntry().size()-1;i>=0;i--){
    		MeasureEntryInfo entry=measureCost.getCostEntry().get(i);
    		if(entry==null||entry.getCostAccount()==null){
    			continue;
    		}
    		//这部分重新从界面取值，所以先删除掉
    		if(accreditSet.contains(entry.getCostAccount().getId().toString())){
    			measureCost.getCostEntry().remove(entry);
    		}else{
    			entry.setId(null);
    		}
    	}
    }
    
    /**
	 * 根据操作及单据状态控制控件状态
	 */
    public void setOprtState(String oprtType) {
		super.setOprtState(oprtType);
		MeasureCostInfo info = (MeasureCostInfo) editData;
		actionSubmit.setEnabled(true);
		actionSave.setEnabled(true);
		if (STATUS_ADDNEW.equals(this.oprtState)) {
			actionSubmit.setEnabled(true);
			actionSave.setEnabled(true);
		} else if (STATUS_EDIT.equals(this.oprtState)) {
			actionSave.setEnabled(true);
			actionSubmit.setEnabled(true);
			if(info != null&&info.getState() == FDCBillStateEnum.SUBMITTED){
				actionSubmit.setEnabled(true);
				actionSave.setEnabled(false);
				btnSave.setEnabled(false);
			}else if(info!=null&&info.getState()==FDCBillStateEnum.SAVED){
				actionSubmit.setEnabled(true);
			}
		} else if (STATUS_VIEW.equals(this.oprtState)) {
			actionSave.setEnabled(false);
			actionSubmit.setEnabled(false);
		}
		
	}
    
    public void beforeActionPerformed(ActionEvent e) {
		super.beforeActionPerformed(e);
		String action = e.getActionCommand();
		if(action == null || action.length()==0 || action.indexOf('$') == -1){
			return;
		}
		//确保每次操作每个action都调用
		handlePermissionForEachItemAction(action);
	}
    
    //同一实体，相同界面，相同方法的权限处理 by hpw 2010.11.18
    private String getPermItemNameByAction(String actionName){
    	String permItemName="ActionOnLoad";
    	boolean isAimMeasure= true;
    	if(getUIContext().get("isAimMeasure")!=null){
    		isAimMeasure=((Boolean)getUIContext().get("isAimMeasure")).booleanValue();
    	}
    	if("ActionOnLoad".endsWith(actionName)){
    		if(isAimMeasure){
    			permItemName="costdb_aimcost_cesuan_view";
    		}else{
    			permItemName="aim_measureCost_view";
    		}
    	}else if("ActionAddRow".endsWith(actionName)|| "ActionDeleteRow".endsWith(actionName) ||"ActionSave".endsWith(actionName)||"ActionSubmit".endsWith(actionName)){
    		if(isAimMeasure){
    			permItemName="costdb_aimcost_cesuan_edit";
    		}else{
    			permItemName="aim_measureCost_edit";
    		}
    	}else if("ActionImportTemplate".endsWith(actionName)){
    		if(isAimMeasure){
    			permItemName="costdb_aimcost_useTemp";
    		}else{
    			permItemName="aim_measureCost_useTemp";
    		}
    	}else if("ActionPrint".endsWith(actionName)||"ActionPrintPreview".endsWith(actionName)){
    		if(isAimMeasure){
    			permItemName="costdb_aimcost_cesuan_print";
    		}else{
    			permItemName="aim_measureCost_print";
    		}
    	}else{//其它权限
    		if(isAimMeasure){
        		permItemName="costdb_aimcost_cesuan_view";
        	}else{
        		permItemName="aim_measureCost_view";
        	}
    	}
    	return permItemName;
    }
    private void handlePermissionForEachItemAction(String actionName) {
		if(actionName == null || actionName.length()==0 || actionName.indexOf('$') == -1){
			return;
		}
		int index = actionName.indexOf('$');
		actionName = actionName.substring(index+1,actionName.length());
    	try {
			PermissionHelper.checkFunctionPermission(new ObjectUuidPK(SysContext.getSysContext().getCurrentUserInfo().getId().toString()), new ObjectUuidPK(SysContext.getSysContext()
					.getCurrentOrgUnit().getId().toString()), getPermItemNameByAction(actionName));
		} catch (EASBizException e) {
			handUIExceptionAndAbort(e);
		} catch (BOSException e) {
			handUIExceptionAndAbort(e);
		}
	}

    /**
     * 同一实体，同一菜单，相同方法权限问题处理
     * by hpw 2010.11.17
     */
    
	protected void handlePermissionForItemAction(ItemAction action) {
//    	super.handlePermissionForItemAction(action);
    	String actionName = action.getActionName();
    	handlePermissionForEachItemAction(actionName);
    }
	
	public void actionImportData_actionPerformed(ActionEvent e)
			throws Exception {

		if(this.editData==null||this.editData.getId()==null){
			FDCMsgBox.showConfirm2(this,"请先保存目标成本测算！");
			this.abort();
		}
//		if(this.plTables.getSelectedIndex()<3){
//			FDCMsgBox.showConfirm2(this,"请选择！");
//			this.abort();
//		}
		DatataskCaller task = new DatataskCaller();
        task.setParentComponent(this);
        if (getImportParam() != null)
        {
            task.invoke(getImportParam(), DatataskMode.ImpMode,true,true);
        }
        //刷新载入最新数据
        isFirstLoad=true;
        IObjectPK pk = new ObjectUuidPK(editData.getId());
        setDataObject(getValue(pk));
        loadFields();
	}
	protected ArrayList getImportParam()
    {	
		DatataskParameter param = new DatataskParameter();
		Hashtable hs = new Hashtable();
		hs.put("editData", this.editData);
		editData.put("planIndex", planIndexTable.getPlanIndexInfo());
		
		
		if(editData.get("project")!=null){
			String prjId = ((CurProjectInfo)editData.get("project")).getId().toString();
			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add(new SelectorItemInfo("*"));
			sic.add(new SelectorItemInfo("curProjProductEntries.*"));
			sic.add(new SelectorItemInfo(
					"curProjProductEntries.curProjProEntrApporData"));
			sic.add(new SelectorItemInfo("curProjCostEntries.*"));
			CurProjectInfo paramProject = new CurProjectInfo();
			try {
				paramProject = CurProjectFactory.getRemoteInstance()
						.getCurProjectInfo(
								new ObjectUuidPK(prjId), sic);
				hs.put("project", paramProject);
			} catch (EASBizException e) {
				e.printStackTrace();
			} catch (BOSException e) {
				e.printStackTrace();
			}
		}
		


		param.setContextParam(hs);
        param.solutionName = getSolutionName();      
        param.alias = getDatataskAlias();
        ArrayList paramList = new ArrayList();
        paramList.add(param);
        return paramList;
    }
	protected String getSolutionName(){
		return "eas.fdc.costmanager.MeasureCost";
    }protected String getDatataskAlias(){
    	return "目标成本测算";
    } 
    
    ProductTypeInfo productTypeInfo = null;
    protected void plTables_stateChanged(ChangeEvent e) throws Exception {
    	if(productTypeMap.size() < 1){
    		initProductTypeMap();
    	}
    	String name = plTables.getSelectedComponent().getName();
    	productTypeInfo  = (ProductTypeInfo)productTypeMap.get(name);
    }

	private void initProductTypeMap() throws BOSException {
		ProductTypeCollection coll = ProductTypeFactory.getRemoteInstance().getProductTypeCollection();
		for(int i = 0 ; i < coll.size() ; i++){
			ProductTypeInfo info = coll.get(i);
			productTypeMap.put(info.getName(),info);
		}
	}
	protected void tblAttachement_tableClicked(KDTMouseEvent e)throws Exception {
		if(e.getType() == 1 && e.getButton() == 1 && e.getClickCount() == 2){
			IRow row  =  tblAttachement.getRow(e.getRowIndex());
			getFileGetter();
			Object selectObj= row.getCell("id").getValue();
			if(selectObj!=null){
				String attachId=selectObj.toString();
				fileGetter.viewAttachment(attachId);
			}
		}
	}
	private  FileGetter fileGetter;
	private  FileGetter getFileGetter() throws Exception {
        if (fileGetter == null)
            fileGetter = new FileGetter((IAttachment) AttachmentFactory.getRemoteInstance(), AttachmentFtpFacadeFactory.getRemoteInstance());
        return fileGetter;
    }
	public void fillAttachmnetTable() throws EASBizException, BOSException {
		this.tblAttachement.removeRows();
		String boId = null;
		if (this.editData.getId() == null) {
			return;
		} else {
			boId = this.editData.getId().toString();
		}
		if (boId != null) {
			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add(new SelectorItemInfo("id"));
			sic.add(new SelectorItemInfo("attachment.id"));
			sic.add(new SelectorItemInfo("attachment.name"));
			sic.add(new SelectorItemInfo("attachment.createTime"));
			sic.add(new SelectorItemInfo("attachment.attachID"));
			sic.add(new SelectorItemInfo("attachment.beizhu"));
			sic.add(new SelectorItemInfo("assoType"));
			sic.add(new SelectorItemInfo("boID"));

			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("boID", boId));
			EntityViewInfo evi = new EntityViewInfo();
			evi.getSorter().add(new SorterItemInfo("boID"));
			evi.getSorter().add(new SorterItemInfo("attachment.name"));
			evi.setFilter(filter);
			evi.setSelector(sic);
			BoAttchAssoCollection cols = null;
			try {
				cols = BoAttchAssoFactory.getRemoteInstance().getBoAttchAssoCollection(evi);
			} catch (BOSException e) {
				e.printStackTrace();
			}
			boolean flag = false;
			if (cols != null && cols.size() > 0) {
				for (Iterator it = cols.iterator(); it.hasNext();) {
					BoAttchAssoInfo boaInfo = (BoAttchAssoInfo)it.next();
					AttachmentInfo attachment = boaInfo.getAttachment();
					IRow row = tblAttachement.addRow();
					row.getCell("id").setValue(attachment.getId().toString());
					row.getCell("name").setValue(attachment.getName());
				}
			}
		}
	}
	public void actionAttachment_actionPerformed(ActionEvent e)throws Exception {
		super.actionAttachment_actionPerformed(e);
		fillAttachmnetTable();
	}
	public KDTable initCompareTable(String productType){
		KDTable tblCompareEntry=new KDTable();
		tblCompareEntry.checkParsed();
		tblCompareEntry.addHeadRow();
		IColumn costAccount=tblCompareEntry.addColumn();
		costAccount.setKey("costAccount");
		tblCompareEntry.getHeadRow(0).getCell("costAccount").setValue("调整成本科目");
		costAccount.setWidth(400);
		costAccount.getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_DISABLE_BG_COLOR);
		costAccount.getStyleAttributes().setLocked(true);
		
		IColumn content=tblCompareEntry.addColumn();
		content.setKey("content");
		tblCompareEntry.getHeadRow(0).getCell("content").setValue("调整内容");
		content.setWidth(300);
		content.getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_DISABLE_BG_COLOR);
		content.getStyleAttributes().setLocked(true);
		
		IColumn reason=tblCompareEntry.addColumn();
		reason.setKey("reason");
		tblCompareEntry.getHeadRow(0).getCell("reason").setValue("调整原因");
		reason.setWidth(500);
		if(productType.equals("汇总")){
			reason.getStyleAttributes().setHided(true);
		}
		reason.setRequired(true);
		if(getOprtState().equals(OprtState.ADDNEW) ||getOprtState().equals(OprtState.EDIT)){
			reason.getStyleAttributes().setLocked(false);
		}else{
			reason.getStyleAttributes().setLocked(true);
		}
		
		tabCompareEntry.add(tblCompareEntry,productType);
		compareTables.put(productType, tblCompareEntry);
		return tblCompareEntry;
	}
	public void setTotalColor(){
		if(this.measureCollectTable==null)return;
		for(int j=0;j<this.measureCollectTable.getTable().getRowCount();j++){
			String longNumber=(String) this.measureCollectTable.getTable().getRow(j).getCell("acctNumber").getValue();
			if(totalColor.containsKey(longNumber)){
				if(totalColor.get(longNumber).toString().indexOf("增加")>0){
					this.measureCollectTable.getTable().getRow(j).getStyleAttributes().setBackground(up.getBackground());
				}else{
					this.measureCollectTable.getTable().getRow(j).getStyleAttributes().setBackground(down.getBackground());
				}
			}else{
				this.measureCollectTable.getTable().getRow(j).getStyleAttributes().setBackground(Color.WHITE);
			}
		}
	}
	public void loadCompareEntry(MeasureCostInfo info){
		totalColor=new HashMap();
		CRMHelper.sortCollection(info.getCompareEntry(), "costAccount", true);
		for(int i=0;i<info.getCompareEntry().size();i++){
			MeasureCostCompareInfo entry=info.getCompareEntry().get(i);
			String productType=entry.getProductType();
			KDTable table=(KDTable) compareTables.get(productType);
			if(table==null){
				table=initCompareTable(productType);
			}
			IRow row=table.addRow();
			row.setUserObject(entry);
			row.getCell("costAccount").setValue(entry.getCostAccount());
			row.getCell("content").setValue(entry.getContent());
			row.getCell("reason").setValue(entry.getReason());
			
			if(productType.equals("汇总")){
				totalColor.put(entry.getCostAccount().split("     ")[0], entry.getContent());
			}
		}
		Object[] key = compareTables.keySet().toArray(); 
		for (int k = 0; k < key.length; k++) {
			String productKey=key[k].toString();
			KDTable table=(KDTable) compareTables.get(productKey);
			if(table.getRowCount()==0){
				tabCompareEntry.remove(table);
				compareTables.remove(productKey);
			}
		}
	}
	public void compare(MeasureCostInfo info,Map map) throws SQLException, BOSException{
		Map reasonMap=new HashMap();
		Object[] key = compareTables.keySet().toArray(); 
		for (int k = 0; k < key.length; k++) {
			String productKey=key[k].toString();
			KDTable table=(KDTable) compareTables.get(productKey);
			for(int i=0;i<table.getRowCount();i++){
				if(table.getRow(i).getCell("costAccount").getValue()==null) continue;
				String costAccount=table.getRow(i).getCell("costAccount").getValue().toString();
				String reason=(String)table.getRow(i).getCell("reason").getValue();
				reasonMap.put(productKey+costAccount, reason);
			}
		}
		
		info.getCompareEntry().clear();
		Map totalMap=new HashMap();
		Map entryMap=new HashMap();
		Map colorMap=new HashMap();
		Map storeColorMap=new HashMap();
		for(int i=3;i<tables.size();i++){
			KDTable table=(KDTable) tables.get(i);
			ProductTypeInfo product = (ProductTypeInfo) table.getHeadRow(0).getUserObject();
			String productKey="六类公摊及期间费";
			if(product!=null)productKey=product.getName();
			for (int j = 0; j < table.getRowCount(); j++) {
				IRow row = table.getRow(j);
				if (row.getUserObject() instanceof MeasureEntryInfo) {
					row.getCell("description").setValue(null);
					boolean isEmpty=true;
					for(int k=3;k<table.getColumnCount();k++){
						if(!FDCHelper.isEmpty(row.getCell(k).getValue())){
							isEmpty=false;
							break;
						}
					}
					if(isEmpty){
						continue;
					}
					MeasureEntryInfo entry = (MeasureEntryInfo) row.getUserObject();
					if(row.getCell(0).getUserObject()==null){
						row=table.getRow(j-1);
					}
					entry.setCostAmount((BigDecimal) row.getCell("sumPrice").getValue());
					entry.setProduct(product);
					
					if(entry.getCostAmount()==null||entry.getCostAmount().compareTo(FDCHelper.ZERO)<=0)continue;
					String longNumber=entry.getCostAccount().getLongNumber().replaceAll("!", "\\.");
					String name=entry.getCostAccount().getName();
					if(entry.getCostAccount()!=null&&entry.getCostAccount().isIsLeaf()){
						if(entryMap.containsKey(productKey+longNumber)){
							continue;
						}else{
							entryMap.put(productKey+longNumber, entry);
							colorMap.put(productKey+longNumber, row);
							storeColorMap.put(productKey+longNumber, table.getRow(j));
							totalMap.put(longNumber+"     "+name, FDCHelper.add(totalMap.get(longNumber+"     "+name), entry.getCostAmount()));
						}
					}
				}
			}
		}
		String id=null;
		Boolean isEditVersion=(Boolean)getUIContext().get("isEditVersion");
    	if(isEditVersion!=null){
    		if(isEditVersion.booleanValue()){
    			if(this.editData.getId()==null){
    				id=((MeasureCostInfo)this.editData).getSrcMeasureCostId();
    			}else{
    				id=this.editData.getId().toString();
    			}
    		}
		}else{
			if(((MeasureCostInfo)this.editData).getVersionType().equals(VersionTypeEnum.AdjustVersion)){
				id=((MeasureCostInfo)this.editData).getSrcMeasureCostId();
			}
		}
		FDCSQLBuilder _builder = new FDCSQLBuilder();
		_builder.appendSql(" select costAccount.flongNumber longNumber,costAccount.fname_l2 name,sum(entry.fcostAmount) costAmount from T_AIM_MeasureEntry entry ");
		_builder.appendSql(" left join T_FDC_CostAccount costAccount on costAccount.fid=entry.fcostAccountId");
		_builder.appendSql(" left join T_FDC_ProductType product on product.fid=entry.fproductId");
		_builder.appendSql(" where costAccount.fisLeaf=1 and entry.fheadId='"+id+"'");
		_builder.appendSql(" group by costAccount.flongNumber,costAccount.fname_l2");
		IRowSet rowSet = _builder.executeQuery();
		while(rowSet.next()){
			String longNumber = rowSet.getString("longNumber").replaceAll("!", "\\.");
			String name=rowSet.getString("name");
			BigDecimal srcAmount=rowSet.getBigDecimal("costAmount");
			String content=null;
			MeasureCostCompareInfo entry=null;
			if(totalMap.get(longNumber+"     "+name)!=null){
				BigDecimal nowAmount=(BigDecimal) totalMap.get(longNumber+"     "+name);
				if(nowAmount.compareTo(srcAmount)!=0){
					entry=new MeasureCostCompareInfo();
					entry.setProductType("汇总");
					entry.setCostAccount(longNumber+"     "+name);
					if(nowAmount.compareTo(srcAmount)>0){
						content="成本增加"+FDCHelper.toBigDecimal(nowAmount.subtract(srcAmount),2)+"元";
					}else{
						content="成本减少"+FDCHelper.toBigDecimal(srcAmount.subtract(nowAmount),2)+"元";
					}
					entry.setContent(content);
					entry.setReason((String) reasonMap.get("汇总"+longNumber+"     "+name));
					info.getCompareEntry().add(entry);
				}
				totalMap.remove(longNumber+"     "+name);
			}else if(srcAmount.compareTo(FDCHelper.ZERO)!=0){
				entry=new MeasureCostCompareInfo();
				entry.setProductType("汇总");
				entry.setCostAccount(longNumber+"     "+name);
				content="成本减少"+FDCHelper.toBigDecimal(srcAmount,2)+"元";
				entry.setContent(content);
				entry.setReason((String) reasonMap.get("汇总"+longNumber+"     "+name));
				info.getCompareEntry().add(entry);
			}
		}
		Object[] totalKey = totalMap.keySet().toArray(); 
		for (int k = 0; k < totalKey.length; k++) {
			MeasureCostCompareInfo entry=new MeasureCostCompareInfo();
			entry.setProductType("汇总");
			entry.setCostAccount((String) totalKey[k]);
			String content="成本增加"+FDCHelper.toBigDecimal(totalMap.get(totalKey[k]),2)+"元";
			entry.setContent(content);
			entry.setReason((String) reasonMap.get("汇总"+totalKey[k]));
			info.getCompareEntry().add(entry);
		}
		_builder = new FDCSQLBuilder();
		_builder.appendSql(" select costAccount.flongNumber longNumber,costAccount.fname_l2 name,product.fname_l2 productKey,sum(entry.fcostAmount) costAmount from T_AIM_MeasureEntry entry ");
		_builder.appendSql(" left join T_FDC_CostAccount costAccount on costAccount.fid=entry.fcostAccountId");
		_builder.appendSql(" left join T_FDC_ProductType product on product.fid=entry.fproductId");
		_builder.appendSql(" where costAccount.fisLeaf=1 and entry.fheadId='"+id+"'");
		_builder.appendSql(" group by costAccount.flongNumber,costAccount.fname_l2,product.fname_l2");
		rowSet = _builder.executeQuery();
		while(rowSet.next()){
			String productKey="六类公摊及期间费";
			if(rowSet.getString("productKey")!=null)productKey=rowSet.getString("productKey");
			String longNumber = rowSet.getString("longNumber").replaceAll("!", "\\.");
			String name=rowSet.getString("name");
			BigDecimal srcAmount=rowSet.getBigDecimal("costAmount");
			String content=null;
			MeasureCostCompareInfo entry=null;
			if(entryMap.get(productKey+longNumber)!=null){
				MeasureEntryInfo nowMEntry=(MeasureEntryInfo) entryMap.get(productKey+longNumber);
				BigDecimal nowAmount=nowMEntry.getCostAmount();
				if(nowAmount.compareTo(srcAmount)!=0){
					entry=new MeasureCostCompareInfo();
					entry.setProductType(productKey);
					entry.setCostAccount(longNumber+"     "+name);
					if(nowAmount.compareTo(srcAmount)>0){
						content="成本增加"+FDCHelper.toBigDecimal(nowAmount.subtract(srcAmount),2)+"元";
						if(((IRow)storeColorMap.get(productKey+longNumber)).getCell(0).getUserObject()==null){
							((IRow)storeColorMap.get(productKey+longNumber)).getCell("description").setValue("upred");
						}else{
							((IRow)storeColorMap.get(productKey+longNumber)).getCell("description").setValue("red");
						}
						((IRow)colorMap.get(productKey+longNumber)).getStyleAttributes().setBackground(up.getBackground());
					}else{
						content="成本减少"+FDCHelper.toBigDecimal(srcAmount.subtract(nowAmount),2)+"元";
						if(((IRow)storeColorMap.get(productKey+longNumber)).getCell(0).getUserObject()==null){
							((IRow)storeColorMap.get(productKey+longNumber)).getCell("description").setValue("upgreen");
						}else{
							((IRow)storeColorMap.get(productKey+longNumber)).getCell("description").setValue("green");
						}
						((IRow)colorMap.get(productKey+longNumber)).getStyleAttributes().setBackground(down.getBackground());
					}
					entry.setContent(content);
					entry.setReason((String) reasonMap.get(productKey+longNumber+"     "+name));
					info.getCompareEntry().add(entry);
					
					map.put(entry.getProductType()+entry.getCostAccount(), entry.getContent());
				}
				entryMap.remove(productKey+longNumber);
			}else if(srcAmount.compareTo(FDCHelper.ZERO)!=0){
				entry=new MeasureCostCompareInfo();
				entry.setProductType(productKey);
				entry.setCostAccount(longNumber+"     "+name);
				content="成本减少"+FDCHelper.toBigDecimal(srcAmount,2)+"元";
				entry.setContent(content);
				entry.setReason((String) reasonMap.get(productKey+longNumber+"     "+name));
				info.getCompareEntry().add(entry);
				
				map.put(entry.getProductType()+entry.getCostAccount(), entry.getContent());
			}
		}
		Object[] entryKey = entryMap.keySet().toArray(); 
		for (int k = 0; k < entryKey.length; k++) {
			MeasureEntryInfo nowMEntry=(MeasureEntryInfo) entryMap.get(entryKey[k]);
			String nowNumber=nowMEntry.getCostAccount().getLongNumber().replaceAll("!", "\\.");
			String nowName=nowMEntry.getCostAccount().getName();
			String productKey="六类公摊及期间费";
			if(nowMEntry.getProduct()!=null)productKey=nowMEntry.getProduct().getName();
			
			MeasureCostCompareInfo entry=new MeasureCostCompareInfo();
			entry.setProductType(productKey);
			entry.setCostAccount(nowNumber+"     "+nowName);
			String content="成本增加"+FDCHelper.toBigDecimal(nowMEntry.getCostAmount(),2)+"元";
			if(((IRow)storeColorMap.get(productKey+nowNumber)).getCell(0).getUserObject()==null){
				((IRow)storeColorMap.get(productKey+nowNumber)).getCell("description").setValue("upred");
			}else{
				((IRow)storeColorMap.get(productKey+nowNumber)).getCell("description").setValue("red");
			}
			((IRow)colorMap.get(productKey+nowNumber)).getStyleAttributes().setBackground(up.getBackground());
			entry.setContent(content);
			entry.setReason((String) reasonMap.get(productKey+nowNumber+"     "+nowName.trim()));
			info.getCompareEntry().add(entry);
			
			map.put(entry.getProductType()+entry.getCostAccount(), entry.getContent());
		}
	}
	public void actionCompare_actionPerformed(ActionEvent e) throws Exception {
		MeasureCostInfo info=(MeasureCostInfo) this.editData;
		compare(info,new HashMap());
		
		tabCompareEntry.removeAll();
		compareTables=new HashMap();
		
		String id=null;
		Boolean isEditVersion=(Boolean)getUIContext().get("isEditVersion");
    	if(isEditVersion!=null){
    		if(isEditVersion.booleanValue()){
    			if(this.editData.getId()==null){
    				id=((MeasureCostInfo)this.editData).getSrcMeasureCostId();
    			}else{
    				id=this.editData.getId().toString();
    			}
    		}
		}else{
			if(((MeasureCostInfo)this.editData).getVersionType().equals(VersionTypeEnum.AdjustVersion)){
				id=((MeasureCostInfo)this.editData).getSrcMeasureCostId();
			}
		}
		PlanIndexEntryCollection ptCol=PlanIndexEntryFactory.getRemoteInstance().getPlanIndexEntryCollection("select product.name from where parent.headId='"+id+"' and product.id is not null");
		initCompareTable("汇总");
		initCompareTable("六类公摊及期间费");
		for(int i=0;i<ptCol.size();i++){
			initCompareTable(ptCol.get(i).getProduct().getName());
		}
		loadCompareEntry(info);
	}
	public void actionExportConstructTable_actionPerformed(ActionEvent e)throws Exception {
		ExportManager exportM = new ExportManager();
        String path = null;
        File tempFile = File.createTempFile("eastemp",".xls");
        path = tempFile.getCanonicalPath();

        KDTables2KDSBookVO[] tablesVO = new KDTables2KDSBookVO[1];
        
    	KDTable table = planIndexTable.getConstructTable();
        tablesVO[0] = new KDTables2KDSBookVO(table);
        String title = "建造标准";
        title=title.replaceAll("[{\\\\}{\\*}{\\?}{\\[}{\\]}{\\/}]", "|");
		tablesVO[0].setTableName(title);
		
        KDSBook book = null;
        book = KDTables2KDSBook.getInstance().exportKDTablesToKDSBook(tablesVO,true,true);
        exportM.exportToExcel(book, path);
        
		KDFileChooser fileChooser = new KDFileChooser();
		fileChooser.setFileSelectionMode(0);
		fileChooser.setMultiSelectionEnabled(false);
		fileChooser.setSelectedFile(new File("建造标准.xls"));
		int result = fileChooser.showSaveDialog(this);
		if (result == KDFileChooser.APPROVE_OPTION){
			File dest = fileChooser.getSelectedFile();
			try{
				File src = new File(path);
				if (dest.exists())
					dest.delete();
				src.renameTo(dest);
				FDCMsgBox.showInfo("导出成功！");
				KDTMenuManager.openFileInExcel(dest.getAbsolutePath());
			}
			catch (Exception e3)
			{
				handUIException(e3);
			}
		}
		tempFile.delete();
	}
	private String path =null;
	public void actionImportConstructTable_actionPerformed(ActionEvent e)throws Exception {
		path = SHEHelper.showExcelSelectDlg(this);
		if (path == null) {
			return;
		}
		Window win = SwingUtilities.getWindowAncestor(this);
        LongTimeDialog dialog = null;
        if(win instanceof Frame){
        	dialog = new LongTimeDialog((Frame)win);
        }else if(win instanceof Dialog){
        	dialog = new LongTimeDialog((Dialog)win);
        }
        if(dialog==null){
        	dialog = new LongTimeDialog(new Frame());
        }
        dialog.setLongTimeTask(new ILongTimeTask() {
			public void afterExec(Object arg0) throws Exception {
				Boolean bol=(Boolean)arg0;
				if(bol){
					FDCMsgBox.showInfo("导入成功！");
				}
			}
			public Object exec() throws Exception {
				boolean bol=importExcelToTable(path,planIndexTable.getConstructTable());
				return bol;
			}
    	}
	    );
	    dialog.show();
	}
	private boolean importExcelToTable(String fileName, KDTable table) throws Exception {
		KDSBook kdsbook = null;
		try {
			kdsbook = POIXlsReader.parse2(fileName);
		} catch (Exception e) {
			e.printStackTrace();
			FDCMsgBox.showWarning(this,"读EXCEL出错,EXCEl格式不匹配！");
			return false;
		}
		if (kdsbook == null) {
			return false;
		}
		for(int i=0;i<KDSBookToBook.traslate(kdsbook).getSheetCount();i++){
			Sheet excelSheet = KDSBookToBook.traslate(kdsbook).getSheet(i);
	        	
            String title = "建造标准";
            if(excelSheet.getSheetName().equals(title)){
            	Map e_colNameMap = new HashMap();
        		int e_maxRow = excelSheet.getMaxRowIndex();
        		int e_maxColumn = excelSheet.getMaxColIndex();
        		for (int col = 0; col <= e_maxColumn; col++) {
        			String excelColName = excelSheet.getCell(0, col, true).getText();
        			e_colNameMap.put(excelColName, new Integer(col));
        		}
        		for (int col = 0; col< table.getColumnCount(); col++) {
        			if (table.getColumn(col).getStyleAttributes().isHided()) {
        				continue;
        			}
        			String colName = (String) table.getHeadRow(0).getCell(col).getValue();
        			Integer colInt = (Integer) e_colNameMap.get(colName);
        			if (colInt == null) {
        				FDCMsgBox.showWarning(this,title+"  表头结构不一致！表格上的关键列:" + colName + "在EXCEL中没有出现！");
        				return false;
        			}
        		}
        		table.removeRows();
        		for (int rowIndex = 1; rowIndex <= e_maxRow; rowIndex++) {
        			IRow row = table.addRow();
        			CostIndexEntryInfo entry = new CostIndexEntryInfo();
        			entry.setId(BOSUuid.create(entry.getBOSType()));
        			entry.setConfig((CostIndexConfigInfo) table.getUserObject());
        			row.setUserObject(entry);
        			for (int col = 0; col < table.getColumnCount(); col++) {
        				if (table.getColumn(col).getStyleAttributes().isHided()) {
	        				continue;
	        			}
        				ICell tblCell = row.getCell(col);
        				String colName = (String) table.getHeadRow(0).getCell(col).getValue();
        				Integer colInt = (Integer) e_colNameMap.get(colName);

        				if (colInt == null) {
        					continue;
        				}
        				Variant cellRawVal = excelSheet.getCell(rowIndex, colInt.intValue(), true).getValue();
        				if (Variant.isNull(cellRawVal)) {
        					continue;
        				}
        				String colValue = cellRawVal.toString();
    					tblCell.setValue(colValue);
        			}
        		}
            	break;
            }
		}
		return true;
	}
}