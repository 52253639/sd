/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import org.apache.log4j.*;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.border.*;
import javax.swing.BorderFactory;
import javax.swing.event.*;
import javax.swing.KeyStroke;

import com.kingdee.bos.ctrl.swing.*;
import com.kingdee.bos.ctrl.kdf.table.*;
import com.kingdee.bos.ctrl.kdf.data.event.*;
import com.kingdee.bos.dao.*;
import com.kingdee.bos.dao.query.*;
import com.kingdee.bos.metadata.*;
import com.kingdee.bos.metadata.entity.*;
import com.kingdee.bos.ui.face.*;
import com.kingdee.bos.ui.util.ResourceBundleHelper;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.service.ServiceContext;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.enums.EnumUtils;
import com.kingdee.bos.ui.face.UIRuleUtil;
import com.kingdee.bos.ctrl.swing.event.*;
import com.kingdee.bos.ctrl.kdf.table.event.*;
import com.kingdee.bos.ctrl.extendcontrols.*;
import com.kingdee.bos.ctrl.kdf.util.render.*;
import com.kingdee.bos.ui.face.IItemAction;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.bos.ui.util.IUIActionPostman;
import com.kingdee.bos.appframework.client.servicebinding.ActionProxyFactory;
import com.kingdee.bos.appframework.uistatemanage.ActionStateConst;
import com.kingdee.bos.appframework.validator.ValidateHelper;
import com.kingdee.bos.appframework.uip.UINavigator;


/**
 * output class name
 */
public abstract class AbstractCluesManageEditUI extends com.kingdee.eas.framework.client.EditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractCluesManageEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer1;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer2;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer3;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAddTrack;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDescription;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPhone;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSource;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCluesStatus;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSellProject;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPropertyConsultant;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCognizePath;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtName;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane1;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtDescription;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtPhone;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboSource;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboCluesStatus;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtSellProject;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtPropertyConsultant;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCognizePath;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblTrack;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblQuestionPaper;
    protected com.kingdee.eas.fdc.sellhouse.CluesManageInfo editData = null;
    /**
     * output class constructor
     */
    public AbstractCluesManageEditUI() throws Exception
    {
        super();
        this.defaultObjectName = "editData";
        jbInit();
        
        initUIP();
    }

    /**
     * output jbInit method
     */
    private void jbInit() throws Exception
    {
        this.resHelper = new ResourceBundleHelper(AbstractCluesManageEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.kDContainer1 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kDContainer2 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kDContainer3 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.btnAddTrack = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.contName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDescription = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPhone = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSource = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCluesStatus = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSellProject = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPropertyConsultant = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCognizePath = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.kDScrollPane1 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtDescription = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.txtPhone = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.comboSource = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.comboCluesStatus = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.prmtSellProject = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtPropertyConsultant = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtCognizePath = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.tblTrack = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.tblQuestionPaper = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kDContainer1.setName("kDContainer1");
        this.kDContainer2.setName("kDContainer2");
        this.kDContainer3.setName("kDContainer3");
        this.btnAddTrack.setName("btnAddTrack");
        this.contName.setName("contName");
        this.contNumber.setName("contNumber");
        this.contDescription.setName("contDescription");
        this.contPhone.setName("contPhone");
        this.contSource.setName("contSource");
        this.contCluesStatus.setName("contCluesStatus");
        this.contSellProject.setName("contSellProject");
        this.contPropertyConsultant.setName("contPropertyConsultant");
        this.contCognizePath.setName("contCognizePath");
        this.txtName.setName("txtName");
        this.txtNumber.setName("txtNumber");
        this.kDScrollPane1.setName("kDScrollPane1");
        this.txtDescription.setName("txtDescription");
        this.txtPhone.setName("txtPhone");
        this.comboSource.setName("comboSource");
        this.comboCluesStatus.setName("comboCluesStatus");
        this.prmtSellProject.setName("prmtSellProject");
        this.prmtPropertyConsultant.setName("prmtPropertyConsultant");
        this.prmtCognizePath.setName("prmtCognizePath");
        this.tblTrack.setName("tblTrack");
        this.tblQuestionPaper.setName("tblQuestionPaper");
        // CoreUI		
        this.menuBar.setVisible(false);		
        this.btnSave.setVisible(false);		
        this.btnCopy.setVisible(false);		
        this.btnCancelCancel.setVisible(false);		
        this.btnCancel.setVisible(false);		
        this.btnFirst.setVisible(false);		
        this.btnPre.setVisible(false);		
        this.btnNext.setVisible(false);		
        this.btnLast.setVisible(false);		
        this.btnPrint.setVisible(false);		
        this.btnPrintPreview.setVisible(false);		
        this.btnAttachment.setVisible(false);
        // kDContainer1		
        this.kDContainer1.setTitle(resHelper.getString("kDContainer1.title"));
        // kDContainer2		
        this.kDContainer2.setTitle(resHelper.getString("kDContainer2.title"));
        // kDContainer3		
        this.kDContainer3.setTitle(resHelper.getString("kDContainer3.title"));
        // btnAddTrack		
        this.btnAddTrack.setText(resHelper.getString("btnAddTrack.text"));
        this.btnAddTrack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnAddTrack_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // contName		
        this.contName.setBoundLabelText(resHelper.getString("contName.boundLabelText"));		
        this.contName.setBoundLabelLength(100);		
        this.contName.setBoundLabelUnderline(true);
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(100);		
        this.contNumber.setBoundLabelUnderline(true);
        // contDescription		
        this.contDescription.setBoundLabelText(resHelper.getString("contDescription.boundLabelText"));		
        this.contDescription.setBoundLabelLength(100);		
        this.contDescription.setBoundLabelUnderline(true);
        // contPhone		
        this.contPhone.setBoundLabelText(resHelper.getString("contPhone.boundLabelText"));		
        this.contPhone.setBoundLabelLength(100);		
        this.contPhone.setBoundLabelUnderline(true);
        // contSource		
        this.contSource.setBoundLabelText(resHelper.getString("contSource.boundLabelText"));		
        this.contSource.setBoundLabelLength(100);		
        this.contSource.setBoundLabelUnderline(true);
        // contCluesStatus		
        this.contCluesStatus.setBoundLabelText(resHelper.getString("contCluesStatus.boundLabelText"));		
        this.contCluesStatus.setBoundLabelLength(100);		
        this.contCluesStatus.setBoundLabelUnderline(true);		
        this.contCluesStatus.setVisible(false);
        // contSellProject		
        this.contSellProject.setBoundLabelText(resHelper.getString("contSellProject.boundLabelText"));		
        this.contSellProject.setBoundLabelLength(100);		
        this.contSellProject.setBoundLabelUnderline(true);
        // contPropertyConsultant		
        this.contPropertyConsultant.setBoundLabelText(resHelper.getString("contPropertyConsultant.boundLabelText"));		
        this.contPropertyConsultant.setBoundLabelLength(100);		
        this.contPropertyConsultant.setBoundLabelUnderline(true);
        // contCognizePath		
        this.contCognizePath.setBoundLabelText(resHelper.getString("contCognizePath.boundLabelText"));		
        this.contCognizePath.setBoundLabelUnderline(true);		
        this.contCognizePath.setBoundLabelLength(100);		
        this.contCognizePath.setEnabled(false);		
        this.contCognizePath.setVisible(false);
        // txtName		
        this.txtName.setRequired(true);		
        this.txtName.setMaxLength(80);
        // txtNumber		
        this.txtNumber.setMaxLength(80);		
        this.txtNumber.setRequired(true);
        // kDScrollPane1
        // txtDescription		
        this.txtDescription.setMaxLength(255);
        // txtPhone		
        this.txtPhone.setMaxLength(80);		
        this.txtPhone.setRequired(true);
        // comboSource		
        this.comboSource.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.CluesSourceEnum").toArray());		
        this.comboSource.setRequired(true);
        // comboCluesStatus		
        this.comboCluesStatus.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.CluesStatusEnum").toArray());
        // prmtSellProject		
        this.prmtSellProject.setEnabled(false);
        // prmtPropertyConsultant		
        this.prmtPropertyConsultant.setRequired(true);		
        this.prmtPropertyConsultant.setDisplayFormat("$name$");		
        this.prmtPropertyConsultant.setEditFormat("$number$");		
        this.prmtPropertyConsultant.setCommitFormat("$number$");		
        this.prmtPropertyConsultant.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.F7PropertyConsultantQuery");
        // prmtCognizePath		
        this.prmtCognizePath.setDisplayFormat("$name$");		
        this.prmtCognizePath.setEditFormat("$number$");		
        this.prmtCognizePath.setCommitFormat("$number$");		
        this.prmtCognizePath.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.F7SHECusAssistantDataQuery");
        // tblTrack
		String tblTrackStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol5\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"trackDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"saleMan.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"trackContent\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"interactionType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"saleMan.id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol5\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{trackDate}</t:Cell><t:Cell>$Resource{saleMan.name}</t:Cell><t:Cell>$Resource{trackContent}</t:Cell><t:Cell>$Resource{interactionType}</t:Cell><t:Cell>$Resource{saleMan.id}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.tblTrack.setFormatXml(resHelper.translateString("tblTrack",tblTrackStrXML));
        this.tblTrack.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    tblTrack_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });

        

        // tblQuestionPaper
		String tblQuestionPaperStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol8\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol9\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol10\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"bizDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"questionPaper.topric\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"creator.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"createTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"lastUpdateUser.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"lastUpdateTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"questionPaper.id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol8\" /><t:Column t:key=\"creator.id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol9\" /><t:Column t:key=\"lastUpdateUser.id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol10\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{bizDate}</t:Cell><t:Cell>$Resource{questionPaper.topric}</t:Cell><t:Cell>$Resource{creator.name}</t:Cell><t:Cell>$Resource{createTime}</t:Cell><t:Cell>$Resource{lastUpdateUser.name}</t:Cell><t:Cell>$Resource{lastUpdateTime}</t:Cell><t:Cell>$Resource{questionPaper.id}</t:Cell><t:Cell>$Resource{creator.id}</t:Cell><t:Cell>$Resource{lastUpdateUser.id}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.tblQuestionPaper.setFormatXml(resHelper.translateString("tblQuestionPaper",tblQuestionPaperStrXML));

        

		//Register control's property binding
		registerBindings();
		registerUIState();


    }

	public com.kingdee.bos.ctrl.swing.KDToolBar[] getUIMultiToolBar(){
		java.util.List list = new java.util.ArrayList();
		com.kingdee.bos.ctrl.swing.KDToolBar[] bars = super.getUIMultiToolBar();
		if (bars != null) {
			list.addAll(java.util.Arrays.asList(bars));
		}
		return (com.kingdee.bos.ctrl.swing.KDToolBar[])list.toArray(new com.kingdee.bos.ctrl.swing.KDToolBar[list.size()]);
	}




    /**
     * output initUIContentLayout method
     */
    public void initUIContentLayout()
    {
        this.setBounds(new Rectangle(10, 10, 600, 629));
        this.setLayout(null);
        kDContainer1.setBounds(new Rectangle(10, 10, 583, 193));
        this.add(kDContainer1, null);
        kDContainer2.setBounds(new Rectangle(10, 244, 583, 171));
        this.add(kDContainer2, null);
        kDContainer3.setBounds(new Rectangle(10, 430, 583, 171));
        this.add(kDContainer3, null);
        btnAddTrack.setBounds(new Rectangle(492, 215, 101, 19));
        this.add(btnAddTrack, null);
        //kDContainer1
        kDContainer1.getContentPane().setLayout(new KDLayout());
        kDContainer1.getContentPane().putClientProperty("OriginalBounds", new Rectangle(10, 10, 583, 193));        contName.setBounds(new Rectangle(10, 40, 270, 19));
        kDContainer1.getContentPane().add(contName, new KDLayout.Constraints(10, 40, 270, 19, 0));
        contNumber.setBounds(new Rectangle(300, 10, 270, 19));
        kDContainer1.getContentPane().add(contNumber, new KDLayout.Constraints(300, 10, 270, 19, 0));
        contDescription.setBounds(new Rectangle(10, 102, 560, 56));
        kDContainer1.getContentPane().add(contDescription, new KDLayout.Constraints(10, 102, 560, 56, 0));
        contPhone.setBounds(new Rectangle(300, 40, 270, 19));
        kDContainer1.getContentPane().add(contPhone, new KDLayout.Constraints(300, 40, 270, 19, 0));
        contSource.setBounds(new Rectangle(300, 70, 270, 19));
        kDContainer1.getContentPane().add(contSource, new KDLayout.Constraints(300, 70, 270, 19, 0));
        contCluesStatus.setBounds(new Rectangle(167, 166, 270, 19));
        kDContainer1.getContentPane().add(contCluesStatus, new KDLayout.Constraints(167, 166, 270, 19, 0));
        contSellProject.setBounds(new Rectangle(10, 10, 270, 19));
        kDContainer1.getContentPane().add(contSellProject, new KDLayout.Constraints(10, 10, 270, 19, 0));
        contPropertyConsultant.setBounds(new Rectangle(10, 70, 270, 19));
        kDContainer1.getContentPane().add(contPropertyConsultant, new KDLayout.Constraints(10, 70, 270, 19, 0));
        contCognizePath.setBounds(new Rectangle(311, 194, 270, 19));
        kDContainer1.getContentPane().add(contCognizePath, new KDLayout.Constraints(311, 194, 270, 19, 0));
        //contName
        contName.setBoundEditor(txtName);
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contDescription
        contDescription.setBoundEditor(kDScrollPane1);
        //kDScrollPane1
        kDScrollPane1.getViewport().add(txtDescription, null);
        //contPhone
        contPhone.setBoundEditor(txtPhone);
        //contSource
        contSource.setBoundEditor(comboSource);
        //contCluesStatus
        contCluesStatus.setBoundEditor(comboCluesStatus);
        //contSellProject
        contSellProject.setBoundEditor(prmtSellProject);
        //contPropertyConsultant
        contPropertyConsultant.setBoundEditor(prmtPropertyConsultant);
        //contCognizePath
        contCognizePath.setBoundEditor(prmtCognizePath);
        //kDContainer2
        kDContainer2.getContentPane().setLayout(new KDLayout());
        kDContainer2.getContentPane().putClientProperty("OriginalBounds", new Rectangle(10, 244, 583, 171));        tblTrack.setBounds(new Rectangle(10, 4, 555, 125));
        kDContainer2.getContentPane().add(tblTrack, new KDLayout.Constraints(10, 4, 555, 125, 0));
        //kDContainer3
        kDContainer3.getContentPane().setLayout(new KDLayout());
        kDContainer3.getContentPane().putClientProperty("OriginalBounds", new Rectangle(10, 430, 583, 171));        tblQuestionPaper.setBounds(new Rectangle(10, 10, 555, 132));
        kDContainer3.getContentPane().add(tblQuestionPaper, new KDLayout.Constraints(10, 10, 555, 132, 0));

    }


    /**
     * output initUIMenuBarLayout method
     */
    public void initUIMenuBarLayout()
    {
        this.menuBar.add(menuFile);
        this.menuBar.add(menuEdit);
        this.menuBar.add(MenuService);
        this.menuBar.add(menuView);
        this.menuBar.add(menuBiz);
        this.menuBar.add(menuTool);
        this.menuBar.add(menuHelp);
        //menuFile
        menuFile.add(menuItemAddNew);
        menuFile.add(kDSeparator1);
        menuFile.add(menuItemSave);
        menuFile.add(menuItemSubmit);
        menuFile.add(menuSubmitOption);
        menuFile.add(rMenuItemSubmit);
        menuFile.add(rMenuItemSubmitAndAddNew);
        menuFile.add(rMenuItemSubmitAndPrint);
        menuFile.add(separatorFile1);
        menuFile.add(MenuItemAttachment);
        menuFile.add(kDSeparator2);
        menuFile.add(menuItemPageSetup);
        menuFile.add(menuItemPrint);
        menuFile.add(menuItemPrintPreview);
        menuFile.add(kDSeparator3);
        menuFile.add(menuItemExitCurrent);
        //menuSubmitOption
        menuSubmitOption.add(chkMenuItemSubmitAndAddNew);
        menuSubmitOption.add(chkMenuItemSubmitAndPrint);
        //menuEdit
        menuEdit.add(menuItemCopy);
        menuEdit.add(menuItemEdit);
        menuEdit.add(menuItemRemove);
        menuEdit.add(kDSeparator4);
        menuEdit.add(menuItemReset);
        //MenuService
        MenuService.add(MenuItemKnowStore);
        MenuService.add(MenuItemAnwser);
        MenuService.add(SepratorService);
        MenuService.add(MenuItemRemoteAssist);
        //menuView
        menuView.add(menuItemFirst);
        menuView.add(menuItemPre);
        menuView.add(menuItemNext);
        menuView.add(menuItemLast);
        //menuBiz
        menuBiz.add(menuItemCancelCancel);
        menuBiz.add(menuItemCancel);
        //menuTool
        menuTool.add(menuItemMsgFormat);
        menuTool.add(menuItemSendMessage);
        menuTool.add(menuItemCalculator);
        //menuHelp
        menuHelp.add(menuItemHelp);
        menuHelp.add(kDSeparator12);
        menuHelp.add(menuItemRegPro);
        menuHelp.add(menuItemPersonalSite);
        menuHelp.add(helpseparatorDiv);
        menuHelp.add(menuitemProductval);
        menuHelp.add(kDSeparatorProduct);
        menuHelp.add(menuItemAbout);

    }

    /**
     * output initUIToolBarLayout method
     */
    public void initUIToolBarLayout()
    {
        this.toolBar.add(btnAddNew);
        this.toolBar.add(btnEdit);
        this.toolBar.add(btnReset);
        this.toolBar.add(btnSave);
        this.toolBar.add(btnSubmit);
        this.toolBar.add(btnCopy);
        this.toolBar.add(btnRemove);
        this.toolBar.add(btnAttachment);
        this.toolBar.add(separatorFW1);
        this.toolBar.add(btnPageSetup);
        this.toolBar.add(btnPrint);
        this.toolBar.add(btnPrintPreview);
        this.toolBar.add(separatorFW2);
        this.toolBar.add(btnFirst);
        this.toolBar.add(btnPre);
        this.toolBar.add(btnNext);
        this.toolBar.add(btnLast);
        this.toolBar.add(separatorFW3);
        this.toolBar.add(btnCancelCancel);
        this.toolBar.add(btnCancel);


    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("name", String.class, this.txtName, "text");
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("description", String.class, this.txtDescription, "text");
		dataBinder.registerBinding("phone", String.class, this.txtPhone, "text");
		dataBinder.registerBinding("source", com.kingdee.eas.fdc.sellhouse.CluesSourceEnum.class, this.comboSource, "selectedItem");
		dataBinder.registerBinding("cluesStatus", com.kingdee.eas.fdc.sellhouse.CluesStatusEnum.class, this.comboCluesStatus, "selectedItem");
		dataBinder.registerBinding("sellProject", com.kingdee.eas.fdc.sellhouse.SellProjectInfo.class, this.prmtSellProject, "data");
		dataBinder.registerBinding("propertyConsultant", com.kingdee.eas.base.permission.UserInfo.class, this.prmtPropertyConsultant, "data");
		dataBinder.registerBinding("cognizePath", com.kingdee.eas.fdc.sellhouse.SHECusAssistantDataInfo.class, this.prmtCognizePath, "data");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.sellhouse.app.CluesManageEditUIHandler";
	}
	public IUIActionPostman prepareInit() {
		IUIActionPostman clientHanlder = super.prepareInit();
		if (clientHanlder != null) {
			RequestContext request = new RequestContext();
    		request.setClassName(getUIHandlerClassName());
			clientHanlder.setRequestContext(request);
		}
		return clientHanlder;
    }
	
	public boolean isPrepareInit() {
    	return false;
    }
    protected void initUIP() {
        super.initUIP();
    }



	
	

    /**
     * output setDataObject method
     */
    public void setDataObject(IObjectValue dataObject)
    {
        IObjectValue ov = dataObject;        	    	
        super.setDataObject(ov);
        this.editData = (com.kingdee.eas.fdc.sellhouse.CluesManageInfo)ov;
    }

    /**
     * output loadFields method
     */
    public void loadFields()
    {
        dataBinder.loadFields();
    }
    /**
     * output storeFields method
     */
    public void storeFields()
    {
		dataBinder.storeFields();
    }

	/**
	 * ????????§µ??
	 */
	protected void registerValidator() {
    	getValidateHelper().setCustomValidator( getValidator() );
		getValidateHelper().registerBindProperty("name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("phone", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("source", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("cluesStatus", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("sellProject", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("propertyConsultant", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("cognizePath", ValidateHelper.ON_SAVE);    		
	}



    /**
     * output setOprtState method
     */
    public void setOprtState(String oprtType)
    {
        super.setOprtState(oprtType);
        if (STATUS_ADDNEW.equals(this.oprtState)) {
        } else if (STATUS_EDIT.equals(this.oprtState)) {
        } else if (STATUS_VIEW.equals(this.oprtState)) {
        }
    }

    /**
     * output btnAddTrack_actionPerformed method
     */
    protected void btnAddTrack_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output tblTrack_tableClicked method
     */
    protected void tblTrack_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("description"));
        sic.add(new SelectorItemInfo("phone"));
        sic.add(new SelectorItemInfo("source"));
        sic.add(new SelectorItemInfo("cluesStatus"));
        sic.add(new SelectorItemInfo("sellProject.*"));
        sic.add(new SelectorItemInfo("propertyConsultant.*"));
        sic.add(new SelectorItemInfo("cognizePath.*"));
        return sic;
    }        

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "CluesManageEditUI");
    }




}