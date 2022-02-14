# The Auction Smart Contract

1. Smart Contract for a Decentralized Auction like an eBay alternative;

2. The Auction has an owner (the person who sells a good or service), a start and an end
date;

3. The owner can cancel the auction if there is an emergency or can finalize the auction
after its end time;

4. People are sending ETH by calling a function called placeBid(). The sender’s address
and the value sent to the auction will be stored in mapping variable called bids;

5. Users are incentivized to bid the maximum they’re willing to pay, but they are not bound
to that full amount, but rather to the previous highest bid plus an increment. The
contract will automatically bid up to a given amount;

6. The highestBindingBid is the selling price and the highestBidder the person who won
the auction;

7. After the auction ends the owner gets the highestBindingBid and everybody else
withdraws their own amount;


## Function Modifiers

1. Function modifiers are used to modify the behaviour of a function. They test a condition
before calling a function which will be executed only if the condition of the modifier
evaluates to true;

2. Using function modifiers you avoid redundant-code and possible errors;

3. They are contract properties and are inherited;

4. They don’t return and use only require();

5. They are defined using the modifier keyword;
