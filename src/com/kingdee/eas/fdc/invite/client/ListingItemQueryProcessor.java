package com.kingdee.eas.fdc.invite.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.ComboBoxModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.KDTableHelper;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataFillListener;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditListener;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDTabbedPane;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.eas.base.commonquery.CompareSignDateEnum;
import com.kingdee.eas.base.commonquery.CompareSignIntEnum;
import com.kingdee.eas.base.commonquery.CompareSignStringEnum;
import com.kingdee.eas.base.commonquery.client.CommonFilterPanel;
import com.kingdee.eas.base.commonquery.client.CommonQueryProcessor;
import com.kingdee.eas.base.commonquery.client.DataObject;
import com.kingdee.eas.base.permission.PermissionFactory;
import com.kingdee.eas.basedata.master.cssp.SupplierCompanyInfoInfo;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.invite.ColumnTypeEnum;
import com.kingdee.eas.fdc.invite.DescriptionEnum;
import com.kingdee.eas.fdc.invite.HeadColumnCollection;
import com.kingdee.eas.fdc.invite.HeadColumnFactory;
import com.kingdee.eas.fdc.invite.HeadColumnInfo;
import com.kingdee.eas.fdc.invite.HeadTypeInfo;
import com.kingdee.eas.fdc.invite.RefPriceCollection;
import com.kingdee.eas.fdc.invite.RefPriceEntryCollection;
import com.kingdee.eas.fdc.invite.RefPriceEntryFactory;
import com.kingdee.eas.fdc.invite.RefPriceEntryInfo;
import com.kingdee.eas.fdc.invite.RefPriceInfo;
import com.kingdee.eas.framework.client.CoreUI;

public class ListingItemQueryProcessor extends CommonQueryProcessor {
	
	protected KDTabbedPane kDTabbedPane1 = null;
	protected ListingItemPriceQueryFilterUI myPanel = null;
	protected CommonFilterPanel commonFilterPanel = null;
	protected ListingItemPriceQueryUI coreUI;
	protected HashMap colMap = null; //表头类型对应的列ID
	
	public final static String EXTEND_VALUE = "extend_value_";
	public final static String EXTEND_TEXT = "extend_text_";
	public final static String EXTEND_DATE = "extend_date_";
	
	protected HashMap extendColMap = null ;//表头类型对应的comboxs
	protected HashMap extendColNameMap = null;//表头类型对应的列名称	
	protected ComboBoxModel model = null;//
	protected HashMap bakDataObjects = null;	
	private HashMap tableMap = new HashMap();//每个表头类型对应的table
	private HashMap maxRowMap = new HashMap();//每个表头类型对应可导出的数据的行数
	private HashMap columnUserObject = new HashMap();
	Map supplierContactInfos = new HashMap();
	//Map supplierContractNumInfos = new HashMap();
	public ListingItemQueryProcessor(ListingItemPriceQueryUI ui,KDTabbedPane tabbedPane,ListingItemPriceQueryFilterUI panel){
		coreUI = ui;
		kDTabbedPane1 = tabbedPane;
		myPanel = panel;
	}
	public ListingItemPriceQueryFilterUI getUserPanel(){
		return myPanel;
	}

	public void process() throws Exception {
		// TODO 自动生成方法存根
		return;

	}
	public void refreshHelper(EntityViewInfo v) throws Exception {
		//this.kDTabbedPane1.removeAll();
		//fillData();
		
	}
	/****
	 * 将表头类型对应的列取出，放入Map
	 * @param headType
	 * @throws BOSException 
	 * @throws BOSException
	 */
	public void setColMap(HeadTypeInfo headType) throws BOSException{
		if(headType == null){
			return;
		}
		if(colMap == null){
			colMap = new HashMap();
			extendColMap = new HashMap();
			extendColNameMap = new HashMap();
		}
		//如果Map中已经有此表头类型的信息，就不用再获取
		if(colMap.containsKey(headType.getId())){
			return ;
		}
		//如果Map中没有，则重新 获取
    	EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("*");
		view.getSelector().add("parent.*");
		view.getSorter().add(new SorterItemInfo("longNumber"));
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(
				new FilterItemInfo("headType.id", headType.getId().toString()));
		HeadColumnCollection entrys = HeadColumnFactory.getRemoteInstance()
				.getHeadColumnCollection(view);
		headType.getEntries().clear();
		headType.getEntries().addCollection(entrys);
		ArrayList tempList = new ArrayList();
		HashMap tempMap = new HashMap();
		HashMap tempNameMap = new HashMap();
		int[] i = {1,1,1};
		for(Iterator it = entrys.iterator();it.hasNext();){
			HeadColumnInfo info = (HeadColumnInfo)it.next();
			if(
					info.getName().equals("子目名称")||
					info.getName().equals("单位")||
					info.getName().equals("工程量")||
					info.getName().equals("金额")||
					!info.isIsLeaf()){
				continue;
			}
			String value = "";
			if(info.getColumnType().equals(ColumnTypeEnum.Amount)){
				value = "" + EXTEND_VALUE + i[0];
				i[0]++;
			}
			else if(info.getColumnType().equals(ColumnTypeEnum.String)){
				value = "" + EXTEND_TEXT + i[1];
				i[1]++;
			}
			else if(info.getColumnType().equals(ColumnTypeEnum.Date)){
				value = "" + EXTEND_DATE + i[2];
				i[2]++;
			}
			if(!value.equals("")){
				DataObject d = new DataObject();
				d.setValue(value);
				d.setName(info.getName());
				tempList.add(d);
				tempMap.put(value,info.getId().toString());
				tempNameMap.put(value,info.getName());
			}
		}
		extendColMap.put(headType.getId(),tempList);
		colMap.put(headType.getId(),tempMap);
		extendColNameMap.put(headType.getId(),tempNameMap);
	}
	public void setColMap(HeadTypeInfo headType,final CommonFilterPanel cPanel) throws BOSException{
		// 需要将已经设置的条件列清空
		cPanel.clear();
		if(headType == null){
			return;
		}
		//设置名称非比填
		myPanel.kDTextFieldItemName.setRequired(false);
		setColMap(headType);
	}
	public void bakDataObjects(KDComboBox comBox){
		if(bakDataObjects == null){
    		bakDataObjects = new HashMap();
    		for(int i=0;i<comBox.getItemCount();i++){
    			DataObject dt = new DataObject();
    			if(comBox.getItemAt(i) instanceof DataObject){
    				DataObject d = (DataObject)comBox.getItemAt(i);
        			dt.setValue(d.getValue());
        			dt.setExtendValue(d.getExtendValue());
        			dt.setName(d.getName());
        			bakDataObjects.put(dt.getValue(),dt);
    			}
    		}
    	}
	}
	/****
	 * 给自定义的条件框，加上表头类型变化的事件
	 * 当表头类型变化的时候，获取表头类型对应的列
	 * @param panel
	 */
	public void addKDLisener(final ListingItemPriceQueryFilterUI panel,final CommonFilterPanel cPanel){ 
		if(panel == null || panel.f7HeadType == null){
			return ;
		}
		panel.f7HeadType.addChangeListener(new ChangeListener(){

			public void stateChanged(ChangeEvent arg0) {
				// TODO 自动生成方法存根
				HeadTypeInfo headType = (HeadTypeInfo)((KDBizPromptBox)arg0.getSource()).getValue();
				try {
					setColMap(headType,cPanel);
				} catch (BOSException e) {
					// TODO 自动生成 catch 块
					e.printStackTrace();
				}
				return ;
			}
		});
	}
	public void removeAllExtend(KDComboBox comBox){
		for(int i=comBox.getItemCount()-1;i>0;i--){
			DataObject obj = (DataObject)comBox.getItemAt(i);
			if(obj.getValue().startsWith("extend_")){
				comBox.removeItemAt(i);
			}
		}
	}
	/********
	 * 增加表头类型中自定义的列到下拉框中
	 * @param comBox
	 * @param headType
	 */
	public void addExtend(KDComboBox comBox,HeadTypeInfo headType){
		if(extendColMap == null){
			try {
				setColMap(headType);
			} catch (BOSException e) {
				// TODO 自动生成 catch 块
				e.printStackTrace();
			}
		}
		ArrayList col =  (ArrayList)this.extendColMap.get(headType.getId());
		
		for(Iterator it = col.iterator();it.hasNext();){
			DataObject objkey = (DataObject)it.next();
			String key = objkey.getValue();
			if(bakDataObjects.containsKey(key)){
				DataObject obj = (DataObject)bakDataObjects.get(key);
				obj.setName(objkey.getName());
				comBox.addItem(obj);
			}
		}
	}
	/******
	 * 重新设置commonFilterPanel中的数据的名称
	 *
	 */
	public void resetCommKDTable(){
		KDTable table = commonFilterPanel.getKdtTable();
		for(int ri=0;ri<table.getRowCount();ri++){			
			DataObject selectObj = (DataObject)table.getCell(ri, 1).getValue();
			if(selectObj!=null){
				String selectValue = selectObj.getValue();
				String selectText  = selectObj.getName();
				HashMap cols = getColNameMap();
				if(cols.containsKey(selectValue))
					selectObj.setName((String)cols.get(selectValue));
				else if(!(selectText.equals("清单名称")||
						selectText.equals("单位")||
						selectText.equals("报价单位")||
						selectText.equals("子目名称")))
				{
					//如果没有,说明改变了表头类型,清空所有行
					commonFilterPanel.clear();
					break;
				}
				
			}
		}
	}
	/****
	 * 给commonFilterPanel加自定义的监听事件
	 * 
	 * @param panel
	 */
	public void addKDLisener(final CommonFilterPanel panel) {
    	if(panel==null || panel.getKdtTable()==null){
    		return ;
    	}
    	/***
    	 * 当panel中的不同tab被选择的时候,重新设置commonFilterPanel中的数据
    	 */
    	KDTabbedPane commTabPane = (KDTabbedPane)panel.getParent();
    	if(commTabPane != null){    		
    		commTabPane.addChangeListener(new ChangeListener(){

				public void stateChanged(ChangeEvent arg0) {
					// TODO 自动生成方法存根
					resetCommKDTable();
				}
    			
    		});
    	}
    	/****
    	 * 当数据填充完时,即,不同方案的被加载的时候,myPanel被选择
    	 */
    	panel.getKdtTable().addKDTDataFillListener(new KDTDataFillListener(){

			public void afterDataFill(KDTDataRequestEvent e) {
				// TODO 自动生成方法存根
				
				((KDTabbedPane)myPanel.getParent()).setSelectedIndex(0);
			}
    		
    	});
    	/***
    	 * 更改下拉框中的数据
    	 */
        panel.getKdtTable().addKDTEditListener(new KDTEditListener() {        	
            public void editStarting(KDTEditEvent arg0) {
                int ri = arg0.getRowIndex();
                if (arg0.getColIndex() == 1) {
                	//过滤字段列
                	if(panel==null || 
                			panel.getKdtTable()==null || 
                			panel.getKdtTable().getCell(ri, 1)==null ||
                			panel.getKdtTable().getCell(ri, 1).getEditor()==null ||
                			panel.getKdtTable().getCell(ri, 1).getEditor().getComponent()==null||
                			!(panel.getKdtTable().getCell(ri, 1).getEditor().getComponent() instanceof KDComboBox)){
                		return ;
                	}
                	
                	KDComboBox comBox = (KDComboBox) panel.getKdtTable().getCell(ri, 1).getEditor().getComponent();
                	DataObject selectObj = (DataObject)panel.getKdtTable().getCell(ri, 1).getValue();
                	String selectValue = "";
                	if(selectObj != null){
                		selectValue = selectObj.getValue();
                	}
                	bakDataObjects(comBox);
                	removeAllExtend(comBox);
                	HeadTypeInfo headType = (HeadTypeInfo)myPanel.f7HeadType.getValue();
                	if(headType != null){
                		addExtend(comBox,headType);
                		if(!selectValue.equals("")){
                			for(int i=1;i<comBox.getItemCount();i++){
                				DataObject obj = (DataObject)comBox.getItemAt(i);
                				if(obj != null && obj.getValue().equals(selectValue)){
                					comBox.setSelectedIndex(i);
                					break;
                				}
                			}
                		}
                	}
                }
                else if (arg0.getColIndex() == 2) {
                	//大于,小于等逻辑匹配列
                	//去掉字段等于...
                	if(panel==null || 
                			panel.getKdtTable()==null || 
                			panel.getKdtTable().getCell(ri, 2)==null||
                			panel.getKdtTable().getCell(ri, 2).getEditor()==null||
                			panel.getKdtTable().getCell(ri, 2).getEditor().getComponent()==null||
                			!(panel.getKdtTable().getCell(ri, 2).getEditor().getComponent() instanceof KDComboBox)){
                		return ;
                	}
                	
                	KDComboBox comBox = (KDComboBox) panel.getKdtTable().getCell(ri, 2).getEditor().getComponent();
                	if(comBox != null){
                		for(int i=comBox.getItemCount()-1;i>1;i--){
                			if(comBox.getItemAt(i) != null && comBox.getItemAt(i) instanceof CompareSignIntEnum){
                				CompareSignIntEnum obj = (CompareSignIntEnum)comBox.getItemAt(i);
                    			if(obj.equals(CompareSignIntEnum.FIELDEQUAL)||                					
                    					obj.equals(CompareSignIntEnum.FIELDLARGE)||                					
                    					obj.equals(CompareSignIntEnum.FIELDLARGEEQUAL)||
                    					obj.equals(CompareSignIntEnum.FIELDLESS)||
                    					obj.equals(CompareSignIntEnum.FIELDLESSEQUAL)||
                    					obj.equals(CompareSignIntEnum.FIELDNOTEQUAL)){
                    				comBox.removeItemAt(i);
                    			}
                			}
                			else if(comBox.getItemAt(i) != null && comBox.getItemAt(i) instanceof CompareSignStringEnum) {
                				CompareSignStringEnum obj = (CompareSignStringEnum)comBox.getItemAt(i);
                    			if(obj.equals(CompareSignStringEnum.FIELDEQUAL)||                					
                    					obj.equals(CompareSignStringEnum.FIELDLARGE)||                					
                    					obj.equals(CompareSignStringEnum.FIELDLARGEEQUAL)||
                    					obj.equals(CompareSignStringEnum.FIELDLESS)||
                    					obj.equals(CompareSignStringEnum.FIELDLESSEQUAL)||
                    					obj.equals(CompareSignStringEnum.FIELDNOTEQUAL)){
                    				comBox.removeItemAt(i);
                    			}
                			}
                			else if(comBox.getItemAt(i) != null && comBox.getItemAt(i) instanceof CompareSignDateEnum) {
                				CompareSignDateEnum obj = (CompareSignDateEnum)comBox.getItemAt(i);
                    			if(obj.equals(CompareSignDateEnum.FIELDEQUAL)||                					
                    					obj.equals(CompareSignDateEnum.FIELDLARGE)||                					
                    					obj.equals(CompareSignDateEnum.FIELDLARGEEQUAL)||
                    					obj.equals(CompareSignDateEnum.FIELDLESS)||
                    					obj.equals(CompareSignDateEnum.FIELDLESSEQUAL)||
                    					obj.equals(CompareSignDateEnum.FIELDNOTEQUAL)){
                    				comBox.removeItemAt(i);
                    			}
                			}
                		}
                	}
                	
                	
                }
            }

            public void editStarted(KDTEditEvent e) {

            }

            public void editValueChanged(KDTEditEvent arg0) {
            	
            }

            public void editStopping(KDTEditEvent arg0) {

            }

            public void editStopped(KDTEditEvent arg0) {
            	int ri = arg0.getRowIndex();
                if (arg0.getColIndex() == 1) {
                	//过滤字段列
                	if(panel==null || 
                			panel.getKdtTable()==null || 
                			panel.getKdtTable().getCell(ri, 1)==null ||
                			panel.getKdtTable().getCell(ri, 1).getValue()==null||
                			!(panel.getKdtTable().getCell(ri, 1).getValue() instanceof DataObject)){
                		return ;
                	}
                	DataObject selectObj = (DataObject)panel.getKdtTable().getCell(ri, 1).getValue();
                	String selectValue = "";
                	if(selectObj != null){
                		selectValue = selectObj.getValue();
                	}
                	if(selectValue.startsWith(EXTEND_DATE)){
                		KDComboBox comBox = (KDComboBox) panel.getKdtTable().getCell(ri, 0).getEditor().getComponent();
                		if(panel.getKdtTable().getCell(ri, 0).getValue()==null)
                			panel.getKdtTable().getCell(ri, 0).setValue(comBox.getItemAt(1));
                		comBox = (KDComboBox) panel.getKdtTable().getCell(ri, 4).getEditor().getComponent();
                		if(panel.getKdtTable().getCell(ri, 4).getValue()==null)
                			panel.getKdtTable().getCell(ri, 4).setValue(comBox.getItemAt(1));
                	}
                }
            }

            public void editCanceled(KDTEditEvent arg0) {

            }
        });

    }
	/***
	 * 获取表头类型对应的列名称信息
	 * @return
	 */
	public HashMap getColNameMap(){
		HeadTypeInfo headType = (HeadTypeInfo) myPanel.f7HeadType.getValue();
		HashMap cols = new HashMap();
		if(headType != null)
		{
			if(extendColNameMap == null){
				try {
					setColMap(headType);
				} catch (BOSException e) {
					// TODO 自动生成 catch 块
					e.printStackTrace();
				}
			}
			cols = (HashMap)extendColNameMap.get(headType.getId());
		}
		return cols;
	}
	/***
	 * 获取表头类型对应的列id信息
	 * @return
	 */
	public HashMap getCols(){
		HeadTypeInfo headType = (HeadTypeInfo) myPanel.f7HeadType.getValue();
		HashMap cols = new HashMap();
		if(headType != null)
		{
			if(colMap==null){
				try {
					setColMap(headType);
				} catch (BOSException e) {
					// TODO 自动生成 catch 块
					e.printStackTrace();
				}
			}
			cols = (HashMap)colMap.get(headType.getId());
		}
		return cols;
	}
	/***
	 * 从HashMap中填充数据到table
	 * @param priceMap
	 * @param refPriceEntrys
	 * @param isCan
	 */
	public void fillData(HashMap priceMap,HashMap refPriceEntrys ,boolean isCan){
		Set headSet = priceMap.keySet();
		for (Iterator iter = headSet.iterator(); iter.hasNext();){
			String orgHheadTypeName = (String)iter.next();
			String headTypeName = orgHheadTypeName.split("!=!")[0];//key = headTypeName + orgId
			HashMap tempPriceMap = (HashMap)priceMap.get(orgHheadTypeName);
			KDTable table = (KDTable)this.tableMap.get(headTypeName);
			Set set = tempPriceMap.keySet();
			int rowMergStart = table.getRowCount();// 
			int rowStart = table.getRowCount();
			int rowIndex = 0;
			for (Iterator it = set.iterator(); it.hasNext();) {
				String key = (String) it.next();
				RefPriceCollection prices = (RefPriceCollection) tempPriceMap.get(key);
				if(prices != null){
					for (int j = 0; j < prices.size(); j++) {
						IRow row = table.addRow();
						rowIndex ++ ;
						
						RefPriceInfo price = prices.get(j);	
						if (j == 0 ) {
							row.getCell("内部编码").setValue(price.getItem().getNumber());
							row.getCell("子目名称").setValue(price.getItem().getName());
							row.getCell("单位").setValue(price.getItem().getUnit());
						}
						if (price.getItem().getOrgUnit() != null) {
							row.getCell("公司").setValue(
									price.getItem().getOrgUnit().getName());
						}
						row.getCell("日期").setValue(price.getDate());
						if (price.getQuotingContent() != null) {
							row.getCell("报价单位").setValue(
									price.getQuotingContent().getSupplier().getName());
							String tempAdd = "";//联系地址
							tempAdd += price.getQuotingContent().getSupplier()
									.getProvince() == null ? "" : price
									.getQuotingContent().getSupplier().getProvince()
									.getName();
							tempAdd += price.getQuotingContent().getSupplier().getCity() == null ? ""
									: price.getQuotingContent().getSupplier().getCity()
											.getName();
							tempAdd += price.getQuotingContent().getSupplier().getAddress() == null ? ""
									: price.getQuotingContent().getSupplier().getAddress();
							row.getCell("联系地址").setValue(tempAdd);
							SupplierCompanyInfoInfo supplierContactInfo = (SupplierCompanyInfoInfo) supplierContactInfos
									.get(price.getQuotingContent().getSupplier().getId()
											.toString());
							if(supplierContactInfo!=null){
								//联系人
								row.getCell("联系人").setValue(
										supplierContactInfo.getContactPerson() == null ? ""
												: supplierContactInfo.getContactPerson());
								//联系电话
								String phone = "";
								phone += (supplierContactInfo.getPhone() == null ? ""
										: supplierContactInfo.getPhone());
								phone += phone.equals("")?"":"," + (supplierContactInfo.getMobile() == null ? ""
										: supplierContactInfo.getMobile());
								row.getCell("联系电话").setValue(phone);
							}
//							合同号
//							row.getCell("合同号")
//									.setValue(
//											supplierContractNumInfos.containsKey(price
//													.getQuotingContent().getSupplier()
//													.getId()) ? supplierContractNumInfos
//													.get(price.getQuotingContent()
//															.getSupplier().getId()) : "");
						} else {
							row.getCell("报价单位").setValue("外界导入");
						}
						if (price.getListing() != null) {
							row.getCell("清单名称").setValue(price.getListing().getName());
						}
						
						RefPriceEntryCollection priceEntrys = (RefPriceEntryCollection)refPriceEntrys.get(price.getId().toString());
						if(priceEntrys!=null){
							for (int k = 0; k < priceEntrys.size(); k++) {
								RefPriceEntryInfo entry = priceEntrys.get(k);
								HeadColumnInfo headColumn = entry.getColumn();
								String colName = headColumn.getName();
								String colKey  = headColumn.getId().toString();
								if (colName == null) {
									colName = "";
								}
								if (colName.equals("内部编码") || colName.equals("子目名称")
										|| colName.equals("单位")) {
									continue;
								}
								if (
										!(
												headColumn.getProperty().equals(DescriptionEnum.TotalPrice)|| 
												headColumn.getProperty().equals(DescriptionEnum.TotalPriceSum) || 
												headColumn.getProperty().equals(DescriptionEnum.Personal)
										)&&(!headColumn.getName().equals("备注"))
									) {
									continue;
								}
								IColumn column = table.getColumn(colKey);
								if (column != null) {
//									HeadColumnInfo colInfo = (HeadColumnInfo) column
//											.getUserObject();
									HeadColumnInfo colInfo = (HeadColumnInfo)this.columnUserObject.get(headTypeName+colKey);
									if(colInfo != null){
										ColumnTypeEnum colType = colInfo.getColumnType();
										InviteHelper.loadRefPriceEntry(row.getCell(colKey), entry, colType);
									}
								}
							}							
						}
						KDTableHelper.autoFitRowHeight(table,rowStart+rowIndex-1,5);
					}
					//合并单元格
					table.getMergeManager().mergeBlock(rowMergStart, 1,
							rowMergStart + prices.size() - 1, 1);
					table.getMergeManager().mergeBlock(rowMergStart, 2,
							rowMergStart + prices.size() - 1, 2);
					table.getMergeManager().mergeBlock(rowMergStart, 3,
							rowMergStart + prices.size() - 1, 3);
					KDTableHelper.autoFitRowHeight(table,rowMergStart + prices.size() - 1,5);
					rowMergStart += prices.size();
				}
			}
			//设置能导出的最大行高
			if(isCan){
				this.maxRowMap.put(headTypeName,new Integer(rowIndex+rowStart));
			}
		}
		
	}
	/***
	 * 获取可导出的最大行数
	 * @param headTypeName
	 * @return
	 */
	public Integer getCanExportMaxRowIndex(String headTypeName){
		if(this.maxRowMap.containsKey(headTypeName))
			return (Integer)this.maxRowMap.get(headTypeName);
		else
			return new Integer(0);
	}
	/***
	 * 获取数据,放置到HashMap
	 * 然后填充数据
	 * @param view
	 * @return
	 * @throws BOSException
	 * @throws EASBizException
	 */
	public boolean fillData(EntityViewInfo view) throws BOSException, EASBizException {
		
		view.getSelector().clear();
		view.getSelector().add("*");
		view.getSelector().add("head.*");
		view.getSelector().add("head.item.*");
		view.getSelector().add("head.item.headType.*");	
		view.getSelector().add("head.item.orgUnit.id");
		view.getSelector().add("head.item.orgUnit.name");
		view.getSelector().add("head.quotingContent.supplier.id");
		view.getSelector().add("head.quotingContent.supplier.name");
		view.getSelector().add("head.quotingContent.supplier.province.name");
		view.getSelector().add("head.quotingContent.supplier.city.name");
		view.getSelector().add("head.quotingContent.supplier.address");
		view.getSelector().add("head.listing.name");
		view.getSelector().add("column.*");	
		
		view.getSorter().add(new SorterItemInfo("head.item.orgUnit.id"));
		view.getSorter().add(new SorterItemInfo("head.item"));
		view.getSorter().add(new SorterItemInfo("head.date"));
		
		HashMap cols = getCols();
		RefPriceEntryCollection refPrices = RefPriceEntryFactory
				.getRemoteInstance().getCollection(view, cols);
		
		
		//获取有权限的组织id
		Map permOrgMap = PermissionFactory.getRemoteInstance().getAuthorizedOrgs(
				new ObjectUuidPK(SysContext.getSysContext().getCurrentUserInfo().getId().toString()),
				OrgType.Company,
				null,
				new MetaDataPK("com.kingdee.eas.fdc.invite.client.ListingItemPriceQueryUI"),
				new MetaDataPK("ActionExport"));
		
		
		
		if(refPrices == null || refPrices.size() == 0){
			this.kDTabbedPane1.setVisible(false);
			return false;
		}	
		
		Set supplierIds = RefPriceSelectUI.getSupplierIds(refPrices);
		supplierContactInfos = RefPriceSelectUI.getSupplierContactInfos(supplierIds);
		//supplierContractNumInfos = RefPriceSelectUI.getSupplierContractNumInfos(supplierIds);
		
		
		HashMap priceMapCanExport = new HashMap();//有权限的Map
		HashMap priceMapCannotExport = new HashMap();//没有权限的Map

		HashMap refPriceEntrys = new HashMap();
		List keyList = new ArrayList();
		this.kDTabbedPane1.removeAll();
		this.tableMap.clear();
		for (int i = 0; i < refPrices.size(); i++) {
			RefPriceInfo price = refPrices.get(i).getHead();
			RefPriceEntryInfo priceEntryInfo = refPrices.get(i);
			String headTypeKey = price.getItem().getHeadType().getName();
			if(!this.tableMap.containsKey(headTypeKey)){
				final KDTable table = new KDTable();
				//enableAutoAddLine(table);
				//tHelper.setCanSetTable(true);
				coreUI.setTableMenu(table);
				table.setName(headTypeKey);
				this.kDTabbedPane1.add(table, headTypeKey);
				fillHead(table,price.getItem().getHeadType());
				this.tableMap.put(headTypeKey,table);
			}
			
			HashMap tempPriceMap = new HashMap();//
			String orgId = price.getItem().getOrgUnit().getId().toString();
			String orgHeadTypeKey = headTypeKey+"!=!"+orgId;
			if(permOrgMap.containsKey(orgId)){
				//在有权限的Map中取出集合
				if(priceMapCanExport.containsKey(orgHeadTypeKey)){
					tempPriceMap = (HashMap)priceMapCanExport.get(orgHeadTypeKey);
				}
				else{
					priceMapCanExport.put(orgHeadTypeKey,tempPriceMap);
				}
			}
			else{
				// 在没有权限的Map中取出集合
				if(priceMapCannotExport.containsKey(orgHeadTypeKey)){
					tempPriceMap = (HashMap)priceMapCannotExport.get(orgHeadTypeKey);
				}
				else{
					priceMapCannotExport.put(orgHeadTypeKey,tempPriceMap);
				}
			}
			
			
			String ItemKey = price.getItem().getId().toString();
			
			//keyList.add(ItemKey);
			
			if (tempPriceMap.containsKey(ItemKey)) {
				RefPriceCollection prices = (RefPriceCollection) tempPriceMap
						.get(ItemKey);				
				prices.add(price);
			} else {
				RefPriceCollection prices = new RefPriceCollection();
				prices.add(price);
				tempPriceMap.put(price.getItem().getId().toString(), prices);
			}
			String priceKey = price.getId().toString();
			
			if(refPriceEntrys.containsKey(priceKey)){
				RefPriceEntryCollection priceEntrys = (RefPriceEntryCollection)refPriceEntrys.get(priceKey);
				priceEntrys.add(priceEntryInfo);
			}
			else{
				RefPriceEntryCollection priceEntrys = new RefPriceEntryCollection();
				priceEntrys.add(priceEntryInfo);
				refPriceEntrys.put(priceKey,priceEntrys);
			}
		}
		//需要修改
		
		//先输出有导出权限的数据
		fillData(priceMapCanExport,refPriceEntrys,true);
		//设置导出数据的最大行数
		//在输出没有导出权限的数据
		fillData(priceMapCannotExport,refPriceEntrys,false);
		//setAotuHeight();
		this.kDTabbedPane1.setVisible(true);
		return true;
	}
	/**
	 * 设置每行自动行高
	 *
	 */
	public void setAotuHeight(){
		for(int tableIndex=0;tableIndex<this.kDTabbedPane1.getTabCount();tableIndex++){
			KDTable table = (KDTable)this.kDTabbedPane1.getComponentAt(tableIndex);
			if(table != null){
				for(int rowIndex=0;rowIndex<table.getRowCount();rowIndex++){
					KDTableHelper.autoFitRowHeight(table,rowIndex,5);
				}
			}
			
		}
	}
	/***
	 * 填充表头
	 * @param table
	 * @param headType
	 * @throws BOSException
	 */
	private void fillHead(KDTable table,HeadTypeInfo headType) throws BOSException {
		table.removeColumns();
		//HeadTypeInfo headType = (HeadTypeInfo) this.f7HeadType.getValue();

		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("*");
		view.getSelector().add("parent.*");
		view.getSorter().add(new SorterItemInfo("longNumber"));
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(
				new FilterItemInfo("headType.id", headType.getId().toString()));
		HeadColumnCollection entrys = HeadColumnFactory.getRemoteInstance()
				.getHeadColumnCollection(view);
		headType.getEntries().clear();
		headType.getEntries().addCollection(entrys);
		table.getSelectManager().setSelectMode(KDTSelectManager.MULTIPLE_CELL_SELECT);
		table.getStyleAttributes().setWrapText(true);
		table.getStyleAttributes().setLocked(true);
		IRow headRow0 = table.addHeadRow();
		IRow headRow1 = table.addHeadRow();
		IColumn column = table.addColumn();
		column.setKey("内部编码");
		column.getStyleAttributes().setHided(true);
		headRow0.getCell("内部编码").setValue("内部编码");
		column = table.addColumn();
		column.setKey("公司");
		headRow0.getCell("公司").setValue("公司");
		column = table.addColumn();
		column.setKey("子目名称");
		headRow0.getCell("子目名称").setValue("子目名称");
		column = table.addColumn();
		column.setKey("单位");
		headRow0.getCell("单位").setValue("单位");
		column = table.addColumn();
		column.setKey("日期");
		column.getStyleAttributes().setNumberFormat("yyyy-mm-dd");
		headRow0.getCell("日期").setValue("日期");
		column = table.addColumn();
		column.setKey("报价单位");
		headRow0.getCell("报价单位").setValue("报价单位");
		
		column = table.addColumn();
		column.setKey("联系地址");
		headRow0.getCell("联系地址").setValue("联系地址");
		
		column = table.addColumn();
		column.setKey("联系人");
		headRow0.getCell("联系人").setValue("联系人");
		
		column = table.addColumn();
		column.setKey("联系电话");
		headRow0.getCell("联系电话").setValue("联系电话");
		
//		column = table.addColumn();
//		column.setKey("合同号");
//		headRow0.getCell("合同号").setValue("合同号");
		
		column = table.addColumn();
		column.setKey("清单名称");
		headRow0.getCell("清单名称").setValue("清单名称");
		
		
		
		table.getHeadMergeManager().mergeBlock(0, 0, 1, 0);
		table.getHeadMergeManager().mergeBlock(0, 1, 1, 1);
		table.getHeadMergeManager().mergeBlock(0, 2, 1, 2);
		table.getHeadMergeManager().mergeBlock(0, 3, 1, 3);
		table.getHeadMergeManager().mergeBlock(0, 4, 1, 4);
		table.getHeadMergeManager().mergeBlock(0, 5, 1, 5);
		table.getHeadMergeManager().mergeBlock(0, 6, 1, 6);
		
		table.getHeadMergeManager().mergeBlock(0, 7, 1, 7);
		table.getHeadMergeManager().mergeBlock(0, 8, 1, 8);
		table.getHeadMergeManager().mergeBlock(0, 9, 1, 9);
		//table.getHeadMergeManager().mergeBlock(0, 10, 1, 10);
		
		HeadColumnCollection cols = headType.getEntries();
		HeadColumnCollection refCols = new HeadColumnCollection();
		for (int i = 0; i < cols.size(); i++) {
			HeadColumnInfo info = cols.get(i);
			if (info.isIsLeaf()
					&& !info.getProperty().equals(DescriptionEnum.System)
					&& !info.getProperty().equals(DescriptionEnum.ProjectAmt)
					&& !info.getProperty()
							.equals(DescriptionEnum.ProjectAmtSum)
					&& !info.getProperty().equals(DescriptionEnum.Amount)
					&& !info.getProperty().equals(DescriptionEnum.AmountSum)
					|| info.getName().equals("备注")) {
				column = table.addColumn();
				//column.setUserObject(info);
				this.columnUserObject.put(headType.getName()+info.getId().toString(),info);
				column.setKey(info.getId().toString());
				ColumnTypeEnum colType = info.getColumnType();
				if (colType.equals(ColumnTypeEnum.Amount)) {
					column.getStyleAttributes().setHorizontalAlign(
							HorizontalAlignment.RIGHT);
					column.getStyleAttributes().setNumberFormat(
							FDCHelper.getNumberFtm(2));
				} else if (colType.equals(ColumnTypeEnum.Date)) {
					column.getStyleAttributes().setNumberFormat(
							"yyyy-MM-dd");
				}
				refCols.add(info);
			}
		}
		int count = 0;
		int baseCount = 10;//6;
		for (int j = 0; j < refCols.size(); j++) {
			HeadColumnInfo infoColumn = refCols.get(j);
			headRow1.getCell(j + baseCount).setValue(infoColumn.getName());
			if (infoColumn.getParent() != null) {
				headRow0.getCell(j + baseCount).setValue(
						infoColumn.getParent().getName());
				if (j < refCols.size() - 1) {
					HeadColumnInfo infoNext = refCols.get(j + 1);
					if (infoNext.getParent() != null
							&& infoColumn.getParent().equals(
									infoNext.getParent())) {
						count++;
					} else {
						table.getHeadMergeManager().mergeBlock(0,
								j - count + baseCount, 0, j + baseCount);
						count = 0;
					}
				} else {
					table.getHeadMergeManager().mergeBlock(0,
							j - count + baseCount, 0, j + baseCount);
					count = 0;
				}
			} else {
				table.getHeadMergeManager().mergeBlock(0, j + baseCount, 1,
						j + baseCount);
			}
		}
		coreUI.initSavedUserConfig();
	}
	public CommonFilterPanel getCommonFilterPanel() {
		return commonFilterPanel;
	}
	public void setCommonFilterPanel(CommonFilterPanel commonFilterPanel) {
		this.commonFilterPanel = commonFilterPanel;
	}

}
