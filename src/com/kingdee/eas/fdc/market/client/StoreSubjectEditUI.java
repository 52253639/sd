/**
 * output package name
 */
package com.kingdee.eas.fdc.market.client;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.appframework.databinding.DataBinder;
import com.kingdee.bos.appframework.validator.ValidateHelper;
import com.kingdee.bos.appframework.validator.Validator;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectListener;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.market.DocumentItemInfo;
import com.kingdee.eas.fdc.market.DocumentOptionInfo;
import com.kingdee.eas.fdc.market.DocumentSubjectTypeEnum;
import com.kingdee.eas.fdc.market.StoreItemCollection;
import com.kingdee.eas.fdc.market.StoreItemFactory;
import com.kingdee.eas.fdc.market.StoreItemInfo;
import com.kingdee.eas.fdc.market.StoreOptionCollection;
import com.kingdee.eas.fdc.market.StoreOptionInfo;
import com.kingdee.eas.fdc.market.StoreSubjectClassFactory;
import com.kingdee.eas.fdc.market.StoreSubjectClassInfo;
import com.kingdee.eas.fdc.market.StoreSubjectFactory;
import com.kingdee.eas.fdc.market.StoreSubjectInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseAgioEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PurchaseAgioEntryFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseInfo;
import com.kingdee.eas.fdc.sellhouse.PurchasePayListEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PurchasePayListEntryFactory;
import com.kingdee.eas.fdc.sellhouse.SHEPayTypeFactory;
import com.kingdee.eas.fdc.sellhouse.SHEPayTypeInfo;
import com.kingdee.eas.fdc.sellhouse.client.SHEHelper;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.framework.client.ListUI;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class StoreSubjectEditUI extends AbstractStoreSubjectEditUI {
	
	public void onLoad() throws Exception {
		super.onLoad();
		kdtItems.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);  //.CELL_SELECT
		kdtOptions.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT); //CELL_SELECT		
		
		this.actionCancel.setVisible(false);
		this.actionCancelCancel.setVisible(false);
		this.actionNext.setVisible(false);
		this.actionLast.setVisible(false);
		this.actionFirst.setVisible(false);
		this.actionPre.setVisible(false);
		this.actionSave.setEnabled(false);
		this.actionSave.setVisible(false);
		this.kdtItems.getColumn("itemNumber").getStyleAttributes().setLocked(true);
		this.kdtOptions.getColumn("optionNumber").getStyleAttributes().setLocked(true);

		if(this.kdtItems.getRowCount()>0){
			KDTableUtil.setSelectedRow(kdtItems, 0);
		}	
		
		SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
		if (!saleOrg.getId().toString().equals(OrgConstants.DEF_CU_ID)) {
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
			this.actionSave.setEnabled(false);
			this.actionCopy.setEnabled(false);
			this.btnAddItem.setEnabled(false);
			this.btnAddOption.setEnabled(false);
			this.btnDeleItem.setEnabled(false);
			this.btnDeleOption.setEnabled(false);
		}
		
		this.storeFields();
		this.initOldData(this.editData);
		//src start
		comVisable();//隐藏分组功能
		//src end
	}
	/**
	 * 隐藏分组功能 
	 */
	public void comVisable(){
		this.btnAddItem.setVisible(false);//增加分组
		this.btnDeleItem.setVisible(false);//删除分组
		this.kdtItems.setVisible(false);//分组表格
		this.kdtOptions.setLocation(10,130);
		this.btnAddOption.setLocation(570, 100);
		this.btnDeleOption.setLocation(680, 100);
	}
	private static final Logger logger = CoreUIObject.getLogger(StoreSubjectEditUI.class);

	private StoreItemInfo item;

	private DataBinder databinderItem = new DataBinder();
	private ValidateHelper itemValidateHelper;

	/**
	 * output class constructor
	 */
	public StoreSubjectEditUI() throws Exception {
		super();
		registerSubjectValidator();
		registerOptionBinding();
	}

	protected IObjectValue getValue(IObjectPK pk) throws Exception {
		// return super.getValue(pk);
		StoreSubjectInfo objectValue = (StoreSubjectInfo) super.getValue(pk);
		if (objectValue.getStoreSubClass() != null) {
			SelectorItemCollection sels = new SelectorItemCollection();
			sels.add("*");
			StoreSubjectClassInfo subjectInfo = StoreSubjectClassFactory.getRemoteInstance().getStoreSubjectClassInfo(new ObjectUuidPK(objectValue.getStoreSubClass().getId()), sels);
			objectValue.setStoreSubClass(subjectInfo);
		}

		EntityViewInfo view = new EntityViewInfo();
		view.getSorter().add(new SorterItemInfo("itemNumber"));
		view.getSorter().add(new SorterItemInfo("options.optionNumber"));
		view.getSelector().add("*");
		view.getSelector().add("options.*");
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(new FilterItemInfo("subjectId", objectValue.getId().toString()));
		StoreItemCollection storeItems = StoreItemFactory.getRemoteInstance().getStoreItemCollection(view);
		objectValue.getItems().clear();
		objectValue.getItems().addCollection(storeItems);

		return objectValue;
	}

	/**
	 * output loadFields method
	 */
	public void loadFields() {
		kdtItems.addKDTSelectListener(new SubjectSelectListener());

/*		
		if (editData.getItems().size() == 0) {
			item = new StoreItemInfo();
			editData.getItems().add(item);
		} else {
			item = editData.getItems().get(0);
		}
		if (item.getOptions().size() == 0) {
			item.getOptions().add(createNewOption());
		}
*/
		
		if(this.editData.getId()!=null) {
			if(this.editData.getSubjectType()!=null && DocumentSubjectTypeEnum.SUBJECT_TYPE_DESCRIPTION.equals(this.editData.getSubjectType())) {
				StoreItemCollection itemColl = this.editData.getItems();
				if(itemColl.size()>0){
					StoreOptionCollection optionColl = itemColl.get(0).getOptions();
					if(optionColl.size()>0) this.txtDespLength.setValue(new Integer(optionColl.get(0).getXLength().intValue()));
				}
			}
		}
		
		databinderItem.setValueObject(item);
		databinderItem.loadFields();
		super.loadFields();
	}

	/**
	 * output storeFields method  
	 */
	public void storeFields() {
		super.storeFields();

		databinderItem.storeFields();
		this.editData.setOrgUnit((FullOrgUnitInfo)SysContext.getSysContext().getCurrentOrgUnit());
	}

	/**
	 * output setDataObject method
	 */
	public void setDataObject(IObjectValue dataObject) {
		IObjectValue ov = dataObject;
		super.setDataObject(ov);
		this.editData = (com.kingdee.eas.fdc.market.StoreSubjectInfo) dataObject;

	}

	public Validator getItemValidator() {
		return null;
	}

	public void registerSubjectValidator() {
		ValidateHelper vh = getItemValidateHelper();
		vh.setCustomValidator(getItemValidator());
		vh.registerBindProperty("items", ValidateHelper.ON_SAVE);
		vh.registerBindProperty("items.topic", ValidateHelper.ON_SAVE);
		vh.registerBindProperty("items.xFontSize", ValidateHelper.ON_SAVE);
		vh.registerBindProperty("items.xFontName", ValidateHelper.ON_SAVE);
		vh.registerBindProperty("topic", ValidateHelper.ON_SAVE);
		vh.registerBindProperty("xFontSize", ValidateHelper.ON_SAVE);
	}

	public final ValidateHelper getItemValidateHelper() {
		if (this.itemValidateHelper == null) {
			this.itemValidateHelper = new ValidateHelper(item, databinderItem);
		}
		// itemValidateHelper.setDataObject( item ) ;
		return itemValidateHelper;
	}

	private void registerOptionBinding() {

		databinderItem.registerBinding("options", DocumentItemInfo.class, this.kdtOptions, "userObject");
		databinderItem.registerBinding("options.topic", String.class, this.kdtOptions, "topic.text");
		databinderItem.registerBinding("options.xHeight", java.math.BigDecimal.class, this.kdtOptions, "xHeight.text");
		databinderItem.registerBinding("options.xLength", BigDecimal.class, this.kdtOptions, "xLength.text");
		databinderItem.registerBinding("options.optionNumber", BigDecimal.class, this.kdtOptions, "optionNumber.text");
		databinderItem.registerBinding("options.itemId", com.kingdee.bos.util.BOSUuid.class, this.kdtOptions, "itemId.text");
		databinderItem.registerBinding("options.isTopicInverse", boolean.class, this.kdtOptions, "isTopicInverse.text");
		databinderItem.registerBinding("options.id", com.kingdee.bos.util.BOSUuid.class, this.kdtOptions, "id.text");
		kdtOptions.putBindContents("editData", new String[] { "topic", "xHeight", "xLength", "optionNumber", "itemId", "isTopicInverse", "id" });
		kdtOptions.checkParsed();
	}

	private void whenSubjectItemRowChanged(int currRow, int currColu, int prevRow, int prevColu) {
		if (currRow >= 0 && (prevRow != prevColu || currRow != prevRow)) {
			// 保存数据先
			storeFields();
			// 换行操作
			StoreItemInfo currItem = editData.getItems().get(currRow);
			// 只需设置 OPTION 的表格
			databinderItem.setValueObject(currItem);
			// 只需LOAD OPTION 的表格
			databinderItem.loadFields();
		}
	}

	private StoreItemInfo createNewItem() {
		StoreItemInfo info = new StoreItemInfo();
		info.getOptions().add(createNewOption());
		return info;
	}

	private StoreOptionInfo createNewOption() {
		StoreOptionInfo info = new StoreOptionInfo();
		return info;
	}

	private void addItemNumber(){
		for(int i=0; i<this.kdtItems.getRowCount(); i++){
			this.kdtItems.getRow(i).getCell("itemNumber").setValue(new Integer(i+1));
		}
	}
	
	private void addOptionNumber(){
		for(int i=0; i<this.kdtOptions.getRowCount(); i++){
			this.kdtOptions.getRow(i).getCell("optionNumber").setValue(new Integer(i+1));
		}
	}
	
	protected void btnAddItem_actionPerformed(ActionEvent e) throws Exception {
		StoreItemInfo detailData = createNewItem();
		IRow row = kdtItems.addRow();
//		kdtItems.getSelectManager().set(row.getRowIndex(), 0);
		//将焦点放在表kdtOptions的第一行的第二个单元格（因为只有一行），可以触发事件对上一个分组进行保存
		this.kdtOptions.getSelectManager().select(0, 1);
		dataBinder.loadLineFields(kdtItems, row, detailData);
//		row.getCell("itemNumber").setValue(new Integer(kdtItems.getRowCount()));
		addItemNumber();
		
		item = detailData;
		item.setItemNumber(FDCHelper.toBigDecimal((this.kdtItems.getRowCount()) + ""));
		databinderItem.setValueObject(item);
		databinderItem.loadFields();
		
		addOptionNumber();
	}

	protected void btnAddOption_actionPerformed(ActionEvent e) throws Exception {
		StoreOptionInfo info = createNewOption();
		IRow row = kdtOptions.addRow();
		
//		row.getCell("optionNumber").setValue((1 + this.kdtOptions.getRowCount()) + "");
		databinderItem.loadLineFields(kdtOptions, row, info);
//		row.getCell("optionNumber").setValue(new Integer(kdtOptions.getRowCount()));
		addOptionNumber();
		
	}

	protected void btnDeleItem_actionPerformed(ActionEvent e) throws Exception {
		if (kdtItems.getRowCount() > 1) {
			KDTSelectManager selectManager = kdtItems.getSelectManager();
			int size = selectManager.size();
			removeLine(kdtItems);
			if (size > 0) {
				int topRow = selectManager.get(0).getTop();
				if (size > 0 && topRow < kdtItems.getRowCount()) {
					selectManager.select(topRow, 0);
				}
			}
		} else {
			MsgBox.showWarning("不能删除，至少要保留一个分组！");
		}
		addItemNumber();
	}

	protected void btnDeleOption_actionPerformed(ActionEvent e) throws Exception {
		if (kdtOptions.getRowCount() > 1) {
			removeLine(kdtOptions);
		} else {
			MsgBox.showWarning("不能删除，至少要保留一个选项！");
		}
		addOptionNumber();
	}

	/**
	 * 变换题目类型的时候使用
	 */
	protected void subjectType_actionPerformed(ActionEvent e) throws Exception {
		if (subjectType.getSelectedItem() == null) {
			subjectType.setSelectedIndex(0);
		}
		if (DocumentSubjectTypeEnum.SUBJECT_TYPE_DESCRIPTION.compareTo(subjectType.getSelectedItem()) == 0) {
			areaSubjectTopic.setText(txttopic.getText());
			hidenAreaSubjectTopic(false);
			this.contDespLength.setVisible(true);
			//src start
			this.contAlignType.setVisible(false);
			//src end
		} else {
			hidenAreaSubjectTopic(true);
			
			this.contDespLength.setVisible(false);
			if(DocumentSubjectTypeEnum.SUBJECT_TYPE_FILL.equals(subjectType.getSelectedItem())){
				this.kdtOptions.getColumn("xLength").getStyleAttributes().setHided(false);
				this.kdtOptions.getColumn("isTopicInverse").getStyleAttributes().setHided(false);
			}else{
				this.kdtOptions.getColumn("xLength").getStyleAttributes().setHided(true);
				this.kdtOptions.getColumn("isTopicInverse").getStyleAttributes().setHided(true);
			}
		}
		//src start
		comVisable();//隐藏分组功能
		//src end
	}

	private void hidenAreaSubjectTopic(boolean hiden) {
		areaSubjectTopic.setVisible(!hiden);
		kdtItems.setVisible(hiden);
		kdtOptions.setVisible(hiden);
		btnAddItem.setVisible(hiden);
		btnDeleItem.setVisible(hiden);
		btnAddOption.setVisible(hiden);
		btnDeleOption.setVisible(hiden);
		txttopic.setVisible(hiden);
		//src start
		contAlignType.setVisible(hiden);
		//src end
	}

	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = super.getSelectors();
		sic.add(new SelectorItemInfo("topic"));
		sic.add(new SelectorItemInfo("storeSubClass.*"));
		sic.add(new SelectorItemInfo("subjectType"));
		sic.add(new SelectorItemInfo("xCellCreter"));
		sic.add(new SelectorItemInfo("items.*"));
		sic.add(new SelectorItemInfo("items.topic"));
		sic.add(new SelectorItemInfo("items.itemNumber"));
		sic.add(new SelectorItemInfo("items.id"));
		sic.add(new SelectorItemInfo("items.subjectId.*"));
		sic.add(new SelectorItemInfo("items.options.*"));
		sic.add(new SelectorItemInfo("items.options.itemId"));
		sic.add(new SelectorItemInfo("items.options.isTopicInverse"));
		sic.add(new SelectorItemInfo("items.options.xHeight"));
		sic.add(new SelectorItemInfo("items.options.xLength"));
		sic.add(new SelectorItemInfo("orgUnit.*"));
		return sic;
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
		// || isTableColumnSelected(table))
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
					MsgBox.showInfo(this, EASResource.getString(FrameWorkClientUtils.strResource + "Msg_NoneEntry"));
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
			for (int i = indexArr.length - 1; i >= 0; i--) {
				int rowIndex = Integer.parseInt(String.valueOf(indexArr[i]));
				IObjectValue detailData = (IObjectValue) table.getRow(rowIndex).getUserObject();
				table.removeRow(rowIndex);
				IObjectCollection collection = (IObjectCollection) table.getUserObject();

				if (collection == null) {
					logger.error("collection not be binded to table");
				} else {
					// Modify By Jacky Zhang
					if (detailData != null) {
						int index = getCollectionIndex(collection, detailData);
						// 避免有合计行的分录处理。
						if (index >= 0 && collection.size() > index) {
							collection.removeObject(index);
						}
					}
				}
				// afterRemoveLine(table, detailData);
			}

			// 如果现在有记录定位到第一行
			if (table.getRow(0) != null)
				table.getSelectManager().select(0, 0);
		}
	}

	// 因为目前ObjectValue比较是按值比较，但集合中使用，如果分录值相同，
	// 都会删除找到的第一个，会出错。自行实现按指针比较。2007-2-5
	private int getCollectionIndex(IObjectCollection collection, IObjectValue obj) {
		int index = -1;
		if (collection == null) {
			return index;
		}
		for (int i = collection.size() - 1; i >= 0; i--) {
			if (obj == collection.getObject(i)) {
				index = i;
				return index;
			}
		}
		return index;
	}

	public void verifyData() throws Exception {
		super.verifyData();
	}

	protected void verifyInput(ActionEvent e) throws Exception {
		super.verifyInput(e);
		
		if(DocumentSubjectTypeEnum.SUBJECT_TYPE_DESCRIPTION.equals(this.subjectType.getSelectedItem()))
			return;
		
		if(this.txttopic.getText()==null || "".equals(this.txttopic.getText())){
			MsgBox.showInfo("请输入主题！");
			abort();
		}
		for(int i=0; i<kdtItems.getRowCount(); i++){
			if(kdtItems.getRow(i).getCell("topic").getValue()== null || "".equals(kdtItems.getRow(i).getCell("topic").getValue().toString())){
				MsgBox.showInfo("请输入分组主题！");
				abort();
			}
		}
//		for(int i=0; i<kdtItems.getRowCount(); i++){
//			if(kdtOptions.getRow(i).getCell("topic").getValue() == null || "".equals(kdtOptions.getRow(i).getCell("topic").getValue().toString())){
//				MsgBox.showInfo("请输入选项主题！");
//				abort();
//			}
//		}
	}

	/**
	 * output actionSave_actionPerformed
	 */
	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		if (DocumentSubjectTypeEnum.SUBJECT_TYPE_DESCRIPTION.compareTo(subjectType.getSelectedItem()) == 0) {
			txttopic.setText(areaSubjectTopic.getText());
		}
		storeFields();
		super.actionSave_actionPerformed(e);
	}

	/**
	 * output actionSubmit_actionPerformed
	 */
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		//src start
		if(this.prmtstoreSubClass.getValue()==null||"".equals(this.prmtstoreSubClass.getValue())){
			MsgBox.showInfo("请输入题库题目类别！");
			abort();
		}
		if(this.txttopic.getText()==null || "".equals(this.txttopic.getText())){
			MsgBox.showInfo("请输入主题！");
			abort();
		}
		//src end
		storeFields();
		
		if (DocumentSubjectTypeEnum.SUBJECT_TYPE_DESCRIPTION.compareTo(subjectType.getSelectedItem()) == 0) {
			//描述题等同于： 1个分组标题，且标题=描述内容，对应一个分组选项，分组选项标题为空
			String despString = this.areaSubjectTopic.getText().trim();

			StoreItemInfo itemInfo = new StoreItemInfo();
			itemInfo.setItemNumber(new BigDecimal(1));
			itemInfo.setTopic(despString);
			StoreOptionInfo optionInfo = new StoreOptionInfo();
			optionInfo.setXLength(new BigDecimal(this.txtDespLength.getIntegerValue().intValue()));
			optionInfo.setItemId(itemInfo);
			optionInfo.setOptionNumber(new BigDecimal(1));
			itemInfo.getOptions().add(optionInfo);
			itemInfo.setSubjectId(this.editData);        		
			this.editData.getItems().clear();
			this.editData.getItems().add(itemInfo);
			this.editData.setTopic(despString);
			this.editData.setSubjectType(DocumentSubjectTypeEnum.SUBJECT_TYPE_DESCRIPTION);
		}
		
		IObjectPK pk = StoreSubjectFactory.getRemoteInstance().submit(this.editData);
		this.editData.setId(BOSUuid.read(pk.toString()));
		
		FDCMsgBox.showInfo("保存成功");
		//ListUI parentUI = (ListUI)this.getUIContext().get("Owner");
		//if(parentUI!=null) parentUI.refreshList();
		//super.actionSubmit_actionPerformed(e);
		
		this.setOprtState(OprtState.VIEW);
	}

	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		checkBeforEditOrDelete(this.editData);
		
		super.actionEdit_actionPerformed(e);
	}
	
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		checkBeforEditOrDelete(this.editData);
		super.actionRemove_actionPerformed(e);
	}
	
	//只有单据的创建组织才能修改和删除
	public static void checkBeforEditOrDelete(StoreSubjectInfo objInfo) {
		if(objInfo.getId()==null) return;
		if(objInfo.getOrgUnit()==null) return;
		
		if(!SysContext.getSysContext().getCurrentOrgUnit().getId().equals(objInfo.getOrgUnit().getId())){
			MsgBox.showWarning("当前组织非单据的创建组织，不能操作！");
			SysUtil.abort();
		}
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
		objectValue.setCreator(SysContext.getSysContext().getCurrentUserInfo());
		StoreSubjectClassInfo storeSubjectClass = (StoreSubjectClassInfo) this.getUIContext().get("storeSubjectClass");
		if (storeSubjectClass != null) {
			objectValue.setStoreSubClass(storeSubjectClass);
		}
		objectValue.setXCellCreter("com.kingdee.eas.fdc.market.client.XCellCommonCreater");
		item = new StoreItemInfo();
		item.setItemNumber(new BigDecimal(1));
		StoreOptionInfo opInfo = createNewOption();
		opInfo.setOptionNumber(new BigDecimal(1));
		item.getOptions().add(opInfo);		
		objectValue.getItems().add(item); 
		//
		
		StoreSubjectClassInfo subject = (StoreSubjectClassInfo)this.getUIContext().get("storeSubjectClass");
		if(subject != null){
			this.prmtstoreSubClass.setValue(subject);
		}
		//addItemNumber();
		//addOptionNumber();
		this.txtDespLength.setValue(new Integer(100));
		
		return objectValue;
	}

	// ///////// 监听类

	class SubjectSelectListener implements KDTSelectListener {
		// 换行监听
		public void tableSelectChanged(KDTSelectEvent e) {

			int currRow = -1;
			int currColu = -1;
			if (e.getSelectBlock() != null) {
				currRow = e.getSelectBlock().getTop();
				currColu = e.getSelectBlock().getLeft();
			}
			int prevRow = -1;
			int prevColu = -1;
			if (e.getPrevSelectBlock() != null) {
				prevRow = e.getPrevSelectBlock().getTop();
				prevColu = e.getPrevSelectBlock().getLeft();
			}
			StoreSubjectEditUI.this.whenSubjectItemRowChanged(currRow, currColu, prevRow, prevColu);
			
			addOptionNumber();
		}
	}

	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		super.actionAddNew_actionPerformed(e);
		
		this.storeFields();
		this.initOldData(this.editData);
	}
	
	
	
}