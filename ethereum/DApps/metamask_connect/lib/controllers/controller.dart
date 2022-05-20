import 'package:flutter_web3/flutter_web3.dart';
import 'package:get/get_state_manager/get_state_manager.dart';

// The web3 part goes here ...
class HomeController extends GetxController {

  String currentAddress = '';

  int currentChain = -1;

  static const operatingChain = 4;

  /// Check if we can access the Ethereum object getter.
  /// From the implementation below, the Etherum object can be null
  /// if the browser doesn’t have any provider available.
  /// i.e. Metamask, Wallet Connect, Binance Chain Wallet, etc.
  bool get isEnabled => ethereum != null;

  /// Check for the chain that is currently connected
  /// to is Rinkeby or not (chain id 4) : https://chainlist.org/
  bool get isInOperatingChain => currentChain == operatingChain;

  ///  Overall check for connection to the website.
  /// ethereum.isConnected() is only used for provider availability,
  /// so we have to check from the address that is exposed from the node.
  bool get isConnected => isEnabled && currentAddress.isNotEmpty;

  ///
  /// Connect to provider and update [currentAddress] and [currentChain] .
  ///
  connect() async {
    if (isEnabled) {
      // By using ethereum.requestAccount
      // we can prompt the provider to make the connection with the website.
      // (i.e. Metamask modal) that returns the list of addresses
      final accs = await ethereum!.requestAccount();

      // TODO: Be sure to handle errors if the user rejects the modal
      if (accs.isNotEmpty) currentAddress = accs.first;

      currentChain = await ethereum!.getChainId();

      update();
    }
  }

  ///
  /// Handle accountChanged and chainChanged events.
  /// This will call clear every time the user changed
  /// his account or chain and update the variable above.
  ///
  clear() {
    currentAddress = '';
    currentChain = -1;
    update();
  }

  init() {
    if (isEnabled) {
      ethereum!.onAccountsChanged((accs) {
        clear();
      });

      ethereum!.onChainChanged((chain) {
        clear();
      });
    }
  }

  @override
  void onInit() {
    init();
    super.onInit();
  }
}
