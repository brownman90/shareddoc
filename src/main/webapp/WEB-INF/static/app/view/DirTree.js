Ext.define('Share.view.DirTree', {
    extend: 'Ext.tree.Panel',
    alias: 'widget.dirtree',

    title: 'Directory Navigation',
    width: 300,
    region: 'west',
    split: true,
    collapsible: true,
    layout: 'fit',
    border: false,

    useArrows: true,
    store: 'Dirs',

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