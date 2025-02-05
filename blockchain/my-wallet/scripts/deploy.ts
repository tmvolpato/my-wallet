import { ethers } from "hardhat";

async function main() {
    const MyWallet = await ethers.getContractFactory("MyWallet");
    const contract = await MyWallet.deploy();

    await contract.waitForDeployment();

    console.log(`Contract deployed at ${contract.target}`);
}

main().catch((error) => {
    console.error(error);
    process.exitCode = 1;
});