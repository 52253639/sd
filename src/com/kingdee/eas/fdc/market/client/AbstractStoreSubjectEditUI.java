/**
 * output package name
 */
package com.kingdee.eas.fdc.market.client;

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
public abstract class AbstractStoreSubjectEditUI extends com.kingdee.eas.framework.client.EditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractStoreSubjectEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conttopic;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contstoreSubClass;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contsubjectType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contxCellCreter;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtOptions;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtItems;
    protected com.kingdee.bos.ctrl.swing.KDButton btnAddItem;
    protected com.kingdee.bos.ctrl.swing.KDButton btnDeleItem;
    protected com.kingdee.bos.ctrl.swing.KDButton btnAddOption;
    protected com.kingdee.bos.ctrl.swing.KDButton btnDeleOption;
    protected com.kingdee.bos.ctrl.swing.KDTextArea areaSubjectTopic;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDespLength;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAlignType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer1;
    protected com.kingdee.bos.ctrl.swing.KDTextField txttopic;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtstoreSubClass;
    protected com.kingdee.bos.ctrl.swing.KDComboBox subjectType;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtxCellCreter;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtDespLength;
    protected com.kingdee.bos.ctrl.swing.KDComboBox combAlignType;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtsubjectNumber;
    protected com.kingdee.eas.fdc.market.StoreSubjectInfo editData = null;
    /**
     * output class constructor
     */
    public AbstractStoreSubjectEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractStoreSubjectEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.conttopic = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contstoreSubClass = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contsubjectType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contxCellCreter = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kdtOptions = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kdtItems = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.btnAddItem = new com.kingdee.bos.ctrl.swing.KDButton();
        this.btnDeleItem = new com.kingdee.bos.ctrl.swing.KDButton();
        this.btnAddOption = new com.kingdee.bos.ctrl.swing.KDButton();
        this.btnDeleOption = new com.kingdee.bos.ctrl.swing.KDButton();
        this.areaSubjectTopic = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.contDespLength = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAlignType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txttopic = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtstoreSubClass = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.subjectType = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.txtxCellCreter = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtDespLength = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.combAlignType = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.txtsubjectNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.conttopic.setName("conttopic");
        this.contstoreSubClass.setName("contstoreSubClass");
        this.contsubjectType.setName("contsubjectType");
        this.contxCellCreter.setName("contxCellCreter");
        this.kdtOptions.setName("kdtOptions");
        this.kdtItems.setName("kdtItems");
        this.btnAddItem.setName("btnAddItem");
        this.btnDeleItem.setName("btnDeleItem");
        this.btnAddOption.setName("btnAddOption");
        this.btnDeleOption.setName("btnDeleOption");
        this.areaSubjectTopic.setName("areaSubjectTopic");
        this.contDespLength.setName("contDespLength");
        this.contAlignType.setName("contAlignType");
        this.kDLabelContainer1.setName("kDLabelContainer1");
        this.txttopic.setName("txttopic");
        this.prmtstoreSubClass.setName("prmtstoreSubClass");
        this.subjectType.setName("subjectType");
        this.txtxCellCreter.setName("txtxCellCreter");
        this.txtDespLength.setName("txtDespLength");
        this.combAlignType.setName("combAlignType");
        this.txtsubjectNumber.setName("txtsubjectNumber");
        // CoreUI		
        this.btnPrint.setVisible(false);		
        this.btnPrintPreview.setVisible(false);		
        this.menuItemPrint.setVisible(false);		
        this.menuItemPrintPreview.setVisible(false);
        // conttopic		
        this.conttopic.setBoundLabelText(resHelper.getString("conttopic.boundLabelText"));		
        this.conttopic.setBoundLabelLength(100);		
        this.conttopic.setBoundLabelUnderline(true);		
        this.conttopic.setVisible(true);		
        this.conttopic.setBoundLabelAlignment(7);		
        this.conttopic.setForeground(new java.awt.Color(0,0,0));
        // contstoreSubClass		
        this.contstoreSubClass.setBoundLabelText(resHelper.getString("contstoreSubClass.boundLabelText"));		
        this.contstoreSubClass.setBoundLabelLength(100);		
        this.contstoreSubClass.setBoundLabelUnderline(true);		
        this.contstoreSubClass.setVisible(true);		
        this.contstoreSubClass.setBoundLabelAlignment(7);		
        this.contstoreSubClass.setForeground(new java.awt.Color(0,0,0));
        // contsubjectType		
        this.contsubjectType.setBoundLabelText(resHelper.getString("contsubjectType.boundLabelText"));		
        this.contsubjectType.setBoundLabelLength(100);		
        this.contsubjectType.setBoundLabelUnderline(true);		
        this.contsubjectType.setVisible(true);		
        this.contsubjectType.setBoundLabelAlignment(7);		
        this.contsubjectType.setForeground(new java.awt.Color(0,0,0));
        // contxCellCreter		
        this.contxCellCreter.setBoundLabelText(resHelper.getString("contxCellCreter.boundLabelText"));		
        this.contxCellCreter.setBoundLabelLength(100);		
        this.contxCellCreter.setBoundLabelUnderline(true);		
        this.contxCellCreter.setVisible(false);		
        this.contxCellCreter.setBoundLabelAlignment(7);		
        this.contxCellCreter.setForeground(new java.awt.Color(0,0,0));
        // kdtOptions
		String kdtOptionsStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:NumberFormat>#,##0</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol2\"><c:NumberFormat>#,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol3\"><c:Protection hidden=\"true\" /><c:NumberFormat>#,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol5\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol6\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"optionNumber\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"0\" t:styleID=\"sCol0\" /><t:Column t:key=\"topic\" t:width=\"540\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" /><t:Column t:key=\"xLength\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" t:styleID=\"sCol2\" /><t:Column t:key=\"xHeight\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" t:styleID=\"sCol3\" /><t:Column t:key=\"isTopicInverse\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /><t:Column t:key=\"id\" t:width=\"0\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol5\" /><t:Column t:key=\"itemId\" t:width=\"0\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol6\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{optionNumber}</t:Cell><t:Cell>$Resource{topic}</t:Cell><t:Cell>$Resource{xLength}</t:Cell><t:Cell>$Resource{xHeight}</t:Cell><t:Cell>$Resource{isTopicInverse}</t:Cell><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{itemId}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.kdtOptions.setFormatXml(resHelper.translateString("kdtOptions",kdtOptionsStrXML));

        

        this.kdtOptions.checkParsed();
        // kdtItems
		String kdtItemsStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:NumberFormat>#,##0</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol2\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol3\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"itemNumber\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"0\" t:styleID=\"sCol0\" /><t:Column t:key=\"topic\" t:width=\"800\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"1\" /><t:Column t:key=\"id\" t:width=\"0\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol2\" /><t:Column t:key=\"subjectId\" t:width=\"0\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol3\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{itemNumber}</t:Cell><t:Cell>$Resource{topic}</t:Cell><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{subjectId}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.kdtItems.setFormatXml(resHelper.translateString("kdtItems",kdtItemsStrXML));

                this.kdtItems.putBindContents("editData",new String[] {"itemNumber","topic","id","subjectId"});


        this.kdtItems.checkParsed();
        KDFormattedTextField kdtItems_itemNumber_TextField = new KDFormattedTextField();
        kdtItems_itemNumber_TextField.setName("kdtItems_itemNumber_TextField");
        kdtItems_itemNumber_TextField.setVisible(true);
        kdtItems_itemNumber_TextField.setEditable(true);
        kdtItems_itemNumber_TextField.setHorizontalAlignment(2);
        kdtItems_itemNumber_TextField.setDataType(1);
        	kdtItems_itemNumber_TextField.setMinimumValue(new java.math.BigDecimal("-999.9999999999"));
        	kdtItems_itemNumber_TextField.setMaximumValue(new java.math.BigDecimal("999.9999999999"));
        kdtItems_itemNumber_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtItems_itemNumber_CellEditor = new KDTDefaultCellEditor(kdtItems_itemNumber_TextField);
        this.kdtItems.getColumn("itemNumber").setEditor(kdtItems_itemNumber_CellEditor);
        KDTextField kdtItems_topic_TextField = new KDTextField();
        kdtItems_topic_TextField.setName("kdtItems_topic_TextField");
        kdtItems_topic_TextField.setMaxLength(80);
        KDTDefaultCellEditor kdtItems_topic_CellEditor = new KDTDefaultCellEditor(kdtItems_topic_TextField);
        this.kdtItems.getColumn("topic").setEditor(kdtItems_topic_CellEditor);
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
        // btnAddOption		
        this.btnAddOption.setText(resHelper.getString("btnAddOption.text"));
        this.btnAddOption.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnAddOption_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnDeleOption		
        this.btnDeleOption.setText(resHelper.getString("btnDeleOption.text"));
        this.btnDeleOption.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnDeleOption_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // areaSubjectTopic		
        this.areaSubjectTopic.setVisible(false);		
        this.areaSubjectTopic.setMaxLength(200);
        // contDespLength		
        this.contDespLength.setBoundLabelText(resHelper.getString("contDespLength.boundLabelText"));		
        this.contDespLength.setBoundLabelLength(100);		
        this.contDespLength.setBoundLabelUnderline(true);
        // contAlignType		
        this.contAlignType.setBoundLabelText(resHelper.getString("contAlignType.boundLabelText"));		
        this.contAlignType.setBoundLabelLength(100);		
        this.contAlignType.setBoundLabelUnderline(true);
        // kDLabelContainer1		
        this.kDLabelContainer1.setBoundLabelText(resHelper.getString("kDLabelContainer1.boundLabelText"));		
        this.kDLabelContainer1.setBoundLabelLength(100);		
        this.kDLabelContainer1.setVisible(false);		
        this.kDLabelContainer1.setEnabled(false);
        // txttopic		
        this.txttopic.setVisible(true);		
        this.txttopic.setHorizontalAlignment(2);		
        this.txttopic.setMaxLength(200);		
        this.txttopic.setRequired(true);		
        this.txttopic.setEnabled(true);		
        this.txttopic.setForeground(new java.awt.Color(0,0,0));
        // prmtstoreSubClass		
        this.prmtstoreSubClass.setQueryInfo("com.kingdee.eas.fdc.market.app.StoreSubjectClassQuery");		
        this.prmtstoreSubClass.setVisible(true);		
        this.prmtstoreSubClass.setEditable(true);		
        this.prmtstoreSubClass.setDisplayFormat("$displayName$");		
        this.prmtstoreSubClass.setEditFormat("$number$");		
        this.prmtstoreSubClass.setCommitFormat("$number$");		
        this.prmtstoreSubClass.setRequired(true);		
        this.prmtstoreSubClass.setEnabled(true);		
        this.prmtstoreSubClass.setForeground(new java.awt.Color(0,0,0));
        		prmtstoreSubClass.addSelectorListener(new SelectorListener() {
			com.kingdee.eas.fdc.market.client.StoreSubjectClassListUI prmtstoreSubClass_F7ListUI = null;
			public void willShow(SelectorEvent e) {
				if (prmtstoreSubClass_F7ListUI == null) {
					try {
						prmtstoreSubClass_F7ListUI = new com.kingdee.eas.fdc.market.client.StoreSubjectClassListUI();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					HashMap ctx = new HashMap();
					ctx.put("bizUIOwner",javax.swing.SwingUtilities.getWindowAncestor(prmtstoreSubClass_F7ListUI));
					prmtstoreSubClass_F7ListUI.setF7Use(true,ctx);
					prmtstoreSubClass.setSelector(prmtstoreSubClass_F7ListUI);
				}
			}
		});
					
        // subjectType		
        this.subjectType.setVisible(true);		
        this.subjectType.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.market.DocumentSubjectTypeEnum").toArray());		
        this.subjectType.setRequired(false);		
        this.subjectType.setEnabled(true);		
        this.subjectType.setForeground(new java.awt.Color(0,0,0));
        this.subjectType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    subjectType_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // txtxCellCreter		
        this.txtxCellCreter.setVisible(true);		
        this.txtxCellCreter.setHorizontalAlignment(2);		
        this.txtxCellCreter.setMaxLength(200);		
        this.txtxCellCreter.setRequired(false);		
        this.txtxCellCreter.setEnabled(true);		
        this.txtxCellCreter.setForeground(new java.awt.Color(0,0,0));		
        this.txtxCellCreter.setText(resHelper.getString("txtxCellCreter.text"));		
        this.txtxCellCreter.setEditable(false);
        // txtDespLength
        // combAlignType		
        this.combAlignType.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.market.DocumentOptionLayoutEnum").toArray());
        // txtsubjectNumber
        this.setFocusTraversalPolicy(new com.kingdee.bos.ui.UIFocusTraversalPolicy(new java.awt.Component[] {txttopic,prmtstoreSubClass,subjectType,txtxCellCreter}));
        this.setFocusCycleRoot(true);
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
        this.setBounds(new Rectangle(0, 0, 800, 600));
        this.setLayout(null);
        conttopic.setBounds(new Rectangle(10, 36, 644, 19));
        this.add(conttopic, null);
        contstoreSubClass.setBounds(new Rectangle(10, 10, 270, 19));
        this.add(contstoreSubClass, null);
        contsubjectType.setBounds(new Rectangle(385, 10, 270, 19));
        this.add(contsubjectType, null);
        contxCellCreter.setBounds(new Rectangle(10, 580, 473, 19));
        this.add(contxCellCreter, null);
        kdtOptions.setBounds(new Rectangle(10, 444, 775, 130));
        this.add(kdtOptions, null);
        kdtItems.setBounds(new Rectangle(10, 100, 660, 338));
        this.add(kdtItems, null);
        btnAddItem.setBounds(new Rectangle(680, 113, 104, 21));
        this.add(btnAddItem, null);
        btnDeleItem.setBounds(new Rectangle(680, 144, 104, 21));
        this.add(btnDeleItem, null);
        btnAddOption.setBounds(new Rectangle(680, 378, 104, 21));
        this.add(btnAddOption, null);
        btnDeleOption.setBounds(new Rectangle(680, 412, 104, 21));
        this.add(btnDeleOption, null);
        areaSubjectTopic.setBounds(new Rectangle(110, 36, 673, 508));
        this.add(areaSubjectTopic, null);
        contDespLength.setBounds(new Rectangle(10, 551, 270, 19));
        this.add(contDespLength, null);
        contAlignType.setBounds(new Rectangle(10, 63, 156, 19));
        this.add(contAlignType, null);
        kDLabelContainer1.setBounds(new Rectangle(505, 579, 270, 19));
        this.add(kDLabelContainer1, null);
        //conttopic
        conttopic.setBoundEditor(txttopic);
        //contstoreSubClass
        contstoreSubClass.setBoundEditor(prmtstoreSubClass);
        //contsubjectType
        contsubjectType.setBoundEditor(subjectType);
        //contxCellCreter
        contxCellCreter.setBoundEditor(txtxCellCreter);
        //contDespLength
        contDespLength.setBoundEditor(txtDespLength);
        //contAlignType
        contAlignType.setBoundEditor(combAlignType);
        //kDLabelContainer1
        kDLabelContainer1.setBoundEditor(txtsubjectNumber);

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
		dataBinder.registerBinding("items", com.kingdee.eas.fdc.market.StoreItemInfo.class, this.kdtItems, "userObject");
		dataBinder.registerBinding("items.topic", String.class, this.kdtItems, "topic.text");
		dataBinder.registerBinding("items.itemNumber", java.math.BigDecimal.class, this.kdtItems, "itemNumber.text");
		dataBinder.registerBinding("items.id", com.kingdee.bos.util.BOSUuid.class, this.kdtItems, "id.text");
		dataBinder.registerBinding("items.subjectId", com.kingdee.eas.fdc.market.StoreItemCollection.class, this.kdtItems, "subjectId.text");
		dataBinder.registerBinding("topic", String.class, this.txttopic, "text");
		dataBinder.registerBinding("storeSubClass", com.kingdee.eas.fdc.market.StoreSubjectClassInfo.class, this.prmtstoreSubClass, "data");
		dataBinder.registerBinding("subjectType", com.kingdee.eas.fdc.market.DocumentSubjectTypeEnum.class, this.subjectType, "selectedItem");
		dataBinder.registerBinding("xCellCreter", String.class, this.txtxCellCreter, "text");
		dataBinder.registerBinding("alignType", com.kingdee.eas.fdc.market.DocumentOptionLayoutEnum.class, this.combAlignType, "selectedItem");
		dataBinder.registerBinding("subjectNumber", int.class, this.txtsubjectNumber, "text");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.market.app.StoreSubjectEditUIHandler";
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
     * output onShow method
     */
    public void onShow() throws Exception
    {
        super.onShow();
        this.txttopic.requestFocusInWindow();
    }

	
	

    /**
     * output setDataObject method
     */
    public void setDataObject(IObjectValue dataObject)
    {
        IObjectValue ov = dataObject;        	    	
        super.setDataObject(ov);
        this.editData = (com.kingdee.eas.fdc.market.StoreSubjectInfo)ov;
    }
			protected com.kingdee.eas.basedata.org.OrgType getMainBizOrgType() {
			return com.kingdee.eas.basedata.org.OrgType.getEnum("Sale");
		}


    /**
     * output loadFields method
     */
    public void loadFields()
    {
        dataBinder.loadFields();
    }
		protected void setOrgF7(KDBizPromptBox f7,com.kingdee.eas.basedata.org.OrgType orgType) throws Exception
		{
			com.kingdee.bos.ctrl.extendcontrols.ext.OrgUnitFilterInfoProducer oufip=(com.kingdee.bos.ctrl.extendcontrols.ext.OrgUnitFilterInfoProducer)com.kingdee.bos.ctrl.extendcontrols.ext.FilterInfoProducerFactory.getOrgUnitFilterInfoProducer(orgType);
			oufip.getModel().setIsCUFilter(true);
			f7.setFilterInfoProducer(oufip);
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
		getValidateHelper().registerBindProperty("items", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("items.topic", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("items.itemNumber", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("items.id", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("items.subjectId", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("topic", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("storeSubClass", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("subjectType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("xCellCreter", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("alignType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("subjectNumber", ValidateHelper.ON_SAVE);    		
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
     * output btnAddOption_actionPerformed method
     */
    protected void btnAddOption_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output btnDeleOption_actionPerformed method
     */
    protected void btnDeleOption_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output subjectType_actionPerformed method
     */
    protected void subjectType_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("items.*"));
//        sic.add(new SelectorItemInfo("items.number"));
    sic.add(new SelectorItemInfo("items.topic"));
    sic.add(new SelectorItemInfo("items.itemNumber"));
    sic.add(new SelectorItemInfo("items.id"));
        sic.add(new SelectorItemInfo("items.subjectId.*"));
//        sic.add(new SelectorItemInfo("items.subjectId.number"));
        sic.add(new SelectorItemInfo("topic"));
        sic.add(new SelectorItemInfo("storeSubClass.*"));
        sic.add(new SelectorItemInfo("subjectType"));
        sic.add(new SelectorItemInfo("xCellCreter"));
        sic.add(new SelectorItemInfo("alignType"));
        sic.add(new SelectorItemInfo("subjectNumber"));
        return sic;
    }        

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.market.client", "StoreSubjectEditUI");
    }

    /**
     * output getEditUIName method
     */
    protected String getEditUIName()
    {
        return com.kingdee.eas.fdc.market.client.StoreSubjectEditUI.class.getName();
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.fdc.market.StoreSubjectFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected IObjectValue createNewData()
    {
        com.kingdee.eas.fdc.market.StoreSubjectInfo objectValue = new com.kingdee.eas.fdc.market.StoreSubjectInfo();
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));		
        return objectValue;
    }



    /**
     * output getDetailTable method
     */
    protected KDTable getDetailTable() {
        return kdtOptions;
	}
    /**
     * output applyDefaultValue method
     */
    protected void applyDefaultValue(IObjectValue vo) {        
		vo.put("subjectType",new Integer(1));
		vo.setString("xCellCreter","com.kingdee.eas.fdc.market.client.XCellCommonCreater");
        
    }        
	protected void setFieldsNull(com.kingdee.bos.dao.AbstractObjectValue arg0) {
		super.setFieldsNull(arg0);
		arg0.put("number",null);
	}

}