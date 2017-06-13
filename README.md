#Ethereum JAVA call examples

##Overview
- This examples use org.web3j package
- For build contract "Customer" use generate.sh
- Before use genetare.sh install "solc" - solidity compiler and add to PATH

##org.web3j key features
 - This library use HTTP Rest API
 - Base steps to create java stub: solidity contract file -> solc compiler ->  
 org.web3j.codegen.SolidityFunctionWrapperGenerator java source generator
 - New address generation: Keys.createEcKeyPair()
 - For Solidity uint256/int256/int/uint types used java java.math.BigInteger class
 
##Eth main features
- Gas used to pay contract execution on peer EVM.
- Gas price is price of one element of gas. For example 1 unit of gas = 20000wei or 1=1 (depends of network)
- Contract I can call (only read), write only with transaction
- Every transaction should has a Gas Limit field. Gas count for simple(move money from A to B address) tx is 21000, 
for contract or contract depends on size(bytes) of transaction or method logic at contract
- After contract upload you should call transaction receipt to get contract address. Address created(mined) after block creation
- For private Eth Blockchain explorer/monitoring you can use: https://github.com/artjoma/actis
- Consensus algorithm [POA](https://github.com/paritytech/parity/wiki/Demo-PoA-tutorial)
- Solidity web IDE [Remix](https://ethereum.github.io/browser-solidity)
- Eth use 18 decimals
- Token(asset) is contract. Usually it implement [ERC20](https://github.com/ethereum/EIPs/issues/20) specification or new 
specification [ERC223](https://github.com/ethereum/EIPs/issues/223). Implementation of [ERC20](https://github.com/Firstbloodio/token/blob/master/smart_contract/FirstBloodToken.sol)


