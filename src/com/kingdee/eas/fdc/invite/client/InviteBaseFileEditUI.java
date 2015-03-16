/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.client;

import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import javax.swing.JButton;

import org.apache.log4j.Logger;

import com.jacob.activeX.ActiveXDIY;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.swing.KDFileChooser;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.ISQLExecutor;
import com.kingdee.bos.dao.query.SQLExecutorFactory;
import com.kingdee.eas.base.attachment.util.FileGetter;
import com.kingdee.eas.base.attachment.util.Resrcs;
import com.kingdee.eas.base.attachment.util.StringUtil4File;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.base.permission.client.longtime.ILongTimeTask;
import com.kingdee.eas.base.permission.client.longtime.LongTimeDialog;
import com.kingdee.eas.base.permission.client.util.UITools;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.ContractTypeInfo;
import com.kingdee.eas.fdc.basedata.EntryDescCollection;
import com.kingdee.eas.fdc.basedata.EntryDescFactory;
import com.kingdee.eas.fdc.basedata.EntryDescInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCCommonServerHelper;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCBaseDataClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.client.ContractClientUtils;
import com.kingdee.eas.fdc.invite.InviteBaseFileFactory;
import com.kingdee.eas.fdc.invite.InviteBaseFileInfo;
import com.kingdee.eas.fdc.invite.InviteContentCollection;
import com.kingdee.eas.fdc.invite.InviteContentFactory;
import com.kingdee.eas.fdc.invite.InviteContentInfo;
import com.kingdee.eas.fdc.invite.InviteTypeInfo;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class InviteBaseFileEditUI extends AbstractInviteBaseFileEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(InviteBaseFileEditUI.class);
    public InviteBaseFileEditUI() throws Exception
    {
        super();
        threadMap = new HashMap();
    }
    private InviteContentInfo inviteContentInfo = null;
	
    private String contractId = null;
    Map threadMap;
    Map entryDescMap=new HashMap();
    private boolean isVersion = false;
    
    public void storeFields()
    {
        super.storeFields();
        editData.setVerNumber(Float.valueOf(txtVerNumber.getText()).floatValue());
        if(editData!=null&&editData.getId()!=null){
        	this.contractId=editData.getId().toString();
        }
    }

    public SelectorItemCollection getSelectors() {
    	 SelectorItemCollection sic = super.getSelectors();
         sic.add(new SelectorItemInfo("id"));
         sic.add(new SelectorItemInfo("inviteType.name"));
         sic.add(new SelectorItemInfo("inviteType.number"));
         sic.add(new SelectorItemInfo("isLastVer"));
         sic.add(new SelectorItemInfo("state"));
    	return sic;
    }
    
    public void actionView_actionPerformed(ActionEvent e) throws Exception {
		LongTimeDialog dialog = UITools.getDialog(this);
		if (dialog == null)
			return;
		dialog.setLongTimeTask(new ILongTimeTask() {
			public Object exec() throws Exception {
				KDTable table = tblModel;
				if (table.getSelectManager().size() == 0) {
					MsgBox.showWarning(EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_MustSelected"));
					SysUtil.abort();
				}
				IRow row = KDTableUtil.getSelectedRow(table);
				if (row == null)
					return null;
				if (threadMap.containsKey(row.getCell("fileType"))) {
					Object set[] = threadMap.keySet().toArray();
					String key = set[0].toString();
					FileModifyThread thread = (FileModifyThread) threadMap
							.get(key);
					MsgBox.showWarning("\u9644\u4EF6(" + thread.file.getName()+ ")\u5DF2\u7ECF\u6253\u5F00!");
					return null;
				}
				String InviteContentId = row.getCell("id").getValue().toString();
				InviteContentInfo contentInfo = InviteContentFactory.getRemoteInstance().getInviteContentInfo("select * where id = '" + InviteContentId+ "'");
				inviteContentInfo = contentInfo;
				if (contentInfo == null) {
					MsgBox.showWarning(EASResource.getString("com.kingdee.eas.fdc.contract.client.ContractResource","noContent"));
					return null;
				} else {
					File file = viewContent(inviteContentInfo);
					file.setReadOnly();
					return null;
				}
			}

			public void afterExec(Object result) throws Exception {

			}
		});
		if (dialog != null)
			dialog.show();
	}
    
    
    public File viewContent(InviteContentInfo contentInfo)
			throws BOSException, IOException, FileNotFoundException,
			EASBizException {
		String type = contentInfo.getFileType();
		String name = "";
		String dat = "";
		if (type.indexOf(".") != -1) {
			name = type.substring(0, type.lastIndexOf("."));
			dat = type.substring(type.lastIndexOf("."), type.length());
		}
		File file = File.createTempFile("KDTF-" + name, dat);
		String fullname = file.getPath();
		StringBuffer sb = new StringBuffer(fullname);
		sb.insert(fullname.lastIndexOf("\\") + 1, "\"");
		sb.append("\"");
		fullname = sb.toString();
		FileOutputStream fos = new FileOutputStream(file);
		fos.write(contentInfo.getContentFile());
		fos.close();
		File tempbat = File.createTempFile("tempbat", ".bat");
		FileWriter fw = new FileWriter(tempbat);
		fw.write("start " + fullname);
		fw.close();
		String tempbatFullname = tempbat.getPath();
		Runtime.getRuntime().exec(tempbatFullname);
		return file;
	}
    /**
     * output actionAddLine_actionPerformed
     */
    public void actionAddLine_actionPerformed(ActionEvent e) throws Exception
    {
    	if(isVersion || contractId==null){
    		FDCMsgBox.showWarning(this,"请先保存单据！");
    		this.abort();
    	}
    	File file = chooseFileByDialog();
		if (file == null)
			return;
		if (!file.canRead()) {
			MsgBox.showWarning(this, ContractClientUtils.getRes("readFileError"));
			SysUtil.abort();
		}
		String fullname = file.getName();
		/**
		 * 笔记留痕在振业的使用场景：
		 * 1、在整个房地产业务系统中标准的"附件管理"摒弃不用 <p>
		 * 2、业务单据的基类必须是FDCBillInfo  <p>
		 * 3、业务单据可以有多个正文多个附件    <p> 
		 * 由肖飙彪实现         by Cassiel_peng
		 */
		String filterExpr1="where version=1.0 and parent='" + contractId
		+ "' and filetype='" + fullname + "'";
		if (InviteContentFactory.getRemoteInstance().exists(filterExpr1)){
			MsgBox.showWarning("\u5DF2\u7ECF\u5B58\u5728'" + fullname+ "'\u7684\u9644\u4EF6");
			return;
		}
		/**
		 * 假设房地产业务系统中只有合同模块启用该正文管理
		 * 由于振业跟正中的"正文管理"的使用场景存在显著差异，一条业务单据允许上传多个正文存在巨大风险，故控制
		 * 版本号相同说明用户至少希望对同一个合同可以上传两个合同正文，控制不允许
		 */
		/*String filterExpr2="where version=1.0 and parent='" + contractId+ "'";
		if(InviteContentFactory.getRemoteInstance().exists(filterExpr2)){
			MsgBox.showWarning("一个合同不能上传两个正文！");
			return;
		}*/
		byte content[] = (byte[]) null;
		try {
			content = FileGetter.getBytesFromFile(file);
			if (content == null || content.length == 0) {
				MsgBox.showWarning("所添加的附件大小不能为0字节！");
				SysUtil.abort();
			}
		} catch (IOException ex) {
			MsgBox.showWarning(this, ContractClientUtils
					.getRes("readFileError"));
			SysUtil.abort();
		}
		BigDecimal version = new BigDecimal("1.0");
		InviteContentInfo contentInfo = new InviteContentInfo();
		contentInfo.setVersion(version);
		contentInfo.setParent(contractId);
		contentInfo.setFileType(fullname);
		contentInfo.setContentFile(content);
		InviteContentFactory.getRemoteInstance().addnew(contentInfo);
		initTableData();
    }
    Map dataMap = new HashMap();
    Map initData = new HashMap();
    public void initTableData() throws BOSException, SQLException {
    	if(contractId==null){
    		return;
    	}
		KDTable table = this.tblModel;
		table.removeRows();
		table.getTreeColumn().setDepth(2);
		EntityViewInfo viewInfo = new EntityViewInfo();
		viewInfo.getSelector().add("id");
		viewInfo.getSelector().add("version");
		viewInfo.getSelector().add("fileType");
		viewInfo.getSelector().add("createTime");
		viewInfo.getSelector().add("creator.*");
		viewInfo.getSelector().add("contentFile");
		SorterItemInfo sorterItemInfo = new SorterItemInfo("fileType");
		sorterItemInfo.setSortType(SortType.ASCEND);
		viewInfo.getSorter().add(sorterItemInfo);
		sorterItemInfo = new SorterItemInfo("version");
		sorterItemInfo.setSortType(SortType.DESCEND);
		viewInfo.getSorter().add(sorterItemInfo);
		FilterInfo filterInfo = new FilterInfo();
		filterInfo.appendFilterItem("parent", contractId);
		viewInfo.setFilter(filterInfo);
		InviteContentCollection contentCollection = InviteContentFactory
				.getRemoteInstance().getInviteContentCollection(viewInfo);
		String preName = null;
		for (int i = 0; i < contentCollection.size(); i++) {
			InviteContentInfo contentInfo = contentCollection.get(i);
			initData.put(contentInfo.getId().toString(), contentInfo);
			IRow row = table.addRow();
			row.getCell("id").setValue(contentInfo.getId().toString());
			row.getCell("version").setValue(contentInfo.getVersion());
			row.getCell("creatorName").setValue(
					contentInfo.getCreator().getName());
			row.getCell("createTime").setValue(contentInfo.getCreateTime());
			row.getCell("fileName").setValue(contentInfo.getFileType());
			String name = contentInfo.getFileType();
			if (!name.equals(preName)) {
				row.setTreeLevel(0);
			} else {
				row.setTreeLevel(1);
				row.getCell("fileName").setValue(null);
			}
			preName = name;
		}

		table.getTreeColumn().setDepth(2);
		table.getStyleAttributes().setLocked(true);
		table.getSelectManager().setSelectMode(2);
		if (getUIContext().get("isFromWorkflow") == null
				|| !((Boolean) getUIContext().get("isFromWorkflow"))
						.booleanValue())
			if (table.getRowCount() > 0) {
				table.getSelectManager().select(0, 0);
			} else {
				actionEdit.setEnabled(false);
			}
	}

    /**
     * output actionInsertLine_actionPerformed
     */
    public void actionEditLine_actionPerformed(ActionEvent e) throws Exception
    {
    	if(isVersion || contractId==null){
    		FDCMsgBox.showWarning(this,"请先保存单据！");
    		this.abort();
    	}
    	
    	LongTimeDialog dialog = UITools.getDialog(this);
		if (dialog == null)
			return;
		dialog.setLongTimeTask(new ILongTimeTask() {
			public Object exec() throws Exception {
				KDTable table = tblModel;
				if (table.getSelectManager().size() == 0) {
					MsgBox.showWarning(	EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_MustSelected"));
					SysUtil.abort();
				}
				IRow row = KDTableUtil.getSelectedRow(table);
				if (threadMap.containsKey(row.getCell("fileType"))) {
					Object set[] = threadMap.keySet().toArray();
					String key = set[0].toString();
					FileModifyThread thread = (FileModifyThread) threadMap.get(key);
					MsgBox.showWarning("\u9644\u4EF6(" + thread.file.getName()+ ")\u5DF2\u7ECF\u6253\u5F00!");
					return null;
				}
				String InviteContentId = row.getCell("id").getValue().toString();
				InviteContentInfo contentInfo = InviteContentFactory.getRemoteInstance().getInviteContentInfo("select * where id = '" + InviteContentId	+ "'");
				inviteContentInfo = contentInfo;
				if (contentInfo == null) {
					MsgBox.showWarning(EASResource.getString("com.kingdee.eas.fdc.contract.client.ContractResource","noContent"));
					return null;
				} else {
					String extendedFileName = StringUtil4File.getExtendedFileName(contentInfo.getFileType());
					if (!"doc".equals(extendedFileName)	&& !"docx".equals(extendedFileName)	&& !"wps".equals(extendedFileName)) {
						MsgBox.showWarning("您所选择的文件类型为" + extendedFileName	+ "不适合修改！");
						return null;
					}

					File file = editContent(inviteContentInfo);
					FileModifyThread fileModifyThread = new FileModifyThread(file, inviteContentInfo.getFileType());
					fileModifyThread.start();
					threadMap.put(file.getAbsolutePath(), fileModifyThread);
				}
				return null;
			}

			public void afterExec(Object result) throws Exception {

			}
		});
		if (dialog != null)
			dialog.show();
    }

    /**
     * output actionRemoveLine_actionPerformed
     */
    public void actionRemoveLine_actionPerformed(ActionEvent e) throws Exception
    {
    	if(isVersion || contractId==null){
    		FDCMsgBox.showWarning(this,"请先保存单据！");
    		this.abort();
    	}
    	
    	KDTable table = this.tblModel;
		if (table.getSelectManager().size() == 0) {
			MsgBox.showWarning(this,EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_MustSelected"));
			SysUtil.abort();
		}
		int rtn = MsgBox.showConfirm2("是否删除该记录？");
		if (rtn == 2) {
			return;
		} else {
				IRow row = KDTableUtil.getSelectedRow(table);
				String id = row.getCell("id").getValue().toString();
//				//即使有删除正文的权限也要求只有正文创建者才能删除正文  by Cassiel_peng 2009-9-3
//		        String userId = SysContext.getSysContext().getCurrentUserInfo().getId().toString();
//		        //billInfo是合同，俺们还以为是合同正文，搞得郁闷   by Cassiel_peng 2009-9-12
////		        String creatorId=this.billInfo.getCreator()!=null?this.billInfo.getCreator().getId().toString():null;
//		        //本来是不想new一个SelectorItemCollection对象的，但是通过ID直接来查找InviteContentInfo就老是提示"该记录已经不存在"，只有new一个SelectorItemCollection了莫名其妙！
//		        SelectorItemCollection selector=new SelectorItemCollection();
//		        selector.add("creator.id");
//		        InviteContentInfo InviteContent=InviteContentFactory.getRemoteInstance().getInviteContentInfo(new ObjectUuidPK(BOSUuid.read(id)),selector);
//		        String creatorId=InviteContent.getCreator()!=null?InviteContent.getCreator().getId().toString():null;
//		        
//		        if(!userId.equals(creatorId)){
//		        	MsgBox.showWarning("你不是该正文的创建者，不能删除该正文！");
//		        	return;
//		        }
			InviteContentFactory.getRemoteInstance().delete("where id = '" + id + "'");
			initTableData();
			
			return;
		}
    }

    protected void areaDesc_keyPressed(KeyEvent e) throws Exception {
    	super.areaDesc_keyPressed(e);
    }
    protected void areaDesc_keyReleased(KeyEvent e) throws Exception {
    	super.areaDesc_keyReleased(e);
    	
    	
    }
    protected void areaDesc_keyTyped(KeyEvent e) throws Exception {
    	super.areaDesc_keyTyped(e);
    	
    }
    
    protected void tblModel_tableClicked(KDTMouseEvent e) throws Exception {
    	if (e.getClickCount() == 2) {
    		if(isVersion){
    			FDCMsgBox.showWarning(this,"请先保存单据！");
    			this.abort();
    		}
			actionView_actionPerformed(null);
    	}
    }
    protected void tblModel_tableSelectChanged(KDTSelectEvent e)
    		throws Exception {
    	if(editData==null||editData.getId()==null){
    		FDCMsgBox.showWarning(this,"请先保存单据！");
    		this.abort();
    	}
    	KDTable table = tblModel;
		IRow row = KDTableUtil.getSelectedRow(table);
		if (FDCUtils.isRunningWorkflow(contractId)) {
			if (editData != null
					&& ((editData.getState().equals(FDCBillStateEnum.AUDITTED)||(editData.getState().equals(FDCBillStateEnum.AUDITTING))))){
				actionEditLine.setEnabled(false);
			}
			if(editData != null
					&& editData.getState().equals(FDCBillStateEnum.SUBMITTED)){
				actionEditLine.setEnabled(true);
			}
			if(getUIContext().get("optState") != null&& getUIContext().get("optState").equals("FINDVIEW")){
				actionAddLine.setEnabled(true);
				actionRemoveLine.setEnabled(true);
				actionEditLine.setEnabled(true);
			}
			if((row.getCell("fileName").getValue() == null)){
				actionEditLine.setEnabled(false);
			}
		} else if (editData == null
				|| editData.getState().equals(FDCBillStateEnum.SAVED)
				|| editData.getState().equals(FDCBillStateEnum.SUBMITTED)) {
			if (editData != null
					&& editData.getState().equals(FDCBillStateEnum.AUDITTED))
				actionEditLine.setEnabled(false);
			else
				actionEdit.setEnabled(true);
			if (row != null && row.getCell("fileName") != null
					&& row.getCell("fileName").getValue() != null)
				actionEditLine.setEnabled(true);
			else
				actionEditLine.setEnabled(false);
			if (getUIContext().get("optState") != null
					&& getUIContext().get("optState").equals(OprtState.VIEW))
				actionEditLine.setEnabled(false);
		}
		changeDesc(row);
    }

    int curIndex = 0;
    private void changeDesc(IRow row){
    	
    	if(row==null) return;
    	
    	//先存原来的到缓存中
    	IRow oldRow = tblModel.getRow(curIndex);
    	if(oldRow!=null&& oldRow.getCell("id")!=null){
    		entryDescMap.put(oldRow.getCell("id").getValue().toString(), this.areaDesc.getText());
    	}
    	
    	//再保存带出当前的
    	if(row.getCell("id")!=null){
    		
    		String id = row.getCell("id").getValue().toString();
    		this.areaDesc.setText((String)entryDescMap.get(id));
    	}
    	
    	curIndex = row.getRowIndex();
    }
    /**
     * output actionAttamentCtrl_actionPerformed
     */
    public void actionAttamentCtrl_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAttamentCtrl_actionPerformed(e);
    }

    public void actionSave_actionPerformed(ActionEvent e) throws Exception {
    	super.actionSave_actionPerformed(e);
    	saveContent();
    	saveEntryDesc();
    	//进入编辑状态
    	isVersion=false;
    }
    
    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
    	super.actionSubmit_actionPerformed(e);
    	saveContent();
    	saveEntryDesc();
    	//进入编辑状态
    	isVersion=false;
    }
    private void saveContent() throws Exception {
    	if(isVersion){
    		for(int i=0;i<tblModel.getRowCount();i++){
    			String key = tblModel.getRow(i).getCell("id").getValue().toString();
    			InviteContentInfo content = (InviteContentInfo)dataMap.get(key);
    			if(content==null) continue;
    			//没继承corebase....
    			InviteContentFactory.getRemoteInstance().save(content);
    		}
    		
    	}	
    }
    
    private void saveEntryDesc() throws Exception{
    	
    	if(tblModel.getRowCount()==0){
    		return;
    	}
    	IRow row = KDTableUtil.getSelectedRow(tblModel);
    	changeDesc(row);
    	
    	if(entryDescMap.size()==0){
    		return;
    	}
    	
    	//先删除
    	FilterInfo filter = new FilterInfo();
    	filter.getFilterItems().add(new FilterItemInfo("head",editData.getId().toString()));
    	EntryDescFactory.getRemoteInstance().delete(filter);
    	
		CoreBaseCollection colls = new CoreBaseCollection();
		for(int i=0;i<tblModel.getRowCount();i++){
			String key = tblModel.getRow(i).getCell("id").getValue().toString();
			
			EntryDescInfo desc = new EntryDescInfo();
			desc.setHead(editData.getId().toString());
			desc.setEntry(key);
			desc.setDesc((String)entryDescMap.get(key));
			colls.add(desc);
		}
		if(colls.size()>0)
		EntryDescFactory.getRemoteInstance().addnew(colls);
    }
    
	protected void attachListeners() {
		
	}

	protected void detachListeners() {
		
	}

	protected ICoreBase getBizInterface() throws Exception {
		return InviteBaseFileFactory.getRemoteInstance();
	}

	public ICoreBase getBillInterface() throws Exception {
		return getBizInterface();
	}
	protected KDTable getDetailTable() {
		return this.tblModel;
	}

	protected KDTextField getNumberCtrl() {
		return txtNumber;
	}
	
	private float initVerNumber () {
		InviteTypeInfo type = (InviteTypeInfo)getUIContext().get("obj");
		if(type!=null){
			
			FDCSQLBuilder builder = new FDCSQLBuilder();
			builder.appendSql("select max(m.fvernumber) fvernumber from T_INV_InviteBaseFile m ");
			builder.appendSql("where m.finvitetypeid=? ");
			builder.addParam(type.getId().toString());
			IRowSet rs;
			try {
				rs = builder.executeQuery();
				if(rs!=null){
					while(rs.next()){
						if(rs.getFloat("fvernumber")%1==0.0){
							return rs.getFloat("fvernumber")+1;
						}
						
						float ver = FDCHelper.toBigDecimal(rs.getFloat("fvernumber")+"").setScale(0,BigDecimal.ROUND_UP).floatValue();
						return ver;
					}
				}
			} catch (BOSException e) {
				handUIException(e);
			} catch (SQLException e) {
				handUIException(e);
			}
		}
		return 1.0f;
	}
	
	
	public void onLoad() throws Exception {
		super.onLoad();
		
		
		initUI();
		if(editData!=null && editData.getId()!=null){
			contractId = editData.getId().toString();
		}
		initTableData();
		initEntryDesc();
    	
    	if (OrgConstants.DEF_CU_ID.equals(SysContext.getSysContext().getCurrentCtrlUnit().getId().toString())) {
    		this.btnCopy.setEnabled(true);
			this.btnAddNew.setEnabled(true);
			this.btnEdit.setEnabled(true);
			this.btnRemove.setEnabled(true);
			this.btnAudit.setEnabled(true);
			this.btnUnAudit.setEnabled(true);
			this.menuItemAddNew.setEnabled(true);
			this.menuItemEdit.setEnabled(true);
			this.menuItemRemove.setEnabled(true);
			this.menuItemAudit.setEnabled(true);
			this.menuItemUnAudit.setEnabled(true);
			this.menuItemCopy.setEnabled(true);
			this.actionCancelCancel.setEnabled(true);
			this.actionCancel.setEnabled(true);
			
		} else {
			this.btnCopy.setEnabled(false);
			this.btnAddNew.setEnabled(false);
			this.btnEdit.setEnabled(false);
			this.btnAudit.setEnabled(false);
			this.btnUnAudit.setEnabled(false);
			this.btnRemove.setEnabled(false);
			this.menuItemAddNew.setEnabled(false);
			this.menuItemEdit.setEnabled(false);
			this.menuItemRemove.setEnabled(false);
			this.menuItemAudit.setEnabled(false);
			this.menuItemUnAudit.setEnabled(false);
			this.menuItemCopy.setEnabled(false);
			this.actionCancelCancel.setEnabled(false);
			this.actionCancel.setEnabled(false);
		}
    	
    	if(editData!=null && editData.getId()!=null){
			if(FDCBillStateEnum.AUDITTED.equals(editData.getState())&& editData.isIsLastVer()){
				this.actionCancel.setEnabled(true);
				this.actionCancelCancel.setEnabled(false);
			}else if(FDCBillStateEnum.AUDITTED.equals(editData.getState())&& !editData.isIsLastVer()){
				this.actionCancel.setEnabled(false);
				this.actionCancelCancel.setEnabled(true);
			} else {
				this.actionCancel.setEnabled(false);
				this.actionCancelCancel.setEnabled(false);
			}
		}
   	}
	
	private void initEntryDesc() throws Exception{
		if(editData.getId()==null){
			return ;
		}
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("*");
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(new FilterItemInfo("head",editData.getId().toString()));
		EntryDescCollection descs = EntryDescFactory.getRemoteInstance().getEntryDescCollection(view);
		for(int i=0;i<descs.size();i++){
			EntryDescInfo desc = descs.get(i);
			entryDescMap.put(desc.getEntry(), desc.getDesc());
		}
		IRow row = KDTableUtil.getSelectedRow(tblModel);
		if(row!=null){
			String key  = row.getCell("id").getValue().toString();
			this.areaDesc.setText((String)entryDescMap.get(key));
		}
		
	}
	
	
	protected void updateButtonStatus() {
		super.updateButtonStatus();
		String oprt = getOprtState();
		if(STATUS_FINDVIEW.equals(oprt) || STATUS_VIEW.equals(oprt)){
			this.actionAddLine.setEnabled(false);
			this.actionRemoveLine.setEnabled(false);
			this.actionEditLine.setEnabled(false);
		}else{
			this.actionAddLine.setEnabled(true);
			this.actionRemoveLine.setEnabled(true);
			this.actionEditLine.setEnabled(true);
		}
	}
	
	private void handleVersion(InviteBaseFileInfo info) throws Exception{
		if(info.getId()==null){//第二次进来info为修订版本，返回
    		return;
    	}
    	//设置源版本，如果对修订的版本进行修订则源版本与之同，否则为源版本
    	if(info.getSourceBillId()==null){
    		info.setSourceBillId(info.getId().toString());
    	}
    	
    	
    	info.setId(BOSUuid.create(info.getBOSType()));
//    	info.setIsLastVer(false);
    	info.setNumber(null);
    	info.setName(null);
    	txtName.setText(null);
    	txtNumber.setText(null);
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
    	float ver = (FDCHelper.toBigDecimal(info.getVerNumber()+"").add(new BigDecimal("0.1"))).floatValue();
    	
    	info.setVerNumber(ver);
    	txtVerNumber.setText(info.getVerNumber()+"");
    	//换ID
    	for(int i=0;i<tblModel.getRowCount();i++){
    		IRow row = tblModel.getRow(i);
    		String oldKey = (String)row.getCell("id").getValue().toString();
    		InviteContentInfo content = (InviteContentInfo)initData.get(oldKey);
    		BOSUuid newKey = BOSUuid.create(content.getBOSType()); 
    		content.setId(newKey);
    		content.setParent(editData.getId().toString());
    		row.getCell("id").setValue(newKey);
    		dataMap.put(newKey.toString(), content);
    		
    		Object desc = entryDescMap.get(oldKey);
    		entryDescMap.put(newKey.toString(), desc);
    	}
    	if(tblModel.getRowCount()>0){
    		
    		tblModel.getSelectManager().setActiveRowIndex(0);
    		IRow row = KDTableUtil.getSelectedRow(tblModel);
    		
    		String id = row.getCell("id").getValue().toString();
        	this.areaDesc.setText((String)entryDescMap.get(id));
        	
    	}
		
	}
	
	protected IObjectValue createNewData() {
		InviteBaseFileInfo info = new InviteBaseFileInfo();
		info.setCreator((UserInfo) (SysContext.getSysContext().getCurrentUserInfo()));
		try {
			info.setCreateTime(FDCDateHelper.getServerTimeStamp());
		} catch (BOSException e) {
			e.printStackTrace();
		}
		info.setIsLastVer(false);
		info.setVerNumber(initVerNumber());
		InviteTypeInfo type = (InviteTypeInfo)getUIContext().get("obj");
		if(prmtInviteType.getValue()!=null){
			type = (InviteTypeInfo)prmtInviteType.getValue();
		}
		info.setInviteType(type);
		prmtInviteType.setUserObject(type);
		prmtInviteType.setValue(type);
		return info;
	}
	private void initUI() throws Exception {
		
		// 分录增、删按钮调整至分录上方
		JButton btnAddRuleNew = ctnModel.add(actionAddLine);
//		JButton btnEditRuleNew = ctnModel.add(actionEditLine);
		JButton btnDelRuleNew = ctnModel.add(actionRemoveLine);
		btnAddRuleNew.setIcon(EASResource.getIcon("imgTbtn_addline"));
		btnAddRuleNew.setText("新增模板");
		btnAddRuleNew.setSize(22, 19);
		
		
//		btnEditRuleNew.setIcon(EASResource.getIcon("imgTbtn_insertline"));
//		btnEditRuleNew.setText("编辑模板");
//		btnEditRuleNew.setSize(22, 19);
		
		btnDelRuleNew.setIcon(EASResource.getIcon("imgTbtn_deleteline"));
		btnDelRuleNew.setText("删除模板");
		btnDelRuleNew.setSize(22, 19);
	
		
		this.btnAddLine.setVisible(false);
		this.btnRemoveLine.setVisible(false);
		this.btnInsertLine.setVisible(false);
		this.btnCopy.setVisible(false);
		this.btnCancelCancel.setVisible(false);
		this.btnCancel.setVisible(false);
		this.actionCopy.setEnabled(false);
		this.actionCopy.setVisible(false);
		this.actionCreateTo.setEnabled(false);
		this.actionCreateTo.setVisible(false);
		this.actionCreateFrom.setEnabled(false);
		this.actionCreateFrom.setVisible(false);
		this.actionCancel.setEnabled(true);
		this.actionCancelCancel.setEnabled(true);
		this.actionCancel.setVisible(true);
		this.actionCancelCancel.setVisible(true);
		this.menuItemCopyFrom.setVisible(false);
		this.menuItemCopyFrom.setEnabled(false);
		this.menuItemCopy.setVisible(false);
		this.menuItemCopy.setEnabled(false);
		this.menuItemCancel.setVisible(false);
		this.menuItemCancelCancel.setVisible(false);
		this.menuItemCancel.setEnabled(false);
		this.menuItemCancelCancel.setEnabled(false);
		this.menuBiz.setEnabled(false);
		this.menuBiz.setVisible(false);
		this.menuTable1.setEnabled(false);
		this.menuTable1.setVisible(false);
		tblModel.getColumn("fileType").getStyleAttributes().setHided(true);
		
		this.areaDesc.setMaxLength(1000);
		this.areaDescription.setMaxLength(1000);
		this.txtVerNumber.setPrecision(1);
	}
	
	public static String getRes(String resName) {
		return EASResource.getString("com.kingdee.eas.fdc.contract.client.ChangeAuditResource", resName);
	}
	
	private File chooseFileByDialog() {
		File retFile = null;
		KDFileChooser fc = new KDFileChooser(System.getProperty("user.home"));
		fc.setFileSelectionMode(0);
		fc.setMultiSelectionEnabled(false);
		int retVal = fc.showOpenDialog(this);
		if (retVal == 1)
			return retFile;
		retFile = fc.getSelectedFile();
		if (!retFile.exists()) {
			MsgBox.showInfo(Resrcs.getString("FileNotExisted"));
			return null;
		}
		if (retFile.length() > 0x3200000L) {
			MsgBox.showInfo(Resrcs.getString("FileSizeNotAllowed"));
			return null;
		} else {
			return retFile;
		}
	}
	
	public File editContent(InviteContentInfo contentInfo)
	throws BOSException, IOException, FileNotFoundException,
	EASBizException {
		String type = contentInfo.getFileType();
		String name = "";
		String dat = "";
		if (type.indexOf(".") != -1) {
			name = type.substring(0, type.lastIndexOf("."));
			name = name.replaceAll(" ", "");
			dat = type.substring(type.lastIndexOf("."), type.length());
		}
		File file = File.createTempFile("KDTF-" + name, dat);
		
		FileOutputStream fos = new FileOutputStream(file);
		fos.write(contentInfo.getContentFile());
		fos.close();
		if (dat.equalsIgnoreCase(".doc")||dat.equalsIgnoreCase(".docx")||dat.equalsIgnoreCase(".wps")) {
			String path = file.getAbsolutePath();
			path = path.replaceAll("\\\\", "/");
			String preName = ActiveXDIY.getUserNameFromWord();
			ActiveXDIY.setUserNameToWord(SysContext.getSysContext()
					.getCurrentUserInfo().getName());
			try{
				ActiveXDIY.openWordToWrite(path);
				
			}catch (RuntimeException e) {
				e.printStackTrace();
				// TODO: handle exception
				FDCMsgBox.showError("当前操作发生错误，文档兼容性存在问题，请确认文档是否office兼容格式!");
				Object set[] = threadMap.keySet().toArray();
				for (int i = 0; i < set.length; i++) {
					String key = set[i].toString();
					FileModifyThread thread = (FileModifyThread) threadMap.get(key);
					thread.setRun(false);
				}
				abort();
			}
			ActiveXDIY.setUserNameToWord(preName);
			
		} else {
			File tempbat = File.createTempFile("tempbat", ".bat");
			FileWriter fw = new FileWriter(tempbat);
			String fullname = file.getPath();
			StringBuffer sb = new StringBuffer(fullname);
			sb.insert(fullname.lastIndexOf("\\") + 1, "\"");
			sb.append("\"");
			fullname = sb.toString();
			fw.write("start " + fullname);
			fw.close();
			String tempbatFullname = tempbat.getPath();
			Runtime.getRuntime().exec(tempbatFullname);
		}
		return file;
	}
	class FileModifyThread extends Thread {

		public void run() {
			while (run)
				try {
					sleep(1000L);
					if (file != null && file.exists()) {
						File newFile = new File(file.getAbsolutePath());
						boolean isFree = file.renameTo(newFile);
						if (isFree) {
							if (newFile.lastModified() != oriModifyTime) {
								String sql = "select max(fversion) as version from T_CON_InviteContent where FParent='"
										+ contractId
										+ "' and FfileType='"
										+ fileName + "'";
								BigDecimal version = new BigDecimal("1.0");
								ISQLExecutor executor = SQLExecutorFactory
										.getRemoteInstance(sql);
								try {
									IRowSet rowset = executor.executeSQL();
									if (rowset.next()) {
										Collection collection = rowset
												.toCollection();
										Iterator iterator = collection
												.iterator();
										if (iterator.hasNext()) {
											Vector vector = (Vector) iterator
													.next();
											if (vector.get(0) != null)
												version = new BigDecimal(vector
														.get(0).toString());
										}
									}
								} catch (Exception e) {
									logger.error("@@@TEST@@@获取正文异常 ", e);
									e.printStackTrace();
								}
								if (version.floatValue() == 0.0F)
									version = new BigDecimal("1.0");
								else
									version = version
											.add(new BigDecimal("0.1"));
								InviteContentInfo contentInfo = new InviteContentInfo();
								contentInfo.setVersion(version);
								contentInfo.setParent(contractId);
								contentInfo.setFileType(fileName);
								byte content[] = (byte[]) null;
								try {
									content = FileGetter.getBytesFromFile(file);
									contentInfo.setContentFile(content);
									InviteContentFactory.getRemoteInstance()
											.addnew(contentInfo);
									initTableData();
								} catch (Exception e) {
									logger.error("@@@TEST@@@保存记录时出现异常 ", e);
									e.printStackTrace();
								}
							}
							logger.error("@@@TEST@@@oriModifyTime = " + oriModifyTime);
							logger.error("@@@TEST@@@newFile.lastModified() = " + newFile.lastModified());
							run = false;
						}
					} else {
						logger.error("@@@TEST@@@文件不存在！" + file.getAbsolutePath());
						run = false;
					}
//					System.out.println("thread run");
				} catch (InterruptedException e) {
					logger.error("@@@TEST@@@线程出现异常 ", e);
					e.printStackTrace();
				}
		}

		public void setRun(boolean myRun) {
			run = myRun;
		}

		boolean run;

		File file;

		String fileName;

		long oriModifyTime;

		public FileModifyThread(File myFile, String myFileName) {
			super();
			run = true;
			fileName = "";
			file = myFile;
			fileName = myFileName;
			if (file != null && file.exists())
				oriModifyTime = file.lastModified();
		}
	}
	protected boolean isContinueAddNew() {
		return false;
	}
	protected void afterSubmitAddNew() {
//		super.afterSubmitAddNew();
//		clearData();
	}
	public void actionCopy_actionPerformed(ActionEvent e) throws Exception {
	}
	
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		// TODO Auto-generated method stub
		super.actionAddNew_actionPerformed(e);
		clearData();
	}
	private void clearData(){
		tblModel.removeRows();
		for(int i=0;i<tblModel.getRowCount();i++){
//			tblModel.removeRow(i);
		}
		this.areaDesc.setText(null);
		this.areaDescription.setText(null);
		contractId=null;
	}
	
	public void actionCancel_actionPerformed(ActionEvent e) throws Exception {
		doCancelOrCancelCancel(false);
	}
	
	private void doCancelOrCancelCancel(boolean flag) throws Exception{
		InviteBaseFileInfo info = this.editData;
		info.setIsLastVer(flag);
		String message = null;
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("isLastVer"));
		if (flag) {
			getBizInterface().updatePartial(info, sic);
			message = EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, "Enabled_OK");
		} else {
			getBizInterface().updatePartial(info, sic);
			message = EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, "DisEnabled_OK");
		}
		setMessageText(message);
		showMessage();

		setDataObject(getValue(new ObjectUuidPK(info.getId())));
		loadFields();
		setSave(true);
		setSaved(true);

		actionCancel.setEnabled(flag);
		actionCancelCancel.setEnabled(!flag);
//		this.btnCancelCancel.setVisible(!flag);
//		this.btnCancelCancel.setEnabled(!flag);
//		this.btnCancel.setVisible(flag);
//		this.btnCancel.setEnabled(flag);
	}
	public void actionCancelCancel_actionPerformed(ActionEvent e)
			throws Exception {
		doCancelOrCancelCancel(true);
	}

}