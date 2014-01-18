Ext.define('Share.view.DocList', {
    extend: 'Ext.grid.Panel',
    alias: 'widget.doclist',

    region: 'center',
    title: 'Document List',
    store: 'Docs',

    selType: 'checkboxmodel',
    tbar: [
        {
            xtype:'form',
            border : false,
            padding: '3 0 0 0',
            height : 29,
            items:[
            {xtype: 'filefield', name: 'file', emptyText: 'Select Document', labelWidth: 70, allowBlank:false}
        ]},
        { action:'upload' ,text:'Upload', iconCls:'upload', tooltip: 'Upload'},
        '->',
        { xtype: 'textfield', name: 'name', emptyText: 'Document Name'},

        { action: 'search', text: 'Search', iconCls: 'search', tooltip: 'Search Docs' }
    ],
    columns: [
        {header: 'Name', dataIndex: 'name', flex: 1},
        {header: 'Size', dataIndex: 'size', flex: 1},
        {header: 'Path', dataIndex: 'path', flex: 1},
        {header: 'Committer', dataIndex: 'committer', flex: 1},
        {header: 'Post Time', dataIndex: 'time', flex: 1},
    ],
    bbar: {
        xtype: 'pagingtoolbar',
        store: 'Docs',
        pageSize: 15,
        displayInfo: true,
        plugins: Ext.create('Ext.ux.ProgressBarPager')
    }
});
