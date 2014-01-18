Ext.define('AM.view.user.Tree', {
    extend: 'Ext.tree.Panel',
    alias: 'widget.usertree',

    title: 'User Tree',
    width: 300,


    useArrows: true,
    store: 'Tree',

    tools: [
        {
            id: 'refresh-tree-btn',
            type: 'refresh'
        }
    ],
    viewConfig: {
        loadMask: true
    }
});