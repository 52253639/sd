/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.swing.KDFileChooser;
import com.kingdee.bos.ctrl.swing.KDPanel;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectStringPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.attachment.AttachmentInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.cp.dm.CategoryInfo;
import com.kingdee.eas.cp.dm.DocumentInfo;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.FDCTreeBaseDataInfo;
import com.kingdee.eas.fdc.basedata.client.FDCBaseDataClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.util.FDCAttachmentUtil;
import com.kingdee.eas.fdc.basedata.util.FDCCPDMUtil;
import com.kingdee.eas.fdc.basedata.util.FDCImageAttUtil;
import com.kingdee.eas.fdc.basedata.util.FileNameExtensionFilter;
import com.kingdee.eas.fdc.schedule.GuideTypeEnum;
import com.kingdee.eas.fdc.schedule.RESchTaskTypeEnum;
import com.kingdee.eas.fdc.schedule.StandardTaskGuideCollection;
import com.kingdee.eas.fdc.schedule.StandardTaskGuideEntryCollection;
import com.kingdee.eas.fdc.schedule.StandardTaskGuideEntryInfo;
import com.kingdee.eas.fdc.schedule.StandardTaskGuideFactory;
import com.kingdee.eas.fdc.schedule.StandardTaskGuideInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.StringUtils;

/**
 * 
 * @(#)							
 * 版权：		金蝶国际软件集团有限公司版权所有 描述：标准任务指引：附件添加、引入、删除;图片上传、添加、重置
 * 
 * @author 杜红明
 * @version EAS7.0
 * @createDate 2011-8-9
 * @see
 */
public class StandardTaskGuideNewEditNewUI extends AbstractStandardTaskGuideNewEditNewUI {
	/** 删除按钮名称 **/
	private static final String BUTTON_DEL_NAME = "删除";

	/** 引入按钮名称 **/
	private static final String BUTTON_IMPORT_NAME = "引入";

	/** 新增按钮名称 **/
	private static final String BUTTON_ADD_NAME = "新增";

	private static final long serialVersionUID = 1L;
	
	DateFormat FORMAT_TIME = new SimpleDateFormat("yyyyMMddHHmmss");

	private static final Logger logger = CoreUIObject.getLogger(StandardTaskGuideNewEditNewUI.class);
	final private static  int size = 1024*5;
	
	/** 图片上传后形成的图片标识 **/
	private String attID = null;
	/** 当前实体含有的图片表示 **/
	private String sourceAttID = "";
	private FDCImageAttUtil imageAttUtil = null;
	
	/** 图片执行添加或删除的计数 **/
	private int imageCheckTimes = 0;
	
	KDWorkButton btnAddForEntryA = null;
	KDWorkButton btnImoprtForEntryA = null;
	KDWorkButton btnDelForEntryA = null;

	KDWorkButton btnAddForEntryB = null;
	KDWorkButton btnImoprtForEntryB = null;
	KDWorkButton btnDelForEntryB = null;

	KDWorkButton btnAddForImage = null;
	KDWorkButton btnDelForImage = null;

	/**
	 * output class constructor
	 */
	public StandardTaskGuideNewEditNewUI() throws Exception {
		super();
	}

	protected FDCTreeBaseDataInfo getEditData() {
		return this.editData;
	}

	protected KDBizMultiLangBox getNameCtrl() {
		return this.txtName;
	}

	protected KDTextField getNumberCtrl() {
		return this.txtNumber;
	}

	protected IObjectValue createNewData() {
		StandardTaskGuideInfo info = new StandardTaskGuideInfo();
		return info;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return StandardTaskGuideFactory.getRemoteInstance();
	}

	/**
	 * @description 从实体中获取数据,赋值于页面
	 * @author 杜红明
	 * @createDate 2011-8-9
	 * @return
	 * @version EAS7.0
	 * @see
	 */
	public void loadFields() {
		cleanCompentAndSetTableStyle();
		super.loadFields();
		loadFieldOfEntry();
		try {
			loadFieldOfImage();
		} catch (EASBizException e1) {
			handUIException(e1);
		}
		if (StringUtils.isEmpty(editData.getNumber())) {

			if (getUIContext().get("upId") != null) {
				String parentId = getUIContext().get("upId").toString();
				Set numberSet = new HashSet();
				String sql = "select fnumber from T_SCH_StandardTaskGuideNew ";
				FDCSQLBuilder builder = new FDCSQLBuilder();
				builder.appendSql(sql);
				if (!parentId.equals("upid")) {
					try {
						StandardTaskGuideInfo guide = StandardTaskGuideFactory.getRemoteInstance().getStandardTaskGuideInfo(
								new ObjectUuidPK(parentId));
						editData.setParent(guide);
						editData.setLevel(guide.getLevel() + 1);
					} catch (EASBizException e) {
						handUIException(e);
					} catch (BOSException e) {
						handUIException(e);
					}
					builder.appendSql(" where fparentid = ?");
					builder.addParam(editData.getParent().getId().toString());

				} else {
					builder.appendSql(" where fparentid is null");
				}

				try {
					IRowSet rs = builder.executeQuery();
					String strNumber = "";
					while (rs.next()) {
						numberSet.add(Integer.parseInt(rs.getString(1)));
					}
					for (int i = 1; i < 100; i++) {
						if (!numberSet.contains(i)) {
							strNumber = (i < 10 ? "0" + i : i + "");
							break;
						}
					}
					this.txtNumber.setText(strNumber);
					if (editData.getParent() != null && editData.getParent().getNumber() != null) {
						this.txtParentNumber.setText(editData.getParent().getNumber());
					}
					editData.setNumber(strNumber);

				} catch (BOSException e) {
					handUIException(e);
				} catch (SQLException e) {
					handUIException(e);
				}
			}
		} else {
			this.txtNumber.setText(editData.getNumber());
		}
	}

	/**
	 * @description 清理图片、分录行、设置表格风格
	 * @author 杜红明
	 * @createDate 2011-8-9
	 * @return
	 * @version EAS7.0
	 * @see
	 */
	private void cleanCompentAndSetTableStyle() {
		entryA.removeRows();
		entryB.removeRows();
		entryA.getStyleAttributes().setLocked(true);
		entryB.getStyleAttributes().setLocked(true);

	}
	
	protected void afterSubmitAddNew() {
		 super.afterSubmitAddNew();
	}
	

	/**
	 * @description 从实体从获取数据赋予界面
	 * @author 杜红明
	 * @createDate 2011-8-9
	 * @return
	 * @version EAS7.0
	 * @see
	 */
	private void loadFieldOfEntry() {
		for (int i = 0; i < editData.getEntrys().size(); i++) {
			StandardTaskGuideEntryInfo entryInfo = editData.getEntrys().get(i);
			IRow row = null;
			if (GuideTypeEnum.systemFlow.equals(entryInfo.getGuideType())) {// 根据指示类型添加行
				row = entryA.addRow();
			} else {
				row = entryB.addRow();
			}
			copyProperies(entryInfo, row);
		}
	}

	/**
	 * @description 从实体获取拷贝于数据行
	 * @author 杜红明
	 * @createDate 2011-8-9
	 * @return
	 * @version EAS7.0
	 * @see
	 */
	private void copyProperies(StandardTaskGuideEntryInfo entryInfo, IRow row) {
		row.getCell("docName").setValue(entryInfo.getDocName());
		row.getCell("docName").getStyleAttributes().setFontColor(Color.blue);
		row.getCell("docName").getStyleAttributes().setUnderline(true);
		if (entryInfo.isIsCPFile()) {
			row.getCell("docPath").setValue("协同文档平台");
		} else {
			row.getCell("docPath").setValue("系统附件平台文档");
		}
		row.getCell("ID").setValue(entryInfo.getId().toString());
		row.getCell("docID").setValue(entryInfo.getDocID());
		row.getCell("guideType").setValue(entryInfo.getGuideType().getValue());
		row.getCell("isCPFile").setValue((new Boolean(entryInfo.isIsCPFile())));
	}

	/**
	 * @description 加载页面图片
	 * @author 杜红明
	 * @createDate 2011-8-9
	 * @return
	 * @version EAS7.0
	 * @throws EASBizException
	 * @see
	 */
	private void loadFieldOfImage() throws EASBizException {
		try {
			if (imageAttUtil == null && editData.getId() != null) {
				imageAttUtil = new FDCImageAttUtil(this, editData.getId().toString(), null);
				List imageList = imageAttUtil.getImgAtts();
				if (!imageList.isEmpty()) {
					attID = ((AttachmentInfo) imageList.get(0)).getId().toString();
					sourceAttID = attID;
					imageAttUtil.setIMGByAttID(attID, kDPanel1);
				}
			}
		} catch (BOSException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @description 追加分录查询字段
	 * @author 杜红明
	 * @createDate 2011-8-9
	 * @return SelectorItemCollection
	 * @version EAS7.0
	 * @see
	 */
	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = super.getSelectors();
		sic.add("longNumber");
		sic.add("entrys.id");
		sic.add("entrys.docID");
		sic.add("entrys.docPath");
		sic.add("entrys.docName");
		sic.add("entrys.guideType");
		sic.add("entrys.isCPFile");
		sic.add("parent.id");
		sic.add("parent.name");
		sic.add("parent.number");
		sic.add("parent.longNubmer");
		return sic;
	}

	/**
	 * @description 从页面获取数据放入实体中
	 * @author 杜红明
	 * @createDate 2011-8-9
	 * @return
	 * @version EAS7.0
	 * @see
	 */
	public void storeFields() {
		super.storeFields();
		this.editData.getEntrys().clear();
		entryStoreFields(entryA, GuideTypeEnum.systemFlow);
		entryStoreFields(entryB, GuideTypeEnum.referenceCase);
	}

	/**
	 * 保存与删除 流程示意图，只有用户点了“保存”操作时触发<br>
	 * 若上传了新图片，不仅要保存新图片，还要删除老的图片
	 * @author 杜红明
	 */
	private void storeOrDeleteImage() {
		// 1：界面中含有图片，客户端选择关闭,并且说要保存,如果一开始有就删除然后上传图片
		if (kDPanel1.getComponentCount() > 0 && !attID.equals(sourceAttID)) {
			if (!StringUtils.isEmpty(sourceAttID)) {
				DeleteImageTread deleteImageTread = new DeleteImageTread(editData.getId().toString(), sourceAttID);
				deleteImageTread.run();
			}
			UploadImageTread uploadImageTread = new UploadImageTread(attID);
			uploadImageTread.run();
		}
		// 2：界面中没有图片,客户端选择了关闭,并且说要保存，但是本身有图片，那么就删除图片
		if (kDPanel1.getComponentCount() == 0 && !StringUtils.isEmpty(sourceAttID)) {
			DeleteImageTread deleteImageTread = new DeleteImageTread(editData.getId().toString(), sourceAttID);
			deleteImageTread.run();
		}
	}

	/**
	 * @description 循环Table数据行,放入分录实体中
	 * @author 杜红明
	 * @createDate 2011-8-9
	 * @return
	 * @version EAS7.0
	 * @see
	 */
	private void entryStoreFields(KDTable table, GuideTypeEnum guideType) {
		for (int i = 0; i < table.getRowCount(); i++) {
			StandardTaskGuideEntryInfo info = new StandardTaskGuideEntryInfo();
			IRow row = table.getRow(i);
			info.setDocName(getCaseCellData(row, "docName"));
			info.setDocPath(getCaseCellData(row, "docPath"));
			info.setId(BOSUuid.read(getCaseCellData(row, "ID")));
			info.setDocID(getCaseCellData(row, "docID"));
			info.setGuideType(guideType);
			info.setIsCPFile(new Boolean(getCaseCellData(row, "isCPFile")).booleanValue());
			this.editData.getEntrys().add(info);
		}
	}

	/**
	 * @description 从row中获取Cell值并转换类型
	 * @author 杜红明
	 * @createDate 2011-8-9
	 * @return String
	 * @version EAS7.0
	 * @see
	 */
	private String getCaseCellData(IRow row, String cellName) {
		return row.getCell(cellName).getValue().toString();
	}

	/**
	 * @description 让页面重置表头
	 * @author 杜红明
	 * @createDate 2011-8-9
	 * @return
	 * @version EAS7.0
	 * @see
	 */
	public void onLoad() throws Exception {
		rMenuItemSubmitAndAddNew.setEnabled(false);
		rMenuItemSubmitAndAddNew.removeAll();
		this.menuBar.remove(rMenuItemSubmitAndAddNew);
		entryA.checkParsed();
		entryB.checkParsed();
		super.onLoad();
		this.windowTitle = "标准任务指引编辑";
		this.btnCopy.setVisible(false);
		this.btnFirst.setVisible(false);
		this.btnLast.setVisible(false);
		this.btnNext.setVisible(false);
		this.btnPageSetup.setVisible(false);
		this.btnPrint.setVisible(false);
		this.btnPrintPreview.setVisible(false);
		this.btnPre.setVisible(false);
		this.btnCancelCancel.setVisible(false);
		this.btnRemove.setVisible(false);
		
		txtStandardDuration.setPrecision(0);
		txtStandardDuration.setDataType(Integer.class);
		txtStandardDuration.setNegatived(false);
	}
	
	private void notifyDelete(){
		int result =FDCMsgBox.showConfirm2("你确认删除当前所选内容吗？");
		if(result == MsgBox.NO || result == MsgBox.CANCEL){
			abort();
		}
	}

	/**
	 * 
	 * @description 初始化按钮,对按钮添加监听
	 * @author 杜红明
	 * @createDate 2011-8-9
	 * @return
	 * @version EAS7.0
	 * @see
	 */
	protected void initWorkButton() {
		if (btnAddForEntryA == null) { //说明未初始化控件
			initMyWorkButton();
		}
		super.initWorkButton();
		actionCancel.setVisible(false);
		actionCancelCancel.setVisible(false);
		actionCopy.setVisible(false);
		actionFirst.setVisible(false);
		actionNext.setVisible(false);
		actionPre.setVisible(false);
		actionLast.setVisible(false);
		this.menuBiz.setVisible(false);
		this.menuView.setVisible(false);

		btnSave.setText("保存");
		btnSave.setIcon(EASResource.getIcon("imgTbtn_save"));
	}

	private void initMyWorkButton() {
		btnAddForEntryA = createButton("btnAdd", BUTTON_ADD_NAME, "imgTbtn_new");
		btnImoprtForEntryA = createButton("btnImport", BUTTON_IMPORT_NAME, "imgTbtn_input");
		btnDelForEntryA = createButton("btnDel", BUTTON_DEL_NAME, "imgTbtn_delete");

		btnAddForEntryB = createButton("btnAdd", BUTTON_ADD_NAME, "imgTbtn_new");
		btnImoprtForEntryB = createButton("btnImport", BUTTON_IMPORT_NAME, "imgTbtn_input");
		btnDelForEntryB = createButton("btnDel", BUTTON_DEL_NAME, "imgTbtn_delete");

		btnAddForImage = createButton("btnAdd", BUTTON_ADD_NAME, "imgTbtn_new");
		btnDelForImage = createButton("btnDel", BUTTON_DEL_NAME, "imgTbtn_delete");

		entryAContainer.addButton(btnAddForEntryA);
		entryAContainer.addButton(btnImoprtForEntryA);
		entryAContainer.addButton(btnDelForEntryA);

		entryBContainer.addButton(btnAddForEntryB);
		entryBContainer.addButton(btnImoprtForEntryB);
		entryBContainer.addButton(btnDelForEntryB);

		imageContainer.addButton(btnAddForImage);
		imageContainer.addButton(btnDelForImage);

		addBtnListener(btnAddForEntryA, entryA);
		importBtnListener(btnImoprtForEntryA, entryA);
		delBtnListener(btnDelForEntryA, entryA);

		addBtnListener(btnAddForEntryB, entryB);
		importBtnListener(btnImoprtForEntryB, entryB);
		delBtnListener(btnDelForEntryB, entryB);

		addBtnListenerForImage(btnAddForImage);

		delBtnListenerForImage(btnDelForImage);
	}

	public void setOprtState(String oprtType) {
		super.setOprtState(oprtType);
		if (STATUS_ADDNEW.equals(this.oprtState)) {
			setCtrlEnabled(true);
		} else if (STATUS_EDIT.equals(this.oprtState)) {
			setCtrlEnabled(true);
		} else if (STATUS_VIEW.equals(this.oprtState)) {
			setCtrlEnabled(false);
		}
	}
	
	/**
	 * 
	 * @description 页面按钮状态控制
	 * @author 杜红明
	 * @createDate 2011-8-9
	 * @return String
	 * @version EAS7.0
	 * @see
	 */
	public String getOprtState() {
		if (this.oprtState.equals(STATUS_VIEW)) {
			try {
				//TODO 处理长编码的问题，代码不应该放在这里，后续可重构该方法 Added By Owen_wen 2013.11.6 
				restOperate();
			} catch (Exception e) {
				// e.printStackTrace(); shit 外包，吞异常不处理  Added By Owen_wen 2013.11.6 
			}
		}
		if (this.oprtState.equals(STATUS_ADDNEW)) {
		} else {
			try {
				restOperate();
			} catch (Exception e) {
				// e.printStackTrace();
			}
		}
		
		return oprtState;
	}

	/**
	 * 禁用或启动某些按钮控件
	 */
	private void setCtrlEnabled(boolean flag) {
		if (btnAddForEntryA == null) { //说明未初始化控件
			initMyWorkButton();
		}
		btnAddForEntryA.setEnabled(flag);
		btnAddForEntryB.setEnabled(flag);
		btnDelForEntryA.setEnabled(flag);
		btnDelForEntryB.setEnabled(flag);
		btnImoprtForEntryA.setEnabled(flag);
		btnImoprtForEntryB.setEnabled(flag);
		btnAddForImage.setEnabled(flag);
		btnDelForImage.setEnabled(flag);
		//		actionAddNew.setEnabled(flag);
		//		actionEdit.setEnabled(flag);
		//		actionRemove.setEnabled(flag);
	}

	/**
	 * 
	 * @description 从协同平台引入附件到标准任务指引
	 * @author 杜红明
	 * @createDate 2011-8-9
	 * @return
	 * @version EAS7.0
	 * @see
	 */
	private void importBtnListener(KDWorkButton importBtn, final KDTable table) {
		importBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				whetherOrNotEditData();
				try {
					// 弹出协同文档管理
					CategoryInfo category = buildCategory();
					// 弹出栏目
					DocumentInfo dInfo = buildDocument(category);// F7弹出功能
					for (int i = 0; i < table.getRowCount(); i++) {
						IRow row = table.getRow(i);
						if (row.getCell("docID").getValue().equals(dInfo.getId().toString())) {
							return;
						}
					}
					importCPDFile(table, dInfo);
				} catch (Exception ex) {
					handlerException(ex);
				}
			}
		});
	}
	
	/**
	 * 
	 * @description 构建文档区域
	 * @author 杜红明
	 * @createDate 2011-8-9
	 * @return CategoryInfo
	 * @version EAS7.0
	 * @see
	 */
	private CategoryInfo buildCategory() {
		KDBizPromptBox F7Category = new KDBizPromptBox();
		F7Category.setQueryInfo("com.kingdee.eas.fdc.schedule.app.CategoryQuery");
		F7Category.setName("引入");
		F7Category.setDataBySelector();// F7弹出功能
		CategoryInfo category = (CategoryInfo) F7Category.getValue();
		category.setName("引入");
		return category;
	}

	/**
	 * 
	 * @description 构建栏目
	 * @author 杜红明
	 * @param category
	 *            查询实体
	 * @createDate 2011-8-9
	 * @return DocumentInfo
	 * @version EAS7.0
	 * @see
	 */
	private DocumentInfo buildDocument(CategoryInfo category) {
		KDBizPromptBox F7Document = new KDBizPromptBox();
		F7Document.setQueryInfo("com.kingdee.eas.fdc.schedule.app.F7DocumentQuery");
		F7Document.setName("引入");
		SelectorItemCollection sic = new SelectorItemCollection();
		EntityViewInfo view = getCategoryQuery(sic, category);
		F7Document.setEntityViewInfo(view);
		F7Document.setSelectorCollection(sic);
		F7Document.setDataBySelector();
		DocumentInfo dInfo = (DocumentInfo) F7Document.getValue();
		return dInfo;
	}

	/**
	 * 
	 * @description 获取文档
	 * @author 杜红明
	 * @param sic
	 *            查询器
	 * @param category
	 *            查询实体
	 * @return EntityViewInfo
	 * @see
	 */
	private EntityViewInfo getCategoryQuery(SelectorItemCollection sic, CategoryInfo category) {
		EntityViewInfo view = new EntityViewInfo();
		sic.add("title");
		sic.add("contentType");
		sic.add("category.id");
		sic.add("category.name");
		sic.add("category.docArea.name");
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("category.id", category.getId(), CompareType.EQUALS));
		view.setFilter(filter);
		return view;
	}
	
	private void importCPDFile(final KDTable table, DocumentInfo dInfo) throws BOSException, SQLException {
		FDCSQLBuilder sql = new FDCSQLBuilder();
		sql.appendSql(" select FBusinessID as docID, FDisplayName as docName from T_FME_BusiDoc ");
		sql.appendSql(" where FBusinessID = ?");
		sql.addParam(dInfo.getId().toString());
		IRowSet rs = sql.executeQuery();
		while (rs.next()) {
			String docID = rs.getString("docID");
			String docName = rs.getString("docName");
			String path = dInfo.getCategory().getDocArea().getName() + "//" + dInfo.getCategory().getName();
			IRow row = table.addRow();
			StandardTaskGuideEntryInfo entryInfo = new StandardTaskGuideEntryInfo();
			entryInfo.setId(BOSUuid.create(entryInfo.getBOSType()));
			entryInfo.setGuideType(GuideTypeEnum.systemFlow);
			entryInfo.setIsCPFile(true);
			row.getCell("ID").setValue(entryInfo.getId().toString());
			row.getCell("docName").setValue(docName);
			row.getCell("docName").getStyleAttributes().setFontColor(Color.blue);
			row.getCell("docName").getStyleAttributes().setUnderline(true);
			row.getCell("docPath").setValue(path);
			row.getCell("docID").setValue(docID);
			row.getCell("isCPFile").setValue(Boolean.TRUE);
		}
	}

	/**
	 * 
	 * @description 添加附件
	 * @author 杜红明
	 * @createDate 2011-8-9
	 * @return
	 * @version EAS7.0
	 * @see
	 */
	private void addBtnListener(KDWorkButton addBtn, final KDTable table) {
		addBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				whetherOrNotEditData();
				File file = getFileChooser();
				StandardTaskGuideEntryInfo entryinfo = createNewDetail(file, 1);
				try {
					String attachmentID = FDCAttachmentUtil.uploadAtt(entryinfo.getId().toString(), file);
					addRowForEntry(file, table, entryinfo);
					table.getRow(table.getRowCount() - 1).getCell("docID").setValue(attachmentID);
				} catch (Exception ex) {
					if (ex.getMessage() != null) {
						FDCMsgBox.showWarning(ex.getMessage());
					}
					SysUtil.abort();
				}
			}
		});
	}

	/**
	 * 
	 * @description 图片添加
	 * @author 杜红明
	 * @createDate 2011-8-9
	 * @return
	 * @version EAS7.0
	 * @see
	 */
	private void addBtnListenerForImage(KDWorkButton addBtn) {
		addBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				whetherOrNotEditData();
				try {
					addTempImage(kDPanel1);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});

	}
	private File imageFile;
	private void addTempImage(JComponent compt) throws IOException {
		int componentCount = kDPanel1.getComponentCount();
		if (componentCount > 0) {
			if (FDCMsgBox.showConfirm2(this, "重新新增图片将删除以前的图片") == FDCMsgBox.OK) {
				kDPanel1.removeAll();
			} else {
				return;
			}
		}
		imageFile = getImageFileChooser();
		if (imageFile != null) {
			attID = imageFile.getName() + FORMAT_TIME.format(new Date());
			
			ImageIcon icon = imageAttUtil.getImageFromFile(imageFile);
			Dimension size = new Dimension(icon.getIconWidth(), icon.getIconHeight());
			if (compt instanceof JLabel) {
				compt.setSize(size);
				compt.setMaximumSize(size);
				compt.setPreferredSize(size);
				((JLabel) compt).setText(null);
				((JLabel) compt).setIcon(icon);
			} else if (compt instanceof JPanel) {
				compt.removeAll();
				JLabel lable = new JLabel();
				lable.setSize(compt.getSize());
				lable.setHorizontalAlignment(SwingConstants.CENTER);
				double hwRate = (icon.getIconHeight()*1.0)/ icon.getIconWidth() ;
				int width = icon.getIconWidth();
				int height = icon.getIconHeight();
				if(width>lable.getWidth()){
					width = lable.getWidth();
					height = new Integer((int) (lable.getHeight()*hwRate)).intValue();
				}
				
				icon.setImage(icon.getImage().getScaledInstance(width,height, Image.SCALE_DEFAULT));
				
				lable.setIcon(icon);
				((KDPanel) compt).add(lable);
				compt.updateUI();
				imageCheckTimes++;
			}
		}
	}
		
	private void deleteTempImage(JComponent compt) {
		if (compt instanceof JPanel) {
			compt.removeAll();
			compt.updateUI();
			imageCheckTimes++;
		}
	}
	
	private void uploadImage(String attID, JComponent compt) {
		try {
			imageAttUtil.setBosID(this.editData.getId().toString());
			sourceAttID = imageAttUtil.uploadAtt(imageFile);
			imageAttUtil.setIMGByAttID(attID, compt);
			imageAttUtil.refreshAtt();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		super.actionAddNew_actionPerformed(e);
		this.kDPanel1.removeAll();
		this.sourceAttID = null;
		attID = null;
	}
	
	/**
	 * 
	 * @description 图片删除
	 * @author 杜红明
	 * @createDate 2011-8-11
	 * @param delBtn
	 *            删除图片按钮
	 * @return
	 * @version EAS7.0
	 * @see
	 */
	private void delBtnListenerForImage(KDWorkButton delBtn) {
		delBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					notifyDelete();
					deleteTempImage(kDPanel1);
				} catch (Exception ex) {
					handlerException(ex);
				}
			}
		});
	}
	
	private void deleteImage(String bizID, String imageAttID) {
		try {
			FDCAttachmentUtil.deleteAtt(bizID, imageAttID);
			imageAttUtil.refreshAtt();
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @description 文档删除
	 * @author 杜红明
	 * @createDate 2011-8-11
	 * @param delBtn
	 *            文档删除按钮
	 * @param table
	 *            操作的分录对应表
	 * @version EAS7.0
	 * @see
	 */
	private void delBtnListener(KDWorkButton delBtn, final KDTable table) {
		delBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int rows[] = KDTableUtil.getSelectedRows(table);
				IRow row = null;
				if (rows.length >0) {
					try {
						notifyDelete();
						for(int i=rows.length;i>0;i--){
							row = table.getRow(rows[i-1]);
							FDCAttachmentUtil.deleteAtt(editData.getId().toString(), getCaseCellData(row, "docID"));
							table.removeRow(row.getRowIndex());
						}
						
						
					} catch (EASBizException e1) {
						handUIException(e1);
					} catch (BOSException e1) {
						handUIException(e1);
					}
				}
			}
		});
	}

	
	/**
	 * 
	 * @description 异常处理
	 * @author 杜红明
	 * @createDate 2011-8-9
	 * @return
	 * @version EAS7.0
	 * @see
	 */
	private void handlerException(Exception ex) {
		if (ex.getMessage() != null) {
			FDCMsgBox.showWarning(this, ex.getMessage());
		}
		SysUtil.abort();
	}


	/**
	 * 
	 * @description 创建一个Button
	 * @author 杜红明
	 * @param name
	 * @param text
	 * @param image
	 * @return KDWorkButton
	 */
	private KDWorkButton createButton(String name, String text, String image) {
		KDWorkButton btn = new KDWorkButton();
		btn.setName(name);
		btn.setText(text);
		btn.setSize(21, 9);
		btn.setIcon(EASResource.getIcon(image));
		btn.setVisible(true);
		btn.setEnabled(true);
		return btn;
	}

	/**
	 * 
	 * @description 创建分录实体
	 * @author 杜红明
	 * @createDate 2011-8-11
	 * @param file
	 * @param tableIndex
	 * @return StandardTaskGuideEntryInfo
	 * @version EAS7.0
	 * @see
	 */
	private StandardTaskGuideEntryInfo createNewDetail(File file, int tableIndex) {
		StandardTaskGuideEntryInfo entryInfo = new StandardTaskGuideEntryInfo();
		entryInfo.setId(BOSUuid.create(entryInfo.getBOSType()));
		entryInfo.setIsCPFile(false);
		if (tableIndex == 1) {
			entryInfo.setGuideType(GuideTypeEnum.systemFlow);
		} else {
			entryInfo.setGuideType(GuideTypeEnum.referenceCase);
		}
		return entryInfo;
	}

	/**
	 * 
	 * @description 添加一行，赋值数据
	 * @author 杜红明
	 * @param fileName
	 *            附件名
	 * @param filePath
	 *            附件路径
	 * @param table
	 *            填入的分录表
	 * @param entryinfo
	 *            分录实体
	 */
	private void addRowForEntry(File file, KDTable table, StandardTaskGuideEntryInfo entryinfo) {
		IRow row = table.addRow();
		row.getCell("isCPFile").setValue(Boolean.FALSE);
		row.getCell("docName").setValue(file.getName());
		row.getCell("docName").getStyleAttributes().setFontColor(Color.blue);
		row.getCell("docName").getStyleAttributes().setUnderline(true);
		row.getCell("docPath").setValue("系统附件平台文档");
		// row.getCell("docPath").setValue(file.getPath());
		row.getCell("ID").setValue(entryinfo.getId().toString());
	}

	/**
	 * 
	 * @description 获取选择的附件
	 * @author 杜红明
	 * @return File
	 */
	private File getFileChooser() {
		KDFileChooser fc = initFileChooser();
		int retVal = fc.showOpenDialog(this);
		if (retVal == KDFileChooser.CANCEL_OPTION) {
			return null;
		}
		File upFile = fc.getSelectedFile();
		validFileSize(upFile);
		return upFile;
	}
	
	private KDFileChooser initFileChooser(){
		KDFileChooser fc = new KDFileChooser(System.getProperty("user.home"));
		fc.setFileSelectionMode(KDFileChooser.FILES_ONLY);
		fc.setMultiSelectionEnabled(false);
		return fc;
	}
	private void validFileSize(File upFile){
		if (upFile.length() > size * 1024) {
			FDCMsgBox.showWarning(this, "请不要上传文件大于" + size/1024 + "MB的附件！以免单据打开缓慢。");
			SysUtil.abort();
		}
	}

	private File getImageFileChooser() {
		KDFileChooser fc =  initFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("jpg,bmp,gif,png", imageAttUtil.getTypesArray());
		fc.setFileFilter(filter);

		int retVal = fc.showOpenDialog(this);
		if (retVal == KDFileChooser.CANCEL_OPTION) {
			return null;
		}
		File upFile = fc.getSelectedFile();
		validFileSize(upFile);
		return upFile;
	}

	/**
	 * 
	 * @description EntryA双击打开文件
	 * @author 杜红明
	 * @createDate 2011-8-11
	 * @version EAS7.0
	 * @see
	 */
	protected void entryA_tableClicked(KDTMouseEvent e) throws Exception {
		tableClicked(e, entryA);
	}

	/**
	 * 
	 * @description 双击table的第二个单元格,判断文件"是否协同",并打开文档
	 * @author 杜红明
	 * @createDate 2011-8-11
	 * @param 鼠标事件
	 * @param table
	 * @throws EASBizException
	 * @throws BOSException
	 * @throws IOException
	 * @throws SQLException void
	 * @version EAS7.0
	 * @see
	 */
	private void tableClicked(KDTMouseEvent e, KDTable table) throws EASBizException, BOSException, IOException, SQLException {
		if (e.getClickCount() == 1 && e.getColIndex() == 1 && KDTableUtil.getSelectedRow(table) != null) {
			IRow row = KDTableUtil.getSelectedRow(table);// 需要判断是否是附件ID
			Boolean isCPFile = (Boolean) row.getCell("isCPFile").getValue();
			if (isCPFile.booleanValue()) {
				
				try {
					File[] files = FDCCPDMUtil.downLoadFileFromCP(getCaseCellData(row, "docID"), getCaseCellData(row, "docName"));
					FDCAttachmentUtil.openFileInWindows(files[0].getPath());
				} catch (Exception e1) {
					FDCMsgBox.showWarning(this, "打开失败!");
					SysUtil.abort();
				}
			} else {
				FDCAttachmentUtil.openAtt(getCaseCellData(row, "docID"));
			}
		}
	}

	/**
	 * 
	 * @description EntryB双击打开文件
	 * @author 杜红明
	 * @createDate 2011-8-11
	 * @version EAS7.0
	 * @see
	 */
	protected void entryB_tableClicked(KDTMouseEvent e) throws Exception {
		tableClicked(e, entryB);
	}

	/**
	 * 
	 * @description 判断页面是否含有数据
	 * @author 杜红明
	 * @createDate 2011-8-12 void
	 * @version EAS7.0
	 * @see
	 */
	private void whetherOrNotEditData() {
		if (editData.getId() == null) {
			FDCMsgBox.showWarning(this, "上传附件，请先保存单据");
			SysUtil.abort();
		}
	}

	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		super.actionEdit_actionPerformed(e);
		setCtrlEnabled(true);
		btnAddNew.setEnabled(true);
	}

	/**
	 * @description 添加数据（根据级次为1来添加数据）
	 * @author 车忠伟
	 * @createDate 2011-8-9
	 * @version EAS7.0
	 */
	public void addNew() throws Exception {
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add(new SelectorItemInfo("number"));
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("level", "1"));
		view.setFilter(filter);
		StandardTaskGuideCollection collections = StandardTaskGuideFactory.getRemoteInstance().getStandardTaskGuideCollection(view);
		int number = 0;
		String numStr = "";
		if (collections.size() > 0) {
			for (int i = 0; i < collections.size(); i++) {
				StandardTaskGuideInfo standardInfo = (StandardTaskGuideInfo) collections.get(i);
				String num = standardInfo.getNumber();
				if (Integer.parseInt(num) > number) {
					number = Integer.parseInt(num);
				}
			}
		}
		// 设置本级编码，在同级下最大编码加1
		if (number + 1 < 10) {
			StringBuilder numberStr = new StringBuilder();
			numberStr.append("000").append((number + 1) + "");
			numStr = numberStr.toString();
		} else if ((number + 1) >= 10 && (number + 1) < 100) {
			StringBuilder numberStr = new StringBuilder();
			numberStr.append("00").append((number + 1) + "");
			numStr = numberStr.toString();
		} else if ((number + 1) >= 100 && (number + 1) < 1000) {
			StringBuilder numberStr = new StringBuilder();
			numberStr.append("0").append((number + 1) + "");
			numStr = numberStr.toString();
		} else {
			numStr = (number + 1) + "";
		}
		this.txtNumber.setText(numStr);
	}

	/**
	 * @description 编辑数据以及其他操作(根据长编码显示上一级编码)
	 * @author 车忠伟 * @createDate 2011-8-9
	 * @version EAS7.0
	 */
	public void restOperate() throws Exception {
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add(new SelectorItemInfo("longNumber"));
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id", editData.getId().toString()));
		view.setFilter(filter);
		StandardTaskGuideCollection collections = StandardTaskGuideFactory.getRemoteInstance().getStandardTaskGuideCollection(view);
		String longNumber = "";
		if (collections.size() > 0) {
			for (int i = 0; i < collections.size(); i++) {
				StandardTaskGuideInfo info = collections.get(i);
				longNumber = info.getLongNumber();
			}
		}
		if (longNumber.indexOf(".") > 0 || longNumber.indexOf("!") > 0) {
			longNumber = longNumber.replace('!', '.');
			this.txtParentNumber.setText(longNumber);
		}
	}

	protected void verifyInput(ActionEvent e) throws Exception {
		KDBizMultiLangBox txtName = getNameCtrl();
		boolean flag = FDCBaseDataClientUtils.isMultiLangBoxInputNameEmpty(txtName, getEditData(), "name");
		if (flag) {
			txtName.requestFocus(true);
			FDCMsgBox.showInfo("任务名称不能为空！");
			SysUtil.abort();
		}
		Pattern tDouble = Pattern.compile("([0-9]{1,2})");
		if (!tDouble.matcher(this.txtNumber.getText().trim()).matches()) {
			FDCMsgBox.showInfo("编码不能超过2个的数字型字符！");
			SysUtil.abort();
		}
//		Pattern verifyLongNumber = Pattern.compile("([0-9]{1,})");
		Integer duration =  (Integer) (txtStandardDuration.getValue(Integer.class) == null?null:txtStandardDuration.getValue(Integer.class));
		
		if (duration!=null && (duration.intValue()<1 || duration.intValue()>10000)) {
				FDCMsgBox.showInfo("标准工期只允许录入1-10000之间的整数！");
				abort();
		}
	}

	/**
	 * @description 验证编码是否存在
	 * @author 车忠伟
	 * @createDate 2011-8-18
	 * @version EAS7.0
	 */
	public void verifyInputNumber() throws Exception {
		String number = this.txtNumber.getText().trim();
		if (this.txtNumber.getText() == null || "".equals(this.txtNumber.getText().toString().trim())) {
			FDCMsgBox.showInfo("本级编码不能为空！");
			SysUtil.abort();
		}
		this.txtNumber.setText(number);
	}

	/**
	 * @description 查询所有的number
	 * @author 车忠伟
	 * @createDate 2011-8-18
	 * @version EAS7.0
	 */
	public void selectNumber(String number, EntityViewInfo view) throws Exception {
		StandardTaskGuideCollection collection = StandardTaskGuideFactory.getRemoteInstance().getStandardTaskGuideCollection(view);
		if (collection.size() > 0) {
			// 创建string类型的数组，将所有的number放入
			String[] numberArray = new String[collection.size()];
			for (int temp = 0; temp < collection.size(); temp++) {
				StandardTaskGuideInfo schTemplateTaskInfo = (StandardTaskGuideInfo) collection.get(temp);
				numberArray[temp] = schTemplateTaskInfo.getNumber().toString();
			}
			// 循环判断
			for (int m = 0; m < numberArray.length; m++) {
				if (number.equals(numberArray[m])) {
					FDCMsgBox.showWarning(this, "本级编码 " + number + " 已经存在，不能重复");
					SysUtil.abort();
				}
			}
		}

	}

	/**
	 * @description 编辑的时候编码是否存在
	 * @author 车忠伟
	 * @createDate 2011-8-18
	 * @version EAS7.0
	 */
	public void provingEditNumber(String number) throws Exception {
		String dataId = this.editData.getId().toString();
		StandardTaskGuideInfo info = StandardTaskGuideFactory.getRemoteInstance().getStandardTaskGuideInfo(new ObjectStringPK(dataId));
		if (info.getLevel() == 1) {// 级次为1的情况
			// 查询所有级次为1的所有number，排除自己number
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("level", new Integer(1)));
			filter.getFilterItems().add(new FilterItemInfo("id", dataId, CompareType.NOTEQUALS));
			view.setFilter(filter);
			selectNumber(number, view);
			this.editData.setNumber(number);
		} else {
			// 级次不为1的情况下，排除自己的number
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("parent.id", info.getParent().getId().toString()));
			filter.getFilterItems().add(new FilterItemInfo("id", dataId, CompareType.NOTEQUALS));
			view.setFilter(filter);
			selectNumber(number, view);
			this.editData.setNumber(number);
			this.editData.setParent(info.getParent());
		}
	}

	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		verifyInputNumber();
		super.actionSave_actionPerformed(e);
		storeOrDeleteImage();
		/** 如果执行了保存,重置计数 **/
		imageCheckTimes = 0;
	}
	
	// 主要是解决关闭的时候判断数据是否发生改变 modify by duhongming
	public boolean destroyWindow() {
		// 开始有图片,点击了保存,最有图片,那么先删除以前的,然后上传现在的,如果一起没有直接上传现在的图
		if (editData == null) {
			return super.destroyWindow();
		}
		String selectedItemData = (String) txtName.getSelectedItemData();
		Object selectItem = taskTypeSelect.getSelectedItem();
		String selectItemStr = "";
		if (selectItem != null) {
			selectItemStr = ((RESchTaskTypeEnum) selectItem).getValue();
		}
		String numbertxt = txtNumber.getText();
		String desctxt = txtDescription.getText();
		String durationtxt = txtStandardDuration.getText();
		if (numbertxt.equals("")) {
			numbertxt = new String();
		}
		if (desctxt.equals("")) {
			desctxt = new String();
		}
		if (durationtxt.equals("")) {
			durationtxt = new Integer(0).toString();
		}

		String[] newObj = new String[] { selectedItemData, numbertxt, selectItemStr, desctxt, durationtxt,
				new Integer(entryA.getRowCount() + entryB.getRowCount()).toString() };
		if (editData == null) {
			for (int i = 0; i < newObj.length; i++) {
				if (newObj[i] != null)
					return super.destroyWindow();
			}
		}
		StandardTaskGuideEntryCollection entrys = editData.getEntrys();
		int entrySize = 0;
		if (entrys != null) {
			entrySize = entrys.size();
		}
		RESchTaskTypeEnum taskType = editData.getTaskType();
		String taskTypeStr = new String();
		if (taskType != null) {
			taskTypeStr = taskType.getValue();
		}
		BigDecimal standardDuration = editData.getStandardDuration();
		int duration = 0;
		if (standardDuration != null) {
			duration = standardDuration.intValue();
		}
		String description = editData.getDescription();
		if (description == null) {
			description = new String();
		}
		String name = editData.getName();
		if (name == null) {
			name = new String();
		}
		String[] oldObj = new String[] { name, new String(editData.getNumber() == null ? "" : editData.getNumber()), taskTypeStr,
				description,
				new Integer(duration).toString(),
				new Integer(entrySize).toString() };
		/** 对图片的特殊处理,可能还需要加一个是否真实做了图片的变化 **/
		if (imageCheckTimes > 0) {
			return super.destroyWindow();
		}
		for (int i = 0; i < newObj.length; i++) {
			if (!oldObj[i].equals(newObj[i])) {
				return super.destroyWindow();
			}
		}
		/** 关闭时List界面执行数据刷新 **/
		StandardTaskGuideNewListUI listUI = (StandardTaskGuideNewListUI) getUIContext().get("Owner");
		listUI.execQuery();
		return super.destroyWindow();
	}

	class DeleteImageTread implements Runnable {
		private String id, attid;

		public DeleteImageTread(String bizID, String imageAttID) {
			this.id = bizID;
			this.attid = imageAttID;
		}
		public void run() {
			deleteImage(id, attid);
		}
	}

	/**
	 * 由于在执行删除图片后立即执行图片的上传会出现上传失败的情况,所以这里特殊的多线程<br>
	 * 
	 * 版权：金蝶国际软件集团有限公司版权所有
	 * 
	 * @author 杜红明
	 * @version EAS7.0
	 * @createDate 2011-10-19
	 * @see
	 */
	class UploadImageTread implements Runnable {
		private String id;

		public UploadImageTread(String imageAttID) {
			this.id = imageAttID;
		}

		public void run() {
			uploadImage(id, kDPanel1);
		}
	}

	protected boolean isModifySave() {
		return true;
	}
}