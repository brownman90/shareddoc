Ext.define('Share.model.Doc', {
    extend: 'Ext.data.Model',

    fields: [
        {name: 'id', type: 'int'},
        {name: 'name', type: 'string'},
        {name: 'type', type: 'string'},
        {name: 'size', type: 'string'},
        {name: 'path', type: 'string'},
        {name: 'time', type: 'string'},
        {name: 'committer', type: 'string'}


    ]
});