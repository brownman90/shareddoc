Ext.define('Share.view.DocLogGrid', {
    extend: 'Ext.grid.Panel',
    alias: 'widget.docloggrid',

    region: 'center',
    title: 'Document Logs',
    store: 'DocLogs',

    columns: [
        {width: 40, dataIndex: 'action', renderer: function (value) {
            return '<div class="' + value + '" style="width: 16px; height: 16px;"></div>';
        }},
        {header: 'Document Name', dataIndex: 'name', flex: 3, renderer: function (value, md, record) {
//            if (record.raw.type.isNotEmpty()) {
//                return value + "<span style='color:gray'>." + record.raw.type + "</span>";
//            }
            return value;
        }},
        {header: 'Upload Path', dataIndex: 'path', flex: 2},
        {header: 'Operator', dataIndex: 'operator', flex: 1, align: 'center'},
        {header: 'Action Time', dataIndex: 'time', xtype: 'datecolumn', format: 'Y-m-d H:s:i', flex: 2, align: 'center'}
    ],
    bbar: {
        xtype: 'pagingtoolbar',
        store: 'DocLogs',
        pageSize: 4,
        displayInfo: true
    }

});
