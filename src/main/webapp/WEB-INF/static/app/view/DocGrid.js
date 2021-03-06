Ext.define('Share.view.DocGrid', {
    extend: 'Ext.grid.Panel',
    alias: 'widget.docgrid',

    region: 'center',
    title: 'Document List',
    titleAlign: 'center',
    store: 'Docs',
    selType: 'checkboxmodel',
    emptyText: "No document is in this directory. Click 'browser' to select document you want to share, then click 'Upload'.",
    tbar: [
        {
            xtype: 'form',
            border: false,
            padding: '3 0 0 0',
            height: 29,
            items: [
                { xtype: 'hiddenfield', name: 'path'},
                { xtype: 'filefield', name: 'file', emptyText: 'Select Document', labelWidth: 70, allowBlank: false}
            ]},
        { action: 'upload', text: 'Upload', iconCls: 'upload', tooltip: 'Upload your Document'},
        '-',
        { action: 'selections', iconCls: 'checkbox', tooltip: "Enable Multi-Select", enableToggle: true},
        { action: 'delete', text: 'Delete', iconCls: 'trash', tooltip: "Delete Documents", disabled: true},
        { action: 'download', text: 'Download', iconCls: 'download', tooltip: "Download Documents", disabled: true},
        '->',
        { xtype: 'searchfield', fieldLabel: 'Search', labelWidth: 50, name: 'name', store: 'Docs', width: 250, emptyText: 'Document Name', paramName: 'name'}
    ],
    columns: [
        {header: 'Name', dataIndex: 'name', flex: 3, renderer: function (value, md, record) {
            if (record.raw.type.isNotEmpty()) {
                return value + "<span style='color:gray'>." + record.raw.type + "</span>";
            }
            return value;
        }},
        {header: 'Size', dataIndex: 'size', flex: 1, renderer: function (value) {
            return CommonsUtils.readableSize(value);
        }},
        {header: 'Path', dataIndex: 'path', flex: 2},
        {header: 'Committer', dataIndex: 'committer', flex: 1, align: 'center'},
        {header: 'Post Time', dataIndex: 'time', xtype: 'datecolumn', format: 'Y-m-d H:s:i', flex: 2, align: 'center'},
        {xtype: 'actioncolumn', menuDisabled: true, align: 'center', text: 'Actions',
            width: 80,
            items: [
                {
                    iconCls: 'download',
                    tooltip: 'Download Document',
                    handler: function (grid, rowIndex) {
                        Ext.Msg.confirm("Confirm", "Are you sure you want to delete this document?", function (v) {
                            if (v === "yes") {
                                var id = grid.getStore().getAt(rowIndex).raw.id;
                                window.open("/doc/download?id=" + id);
                            }
                        });
                    }
                },
                {
                    iconCls: 'trash',
                    tooltip: 'Delete Document',
                    handler: function (grid, rowIndex, colIndex) {
                        var store = grid.getStore();
                        var record = store.getAt(rowIndex);
                        Ext.Msg.confirm("Confirm", "Are you sure you want to delete this document?", function (v) {
                            if (v === "yes") {
                                var operation = new Ext.data.Operation({
                                    action: 'destroy',
                                    id: record.raw.id
                                });
                                store.getProxy().destroy(operation, function (data) {
                                    var result = Ext.JSON.decode(data.response.responseText);
                                    if (result.success) {
                                        store.remove(record);
                                    } else {
                                        Ext.CommonsMsg.error('Error', result.message);
                                    }
                                });
                            }
                        });
                    }
                }
            ]}
    ],
    tools: [
        {
            type: 'gear',
            tooltip: 'Settings',
            action: 'settings'
        }
    ],
    bbar: {
        xtype: 'pagingtoolbar',
        store: 'Docs',
        pageSize: 15,
        displayInfo: true,
        plugins: Ext.create('Ext.ux.ProgressBarPager'),
        items : [
            ' ','-',{
                text : 'Logs',
                iconCls: 'log',
                tooltip: 'Document Logs and System Logs',
                handler : function(){
                    Ext.widget("logwin").show();
                }
            }
        ]
    },

    listeners: {
        viewready: function (grid) {
            //hide the check column on view ready
            Ext.Array.insert(grid.columns, 0, [grid.columnManager.columns[0]]);
            grid.columns[0].hide();
        }


    }
});
