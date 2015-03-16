/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.text.NumberFormat;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.org.AdminOrgUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basecrm.client.FDCSysContext;
import com.kingdee.eas.fdc.sellhouse.CommerceChanceDataTypeInfo;
import com.kingdee.eas.fdc.sellhouse.IRoomAssistantType;
import com.kingdee.eas.fdc.sellhouse.RoomAssistantCollection;
import com.kingdee.eas.fdc.sellhouse.RoomAssistantFactory;
import com.kingdee.eas.fdc.sellhouse.RoomAssistantTypeCollection;
import com.kingdee.eas.fdc.sellhouse.SHECusAssistantDataCollection;
import com.kingdee.eas.fdc.sellhouse.SHECusAssistantDataFactory;
import com.kingdee.eas.fdc.sellhouse.RoomAssistantTypeFactory;
import com.kingdee.eas.fdc.sellhouse.RoomAssistantTypeInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.NumericExceptionSubItem;

/**
 * output class name
 */
public class RoomAssistantTypeEditUI extends AbstractRoomAssistantTypeEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(RoomAssistantTypeEditUI.class);
	Map orgMap = FDCSysContext.getInstance().getOrgMap();
    /**
     * output class constructor
     */
    public RoomAssistantTypeEditUI() throws Exception
    {
        super();
    }
    
    public void onLoad() throws Exception {
		super.onLoad();
		// 设置界面初始化大小
//		this.setSize(290, 60);
		txtName.setRequired(true);
//		txtNumber.setRequired(true);
		btnSave.setVisible(false);
		actionSave.setVisible(false);
		btnCopy.setVisible(false);
		actionCopy.setVisible(false);
		btnPrint.setVisible(false);
		actionPrint.setVisible(false);
		btnPrintPreview.setVisible(false);
		actionPrintPreview.setVisible(false);
		btnCancelCancel.setVisible(false);
		actionCancelCancel.setVisible(false);
		btnCancel.setVisible(false);
		actionCancel.setVisible(false);
		this.txtNumber.setEnabled(false);
		
		//不用销售组织判断,改为售楼组织
		FullOrgUnitInfo orgUnit = SHEHelper.getCurrentSaleOrg().castToFullOrgUnitInfo();
		String idStr =null;
		if(null!= orgUnit.getId()){
			idStr = orgUnit.getId().toString();
		}
		if(null==orgMap.get(idStr)){
			this.btnAddNew.setEnabled(false);
			this.btnEdit.setEnabled(false);
			this.btnRemove.setEnabled(false);
			this.btnCancel.setEnabled(false);
			this.btnCancelCancel.setEnabled(false);

			
		}else{
			this.btnAddNew.setEnabled(true);
			this.btnEdit.setEnabled(true);
			this.btnRemove.setEnabled(true);
			this.btnCancel.setEnabled(true);
			this.btnCancelCancel.setEnabled(true);

			
		}
		if(OprtState.ADDNEW.equals(this.getOprtState())){
			this.btnEdit.setEnabled(false);
			this.btnRemove.setEnabled(false);
			
		}
		
	}
    public void loadFields() {
        super.loadFields();
        this.txtNumber.setEnabled(false);
      

	        
			if(OprtState.ADDNEW.equals(this.getOprtState())){
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new  FilterInfo();
			SorterItemCollection sorterCol = new SorterItemCollection();
			SorterItemInfo sorterInfo = new SorterItemInfo("number");
			
			sorterInfo.setSortType(SortType.DESCEND);
			sorterCol.add(sorterInfo);
			//父节点级别问题
			
			  if(null==this.editData.getParent()){
			filter.getFilterItems().add(new FilterItemInfo("level",new Integer(1)));
			
			  }
			  else{
				  filter.getFilterItems().add(new FilterItemInfo("type.id",this.editData.getParent().getId().toString()));	
				  filter.getFilterItems().add(new FilterItemInfo("level",new Integer(this.editData.getParent().getLevel()+1)));	
					if(this.editData.getParent().getLevel()>=2){
						this.comboSelType.setEnabled(false);
						
						this.comboSelType.setSelectedItem(this.editData.getParent().getSelType());
					}
				}
			view.setSorter(sorterCol);
			view.setFilter(filter);
			IRoomAssistantType iRoomAssType=null;
			RoomAssistantTypeCollection roomAssTypeCol =null;
			try {
				iRoomAssType = ((IRoomAssistantType)this.getBizInterface());
				roomAssTypeCol = iRoomAssType.getRoomAssistantTypeCollection(view);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(null!=roomAssTypeCol&&roomAssTypeCol.size()>0){
				RoomAssistantTypeInfo roomAssType = roomAssTypeCol.get(0);
					NumberFormat nf = NumberFormat.getInstance();//设定2为前补零
					//设置是否使用分组
					nf.setGroupingUsed(false);
					//设置最大整数位数
		       
		        	 nf.setMaximumIntegerDigits(2);
			        //设置最小整数位数    
			        nf.setMinimumIntegerDigits(2);
		        	
		       
		        this.txtNumber.setText(nf.format(new Integer(roomAssType.getNumber()).intValue()+1));
			}else{
				 this.txtNumber.setText("01");
			}
		
			
		}
    }
	public void storeFields() {
		super.storeFields();
	}

	// 判断编码，名称不能为空
	protected void verifyInput(ActionEvent e) throws Exception {
		super.verifyInput(e);
		if (txtNumber.getText() == null || txtNumber.getText().trim().equals("")) {
			MsgBox.showInfo(this, "辅助资料编码不能为空!");
			SysUtil.abort();
		}
		if (txtName.getText() == null || txtName.getText().trim().equals("")) {
			MsgBox.showInfo(this, "辅助资料名称不能为空!");
			SysUtil.abort();
		}
		
		if (editData.getId() != null) {
			if (getBizInterface().exists("where name='" + editData.getName() + "' and id != '" + editData.getId().toString() + "'")) {
				throw new EASBizException(new NumericExceptionSubItem("603","此名称已存在!"));
			}
		}
		if (editData.getId() != null) {
			if (getBizInterface().exists("where number='" + editData.getNumber() + "' and id != '" + editData.getId().toString() + "'")) {
				throw new EASBizException(new NumericExceptionSubItem("603","此编码已存在!"));
			}
		}
		if(OprtState.ADDNEW.equals(this.getOprtState())){
			if (getBizInterface().exists("where name='" + editData.getName() + "'")) {
				throw new EASBizException(new NumericExceptionSubItem("603","此名称已存在!"));
			}
		}
		
		if (this.comboSelType.getSelectedItem()==null) {
			MsgBox.showInfo(this, "类型不能为空!");
			SysUtil.abort();
		}
	}
	
	public void beforeActionPerformed(ActionEvent e) {
		if (actionAddNew.getClass().getName().equals(e.getActionCommand()) || actionEdit.getClass().getName().equals(e.getActionCommand())
				|| actionRemove.getClass().getName().equals(e.getActionCommand()) || actionCopy.getClass().getName().equals(e.getActionCommand())) {
			AdminOrgUnitInfo orgInfo = SysContext.getSysContext().getCurrentAdminUnit();

//			if (orgInfo == null || orgInfo.getUnitLayerType() == null || !("00000000-0000-0000-0000-00000000000162824988".equals(orgInfo.getUnitLayerType().getString("id")))) {
//				MsgBox.showInfo("当前系统组织不是集团,不能操作!");
//				SysUtil.abort();
//			}
		}
		super.beforeActionPerformed(e);
	}
	
	public void setOprtState(String oprtType) {
		super.setOprtState(oprtType);
	}
	
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		if(editData!=null && editData.getId()!=null){
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("type",editData.getId()));
			view.setFilter(filter);
			RoomAssistantCollection rs=RoomAssistantFactory.getRemoteInstance()
				.getRoomAssistantCollection(view);
			if (rs != null && rs.size() > 0) {
				MsgBox.showInfo("此组别下有数据，不能删除!");
				SysUtil.abort();
			}
		}
		if(editData != null  &&  ((RoomAssistantTypeInfo)editData).isIsPopValue()){
			MsgBox.showInfo("预设数据，不能删除!");
			SysUtil.abort();
		}
		
		super.actionRemove_actionPerformed(e);
	}
	
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		if(editData != null  && ((RoomAssistantTypeInfo)editData).isIsPopValue()){
			MsgBox.showInfo("预设数据，不能修改!");
			SysUtil.abort();
		}
		
		super.actionEdit_actionPerformed(e);
	}
	
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		
		super.actionSubmit_actionPerformed(e);
	}
	
	/**
	 * 
	 * 描述：新增权限
	 */
	protected String getAddNewPermItemName() {
//		return "RetailAssistantDataGroup_addnew";
		return null;
	}
	
	/**
	 * 
	 * 描述：查看权限
	 */
	protected String getViewPermItemName() {
		return null;
	}
	
	protected final String getOnloadPermItemName() {
		String state = getOprtState();
		if (state.equals(OprtState.ADDNEW)) {
			return getAddNewPermItemName();
		}
		return getViewPermItemName();
	}
    
    
	protected IObjectValue createNewData() {
		RoomAssistantTypeInfo roomAssType = new RoomAssistantTypeInfo();
		roomAssType.setOrgUnit(SysContext.getSysContext().getCurrentOrgUnit().castToFullOrgUnitInfo());
		
		RoomAssistantTypeInfo parentInfo = (RoomAssistantTypeInfo) getUIContext().get(UIContext.PARENTNODE);
		roomAssType.setParent(parentInfo);
		roomAssType.setIsPopValue(false);
		return roomAssType;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return RoomAssistantTypeFactory.getRemoteInstance();
	}
	
	protected void checkIsOUSealUp() throws Exception {
//		super.checkIsOUSealUp();
	}
   
}