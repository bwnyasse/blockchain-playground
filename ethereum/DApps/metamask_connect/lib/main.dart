import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:metamask_connect/widgets/home.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) => const GetMaterialApp(
        title: 'Metamask connect Sample',
        home: Home(),
      );
}
