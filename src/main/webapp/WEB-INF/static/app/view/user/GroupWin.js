Ext.define('AM.view.user.GroupWin', {
    extend: 'Commons.SimpleWindow',
    alias: 'widget.usergrouping',
    title: 'Group View',
    width: 600,
    height: 500,
    initComponent: function () {
        this.items = [
            {
                xtype: 'gridpanel',
                store: 'GroupUsers',
                columns: [
                    {header: 'Username', dataIndex: 'username', width: 200, locked: true, summaryType: 'count',
                        summaryRenderer: function (value, summaryData, dataIndex) {
                            return ((value === 0 || value > 1) ? '(' + value + ' Users)' : '(1 User)');
                        }},
                    {header: 'Nickname', dataIndex: 'nickname', width: 100},
                    {header: 'Realname', dataIndex: 'realname', width: 100},
                    {header: 'Gender', dataIndex: 'sex', width: 100, align: 'center', renderer: function (value) {
                        if (value === 1)
                            return 'Male';
                        return 'Female';
                    }},
                    {header: 'Birthday', dataIndex: 'birthday', width: 100, xtype: 'datecolumn', format: 'Y-m-d', align: 'center'}
                ],
                features: [
                    {
                        ftype: 'groupingsummary',
//                hideGroupedHeader: true,
                        startCollapsed: true,
                        groupHeaderTpl: '{columnName}: {name} ({rows.length} Item{[values.rows.length > 1 ? "s" : ""]})'
                    }
                ]
            }
        ];
        this.callParent(arguments);
    }

});