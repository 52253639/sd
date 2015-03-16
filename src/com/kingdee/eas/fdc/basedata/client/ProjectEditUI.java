/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.Action;
import javax.swing.JTextField;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.BizDataFormat;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.event.SelectorEvent;
import com.kingdee.bos.ctrl.swing.util.CtrlCommonConstant;
import com.kingdee.bos.dao.AbstractObjectValue;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectStringPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.framework.agent.AgentUtility;
import com.kingdee.bos.framework.agent.IObjectValueAgent;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basedata.ApportionTypeCollection;
import com.kingdee.eas.fdc.basedata.ApportionTypeFactory;
import com.kingdee.eas.fdc.basedata.ApportionTypeInfo;
import com.kingdee.eas.fdc.basedata.CurProjProductEntriesCollection;
import com.kingdee.eas.fdc.basedata.CurProjProductEntriesInfo;
import com.kingdee.eas.fdc.basedata.CurProjectCollection;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBasedataException;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.FDCSQLFacadeFactory;
import com.kingdee.eas.fdc.basedata.HashSearchEngine;
import com.kingdee.eas.fdc.basedata.IApportionType;
import com.kingdee.eas.fdc.basedata.ICurProject;
import com.kingdee.eas.fdc.basedata.IProjectIndexData;
import com.kingdee.eas.fdc.basedata.OperationPhasesInfo;
import com.kingdee.eas.fdc.basedata.ProductTypeInfo;
import com.kingdee.eas.fdc.basedata.ProjectBaseInfo;
import com.kingdee.eas.fdc.basedata.ProjectHelper;
import com.kingdee.eas.fdc.basedata.ProjectIndexDataCollection;
import com.kingdee.eas.fdc.basedata.ProjectIndexDataEntryCollection;
import com.kingdee.eas.fdc.basedata.ProjectIndexDataEntryInfo;
import com.kingdee.eas.fdc.basedata.ProjectIndexDataFactory;
import com.kingdee.eas.fdc.basedata.ProjectIndexDataInfo;
import com.kingdee.eas.fdc.basedata.ProjectStageEnum;
import com.kingdee.eas.fdc.basedata.ProjectStatusInfo;
import com.kingdee.eas.fdc.basedata.ProjectTypeInfo;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.sellhouse.SellProjectCollection;
import com.kingdee.eas.fdc.sellhouse.SellProjectFactory;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.ObjectValueForEditUIUtil;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.DateTimeUtils;

/**
 * 描述:工程项目编辑界面
 * 
 * @author jackwang date:2006-7-7
 * @version EAS5.1
 */
public class ProjectEditUI extends AbstractProjectEditUI {
	private static final String COL_VALUE = "value";

	private boolean isFinacial = false;

	private static final String COL_APPORTION_TYPE = "apportionType";

	private static final Logger logger = CoreUIObject
			.getLogger(ProjectEditUI.class);

//	private FullOrgUnitInfo addFullOrgUnitCache = null;

	private AbstractObjectValue oldData = null;

	private Map isCompSettleMap = new HashMap();

	/**
	 * modify by qinyouzhen 2011-05-11. 动态加入分摊指标，从第2列之后开始
	 */
	private final int startAsstColumn = 2;

	private ArrayList apportionTypeColumns = new ArrayList();

	ApportionTypeCollection apportionTypeCollection = new ApportionTypeCollection();

	//是否使用成本月结,当前财务组织的期间
	private Date[] pkdate ;
	
	private void setTitle() {
		FDCBaseDataClientUtils.setupUITitle(this, EASResource.getString(
				FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE,
				FDCBaseDataClientUtils.PROJECT));
	}

	private boolean isReSetDevPrj = false;
	
	/**
	 * output class constructor
	 */
	public ProjectEditUI() throws Exception {
		super();

		pkdate = FDCClientHelper.getCompanyCurrentDate();
	}

	public void onLoad() throws Exception {
		super.onLoad();
		this.txtProjectAddress.setMaxLength(80);
		FDCClientHelper.setActionEnable(actionRemove, false);
		tblProductEntries.checkParsed();
		tblProductEntries.getColumn("isAccObj").getStyleAttributes().setLocked(
				true);
		tblProductEntries.getColumn("isCompSettle").getStyleAttributes()
				.setLocked(true);
		tblCostEntries.getColumn("apportionType").getStyleAttributes()
				.setLocked(true);
		FDCHelper.formatTableNumber(this.tblCostEntries, COL_VALUE);
		tblProductEntries.getColumn("compArea").setEditor(
				FDCClientHelper.getNumberCellEditor());
		tblProductEntries.getColumn("compDate").setEditor(
				FDCClientHelper.getDateCellEditor());
		FDCHelper.formatTableNumber(tblProductEntries, "compArea");
		tblProductEntries.getColumn("compDate").getStyleAttributes()
				.setNumberFormat(FDCHelper.KDTABLE_DATE_FMT);
		// add by qinyouzhen 2011-05-11,设置字段“产品类型”的背景颜色
		tblProductEntries.getColumn("name").getStyleAttributes().setBackground(
				FDCTableHelper.cantEditColor);
		
		setTitle();
		setBtnStatus();
		this.bizProjectStatus
				.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7ProjectStatusQuery");
		this.bizProjectType
				.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7ProjectTypeQuery");
		tblCostEntries.getColumn(COL_VALUE).setEditor(
				FDCClientHelper.getNumberCellEditor());

//		replaceSepOfNumber();
		if ((!OrgConstants.DEF_CU_ID.equals(SysContext.getSysContext()
				.getCurrentCtrlUnit().getId().toString()))
				&& (!SysContext.getSysContext().getCurrentFIUnit()
						.isIsOnlyUnion())) {
			this.btnAddNew.setEnabled(true);
			if (STATUS_ADDNEW.equals(this.getOprtState())) {
				this.btnEdit.setEnabled(false);
				this.btnRemove.setEnabled(false);
			} else if (STATUS_STATE.equals(this.getOprtState())) {
				this.btnEdit.setEnabled(false);
				this.btnSave.setEnabled(false);
				this.btnAddNew.setEnabled(false);
				this.btnRemove.setEnabled(false);
			} else {
				if (STATUS_EDIT.equals(this.getOprtState())) {
					this.btnRemove.setEnabled(true);
				} else {
					this.btnRemove.setEnabled(false);
				}
			}
			// this.btnRemove.setEnabled(true);
			this.btnCancel.setVisible(true);
			this.btnCancelCancel.setVisible(true);
			this.menuItemCancel.setVisible(true);
			this.menuItemCancelCancel.setVisible(true);
			this.menuItemAddNew.setEnabled(true);
			this.menuItemEdit.setEnabled(true);
			this.menuItemRemove.setEnabled(true);
		} else {
			this.btnAddNew.setEnabled(false);
			this.btnEdit.setEnabled(false);
			this.btnRemove.setEnabled(false);
			this.btnCancel.setVisible(false);
			this.btnCancelCancel.setVisible(false);
			this.menuItemCancel.setVisible(false);
			this.menuItemCancelCancel.setVisible(false);
			this.menuItemAddNew.setEnabled(false);
			this.menuItemEdit.setEnabled(false);
			this.menuItemRemove.setEnabled(false);
		}
		// if(this.editData.isIsLeaf()){
		// this.btnEdit.setEnabled(true);
		// this.btnRemove.setEnabled(true);
		// }else{
		// this.btnEdit.setEnabled(false);
		// this.btnRemove.setEnabled(false);
		// }

		tblProductEntries.getHeadRow(0).getCell("isAccObj").setValue("是否核算对象");
		// this.menuItemCancel.setVisible(false);
		// this.menuItemCancelCancel.setVisible(false);
		// 锁定不让录入指标
		tblCostEntries.getColumn("value").getStyleAttributes().setLocked(true);
		tblCostEntries.getColumn("value").getStyleAttributes().setBackground(
				FDCTableHelper.cantEditColor);
		bizProjectType.setRequired(true);
		if (this.editData.getParent()!=null || (editData.getLevel() > 1)) {
			bizProjectType.setData(editData.getProjectType());
			bizProjectType.setEnabled(false);
			bizProjectType.setEditable(false);
			// 是否开发项目不能修改
			chkIsDevPrj.setEnabled(false);
		} else {
			bizProjectType.setEnabled(true);
			bizProjectType.setRequired(true);
			// 一级工程项目存在业务数据，不可修改
			if(!STATUS_ADDNEW.equals(getOprtState()) && ProjectHelper.checkBeforeModifyIsDevPrj(editData)){
				chkIsDevPrj.setEnabled(false);
			}
		}
		// 财务组织级参数,用当前组织去判断,当前组织为成本中心,上级实体财务组织此参数禁用时,获取的参数是错误的
		HashMap paramItem = FDCUtils.getDefaultFDCParam(null, SysContext
				.getSysContext().getCurrentFIUnit().getId().toString());
		if (paramItem.get(FDCConstants.FDC_PARAM_FINACIAL) != null) {
			isFinacial = Boolean.valueOf(
					paramItem.get(FDCConstants.FDC_PARAM_FINACIAL).toString())
					.booleanValue();
		}
		if (!isFinacial
				&& (STATUS_EDIT.equals(this.oprtState) || STATUS_ADDNEW
						.equals(this.oprtState))) {
			bizProjectStatus.setEnabled(true);
		} else {
			bizProjectStatus.setEnabled(false);
		}
		
		if(STATUS_EDIT.equals(this.oprtState)){
			this.wbtProductDelLine.setEnabled(!this.editData.isIsEnabled());
		}
		
		storeFields();
		initOldData(editData);
//		setSortNoEnable();
		
		if(editData.getParent()!=null){
			Set id=new HashSet();
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("projectBase.id",null,CompareType.NOTEQUALS));
			
			if(editData.getId()!=null){
				filter.getFilterItems().add(new FilterItemInfo("id",editData.getId(),CompareType.NOTEQUALS));
			}
			view.setFilter(filter);
			CurProjectCollection col=CurProjectFactory.getRemoteInstance().getCurProjectCollection(view);
			for(int i=0;i<col.size();i++){
				id.add(col.get(i).getProjectBase().getId().toString());
			}
			view = new EntityViewInfo();
			filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("isNewProject",Boolean.TRUE));
			filter.getFilterItems().add(new FilterItemInfo("projectBase.engineeProject.id",editData.getFullOrgUnit().getId()));
			
			if(id.size()>0){
				filter.getFilterItems().add(new FilterItemInfo("id",id,CompareType.NOTINCLUDE));
			}
			view.setFilter(filter);
			this.prmtProjectBase.setEntityViewInfo(view);
			this.prmtProjectBase.setRequired(true);
		}else{
			this.prmtProjectBase.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
			this.prmtProjectBase.setEnabled(false);
		}
		this.prmtProjectBase.setRequired(false);
	}

	private void replaceSepOfNumber() {
		// 设置编辑和查看状态下界面的上级编码控件和本级简码控件
		String longNumber = editData.getLongNumber();
		if (longNumber != null) {
			longNumber = longNumber.replace('!', '.');
			this.txtLongNumber.setText(longNumber);
			if (longNumber.indexOf(".") > 0) {
				this.txtParentNumber.setText(longNumber.substring(0, longNumber
						.lastIndexOf(".")));
			}
		}
	}

	/**
	 * load指标数据
	 * 
	 * @throws BOSException
	 * @throws EASBizException
	 */
	private void loadIdxData() throws BOSException, EASBizException {
//		this.addFullOrgUnitCache = this.editData.getFullOrgUnit();
//		clearTable();

		// -----------------------------------------------------------------------------------
		/*
		 * 填充成本信息页签的指标数据 - 从指标管理取数
		 */
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("projOrOrgID", editData.getId().toString()));
		filter.getFilterItems().add(
				new FilterItemInfo("projectStage",
						ProjectStageEnum.DYNCOST_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("productType", null));
		filter.getFilterItems().add(
				new FilterItemInfo("isLatestVer", Boolean.TRUE));
		view.setFilter(filter);
		view.getSelector().add("projOrOrgID");
		view.getSelector().add("entries.apportionType.*");
		view.getSelector().add("entries.indexValue");

		ProjectIndexDataCollection idxColl = getIndexInterface()
				.getProjectIndexDataCollection(view);

		ProjectIndexDataEntryCollection entries = null;
		if (idxColl != null && idxColl.size() > 0) {
			ProjectIndexDataInfo indexDataInfo = idxColl.get(0);
			entries = indexDataInfo.getEntries();
		}

		FilterInfo appFilter = ProjectIndexDataUtils.getAppFilter(null, true);
		// 分摊指标不要“其他”，产品分录中保留，万科刘从胜提的要求，2007.11
		FilterInfo tempFilter=new FilterInfo();
		tempFilter.getFilterItems().add(
				new FilterItemInfo("name", "其他", CompareType.NOTEQUALS));
		tempFilter.getFilterItems().add(
				new FilterItemInfo("name", "其它", CompareType.NOTEQUALS));
		appFilter.mergeFilter(tempFilter, "and");
		ProjectIndexDataEntryCollection coll = ProjectIndexDataUtils
				.createBlankIndexesColl(appFilter);

		
		if (entries != null && entries.size() > 0) {
			HashSearchEngine searchEngin = new HashSearchEngine(coll,
					new String[] { COL_APPORTION_TYPE });
			for (Iterator iter = entries.iterator(); iter.hasNext();) {
				ProjectIndexDataEntryInfo entryInfo = (ProjectIndexDataEntryInfo) iter
						.next();
				if (searchEngin.evaluate(entryInfo)) {
					ProjectIndexDataEntryInfo idxInfo = (ProjectIndexDataEntryInfo) searchEngin
							.getResult();
					idxInfo.setIndexValue(entryInfo.getIndexValue());
				}
			}
		}

		for (Iterator iter = coll.iterator(); iter.hasNext();) {
			ProjectIndexDataEntryInfo element = (ProjectIndexDataEntryInfo) iter
					.next();

			ApportionTypeInfo apportionTypeInfo = element.getApportionType();

			IRow row = this.tblCostEntries.addRow();

			row.getCell(COL_APPORTION_TYPE).setValue(apportionTypeInfo);

			ICell cellValueAttribute = row.getCell(COL_VALUE);
			// cellValueAttribute.getStyleAttributes().setLocked(false);
			cellValueAttribute.setValue(element.getIndexValue());

			// 可售面积不允许编辑sxhong 2007/11/29
			if (editData.isIsLeaf()
					&& apportionTypeInfo.getId().toString().equals(
							ApportionTypeInfo.sellAreaType)) {
				cellValueAttribute.getStyleAttributes().setLocked(true);
				cellValueAttribute.getStyleAttributes().setBackground(
						new Color(0xE8E8E3));
			}
		}
		// ------------------------end--------------------------------------------------

		/*
		 * 填充产品设置页签的数据 - 从指标管理取
		 */
		this.tblProductEntries.checkParsed(true);

		IApportionType iApportionType = ApportionTypeFactory
				.getRemoteInstance();
		EntityViewInfo evi = new EntityViewInfo();
		FilterInfo filter2 = new FilterInfo();
		filter2.getFilterItems().add(
				new FilterItemInfo("isEnabled", Boolean.valueOf(true)));
		filter2.getFilterItems().add(
				new FilterItemInfo("targetType.isEnabled", Boolean.TRUE));
		filter2.getFilterItems().add(
				new FilterItemInfo("forCostApportion", Boolean.TRUE));
		filter2.getFilterItems().add(
				new FilterItemInfo("id", FDCConstants.AIM_COST_PERCENT_ID,
						CompareType.NOTEQUALS));
		filter2.getFilterItems().add(
				new FilterItemInfo("id", ApportionTypeInfo.appointType,
						CompareType.NOTEQUALS));
		filter2.mergeFilter(ApportionTypeInfo.getCUFilter(SysContext.getSysContext().getCurrentCtrlUnit()), "and");
		evi.setFilter(filter2);
		evi.getSelector().add("*");
		evi.getSelector().add("targetType.id");
		apportionTypeCollection.addCollection(iApportionType
				.getApportionTypeCollection(evi));
		int tmp = apportionTypeCollection.size();
		for (int i = 0; i < tmp; i++) {
			IColumn column = tblProductEntries.addColumn(startAsstColumn + i);
			column.setWidth(100);
			column.setKey(apportionTypeCollection.get(i).getId().toString());
			column.setUserObject(apportionTypeCollection.get(i));
			column.setEditor(FDCClientHelper.getNumberCellEditor());
			tblProductEntries.getHeadRow(0).getCell(startAsstColumn + i)
					.setValue(apportionTypeCollection.get(i).getName());
			apportionTypeColumns.add(column);
			FDCHelper.formatTableNumber(this.tblProductEntries,
					apportionTypeCollection.get(i).getId().toString());

			column.getStyleAttributes().setLocked(true);
			column.getStyleAttributes().setBackground(
					FDCTableHelper.cantEditColor);
		}

		loadProductEntries();
		// this.bizProjectStatus.setEnabled(true);
		// bizProjectStatus.setEditable(false);
	}

	private void loadProductEntries() throws BOSException {
		CurProjProductEntriesCollection cpe = this.editData.getCurProjProductEntries();
		if (cpe != null && cpe.size() > 0) {
			sortEntries(cpe);
			tblProductEntries.removeRows();
			// 行
			IRow row;
			for (int i = 0; i < cpe.size(); i++) {
				row = this.tblProductEntries.addRow();

				ICell cellValueAttribute = row.getCell("productType");

				JTextField txtEnable = new JTextField();
				KDTDefaultCellEditor ceEnable = new KDTDefaultCellEditor(
						txtEnable);
				ceEnable = new KDTDefaultCellEditor(createPropertyNameF7());
				cellValueAttribute.setEditor(ceEnable);

				ProductTypeInfo productType = cpe.get(i).getProductType();
				cellValueAttribute.setValue(productType);
				//2008-10-31 冻结取产品类型的Cell，防止F7重新选择修改产品类型。
				cellValueAttribute.getStyleAttributes().setLocked(true);
				
				//add by qinyouzhen 2011-05-11,编辑页面加入产品类型代码  
				ObjectValueRender re = new ObjectValueRender();
				re.setFormat(new BizDataFormat("$number$"));
				cellValueAttribute.setRenderer(re);
				if (productType != null) {
					row.getCell("name").setValue(productType.getName());
				}
				/*
				 * 从指标管理取数
				 */
				if (editData == null || editData.getId() == null) {
					// 新增的时候没有指标
					return;
				}
				EntityViewInfo view3 = new EntityViewInfo();
				FilterInfo filter3 = new FilterInfo();
				filter3.getFilterItems().add(
						new FilterItemInfo("projOrOrgID", editData.getId()
								.toString()));
				filter3.getFilterItems().add(
						new FilterItemInfo("projectStage",
								ProjectStageEnum.DYNCOST_VALUE));
				filter3.getFilterItems().add(
						new FilterItemInfo("productType", productType.getId()
								.toString()));
				filter3.getFilterItems().add(
						new FilterItemInfo("isLatestVer", Boolean.TRUE));
				filter3.getFilterItems().add(
						new FilterItemInfo("id", FDCHelper
								.getSetByArray(new String[] {
										ApportionTypeInfo.aimCostType,
										ApportionTypeInfo.appointType }),
								CompareType.NOTINCLUDE));
				view3.setFilter(filter3);
				view3.getSelector().add("entries.apportionType.id");
				view3.getSelector().add("entries.indexValue");

				ProjectIndexDataCollection idxCol = getIndexInterface()
						.getProjectIndexDataCollection(view3);

				if (idxCol != null && idxCol.size() > 0) {
					ProjectIndexDataInfo indexDataInfo = idxCol.get(0);
					ProjectIndexDataEntryCollection entries2 = indexDataInfo
							.getEntries();
					for (Iterator iter = entries2.iterator(); iter.hasNext();) {
						ProjectIndexDataEntryInfo entryInfo = (ProjectIndexDataEntryInfo) iter
								.next();
						String appTypeId = entryInfo.getApportionType().getId()
								.toString();
						ICell cell = row.getCell(appTypeId);
						if (cell == null)
							continue; // 这种情况发生,应该是指标的"用于成本分摊"的属性为false的记录,工程项目这里只显示该属性为true的记录
						cell.setValue(entryInfo.getIndexValue());
					}
				}

				row.getCell("isAccObj").setValue(Boolean.valueOf(cpe.get(i).isIsAccObj()));
				boolean isCompSettle = cpe.get(i).isIsCompSettle();
				row.getCell("isCompSettle").setValue(Boolean.valueOf(isCompSettle));
				row.getCell("compArea").setValue(cpe.get(i).getCompArea());
				row.getCell("compDate").setValue(cpe.get(i).getCompDate());
				if (row.getCell("fiVouchered") != null)
					row.getCell("fiVouchered").setValue(Boolean.valueOf(cpe.get(i).isFiVouchered()));

				isCompSettleMap.put(productType.getId().toString(), Boolean.valueOf(isCompSettle));
			}
		}
	}

	/**
	 * 为新增初始化数据
	 * 进行了逻辑优化  by sxhong 2009-04-16 21:07:51
	 * @throws BOSException
	 * @throws Exception
	 */
	private void initData() throws BOSException, Exception {
		this.chkIsEnabled.setSelected(false);
		loadDatas();
		this.txtSortNo.setValue(new Integer(this.editData.getSortNo()));
		this.chkIsEnabled.setSelected(this.editData.isIsEnabled());
		if(this.editData.getParent()!=null){    //存在上级工程项目
			String parentLongNumber = this.editData.getParent().getLongNumber().replace('!', '.');
			this.txtParentNumber.setText(parentLongNumber);
		}
		
		
	}

	private IProjectIndexData getIndexInterface() throws BOSException {
		return ProjectIndexDataFactory.getRemoteInstance();
	}

	public void loadFields() {
		super.loadFields();
		replaceSepOfNumber();
		clearTable();
		try {
			if (STATUS_ADDNEW.equals(this.getOprtState())) {
				initData();
				this.btnEdit.setEnabled(false);
				this.btnRemove.setEnabled(false);

			} else {
				loadIdxData();
				
				//设置parentInfo 以减少EditUI对ListUI的依赖
				if(this.editData.getParent()!=null){
					this.getUIContext().put("parentInfo",this.editData.getParent());
				}else{
					this.getUIContext().put("parentInfo",this.editData.getFullOrgUnit());
				}
				
			}
		} catch (BOSException e) {
			handUIException(e);
		} catch (Exception e) {
			handUIException(e);
		}
		
		setSortNoEnable();
	}

	public SelectorItemCollection getSelectors() {
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add(new SelectorItemInfo("*"));
		selector.add(new SelectorItemInfo("landDeveloper.*"));
		selector.add(new SelectorItemInfo("fullOrgUnit.*"));
		selector.add(new SelectorItemInfo("curProject.*"));
		selector.add(new SelectorItemInfo("parent.*"));
		selector.add(new SelectorItemInfo("curProjProductEntries.*"));
		selector.add(new SelectorItemInfo("curProjProductEntries.fiVouchered"));
		selector.add(new SelectorItemInfo(
				"curProjProductEntries.curProjProEntrApporData.*"));
		selector
				.add(new SelectorItemInfo(
						"curProjProductEntries.curProjProEntrApporData.apportionType.id"));
		selector
				.add(new SelectorItemInfo(
						"curProjProductEntries.curProjProEntrApporData.apportionType.name"));
		selector
				.add(new SelectorItemInfo(
						"curProjProductEntries.curProjProEntrApporData.apportionType.*"));
		selector
				.add(new SelectorItemInfo("curProjProductEntries.productType.*"));
		selector.add(new SelectorItemInfo("curProjCostEntries.*"));
		selector
				.add(new SelectorItemInfo("curProjCostEntries.apportionType.*"));
		selector.add(new SelectorItemInfo("projectStatus.*"));
		selector.add(new SelectorItemInfo("projectType.*"));
		
		selector.add("projectBase.*");
		return selector;
	}

	private void setBtnStatus() {
		this.actionProductAddLine.putValue(Action.SMALL_ICON, EASResource
				.getIcon("imgTbtn_addline"));
		this.actionProductInsertLine.putValue(Action.SMALL_ICON, EASResource
				.getIcon("imgTbtn_insert"));
		this.actionProductDelLine.putValue(Action.SMALL_ICON, EASResource
				.getIcon("imgTbtn_deleteline"));
		wbtProductAddLine.setText(null);
		this.wbtProductInsertLine.setText(null);
		wbtProductDelLine.setText(null);

		if (STATUS_ADDNEW.equals(getOprtState())) {// 新增状态
			this.btnCancelCancel.setVisible(false);// 启用按钮不可见
			this.btnCancel.setVisible(false);// 禁用按钮不可见
			this.menuItemCancel.setVisible(false);
			this.menuItemCancelCancel.setVisible(false);
			if (!isFinacial) {
				bizProjectStatus.setEnabled(true);
			} else {
				bizProjectStatus.setEnabled(false);
			}
		} else if (STATUS_EDIT.equals(getOprtState())) {// 修改状态
			if (!isFinacial) {
				bizProjectStatus.setEnabled(true);
			} else {
				bizProjectStatus.setEnabled(false);
			}
			if (this.editData.isIsEnabled()) {// 如果当前为启用状态
				this.btnCancel.setVisible(true);// 禁用按钮可用
				this.btnCancel.setEnabled(true);// 禁用按钮可用
				this.menuItemCancel.setVisible(true);
				this.menuItemCancel.setEnabled(true);
				this.btnCancelCancel.setVisible(false);// 启用按钮不可见
				this.menuItemCancelCancel.setVisible(false);

			} else {// 如果当前为禁用状态
				this.btnCancelCancel.setVisible(true);// 启用按钮可见
				this.btnCancelCancel.setEnabled(true);// 启用按钮可用
				this.menuItemCancelCancel.setVisible(true);
				this.menuItemCancelCancel.setEnabled(true);
				this.btnCancel.setVisible(false);// 禁用按钮不可见
				this.menuItemCancel.setVisible(false);
			}
		} else if (STATUS_VIEW.equals(getOprtState())) {// 查看状态
			if (this.editData.isIsEnabled()) {// 如果当前为启用状态
				this.btnCancel.setVisible(true);// 禁用按钮可用
				this.btnCancel.setEnabled(true);// 禁用按钮可用
				this.menuItemCancel.setVisible(true);
				this.menuItemCancel.setEnabled(true);
				this.btnCancelCancel.setVisible(false);// 启用按钮不可见
				this.menuItemCancelCancel.setVisible(false);
			} else {// 如果当前为禁用状态
				this.btnCancelCancel.setVisible(true);// 启用按钮可见
				this.btnCancelCancel.setEnabled(true);// 启用按钮可用
				this.menuItemCancelCancel.setVisible(true);
				this.menuItemCancelCancel.setEnabled(true);
				this.btnCancel.setEnabled(false);// 禁用按钮不可见
				this.menuItemCancel.setEnabled(false);
			}
		} else if (STATUS_STATE.equals(this.getOprtState())) {
			this.btnEdit.setEnabled(false);
			this.btnSave.setEnabled(false);
			this.btnAddNew.setEnabled(false);
			this.btnRemove.setEnabled(false);
			tblCostEntries.getStyleAttributes().setLocked(true);
			tblProductEntries.getStyleAttributes().setLocked(true);
			tblProductEntries.getColumn("compArea").getStyleAttributes()
					.setLocked(false);
			tblProductEntries.getColumn("compDate").getStyleAttributes()
					.setLocked(false);
			tblProductEntries.getColumn("isCompSettle").getStyleAttributes()
					.setLocked(false);
			wbtProductAddLine.setEnabled(false);
			wbtProductDelLine.setEnabled(false);
			wbtProductInsertLine.setEnabled(false);
		}

		// if
		// (!OrgConstants.DEF_CU_ID.equals(SysContext.getSysContext().getCurrentCtrlUnit().getId().toString()))
		// {
		// this.btnAddNew.setEnabled(true);
		// this.btnEdit.setEnabled(true);
		// this.btnRemove.setEnabled(true);
		// this.btnCancel.setVisible(true);
		// this.btnCancelCancel.setVisible(true);
		// this.menuItemCancel.setVisible(true);
		// this.menuItemCancelCancel.setVisible(true);
		// } else {
		// this.btnAddNew.setEnabled(false);
		// this.btnEdit.setEnabled(false);
		// this.btnRemove.setEnabled(false);
		// this.btnCancel.setVisible(false);
		// this.btnCancelCancel.setVisible(false);
		// this.menuItemCancel.setVisible(false);
		// this.menuItemCancelCancel.setVisible(false);
		// }
		this.btnPrint.setVisible(false);
		this.btnPrintPreview.setVisible(false);
		this.menuView.setVisible(false);
	}

	private void loadDatas() {
		try {
			IApportionType iApportionType = ApportionTypeFactory
					.getRemoteInstance();
			EntityViewInfo evi = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(
					new FilterItemInfo("isEnabled", Boolean.valueOf(true)));
			filter.getFilterItems().add(
					new FilterItemInfo("forCostApportion", Boolean.TRUE));
			filter.getFilterItems().add(
					new FilterItemInfo("id", FDCConstants.AIM_COST_PERCENT_ID,
							CompareType.NOTEQUALS));
			filter.getFilterItems().add(
					new FilterItemInfo("id", ApportionTypeInfo.appointType,
							CompareType.NOTEQUALS));
			filter.mergeFilter(ApportionTypeInfo.getCUFilter(SysContext.getSysContext().getCurrentCtrlUnit()), "and");
			evi.setFilter(filter);

			evi.getSelector().add("*");
			evi.getSelector().add("targetType.id");
			evi.getSelector().add("name");
			evi.getSorter().add(new SorterItemInfo("number"));
			apportionTypeCollection = iApportionType
					.getApportionTypeCollection(evi);
			tblCostEntries.checkParsed();// 检索
			tblCostEntries.getColumn(COL_APPORTION_TYPE).setWidth(175);
			tblCostEntries.getColumn(COL_VALUE).setWidth(225);
			
			for (int i = 0; i < apportionTypeCollection.size(); i++) {
				ApportionTypeInfo apportionTypeInfo = (ApportionTypeInfo) apportionTypeCollection
						.get(i);
				IRow row = this.tblCostEntries.addRow();
				row.getCell(COL_APPORTION_TYPE).setValue(apportionTypeInfo);
				// row.getCell(COL_VALUE).getStyleAttributes().setLocked(false);
				// ICell cellValueAttribute;
				// cellValueAttribute = row.getCell("value");
				// cellValueAttribute.getStyleAttributes().setLocked(false);
				// cellValueAttribute.setEditor(new
				// KDTDefaultCellEditor(createIntFieldForInit()));
			}
			// 添加额外的启用分摊类型数据到两个table中
			HashSet lnUps = new HashSet();
			for (int i = 0; i < this.tblCostEntries.getRowCount(); i++) {
				lnUps.add(((ApportionTypeInfo) this.tblCostEntries.getRow(i)
						.getCell(COL_APPORTION_TYPE).getValue()).getId());
			}
			FilterInfo filterInfo = new FilterInfo();
			filterInfo.getFilterItems().add(
					new FilterItemInfo("id", lnUps, CompareType.NOTINCLUDE));
			filterInfo.getFilterItems().add(
					new FilterItemInfo("isEnabled", Boolean.valueOf(true),
							CompareType.EQUALS));
			filterInfo.getFilterItems().add(
					new FilterItemInfo("forCostApportion", Boolean.TRUE,
							CompareType.EQUALS));
			filterInfo.getFilterItems().add(
					new FilterItemInfo("id", FDCConstants.AIM_COST_PERCENT_ID,
							CompareType.NOTEQUALS));
			filterInfo.getFilterItems().add(
					new FilterItemInfo("id", ApportionTypeInfo.appointType,
							CompareType.NOTEQUALS));
			EntityViewInfo evi1 = new EntityViewInfo();
			filterInfo.mergeFilter(ApportionTypeInfo.getCUFilter(SysContext.getSysContext().getCurrentCtrlUnit()), "and");
			evi1.setFilter(filterInfo);
			evi1.getSelector().add("*");
			evi1.getSelector().add("targetType.id");
			ApportionTypeCollection otherAdd = ApportionTypeFactory
					.getRemoteInstance().getApportionTypeCollection(evi1);
			if (otherAdd.size() != 0) {
				for (int i = 0; i < otherAdd.size(); i++) {
					IRow row = this.tblCostEntries.addRow();
					row.getCell(COL_APPORTION_TYPE).setValue(otherAdd.get(i));
					row.getCell(COL_VALUE).setValue(FDCHelper.ZERO);
				}
				apportionTypeCollection.addCollection(otherAdd);
				// int colCount = tblProductEntries.getColumnCount();
				// for (int i = 0; i < otherAdd.size(); i++) {
				// // HashMap item = (HashMap) apportionTypeCollection.get(i);
				// IColumn column = tblProductEntries.addColumn(colCount-1);
				// column.setWidth(100);
				// column.setKey(otherAdd.get(i).getId().toString());
				// column.setUserObject(otherAdd.get(i));
				// tblProductEntries.getHeadRow(0).getCell(colCount-1).setValue(otherAdd.get(i).getName());
				// apportionTypeColumns.add(column);
				// }
			}
			//
			this.tblProductEntries.checkParsed(true);// 检索
			for (int i = 0; i < apportionTypeCollection.size(); i++) {
				// HashMap item = (HashMap) apportionTypeCollection.get(i);
				IColumn column = tblProductEntries.addColumn(startAsstColumn
						+ i);
				column.setWidth(100);
				column
						.setKey(apportionTypeCollection.get(i).getId()
								.toString());
				column.setUserObject(apportionTypeCollection.get(i));
				column.setEditor(FDCClientHelper.getNumberCellEditor());
				tblProductEntries.getHeadRow(0).getCell(startAsstColumn + i)
						.setValue(apportionTypeCollection.get(i).getName());
				apportionTypeColumns.add(column);
				FDCHelper.formatTableNumber(this.tblProductEntries,
						apportionTypeCollection.get(i).getId().toString());
				column.getStyleAttributes().setLocked(true);
				column.getStyleAttributes().setBackground(
						FDCTableHelper.cantEditColor);
			}

			// /
			// IColumn column;
			// IRow row = tblProductEntries.addRow();
			// for (int j = 0; j < apportionTypeColumns.size(); j++) {
			// column = (IColumn) apportionTypeColumns.get(j);
			// ;
			// row.getCell(column.getKey()).setValue(((ApportionTypeInfo)column.getUserObject()).get);
			// }
			// 删除其它行
			for (int i = tblCostEntries.getRowCount() - 1; i >= 0; i--) {
				Object value = tblCostEntries.getCell(i, COL_APPORTION_TYPE)
						.getValue();
				if (value instanceof ApportionTypeInfo) {
					ApportionTypeInfo apportionTypeInfo = (ApportionTypeInfo) value;
					if (apportionTypeInfo.getName() != null
							&& (apportionTypeInfo.getName().equals("其它") || apportionTypeInfo
									.getName().equals("其他"))) {
						tblCostEntries.removeRow(i);
					}
				}

			}
		} catch (BOSException e) {
			e.printStackTrace();
		}
	}

	private KDFormattedTextField createIntFieldForInit() {
		KDFormattedTextField initField = new KDFormattedTextField(
				KDFormattedTextField.INTEGER);
		initField.setDataVerifierType(KDFormattedTextField.INPUTING_VERIFIER);
		initField.putClientProperty("OriginValue", Boolean.TRUE);
		initField.setMaximumValue(new Integer(1000000000));
		initField.setMinimumValue(new Integer(0));
		initField.setValue(new Integer(1));
		return initField;
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		beforeStoreFields();
		super.storeFields();
//		this.editData.setStartDate((Date)this.pkStartDate..getValue());
//		this.editData.getStartDate()
	}

	/**
	 * 保存指标数据到指标管理
	 * 
	 */
	private void saveIdxData() throws Exception {
		try {
			String id = (String) getUIContext().get(UIContext.ID);

			if (FDCHelper.isEmpty(id))
				return;

			ProjectIndexDataInfo costIdxInfo2 = getCostIdxInfo();
			if (costIdxInfo2 == null) {
				return;
			}
			if (editData.isIsLeaf()) {
				// 保存之前先取数据库内的产品占地面积与建筑面积与现在的值进行比较

				FDCSQLBuilder builder = new FDCSQLBuilder();
				builder
						.appendSql("select fapportionTypeid,sum(findexvalue) as indexvalue ");
				builder.appendSql("from T_FDC_Projectindexdata head ");
				builder
						.appendSql("inner join T_FDC_ProjectIndexdataEntry entry on entry.fparentid=head.fid ");
				builder.appendSql("where entry.fapportiontypeid in (?,?) ");
				builder
						.appendSql("and fprojOrOrgID=? and fprojectStage = 'DYNCOST' and fisLatestVer=1 ");
				builder
						.appendSql("and fproducttypeid is not null group by fapportionTypeid");
				builder.addParam(ApportionTypeInfo.buildAreaType);
				builder.addParam(ApportionTypeInfo.sellAreaType);
				builder.addParam(id);
				IRowSet rowSet = builder.executeQuery();
				BigDecimal oldBuildAmt = FDCHelper.ZERO;
				BigDecimal oldSellAmt = FDCHelper.ZERO;
				if (rowSet != null && rowSet.size() > 0) {
					while (rowSet.next()) {
						if (rowSet.getString("fapportionTypeId").equals(
								ApportionTypeInfo.buildAreaType)) {
							oldBuildAmt = rowSet.getBigDecimal("indexvalue");
						}
						if (rowSet.getString("fapportionTypeId").equals(
								ApportionTypeInfo.sellAreaType)) {
							oldSellAmt = rowSet.getBigDecimal("indexvalue");
						}
					}
				}
				BigDecimal buildAmt = FDCHelper.ZERO;
				BigDecimal sellAmt = FDCHelper.ZERO;
				ProjectIndexDataCollection productIdxColl2 = getProductIdxColl();
				for (Iterator iter = productIdxColl2.iterator(); iter.hasNext();) {
					ProjectIndexDataInfo element = (ProjectIndexDataInfo) iter
							.next();

					for (Iterator iter2 = element.getEntries().iterator(); iter2
							.hasNext();) {
						ProjectIndexDataEntryInfo entry = (ProjectIndexDataEntryInfo) iter2
								.next();
						if (entry.getApportionType().getId().toString().equals(
								ApportionTypeInfo.buildAreaType)) {
							buildAmt = buildAmt.add(FDCHelper
									.toBigDecimal(entry.getIndexValue()));
						}
						if (entry.getApportionType().getId().toString().equals(
								ApportionTypeInfo.sellAreaType)) {
							sellAmt = sellAmt.add(FDCHelper.toBigDecimal(entry
									.getIndexValue()));
						}
					}
				}
				boolean hasBuildArea = false;
				boolean hasSellArea = false;
				for (Iterator iter = costIdxInfo2.getEntries().iterator(); iter
						.hasNext();) {
					ProjectIndexDataEntryInfo entry = (ProjectIndexDataEntryInfo) iter
							.next();
					if (entry.getApportionType().getId().toString().equals(
							ApportionTypeInfo.buildAreaType)) {
						if (FDCHelper.toBigDecimal(entry.getIndexValue())
								.signum() == 0) {
							entry.setIndexValue(buildAmt);
						} else {
							entry.setIndexValue(entry.getIndexValue().add(
									buildAmt).subtract(oldBuildAmt));
						}
						hasBuildArea = true;
					}

					if (entry.getApportionType().getId().toString().equals(
							ApportionTypeInfo.sellAreaType)) {
						if (FDCHelper.toBigDecimal(entry.getIndexValue())
								.signum() == 0) {
							entry.setIndexValue(sellAmt);
						} else {
							entry.setIndexValue(entry.getIndexValue().add(
									sellAmt).subtract(oldSellAmt));
						}
						hasSellArea = true;
					}
				}
				if (!hasBuildArea) {
					ProjectIndexDataEntryInfo info = new ProjectIndexDataEntryInfo();
					info.setParent(costIdxInfo2);
					ApportionTypeInfo apport = new ApportionTypeInfo();
					apport.setId(BOSUuid.read(ApportionTypeInfo.buildAreaType));
					info.setApportionType(apport);
					info.setIndexValue(buildAmt);
					costIdxInfo2.getEntries().add(info);
				}
				if (!hasSellArea) {
					ProjectIndexDataEntryInfo info = new ProjectIndexDataEntryInfo();
					info.setParent(costIdxInfo2);
					ApportionTypeInfo apport = new ApportionTypeInfo();
					apport.setId(BOSUuid.read(ApportionTypeInfo.sellAreaType));
					info.setApportionType(apport);
					info.setIndexValue(sellAmt);
					costIdxInfo2.getEntries().add(info);
				}
			}

			costIdxInfo2.setProjOrOrgID(BOSUuid.read(id));
			costIdxInfo2.setIsLatestVer(true);
			costIdxInfo2.setProjectStage(ProjectStageEnum.DYNCOST);
			getIndexInterface().submit(costIdxInfo2);
			// 07.1.16根据客户需求,去掉汇总功能
			// TreeNode node =
			// ProjectIndexDataUtils.getNodeById(costIdxInfo2.getProjOrOrgID().toString());
			// 向上汇总
			// ProjectIndexDataUtils.sumUp(node, null);

			ProjectIndexDataCollection productIdxColl2 = getProductIdxColl();
			for (Iterator iter = productIdxColl2.iterator(); iter.hasNext();) {
				ProjectIndexDataInfo element = (ProjectIndexDataInfo) iter
						.next();
				element.setProjOrOrgID(BOSUuid.read(id));
				element.setProjectStage(ProjectStageEnum.DYNCOST);
				getIndexInterface().submit(element);
				// 07.1.16根据客户需求,去掉汇总功能
				// node =
				// ProjectIndexDataUtils.getNodeById(element.getProjOrOrgID().toString());
				// 向上汇总
				// ProjectIndexDataUtils.sumUp(node,
				// element.getProductType().getId().toString());
			}

		} catch (Exception e) {
			handUIException(e);
		}

	}

	/**
	 * 校验值对象的合法性
	 */
	protected void verifyInput(ActionEvent e) throws Exception {
		FDCClientVerifyHelper.verifyRequire(this);
		if (this.txtNumber.getText() == null
				|| this.txtNumber.getText().trim().length() == 0) {
			throw new com.kingdee.eas.fdc.basedata.FDCBasedataException(
					FDCBasedataException.NUMBER_IS_EMPTY);
		} else {
			if (!(this.txtNumber.getText().indexOf(".") < 0)) {
				throw new FDCBasedataException(
						FDCBasedataException.NUMBER_CHECK_3);
			}
			if (!(this.txtNumber.getText().indexOf("!") < 0)) {
				throw new FDCBasedataException(
						FDCBasedataException.NUMBER_CHECK_3);
			}
		}
		if (this.txtName.getSelectedItem().toString() == null
				|| this.txtName.getSelectedItem().toString().trim().length() == 0) {
			throw new com.kingdee.eas.fdc.basedata.FDCBasedataException(
					FDCBasedataException.NAME_IS_EMPTY);
		}
		if (this.bizProjectStatus.getValue() == null) {
			throw new com.kingdee.eas.fdc.basedata.FDCBasedataException(
					FDCBasedataException.PROJECTSTATUS_IS_EMPTY);
		}
		if (this.bizProjectType.getValue() == null) {
			MsgBox.showError(this, "项目系列为空");
			SysUtil.abort();
		}
		if (this.pkStartDate.getValue() == null) {
			throw new com.kingdee.eas.fdc.basedata.FDCBasedataException(
					FDCBasedataException.STARTDATE_IS_NULL);
		}
		if(this.txtSortNo.getValue().toString().compareTo("0")<0){
//			MsgBox.showConfirm2New(this,"开发顺序必须为非负整数才能保存!");
			MsgBox.showWarning(this, "开发顺序必须为非负整数才能保存!");
			SysUtil.abort();
		}
//		if(editData.getParent()!=null){
//			FDCClientVerifyHelper.verifyEmpty(this, this.prmtProjectBase);
//			FilterInfo filter = new FilterInfo();
//			filter.getFilterItems().add(new FilterItemInfo("projectBase.id",((OperationPhasesInfo)this.prmtProjectBase.getValue()).getId()));
//			if(editData.getId()!=null){
//				filter.getFilterItems().add(new FilterItemInfo("id",editData.getId(),CompareType.NOTEQUALS));
//			}
//			if(CurProjectFactory.getRemoteInstance().exists(filter)){
//				MsgBox.showInfo("运营分期信息重复选择！");
//				SysUtil.abort();
//			}
//		}
		
		for (int i = 0; i < this.tblProductEntries.getRowCount(); i++) {
			Object curProdValue = tblProductEntries.getRow(i).getCell(
					"productType").getValue();
			if (curProdValue == null) {
				MsgBox.showWarning(this, EASResource.getString(
						"com.kingdee.eas.fdc.basedata.FDCBaseDataResource",
						"Project_ProductNumber_Null"));
				SysUtil.abort();
			}

//			boolean isCS = ((Boolean) tblProductEntries.getCell(i,
//					"isCompSettle").getValue()).booleanValue();
//			if (curProdValue != null && isCS) {
//				if (tblProductEntries.getCell(i, "compArea").getValue() == null) {
//					MsgBox.showWarning(this, "第" + (i + 1) + "行分录竣工面积不能为空");
//					SysUtil.abort();
//				}
//
//				if (((BigDecimal) tblProductEntries.getCell(i, "compArea")
//						.getValue()).signum() <= 0) {
//					MsgBox.showWarning(this, "第" + (i + 1) + "行分录竣工面积必须大于0");
//					SysUtil.abort();
//				}
//
//				if (tblProductEntries.getCell(i, "compDate").getValue() == null) {
//					MsgBox.showWarning(this, "第" + (i + 1) + "行分录竣工日期不能为空");
//					SysUtil.abort();
//				}
//				// 增加检查开始日期小于竣工日期 add by jianxing_zhou 2007-8-27
//				else if (!((Date) tblProductEntries.getCell(i, "compDate")
//						.getValue()).after((Date) this.pkStartDate.getValue())) {
//					MsgBox.showWarning(this, "第" + (i + 1) + "行分录竣工日期不能早于开始日期");
//					SysUtil.abort();
//				}
//			}

			for (int j = i + 1; j < this.tblProductEntries.getRowCount(); j++) {

				Object nextProdValue = tblProductEntries.getRow(j).getCell(
						"productType").getValue();

				if (nextProdValue != null
						&& ((ProductTypeInfo) curProdValue).getId().toString()
								.equals(
										((ProductTypeInfo) nextProdValue)
												.getId().toString())) {
					MsgBox
							.showWarning(
									this,
									com.kingdee.eas.util.client.EASResource
											.getString(
													"com.kingdee.eas.fdc.basedata.FDCBaseDataResource",
													"Project_ProductEntry_Dul"));
					SysUtil.abort();
					// return false;
				}

				// if(((CostCenterOrgUnitInfo)this.tblProductEntries.getRow(i).getCell("costCenter").getValue()).getId().toString().equals(((CostCenterOrgUnitInfo)this.tblProductEntries.getRow(j).getCell("costCenter").getValue()).getId().toString())){
				// MsgBox.showError(com.kingdee.eas.util.client.EASResource.getString("com.kingdee.eas.fdc.basedata.FDCBaseDataResource",
				// "ProjectWithCostCenter_Exist"));
				// // return false;
				// }
			}
		}
	}

	private ProjectIndexDataInfo costIdxInfo;

	private ProjectIndexDataCollection productIdxColl;

	private ProjectIndexDataInfo getIndexInfo(ProductTypeInfo productType) {
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("projOrOrgID", editData.getId()));
		if (productType == null) {
			filter.getFilterItems().add(
					new FilterItemInfo("productType.id", null));
		} else {
			filter.getFilterItems().add(
					new FilterItemInfo("productType.id", productType.getId()
							.toString()));
		}
		filter.getFilterItems().add(
				new FilterItemInfo("isLatestVer", Boolean.TRUE));
		filter.getFilterItems().add(
				new FilterItemInfo("projectStage",
						ProjectStageEnum.DYNCOST_VALUE));
		view.setFilter(filter);
		view.getSelector().add("id");
		view.getSelector().add("projOrOrgID");
		view.getSelector().add("productType.id");
		view.getSelector().add("productType.name");
		view.getSelector().add("isLatestVer");
		view.getSelector().add("entries.*");

		ProjectIndexDataCollection coll = null;
		try {
			coll = getIndexInterface().getProjectIndexDataCollection(view);
		} catch (BOSException e) {
			handUIException(e);
		}

		ProjectIndexDataInfo info = null;

		if (coll == null || coll.size() == 0 || coll.get(0) == null) {
			info = construcatAIndexInfo(productType);
		} else {
			info = coll.get(0);
		}

		return info;
	}

	private ProjectIndexDataInfo construcatAIndexInfo(
			ProductTypeInfo productType) {
		ProjectIndexDataInfo info = new ProjectIndexDataInfo();
		info.setProductType(productType);
		info.setProjOrOrgID(editData.getId());
		info.setIsLatestVer(true);
		info.setVerNo("V1.0");
		info.setVerName("初始版本");
		info.setVerTime(new Timestamp(System.currentTimeMillis()));

		return info;
	}

	private void beforeStoreFields() {

		this.editData.getCurProjProductEntries().clear();

		// 成本分录
		ProjectIndexDataInfo costInfo = getIndexInfo(null);

		ProjectIndexDataEntryCollection costIdxColl = new ProjectIndexDataEntryCollection();
		ProjectIndexDataEntryInfo projectIndexDataEntryInfo = null;
		for (int i = 0; i < this.tblCostEntries.getRowCount(); i++) {
			BigDecimal value = (BigDecimal) this.tblCostEntries.getRow(i)
					.getCell(COL_VALUE).getValue();
			if (value == null || value.signum() == 0)
				continue;

			projectIndexDataEntryInfo = new ProjectIndexDataEntryInfo();
			ApportionTypeInfo apportionTypeInfo = (ApportionTypeInfo) this.tblCostEntries
					.getCell(i, COL_APPORTION_TYPE).getValue();
			projectIndexDataEntryInfo.setApportionType(apportionTypeInfo);
			projectIndexDataEntryInfo.setTargetType(apportionTypeInfo
					.getTargetType());

			projectIndexDataEntryInfo.setIndexValue(value);

			costIdxColl.add(projectIndexDataEntryInfo);
		}
		costInfo.getEntries().clear();
		costInfo.getEntries().addCollection(costIdxColl);

		setCostIdxInfo(costInfo);

		// 产品分录

		ProjectIndexDataInfo productInfo = null;
		ProjectIndexDataCollection productColl = new ProjectIndexDataCollection();
		ProjectIndexDataEntryInfo productIdxEntryInfo = null;
		CurProjProductEntriesInfo curProjProductEntriesInfo = null;
		for (int i = 0; i < this.tblProductEntries.getRowCount(); i++) {
			ProductTypeInfo productType = (ProductTypeInfo) this.tblProductEntries
					.getRow(i).getCell("productType").getValue();

			if (productType == null)
				continue;

			productInfo = getIndexInfo(productType);
			productInfo.getEntries().clear();

			for (int j = 1; j < this.apportionTypeCollection.size(); j++) {
				productIdxEntryInfo = new ProjectIndexDataEntryInfo();
				productIdxEntryInfo
						.setApportionType(this.apportionTypeCollection.get(j));
				productIdxEntryInfo.setTargetType(this.apportionTypeCollection
						.get(j).getTargetType());

				BigDecimal value = (BigDecimal) this.tblProductEntries
						.getRow(i).getCell(j + 1).getValue();

				if (value != null && value.signum() > 0) {

					productIdxEntryInfo.setIndexValue(value);

					productInfo.getEntries().add(productIdxEntryInfo);
				}
			}

			productColl.add(productInfo);

			curProjProductEntriesInfo = new CurProjProductEntriesInfo();

			curProjProductEntriesInfo.setProductType(productType);
			curProjProductEntriesInfo.setIsAccObj((Boolean
					.valueOf(this.tblProductEntries.getRow(i).getCell(
							"isAccObj").getValue().toString())).booleanValue());
			curProjProductEntriesInfo.setIsCompSettle((Boolean
					.valueOf(this.tblProductEntries.getRow(i).getCell(
							"isCompSettle").getValue().toString()))
					.booleanValue());
			curProjProductEntriesInfo
					.setCompArea((BigDecimal) this.tblProductEntries.getRow(i)
							.getCell("compArea").getValue());
			curProjProductEntriesInfo.setCompDate((Date) this.tblProductEntries
					.getRow(i).getCell("compDate").getValue());
			if (this.tblProductEntries.getRow(i).getCell("fiVouchered") != null) {
				Object fiVocher = this.tblProductEntries.getRow(i).getCell(
						"fiVouchered").getValue();
				if (fiVocher != null) {
					curProjProductEntriesInfo
							.setFiVouchered(((Boolean) fiVocher).booleanValue());
				}
			}
			curProjProductEntriesInfo.setCurProject(this.editData);

			this.editData.getCurProjProductEntries().add(
					curProjProductEntriesInfo);
		}
		setProductIdxColl(productColl);
		//从界面取数（输入长度超80,提示，改成小于80还提示)
		editData.setLongNumber(txtLongNumber.getText());
	}
	
	/**
	 * output txtNumber_focusLost method
	 */
	protected void txtNumber_focusLost(java.awt.event.FocusEvent e)
			throws Exception {
		if (this.txtNumber.getText() != null) {
			if (this.txtParentNumber.getText() != null
					&& (!"".equals(this.txtParentNumber.getText().trim()))
					&& !"".equals(this.txtNumber.getText().trim())) {
				this.txtLongNumber.setText(this.txtParentNumber.getText() + "."
						+ this.txtNumber.getText());
			} else {
				this.txtLongNumber.setText(this.txtNumber.getText());
			}
		}
	}

	/**
	 * output actionProductAddLine_actionPerformed method
	 */
	public void actionProductAddLine_actionPerformed(ActionEvent e)
			throws Exception {
		this.tblProductEntries.checkParsed();
		int index = -1;
		index = this.tblProductEntries.getRowCount();
		IRow row;
		if (index != -1) {
			row = tblProductEntries.addRow(index);
		} else {
			row = tblProductEntries.addRow();
		}
		initProductCell(row);
		row.getCell("isAccObj").setValue(Boolean.TRUE);
		row.getCell("isCompSettle").setValue(Boolean.FALSE);
	}

	private KDBizPromptBox createPropertyNameF7() {
		KDBizPromptBox propertyNameF7 = new KDBizPromptBox();
		propertyNameF7
				.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7ProductTypeQuery");
		propertyNameF7.setEditable(false);
		return propertyNameF7;
	}

	/**
	 * output actionProductInsertLine_actionPerformed method
	 */
	public void actionProductInsertLine_actionPerformed(ActionEvent e)
			throws Exception {
		int i = -1;
		i = this.tblProductEntries.getSelectManager().getActiveRowIndex();
		if (i == -1) {
			MsgBox.showError(com.kingdee.eas.util.client.EASResource.getString(
					"com.kingdee.eas.fdc.basedata.FDCBaseDataResource",
					"Selected_Insert"));
			return;
		}
		IRow row;
		row = tblProductEntries.addRow(i);
		initProductCell(row);
		row.getCell("isAccObj").setValue(Boolean.TRUE);
		row.getCell("isCompSettle").setValue(Boolean.FALSE);
	}

	/**
	 * 重构选择产品类型的方法，在分录新增、插入时用到
	 * add by qinyouzhen 
	 * @Date 2011-05-11
	 * @param row
	 */
	private void initProductCell(IRow row) {
		ICell cellValueAttribute = row.getCell("productType");
		cellValueAttribute.setValue(null);
		JTextField txtEnable = new JTextField();
		KDTDefaultCellEditor ceEnable = new KDTDefaultCellEditor(txtEnable);
		ceEnable = new KDTDefaultCellEditor(createPropertyNameF7());
		cellValueAttribute.setEditor(ceEnable);
		ObjectValueRender re = new ObjectValueRender();
		re.setFormat(new BizDataFormat("$number$"));
		cellValueAttribute.setRenderer(re);
	}

	/**
	 * output actionAddNew_actionPerformed
	 */
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		if (this.getUIContext().get("parentInfo") instanceof CurProjectInfo) {
			CurProjectInfo info = (CurProjectInfo) this.getUIContext().get(
					"parentInfo");
			// 查看状态新增的为下级
			FDCClientUtils.checkProjUsed(this, info.getId().toString());
		}
		ProjectTypeInfo type = (ProjectTypeInfo) bizProjectType.getData();

		super.actionAddNew_actionPerformed(e);
//		clearTable();
//		initData();
		if (type != null) {
			bizProjectType.setData(type);
		}
		if (editData != null && editData.getLevel() != 1) {
			bizProjectType.setEditable(false);
			bizProjectType.setEnabled(false);
		}
		
		storeFields();
		initOldData(editData);
	}

	/**
	 * output actionProductDelLine_actionPerformed method
	 */
	public void actionProductDelLine_actionPerformed(ActionEvent e)
			throws Exception {
		int i = -1;
		i = this.tblProductEntries.getSelectManager().getActiveRowIndex();
		if (i == -1) {
			MsgBox.showError(com.kingdee.eas.util.client.EASResource.getString(
					"com.kingdee.eas.fdc.basedata.FDCBaseDataResource",
					"Selected_Delete"));
			return;
		}

		/*
		 * 删除有核算对象标志的产品分录行的时候进行校验
		 */
		int activeRowIndex = tblProductEntries.getSelectManager()
				.getActiveRowIndex();
		if (activeRowIndex < 0)
			SysUtil.abort();
		Object productType = tblProductEntries.getCell(activeRowIndex,
				tblProductEntries.getColumn("productType").getColumnIndex())
				.getValue();
		if (productType != null) {
			int columnIndex = tblProductEntries.getColumn("isAccObj")
					.getColumnIndex();

			Object value = tblProductEntries.getCell(activeRowIndex,
					columnIndex).getValue();

			boolean b = ((Boolean) value).booleanValue();
			if (b) {
				String productTypeID = ((CoreBaseInfo) productType).getId()
						.toString();
				if (checkIsAcctObjRef(productTypeID)) {
					//直接提示不允许删除
					MsgBox.showWarning(this,"该产品类型已被成本数据引用，无法删除!");
					SysUtil.abort();
//					int result = MsgBox
//							.showConfirm3(EASResource
//									.getString(
//											"com.kingdee.eas.fdc.basedata.client.BasedataResource",
//											"splitRefCantEditDisab"));
//					if (result == MsgBox.NO || result == MsgBox.CANCEL) {
//						SysUtil.abort();
//					}
				}
			}
		}

		// 删除
		this.tblProductEntries.removeRow(i);
	}

	private void clearTable() {
		this.tblCostEntries.removeRows();
		this.tblProductEntries.removeRows();
		// this.tblProductEntries.removeColumns();
		// for (int i = 3; i < this.tblProductEntries.getColumnCount(); i++) {
		// this.tblProductEntries.removeColumn(3);
		// }
	}

	protected void unLockUI() {
		super.unLockUI();
		tblCostEntries.getStyleAttributes().setLocked(false);
		tblProductEntries.getStyleAttributes().setLocked(false);
		tblCostEntries.getColumn("apportionType").getStyleAttributes()
				.setLocked(true);
	}

	/**
	 * output actionSubmit_actionPerformed
	 */
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		txtNumber_focusLost(null);//先名称再编码，不触发事件导致保存空值，在这里手工触发事件
		super.actionSubmit_actionPerformed(e);

		CurProjProductEntriesCollection curProjProductEntries = editData
				.getCurProjProductEntries();
		for (Iterator iter = curProjProductEntries.iterator(); iter.hasNext();) {
			CurProjProductEntriesInfo element = (CurProjProductEntriesInfo) iter
					.next();
			String prodId = element.getProductType().getId().toString();
			isCompSettleMap.put(prodId, Boolean.valueOf(element
					.isIsCompSettle()));

		}
		if(STATUS_EDIT.equals(this.oprtState)){
			this.wbtProductDelLine.setEnabled(!this.editData.isIsEnabled());
		}
		
		storeFields();
		initOldData(editData);
	}

	protected void afterSubmitAddNew() {

		// /*
		// * 保存指标数据
		// */
		// try {
		// saveIdxData();
		// } catch (Exception e) {
		// handUIException(e);
		// }

		super.afterSubmitAddNew();

		int rowCount = tblCostEntries.getRowCount();
		for (int i = 0; i < rowCount; i++) {
			tblCostEntries.getCell(i, "value").setValue(null);
		}

		tblProductEntries.removeRows();
		storeFields();
		initOldData(editData);
	}

	/**
	 * output actionCancel_actionPerformed
	 */
	public void actionCancel_actionPerformed(ActionEvent e) throws Exception {
		// super.actionCancel_actionPerformed(e);
		if (this.editData != null && this.editData.getId() != null) {
			IObjectPK pk = new ObjectStringPK(this.editData.getId().toString());
			if (((ICurProject) getBizInterface()).disEnabled(pk)) {
				this.showResultMessage(EASResource.getString(
						FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE,
						"DisEnabled_OK"));
				
				setSave(true);
				chkIsEnabled.setSelected(false);
				
				this.btnCancelCancel.setVisible(true);
				this.btnCancelCancel.setEnabled(true);
				this.btnCancel.setVisible(false);

				this.menuItemCancelCancel.setVisible(true);
				this.menuItemCancelCancel.setEnabled(true);
				this.menuItemCancel.setVisible(false);

			}
		}
		if(STATUS_EDIT.equals(this.oprtState)){
			this.wbtProductDelLine.setEnabled(true);
		}
	}

	protected void showResultMessage(String message) {
		// setMessageText(EASResource.getString(message));
		setMessageText(message);
		// setMessageIcon(SHOW_MESSAGE_ICON_ERROR);
		// setMessageBgcolor(SHOW_MESSAGE_BG_ERROR);
		showMessage();
	}

	/**
	 * output actionCancelCancel_actionPerformed
	 */
	public void actionCancelCancel_actionPerformed(ActionEvent e)
			throws Exception {
		// super.actionCancelCancel_actionPerformed(e);
		if (this.editData != null && this.editData.getId() != null) {
			IObjectPK pk = new ObjectStringPK(this.editData.getId().toString());
			if (((ICurProject) getBizInterface()).enabled(pk)) {
				this.showResultMessage(EASResource.getString(
						FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE,
						"Enabled_OK"));
				
				setSave(true);
				this.chkIsEnabled.setSelected(true);
				
				this.btnCancel.setVisible(true);
				this.btnCancel.setEnabled(true);
				this.btnCancelCancel.setVisible(false);

				this.menuItemCancel.setVisible(true);
				this.menuItemCancel.setEnabled(true);
				this.menuItemCancelCancel.setVisible(false);
			}
		}
		if(STATUS_EDIT.equals(this.oprtState)){
			this.wbtProductDelLine.setEnabled(false);
		}
	}
	
	/**
	 * output actionEdit_actionPerformed
	 */
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		// if (STATUS_VIEW.equals(this.getOprtState())) {
		this.tblCostEntries.setEnabled(true);
		this.tblProductEntries.setEnabled(true);
		this.wbtProductAddLine.setEnabled(true);
		this.wbtProductDelLine.setEnabled(true);
		this.wbtProductInsertLine.setEnabled(true);
		// }
		super.actionEdit_actionPerformed(e);
		if (editData != null && editData.getLevel() != 1) {
			bizProjectType.setEditable(false);
			bizProjectType.setEnabled(false);
		}
		
		this.wbtProductDelLine.setEnabled(!editData.isIsEnabled());
//		setSortNoEnable();
	}

	/**
	 * output actionAttachment_actionPerformed
	 */
	public void actionAttachment_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionAttachment_actionPerformed(e);
	}

	protected IObjectValue createNewData() {
		if(getUIContext().get("parentInfo")==null){
			//无节点(组织或工程项目)不能新增
			throw new NullPointerException("parentInfo can't be null!");
		}
		CurProjectInfo info = new CurProjectInfo();
		info.setIsLeaf(true);

		if(getUIContext().get("parentInfo") instanceof CurProjectInfo){    //节点为工程项目，则新增下级工程项目
			CurProjectInfo prj=(CurProjectInfo)getUIContext().get("parentInfo");
			info.setParent(prj);
			info.setFullOrgUnit(prj.getFullOrgUnit());
			//启用状态,与上级同
			info.setIsEnabled(prj.isIsEnabled());
			//级别，上级级别+1
			info.setLevel(prj.getLevel()+1);
			//项目系列，与上级相同
			info.setProjectType(prj.getProjectType());
			//开发项目,与上级相同,不可修改
			info.setIsDevPrj(prj.isIsDevPrj());
			chkIsDevPrj.setEnabled(false);
			
		}else if(getUIContext().get("parentInfo") instanceof FullOrgUnitInfo){    //节点为组织，则新增一级工程项目    
			info.setFullOrgUnit((FullOrgUnitInfo)getUIContext().get("parentInfo"));
			//1级默认不启用
			info.setIsEnabled(false);
			info.setLevel(1);
			//默认为开发项目
			info.setIsDevPrj(true);
		}

		try {
			info.setProjectStatus(ProjectStatusInfo.getProjectStatueById(ProjectStatusInfo.notGetID));
			info.setSortNo(getMaxSortNo(info));
		} catch (Exception e) {
			handUIException(e);
		}
		
		Date now = DateTimeUtils.truncateDate(new Date());
		if(now.before(pkdate[0])){
			now = pkdate[0];
		}else if(now.after(pkdate[1])){
			now = pkdate[1];
		}
		
		info.setStartDate(now);
		return info;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return CurProjectFactory.getRemoteInstance();
	}

	protected void initOldData(IObjectValue dataObject) {
		//删除注释行  by sxhong 2009-04-16 21:14:03
		AbstractObjectValue objectValue = (AbstractObjectValue) dataObject;
		if (objectValue instanceof IObjectValueAgent) {
			oldData = (AbstractObjectValue) AgentUtility
					.deepCopyAgentValue((IObjectValueAgent) objectValue);
		} else {
			oldData = (AbstractObjectValue) objectValue.clone();
		}
		// super.initOldData(dataObject);
	}

	/**
	 * 判断当前编辑的数据是否发生变化
	 */
	public boolean isModify() {

		// 如果是Onload时的中断处理，则不会进行数据比较。2006-8-22 by psu_s
		// if(isOnLoadExceptionAbort)
		// {
		// return false;
		// }
		try {
			com.kingdee.bos.ctrl.common.util.ControlUtilities
					.checkFocusAndCommit();
		} catch (ParseException e) {
			handleControlException();
			// 工作流需要知道是否发生了异常
			// wfContext.setThrowException(true);

			abort();
		}

		// return false;
		/*
		 * 去掉这个判断。没有什么性能损耗。 2006-9-21 if(isSave()) { return false; }
		 */
		// 查看状态不判断是否修改
		if (OprtState.VIEW.equals(getOprtState())) {
			return false;
		}

		try {
			storeFields();
		} catch (Exception exc) {
			return false;
		}

		return !ObjectValueForEditUIUtil.objectValueEquals(oldData, editData);
		// return false;
	}

	public ProjectIndexDataInfo getCostIdxInfo() {
		return costIdxInfo;
	}

	public void setCostIdxInfo(ProjectIndexDataInfo costIdxInfo) {
		this.costIdxInfo = costIdxInfo;
	}

	public ProjectIndexDataCollection getProductIdxColl() {
		return productIdxColl;
	}

	public void setProductIdxColl(ProjectIndexDataCollection productIdxColl) {
		this.productIdxColl = productIdxColl;
	}

	/**
	 * 对是否核算对象通过Table Click事件进行值的改变
	 */
	protected void tblProductEntries_tableClicked(KDTMouseEvent e)
			throws Exception {

		if (getOprtState() == STATUS_VIEW)
			return;
		if (tblProductEntries.getRowCount() == 0)
			return;

		if (e.getRowIndex() < 0)
			return;
		/*
		 * 取消是否核算对象标志的时候进行校验
		 */
		int isAccObjColumnIndex = tblProductEntries.getColumn("isAccObj")
				.getColumnIndex();
		int isCompSettleColumnIndex = tblProductEntries.getColumn(
				"isCompSettle").getColumnIndex();
		Object value = tblProductEntries.getCell(e.getRowIndex(),
				isAccObjColumnIndex).getValue();
		Object csValue = tblProductEntries.getCell(e.getRowIndex(),
				isCompSettleColumnIndex).getValue();
		boolean b = ((Boolean) value).booleanValue();
		boolean csB = ((Boolean) csValue).booleanValue();

		if (e.getColIndex() == isAccObjColumnIndex
				|| e.getColIndex() == tblProductEntries
						.getColumn("productType").getColumnIndex()) {
			Object productType = tblProductEntries
					.getCell(
							e.getRowIndex(),
							tblProductEntries.getColumn("productType")
									.getColumnIndex()).getValue();
			if (productType == null)
				return;

			if (b) {
				//去除点击产品类型列时的提示。
//				String productTypeID = ((CoreBaseInfo) productType).getId()
//						.toString();
//				if (checkIsAcctObjRef(productTypeID)) {
//
//					int result = MsgBox
//							.showConfirm3(EASResource
//									.getString(
//											"com.kingdee.eas.fdc.basedata.client.BasedataResource",
//											"splitRefCantEditDisab"));
//					if (result == MsgBox.NO || result == MsgBox.CANCEL) {
//						SysUtil.abort();
//					}
//				}
			}
		}
		if (e.getColIndex() == isAccObjColumnIndex) {
			// 对值进行改变
			tblProductEntries.getCell(e.getRowIndex(), isAccObjColumnIndex)
					.setValue(Boolean.valueOf(!b));
		}

		if (e.getColIndex() == isCompSettleColumnIndex) {
			Object productType = tblProductEntries
					.getCell(
							e.getRowIndex(),
							tblProductEntries.getColumn("productType")
									.getColumnIndex()).getValue();
			if (productType == null)
				return;

			if (csB) {
				String productTypeID = ((CoreBaseInfo) productType).getId()
						.toString();
				Boolean isCS = (Boolean) isCompSettleMap.get(productTypeID);
				if (isCS != null && isCS.booleanValue()) { // 不能修改
					return;
				}

				tblProductEntries.getCell(e.getRowIndex(), "compArea")
						.getStyleAttributes().setBackground(Color.WHITE);
				tblProductEntries.getCell(e.getRowIndex(), "compDate")
						.getStyleAttributes().setBackground(Color.WHITE);
			} else {
				tblProductEntries.getCell(e.getRowIndex(), "compArea")
						.getStyleAttributes().setBackground(
								FDCHelper.KDTABLE_TOTAL_BG_COLOR);
				tblProductEntries.getCell(e.getRowIndex(), "compDate")
						.getStyleAttributes().setBackground(
								FDCHelper.KDTABLE_TOTAL_BG_COLOR);
			}

			// 对值进行改变
			tblProductEntries.getCell(e.getRowIndex(), isCompSettleColumnIndex)
					.setValue(Boolean.valueOf(!csB));

		}
	}
	/**
	 * add by qinyouzhen 2011-05-11,编辑页面添加产品类型代码并排序
	 */
	protected void tblProductEntries_editStopped(KDTEditEvent e)
			throws Exception {
		int productTypeColumnIndex = tblProductEntries.getColumn("productType")
				.getColumnIndex();
		if (e.getColIndex() == productTypeColumnIndex) {
			Object obj = tblProductEntries.getCell(e.getRowIndex(),
					productTypeColumnIndex).getValue();
			if (obj != null && obj instanceof ProductTypeInfo) {
				tblProductEntries.getCell(e.getRowIndex(), "name").setValue(
						((ProductTypeInfo) obj).getName());
			}
		}
	}

	/**
	 * 检查产品分录中的作为核算对象的产品类型是否已被拆分单据引用(只校验合同和变更拆分就可以了)
	 * 2008-10-31 加入目标成本测算 可研成本测算 动态成本分摊 和 无文本合同付款拆分数据的校验
	 * @param productTypeID
	 * @return
	 * @throws BOSException
	 * @throws SQLException
	 */
	private boolean checkIsAcctObjRef(String productTypeID)
			throws BOSException, SQLException {
		//校验合同拆分
		String sql = "select a.fid from T_CON_ContractCostSplitEntry a inner join T_FDC_CostAccount b on a.FCostAccountID = b.FID where b.FCurProject = ? and a.FProductID = ?";
		List params = new ArrayList();
		params.add(getSelectBOID());
		params.add(productTypeID);
		IRowSet rowSet = FDCSQLFacadeFactory.getRemoteInstance().executeQuery(
				sql, params);
		if(rowSet.next())
			return true;
		//校验变更拆分
		sql = "select a.fid from T_CON_ConChangeSplitEntry a inner join T_FDC_CostAccount b on a.FCostAccountID = b.FID where b.FCurProject = ? and a.FProductID = ?";
		rowSet = FDCSQLFacadeFactory.getRemoteInstance().executeQuery(sql,
				params);
		if(rowSet.next())
			return true;
		
		//成本测算
		sql = "select a.fid from T_AIM_PlanIndexEntry a "+
			"inner join  T_AIM_PlanIndex b on a.FParentId = b.fid "+
			"inner join T_Aim_MeasureCost c on b.fheadid = c.fid "+
			"where c.FIsLastVersion = 1 "+
			"and c.fprojectid = ? and a.FProductID = ?";
		rowSet = FDCSQLFacadeFactory.getRemoteInstance().executeQuery(sql,
				params);
		if(rowSet.next())
			return true;
		//校验目标成本测算
		sql = "select a.fid from T_AIM_MeasureEntry a " +
		"inner join T_AIM_MeasureCost m on a.FHeadID = m.FID " +
		"inner join T_FDC_CostAccount b on a.FCostAccountID = b.FID " +
		"where m.FIsLastVersion = 1 and  b.FCurProject = ? and a.FProductID = ?";
		rowSet = FDCSQLFacadeFactory.getRemoteInstance().executeQuery(sql,
				params);
		if(rowSet.next())
			return true;

		//校验目标成本
		sql = "select a.fid from T_AIM_CostEntry a " +
		"inner join T_AIM_AimCost m on a.FHeadID = m.FID " +
		"inner join T_FDC_CostAccount b on a.FCostAccountID = b.FID " +
		"where m.FIsLastVersion = 1 and  b.FCurProject = ? and a.FProductID = ?";
		rowSet = FDCSQLFacadeFactory.getRemoteInstance().executeQuery(sql,
				params);
		if(rowSet.next())
			return true;

		//校验付款拆分
		sql = "select a.fid from T_FNC_PaymentSplitEntry a " +
		"inner join T_FDC_CostAccount b on a.FCostAccountID = b.FID " +
		" where b.FCurProject = ? and a.FProductID = ?";
		rowSet = FDCSQLFacadeFactory.getRemoteInstance().executeQuery(sql,
				params);
		if(rowSet.next())
			return true;

		//校验动态成本调整
		sql = "select a.fid from T_AIM_AdjustRecordEntry a " +
		"inner join T_AIM_DynamicCost b on a.FParentID = b.FID " +
		"inner join T_FDC_CostAccount c on b.FAccountID = c.FID "+
		" where c.FCurProject = ? and a.FProductID = ?";
		rowSet = FDCSQLFacadeFactory.getRemoteInstance().executeQuery(sql,
				params);
		if(rowSet.next())
			return true;
		
		//校验动态成本待发生
		sql = "select a.fid from T_AIM_IntendingCostEntry a " +
		"inner join T_AIM_DynamicCost b on a.FParentID = b.FID " +
		"inner join T_FDC_CostAccount c on b.FAccountID = c.FID "+
		" where c.FCurProject = ? and a.FProductID = ?";
		rowSet = FDCSQLFacadeFactory.getRemoteInstance().executeQuery(sql,
				params);
		if(rowSet.next())
			return true;
		//都没有则返回false	
		return false;
//		boolean contractHas = rowSet.next();
//		boolean conChangeHas = false;
//		if (!contractHas) {
//			sql = "select a.fid from T_CON_ConChangeSplitEntry a inner join T_FDC_CostAccount b on a.FCostAccountID = b.FID where b.FCurProject = ? and a.FProductID = ?";
//			rowSet = FDCSQLFacadeFactory.getRemoteInstance().executeQuery(sql,
//					params);
//			conChangeHas = rowSet.next();
//		}
//
//		return contractHas || conChangeHas;
	}

	public void setOprtState(String oprtType) {
		super.setOprtState(oprtType);
		if (STATUS_VIEW.equals(this.getOprtState())) {
			setTableWorkBtnEnabled(false);
		} else {
			setTableWorkBtnEnabled(true);
		}
		if (!isFinacial
				&& (STATUS_ADDNEW.equals(oprtType) || (STATUS_EDIT
						.equals(oprtType)))) {
			bizProjectStatus.setEnabled(true);
		} else {
			bizProjectStatus.setEnabled(false);
		}
		setSortNoEnable();
	}

	private void setTableWorkBtnEnabled(boolean b) {
		this.tblCostEntries.setEnabled(b);
		this.tblProductEntries.setEnabled(b);
		this.wbtProductAddLine.setEnabled(b);
		this.wbtProductDelLine.setEnabled(b);
		this.wbtProductInsertLine.setEnabled(b);
	}

	protected void bizProjectStatus_willShow(SelectorEvent e) throws Exception {
		if (STATUS_ADDNEW.equals(this.getOprtState())) {
			return;
		}
		CurProjectInfo info = (CurProjectInfo) getBizInterface().getValue(
				new ObjectUuidPK(editData.getId()));
		if (info.getProjectStatus() != null) {
			// 已获取
			if (info.getProjectStatus().getId().toString().equals(
					"COR8bAETEADgAADcwKgOlI160rk=")) {
				bizProjectStatus.getQueryAgent().resetRuntimeEntityView();
				EntityViewInfo evi = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(
						new FilterItemInfo("id",
								"COR8bAETEADgAADYwKgOlI160rk=",
								CompareType.NOTEQUALS));
				evi.setFilter(filter);
				bizProjectStatus.setEntityViewInfo(evi);
			}
			// 竣工结算
			else if (info.getProjectStatus().getId().toString().equals(
					"COR8bAETEADgAADkwKgOlI160rk=")) {
				bizProjectStatus.getQueryAgent().resetRuntimeEntityView();
				EntityViewInfo evi = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(
						new FilterItemInfo("id",
								"COR8bAETEADgAADYwKgOlI160rk=",
								CompareType.NOTEQUALS));
				filter.getFilterItems().add(
						new FilterItemInfo("id",
								"COR8bAETEADgAADcwKgOlI160rk=",
								CompareType.NOTEQUALS));
				evi.setFilter(filter);
				bizProjectStatus.setEntityViewInfo(evi);
			}
			// 已关闭
			else if (info.getProjectStatus().getId().toString().equals(
					"COR8bAETEADgAADowKgOlI160rk=")) {
				bizProjectStatus.getQueryAgent().resetRuntimeEntityView();
				EntityViewInfo evi = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(
						new FilterItemInfo("id",
								"COR8bAETEADgAADYwKgOlI160rk=",
								CompareType.NOTEQUALS));
				filter.getFilterItems().add(
						new FilterItemInfo("id",
								"COR8bAETEADgAADcwKgOlI160rk=",
								CompareType.NOTEQUALS));
				evi.setFilter(filter);
				bizProjectStatus.setEntityViewInfo(evi);
			}
		}
	}

	/**
	 * 设置开发顺序sortNo不可以编辑
	 */
	public void setSortNoEnable(){
		if(OprtState.ADDNEW.equals(this.getOprtState())||OprtState.EDIT.equals(this.getOprtState())){
			this.txtSortNo.setEnabled(true);
		}else{
			this.txtSortNo.setEnabled(false);
			return;
		}
		if(this.editData==null){
			return;
		}
		Set set = new HashSet();
		set.add(ProjectStatusInfo.flowID);
		set.add(ProjectStatusInfo.settleID);
		set.add(ProjectStatusInfo.transferID);
		set.add(ProjectStatusInfo.closeID);
		//如果不是叶节点，或是有set中状态都是sortNo不可编辑
		if(!this.editData.isIsLeaf()){
			this.txtSortNo.setEnabled(false);
		}else{
			if(this.editData.getProjectStatus()!=null){
				String statusId = this.editData.getProjectStatus().getId().toString();
				if(set.contains(statusId)){
					this.txtSortNo.setEnabled(false);
				}
			}
		}
	}
	
	/**
	 * 返回下一个sortNo，最大的sortNo+1 或者 1
	 *  by sxhong 2009-04-16 21:07:31
	 * @param info
	 * @return
	 * @throws BOSException 
	 */
	private int getMaxSortNo(CurProjectInfo info) throws BOSException{
		FDCSQLBuilder builder=new FDCSQLBuilder();
		builder.appendSql("select max(fsortNo) fsortNo from T_FDC_CurProject where (fparentId is null or fparentid=?) and ffullorgUnit=?");
		builder.addParam(info.getParent()!=null?info.getParent().getId().toString():"never");
		builder.addParam(info.getFullOrgUnit().getId().toString());
		IRowSet rowSet = builder.executeQuery();
		try{
			if(rowSet.next()){
				return rowSet.getInt("fsortNo")+1;
			}
		}catch(SQLException e){
			throw new BOSException(e);
		}
		return 1;
	}
	
	/**
	 * 编辑页面，根据产品类型代码并排序
	 * add by qinyouzhen, 2011-05-11
	 * @param col
	 */
	private void sortEntries(CurProjProductEntriesCollection cpe) {
		Object[] array = cpe.toArray();
		Arrays.sort(array, new EntriesComparator());
		cpe.clear();
		for (int i = 0; i < array.length; i++) {
			cpe.add((CurProjProductEntriesInfo) array[i]);
		}
	}
	
	/**
	 * 根据产品类型代码并排序，任意两笔记录的比较
	 * @author youzhen_qin, 2011-05-11
	 */
	private class EntriesComparator implements Comparator {
		public int compare(Object obj1, Object obj2) {
			if (obj1 instanceof CurProjProductEntriesInfo
					&& obj2 instanceof CurProjProductEntriesInfo) {
				String num1 = ((CurProjProductEntriesInfo) obj1)
						.getProductType().getNumber();
				String num2 = ((CurProjProductEntriesInfo) obj2)
						.getProductType().getNumber();
				return num1.compareTo(num2);
			}
			return 0;
		}
	}
}
