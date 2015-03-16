/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.Map;

import javax.swing.JTextField;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.event.KDTActiveCellEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTActiveCellListener;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.swing.KDCheckBox;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.fdc.basecrm.client.FDCSysContext;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitInfo;
import com.kingdee.eas.fdc.sellhouse.IRoom;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SubareaInfo;
import com.kingdee.eas.fdc.sellhouse.TenancyAreaEnum;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class RoomPropNoBatchEditUI extends AbstractRoomPropNoBatchEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(RoomPropNoBatchEditUI.class);
    boolean isload;

    /**
     * output class constructor
     */
    public RoomPropNoBatchEditUI() throws Exception
    {
        super();
        this.actionCanceled.setEnabled(true);
		
        this.tblMain.addKDTEditListener(new KDTEditAdapter(){
			public void editStopped(KDTEditEvent e) {				
				try{
					tblMain_editStopped(e);
				}catch(Exception ex){
					ex.printStackTrace();
				}				
			}
		});
		this.tblMain.addKDTActiveCellListener(new KDTActiveCellListener(){
			public void activeCellChanged(KDTActiveCellEvent e) {
				// TODO 自动生成方法存根
				try{
				tblMain_activeCellChanged(e);
				}catch(Exception ex){
					ex.printStackTrace();
				}				
			}
			
		});
    }
	private void tblMain_editStopped(KDTEditEvent e)throws Exception{
//		int rowIndex=e.getRowIndex();
//		int colIndex=e.getColIndex();
//		
//    	int selIndex = tblMain.getColumn("isSel").getColumnIndex();
//    	int roomPropNoIndex = tblMain.getColumn("roomPropNo").getColumnIndex();
//    	int numberIndex = tblMain.getColumn("number").getColumnIndex();
//    	int idIndex = tblMain.getColumn("id").getColumnIndex();
//		
//    	if (rowIndex>=0){
////	    	if(colIndex == roomPropNoIndex){
//	    		if(tblMain.getCell(rowIndex, roomPropNoIndex).getValue()!=null 
//	    				&& tblMain.getCell(rowIndex, roomPropNoIndex).getValue().toString().length()>0){
//    				tblMain.getCell(rowIndex, selIndex).setValue(Boolean.TRUE);
//				}else{
////	    			MsgBox.showInfo("物业房号不能为空！");
////	    			tblMain.getCell(rowIndex, roomPropNoIndex).setValue(
////	    					tblMain.getCell(rowIndex, numberIndex).getValue());
//	    			tblMain.getCell(rowIndex, selIndex).setValue(Boolean.TRUE);
//				}
////	    	}
//		}
	}
	private void tblMain_activeCellChanged(KDTActiveCellEvent e)throws Exception{
//		int rowIndex=e.getPrevRowIndex();
//		int colIndex=e.getColumnIndex();
//		
//    	int selIndex = tblMain.getColumn("isSel").getColumnIndex();
//    	int roomPropNoIndex = tblMain.getColumn("roomPropNo").getColumnIndex();
//    	int numberIndex = tblMain.getColumn("number").getColumnIndex();
//    	int idIndex = tblMain.getColumn("id").getColumnIndex();
//		
//    	if (rowIndex>=0){
////	    	if(colIndex == roomPropNoIndex){
//	    		if(tblMain.getCell(rowIndex, roomPropNoIndex).getValue()!=null 
//	    				&& tblMain.getCell(rowIndex, roomPropNoIndex).getValue().toString().length()>0){
//	    			
//				}else{
////	    			MsgBox.showInfo("物业房号不能为空！");
////	    			tblMain.getCell(rowIndex, roomPropNoIndex).setValue(
////	    					tblMain.getCell(rowIndex, numberIndex).getValue());
//	    			tblMain.getCell(rowIndex, selIndex).setValue(Boolean.TRUE);
//				}
////	    	}
//		}
	}
   
	protected void execQuery()
	{		
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode)getUIContext().get("nodeid"); 
		
		FilterInfo filter = new FilterInfo();
		if (node.getUserObject() instanceof BuildingUnitInfo) {
			BuildingUnitInfo unit = (BuildingUnitInfo)node.getUserObject();
			BuildingInfo building = (BuildingInfo) ((DefaultKingdeeTreeNode) node.getParent()).getUserObject();
			String buildingId = building.getId().toString();
			filter.getFilterItems().add(new FilterItemInfo("building.id", buildingId));
			filter.getFilterItems().add(new FilterItemInfo("buildUnit.id", unit.getId().toString()));
			// SHEHelper.fillRoomTableByNode(this.tblGraph,node, null, null);
			
		} else if (node.getUserObject() instanceof BuildingInfo) {
			BuildingInfo building = (BuildingInfo) node.getUserObject();
			// ---可查看非明细节点的数据时,这里不区分楼栋是否分单元 zhicheng_jin 090228
			String buildingId = building.getId().toString();
			filter.getFilterItems().add(new FilterItemInfo("building.id", buildingId));
			// SHEHelper.fillRoomTableByNode(this.tblGraph,node, null, null);
		} else if (node.getUserObject() instanceof SubareaInfo) { // 分区
			SubareaInfo subarea = (SubareaInfo) node.getUserObject();
			filter.getFilterItems().add(new FilterItemInfo("building.subarea.id", subarea.getId().toString()));
			this.mainQuery.getSorter().add(new SorterItemInfo("building.id"));
			this.mainQuery.getSorter().add(new SorterItemInfo("unit"));

		} else if (node.getUserObject() instanceof SellProjectInfo) { // 销售项目
			SellProjectInfo sellProject = (SellProjectInfo) node.getUserObject();
			filter.getFilterItems().add(new FilterItemInfo("building.sellProject.id", sellProject.getId().toString()));

			this.mainQuery.getSorter().add(new SorterItemInfo("building.subarea.id"));
			this.mainQuery.getSorter().add(new SorterItemInfo("building.id"));
			this.mainQuery.getSorter().add(new SorterItemInfo("unit"));
		} else {
			filter.getFilterItems().add(new FilterItemInfo("id", null));
		}
		this.mainQuery.setFilter(filter);
		this.mainQuery.getSorter().add(new SorterItemInfo("number"));

		super.execQuery();
	}
	 /**
    *
    * <p>@author tim_gao
    * @description  增加售楼的判断
    * if(FDCSysContext.getInstance().checkIsSHEOrg()){//售楼
           			
           		}else{
           			
           		}<p>
    */
	public void onLoad() throws Exception {
		// TODO Auto-generated method stub
		this.tblMain.getViewManager().setFreezeView(-1,7);		
		super.onLoad();
		execQuery();
		IRow head=tblMain.getHeadRow(0);
		ICell cell=null;
		IColumn icolumn=tblMain.getColumn("roomPropNo");
		icolumn.setKey("roomPropNo");
		icolumn.setWidth(100);
		cell=head.getCell("roomPropNo");
		cell.setValue("物业房号");
		
		KDTextField kdtEntries_roomPropNo_TextField = new KDTextField();
		kdtEntries_roomPropNo_TextField
				.setName("kdtEntries_roomPropNo_CellEditor");
		kdtEntries_roomPropNo_TextField.setVisible(true);
		kdtEntries_roomPropNo_TextField.setEditable(true);
		kdtEntries_roomPropNo_TextField.setHorizontalAlignment(2);
		KDTDefaultCellEditor kdtEntries_roomPropNo_CellEditor = new KDTDefaultCellEditor(
				kdtEntries_roomPropNo_TextField);

		icolumn.setEditor(kdtEntries_roomPropNo_CellEditor);
		tblMain.getSelectManager().setSelectMode(KDTSelectManager.CELL_SELECT);
		icolumn.getStyleAttributes().setLocked(true);	

		ICell cell1=null;
		IColumn icolumn1=tblMain.getColumn("isSel");
		icolumn1.setKey("isSel");
//		icolumn1.setWidth(100);
		cell1=head.getCell("isSel");
		cell1.setValue("修改");
		
		KDCheckBox kdtEntries_isSel_CheckBox = new KDCheckBox();
//		kdtEntries_isSel_CheckBox
//				.setName("kdtEntries_isSel_CellEditor");
//		kdtEntries_isSel_CheckBox.setVisible(true);
		kdtEntries_isSel_CheckBox.setEditable(true);
//
//		kdtEntries_isSel_CheckBox.setHorizontalAlignment(2);
		KDTDefaultCellEditor kdtEntries_isSel_CellEditor = new KDTDefaultCellEditor(
				kdtEntries_isSel_CheckBox);

		icolumn1.setEditor(kdtEntries_isSel_CellEditor);

		int selIndex = tblMain.getColumn("isSel").getColumnIndex();
        for(int i=0; i<tblMain.getRowCount();i++){
        	tblMain.getCell(i, selIndex).setValue(Boolean.FALSE);
        }
        
		icolumn1.getStyleAttributes().setLocked(false);
		
		KDFormattedTextField kdFormattedTextField=new KDFormattedTextField();
		kdFormattedTextField.setRemoveingZeroInDispaly(false);
		kdFormattedTextField.setRemoveingZeroInEdit(false);
		kdFormattedTextField.setNegatived(false);
		kdFormattedTextField.setPrecision(2);
		kdFormattedTextField.setHorizontalAlignment(JTextField.RIGHT);
		kdFormattedTextField.setSupportedEmpty(true);
		kdFormattedTextField.setDataType(BigDecimal.class);
		KDTDefaultCellEditor kdTDefaultCellEditor=new KDTDefaultCellEditor(kdFormattedTextField);
		KDTextField kdTextField=new KDTextField();
		kdTextField.setMaxLength(40);
		KDTDefaultCellEditor kdTDefaultCellEditor2=new KDTDefaultCellEditor(kdTextField);
		tblMain.getColumn("number").setEditor(kdTDefaultCellEditor2);
		tblMain.getColumn("number").getStyleAttributes().setLocked(false);
		tblMain.getColumn("roomNo").setEditor(kdTDefaultCellEditor2);
		tblMain.getColumn("roomNo").getStyleAttributes().setLocked(false);
		tblMain.getColumn("roomPropNo").getStyleAttributes().setLocked(true);
		
		tblMain.getColumn("carbarnArea").setEditor(kdTDefaultCellEditor);
		tblMain.getColumn("carbarnArea").getStyleAttributes().setLocked(false);
		tblMain.getColumn("guardenArea").setEditor(kdTDefaultCellEditor);
		tblMain.getColumn("guardenArea").getStyleAttributes().setLocked(false);
		tblMain.getColumn("balconyArea").setEditor(kdTDefaultCellEditor);
		tblMain.getColumn("balconyArea").getStyleAttributes().setLocked(false);
		tblMain.getColumn("flatArea").setEditor(kdTDefaultCellEditor);
		tblMain.getColumn("flatArea").getStyleAttributes().setLocked(false);
		tblMain.getColumn("useArea").setEditor(kdTDefaultCellEditor);
		tblMain.getColumn("useArea").getStyleAttributes().setLocked(false);
//		tblMain.getColumn("tenancyArea").setEditor(kdTDefaultCellEditor);
//		//by tim_gao 2011-2-22 计租面积3位小数
//		tblMain.getColumn("tenancyArea").getStyleAttributes()
//		.setNumberFormat(FDCHelper.getNumberFtm(3));
//		tblMain.getColumn("tenancyArea").getStyleAttributes().setLocked(false);
//		this.btnTenancyArea.setEnabled(true);
		
		//处理物业，租赁，售楼公用
		//不用销售组织判断,改为售楼组织
		
		this.tblMain.getColumn("number").getStyleAttributes().setHided(true);
		this.tblMain.getColumn("roomNo").getStyleAttributes().setHided(true);
		this.tblMain.getColumn("roomPropNo").getStyleAttributes().setHided(true);
		if(FDCSysContext.getInstance().checkIsSHEOrg()){//售楼
			this.tblMain.getColumn("number").getStyleAttributes().setHided(false);
		}else{
			this.tblMain.getColumn("number").getStyleAttributes().setHided(false);
			this.tblMain.getColumn("roomNo").getStyleAttributes().setHided(false);
			this.tblMain.getColumn("roomPropNo").getStyleAttributes().setHided(false);
		}
		
	}

	public void onShow() throws Exception {
		// TODO Auto-generated method stub
		super.onShow();
		for (int i=0;i<this.toolBar.getToolBarComponents().length;i++){
			if(this.toolBar.getToolBarComponents()[i]!=null&&this.toolBar.getToolBarComponents()[i].getName()!=null){
				this.toolBar.getToolBarComponents()[i].setVisible(false);
			}			
		}
	}

    public void actionCanceled_actionPerformed(ActionEvent e) throws Exception {
		this.disposeUIWindow();
	}
    
    /**
     *
     * <p>@author tim_gao
     * @description  增加售楼的判断
     * if(FDCSysContext.getInstance().checkIsSHEOrg()){//售楼
            			
            		}else{
            			
            		}<p>
     */
    
	public void actionOK_actionPerformed(ActionEvent e) throws Exception {
		
    	int roomPropNoIndex = tblMain.getColumn("roomPropNo").getColumnIndex();
    	int roomNumberIndex = tblMain.getColumn("number").getColumnIndex(); 
    	for(int j=0; j<tblMain.getRowCount();j++){
    		

    		if(FDCSysContext.getInstance().checkIsSHEOrg()){//售楼
    			
    		}else{
    			if(tblMain.getCell(j, roomPropNoIndex).getValue()!=null 
        				&& tblMain.getCell(j, roomPropNoIndex).getValue().toString().length()>0){

    			}else{
        			MsgBox.showInfo("第" + (j+1) + "行物业房号为空，请修改！");
        			SysUtil.abort();
    			}
    		
    		}
    		
    		if(tblMain.getCell(j, roomNumberIndex).getValue()!=null 
    				&& tblMain.getCell(j, roomNumberIndex).getValue().toString().length()>0){

			}else{
    			MsgBox.showInfo("第" + (j+1) + "行房源编号为空，请修改！");
    			SysUtil.abort();
			}
		}				
		String sid = "";
        StringBuffer sErr = new StringBuffer();
        StringBuffer sErrNumber = new StringBuffer();
    	
        StringBuffer sErrRoomNo = new StringBuffer();
        StringBuffer sErrRoomPropNo = new StringBuffer();
		
        IRoom iroom = RoomFactory.getRemoteInstance();
        boolean blnChange = false;
        FilterInfo filter;
        
//        String numberStr="";
//        String allNumberStr="";
//        String roomNo="";
//        String allRoomNo="";
//        for(int j=0;j<tblMain.getRowCount();j++){
//        	if(tblMain.getCell(j, "id").getValue()!=null && ((Boolean)tblMain.getCell(j, "isSel").getValue()).booleanValue()){
//        		for(int k=0;k<tblMain.getRowCount();k++){
//            		if(j!=k){
//            			numberStr=tblMain.getRow(j).getCell("number").getValue().toString();
//                		allNumberStr=tblMain.getRow(k).getCell("number").getValue().toString();
//                		if(numberStr.equals(allNumberStr)){
//                			MsgBox.showInfo("第" + (j+1) + "行预测房号重复，请修改！");
//                			SysUtil.abort();
//                		}	
//                		if(tblMain.getRow(j).getCell("roomNo").getValue()!=null){
//                			roomNo=tblMain.getRow(j).getCell("roomNo").getValue().toString();
//                		}
//                		if(tblMain.getRow(k).getCell("roomNo").getValue()!=null){
//                			allRoomNo=tblMain.getRow(k).getCell("roomNo").getValue().toString();
//                		}
//                		if((!roomNo.equals("")) && (!allRoomNo.equals(""))){
//                			if(roomNo.equals(allRoomNo)){
//                    			MsgBox.showInfo("第" + (j+1) + "行实测房号重复，请修改！");
//                    			SysUtil.abort();
//                    		}	
//                		}
//            		}
//        		}
//        	}
//        }
        
        for(int i=0; i<tblMain.getRowCount();i++){
         	if(tblMain.getCell(i, "id").getValue()!=null && ((Boolean)tblMain.getCell(i, "isSel").getValue()).booleanValue()){
        		if(tblMain.getCell(i, "id").getValue()!=null){
        			blnChange = true;
        			sid = tblMain.getCell(i, "id").getValue().toString();
        			RoomInfo rinfo = iroom.getRoomInfo(new ObjectUuidPK(sid));
        			SelectorItemCollection sic=new SelectorItemCollection();
        			
        			if(FDCSysContext.getInstance().checkIsSHEOrg()){//售楼
            			
            		}else{
            			if(tblMain.getCell(i, "roomPropNo").getValue()!=null){
            				rinfo.setRoomPropNo(tblMain.getCell(i, "roomPropNo").getValue().toString());
            				sic.add("roomPropNo");
            			}
            			if(tblMain.getCell(i, "roomNo").getValue()!=null){
            				rinfo.setRoomNo(tblMain.getCell(i, "roomNo").getValue().toString());
            				sic.add("roomNo");
            			}
            		}
        			
        			if(tblMain.getCell(i, "number").getValue()!=null){
        				rinfo.setNumber(tblMain.getCell(i, "number").getValue().toString());
        				sic.add("number");
        			}
        			
        			if(tblMain.getCell(i, "carbarnArea").getValue()!=null){
        				rinfo.setCarbarnArea(new BigDecimal(tblMain.getCell(i, "carbarnArea").getValue().toString()));
        				sic.add("carbarnArea");
        			}
        			if(tblMain.getCell(i, "guardenArea").getValue()!=null){
        				rinfo.setGuardenArea(new BigDecimal(tblMain.getCell(i, "guardenArea").getValue().toString()));
        				sic.add("guardenArea");
        			}
        			if(tblMain.getCell(i, "balconyArea").getValue()!=null){
        				rinfo.setBalconyArea(new BigDecimal(tblMain.getCell(i, "balconyArea").getValue().toString()));
        				sic.add("balconyArea");
        			}
        			if(tblMain.getCell(i, "flatArea").getValue()!=null){
        				rinfo.setFlatArea(new BigDecimal(tblMain.getCell(i, "flatArea").getValue().toString()));
        				sic.add("flatArea");
        			}
        			if(tblMain.getCell(i, "useArea").getValue()!=null){
        				rinfo.setUseArea(new BigDecimal(tblMain.getCell(i, "useArea").getValue().toString()));
        				sic.add("useArea");
        			}
//        			if(tblMain.getCell(i, "tenancyArea").getValue()!=null){
//        				rinfo.setTenancyArea(new BigDecimal(tblMain.getCell(i, "tenancyArea").getValue().toString()));
//        				sic.add("tenancyArea");
//        			}
        			filter = new FilterInfo();
    				filter.getFilterItems().add(new FilterItemInfo("building", rinfo.getBuilding().getId().toString()));
    				if(rinfo.getBuildUnit()!=null)
    					filter.getFilterItems().add(new FilterItemInfo("buildUnit", rinfo.getBuildUnit().getId()));			
    				filter.getFilterItems().add(new FilterItemInfo("number", rinfo.getNumber()));
    				if (rinfo.getId() != null)
    					filter.getFilterItems().add(new FilterItemInfo("id", rinfo.getId().toString(), CompareType.NOTEQUALS));
    				if (iroom.exists(filter)) {
    					sErr.append("[").append(rinfo.getNumber()).append("]");
    					sErrNumber.append("[").append(rinfo.getNumber()).append("]");
        				continue;
    				}

    	    		if(FDCSysContext.getInstance().checkIsSHEOrg()){//售楼
    	    			
    	    		}else{
    	    			if(rinfo.getRoomNo()!=null && !rinfo.getRoomNo().trim().equals("")){
        					filter = new FilterInfo();
            				filter.getFilterItems().add(new FilterItemInfo("building", rinfo.getBuilding().getId().toString()));
            				if(rinfo.getBuildUnit()!=null)
            					filter.getFilterItems().add(new FilterItemInfo("buildUnit", rinfo.getBuildUnit().getId()));			
            				
                    			filter.getFilterItems().add(new FilterItemInfo("roomNo", rinfo.getRoomNo()));
                    	
            				
            				if (rinfo.getId() != null)
            					filter.getFilterItems().add(new FilterItemInfo("id", rinfo.getId().toString(), CompareType.NOTEQUALS));
            				if (iroom.exists(filter)) {
            					sErr.append("[").append(rinfo.getNumber()).append("]");
            				
            		    			sErrRoomNo.append("[").append(rinfo.getRoomNo()).append("]");
            		    		
                				continue;
            				}
        				}
        				
        				filter = new FilterInfo();
        				filter.getFilterItems().add(new FilterItemInfo("building", rinfo.getBuilding().getId().toString()));
        				if(rinfo.getBuildUnit()!=null)
        					filter.getFilterItems().add(new FilterItemInfo("buildUnit", rinfo.getBuildUnit().getId()));			
        				
                			filter.getFilterItems().add(new FilterItemInfo("roomPropNo", rinfo.getRoomPropNo()));
                		
        			
        				if (rinfo.getId() != null)
        					filter.getFilterItems().add(new FilterItemInfo("id", rinfo.getId().toString(), CompareType.NOTEQUALS));
        				if (iroom.exists(filter)) {
        					sErr.append("[").append(rinfo.getNumber()).append("]");
        					
                    			sErrRoomPropNo.append("[").append(rinfo.getRoomPropNo()).append("]");
                    		
            				continue;
        				}
    	    		}
    				
    				
        			try{
        				iroom.updatePartial(rinfo ,sic);
        			}catch(BOSException e1){
        				sErr.append("[").append(tblMain.getCell(i, "number").getValue().toString()).append("]");
        				continue;
        			}
        		}
        	}
        }
        if(blnChange){
        	if(sErr.length()>0){
        		
        		
        		if(FDCSysContext.getInstance().checkIsSHEOrg()){//售楼
        			if(sErrNumber.length()>0){
            			MsgBox.showInfo("房源编号为" + sErr.toString() + "的房间房源编号修改失败,预房源编号"+ sErrNumber.toString() + "已存在！");
            		}
        		}else{
        			if(sErrNumber.length()>0){
            			MsgBox.showInfo("预测房号为" + sErr.toString() + "的房间预测房号修改失败,预测房号"+ sErrNumber.toString() + "已存在！");
            		}
        			if(sErrRoomNo.length()>0){
            			MsgBox.showInfo("预测房号为" + sErr.toString() + "的房间实测房号修改失败,实测房号"+ sErrRoomNo.toString() + "已存在！");
            		}else if(sErrRoomPropNo.length()>0){
            			MsgBox.showInfo("预测房号为" + sErr.toString() + "的房间物业房号修改失败,物业房号"+ sErrRoomPropNo.toString() + "已存在！");
            		}
        		}
        		
        	}else{
        		MsgBox.showInfo("修改成功！");
        		this.disposeUIWindow();
	        }
        }
	}
	
//	public void actionTenancyArea_actionPerformed(ActionEvent e)
//			throws Exception {
//		TenancyAreaEnum tae= (TenancyAreaEnum) comboTenancyArea.getSelectedItem();
//		String str=tae.getValue();
//		for(int i =0;i<tblMain.getRowCount();i++){
//			tblMain.getRow(i).getCell("tenancyArea").setValue
//			(tblMain.getRow(i).getCell(str).getValue());
//			tblMain.getRow(i).getCell("isSel").setValue(Boolean.TRUE);
//		}
//	}
	
	/**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }

    /**
     * output tblMain_tableClicked method
     */
    protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
        super.tblMain_tableClicked(e);
    }

    /**
     * output menuItemImportData_actionPerformed method
     */
    protected void menuItemImportData_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        super.menuItemImportData_actionPerformed(e);
    }

    /**
     * output actionPageSetup_actionPerformed
     */
    public void actionPageSetup_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPageSetup_actionPerformed(e);
    }

    /**
     * output actionExitCurrent_actionPerformed
     */
    public void actionExitCurrent_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExitCurrent_actionPerformed(e);
    }

    /**
     * output actionHelp_actionPerformed
     */
    public void actionHelp_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionHelp_actionPerformed(e);
    }

    /**
     * output actionAbout_actionPerformed
     */
    public void actionAbout_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAbout_actionPerformed(e);
    }

    /**
     * output actionOnLoad_actionPerformed
     */
    public void actionOnLoad_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionOnLoad_actionPerformed(e);
    }

    /**
     * output actionSendMessage_actionPerformed
     */
    public void actionSendMessage_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSendMessage_actionPerformed(e);
    }

    /**
     * output actionCalculator_actionPerformed
     */
    public void actionCalculator_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCalculator_actionPerformed(e);
    }

    /**
     * output actionExport_actionPerformed
     */
    public void actionExport_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExport_actionPerformed(e);
    }

    /**
     * output actionExportSelected_actionPerformed
     */
    public void actionExportSelected_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExportSelected_actionPerformed(e);
    }

    /**
     * output actionRegProduct_actionPerformed
     */
    public void actionRegProduct_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRegProduct_actionPerformed(e);
    }

    /**
     * output actionPersonalSite_actionPerformed
     */
    public void actionPersonalSite_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPersonalSite_actionPerformed(e);
    }

    /**
     * output actionProcductVal_actionPerformed
     */
    public void actionProcductVal_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionProcductVal_actionPerformed(e);
    }

    /**
     * output actionAddNew_actionPerformed
     */
    public void actionAddNew_actionPerformed(ActionEvent e) throws Exception
    {
//        super.actionAddNew_actionPerformed(e);
    }

    /**
     * output actionView_actionPerformed
     */
    public void actionView_actionPerformed(ActionEvent e) throws Exception
    {
//        super.actionView_actionPerformed(e);
    }

    /**
     * output actionEdit_actionPerformed
     */
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception
    {
//        super.actionEdit_actionPerformed(e);
    }

    /**
     * output actionRemove_actionPerformed
     */
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRemove_actionPerformed(e);
    }

    /**
     * output actionRefresh_actionPerformed
     */
    public void actionRefresh_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRefresh_actionPerformed(e);
    }

    /**
     * output actionPrint_actionPerformed
     */
    public void actionPrint_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPrint_actionPerformed(e);
    }

    /**
     * output actionPrintPreview_actionPerformed
     */
    public void actionPrintPreview_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPrintPreview_actionPerformed(e);
    }

    /**
     * output actionLocate_actionPerformed
     */
    public void actionLocate_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionLocate_actionPerformed(e);
    }

    /**
     * output actionQuery_actionPerformed
     */
    public void actionQuery_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionQuery_actionPerformed(e);
    }

    /**
     * output actionImportData_actionPerformed
     */
    public void actionImportData_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionImportData_actionPerformed(e);
    }

    /**
     * output actionAttachment_actionPerformed
     */
    public void actionAttachment_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAttachment_actionPerformed(e);
    }

    /**
     * output actionExportData_actionPerformed
     */
    public void actionExportData_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExportData_actionPerformed(e);
    }

    /**
     * output actionToExcel_actionPerformed
     */
    public void actionToExcel_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionToExcel_actionPerformed(e);
    }

    /**
     * output actionStartWorkFlow_actionPerformed
     */
    public void actionStartWorkFlow_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionStartWorkFlow_actionPerformed(e);
    }

    /**
     * output actionPublishReport_actionPerformed
     */
    public void actionPublishReport_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPublishReport_actionPerformed(e);
    }

    /**
     * output actionCancel_actionPerformed
     */
    public void actionCancel_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCancel_actionPerformed(e);
    }

    /**
     * output actionCancelCancel_actionPerformed
     */
    public void actionCancelCancel_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCancelCancel_actionPerformed(e);
    }

    /**
     * output actionQueryScheme_actionPerformed
     */
    public void actionQueryScheme_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionQueryScheme_actionPerformed(e);
    }

	protected ICoreBase getBizInterface() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	protected String getEditUIName() {
		// TODO Auto-generated method stub
		return null;
	}

}