Ext.define('Share.store.Docs', {
    extend: 'Ext.data.Store',
    model: 'Share.model.Doc',
    autoLoad: true,
    pageSize: 15,

    proxy: {
        type: 'ajax',
        api: {
            read: '/doc/list',
            update: '/doc/update',
            create: '/doc/create',
            destroy: '/doc/delete'
        },
        reader: {
            type: 'json',
            root: 'rows',
            totalProperty: 'count'
        }
    }
});