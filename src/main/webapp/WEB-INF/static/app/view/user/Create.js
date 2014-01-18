Ext.define('AM.view.user.Create', {
    extend: 'Commons.Window',
    alias: 'widget.usercreate',

    title: 'Create User',
    width: 350,
    initComponent: function () {
        this.items = [
            {
                xtype: 'form',
                padding: 10,
                border: false,
                defaults: {
                    xtype: 'textfield',
                    msgTarget: 'side',
                    labelWidth: 75,
                    anchor: '100%'
                },
                items: [
                    {
                        name: 'username',
                        fieldLabel: 'Username',
                        allowBlank: false
                    },
                    {
                        name: 'nickname',
                        fieldLabel: 'Nickname'
                    },
                    {
                        name: 'realname',
                        fieldLabel: 'Realname'
                    },
                    {
                        xtype: 'radiogroup',
                        fieldLabel: 'Gender',
                        items: [
                            {boxLabel: 'Male', name: 'sex', inputValue: 1, checked: true},
                            {boxLabel: 'Female', name: 'sex', inputValue: 0}
                        ]
                    },
                    {
                        name: 'birthday',
                        fieldLabel: 'Birthday',
                        xtype: 'datefield',
                        format: 'Y-m-d'
                    }
                ]
            }
        ];

        this.callParent(arguments);
    }
});
