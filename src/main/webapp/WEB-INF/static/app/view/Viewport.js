Ext.define('Share.view.Viewport', {
    extend: 'Ext.container.Viewport',

    layout: 'border',
    items: [
        {
            xtype: 'doclist'
        },
        {
            xtype: 'dirtree'
        }
    ]
});