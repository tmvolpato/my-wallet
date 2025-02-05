// This setup uses Hardhat Ignition to manage smart contract deployments.
// Learn more about it at https://hardhat.org/ignition

import { buildModule } from "@nomicfoundation/hardhat-ignition/modules";

const MyWalletModule = buildModule("MyWalletModule", (m) => {  

  const myWallet = m.contract("MyWallet");

  return { myWallet };
});

export default MyWalletModule;