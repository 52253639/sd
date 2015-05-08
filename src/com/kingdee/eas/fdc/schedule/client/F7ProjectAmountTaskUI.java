/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

import java.awt.event.ActionEvent;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.CellTreeNode;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.NodeClickListener;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.fdc.basedata.client.FDCColorConstants;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskCollection;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskFactory;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo;
import com.kingdee.eas.fdc.schedule.ScheduleTaskFiledEnum;
import com.kingdee.eas.util.SysUtil;

/**
 * output class name
 */
public class F7ProjectAmountTaskUI extends AbstractF7ProjectAmountTaskUI
{
    private static final Logger logger = CoreUIObject.getLogger(F7ProjectAmountTaskUI.class);
	private FilterInfo filter;
	private boolean isCancel ;
    /**
     * output class constructor
     */
    public F7ProjectAmountTaskUI() throws Exception
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

    public void onLoad() throws Exception {
    	// TODO Auto-generated method stub
    	super.onLoad();
    	this.btnCancel.setEnabled(true);
    	this.btnOk.setEnabled(true);
    	fillTable();

    }
    
    private void initFilter(){
  	  if(getUIContext().get("filter") == null){
        	F7WorkAmountTaskPromptBox parent = (F7WorkAmountTaskPromptBox) getUIContext().get("Owner");
        	this.filter = parent.getFilter();
        }else{
        	this.filter = (FilterInfo)getUIContext().get("filter");
        }
    }
    
    private void fillTable() {
    	initFilter();
    	getMainTable().checkParsed();
    	getMainTable().getColumn("wbs").getStyleAttributes().setHided(true); 
    	getMainTable().getColumn("complete").getStyleAttributes().setHided(true); 
    	getMainTable().getColumn("adminPerson").getStyleAttributes().setHided(true); 
    	getMainTable().getColumn("isleaf").getStyleAttributes().setHided(true); 
    	getMainTable().getColumn("level").getStyleAttributes().setHided(true); 
    	getMainTable().getColumn("islastver").getStyleAttributes().setHided(true); 
     	getMainTable().getColumn("taskexestate").getStyleAttributes().setHided(true); 
    	getMainTable().getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);

    	EntityViewInfo viewInfo = new EntityViewInfo();
    	viewInfo.setSorter(createSorter());
    	viewInfo.setSelector(createSeletorCollection());
    	viewInfo.setFilter(filter);
    	fillRows(viewInfo);
    }
    
    private void fillRows(EntityViewInfo viewInfo){
    	FDCScheduleTaskCollection cols = fetchTaskData(viewInfo);
    	if(cols != null && cols.size() > 0){
    		for(Iterator it = cols.iterator();it.hasNext();){
    			FDCScheduleTaskInfo info = (FDCScheduleTaskInfo) it.next();
    			info.setIsLeaf(info.getWbs().isIsLeaf());
    			IRow row = getMainTable().addRow();
    			setRowData(info,row);
    		}
    	}
    }
    
    /**
     * 选择要显示的字段
     * @return
     */
    private SelectorItemCollection createSeletorCollection(){
    	SelectorItemCollection sic = new SelectorItemCollection();
    	sic.add(new SelectorItemInfo("id"));
    	sic.add(new SelectorItemInfo("number"));
    	sic.add(new SelectorItemInfo("longNumber"));
    	sic.add(new SelectorItemInfo("name"));
    	sic.add(new SelectorItemInfo("start"));
    	sic.add(new SelectorItemInfo("end"));
    	sic.add(new SelectorItemInfo("level"));
    	sic.add(new SelectorItemInfo("effectTimes"));
    	sic.add(new SelectorItemInfo("natureTimes"));
    	sic.add(new SelectorItemInfo("isKey"));
    	sic.add(new SelectorItemInfo("isLeaf"));
    	sic.add(new SelectorItemInfo("complete"));
    	sic.add(new SelectorItemInfo("adminPerson.name"));
    	sic.add(new SelectorItemInfo("schedule.id"));
    	sic.add(new SelectorItemInfo("schedule.name"));
    	sic.add(new SelectorItemInfo("schedule.complete"));
    	sic.add(new SelectorItemInfo("schedule.number"));
    	sic.add(new SelectorItemInfo("schedule.project.id"));
    	sic.add(new SelectorItemInfo("schedule.project.name"));
    	sic.add(new SelectorItemInfo("schedule.project.number"));
//    	sic.add(new SelectorItemInfo("dependEntrys.id"));
    	sic.add(new SelectorItemInfo("wbs.number"));
    	sic.add(new SelectorItemInfo("wbs.isLeaf"));
    	
    	return sic;
    }
    
    /**
     * 数据排序方式
     * @return
     */
    private SorterItemCollection createSorter(){
    	SorterItemCollection sort = new SorterItemCollection();
    	sort.add(new SorterItemInfo("schedule.project.longNumber"));
    	sort.add(new SorterItemInfo("longNumber"));
    	return sort;
    }
    
    /**
     * 查询界面显示的Task数据
     * @param viewInfo 
     * @return
     */
    private FDCScheduleTaskCollection fetchTaskData(EntityViewInfo viewInfo){
    	FDCScheduleTaskCollection  cols = null;
    	try {
			 cols = FDCScheduleTaskFactory.getRemoteInstance().getFDCScheduleTaskCollection(viewInfo);
		} catch (BOSException e) {
			// TODO Auto-generated catch block
			this.handleException(e);
		}
		return cols;
    }
    
    /**
     * 关键方法
     * @param info
     * @param row
     */
    private void setRowData(FDCScheduleTaskInfo info,IRow row){
    	CellTreeNode node = new CellTreeNode();
    	row.setUserObject(info);
    	node.setValue(info.getNumber());
    	node.setTreeLevel(info.getLevel());
    	if(info.getSchedule() !=null && info.getSchedule().getProject() != null){
    	 	row.getCell("project").setValue(info.getSchedule().getProject().getName());
    	}
    	if(info.getWbs() != null ){
    		row.getCell("wbs").setValue(info.getWbs().getNumber());
    	}
    	
    	row.getCell("tasknumber").setValue(node);
    	row.getCell("taskname").setValue(info.getName());
    	row.getCell("complete").getStyleAttributes().setNumberFormat("#,###.00");
    	row.getCell("complete").setValue(info.getComplete());
    	row.getCell("effecttime").setValue(info.getEffectTimes());
    	row.getCell("start").setValue(info.getStart());
    	row.getCell("end").setValue(info.getEnd());
    	if(info.getAdminPerson() !=null){
    		row.getCell("adminPerson").setValue(info.getAdminPerson().getName());
    	}
    	row.getCell("isleaf").setValue(new Boolean(info.isIsLeaf()));
    	row.getCell("iskeytask").setValue(new Boolean(info.isIsKey()));
    	row.getCell("level").setValue(new Integer(info.getLevel()));
    	if(info.getSchedule() !=null){
    		row.getCell("islastver").setValue(new Boolean(info.getSchedule().isIsLatestVer()));
    		row.getCell("taskexestate").setValue(info.getSchedule().getState());
    	}
    	row.getStyleAttributes().setLocked(true);
    	row.getCell("tasknumber").getStyleAttributes().setLocked(false);
    	row.getCell("natureTimes").setValue(info.getNatureTimes());
    	final int level = info.getLevel()-1;
    	final boolean isLeaf = info.isIsLeaf();
    	node.setTreeLevel(level);

    	if(isLeaf){
    		node.setHasChildren(false);
    		if(level>1){
    			row.getStyleAttributes().setHided(true);
    		}
    	}else{
    		//			row.getStyleAttributes().setBackground(Color.BLACK);
    		if(level<=1){
    			if(level==0){
    				node.setCollapse(false);
    			}else{
    				node.setCollapse(true);
    			}
    		}else{
    			node.setCollapse(true);//是否只隐藏根结点
    			row.getStyleAttributes().setHided(true);
    		}
    		node.addClickListener(new NodeClickListener(){
    			public void doClick(CellTreeNode source, ICell cell,
    					int type) {
    				tblMain.revalidate();

    			}
    		});
    		node.setHasChildren(true);
    		row.getStyleAttributes().setBackground(FDCColorConstants.lockColor);
    		row.getStyleAttributes().setLocked(true);
    		row.getCell("tasknumber").getStyleAttributes().setLocked(false);
    	}

    	row.getCell("tasknumber").setValue(node);
    }
    
  
    /**
     * output actionBtnOk_actionPerformed
     */
    public void actionBtnOk_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionBtnOk_actionPerformed(e);
    	isCancel = false;
    	IRow row = FDCTableHelper.getSelectedRow(getMainTable());
    	if(row == null || row.getUserObject() == null){
    		FDCMsgBox.showWarning(this, "请选择行");
    		SysUtil.abort();
    	}
    	this.getUIContext().put("selectedValue", row.getUserObject());
    	this.disposeUIWindow();
    }

    /**
     * output actionBtnCancel_actionPerformed
     */
    public void actionBtnCancel_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionBtnCancel_actionPerformed(e);
    	isCancel = true;
    	this.destroyWindow();
    }

    public boolean isCancel() {
		return isCancel;
	}
    
    /**
     * 查询、模糊查询
     */
    public void actionBtnFilter_actionPerformed(ActionEvent e) throws Exception
    {
    	super.actionBtnFilter_actionPerformed(e);
    	if(getMainTable().getRowCount()>0){
    		getMainTable().removeRows();
    	}
    	getMainTable().checkParsed();
    	getMainTable().getColumn("wbs").getStyleAttributes().setHided(true); 
    	getMainTable().getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
    	if(getUIContext().get("filter") == null){
    		F7WorkAmountTaskPromptBox parent = (F7WorkAmountTaskPromptBox) getUIContext().get("Owner");
    		this.filter = parent.getFilter();
    	}else{
    		this.filter = (FilterInfo)getUIContext().get("filter");
    	}
    	EntityViewInfo viewInfo = new EntityViewInfo();
    	viewInfo.setSorter(createSorter());
    	viewInfo.setSelector(createSeletorCollection());
    	
    	for(int i=filter.getFilterItems().size()-1;i>=0;i--){
    		FilterItemInfo f =filter.getFilterItems().get(i);
    		if(f!=null&& "id".equals(f.getPropertyName())){
    			filter.getFilterItems().remove(f);
    		}
    	}
    	
    	filter.getFilterItems().add(new FilterItemInfo("id",getFilterQueryStr(),CompareType.INNER));
    	viewInfo.setFilter(filter);
    	fillRows(viewInfo);
    	
    }
    
    
    private boolean isBlurQuery(){
    	return chkIsLike.isSelected();
    }
    
    /**
     * 生成模糊查询sql
     * @return
     */
    private String getFilterQueryStr(){
    	StringBuffer str = new StringBuffer("select fid from t_sch_fdcscheduletask where ");
    	if(ScheduleTaskFiledEnum.ALL.equals(cmbFilterField.getSelectedItem())){
//    		如果是先择所有字段并且是模糊查询
    		if(isBlurQuery()){
    			List filedList = ScheduleTaskFiledEnum.getEnumList();
    			for(int i=0;i<filedList.size();i++){
    				ScheduleTaskFiledEnum enumItem = (ScheduleTaskFiledEnum) filedList.get(i);
    				
    				if(!enumItem.getValue().equals("ALL")){
    					if(i>1){
    						str.append(" or ");
    					}
    					str.append(enumItem.getValue());
    					str.append(" like '%");
    					if(txtValue.getText() != null && txtValue.getText().trim().length()>0){
    						str.append(txtValue.getText().trim());
    					}
    					str.append("%'");
    				}
    			}
    		}else{
    			List filedList = ScheduleTaskFiledEnum.getEnumList();
    			for(int i=0;i<filedList.size();i++){
    				ScheduleTaskFiledEnum enumItem = (ScheduleTaskFiledEnum) filedList.get(i);
    				
    				if(!enumItem.getValue().equals("ALL")){
    					if(i>1){
    						str.append(" or ");
    					}
    					str.append(enumItem.getValue());
    					str.append(" = '");
    					if(txtValue.getText() != null && txtValue.getText().trim().length()>0){
    						str.append(txtValue.getText().trim());
    					}
    					str.append("'");
    				}
    			}
    		}
    	}else if(ScheduleTaskFiledEnum.NAME.equals(cmbFilterField.getSelectedItem())){
    		if(isBlurQuery()){
    			str.append(ScheduleTaskFiledEnum.NAME_VALUE);
    			str.append(" like '%");
    			if(txtValue.getText() != null && txtValue.getText().trim().length()>0){
    				str.append(txtValue.getText().trim());
    			}
    			str.append("%'");
    		}else{
    			if(txtValue.getText() != null && txtValue.getText().trim().length()>0){
    				str.append(ScheduleTaskFiledEnum.NAME_VALUE);
    				str.append(" = '");
    				str.append(txtValue.getText().trim());
    				str.append("'");
    			}
    		}   		
		}else if(ScheduleTaskFiledEnum.NUMBER.equals(cmbFilterField.getSelectedItem())){
			if(isBlurQuery()){
    			str.append(ScheduleTaskFiledEnum.NUMBER_VALUE);
    			str.append(" like '%");
    			if(txtValue.getText() != null && txtValue.getText().trim().length()>0){
    				str.append(txtValue.getText().trim());
    			}
    			str.append("%'");
    		}else{
    			if(txtValue.getText() != null && txtValue.getText().trim().length()>0){
    				str.append(ScheduleTaskFiledEnum.NUMBER_VALUE);
    				str.append(" = '");
    				str.append(txtValue.getText().trim());
    				str.append("'");
    			}
    		}   		
		}
    	return str.toString();
    }
    
    private KDTable getMainTable() {
    	return this.tblMain;
	}
    public Object getData(){
    	return getUIContext().get("selectedValue");
    }
}