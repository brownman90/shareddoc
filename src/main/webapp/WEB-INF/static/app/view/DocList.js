Ext.define('Share.view.DocList', {
    extend: 'Ext.grid.Panel',
    alias: 'widget.doclist',

    region: 'center',
    title: 'Document List',
    store: 'Docs',

    selType: 'checkboxmodel',
    tbar: [
        {
            xtype: 'form',
            border: false,
            padding: '3 0 0 0',
            height: 29,
            items: [
                {xtype: 'filefield', name: 'file', emptyText: 'Select Document', labelWidth: 70, allowBlank: false}
            ]},
        { action: 'upload', text: 'Upload', iconCls: 'upload', tooltip: 'Upload your Document'},
        '-',
        { action: 'selections', text: 'Selections', iconCls: 'checkbox', tooltip: "Enable Multi-Select", enableToggle: true},
        '-',
        { action: 'delete', text: 'Delete', iconCls: 'trash', tooltip: "Delete Documents"},
        '->',
        { xtype: 'textfield', name: 'name', emptyText: 'Document Name'},

        { action: 'search', text: 'Search', iconCls: 'search', tooltip: 'Search Docs' }
    ],
    columns: [
        {header: 'Name', dataIndex: 'name', flex: 4},
        {header: 'Path', dataIndex: 'path', flex: 2},
        {header: 'Committer', dataIndex: 'committer', flex: 1, align: 'center'},
        {header: 'Post Time', dataIndex: 'time', flex: 1, align: 'center'},
        {xtype: 'actioncolumn', menuDisabled: true, align: 'center', text: 'Actions',
            width: 80,
            items: [
                {
                    iconCls: 'download',
                    tooltip: 'Download Document'

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
                                    if (data.success) {
                                        store.remove(record);
                                    }
                                });
                            }
                        });
                    }
                }
            ]}
    ],
    bbar: {
        xtype: 'pagingtoolbar',
        store: 'Docs',
        pageSize: 15,
        displayInfo: true,
        plugins: Ext.create('Ext.ux.ProgressBarPager')
    },

    listeners: {
        viewready: function (grid) {
            Ext.Array.insert(grid.columns, 0, [grid.columnManager.columns[0]]);
            grid.columns[0].hide();
        }
    }
});
