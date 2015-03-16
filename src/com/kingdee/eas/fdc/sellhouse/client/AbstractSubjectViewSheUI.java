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
public abstract class AbstractSubjectViewSheUI extends com.kingdee.eas.framework.client.BillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractSubjectViewSheUI.class);
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtOptions;
    protected com.kingdee.bos.ctrl.swing.KDButton btnOK;
    protected com.kingdee.bos.ctrl.swing.KDButton addLine;
    protected com.kingdee.bos.ctrl.swing.KDButton deleLine;
    protected com.kingdee.bos.ctrl.swing.KDButton btnNO;
    protected com.kingdee.bos.ctrl.swing.KDSpinner txtXFontSize;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel2;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comSubjectType;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel4;
    protected com.kingdee.bos.ctrl.swing.KDSpinner subjectFontSize;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel5;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel8;
    protected com.kingdee.bos.ctrl.swing.KDButton btnAddItem;
    protected com.kingdee.bos.ctrl.swing.KDButton btnDeleItem;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtItems;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comOptonAlignType;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel1;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comHorizonType;
    protected com.kingdee.bos.ctrl.swing.KDTextArea descriptionText;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel7;
    protected com.kingdee.bos.ctrl.swing.KDNumberTextField txtSubjectNumber;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox comSubjectNumberShow;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDocItemType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSelPro;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDespLength;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDespFontSize;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox kDIsRequired;
    protected com.kingdee.bos.ctrl.swing.KDComboBox combDocItemType;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtSellPro;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtSubjectTopic;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtDespLength;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtDespFontSize;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnSure;
    protected com.kingdee.eas.fdc.market.DocumentItemInfo editData = null;
    protected com.kingdee.eas.fdc.market.DocumentSubjectInfo documentSubject = null;
    protected ActionSure actionSure = null;
    protected ActionAddFillLine actionAddFillLine = null;
    /**
     * output class constructor
     */
    public AbstractSubjectViewSheUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractSubjectViewSheUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionSure
        this.actionSure = new ActionSure(this);
        getActionManager().registerAction("actionSure", actionSure);
         this.actionSure.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionAddFillLine
        this.actionAddFillLine = new ActionAddFillLine(this);
        getActionManager().registerAction("actionAddFillLine", actionAddFillLine);
         this.actionAddFillLine.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.kdtOptions = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.btnOK = new com.kingdee.bos.ctrl.swing.KDButton();
        this.addLine = new com.kingdee.bos.ctrl.swing.KDButton();
        this.deleLine = new com.kingdee.bos.ctrl.swing.KDButton();
        this.btnNO = new com.kingdee.bos.ctrl.swing.KDButton();
        this.txtXFontSize = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.kDLabel2 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.comSubjectType = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.kDLabel4 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.subjectFontSize = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.kDLabel5 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel8 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.btnAddItem = new com.kingdee.bos.ctrl.swing.KDButton();
        this.btnDeleItem = new com.kingdee.bos.ctrl.swing.KDButton();
        this.kdtItems = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.comOptonAlignType = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.kDLabel1 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.comHorizonType = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.descriptionText = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.kDLabel7 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.txtSubjectNumber = new com.kingdee.bos.ctrl.swing.KDNumberTextField();
        this.comSubjectNumberShow = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.contDocItemType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSelPro = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDespLength = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDespFontSize = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDIsRequired = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.combDocItemType = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.prmtSellPro = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtSubjectTopic = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtDespLength = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtDespFontSize = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.btnSure = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.kdtOptions.setName("kdtOptions");
        this.btnOK.setName("btnOK");
        this.addLine.setName("addLine");
        this.deleLine.setName("deleLine");
        this.btnNO.setName("btnNO");
        this.txtXFontSize.setName("txtXFontSize");
        this.kDLabel2.setName("kDLabel2");
        this.comSubjectType.setName("comSubjectType");
        this.kDLabel4.setName("kDLabel4");
        this.subjectFontSize.setName("subjectFontSize");
        this.kDLabel5.setName("kDLabel5");
        this.kDLabel8.setName("kDLabel8");
        this.btnAddItem.setName("btnAddItem");
        this.btnDeleItem.setName("btnDeleItem");
        this.kdtItems.setName("kdtItems");
        this.comOptonAlignType.setName("comOptonAlignType");
        this.kDLabel1.setName("kDLabel1");
        this.comHorizonType.setName("comHorizonType");
        this.descriptionText.setName("descriptionText");
        this.kDLabel7.setName("kDLabel7");
        this.txtSubjectNumber.setName("txtSubjectNumber");
        this.comSubjectNumberShow.setName("comSubjectNumberShow");
        this.contDocItemType.setName("contDocItemType");
        this.contSelPro.setName("contSelPro");
        this.kDLabelContainer1.setName("kDLabelContainer1");
        this.contDespLength.setName("contDespLength");
        this.contDespFontSize.setName("contDespFontSize");
        this.kDIsRequired.setName("kDIsRequired");
        this.combDocItemType.setName("combDocItemType");
        this.prmtSellPro.setName("prmtSellPro");
        this.txtSubjectTopic.setName("txtSubjectTopic");
        this.txtDespLength.setName("txtDespLength");
        this.txtDespFontSize.setName("txtDespFontSize");
        this.btnSure.setName("btnSure");
        // CoreUI		
        this.btnAddNew.setVisible(false);		
        this.btnEdit.setVisible(false);		
        this.btnSave.setVisible(false);		
        this.btnSubmit.setVisible(false);		
        this.btnCopy.setVisible(false);		
        this.btnRemove.setVisible(false);		
        this.btnFirst.setVisible(false);		
        this.btnPre.setVisible(false);		
        this.btnNext.setVisible(false);		
        this.btnLast.setVisible(false);		
        this.btnPrint.setVisible(false);		
        this.btnPrintPreview.setVisible(false);		
        this.btnAttachment.setVisible(false);		
        this.separatorFW1.setVisible(false);		
        this.separatorFW2.setVisible(false);		
        this.separatorFW3.setVisible(false);		
        this.btnAddLine.setVisible(false);		
        this.btnInsertLine.setVisible(false);		
        this.btnRemoveLine.setVisible(false);		
        this.btnCreateFrom.setVisible(false);		
        this.btnTraceUp.setVisible(false);		
        this.btnTraceDown.setVisible(false);		
        this.btnAuditResult.setVisible(false);		
        this.btnMultiapprove.setVisible(false);		
        this.btnNextPerson.setVisible(false);		
        this.btnWorkFlowG.setVisible(false);		
        this.separatorFW8.setVisible(false);		
        this.separatorFW7.setVisible(false);
        // kdtOptions
		String kdtOptionsStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol2\"><c:NumberFormat>0</c:NumberFormat></c:Style><c:Style id=\"sCol4\"><c:NumberFormat>0</c:NumberFormat></c:Style><c:Style id=\"sCol5\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol6\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"optionNumber\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"0\" t:configured=\"false\" /><t:Column t:key=\"topic\" t:width=\"500\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" t:configured=\"false\" /><t:Column t:key=\"xLength\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"2\" t:configured=\"false\" t:styleID=\"sCol2\" /><t:Column t:key=\"isTopicInverse\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" t:configured=\"false\" /><t:Column t:key=\"xFontSize\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"4\" t:configured=\"false\" t:styleID=\"sCol4\" /><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" t:configured=\"false\" t:styleID=\"sCol5\" /><t:Column t:key=\"xFontName\" t:width=\"10\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" t:configured=\"false\" t:styleID=\"sCol6\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:configured=\"false\"><t:Cell t:configured=\"false\">序号</t:Cell><t:Cell t:configured=\"false\">选项标题</t:Cell><t:Cell t:configured=\"false\">预留长度</t:Cell><t:Cell t:configured=\"false\">标题倒置</t:Cell><t:Cell t:configured=\"false\">字体大小</t:Cell><t:Cell t:configured=\"false\">id</t:Cell><t:Cell t:configured=\"false\">字体名称</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.kdtOptions.setFormatXml(resHelper.translateString("kdtOptions",kdtOptionsStrXML));
        this.kdtOptions.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopping(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    kdtOptions_editStopping(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

                this.kdtOptions.putBindContents("editData",new String[] {"optionNumber","topic","xLength","isTopicInverse","xFontSize","id","xFontName"});


        // btnOK		
        this.btnOK.setText(resHelper.getString("btnOK.text"));		
        this.btnOK.setVisible(false);
        this.btnOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnOK_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // addLine		
        this.addLine.setText(resHelper.getString("addLine.text"));
        this.addLine.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    addLine_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // deleLine		
        this.deleLine.setText(resHelper.getString("deleLine.text"));
        this.deleLine.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    deleLine_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnNO		
        this.btnNO.setText(resHelper.getString("btnNO.text"));		
        this.btnNO.setVisible(false);
        this.btnNO.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnNO_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // txtXFontSize		
        this.txtXFontSize.setVisible(false);
        // kDLabel2		
        this.kDLabel2.setText(resHelper.getString("kDLabel2.text"));		
        this.kDLabel2.setVisible(false);
        // comSubjectType		
        this.comSubjectType.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.market.DocumentSubjectTypeEnum").toArray());
        this.comSubjectType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    comSubjectType_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // kDLabel4		
        this.kDLabel4.setText(resHelper.getString("kDLabel4.text"));
        // subjectFontSize
        // kDLabel5		
        this.kDLabel5.setText(resHelper.getString("kDLabel5.text"));
        // kDLabel8		
        this.kDLabel8.setText(resHelper.getString("kDLabel8.text"));
        // btnAddItem		
        this.btnAddItem.setText(resHelper.getString("btnAddItem.text"));
        this.btnAddItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnAddItem_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnDeleItem		
        this.btnDeleItem.setText(resHelper.getString("btnDeleItem.text"));
        this.btnDeleItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnDeleItem_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // kdtItems
		String kdtItemsStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol3\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"assisType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"0\" t:configured=\"false\" t:styleID=\"sCol0\" /><t:Column t:key=\"topic\" t:width=\"450\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" t:configured=\"false\" /><t:Column t:key=\"xFontSize\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" t:configured=\"false\" /><t:Column t:key=\"xFontName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" t:configured=\"false\" t:styleID=\"sCol3\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:configured=\"false\"><t:Cell t:configured=\"false\">资料类别</t:Cell><t:Cell t:configured=\"false\">分组标题</t:Cell><t:Cell t:configured=\"false\">字体大小</t:Cell><t:Cell t:configured=\"false\">字体</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.kdtItems.setFormatXml(resHelper.translateString("kdtItems",kdtItemsStrXML));
        this.kdtItems.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopping(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    kdtItems_editStopping(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

        

        // comOptonAlignType		
        this.comOptonAlignType.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.market.DocumentOptionLayoutEnum").toArray());
        // kDLabel1		
        this.kDLabel1.setText(resHelper.getString("kDLabel1.text"));
        // comHorizonType		
        this.comHorizonType.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.market.DocumentOptionHorizonLayoutEnum").toArray());		
        this.comHorizonType.setVisible(false);
        // descriptionText		
        this.descriptionText.setVisible(false);
        // kDLabel7		
        this.kDLabel7.setText(resHelper.getString("kDLabel7.text"));
        // txtSubjectNumber
        // comSubjectNumberShow		
        this.comSubjectNumberShow.setText(resHelper.getString("comSubjectNumberShow.text"));		
        this.comSubjectNumberShow.setSelected(true);
        // contDocItemType		
        this.contDocItemType.setBoundLabelText(resHelper.getString("contDocItemType.boundLabelText"));		
        this.contDocItemType.setBoundLabelLength(100);		
        this.contDocItemType.setBoundLabelUnderline(true);
        // contSelPro		
        this.contSelPro.setBoundLabelText(resHelper.getString("contSelPro.boundLabelText"));		
        this.contSelPro.setBoundLabelLength(100);		
        this.contSelPro.setBoundLabelUnderline(true);		
        this.contSelPro.setVisible(false);
        // kDLabelContainer1		
        this.kDLabelContainer1.setBoundLabelText(resHelper.getString("kDLabelContainer1.boundLabelText"));		
        this.kDLabelContainer1.setBoundLabelLength(100);		
        this.kDLabelContainer1.setBoundLabelUnderline(true);
        // contDespLength		
        this.contDespLength.setBoundLabelText(resHelper.getString("contDespLength.boundLabelText"));		
        this.contDespLength.setBoundLabelLength(70);		
        this.contDespLength.setBoundLabelUnderline(true);
        // contDespFontSize		
        this.contDespFontSize.setBoundLabelText(resHelper.getString("contDespFontSize.boundLabelText"));		
        this.contDespFontSize.setBoundLabelLength(70);		
        this.contDespFontSize.setBoundLabelUnderline(true);
        // kDIsRequired		
        this.kDIsRequired.setText(resHelper.getString("kDIsRequired.text"));
        // combDocItemType		
        this.combDocItemType.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.market.DocSubItemTypeEnum").toArray());
        this.combDocItemType.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                    combDocItemType_itemStateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // prmtSellPro		
        this.prmtSellPro.setDisplayFormat("$name$");		
        this.prmtSellPro.setEditFormat("$number$");		
        this.prmtSellPro.setCommitFormat("$number$");		
        this.prmtSellPro.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.F7SellProjectQuery");
        this.prmtSellPro.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtSellPro_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtSubjectTopic
        this.txtSubjectTopic.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    txtSubjectTopic_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // txtDespLength
        // txtDespFontSize
        // btnSure
        this.btnSure.setAction((IItemAction)ActionProxyFactory.getProxy(actionSure, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnSure.setText(resHelper.getString("btnSure.text"));		
        this.btnSure.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_affirm"));
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
        this.setBounds(new Rectangle(10, 10, 800, 550));
        this.setLayout(null);
        kDSeparator6.setBounds(new Rectangle(12, 89, 640, 10));
        this.add(kDSeparator6, null);
        kDSeparator7.setBounds(new Rectangle(10, 386, 646, 9));
        this.add(kDSeparator7, null);
        kdtOptions.setBounds(new Rectangle(14, 366, 778, 178));
        this.add(kdtOptions, null);
        btnOK.setBounds(new Rectangle(670, 9, 122, 21));
        this.add(btnOK, null);
        addLine.setBounds(new Rectangle(668, 294, 122, 21));
        this.add(addLine, null);
        deleLine.setBounds(new Rectangle(668, 323, 122, 21));
        this.add(deleLine, null);
        btnNO.setBounds(new Rectangle(670, 34, 122, 21));
        this.add(btnNO, null);
        txtXFontSize.setBounds(new Rectangle(687, 232, 56, 19));
        this.add(txtXFontSize, null);
        kDLabel2.setBounds(new Rectangle(684, 237, 55, 19));
        this.add(kDLabel2, null);
        comSubjectType.setBounds(new Rectangle(67, 64, 70, 19));
        this.add(comSubjectType, null);
        kDLabel4.setBounds(new Rectangle(10, 64, 52, 19));
        this.add(kDLabel4, null);
        subjectFontSize.setBounds(new Rectangle(170, 64, 54, 19));
        this.add(subjectFontSize, null);
        kDLabel5.setBounds(new Rectangle(142, 64, 27, 19));
        this.add(kDLabel5, null);
        kDLabel8.setBounds(new Rectangle(13, 90, 54, 19));
        this.add(kDLabel8, null);
        btnAddItem.setBounds(new Rectangle(670, 72, 122, 21));
        this.add(btnAddItem, null);
        btnDeleItem.setBounds(new Rectangle(670, 99, 122, 21));
        this.add(btnDeleItem, null);
        kdtItems.setBounds(new Rectangle(14, 109, 638, 251));
        this.add(kdtItems, null);
        comOptonAlignType.setBounds(new Rectangle(280, 64, 64, 19));
        this.add(comOptonAlignType, null);
        kDLabel1.setBounds(new Rectangle(230, 64, 54, 19));
        this.add(kDLabel1, null);
        comHorizonType.setBounds(new Rectangle(672, 134, 122, 19));
        this.add(comHorizonType, null);
        descriptionText.setBounds(new Rectangle(13, 95, 642, 449));
        this.add(descriptionText, null);
        kDLabel7.setBounds(new Rectangle(352, 64, 30, 19));
        this.add(kDLabel7, null);
        txtSubjectNumber.setBounds(new Rectangle(376, 64, 39, 19));
        this.add(txtSubjectNumber, null);
        comSubjectNumberShow.setBounds(new Rectangle(421, 64, 76, 19));
        this.add(comSubjectNumberShow, null);
        contDocItemType.setBounds(new Rectangle(10, 34, 270, 19));
        this.add(contDocItemType, null);
        contSelPro.setBounds(new Rectangle(381, 34, 270, 19));
        this.add(contSelPro, null);
        kDLabelContainer1.setBounds(new Rectangle(10, 6, 641, 19));
        this.add(kDLabelContainer1, null);
        contDespLength.setBounds(new Rectangle(672, 181, 122, 19));
        this.add(contDespLength, null);
        contDespFontSize.setBounds(new Rectangle(672, 211, 122, 19));
        this.add(contDespFontSize, null);
        kDIsRequired.setBounds(new Rectangle(500, 64, 58, 19));
        this.add(kDIsRequired, null);
        //contDocItemType
        contDocItemType.setBoundEditor(combDocItemType);
        //contSelPro
        contSelPro.setBoundEditor(prmtSellPro);
        //kDLabelContainer1
        kDLabelContainer1.setBoundEditor(txtSubjectTopic);
        //contDespLength
        contDespLength.setBoundEditor(txtDespLength);
        //contDespFontSize
        contDespFontSize.setBoundEditor(txtDespFontSize);

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
        this.menuBar.add(menuTable1);
        this.menuBar.add(menuTool);
        this.menuBar.add(menuWorkflow);
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
        menuFile.add(menuItemSendMail);
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
        menuEdit.add(separator1);
        menuEdit.add(menuItemCreateFrom);
        menuEdit.add(menuItemCreateTo);
        menuEdit.add(menuItemCopyFrom);
        menuEdit.add(separatorEdit1);
        menuEdit.add(menuItemEnterToNextRow);
        menuEdit.add(separator2);
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
        menuView.add(separator3);
        menuView.add(menuItemTraceUp);
        menuView.add(menuItemTraceDown);
        menuView.add(menuItemLocate);
        //menuBiz
        menuBiz.add(menuItemCancelCancel);
        menuBiz.add(menuItemCancel);
        menuBiz.add(MenuItemVoucher);
        menuBiz.add(menuItemDelVoucher);
        //menuTable1
        menuTable1.add(menuItemAddLine);
        menuTable1.add(menuItemCopyLine);
        menuTable1.add(menuItemInsertLine);
        menuTable1.add(menuItemRemoveLine);
        //menuTool
        menuTool.add(menuItemSendMessage);
        menuTool.add(menuItemMsgFormat);
        menuTool.add(menuItemCalculator);
        //menuWorkflow
        menuWorkflow.add(menuItemStartWorkFlow);
        menuWorkflow.add(separatorWF1);
        menuWorkflow.add(menuItemViewSubmitProccess);
        menuWorkflow.add(menuItemViewDoProccess);
        menuWorkflow.add(MenuItemWFG);
        menuWorkflow.add(menuItemWorkFlowList);
        menuWorkflow.add(separatorWF2);
        menuWorkflow.add(menuItemMultiapprove);
        menuWorkflow.add(menuItemNextPerson);
        menuWorkflow.add(menuItemAuditResult);
        menuWorkflow.add(kDSeparator5);
        menuWorkflow.add(kDMenuItemSendMessage);
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
        this.toolBar.add(btnSave);
        this.toolBar.add(btnReset);
        this.toolBar.add(btnSubmit);
        this.toolBar.add(btnCopy);
        this.toolBar.add(btnRemove);
        this.toolBar.add(btnCancelCancel);
        this.toolBar.add(btnCancel);
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
        this.toolBar.add(btnTraceUp);
        this.toolBar.add(btnTraceDown);
        this.toolBar.add(btnWorkFlowG);
        this.toolBar.add(btnSignature);
        this.toolBar.add(btnViewSignature);
        this.toolBar.add(separatorFW4);
        this.toolBar.add(separatorFW7);
        this.toolBar.add(btnCreateFrom);
        this.toolBar.add(btnCopyFrom);
        this.toolBar.add(btnCreateTo);
        this.toolBar.add(separatorFW5);
        this.toolBar.add(separatorFW8);
        this.toolBar.add(btnAddLine);
        this.toolBar.add(btnCopyLine);
        this.toolBar.add(btnInsertLine);
        this.toolBar.add(btnRemoveLine);
        this.toolBar.add(separatorFW6);
        this.toolBar.add(separatorFW9);
        this.toolBar.add(btnVoucher);
        this.toolBar.add(btnDelVoucher);
        this.toolBar.add(btnAuditResult);
        this.toolBar.add(btnMultiapprove);
        this.toolBar.add(btnWFViewdoProccess);
        this.toolBar.add(btnWFViewSubmitProccess);
        this.toolBar.add(btnNextPerson);
        this.toolBar.add(btnSure);


    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("options.topic", String.class, this.kdtOptions, "topic.text");
		dataBinder.registerBinding("options.xLength", int.class, this.kdtOptions, "xLength.text");
		dataBinder.registerBinding("options.xFontSize", int.class, this.kdtOptions, "xFontSize.text");
		dataBinder.registerBinding("options.xFontName", String.class, this.kdtOptions, "xFontName.text");
		dataBinder.registerBinding("options.optionNumber", int.class, this.kdtOptions, "optionNumber.text");
		dataBinder.registerBinding("options", com.kingdee.eas.fdc.market.DocumentOptionInfo.class, this.kdtOptions, "userObject");
		dataBinder.registerBinding("options.isTopicInverse", boolean.class, this.kdtOptions, "isTopicInverse.text");
		dataBinder.registerBinding("options.id", com.kingdee.bos.util.BOSUuid.class, this.kdtOptions, "id.text");
		dataBinder.registerBinding("xFontSize", int.class, this.txtXFontSize, "value");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.sellhouse.app.SubjectViewSheUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.market.DocumentItemInfo)ov;
    }

    /**
     * output setDataObject method
     */
    public void setDataObject(String key, IObjectValue dataObject)
    {
        super.setDataObject(key, dataObject);
        if (key.equalsIgnoreCase("documentSubject")) {
            this.documentSubject = (com.kingdee.eas.fdc.market.DocumentSubjectInfo)dataObject;
		}
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
	 * ????????У??
	 */
	protected void registerValidator() {
    	getValidateHelper().setCustomValidator( getValidator() );
		getValidateHelper().registerBindProperty("options.topic", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("options.xLength", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("options.xFontSize", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("options.xFontName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("options.optionNumber", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("options", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("options.isTopicInverse", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("options.id", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("xFontSize", ValidateHelper.ON_SAVE);    		
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
        } else if (STATUS_FINDVIEW.equals(this.oprtState)) {
        }
    }

    /**
     * output kdtOptions_editStopping method
     */
    protected void kdtOptions_editStopping(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output btnOK_actionPerformed method
     */
    protected void btnOK_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output addLine_actionPerformed method
     */
    protected void addLine_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output deleLine_actionPerformed method
     */
    protected void deleLine_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output btnNO_actionPerformed method
     */
    protected void btnNO_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output comSubjectType_actionPerformed method
     */
    protected void comSubjectType_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output btnAddItem_actionPerformed method
     */
    protected void btnAddItem_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output btnDeleItem_actionPerformed method
     */
    protected void btnDeleItem_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output kdtItems_editStopping method
     */
    protected void kdtItems_editStopping(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    { 
    	/*
        int index=kdtOptions.getSelectManager().getActiveRowIndex();// wangxiaomin
			if(kdtOptions.getRow(index).getCell("xLength").getValue() instanceof Integer){
				int length=((Integer)kdtOptions.getRow(index).getCell("xLength").getValue()).intValue();
				if(length>10000||length<0){
					kdtOptions.getRow(index).getCell("xLength").setValue("");
					MsgBox.showInfo("长度超长！");
					return;
				}
			}else if(kdtOptions.getRow(index).getCell("xLength").getValue() instanceof String){
				kdtOptions.getRow(index).getCell("xLength").setValue("");
				MsgBox.showInfo("长度应为整数！");
				return ;
			}
			*/
    }

    /**
     * output combDocItemType_itemStateChanged method
     */
    protected void combDocItemType_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
    }

    /**
     * output prmtSellPro_dataChanged method
     */
    protected void prmtSellPro_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output txtSubjectTopic_actionPerformed method
     */
    protected void txtSubjectTopic_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
    sic.add(new SelectorItemInfo("options.topic"));
    sic.add(new SelectorItemInfo("options.xLength"));
    sic.add(new SelectorItemInfo("options.xFontSize"));
    sic.add(new SelectorItemInfo("options.xFontName"));
    sic.add(new SelectorItemInfo("options.optionNumber"));
        sic.add(new SelectorItemInfo("options.*"));
//        sic.add(new SelectorItemInfo("options.number"));
    sic.add(new SelectorItemInfo("options.isTopicInverse"));
    sic.add(new SelectorItemInfo("options.id"));
        sic.add(new SelectorItemInfo("xFontSize"));
        return sic;
    }        
    	

    /**
     * output actionSure_actionPerformed method
     */
    public void actionSure_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionAddFillLine_actionPerformed method
     */
    public void actionAddFillLine_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionSure(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionSure() {
    	return false;
    }
	public RequestContext prepareActionAddFillLine(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAddFillLine() {
    	return false;
    }

    /**
     * output ActionSure class
     */     
    protected class ActionSure extends ItemAction {     
    
        public ActionSure()
        {
            this(null);
        }

        public ActionSure(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionSure.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSure.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSure.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractSubjectViewSheUI.this, "ActionSure", "actionSure_actionPerformed", e);
        }
    }

    /**
     * output ActionAddFillLine class
     */     
    protected class ActionAddFillLine extends ItemAction {     
    
        public ActionAddFillLine()
        {
            this(null);
        }

        public ActionAddFillLine(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionAddFillLine.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAddFillLine.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAddFillLine.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractSubjectViewSheUI.this, "ActionAddFillLine", "actionAddFillLine_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "SubjectViewSheUI");
    }




}