Ext.define('Share.view.ConfigWin', {
    extend: 'Commons.Window',
    alias: 'widget.configwin',

    title: 'Settings',
    width: 500,
    height: 400,
    initComponent: function () {
        this.items = [
            {
                xtype: 'form',
                padding: 10,
                border: false,
                items: [
                    {
                        xtype: 'fieldset',
                        title: 'Storage',
                        defaults: {
                            msgTarget: 'side',
                            anchor: '100%'
                        },
                        items: [
                            {
                                xtype: 'textfield',
                                name: 'location',
                                fieldLabel: 'Location',
                                allowBlank: false
                            },
                            {
                                xtype: 'textslider',
                                name: 'limit',
                                itemId: 'limit',
                                fieldLabel: 'Max Capacity',
                                vtype: 'disk',
                                allowBlank: false,
                                useTips: true,
                                tipText: function (thumb) {
                                    var max = thumb.slider.maxValue;
                                    var ratio = Math.floor(thumb.value * 1.0 / max * 100);
                                    var value = CommonsUtils.readableSize(thumb.value);
                                    return Ext.String.format("{0} - {1}%", value, ratio);
                                },
                                displayFormatter: CommonsUtils.readableSize,
                                sliderFormatter: CommonsUtils.disReadableSize
                            },
                            {
                                xtype: 'displayslider',
                                name: 'exceed',
                                itemId: 'exceed',
                                fieldLabel: 'Exceed Warn',
                                sliderStep: 5,
                                useTips: true,
                                tipText: function (thumb) {
                                    return thumb.value + "%";
                                },
                                displayFormatter: function (value) {
                                    return value + "%";
                                }
                            }
                        ]
                    }
                ]
            }
        ];

        this.callParent(arguments);
    }
});
