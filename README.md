# CustomSwitchView


Custom switch  view only work on swipe and customized size, By extending view class. this example can give you fine help in understanding clipping and restoring canvas.

Changes the color of the sliding circle

    app:buttonColor="@color/buttonColor"

Changes the imageof the sliding circle

    app:buttonImage="@drawable/flash_24dp"

Changes the color of the sliding circle shadow

    app:shadowColor="@color/shadowColor"

Changes the color of the switchOn side

    app:leftColor="@color/onColor"

Changes the color of the switchOff side

    app:rightColor="@color/offColor"

Changes the switch state On/Off

    app:isOn="true"

Changes the image of the switchOn side

    app:leftImage="@drawable/drawleft"

Changes the image of the switchOff side

    app:rightImage="@drawable/drawright"

Add extra radius to sliding circle bby increasing the padding

    app:extraRadius="10dp"

Changes the ration width : height

    app:ratio="two"

The switch view has the OnCheckedChangeListener

switchView.setOnCheckedChangeListener(new SwitchViewTest.OnCheckedChangeListener() {
            @override
            public void onCheckedChanged(SwitchViewTest switchView, boolean isChecked) {
                Log.i("Switch View", String.valueOf(isChecked));
            }
        });

The switch view has functionality change state of the switch

switchView.setCheck(false);


If you like any changes please comment below it will be appriciated

For ScreenShot go "https://www.facebook.com/aazammazaa/"
