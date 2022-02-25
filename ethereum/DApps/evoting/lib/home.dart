import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:http/http.dart';
import 'package:web3dart/web3dart.dart';

class HomePage extends StatefulWidget {
  const HomePage({Key? key}) : super(key: key);

  @override
  State<HomePage> createState() => _HomePageState();
}

class _HomePageState extends State<HomePage> {
  //
  //-----[ START ] Creating variables
  //
  late Client httpClient;

  late Web3Client ethClient;

  //url from Infura
  final String blockchainUrl = "https://rinkeby.infura.io/v3/26b02f5644054472b3e129acc148be2d";

  //strore the value of alpha and beta
  int totalVotesA = 0;
  int totalVotesB = 0;
  //
  //-----[ END ] Creating variables
  //

  @override
  void initState() {
    // In our initState callback, we are going to initialize
    // our httpClient and Web3client and call a method to get
    // the values of alpha and beta from our smart contract
    httpClient = Client();
    ethClient = Web3Client(
      blockchainUrl,
      httpClient,
    );
    getTotalVotes();
    super.initState();
  }

  Future<DeployedContract> getContract() async {
    // obtain our smart contract using rootbundle to access our json file
    String abiFile = await rootBundle.loadString("assets/contract.json");

    // Note, to get the contract address, go back to your Remix IDE,
    // click on the Deploy and Run tab, and click the copy icon
    // below the Deployed Contracts
    String contractAddress = "0x3d25c140fabbd73E2B58A55E704aa234F798912c";

    // Construct a contract using the DeployedContract class from our web3dart
    // package, which takes in the ABI file, name of our smart contract
    // (which in our case was Voting), and the contract address and
    // returns it from our function.
    final contract = DeployedContract(
      ContractAbi.fromJson(abiFile, "Voting"),
      EthereumAddress.fromHex(contractAddress),
    );

    return contract;
  }

  ///
  /// This function will be used to call a
  /// function named [name] inside of our smart contract
  ///
  Future<List<dynamic>> callFunction(String name) async {
    final contract = await getContract();
    final function = contract.function(name);
    final result = await ethClient.call(
      contract: contract,
      function: function,
      params: [],
    );
    return result;
  }

  ///
  /// In this function, we are going to use this callFunction to call functions
  /// such as getTotalVotesAlpha and getTotalVotesBeta, which we created in our smart contract.
  /// Be sure that the name you pass matches exactly what you have on your deployed contract.
  ///
  Future<void> getTotalVotes() async {
    List<dynamic> resultsA = await callFunction("getTotalVotesAlpha");
    List<dynamic> resultsB = await callFunction("getTotalVotesBeta");

    // will be update the state and the UI
    setState(() {
      totalVotesA = int.parse(resultsA[0].toString());
      totalVotesB = int.parse(resultsB[0].toString());
    });
  }

  snackBar({String? label}) {
    ScaffoldMessenger.of(context).showSnackBar(
      SnackBar(
        content: Row(
          mainAxisAlignment: MainAxisAlignment.spaceBetween,
          children: [
            Text(label!),
            const CircularProgressIndicator(
              color: Colors.white,
            )
          ],
        ),
        duration: const Duration(days: 1),
        backgroundColor: Colors.blue,
      ),
    );
  }

  ///
  /// What makes the voting action different is that we are doing
  /// a write and not a read request.
  ///
  /// So, we are going to make use of our private key from Metamask.
  ///
  /// Or you can make use of any private key; it would go through,
  /// since this is a test account.
  ///
  /// Also, instead of using the call, we are instead going to use sendTransaction
  /// and pass our private key, a transaction object, and specify the chainId,
  /// which in our case (Rinkeby) is 4
  ///
  Future<void> vote(bool voteAlpha) async {
    snackBar(label: "Recording vote");
    //obtain private key for write operation
    // for the demo , create a folder private with a file pk-dev.txt that will contain the private key
    String pkValue = await rootBundle.loadString("private/pk-dev.txt");
    Credentials key = EthPrivateKey.fromHex(pkValue);

    //obtain our contract from abi in json file
    final contract = await getContract();

    // extract function from json file
    final function = contract.function(
      voteAlpha ? "voteAlpha" : "voteBeta",
    );

    //send transaction using our private key, function and contract
    await ethClient.sendTransaction(
      key,
      Transaction.callContract(
        contract: contract,
        function: function,
        parameters: [],
      ),
      chainId: 4,
    );

    ScaffoldMessenger.of(context).removeCurrentSnackBar();
    snackBar(label: "verifying vote");
    //set a 20 seconds delay to allow the transaction to be verified before trying to retrieve the balance
    Future.delayed(const Duration(seconds: 20), () {
      ScaffoldMessenger.of(context).removeCurrentSnackBar();
      snackBar(label: "retrieving votes");
      getTotalVotes();
      ScaffoldMessenger.of(context).clearSnackBars();
    });
  }

  @override
  Widget build(BuildContext context) {
    return SafeArea(
      child: Scaffold(
        appBar: AppBar(
          title: const Text('Evoting Demo on Ethereum'),
        ),
        body: Container(
          padding: const EdgeInsets.all(20),
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.center,
            children: [
              Container(
                padding: const EdgeInsets.all(30),
                alignment: Alignment.center,
                decoration:
                    const BoxDecoration(color: Colors.blue, borderRadius: BorderRadius.all(Radius.circular(20))),
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.spaceAround,
                  children: [
                    Column(
                      children: [
                        const CircleAvatar(
                          child: Text("A"),
                        ),
                        const SizedBox(
                          height: 10,
                        ),
                        Text(
                          "Total Votes: $totalVotesA",
                          style: const TextStyle(color: Colors.white, fontWeight: FontWeight.bold),
                        )
                      ],
                    ),
                    Column(
                      children: [
                        const CircleAvatar(
                          child: Text("B"),
                        ),
                        const SizedBox(
                          height: 10,
                        ),
                        Text("Total Votes: $totalVotesB",
                            style: const TextStyle(color: Colors.white, fontWeight: FontWeight.bold))
                      ],
                    ),
                  ],
                ),
              ),
              const SizedBox(
                height: 30,
              ),
              Row(
                mainAxisAlignment: MainAxisAlignment.spaceAround,
                children: [
                  ElevatedButton(
                    onPressed: () {
                      vote(true);
                    },
                    child: const Text('Vote Alpha'),
                    style: ElevatedButton.styleFrom(shape: const StadiumBorder()),
                  ),
                  const SizedBox(
                    height: 30,
                  ),
                  ElevatedButton(
                    onPressed: () {
                      vote(false);
                    },
                    child: const Text('Vote Beta'),
                    style: ElevatedButton.styleFrom(shape: const StadiumBorder()),
                  )
                ],
              )
            ],
          ),
        ),
      ),
    );
  }
}
