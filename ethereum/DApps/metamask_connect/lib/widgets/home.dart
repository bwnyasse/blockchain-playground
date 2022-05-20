import 'package:flutter/material.dart';
import 'package:get/get_state_manager/get_state_manager.dart';
import 'package:metamask_connect/controllers/controller.dart';
import 'package:metamask_connect/extensions/extension.dart';
import 'package:niku/widget/button.dart';

class Home extends StatelessWidget {
  const Home({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return GetBuilder<HomeController>(
      init: HomeController(),
      builder: (h) {
        String shown = '';
        if (h.isConnected && h.isInOperatingChain) {
          shown = "You are connected to Rinkeby";
        } else if (h.isConnected && !h.isInOperatingChain) {
          shown = 'Wrong Chain ! Please connect to Rinkeby!';
        } else if (h.isEnabled) {
          return NikuButton.outlined('Connect'.text
            ..bold
            ..fontSize = 20);
        } else {
          shown = 'Your browser is not supported !';
        }

        return Center(
          child: shown.text
            ..bold
            ..fontSize = 20,
        );
      },
    );
  }
}
