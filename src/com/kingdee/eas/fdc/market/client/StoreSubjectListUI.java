/**
 * output package name
 */
package com.kingdee.eas.fdc.market.client;

import java.awt.BorderLayout;
import java.awt.LayoutManager;
import java.awt.LayoutManager2;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JScrollPane;
import javax.swing.tree.TreePath;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.swing.KDLayout;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.dtp.model.layout.XYLayout;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.market.DocumentItemCollection;
import com.kingdee.eas.fdc.market.DocumentItemInfo;
import com.kingdee.eas.fdc.market.DocumentOptionCollection;
import com.kingdee.eas.fdc.market.DocumentOptionHorizonLayoutEnum;
import com.kingdee.eas.fdc.market.DocumentOptionInfo;
import com.kingdee.eas.fdc.market.DocumentOptionLayoutEnum;
import com.kingdee.eas.fdc.market.DocumentSubjectCollection;
import com.kingdee.eas.fdc.market.DocumentSubjectInfo;
import com.kingdee.eas.fdc.market.DocumentSubjectTypeEnum;
import com.kingdee.eas.fdc.market.IStoreSubject;
import com.kingdee.eas.fdc.market.QuestionTypeInfo;
import com.kingdee.eas.fdc.market.StoreItemCollection;
import com.kingdee.eas.fdc.market.StoreItemInfo;
import com.kingdee.eas.fdc.market.StoreOptionCollection;
import com.kingdee.eas.fdc.market.StoreOptionInfo;
import com.kingdee.eas.fdc.market.StoreSubjectClassFactory;
import com.kingdee.eas.fdc.market.StoreSubjectClassInfo;
import com.kingdee.eas.fdc.market.StoreSubjectCollection;
import com.kingdee.eas.fdc.market.StoreSubjectFactory;
import com.kingdee.eas.fdc.market.StoreSubjectInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SpecialDiscountFactory;
import com.kingdee.eas.fdc.sellhouse.SpecialDiscountInfo;
import com.kingdee.eas.fdc.sellhouse.client.FDCTreeHelper;
import com.kingdee.eas.fdc.sellhouse.client.SHEHelper;
import com.kingdee.eas.fdc.tenancy.ITenancyEntryInfo;
import com.kingdee.eas.fdc.tenancy.ITenancyPayListInfo;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.eas.framework.client.tree.KDTreeNode;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.tool.simplecode.Instanceof;


/**
 * output class name
 */
public class StoreSubjectListUI extends AbstractStoreSubjectListUI {

	private static final Logger logger = CoreUIObject.getLogger(StoreSubjectListUI.class);

	public final static String can_ToQuestion_Paper = "documentSubjectInfo";

	private StoreSubjectClassInfo storeSubjectClass = null;

	DocumentSubjectCollection documentSubjectInfo;
	Set choose=new HashSet();
	boolean duringLoading = true;
	XDocument xDocument;
	JScrollPane xScrollPane;
	XDocument fromxDocument;
	/**
	 * output class constructor
	 */
	public StoreSubjectListUI() throws Exception {
		super();
	}

	protected String getEditUIModal() {
		return UIFactoryName.MODEL;
	}

	public void setDataObject(IObjectValue dataObject) {

		super.setDataObject(dataObject);
	}

	public void setDataObject(String key, IObjectValue objectValue) {

		super.setDataObject(key, objectValue);
//		if (can_ToQuestion_Paper.equals(key)) {
//			documentSubjectInfo = (DocumentSubjectCollection) objectValue;
//		}
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	/**
	 * onLoad中初始化Tree
	 */
	public void onLoad() throws Exception {
		duringLoading = true;
		//
		xDocument = new XDocument();
		//src start 题库排序
		//xDocument.setEnableRightMouseEvent(false);
		this.setUITitle("问卷题库");
		//src end
		xScrollPane = new JScrollPane(xDocument);

		mainDocPanel.setLayout(new BorderLayout());
		
		if(this.getUIContext().get("FromQuestionDefine")!=null){//从问卷定义打开
				this.setLayout(null);
				pnlMain.setBounds(new Rectangle(6, 7, 787, 616));
		}
		if(this.getUIContext().get("xDocument")!=null){
			fromxDocument=(XDocument) this.getUIContext().get("xDocument");
		}
		if(this.getUIContext().get(can_ToQuestion_Paper)!=null){
			documentSubjectInfo=(DocumentSubjectCollection) this.getUIContext().get(can_ToQuestion_Paper);
		}
		mainDocPanel.add(xScrollPane, BorderLayout.CENTER);
		xDocument.setEmptyDoc();
		
		super.onLoad();

		if (documentSubjectInfo != null) {
			this.toQuestionDefine.setVisible(true);
			QuestionTypeInfo questionType = (QuestionTypeInfo) this.getUIContext().get("questionType");
			if (questionType != null) {
				DefaultKingdeeTreeNode orgNode = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
				if(!orgNode.isRoot()){
					orgNode.setUserObject(questionType);
				}
			}
		} else {
			this.toQuestionDefine.setVisible(false);
		}
		
		duringLoading = false;
		this.actionQuery.setVisible(false);
		this.actionLocate.setVisible(false);
		this.actionPrint.setVisible(false);
		this.actionPrintPreview.setVisible(false);
		this.actionMoveTree.setVisible(false);
		this.chkIncludeChild.setVisible(false);
		
		//this.tblMain.setVisible(true);
		//this.kDSplitPane1.setDividerLocation(300);
		SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
		if (!saleOrg.getId().toString().equals(OrgConstants.DEF_CU_ID)) {
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
			
			this.treeView.setShowControlPanel(false);
		}	
	}
	protected void setNodeChange(boolean flag){
		this.btnAddNew.setEnabled(flag);
		this.btnEdit.setEnabled(flag);
		this.btnRemove.setEnabled(flag);
		this.btnView.setEnabled(flag);
		this.btnsaveSubNumber.setEnabled(flag);
	}
	protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception {
		//如果当选择根节点时清空时，放开下面的代码		
		if(getSelectedTreeNode().isRoot()){
			if(xDocument.getDoc()!=null){
				xDocument.setEmptyDoc();
			}
			//src start
			setNodeChange(false);
			//src end
			return;
		}else{
			//src start
			setNodeChange(true);
			//src end
		}
		
		
 	super.treeMain_valueChanged(e);
/*		if (duringLoading || getSelectedTreeNode() == null || getSelectedTreeNode().isRoot()) {
			return;
		}
		buildTreeFilter();
		DefaultKingdeeTreeNode orgNode = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (orgNode != null && orgNode.getUserObject() instanceof StoreSubjectClassInfo) {
			StoreSubjectClassInfo storeSubjectClassInfo = (StoreSubjectClassInfo) orgNode.getUserObject();
			if (storeSubjectClassInfo != null) {
				EntityViewInfo view = new EntityViewInfo();
				SelectorItemCollection sel = this.getSelectors();
				view.setSelector(sel);
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("storeSubClass.id", storeSubjectClassInfo.getId().toString()));
				view.setFilter(filter);
				view.getSorter().add(new SorterItemInfo("items.itemNumber"));
				view.getSorter().add(new SorterItemInfo("items.options.optionNumber"));
				IStoreSubject remote = StoreSubjectFactory.getRemoteInstance();
				StoreSubjectCollection ssc = remote.getStoreSubjectCollection(view);
//				if (ssc != null && ssc.size() > 0) {
					DocumentSubjectCollection dsc = convertStor2Doc(ssc);
					xDocument.getDoc().getSubjects().clear();
					xDocument.getDoc().getSubjects().addCollection(dsc);
					xDocument.reBuild();
//				}
				this.storeSubjectClass = storeSubjectClassInfo;
			}
		}*/
		
		this.execQuery();
	}

	public SelectorItemCollection getSelectors() {
		super.getSelectors();
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("topic"));
		sic.add(new SelectorItemInfo("storeSubClass.*"));
		sic.add(new SelectorItemInfo("subjectType"));
		sic.add(new SelectorItemInfo("xCellCreter"));
		//src start
		sic.add(new SelectorItemInfo("subjectNumber"));
		//src end
		sic.add(new SelectorItemInfo("items.*"));
		sic.add(new SelectorItemInfo("items.topic"));
		sic.add(new SelectorItemInfo("items.itemNumber"));
		sic.add(new SelectorItemInfo("items.id"));
		sic.add(new SelectorItemInfo("items.subjectId.*"));
		sic.add(new SelectorItemInfo("items.options.*"));
		sic.add(new SelectorItemInfo("items.options.itemId"));
		sic.add(new SelectorItemInfo("items.options.optionNumber"));
		sic.add(new SelectorItemInfo("items.options.isTopicInverse"));
		sic.add(new SelectorItemInfo("items.options.xHeight"));
		sic.add(new SelectorItemInfo("items.options.xLength"));
		return sic;
	}

	private DocumentSubjectCollection convertStor2Doc(StoreSubjectCollection ssc) {
		DocumentSubjectCollection dsc = new DocumentSubjectCollection();
		for (int i = 0; i < ssc.size(); i++) {
			DocumentSubjectInfo dsi = convertStor2Doc(ssc.get(i));
			dsi.setShowNumber(i + 1);
			dsi.setIsShowNumber(1);
			dsi.setSubjectNumber(i + 1);// 序号必须从1开始
			dsc.add(dsi);
		}

		return dsc;
	}

	private DocumentSubjectInfo convertStor2Doc(StoreSubjectInfo ssi) {
		DocumentSubjectInfo dsi = new DocumentSubjectInfo();
		dsi.setTopic(ssi.getTopic());
		dsi.setSubjectType(ssi.getSubjectType());
		dsi.setXCellCreter(ssi.getXCellCreter());
		//src start 题库排列方式
		if(ssi.getAlignType()==null||ssi.getAlignType().equals("")){
			dsi.setAlignType(DocumentOptionLayoutEnum.ALIGN_TYPE_LINE);
		}else{
			dsi.setAlignType(ssi.getAlignType());
		}
		//src end
		dsi.setHorizontalAlign(DocumentOptionHorizonLayoutEnum.HORIZONTAL_ALIGN_LEFT);
		dsi.setXFontSize(14);
		dsi.setColumnCount(1);
		dsi.setId(ssi.getId());
		//
		DocumentItemCollection dic = dsi.getItems();
		StoreItemCollection sic = ssi.getItems();
		for (int itemI = 0; itemI < sic.size(); itemI++) {
			StoreItemInfo sii = sic.get(itemI);
			DocumentItemInfo dii = new DocumentItemInfo();
			dii.setTopic(sii.getTopic());
			dii.setItemNumber(itemI);// sii.getItemNumber().intValue()
			dii.setXFontSize(14);
			// dii.setId(sii.getId());
			// dii.setSubjectId(sii.getSubjectId());
			dic.add(dii);
			//
			DocumentOptionCollection doc = dii.getOptions();
			StoreOptionCollection soc = sii.getOptions();
			sortStoreOtionCollectionByoptionNumber(soc);
			for (int optionI = 0; optionI < soc.size(); optionI++) {
				StoreOptionInfo soi = soc.get(optionI);
				DocumentOptionInfo doi = new DocumentOptionInfo();
				doi.setTopic(soi.getTopic());
				doi.setIsTopicInverse(soi.isIsTopicInverse());
				doi.setXLength(soi.getXLength() == null ? 0 : soi.getXLength().intValue());
				doi.setOptionNumber(optionI);
				doi.setXFontSize(12);
				doc.add(doi);
			}

		}

		return dsi;
	}

	private void sortStoreOtionCollectionByoptionNumber(StoreOptionCollection soc) {

		Object[] toSortData = soc.toArray();
		Arrays.sort(toSortData, new Comparator() {
			public int compare(Object arg0, Object arg1) {
				StoreOptionInfo pay0 = (StoreOptionInfo) arg0;
				StoreOptionInfo pay1 = (StoreOptionInfo) arg1;
				if (pay0 == null || pay1 == null) {
					return 0;
				}
				return pay0.getOptionNumber().compareTo(pay1.getOptionNumber());
			}
		});
		soc.clear();
		for (int j = 0; j < toSortData.length; j++) {
			soc.addObject((IObjectValue) toSortData[j]);
		}
	}

	/**
	 * 响应按钮“添加到试卷”的事件
	 */
	protected void toQuestionDefine_actionPerformed(ActionEvent e) throws Exception {

		if (documentSubjectInfo != null) {
			DocumentSubjectInfo selectedSubject = xDocument.getSelectedSubjectInfo();
			if (selectedSubject != null) {
				if(choose.contains(selectedSubject.getId().toString())){
					MsgBox.showInfo("所选题目重复，请重新从题库选题！");
					SysUtil.abort();
				}else{
					choose.add(selectedSubject.getId().toString());
				}
				DocumentSubjectInfo dThree = new DocumentSubjectInfo();
				dThree.setXFontName("Dialog");
				dThree.setTopic("");
				dThree.setXFontSize(12);
				dThree.setColumnCount(1);
				dThree.setSubjectNumber(0);// 对于新增数据，这里必须设置数据小于1
				dThree.setShowNumber(1);
				dThree.setIsShowNumber(1);
				dThree.setSubjectType(DocumentSubjectTypeEnum.SUBJECT_TYPE_SINGLE);
				dThree.setHorizontalAlign(DocumentOptionHorizonLayoutEnum.HORIZONTAL_ALIGN_LEFT);
				dThree.setXCellCreter("com.kingdee.eas.fdc.market.client.XCellCommonCreater");
				dThree.copyNewToOld();
				fillDoc2Doc(dThree, selectedSubject);
				
				DocumentSubjectCollection dsCollection = fromxDocument.getDoc().getSubjects();
				for(int i = 0;i<dsCollection.size();i++){
					DocumentSubjectInfo info = dsCollection.get(i);
					if(dThree.getTopic().equals(info.getTopic())&&dThree.getSubjectType().equals(info.getSubjectType())){
						MsgBox.showInfo("所选题目重复，请重新从题库选题！");
						SysUtil.abort();
					}
				}
				documentSubjectInfo.add(dThree);
				MsgBox.showInfo("添加成功！");
			} else {
				MsgBox.showError("请选择题目.");
			}
		}
	}

	/**
	 * 将选择的题目属性设置到目标上
	 */
	private void fillDoc2Doc(DocumentSubjectInfo dest, DocumentSubjectInfo sour) {
		dest.setTopic(sour.getTopic());
		dest.setSubjectType(sour.getSubjectType());
		dest.setXCellCreter(sour.getXCellCreter());
		//src start
//		dest.setAlignType(DocumentOptionLayoutEnum.ALIGN_TYPE_LINE);
		if(sour.getAlignType()!=null){
			dest.setAlignType(sour.getAlignType());
		}else{
			dest.setAlignType(DocumentOptionLayoutEnum.ALIGN_TYPE_LINE);
		}
		//src end
		dest.setHorizontalAlign(DocumentOptionHorizonLayoutEnum.HORIZONTAL_ALIGN_LEFT);
		dest.setXFontSize(sour.getXFontSize());
		dest.setColumnCount(1);
		dest.setSubjectNumber(-1);
		//
		DocumentItemCollection dic = dest.getItems();
		DocumentItemCollection sic = sour.getItems();
		for (int itemI = 0; itemI < sic.size(); itemI++) {
			DocumentItemInfo sii = sic.get(itemI);
			DocumentItemInfo dii = new DocumentItemInfo();
			dii.setTopic(sii.getTopic());
			dii.setItemNumber(sii.getItemNumber());//sii.getItemNumber().intValue
			// ()
			dii.setXFontSize(sii.getXFontSize());
			dic.add(dii);
			//
			DocumentOptionCollection doc = dii.getOptions();
			DocumentOptionCollection soc = sii.getOptions();
			for (int optionI = 0; optionI < soc.size(); optionI++) {
				DocumentOptionInfo soi = soc.get(optionI);
				DocumentOptionInfo doi = new DocumentOptionInfo();
				doi.setTopic(soi.getTopic());
				doi.setIsTopicInverse(soi.isIsTopicInverse());
				doi.setXLength(soi.getXLength());
				doi.setOptionNumber(soi.getOptionNumber());//soi.getOptionNumber
				// ().intValue()
				doi.setXFontSize(soi.getXFontSize());
				doc.add(doi);
			}

		}
	}

	private void synchronizeDocAndList() {
		DocumentSubjectInfo subjInfo = xDocument.getSelectedSubjectInfo();
		//getSelectedKeyValue();
		if (subjInfo != null && subjInfo.getId() != null) {
			String subjId = subjInfo.getId().toString();
			int rowCount = tblMain.getRowCount();
			String keyFieldName = getKeyFieldName();
			for (int i = 0; i < rowCount; i++) {
				String id = (String) tblMain.getCell(i, keyFieldName).getValue();
				if (id != null && subjInfo.getId() != null && id.equals(subjId)) {
					KDTableUtil.setSelectedRow(this.tblMain, i);
				}
			}
		}
	}

	/**
	 * output actionView_actionPerformed
	 */
	public void actionView_actionPerformed(ActionEvent e) throws Exception {
		//this.checkSelected();
		synchronizeDocAndList();
		
		DefaultKingdeeTreeNode thisNode = (DefaultKingdeeTreeNode)treeMain.getLastSelectedPathComponent();	
		super.actionView_actionPerformed(e);	
		this.treeMain.setSelectionNode(thisNode);
	}

	/**
	 * output actionEdit_actionPerformed
	 */
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		//this.checkSelected();
		synchronizeDocAndList();
		
		checkBeforEditOrDelete();
		
		super.actionEdit_actionPerformed(e);
		this.execQuery();
	}

	private void checkBeforEditOrDelete() throws Exception {
		DocumentSubjectInfo subjInfo = xDocument.getSelectedSubjectInfo();
		String idStr = getSelectedKeyValue();		
		if (subjInfo != null && subjInfo.getId() != null) {
			idStr = subjInfo.getId().toString();
		}
		if(idStr!=null)
			StoreSubjectEditUI.checkBeforEditOrDelete((StoreSubjectInfo)getBizInterface().getValue("select * where id = '"+idStr+"'"));
	}
	
	
	/**
	 * output actionRemove_actionPerformed
	 */
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		//this.checkSelected();
		synchronizeDocAndList();
		
		checkBeforEditOrDelete();
		super.actionRemove_actionPerformed(e);
	}
	


	/**
	 * output getBizInterface method
	 */
	protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception {
		return com.kingdee.eas.fdc.market.StoreSubjectFactory.getRemoteInstance();
	}

	/**
	 * output createNewData method
	 */
	protected com.kingdee.bos.dao.IObjectValue createNewData() {
		com.kingdee.eas.fdc.market.StoreSubjectInfo objectValue = new com.kingdee.eas.fdc.market.StoreSubjectInfo();
		objectValue.setXCellCreter("com.kingdee.eas.fdc.market.client.XCellCommonCreater");
		objectValue.setStoreSubClass(storeSubjectClass);
		return objectValue;
	}

	protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
		uiContext.put("storeSubjectClass", storeSubjectClass);
		//super.prepareUIContext(uiContext, e);
	}

	protected ITreeBase getTreeInterface() throws Exception {
		return StoreSubjectClassFactory.getRemoteInstance();
	}

	protected String getRootName() {
		return "题库题目类别";
	}

	public void initUIContentLayout() {
		super.initUIContentLayout();
		pnlMain.setDividerLocation(210);
	}

	protected IObjectPK getSelectedTreeKeyValue() {
		try {
			return new ObjectUuidPK(getSelectedNodeKeyValue());
		} catch (Exception ex) {
			handUIException(ex);
			return null;
		}
	}

	protected String getGroupEditUIName() {
		return StoreSubjectClassEditUI.class.getName();
	}

	protected String getQueryFieldName() {
		return "storeSubClass.id";
	}

	public void actionGroupRemove_actionPerformed(ActionEvent e) throws Exception {
		checkTreeNodeSelected(e);
		KDTreeNode treeNode = (KDTreeNode) treeMain.getLastSelectedPathComponent();
		if (treeNode.getUserObject() instanceof StoreSubjectClassInfo) {
			StoreSubjectClassInfo info = (StoreSubjectClassInfo) treeNode.getUserObject();
			if (info != null) {
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("storeSubClass.id", info.getId().toString()));
				if (StoreSubjectFactory.getRemoteInstance().exists(filter)) {
					MsgBox.showInfo("题目类别已被题目引用，不能删除！");
					return;
				}
			}
		}

		super.actionGroupRemove_actionPerformed(e);
	}
	
    protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK, EntityViewInfo viewInfo) {
    	StoreSubjectClassInfo storeSubjectClassInfo = null;
    	DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode)treeMain.getLastSelectedPathComponent();
		if(node!=null && node.getUserObject() instanceof StoreSubjectClassInfo) {
			storeSubjectClassInfo = (StoreSubjectClassInfo) node.getUserObject();
		}
		this.storeSubjectClass = storeSubjectClassInfo;    		
    	
    	FilterInfo treeFilter = new FilterInfo();
		if(storeSubjectClassInfo !=null) {
			treeFilter.getFilterItems().add(new FilterItemInfo("storeSubClass.id", storeSubjectClassInfo.getId().toString()));
		}else {
			treeFilter.getFilterItems().add(new FilterItemInfo("storeSubClass.id", null));
		}	
    	
		viewInfo = (EntityViewInfo) this.mainQuery.clone();
		if (viewInfo.getFilter() != null) {
			try{
				viewInfo.getFilter().mergeFilter(treeFilter, "and");
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			viewInfo.setFilter(treeFilter);
		}
		
		//src start
		SorterItemCollection  col = new SorterItemCollection();
        col.add(new SorterItemInfo("subjectNumber"));
        viewInfo.setSorter(col);
		//src end
		
		IStoreSubject remote;
		try {
			remote = StoreSubjectFactory.getRemoteInstance();
			StoreSubjectCollection ssc = remote.getStoreSubjectCollection(viewInfo);
			DocumentSubjectCollection dsc = convertStor2Doc(ssc);
			xDocument.getDoc().getSubjects().clear();
			xDocument.getDoc().getSubjects().addCollection(dsc);
			xDocument.reBuild();
		} catch (BOSException e) {
			e.printStackTrace();
		}		
		
		return super.getQueryExecutor(queryPK, viewInfo);
    }

    public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
    	super.actionAddNew_actionPerformed(e);
    	
    	this.tblMain.refresh();
    }

	/* (non-Javadoc)
	 * @see com.kingdee.eas.fdc.market.client.AbstractStoreSubjectListUI#saveSubNumberAction_actionPerformed(java.awt.event.ActionEvent)
	 */
	public void saveSubNumberAction_actionPerformed(ActionEvent e) throws Exception {
		super.saveSubNumberAction_actionPerformed(e);
		DocumentSubjectCollection sc = xDocument.getDoc().getSubjects();
		for(int i=0 ;i <sc.size() ; i++){
			DocumentSubjectInfo s = sc.get(i);
			System.out.println(s.getId());
			if(s.getId()!=null){
				StoreSubjectInfo billInfo = new StoreSubjectInfo();
				billInfo.setId(s.getId());
				billInfo.setSubjectNumber(s.getSubjectNumber());
		        SelectorItemCollection selector = new SelectorItemCollection();
		        selector.add("subjectNumber");
				try {
					StoreSubjectFactory.getRemoteInstance().updatePartial(billInfo, selector);
				} catch (EASBizException ee) {
					ee.printStackTrace();
				}
			}
		}
		if(sc.size()>0){
			MsgBox.showInfo("题库排序成功！");
		}else{
			MsgBox.showInfo("该类别没有题目！");
		}
	}

}