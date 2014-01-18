Ext.define('Share.store.Dirs', {
    extend: 'Ext.data.TreeStore',
    proxy: {
        type: 'ajax',
        api: {
            read: '/dir/tree'
        }
    },
    root: {
        id: '/',
        text: 'Root',
        expanded: true
    }



});