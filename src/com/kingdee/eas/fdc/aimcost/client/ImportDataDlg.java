/*
 * 创建日期 2004-11-19
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
package com.kingdee.eas.fdc.aimcost.client;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.swing.KDButton;
import com.kingdee.bos.ctrl.swing.KDButtonGroup;
import com.kingdee.bos.ctrl.swing.KDCheckBox;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDDialog;
import com.kingdee.bos.ctrl.swing.KDFileChooser;
import com.kingdee.bos.ctrl.swing.KDLabel;
import com.kingdee.bos.ctrl.swing.KDLabelContainer;
import com.kingdee.bos.ctrl.swing.KDPanel;
import com.kingdee.bos.ctrl.swing.KDProgressBar;
import com.kingdee.bos.ctrl.swing.KDSeparator;
import com.kingdee.bos.ctrl.swing.KDStatusBar;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.aimcost.MeasureCostFactory;
import com.kingdee.eas.fdc.aimcost.MeasureCostInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.framework.client.ListUI;
import com.kingdee.eas.tools.datatask.DatataskConstants;
import com.kingdee.eas.tools.datatask.DatataskMode;
import com.kingdee.eas.tools.datatask.DatataskParameter;
import com.kingdee.eas.tools.datatask.ErrorHandleModeEnum;
import com.kingdee.eas.tools.datatask.IDatataskRunServer;
import com.kingdee.eas.tools.datatask.client.TemplateExport;
import com.kingdee.eas.tools.datatask.log.TaskLog;
import com.kingdee.eas.tools.datatask.runtime.AbstractExternalDataReader;
import com.kingdee.eas.tools.datatask.runtime.ExternalCSVDataReader;
import com.kingdee.eas.tools.datatask.runtime.ExternalExcelDataReader;
import com.kingdee.eas.tools.datatask.runtime.ExternalStructInfo;
import com.kingdee.eas.tools.datatask.runtime.ExternalTextDataReader;
import com.kingdee.eas.tools.datatask.runtime.FileFormatNotStandardException;
import com.kingdee.eas.tools.datatask.task.util.FileType;
import com.kingdee.eas.tools.datatask.util.DatataskStringUtils;
import com.kingdee.eas.tools.datatask.util.GlobalFunction;
import com.kingdee.eas.tools.datatask.util.GlobalInfo;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.ExceptionHandler;
import com.kingdee.eas.util.client.MsgBox;

/**
 * @author caterpillar
 * 
 * 更改所生成类型注释的模板为 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
public class ImportDataDlg extends KDDialog implements ActionListener, ComponentListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// Function data
	private static final Logger logger = Logger.getLogger(ImportDataDlg.class);

	private ArrayList params = null;

	private ArrayList logs = null;

	/**
	 * 是否取消
	 */
	private boolean isDone = true;

	private int mode = DatataskMode.ImpMode | DatataskMode.INSERT;

	private static long MaxFileSize = DatataskConstants.MAXFILESIZE;

	// UI data
	public boolean isOK = false;

	private KDPanel pnlInfos = new KDPanel();

	private KDPanel pnlBtns = new KDPanel();

	private static KDStatusBar statusBar = null;

	private static KDProgressBar progressBar = new KDProgressBar();

	private KDComboBox cbxMoreExcels = new KDComboBox();

	private KDLabel lblBizs = new KDLabel();

	private KDLabel lblFile = new KDLabel();

	private KDTextField txtFile = new KDTextField();

	private KDLabelContainer lblcFile = new KDLabelContainer();

	private KDLabelContainer lblcBizs = new KDLabelContainer();

	private KDWorkButton btnOpen = new KDWorkButton("", GlobalInfo.getIcon("tbtn_open"));

	private KDSeparator separator = new KDSeparator();

	private KDButton btnExportTemplate = new KDButton();
	
	private KDButton btnExportTemplate2 = new KDButton();

	private KDButton btnImportTemplate = new KDButton();

	private KDButton btnOk = new KDButton();

	private KDButton btnCancel = new KDButton();

	// 新增页面元素，可以隐藏
	private KDPanel pnlCanHide = new KDPanel();

	// 导入方式面板
	private KDPanel ImportModePanel = new KDPanel();

	private KDLabelContainer lcSheet = new KDLabelContainer();

	// private KDLabel lblSheet = new KDLabel("页签");

	private KDLabel lblSheet = new KDLabel(EASResource.getString(DatataskStringUtils.DATATASKRESOURCE, DatataskStringUtils.SHEET));

	private KDComboBox comboSheets = new KDComboBox();

	private KDWorkButton btnRefreshSheet = new KDWorkButton("",EASResource.getIcon("imgTbtn_refresh"));

	private KDButtonGroup bgRadio = new KDButtonGroup();

	private KDCheckBox boxIsOverRide = new KDCheckBox();//是否覆盖

	// Layout data
	private static final int nWidth = 450;

	private static final int nHeight = 345;

	private static final int nMinOffset = 3;

	private static final int nMaxOffset = 10;

	private static final int nDlgBorderHeight = 27;

	private static final int nDlgBorderWidth = 8;

	private boolean isSltSheet = false;

	private String uuid = null;

	private IDatataskRunServer iDatatask = null;

//	private DlgEASDatataskLog dlgLog = null;

	private ExternalStructInfo structInfo = null;

	//错误处理方式

	private KDPanel pnlErrorHandleMode = new KDPanel();
	
	private KDLabelContainer labelErrorHandleMode =  new KDLabelContainer(EASResource.getString(DatataskStringUtils.DATATASKRESOURCE,"dataErrorHandle"));
	
	private KDComboBox comboErrorHandleMode = new KDComboBox();//错误处理方式
	
	private KDLabel LabelWizard =  new KDLabel(EASResource.getIcon("imgTransfers_pic260"));
	
	private KDLabelContainer LabelProcess =  new KDLabelContainer(EASResource.getString(DatataskStringUtils.DATATASKRESOURCE,"importing"));

	public ImportDataDlg(Frame parent, ArrayList params, int mode)
	{
		this(parent, params, mode, false);
	}

	public ImportDataDlg(Dialog parent, ArrayList params, int mode)
	{
		this(parent, params, mode, false);
	}

	public ImportDataDlg(Dialog parent, ArrayList params, int mode, boolean isSltSheet)
	{
		super(parent);
		init(params, mode, isSltSheet);	
	}
	public ImportDataDlg(Frame parent, ArrayList params, int mode, boolean isSltSheet)
	{
		super(parent);
		init(params, mode, isSltSheet);

	}
	private void init(ArrayList params, int mode, boolean isSltSheet)
	{
		this.params = params;
		this.mode = mode;
		if (this.mode == DatataskMode.ImpMode)
		{
			this.mode = DatataskMode.ImpMode_INSERT;
		}
		this.isSltSheet = isSltSheet;

		try
		{
			jbInit();
		} catch (Exception e)
		{
			logger.debug(e.getMessage());
		}
		
	}
	
	public void setHideOverBox(boolean isHide)
	{
		this.boxIsOverRide.setVisible(!isHide);
	}

	private void initGUIControl()
	{
		lblFile.setText(EASResource.getString(DatataskStringUtils.DATATASKRESOURCE, DatataskStringUtils.FILE));
		txtFile.setText("");

		lblBizs.setText(EASResource.getString(DatataskStringUtils.DATATASKRESOURCE, DatataskStringUtils.BIZMODULENAME));

		lblcFile.setBoundEditor(txtFile);
		lblcFile.setBoundLabel(lblFile);
		lblcFile.setBoundLabelUnderline(true);
		lblcFile.setBoundLabelLength(80);

		initSltSheetsComboBox();
		initErrorHandleBox();

		lcSheet.add(comboSheets);
		lcSheet.setBoundEditor(comboSheets);
		lcSheet.setBoundLabel(lblSheet);
		lcSheet.setBoundLabelUnderline(true);
		lcSheet.setBoundLabelLength(80);

		labelErrorHandleMode.add(comboErrorHandleMode);
		labelErrorHandleMode.setBoundEditor(comboErrorHandleMode);
		labelErrorHandleMode.setBoundLabelUnderline(true);
		labelErrorHandleMode.setBoundLabelLength(80);

		LabelProcess.setBoundEditor(progressBar);
		lcSheet.setBoundLabelUnderline(true);
		lcSheet.setBoundLabelLength(80);

		btnExportTemplate.setText(EASResource.getString(DatataskStringUtils.DATATASKRESOURCE, "exporttemplate"));
		btnExportTemplate2.setText(EASResource.getString(DatataskStringUtils.DATATASKRESOURCE, "exporttemplate"));
		btnImportTemplate.setText(EASResource.getString(DatataskStringUtils.DATATASKRESOURCE, "importtemplate"));
		btnOk.setText(EASResource.getString(DatataskStringUtils.DATATASKRESOURCE, DatataskStringUtils.OK));
		btnCancel.setText(EASResource.getString(DatataskStringUtils.DATATASKRESOURCE, DatataskStringUtils.CANCEL));

		btnExportTemplate.setMnemonic(KeyEvent.VK_T);
		btnExportTemplate2.setMnemonic(KeyEvent.VK_T);
		
		btnOpen.setToolTipText(EASResource.getString(DatataskStringUtils.DATATASKRESOURCE, "selectfile"));
//		btnOpen.setFocusable(false);
		btnRefreshSheet.setToolTipText(EASResource.getString(DatataskStringUtils.DATATASKRESOURCE, "refreshsheet"));
		this.txtFile.setEditable(false);
		boxIsOverRide.setText(EASResource.getString(DatataskStringUtils.DATATASKRESOURCE, "overridesamedata"));
		this.setStatusBar(null);
		progressBar.setMinimum(0);
		progressBar.setMaximum(100);
		progressBar.setStringPainted(true);
		changeByMode();
	}

	private void initUIContentLayout()
	{
		Container contentPane = this.getContentPane();
		contentPane.setLayout(new GridBagLayout());
		KDPanel panelProcess = new KDPanel();
		panelProcess.add(LabelProcess);
//		LabelWizard.setBorder(LineBorder.createGrayLineBorder());
		// Panel infos
		pnlInfos.setLayout(new GridBagLayout());

		pnlInfos.add(lblcFile, new GridBagConstraints(0, 0, 3, 1, 1, 0, GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(0, 0,
				0, 0), 0, 0));
		pnlInfos.add(btnOpen, new GridBagConstraints(3, 0, 1, 1, 0, 0, GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(0, 1, 0,
				0), 0, 0));
		pnlErrorHandleMode.setLayout(new GridBagLayout());
		pnlErrorHandleMode.add(labelErrorHandleMode, new GridBagConstraints(0, 0, 1, 1, 1, 0, GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0,
				0), 0, 0));

		pnlBtns.setLayout(new GridBagLayout());

		if (this.mode == DatataskMode.ExpMode)
		{
			LabelProcess.setBoundLabelText(EASResource.getString(DatataskStringUtils.DATATASKRESOURCE,"exporting"));
			btnOk.setText(EASResource.getString(DatataskStringUtils.DATATASKRESOURCE, "export"));
			pnlBtns.add(separator, new GridBagConstraints(0, 0, 5, 1, 0, 0, GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(10, 0,
					0, 0), 0, 0));
			pnlBtns.add(btnExportTemplate2, new GridBagConstraints(0, 1, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
					new Insets(8, 10, 8, 0),0, 0));
			pnlBtns.add(new KDPanel(), new GridBagConstraints(1, 1, 2, 1, 1, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(
			8, 0, 8, 0), 0, 0));
			pnlBtns.add(btnOk, new GridBagConstraints(3, 1, 1, 1,0, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(8, 0, 8,
					0), 20, 0));
			pnlBtns.add(btnCancel, new GridBagConstraints(4, 1, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(8,
					5, 8, 10), 20, 0));

		} else if (DatataskMode.isImpMode(this.mode))
		{
			btnOk.setText(EASResource.getString(DatataskStringUtils.DATATASKRESOURCE, "import"));
			LabelProcess.setBoundLabelText(EASResource.getString(DatataskStringUtils.DATATASKRESOURCE,"importing"));
			pnlBtns.add(separator, new GridBagConstraints(0, 0, 5, 1, 1, 0, GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(10, 0,
					0, 0), 0, 0));
			pnlBtns.add(btnExportTemplate2, new GridBagConstraints(0, 1, 1, 1, 1, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
					new Insets(8, 10, 8, 0),-10, 0));
			pnlBtns.add(new KDPanel(), new GridBagConstraints(1, 1, 2, 1, 4, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(
			8, 0, 8, 0), 0, 0));
			pnlBtns.add(btnOk, new GridBagConstraints(3, 1, 1, 1,0, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(8, 0, 8,
					0), 20, 0));
			pnlBtns.add(btnCancel, new GridBagConstraints(4, 1, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(8,
					5, 8, 10), 20, 0));
		} else if (DatataskMode.ImpTemplateMode == this.mode)
		{
			LabelProcess.setBoundLabelText(EASResource.getString(DatataskStringUtils.DATATASKRESOURCE,"importing"));
			pnlBtns.add(separator, new GridBagConstraints(0, 0, 5, 1, 0, 0, GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(5, 0,
					0, 0), 0, 0));
			pnlBtns.add(new KDPanel(), new GridBagConstraints(0, 1, 2, 1, 1, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(
					8, 0, 8, 0), 0, 0));
			pnlBtns.add(btnImportTemplate, new GridBagConstraints(3, 1, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
					new Insets(8, 0, 8, 0), 10, 0));
			pnlBtns.add(btnCancel, new GridBagConstraints(4, 1, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(8,
					5, 8, 10), 0, 0));
		} else if (DatataskMode.ExpTemplateMode == this.mode)
		{
			LabelProcess.setBoundLabelText(EASResource.getString(DatataskStringUtils.DATATASKRESOURCE,"exporting"));
			pnlBtns.add(separator, new GridBagConstraints(0, 0, 5, 1, 0, 0, GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(5, 0,
					0, 0), 0, 0));
			pnlBtns.add(new KDPanel(), new GridBagConstraints(0, 1, 2, 1, 1, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(
					8, 0, 8, 0), 0, 0));
			pnlBtns.add(btnExportTemplate2, new GridBagConstraints(3, 1, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
					new Insets(8, 0, 8, 0), 0, 0));
			pnlBtns.add(btnCancel, new GridBagConstraints(4, 1, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(8,
					5, 8, 10), 0, 0));
		}

		if (isSltSheet)
		{
			ImportModePanel.setLayout(new BorderLayout());
			ImportModePanel.add(boxIsOverRide, BorderLayout.WEST);
			pnlCanHide.setLayout(new GridBagLayout());
			pnlCanHide.add(lcSheet, new GridBagConstraints(0, 0, 3, 1, 1, 0, GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(0,
					0, 0, 0), 0, 0));
			pnlCanHide.add(btnRefreshSheet, new GridBagConstraints(3, 0, 1, 1, 0, 0, GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
					new Insets(0, 1, 0, 0), 0, 0));

			contentPane.add(LabelWizard, new GridBagConstraints(0, 0, 1, 5, 0, 1, GridBagConstraints.NORTHEAST, GridBagConstraints.HORIZONTAL, new Insets(
					10, 10, 0, 0), 1, 1));
			
			contentPane.add(pnlInfos, new GridBagConstraints(1, 0, 1, 1, 1, 0, GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(
					10, 10, 0, 10), 0, 0));
			
			contentPane.add(pnlCanHide, new GridBagConstraints(1, 1, 1, 1, 1, 0, GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
					new Insets(10, 10, 0, 10), 0, 0));

			contentPane.add(pnlErrorHandleMode, new GridBagConstraints(1, 2, 1, 1, 1, 0, GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL, new Insets(
					10, 10, 0, 10), 0, 0));
			
			contentPane.add(ImportModePanel, new GridBagConstraints(1, 3, 1, 1, 1, 0, GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(
					10, 10, 0, 10), 0, 0));			
			
			contentPane.add(LabelProcess, new GridBagConstraints(1, 4, 1, 1, 1, 1, GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL,
					new Insets(0, 10, 10, 10), 0, 0));

			contentPane.add(pnlBtns, new GridBagConstraints(0, 5, 5, 1, 0.5, 0.5, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
					new Insets(0, 0, 0, 0), 0, 0));

		} else
		{			
			contentPane.add(LabelWizard, new GridBagConstraints(0, 0, 1, 4, 0, 1, GridBagConstraints.NORTHEAST, GridBagConstraints.HORIZONTAL, new Insets(
					10, 10, 0, 0), 1, 1));
			
			contentPane.add(pnlInfos, new GridBagConstraints(1, 0, 1, 1, 1, 0, GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(
					10, 10, 0, 10), 0, 0));
			
			contentPane.add(pnlErrorHandleMode, new GridBagConstraints(1, 1, 1, 1, 1, 0, GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(
					10, 10, 0, 10), 0, 0));
			
			contentPane.add(ImportModePanel, new GridBagConstraints(1, 2, 1, 1, 1, 0, GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(
					10, 10, 0, 10), 0, 0));	
			
			contentPane.add(LabelProcess, new GridBagConstraints(1, 3, 1, 1, 1, 1, GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL, new Insets(
					0, 10, 10, 10), 0, 0));

			contentPane.add(pnlBtns, new GridBagConstraints(0, 4, 4, 1, 0.5, 0.5, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
					new Insets(0, 0, 0, 0), 0, 0));
		}
		LabelProcess.setVisible(false);this.repaint();
	}

	private void initListener()
	{
		this.addComponentListener(this);
		this.btnOpen.addActionListener(this);
		this.btnExportTemplate.addActionListener(this);
		this.btnImportTemplate.addActionListener(this);
		this.btnOk.addActionListener(this);
		this.btnCancel.addActionListener(this);
		this.btnRefreshSheet.addActionListener(this);
		btnExportTemplate2.addActionListener(this);
	}

	private void jbInit()
	{
		this.setResizable(false);
		Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		this.setBounds((screenSize.width - this.nWidth) / 2, (screenSize.height - this.nHeight) / 2, nWidth, nHeight);
		this.getRootPane().setDefaultButton(btnOk);

		initGUIControl();
		initUIContentLayout();
		initListener();
		
//		setHideOverBox(true);
		boxIsOverRide.setSelected(true);
		boxIsOverRide.setEnabled(false);
		btnExportTemplate2.setVisible(true);
//		this.txtFile.setText("w:/test/11.xls");
//		FDCGlobalFunction.setLastSelectPath("w:/test");
	}

	private void initSltSheetsComboBox()
	{
		comboSheets.removeAllItems();

		for (int i = 0; i < this.params.size(); i++)
		{
			DatataskParameter param = (DatataskParameter) this.params.get(i);

			// Notices that more excel sheets needed
			if ((param.alias != null) && (param.alias.trim().compareTo("") != 0))
			{
				String sheetNames[]  = param.alias.split(":");

				// Adds all excel sheets to combobox
				for (int j = 0; j < sheetNames.length; j++)
				{
					comboSheets.addItem(sheetNames[j]);
				}
			}

		}
	}
	private void initErrorHandleBox()
	{
		comboErrorHandleMode.removeAllItems();
		comboErrorHandleMode.addItem(ErrorHandleModeEnum.ReturnRightnow);
		comboErrorHandleMode.addItem(ErrorHandleModeEnum.ReturnWhenTen);
		comboErrorHandleMode.addItem(ErrorHandleModeEnum.ReturnWhenFifty);
		comboErrorHandleMode.addItem(ErrorHandleModeEnum.ReturnWhenhundred);	
		comboErrorHandleMode.addItem(ErrorHandleModeEnum.ReturnWhenFinish);	
//		comboErrorHandleMode.setSelectedIndex(0);
		comboErrorHandleMode.setSelectedIndex(0);
		comboErrorHandleMode.setEnabled(false);
	}

	private void changeByMode()
	{
		DatataskParameter tmp = (DatataskParameter) params.get(0);
//		String title = tmp.alias;
		Object title=tmp.getContextParam().get("title");
		
		if (this.mode == DatataskMode.ExpMode || this.mode == DatataskMode.ExpTemplateMode)
		{
			// this.setTitle(alias+" 引出");

			this.setTitle(title + " " + EASResource.getString(DatataskStringUtils.DATATASKRESOURCE, DatataskStringUtils.EXPORT));

//			this.btnExportTemplate.setVisible(true);

		} else if (DatataskMode.isImpMode(this.mode))
		{
			// this.setTitle(alias+" 引入");

			this.setTitle(title + " " + EASResource.getString(DatataskStringUtils.DATATASKRESOURCE, DatataskStringUtils.IMPORT));
//			this.btnExportTemplate.setVisible(true);
		}

	}


	private class ExceptionRunnable implements Runnable
	{
		private String exMsg;
		public void setExMsg(String msg)
		{
			exMsg = msg;
		}
		public void run()
		{
			MsgBox.showInfo(ImportDataDlg.this, exMsg);
			toDefaultCursor();
			enableButtons();
		}
	}

	private class EndRunnable implements Runnable
	{

		private ArrayList sheetNamesColl;
		
		public void setSheetNames(ArrayList sheetNames)
		{
			sheetNamesColl = sheetNames;
		}
		public void run()
		{

			comboSheets.removeAllItems();
			if (sheetNamesColl != null)
			{
				int size = sheetNamesColl.size();
				for (int i = 0; i < size; i++)
				{
					comboSheets.addItem(sheetNamesColl.get(i));
				}
			}
			if (params.get(0) instanceof DatataskParameter)
			{
				DatataskParameter dp = (DatataskParameter) params.get(0);
				if (dp != null)
				{
					comboSheets.setSelectedItem(dp.alias);
				}
			}
			toDefaultCursor();
			enableButtons();
			boxIsOverRide.setEnabled(false);
		}

	}

	private void refreshSheet()
	{
		this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		disableButtons();

		Thread refreshThread = new Thread()
		{

			public void run()
			{
				ExternalExcelDataReader reader = new ExternalExcelDataReader();
				ArrayList sheetNames = null;
				try
				{
					sheetNames = reader.getSheetNameCollection(getFileName());
				} catch (Exception err)
				{
					ExceptionRunnable exRun = new ExceptionRunnable();
					exRun.setExMsg(err.getMessage());
					SwingUtilities.invokeLater(exRun);
					logger.error("open excel file error:" + err);
					return;
				}
				EndRunnable endRun = new EndRunnable();
				endRun.setSheetNames(sheetNames);
				SwingUtilities.invokeLater(endRun);
			}

		};

		refreshThread.start();
	}

	private void toDefaultCursor()
	{
		this.setCursor(Cursor.getDefaultCursor());
	}

	private void onBtnExportTemplateClicked()
	{
		DatataskParameter param = (DatataskParameter) this.params.get(0);
		param.getContextParam().put("exportTemplateOnly", "true");
		this.onBtnOKClicked();
	}

	//TODO 代码
	MeasureImporter importer=null;
	/**
	 * 由于要判断需要导入的和现有是否重复，修改了函数的接口，添加了一个参数
	 * List verNumber: 当前组织下所有的版本号
	 */
	private void invokeWork() throws Exception
	{
		DatataskParameter tmp = (DatataskParameter) params.get(0);
		String orgId=(String)tmp.getContextParam().get("orgUnitId");
		ListUI listui=(ListUI)tmp.getContextParam().get("ListUI");
		List verNumber = (List)tmp.getContextParam().get("VerNumber");
		ActionEvent evt=(ActionEvent)tmp.getContextParam().get("ActionEvent");
		importer=new MeasureImporter(new File(getFileName()),orgId);
		MeasureCostInfo measure = importer.transToMeasure(verNumber);
        UIContext uiContext = new UIContext(this);
        uiContext.put("MeasureEditData",measure);
    	String editUIModal = null;
    	String editUIName = null;
        if(listui instanceof MeasureCostListUI){
        	MeasureCostListUI list=(MeasureCostListUI)listui;
        	editUIModal = list.getEditUIModal();
        	editUIName = list.getEditUIName();
        	list.prepareUIContext(uiContext, evt);
        }else{
        	AimMeasureCostListUI list=(AimMeasureCostListUI)listui;
        	editUIModal = list.getEditUIModal();
        	editUIName = list.getEditUIName();
        	list.prepareUIContext(uiContext, evt);
        }
		IUIWindow  uiWindow = UIFactory.createUIFactory(editUIModal).create(editUIName, uiContext, null,
                "ADDNEW1");
		uiWindow.show();		
	}

	/**
	 * 得到导入的过程提示
	 */
	private String getProcessString(){
		if(importer!=null){
			return importer.getProcessString();
		}
		return "";
	}
	private void enableButtons()
	{
		this.btnOk.setEnabled(true);
		this.btnCancel.setEnabled(true);
		this.btnOpen.setEnabled(true);
		this.btnExportTemplate2.setEnabled(true);
		this.txtFile.setEnabled(true);
		this.cbxMoreExcels.setEnabled(true);
		this.btnRefreshSheet.setEnabled(true);
		this.btnRefreshSheet.setEnabled(true);
		this.comboSheets.setEnabled(true);
		this.boxIsOverRide.setEnabled(true);

		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

	private void disableButtons()
	{
		this.btnOk.setEnabled(false);
		this.btnCancel.setEnabled(false);
		this.btnOpen.setEnabled(false);
		this.btnExportTemplate2.setEnabled(false);
		this.txtFile.setEnabled(false);
		this.cbxMoreExcels.setEnabled(false);
		this.btnRefreshSheet.setEnabled(false);
		this.comboSheets.setEnabled(false);
		this.boxIsOverRide.setEnabled(false);

		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
	}

	private void onFinishedRunning()
	{
		progressBar.setValue(100);
		LabelProcess.setVisible(false);
		enableButtons();
		if(isOK){
			FDCMsgBox.showInfo(this, "导入成功！");
			btnCancel.doClick();
		}
	}

	private void onPreparedToRun()
	{
//		if (isSltSheet)
//		{
//			this.setSize(nWidth, nHeight + 22);
//		} else
//		{
//			this.setSize(nWidth, nHeight - 50 + 22);
//		}
//		this.setStatusBar(this.getStatusBar());
//		this.getStatusBar().validate();
		// this.getContentPane().repaint(this.getBounds().x,this.getBounds().y+nHeight,nWidth,nHeight);
		progressBar.setValue(0);
		progressBar.setVisible(true);
		LabelProcess.setVisible(true);
		LabelProcess.invalidate();
		increaseValue();
		disableButtons();
	}
	private boolean noStopRequested = true;
	private Thread progressThread = null;
	
	private void increaseValue()
	{
		Runnable increaseRun = new Runnable()
		{
			public void run()
			{
				while(noStopRequested)
				{
					try
					{
						Thread.sleep(50);
						progressBar.setValue(progressBar.getValue()+5);
						if(progressBar.getValue()>=progressBar.getMaximum())
						{
							progressBar.setValue(progressBar.getMinimum());
						}
						progressBar.setString(getProcessString());
						repaint();
					}catch(InterruptedException e)
					{
						Thread.currentThread().interrupt();						
					}
				}
				
			}
			
		};
		progressThread = new Thread(increaseRun);
		progressThread.start();
	}
	
	public void stopProgressRequest()
	{
		noStopRequested = false;
		if(!progressThread.isInterrupted()){
			progressThread.interrupt();
		}
	}

	private boolean isValidFile(String filePath)
	{

		FileInputStream f = null;
		try
		{
			f = new FileInputStream(filePath);
			if (f != null && DatataskMode.isImpMode(this.mode) && f.available() > MaxFileSize)
			{
				MsgBox.showError(this, EASResource.getString(DatataskStringUtils.DATATASKRESOURCE, "importfiletoolarge"));
				return false;
			}
		} catch (FileNotFoundException e)
		{
			logger.error(e);
			MsgBox.showError(this, "文件不存在");
			return false;
		} catch (IOException e)
		{
			logger.error(e);
			return false;
		}finally{
			if(f != null)
			{
				try {
					f.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return true;
	}

	public ExternalStructInfo getExternalStructInfo()
	{
		return structInfo;
	}

	private void onBtnOKClicked()
	{
		noStopRequested = true;
		// 是否选择文件
		String filePath = this.txtFile.getText();
		if (filePath == null || filePath.equals(""))
		{
			// MsgBox.showError(this, "请选择导入文档。");

			MsgBox.showError(this, EASResource.getString(DatataskStringUtils.DATATASKRESOURCE, DatataskStringUtils.IMPORTDOCUMENT));
			return;
		}

		if (DatataskMode.isImpMode(this.mode) && !isValidFile(filePath))
		{
			return;
		}

		if (!GlobalFunction.isSupportFileType(filePath))
		{
			// 支持excel,text,csv格式文件引入。请选择支持的格式文件。
			MsgBox.showError(this, EASResource.getString(DatataskStringUtils.DATATASKRESOURCE, "supportfiletype"));
			return;

		}

		uuid = GlobalFunction.getUuID();

		onPreparedToRun();
		
		//enable cancel button by sxhong
//		this.btnCancel.setEnabled(true)
		Thread taskThread = new Thread()
		{
			public void run()
			{
				try
				{
					invokeWork();
					UpdateUIRunnable update = new UpdateUIRunnable();
					SwingUtilities.invokeLater(update);
					ImportDataDlg.this.isOK = true;
					
				} catch (Exception e)
				{
					ImportDataDlg.this.isOK = false;
					UpdateUIRunnable update = new UpdateUIRunnable();
					update.setException(e);
					SwingUtilities.invokeLater(update);
					return;
				} finally
				{
					stopProgressRequest();
				}

			}
		};
		taskThread.start();
		isDone=false;
		// monitor = new TaskProcessMonitor();
		// monitor.startMonitor(new String[] { uuid }, this, this);
	}


	private class UpdateUIRunnable implements Runnable
	{
		private TaskLog _log = null;

		private Exception _e = null;

		public void setTaskLog(TaskLog log)
		{
			_log = log;
		}

		public void setException(Exception e)
		{
			_e = e;
		}

		public void run()
		{
			if(isOK){
				onFinishedRunning();
				return;
			}
			if (_log != null)
			{
				onFinishedRunning();
				return;
			}
			if (_e != null && _e instanceof FileNotFoundException)
			{
				onFinishedRunning();
				MsgBox.showInfo(ImportDataDlg.this, EASResource.getString(DatataskStringUtils.DATATASKRESOURCE, "openedfile"));
				return;
			}

			if (_e != null)
			{
				onFinishedRunning();
				ExceptionHandler.handle(ImportDataDlg.this, _e);
				// MsgBox.showInfo(DlgEASDatatask.this, _e.getMessage());
				return;
			}
		}
	}

	private void onBtnCancelClicked()
	{
		this.setVisible(false);
		this.dispose();
		if(params!=null){
			params.clear();
		}
	}

	private void onBtnOpenClicked()
	{

		String sFileName = null;
		try
		{
			if (DatataskMode.isImpMode(this.mode))
			{
				sFileName = GlobalFunction.excelFileChoose(this);
			} else if (this.mode == DatataskMode.ExpMode)
			{
				sFileName = GlobalFunction.excelFileSaver(this);
			} else if (this.mode == DatataskMode.ImpTemplateMode)
			{
				sFileName = GlobalFunction.excelFileChoose(this);
			} else if (this.mode == DatataskMode.ExpTemplateMode)
			{
				sFileName = GlobalFunction.excelFileSaver(this);
			}
		} catch (Exception err)
		{
			MsgBox.showError(err.getMessage());
			return;
		}
		if (sFileName != null && !sFileName.equals(""))
		{
			this.txtFile.setText(sFileName);
			String fileExtension = GlobalFunction.getFileExtension(sFileName);

			// 隐藏多页签
			HideOrVisibleSheet(fileExtension);

//			if (!isValidFile(sFileName))
//			{
//				return;
//			}
			if (this.isSltSheet && FileType.ExcelType.equals(fileExtension))
			{
				refreshSheet();
			}
		}
//
		this.btnOk.requestFocusInWindow();
		boxIsOverRide.setEnabled(false);

	}

	private void HideOrVisibleSheet(String fileType)
	{
		if (FileType.ExcelType.equals(fileType))
		{
			this.lcSheet.setVisible(true);
			this.btnRefreshSheet.setVisible(true);
		} else
		{
			this.lcSheet.setVisible(false);
			this.btnRefreshSheet.setVisible(false);
		}
	}

	public void setDatataskMode(int mode)
	{
		this.mode = mode;
	}

	public String getFileName()
	{
		return txtFile.getText();
	}

	/**
	 * actionPerformed
	 * 
	 * @param e
	 *            ActionEvent
	 */
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource().equals(this.btnExportTemplate))
		{
			this.onBtnExportTemplateClicked();
		} else if (e.getSource().equals(this.btnOk))
		{
			this.onBtnOKClicked();
		} else if (e.getSource().equals(this.btnCancel))
		{
			this.onBtnCancelClicked();
		} else if (e.getSource().equals(this.btnOpen))
		{
			this.onBtnOpenClicked();
		} else if (e.getSource().equals(this.btnExportTemplate2))
		{
			try {
				exportTemplate();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		} else if (e.getSource().equals(this.btnRefreshSheet))
		{
			String filePath = this.txtFile.getText();
			if (filePath == null || filePath.equals(""))
			{
				// MsgBox.showError(this, "请选择导入文档。");

				MsgBox.showError(this, EASResource.getString(DatataskStringUtils.DATATASKRESOURCE, DatataskStringUtils.IMPORTDOCUMENT));
				return;
			}
			this.refreshSheet();
		}

		this.btnOk.requestFocusInWindow();
	}
	
	/**
	 * 引出模板
	 * @throws Exception 
	 */
	private void exportTemplate() throws Exception
	{
		String fileName=GlobalFunction.excelFileSaver(this);
		if(FDCHelper.isEmpty(fileName)){
			return;
		}
		//导出模板
//		InputStream is=Class.forName("com.kingdee.eas.fdc.aimcost.client.MeasureCostListUI").getResourceAsStream("measureImporterTemplate.xls");
//		try{
//			FileOutputStream os=new FileOutputStream(fileName);
//			byte data[]=new byte[1024];
//			while(is!=null&&is.available()>0){
//				is.read(data);
//				os.write(data);
//			}
//			os.close();
//			is.close();
//			FDCMsgBox.showInfo(this,"保存成功！");
//		}catch(Exception e1){
//			e1.printStackTrace();
//			FDCMsgBox.showDetailAndOK(this,"所选择的文件无法打开,请确认文件是否存在或者被使用",e1.getMessage(),0);
//		}
		
		/**
		 * 因分包问题，从客房端无法读取模板文件,改为从服务器端读取
		 * modify by pengwei_hou 2008-10-31 18:00
		 */
		
		logger.info("hpwURL: " + "client object!");
		Object object = MeasureCostFactory.getRemoteInstance().getTemplateDataStream();
		logger.info("hpwURL: MeasureCostFactory.getRemoteInstance().toString():" + MeasureCostFactory.getRemoteInstance().toString());
		
		logger.info("hpwURL: object==" + (object == null));
		logger.info("hpwURL: " + "client app write data before!");
		if(object instanceof byte[]){
			FileOutputStream os = new FileOutputStream(fileName);
			BufferedOutputStream bw = new BufferedOutputStream(os);
			byte[] data = (byte[])object;
			logger.info("hpwURL: " + "client app write data ready!");
			try{
				bw.write(data, 0, data.length);
				logger.info("hpwURL: " + "client app write data complete!");
			} catch (Exception e){
				e.printStackTrace();
				FDCMsgBox.showDetailAndOK(this, "所选择文件无法打开,请确认文件是否存在或被使用!", e.getMessage(), 0);
			} finally{
				bw.close();
				os.close();
				logger.info("hpwURL: " + "client app close stream!");
			}
		}
		

		return;
	
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see java.awt.event.ComponentListener#componentHidden(java.awt.event.ComponentEvent)
	 */
	public void componentHidden(ComponentEvent arg0)
	{

	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see java.awt.event.ComponentListener#componentMoved(java.awt.event.ComponentEvent)
	 */
	public void componentMoved(ComponentEvent arg0)
	{

	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see java.awt.event.ComponentListener#componentResized(java.awt.event.ComponentEvent)
	 */
	public void componentResized(ComponentEvent arg0)
	{

	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see java.awt.event.ComponentListener#componentShown(java.awt.event.ComponentEvent)
	 */
	public void componentShown(ComponentEvent arg0)
	{
	}



}