Ext.define('Share.store.DocLogs', {
    extend: 'Ext.data.Store',
    model: 'Share.model.DocLog',
    autoLoad: true,
    pageSize: 4,

    proxy: {
        type: 'ajax',
        url: '/log/doc/list',
        reader: {
            type: 'json',
            root: 'rows'
        }
    }
});