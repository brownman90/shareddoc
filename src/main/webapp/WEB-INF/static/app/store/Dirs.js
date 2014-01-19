Ext.define('Share.store.Dirs', {
    extend: 'Ext.data.TreeStore',
    proxy: {
        type: 'ajax',
        api: {
            read: '/dir/tree',
            create: '/dir/create',
            destroy: '/dir/delete',
            update: '/dir/update'
        }
    },
    root: {
        id: '/',
        text: 'Root',
        expanded: true
    }



});