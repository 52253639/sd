/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.swing.KDFileChooser;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basecrm.FDCOrgStructureFactory;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomModePicInfo;
import com.kingdee.eas.fdc.sellhouse.RoomModelFactory;
import com.kingdee.eas.fdc.sellhouse.RoomModelInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.framework.ICoreBase;

/**
 * output class name
 */
public class RoomModelEditUI extends AbstractRoomModelEditUI {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8509594936538212795L;
	private static final Logger logger = CoreUIObject
			.getLogger(RoomModelEditUI.class);
	public static final String KEY_SUBMITTED_DATA = "submittedData";
	public static final String KEY_DELETE_DATA = "deleteData";
	public static final String KEY_NEW_DATA = "newData";
	public static final String KEY_DESTORY_WINDOW = "destoryWindowAfterSubmit";
	private String id = "";
	private static final String CHECK_NAME = "name";
	private static final String CHECK_NUMBER = "number";
	
	/**
	 * 选择上传图片的控件
	 */
	private KDFileChooser chooser = new KDFileChooser();
	private File imgPath = null;// 图片路径

	/**
	 * output class constructor
	 */
	public RoomModelEditUI() throws Exception {
		super();
	}

	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		this.editData = null;
		super.actionAddNew_actionPerformed(e);
		this.getUIContext().put(KEY_NEW_DATA, this.editData);
		this.actionImg.setEnabled(true);

	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {

		super.storeFields();
		this.editData.setIsEnabled(false);
	}

	public void loadFields() {
		super.loadFields();

		if (this.editData.getImgPath() != null) {
			this.txtPicPath.setText(this.editData.getImgPath().toString());
		}
	}

	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = super.getSelectors();
		// sic.add(new SelectorItemInfo("*"));
		sic.add(new SelectorItemInfo("name"));
		sic.add(new SelectorItemInfo("number"));
		sic.add(new SelectorItemInfo("description"));
		sic.add(new SelectorItemInfo("roomModelType.id"));
		sic.add(new SelectorItemInfo("roomModelType.name"));
		sic.add(new SelectorItemInfo("roomModelType.number"));
		sic.add(new SelectorItemInfo("imgPath"));
		return sic;
	}

	protected IObjectValue createNewData() {
		RoomModelInfo info = new RoomModelInfo();
		if (this.editData == null) {
			
			BOSUuid uuid = BOSUuid.create(info.getBOSType());
			if (uuid != null) {
				id = uuid.toString();
				info.setId(uuid);
			}
		}
		if(this.getUIContext().get("SheSellProjectID")!=null){
			String projectId=this.getUIContext().get("SheSellProjectID").toString();
			SellProjectInfo project=new SellProjectInfo();
			project.setId(BOSUuid.read(projectId));
			info.setSellProject(project);
		}
		
		return info;
	}

	/**
	 * 初始化户型类别
	 */
	private void initRoomModelType() {
		FilterInfo filterInfo = new FilterInfo();
		filterInfo.getFilterItems().add(
				new FilterItemInfo("isEnabled", Boolean.TRUE,
						CompareType.EQUALS));
		String queryInfo = "com.kingdee.eas.fdc.sellhouse.app.RoomModelTypeQuery";
		SHEHelper.initF7(this.prmtRoomModelType, queryInfo, filterInfo);
	}

	public void onLoad() throws Exception {
		super.onLoad();
		this.actionAddNew.setVisible(true);
		this.actionAttachment.setVisible(false);
		this.actionEdit.setVisible(true);
		this.actionRemove.setVisible(true);
		this.actionEdit.setEnabled(false);
		this.actionAddNew.setEnabled(false);
		this.actionRemove.setEnabled(false);
		this.actionSave.setVisible(false);
		this.actionSave.setEnabled(false);
		this.actionSubmit.setVisible(true);
		this.actionSubmit.setEnabled(true);
		this.actionSubmit.setEnabled(false);
		this.actionCopy.setVisible(false);
		this.actionAbout.setVisible(false);
		this.actionPrint.setVisible(false);
		this.actionPrintPreview.setVisible(false);
		this.actionNext.setVisible(false);
		this.actionLast.setVisible(false);
		this.actionFirst.setVisible(false);
		this.actionPre.setVisible(false);
		this.actionCancel.setVisible(false);
		this.actionCancelCancel.setVisible(false);
		this.prmtRoomModelType.setEditable(false);
		if (OprtState.VIEW.equals(this.getOprtState())) {
			this.actionImg.setEnabled(false);
			this.actionSave.setEnabled(false);
			this.actionSubmit.setEnabled(false);
		} else {
			this.actionImg.setEnabled(true);
		}
		
		
		if(OprtState.ADDNEW.equals(this.getOprtState())){
			this.actionEdit.setEnabled(false);
		}
		

		initRoomModelType();

	}

	public void onShow() throws Exception {
		super.onShow();
		if (isSellOrg()) {
			this.actionEdit.setEnabled(true);
			this.actionAddNew.setEnabled(true);
			this.actionRemove.setEnabled(true);
			if(OprtState.VIEW.equals(this.getOprtState())){
				this.actionSave.setEnabled(false);
				this.actionSubmit.setEnabled(false);
			}else{
				this.actionSave.setEnabled(true);
				this.actionSubmit.setEnabled(true);
			}
			
			if(OprtState.ADDNEW.equals(this.getOprtState())){
				this.actionEdit.setEnabled(false);
				this.actionRemove.setEnabled(false);
			}
		}
	}
	
	private boolean isSellOrg() {
		boolean res = false;
		try {
			FullOrgUnitInfo fullOrginfo = SysContext.getSysContext()
					.getCurrentOrgUnit().castToFullOrgUnitInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(
					new FilterItemInfo("sellHouseStrut", Boolean.TRUE,
							CompareType.EQUALS));
			if (fullOrginfo != null) {
				filter.getFilterItems().add(
						new FilterItemInfo("unit.id", fullOrginfo.getId()
								.toString(), CompareType.EQUALS));
			}
			filter.getFilterItems().add(
					new FilterItemInfo("tree.id", OrgConstants.SALE_VIEW_ID,
							CompareType.EQUALS));
			res = FDCOrgStructureFactory.getRemoteInstance().exists(filter);
		} catch (Exception ex) {
			logger.error(ex.getMessage() + "获得售楼组织失败!");
		}

		return res;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return RoomModelFactory.getRemoteInstance();
	}

	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		this.actionImg.setEnabled(true);
		this.actionSave.setEnabled(true);
		super.actionEdit_actionPerformed(e);
	}

	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		FDCClientVerifyHelper.verifyEmpty(this, this.prmtRoomModelType);
		if(OprtState.ADDNEW.equals(this.getOprtState())){
			checkRoomModelNameOrNumber(CHECK_NAME);
			checkRoomModelNameOrNumber(CHECK_NUMBER);
			
		}
		super.actionSubmit_actionPerformed(e);
		if (Boolean.TRUE.equals(this.getUIContext().get(KEY_DESTORY_WINDOW))) {
			destroyWindow();
		}
		RoomModelInfo room = null;
		if (!"".equals(id)) {
			room = RoomModelFactory
					.getRemoteInstance()
					.getRoomModelInfo(
							"select id,name,number,roomModelType.id,roomModelType.name,roomModelType.number,description where id='"
									+ this.id + "'");
		} else {
			room = RoomModelFactory
					.getRemoteInstance()
					.getRoomModelInfo(
							"select id,name,number,roomModelType.id,roomModelType.name,roomModelType.number,description where id='"
									+ this.editData.getId().toString() + "'");

		}
		this.getUIContext().put(KEY_SUBMITTED_DATA, room);
	}
	
	private void checkRoomModelNameOrNumber(String type){
		FilterInfo filter = new FilterInfo();
		if (CHECK_NAME.equals(type)) {
			filter.getFilterItems().add(
					new FilterItemInfo("name", this.txtName.getText(),
							CompareType.EQUALS));
		} else {
			filter.getFilterItems().add(
					new FilterItemInfo("number", this.txtNumber.getText(),
							CompareType.EQUALS));
		}

		if(this.getUIContext().get("SheSellProjectID")!=null){
			String projectId=this.getUIContext().get("SheSellProjectID").toString();
			filter.getFilterItems().add(
					new FilterItemInfo("sellProject.id", projectId,
							CompareType.EQUALS));
		}
		
		try {
			if (RoomModelFactory.getRemoteInstance().exists(filter)) {
				if (CHECK_NAME.equals(type)) {
					FDCMsgBox.showWarning(this, "名称已经存在,不能重复!");
					this.abort();
				} else {
					FDCMsgBox.showWarning(this, "编码已经存在,不能重复!");
					this.abort();
				}
			}
		} catch (EASBizException e) {
			logger.error(e.getMessage());
		} catch (BOSException e) {
			logger.error(e.getMessage());
		}
	}

	protected void initOldData(IObjectValue dataObject) {

	}

	public void actionImg_actionPerformed(ActionEvent e) throws Exception {
		// super.actionImg_actionPerformed(e);
		chooser.resetChoosableFileFilters();
		chooser.removeChoosableFileFilter(chooser.getFileFilter());
		chooser.addChoosableFileFilter(new MyFileFilter(".jpg;.gif", "JPG/GIF格式图片"));
//		chooser.addChoosableFileFilter(new MyFileFilter(".bmp", "BMP格式图片"));
//		chooser.addChoosableFileFilter(new MyFileFilter("", "GIF格式图片"));
		int i = chooser.showOpenDialog(null);
		if (i == KDFileChooser.APPROVE_OPTION) {// 当选择了图片后才进行下面操作
			File file = chooser.getSelectedFile();
			imgPath = file;
		}

		if (imgPath != null) {// 保存图片
			long len = imgPath.length();
			byte[] bytes = new byte[(int) len];
			if (bytes.length > 1024000) {
				FDCMsgBox.showWarning(this, "图片过大，请重新选择!");
				this.abort();
			}
			BufferedInputStream bufferedInputStream = new BufferedInputStream(
					new FileInputStream(imgPath));
			int r = bufferedInputStream.read(bytes);
			if (r != len) {
				throw new IOException("文件读取异常！");
			}
			bufferedInputStream.close();
			this.txtPicPath.setText(imgPath.getPath());
			this.editData.setImgPath(imgPath.getPath());
			RoomModePicInfo pic = new RoomModePicInfo();
			pic.setImgData(bytes);
			this.editData.getPic().clear();
			this.editData.getPic().add(pic);

		}
	}

	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		if (this.editData.getId() != null) {
			boolean result = RoomFactory.getRemoteInstance().exists(
					"select id where roomModel.id='"
							+ this.editData.getId().toString() + "'");
			if (result) {
				FDCMsgBox.showWarning(this, "户型已经被使用，不能删除!");
				this.abort();
			}
		}

		this.getUIContext().put(KEY_DELETE_DATA, this.editData);
		super.actionRemove_actionPerformed(e);
	}
}