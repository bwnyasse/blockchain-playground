import 'package:flutter/material.dart';
import 'package:flutter_signin_button/flutter_signin_button.dart';
import 'package:get/get_state_manager/get_state_manager.dart';
import 'package:metamask_connect/controllers/controller.dart';
import 'package:metamask_connect/widgets/icons.dart';

class Home extends StatelessWidget {
  const Home({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return GetBuilder<HomeController>(
        init: HomeController(),
        builder: (h) {
          return Scaffold(
            appBar: AppBar(
              title: const Text('Demo Web3 connection to a metamask'),
            ),
            body: Builder(builder: (_) {
              print("h.isConnected " + h.isConnected.toString());
              print("h.isInOperatingChain " + h.isInOperatingChain.toString());
              print("h.isEnabled " + h.isEnabled.toString());
              return Center(
                child: Column(
                  mainAxisAlignment: MainAxisAlignment.center,
                  children: <Widget>[
                    if (h.isConnected && h.isInOperatingChain) const Text("You are connected to Rinkeby"),
                    if (h.isConnected && !h.isInOperatingChain) const Text('Wrong Chain ! Please connect to Rinkeby!'),
                    if (!h.isConnected && h.isEnabled)
                      SignInButtonBuilder(
                        text: 'Sign in with Metamask',
                        icon: UiIcons.metamask,
                        onPressed: () => h.connect(),
                        backgroundColor: Colors.black,
                        width: 220.0,
                      ),
                    if (!h.isEnabled) const Text('Please use a Web3 supported browser.'),
                    const Text(''),
                  ],
                ),
              );
            }),
          );
        });
  }
}
