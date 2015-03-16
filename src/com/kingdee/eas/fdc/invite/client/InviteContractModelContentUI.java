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
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.swing.KDFileChooser;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.ISQLExecutor;
import com.kingdee.bos.dao.query.SQLExecutorFactory;
import com.kingdee.eas.base.attachment.util.FileGetter;
import com.kingdee.eas.base.attachment.util.Resrcs;
import com.kingdee.eas.base.attachment.util.StringUtil4File;
import com.kingdee.eas.base.permission.client.longtime.ILongTimeTask;
import com.kingdee.eas.base.permission.client.longtime.LongTimeDialog;
import com.kingdee.eas.base.permission.client.util.UITools;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractContentCollection;
import com.kingdee.eas.fdc.contract.ContractContentFactory;
import com.kingdee.eas.fdc.contract.ContractContentInfo;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.client.ContractBillEditUI;
import com.kingdee.eas.fdc.contract.client.ContractClientUtils;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class InviteContractModelContentUI extends AbstractInviteContractModelContentUI
{
    private static final Logger logger = CoreUIObject.getLogger(InviteContractModelContentUI.class);
    public InviteContractModelContentUI() throws Exception
    {
        super();
        contractId = "";
		isModify = true;
		threadMap = new HashMap();
    }
    private boolean isAddContentAfterAudited() {
		boolean returnVal=false;
		try {
			returnVal=FDCUtils.getDefaultFDCParamByKey(null, null,FDCConstants.FDC_PARAM_ADDCONTENTAUDITED);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnVal;
	}
	public void onLoad() throws Exception {
		super.onLoad();
		billInfo = (FDCBillInfo) getUIContext().get("billInfo");
		contractId = billInfo.getId().toString();
		initTable();
		initTableData();
		if (billInfo != null) {
			//判断单据是否在工作流中   by Cassiel_peng  2009-8-29
			if (FDCUtils.isRunningWorkflow(contractId)) {
				//仅支持在流程中各环节人员按权限添加修改删除正文，流程外不能维护正文
				actionAdd.setEnabled(true);
				actionDelete.setEnabled(true);
				actionEdit.setEnabled(true);
				//所谓的"流程外不能维护正文"，那么如果单据已经触发了工作但是状态还是提交是不受控制的
//				if(billInfo.getState().equals(FDCBillStateEnum.SAVED)
//						||billInfo.getState().equals(
//								FDCBillStateEnum.SUBMITTED)){
//					actionAdd.setEnabled(true);
//					actionDelete.setEnabled(true);
//					actionEdit.setEnabled(true);
//				}
				//如果单据状态为"FINDVIEW"，说明是在流程中，那么所有的正文维护功能都放开，当然还是会有权限控制
//				if(getUIContext().get("optState") != null&& getUIContext().get("optState").equals("FINDVIEW")){
//					actionAdd.setEnabled(true);
//					actionDelete.setEnabled(true);
//					actionEdit.setEnabled(true);
//				}
			} else {
				//已经审批过或者正在审批中的合同不默认不允许维护合同正文
				if (billInfo.getState()!=null&&!billInfo.getState().equals(FDCBillStateEnum.SAVED)
						&& !billInfo.getState().equals(
								FDCBillStateEnum.SUBMITTED)) {
					actionAdd.setEnabled(false);
					actionDelete.setEnabled(false);
					actionEdit.setEnabled(false);
					//参数控制允许审批后可增加合同的正文  by Casssiel_peng 2009-10-2
					if(isAddContentAfterAudited()){
						actionAdd.setEnabled(true);
					}
				}
				//界面操作状态为"查看"时不能进行添加、删除、修改操作
				if (getUIContext().get("optState") != null
						&& getUIContext().get("optState")
								.equals(OprtState.VIEW)) {
					actionAdd.setEnabled(false);
					actionDelete.setEnabled(false);
					actionEdit.setEnabled(false);
					if(isAddContentAfterAudited()){
						actionAdd.setEnabled(true);
					}
				}
			}
			String name = billInfo.getName();
			if (name == null || name.equals(""))
				name = billInfo.getNumber();
			if (name == null || name.equals(""))
				setUITitle("正文管理");
			else
				setUITitle("正文管理 - " + name);
		}
		
		//旭辉不需要添加按钮
		actionAdd.setVisible(false);
	}

	public void initTable() {
		KDTable table = tblContent;
		IRow headRow = table.addHeadRow();
		IColumn column = null;
		column = table.addColumn();
		column.setKey("fileName");
		column.setWidth(260);
		headRow.getCell("fileName").setValue("\u6587\u4EF6\u540D\u79F0");
		column = table.addColumn();
		column.setKey("version");
		column.setWidth(40);
		column.getStyleAttributes().setNumberFormat("#,##0.0");
		column
				.getStyleAttributes()
				.setHorizontalAlign(
						com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment.RIGHT);
		headRow.getCell("version").setValue("\u7248\u672C");
		column = table.addColumn();
		column.setKey("creatorName");
		column.setWidth(60);
		headRow.getCell("creatorName").setValue("\u4FEE\u6539\u4EBA");
		column = table.addColumn();
		column.setKey("creatorTime");
		column.setWidth(150);
		column.getStyleAttributes().setNumberFormat("yyyy-MM-dd HH:mm:ss");
		headRow.getCell("creatorTime").setValue("\u4FEE\u6539\u65F6\u95F4");
		column = table.addColumn();
		column.setKey("id");
		column.getStyleAttributes().setHided(true);
	}

	public void initTableData() throws BOSException, SQLException {
		KDTable table = tblContent;
		table.removeRows();
		table.getTreeColumn().setDepth(2);
		//使用contractId 取的 FID
		FDCSQLBuilder builder = new FDCSQLBuilder();
		String sql = "SELECT FID FROM T_CON_CONTRACTCONTENT WHERE fparent = '"+contractId+"'";
		builder.appendSql(sql);
		IRowSet rowSet = builder.executeQuery();
		Set idSet = new HashSet();
		while(rowSet.next()){
			idSet.add(rowSet.getString("FID"));
		}
		EntityViewInfo viewInfo = new EntityViewInfo();
		viewInfo.getSelector().add("id");
		viewInfo.getSelector().add("version");
		viewInfo.getSelector().add("fileType");
		viewInfo.getSelector().add("createTime");
		viewInfo.getSelector().add("creator.*");
		SorterItemInfo sorterItemInfo = new SorterItemInfo("fileType");
		sorterItemInfo.setSortType(SortType.ASCEND);
		viewInfo.getSorter().add(sorterItemInfo);
		sorterItemInfo = new SorterItemInfo("version");
		sorterItemInfo.setSortType(SortType.DESCEND);
		viewInfo.getSorter().add(sorterItemInfo);
		FilterInfo filterInfo = new FilterInfo();
		filterInfo.getFilterItems().add(new FilterItemInfo("id", idSet,CompareType.INCLUDE));
		viewInfo.setFilter(filterInfo);
		ContractContentCollection contentCollection = ContractContentFactory
				.getRemoteInstance().getContractContentCollection(viewInfo);
//		ContractContentCollection contentCollection = ContractContentFactory
//		.getRemoteInstance().getContractContentCollection("where contract.id ='"+contractId+"'");
		String preName = null;
		for (int i = 0; i < contentCollection.size(); i++) {
			ContractContentInfo contentInfo = contentCollection.get(i);
			IRow row = table.addRow();
			row.getCell("id").setValue(contentInfo.getId().toString());
			row.getCell("version").setValue(contentInfo.getVersion());
			row.getCell("fileName").setValue(contentInfo.getFileType());
			row.getCell("creatorName").setValue(
					contentInfo.getCreator().getName());
			row.getCell("creatorTime").setValue(contentInfo.getCreateTime());
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
				actionView.setEnabled(true);
				actionDelete.setEnabled(true);
				table.getSelectManager().select(0, 0);
			} else {
				actionView.setEnabled(false);
				actionEdit.setEnabled(false);
				actionDelete.setEnabled(false);
			}
	}

	public void actionAdd_actionPerformed(ActionEvent e) throws Exception {
		File file = chooseFileByDialog();
		if (file == null)
			return;
		if (!file.canRead()) {
			MsgBox.showWarning(this, ContractClientUtils
					.getRes("readFileError"));
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
		if (ContractContentFactory.getRemoteInstance().exists(filterExpr1)){
			MsgBox.showWarning("\u5DF2\u7ECF\u5B58\u5728'" + fullname+ "'\u7684\u9644\u4EF6");
			return;
		}
		/**
		 * 假设房地产业务系统中只有合同模块启用该正文管理
		 * 由于振业跟正中的"正文管理"的使用场景存在显著差异，一条业务单据允许上传多个正文存在巨大风险，故控制
		 * 版本号相同说明用户至少希望对同一个合同可以上传两个合同正文，控制不允许
		 */
		/*String filterExpr2="where version=1.0 and parent='" + contractId+ "'";
		if(ContractContentFactory.getRemoteInstance().exists(filterExpr2)){
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
		ContractContentInfo contentInfo = new ContractContentInfo();
		contentInfo.setVersion(version);
		contentInfo.setParent(contractId);
		contentInfo.setFileType(fullname);
		contentInfo.setContentFile(content);
		ContractContentFactory.getRemoteInstance().addnew(contentInfo);
		initTableData();
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

	public void actionView_actionPerformed(ActionEvent e) throws Exception {
		LongTimeDialog dialog = UITools.getDialog(this);
		if (dialog == null)
			return;
		dialog.setLongTimeTask(new ILongTimeTask() {
			public Object exec() throws Exception {
				KDTable table = tblContent;
				if (table.getSelectManager().size() == 0) {
					MsgBox
							.showWarning(

							EASResource
									.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_MustSelected"));
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
					MsgBox.showWarning("\u9644\u4EF6(" + thread.file.getName()
							+ ")\u5DF2\u7ECF\u6253\u5F00!");
					return null;
				}
				String contractContentId = row.getCell("id").getValue()
						.toString();
				ContractContentInfo contentInfo = ContractContentFactory
						.getRemoteInstance().getContractContentInfo(
								"select * where id = '" + contractContentId
										+ "'");
				contractContentInfo = contentInfo;
				if (contentInfo == null) {
					MsgBox
							.showWarning(EASResource
									.getString(
											"com.kingdee.eas.fdc.contract.client.ContractResource",
											"noContent"));
					return null;
				} else {

					File file = viewContent(contractContentInfo);
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

	public File viewContent(ContractContentInfo contentInfo)
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

	public File editContent(ContractContentInfo contentInfo)
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
//		if (dat.equalsIgnoreCase(".doc")||dat.equalsIgnoreCase(".docx")||dat.equalsIgnoreCase(".wps")) {
//			String path = file.getAbsolutePath();
//			path = path.replaceAll("\\\\", "/");
//			String preName = ActiveXDIY.getUserNameFromWord();
//			ActiveXDIY.setUserNameToWord(SysContext.getSysContext()
//					.getCurrentUserInfo().getName());
//			try{
//				ActiveXDIY.openWordToWrite(path);
//				
//			}catch (RuntimeException e) {
//				e.printStackTrace();
//				// TODO: handle exception
//				FDCMsgBox.showError("当前操作发生错误，文档兼容性存在问题，请确认文档是否office兼容格式!");
//				Object set[] = threadMap.keySet().toArray();
//				for (int i = 0; i < set.length; i++) {
//					String key = set[i].toString();
//					FileModifyThread thread = (FileModifyThread) threadMap.get(key);
//					thread.setRun(false);
//				}
//				abort();
//			}
//			ActiveXDIY.setUserNameToWord(preName);
//			
//		} else {
//			file.setLastModified(new Date().getTime());
			File tempbat = File.createTempFile("tempbat", ".bat");
			FileWriter fw = new FileWriter(tempbat);
			String fullname = file.getPath();
			StringBuffer sb = new StringBuffer(fullname);
			sb.insert(fullname.lastIndexOf("\\") + 1, "\"");
			sb.append("\"");
			fullname = sb.toString();
			fw.write("start /I " + fullname);
			fw.close();
			String tempbatFullname = tempbat.getPath();
			Runtime.getRuntime().exec(tempbatFullname);
//		}
		return file;
	}

	protected void disposeUIWindow() {
		Object set[] = threadMap.keySet().toArray();
		for (int i = 0; i < set.length; i++) {
			String key = set[i].toString();
			FileModifyThread thread = (FileModifyThread) threadMap.get(key);
			thread.setRun(false);
		}

		super.disposeUIWindow();
	}

	public void actionDelete_actionPerformed(ActionEvent e) throws Exception {
		KDTable table = tblContent;
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
				//即使有删除正文的权限也要求只有正文创建者才能删除正文  by Cassiel_peng 2009-9-3
		        String userId = SysContext.getSysContext().getCurrentUserInfo().getId().toString();
		        //billInfo是合同，俺们还以为是合同正文，搞得郁闷   by Cassiel_peng 2009-9-12
//		        String creatorId=this.billInfo.getCreator()!=null?this.billInfo.getCreator().getId().toString():null;
		        //本来是不想new一个SelectorItemCollection对象的，但是通过ID直接来查找ContractContentInfo就老是提示"该记录已经不存在"，只有new一个SelectorItemCollection了莫名其妙！
		        SelectorItemCollection selector=new SelectorItemCollection();
		        selector.add("creator.id");
		        selector.add("version");
		        selector.add("parent");
		        ContractContentInfo contractContent=ContractContentFactory.getRemoteInstance().getContractContentInfo(new ObjectUuidPK(BOSUuid.read(id)),selector);
		        String creatorId=contractContent.getCreator()!=null?contractContent.getCreator().getId().toString():null;
		        
		        if(!userId.equals(creatorId)){
		        	MsgBox.showWarning("你不是该正文的创建者，不能删除该正文！");
		        	return;
		        }
		        //如果是v1.0被删除，则需要情况 合同界面上的标准合同字段
		        if(FDCHelper.ONE.equals(contractContent.getVersion())){
		        	int re = MsgBox.showConfirm2("删除此条记录将会删除所有的正文版本以及标准合同！是否确定删除？");
		    		if (re == 2) {
		    			return;
		    		} else {
		    			FDCSQLBuilder builder = new FDCSQLBuilder();
		    			builder.appendSql("delete from T_CON_Contractcontent where fParent = '"+contractContent.getParent()+"'");
		    			builder.execute();
		    			InviteDocumentsEditUI editUI = (InviteDocumentsEditUI)getUIContext().get(UIContext.OWNER);
		    			editUI.prmtContractModel.setEnabled(true);
		    			editUI.prmtContractModel.setValue(null);
		    			initTableData();
		    			return;
		    		}
		        }
			ContractContentFactory.getRemoteInstance().delete("where id = '" + id + "'");
			initTableData();
			return;
		}
	}

	protected void tblContent_tableClicked(KDTMouseEvent e) throws Exception {
		if (e.getClickCount() == 2)
			actionView_actionPerformed(null);
	}

	protected void tblContent_tableSelectChanged(KDTSelectEvent e)
			throws Exception {
		KDTable table = tblContent;
		IRow row = KDTableUtil.getSelectedRow(table);
		if (FDCUtils.isRunningWorkflow(contractId)) {
			if (billInfo != null
					&& ((billInfo.getState().equals(FDCBillStateEnum.AUDITTED)||(billInfo.getState().equals(FDCBillStateEnum.AUDITTING))))){
				actionEdit.setEnabled(false);
			}
			if(billInfo != null
					&& billInfo.getState().equals(FDCBillStateEnum.SUBMITTED)){
				actionEdit.setEnabled(true);
			}
			if(getUIContext().get("optState") != null&& getUIContext().get("optState").equals("FINDVIEW")){
				actionAdd.setEnabled(true);
				actionDelete.setEnabled(true);
				actionEdit.setEnabled(true);
			}
			if((row.getCell("fileName").getValue() == null)){
				actionEdit.setEnabled(false);
			}
		} else if (
				(billInfo.getState()!=null&& billInfo.getState().equals(FDCBillStateEnum.SAVED))
				|| (billInfo.getState()!=null&&billInfo.getState().equals(FDCBillStateEnum.SUBMITTED))) {
			if (billInfo != null
					&& billInfo.getState().equals(FDCBillStateEnum.AUDITTED))
				actionEdit.setEnabled(false);
			else
				actionEdit.setEnabled(true);
			if (row != null && row.getCell("fileName") != null
					&& row.getCell("fileName").getValue() != null)
				actionEdit.setEnabled(true);
			else
				actionEdit.setEnabled(false);
			if (getUIContext().get("optState") != null
					&& getUIContext().get("optState").equals(OprtState.VIEW))
				actionEdit.setEnabled(false);
		}
	}

	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		LongTimeDialog dialog = UITools.getDialog(this);
		if (dialog == null)
			return;
		dialog.setLongTimeTask(new ILongTimeTask() {
			public Object exec() throws Exception {
				KDTable table = tblContent;
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
				String contractContentId = row.getCell("id").getValue().toString();
				SelectorItemCollection selector=new SelectorItemCollection();
			    selector.add("creator.id");
			    selector.add("version");
			    selector.add("parent");
			    selector.add("*");
				ContractContentInfo contentInfo = ContractContentFactory.getRemoteInstance().getContractContentInfo(new ObjectUuidPK(BOSUuid.read(contractContentId)),selector);
				contractContentInfo = contentInfo;
				if (contentInfo == null) {
					MsgBox.showWarning(EASResource.getString("com.kingdee.eas.fdc.contract.client.ContractResource","noContent"));
					return null;
				} else {
					String extendedFileName = StringUtil4File.getExtendedFileName(contentInfo.getFileType());
					if (!"doc".equals(extendedFileName)	&& !"docx".equals(extendedFileName)	&& !"wps".equals(extendedFileName)) {
						MsgBox.showWarning("您所选择的文件类型为" + extendedFileName	+ "不适合修改！");
						return null;
					}
					String userId = SysContext.getSysContext().getCurrentUserInfo().getId().toString();
					String creatorId=contentInfo.getCreator()!=null?contentInfo.getCreator().getId().toString():null;
			        if(!userId.equals(creatorId)){
			        	MsgBox.showWarning("你不是该正文的创建者，不能修改该正文！");
			        	return null;
			        }
					File file = editContent(contractContentInfo);
					FileModifyThread fileModifyThread = new FileModifyThread(file, contractContentInfo.getFileType());
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
	private ContractContentInfo contractContentInfo = null;

	String contractId;

	FDCBillInfo billInfo;

	boolean isModify;

	Map threadMap;
	class FileModifyThread extends Thread {

		public void run() {
			while (run)
				try {
					sleep(1000L);
					if (file != null && file.exists()) {
						File newFile = new File(file.getAbsolutePath());
						boolean isFree = file.renameTo(newFile);
						if (isFree&&file.lastModified() != oriModifyTime) {
//							sleep(500L);
//							File newFile1 = new File(file.getAbsolutePath());
//							if (file.lastModified() != oriModifyTime) {
								String sql = "select max(fversion) as version from T_CON_ContractContent where fparent='"
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
//								logger.error("1111111-----打印啊。。。。！");
								if (version.floatValue() == 0.0F)
									version = new BigDecimal("1.0");
								else
									version = version.add(new BigDecimal("0.1"));
								ContractContentInfo contentInfo = new ContractContentInfo();
								
								contentInfo.setVersion(version);
								contentInfo.setParent(contractId);
								contentInfo.setFileType(fileName);
								byte content[] = (byte[]) null;
								try {
									content = FileGetter.getBytesFromFile(file);
									contentInfo.setContentFile(content);
									ContractContentFactory.getRemoteInstance().addnew(contentInfo);
									initTableData();
								} catch (Exception e) {
									logger.error("@@@TEST@@@保存记录时出现异常 ", e);
									e.printStackTrace();
								}
//							}
							logger.error("@@@TEST@@@oriModifyTime = " + new Date(oriModifyTime));
							logger.error("@@@TEST@@@file.lastModified() = " + new Date(file.lastModified()));
							logger.error("@@@TEST@@@newFile.lastModified() = " + new Date(newFile.lastModified()));
//							logger.error("@@@TEST@@@newFile1.lastModified() = " + new Date(newFile1.lastModified()));
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
//			fileName = "";
			file = myFile;
			fileName = myFileName;
			if (file != null && file.exists())
				oriModifyTime = file.lastModified();
		}
	}
}