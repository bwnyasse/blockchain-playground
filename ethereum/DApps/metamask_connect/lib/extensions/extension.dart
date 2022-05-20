import 'package:flutter/material.dart';
import 'package:niku/widget/column.dart';
import 'package:niku/widget/row.dart';
import 'package:niku/widget/text.dart';
import 'package:niku/widget/wrap.dart';

// Define some extensions to make life easier.

extension StringE on String {
  NikuText get text => NikuText(this);
}

extension ListE on List<Widget> {
  NikuColumn get column => NikuColumn(this);
  NikuRow get row => NikuRow(this);
  NikuWrap get wrap => NikuWrap(this);
}
